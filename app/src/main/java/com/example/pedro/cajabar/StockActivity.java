package com.example.pedro.cajabar;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;


public class StockActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private ArrayList<Producto> listaStock;
    private ArrayList<LineaPedido> listaPedido;
    private ArrayList<ImageView> imagenesButton;
    private TextView precio;
    private double precioTotal;
    private TableLayout tabla;
    private Spinner cantidad;
    private Config config;
    private String simboloMoneda;
    private Producto productoElegido;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock);
        imagenesButton = new ArrayList<>();
        config = (Config) getIntent().getSerializableExtra("config");
        if (config.getMoneda().equals("euro")) {
            simboloMoneda = " €";
        } else if (config.getMoneda().equals("dolar")) {
            simboloMoneda = " $";

        } else if (config.getMoneda().equals("bitcoin")) {
            simboloMoneda = " B";
        }
        if(getIntent().getExtras().containsKey("listaPedido")){
            listaPedido = ((ListaPedido)getIntent().getSerializableExtra("listaPedido")).getListaPedido();
        }else {
            listaPedido = new ListaPedido().getListaPedido();
        }

        cantidad = findViewById(R.id.cantidadProducto);
        cantidad.setOnItemSelectedListener(this);

        precio = findViewById(R.id.precio);

        listaStock = new ListaStock(this, config).getListaStock();

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
                    if (!config.isAlcoholic()) {
                        if (p.hasAlcohol()) {
                            img.setImageResource(R.mipmap.img_prohibido);
                            txt.setText("");
                            img.setId(-1);
                        } else {
                            img.setImageResource(p.getImagen());
                            img.setId(nameToID(p.getNombre()));
                            txt.setText(p.checkPrecio() + simboloMoneda);
                            if (config.getMoneda().equals("bitcoin")) {
                                txt.setTextSize(15);
                            }

                            imagenesButton.add(img);
                        }
                    } else {
                        img.setImageResource(p.getImagen());
                        img.setId(nameToID(p.getNombre()));
                        txt.setText(p.checkPrecio() + simboloMoneda);
                        if (config.getMoneda().equals("bitcoin")) {
                            txt.setTextSize(15);
                        }
                        imagenesButton.add(img);
                    }
                    filaImg.addView(img);
                    filaTxt.addView(txt);

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
        if (v.getId() == -1) {
            productoElegido = null;
        }

        for (ImageView imgB : imagenesButton) {
            if (imgB.getId() == img.getId()) {
                img.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                productoElegido = checkID(img.getId());
                setPrecio();
            } else {
                imgB.setBackgroundColor(Color.TRANSPARENT);
            }
        }
    }

    //Establecer precio de la selección en el TextView
    public void setPrecio() {
        if (productoElegido != null) {
            precioTotal = productoElegido.getPrecio() * Integer.parseInt(cantidad.getSelectedItem().toString());
            precio.setText(productoElegido.checkPrecio(precioTotal) + simboloMoneda);
        } else {
            precio.setText(R.string.na);
        }
    }

    //Metodo que indica el producto seleccionado.
    private Producto checkID(int id) {

        for (Producto p : listaStock) {
            if (id == nameToID(p.getNombre())) {
                return p;
            }
        }
        return null;
    }

    public void confirmar(View v) {
        if (productoElegido == null) {
            Toast.makeText(getApplicationContext(), "Seleccione un producto", Toast.LENGTH_SHORT).show();
        } else {
            LineaPedido lineaPedido = new LineaPedido(productoElegido, Integer.parseInt(cantidad.getSelectedItem().toString()), precioTotal);
            listaPedido.add(lineaPedido);
        }

        Intent intent = new Intent();
        intent.putExtra("config", config);
        intent.putExtra("listaPedido", listaPedido);
        setResult(RESULT_OK);
        finish();
    }


    public void cancelar(View v) {
        finish();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        setPrecio();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}
