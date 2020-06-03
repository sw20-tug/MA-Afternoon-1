package com.tugraz.flatshareapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.tugraz.flatshareapp.database.BillRepository;
import com.tugraz.flatshareapp.database.FlatRepository;
import com.tugraz.flatshareapp.database.Models.Bill;
import com.tugraz.flatshareapp.database.Models.Flat;
import com.tugraz.flatshareapp.utility.Persistence;

import java.util.List;

public class OverviewActivity extends AppCompatActivity {

    Button btn_room_mates, btn_shopping_list, btn_cleaning_schedule, btn_financing, btn_organize, btn_bills, btn_report;
    FlatRepository dbExecutor;
    BillRepository bill_repo;
    TextView check;
    private static final String TAG = CreateFlatFormActivity.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbExecutor = new FlatRepository(getApplication());
        bill_repo = new BillRepository(getApplication());

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


        // Set layout
        setContentView(R.layout.activity_overview);

        btn_room_mates  = (Button) findViewById(R.id.btn_room_mates);
        btn_shopping_list = (Button) findViewById(R.id.btn_shopping_list);
        btn_cleaning_schedule = (Button) findViewById(R.id.btn_cleaning_schedule);
        btn_financing = (Button) findViewById(R.id.btn_financing);
        btn_organize = (Button) findViewById(R.id.btn_organizeflat);
        btn_bills = (Button) findViewById(R.id.btn_bill);
        btn_report = findViewById(R.id.btn_report);

        updateFlatData();

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

        btn_bills.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                switchActivity(BillsActivity.class);
            }
        });

        btn_report.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                switchActivity(ReportActivity.class);
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

    @Override
    protected void onResume()
    {
        super.onResume();

        updateFlatData();
    }

    public void updateFlatData()
    {
        try {
            boolean is_empty = true;
            for (Bill current_bill: bill_repo.getAllBills())
            {
                if(current_bill.getFlatId() == Persistence.Instance().getActiveFlatID())
                {
                    is_empty = false;
                }
            }
            if(is_empty)
            {
                bill_repo.insert(new Bill(getResources().getString(R.string.bill_list_rental_fee), 0, true, Persistence.Instance().getActiveFlatID()));
                bill_repo.insert(new Bill(getResources().getString(R.string.bill_list_smartphone_bill), 0, true, Persistence.Instance().getActiveFlatID()));
                bill_repo.insert(new Bill(getResources().getString(R.string.bill_list_internet_bill), 0, true, Persistence.Instance().getActiveFlatID()));
                bill_repo.insert(new Bill(getResources().getString(R.string.bill_list_tv_bill), 0, true, Persistence.Instance().getActiveFlatID()));
                bill_repo.insert(new Bill(getResources().getString(R.string.bill_list_energy_bill), 0, true, Persistence.Instance().getActiveFlatID()));
            }

            for(Flat cFlat : dbExecutor.getAllFlats()){
                if(cFlat.getActive()){
                    check = findViewById(R.id.tv_city_value);
                    check.setText(cFlat.getCity());
                    check = findViewById(R.id.tv_country_value);
                    check.setText(cFlat.getCountry());
                    check = findViewById(R.id.tv_street_name_value);
                    check.setText(cFlat.getStreetName());
                    check = findViewById(R.id.tv_street_number_value);
                    check.setText(cFlat.getStreetNumber());
                    check = findViewById(R.id.tv_flat_name);
                    check.setText(cFlat.getName());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void switchActivity(Class activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }

}
