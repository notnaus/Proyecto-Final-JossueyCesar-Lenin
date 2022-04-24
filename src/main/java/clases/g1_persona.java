/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clases;

/**
 *
 * @author josrm
 */
public class g1_persona extends g1_usuario{
    private String cedula;
    private String nombre;

    public g1_persona(String cedula, String nombre, int id, String clave, int tipo) {
        super(id, clave, tipo);
        this.cedula = cedula;
        this.nombre = nombre;
    }

    public g1_persona(String cedula, String nombre) {
        this.cedula = cedula;
        this.nombre = nombre;
    }

    public g1_persona() {
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public void menu(){
        System.out.println("Menu de persona");
    }
}
