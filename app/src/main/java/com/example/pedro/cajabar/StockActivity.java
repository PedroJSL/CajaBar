package com.example.pedro.cajabar;

import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;


public class StockActivity extends AppCompatActivity {
    private ArrayList<Producto> listaStock;
    private TableLayout tabla;
    private Spinner cantidad;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock);

        cantidad = findViewById(R.id.cantidadProducto);
        listaStock = new ListaStock(this).getListaStock();
        tabla = findViewById(R.id.tablaStock);
        presentarProductos();
    }

    private void presentarProductos() {
        Iterator<Producto> it  = listaStock.iterator();
        TableRow filaImg = null, filaTxt = null;
        Producto p;
        ImageView img;
        TextView txt;
        int imagenPorFila = 4;
        Display pantalla = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        pantalla.getSize(size);
        int width = size.x;
        int tamañoImagen = width / imagenPorFila;
        int contador = 0;
        try {
            do {
                if (contador == 0) {
                    filaImg = (TableRow) getLayoutInflater().inflate(R.layout.tablerow_style, null);
                    filaTxt = (TableRow) getLayoutInflater().inflate(R.layout.tablerow_style, null);
                    contador++;
                } else if (contador > 0 && contador < imagenPorFila+1) {
                    p = it.next();
                    img = (ImageView) getLayoutInflater().inflate(R.layout.imageview_style, null);
                    TableRow.LayoutParams params = new TableRow.LayoutParams();
                    params.width = tamañoImagen;
                    params.height = tamañoImagen;
                    img.setLayoutParams(params);
                    txt = (TextView) getLayoutInflater().inflate(R.layout.textview_style, null);
                    img.setImageResource(p.getImagen());
                    txt.setText(String.valueOf(p.getPrecio()));
                    filaImg.addView(img);
                    filaTxt.addView(txt);
                    contador++;
                } else {
                    tabla.addView(filaImg);
                    tabla.addView(filaTxt);
                    contador = 0;
                }
            } while (true);
        }catch (NoSuchElementException e){
                tabla.addView(filaImg);
                tabla.addView(filaTxt);
        }

    }


}
