package com.s23010467.easymarketapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;

public class register extends AppCompatActivity {
    EditText firstName,lastName,email,password,comfirmPassword;
    Button RegisterBtnRegister;
    RadioButton userRadio,adminRadio;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        // Input value extract in variables
        firstName = findViewById(R.id.registerInputFirstName);
        lastName = findViewById(R.id.registerInputLastName);
        email = findViewById(R.id.registerInputMailAddress);
        password = findViewById(R.id.registerInputNewPassword);
        comfirmPassword = findViewById(R.id.registerInputReEntryPassword);
        RegisterBtnRegister = findViewById(R.id.registerBtnRegister);
        userRadio = findViewById(R.id.registerRadioBtnUser);
        adminRadio = findViewById(R.id.registerRadioBtnAdmin);

        mAuth = FirebaseAuth.getInstance();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        RegisterBtnRegister.setOnClickListener(v -> {
            String userEmail = email.getText().toString();
            String userPassword = password.getText().toString();
            String userConfirmPassword = comfirmPassword.getText().toString();

            if (TextUtils.isEmpty(userEmail) || TextUtils.isEmpty(userPassword)) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!userPassword.equals(userConfirmPassword)) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                return;
            }

            mAuth.createUserWithEmailAndPassword(userEmail, userPassword)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(register.this, dashboard.class));
                            finish();
                        } else {
                            Toast.makeText(this, "Registration Failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
        });

        // Create function to navigate Login Page trough Signin text ...

        TextView goSignin = findViewById(R.id.registerTextSignIn);

        goSignin.setOnClickListener(v -> {
            Intent intent = new Intent(register.this, login.class);
            startActivity(intent);
            finish();
        });
    }
}