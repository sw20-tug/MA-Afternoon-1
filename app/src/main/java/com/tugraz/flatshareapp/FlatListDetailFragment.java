package com.tugraz.flatshareapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.tugraz.flatshareapp.database.FlatRepository;
import com.tugraz.flatshareapp.database.Models.Flat;

public class FlatListDetailFragment extends Fragment {

    Button flat_close, flat_save;

    TextView tv_flat_name, tv_street_name, tv_street_number, tv_city, tv_country;

    Flat flat;

    FlatRepository flat_repo;

    boolean created;

    FlatListDetailFragment (Flat flat, FlatRepository repo) {
        this.flat = flat;
        this.flat_repo = repo;

        if(flat != null)
        {
            created = true;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_flat_list_detail, null);

        flat_close  = (Button) view.findViewById(R.id.btn_flat_detail_close);
        flat_save = (Button) view.findViewById(R.id.button_flatlist_detail_edit);

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
                    created = true;
                }
                else
                {
                    flat_repo.update(new_flat);
                }
            }
        });

        return view;
    }

}
