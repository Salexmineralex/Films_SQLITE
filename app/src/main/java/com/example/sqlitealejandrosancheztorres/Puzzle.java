package com.example.sqlitealejandrosancheztorres;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

public class Puzzle extends AppCompatActivity {

    Pelicula p;

    GridLayout grl;

    int mov = 0;

    int numClicks = 0;

    ArrayList<Bitmap> Original;

    ImageButton imageButton1 = null;

    int idc;

    ArrayList<Bitmap> AB = new ArrayList<Bitmap>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle);

          grl = findViewById(R.id.gridLayout);

        DBHelper dbh = new DBHelper(this);

        int id = getIntent().getIntExtra("p",0);

        idc = getIntent().getIntExtra("idc",0);

        p = (Pelicula) dbh.getData(id,idc);


          crearpuzzle();


    }

        private void crearpuzzle()
        {

            grl.setColumnCount(3);

            grl.setRowCount(3);



            Bitmap[][] arrayposter= splitBitmap(p.getCartel(),3,3);


            AB = matriztoarraylist(arrayposter);

            Original = new ArrayList<Bitmap>(AB);

            int numColumnas = grl.getColumnCount();

            int numFilas = grl.getRowCount();

            Collections.shuffle(AB);

            System.out.println(numFilas+" "+numColumnas);

            grl.setUseDefaultMargins(false);


            int r = 0;

            for (int i = 0; i < numColumnas*numFilas; i++)
            {


                    ImageButton bt = new ImageButton(this);


                    bt.setLayoutParams(new ViewGroup.LayoutParams(1075/numColumnas , 1115/numFilas));

                    bt.setId(r);

                    bt.setImageBitmap(AB.get(i));

                    bt.setPadding(20,10,20,10);

                    bt.setScaleType(ImageView.ScaleType.CENTER_CROP);

                    bt.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View view) {




                            numClicks++;

                            if (numClicks == 1) {
                                imageButton1 = (ImageButton) view;



                                System.out.println("ID " + imageButton1.getId());


                            } else if (numClicks <= 2)
                            {

                                mov++;


                                ImageButton imageButton2 = (ImageButton) view;

                                Bitmap aux;

                                System.out.println("ID " + imageButton2.getId());

                                aux = (((BitmapDrawable)imageButton1.getDrawable()).getBitmap());

                                imageButton1.setImageBitmap(((BitmapDrawable)imageButton2.getDrawable()).getBitmap());

                                AB.set(imageButton1.getId(),(((BitmapDrawable)imageButton1.getDrawable()).getBitmap()));

                                imageButton2.setImageBitmap(aux);

                                AB.set(imageButton2.getId(),aux);

                                numClicks = 0;
                                
                                comprobarganar();

                            }
                        }
                    });


                    grl.addView(bt);

                    System.out.println("AAAAAAAAAAAAAAAAAAAAAa"+bt.getId());

                    r++;

            }






    }

    private void comprobarganar()
    {
        boolean ganar = true;

        for (int i = 0; i <Original.size() ; i++)
        {

            if(!Original.get(i).equals(AB.get(i)))
            {

                ganar = false;
            }


        }

        if(ganar)
        {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // 2. Chain together various setter methods to set the dialog characteristics
            builder.setMessage("Has necesitado "+mov+" Movimientos")
                    .setTitle("Has ganado");

            builder.setPositiveButton("Reintentar", new DialogInterface.OnClickListener()
            {
                public void onClick(DialogInterface dialog, int id)
                {

                    finish();

                    startActivity(getIntent());

                }
            });
            builder.setNegativeButton("Volver Atras", new DialogInterface.OnClickListener()
            {
                public void onClick(DialogInterface dialog, int id)
                {
                    finish();
                }
            });

        // 3. Get the <code><a href="/reference/android/app/AlertDialog.html">AlertDialog</a></code> from <code><a href="/reference/android/app/AlertDialog.Builder.html#create()">create()</a></code>
            AlertDialog dialog = builder.create();

            dialog.show();

            System.out.println("HASGANADO");

        }



    }

    public  ArrayList<Bitmap> matriztoarraylist(Bitmap[][] bitmap)
    {

        ArrayList<Bitmap> AB = new ArrayList<Bitmap>();

        for (int i = 0; i < bitmap.length; i++)
        {

            for (int j = 0; j <  bitmap[i].length; j++)
            {

                AB.add(bitmap[j][i]);

            }

        }


        return AB;
    }

    public Bitmap[][] splitBitmap(Bitmap bitmap, int xCount, int yCount) {
        // Allocate a two dimensional array to hold the individual images.
        Bitmap[][] bitmaps = new Bitmap[xCount][yCount];
        int width, height;
        // Divide the original bitmap width by the desired vertical column count
        width = bitmap.getWidth() / xCount;
        // Divide the original bitmap height by the desired horizontal row count
        height = bitmap.getHeight() / yCount;
        // Loop the array and create bitmaps for each coordinate
        for(int x = 0; x < xCount; ++x) {
            for(int y = 0; y < yCount; ++y) {
                // Create the sliced bitmap
                bitmaps[x][y] = Bitmap.createBitmap(bitmap, x * width, y * height, width, height);
            }
        }
        // Return the array
        return bitmaps;
    }

}