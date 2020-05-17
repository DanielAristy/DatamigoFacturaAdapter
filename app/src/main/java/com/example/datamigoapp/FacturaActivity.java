package com.example.datamigoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.datamigoapp.entity.Documento;
import com.example.datamigoapp.utilities.ActionBarUtil;

import java.net.URISyntaxException;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FacturaActivity extends AppCompatActivity implements View.OnClickListener {

    private ActionBarUtil actionBarUtil;

    @BindView(R.id.imgFactura)
    public ImageView imgFactura;
    @BindView(R.id.txtNitProveedor)
    public EditText txtNitProveedor;
    @BindView(R.id.valorFactura)
    public EditText valorFactura;
    Button btnFecha;
    @BindView(R.id.txtPickerFecha)
    TextView txtPickerFecha;
    int month, day, year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_factura);
        ButterKnife.bind(this);
        initComponents();
        inicializacion();
        btnFecha.setOnClickListener(this);
    }

    private void inicializacion(){
        btnFecha = findViewById(R.id.btnFecha);
        txtPickerFecha = findViewById(R.id.txtPickerFecha);
    }

    private void initComponents() {
        actionBarUtil = new ActionBarUtil(this);
        actionBarUtil.setToolBar(getString(R.string.registro_factura));
    }

    public void loadImage(View view) {
        tomarFoto();
    }

    public void selectImage(View view) {
        cargarImagen();
    }

    private void tomarFoto(){
        Intent tomarFoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(tomarFoto,200);
    }
    private void cargarImagen() {
        Intent cargarFoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(cargarFoto, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            imgFactura.setImageURI(data.getData());
        }
        if (requestCode == 200 && resultCode == RESULT_OK) {
            imgFactura.setImageBitmap((Bitmap) data.getExtras().get("data"));
        }
    }

    @Override
    public void onClick(View view) {
        if (view == btnFecha) {
            Calendar calendar = Calendar.getInstance();
            day = calendar.get(Calendar.DAY_OF_MONTH);
            month = calendar.get(Calendar.MONTH);
            year = calendar.get(Calendar.YEAR);

            DatePickerDialog datePickerDialog = new DatePickerDialog(FacturaActivity.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    txtPickerFecha.setText(dayOfMonth+"/"+(month + 1)+"/"+year);
                }
            }
            ,day,month,year);
            datePickerDialog.show();
        }
    }

    /**Evento onClick para hacer el registro de Facturas*/
    public void registrarFactura(View view) {
//        Intent intent = new Intent(this,MainActivity.class);
//        startActivity(intent);
        String nitProveedor = txtNitProveedor.getText().toString();
        Double tarifa = toDouble(valorFactura.getText().toString());

        if (validarInformacion(nitProveedor,tarifa)){
            //Crear el objeto Documento para mandar los parametros
            finish();
        }

    }

    private boolean validarInformacion(String nitProveedor, Double tarifa) {
        boolean verificar = true;

        if ("".equals(nitProveedor)){
            txtNitProveedor.setError(getString(R.string.requerido));
            verificar = false;
        }

        if (tarifa == 0){
            valorFactura.setError(getString(R.string.requerido));
            verificar = false;
        }

        return verificar;
    }

    private Double toDouble(String valor) {
        return "".equals(valor)? 0 : Double.parseDouble(valor);
    }

    /**Retroceso para la flecha*/
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
