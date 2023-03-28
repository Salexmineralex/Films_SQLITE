package com.example.sqlitealejandrosancheztorres;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.RenderEffect;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;

import jp.wasabeef.glide.transformations.BlurTransformation;

public class VerInfoPelicula extends AppCompatActivity{

    TextView nombre;

    TextView genero;

    ImageView poster;

    TextView director;

    TextView sinopsis;

    Pelicula p;

    int id;

    int idc;

    private static String JSON_URL = "";


    @RequiresApi(api = Build.VERSION_CODES.S)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_info_pelicula);

        ScrollView sc = findViewById(R.id.scrollView4);

        nombre = findViewById(R.id.Nombre);

        genero = findViewById(R.id.genero);

        poster = findViewById(R.id.imageView2);

        director = findViewById(R.id.Director);

        sinopsis = findViewById(R.id.sinopsis);

        DBHelper dbh = new DBHelper(this);

        id = getIntent().getIntExtra("id",0);

        idc = getIntent().getIntExtra("idc",0);

        p = (Pelicula) dbh.getData(id,idc);

        nombre.setText(p.getDirector());

        genero.setText(p.getGenero()+"\\"+p.getDuracion()+" minutos");

        poster.setImageBitmap(p.getCartel());

        director.setText(p.getNombre());

        sinopsis.setText("Sinopsis: "+p.getSinopsis());

        String nombremas = p.getNombre().replace(' ','+');


         JSON_URL = "https://api.themoviedb.org/3/search/movie?api_key=6dc2fa7afbc83f5c84eb4cfc95068114&query="+nombremas;

         GetData gd = new GetData(JSON_URL,this);

         gd.execute();



        /*Glide.with(this).load(p.getCartel())
                .apply(bitmapTransform(new BlurTransformation(22)))
                .into((ImageView) findViewById(R.id.imageView3));
*/

        /*String hexColor = String.format("#%06X", (0xFFFFFF & getDominantColor(p.getCartel())));

        System.out.println(hexColor);

        int rgb [] = getRGB(hexColor);

        System.out.println(rgb[0]);
        System.out.println(rgb[1]);
        System.out.println(rgb[2]);

        float suma = rgb[0]*0.299f+rgb[1]*0.587f+rgb[2]*0.114f;

        System.out.println(suma);

        if(suma < 115)
        {
            //BLANCO

            nombre.setTextColor(getResources().getColor(R.color.whitebruce));
            genero.setTextColor(getResources().getColor(R.color.whitebruce));
            director.setTextColor(getResources().getColor(R.color.whitebruce));
            sinopsis.setTextColor(getResources().getColor(R.color.whitebruce));

        }else
            {
                //NEGRO
                nombre.setTextColor(getResources().getColor(R.color.black));
                genero.setTextColor(getResources().getColor(R.color.black));
                director.setTextColor(getResources().getColor(R.color.black));
                sinopsis.setTextColor(getResources().getColor(R.color.black));
            }

*/


    }

    public void jugarpuzzle(View view)
    {


        Intent i = new Intent(getApplicationContext(),Puzzle.class);

        i.putExtra("p",id);

        startActivity(i);

    }


    @Override
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


                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



//    public static int getDominantColor(Bitmap bitmap)
//    {
//        Bitmap newBitmap = Bitmap.createScaledBitmap(bitmap, 1, 1, true);
//        final int color = newBitmap.getPixel(0, 0);
//        newBitmap.recycle();
//        return color;
//    }
//
//    public static int[] getRGB(final String rgb)
//    {
//        int r = Integer.parseInt(rgb.substring(1, 3), 16); // 16 for hex
//        int g = Integer.parseInt(rgb.substring(3, 5), 16); // 16 for hex
//        int b = Integer.parseInt(rgb.substring(5, 7), 16); // 16 for hex
//        return new int[] {r, g, b};
//    }




}


