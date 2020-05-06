package com.tugraz.flatshareapp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
//import android.os.PersistableBundle;

import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.tugraz.flatshareapp.database.FlatRepository;
import com.tugraz.flatshareapp.database.Models.Flat;
import com.tugraz.flatshareapp.utility.Persistence;

import java.util.ArrayList;
import java.util.List;

public class FlatListActivity  extends FragmentActivity {

    Button btn_add_flat;
    LinearLayout flat_list;

    FlatRepository flatData;
    String flatName;
    Boolean flatActive;
    FragmentManager fragmentManager;
    Context context;
    List<Flat> allFlatdata;

    public void LoadFlatList()
    {
        flat_list.removeAllViews();

        try {
            allFlatdata = flatData.getAllFlats();

            for (final Flat flatInv : allFlatdata) {

                View view = LayoutInflater.from(context).inflate(R.layout.template_flat_list, null);
//                view.findViewById()
                flat_list.addView(view);

                flatName = flatInv.getName();
                flatActive = flatInv.getActive();

                TextView name = view.findViewById(R.id.flat_list_flat_name);
                name.setText(flatInv.getName());

                TextView active = view.findViewById(R.id.flat_list_flat_active);

                if(flatInv.getActive())
                {
                    active.setVisibility(View.VISIBLE);
                }
                else
                {
                    active.setVisibility(View.INVISIBLE);
                }

                view.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                        FlatListDetailFragment fragment = new FlatListDetailFragment(flatInv, flatData);
                        fragmentTransaction.add(R.id.fragment_container, fragment);
                        fragmentTransaction.addToBackStack("").commit();
                    }
                });

                view.setLongClickable(true);
                view.setOnLongClickListener(new View.OnLongClickListener() {

                    @Override
                    public boolean onLongClick(View view) {

                        int id = flatInv.getId();

                        for(Flat f : allFlatdata)
                        {
                            if(f.getId() == id)
                            {
                                f.setActive(true);
                            }
                            else
                            {
                                f.setActive(false);
                            }

                            flatData.update(f);
                        }

                        Persistence.Instance().setActiveFlatID(id);

                        LoadFlatList();

                        return true;
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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

                FlatListDetailFragment fragment = new FlatListDetailFragment(null, flatData);
                fragmentTransaction.add(R.id.fragment_container, fragment);
                fragmentTransaction.addToBackStack("").commit();
            }
        });

        fragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                LoadFlatList();
            }
        });

        LoadFlatList();
    }



}
