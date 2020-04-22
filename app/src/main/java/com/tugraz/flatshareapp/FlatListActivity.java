package com.tugraz.flatshareapp;

import android.os.Bundle;
//import android.os.PersistableBundle;

import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.tugraz.flatshareapp.database.FlatRepository;
import com.tugraz.flatshareapp.database.Models.Flat;

import java.util.List;

public class FlatListActivity  extends FragmentActivity {
    FlatRepository flatData;
    String flatName;
    Boolean flatActive;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flat_list);

        //
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        FlatListDetailFragment fragment = new FlatListDetailFragment();
        fragmentTransaction.add(R.id.fragment_container, fragment);
        fragmentTransaction.commit();

        try {
            List<Flat> allFlatdata = flatData.getAllFlats();

            for (Flat flatInv : allFlatdata) {
                flatName = flatInv.getName();
                flatActive = flatInv.getActive();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
