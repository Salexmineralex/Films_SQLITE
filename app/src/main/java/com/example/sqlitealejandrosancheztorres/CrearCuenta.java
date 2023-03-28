package com.example.sqlitealejandrosancheztorres;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class CrearCuenta extends AppCompatActivity {

    DBHelper db = new DBHelper(this);

    TextInputEditText nombre;

    TextInputEditText contraseña;

    TextInputEditText contraseña2;

    TextView error;

    TextInputLayout nombret;

    TextInputLayout contra1t;

    TextInputLayout contra2t;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_cuenta);

        nombre = findViewById(R.id.nombrei2);

        contraseña = findViewById(R.id.contraseñai2);

        contraseña2 = findViewById(R.id.contraseñai3);

        error = findViewById(R.id.textView6);

        nombret = findViewById(R.id.textField2);

        contra1t = findViewById(R.id.textField);

        contra2t = findViewById(R.id.textfiel3);

        nombret.setErrorEnabled(true);

        contra1t.setErrorEnabled(true);

        contra2t.setErrorEnabled(true);

    }


    public void CrearCuenta(View view)
    {

        if(nombre.getText().toString().isEmpty())
        {

            nombret.setError("Nombre de usuario no puede estar vacio");

        }else
            {

                if(!db.ExisteUsuario(nombre.getText().toString()))
                {

                    System.out.println("A");



                    if (contraseña.getText().toString().equals(contraseña2.getText().toString()))
                    {

                        if (comprobarcontra(contraseña.getText().toString()))
                        {
                            db.insertAccount(nombre.getText().toString(),contraseña.getText().toString());

                            finish();
                        }else
                            {
                                error.setText("La contraseña debe contener:\n 8 Caracteres \n Un Digito \n Una letra mayuscula");

                                error.setVisibility(View.VISIBLE);
                            }




                    }else
                    {

                        contra2t.setError("Contraseña no coincide");

                        contra1t.setError("Contraseña no coincide");

                    }

                }else
                {
                    nombret.setError("Nombre de usuario ya existente");

                }
            }



    }

    public boolean comprobarcontra(String pass)
    {

        Boolean valida = false;

        Boolean upper = false;

        Boolean digit = false;

        if(pass == null || pass.length() < 8)
        {
            valida = false;

        }else
            {
                for (int i = 0; i < pass.length() ; i++)
                {

                    if (Character.isDigit(pass.charAt(i)))
                    {
                        digit = true;
                    }
                    if (Character.isUpperCase(pass.charAt(i)))
                    {
                        upper = true;
                    }

                }
            }

        if(digit == true && upper == true)
        {

            valida = true;

        }


        return valida;

    }
}