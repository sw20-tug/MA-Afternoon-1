package com.tugraz.flatshareapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FlatListDetailFragment extends Fragment {

    Button flat_save;

    FlatListDetailFragment () {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_flat_list_detail, null);

        flat_save  = (Button) view.findViewById(R.id.btn_flat_detail_save);

        flat_save.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        return view;
    }

}
