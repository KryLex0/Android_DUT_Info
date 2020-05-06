package com.example.mypaintv0;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.ContentProviderClient;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import javax.xml.transform.Result;

import yuku.ambilwarna.AmbilWarnaDialog;

public class Activity2 extends AppCompatActivity {

    PaintView paintView;
    public ProgressBar progressBar;     //progressBar
    public SeekBar seekbar_cursor;      //curseur sur la progressBar
    public TextView seekbar_value;      //valeur progressBar
    //public TextView text_seekbar;      //Texte: Taille du curseur
    //MainActivity mainobj = new MainActivity();

    private static int sizeCursor;
    private static int DefaultSizeCursor  = -1;


    private static int DefaultColor = -1;
    private static int newColor;
    Button btn_color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        //text_seekbar = (TextView) findViewById(R.id.text_seekbar);
        progressBar = (ProgressBar) findViewById(R.id.progressBar); //R.id.progressBar);
        seekbar_cursor = (SeekBar) findViewById(R.id.seekbar_cursor); //id.seekbar_cursor);
        seekbar_value = (TextView)findViewById(R.id.seekbar_value); //R.id.seekbar_value);
        btn_color = (Button) findViewById(R.id.btn_color);


        Intent intent = getIntent();
        int x = (intent.getIntExtra("tailleCurs", 0));
        int y = (intent.getIntExtra("couleurCurs", 0));
        Log.i("couleur2===== ", String.valueOf(x));

        if(x != DefaultSizeCursor){
            setNewColor(y);
            setSizeCursor(x);
            Log.i("couleur = ", String.valueOf(getNewColor()));
            Log.i("size = ", String.valueOf(getSizeCursor()));
        }else {
            setSizeCursor(DefaultSizeCursor);
            setNewColor(DefaultColor);
            Log.i("couleur = ", "erreur");
        }

        progressBar.setProgress(getSizeCursor());
        seekbar_cursor.setProgress(getSizeCursor());
        btn_color.setBackgroundColor(getNewColor());
        seekbar_value.setText("" + getSizeCursor() + "px");



        btn_color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openColorPicker();
            }
        });

        getSupportActionBar().setTitle("Options");

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);


        //Log.i("curseur =", String.valueOf(sizeCursor));




        seekbar_cursor.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
                progressBar.setProgress(progress);
                seekbar_value.setText("" + progress + "px");
                setSizeCursor(progress);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar){

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar){
                Log.i("stop",String.valueOf(getSizeCursor()));


            }

        });

    }


    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("color", getNewColor());
        savedInstanceState.putInt("cursor_size", getSizeCursor());
    }

    @Override
    public void onBackPressed() {               //back fleche android
        //super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        if(getSizeCursor() != DefaultSizeCursor) {
            intent.putExtra("taille", getSizeCursor());
            setResult(RESULT_OK, intent);
        }
        if(getNewColor() != DefaultColor) {
            intent.putExtra("couleur", getNewColor());
            setResult(RESULT_OK, intent);
        }

        Log.i("new color get = ", String.valueOf(getNewColor()));
        Log.i("new size get = ", String.valueOf(getSizeCursor()));
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {          //back fleche appli
        int id = item.getItemId();
        if(id == android.R.id.home){
            onBackPressed();
        }
        //return super.onOptionsItemSelected(item);
        return true;
    }


    public int getSizeCursor(){
        return sizeCursor;
    }
    public void setSizeCursor(int x){
        sizeCursor = x;
    }

    public int getNewColor(){
        return newColor;
    }
    public void setNewColor(int x){
        newColor = x;
    }

    public void openColorPicker(){
        AmbilWarnaDialog colorPicker = new AmbilWarnaDialog(this, getNewColor(), new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                setNewColor(color);
                btn_color.setBackgroundColor(getNewColor());
                Log.i("couleur blanche",String.valueOf(getNewColor()));
                //onBackPressed();
            }
        });
        colorPicker.show();
    }


}
