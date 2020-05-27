package com.tugraz.flatshareapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.tugraz.flatshareapp.database.Models.Flat;
import com.tugraz.flatshareapp.database.FlatRepository;
import com.tugraz.flatshareapp.database.Models.Roommate;
import com.tugraz.flatshareapp.database.RoommateRepository;
import com.tugraz.flatshareapp.utility.Persistence;

import java.util.Date;


public class CreateFlatFormActivity extends AppCompatActivity {

    private static final String TAG = CreateFlatFormActivity.class.getSimpleName();

    EditText editFlatName, editStreetName, editStreetNumber, editCity, editCountry;
    Button buttonCreateFlat;

    FlatRepository dbExecutor;
    RoommateRepository roommateRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_flat_form);

        dbExecutor = new FlatRepository(getApplication());
        roommateRepository = new RoommateRepository(getApplication());

        try {
            if(dbExecutor.getAllFlats().size() != 0)
                finish();
        } catch (Exception e) {
            e.printStackTrace();
        }

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

                /*Date sometime = new Date();

                try {
                    Persistence.Instance().setActiveFlatID(Persistence.INITIAL_ID);
                    if(roommateRepository.getAllRoommates().size() == 0)
                        roommateRepository.insert(new Roommate("me", "", sometime, sometime, Persistence.INITIAL_ID));
                    Persistence.Instance().setActiveRoommateId(Persistence.INITIAL_ID);
                } catch (Exception e) {
                    e.printStackTrace();
                }*/

                finish();
            }
        });
    }
}

