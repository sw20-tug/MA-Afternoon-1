package com.tugraz.flatshareapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class RoommatesActivity extends AppCompatActivity {

    Button btn_add_room_mates;
    FragmentTransaction frame_transaction;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roommates);

        btn_add_room_mates = (Button) findViewById(R.id.btn_roommates_add);
        final FragmentManager manager = getSupportFragmentManager();

        btn_add_room_mates.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Fragment detail_fragment = new RoommatesDetailFragment();
                frame_transaction = manager.beginTransaction();
                frame_transaction.add(R.id.detail_fragment_container, detail_fragment);
                frame_transaction.addToBackStack("").commit();
            }
        });
    }
}
