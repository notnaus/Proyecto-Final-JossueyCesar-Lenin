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
public class g1_cliente extends g1_persona {
     double saldo;
    g1_cliente cliente;

    public g1_cliente(double saldo, String cedula, String nombre, int id, String clave, int tipo) {
        super(cedula, nombre, id, clave, tipo);
        this.saldo = saldo;
    }

    public g1_cliente(double saldo, String cedula, String nombre) {
        super(cedula, nombre);
        this.saldo = saldo;
    }

    public g1_cliente(double saldo) {
        this.saldo = saldo;
    }

    public g1_cliente() {
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }
    

    @Override
    public String toString() {
        String salida = "Datos del cliente \n"
                + "Id usuario: "+ getId() +"\n"
                + "Nombre: "+getNombre() + "\n"
                + "Cedula: "+ getCedula() +"\n"
                + "Clave Acceso: "+ getClave() +"\n"
                + "Saldo: "+ getSaldo() +"\n";
        return salida;
    }
    @Override
    public void menu(ArrayList sistemaCC,
            ArrayList dbMovimientos,
            g1_usuario u){
        this.dbMovimientos = dbMovimientos;
        this.sistemaCC = sistemaCC;
        this.cliente = (g1_cliente)u;
        String menu[] = {
            "Menu principal de cliente",
            "1. Realizar Transaccion",
            "2. Modificar perfil",
            "3. Consultar movimientos",
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
                        realizarMovimiento();
                        break;
                    }
                    case 2:{
                        modificarPerfil();
                        break;
                    }
                    case 3:{
                        consultarMovimientos();
                        break;
                    }
                }
            }catch (NumberFormatException e){
                JOptionPane.showMessageDialog(null,
                    "Menu Principal\n"
                        + "Debe incluir un numero entero",
                    "Informacion",JOptionPane.OK_OPTION);
            }
        }while (op!=0);   
    }
    private void consultarMovimientos(){
        String salida = cliente.toString();
        salida+=g1_movimiento.mostarListaMovimientosCliente(dbMovimientos, cliente.getCedula());
        System.out.println(salida);
        JOptionPane.showMessageDialog(null, salida);
    }
    
    private void modificarPerfil(){
        int sel = JOptionPane.showConfirmDialog(null, cliente.toString()+"\n\n"
            + "Desea Modificar sus datos personales?", "Confirmacion", 0);
        if(sel==0){
            boolean continuar = true;
            int sel2 = JOptionPane.showConfirmDialog(null, "Desea Modificar su clave?",
                    "Confirmacion", 0);
            if (sel2 == 0){
                do{
                    try{
                        cliente.setClave(JOptionPane.showInputDialog(
                                   null, "Defina la clave"));
                       if(cliente.getClave().length()<6){
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
                        cliente.setNombre(JOptionPane.showInputDialog(
                            null, "Nombre cliente"));
                        if(cliente.getNombre().length()<4){
                            JOptionPane.showMessageDialog(null,"El largo minimo es de 4 caracteres");
                        }else{
                            continuar = false;
                        }
                    }catch(NullPointerException ex){
                        JOptionPane.showMessageDialog(null,"No puede salir sin ingresar datos");
                    }
                }while (continuar);
            }
            sel = JOptionPane.showConfirmDialog(null, cliente.toString()+"\n\n"
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
    
    private void realizarMovimiento(){
        String menu[] = {
            "Seleccione el movimiento a realizar",
            "1. Nota de credito",
            "2. Abono a la cuenta ",
            "0. Volver al menu principal"
        };
        int op= -1;
        do
        { 
            try{
                op = Integer.parseInt(JOptionPane.showInputDialog(null,
                        menu,
                        "Menu Principal", 
                        JOptionPane.QUESTION_MESSAGE));
                
                if(op!=0 && op<5){
                    g1_movimiento m = new g1_movimiento();
                    m.setTipo(op);
                    m.setIdCliente(cliente.getCedula());
                    m.setEstado(0);
                    m.setId(calcularIdentificador());
                    boolean continuar = true;
                    do{
                        try{
                            m.setMonto(Double.parseDouble(JOptionPane.showInputDialog(
                                    null, "Monto del movimiento:")));
                            continuar = false;
                        }catch(NumberFormatException e){
                            JOptionPane.showMessageDialog(null,"Debe ingresar un valor numerico");
                        }
                    }while (continuar);
                    System.out.println(m.getTipo());
                    
                    
                    if(m.getTipo()>1 && m.getTipo()<5){
                        if (m.getMonto()>cliente.getSaldo()){
                            JOptionPane.showMessageDialog(null,"Movimiento no permitido\n"
                                    + "El monto es superior\n"
                                    + "Verifique con el Tramitador si existen movimientos pendientes de aplicar");
                        }else{
                            int sel = JOptionPane.showConfirmDialog(null, m.toString()+"\n\n"
                            + "Desea solicitar el movimiento", "Confirmacion", 0);
                            if(sel==0){
                                dbMovimientos.add(m);
                                JOptionPane.showMessageDialog(null,"Movimiento solicitado");
                            }else{
                                JOptionPane.showMessageDialog(null,"Movimiento descartado");
                            }
                        }
                    }else{
                         int sel = JOptionPane.showConfirmDialog(null, m.toString()+"\n\n"
                            + "Desea solicitar el movimiento", "Confirmacion", 0);
                        if(sel==0){
                            dbMovimientos.add(m);
                            JOptionPane.showMessageDialog(null,"Movimiento solicitado");
                        }else{
                            JOptionPane.showMessageDialog(null,"Movimiento descartado");
                        }
                    }
                }
            }catch (NumberFormatException e){
                JOptionPane.showMessageDialog(null,
                    "Menu Principal\n"
                        + "Error de formato de numero: "+e.toString(),
                    "Informacion",JOptionPane.OK_OPTION);
            }catch(NullPointerException e){
                JOptionPane.showMessageDialog(null,
                    "Menu Principal\n"
                        + "Compra cancelada: "+e.toString(),
                    "Informacion",JOptionPane.OK_OPTION);
            }
        }while (op!=0); 
    }
    
    
    
    
     private int calcularIdentificador(){
        LocalDateTime now = LocalDateTime.now();
        int year = now.getYear();
        int month = now.getMonthValue();
        int day = now.getDayOfMonth();
        int hour = now.getHour();
        int minute = now.getMinute();
        int second = now.getSecond();
        int millis = now.get(ChronoField.MILLI_OF_SECOND);
        int ide = year+month+day+hour+minute+second+millis;
        return ide;
    }
    
}
