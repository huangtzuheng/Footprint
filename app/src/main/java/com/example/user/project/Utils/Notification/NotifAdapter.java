package com.example.user.project.Utils.Notification;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.project.Item;
import com.example.user.project.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NotifAdapter extends RecyclerView.Adapter<NotifAdapter.NotifAdapterViewHolder> {
    private List<Item> mNotifData;
    private final NotifAdapter.NotifAdapterOnClickHandler mClickHandler;

    public interface NotifAdapterOnClickHandler {
        void onClick(String thisGood);
    }

    public NotifAdapter(List<Item> mData, NotifAdapter.NotifAdapterOnClickHandler clickHandler) {
        mNotifData = mData;
        mClickHandler = clickHandler;
    }

    public class NotifAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView mGoodID, mRecycleNo, mGoodTitle, mGoodSize, mGoodPrice;
        public LinearLayout mRelativeLayout;
        public ImageView mimageView;
        public Button bt_Remove;

        public NotifAdapterViewHolder(View view) {
            super(view);

            mGoodID = (TextView) view.findViewById(R.id.notif_Good_ID);
            mGoodTitle = (TextView) view.findViewById(R.id.notif_Good_Title);
            mGoodSize = (TextView) view.findViewById(R.id.notif_Good_Size);
            mGoodPrice = (TextView) view.findViewById(R.id.notif_Good_Price);
            mRecycleNo = (TextView) view.findViewById(R.id.notif_Recycle_No);

            mRelativeLayout = (LinearLayout) view.findViewById(R.id.rl_notifList);
            mimageView = (ImageView) view.findViewById(R.id.iv_notifList);
            view.setOnClickListener(this);

            bt_Remove = (Button) view.findViewById(R.id.notif_Good_RemoveBT);
            bt_Remove.setOnClickListener(new Button.OnClickListener(){
                @Override
                public void onClick(View v) {
                    String theRemoveID = mGoodID.getText().toString();

                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(v.getContext());
                    String currentNotif = prefs.getString("NotifList","");
                    String NotifArray[] = currentNotif.split(",");
                    String newNotif = "";

                    for(String ID_in_Notif : NotifArray){
                        if(!ID_in_Notif.equals(theRemoveID)){
                            if(newNotif.length() > 0){
                                newNotif += "," + String.valueOf(ID_in_Notif);
                            }else{
                                newNotif = String.valueOf(ID_in_Notif);
                            }
                        }
                    }

                    prefs.edit().putString("NotifList",newNotif).commit();
                    Toast toast = Toast.makeText(v.getContext()," 已確定賣出，請重新整理!!!", Toast.LENGTH_SHORT);
                    toast.show();


                }

            });
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            String thisGood = mNotifData.get(adapterPosition).getTITLE();
            mClickHandler.onClick(thisGood);
        }
    }

    @Override
    public NotifAdapter.NotifAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.notif_list;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new NotifAdapter.NotifAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NotifAdapter.NotifAdapterViewHolder notifAdapterViewHolder, int position) {
        Item thisGood = mNotifData.get(position);

        notifAdapterViewHolder.mGoodID.setText(String.valueOf(thisGood.getId()));
        notifAdapterViewHolder.mGoodTitle.setText(thisGood.getTITLE());
        notifAdapterViewHolder.mGoodPrice.setText(String.valueOf(thisGood.getPRICE()));
        notifAdapterViewHolder.mGoodSize.setText(String.valueOf(thisGood.getSIZE()));

        notifAdapterViewHolder.mRecycleNo.setText(String.valueOf(position));

        Picasso.get().load(thisGood.getPICTURE()).fit().into(notifAdapterViewHolder.mimageView);
    }

    @Override
    public int getItemCount() {
        if (mNotifData == null) return 0;
        return mNotifData.size();
    }
}
