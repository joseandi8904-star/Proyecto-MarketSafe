/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;


public class Nodo_LD <T> {
    public T dato;
    public Nodo_LD <T> sig;
    public Nodo_LD <T> ant;

    public Nodo_LD(T dato) {
        this.dato = dato;
        sig = ant = null;
    }
}
