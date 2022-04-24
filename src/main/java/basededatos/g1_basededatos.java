/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package basededatos;

import clases.g1_cajero;
import clases.g1_cliente;
import clases.g1_movimiento;
import clases.g1_realizacion_movimiento;
import clases.g1_usuario;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author josrm
 */
public class g1_basededatos {
    public static void crearArchivoUsuarios(ArrayList<g1_usuario> sistemaCC) {
        FileWriter fw = null;
        try {
            fw = new FileWriter("./sistemaCC.txt");
            BufferedWriter bf = new BufferedWriter(fw);
            String salida="";
            for (g1_usuario usuario : sistemaCC) {
                if(usuario instanceof g1_cliente){
                    g1_cliente cliente = (g1_cliente)usuario;
                    salida = cliente.getTipo()+","
                        +cliente.getId()+","
                        +cliente.getClave()+","
                        +cliente.getCedula()+","
                        +cliente.getNombre()+","
                        +cliente.getSaldo()+"\n";
                    bf.write(salida);
                }else if(usuario instanceof g1_cajero){
                    g1_cajero cajero = (g1_cajero)usuario;
                    salida =cajero.getTipo()+"," 
                        +cajero.getId()+","
                        +cajero.getClave()+","
                        +cajero.getCedula()+","
                        +cajero.getNombre()+","
                        +cajero.getCodEmpleado()+"\n";
                    bf.write(salida);
                }  
            }
            bf.close();
        } catch (IOException ex) {
            System.out.println("El archivo no se creo correctamente");
        } finally {
            if (fw != null) {
                try {
                    fw.close();
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }
    }
    public static ArrayList leerArchivoUsuarios() {
        File f = new File("./sistemaCC.txt");
        ArrayList<g1_usuario> dbUsuarios = new ArrayList<>();
        Scanner scanner;
        try {
            scanner = new Scanner(f);
            while (scanner.hasNextLine()) {
                String linea = scanner.nextLine();
                Scanner delimitar = new Scanner(linea);
                delimitar.useDelimiter("\\s*,\\s*");
                
                int tipoUsuario = Integer.parseInt(delimitar.next());
                if(tipoUsuario == 1){
                    g1_cliente cliente = new g1_cliente();
                    cliente.setTipo(tipoUsuario);
                    cliente.setId(Integer.parseInt(delimitar.next()));
                    cliente.setClave(delimitar.next());
                    cliente.setCedula(delimitar.next());
                    cliente.setNombre(delimitar.next());
                    cliente.setSaldo(Double.parseDouble(delimitar.next())); 
                    dbUsuarios.add(cliente);
                }else if(tipoUsuario == 2){
                    g1_cajero cajero = new g1_cajero();
                    cajero.setTipo(tipoUsuario);
                    cajero.setId(Integer.parseInt(delimitar.next()));
                    cajero.setClave(delimitar.next());
                    cajero.setCedula(delimitar.next());
                    cajero.setNombre(delimitar.next());
                    cajero.setCodEmpleado(delimitar.next()); 
                    dbUsuarios.add(cajero);
                }
            }
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
        return dbUsuarios;
    }
    
    /**
     * A partir de aca el manejo de transacciones.
     */
    
    public static void crearArchivoMovimientos(ArrayList<g1_movimiento> dbMovimientos) {
        FileWriter fw = null;
        try {
            fw = new FileWriter("./dbMovimientos.txt");
            BufferedWriter bf = new BufferedWriter(fw);
            String salida="";
            for (g1_movimiento movimiento : dbMovimientos) {
                if(movimiento instanceof g1_realizacion_movimiento){
                    g1_realizacion_movimiento movimientoR = (g1_realizacion_movimiento)movimiento;
                    salida = movimientoR.getEstado()+","
                            + movimientoR.getId() +","
                            + movimientoR.getIdCliente() +","
                            + movimientoR.getTipo() +","
                            + movimientoR.getMonto() +","
                            + movimientoR.getIdCajero() +","
                            + movimientoR.getFechaRealizado() +"\n";
                }else{
                    salida = movimiento.getEstado()+","
                            + movimiento.getId() +","
                            + movimiento.getIdCliente() +","
                            + movimiento.getTipo() +","
                            + movimiento.getMonto() +"\n";
                }  
                bf.write(salida);
            }
            bf.close();
        } catch (IOException ex) {
            System.out.println("El archivo no se creo correctamente");
        } finally {
            if (fw != null) {
                try {
                    fw.close();
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }
    }
    
    public static ArrayList leerArchivoMovimientos() {
        File f = new File("./dbMovimientos.txt");
        ArrayList<g1_movimiento> dbMovimientos = new ArrayList<>();
        Scanner scanner;
        try {
            scanner = new Scanner(f);
            while (scanner.hasNextLine()) {
                String linea = scanner.nextLine();
                Scanner delimitar = new Scanner(linea);
                delimitar.useDelimiter("\\s*,\\s*");
                
                int estadoMovimiento = Integer.parseInt(delimitar.next());
                if(estadoMovimiento == 1){
                    g1_realizacion_movimiento movimiento = new g1_realizacion_movimiento();
                    movimiento.setEstado(estadoMovimiento);
                    movimiento.setId(Integer.parseInt(delimitar.next()));
                    movimiento.setIdCliente(delimitar.next());
                    movimiento.setTipo(Integer.parseInt(delimitar.next()));
                    movimiento.setMonto(Double.parseDouble(delimitar.next()));
                    movimiento.setIdCajero(delimitar.next());
                    movimiento.setFechaRealizado(delimitar.next()); 
                    dbMovimientos.add(movimiento);
                }else{
                    g1_movimiento mov = new g1_movimiento();
                    mov.setEstado(estadoMovimiento);
                    mov.setId(Integer.parseInt(delimitar.next()));
                    mov.setIdCliente(delimitar.next());
                    mov.setTipo(Integer.parseInt(delimitar.next()));
                    mov.setMonto(Double.parseDouble(delimitar.next()));
                    dbMovimientos.add(mov);
                }

            }
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
        return dbMovimientos;
    }
}
