/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clases;

/**
 *
 * @author josrm
 */
public class g1_realizacion_movimiento extends g1_movimiento {
    private String idTramitador;
    private String fechaRealizado;

    public g1_realizacion_movimiento() {
    }

    public g1_realizacion_movimiento(String idTramitador, String fechaRealizado, int id, String idCliente, int tipo, double monto, int estado) {
        super(id, idCliente, tipo, monto, estado);
        this.idTramitador = idTramitador ;
        this.fechaRealizado = fechaRealizado;
    }
    
    
    public String getIdTramitador() {
        return idTramitador;
    }

    public void setIdTramitador(String idTramitador) {
        this.idTramitador = idTramitador;
    }

    public String getFechaRealizado() {
        return fechaRealizado;
    }

    public void setFechaRealizado(String fechaRealizado) {
        this.fechaRealizado = fechaRealizado;
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
                + "Estado: "+estado+"\n"
                + "Id Tramitador:" + idTramitador +"\n"
                + "Fecha Aplicacion: "+fechaRealizado;
        return salida;         
    } 
}
