package com.tugraz.flatshareapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.tugraz.flatshareapp.database.FinanceRepository;
import com.tugraz.flatshareapp.database.Models.Finance;
import com.tugraz.flatshareapp.utility.Persistence;

import java.util.ArrayList;
import java.util.List;

public class ReportFixedAssetListFragment extends Fragment {
    int current_flat_id;
    FinanceRepository finance_repo;
    List<Finance> finance_list;

    TextView report_assets_total_sum;

    ListView listView;




    ReportFixedAssetListFragment(FinanceRepository finance_repo){
        this.finance_repo = finance_repo;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        current_flat_id = Persistence.Instance().getActiveFlatID();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_report_fixed_assests_list, container, false);

        listView = view.findViewById(R.id.assets_list);
        report_assets_total_sum = view.findViewById(R.id.report_assets_total_sum);


        try {
            finance_list = getFinances();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (finance_list != null && !finance_list.isEmpty()) {
            final ArrayAdapter<Finance> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, finance_list);
            listView.setAdapter(adapter);

            report_assets_total_sum.setText(getString(R.string.report_fixed_assets_sum) + " = " + calculateSum(finance_list));
            report_assets_total_sum.setVisibility(View.VISIBLE);
        }


        return view;
    }

    private Float calculateSum(List<Finance> finance_list) {
        float sum = 0;
        for(Finance finance: finance_list) {
            sum += finance.getValue();
        }
        return sum;
    }

    private List<Finance> getFinances() throws Exception {
        List<Finance> result = new ArrayList<>();
        for(Finance finance: finance_repo.getAllFinances()) {
            if(finance.getFlatId() != current_flat_id)
                continue;
            else
                result.add(finance);
        }
        return result;
    }

}
