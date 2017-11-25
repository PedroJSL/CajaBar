package com.example.pedro.cajabar;

import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;


public class StockActivity extends AppCompatActivity {
    private ArrayList<Producto> listaStock;
    private ArrayList<ImageView> imagenesButton;
    private TableLayout tabla;
    private Spinner cantidad;

    private String nombreProducto = "";
    private int cantidadProducto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock);
        imagenesButton = new ArrayList<>();
        cantidad = findViewById(R.id.cantidadProducto);
        listaStock = new ListaStock(this).getListaStock();
        tabla = findViewById(R.id.tablaStock);
        presentarProductos();
    }

    private void presentarProductos() {
        Iterator<Producto> it = listaStock.iterator();
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
                } else if (contador > 0 && contador < imagenPorFila + 1) {
                    p = it.next();
                    img = (ImageView) getLayoutInflater().inflate(R.layout.imageview_style, null);
                    TableRow.LayoutParams params = new TableRow.LayoutParams();
                    params.width = tamañoImagen;
                    params.height = tamañoImagen;
                    img.setLayoutParams(params);
                    txt = (TextView) getLayoutInflater().inflate(R.layout.textview_style, null);
                    img.setImageResource(p.getImagen());
                    img.setId(nameToID(p.getNombre()));
                    txt.setText(String.valueOf(p.getPrecio()));
                    filaImg.addView(img);
                    filaTxt.addView(txt);
                    imagenesButton.add(img);
                    contador++;
                } else {
                    tabla.addView(filaImg);
                    tabla.addView(filaTxt);
                    contador = 0;
                }
            } while (true);
        } catch (NoSuchElementException e) {
            tabla.addView(filaImg);
            tabla.addView(filaTxt);
        }
    }

    //Método que genera una id propia a partir del nombre de producto para distinguir qué imagen ha sido pulsada.
    public int nameToID(String nombreProducto) {
        int id = 0;
        for (int i = 0; i < nombreProducto.length(); i++) {
            id += (int) nombreProducto.charAt(i);
        }
        return id;
    }

    //Método que trata las imagenes, imitando la lógica de un RadioButton
    public void elegirProducto(View v) {
        ImageView img = (ImageView) v;

        for (ImageView imgB : imagenesButton) {
            if (imgB.getId() == img.getId()) {
                img.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                nombreProducto = checkID(img.getId());
            } else {
                imgB.setBackgroundColor(Color.TRANSPARENT);
            }
        }

    }

    //Metodo que indica el producto seleccionado.
    private String checkID(int id) {
        String nombre = "";
        for (Producto p : listaStock) {
            if (id == nameToID(p.getNombre())) {
                nombre = p.getNombre();
            }
        }
        return nombre;
    }

    public void confirmar(View v) {
        if (nombreProducto.equals("")) {
            Toast.makeText(getApplicationContext(), "Seleccione un producto", Toast.LENGTH_SHORT).show();
        } else {
            for (Producto p : listaStock) {
                if (p.getNombre().equals(nombreProducto)) {
                    LineaPedido lineaPedido = new LineaPedido(p, Integer.parseInt(cantidad.getSelectedItem().toString()));
                }
            }

        }
    }

    public void cancelar(View v) {

    }

}
