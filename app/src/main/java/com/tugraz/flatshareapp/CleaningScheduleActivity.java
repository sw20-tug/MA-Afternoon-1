package com.tugraz.flatshareapp;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.tugraz.flatshareapp.database.CleaningRepository;

public class CleaningScheduleActivity extends AppCompatActivity {

    Button btn_add_task;

    CleaningRepository clean_repo;

    FragmentManager fragmentManager;
    Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cleaning_schedule);

        clean_repo = new CleaningRepository(getApplication());
        context = this;

        fragmentManager = getSupportFragmentManager();

        btn_add_task  = (Button) findViewById(R.id.btn_cleaning_schedule_add);

        btn_add_task.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                CleaningEditFragment fragment = new CleaningEditFragment(clean_repo, null);
                fragmentTransaction.add(R.id.fragment_container, fragment);
                fragmentTransaction.addToBackStack("").commit();
            }
        });
    }
}
