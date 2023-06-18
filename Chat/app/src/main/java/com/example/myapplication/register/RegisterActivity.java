package com.example.myapplication.register;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.myapplication.R;
import com.example.myapplication.entities.RegisterUser;
import com.example.myapplication.login.LoginActivity;
import com.example.myapplication.RegisterAPI;

import java.io.ByteArrayOutputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterActivity extends AppCompatActivity {
    RegisterAPI registerAPI = new RegisterAPI();
    private RegisterActivity activity;
    private EditText firstNameInput;
    private EditText lastNameInput;
    private EditText usernameInput;
    private EditText passwordInput;
    private CircleImageView profilePic;
    private static final int REQUEST_CODE = 1;

    private EditText confirmPasswordInput;

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
        activity = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        firstNameInput = findViewById(R.id.firstNameInput);
        lastNameInput = findViewById(R.id.lastNameInput);
        Button nextButton = findViewById(R.id.nextButton);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: EDIT TEXT OR TEXTVIEW

                firstNameInput = findViewById(R.id.firstNameInput);
                lastNameInput = findViewById(R.id.lastNameInput);
                usernameInput = findViewById(R.id.usernameInput);
                passwordInput = findViewById(R.id.passwordInput);
                confirmPasswordInput = findViewById(R.id.confirmPasswordInput);

                boolean isFirstNameValid = firstNameInput.length() > 0;
                boolean isLastNameValid = lastNameInput.length() > 0;

                //if length is 0 "or less"
                if(!isFirstNameValid && !isLastNameValid){
                    Toast.makeText(activity, "Please enter your first and last name", Toast.LENGTH_SHORT).show();
                } else {
                    if (!isFirstNameValid) {
                        Toast.makeText(activity, "Please enter your first name", Toast.LENGTH_SHORT).show();
                    } else if (!isLastNameValid) {
                        Toast.makeText(activity, "Please enter your last name", Toast.LENGTH_SHORT).show();
                    }
                }

                String displayName = firstNameInput.getText().toString() + " " + lastNameInput.getText().toString();

                //convert image to string
                //get drawable
                Drawable drawable = profilePic.getDrawable();
                //drawable to bitmap
                Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
                //bitmap to byte array
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                //byte array to string
                String encodedProfilePic = Base64.encodeToString(byteArray, Base64.DEFAULT);

                registerAPI.post(new RegisterUser(usernameInput.getText().toString(), passwordInput.getText().toString(), displayName, encodedProfilePic));

            }
        });



        //TODO: IS NECESSARY?

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

         profilePic = findViewById(R.id.profilePic);

         profilePic.setOnClickListener(new View.OnClickListener(){
             @Override
             public void onClick(View v){
                 //new intent opens to enable the user to choose a picture
                 Intent intent = new Intent(Intent.ACTION_PICK,
                         android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                 //the requested is used later in the
                 startActivityForResult(intent, REQUEST_CODE);
             }
         });


         registerAPI.getResponseLiveData().observe(this, new Observer<String>(){
            @Override
            public void onChanged(String s) {
                if (s.equals("true")){
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(activity, "Could not create user", Toast.LENGTH_SHORT).show();
                }
            }
        });
        }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            profilePic.setImageURI(selectedImage);
        }
    }

}
