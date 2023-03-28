package com.example.sqlitealejandrosancheztorres;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;

public class AnadirPelicula extends AppCompatActivity {

    ImageView poster;
    private static final int PICK_IMAGE = 100;
    Uri imageUri;

    TextInputEditText nombre;

    TextInputEditText director;

    TextInputEditText genero;

    TextInputEditText sinopsis;

    TextInputEditText duracion;

    int idc;

    private DBHelper mydb ;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadir_pelicula);

        poster = (ImageView)findViewById(R.id.imageView);

        nombre = (TextInputEditText) findViewById(R.id.textedittextNombre);

        director = (TextInputEditText) findViewById(R.id.textedittextDirector);

        genero = (TextInputEditText) findViewById(R.id.textedittextGenero);

        sinopsis = (TextInputEditText) findViewById(R.id.textedittextSynopsis);

        duracion = (TextInputEditText) findViewById(R.id.textedittextDuracion);

        idc = getIntent().getIntExtra("idc",0);

        mydb = new DBHelper(this) ;

    }


    public void seleccimage(View view)
    {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            imageUri = data.getData();
            poster.setImageURI(imageUri);
        }
    }

    public void meterpelicula(View view)
    {
        try {


            Bitmap posterbit = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);



            Pelicula p = new Pelicula(nombre.getText().toString(),director.getText().toString(), genero.getText().toString(),  Integer.parseInt(duracion.getText().toString()) , sinopsis.getText().toString(), posterbit);


            System.out.println(p.toString());

            if (mydb.insertFilm(p,idc))
            {

                finish();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}