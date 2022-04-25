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
public class g1_movimiento {
     private int id;
    private String idCliente;
    private int tipo;
    private double monto;
    private int estado;

    public g1_movimiento(int id, String idCliente, int tipo, double monto, int estado) {
        this.id = id;
        this.idCliente = idCliente;
        this.tipo = tipo;
        this.monto = monto;
        this.estado = estado;
    }

    public g1_movimiento() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        String tipo="";
        String estado="";
        switch(getTipo()){
            case 1: tipo = "Nota de Credito";break;
            case 2: tipo = "Abono a la cuenta";break;
            case 3: tipo = "Nota de debito";
        }
        switch(getEstado()){
            case 0: estado = "Pendiente";break;
            case 1: estado = "Aplicado";break;
        }
        String salida = "Detalle de movimiento: \n\n"
                + "Id Movimiento: "+getId()+"\n"
                + "Monto: "+getMonto()+"\n"
                + "Tipo: "+tipo+"\n"
                + "Estado: "+estado+"\n";
        return salida;         
    } 
    public static ArrayList listaMovimientosPendientes(ArrayList dbMovimientos){
        ArrayList<g1_movimiento> listaMovimientosPendientes = new ArrayList();
        
        for (int i = 0; i<dbMovimientos.size();i++){
            g1_movimiento temporal = (g1_movimiento)dbMovimientos.get(i);
            if(temporal.getEstado()==0){
                listaMovimientosPendientes.add(temporal);
            }
        }
        return listaMovimientosPendientes;
    }
    
    public static ArrayList listaMovimientosPendientesTramitador(ArrayList dbMovimientos){
        ArrayList<g1_movimiento> listaMovimientosPendientes = new ArrayList();
        
        for (int i = 0; i<dbMovimientos.size();i++){
            g1_movimiento temporal = (g1_movimiento)dbMovimientos.get(i);
            if(temporal.getEstado()==0 && temporal.getTipo() != 3){
                listaMovimientosPendientes.add(temporal);
            }
        }
        return listaMovimientosPendientes;
    }
    
    
    
    
    
    
    
    
    
    public static g1_movimiento getMovimiento(ArrayList dbMovimientos, int id){
        for (int i = 0; i<dbMovimientos.size();i++){
            g1_movimiento temporal = (g1_movimiento)dbMovimientos.get(i);
            if(temporal.getId()==id){
                return temporal;
            }
        }
        return null;
    }  
    public static String mostarListaMovimientos(ArrayList Movimientos){
        String salida = String.format("%10s%10s%25s%12s%10s\n", 
                "Indice","Id","Tipo","Monto","Estado");
        String estado="";
        String tipo="";        
        for (int i = 0; i<Movimientos.size();i++){
            g1_movimiento temporal = (g1_movimiento)Movimientos.get(i);
            switch(temporal.getTipo()){
                case 1: tipo = "Nota de Credito";break;
                case 2: tipo = "Abono a la cuenta";break;
                case 3: tipo = "Nota de debito";
            }
            switch(temporal.getEstado()){
                case 0: estado = "Pendiente";break;
                case 1: estado = "Aplicado";break;
            }
            salida += String.format("%10s%10s%25s%12s%10s\n",(i+1), 
                temporal.getId(),tipo,temporal.getMonto(),estado);
        }
        return salida;
    }
    public static void actualizarMovimiento(ArrayList dbMovimientos, g1_realizacion_movimiento movimiento){
        for(int i = 0; i<dbMovimientos.size();i++){
            g1_movimiento temporal =(g1_movimiento) dbMovimientos.get(i);
            if(temporal.getId()==movimiento.getId()){
                dbMovimientos.set(i, movimiento);
            }
        }
    }
    
    public static String mostarListaMovimientosCliente(ArrayList Movimientos,String cedula){
        String salida = String.format("%10s%10s%25s%12s%10s\n", 
                "Indice","Id","Tipo","Monto","Estado");
        String estado="";
        String tipo="";
        int indice = 0;
        for (int i = 0; i<Movimientos.size();i++){
            g1_movimiento temporal = (g1_movimiento)Movimientos.get(i);
            if(temporal.idCliente.equals(cedula)){
                indice++;
                switch(temporal.getTipo()){
                case 1: tipo = "Nota de Credito";break;
                case 2: tipo = "Abono a la cuenta";break;
                case 3: tipo = "Nota de debito";break;
                
                }
                switch(temporal.getEstado()){
                    case 0: estado = "Pendiente";break;
                    case 1: estado = "Aplicado";break;
                }
                salida += String.format("%10s%10s%25s%12s%10s\n",indice, 
                    temporal.getId(),tipo,temporal.getMonto(),estado);
            }
        }
        return salida;
    }
}
