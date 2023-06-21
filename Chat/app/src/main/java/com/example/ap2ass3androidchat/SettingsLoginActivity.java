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

public class SettingsLoginActivity extends AppCompatActivity {

        private Switch nightSwitch;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.settings_login_activity);

            Intent activityIntent = getIntent();

            nightSwitch = findViewById(R.id.nightswitch);

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
//                    SharedPreferences.Editor editor = sharedPref.edit();
//                    editor.putBoolean("nightSwitchState", isChecked);
//                    editor.apply();
                }
            });

        }


    }
