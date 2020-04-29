package com.tugraz.flatshareapp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

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
    String shoppingListName;
    Boolean shoppingListDone;
//    List<Integer> shopping_list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);

        shoppingList = new ShoppingListRepository(getApplication());
        context = this;

        fragmentManager = getSupportFragmentManager();

        btn_add_shopping_list  = (Button) findViewById(R.id.btn_shopping_list_add);

//        shopping_list = (LinearLayout) findViewById(R.id.linear_layout_flat_list_list);

        btn_add_shopping_list.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                ShoppingListDetailFragment fragment = new ShoppingListDetailFragment();
                fragmentTransaction.add(R.id.fragment_container, fragment);
                fragmentTransaction.addToBackStack("").commit();
            }
        });

        try {
            List<ShoppingList> allShoppingdata = shoppingList.getAllFlats();

            for (ShoppingList shopInv : allShoppingdata) {

                View view = LayoutInflater.from(context).inflate(R.layout.template_shopping_list, null);
//                view.findViewById()
//                flat_list.addView(view);

//                flat_ids.add(flatInv.getId());

                shoppingListName = shopInv.getName();
                shoppingListDone = shopInv.getCompleted();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
