package com.tugraz.flatshareapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class RoommatesActivity extends AppCompatActivity {

    Button btn_add_room_mates;
    Fragment detail_frag;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roommates);
        btn_add_room_mates = (Button) findViewById(R.id.btn_roommates_add);
        detail_frag = findViewById(R.id.detail_frag);
        btn_add_room_mates.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
            }
        });
    }
}
