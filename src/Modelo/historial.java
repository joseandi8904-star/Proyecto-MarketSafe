/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author ADMIN
 */
public class historial {
    public String id;
    public String fecha;
    public String direccion;
    public String [] articulos;
    public float total;

    public historial(String id, String fecha, String direccion, String[] articulos, float total) {
        this.id=id;
        this.fecha = fecha;
        this.direccion = direccion;
        this.articulos = articulos;
        this.total = total;
    }

    
}
