package com.example.user.project.Utils.Cart;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.user.project.Item;
import com.example.user.project.ItemDAO;
import com.example.user.project.R;
import com.example.user.project.Utils.Detail.DetailActivity;
import com.example.user.project.Utils.Utils.BottomNavigationViewHelper;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity implements CartAdapter.CartAdapterOnClickHandler {
    private static final String TAG = "CartActivity";
    private static final int ACTIVITY_NUM = 3;
    private Context mContext = CartActivity.this;
    private RecyclerView mRecyclerView;
    private CartAdapter mCartAdapter;
    public String[] testDataC = {"", "", ""};

    SharedPreferences prefs;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        Log.d(TAG, "onCreate: starting.");

        setupBottomNavigationView();

        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_cart);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);


        Button bt_refresh = (Button) findViewById(R.id.cart_Recycle_Refresh);
        bt_refresh.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Item> cart = getCartCookie();

                refresh(cart);


//                finish();
//                Log.i("XXXX","XXXX");
//                Toast toast = Toast.makeText(v.getContext(),"XXX", Toast.LENGTH_SHORT);
//                toast.show();
            }
        });

        //================== Get Cart Item Data ===================
        ArrayList<Item> cart = getCartCookie();
        if (cart != null) {

            mCartAdapter = new CartAdapter(cart, this);
            mRecyclerView.setAdapter(mCartAdapter);
        } else {

        }


    }

    private void setupBottomNavigationView() {
        Log.d(TAG, "setupBottomNavigationView: setting up BottomNavigationView");
        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottomNavViewBar);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNavigation(mContext, bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }

    public void onClick(String thisGood) {
        Intent detailIntent = new Intent(this, DetailActivity.class);
        detailIntent.putExtra(Intent.EXTRA_TEXT, thisGood);
        startActivity(detailIntent);
    }

    public ArrayList<Item> getCartCookie() {
        ArrayList<Item> cartList = new ArrayList<Item>();


        String CartList = prefs.getString("CartList", "");
        Log.i("CartList", CartList);

        if (CartList.length() > 0) {
            ItemDAO itemTable = new ItemDAO(this);
            String CartArray[] = CartList.split(",");

            for (String thisID_Str : CartArray) {
                Long thisID_Long = Long.valueOf(thisID_Str);
                Item thisItem = itemTable.get(thisID_Long);
                cartList.add(thisItem);
            }

            return cartList;
        } else {
            return null;
        }


    }

    public void refresh(ArrayList<Item> cart) {
        mCartAdapter = new CartAdapter(cart, this);
        mRecyclerView.setAdapter(mCartAdapter);
    }


}


