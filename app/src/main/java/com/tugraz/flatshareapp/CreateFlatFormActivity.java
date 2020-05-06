package com.tugraz.flatshareapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

import com.tugraz.flatshareapp.database.Models.Flat;
import com.tugraz.flatshareapp.database.FlatRepository;
import com.tugraz.flatshareapp.utility.Persistence;

import java.util.List;


public class CreateFlatFormActivity extends AppCompatActivity {

    private static final String TAG = CreateFlatFormActivity.class.getSimpleName();

    EditText editFlatName, editStreetName, editStreetNumber, editCity, editCountry;
    Button buttonCreateFlat;

    FlatRepository dbExecutor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_flat_form);
        dbExecutor = new FlatRepository(getApplication());


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
                dbExecutor.insert(new Flat(flatName, streetName, streetNumber, city, country, true));
                // TODO handle active flat
                try {
                    List<Flat> list = dbExecutor.getAllFlats();
                    for (Flat flat : list ) {
                        if(flat.getActive())
                            Persistence.Instance().setActiveFlatID(flat.getId());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                switchActivity(OverviewActivity.class);

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

    public void switchActivity(Class activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }
}

