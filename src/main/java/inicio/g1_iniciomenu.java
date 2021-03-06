/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package inicio;

import clases.g1_administrador;
import clases.g1_movimiento;
import clases.g1_usuario;
import basededatos.g1_basededatos;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author josrm
 */
public class g1_iniciomenu {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        g1_administrador admin = new g1_administrador();
        admin.setId(1001);
        admin.setClave("123456");
        admin.setTipo(0);
        admin.setNombre("Administrador");
        admin.setCedula("1000000001");
        admin.setIdSucursal("Main");
        
        Path archivoUsuarios = Paths.get("./sistemaCC.txt");
        Path archivoMovimientos = Paths.get("./dbMovimientos.txt");
        ArrayList<g1_usuario> sistemaCC;
        ArrayList<g1_movimiento> dbMovimientos;
        
        if (Files.exists(archivoUsuarios)){
            sistemaCC = g1_basededatos.leerArchivoUsuarios();
        }else{
            sistemaCC = new ArrayList();
        }
        
        sistemaCC.add(admin);
        
        if (Files.exists(archivoMovimientos)){
            dbMovimientos = g1_basededatos.leerArchivoMovimientos();
        }else{
            dbMovimientos = new ArrayList();
        }
        
        g1_usuario usuario = login(sistemaCC);
        if(usuario!=null){
            usuario.menu(sistemaCC,dbMovimientos,usuario);
        }else{
            JOptionPane.showMessageDialog(null,"Usuario a cancelado"
            + " el inicio de sesion","Error",JOptionPane.ERROR_MESSAGE);
        }
    }
    public static g1_usuario login(ArrayList sistemaCC){
        int user;
        String pass;
        boolean ingreso = false;
        JOptionPane.showMessageDialog(null, "Bienvenidos al Sistema");
        do{
            try{
                user = Integer.parseInt(JOptionPane.showInputDialog(null, "Ingrese su ID de usuario"));
                pass = JOptionPane.showInputDialog(null, "Ingrese su contrasena");
                
                g1_usuario usuario = g1_usuario.getUsuario(sistemaCC, user);
                if (usuario!=null){
                    if (user==usuario.getId() && pass.equals(usuario.getClave())) 
                    {
                        return usuario;
                    }         
                else  
                    {
                        JOptionPane.showMessageDialog(null, "Acceso denegado:\n"
                        + "Por favor ingrese un usuario y contrase??a correctos",  
                        "ACCESO DENEGADO", JOptionPane.ERROR_MESSAGE);
                    } 
                }else{
                    JOptionPane.showMessageDialog(null,"Usuario no registrado");
                }  
            }
            catch (Exception e){
                return null;
            }
        }while(!ingreso);
        return null;
    }
}
