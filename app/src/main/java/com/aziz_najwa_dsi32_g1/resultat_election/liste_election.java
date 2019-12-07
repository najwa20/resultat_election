package com.aziz_najwa_dsi32_g1.resultat_election;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.appcompat.app.AppCompatActivity;

public class liste_election extends AppCompatActivity {

    ListView list;
    datahelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_election);

        list=findViewById(R.id.list1);
        db = new datahelper(this);
        final ArrayList<HashMap<String,String>> listitems =db.getEle();

        final SimpleAdapter adapter = new SimpleAdapter(this.getBaseContext(),
                listitems,
                R.layout.exemple_election,
                new String[]{"img", "nom","id"},
                new int[]{R.id.image_ex_el, R.id.txt_ex_el}
        );
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String,String> map = (HashMap<String,String>) list.getItemAtPosition(position);
                Intent i = new Intent(getApplicationContext(), list_choix.class);
                i.putExtra("id",map.get("id"));
                startActivity(i);
            }
        });
    }
}
