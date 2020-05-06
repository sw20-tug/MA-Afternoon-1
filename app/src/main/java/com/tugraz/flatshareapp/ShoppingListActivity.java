package com.tugraz.flatshareapp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

//import com.tugraz.flatshareapp.database.FlatRepository;
import com.tugraz.flatshareapp.database.Models.Flat;
import com.tugraz.flatshareapp.database.Models.ShoppingList;
import com.tugraz.flatshareapp.database.ShoppingListRepository;

import java.util.List;

public class ShoppingListActivity extends AppCompatActivity {

    ShoppingListRepository shoppingList;
    Context context;
    FragmentManager fragmentManager;
    Button btn_add_shopping_list;
    LinearLayout layoutShoppingList;
    String shoppingListName;
    Boolean shoppingListDone;

    public void DisplayShoppingList(){
        layoutShoppingList.removeAllViews();
        try {
            List<ShoppingList> allShoppingdata = shoppingList.getAllFlats();

            for (final ShoppingList shopInv : allShoppingdata) {

                View view = LayoutInflater.from(context).inflate(R.layout.template_shopping_list, null);
                layoutShoppingList.addView(view);

                TextView tempProdName = view.findViewById(R.id.shopping_list_template_name);
                tempProdName.setText(shopInv.getName());

                CheckBox tempProdStatus = view.findViewById(R.id.shopping_list_completed);
                tempProdStatus.setEnabled(false);
                tempProdStatus.setChecked(shopInv.getCompleted());

                view.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                        ShoppingListDetailFragment fragment = new ShoppingListDetailFragment(shopInv, shoppingList);
                        fragmentTransaction.add(R.id.fragment_container, fragment);
                        fragmentTransaction.addToBackStack("").commit();
                    }
                });

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);

        shoppingList = new ShoppingListRepository(getApplication());
        context = this;

        fragmentManager = getSupportFragmentManager();

        btn_add_shopping_list  = (Button) findViewById(R.id.btn_shopping_list_add);
        layoutShoppingList = findViewById(R.id.linear_layout_shopping_list_list);

//        shopping_list = (LinearLayout) findViewById(R.id.linear_layout_flat_list_list);

        btn_add_shopping_list.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                ShoppingListDetailFragment fragment = new ShoppingListDetailFragment(null, shoppingList);
                fragmentTransaction.add(R.id.fragment_container, fragment);
                fragmentTransaction.addToBackStack("").commit();
            }
        });

        fragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                DisplayShoppingList();
            }
        });

        DisplayShoppingList();
    }
}
