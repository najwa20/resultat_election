package com.aziz_najwa_dsi32_g1.resultat_election;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

public class fin_elec extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    datahelper db;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new datahelper(this);
        setContentView(R.layout.conf_page);
        toolbar = findViewById(R.id.toolbar);


        this.configureDrawerLayout();

        this.configureNavigationView();
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
}
