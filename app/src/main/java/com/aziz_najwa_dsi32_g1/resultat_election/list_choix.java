package com.aziz_najwa_dsi32_g1.resultat_election;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.appcompat.app.AppCompatActivity;

public class list_choix  extends AppCompatActivity {

    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resultat_election);

        list=findViewById(R.id.list_choix);
        final ArrayList<HashMap<String,String>> listitems =new ArrayList<>();
        HashMap<String,String> map =new HashMap<>();
        map.put("titre","choix1");
        map.put("img",String.valueOf(R.drawable.election));
        listitems.add(map);

        map =new HashMap<>();
        map.put("titre","choix2");
        map.put("img",String.valueOf(R.drawable.election));
        listitems.add(map);

        map =new HashMap<>();
        map.put("titre","choix3");
        map.put("img",String.valueOf(R.drawable.election));
        listitems.add(map);listitems.add(map);listitems.add(map);

        final SimpleAdapter adapter = new SimpleAdapter(this.getBaseContext(),
                listitems,
                R.layout.exempl_choix,
                new String[]{"img","titre"},
                new int[]{R.id.imageView5,R.id.id_nom}
        );
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String,String> map = (HashMap<String,String>) list.getItemAtPosition(position);
                Toast.makeText(list_choix.this, map.get("titre"), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
