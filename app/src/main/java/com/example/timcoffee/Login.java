package com.example.timcoffee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

public class Login extends AppCompatActivity {
    private Button btn_login;
    private Button btn_signup;
    private RelativeLayout signup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //button act login
        btn_login = (Button) findViewById(R.id.bt_login);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, MainActivity.class);
                startActivity(intent);
            }
        });

        //button act register
        btn_signup = (Button) findViewById(R.id.bt_signup);

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, signup.class);
                startActivity(intent);
            }
        });

        //btn forward signup
        signup = (RelativeLayout) findViewById(R.id.login);
    }
}