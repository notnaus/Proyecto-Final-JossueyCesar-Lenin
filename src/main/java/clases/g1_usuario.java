/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clases;

import java.util.ArrayList;

/**
 *
 * @author josrm
 */
public class g1_usuario {
    ArrayList<g1_usuario> sistemaCC;
    ArrayList<g1_movimiento> dbMovimientos;
    private int id;
    private String clave;
    private int tipo;

    public g1_usuario(int id, String clave, int tipo) {
        this.id = id;
        this.clave = clave;
        this.tipo = tipo;
    }
    public g1_usuario() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }
    
    public static g1_usuario getUsuario(ArrayList<g1_usuario> sistemaCC,int id){
        for(int i = 0; i<sistemaCC.size();i++){
            g1_usuario temporal = sistemaCC.get(i);
            if (temporal.getId() == id){
                return temporal;
            }
        }
        return null;  
    }
    public static g1_usuario getUsuario(ArrayList<g1_usuario> sistemaCC,String cedula){
        for(int i = 0; i<sistemaCC.size();i++){
            g1_persona temporal =(g1_persona)sistemaCC.get(i);
            if (temporal.getCedula().equals(cedula)){
                return temporal;
            }
        }
        return null;  
    }
    public void menu(ArrayList sistemaCC,ArrayList dbMovimientos,g1_usuario u){
        System.out.println("Menu de usuario");
    }
    public static void actualizarUsuario(ArrayList sistemaCC, g1_usuario usuario){
        for(int i = 0; i<sistemaCC.size();i++){
            g1_usuario temporal =(g1_usuario) sistemaCC.get(i);
            if(temporal.getId()==usuario.getId()){
                sistemaCC.set(i, usuario);
            }
        }
    }
    public static String listarUsuariosSistema(ArrayList sistemaCC){
        String tipo="";
        String salida =String.format("%10s %15s %40s %20s\n",
            "IdUsuario","Cedula","Nombre","Tipo");
        for(int i = 0; i<sistemaCC.size();i++){
            g1_persona temporal =(g1_persona) sistemaCC.get(i);
            switch(temporal.getTipo()){
                case 1: tipo = "Cliente";break;
                case 2: tipo = "Tramitador";break;
            }
            salida += String.format("%10s %15s %40s %20s\n", 
                    temporal.getId(),
                    temporal.getCedula(),
                    temporal.getNombre(),
                    tipo
                    );
        }
        return salida;
    }
}
