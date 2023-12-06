package com.example.mobilprogramlama;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.RadioButton;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.view.View; // Yeni eklenen import
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
public class converter extends AppCompatActivity {

    EditText editTextDecimalInput;
    Spinner spinnerDecimalConversion;
    TextView textViewDecimalResult;
    EditText inputByte;
    Spinner spinnerByte;
    TextView textViewByteResult;
    EditText inputCelsius;
    RadioButton radioButtonFahrenheit;
    RadioButton radioButtonKelvin;
    TextView textViewCelsiusResult;
    Button ReturnButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_converter);

        editTextDecimalInput = findViewById(R.id.editTextDecimalInput);
        spinnerDecimalConversion = findViewById(R.id.spinnerDecimalConversion);
        textViewDecimalResult = findViewById(R.id.textViewDecimalResult);
        inputByte = findViewById(R.id.inputByte);
        spinnerByte = findViewById(R.id.spinnerByte);
        textViewByteResult = findViewById(R.id.resultByte);
        inputCelsius = findViewById(R.id.inputCelsius);
        radioButtonFahrenheit = findViewById(R.id.radioButtonFahrenheit);
        radioButtonKelvin = findViewById(R.id.radioButtonKelvin);
        textViewCelsiusResult = findViewById(R.id.resultCelsius);
        ReturnButton = findViewById(R.id.returButtonC);

        editTextDecimalInput.addTextChangedListener(textWatcher);
        inputByte.addTextChangedListener(byteTextWatcher);
        inputCelsius.addTextChangedListener(celsiusTextWatcher);

        ReturnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ana sayfaya dönüş
                Intent intent = new Intent(converter.this, mainpage.class);
                startActivity(intent);
                finish(); // Şuanki aktiviteyi kapat
            }
        });
        // Spinner'ların seçim değişikliğini dinleyen metodlar
        spinnerDecimalConversion.setOnItemSelectedListener(spinner1);
        spinnerByte.setOnItemSelectedListener(spinner2);
        radioButtonFahrenheit.setOnCheckedChangeListener(radio);
    }
    private final AdapterView.OnItemSelectedListener spinner1 = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            performDecimalConversion(editTextDecimalInput.getText().toString());
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };
    private final AdapterView.OnItemSelectedListener spinner2 = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            performByteConversion(inputByte.getText().toString());
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };
    private final CompoundButton.OnCheckedChangeListener radio = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            performCelsiusConversion(inputCelsius.getText().toString());
        }
    };
    private final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}

        @Override
        public void afterTextChanged(Editable s) {
            performDecimalConversion(s.toString());
        }
    };

    private final TextWatcher byteTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}

        @Override
        public void afterTextChanged(Editable s) {
            performByteConversion(s.toString());
        }
    };

    private final TextWatcher celsiusTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}

        @Override
        public void afterTextChanged(Editable s) {
            performCelsiusConversion(s.toString());
        }
    };

    private void performDecimalConversion(String input) {
        if (!input.isEmpty()) {
            int selectedItemPosition = spinnerDecimalConversion.getSelectedItemPosition();
            switch (selectedItemPosition) {
                case 0: // Decimal to Binary
                    try {
                        int decimalValue = Integer.parseInt(input);
                        String binaryValue = Integer.toBinaryString(decimalValue);
                        textViewDecimalResult.setText(binaryValue);
                    } catch (NumberFormatException e) {
                        textViewDecimalResult.setText("");
                    }
                    break;
                case 1: // Decimal to Octal
                    try {
                        int decimalValue = Integer.parseInt(input);
                        String octalValue = Integer.toOctalString(decimalValue);
                        textViewDecimalResult.setText(octalValue);
                    } catch (NumberFormatException e) {
                        textViewDecimalResult.setText("");
                    }
                    break;
                case 2: // Decimal to Hexadecimal
                    try {
                        int decimalValue = Integer.parseInt(input);
                        String hexValue = Integer.toHexString(decimalValue);
                        textViewDecimalResult.setText(hexValue.toUpperCase());
                    } catch (NumberFormatException e) {
                        textViewDecimalResult.setText("");
                    }
                    break;
            }
        } else {
            textViewDecimalResult.setText("");
        }
    }

    private void performByteConversion(String input) {
        if (!input.isEmpty()) {
            int selectedItemPosition = spinnerByte.getSelectedItemPosition();
            switch (selectedItemPosition) {
                case 0: // MegaByte to KiloByte
                    try {
                        double megaByte = Double.parseDouble(input);
                        double kiloByte = megaByte * 1024;
                        textViewByteResult.setText(String.valueOf(kiloByte));
                    } catch (NumberFormatException e) {
                        textViewByteResult.setText("");
                    }
                    break;
                case 1: // MegaByte to Byte
                    try {
                        double megaByte = Double.parseDouble(input);
                        double byteVal = megaByte * 1024 * 1024;
                        textViewByteResult.setText(String.valueOf(byteVal));
                    } catch (NumberFormatException e) {
                        textViewByteResult.setText("");
                    }
                    break;
                case 2: // MegaByte to KibiByte
                    try {
                        double megaByte = Double.parseDouble(input);
                        double kibiByte = megaByte * (Math.pow(10,6) / Math.pow(2,10));
                        textViewByteResult.setText(String.valueOf(kibiByte));
                    } catch (NumberFormatException e) {
                        textViewByteResult.setText("");
                    }
                    break;
                case 3: // MegaByte to Bit
                    try {
                        double megaByte = Double.parseDouble(input);
                        double bitVal = megaByte * 1024 * 1024 * 8;
                        textViewByteResult.setText(String.valueOf(bitVal));
                    } catch (NumberFormatException e) {
                        textViewByteResult.setText("");
                    }
                    break;
            }
        } else {
            textViewByteResult.setText("");
        }
    }

    private void performCelsiusConversion(String input) {
        if (!input.isEmpty()) {
            if (radioButtonFahrenheit.isChecked()) {
                try {
                    double celsius = Double.parseDouble(input);
                    double fahrenheit = (celsius * 9 / 5) + 32;
                    textViewCelsiusResult.setText(String.valueOf(fahrenheit));
                } catch (NumberFormatException e) {
                    textViewCelsiusResult.setText("");
                }
            } else if (radioButtonKelvin.isChecked()) {
                try {
                    double celsius = Double.parseDouble(input);
                    double kelvin = celsius + 273.15;
                    textViewCelsiusResult.setText(String.valueOf(kelvin));
                } catch (NumberFormatException e) {
                    textViewCelsiusResult.setText("");
                }
            }
        } else {
            textViewCelsiusResult.setText("");
        }
    }

    // ... (Diğer metodlar)
}


