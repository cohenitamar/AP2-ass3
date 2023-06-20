package com.example.myapplication.register;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    private int validateInput() {
        int flag = 0;
        boolean isFirstNameValid = firstNameInput.length() > 0;
        boolean isLastNameValid = lastNameInput.length() > 0;

        //if length is 0 "or less"
        if(!isFirstNameValid && !isLastNameValid){
            Toast.makeText(activity, "Please enter your first and last name", Toast.LENGTH_SHORT).show();
            return 1;
        } else {
            if (!isFirstNameValid) {
                Toast.makeText(activity, "Please enter your first name", Toast.LENGTH_SHORT).show();
                return 1;
            } else if (!isLastNameValid) {
                Toast.makeText(activity, "Please enter your last name", Toast.LENGTH_SHORT).show();
                return 1;
            }
        }
        if(usernameInput.length() < 3){
            Toast.makeText(activity, "Username needs to be at least 3 letters long", Toast.LENGTH_SHORT).show();
            return 1;
        }
        if(!passwordInput.getText().toString().equals(confirmPasswordInput.getText().toString())){
            Toast.makeText(activity, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return 1;
        }

        if(passwordInput.length() < 8 ){
            Toast.makeText(activity, "Password needs to be at least 8 letters long", Toast.LENGTH_SHORT).show();
            return 1;
        }

        // Check for uppercase letter
        Pattern uppercasePattern = Pattern.compile("[A-Z]");
        Matcher uppercaseMatcher = uppercasePattern.matcher(passwordInput.getText().toString());
        boolean hasUppercase = uppercaseMatcher.find();
        if (!hasUppercase) {
            Toast.makeText(activity, "Password must contain uppercase letter", Toast.LENGTH_SHORT).show();
            return 1;
        }

        // Check for lowercase letter
        Pattern lowercasePattern = Pattern.compile("[a-z]");
        Matcher lowercaseMatcher = lowercasePattern.matcher(passwordInput.getText().toString());
        boolean hasLowercase = lowercaseMatcher.find();
        if (!hasLowercase) {
            Toast.makeText(activity, "Password must contain lowercase letter", Toast.LENGTH_SHORT).show();
            return 1;
        }

        // Check for digit
        Pattern digitPattern = Pattern.compile("[0-9]");
        Matcher digitMatcher = digitPattern.matcher(passwordInput.getText().toString());
        boolean hasDigit = digitMatcher.find();
        if (!hasDigit) {
            Toast.makeText(activity, "Password must contain a number", Toast.LENGTH_SHORT).show();
            return 1;
        }
        return 0;
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

                int isValidate = validateInput();

                if (isValidate == 0) {
                    String displayName = firstNameInput.getText().toString() + " " + lastNameInput.getText().toString();
                    //convert image to string
                    Drawable defaultDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.nopictureuser, null);
                    //get drawable
                    Drawable drawable = profilePic.getDrawable();
                    if (drawable != null && drawable.getConstantState() != null && defaultDrawable != null && defaultDrawable.getConstantState() != null) {
                        if (drawable.getConstantState().equals(defaultDrawable.getConstantState())) {
                            // The user has not inserted a picture
                            Toast.makeText(activity, "Please insert a picture", Toast.LENGTH_SHORT).show();
                        } else {
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
                    }
                }
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
                    Toast.makeText(activity, "User created successfully", Toast.LENGTH_SHORT).show();
                    RegisterActivity.this.finish();
                } else if (s.equals("User already exist.")) {
                    Toast.makeText(activity, "User already exists", Toast.LENGTH_SHORT).show();
                } else{
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
