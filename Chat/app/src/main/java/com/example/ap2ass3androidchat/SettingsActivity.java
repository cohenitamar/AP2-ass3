package com.example.ap2ass3androidchat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.android.material.imageview.ShapeableImageView;

public class SettingsActivity extends AppCompatActivity {
    private String username;
    private Switch nightSwitch;
    private SharedPreferences sharedPref;
    String token;

    RegisterAPI registerAPI;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);


        Intent activityIntent = getIntent();

        if (activityIntent != null) {
            token = activityIntent.getStringExtra("token");
            username = activityIntent.getStringExtra("username");
        }
        registerAPI = new RegisterAPI();

        ShapeableImageView img = findViewById(R.id.myprofileimage);
        TextView name = findViewById(R.id.myname);

        registerAPI.getUser(token, username, img, name);

        nightSwitch = findViewById(R.id.nightswitch);
        sharedPref = getPreferences(Context.MODE_PRIVATE);

        int currentNightMode = AppCompatDelegate.getDefaultNightMode();

        if (currentNightMode == AppCompatDelegate.MODE_NIGHT_YES) {
            nightSwitch.setChecked(true);
        } else {
            nightSwitch.setChecked(false);
        }

        // Set the listener to save the state when the button is toggled

        nightSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putBoolean("nightSwitchState", isChecked);
                editor.apply();
            }
        });

        Button logoutButton = findViewById(R.id.logoutbutton);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SingletonLogout.setState(1);
                SingletonNotification.toggleState();
                finish();
            }
        }) ;

    }


}
