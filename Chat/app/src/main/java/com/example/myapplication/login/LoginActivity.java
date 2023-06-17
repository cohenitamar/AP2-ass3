package com.example.myapplication.login;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.example.myapplication.LoginAPI;
import com.example.myapplication.R;
import com.example.myapplication.contacts.ContactActivity;
import com.example.myapplication.entities.UserLogin;


public class LoginActivity extends AppCompatActivity {

    String usernameStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        LoginAPI loginAPI = new LoginAPI();

        Button btn = findViewById(R.id.buttonLogin);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView username = findViewById(R.id.usernameInput);
                TextView password = findViewById(R.id.passwordInput);
                usernameStr = username.getText().toString();
                loginAPI.post(new UserLogin(username.getText().toString(),password.getText().toString()));


            }
        });

        loginAPI.getResponseLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {

                if (s.equals("Not valid user/password.")){
                    Log.e("tall","check");
                }
                else{
                    Intent intent = new Intent(LoginActivity.this, ContactActivity.class);
                    intent.putExtra("token","Bearer " + s);
                    intent.putExtra("username",usernameStr);
                    startActivity(intent);
                }

            }
        });
    }
}
