package com.example.myapplication.login;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.example.myapplication.ChatsAPI;
import com.example.myapplication.LoginAPI;
import com.example.myapplication.R;
import com.example.myapplication.contacts.ContactActivity;
import com.example.myapplication.entities.UserLogin;


public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        LoginAPI loginAPI = new LoginAPI();
        ChatsAPI chatsAPI = new ChatsAPI();

        Button btn = findViewById(R.id.buttonLogin);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView username = findViewById(R.id.usernameInput);
                TextView password = findViewById(R.id.passwordInput);
                loginAPI.post(new UserLogin(username.getText().toString(),password.getText().toString()));

            }
        });

        loginAPI.getResponseLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Intent intent = new Intent(LoginActivity.this, ContactActivity.class);
                chatsAPI.get("Bearer" +" " + s);
               // intent.putExtra("token",s);
               // startActivity(intent);
            }
        });
    }
}
