package com.example.user.project.Utils.Profile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.user.project.R;

public class NewBidActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText title, price, description;
    private Button post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_bid);
        title = (EditText) findViewById(R.id.bid_title);
        price = (EditText) findViewById(R.id.bid_price);

        //spinner setiing
        Spinner spinner = (Spinner) findViewById(R.id.bid_size);
        ArrayAdapter<CharSequence> bidSize = ArrayAdapter.createFromResource(this,
                R.array.shoesize,
                android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(bidSize);

        //description setting
        description = findViewById(R.id.bid_description);
        description.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        description.setGravity(Gravity.TOP);
        description.setSingleLine(false);
        description.setHorizontallyScrolling(false);

        //button onClick
        post = (Button) findViewById(R.id.bu_newBid);
        post.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (!TextUtils.isEmpty(title.getText()) && !TextUtils.isEmpty(price.getText()) && !TextUtils.isEmpty(description.getText())) {
            Intent profile = new Intent(this, ProfileActivity.class);
            startActivity(profile);
            Toast.makeText(this, "Post Succeed", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Every Field Should Be Filled", Toast.LENGTH_LONG).show();
        }
    }
}
