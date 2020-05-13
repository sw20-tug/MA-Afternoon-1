package com.tugraz.flatshareapp;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.tugraz.flatshareapp.database.CleaningRepository;
import com.tugraz.flatshareapp.database.Models.Cleaning;
import com.tugraz.flatshareapp.database.Models.Roommate;
import com.tugraz.flatshareapp.database.RoommateRepository;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class CleaningScheduleActivity extends AppCompatActivity {

    Button btn_add_task;

    CleaningRepository clean_repo;
    RoommateRepository roommate_repo;

    FragmentManager fragmentManager;
    Context context;

    LinearLayout cleaning_list;
    Spinner spinner_export;


    public void loadCleaningScheduleList()
    {
        cleaning_list.removeAllViews();
        try {
            List<Cleaning> allCleanings = clean_repo.getAllCleanings();

            for (final Cleaning cleaning : allCleanings) {

                resetSchedule(cleaning);

                View view = LayoutInflater.from(this).inflate(R.layout.template_cleaning_schedule_list, null);
                TextView name = view.findViewById(R.id.rommate_name);
                TextView description = view.findViewById(R.id.cleaning_description);

                Button editButton = view.findViewById(R.id.cleaning_edit_btn);

                editButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final FragmentManager manager = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                        CleaningEditFragment fragment = new CleaningEditFragment(clean_repo, cleaning, roommate_repo);
                        fragmentTransaction.add(R.id.fragment_container, fragment);
                        fragmentTransaction.addToBackStack("").commit();
                    }
                });

                Roommate roommate = roommate_repo.get(cleaning.getRoommateId());


                String complete_name = roommate.getName() + " " +roommate.getLastName();
                name.setText(complete_name);
                description.setText(cleaning.getDescription());
                if(cleaning.isCompleted()) {
                    view.setBackgroundColor(Color.GREEN);
                }
                cleaning_list.addView(view);
//                roommates_id.add(roommateInv.getId());

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void resetSchedule(Cleaning cleaning) {
        if (cleaning.isCompleted()) {
            Date newDate = new Date(cleaning.getDoneTimestamp());

            GregorianCalendar calendar = new GregorianCalendar();
            calendar.setTime(newDate);
            if(cleaning.isWeekly()) {
                calendar.add(Calendar.DAY_OF_YEAR, 7);
            } else {
                calendar.add(Calendar.MONTH, 1);
            }

            newDate.setTime(calendar.getTime().getTime());

            if (newDate.getTime() < new Date().getTime()) {
                cleaning.setCompleted(false);
            }

            clean_repo.update(cleaning);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cleaning_schedule);

        clean_repo = new CleaningRepository(getApplication());
        roommate_repo = new RoommateRepository(getApplication());
        context = this;

        cleaning_list = findViewById(R.id.cleaning_list);
        spinner_export = findViewById(R.id.spinner_cleaning_export);

        fragmentManager = getSupportFragmentManager();

        btn_add_task  = (Button) findViewById(R.id.btn_cleaning_schedule_add);

        btn_add_task.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                CleaningEditFragment fragment = new CleaningEditFragment(clean_repo, null, roommate_repo);
                fragmentTransaction.add(R.id.fragment_container, fragment);
                fragmentTransaction.addToBackStack("").commit();
            }
        });

        ArrayList values = new ArrayList();
        values.add(getResources().getString(R.string.cleaning_export_all));
        values.add(getResources().getString(R.string.cleaning_export_user));
        ArrayAdapter<String > adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, values);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        spinner_export.setAdapter(adapter);

        spinner_export.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(android.widget.AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(android.widget.AdapterView<?> parent) {

            }
        });


        fragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                loadCleaningScheduleList();
            }
        });

        loadCleaningScheduleList();
    }
}
