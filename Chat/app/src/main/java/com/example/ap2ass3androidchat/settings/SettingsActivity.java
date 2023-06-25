package com.example.ap2ass3androidchat.settings;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.ap2ass3androidchat.R;
import com.example.ap2ass3androidchat.api.RegisterAPI;
import com.example.ap2ass3androidchat.singleton.SingletonLogout;
import com.example.ap2ass3androidchat.singleton.SingletonNotification;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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


        sharedPref = getSharedPreferences("prefs", Context.MODE_PRIVATE);

        EditText URL = findViewById(R.id.urltext);
        Button saveURL = findViewById(R.id.saveurl);
        URL.setText(sharedPref.getString("URL", "http://10.0.2.2:5000"));
        Button logoutButton = findViewById(R.id.logoutbutton);
        ShapeableImageView img = findViewById(R.id.myprofileimage);
        TextView name = findViewById(R.id.myname);
        TextView myProfile = findViewById(R.id.myprofile);
        if (token.equals("NO_TOKEN") && username.equals("NO_USERNAME")) {
            img.setVisibility(View.GONE);
            name.setVisibility(View.GONE);
            logoutButton.setVisibility(View.GONE);
            myProfile.setVisibility(View.GONE);
        } else {
/*            img.setVisibility(View.);
            name.setVisibility(View.GONE);*/
            registerAPI.getUser(token, username, img, name);
        }
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
 /*               SharedPreferences.Editor editor = sharedPref.edit();
                editor.putBoolean("nightSwitchState", isChecked);
                editor.apply();*/
            }
        });

        saveURL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Pattern pattern = Pattern.compile("^(http|https)://");
                Matcher matcher = pattern.matcher(URL.getText().toString());

                if (!matcher.find()) {
                    Toast.makeText(getApplicationContext(),
                            "Invalid URL, has to start with http / https",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("URL", URL.getText().toString());
                URL.setText(URL.getText().toString());
                editor.apply();
                URL.clearFocus();
                if (!(token.equals("NO_TOKEN") && username.equals("NO_USERNAME"))) {
                    SingletonLogout.setState(1);
                    SingletonNotification.toggleState();
                }
                finish();
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SingletonLogout.setState(1);
                SingletonNotification.toggleState();
                finish();
            }
        });

    }


}
