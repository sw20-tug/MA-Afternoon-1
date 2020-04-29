package com.tugraz.flatshareapp;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class RoommatesDetailFragment extends Fragment {

    Button save_button;
    View view;
    public RoommatesDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       view = inflater.inflate(R.layout.fragment_roommates_detail, container, false);
       save_button = view.findViewById(R.id.btn_rommates_detail_save);
       save_button.setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View v) {
               getActivity().getSupportFragmentManager().popBackStack();
               String first_name, last_name, birthday, roommate_since;
           }
       });
        return view;
    }
}
