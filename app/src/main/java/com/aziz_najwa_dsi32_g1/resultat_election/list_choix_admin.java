package com.aziz_najwa_dsi32_g1.resultat_election;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.appcompat.app.AppCompatActivity;

@SuppressWarnings("ALL")
public class list_choix_admin extends AppCompatActivity {

    ListView list;
    datahelper db;
    Intent in;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_choix_admin);
        list = findViewById(R.id.list1);
        db = new datahelper(this);
        btn = findViewById(R.id.btn_aj_choix);
        in = getIntent();
        final String id = in.getStringExtra("id");
        final ArrayList<HashMap<String, String>> listitems = db.getchoix(id);

        final SimpleAdapter adapter = new SimpleAdapter(this.getBaseContext(),
                listitems,
                R.layout.exemple_choix_admin,
                new String[]{"img", "nom", "id"},
                new int[]{R.id.image_ex_choix_ad, R.id.txt_ex_choix_ad}
        );
        list.setAdapter(adapter);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(getApplicationContext(), ajouter_choix.class);
                i1.putExtra("id", id);
                startActivity(i1);
            }
        });
    }

    public void supchoix(View view) {
        int p = list.getPositionForView(view);
        HashMap<String, String> map = (HashMap<String, String>) list.getItemAtPosition(p);
        db.deletchoix(map.get("id"));
        recreate();
    }
}
