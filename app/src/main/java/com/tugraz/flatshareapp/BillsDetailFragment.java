package com.tugraz.flatshareapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.tugraz.flatshareapp.database.BillRepository;
import com.tugraz.flatshareapp.database.FlatRepository;
import com.tugraz.flatshareapp.database.Models.Bill;
import com.tugraz.flatshareapp.database.Models.Roommate;
import com.tugraz.flatshareapp.database.RoommateRepository;
import com.tugraz.flatshareapp.utility.Persistence;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BillsDetailFragment extends Fragment {

    Button save_button;
    Button delete_button;
    Button close_button;
    View view;
    BillRepository bill_repository;
    Bill bill = null;
    boolean edit;

    // UI components
    EditText bill_name_view;
    EditText bill_price_view;
    Switch bill_monthly_view;
    TextView bill_monthly_text_view;



    public BillsDetailFragment(BillRepository bill_repository) {
        this.bill_repository = bill_repository;
        edit = false;
        // Required empty public constructor
    }

    public BillsDetailFragment(BillRepository bill_repository, Bill bill)
    {
        this.bill_repository = bill_repository;
        this.bill = bill;
        edit = true;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       view = inflater.inflate(R.layout.fragment_bills_detail, container, false);


        bill_name_view = view.findViewById(R.id.text_bills_detail_value);
        bill_price_view = view.findViewById(R.id.text_bills_detail_price_value);
        bill_monthly_view = view.findViewById(R.id.swt_bills_detail_monthly);
        bill_monthly_text_view = view.findViewById(R.id.text_bills_monthly);




       if(edit) {
           bill_name_view.setText(bill.getDescription());
           bill_price_view.setText(Float.toString(bill.getValue()));
           if(bill.isMonthly())
           {
               bill_monthly_text_view.setText(R.string.bill_detail_monthly);
               bill_monthly_view.setChecked(false);
           }
           else {
               bill_monthly_text_view.setText(R.string.bill_detail_yearly);
               bill_monthly_view.setChecked(true);
           }
       }

       save_button = view.findViewById(R.id.btn_bills_detail_save);
       delete_button = view.findViewById(R.id.btn_delete_bill_detail);
       close_button = view.findViewById(R.id.button_bills_detail_close);

       close_button.setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View v) {
               getActivity().getSupportFragmentManager().popBackStack();
           }
       });

       delete_button.setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View v) {
               new AlertDialog.Builder(getContext())
                       .setMessage(R.string.dialog_delete_roommate)
                       .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialogInterface, int i) {
                               bill_repository.delete(bill);
                               getActivity().getSupportFragmentManager().popBackStack();
                           }
                       })
                       .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialog, int which) {
                               dialog.dismiss();
                           }
                       })
                       .create()
                       .show();
           }
       });

        bill_monthly_view.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked)
                    bill_monthly_text_view.setText(R.string.bill_detail_monthly);
                else
                    bill_monthly_text_view.setText(R.string.bill_detail_yearly);
            }
        });

       save_button.setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View v) {
               String name, price;
               boolean monthly;
               name = bill_name_view.getText().toString();
               price = bill_price_view.getText().toString();
               monthly = bill_monthly_view.isChecked();
               Float price_value = null;
               price_value = Float.parseFloat(price);



               if(!edit && bill == null) {
                   bill_repository.insert(new Bill(name, price_value, !monthly, Persistence.Instance().getActiveFlatID()));
               }
               else {
                   bill.setDescription(name);
                   bill.setValue(price_value);
                   bill.setMonthly(!monthly);
                   bill_repository.update(bill);
               }
               getActivity().getSupportFragmentManager().popBackStack();
               }
       });
        return view;
    }
}
