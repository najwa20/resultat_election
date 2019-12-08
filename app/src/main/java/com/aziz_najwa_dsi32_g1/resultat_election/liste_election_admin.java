package com.aziz_najwa_dsi32_g1.resultat_election;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.appcompat.app.AppCompatActivity;

@SuppressWarnings("ALL")
public class liste_election_admin extends AppCompatActivity {

    ListView list;
    Button btn;
    datahelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_election_admin);

        list = findViewById(R.id.list3);
        btn = findViewById(R.id.btn_aj_elec);
        db = new datahelper(this);
        final ArrayList<HashMap<String, String>> listitems = db.getEle();
        final SimpleAdapter adapter = new SimpleAdapter(this.getBaseContext(),
                listitems,
                R.layout.exemple_election_admin,
                new String[]{"img", "nom", "id"},
                new int[]{R.id.image_ex_el_ad, R.id.txt_ex_el_ad}
        );
        list.setAdapter(adapter);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ajouter_election.class);
                startActivity(i);
            }
        });
    }

    public void listchoix(View view) {
        int p = list.getPositionForView(view);
        HashMap<String, String> map = (HashMap<String, String>) list.getItemAtPosition(p);
        Intent i = new Intent(getApplicationContext(), list_choix_admin.class);
        i.putExtra("id", map.get("id"));
        startActivity(i);
    }

    public void supele(View view) {
        int p = list.getPositionForView(view);
        HashMap<String, String> map = (HashMap<String, String>) list.getItemAtPosition(p);
        db.deletelect(map.get("id"));
        recreate();
    }

    public void arrele(View view) {
        int p = list.getPositionForView(view);
        HashMap<String, String> map = (HashMap<String, String>) list.getItemAtPosition(p);
        db.arrelect(map.get("id"));
        Toast.makeText(getApplicationContext(), "election stoped", Toast.LENGTH_LONG).show();

    }
}
