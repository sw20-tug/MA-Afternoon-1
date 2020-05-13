package com.tugraz.flatshareapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.tugraz.flatshareapp.database.FinanceRepository;
import com.tugraz.flatshareapp.database.Models.Finance;
import com.tugraz.flatshareapp.database.Models.Flat;
import com.tugraz.flatshareapp.database.Models.Roommate;
import com.tugraz.flatshareapp.database.RoommateRepository;
import com.tugraz.flatshareapp.utility.Persistence;

import java.util.List;

public class FinancingFurnitureDetailFragment extends Fragment {

    private Button btn_close, btn_save;
    private TextView item_name, item_price, current_roomate_id;
    FinanceRepository finance_repo;
    LinearLayout linear_roomate_list;
    List<Roommate> roommate_list;
    List<Integer> checked_roommates;
    CheckBox current_roomate;
    Integer current_flat_id = Persistence.Instance().getActiveFlatID();

    FinancingFurnitureDetailFragment(List<Roommate> roomate_list, FinanceRepository finance_repo){
        this.roommate_list = roomate_list;
        this.finance_repo = finance_repo;
    }

    public void loadRoomateList(){
        linear_roomate_list.removeAllViews();
        try {
            for (final Roommate roommateInv : roommate_list) {
                if (roommateInv.getFlatId() == current_flat_id) {

                    View view = LayoutInflater.from(getActivity()).inflate(R.layout.template_financing_furniture_list, null);

                    CheckBox name = view.findViewById(R.id.financing_furniture_roomate_list);
                    TextView roommate_id = view.findViewById(R.id.financing_furniture_template_roomate_id);


                    String complete_name = roommateInv.getName() + " " + roommateInv.getLastName();
                    name.setText(complete_name);

                    roommate_id.setText(Integer.toString(roommateInv.getId()));
                    linear_roomate_list.addView(view);
                }
//                roommates_id.add(roommateInv.getId());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_financing_furniture_detail, null);
        linear_roomate_list = view.findViewById(R.id.linear_layout_financing_furniture_roommate_list);

        for(Roommate r : roommate_list){
            if(r.getFlatId() != current_flat_id){
                roommate_list.remove(r);
            }
        }
        loadRoomateList();

        btn_close = view.findViewById(R.id.button_financing_furniture_detail_close);
        btn_save = view.findViewById(R.id.btn_financing_furniture_detail_save);
        item_name = view.findViewById(R.id.input_financing_furniture_detail_item_name);
        item_price = view.findViewById(R.id.input_financing_furniture_detail_item_price);

        btn_close.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Integer cur_id = null;
                //Fetch the checked list
                //error while adding the checked roommates to list
//                for(int i=0; i<linear_roomate_list.getChildCount(); i++){
//                    View view = linear_roomate_list.getChildAt(i);
//                    if(view.findViewById(R.id.financing_furniture_template_roomate_id) != null) {
//                        current_roomate_id = view.findViewById(R.id.financing_furniture_template_roomate_id);
//                        cur_id = Integer.parseInt(current_roomate_id.getText().toString());
//                    }
//                    if(view.findViewById(R.id.financing_furniture_roomate_list) != null) {
//                        current_roomate = view.findViewById(R.id.financing_furniture_roomate_list);
//
//                        if (current_roomate.isChecked())
//                            if(cur_id != null)
//                                checked_roommates.add(cur_id);
//                    }
//                }

                //Divide the price among checked list
                if(!checked_roommates.isEmpty()){
                    float price = Float.parseFloat(item_price.getText().toString());
                    float indv_price = price / (float)checked_roommates.size();

                    for(int cur_roommate_id: checked_roommates)
                        finance_repo.insert(new Finance(item_name.getText().toString(), indv_price,  cur_roommate_id, current_flat_id));
                }
            }
        });

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
