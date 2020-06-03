package com.tugraz.flatshareapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.tugraz.flatshareapp.database.BillRepository;
import com.tugraz.flatshareapp.database.Models.Bill;
import com.tugraz.flatshareapp.database.Models.Roommate;
import com.tugraz.flatshareapp.database.RoommateRepository;
import com.tugraz.flatshareapp.utility.Persistence;

import java.util.ArrayList;
import java.util.List;

public class BillsActivity extends FragmentActivity {

    BillRepository bill_repository;
    Button btn_add_bill;
    FragmentTransaction frame_transaction;
    LinearLayout bills_list;
    FragmentManager fragment_manager;

    void loadBillsList()
    {
        bills_list.removeAllViews();
        try {
            List<Bill> all_bills = bill_repository.getAllBills();

            for (final Bill bill_inv : all_bills) {

                if(bill_inv.getFlatId() != Persistence.Instance().getActiveFlatID())
                    continue;

                View view = LayoutInflater.from(this).inflate(R.layout.template_bills_list, null);
                view.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        FragmentTransaction fragmentTransaction = fragment_manager.beginTransaction();

                        BillsDetailFragment fragment = new BillsDetailFragment(bill_repository, bill_inv);
                        fragmentTransaction.add(R.id.fragment_container, fragment);
                        fragmentTransaction.addToBackStack("").commit();
                    }
                });
                TextView name = view.findViewById(R.id.bill_name);
                TextView price = view.findViewById(R.id.bill_price);
                name.setText(bill_inv.getDescription() + " (" + (bill_inv.isMonthly() ? getString(R.string.monthly) : getString(R.string.bill_detail_yearly)) + ")");
                price.setText(Float.toString(bill_inv.getValue()));
                bills_list.addView(view);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bills_list);
        bill_repository = new BillRepository(getApplication());
        btn_add_bill = (Button) findViewById(R.id.btn_bills_list_add);
        fragment_manager = getSupportFragmentManager();
        bills_list = findViewById(R.id.linear_layout_bills_list_list);
        btn_add_bill.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Fragment detail_fragment = new BillsDetailFragment(bill_repository);
                frame_transaction = fragment_manager.beginTransaction();
                frame_transaction.add(R.id.fragment_container, detail_fragment);
                frame_transaction.addToBackStack("").commit();
            }
        });
        fragment_manager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                loadBillsList();
            }
        });

        loadBillsList();

    }
}
