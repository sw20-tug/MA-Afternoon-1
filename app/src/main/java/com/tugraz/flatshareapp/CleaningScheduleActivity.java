package com.tugraz.flatshareapp;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.tugraz.flatshareapp.database.CleaningRepository;
import com.tugraz.flatshareapp.database.FlatRepository;
import com.tugraz.flatshareapp.database.Models.Cleaning;
import com.tugraz.flatshareapp.database.Models.Roommate;
import com.tugraz.flatshareapp.database.RoommateRepository;
import com.tugraz.flatshareapp.utility.Persistence;

import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class CleaningScheduleActivity extends AppCompatActivity {

    Button btn_add_task;

    CleaningRepository clean_repo;
    RoommateRepository roommate_repo;
    FlatRepository flat_repo;

    FragmentManager fragmentManager;
    Context context;

    LinearLayout cleaning_list;
    Spinner spinner_export;

    private String file_content = "";
    private static int UID = 0;

    private boolean ignore_spinner_selection = false;

    private String getDayOfWeek(int value) {
        switch (value) {
            case 0: return "SU";
            case 1: return "MO";
            case 2: return "TU";
            case 3: return "WE";
            case 4: return "TH";
            case 5: return "FR";
            case 6: return "SA";
            default: return "MO";
        }
    }

    private void addLine(String line) {
        file_content += line + "\r\n";
    }

    public String pad(int num) {
        if(num <= 9)
            return "0" + num;
        else
            return "" + num;
    }

    private String getDateString(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);

        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int day = c.get(Calendar.DAY_OF_MONTH);
        int hour = c.get(Calendar.HOUR);
        int minute = c.get(Calendar.MINUTE);
        int second = c.get(Calendar.SECOND);

        return year + pad(month) + pad(day) + "T" + pad(hour) + pad(minute) + pad(second);
    }

    private class CalendarExport {

        public int id;
        public String name;

        CalendarExport(int id, String firstname, String lastname, boolean export_all) {

            this.id = id;

            if(export_all)
                this.name = getResources().getString(R.string.cleaning_export_all);
            else
                this.name = firstname + " " + lastname;
        }

        @NonNull
        @Override
        public String toString() {
            return this.name;
        }
    }

    private ArrayList<CalendarExport> loadFlatRoommates() throws Exception {

        ArrayList<CalendarExport> list = new ArrayList<>();
        list.add(new CalendarExport(-1, "", "", true));

        for (Roommate rm : roommate_repo.getAllRoommates()){
            if(rm.getFlatId() == Persistence.Instance().getActiveFlatID()) {
                list.add(new CalendarExport(rm.getId(), rm.getName(), rm.getLastName(), false));
            }
        }

        return list;
    }

    private void addEventToCal(int roommate_id) throws Exception {
        for (Cleaning clean : clean_repo.getAllCleanings()) {
            if(clean.getFlatId() != Persistence.Instance().getActiveFlatID() || clean.getRoommateId() != roommate_id)
                continue;

            // add event to calendar
            addLine("BEGIN:VEVENT");

            Date my_date = new Date(clean.getDoneTimestamp());

            addLine("SUMMARY:" + clean.getDescription());
            addLine("DTSTART:" + getDateString(my_date));
            addLine("DTEND:" + getDateString(my_date));
            addLine("DTSTAMP:" + getDateString(my_date));

            addLine("UID:" + UID++ + "@maafternoon1");

            addLine("RRULE:" +
                    "FREQ=" + ( clean.isWeekly() ? "WEEKLY" : "MONTLY" ) + ";" +
                    "UNTIL=" + "20271231T090000"
            );

            addLine("END:VEVENT");
        }
    }


    public void loadCleaningScheduleList()
    {
        cleaning_list.removeAllViews();
        try {
            List<Cleaning> allCleanings = clean_repo.getAllCleanings();

            for (final Cleaning cleaning : allCleanings) {

                if(cleaning.getFlatId() != Persistence.Instance().getActiveFlatID())
                    continue;

                resetSchedule(cleaning);

                View view = LayoutInflater.from(this).inflate(R.layout.template_cleaning_schedule_list, null);
                TextView name = view.findViewById(R.id.rommate_name);
                TextView description = view.findViewById(R.id.cleaning_description);

                Button editButton = view.findViewById(R.id.cleaning_edit_btn);

                editButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final FragmentManager manager = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                        CleaningEditFragment fragment = new CleaningEditFragment(clean_repo, cleaning, roommate_repo);
                        fragmentTransaction.add(R.id.fragment_container, fragment);
                        fragmentTransaction.addToBackStack("").commit();
                    }
                });

                Roommate roommate = roommate_repo.get(cleaning.getRoommateId());

                if(roommate == null){
                    clean_repo.delete(cleaning);
                    continue;
                }


                String complete_name = roommate.getName() + " " + roommate.getLastName();
                name.setText(complete_name);
                description.setText(cleaning.getDescription());
                if(cleaning.isCompleted()) {
                    view.setBackgroundColor(Color.GREEN);
                }
                cleaning_list.addView(view);
//                roommates_id.add(roommateInv.getId());

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void resetSchedule(Cleaning cleaning) {
        if (cleaning.isCompleted()) {
            Date newDate = new Date(cleaning.getDoneTimestamp());

            GregorianCalendar calendar = new GregorianCalendar();
            calendar.setTime(newDate);
            if(cleaning.isWeekly()) {
                calendar.add(java.util.Calendar.DAY_OF_YEAR, 7);
            } else {
                calendar.add(java.util.Calendar.MONTH, 1);
            }

            newDate.setTime(calendar.getTime().getTime());

            if (newDate.getTime() < new Date().getTime()) {
                cleaning.setCompleted(false);
            }

            clean_repo.update(cleaning);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cleaning_schedule);

        clean_repo = new CleaningRepository(getApplication());
        roommate_repo = new RoommateRepository(getApplication());
        flat_repo = new FlatRepository(getApplication());

        context = this;

        cleaning_list = findViewById(R.id.cleaning_list);
        spinner_export = findViewById(R.id.spinner_cleaning_export);

        fragmentManager = getSupportFragmentManager();

        btn_add_task  = (Button) findViewById(R.id.btn_cleaning_schedule_add);

        btn_add_task.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                CleaningEditFragment fragment = new CleaningEditFragment(clean_repo, null, roommate_repo);
                fragmentTransaction.add(R.id.fragment_container, fragment);
                fragmentTransaction.addToBackStack("").commit();
            }
        });

        try {
            ArrayList values = loadFlatRoommates();
            ArrayAdapter<CalendarExport > adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, values);

            adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

            spinner_export.setAdapter(adapter);
            ignore_spinner_selection = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        spinner_export.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(android.widget.AdapterView<?> parent, View view, int position, long id) {
                //http://ical4j.sourceforge.net/introduction.html
                if(ignore_spinner_selection == true) {
                    ignore_spinner_selection = false;
                    return;
                }

                try {
                    List<Roommate> list_mate = roommate_repo.getAllRoommates();

                    addLine("BEGIN:VCALENDAR");
                    addLine("VERSION:2.0");
                    addLine("PRODID:flatshareapp");

                    String file_name = "";
                    String flat_name = flat_repo.getAllFlats().get(Persistence.Instance().getActiveFlatID()).getName();

                    if(position == 0) {
                        file_name = flat_name + R.string.cleaning_suffix_all;

                        for (Roommate mate : list_mate) {
                            if(mate.getFlatId() != Persistence.Instance().getActiveFlatID())
                                continue;

                            int mate_id = mate.getId();

                            addEventToCal(mate_id);
                        }
                    }
                    else {
                        int mate_id = ((CalendarExport)spinner_export.getSelectedItem()).id;
                        Roommate selected_mate = roommate_repo.get(mate_id);

                        file_name = flat_name + "_" + selected_mate.getName() + "_" + selected_mate.getLastName() + R.string.cleaning_suffix ;

                        addEventToCal(mate_id);
                    }

                    addLine("END:VCALENDAR");

                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(file_name + ".ics", Context.MODE_PRIVATE));
                    outputStreamWriter.write(file_content);
                    outputStreamWriter.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }

                spinner_export.setSelection(0);
                ignore_spinner_selection = true;
            }

            @Override
            public void onNothingSelected(android.widget.AdapterView<?> parent) {

            }
        });


        fragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                loadCleaningScheduleList();
            }
        });

        loadCleaningScheduleList();
    }
}
