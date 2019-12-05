package com.aziz_najwa_dsi32_g1.resultat_election;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class login extends AppCompatActivity {
    EditText login;
    EditText pwd;
    Button btn;
    TextView txt;
    datahelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.creation_compt);
        db = new datahelper(this);
        login = findViewById(R.id.login);
        pwd = findViewById(R.id.user_password);
        btn = findViewById(R.id.id_cree);
        txt = findViewById(R.id.textView);
        txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), cree_compte.class);
                startActivity(i);
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String t1 = login.getText().toString();
                String t2 = pwd.getText().toString();
                if (db.chekpass(t1, t2)) {
                    if (t1 == "admin") {
                        Toast.makeText(getApplicationContext(), "succesfuly login admin", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "succesfuly login user", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "woring email or password", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
