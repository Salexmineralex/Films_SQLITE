package com.example.sqlitealejandrosancheztorres;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;

public class EditarPelicula extends AppCompatActivity {

    private static final int PICK_IMAGE = 4000 ;

    ImageView poster;

    Uri imageUri;

    TextInputEditText nombre;

    TextInputEditText director;

    TextInputEditText genero;

    TextInputEditText sinopsis;

    TextInputEditText duracion;

    DBHelper db;

    Pelicula p;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_pelicula);

        db = new DBHelper(this);

        poster = (ImageView)findViewById(R.id.imageView);

        nombre = (TextInputEditText) findViewById(R.id.textedittextNombre);

        director = (TextInputEditText) findViewById(R.id.textedittextDirector);

        genero = (TextInputEditText) findViewById(R.id.textedittextGenero);

        sinopsis = (TextInputEditText) findViewById(R.id.textedittextSynopsis);

        duracion = (TextInputEditText) findViewById(R.id.textedittextDuracion);



        p = db.getData(Integer.parseInt(getIntent().getStringExtra("id"))  ,getIntent().getIntExtra("idc",0));

        poster.setImageBitmap(p.getCartel());

        nombre.setText(p.getNombre());


        director.setText(p.getDirector());

        genero.setText(p.getGenero());

        sinopsis.setText(p.getSinopsis());

        duracion.setText(String.valueOf(p.getDuracion()));

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

    public void editarpelicula(View view) throws IOException {

        p.setNombre(nombre.getText().toString());

        p.setGenero(genero.getText().toString());

        p.setDirector(director.getText().toString());

        p.setDuracion(Integer.parseInt(duracion.getText().toString()));

        p.setSinopsis(sinopsis.getText().toString());

        if(imageUri == null)
        {

            p.setCartel(((BitmapDrawable)poster.getDrawable()).getBitmap());

        }else
            {

                Bitmap posterbit = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);

                p.setCartel(posterbit);
            }


        db.updatefilm(p);



        finish();


    }
}