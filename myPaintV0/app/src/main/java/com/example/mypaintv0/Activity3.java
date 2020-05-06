package com.example.mypaintv0;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

public class Activity3 extends AppCompatActivity {

    TextView credit_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3);
        credit_info = (TextView) findViewById(R.id.credit_info);
        credit_info.setText(credit_info.getText().toString() + "Version 0: Création de la zone de dessin\n\n");
        credit_info.setText(credit_info.getText().toString() + "Version 0.1: Ajout d'un menu avec la possibilité de changer le style de pinceau (à revoir car Freeze)\n\n");
        credit_info.setText(credit_info.getText().toString() + "Version 0.1.5: Correction de l'activité principale (récupération des dessins lorsque l'on va dans les options et qu'on retourne sur la mainActivity)\n\n");
        credit_info.setText(credit_info.getText().toString() + "Version 0.2: Ajout du menu 'Options' qui permet de modifier la taille du curseur et la couleur\n\n");
        credit_info.setText(credit_info.getText().toString() + "Version 0.3.7: Correction de l'affichage des options lorsque l'on quitte et revient dans cet item\n" +
                "Avant, impossible d'afficher les variables de couleurs et de taille\n\n");
        credit_info.setText(credit_info.getText().toString() + "Version 0.4: Ajout des icones de dessin/gomme/trash.... dans le menu\n\n");
    }


    @Override
    public void onBackPressed() {               //back fleche android
        //super.onBackPressed();
        //Intent intent = new Intent(this, MainActivity.class);
        //intent.putExtra("taille", getSizeCursor());
        //setResult(RESULT_OK, intent);
        setResult(RESULT_OK);
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


}
