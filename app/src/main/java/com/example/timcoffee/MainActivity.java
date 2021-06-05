package com.example.timcoffee;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.timcoffee.fragment.MainFragment;
import com.example.timcoffee.util.SessionManager;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private SessionManager sessionManager;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private MaterialToolbar materialToolbar;
    private TextView tvNavNama, tvNavEmail;
    private String nama, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Cek Session
        sessionManager = new SessionManager(MainActivity.this);
        if (!sessionManager.isLoggedin()) {
            moveToLogin();
        }

        initViews();
        setSupportActionBar(materialToolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, materialToolbar,
                R.string.drawer_start, R.string.drawer_close);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item_Home:
                        Toast.makeText(MainActivity.this, "Home Selected", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.item_cart:
                        Toast.makeText(MainActivity.this, "Cart Selected", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.item_order:
                        Toast.makeText(MainActivity.this, "Order Selected", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.item_aboutUs:
                        Toast.makeText(MainActivity.this, "About us Selected", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.item_logout:
                        sessionManager.logoutSession();
                        moveToLogin();
                        finish();
                        break;
                }
                return false;
            }
        });

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, new MainFragment());
        transaction.commit();

        // Set user information
        nama = sessionManager.getUserDetail().get(SessionManager.NAME);
        if (nama.split(" ").length > 1){
            nama = nama.split(" ")[1];
        }
        email = sessionManager.getUserDetail().get(SessionManager.EMAIL);
        tvNavNama.setText(nama.substring(0,1).toUpperCase() + nama.substring(1).toLowerCase());
        tvNavEmail.setText(email);

    }

    private void initViews() {
        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.navigationView);
        materialToolbar = findViewById(R.id.toolbar);

        View headerView = navigationView.getHeaderView(0);
        tvNavNama = headerView.findViewById(R.id.tv_headerName);
        tvNavEmail = headerView.findViewById(R.id.tv_headerEmail);
    }

    private void moveToLogin() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
        finish();
    }
}