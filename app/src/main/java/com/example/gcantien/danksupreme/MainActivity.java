package com.example.gcantien.danksupreme;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    public TextView urlTextView;
    public JSONObject jsonObject;

    /*
     * The main class: creates a JSON object to store user data and upload
     * it to a server for analysis.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* EditText fields take in user data and store it in JSON object */
        EditText editTextName = findViewById(R.id.editTextName);
        EditText editTextAge = findViewById(R.id.editTextAge);
        EditText editTextGender = findViewById(R.id.editTextGender);
        EditText editTextRelationship = findViewById(R.id.editTextRelationship);
        EditText editTextCause = findViewById(R.id.editTextCause);

        jsonObject = new JSONObject();

        /* Button sends data to server */
        Button getMatchButton = findViewById(R.id.button);

        urlTextView = findViewById(R.id.urlTextView);

        /* Listeners wait for text to be inputted and then add them to the JSONObject */
        editTextName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //Nothing to do here
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    jsonObject.put("name", charSequence.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void afterTextChanged(Editable editable) {
                //Nothing to do here
            }
        });

        editTextAge.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //Nothing to do here
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                Log.d("HELLO", charSequence.toString());
//                Toast.makeText(MainActivity.this, charSequence.toString(), Toast.LENGTH_SHORT).show();
                try {
                    jsonObject.put("age", Integer.parseInt(charSequence.toString()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void afterTextChanged(Editable editable) {
                //Nothing to do here
            }
        });

        editTextGender.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //Nothing to do here
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    if (charSequence.toString().equals("Male")) {
                        jsonObject.put("gender", 1);
                    } else {
                        jsonObject.put("gender", 0);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void afterTextChanged(Editable editable) {
                //Nothing to do here
            }
        });

        editTextRelationship.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //Nothing to do here
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                Log.d("HELLO", charSequence.toString());
//                Toast.makeText(MainActivity.this, charSequence.toString(), Toast.LENGTH_SHORT).show();
                try {
                    jsonObject.put("relationship", Integer.parseInt(charSequence.toString()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {
                //Nothing to do here
            }
        });

        editTextCause.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //Nothing to do here
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                Log.d("HELLO", charSequence.toString());
//                Toast.makeText(MainActivity.this, charSequence.toString(), Toast.LENGTH_SHORT).show();
                try {
                    jsonObject.put("cause of death", Integer.parseInt(charSequence.toString()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void afterTextChanged(Editable editable) {
                //Nothing to do here
            }
        });

        /* Button Listener sends data to the cloud by instantiating JSONTask.
           See JSONTask for more information */
        getMatchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new JSONTask(MainActivity.this).execute("http://127.0.0.1:5000/getmatch", jsonObject.toString());
            }
        });

        editTextName.getText();


    }
}
