package com.example.pedro.cajabar;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioGroup;

import java.util.ArrayList;


public class Configuracion extends AppCompatActivity {
    Config config;
    ArrayList<LineaPedido> listaPedido;
    RadioGroup moneda, impuesto;
    CheckBox alcoholic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);

        config = (Config) getIntent().getSerializableExtra("config");
        if(getIntent().getExtras().containsKey("listaPedido")){
           // listaPedido = getIntent().getLiExtra("listaPedido");
        }
        moneda = findViewById(R.id.rgMoneda);
        impuesto = findViewById(R.id.rgImpuesto);
        alcoholic = findViewById(R.id.bebidasAlcoholicas);

        readConfig();

    }

    private void readConfig() {

        switch (config.getMoneda()) {
            case "euro":
                moneda.check(R.id.euro);
                break;
            case "dolar":
                moneda.check(R.id.dolar);
                break;
            case "bitcoin":
                moneda.check(R.id.bitcoin);
                break;
        }


        switch (config.getImpuesto()){
            case "iva":
                impuesto.check(R.id.iva);
                break;
            case "igic":
                impuesto.check(R.id.igic);
                break;
        }

        if(config.isAlcoholic()){
            alcoholic.setChecked(true);
        }else{
            alcoholic.setChecked(false);
        }
    }

    public void setConfig(){

        switch (moneda.getCheckedRadioButtonId()) {
            case R.id.euro:
                config.setMoneda("euro");
                break;
            case R.id.dolar:
                config.setMoneda("dolar");
                break;
            case R.id.bitcoin:
                config.setMoneda("bitcoin");
                break;
        }


        switch (impuesto.getCheckedRadioButtonId()){
            case R.id.iva:
                config.setImpuesto("iva");
                break;
            case R.id.igic:
                config.setImpuesto("igic");
                break;
        }

        if(alcoholic.isChecked()){
            config.setAlcoholic(true);
        }else{
            config.setAlcoholic(false);
        }
    }

    public void botonGuardar(View v){
        setConfig();
        Intent intent = new Intent();
        intent.putExtra("config",config);
        setResult(RESULT_OK,intent);
        finish();
    }


}
