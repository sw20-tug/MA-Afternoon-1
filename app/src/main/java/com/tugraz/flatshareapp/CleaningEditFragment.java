package com.tugraz.flatshareapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.tugraz.flatshareapp.database.CleaningRepository;
import com.tugraz.flatshareapp.database.Models.Cleaning;

import org.w3c.dom.Text;

public class CleaningEditFragment extends Fragment {

    Button task_close, task_save, task_delete;

    Switch frequency_switch;

    TextView frequency_text;

    Spinner roommate_selection;

    Cleaning task;
    CleaningRepository clean_repo;

    CleaningEditFragment(CleaningRepository clean_repo, Cleaning task) {
        this.clean_repo = clean_repo;
        this.task = task;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_cleaning_edit, null);

        task_close  = (Button) view.findViewById(R.id.button_cleaning_schedule_edit_close);
        task_save  = (Button) view.findViewById(R.id.button_cleaning_schedule_edit_save);
        task_delete  = (Button) view.findViewById(R.id.button_cleaning_schedule_edit_delete);

        frequency_switch = (Switch) view.findViewById(R.id.switch_cleaning_schedule_edit_frequency);
        frequency_text = (TextView) view.findViewById(R.id.text_cleaning_schedule_edit_frequency);

        roommate_selection = (Spinner) view.findViewById(R.id.spinner_cleaning_schedule_edit_roommate);

        //TODO fill spinner with roommates

        task_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        task_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        task_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(task != null) {
                    new AlertDialog.Builder(getContext())
                            .setMessage(R.string.dialog_delete_flat)
                            .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    clean_repo.delete(task);
                                    getActivity().getSupportFragmentManager().popBackStack();
                                }
                            })
                            .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .create()
                            .show();
                }
            }
        });

        frequency_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    frequency_text.setText(R.string.cleaning_edit_date_frequency_monthly);
                else
                    frequency_text.setText(R.string.cleaning_edit_date_frequency_weekly);
            }
        });

        return view;
    }
}
