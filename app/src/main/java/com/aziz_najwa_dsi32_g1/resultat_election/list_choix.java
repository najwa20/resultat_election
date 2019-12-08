package com.aziz_najwa_dsi32_g1.resultat_election;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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
public class list_choix extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    ListView list;
    datahelper db;
    Button btn;
    Intent in;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_choix);
        btn = findViewById(R.id.btn_aj_choix);
        toolbar = findViewById(R.id.toolbar);

        this.configureDrawerLayout();

        this.configureNavigationView();


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

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int len = list.getCount();
                int rep = -1;
                CheckBox c1;
                for (int i = 0; i < len; i++) {
                    View v1 = list.getChildAt(i);
                    c1 = v1.findViewById(R.id.checkBox);
                    if (c1.isChecked()) {
                        rep = i;
                    }
                }
                if (rep == -1) {
                    Toast.makeText(getApplicationContext(), "choice your choise", Toast.LENGTH_LONG).show();
                } else {
                    in = getIntent();
                    String id = in.getStringExtra("id");
                    SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
                    String login = pref.getString("login", null);
                    db.send(id, String.valueOf(rep), login);
                    Intent i = new Intent(getApplicationContext(), fin_elec.class);
                    i.putExtra("id", id);
                    startActivity(i);
                }
            }
        });
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

    public void check(View view) {
        CheckBox c = view.findViewById(R.id.checkBox);
        CheckBox c1;
        if (c.isChecked()) {
            int len = list.getCount();
            for (int i = 0; i < len; i++) {
                View v1 = list.getChildAt(i);
                c1 = v1.findViewById(R.id.checkBox);
                c1.setChecked(false);
            }
            c.setChecked(true);
        } else {
            int len = list.getCount();
            for (int i = 0; i < len; i++) {
                View v1 = list.getChildAt(i);
                c1 = v1.findViewById(R.id.checkBox);
                c1.setChecked(false);
            }
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
                Intent intent2 = new Intent(this, login.class);
                this.startActivity(intent2);
                break;
        }

        this.drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }
}
