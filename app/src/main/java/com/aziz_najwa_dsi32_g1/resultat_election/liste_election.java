package com.aziz_najwa_dsi32_g1.resultat_election;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

@SuppressWarnings("ALL")
public class liste_election extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    ListView list;
    datahelper db;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_election);

        this.configureToolBar();
        this.configureDrawerLayout();
        this.configureNavigationView();

        db = new datahelper(this);


        //remplisage de list
        list = findViewById(R.id.list1);
        final ArrayList<HashMap<String, String>> listitems = db.getEle();

        final SimpleAdapter adapter = new SimpleAdapter(this.getBaseContext(),
                listitems,
                R.layout.exemple_election,
                new String[]{"img", "nom", "id"},
                new int[]{R.id.image_ex_el, R.id.txt_ex_el}
        );
        list.setAdapter(adapter);

        //go to items
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String, String> map = (HashMap<String, String>) list.getItemAtPosition(position);
                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
                SharedPreferences.Editor editor = pref.edit();
                String login = pref.getString("login", null);
                if (db.testter(Integer.valueOf(map.get("id"))).equals("1")) {
                    Intent i1 = new Intent(getApplicationContext(), res_elec.class);
                    i1.putExtra("id", map.get("id"));
                    startActivity(i1);
                } else {
                    if (db.testelec(map.get("id"), login)) {
                        Intent i = new Intent(getApplicationContext(), fin_elec.class);
                        startActivity(i);
                    } else {
                        Intent i = new Intent(getApplicationContext(), list_choix.class);
                        i.putExtra("id", map.get("id"));
                        startActivity(i);
                    }
                }
            }
        });
    }


    //Menu

    @Override
    public void onBackPressed() {
        // 5 - Handle back click to close menu
        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // 4 - Handle Navigation Item Click
        int id = item.getItemId();

        switch (id) {
            case R.id.accueil:
                Intent intent1 = new Intent(this, liste_election.class);
                this.startActivity(intent1);
                break;
            case R.id.dec:
                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
                @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = pref.edit();
                editor.remove("login");
                editor.remove("pwd");
                editor.remove("c");
                editor.apply();
                Intent intent2 = new Intent(this, login.class);
                this.startActivity(intent2);
                break;
        }

        this.drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    // ---------------------
    // CONFIGURATION
    // ---------------------

    // 1 - Configure Toolbar
    private void configureToolBar() {
        this.toolbar = findViewById(R.id.toolbar);
    }

    // 2 - Configure Drawer Layout
    private void configureDrawerLayout() {
        this.drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    // 3 - Configure NavigationView
    private void configureNavigationView() {
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }
}
