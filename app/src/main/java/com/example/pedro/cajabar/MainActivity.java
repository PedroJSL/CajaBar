package com.example.pedro.cajabar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private Config config;
    private TextView tvImp,tvCantidadImpuesto,tvImporteImpuesto,tvImporteTotal,tvCantidadTotal;
    private ListaPedido objListaPedido;
    private ArrayList<LineaPedido> listaPedido;
    private TableLayout tablaPedido;
    private String simboloMoneda;
    private double impuesto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        config = new Config();
        tablaPedido = findViewById(R.id.tablaPedido);
        tvImp = findViewById(R.id.tvImpuesto);
        tvCantidadImpuesto = findViewById(R.id.tv_cantImpuesto);
        tvImporteImpuesto = findViewById(R.id.resCalculoImpuesto);
        tvCantidadTotal = findViewById(R.id.tv_cantTotal);
        tvImporteTotal = findViewById(R.id.resCalculoTotal);

    }

    @Override
    protected void onStart() {
        super.onStart();

        if (config.getMoneda().equals("euro")) {
            simboloMoneda = " €";
        } else if (config.getMoneda().equals("dolar")) {
            simboloMoneda = " $";

        } else if (config.getMoneda().equals("bitcoin")) {
            simboloMoneda = " B";
        }
        comprobarAlcohol();
        presentarListaPedido();
        setImpuesto();

    }

    //Método para establecer el impuesto.
    public void setImpuesto(){
        switch (config.getImpuesto()){
            case "iva":
                tvImp.setText(R.string.tv_iva);
                tvCantidadImpuesto.setText(R.string.valorIVA);
                impuesto = 0.21;
                break;
            case "igic":
                tvImp.setText(R.string.tv_igic);
                tvCantidadImpuesto.setText(R.string.valorIGIC);
                impuesto = 0.06;
                break;
        }
    }

    //Metodo que calcula el importe total
    public void calculoTotal(double sumaPrecios,int cantProductos){
        //impuesto
        double impuestoPrecio = redondearDecimal(sumaPrecios*impuesto,config.getMoneda());
        tvImporteImpuesto.setText(String.valueOf(impuestoPrecio)+simboloMoneda);
        tvCantidadTotal.setText(String.valueOf(cantProductos));
        double importeTotal = redondearDecimal(impuestoPrecio+sumaPrecios,config.getMoneda());
        tvImporteTotal.setText(String.valueOf((importeTotal))+simboloMoneda);
    }
    public double redondearDecimal(double precio,String moneda) {
        if (moneda.equals("bitcoin")) {
            return ((double) Math.round(precio * 100000d) / 100000d);
        } else {
            return ((double) Math.round(precio * 100d) / 100d);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    config = (Config) data.getSerializableExtra("config");
                }
                break;
            case 2:
                if (resultCode == RESULT_OK) {
                    config = (Config) data.getSerializableExtra("config");
                    objListaPedido = ((ListaPedido) data.getSerializableExtra("listaPedido"));
                    listaPedido = objListaPedido.getListaPedido();
                }
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings_menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.configuracion:
                Intent intent = new Intent(this, Configuracion.class);
                intent.putExtra("config", config);
                if (listaPedido != null) {
                    intent.putExtra("listaPedido", listaPedido);
                }
                startActivityForResult(intent, 1);
                return true;
            default:
                return true;
        }
    }

    //Metodo para elimitar de la lista bebidas alcoholicas cuando no deberian.
    public void comprobarAlcohol(){
        if (listaPedido!=null&&!config.isAlcoholic()){
            for (LineaPedido line:listaPedido) {
                if(line.getProducto().hasAlcohol()) {
                    listaPedido.remove(line);
                }
            }
        }
    }


    public void presentarListaPedido() {
        if (listaPedido != null) {
            int cantProductos = 0;
            double sumaPrecios = 0;
            for (LineaPedido linea : listaPedido) {
                linea.getProducto().cambioMoneda(config.getMoneda());
                cantProductos += linea.getCantidad();
                sumaPrecios +=calcularPrecioItem(linea);
                TableRow fila = (TableRow) getLayoutInflater().inflate(R.layout.tablerow_style, null);
                TableRow.LayoutParams params = new TableRow.LayoutParams();

                ImageView foto = (ImageView) getLayoutInflater().inflate(R.layout.imageview_style, null);
                params.weight = 1;
                foto.setLayoutParams(params);

                TextView nombreProducto = (TextView) getLayoutInflater().inflate(R.layout.textview_pedido_style, null);
                params.weight = 3;
                nombreProducto.setLayoutParams(params);

                TextView precioProducto = (TextView) getLayoutInflater().inflate(R.layout.textview_pedido_style, null);
                params.weight = 1;
                precioProducto.setLayoutParams(params);

                TextView cantidadProducto = (TextView) getLayoutInflater().inflate(R.layout.textview_pedido_style, null);
                cantidadProducto.setLayoutParams(params);

                TextView precioTotalProducto = (TextView) getLayoutInflater().inflate(R.layout.textview_pedido_style, null);
                precioTotalProducto.setTextColor(getResources().getColor(R.color.secondaryTextColor));
                precioTotalProducto.setLayoutParams(params);

                foto.setImageResource(linea.getProducto().getImagen());
                nombreProducto.setText(linea.getProducto().getNombre());
                precioProducto.setText(linea.getProducto().getPrecio() + simboloMoneda);
                cantidadProducto.setText("x" + String.valueOf(linea.getCantidad()));
                precioTotalProducto.setText(String.valueOf(calcularPrecioItem(linea)+ simboloMoneda));

                fila.addView(foto);
                fila.addView(nombreProducto);
                fila.addView(precioProducto);
                fila.addView(cantidadProducto);
                fila.addView(precioTotalProducto);

                tablaPedido.addView(fila);
            }
            calculoTotal(sumaPrecios,cantProductos);
        }
    }

    public double calcularPrecioItem(LineaPedido linea){
        return linea.getProducto().redondearDecimal(linea.getProducto().getPrecio()*linea.getCantidad(),config.getMoneda());
    }

    public void botonAñadir(View v) {
        Intent intent = new Intent(this, StockActivity.class);
        intent.putExtra("config", config);
        if (objListaPedido != null) {
            intent.putExtra("listaPedido", objListaPedido);
        }
        startActivityForResult(intent, 2);
    }

    @Override
    protected void onPause() {
        super.onPause();
        eliminarTabla();
    }

    //Limpiar tabla
    private void eliminarTabla() {
        tablaPedido.removeAllViews();
    }

    //Metodo para cambiar la moneda del precio total.
    public double cambioMoneda(Producto p, double precio) {
        switch (config.getMoneda()) {
            case "euro":
                if (p.getMoneda().equals("dolar")) {
                    //Dolar a Euro
                    precio = redondearDecimal(precio / 1.17,config.getMoneda());
                } else if (p.getMoneda().equals("bitcoin")) {
                    //Bitcoin a Euro
                    precio = redondearDecimal(precio * 7275.93,config.getMoneda());
                }
                break;
            case "dolar":
                if (p.getMoneda().equals("euro")) {
                    //Euro a Dolar

                    precio = redondearDecimal(precio * 1.17,config.getMoneda());
                } else if (p.getMoneda().equals("bitcoin")) {
                    //Bitcoin a Dolar
                    precio = redondearDecimal(precio * 8766.2,config.getMoneda());
                }
                break;
            case "bitcoin":
                if (p.getMoneda().equals("euro")) {
                    //Euro a Bitcoin
                    precio = redondearDecimal(precio / 7275.93,config.getMoneda());
                } else if (p.getMoneda().equals("dolar")) {
                    //Dolar a Bitcoin
                    precio = redondearDecimal(precio / 8766.2,config.getMoneda());
                }
                break;
        }
        p.setMoneda(config.getMoneda());
        return precio;
    }



    public void botonCancelar(View v){
        eliminarTabla();
        objListaPedido = null;
        listaPedido = null;
        tvImporteImpuesto.setText(R.string.na);
        tvCantidadTotal.setText(R.string.na);
        tvImporteTotal.setText(R.string.na);
    }

    public void botonEnviar(View v){
        eliminarTabla();
        objListaPedido = null;
        listaPedido = null;
        tvImporteImpuesto.setText(R.string.na);
        tvCantidadTotal.setText(R.string.na);
        tvImporteTotal.setText(R.string.na);
        Toast.makeText(getApplicationContext(),"Pedido enviado.",Toast.LENGTH_SHORT).show();
    }
}
