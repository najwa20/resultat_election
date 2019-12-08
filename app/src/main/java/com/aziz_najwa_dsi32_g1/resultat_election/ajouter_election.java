package com.aziz_najwa_dsi32_g1.resultat_election;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ajouter_election extends AppCompatActivity {
    EditText e1;
    Button btn;
    datahelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ajouter_elec);
        db = new datahelper(this);
        e1=findViewById(R.id.nom_elec);
        btn=findViewById(R.id.id_cree_ele);
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
                    db.insertelection(nom);
                    Intent i = new Intent(getApplicationContext(), liste_election_admin.class);
                    startActivity(i);
                }
            }
        });
    }
}
