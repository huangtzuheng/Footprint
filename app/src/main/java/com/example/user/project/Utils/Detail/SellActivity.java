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
import com.example.user.project.ThingDAO;
import com.example.user.project.User;
import com.example.user.project.UserDAO;
import com.example.user.project.Utils.Home.MainActivity;
import com.squareup.picasso.Picasso;

import java.util.HashSet;
import java.util.Set;

public class SellActivity extends AppCompatActivity {
    private TextView nGoodID, nGoodTitle, nGoodSize, nGoodPrice, nGoodDescription;
    private TextView nUserName, nUserScore, nUserPhone;
    private ImageView nGoodimg;
    private Button but_sellit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell);
        LinkElements();


        Bundle bundle =this.getIntent().getExtras();
        long GoodID =  bundle.getLong("goodId",0);

        if(GoodID == 0){
//            finish();  //此為判斷無資料時返回上一分頁，測試結束可刪除註解符號
        }else{
            Item thisGood = new ThingDAO(this).get(GoodID);
            nGoodID.setText(String.valueOf(thisGood.getId()));
            nGoodTitle.setText(thisGood.getTITLE());
            nGoodPrice.setText(String.valueOf(thisGood.getPRICE()));
            nGoodSize.setText(String.valueOf(thisGood.getSIZE()));
            nGoodDescription.setText(thisGood.getDESCRIPTION());

            UserDAO userDAO = new UserDAO(this);
            User thisUser = userDAO.get(thisGood.getUId());
            nUserName.setText(thisUser.getName());
            nUserPhone.setText(thisUser.getPhone());
            nUserScore.setText(String.valueOf(thisUser.getScore()));

//            Picasso.get().load(thisGood.getPICTURE()).into(nGoodimg);

            but_sellit.setOnClickListener(new Button.OnClickListener(){
                @Override
                public void onClick(View v) {

                    Long thisID = Long.valueOf(nGoodID.getText().toString());

                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    String currentCart = prefs.getString("CartList","");

                    if(currentCart.length() > 0){
                        currentCart += "," + String.valueOf(thisID);
                    }else{
                        currentCart = String.valueOf(thisID);
                    }

                    prefs.edit().putString("CartList",currentCart).commit();


                    Toast toast = Toast.makeText(getApplicationContext(),"商品編號("+thisID + ") 請去通知確認!", Toast.LENGTH_SHORT);
                    toast.show();
                }

            });



        }

//        Intent intentThatStartedThisActivity = getIntent();
//        if (intentThatStartedThisActivity.hasExtra(Intent.EXTRA_TEXT)) {
//            String gSell = intentThatStartedThisActivity.getStringExtra(Intent.EXTRA_TEXT);
//            nGoodSell.setText(gSell);
//      }
    }

    public void LinkElements(){
        nGoodID =  (TextView) findViewById(R.id.sellInfo_id);
        nGoodTitle = (TextView) findViewById(R.id.sellInfo_title);
        nGoodSize = (TextView) findViewById(R.id.sellInfo_size);
        nGoodPrice = (TextView) findViewById(R.id.sellInfo_price);
        nGoodDescription = (TextView) findViewById(R.id.sellInfo_buyer_description);

        nUserName= (TextView) findViewById(R.id.sellInfo_buyer_name);
        nUserScore= (TextView) findViewById(R.id.sellInfo_buyerer_score);
        nUserPhone= (TextView) findViewById(R.id.sellInfo_buyer_phone);

        nGoodimg = (ImageView) findViewById(R.id.goodInfo_pic);

        but_sellit = (Button) findViewById(R.id.sell_detail);
    }
}

