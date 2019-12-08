package com.aziz_najwa_dsi32_g1.resultat_election;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

@SuppressWarnings("ALL")
public class liste_election_admin extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    ListView list;
    Button btn;
    datahelper db;

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_election_admin);

        this.configureToolBar();
        this.configureDrawerLayout();
        this.configureNavigationView();

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

        switch (id){
            case R.id.accueil :
                Intent intent1 = new Intent(this,liste_election.class);
                this.startActivity(intent1);
                break;
            case R.id.dec:
                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
                @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = pref.edit();
                editor.remove("login");
                editor.remove("pwd");
                editor.remove("c");
                editor.apply();
                Intent intent2 = new Intent(this,login.class);
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
    private void configureToolBar(){
        this.toolbar = findViewById(R.id.toolbar);
    }

    // 2 - Configure Drawer Layout
    private void configureDrawerLayout(){
        this.drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    // 3 - Configure NavigationView
    private void configureNavigationView(){
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }
}
