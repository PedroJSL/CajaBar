package com.example.pedro.cajabar;

/**
 * Created by pedro on 22/11/2017.
 */

public class Producto {

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

    public void precioSegunMoneda(String moneda) {
        switch (moneda) {
            case "dolar":

                    //Euro a Dolar
                    precio = redondearDecimal(precio * 1.17);
                break;
            case "bitcoin":
                    //Euro a Bitcoin
                    precio = redondearDecimal(precio/0.000136370);
                break;
        }
        this.moneda = moneda;
    }

   /* public void cambioMoneda(String moneda) {
        switch (moneda) {
            case "euro":
                if (this.moneda.equals("dolar")) {
                    //Dolar a Euro
                    precio = redondearDecimal(precio / 1.17);
                } else if (this.moneda.equals("bitcoin")) {
                    //Bitcoin a Euro
                    precio = redondearDecimal(precio / 7275.93);
                }
                break;
            case "dolar":
                if (this.moneda.equals("euro")) {
                    //Euro a Dolar
                    precio = redondearDecimal(precio * 1.17);
                } else if (this.moneda.equals("bitcoin")) {
                    //Bitcoind a Dolar
                    precio = redondearDecimal(precio / 8766.2);
                }
                break;
            case "bitcoin":
                if(this.moneda.equals("euro")){
                    //Euro a Bitcoin
                    precio = redondearDecimal(precio*7275.93);
                }else if(this.moneda.equals("dolar")){
                    //Dolar a Bitcoin
                    precio = redondearDecimal(precio * 8766.2);
                }
                break;
        }
        this.moneda = moneda;
    }*/

    private double redondearDecimal(double precio) {
        return ((double) Math.round(precio * 100d) / 100d);
    }

    public String checkPrecio(){
        if(precio - (int)precio ==0){
            return String.valueOf((int) precio);
        }else {
            return String.valueOf(precio);
        }
    }

    public String checkPrecio(double precio){
        if(precio - (int)precio ==0){
            return String.valueOf((int) precio);
        }else {
            return String.valueOf(precio);
        }
    }
}
