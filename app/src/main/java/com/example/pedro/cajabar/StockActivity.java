package com.example.pedro.cajabar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;


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
        int width = getResources().getConfiguration().screenWidthDp;
        int imagenPorFila = width / 100; //100 = tamaño de la imagen
        int contador = 0;
        for(Producto prod : listaStock){
            Log.d("producto: ",prod.getNombre());
        }
        while(it.hasNext()){
            if(contador==0){
                filaImg = new TableRow(this);
                filaTxt = new TableRow(this);
                contador++;
            }else if(contador>0&&contador<imagenPorFila){
                p = it.next();
                img = (ImageView)getLayoutInflater().inflate(R.layout.imageview_style,null);
                txt = (TextView)getLayoutInflater().inflate(R.layout.textview_style, null);
                img.setImageResource(p.getImagen());
                txt.setText(String.valueOf(p.getPrecio()));
                filaImg.addView(img);
                filaTxt.addView(txt);
                contador++;
            }else{
                tabla.addView(filaImg);
                tabla.addView(filaTxt);
                contador=0;
            }
        }

    }


}
