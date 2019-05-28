package com.example.user.project.Utils.Home;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.user.project.R;

public class SearchableActivity extends AppCompatActivity {
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchable);
        mTextView = (TextView) findViewById(R.id.search_tv);
        mTextView.setText("search result");
//        getSupportActionBar().hide();
    }
}
