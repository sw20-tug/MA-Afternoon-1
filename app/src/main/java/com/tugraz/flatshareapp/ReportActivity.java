package com.tugraz.flatshareapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.os.Bundle;

import com.tugraz.flatshareapp.database.BillRepository;
import com.tugraz.flatshareapp.database.RoommateRepository;

public class ReportActivity extends AppCompatActivity {
    Context context;
    FragmentManager fragmentManager;

    RoommateRepository roomate_repo;
    BillRepository bill_repo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        roomate_repo = new RoommateRepository(getApplication());
        bill_repo = new BillRepository(getApplication());

        switchFragment(new ReportFixedCostsSummaryFragment(roomate_repo, bill_repo));
    }

    private void switchFragment(Fragment fragment) {
        // Begin the transaction
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        // Replace the contents of the container with the new fragment
        ft.replace(R.id.fragment_container, fragment);
        // Complete the changes added above
        ft.commit();
    }


}
