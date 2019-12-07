package com.aziz_najwa_dsi32_g1.resultat_election;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ajouter_choix extends AppCompatActivity {
    EditText e1;
    Button btn;
    datahelper db;
    Intent in;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ajout_choix);
        db = new datahelper(this);
        e1=findViewById(R.id.nom_choix);
        btn=findViewById(R.id.id_cree_choix);
        in = getIntent();
        final int id= Integer.parseInt(in.getStringExtra("id"));
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nom=e1.getText().toString();
                if(nom.equals(""))
                {
                    Toast.makeText(getApplicationContext(), "Files are empty", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Boolean ins = db.insertchoix(nom,id);
                    Intent i = new Intent(getApplicationContext(), list_choix_admin.class);
                    i.putExtra("id",String.valueOf(id));
                    startActivity(i);
                }
            }
        });
    }
}