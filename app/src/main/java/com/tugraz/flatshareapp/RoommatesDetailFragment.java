package com.tugraz.flatshareapp;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.tugraz.flatshareapp.database.Models.Roommate;
import com.tugraz.flatshareapp.database.RoommateRepository;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RoommatesDetailFragment extends Fragment {

    Button save_button;
    View view;
    RoommateRepository roommate_repository;
    public RoommatesDetailFragment(RoommateRepository roommate_repository) {
        this.roommate_repository = roommate_repository;
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
               String first_name, last_name, birthday, roommate_since;
               TextView first_name_view = view.findViewById(R.id.input_rommates_detail_first_name);
               first_name = first_name_view.getText().toString();
               TextView last_name_view = view.findViewById(R.id.input_rommates_detail_last_name);
               last_name = last_name_view.getText().toString();
               EditText birthday_view = view.findViewById(R.id.input_rommates_detail_birthday);
               birthday = birthday_view.getText().toString();
               TextView roommate_since_view = view.findViewById(R.id.input_rommates_detail_roommates_since);
               roommate_since = roommate_since_view.getText().toString();
               Date birthday_date = null;
               SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
               try {
                   birthday_date = df.parse(birthday);
               } catch (ParseException e) {
                   e.printStackTrace();
               }
               Date roommate_since_date = null;
               try {
                   roommate_since_date = df.parse(roommate_since);
               } catch (ParseException e) {
                   e.printStackTrace();
               }
               if(birthday_date != null || roommate_since_date != null) {
                   roommate_repository.insert(new Roommate(first_name, last_name, birthday_date, roommate_since_date, 1));
                   getActivity().getSupportFragmentManager().popBackStack();
               }

           }
       });
        return view;
    }
}
