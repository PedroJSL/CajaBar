package com.example.pedro.cajabar;

import android.app.Activity;

import java.util.ArrayList;

public class ListaStock {


    private ArrayList<Producto> listaStock;
    Activity v;

    private String[] nombres;
    private int[] imagenes;
    private double[] precios;
    private boolean[] tienenAlcohol;

    public ListaStock(Activity v) {
        this.v = v;
        listaStock = new ArrayList<>();
        crearListaStock();

    }
    public ArrayList<Producto> getListaStock() {
        return listaStock;
    }

    public void crearListaStock() {

        nombres = new String[]{v.getResources().getString(R.string.appletizer),
                v.getResources().getString(R.string.arrugadas),
                v.getResources().getString(R.string.calamares),
                v.getResources().getString(R.string.tropical),
                v.getResources().getString(R.string.clipper),
                v.getResources().getString(R.string.cola),
                v.getResources().getString(R.string.ensalada),
                v.getResources().getString(R.string.flan),
                v.getResources().getString(R.string.helado),
                v.getResources().getString(R.string.hamburguesa),
                v.getResources().getString(R.string.nuggets),
                v.getResources().getString(R.string.nestea),
                v.getResources().getString(R.string.papas),
                v.getResources().getString(R.string.perrito),
                v.getResources().getString(R.string.pizza),
                v.getResources().getString(R.string.redbull),
                v.getResources().getString(R.string.sandwich),
                v.getResources().getString(R.string.tarta),
                v.getResources().getString(R.string.tiramisu),
                v.getResources().getString(R.string.blanco),
                v.getResources().getString(R.string.tinto)};

        imagenes = new int[]{R.mipmap.img_appletizer, R.mipmap.img_arrugadas, R.mipmap.img_calamares,
                R.mipmap.img_tropical, R.mipmap.img_clipper, R.mipmap.img_cocacola, R.mipmap.img_ensalada,
                R.mipmap.img_flan, R.mipmap.img_helado, R.mipmap.img_hamburguesa, R.mipmap.img_nugget,
                R.mipmap.img_nestea, R.mipmap.img_papas, R.mipmap.img_perritos, R.mipmap.img_pizza,
                R.mipmap.img_redbull, R.mipmap.img_sandwich, R.mipmap.img_tarta, R.mipmap.img_tiramisu,
                R.mipmap.img_blanco, R.mipmap.img_tinto};

        precios = new double[]{1.5, 4, 5.95, 1.20, 1.5, 1.5, 3, 2.5, 2.10, 5.5, 6.5, 1.5, 3.25,
                2.25, 7.75, 2, 3.25, 2.10, 2.5, 8.75, 9.25};

        tienenAlcohol = new boolean[]{false, false, false, true, false, false, false, false,
                false, false, false, false, false, false, false, false, false, false, false,
                true, true};


        for (int i = 0; i < nombres.length; i++) {
            Producto product = new Producto(imagenes[i], nombres[i], precios[i], tienenAlcohol[i]);
            listaStock.add(product);
        }

    }
}
