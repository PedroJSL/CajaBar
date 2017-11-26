package com.example.pedro.cajabar;

import java.io.Serializable;

/**
 * Created by pedro on 25/11/2017.
 */

public class LineaPedido implements Serializable{
    Producto producto;
    int cantidad;


    public LineaPedido(Producto producto, int cantidad){
        this.producto = producto;
        this.cantidad = cantidad;

    }

    public Producto getProducto() {
        return producto;
    }

    public int getCantidad() {
        return cantidad;
    }

}
