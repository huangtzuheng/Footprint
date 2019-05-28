package com.example.user.project.Utils.Utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.project.Item;
import com.example.user.project.R;
import com.example.user.project.Utils.Detail.DetailActivity;
import com.example.user.project.Utils.Home.MainActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;

public class GridImageAdapter extends ArrayAdapter<Item>{
    private Context mContext;
    private LayoutInflater mInflater;
    private int layoutResource;
    private String mAppend;
    private ArrayList<Item> items;

    public GridImageAdapter(Context context, int layoutResource, String append, ArrayList<Item> items){
        super(context, layoutResource, items);
        mContext = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.layoutResource = layoutResource;
        mAppend = append;
        this.items = items;
    }

    private  static class ViewHolder{
        RelativeLayout thisLayout;
        SquareImageView image;
        TextView goodId;
        ProgressBar mProgressBar;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;
        if(convertView == null) {
            convertView = mInflater.inflate(layoutResource, parent, false);
            holder = new ViewHolder();
            holder.thisLayout = (RelativeLayout) convertView.findViewById(R.id.aProfileItem);
            holder.mProgressBar = (ProgressBar) convertView.findViewById(R.id.gridImageProgressbar);
            holder.goodId = (TextView)convertView.findViewById(R.id.profile_grid_id);
            holder.image = (SquareImageView) convertView.findViewById(R.id.gridImageView);


            holder.thisLayout.setOnClickListener(new ImageView.OnClickListener(){
                @Override
                public void onClick(View v) {
                    TextView tv_id = (TextView)v.findViewById(R.id.profile_grid_id);
                    String id = tv_id.getText().toString();


                    Intent i = new Intent(getContext(), DetailActivity.class);
//
                    Bundle bundle = new Bundle();
                    bundle.putLong("GoodID", Long.valueOf(id));
                    i.putExtras(bundle);

                    mContext.startActivity(i);

//                    Toast.makeText(getContext(),   "這項商品的編號是：" + String.valueOf(id) , Toast.LENGTH_SHORT).show();
                }

            });

            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }

        holder.goodId.setText(String.valueOf(items.get(position).getId()));

        String imgURL = items.get(position).getPICTURE();
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.displayImage(mAppend + imgURL, holder.image, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                if(holder.mProgressBar !=null){
                    holder.mProgressBar.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                if(holder.mProgressBar !=null){
                    holder.mProgressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                if(holder.mProgressBar !=null){
                    holder.mProgressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                if(holder.mProgressBar !=null){
                    holder.mProgressBar.setVisibility(View.GONE);
                }
            }
        });

        return convertView;
    }

}
