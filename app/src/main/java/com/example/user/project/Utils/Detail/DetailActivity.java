package com.example.user.project.Utils.Detail;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.project.Item;
import com.example.user.project.ItemDAO;
import com.example.user.project.R;
import com.example.user.project.User;
import com.example.user.project.UserDAO;
import com.example.user.project.Utils.Home.MainActivity;
import com.squareup.picasso.Picasso;

import java.util.HashSet;
import java.util.Set;

public class DetailActivity extends AppCompatActivity {
    private TextView mGoodID, mGoodTitle, mGoodSize, mGoodPrice, mGoodDescription;
    private TextView mUserName, mUserScore, mUserPhone;
    private ImageView mGoodimg;
    private Button btn_addCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        LinkElements();


        Bundle bundle =this.getIntent().getExtras();
        long GoodID =  bundle.getLong("GoodID",0);

        if(GoodID == 0){
//            finish();  //此為判斷無資料時返回上一分頁，測試結束可刪除註解符號
        }else{
            Item thisGood = new ItemDAO(this).get(GoodID);
            mGoodID.setText(String.valueOf(thisGood.getId()));
            mGoodTitle.setText(thisGood.getTITLE());
            mGoodPrice.setText(String.valueOf(thisGood.getPRICE()));
            mGoodSize.setText(String.valueOf(thisGood.getSIZE()));
            mGoodDescription.setText(thisGood.getDESCRIPTION());

            UserDAO userDAO = new UserDAO(this);
            User thisUser = userDAO.get(thisGood.getUId());
            mUserName.setText(thisUser.getName());
            mUserPhone.setText(thisUser.getPhone());
            mUserScore.setText(String.valueOf(thisUser.getScore()));

            Picasso.get().load(thisGood.getPICTURE()).into(mGoodimg);

            btn_addCart.setOnClickListener(new Button.OnClickListener(){
                @Override
                public void onClick(View v) {

                    Long thisID = Long.valueOf(mGoodID.getText().toString());

                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    String currentCart = prefs.getString("CartList","");

                    if(currentCart.length() > 0){
                        currentCart += "," + String.valueOf(thisID);
                    }else{
                        currentCart = String.valueOf(thisID);
                    }

                    prefs.edit().putString("CartList",currentCart).commit();


                    Toast toast = Toast.makeText(getApplicationContext(),"商品編號("+thisID + ") 已加入購物車!", Toast.LENGTH_SHORT);
                    toast.show();
                }

            });



        }

//        Intent intentThatStartedThisActivity = getIntent();
//        if (intentThatStartedThisActivity.hasExtra(Intent.EXTRA_TEXT)) {
//            String gDetail = intentThatStartedThisActivity.getStringExtra(Intent.EXTRA_TEXT);
//            mGoodDetail.setText(gDetail);
//        }
    }

    public void LinkElements(){
        mGoodID =  (TextView) findViewById(R.id.goodInfo_id);
        mGoodTitle = (TextView) findViewById(R.id.goodInfo_title);
        mGoodSize = (TextView) findViewById(R.id.goodInfo_size);
        mGoodPrice = (TextView) findViewById(R.id.goodInfo_price);
        mGoodDescription = (TextView) findViewById(R.id.goodInfo_seller_description);

        mUserName= (TextView) findViewById(R.id.goodInfo_seller_name);
        mUserScore= (TextView) findViewById(R.id.goodInfo_seller_score);
        mUserPhone= (TextView) findViewById(R.id.goodInfo_seller_phone);

        mGoodimg = (ImageView) findViewById(R.id.goodInfo_pic);

        btn_addCart = (Button) findViewById(R.id.bt_detail_addCart);
    }
}
