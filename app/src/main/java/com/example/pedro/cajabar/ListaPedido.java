package com.example.pedro.cajabar;


import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by pedro on 25/11/2017.
 */

public class ListaPedido implements Serializable{
    ArrayList<LineaPedido> listaPedido;
    public ListaPedido(){
            listaPedido = new ArrayList<>();
    }

    public ArrayList<LineaPedido> getListaPedido() {
        return listaPedido;
    }

    public void setListaPedido(ArrayList<LineaPedido> listaPedido){
        this.listaPedido = listaPedido;
    }
}
