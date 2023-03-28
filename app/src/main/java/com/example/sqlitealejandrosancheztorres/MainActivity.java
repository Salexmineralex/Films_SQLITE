package com.example.sqlitealejandrosancheztorres;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final int PELICULA_OK = 2000 ;

    private static
    ArrayAdpterPelicula  aa;

    DBHelper dbh;

    FloatingActionButton buttonapp;

    GridLayout grl;

    int idc;

    ArrayList<Pelicula> Peliculas = new ArrayList<Pelicula>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbh = new DBHelper(this);


        buttonapp = findViewById(R.id.fab);

        idc = getIntent().getIntExtra("idc",0);


        Peliculas = dbh.getAllFilms(idc);

        ListView obj;



        //crearcartelera();

        ArrayAdpterPelicula arrayAdapter= new ArrayAdpterPelicula(this, Peliculas,idc,this);

        obj = (ListView)findViewById(R.id.listview1);

        obj.setAdapter(arrayAdapter);



    }

    public static void refrescar()
    {
        aa.notifyDataSetChanged();
    }

    public void crearcartelera()
    {

        grl.setColumnCount(2);

        grl.setUseDefaultMargins(true);


            if(Peliculas.size() == 1)
            {
                grl.setRowCount(1);
            }else
                {
                    grl.setRowCount(Peliculas.size()/2);
                }




            int numColumnas = grl.getColumnCount();

            int numFilas = grl.getRowCount();

        System.out.println(numFilas+" "+numColumnas);


            int r = 0;

            for (int i = 0; i < Peliculas.size(); i++) {

                ImageButton bt = new ImageButton(this);


                bt.setLayoutParams(new ViewGroup.LayoutParams(1070/numColumnas , 1000));

                bt.setId(Integer.parseInt(Peliculas.get(i).getId()));

                bt.setImageBitmap(Peliculas.get(i).getCartel());

                bt.setPadding(20,10,20,10);

                bt.setScaleType(ImageView.ScaleType.CENTER_CROP);

                bt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view)
                    {

                        Intent i = new Intent(getApplicationContext(),VerInfoPelicula.class);

                        i.putExtra("id",bt.getId());

                        startActivity(i);

                    }
                });



                grl.addView(bt);

                System.out.println("AAAAAAAAAAAAAAAAAAAAAa"+bt.getId());

                r++;

            }


    }





  /* @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        super.onOptionsItemSelected(item);
        //En este otro caso inicio display_contact pero sin id: para que aparezca vacío
        switch(item.getItemId()) {
            case R.id.item1:Bundle dataBundle = new Bundle();

                Intent intent = new Intent(getApplicationContext(),AnadirPelicula.class);

                startActivityForResult(intent,PELICULA_OK);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        System.out.println("AAAAR");

            finish();
            startActivity(getIntent());



    }

    public void meterpeliculas(View view)
    {

        Intent intent = new Intent(getApplicationContext(),AnadirPelicula.class);

        intent.putExtra("idc",idc);

        startActivityForResult(intent,PELICULA_OK);
    }


}