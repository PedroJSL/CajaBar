package com.example.pedro.cajabar;


import android.widget.Toast;

import java.io.Serializable;


/**
 * Created by pedro on 22/11/2017.
 */

public class Producto implements Serializable {

    private int imagen;
    private String nombre;
    private double precio;
    private String moneda;
    private boolean tieneAlcohol;

    public Producto(int imagen, String nombre, double precio, String moneda, boolean tieneAlcohol) {
        this.imagen = imagen;
        this.nombre = nombre;
        this.precio = precio;
        this.moneda = moneda;
        this.tieneAlcohol = tieneAlcohol;

        precioSegunMoneda(moneda);
    }

    public int getImagen() {
        return imagen;
    }


    public String getNombre() {
        return nombre;
    }


    public double getPrecio() {
        return precio;
    }


    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public boolean hasAlcohol() {
        return tieneAlcohol;
    }


    public void precioSegunMoneda(String moneda) {
        switch (moneda) {
            case "dolar":
                //Euro a Dolar
                precio = redondearDecimal(precio * 1.17,moneda);
                break;
            case "bitcoin":
                //Euro a Bitcoin
                precio = redondearDecimal(precio / 7275.93,moneda);
                break;
        }
        this.moneda = moneda;
    }


    public void cambioMoneda(String moneda) {
        switch (moneda) {
            case "euro":
                if (this.moneda.equals("dolar")) {
                    //Dolar a Euro
                    precio = redondearDecimal(precio / 1.17,moneda);
                } else if (this.moneda.equals("bitcoin")) {
                    //Bitcoin a Euro
                    precio = redondearDecimal(precio * 7275.93,moneda);
                }
                break;
            case "dolar":
                if (this.moneda.equals("euro")) {
                    //Euro a Dolar
                    precio = redondearDecimal(precio * 1.17,moneda);
                } else if (this.moneda.equals("bitcoin")) {
                    //Bitcoin a Dolar
                    precio = redondearDecimal(precio * 8766.2,moneda);
                }
                break;
            case "bitcoin":
                if (this.moneda.equals("euro")) {
                    //Euro a Bitcoin
                    precio = redondearDecimal(precio / 7432.35,moneda);
                } else if (this.moneda.equals("dolar")) {
                    //Dolar a Bitcoin
                    precio = redondearDecimal(precio / 8857.25,moneda);
                }
                break;
        }
        this.moneda = moneda;
    }


    public double redondearDecimal(double precio,String moneda) {
        if (moneda.equals("bitcoin")) {
            return ((double) Math.round(precio * 100000d) / 100000d);
        } else {
            return ((double) Math.round(precio * 100d) / 100d);
        }
    }

    public String checkPrecio() {
        if (precio - (int) precio == 0) {
            return String.valueOf((int) precio);
        } else {
            return String.valueOf(precio);
        }
    }

    public String checkPrecio(double precio) {
        if (precio - (int) precio == 0) {
            return String.valueOf((int) precio);
        } else {
            return String.valueOf(precio);
        }
    }
}
