package com.tugraz.flatshareapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.tugraz.flatshareapp.database.FlatRepository;
import com.tugraz.flatshareapp.database.Models.Flat;
import com.tugraz.flatshareapp.utility.Persistence;

import java.util.List;

public class OverviewActivity extends AppCompatActivity {

    Button btn_room_mates, btn_shopping_list, btn_cleaning_schedule, btn_financing, btn_organize;
    FlatRepository dbExecutor;
    private static final String TAG = CreateFlatFormActivity.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // initialise active flat persistance
        try {
            for(Flat flat: dbExecutor.getAllFlats()) {
                if(flat.getActive()) {
                    Persistence.Instance().setActiveFlatID(flat.getId());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        dbExecutor = new FlatRepository(getApplication());
        // Set layout
        setContentView(R.layout.activity_overview);

        btn_room_mates  = (Button) findViewById(R.id.btn_room_mates);
        btn_shopping_list = (Button) findViewById(R.id.btn_shopping_list);
        btn_cleaning_schedule = (Button) findViewById(R.id.btn_cleaning_schedule);
        btn_financing = (Button) findViewById(R.id.btn_financing);
        btn_organize = (Button) findViewById(R.id.btn_organizeflat);

//        switchActivity(CreateFlatFormActivity.class);
        btn_room_mates.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                switchActivity(RoommatesActivity.class);
            }
        });

        btn_shopping_list.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                switchActivity(ShoppingListActivity.class);
            }
        });

        btn_cleaning_schedule.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                switchActivity(CleaningScheduleActivity.class);
            }
        });

        btn_organize.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                switchActivity(FlatListActivity.class);
            }
        });

        btn_financing.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                switchActivity(FinancingFurnitureActivity.class);
            }
        });

        try {
            List<Flat> flats = dbExecutor.getAllFlats();
            // if flat found fill in fields
            if (flats.isEmpty()) {
                switchActivity(CreateFlatFormActivity.class);
            }
        } catch (Exception e) {
            Log.e(TAG, "onCreate: ", e);
        }
    }

    public void switchActivity(Class activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }

}
