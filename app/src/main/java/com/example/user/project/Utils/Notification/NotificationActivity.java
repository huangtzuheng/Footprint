package com.example.user.project.Utils.Notification;

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

import com.example.user.project.Item;
import com.example.user.project.ItemDAO;
import com.example.user.project.R;
import com.example.user.project.ThingDAO;
import com.example.user.project.Utils.Cart.CartAdapter;
import com.example.user.project.Utils.Detail.DetailActivity;
import com.example.user.project.Utils.Detail.SellActivity;
import com.example.user.project.Utils.Utils.BottomNavigationViewHelper;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.ArrayList;

public class NotificationActivity extends AppCompatActivity implements NotifAdapter.NotifAdapterOnClickHandler {
    private static final String TAG = "NotificationActivity";
    private static final int ACTIVITY_NUM = 2;
    private Context mContext = NotificationActivity.this;
    private RecyclerView mRecyclerView;
    private NotifAdapter mNotifAdapter;
    public String[] testDataC = {"", "", ""};

    SharedPreferences prefs;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        Log.d(TAG, "onCreate: starting.");

        setupBottomNavigationView();
        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_notif);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);


        Button bt_refresh = (Button) findViewById(R.id.notif_Recycle_Refresh);
        bt_refresh.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Item> notif = getNotifCookie();

                refresh(notif);


//                finish();
//                Log.i("XXXX","XXXX");
//                Toast toast = Toast.makeText(v.getContext(),"XXX", Toast.LENGTH_SHORT);
//                toast.show();
            }
        });

        //================== Get Cart Item Data ===================
        ArrayList<Item> notif = getNotifCookie();
        if (notif != null) {

            mNotifAdapter = new NotifAdapter(notif, this);
            mRecyclerView.setAdapter(mNotifAdapter);
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
        Intent sellIntent = new Intent(this, SellActivity.class);
        sellIntent.putExtra(Intent.EXTRA_TEXT, thisGood);
        startActivity(sellIntent);
    }

    public ArrayList<Item> getNotifCookie() {
        ArrayList<Item> notifList = new ArrayList<Item>();


        String NotifList = prefs.getString("NotifList", "");
        Log.i("NotifList", NotifList);

        if (NotifList.length() > 0) {
            ThingDAO itemTable = new ThingDAO(this);
            String NotifArray[] = NotifList.split(",");

            for (String thisID_Str : NotifArray) {
                Long thisID_Long = Long.valueOf(thisID_Str);
                Item thisItem = itemTable.get(thisID_Long);
                notifList.add(thisItem);
            }

            return notifList;
        } else {
            return null;
        }


    }

    public void refresh(ArrayList<Item> notif) {
        mNotifAdapter = new NotifAdapter(notif, this);
        mRecyclerView.setAdapter(mNotifAdapter);
    }
}


