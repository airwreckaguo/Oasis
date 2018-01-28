package com.example.gcantien.danksupreme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText editText2 = findViewById(R.id.editText2);
        editText2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //Nothing to do here
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //TODO: Process the text
//                Log.d("HELLO", charSequence.toString());
//                Toast.makeText(MainActivity.this, charSequence.toString(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void afterTextChanged(Editable editable) {
                //Nothing to do here
            }
        });
        editText2.getText();
    }
}
