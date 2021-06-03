package com.example.timcoffee;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.timcoffee.api.APIInterface;
import com.example.timcoffee.api.ApiClient;
import com.example.timcoffee.model.LoginRequestBody;
import com.example.timcoffee.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private APIInterface apiInterface;
    private Button btnLogin;
    private TextView tvSignup;
    private EditText etLoginUsername, etLoginPassword;
    private String username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = findViewById(R.id.bt_Login);
        tvSignup = findViewById(R.id.tv_LoginSignup);
        etLoginUsername = findViewById(R.id.et_LoginUsername);
        etLoginPassword = findViewById(R.id.et_LoginPassword);

        // Action Tombol Login
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = etLoginUsername.getText().toString();
                password = etLoginPassword.getText().toString();
                login(username, password);
            }
        });

        // Action Tombol Daftar (walau pake TextView xixixi)\
        tvSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
    }

    // method Login
    private void login(String username, String password) {
        LoginRequestBody loginRequestBody = new LoginRequestBody(username, password);
        ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage("Tunggu Sebentar ...");
        progressDialog.show();

        apiInterface = ApiClient.getRetrofit().create(APIInterface.class);
        Call<User> loginCall = apiInterface.loginResponse(loginRequestBody);
        loginCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.body() != null && response.isSuccessful()) {
                    progressDialog.dismiss();
                    Toast.makeText(LoginActivity.this, "Berhasil Login : " + response.body().getName(), Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                } else if (response.code() == 406) {
                    progressDialog.dismiss();
                    Toast.makeText(LoginActivity.this, "Password Salah", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 404) {
                    progressDialog.dismiss();
                    Toast.makeText(LoginActivity.this, "Nomer Handphone/Email tidak terdaftar", Toast.LENGTH_SHORT).show();
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(LoginActivity.this, "Gagal Login", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(LoginActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}