package com.example.pedro.cajabar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Config config;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        config = new Config();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 1:
            if(resultCode == RESULT_OK){
                Toast.makeText(getApplicationContext(),"Vuelvo de configuracion",Toast.LENGTH_SHORT).show();
                config = (Config)data.getSerializableExtra("config");
            }
            break;
            case 2:
                Toast.makeText(getApplicationContext(),"Vuelvo de ver el Menú",Toast.LENGTH_SHORT).show();
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
                startActivityForResult(intent,1);
                return true;
            default:
                return true;
        }
    }


    public void botonAñadir(View v){
        Intent intent = new Intent(this,StockActivity.class);
        intent.putExtra("config",config);
        startActivityForResult(intent,2);
    }
}
