package com.example.mypaintv0;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;

public class MainActivity extends AppCompatActivity {



    PaintView paintView;
    public ProgressBar progressBar;
    public SeekBar seekbar_cursor;
    public TextView seekbar_value;
    public static int sizeCursor;
    private Activity2 activity2Obj;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        paintView = (PaintView) findViewById(R.id.paintView);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        paintView.init(metrics);



//---------------------------------------------------------------------------------



//---------------------------------------------------------------------------------

    }

    public int getPaintSizeCursor(){
        return paintView.getStrokeWidth();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 222 && resultCode == RESULT_OK){
            int xSize = (data.getIntExtra("taille", paintView.getStrokeWidth()));
            paintView.setStrokeWidth(xSize);
            int xColor = (data.getIntExtra("couleur", paintView.getColor()));
            paintView.setColor(xColor);
        }


        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main, menu);
        if(menu instanceof MenuBuilder){
            MenuBuilder menuBuilder = (MenuBuilder) menu;
            menuBuilder.setOptionalIconsVisible(true);
        }
        //return true;
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.normal:
                paintView.normal();
                return true;
                /*
            case R.id.emboss:
                paintView.emboss();
                return true;
                */
            case R.id.blur:
                paintView.blur();
                return true;
            case R.id.clear:
                paintView.clear();
                return true;
            case R.id.option:
                openActivity2();
                return true;
            case R.id.credit:
                openActivity3();
                return true;
            case R.id.gomme:
                paintView.effacer();
                return true;

            case R.id.color_palet:

                return true;


            case R.id.save:
                //paintView.clear();
                return true;
            case R.id.import_img:
                //openActivity3();
                return true;
            case R.id.color_pot:
                //openActivity3();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    public void openActivity2(){
        Intent intent = new Intent(getApplicationContext(), Activity2.class);
        intent.putExtra("tailleCurs", paintView.getStrokeWidth());
        intent.putExtra("couleurCurs", paintView.getColor());
        startActivityForResult((intent), 222);
    }

    public void openActivity3(){
        Intent intent = new Intent(getApplicationContext(), Activity3.class);
        //startActivityForResult((intent), 333);
        startActivity(intent);
    }
}