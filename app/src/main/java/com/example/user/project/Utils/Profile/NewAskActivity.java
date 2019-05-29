package com.example.user.project.Utils.Profile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.user.project.R;

public class NewAskActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText picture, title, price, description;
    private Button post;
    final String[] sizeTable = {"5", "6", "7", "8", "9", "10", "11", "12", "13"};
    private String data_picture, data_title, data_price, data_size, data_description;
    public String tempSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_ask);
        picture = (EditText) findViewById(R.id.ask_picture);
        title = (EditText) findViewById(R.id.ask_title);
        price = (EditText) findViewById(R.id.ask_price);

        //spinner setting
        Spinner spinner = (Spinner)findViewById(R.id.ask_size);
        ArrayAdapter<String> bidSize = new ArrayAdapter(this,
                android.R.layout.simple_spinner_dropdown_item, sizeTable);
        spinner.setAdapter(bidSize);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                tempSize = sizeTable[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //description setting
        description = findViewById(R.id.ask_description);
        description.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        description.setGravity(Gravity.TOP);
        description.setSingleLine(false);
        description.setHorizontallyScrolling(false);

        //button onClick
        post = (Button) findViewById(R.id.bu_newAsk);
        post.setOnClickListener(this);
    }
    @Override
    public void onClick(View v){
        if (!TextUtils.isEmpty(picture.getText()) && !TextUtils.isEmpty(title.getText()) && !TextUtils.isEmpty(price.getText()) && !TextUtils.isEmpty(description.getText())) {
            data_picture = picture.getText().toString();
            data_title = title.getText().toString();
            data_price = price.getText().toString();
            data_size = tempSize;
            data_description = description.getText().toString();
            Intent profile = new Intent(this, ProfileActivity.class);
            startActivity(profile);
            Toast.makeText(this, "Post Succeed", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Every Field Should Be Filled", Toast.LENGTH_LONG).show();
        }
    }
}
