package com.example.datamigoapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import com.example.datamigoapp.adapter.FacturaAdapter;
import com.example.datamigoapp.entity.Documento;
import com.example.datamigoapp.utilities.ActionBarUtil;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.listViewFacturas)
    public ListView listViewFacturas;
    @BindView(R.id.txtBuscar)
    public EditText txtBuscar;
    private FacturaAdapter facturaAdapter;
    private ActionBarUtil actionBarUtil;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        loadInfo();
        buscarOnTextListener();
        initComponents();
    }
    /**Titulo del activity*/
    private void initComponents() {
        actionBarUtil = new ActionBarUtil(this);
        actionBarUtil.setToolBar(getString(R.string.facturas_recientes));
    }

    private void buscarOnTextListener() {

        txtBuscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                facturaAdapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void loadInfo() {

        List<Documento> listaFacturas = new ArrayList<>();
        listaFacturas.add( new
                Documento(R.drawable.jupiter,
                "El socio",
                "150000.0",
                "22/02/1994"));
        listaFacturas.add( new
                Documento(R.drawable.marte,
                "La Rambla",
                "200000.0",
                "01/02/1998"));
        listaFacturas.add( new
                Documento(R.drawable.tierra,
                "Colorin",
                "300000.0",
                "10/01/1998"));

        facturaAdapter = new FacturaAdapter(this,listaFacturas);
        listViewFacturas.setAdapter(facturaAdapter);
    }


    public void goToRegistroFactura(View view) {

        Intent crearFactura = new Intent(this,FacturaActivity.class);
        startActivity(crearFactura);
    }

    /**Retroceso*/
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
