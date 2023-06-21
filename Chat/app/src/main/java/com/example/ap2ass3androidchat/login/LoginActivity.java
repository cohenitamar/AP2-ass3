package com.example.ap2ass3androidchat.login;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.example.ap2ass3androidchat.LoginAPI;
import com.example.ap2ass3androidchat.R;

import com.example.ap2ass3androidchat.SettingsActivity;
import com.example.ap2ass3androidchat.SingletonLogout;
import com.example.ap2ass3androidchat.SingletonNotification;

import com.example.ap2ass3androidchat.SingletonDatabase;
import com.example.ap2ass3androidchat.contacts.ContactActivity;
import com.example.ap2ass3androidchat.entities.UserLogin;
import com.example.ap2ass3androidchat.register.RegisterActivity;
import com.google.firebase.iid.FirebaseInstanceId;


public class LoginActivity extends AppCompatActivity {

    String usernameStr;
    String phoneToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        LoginAPI loginAPI = new LoginAPI();

        Button btn = findViewById(R.id.buttonLogin);

        SingletonNotification.getLogoutInstance();
        FirebaseInstanceId.getInstance()
                .getInstanceId()
                .addOnSuccessListener(LoginActivity.this, instanceIdResult -> {
                    phoneToken = instanceIdResult.getToken();
                });

        Button settings = findViewById(R.id.btn_settings);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SettingsActivity.class);
                intent.putExtra("token", "NO_TOKEN");
                intent.putExtra("username", "NO_USERNAME");
                startActivity(intent);
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView username = findViewById(R.id.usernameInput);
                TextView password = findViewById(R.id.passwordInput);
                usernameStr = username.getText().toString();
                loginAPI.post(new UserLogin(username.getText().toString(),
                        password.getText().toString()), phoneToken);
                SingletonDatabase
                        .getMessageInstance(getApplicationContext())
                        .messageDao().deleteAll();
                SingletonDatabase
                        .getContactInstance(getApplicationContext())
                        .contactDao().deleteAll();
            }
        });

        Button createAccount = findViewById(R.id.createAccount);
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });


        loginAPI.getResponseLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s.equals("Not valid user/password.")) {
                    Log.e("tall", "check");
                } else {
                    Intent intent = new Intent(LoginActivity.this, ContactActivity.class);
                    intent.putExtra("token", "Bearer " + s);
                    intent.putExtra("username", usernameStr);
                    startActivity(intent);
                    SingletonLogout.setState(0);
                    SingletonNotification.toggleState();
                }
            }
        });
    }


}
