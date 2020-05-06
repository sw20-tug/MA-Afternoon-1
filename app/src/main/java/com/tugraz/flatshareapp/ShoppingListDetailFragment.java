package com.tugraz.flatshareapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.tugraz.flatshareapp.database.Models.ShoppingList;
import com.tugraz.flatshareapp.database.ShoppingListRepository;
import com.tugraz.flatshareapp.utility.Persistence;

import java.util.List;

public class ShoppingListDetailFragment extends Fragment {
    private Button shopping_list_save, shopping_list_close, shopping_list_delete;
    private String prodName, prodDesc;
    private ShoppingListRepository shoppingListRepo;
    private ShoppingList shoppingList;
    private TextView productName, productDesc;
    CheckBox prodCompleted;
    Boolean prodStatus = false;

    ShoppingListDetailFragment(ShoppingList shoppingList, ShoppingListRepository shoppingListRepo){
        this.shoppingList = shoppingList;
        this.shoppingListRepo = shoppingListRepo;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view =  inflater.inflate(R.layout.fragment_shopping_list_detail, null);
        productName = view.findViewById(R.id.input_shopping_list_product_name);
        productDesc = view.findViewById(R.id.input_shopping_list_product_description);
        prodCompleted = view.findViewById(R.id.input_shopping_list_product_completed);

//        shopping_list_save  = (Button) view.findViewById(R.id.button_shopping_detail_save);
        shopping_list_save  = (Button) view.findViewById(R.id.btn_shopping_detail_save);
        shopping_list_close = (Button) view.findViewById(R.id.btn_shopping_detail_back);
        shopping_list_delete = (Button) view.findViewById(R.id.btn_shopping_detail_delete);

        if(shoppingList != null){
            productName.setText(shoppingList.getName());
            productDesc.setText(shoppingList.getDescription());
            prodCompleted.setChecked(shoppingList.getCompleted());
        }

        shopping_list_save.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

//                Log.e("","shopping list name " + shoppingList.getName());
                prodName = productName.getText().toString();
                prodDesc = productDesc.getText().toString();

                if(prodCompleted.isChecked())
                    prodStatus = true;
                ShoppingList updateShoppingList = new ShoppingList(prodName, prodDesc, prodStatus, Persistence.Instance().getActiveFlatID());
                if(shoppingList == null) {
                    if(!prodName.isEmpty()) {
                        shoppingListRepo.insert(updateShoppingList);
                    }
                }
                else {
                    if(!prodName.isEmpty()) {
                        updateShoppingList.setId(shoppingList.getId());
                        shoppingListRepo.update(updateShoppingList);
                    }
                }
            }
        });

        shopping_list_close.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        shopping_list_delete.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                prodName = productName.getText().toString();
                prodDesc = productDesc.getText().toString();

                if(prodCompleted.isChecked())
                    prodStatus = true;

                ShoppingList updateShoppingList = new ShoppingList(prodName, prodDesc, prodStatus, Persistence.Instance().getActiveFlatID());
                updateShoppingList.setId(shoppingList.getId());
                shoppingListRepo.delete(updateShoppingList);
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
