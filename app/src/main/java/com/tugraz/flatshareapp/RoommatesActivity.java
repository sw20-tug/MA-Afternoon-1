package com.tugraz.flatshareapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.tugraz.flatshareapp.database.Models.Flat;
import com.tugraz.flatshareapp.database.Models.Roommate;
import com.tugraz.flatshareapp.database.RoommateRepository;

import java.util.ArrayList;
import java.util.List;

public class RoommatesActivity extends AppCompatActivity {

    Button btn_add_room_mates;
    FragmentTransaction frame_transaction;
    RoommateRepository roommate_repository;
    LinearLayout roommates_list;
    List<Integer> roommates_id;


    public void loadRoommateList()
    {
        roommates_list.removeAllViews();
        try {
            List<Roommate> allRoommates = roommate_repository.getAllRoommates();

            for (Roommate roommateInv : allRoommates) {

                View view = LayoutInflater.from(this).inflate(R.layout.template_roommate_list, null);
                TextView name = view.findViewById(R.id.roommate_template_name);
                String complete_name = roommateInv.getName() + " " +roommateInv.getLastName();
                name.setText(complete_name);
                roommates_list.addView(view);

                roommates_id.add(roommateInv.getId());

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roommates);
        roommate_repository = new RoommateRepository(getApplication());
        btn_add_room_mates = (Button) findViewById(R.id.btn_roommates_add);
        final FragmentManager manager = getSupportFragmentManager();
        roommates_id = new ArrayList<>();
        roommates_list = findViewById(R.id.linear_layout_roommates_list);
        btn_add_room_mates.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Fragment detail_fragment = new RoommatesDetailFragment(roommate_repository);
                frame_transaction = manager.beginTransaction();
                frame_transaction.add(R.id.detail_fragment_container, detail_fragment);
                frame_transaction.addToBackStack("").commit();
            }
        });
        manager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                loadRoommateList();
            }
        });

       loadRoommateList();
    }
}
