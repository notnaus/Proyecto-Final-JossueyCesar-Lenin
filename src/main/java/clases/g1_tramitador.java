/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clases;

import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import basededatos.g1_basededatos;

/**
 *
 * @author josrm
 */
public class g1_tramitador extends g1_persona {
    private String codEmpleado;
    g1_tramitador Tramitador;

    public g1_tramitador(String codEmpleado, String cedula, String nombre, int id, String clave, int tipo) {
        super(cedula, nombre, id, clave, tipo);
        this.codEmpleado = codEmpleado;
    }

    public g1_tramitador(String codEmpleado, String cedula, String nombre) {
        super(cedula, nombre);
        this.codEmpleado = codEmpleado;
    }

    public g1_tramitador(String codEmpleado) {
        this.codEmpleado = codEmpleado;
    }

    public g1_tramitador() {
    }

    public String getCodEmpleado() {
        return codEmpleado;
    }

    public void setCodEmpleado(String codEmpleado) {
        this.codEmpleado = codEmpleado;
    }

    @Override
    public String toString() {
        String salida = "Datos del tramidator \n"
                + "Id usuario: "+ getId() +"\n"
                + "Nombre: "+getNombre() + "\n"
                + "Cedula: "+ getCedula() +"\n"
                + "Clave Acceso: "+ getClave() +"\n"
                + "Codigo Empleado: "+ getCodEmpleado() +"\n";
        return salida;
    }
    @Override
    public void menu(ArrayList sistemaCC,
            ArrayList dbMovimientos,
            g1_usuario u){
        this.dbMovimientos = dbMovimientos;
        this.sistemaCC = sistemaCC;
        this.Tramitador = (g1_tramitador)u;
        String menu[] = {
            "Menu principal de Tramitador",
            "1. Verificar listado de transacciones",
            "2. Consultar movimientos de un cliente",
            "3. Modificar perfil",
            "0. Salir del sistema"
        };
        int op= -1;
        do
        { 
            try{
                op = Integer.parseInt(JOptionPane.showInputDialog(null,
                        menu,
                        "Menu Principal", 
                        JOptionPane.QUESTION_MESSAGE));
                switch(op){
                    case 0:{
                        JOptionPane.showMessageDialog(null,"Saliendo del sistema",
                            "Informacion",JOptionPane.OK_OPTION);
                        g1_basededatos.crearArchivoUsuarios(sistemaCC);
                        g1_basededatos.crearArchivoMovimientos(dbMovimientos);
                        System.exit(0);
                        break;
                    }
                    case 1:{
                        mostarTransaccionesPendientes();
                        break;
                    }
                    case 2:{
                        consultarCuentaCliente();
                        break;
                    }
                    case 3:{
                        modificarPerfil();
                        break;
                    }
                }
            }catch (NumberFormatException e){
                JOptionPane.showMessageDialog(null,
                    "Menu Principal\n"
                        + "Debe introducir una opcion valida",
                    "Informacion",JOptionPane.OK_OPTION);
            }catch(NullPointerException e){
                JOptionPane.showMessageDialog(null,
                    "Menu Principal\n"
                        + "Debe introducir un valor dentro del rango",
                    "Informacion",JOptionPane.OK_OPTION);
            }
        }while (op!=0);   
    }
    
    private void consultarCuentaCliente(){
        try{
            int sel = Integer.parseInt(JOptionPane.showInputDialog(null,
                    "Id del cliente a consultar"));
            g1_usuario u = g1_usuario.getUsuario(sistemaCC, sel);
            if (u instanceof g1_cliente){
                g1_cliente cliente = (g1_cliente)u;
                if (cliente!=null){
                    String salida = cliente.toString();
                    salida+=g1_movimiento.mostarListaMovimientosCliente(dbMovimientos, cliente.getCedula());
                    System.out.println(salida);
                    JOptionPane.showMessageDialog(null, salida);
                }else{
                    JOptionPane.showMessageDialog(null,"Cliente no registrado");
                }  
            }else{
                JOptionPane.showMessageDialog(null,"Cliente no registrado");
            }
        }catch(NumberFormatException e){
                JOptionPane.showMessageDialog(null,"Debe ingresar un valor entero");
        }catch(IndexOutOfBoundsException e){
            JOptionPane.showMessageDialog(null,"Ha seleccionado un elemento no disponible");
        }
    }
    private void mostarTransaccionesPendientes(){
        ArrayList<g1_movimiento> pendientes = 
                g1_movimiento.listaMovimientosPendientes(dbMovimientos);
        boolean continuar = true;
        do{
            try{
                System.out.println(g1_movimiento.mostarListaMovimientos(pendientes));
                int sel = Integer.parseInt(JOptionPane.showInputDialog(null, 
                    g1_movimiento.mostarListaMovimientos(pendientes)+"\n"
                    + "Seleccione la transaccion a aplicar"));
                g1_movimiento m = pendientes.get((sel-1));
                g1_cliente c = (g1_cliente)g1_usuario.getUsuario(sistemaCC, m.getIdCliente());
                
                sel = JOptionPane.showConfirmDialog(null,"Cliente: "+c.getNombre()+"\n\n"+ 
                        m.toString()+"\n" 
                        + "Desea aplicar este movimiento",
                        "Seleccione el movimiento a aplicar",0);
                if(sel == 0){
                    if(m.getTipo()>1 && m.getTipo()<5){
                        if (m.getMonto()>c.getSaldo()){
                            JOptionPane.showMessageDialog(null,"Movimiento no permitido\n"
                                    + "El monto es superior al contenido en el banco\n"
                                    + "Posibilidad de varias solicides realizadas");
                        }else{
                            g1_realizacion_movimiento mR = new g1_realizacion_movimiento();
                            mR.setId(m.getId());
                            mR.setIdCliente(m.getIdCliente());
                            mR.setTipo(m.getTipo());
                            mR.setMonto(m.getMonto());
                            mR.setEstado(1);
                            mR.setIdTramitador(codEmpleado);
                            mR.setFechaRealizado(momento());
                            g1_movimiento.actualizarMovimiento(dbMovimientos, mR);
                            c.setSaldo((c.getSaldo()-m.getMonto()));
                            g1_usuario.actualizarUsuario(sistemaCC, c);  
                            JOptionPane.showMessageDialog(null, mR.toString()+"\n"
                                    + "Aplicacion terminada");
                        } 
                    }else{
                        g1_realizacion_movimiento mR = new g1_realizacion_movimiento();
                            mR.setId(m.getId());
                            mR.setIdCliente(m.getIdCliente());
                            mR.setTipo(m.getTipo());
                            mR.setMonto(m.getMonto());
                            mR.setEstado(1);
                            mR.setIdTramitador(codEmpleado);
                            mR.setFechaRealizado(momento());
                            g1_movimiento.actualizarMovimiento(dbMovimientos, mR);
                            c.setSaldo((c.getSaldo()+m.getMonto()));
                            g1_usuario.actualizarUsuario(sistemaCC, c);  
                            JOptionPane.showMessageDialog(null, mR.toString()+"\n"
                                    + "Aplicacion terminada");
                    }
                }else{
                    JOptionPane.showMessageDialog(null,"No se ha aplicado el movimiento");
                }
                continuar = false;
            }catch(NumberFormatException e){
                JOptionPane.showMessageDialog(null,"Debe ingresar un valor entero");
            }catch(IndexOutOfBoundsException e){
                JOptionPane.showMessageDialog(null,"Ha seleccionado un elemento no disponible");
            }
        }while(continuar);
        
        
    }
    private void modificarPerfil(){
        int sel = JOptionPane.showConfirmDialog(null, Tramitador.toString()+"\n\n"
            + "Desea Modificar sus datos personales?", "Confirmacion", 0);
        if(sel==0){
            boolean continuar = true;
            int sel2 = JOptionPane.showConfirmDialog(null, "Desea Modificar su clave?",
                    "Confirmacion", 0);
            if (sel2 == 0){
                do{
                    try{
                        Tramitador.setClave(JOptionPane.showInputDialog(
                                   null, "Defina la clave"));
                       if(Tramitador.getClave().length()<6){
                           JOptionPane.showMessageDialog(null,"El largo minimo es de 6 caracteres");
                       }else{
                           continuar = false;
                       }
                    }catch(NullPointerException ex){
                        JOptionPane.showMessageDialog(null,"No puede salir sin ingresar datos");
                    }
                }while (continuar);
            }
            sel2 = JOptionPane.showConfirmDialog(null, "Desea Modificar su nombre?",
                    "Confirmacion", 0);
            if (sel2 == 0){
                continuar = true;
                do{
                    try{
                        Tramitador.setNombre(JOptionPane.showInputDialog(
                            null, "Nombre cliente"));
                        if(Tramitador.getNombre().length()<4){
                            JOptionPane.showMessageDialog(null,"El largo minimo es de 4 caracteres");
                        }else{
                            continuar = false;
                        }
                    }catch(NullPointerException ex){
                        JOptionPane.showMessageDialog(null,"No puede salir sin ingresar datos");
                    }
                }while (continuar);
            }
            sel = JOptionPane.showConfirmDialog(null, Tramitador.toString()+"\n\n"
            + "Confirma que desea actualizar los cambios?", "Confirmacion", 0);
            if(sel==0){
                g1_usuario.actualizarUsuario(sistemaCC, this);
                JOptionPane.showMessageDialog(null,"Cambios realizados");
            }else{
                JOptionPane.showMessageDialog(null,"Actualizacion cancelada");
            }
        }else{
            JOptionPane.showMessageDialog(null,"No se realizan cambios");
        }
    }

       private String momento(){
        LocalDateTime now = LocalDateTime.now();
        int year = now.getYear();
        int month = now.getMonthValue();
        int day = now.getDayOfMonth();
        int hour = now.getHour();
        int minute = now.getMinute();
        int second = now.getSecond();
        int millis = now.get(ChronoField.MILLI_OF_SECOND);
        
        String salida = String.valueOf(day)+"-"
                + String.valueOf(month)+"-"
                + String.valueOf(year)+"  "
                + String.valueOf(hour)+":"
                + String.valueOf(minute)+":"
                + String.valueOf(millis);
        
        return salida;
    }
}
