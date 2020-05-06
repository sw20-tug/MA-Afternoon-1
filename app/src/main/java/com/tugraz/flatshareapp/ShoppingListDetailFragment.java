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

import com.tugraz.flatshareapp.database.Models.ShoppingList;
import com.tugraz.flatshareapp.database.ShoppingListRepository;

public class ShoppingListDetailFragment extends Fragment {
    Button shopping_list_save, shopping_list_close;
    ShoppingListRepository shoppingListRepo;

    ShoppingListDetailFragment(ShoppingListRepository shoppingListRepo){
        this.shoppingListRepo = shoppingListRepo;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view =  inflater.inflate(R.layout.fragment_shopping_list_detail, null);

//        shopping_list_save  = (Button) view.findViewById(R.id.button_shopping_detail_save);
        shopping_list_save  = (Button) view.findViewById(R.id.btn_shopping_detail_save);
        shopping_list_close = (Button) view.findViewById(R.id.btn_shopping_detail_back);

        shopping_list_save.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String prodName, prodDesc;
                TextView productName = view.findViewById(R.id.input_shopping_list_product_name);
                prodName = productName.getText().toString();
                TextView productDesc = view.findViewById(R.id.input_shopping_list_product_description);
                prodDesc = productDesc.getText().toString();

                if(!prodName.isEmpty()) {
                    shoppingListRepo.insert(new ShoppingList(prodName, prodDesc, false, 1));
//                    getActivity().getSupportFragmentManager().popBackStack();
                }
            }
        });

        shopping_list_close.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
