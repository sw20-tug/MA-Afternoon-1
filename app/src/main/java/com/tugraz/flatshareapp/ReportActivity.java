package com.tugraz.flatshareapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import com.tugraz.flatshareapp.database.BillRepository;
import com.tugraz.flatshareapp.database.FinanceRepository;
import com.tugraz.flatshareapp.database.RoommateRepository;

public class ReportActivity extends AppCompatActivity {
    Context context;
    FragmentManager fragmentManager;
//    ConstraintLayout report_constraint_layout;

    RoommateRepository roomate_repo;
    BillRepository bill_repo;
    Switch report_switch;
    FinanceRepository finance_repo;
    ReportFixedCostsSummaryFragment report_fixed_cost_summary_fragment;
    ReportFixedAssetListFragment report_fixed_asset_fragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        finance_repo = new FinanceRepository(getApplication());
        fragmentManager = getSupportFragmentManager();
        report_fixed_cost_summary_fragment = new ReportFixedCostsSummaryFragment(roomate_repo, bill_repo);
        report_fixed_asset_fragment = new ReportFixedAssetListFragment(finance_repo);


        roomate_repo = new RoommateRepository(getApplication());
        bill_repo = new BillRepository(getApplication());

        report_switch = findViewById(R.id.report_switch);
        switchFragment(report_fixed_cost_summary_fragment);

        report_switch.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
//                FragmentTransaction t = getSupportFragmentManager().beginTransaction();
                if (report_switch.isChecked()) {

                    report_switch.setText(R.string.report_switch_fixed_assests);
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    if (report_fixed_cost_summary_fragment != null) {
                        fragmentTransaction.detach(report_fixed_cost_summary_fragment);
//                        fragmentTransaction.commit();
                    }
                    report_fixed_asset_fragment = new ReportFixedAssetListFragment(finance_repo);
                    fragmentTransaction.add(R.id.fragment_container, report_fixed_asset_fragment);
                    fragmentTransaction.addToBackStack("").commit();

                } else {
                    report_switch.setText(R.string.report_switch_fixed_costs);
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    if (report_fixed_asset_fragment != null) {
                        fragmentTransaction.detach(report_fixed_asset_fragment);
//                        fragmentTransaction.commit();
                    }
                    report_fixed_cost_summary_fragment = new ReportFixedCostsSummaryFragment(roomate_repo, bill_repo);
                    fragmentTransaction.add(R.id.fragment_container, report_fixed_cost_summary_fragment);
                    fragmentTransaction.addToBackStack("").commit();
                }
            }
        });
    }

    private void switchFragment(Fragment fragment) {
        report_switch.setText(R.string.report_switch_fixed_costs);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (report_fixed_asset_fragment != null) {
            fragmentTransaction.detach(report_fixed_asset_fragment);
//                        fragmentTransaction.commit();
        }
        report_fixed_cost_summary_fragment = new ReportFixedCostsSummaryFragment(roomate_repo, bill_repo);
        fragmentTransaction.add(R.id.fragment_container, report_fixed_cost_summary_fragment);
        fragmentTransaction.addToBackStack("").commit();
        // Begin the transaction
//        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//        // Replace the contents of the container with the new fragment
//        ft.replace(R.id.fragment_container, fragment);
//        // Complete the changes added above
//        ft.commit();
    }


}
