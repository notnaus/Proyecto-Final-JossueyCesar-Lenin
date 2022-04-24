/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clases;
import basededatos.g1_basededatos;
import java.util.ArrayList;
import javax.swing.JOptionPane;


/**
 *
 * @author josrm
 */
public class g1_administrador extends g1_persona {
     private String idSucursal;

    public g1_administrador(String idSucursal, String cedula, String nombre, int id, String clave, int tipo) {
        super(cedula, nombre, id, clave, tipo);
        this.idSucursal = idSucursal;
    }

    public g1_administrador(String idSucursal, String cedula, String nombre) {
        super(cedula, nombre);
        this.idSucursal = idSucursal;
    }

    public g1_administrador(String idSucursal) {
        this.idSucursal = idSucursal;
    }

    public g1_administrador() {
    }

    public String getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(String idSucursal) {
        this.idSucursal = idSucursal;
    }
    @Override
    public void menu(ArrayList sistemaCC,ArrayList dbMovimientos,g1_usuario u){
        this.dbMovimientos = dbMovimientos;
        this.sistemaCC = sistemaCC;
        String menu[] = {
            "Menu principal de administrador",
            "1. Agregar cliente",
            "2. Agregar cajero",
            "3. Consultar cliente",
            "4. Consultar cajero",
            "5. Eliminar usuario del sistema",
            "6. Listar usuarios del sistema",
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
                        System.exit(0);
                        break;
                    }
                    case 1:{
                        crearCliente();
                        break;
                    }
                    case 2:{
                        crearCajero();
                        break;           
                    }
                    case 3:{
                        boolean continuar = true;
                        do{
                            try{
                                JOptionPane.showMessageDialog(null,
                                    buscarCliente(
                                            Integer.parseInt(JOptionPane.showInputDialog(
                                            null, "Id del cliente a buscar:")),
                                            sistemaCC ));
                                continuar = false;
                            }catch(NumberFormatException e){
                                JOptionPane.showMessageDialog(null,"Debe ingresar un valor entero");
                            }
                        }while(continuar);
                        break;
                    }
                    case 4: {
                        boolean continuar = true;
                        do{
                            try{
                                JOptionPane.showMessageDialog(null,
                                    buscarCajero(
                                            Integer.parseInt(JOptionPane.showInputDialog(
                                            null, "Id del cajero a buscar:")),
                                            sistemaCC ));
                                continuar = false;
                            }catch(NumberFormatException e){
                                JOptionPane.showMessageDialog(null,"Debe ingresar un valor entero");
                            }
                        }while(continuar);
                        break;
                    }
                    case 5: {
                        boolean continuar = true;
                        do{
                            try{
                                borrarUsuario(
                                        Integer.parseInt(JOptionPane.showInputDialog(
                                        null, "Id del Usuario a borrar:")),
                                        sistemaCC );
                                JOptionPane.showMessageDialog(null,"Usuario eliminado");
                                continuar = false;
                            }catch(NumberFormatException e){
                                JOptionPane.showMessageDialog(null,"Debe ingresar un valor entero");
                            }
                        }while(continuar);
                        break;
                    }
                    case 6: {
                        System.out.println(g1_usuario.listarUsuariosSistema(sistemaCC));
                        JOptionPane.showMessageDialog(null,g1_usuario.listarUsuariosSistema(sistemaCC));
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
    
    private void borrarUsuario(int id, ArrayList dbUsuarios){
        for(int i = 0 ; i<dbUsuarios.size();i++){
            g1_usuario u = (g1_usuario)dbUsuarios.get(i);
            if (u.getId()==id){
                dbUsuarios.remove(i);
            } 
        }
    }
    private String buscarCajero(int id, ArrayList dbUsuarios){
        for(int i = 0 ; i<dbUsuarios.size();i++){
            g1_usuario u = (g1_usuario)dbUsuarios.get(i);
            if (u instanceof g1_cajero){
                g1_cajero c = (g1_cajero)u;
                if (c.getId()==id){
                    return (c.toString());
                } 
            } 
        }
        return ("Id no se encuentra registrado");
    }
    private String buscarCliente(int id, ArrayList dbUsuarios){
        for(int i = 0 ; i<dbUsuarios.size();i++){
            g1_usuario u = (g1_usuario)dbUsuarios.get(i);
            if (u instanceof g1_cliente){
                g1_cliente c = (g1_cliente)u;
                if (c.getId()==id){
                    return (c.toString());
                } 
            } 
        }
        return ("Id no se encuentra registrado");
    }
    private void crearCliente(){
        boolean continuar;
        g1_cliente c = new g1_cliente();
        
        continuar = true;
        do{
            try{
                c.setId(Integer.parseInt(JOptionPane.showInputDialog(
                        null, "Id del cliente")));
                continuar = false;
            }catch(NumberFormatException e){
                JOptionPane.showMessageDialog(null,"Debe ingresar un entero");
            }
        }while (continuar);
        /*
        Este bloque esta totalmente validado. Para entrada de texto
        */
        continuar = true;
        do{
            try{
                c.setCedula(JOptionPane.showInputDialog(
                    null, "Numero de cedula"));
                if(c.getCedula().length()<9){
                    JOptionPane.showMessageDialog(null,"El largo minimo es de 9 caracteres");
                }else{
                    continuar = false;
                }
            }catch(NullPointerException ex){
                JOptionPane.showMessageDialog(null,"No puede salir sin ingresar datos");
            }
        }while(continuar);
        
        continuar = true;
        do{
            try{
                c.setClave(JOptionPane.showInputDialog(
                           null, "Defina la clave"));
               if(c.getClave().length()<6){
                   JOptionPane.showMessageDialog(null,"El largo minimo es de 6 caracteres");
               }else{
                   continuar = false;
               }
            }catch(NullPointerException ex){
                JOptionPane.showMessageDialog(null,"No puede salir sin ingresar datos");
            }
        }while (continuar);
        continuar = true;
        do{
            try{
                c.setNombre(JOptionPane.showInputDialog(
                    null, "Nombre cliente"));
                if(c.getNombre().length()<4){
                    JOptionPane.showMessageDialog(null,"El largo minimo es de 4 caracteres");
                }else{
                    continuar = false;
                }
            }catch(NullPointerException ex){
                JOptionPane.showMessageDialog(null,"No puede salir sin ingresar datos");
            }
        }while (continuar);
        
        continuar = true;
        do{
            try{
                c.setSaldo(Double.parseDouble(JOptionPane.showInputDialog(
                        null, "Saldo inicial de la cuenta:")));
                continuar = false;
            }catch(NumberFormatException e){
                JOptionPane.showMessageDialog(null,"Debe ingresar un valor numerico");
            }
        }while (continuar);
        c.setTipo(1);
        sistemaCC.add(c);
    }
    private void crearCajero(){
        boolean continuar;
        g1_cajero c = new g1_cajero();
        
        continuar = true;
        do{
            try{
                c.setId(Integer.parseInt(JOptionPane.showInputDialog(
                        null, "Id del cajero")));
                continuar = false;
            }catch(NumberFormatException e){
                JOptionPane.showMessageDialog(null,"Debe ingresar un entero");
            }
        }while (continuar);
        /*
        Este bloque esta totalmente validado. Para entrada de texto
        */
        continuar = true;
        do{
            try{
                c.setCedula(JOptionPane.showInputDialog(
                    null, "Numero de cedula"));
                if(c.getCedula().length()<9){
                    JOptionPane.showMessageDialog(null,"El largo minimo es de 9 caracteres");
                }else{
                    continuar = false;
                }
            }catch(NullPointerException ex){
                JOptionPane.showMessageDialog(null,"No puede salir sin ingresar datos");
            }
        }while(continuar);
        
        continuar = true;
        do{
            try{
                c.setClave(JOptionPane.showInputDialog(
                           null, "Defina la clave"));
               if(c.getClave().length()<6){
                   JOptionPane.showMessageDialog(null,"El largo minimo es de 6 caracteres");
               }else{
                   continuar = false;
               }
            }catch(NullPointerException ex){
                JOptionPane.showMessageDialog(null,"No puede salir sin ingresar datos");
            }
        }while (continuar);
        continuar = true;
        do{
            try{
                c.setNombre(JOptionPane.showInputDialog(
                    null, "Nombre cajero"));
                if(c.getNombre().length()<4){
                    JOptionPane.showMessageDialog(null,"El largo minimo es de 4 caracteres");
                }else{
                    continuar = false;
                }
            }catch(NullPointerException ex){
                JOptionPane.showMessageDialog(null,"No puede salir sin ingresar datos");
            }
        }while (continuar);
        continuar = true;
        do{
            try{
                c.setCodEmpleado(JOptionPane.showInputDialog(
                    null, "Codigo Empleado:"));
                if(c.getNombre().length()<4){
                    JOptionPane.showMessageDialog(null,"El largo minimo es de 4 caracteres");
                }else{
                    continuar = false;
                }
            }catch(NullPointerException ex){
                JOptionPane.showMessageDialog(null,"No puede salir sin ingresar datos");
            }
        }while (continuar);
        c.setTipo(2);
        sistemaCC.add(c);
    }
}
