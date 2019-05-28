package com.example.user.project.Utils.Utils;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.MenuItem;

import com.example.user.project.R;
import com.example.user.project.Utils.Cart.CartActivity;
import com.example.user.project.Utils.Home.MainActivity;
import com.example.user.project.Utils.Notification.NotificationActivity;
import com.example.user.project.Utils.Profile.ProfileActivity;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class BottomNavigationViewHelper {
    private static final String TAG = "BottomNavigationViewHel";
    public static void setupBottomNavigationView(BottomNavigationViewEx bottomNavigationViewEx){
        Log.d(TAG, "setupBottomNavigationView: Setting up BottomNavigationView");
        bottomNavigationViewEx.enableAnimation(false);
        bottomNavigationViewEx.enableItemShiftingMode(false);
        bottomNavigationViewEx.enableShiftingMode(false);
        bottomNavigationViewEx.setTextVisibility(false);
    }
    public static void enableNavigation(final Context context, BottomNavigationViewEx view){
        view.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.ic_home:
                        Intent intent1 = new Intent(context, MainActivity.class);
                        context.startActivity(intent1);
                        break;
                    case R.id.ic_notification:
                        Intent intent2 = new Intent(context, NotificationActivity.class);
                        context.startActivity(intent2);
                        break;
                    case R.id.ic_cart:
                        Intent intent3 = new Intent(context, CartActivity.class);
                        context.startActivity(intent3);
                        break;
                    case R.id.ic_person:
                        Intent intent4 = new Intent(context, ProfileActivity.class);
                        context.startActivity(intent4);
                        break;
                }


                return false;
            }
        });
    }
}
