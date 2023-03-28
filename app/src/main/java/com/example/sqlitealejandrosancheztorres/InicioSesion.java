package com.example.sqlitealejandrosancheztorres;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class InicioSesion extends AppCompatActivity {

    DBHelper db = new DBHelper(this);

    TextInputEditText nombre;

    TextInputEditText contraseña;

    TextInputLayout Texti1;

    TextInputLayout Texti2;

    TextView error;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_sesion);

        nombre = findViewById(R.id.nombrei);

        contraseña = findViewById(R.id.contraseñai);

        Texti1 = findViewById(R.id.textField);

        Texti2 = findViewById(R.id.textField2);

        error = findViewById(R.id.error);

        Texti1.setErrorEnabled(true);

        Texti2.setErrorEnabled(true);

    }

    public void CrearCuenta(View view)
    {
        Intent i = new Intent(this,CrearCuenta.class);

        startActivity(i);


    }

    public void IniciarSesion(View view)
    {


        if(db.ExisteCuenta(nombre.getText().toString(),contraseña.getText().toString()))
        {

            Intent i = new Intent(this,MainActivity.class);

            i.putExtra("idc",db.getID(nombre.getText().toString(),contraseña.getText().toString()));

            startActivity(i);


        }else
            {
                Texti1.setError("Error");

                Texti2.setError("Error");

                error.setVisibility(View.VISIBLE);
            }


    }
}