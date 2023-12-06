package com.example.mobilprogramlama;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.provider.ContactsContract;
import android.telephony.SmsManager;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Locale;
import java.util.Random;

public class Messager extends AppCompatActivity {
    private ArrayList<String> phoneNumbers = new ArrayList<>();
    private TextView eventTextView;
    private Button sendButton;
    private Button returnButton;
    Button createButton;
    public int day;
    Calendar calendar;
    public int month;
    public String monthText;
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;
    private static final int PERMISSIONS_REQUEST_SEND_SMS = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, PERMISSIONS_REQUEST_SEND_SMS);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messager);

        calendar = Calendar.getInstance();
        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH) + 1; // Ay indeksi 0'dan başlar, bu yüzden +1 eklenir
        eventTextView = findViewById(R.id.editTextText4);
        createButton = findViewById(R.id.generateButton);
        returnButton = findViewById(R.id.returnButton3);
        sendButton = findViewById(R.id.sendButton);

        Spinner contactList = findViewById(R.id.contactList);
        loadContacts(); // Kişileri yükleme
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, phoneNumbers);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        contactList.setAdapter(adapter);

        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ana sayfaya dönüş
                Intent intent = new Intent(Messager.this, mainpage.class);
                startActivity(intent);
                finish(); // Şuanki aktiviteyi kapat
            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedContactPosition = contactList.getSelectedItemPosition();
                if (selectedContactPosition != Spinner.INVALID_POSITION) {
                    String selectedPhoneNumber = phoneNumbers.get(selectedContactPosition);
                    String message = eventTextView.getText().toString();
                    sendSMS("+90" + selectedPhoneNumber.substring(selectedPhoneNumber.length()-10,selectedPhoneNumber.length()), message);
                } else {
                    Toast.makeText(getApplicationContext(), "No contact selected!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateMessage();
            }
        });
    }
    public void generateMessage() {
        String url = "https://en.wikipedia.org/wiki/" + getMonthName(month) + "_" + day;
        new RetrieveEvents().execute(url);
    }
    private void loadContacts() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACTS);
            return;
        }

        Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            ArrayList<String> tempPhoneNumbers = new ArrayList<>(); // Geçici liste
            do {
                int contactNameIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                int phoneNumberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);

                if (contactNameIndex != -1 && phoneNumberIndex != -1) {
                    String contactName = cursor.getString(contactNameIndex);
                    String phoneNumber = cursor.getString(phoneNumberIndex);

                    // Aynı kişi zaten listede yoksa ekle
                    if (!tempPhoneNumbers.contains(contactName + ": " + phoneNumber)) {
                        tempPhoneNumbers.add(contactName + ": " + phoneNumber);
                    }
                }
            } while (cursor.moveToNext());
            cursor.close();

            // Alfabetik sıralama
            Collections.sort(tempPhoneNumbers);

            // Temizlenmiş ve sıralanmış listeyi orijinal listeye ata
            phoneNumbers.clear();
            phoneNumbers.addAll(tempPhoneNumbers);
        }
    }

    private void sendSMS(String phoneNumber, String message) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);
            Toast.makeText(getApplicationContext(), "Message sent successfully!", Toast.LENGTH_SHORT).show();
        } catch (IllegalArgumentException e) {
            Toast.makeText(getApplicationContext(), "Invalid destination number!", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        } catch (SecurityException e) {
            Toast.makeText(getApplicationContext(), "Permission denied to send SMS!", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Failed to send message!", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private class RetrieveEvents extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            try {
                // Wikipedia sayfasını çek
                Document doc = Jsoup.connect(urls[0]).get();
                // "ul" etiketleri içindeki metinleri al
                Elements elements = doc.select("div.mw-content-ltr ul"); // div içindeki ul elementlerini al
                // Her bir elementi kontrol ederek reflist sınıfının öncesini al

                int indexFinder = 0;
                for (Element element : elements) {
                    if (element.nextElementSibling() != null && (element.previousElementSiblings().hasClass("reflist") || element.previousElementSiblings().hasClass("references"))) {
                        break;
                    }
                    indexFinder++;
                }
                Random rand = new Random();
                int randomIndex = rand.nextInt(elements.size() - (elements.size() - indexFinder) - 1);

                Element eventList = elements.get(randomIndex);
                StringBuilder eventsHTML = new StringBuilder();

                // "ul" içerisindeki her bir öğeyi döngüyle işle
                for (Element event : eventList.getElementsByTag("li")) {
                    eventsHTML.append(event.text()).append("\n\n"); // Sadece metin halini alıyoruz
                }
                return eventsHTML.toString();
            } catch (IOException e) {
                e.printStackTrace();
                return "Error: " + e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            // Sonucu TextView'e yerleştir
            String[] eventsArray = result.split("\n\n"); // Her bir olayı bir diziye alıyoruz

            // Rastgele bir olayı seç
            Random rand = new Random();
            int randomEventIndex = rand.nextInt(eventsArray.length);

            // Seçilen olayı EditText'e yerleştir
            String textToAssign = eventsArray[randomEventIndex].contains("–") ?
                    eventsArray[randomEventIndex] :
                    "Holiday of " + eventsArray[randomEventIndex];

            eventTextView.setText(day + " " + getMonthName(month) + ": " + textToAssign);
        }
    }

    // Ay numarasını ayın adına çevirme
    private String getMonthName(int month) {
        String[] monthNames = new java.text.DateFormatSymbols(Locale.ENGLISH).getMonths();
        return monthNames[month - 1];
    }
}
