package com.tugraz.flatshareapp;

import android.graphics.Color;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.tugraz.flatshareapp.database.BillRepository;
import com.tugraz.flatshareapp.database.Models.Bill;
import com.tugraz.flatshareapp.database.Models.Roommate;
import com.tugraz.flatshareapp.database.RoommateRepository;
import com.tugraz.flatshareapp.utility.Persistence;

import java.util.ArrayList;
import java.util.List;

public class ReportFixedCostsSummaryFragment extends Fragment {
    TableLayout report_table;
    Integer current_flat_id;

    RoommateRepository roomate_repo;
    BillRepository bill_repo;



    public ReportFixedCostsSummaryFragment(RoommateRepository roomate_repo, BillRepository bill_repo) {
        this.roomate_repo = roomate_repo;
        this.bill_repo = bill_repo;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        current_flat_id = Persistence.Instance().getActiveFlatID();
        View view = inflater.inflate(R.layout.fragment_report_fixed_costs_summary, container, false);

        report_table = view.findViewById(R.id.report_table);

        try {
            loadTableData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    private void loadTableData() throws Exception {
        report_table.setStretchAllColumns(true);

        List<Roommate> currentflatRoommates = getCurrentFlatRoommates();
        Integer number_of_roommates = currentflatRoommates.size();

        List<Bill> currentFlatBills = getCurrentFlatBills();
        Integer number_of_bills = currentFlatBills.size();

        if(number_of_roommates > -1 && number_of_bills > -1) {
            fillReportTable(number_of_roommates, currentflatRoommates, currentFlatBills);
        }

    }

    private void fillReportTable(Integer number_of_roommates, List<Roommate> currentflatRoommates, List<Bill> currentFlatBills) throws Exception {
        TableRow.LayoutParams llp = new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        llp.setMargins(0, 0, 0, 0);//2px right-margin

        Float monthlyBillsSum = Float.valueOf("0");
        Float yearlyBillsSum = Float.valueOf("0");

        fillMonthlyAndYearlyBillsSum(monthlyBillsSum, yearlyBillsSum, currentFlatBills);



        setHeaderRow(llp);

        for (Roommate roommate: currentflatRoommates) {
            LinearLayout cell_1 = new LinearLayout(getContext());
            cell_1.setBackgroundColor(Color.WHITE);
            cell_1.setLayoutParams(llp);//2px border on the right for the cell
            cell_1.setGravity(Gravity.CENTER);

            LinearLayout cell_2 = new LinearLayout(getContext());
            cell_2.setBackgroundColor(Color.WHITE);
            cell_2.setLayoutParams(llp);//2px border on the right for the cell
            cell_2.setGravity(Gravity.CENTER);

            LinearLayout cell_3 = new LinearLayout(getContext());
            cell_3.setBackgroundColor(Color.WHITE);
            cell_3.setLayoutParams(llp);//2px border on the right for the cell
            cell_3.setGravity(Gravity.CENTER);

            TextView roomate_name = new TextView(getContext());
            roomate_name.setText(roommate.getName() + " " + roommate.getLastName());
            roomate_name.setPadding(6, 0, 6, 3);
            cell_1.addView(roomate_name);
            cell_1.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.border));


            TextView fixed_costs_per_month = new TextView(getContext());
            fixed_costs_per_month.setText(String.valueOf(monthlyBillsSum / number_of_roommates));
            fixed_costs_per_month.setPadding(6, 0, 6, 3);
            cell_2.addView(fixed_costs_per_month);
            cell_2.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.border));


            TextView fixed_costs_per_year = new TextView(getContext());
            fixed_costs_per_year.setText(String.valueOf(yearlyBillsSum / number_of_roommates));
            fixed_costs_per_year.setPadding(6, 0, 6, 3);
            cell_3.addView(fixed_costs_per_year);
            cell_3.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.border));

            TableRow row = new TableRow(getContext());

            row.addView(cell_1);  //Line 39
            row.addView(cell_2);
            row.addView(cell_3);

            row.setGravity(Gravity.CENTER);
//            tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.FILL_PARENT, 1.0f));

            report_table.addView(row);
        }

        setTotalRow(llp, monthlyBillsSum, yearlyBillsSum);
    }

    private void fillMonthlyAndYearlyBillsSum(Float monthlyBillsSum, Float yearlyBillsSum, List<Bill> currentFlatBills) throws Exception {
        for(Bill bill: currentFlatBills) {
            if(bill.isMonthly()) {
                monthlyBillsSum += bill.getValue();
            } else {
                yearlyBillsSum += bill.getValue();
            }
        }
    }

    private List<Roommate> getCurrentFlatRoommates() throws Exception {
        List<Roommate> currentflatRoommates = new ArrayList<>();
        for(Roommate roommate: roomate_repo.getAllRoommates()) {
            if(roommate.getFlatId() == current_flat_id) {
                currentflatRoommates.add(roommate);
            }
        }
        return currentflatRoommates;
    }

    private List<Bill> getCurrentFlatBills() throws Exception {
        List<Bill> currentFlatBills = new ArrayList<>();
        for(Bill bill: bill_repo.getAllBills()) {
            if(bill.getFlatId() == current_flat_id) {
                currentFlatBills.add(bill);
            }
        }
        return currentFlatBills;
    }

    private void setHeaderRow(TableRow.LayoutParams llp) {
        LinearLayout cell_1 = new LinearLayout(getContext());
        cell_1.setBackgroundColor(Color.WHITE);
        cell_1.setLayoutParams(llp);//2px border on the right for the cell
        cell_1.setGravity(Gravity.CENTER);

        LinearLayout cell_2 = new LinearLayout(getContext());
        cell_2.setBackgroundColor(Color.WHITE);
        cell_2.setLayoutParams(llp);//2px border on the right for the cell
        cell_2.setGravity(Gravity.CENTER);

        LinearLayout cell_3 = new LinearLayout(getContext());
        cell_3.setBackgroundColor(Color.WHITE);
        cell_3.setLayoutParams(llp);//2px border on the right for the cell
        cell_3.setGravity(Gravity.CENTER);

        TextView roomate_name = new TextView(getContext());
        roomate_name.setText("Roomate name");
        roomate_name.setPadding(6, 0, 6, 3);
        cell_1.addView(roomate_name);
        cell_1.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.border));

        TextView fixed_costs_per_month = new TextView(getContext());
        fixed_costs_per_month.setText("Monthly costs");
        fixed_costs_per_month.setPadding(6, 0, 6, 3);
        cell_2.addView(fixed_costs_per_month);
        cell_2.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.border));

        TextView fixed_costs_per_year = new TextView(getContext());
        fixed_costs_per_year.setText("Yearly costs");
        fixed_costs_per_year.setPadding(6, 0, 6, 3);
        cell_3.addView(fixed_costs_per_year);
        cell_3.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.border));

        TableRow rowHeader = new TableRow(getContext());

        rowHeader.addView(cell_1);  //Line 39
        rowHeader.addView(cell_2);
        rowHeader.addView(cell_3);

        report_table.addView(rowHeader);
    }

    private void setTotalRow(TableRow.LayoutParams llp, Float totalMonthlyBills, Float totalYearlyBills) {
        LinearLayout cell_1 = new LinearLayout(getContext());
        cell_1.setBackgroundColor(Color.WHITE);
        cell_1.setLayoutParams(llp);//2px border on the right for the cell
        cell_1.setGravity(Gravity.CENTER);

        LinearLayout cell_2 = new LinearLayout(getContext());
        cell_2.setBackgroundColor(Color.WHITE);
        cell_2.setLayoutParams(llp);//2px border on the right for the cell
        cell_2.setGravity(Gravity.CENTER);

        LinearLayout cell_3 = new LinearLayout(getContext());
        cell_3.setBackgroundColor(Color.WHITE);
        cell_3.setLayoutParams(llp);//2px border on the right for the cell
        cell_3.setGravity(Gravity.CENTER);

        TextView roomate_name = new TextView(getContext());
        roomate_name.setText("Total:");
        roomate_name.setPadding(6, 0, 6, 3);
        cell_1.addView(roomate_name);
        cell_1.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.border));

        TextView fixed_costs_per_month = new TextView(getContext());
        fixed_costs_per_month.setText(String.valueOf(totalMonthlyBills));
        fixed_costs_per_month.setPadding(6, 0, 6, 3);
        cell_2.addView(fixed_costs_per_month);
        cell_2.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.border));

        TextView fixed_costs_per_year = new TextView(getContext());
        fixed_costs_per_year.setText(String.valueOf(totalYearlyBills));
        fixed_costs_per_year.setPadding(6, 0, 6, 3);
        cell_3.addView(fixed_costs_per_year);
        cell_3.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.border));

        TableRow rowHeader = new TableRow(getContext());

        rowHeader.addView(cell_1);  //Line 39
        rowHeader.addView(cell_2);
        rowHeader.addView(cell_3);

        report_table.addView(rowHeader);
    }
}
