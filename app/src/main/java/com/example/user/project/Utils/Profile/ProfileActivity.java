package com.example.user.project.Utils.Profile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.user.project.Item;
import com.example.user.project.ItemDAO;
import com.example.user.project.R;
import com.example.user.project.User;
import com.example.user.project.UserDAO;
import com.example.user.project.Utils.Utils.BottomNavigationViewHelper;
import com.example.user.project.Utils.Utils.GridImageAdapter;
import com.example.user.project.Utils.Utils.UniversalImageLoader;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "ProfileActivity";
    private static final int ACTIVITY_NUM = 3;
    private static final int NUM_GRID_COLUMS = 3;

    private Context mContext = ProfileActivity.this;

    private ProgressBar mProgressBar;
    private TextView UserName, UserScore, UserPhone;
    private ImageView profilePhoto;
    private User theUser;
    private Button addBid, addAsk;


    //========= 這應該要登入後存SharedPreferences =========

    public static long login_USER_ID;

    //========= 這應該要登入後存SharedPreferences =========


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        login_USER_ID = prefs.getLong("UserID", 0);


        mProgressBar = (ProgressBar) findViewById(R.id.profileProgressBar);
        UserName = (TextView) findViewById(R.id.display_name);
        UserScore = (TextView) findViewById(R.id.profile_seller_score);
        UserPhone = (TextView) findViewById(R.id.profile_phone);
        mProgressBar.setVisibility(View.GONE);

        theUser = new UserDAO(this).get(login_USER_ID);

        setupBottomNavigationView();
        setupToolbar();
        setupActivityWidget();
        setProfileImgae();
        UserName.setText(theUser.getName());
        UserPhone.setText(theUser.getPhone());
        UserScore.setText(String.valueOf(theUser.getScore()));

        getMySellingData();
        //新增Bid、Ask
        addBid = findViewById(R.id.bu_bid);
        addBid.setOnClickListener(this);
        addAsk = findViewById(R.id.bu_ask);
        addAsk.setOnClickListener(this);


        // =============== Link Listener ===============

        final TextView tv_buying = (TextView) findViewById(R.id.profile_Btn_Buying);
        final TextView tv_selling = (TextView) findViewById(R.id.profile_Btn_Selling);


        tv_selling.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_buying.setTextColor(Color.parseColor("#666666"));
                tv_selling.setTextColor(Color.parseColor("#77a9f9"));
                tv_buying.setTypeface(null, Typeface.NORMAL);
                tv_selling.setTypeface(null, Typeface.BOLD);
                getMySellingData();
            }

        });

        tv_buying.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_buying.setTextColor(Color.parseColor("#77a9f9"));
                tv_selling.setTextColor(Color.parseColor("#666666"));
                tv_buying.setTypeface(null, Typeface.BOLD);
                tv_selling.setTypeface(null, Typeface.NORMAL);
                tempGridSetup();
            }

        });


        // =============== Link Listener ===============


    }


    public void getMySellingData() {
        List<Item> mySellingGood = new ItemDAO(this).getByUID(login_USER_ID);

        ArrayList<Item> items = new ArrayList<>();
        for (Item theGood : mySellingGood) {
            items.add(theGood);
        }

        setupImageGrid(items);
    }

    private void tempGridSetup() {
//        ArrayList<String> imgURLs = new ArrayList<>();
//        imgURLs.add("https://i.redd.it/59kjlxxf720z.jpg");
//        imgURLs.add("https://i.redd.it/pwduhknig00z.jpg");
//        imgURLs.add("https://i.redd.it/clusqsm4oxzy.jpg");
//        imgURLs.add("https://i.redd.it/svqvn7xs420z.jpg");
//        imgURLs.add("http://i.imgur.com/j4AfH6P.jpg");
//        imgURLs.add("https://i.redd.it/89cjkojkl10z.jpg");
//        imgURLs.add("https://i.redd.it/aw7pv8jq4zzy.jpg");

        ArrayList<Item> items = new ArrayList<>();
        setupImageGrid(items);
    }


    private void setupImageGrid(ArrayList<Item> imgURLs) {
        GridView gridView = (GridView) findViewById(R.id.gridView);

        int gridWidth = getResources().getDisplayMetrics().widthPixels;
        int imageWidth = gridWidth / NUM_GRID_COLUMS;
        gridView.setColumnWidth(imageWidth);

        GridImageAdapter adapter = new GridImageAdapter(mContext, R.layout.layout_grid_imageview, "", imgURLs);
        gridView.setAdapter(adapter);
    }

    private void setProfileImgae() {
//        Log.d(TAG, "setProfileImage: setting profile photo. ");
//        String imgURL = "www.androidcentral.com/sites/androidcentral.com/files/styles/xlarge/public/article_images/2016/08/ac-lloyd.jpg?itok=bb72IeLf";
//        UniversalImageLoader.setImage(imgURL, profilePhoto, mProgressBar, "https://");
        Picasso.get().load(theUser.getPicture()).fit().into(profilePhoto);

    }


    private void setupActivityWidget() {
        mProgressBar = (ProgressBar) findViewById(R.id.profileProgressBar);
        mProgressBar.setVisibility(View.GONE);
        profilePhoto = (ImageView) findViewById(R.id.profile_photo);
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.profileToolBar);
        setSupportActionBar(toolbar);

        ImageView profileMenu = (ImageView) findViewById(R.id.profileMenu);
        profileMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: navigating to account setting.");
                Intent intent = new Intent(mContext, AccountSettingActivity.class);
                startActivity(intent);
            }
        });

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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bu_bid:
                Intent bid = new Intent(this, NewBidActivity.class);
                startActivity(bid);
                break;
            case R.id.bu_ask:
                Intent ask = new Intent(this, NewAskActivity.class);
                startActivity(ask);
                break;
        }
    }
}


