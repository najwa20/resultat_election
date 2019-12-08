package com.aziz_najwa_dsi32_g1.resultat_election;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

public class list_choix extends AppCompatActivity {

    ListView list;
    datahelper db;
    Button btn;
    private DrawerLayout mDrawerLayout;
    Intent in;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_choix);
        btn=findViewById(R.id.btn_aj_choix);
        toolbar =findViewById(R.id.toolbar);

        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true);
        }

        mDrawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();

                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here

                        return true;
                    }
                });


        list = findViewById(R.id.list1);
        db = new datahelper(this);
        in = getIntent();
        String i = in.getStringExtra("id");
        final ArrayList<HashMap<String, String>> listitems = db.getchoix(i);

        final SimpleAdapter adapter = new SimpleAdapter(this.getBaseContext(),
                listitems,
                R.layout.exemple_choix,
                new String[]{"img", "nom", "id"},
                new int[]{R.id.image_ex_choix, R.id.txt_ex_choix}
        );
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String, String> map =
                        (HashMap<String, String>) list.getItemAtPosition(position);
                Toast.makeText(list_choix.this, map.get("titre"), Toast.LENGTH_SHORT).show();
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int len=list.getCount();
                int rep=-1;
                CheckBox c1;
                for(int i=0 ; i < len ; i++){
                    View v1=list.getChildAt(i);
                    c1=v1.findViewById(R.id.checkBox);
                    if(c1.isChecked())
                    {
                        rep=i;
                    }
                }
                if(rep==-1)
                {
                    Toast.makeText(getApplicationContext(), "choice your choise", Toast.LENGTH_LONG).show();
                }
                else
                {
                    in = getIntent();
                    String id = in.getStringExtra("id");
                    SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
                    SharedPreferences.Editor editor = pref.edit();
                    String login = pref.getString("login", null);
                    db.send(id,String.valueOf(rep),login);
                    Intent i = new Intent(getApplicationContext(), fin_elec.class);
                    startActivity(i);
                }
            }
        });
    }



    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            mDrawerLayout.openDrawer(GravityCompat.START);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void check(View view) {
        CheckBox c=view.findViewById(R.id.checkBox);
        CheckBox c1;
        if(c.isChecked())
        {
            int len=list.getCount();
            for(int i=0 ; i < len ; i++){
                View v1=list.getChildAt(i);
                c1=v1.findViewById(R.id.checkBox);
                c1.setChecked(false);
            }
            c.setChecked(true);
        }
        else
        {
            int len=list.getCount();
            for(int i=0 ; i < len ; i++){
                View v1=list.getChildAt(i);
                c1=v1.findViewById(R.id.checkBox);
                c1.setChecked(false);
            }
        }
    }
}
