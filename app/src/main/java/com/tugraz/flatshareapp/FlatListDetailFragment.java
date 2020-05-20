package com.tugraz.flatshareapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.tugraz.flatshareapp.database.BillRepository;
import com.tugraz.flatshareapp.database.FlatRepository;
import com.tugraz.flatshareapp.database.Models.Bill;
import com.tugraz.flatshareapp.database.Models.Flat;

public class FlatListDetailFragment extends Fragment {

    Button flat_close, flat_save, flat_delete;

    TextView tv_flat_name, tv_street_name, tv_street_number, tv_city, tv_country;

    Flat flat;

    FlatRepository flat_repo;

    BillRepository bill_repo;

    boolean created;

    FlatListDetailFragment (Flat flat, FlatRepository repo, BillRepository bill_repo) {
        this.flat = flat;
        this.flat_repo = repo;
        this.bill_repo = bill_repo;

        if(flat != null)
        {
            created = true;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_flat_list_detail, null);

//        bill_repo = new BillRepository();

        flat_close  = (Button) view.findViewById(R.id.btn_flat_detail_close);
        flat_save = (Button) view.findViewById(R.id.button_flatlist_detail_edit);
        flat_delete = (Button) view.findViewById(R.id.button_flat_list_detail_delete);

        tv_flat_name = (TextView) view.findViewById(R.id.input_flat_list_product_name);
        tv_street_name = (TextView) view.findViewById(R.id.input_flat_list_street_name);
        tv_street_number = (TextView) view.findViewById(R.id.input_flat_list_street_number);
        tv_city = (TextView) view.findViewById(R.id.input_flat_list_city);
        tv_country = (TextView) view.findViewById(R.id.input_flat_list_country);

        if(flat != null)
        {
            tv_flat_name.setText(flat.getName());
            tv_street_name.setText(flat.getStreetName());
            tv_street_number.setText(flat.getStreetNumber());
            tv_city.setText(flat.getCity());
            tv_country.setText(flat.getCountry());
        }

        flat_close.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        flat_save.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                boolean active = false;

                if(flat != null)
                {
                    active = flat.getActive();
                }

                Flat new_flat = new Flat(tv_flat_name.getText().toString(),
                        tv_street_name.getText().toString(),
                        tv_street_number.getText().toString(),
                        tv_city.getText().toString(),
                        tv_country.getText().toString(),
                        false);

                if(flat != null)
                {
                    new_flat.setId(flat.getId());
                }

                if(!created)
                {
                    flat_repo.insert(new_flat);
                    if(bill_repo != null) {
                        //add bills for the new flat initialised to zero
                        bill_repo.insert(new Bill("Rental fee", 0, true, new_flat.getId()));
                        bill_repo.insert(new Bill("Smartphone bill", 0, true, new_flat.getId()));
                        bill_repo.insert(new Bill("Internet bill", 0, true, new_flat.getId()));
                        bill_repo.insert(new Bill("TV bill", 0, true, new_flat.getId()));
                        bill_repo.insert(new Bill("Energy bill", 0, true, new_flat.getId()));
                    }
                    created = true;
                }
                else
                {
                    flat_repo.update(new_flat);
                }
            }
        });

        flat_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flat != null) {
                    new AlertDialog.Builder(getContext())
                            .setMessage(R.string.dialog_delete_flat)
                            .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    flat_repo.delete(flat);
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

        return view;
    }

}
