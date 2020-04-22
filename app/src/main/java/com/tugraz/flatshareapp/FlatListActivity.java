package com.tugraz.flatshareapp;

import android.os.Bundle;
//import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.tugraz.flatshareapp.database.FlatRepository;
import com.tugraz.flatshareapp.database.Models.Flat;

import java.util.List;

public class FlatListActivity  extends AppCompatActivity {
    FlatRepository flatData;
    String flatName;
    Boolean flatActive;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flat_list);
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
