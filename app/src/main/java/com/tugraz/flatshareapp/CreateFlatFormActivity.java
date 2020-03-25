package com.tugraz.flatshareapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;


public class CreateFlatFormActivity extends AppCompatActivity {

    EditText editFlatName, editStreetName, editStreetNumber, editCity, editCountry;
    Button buttonCreateFlat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_flat_form);

        editFlatName  = (EditText) findViewById(R.id.editFlatName);
        editStreetName = (EditText) findViewById(R.id.editStreetName);
        editStreetNumber = (EditText) findViewById(R.id.editStreetNumber);
        editCity = (EditText) findViewById(R.id.editCity);
        editCountry = (EditText) findViewById(R.id.editCountry);
        buttonCreateFlat = (Button) findViewById(R.id.buttonCreateFlat);



        buttonCreateFlat.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String flatName = editFlatName.getText().toString();
                String streetName = editStreetName.getText().toString();
            }
        });
        }


    }

