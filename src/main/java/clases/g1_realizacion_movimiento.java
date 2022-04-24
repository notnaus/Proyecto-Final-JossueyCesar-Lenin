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
    private String idtramitador;
    private String fechaRealizado;

    public g1_realizacion_movimiento() {
    }

    public g1_realizacion_movimiento(String idtramitador, String fechaRealizado, int id, String idCliente, int tipo, double monto, int estado) {
        super(id, idCliente, tipo, monto, estado);
        this.idtramitador = idtramitador;
        this.fechaRealizado = fechaRealizado;
    }
    
    
    public String getIdtramitador() {
        return idtramitador;
    }

    public void setIdCajero(String idtramitador) {
        this.idtramitador = idtramitador;
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
            case 1: tipo = "Deposito - Abono a la cuenta";break;
            case 2: tipo = "Transferencia local - Nota de debito";break;
            case 3: tipo = "Trasferencia otros bancos";break;
            case 4: tipo = "Retiro de dinero - Nota de credito";break;
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
                + "Id tramitador:" + idtramitador +"\n"
                + "Fecha Aplicacion: "+fechaRealizado;
        return salida;         
    } 

    void setIdtramitador(String codEmpleado) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

 
}
