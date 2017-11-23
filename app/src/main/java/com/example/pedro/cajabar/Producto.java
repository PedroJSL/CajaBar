package com.example.pedro.cajabar;

/**
 * Created by pedro on 22/11/2017.
 */

public class Producto {

    private int imagen;
    private String nombre;
    private double precio;
    private boolean tieneAlcohol;

    public Producto(int imagen,String nombre, double precio, boolean tieneAlcohol){
        this.imagen = imagen;
        this.nombre = nombre;
        this.precio = precio;
        this.tieneAlcohol = tieneAlcohol;
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public boolean hasAlcohol() {
        return tieneAlcohol;
    }

    public void setTieneAlcohol(boolean tieneAlcohol) {
        this.tieneAlcohol = tieneAlcohol;
    }
}
