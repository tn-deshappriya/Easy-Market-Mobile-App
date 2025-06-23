package com.s23010467.easymarketapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity {

    EditText email, password;
    Button loginBtn,registerBtn;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.loginInputEmail);
        password = findViewById(R.id.loginInputPassword);
        loginBtn = findViewById(R.id.loginSignInBtn);
        registerBtn =findViewById(R.id.loginRegisterBtn);
        mAuth = FirebaseAuth.getInstance();

        // Move this OUTSIDE the login button click
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        loginBtn.setOnClickListener(v -> {
            String emailText = email.getText().toString().trim();
            String passwordText = password.getText().toString().trim();

            if (TextUtils.isEmpty(emailText) || TextUtils.isEmpty(passwordText)) {
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
                return;
            }

            mAuth.signInWithEmailAndPassword(emailText, passwordText)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();

                            // Redirect to Dashboard instead of Register page
                            startActivity(new Intent(login.this,dashboard.class));
                            finish();

                        } else {
                            Toast.makeText(this, "Login Failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
        });
        registerBtn.setOnClickListener(v -> {
            Intent intent = new Intent(login.this, register.class);
            startActivity(intent);

        });

    }
}
