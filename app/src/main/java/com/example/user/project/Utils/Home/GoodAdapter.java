package com.example.user.project.Utils.Home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.View.OnClickListener;

import com.example.user.project.Item;
import com.example.user.project.R;

import com.example.user.project.User;
import com.example.user.project.UserDAO;
import com.squareup.picasso.Picasso;

import java.util.List;


public class GoodAdapter extends RecyclerView.Adapter<GoodAdapter.GoodAdapterViewHolder> {
    private List<Item> mGoodData;
    private final GoodAdapterOnClickHandler mClickHandler;

    public interface GoodAdapterOnClickHandler {
        void onClick(String thisGood);
    }

    public GoodAdapter(List<Item> mData, GoodAdapterOnClickHandler clickHandler) {
        mGoodData = mData;
        mClickHandler = clickHandler;
    }

    public class GoodAdapterViewHolder extends RecyclerView.ViewHolder implements OnClickListener {
        public TextView mGoodTitle, mGoodSeller, mGoodPrice,mGoodSize, mGoodDescription;
        public LinearLayout mLinearLayout;
        public ImageView mimageView;
        UserDAO testU;

        public GoodAdapterViewHolder(View view) {
            super(view);

            mGoodTitle = (TextView) view.findViewById(R.id.tv_good_title);
            mGoodSeller = (TextView) view.findViewById(R.id.tv_good_seller);
            mGoodPrice = (TextView) view.findViewById(R.id.tv_good_price);
            mGoodSize = (TextView) view.findViewById(R.id.tv_good_size);
            mGoodDescription = (TextView) view.findViewById(R.id.tv_good_description);

            mLinearLayout = (LinearLayout) view.findViewById(R.id.ll);

            mimageView = (ImageView)view.findViewById(R.id.iv_goodList);

            testU = new UserDAO(view.getContext());

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            String thisGoodID = String.valueOf(mGoodData.get(adapterPosition).getId());
            mClickHandler.onClick(thisGoodID);
        }
    }

    @Override
    public GoodAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.good_list;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new GoodAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GoodAdapterViewHolder goodAdapterViewHolder, int position) {
        Item theGood = mGoodData.get(position);
        User theUser = goodAdapterViewHolder.testU.get(theGood.getUId());


        goodAdapterViewHolder.mGoodTitle.setText(theGood.getTITLE() + "(" + String.valueOf(theGood.getId()) + ")");
        goodAdapterViewHolder.mGoodSeller.setText(theUser.getName());
        goodAdapterViewHolder.mGoodPrice.setText(String.valueOf(theGood.getPRICE()));
        goodAdapterViewHolder.mGoodSize.setText(String.valueOf(theGood.getSIZE()));
        goodAdapterViewHolder.mGoodDescription.setText(theGood.getDESCRIPTION());

        Picasso.get().load(theGood.getPICTURE()).fit().into(goodAdapterViewHolder.mimageView);

    }

    @Override
    public int getItemCount() {
        if (mGoodData == null) return 0;
        return mGoodData.size();
    }

//    public void setGoodData(String[] goodData) {
//        mGoodData = goodData;
//        notifyDataSetChanged();
//    }
}
