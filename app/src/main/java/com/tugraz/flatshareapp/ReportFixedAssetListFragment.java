package com.tugraz.flatshareapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.tugraz.flatshareapp.database.FinanceRepository;
import com.tugraz.flatshareapp.database.Models.Finance;
import com.tugraz.flatshareapp.utility.Persistence;

import java.util.List;

public class ReportFixedAssetListFragment extends Fragment {
    int current_flat_id = Persistence.Instance().getActiveFlatID();
    FinanceRepository finance_repo;
    List<Finance> finance_list;
    String asset_name;
    float asset_price = 0;
    TextView text_asset_name, text_asset_price;
    LinearLayout report_linear_layout, report_parent_linear_layout;

    ReportFixedAssetListFragment(FinanceRepository finance_repo){
        this.finance_repo = finance_repo;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void loadFinanceList(){
        report_parent_linear_layout.removeAllViews();
        try {
            finance_list = finance_repo.getAllFinances();
            View view;
            for(Finance f : finance_list){
                view =  LayoutInflater.from(getActivity()).inflate(R.layout.template_report_fixed_assests_list, null);

                if(f.getFlatId() != current_flat_id)
                    continue;
//                asset_name = f.getDescription();

                asset_price += f.getValue();

                text_asset_name = view.findViewById(R.id.report_asset_name);
                text_asset_price = view.findViewById(R.id.report_asset_price);

                text_asset_name.setText(f.getDescription());
                text_asset_price.setText(String.format("%.2f",f.getValue()));
                report_parent_linear_layout.addView(view);
            }
//            inner_view =  LayoutInflater.from(getActivity()).inflate(R.layout.template_report_fixed_assests_list, null);
//            text_asset_name.setText("Total:");
//            text_asset_price.setText(String.format("%.2f",asset_price));
//            report_parent_linear_layout.addView(inner_view);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_report_fixed_assests_list, null);
//        report_linear_layout = view.findViewById(R.id.report_linear_layout);
        report_parent_linear_layout = view.findViewById(R.id.report_parent_linear_layout);


        loadFinanceList();

        return view;
    }
}
