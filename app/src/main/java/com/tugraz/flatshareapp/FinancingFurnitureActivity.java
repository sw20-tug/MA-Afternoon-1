package com.tugraz.flatshareapp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.tugraz.flatshareapp.database.FinanceRepository;
import com.tugraz.flatshareapp.database.FlatRepository;
import com.tugraz.flatshareapp.database.Models.Finance;
import com.tugraz.flatshareapp.database.Models.Roommate;
import com.tugraz.flatshareapp.database.RoommateRepository;
import com.tugraz.flatshareapp.utility.Persistence;

import java.util.List;

public class FinancingFurnitureActivity  extends FragmentActivity {
    Context context;
    FragmentManager fragmentManager;
    Button btn_financing_furniture_add;
    LinearLayout financing_list;
    RoommateRepository roomate_repo;
    List<Roommate> roomate_list;
    List<Finance> finance_list;
    FinanceRepository finance_repo;
    TextView roommate_name, price, item;
    int curr_flat_id = Persistence.Instance().getActiveFlatID();

    public void LoadFinancingList(){
        financing_list.removeAllViews();
        try{
            finance_list = finance_repo.getAllFinances();
            for(Finance f : finance_list){
                View view = LayoutInflater.from(this).inflate(R.layout.template_financing_furniture_detail_list, null);
                roommate_name = view.findViewById(R.id.financing_furniture_roommate_name);
                price  = view.findViewById(R.id.financing_furniture_price);
                item  = view.findViewById(R.id.financing_furniture_item);

                String cur_roommate = "";
                for(Roommate r : roomate_list) {
                    if (curr_flat_id == r.getFlatId() && r.getId() == f.getRoommateId())
                        cur_roommate = r.getName() + " " + r.getLastName();
                }
                roommate_name.setText(cur_roommate);
                price.setText(String.format("%.2f",f.getValue()));
                price.setEnabled(false);
                item.setText(f.getDescription());

                financing_list.addView(view);
            }

        }
        catch (Exception e){

        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_financing_furniture_list);

        finance_repo = new FinanceRepository(getApplication());
        roomate_repo = new RoommateRepository(getApplication());
        try {
            roomate_list = roomate_repo.getAllRoommates();
        } catch (Exception e) {
//            e.printStackTrace();
        }
        context = this;

        fragmentManager = getSupportFragmentManager();

        btn_financing_furniture_add  = (Button) findViewById(R.id.btn_financing_furniture_list_add);

        financing_list = (LinearLayout) findViewById(R.id.linear_layout_financing_furniture_list_list);

        btn_financing_furniture_add.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                FinancingFurnitureDetailFragment fragment = new FinancingFurnitureDetailFragment(roomate_list, finance_repo);
                fragmentTransaction.add(R.id.fragment_container, fragment);
                fragmentTransaction.addToBackStack("").commit();
            }
        });

        fragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                LoadFinancingList();
            }
        });

        LoadFinancingList();
    }
}
