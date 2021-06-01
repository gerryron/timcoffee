package com.example.timcoffee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class order_success extends AppCompatActivity {

    private TextView btn_pay;
    private Button btn_back_menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_success);

        //button forward pay
        btn_pay = (TextView)findViewById(R.id.order_success);

        //button act back to menu
        btn_back_menu = (Button) findViewById(R.id.bt_order_success);

        btn_back_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(order_success.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}