package com.aziz_najwa_dsi32_g1.resultat_election;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

@SuppressWarnings("ALL")
public class res_elec extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    datahelper db;
    ListView list;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_res);

        this.configureToolBar();

        this.configureDrawerLayout();

        this.configureNavigationView();

        db = new datahelper(this);
        list = findViewById(R.id.list5);
        Intent in = getIntent();
        String i = in.getStringExtra("id");
        final ArrayList<HashMap<String, String>> listitems = db.getchoixres(i);

        final SimpleAdapter adapter = new SimpleAdapter(this.getBaseContext(),
                listitems,
                R.layout.exemple_result,
                new String[]{"img", "nom", "res", "id"},
                new int[]{R.id.image_ex_res, R.id.txt_ex_res, R.id.res_ex_res}
        );
        list.setAdapter(adapter);
    }

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
                SharedPreferences.Editor editor = pref.edit();
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
}