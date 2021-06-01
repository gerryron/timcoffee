package com.example.timcoffee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.timcoffee.adapter.MenuAdapter;
import com.example.timcoffee.model.MenuModel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button btn_buy;
    private TextView btn_back_menu;
    private TextView btn_cancle;
    private TextView btn_login;

    RecyclerView recyclerView;
    List<MenuModel> listMenu = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        recyclerView = findViewById(R.id.recyclerView);

        //membuat model data

        Drawable drawable = getResources().getDrawable(R.drawable.drink1);


        MenuModel menuModel1 = new MenuModel();
        menuModel1.setGambarMenu(drawable);
        menuModel1.setNamaMenu("Latte");
        menuModel1.setHargaMenu(new BigDecimal("20000"));

        for (int i = 0; i < 20 ; i++) {
            listMenu.add(menuModel1);
        }


        //membuat adapter
        recyclerView.setAdapter(new MenuAdapter(MainActivity.this, listMenu));

        //layout manager
        recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));

        //btn act buy
        btn_buy = (Button) findViewById(R.id.bt_buy);

        btn_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, checkout.class);
                startActivity(intent);
            }
        });

        //button forward pay
        btn_back_menu = (TextView)findViewById(R.id.txtbanner);

        //button forward pay
        btn_cancle = (TextView)findViewById(R.id.txtbanner);

        //button forward login
        btn_login = (TextView)findViewById(R.id.txtbanner);

        }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.optionmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu:
                Toast.makeText(this, "Menu Selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.home:
                Toast.makeText(this, "Home Selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.login:
                Toast.makeText(this, "Logout Selected", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}