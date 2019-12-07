package com.aziz_najwa_dsi32_g1.resultat_election;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.appcompat.app.AppCompatActivity;

public class liste_election_admin  extends AppCompatActivity {

    ListView list;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_election_admin);

        list = findViewById(R.id.list3);
        btn = findViewById(R.id.btn_aj_elec);
        final ArrayList<HashMap<String, String>> listitems = new ArrayList<>();
        HashMap<String, String> map = new HashMap<>();
        map.put("titre", "Election municipale");
        map.put("img", String.valueOf(R.drawable.election));
        listitems.add(map);

        map = new HashMap<>();
        map.put("titre", "Elections législatives");
        map.put("img", String.valueOf(R.drawable.election));
        listitems.add(map);

        map = new HashMap<>();
        map.put("titre", "Election presidentielle");
        map.put("img", String.valueOf(R.drawable.election));
        listitems.add(map);

        final SimpleAdapter adapter = new SimpleAdapter(this.getBaseContext(),
                listitems,
                R.layout.exemple_election_admin,
                new String[]{"img", "titre"},
                new int[]{R.id.imageView4, R.id.id_election3}
        );
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(), list_choix_admin.class);
                startActivity(i);
            }
        });
    }
}
