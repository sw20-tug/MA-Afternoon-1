package com.tugraz.flatshareapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

import com.tugraz.flatshareapp.database.Models.Flat;
import com.tugraz.flatshareapp.database.Repository;

import java.util.List;


public class CreateFlatFormActivity extends AppCompatActivity {

    private static final String TAG = CreateFlatFormActivity.class.getSimpleName();

    EditText editFlatName, editStreetName, editStreetNumber, editCity, editCountry;
    Button buttonCreateFlat;

    Repository dbExecutor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_flat_form);
        dbExecutor = new Repository(getApplication());


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
                String streetNumber = editStreetNumber.getText().toString();
                String city = editCity.getText().toString();
                String country = editCountry.getText().toString();
                // TODO handle active flat
                dbExecutor.insert(new Flat(flatName, streetName, streetNumber, city, country/*, true*/));
            }
        });

        // TODO migrate that to overview
        try {
            List<Flat> flats = dbExecutor.getAllFlats();
            // if flat found fill in fields
            if (!flats.isEmpty()) {
                fillFormWithFlatInfo(flats.get(0));
            }
        } catch (Exception e) {
            Log.e(TAG, "onResume: ", e);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void fillFormWithFlatInfo(Flat flat) {
        editFlatName.setText(flat.getName());
        editStreetName.setText(flat.getStreetName());
        editStreetNumber.setText(flat.getStreetNumber());
        editCity.setText(flat.getCity());
        editCountry.setText(flat.getCountry());
        buttonCreateFlat.setText("Edit");
    }
}

