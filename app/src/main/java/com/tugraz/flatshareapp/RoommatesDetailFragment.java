package com.tugraz.flatshareapp;

import android.content.Context;
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
import com.tugraz.flatshareapp.utility.Persistence;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RoommatesDetailFragment extends Fragment {

    Button save_button;
    View view;
    RoommateRepository roommate_repository;
    Roommate roommate;
    boolean edit;
    SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");

    // UI components
    TextView first_name_view;
    TextView last_name_view;
    EditText birthday_view;
    TextView roommate_since_view;



    public RoommatesDetailFragment(RoommateRepository roommate_repository) {
        this.roommate_repository = roommate_repository;
        edit = false;
        // Required empty public constructor
    }

    public RoommatesDetailFragment(RoommateRepository roommate_repository, Roommate roommate)
    {
        this.roommate_repository = roommate_repository;
        this.roommate = roommate;
        edit = true;
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

        first_name_view = view.findViewById(R.id.input_rommates_detail_first_name);
        last_name_view = view.findViewById(R.id.input_rommates_detail_last_name);
        birthday_view = view.findViewById(R.id.input_rommates_detail_birthday);
        roommate_since_view = view.findViewById(R.id.input_rommates_detail_roommates_since);


       if(edit) {
           first_name_view.setText(roommate.getName());
           last_name_view.setText(roommate.getLastName());
           birthday_view.setText(df.format(new Date(roommate.getBirthday())));
           roommate_since_view.setText(df.format(new Date(roommate.getRoomateSince())));
       }

       save_button = view.findViewById(R.id.btn_roomates_detail_save);

       save_button.setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View v) {
               String first_name, last_name, birthday, roommate_since;
               first_name = first_name_view.getText().toString();
               last_name = last_name_view.getText().toString();
               birthday = birthday_view.getText().toString();
               roommate_since = roommate_since_view.getText().toString();
               Date birthday_date = null;
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
                   //TODO: change faltID to the actual flatID
                   if(!edit && roommate == null)
                    roommate_repository.insert(new Roommate(first_name, last_name, birthday_date, roommate_since_date, Persistence.Instance().getActiveFlatID()));
                   else {
                       roommate.setName(first_name);
                       roommate.setLastName(last_name);
                       if(birthday_date != null)
                           roommate.setBirthday(birthday_date.getTime());
                       if(roommate_since_date != null)
                           roommate.setRoomateSince(roommate_since_date.getTime());

                       roommate_repository.update(roommate);
                   }
                   getActivity().getSupportFragmentManager().popBackStack();
               }

           }
       });
        return view;
    }
}
