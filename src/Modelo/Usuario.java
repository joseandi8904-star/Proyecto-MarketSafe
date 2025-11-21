/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author ADMIN
 */
public class usuario {
    public String correo;
    public String contraseña;
    public String nombres;
    public String apellido;
    public String idu;

    public usuario(String correo, String contraseña, String nombres, String apellido, String idu) {
        this.idu = idu;
        this.correo = correo;
        this.contraseña = contraseña;
        this.nombres = nombres;
        this.apellido = apellido;
    }

    
    
    
}
