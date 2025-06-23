package com.s23010467.easymarketapp;

import static com.s23010467.easymarketapp.R.id.registerInputLastName;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class dashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dashboard);

        // Announsment Bar Create..

        TextSwitcher textSwitcher = findViewById(R.id.textSwitcher);
        ImageView closeIcon = findViewById(R.id.closeIcon);
        RelativeLayout bar = findViewById(R.id.announcementBar);

// Define announcement messages
        String[] messages = {
                "Fresh vegetables available at market today!",
                "Don't miss weekend discounts on fruits.",
                "New local markets added nearby!",
                "Check out your cart for the best offers."
        };

// Set up text switcher
        textSwitcher.setFactory(() -> {
            TextView textView = new TextView(dashboard.this);
            textView.setTextColor(Color.BLACK);
            textView.setTextSize(24);
            textView.setGravity(Gravity.CENTER);
            return textView;
        });

// Set animations (optional)
        textSwitcher.setInAnimation(this, android.R.anim.fade_in);
        textSwitcher.setOutAnimation(this, android.R.anim.fade_out);

// Auto-switch messages every 3 seconds
        Handler handler = new Handler();
        final int[] index = {0};

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                textSwitcher.setText(messages[index[0]]);
                index[0] = (index[0] + 1) % messages.length;
                handler.postDelayed(this, 3000); // 3 seconds
            }
        };
        handler.post(runnable);

// Close button functionality
        closeIcon.setOnClickListener(v -> {
            bar.setVisibility(View.GONE);
            handler.removeCallbacks(runnable); // stop rotating
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }
}