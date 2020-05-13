package com.tugraz.flatshareapp;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.tugraz.flatshareapp.database.CleaningRepository;
import com.tugraz.flatshareapp.database.Models.Cleaning;
import com.tugraz.flatshareapp.database.Models.Roommate;
import com.tugraz.flatshareapp.database.RoommateRepository;

import java.util.List;

public class CleaningScheduleActivity extends AppCompatActivity {

    Button btn_add_task;

    CleaningRepository clean_repo;
    RoommateRepository roommate_repo;

    FragmentManager fragmentManager;
    Context context;

    LinearLayout cleaning_list;


    public void loadCleaningScheduleList()
    {
        cleaning_list.removeAllViews();
        try {
            List<Cleaning> allCleanings = clean_repo.getAllCleanings();

            for (final Cleaning cleaning : allCleanings) {

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cleaning_schedule);

        clean_repo = new CleaningRepository(getApplication());
        roommate_repo = new RoommateRepository(getApplication());
        context = this;

        cleaning_list = findViewById(R.id.cleaning_list);

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

        fragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                loadCleaningScheduleList();
            }
        });

        loadCleaningScheduleList();
    }
}
