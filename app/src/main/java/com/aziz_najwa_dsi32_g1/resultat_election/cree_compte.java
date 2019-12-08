package com.aziz_najwa_dsi32_g1.resultat_election;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class cree_compte extends AppCompatActivity {
    EditText cin;
    EditText nom;
    EditText prenom;
    EditText email;
    EditText login;
    EditText pwd;
    Button btn;
    TextView txt;
    datahelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cree_votre_compte);

        db = new datahelper(this);
        cin = findViewById(R.id.edittext);
        nom = findViewById(R.id.id_nom);
        prenom = findViewById(R.id.id_pre);
        email = findViewById(R.id.id_email);
        login = findViewById(R.id.id_iden);
        pwd = findViewById(R.id.id_pass);
        btn = findViewById(R.id.button);
        txt = findViewById(R.id.textView14);
        txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), login.class);
                startActivity(i);
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String t1 = cin.getText().toString();
                String t2 = nom.getText().toString();
                String t3 = prenom.getText().toString();
                String t4 = email.getText().toString();
                String t5 = login.getText().toString();
                String t6 = pwd.getText().toString();
                if ((t1.equals("")) || (t2.equals("")) || (t3.equals("")) || (t4.equals("")) || (t5.equals("")) || (t6.equals(""))) {
                    Toast.makeText(getApplicationContext(), "Files are empty", Toast.LENGTH_LONG).show();
                } else {
                    boolean chkemail = db.chekemail(t4);
                    boolean chkelog = db.cheklog(t5);
                    if (chkemail) {
                        if (chkelog) {
                            db.insertuser(t1, t2, t3, t4, t5, t6);
                            Intent i = new Intent(getApplicationContext(), login.class);
                            startActivity(i);
                        } else {
                            Toast.makeText(getApplicationContext(), "Login alredy exist", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Email alredy exist", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }
}
