package com.example.user.project.Utils.Cart;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.project.Item;
import com.squareup.picasso.Picasso;

import com.example.user.project.R;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartAdapterViewHolder> {
    private List<Item> mCartData;
    private final CartAdapter.CartAdapterOnClickHandler mClickHandler;

    public interface CartAdapterOnClickHandler {
        void onClick(String thisGood);
    }

    public CartAdapter(List<Item> mData, CartAdapter.CartAdapterOnClickHandler clickHandler) {
        mCartData = mData;
        mClickHandler = clickHandler;
    }

    public class CartAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView mGoodID, mRecycleNo, mGoodTitle, mGoodSize, mGoodPrice;
        public LinearLayout mRelativeLayout;
        public ImageView mimageView;
        public Button bt_Remove;

        public CartAdapterViewHolder(View view) {
            super(view);

            mGoodID = (TextView) view.findViewById(R.id.cart_Good_ID);
            mGoodTitle = (TextView) view.findViewById(R.id.cart_Good_Title);
            mGoodSize = (TextView) view.findViewById(R.id.cart_Good_Size);
            mGoodPrice = (TextView) view.findViewById(R.id.cart_Good_Price);
            mRecycleNo = (TextView) view.findViewById(R.id.cart_Recycle_No);

            mRelativeLayout = (LinearLayout) view.findViewById(R.id.rl_cartList);
            mimageView = (ImageView) view.findViewById(R.id.iv_cartList);
            view.setOnClickListener(this);

            bt_Remove = (Button) view.findViewById(R.id.cart_Good_RemoveBT);
            bt_Remove.setOnClickListener(new Button.OnClickListener(){
                @Override
                public void onClick(View v) {
                    String theRemoveID = mGoodID.getText().toString();

                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(v.getContext());
                    String currentCart = prefs.getString("CartList","");
                    String CartArray[] = currentCart.split(",");
                    String newCcart = "";

                    for(String ID_in_Cart : CartArray){
                        if(!ID_in_Cart.equals(theRemoveID)){
                            if(newCcart.length() > 0){
                                newCcart += "," + String.valueOf(ID_in_Cart);
                            }else{
                                newCcart = String.valueOf(ID_in_Cart);
                            }
                        }
                    }

                    prefs.edit().putString("CartList",newCcart).commit();
                    Toast toast = Toast.makeText(v.getContext()," 已從購物車移除，請重新整理!!!", Toast.LENGTH_SHORT);
                    toast.show();


                }

            });
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            String thisGood = mCartData.get(adapterPosition).getTITLE();
            mClickHandler.onClick(thisGood);
        }
    }

    @Override
    public CartAdapter.CartAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.cart_list;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new CartAdapter.CartAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CartAdapter.CartAdapterViewHolder cartAdapterViewHolder, int position) {
        Item thisGood = mCartData.get(position);

        cartAdapterViewHolder.mGoodID.setText(String.valueOf(thisGood.getId()));
        cartAdapterViewHolder.mGoodTitle.setText(thisGood.getTITLE());
        cartAdapterViewHolder.mGoodPrice.setText(String.valueOf(thisGood.getPRICE()));
        cartAdapterViewHolder.mGoodSize.setText(String.valueOf(thisGood.getSIZE()));

        cartAdapterViewHolder.mRecycleNo.setText(String.valueOf(position));

        Picasso.get().load(thisGood.getPICTURE()).fit().into(cartAdapterViewHolder.mimageView);
    }

    @Override
    public int getItemCount() {
        if (mCartData == null) return 0;
        return mCartData.size();
    }

//    public void setCartData(String[] cartData) {
//        mCartData = cartData;
//        notifyDataSetChanged();
//    }
}
