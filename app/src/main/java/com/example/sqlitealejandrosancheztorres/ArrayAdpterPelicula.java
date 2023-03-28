package com.example.sqlitealejandrosancheztorres;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomappbar.BottomAppBar;

import java.util.ArrayList;

public class ArrayAdpterPelicula extends ArrayAdapter<Pelicula>  {

    int tamaño;

    int contadorps;

    int idc;

    Boolean selected[];

    Context context;

    Activity a;


    public ArrayAdpterPelicula(Context context, java.util.List<Pelicula> Peliculas, int idc, Activity a)
    {
        super(context, 0, Peliculas);

        this.context = context;

        tamaño = Peliculas.size();

        this.idc = idc;

        selected = new Boolean[tamaño];

        rellenararray(selected);

        this.a = a;



    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v;

        v = LayoutInflater.from(getContext()).inflate(R.layout.layoutlv, parent, false);

        ImageButton ib1 = v.findViewById(R.id.imageButton);

        ImageButton ib2 = v.findViewById(R.id.imageButton2);

        if (position % 2 == 0) {
            Pelicula p = getItem(position);

            ib1.setImageBitmap(p.getCartel());

            ib1.setPadding(20,10,20,10);

            ib1.setScaleType(ImageView.ScaleType.CENTER_CROP);

            ib1.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {

                    if(!anyoneselec())
                    {

                        Intent i = new Intent(context,VerInfoPelicula.class);

                        i.putExtra("id",Integer.parseInt(p.getId()));

                        i.putExtra("idc",idc);

                        context.startActivity(i);

                    }

                }
            });

            ib1.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view)
                {
                    if(!selected[position])
                    {

                        final Animation animShake = AnimationUtils.loadAnimation(context, R.anim.shake);

                        ib1.startAnimation(animShake);

                        selected[position] = true;

                        mostrar();

                        mostraredit();
                    }else
                        {
                            final Animation animShake = AnimationUtils.loadAnimation(context, R.anim.shake);
                            animShake.cancel();
                            ib1.clearAnimation();

                            selected[position] = false;
                            mostrar();
                            mostraredit();
                        }



                    return false;
                }
            });

            try {

                Pelicula p2 = getItem(position + 1);

                ib2.setImageBitmap(p2.getCartel());

                ib2.setPadding(20,10,20,10);

                ib2.setScaleType(ImageView.ScaleType.CENTER_CROP);

                ib2.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {

                        if(!anyoneselec())
                        {

                            Intent i = new Intent(context,VerInfoPelicula.class);

                            i.putExtra("id",Integer.parseInt(p2.getId()));

                            i.putExtra("idc",idc);

                            context.startActivity(i);
                        }


                    }
                });

                ib2.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view)
                    {

                        if(!selected[position+1])
                        {

                            final Animation animShake = AnimationUtils.loadAnimation(context, R.anim.shake);

                            ib2.startAnimation(animShake);

                            selected[position+1] = true;

                            mostrar();

                            mostraredit();

                        }else
                        {
                            final Animation animShake = AnimationUtils.loadAnimation(context, R.anim.shake);

                            animShake.cancel();
                            ib2.clearAnimation();

                            selected[position+1] = false;

                            mostrar();

                            mostraredit();
                        }



                        return false;
                    }
                });


            } catch (Exception e)
            {

                ib2.setBackgroundDrawable(null);

            }



        }else
            {
                v = LayoutInflater.from(getContext()).inflate(R.layout.layoutvacio, parent, false);

                return v;

            }



        return v;
    }

    public void rellenararray(Boolean array[])
    {

        for (int i = 0; i < array.length; i++)
        {

            array[i] = false;

        }



    }

    public int contartrue(Boolean array[])
    {
        int a = 0;

        for (int i = 0; i < array.length; i++)
        {

            if(array[i] == true)
            {
                a++;
            }

        }


        return a;
    }



    public void mostraredit()
    {

        DBHelper db = new DBHelper(context);

        BottomAppBar ba = a.findViewById(R.id.bottomAppBar);

        Menu s = ba.getMenu();

        MenuItem menuite = s.getItem(1);


            System.out.println(s);

            if(contartrue(selected) == 1)
            {

                menuite.setVisible(true);

                menuite.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener()
                {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem)
                    {



                        Intent i = new Intent(a,EditarPelicula.class);

                        i.putExtra("id",getIdPeliculasSelec().get(0));

                        i.putExtra("idc",idc);

                        a.startActivityForResult(i,2000);



                        return false;
                    }
                });


            }else
                {

                    menuite.setVisible(false);

                }



    }

    public void mostrar()
    {

        DBHelper db = new DBHelper(context);

        BottomAppBar ba = a.findViewById(R.id.bottomAppBar);

        Menu s = ba.getMenu();

        MenuItem menuite = s.getItem(0);



        System.out.println(s);

        if(contartrue(selected) > 0)
        {



            menuite.setVisible(true);

            menuite.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener()
            {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem)
                {

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);

                    // 2. Chain together various setter methods to set the dialog characteristics
                    builder.setMessage("Estas Seguro?")
                            .setTitle("Se va a borrar "+contartrue(selected)+" Pelicula(s)");

                    builder.setPositiveButton("Si", new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface dialog, int id)
                        {

                            for (int i = 0; i < getIdPeliculasSelec().size(); i++)
                            {

                                db.deletefilm(Integer.parseInt(getIdPeliculasSelec().get(i)));

                                Intent in = a.getIntent();

                                a.finish();

                                a.startActivity(in);


                            }


                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface dialog, int id)
                        {

                        }
                    });

                    // 3. Get the <code><a href="/reference/android/app/AlertDialog.html">AlertDialog</a></code> from <code><a href="/reference/android/app/AlertDialog.Builder.html#create()">create()</a></code>
                    AlertDialog dialog = builder.create();

                    dialog.show();


                    return false;
                }
            });


        }else
        {

            menuite.setVisible(false);

        }



    }

    public ArrayList<String> getIdPeliculasSelec()
    {
        ArrayList<String> ids = new ArrayList<String>();

        ArrayList<Integer> posis= new ArrayList<Integer>();

        for (int i = 0; i < selected.length; i++)
        {

            if(selected[i] == true)
            {
                posis.add(i);
            }

        }

        for (int i = 0; i < posis.size() ; i++)
        {
            Pelicula p = getItem(posis.get(i));

            ids.add(p.getId());

        }

        return ids;
    }


    public boolean anyoneselec()
    {

        Boolean seleccionado = false;

        for (int i = 0; i < selected.length; i++)
        {

            if (selected[i])
            {
                seleccionado = true;
            }

        }

        return seleccionado;
    }



}


