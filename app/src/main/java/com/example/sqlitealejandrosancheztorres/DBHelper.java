package com.example.sqlitealejandrosancheztorres;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper
{

    public static final String DATABASE_NAME = "Cartelera.db";

    public DBHelper(Context context) {

        //Si quiero resetear la BDD, en versión puedo poner un 2 y se va a crear nueva
        super(context, DATABASE_NAME , null, 7);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {

        sqLiteDatabase.execSQL(
                "create table "+PeliculaEntry.PELICULA_TABLE_NAME  +"("
                        + PeliculaEntry.PELICULA_ID + " integer primary key ,"
                        + PeliculaEntry.PELICULA_DIRECTOR +" text,"
                        + PeliculaEntry.PELICULA_NAME +" text,"
                        + PeliculaEntry.PELICULA_GENDER+" text,"
                        + PeliculaEntry.PELICULA_DURATION +" integer,"
                        + PeliculaEntry.PELICULA_POSTER +" blob,"
                        + PeliculaEntry.PELICULA_SYNOPSIS +" text,"
                        + " idc "+" integer,"
                        + " FOREIGN KEY ("+ " idc " +") REFERENCES "+CuentaEntry.CUENTA_TABLE_NAME+"("+CuentaEntry.CUENTA_ID+"));"
        );

        sqLiteDatabase.execSQL(
                "create table "+CuentaEntry.CUENTA_TABLE_NAME  +"("
                        + CuentaEntry.CUENTA_ID + " integer primary key ,"
                        + CuentaEntry.CUENTA_NAME +" text,"
                        + CuentaEntry.CUENTA_CONTRASEÑA +" text)"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS "+ PeliculaEntry.PELICULA_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ CuentaEntry.CUENTA_TABLE_NAME);
        onCreate(db);
    }

    public boolean insertFilm (Pelicula p,int idc)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        System.out.println(p.toString());

        contentValues.put(PeliculaEntry.PELICULA_NAME, p.getNombre());
        contentValues.put(PeliculaEntry.PELICULA_DIRECTOR, p.getDirector());
        contentValues.put(PeliculaEntry.PELICULA_GENDER, p.getGenero());
        contentValues.put(PeliculaEntry.PELICULA_DURATION, p.getDuracion());
        contentValues.put(PeliculaEntry.PELICULA_POSTER, getBitmapAsByteArray(p.getCartel()));
        contentValues.put(PeliculaEntry.PELICULA_SYNOPSIS, p.getSinopsis());
        contentValues.put("idc", idc);
        db.insert(PeliculaEntry.PELICULA_TABLE_NAME, null, contentValues);

        //SE PODRIA USAR UN OBJETO
        return true;
    }

    public void insertAccount (String nombre, String contraseña)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(CuentaEntry.CUENTA_NAME, nombre);
        contentValues.put(CuentaEntry.CUENTA_CONTRASEÑA, contraseña);
        db.insert(CuentaEntry.CUENTA_TABLE_NAME, null, contentValues);

        //SE PODRIA USAR UN OBJETO

    }

    public Boolean ExisteCuenta(String nombre, String contraseña)
    {

        Boolean existe = false;

        SQLiteDatabase db = this.getReadableDatabase();
        //Cursor res =  db.rawQuery( "select count(*) from "+ CuentaEntry.CUENTA_TABLE_NAME+ " " +
          //      "where "+ CuentaEntry.CUENTA_NAME+" = " + nombre + " and "+ CuentaEntry.CUENTA_CONTRASEÑA+" = "+ contraseña, null );

        Cursor res= db.rawQuery("select count(*) from "+CuentaEntry.CUENTA_TABLE_NAME +" where "+CuentaEntry.CUENTA_NAME+"='" + nombre + "' and "+CuentaEntry.CUENTA_CONTRASEÑA+"='" + contraseña +"'", null);

        res.moveToFirst();


        int count= res.getInt(0);

        System.out.println(count);

        if(count >= 1)
        {

            existe = true;

        }

        System.out.println(existe);

        return existe;


    }

    public Boolean ExisteUsuario(String nombre)
    {

        Boolean existe = false;

        SQLiteDatabase db = this.getReadableDatabase();
        //Cursor res =  db.rawQuery( "select count(*) from "+ CuentaEntry.CUENTA_TABLE_NAME+ " " +
        //      "where "+ CuentaEntry.CUENTA_NAME+" = " + nombre + " and "+ CuentaEntry.CUENTA_CONTRASEÑA+" = "+ contraseña, null );

        Cursor res= db.rawQuery("select count(*) from "+CuentaEntry.CUENTA_TABLE_NAME +" where "+CuentaEntry.CUENTA_NAME+"='" + nombre + "'" , null);

        res.moveToFirst();


        int count= res.getInt(0);

        System.out.println(count);

        if(count >= 1)
        {

            existe = true;

        }

        System.out.println(existe);

        return existe;


    }

    public int getID(String nombre, String contraseña)
    {

        int id = 0;

        SQLiteDatabase db = this.getReadableDatabase();
        //Cursor res =  db.rawQuery( "select count(*) from "+ CuentaEntry.CUENTA_TABLE_NAME+ " " +
        //      "where "+ CuentaEntry.CUENTA_NAME+" = " + nombre + " and "+ CuentaEntry.CUENTA_CONTRASEÑA+" = "+ contraseña, null );

        Cursor res= db.rawQuery("select "+ CuentaEntry.CUENTA_ID +" from "+CuentaEntry.CUENTA_TABLE_NAME +" where "+CuentaEntry.CUENTA_NAME+"='" + nombre + "' and "+CuentaEntry.CUENTA_CONTRASEÑA+"='" + contraseña +"'", null);

        res.moveToFirst();


        id = res.getInt(0);


        return id;


    }

    public Pelicula getData(int id, int idc)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+ PeliculaEntry.PELICULA_TABLE_NAME+ " " +
                "where "+ PeliculaEntry.PELICULA_ID+" = " + id, null );

        res.moveToFirst();

        int columna = res.getColumnIndex((PeliculaEntry.PELICULA_ID));

        int idString= res.getInt(columna);

        columna = res.getColumnIndex(PeliculaEntry.PELICULA_NAME);

        String nombre = res.getString(columna);

        columna = res.getColumnIndex(PeliculaEntry.PELICULA_DIRECTOR);

        String director = res.getString(columna);

        columna = res.getColumnIndex(PeliculaEntry.PELICULA_GENDER);

        String genero = res.getString(columna);

        columna = res.getColumnIndex(PeliculaEntry.PELICULA_DURATION);

        int duracion = res.getInt(columna);

        columna = res.getColumnIndex(PeliculaEntry.PELICULA_POSTER);

        byte[] imgByte = res.getBlob(columna);

        Bitmap poster = BitmapFactory.decodeByteArray(imgByte,0,imgByte.length);

        columna = res.getColumnIndex(PeliculaEntry.PELICULA_SYNOPSIS);

        String synospsis = res.getString(columna);

        Pelicula p = new Pelicula(String.valueOf(idString),nombre,director,genero,duracion,synospsis,poster);


        return p;
    }

    public int numberOfRows()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, PeliculaEntry.PELICULA_TABLE_NAME);
        return numRows;
    }

    public boolean updatefilm (Pelicula p) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(PeliculaEntry.PELICULA_NAME, p.getNombre());
        contentValues.put(PeliculaEntry.PELICULA_DIRECTOR, p.getDirector());
        contentValues.put(PeliculaEntry.PELICULA_GENDER, p.getGenero());
        contentValues.put(PeliculaEntry.PELICULA_DURATION, p.getDuracion());
        contentValues.put(PeliculaEntry.PELICULA_POSTER, getBitmapAsByteArray(p.getCartel()));
        contentValues.put(PeliculaEntry.PELICULA_SYNOPSIS, p.getSinopsis());
        db.update(PeliculaEntry.PELICULA_TABLE_NAME,
                contentValues, PeliculaEntry.PELICULA_ID +" = ? ", new String[] { p.getId() } );
        return true;
    }

    public Integer deletefilm (Integer id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(PeliculaEntry.PELICULA_TABLE_NAME,
                PeliculaEntry.PELICULA_ID +" = ? ",
                new String[] { Integer.toString(id) });
    }

    public ArrayList<Pelicula> getAllFilms(int idc)
    {
        ArrayList<Pelicula> array_list = new ArrayList<Pelicula>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+PeliculaEntry.PELICULA_TABLE_NAME+ " " +
                "where "+ "idc"+" = " + idc, null );
        res.moveToFirst();



        while(res.isAfterLast() == false){
//Voy a cargar en la lista el ID y el NOMBRE de los contactos



            int columna = res.getColumnIndex((PeliculaEntry.PELICULA_ID));

            int idString= res.getInt(columna);

            columna = res.getColumnIndex(PeliculaEntry.PELICULA_NAME);

            String nombre = res.getString(columna);

            columna = res.getColumnIndex(PeliculaEntry.PELICULA_DIRECTOR);

            String director = res.getString(columna);

            columna = res.getColumnIndex(PeliculaEntry.PELICULA_GENDER);

            String genero = res.getString(columna);

            columna = res.getColumnIndex(PeliculaEntry.PELICULA_DURATION);

            int duracion = res.getInt(columna);

            columna = res.getColumnIndex(PeliculaEntry.PELICULA_POSTER);

            byte[] imgByte = res.getBlob(columna);

            Bitmap poster = BitmapFactory.decodeByteArray(imgByte,0,imgByte.length);

            columna = res.getColumnIndex(PeliculaEntry.PELICULA_SYNOPSIS);

            String synospsis = res.getString(columna);


            array_list.add(new Pelicula(String.valueOf(idString),nombre,director,genero,duracion,synospsis,poster));

            res.moveToNext();
        }
        return array_list;
    }








    public static byte[] getBitmapAsByteArray(Bitmap bitmap)
    {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);

        return outputStream.toByteArray();


    }
}
