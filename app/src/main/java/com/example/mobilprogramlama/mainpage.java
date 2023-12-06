package com.example.mobilprogramlama;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import androidx.core.view.WindowCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.mobilprogramlama.databinding.ActivityMainpageBinding;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
public class mainpage extends AppCompatActivity {
    private Button buttonActivity1;
    private Button buttonActivity2;
    private Button buttonActivity3;
    private TextView textViewUsername;
    private TextView textViewSchoolNumber;

    public void onButtonClick(View view) {
        Intent intent = new Intent(this, converter.class);
        startActivity(intent);
    }
    public void onButtonClick2(View view) {
        Intent intent = new Intent(this, ProgressBars.class);
        startActivity(intent);
    }
    public void onButtonClick3(View view) {
        Intent intent = new Intent(this, Messager.class);
        startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpage);
        buttonActivity1 = findViewById(R.id.buttonActivity1);
        buttonActivity2 = findViewById(R.id.buttonActivity2);
        buttonActivity3 = findViewById(R.id.buttonActivity3);
        textViewUsername = findViewById(R.id.textViewUsername);
        textViewSchoolNumber = findViewById(R.id.textViewSchoolNumber);
        // Textview'lerin ekranın dışına yerleştirilmesi
        textViewUsername.setTranslationX(-1000f);
        textViewSchoolNumber.setTranslationX(1000f);

        // Textview'lerin ekranın içine gelmesini sağlayan animasyonlar
        ObjectAnimator animTextViewUsername = ObjectAnimator.ofFloat(textViewUsername, "translationX", 0f);
        animTextViewUsername.setDuration(1000);
        animTextViewUsername.start();

        ObjectAnimator animTextViewSchoolNumber = ObjectAnimator.ofFloat(textViewSchoolNumber, "translationX", 0f);
        animTextViewSchoolNumber.setDuration(1000);
        animTextViewSchoolNumber.start();

        // Butonların ekranın dışına yerleştirilmesi
        buttonActivity1.setTranslationX(-1000f);
        buttonActivity2.setTranslationX(1000f);
        buttonActivity3.setTranslationX(-1000f);

        // Butonların ekranın içine gelmesini sağlayan animasyonlar
        ObjectAnimator animButton1 = ObjectAnimator.ofFloat(buttonActivity1, "translationX", 0f);
        animButton1.setDuration(1000);
        animButton1.start();

        ObjectAnimator animButton2 = ObjectAnimator.ofFloat(buttonActivity2, "translationX", 0f);
        animButton2.setDuration(1000);
        animButton2.start();

        ObjectAnimator animButton3 = ObjectAnimator.ofFloat(buttonActivity3, "translationX", 0f);
        animButton3.setDuration(1000);
        animButton3.start();

        buttonActivity1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClick(v);
            }
        });
        buttonActivity2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClick2(v);
            }
        });
        buttonActivity3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClick3(v);
            }
        });
    }
}