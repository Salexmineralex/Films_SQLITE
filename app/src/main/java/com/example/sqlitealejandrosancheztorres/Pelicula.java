package com.example.sqlitealejandrosancheztorres;

import android.graphics.Bitmap;

import java.io.Serializable;

public class Pelicula implements Serializable
{

    String id;

    private String nombre;

    private String director;

    private String genero;

    private int duracion;

    private String sinopsis;

    private Bitmap cartel;

    public Pelicula(String id, String nombre, String director, String genero, int duracion, String sinopsis, Bitmap cartel)
    {
        this.id = id;
        this.nombre = nombre;
        this.director = director;
        this.genero = genero;
        this.duracion = duracion;
        this.sinopsis = sinopsis;
        this.cartel = cartel;
    }

    public Pelicula(String nombre, String director, String genero, int duracion, String sinopsis, Bitmap cartel)
    {
        this.id = "";
        this.nombre = nombre;
        this.director = director;
        this.genero = genero;
        this.duracion = duracion;
        this.sinopsis = sinopsis;
        this.cartel = cartel;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    public Bitmap getCartel() {
        return cartel;
    }

    public void setCartel(Bitmap cartel) {
        this.cartel = cartel;
    }

    @Override
    public String toString() {
        return "Pelicula{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", director='" + director + '\'' +
                ", genero='" + genero + '\'' +
                ", duracion=" + duracion +
                ", sinopsis='" + sinopsis + '\'' +
                ", cartel=" + cartel +
                '}';
    }
}
