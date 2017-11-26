package com.example.pedro.cajabar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    Config config;
    ArrayList<LineaPedido> listaPedido;
    TableLayout tablaPedido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        config = new Config();
        tablaPedido = findViewById(R.id.tablaPedido);
    }

    @Override
    protected void onStart() {
        super.onStart();
        presentarListaPedido();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 1:
            if(resultCode == RESULT_OK){
                config = (Config)data.getSerializableExtra("config");
            }
            break;
            case 2:
                if(resultCode == RESULT_OK){
                 config = (Config)data.getSerializableExtra("config");
                 listaPedido = ((ListaPedido)data.getSerializableExtra("listaPedido")).getListaPedido();

                }
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings_menu,menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.configuracion:
                Intent intent = new Intent(this,Configuracion.class);
                intent.putExtra("config",config);
                if(listaPedido!=null){
                    intent.putExtra("listaPedido",listaPedido);
                }
                startActivityForResult(intent,1);
                return true;
            default:
                return true;
        }
    }

    public void presentarListaPedido(){
        if(listaPedido!=null){
            TableRow fila = (TableRow)getLayoutInflater().inflate(R.layout.tablerow_style, null);
            ImageView foto = (ImageView)getLayoutInflater().inflate(R.layout.imageview_style,null);
            TextView nombreProducto = (TextView) getLayoutInflater().inflate(R.layout.textview_style,null);

            TextView precioProducto = (TextView) getLayoutInflater().inflate(R.layout.textview_style,null);
            TextView cantidadProducto = (TextView) getLayoutInflater().inflate(R.layout.textview_style,null);
            TextView precioTotalProducto = (TextView) getLayoutInflater().inflate(R.layout.textview_style,null);

            for (LineaPedido linea: listaPedido) {
                foto.setImageResource(linea.getProducto().getImagen());
                nombreProducto.setText(linea.getProducto().getNombre());
                precioProducto.setText(linea.getProducto().checkPrecio());
                cantidadProducto.setText(String.valueOf(linea.getCantidad()));
                precioTotalProducto.setText(String.valueOf(linea.getPrecioTotal()));

                fila.addView(foto);
                fila.addView(nombreProducto);
                fila.addView(precioProducto);
                fila.addView(cantidadProducto);
                fila.addView(precioTotalProducto);

                tablaPedido.addView(fila);
            }
        }
    }


    public void botonAñadir(View v){
        Intent intent = new Intent(this,StockActivity.class);
        intent.putExtra("config",config);
        if(listaPedido!=null){
            intent.putExtra("listaPedido",listaPedido);
        }
        startActivityForResult(intent,2);
    }
}
