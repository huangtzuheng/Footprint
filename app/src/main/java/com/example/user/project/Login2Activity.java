package com.example.user.project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Login2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String outputString = intent.getStringExtra("userID");
        String outputString2 =intent.getStringExtra("userPS");
        setContentView(R.layout.activity_login2);
        TextView showStringTV =(TextView) findViewById(R.id.showString);
        TextView showStringTV2 =(TextView) findViewById(R.id.showString2);
        showStringTV.setText(outputString+"您好");
        showStringTV2.setText("歡迎登入");
    }
}
