package com.example.user.project.Utils.Home;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.project.ItemDAO;
import com.example.user.project.ThingDAO;
import com.example.user.project.UserDAO;
import com.example.user.project.Item;
import com.squareup.picasso.Picasso;

import com.example.user.project.R;
import com.example.user.project.Utils.Utils.BottomNavigationViewHelper;
import com.example.user.project.Utils.Utils.UniversalImageLoader;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener,
        TabLayout.OnTabSelectedListener {
    private static final String TAG = "MainActivity";
    private static final int ACTIVITY_NUM = 0;
    private Context mContext = MainActivity.this;
    private RecyclerView mRecyclerView;
    private GoodAdapter mGoodAdapter;
    public String[] testData = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m"};
//    private SpannableString title;
    private Toolbar mToolbar;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private BidFragment bid = new BidFragment();
    private AskFragment ask = new AskFragment();
final String[] shoesizetable={"6","7","8","9","10","11","12"};
private double shoeselectsize;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Log.d(TAG, "onCreate: starting.");


        //====== Initial Database =========

        ItemDAO testI =new ItemDAO(MainActivity.this);
        ThingDAO testT =new ThingDAO(MainActivity.this);
        UserDAO testU = new UserDAO(MainActivity.this);
        // 如果資料庫是空的，就建立一些範例資料
        // 這是為了方便測試用的，完成應用程式以後可以拿掉
        if (testI.getCount() == 0) {
            testI.sample();


        }
        if (testU.getCount() == 0) {
            testU.sample();

        }
        if (testT.getCount() == 0) {
            testT.sample();

        }



        testI.close();
        testT.close();

        //====== Initial Database =========


        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        prefs.edit().putLong("UserID", 3)
                .apply();


        initImageLoader();
        setupBottomNavigationView();
        setupViewPager();
        //  made by austin
        mToolbar = (Toolbar) findViewById(R.id.tb_home);



        SpannableString title = new SpannableString("Foot print");
        int theOrange = Color.parseColor("#ff9900");
        title.setSpan(new BackgroundColorSpan(theOrange),5,10, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        title.setSpan(new ForegroundColorSpan(Color.WHITE),0,4, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        mToolbar.setTitle(title);
        setSupportActionBar(mToolbar);


    }

    private void initImageLoader() {
        UniversalImageLoader universalImageLoader = new UniversalImageLoader(mContext);
        ImageLoader.getInstance().init(universalImageLoader.getConfig());
    }

    private void setupViewPager() {
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new BidFragment());
        adapter.addFragment(new AskFragment());

        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        mViewPager = (ViewPager) findViewById(R.id.container);

        mViewPager.addOnPageChangeListener(this);
        mTabLayout.addOnTabSelectedListener(this);

        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        return bid;
                    case 1:
                        return ask;
                }
                return null;
            }

            @Override
            public int getCount() {
                return 2;
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
    public void onTabSelected(TabLayout.Tab tab) {
        //TabLayout里的TabItem被选中的时候触发
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        //viewPager滑动之后显示触发
        mTabLayout.getTabAt(position).select();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_item, menu);
        MenuItem menuSearchItem = menu.findItem(R.id.my_search);

        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menuSearchItem.getActionView();

        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        // 這邊讓icon可以還原到搜尋的icon
        searchView.setIconifiedByDefault(true);
        // spinner
        Spinner sizeSelect = (Spinner) menu.findItem(R.id.size_menu)
                .setActionView(R.layout.myspinner)
                .getActionView()
                .findViewById(R.id.size_spinner);
        sizeSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "您選擇的鞋子尺寸是" + shoesizetable[position] + "(US)", Toast.LENGTH_LONG).show();
                shoeselectsize = Double.parseDouble(shoesizetable[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayAdapter<CharSequence> shoe = ArrayAdapter.createFromResource(this, R.array.shoesize, R.layout.dropdown);
        sizeSelect.setAdapter(shoe);
        // 按下確認開啟新的activity
//        final SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                Intent intent = new Intent(getApplicationContext(), SearchableActivity.class);
//                startActivity(intent);
//                return true;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//
//                return true;
//
//            }
//        };
//        searchView.setOnQueryTextListener(queryTextListener);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
//            case R.id.size:
//                break;
        }
        return super.onOptionsItemSelected(item);
    }



}
