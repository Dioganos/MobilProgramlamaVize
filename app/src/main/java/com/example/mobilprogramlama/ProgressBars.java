package com.example.mobilprogramlama;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class ProgressBars extends AppCompatActivity {
    int minValue, maxValue;
    int count;
    private EditText countTxt;
    private EditText minTxt;
    private EditText maxTxt;
    private Button generateBtn;
    private Button returnButton;
    private LinearLayout progressLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_bars);

        countTxt = findViewById(R.id.editTextText);
        minTxt = findViewById(R.id.editTextText2);
        maxTxt = findViewById(R.id.editTextText3);
        generateBtn = findViewById(R.id.buttonProgress);
        progressLayout = findViewById(R.id.progressLayout);
        returnButton = findViewById(R.id.returnButton4);

        generateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                minValue = Integer.parseInt(minTxt.getText().toString());
                maxValue = Integer.parseInt(maxTxt.getText().toString());
                count = Integer.parseInt(countTxt.getText().toString());

                if(count <= 0)
                    Toast.makeText(getApplicationContext(), "Olusturulacak progressbar sayisi 0'dan buyuk olmali!!", Toast.LENGTH_SHORT).show();
                else if(maxValue <= minValue)
                    Toast.makeText(getApplicationContext(), "Maksimum deger minimum degerden buyuk olmali!", Toast.LENGTH_SHORT).show();
                else if((maxValue -minValue) < 2)
                    Toast.makeText(getApplicationContext(), "Minimum ve maksimum deger araligi minimum 2 olmali!", Toast.LENGTH_SHORT).show();
                else
                    generateProgressBars(minValue, maxValue, count);
            }
        });
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ana sayfaya dönüş
                Intent intent = new Intent(ProgressBars.this, mainpage.class);
                startActivity(intent);
                finish(); // Şuanki aktiviteyi kapat
            }
        });
    }

    void generateProgressBars(int minValue, int maxValue, int count) {
        progressLayout.removeAllViews();

        Random r = new Random();

        for (int i = 0; i < count; i++) {
            int localMin = 0, localMax = 0;

            do {
                localMin = r.nextInt(maxValue - minValue + 1) + minValue;
                localMax = r.nextInt(maxValue - minValue + 1) + minValue;
            } while (localMax <= localMin);

            int randomValue = r.nextInt(localMax - localMin + 1) + localMin;
            float percent = ((float) (randomValue - localMin) / (localMax - localMin)) * 100;

            View rowView = getLayoutInflater().inflate(R.layout.activity_progress_bars_template, null);

            TextView leftText = rowView.findViewById(R.id.leftText);
            TextView rightText = rowView.findViewById(R.id.rightText);
            TextView percentText = rowView.findViewById(R.id.percent);
            ProgressBar progressBar = rowView.findViewById(R.id.progressBar);

            leftText.setText(String.valueOf(localMin));
            rightText.setText(String.valueOf(localMax));
            percentText.setText("RandomValue: " + randomValue + " : " + (int) percent + "%");
            progressBar.setProgress((int) percent);

            progressLayout.addView(rowView);
        }
    }
}
