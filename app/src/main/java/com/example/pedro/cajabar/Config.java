package com.example.pedro.cajabar;

import java.io.Serializable;

/**
 * Created by pedro on 21/11/2017.
 */

public class Config implements Serializable{

    static String moneda;
    static String impuesto;
    static boolean alcoholic;

    public Config(){
        moneda = "euro";
        impuesto = "igic";
        alcoholic = true;
    }

    public String getMoneda(){
        return moneda;
    }

    public void setMoneda(String moneda){
        this.moneda = moneda;
    }

    public String getImpuesto() {
        return impuesto;
    }

    public void setImpuesto(String impuesto) {
        this.impuesto = impuesto;
    }

    public boolean isAlcoholic() {
        return alcoholic;
    }

    public void setAlcoholic(boolean alcoholic) {
        this.alcoholic = alcoholic;
    }
}
