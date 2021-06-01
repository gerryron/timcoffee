package com.example.timcoffee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.TextView;

public class checkout extends AppCompatActivity {
    EditText ecatatan, ebooking;
    private TextView btn_buy;
    private Button btn_pay;
    private Button btn_cancle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        //inissialisasi
        ecatatan = (EditText) findViewById(R.id.catatan);
        ebooking = (EditText) findViewById(R.id.booking);

        //button forward buy
        btn_buy = (TextView)findViewById(R.id.info_bayar);

        //button act pay
        btn_pay = (Button) findViewById(R.id.bt_pay);

        btn_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(checkout.this, order_success.class);
                startActivity(intent);
            }
        });

        //button act cancel
        btn_cancle = (Button) findViewById(R.id.bt_cancel);

        btn_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(checkout.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public void onCheckboxClicked(View view) {
        String inputcatatan = String.valueOf(ecatatan.getText().toString());
        String inputbooking = String.valueOf(ebooking.getText().toString());
    }
}