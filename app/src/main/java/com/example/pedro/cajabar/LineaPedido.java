package com.example.pedro.cajabar;

/**
 * Created by pedro on 25/11/2017.
 */

public class LineaPedido {
    Producto producto;
    int cantidad;
    double precioTotal;

    public LineaPedido(Producto producto, int cantidad, double precioTotal){
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioTotal = precioTotal;
    }

    public Producto getProducto() {
        return producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public double getPrecioTotal() {
        return precioTotal;
    }
}
