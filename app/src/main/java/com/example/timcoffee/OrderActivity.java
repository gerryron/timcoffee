package com.example.timcoffee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.timcoffee.fragment.OrderFragment;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class OrderActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private MaterialToolbar materialToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        initViews();
        initBottomNavView();
        setSupportActionBar(materialToolbar);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, new OrderFragment());
        transaction.commit();

    }

    private void initBottomNavView() {
        bottomNavigationView.setSelectedItemId(R.id.btmNav_Cart);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.btmNav_Home:
                        Intent homeIntent = new Intent(OrderActivity.this, MainActivity.class);
                        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(homeIntent);
                        break;
                    case R.id.btmNav_Cart:
                        Intent cartIntent = new Intent(OrderActivity.this, CartActivity.class);
                        cartIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(cartIntent);
                        break;
                }

                return false;
            }
        });
    }

    private void initViews() {
        bottomNavigationView = findViewById(R.id.bottomNavView);
        materialToolbar = findViewById(R.id.toolbar);
    }
}