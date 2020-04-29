package com.tugraz.flatshareapp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
//import android.os.PersistableBundle;

import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.tugraz.flatshareapp.database.FlatRepository;
import com.tugraz.flatshareapp.database.Models.Flat;

import java.util.ArrayList;
import java.util.List;

public class FlatListActivity  extends FragmentActivity {

    Button btn_add_flat;
    LinearLayout flat_list;

    FlatRepository flatData;
    String flatName;
    Boolean flatActive;
    List<Integer> flat_ids = new ArrayList<>();

    FragmentManager fragmentManager;
    Context context;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flat_list);

        flatData = new FlatRepository(getApplication());
        context = this;

        fragmentManager = getSupportFragmentManager();

        btn_add_flat  = (Button) findViewById(R.id.flat_list_entry_flat_add);

        flat_list = (LinearLayout) findViewById(R.id.linear_layout_flat_list_list);

        btn_add_flat.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                FlatListDetailFragment fragment = new FlatListDetailFragment();
                fragmentTransaction.add(R.id.fragment_container, fragment);
                fragmentTransaction.addToBackStack("").commit();
            }
        });

        try {
            List<Flat> allFlatdata = flatData.getAllFlats();

            for (Flat flatInv : allFlatdata) {

                View view = LayoutInflater.from(context).inflate(R.layout.template_flat_list, null);
//                view.findViewById()
                flat_list.addView(view);

                flat_ids.add(flatInv.getId());

                flatName = flatInv.getName();
                flatActive = flatInv.getActive();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
