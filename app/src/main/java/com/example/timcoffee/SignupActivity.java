package com.example.timcoffee;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.timcoffee.api.APIInterface;
import com.example.timcoffee.api.ApiClient;
import com.example.timcoffee.model.RegisterRequestBody;
import com.example.timcoffee.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {

    private RelativeLayout btn_login;
    private Button btSignup;
    private TextView tvLogin;
    private EditText etName, etPhoneNumber, etEmail, etPassword, etConfirmPassword;
    private String nama, noHp, email, password, confirmPassword;
    private APIInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        btSignup = findViewById(R.id.bt_Signup);
        tvLogin = findViewById(R.id.tv_SignupLogin);
        etName = findViewById(R.id.et_RegisterName);
        etPhoneNumber = findViewById(R.id.et_RegisterPhoneNumber);
        etEmail = findViewById(R.id.et_RegisterEmail);
        etPassword = findViewById(R.id.et_RegisterPassword);
        etConfirmPassword = findViewById(R.id.et_RegisterConfirmPassword);

        // Action Tombol Signup
        btSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nama = etName.getText().toString();
                noHp = etPhoneNumber.getText().toString();
                email = etEmail.getText().toString();
                password = etPassword.getText().toString();
                confirmPassword = etConfirmPassword.getText().toString();
                register(nama, noHp, email, password, confirmPassword);
            }
        });

        // Action Tombol Kembali ke halaman Login (walau pake TextView xixixi)
        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    // Method Register
    private void register(String nama, String noHp, String email, String password, String confirmPassword) {
        if (!password.equals(confirmPassword)){
            Toast.makeText(SignupActivity.this, "Password tidak sesuai", Toast.LENGTH_SHORT).show();
            return;
        }

        if ((noHp == null || noHp.equals("")) || (email == null || email.equals(""))){
            Toast.makeText(SignupActivity.this, "noHp/ email tidak boleh kosong", Toast.LENGTH_SHORT).show();
            return;
        }

        ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this);
        progressDialog.setMessage("Tunggu Sebentar ...");
        progressDialog.show();

        RegisterRequestBody requestBody = new RegisterRequestBody(nama, noHp, email, password, null);
        apiInterface = ApiClient.getRetrofit().create(APIInterface.class);
        Call<User> loginCall = apiInterface.registerResponse(requestBody);
        loginCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.body() != null && response.isSuccessful()) {
                    progressDialog.dismiss();
                    Toast.makeText(SignupActivity.this, "Berhasil membuat akun !", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else if (response.code() == 409) {
                    progressDialog.dismiss();
                    Toast.makeText(SignupActivity.this, "Nomer Hp/Email sudah terdaftar", Toast.LENGTH_SHORT).show();
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(SignupActivity.this, "Gagal membuat akun !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(SignupActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}