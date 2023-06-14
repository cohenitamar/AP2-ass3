package com.example.myapplication.register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;
import com.google.android.material.textfield.TextInputLayout;

public class RegisterActivity extends AppCompatActivity {

    private EditText firstNameInput;
    private EditText lastNameInput;

    private void validateInput() {
        String firstName = firstNameInput.getText().toString().trim();
        String lastName = lastNameInput.getText().toString().trim();

        if (firstName.length() == 0 && lastName.length() == 0) {
            firstNameInput.setError("Please fill first name and last name");
        }
        if (firstName.length() == 0) {
            firstNameInput.setError("Please fill first name");
        } else {
            firstNameInput.setError(null);
        }

        if (lastName.length() == 0) {
            lastNameInput.setError("Please fill last name");
        } else {
            lastNameInput.setError(null);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        firstNameInput = findViewById(R.id.firstNameInput);
        lastNameInput = findViewById(R.id.editTextText2);
        Button nextButton = findViewById(R.id.nextButton);

        firstNameInput.addTextChangedListener(new TextWatcher() {
            //nothing needs to happen
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                validateInput();
            }
        });

        lastNameInput.addTextChangedListener(new TextWatcher() {
            //nothing needs to happen
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                validateInput();
            }
        });

        //behavior when clicks the next button
        nextButton.setOnClickListener(view -> {

            boolean isFirstNameValid = firstNameInput.length() > 0;
            boolean isLastNameValid = lastNameInput.length() > 0;

            //if length is 0 "or less"
            if(!isFirstNameValid && !isLastNameValid){
                Toast.makeText(this, "Please enter your first and last name", Toast.LENGTH_SHORT).show();
            } else {
                if (!isFirstNameValid) {
                    Toast.makeText(this, "Please enter your first name", Toast.LENGTH_SHORT).show();
                } else if (!isLastNameValid) {
                    Toast.makeText(this, "Please enter your last name", Toast.LENGTH_SHORT).show();
                }
            }

            //if they are both valid, moves to next page
            if (isFirstNameValid && isLastNameValid) {
                Intent intent = new Intent(RegisterActivity.this, PasswordActivity.class);
                startActivity(intent);            }
        });
    }

}
