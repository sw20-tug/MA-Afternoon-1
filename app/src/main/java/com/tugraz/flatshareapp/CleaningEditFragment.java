package com.tugraz.flatshareapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.tugraz.flatshareapp.database.CleaningRepository;
import com.tugraz.flatshareapp.database.Models.Cleaning;
import com.tugraz.flatshareapp.database.Models.Roommate;
import com.tugraz.flatshareapp.database.RoommateRepository;
import com.tugraz.flatshareapp.utility.Persistence;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class CleaningEditFragment extends Fragment {

    Button task_close, task_save, task_delete;

    Switch frequency_switch;
    Switch done_switch;

    TextView frequency_text;

    EditText description;

    Spinner roommate_selection;

    Cleaning task;
    CleaningRepository clean_repo;
    RoommateRepository roommate_repo;

    CleaningEditFragment(CleaningRepository clean_repo, Cleaning task, RoommateRepository roommate_repo) {
        this.clean_repo = clean_repo;
        this.roommate_repo = roommate_repo;
        this.task = task;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_cleaning_edit, null);

        task_close  = (Button) view.findViewById(R.id.button_cleaning_schedule_edit_close);
        task_save  = (Button) view.findViewById(R.id.button_cleaning_schedule_edit_save);
        task_delete  = (Button) view.findViewById(R.id.button_cleaning_schedule_edit_delete);

        description = view.findViewById(R.id.cleaning_description);

        frequency_switch = (Switch) view.findViewById(R.id.switch_cleaning_schedule_edit_frequency);
        frequency_text = (TextView) view.findViewById(R.id.text_cleaning_schedule_edit_frequency);

        done_switch = view.findViewById(R.id.done_switch);

        roommate_selection = (Spinner) view.findViewById(R.id.spinner_cleaning_schedule_edit_roommate);

        if(task != null) {
            description.setText(task.getDescription());
            frequency_switch.setChecked(task.isWeekly());
            frequency_text.setText(task.isWeekly() ? R.string.cleaning_edit_date_frequency_weekly : R.string.cleaning_edit_date_frequency_monthly);
            done_switch.setChecked(task.isCompleted());
        }

        //TODO fill spinner with roommates
        List<Roommate> rommates = new ArrayList<>();
        try {
            rommates = roommate_repo.getAllRoommates();
        } catch (Exception e) {
            e.printStackTrace();
        }

        ArrayAdapter<Roommate> adapter = null;
        if(this.getActivity() != null && !rommates.isEmpty()) {
            adapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_item, rommates);
            adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
            roommate_selection.setAdapter(adapter);
            if(task != null) {
                try {
                    roommate_selection.setSelection(adapter.getPosition(roommate_repo.get(task.getRoommateId())));
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }


        task_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        task_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cleaning cleaning;
                boolean weekly = "Weekly".contentEquals(frequency_text.getText());
                if(task == null) {
                    cleaning = new Cleaning(weekly, 0, description.getText().toString()
                            , done_switch.isChecked(), ((Roommate)roommate_selection.getSelectedItem()).getId(),  Persistence.Instance().getActiveFlatID());
                    clean_repo.insert(cleaning);
                } else {
                    cleaning = new Cleaning(weekly, task.getDoneTimestamp(), description.getText().toString()
                            , done_switch.isChecked(), ((Roommate)roommate_selection.getSelectedItem()).getId(),  Persistence.Instance().getActiveFlatID());
                    cleaning.setId(task.getId());
                    clean_repo.update(cleaning);
                }

                Toast.makeText(getActivity(), "Saved!", Toast.LENGTH_SHORT).show();
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
