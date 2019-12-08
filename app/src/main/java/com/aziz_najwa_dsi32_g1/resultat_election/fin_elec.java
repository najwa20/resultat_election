package com.aziz_najwa_dsi32_g1.resultat_election;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class fin_elec  extends AppCompatActivity {
    datahelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new datahelper(this);
        Intent in = getIntent();
        int i =Integer.valueOf(in.getStringExtra("id"));

        if(db.testter(i))
        {
            setContentView(R.layout.exemple_result);
        }
        else
        {
            setContentView(R.layout.conf_page);
        }
    }
}
