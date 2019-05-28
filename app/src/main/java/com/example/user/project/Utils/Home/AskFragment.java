package com.example.user.project.Utils.Home;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.user.project.Item;
import com.example.user.project.R;
import com.example.user.project.ThingDAO;
import com.example.user.project.Utils.Detail.DetailActivity;
import com.example.user.project.Utils.Detail.SellActivity;

import java.util.List;

public class AskFragment extends Fragment implements GoodAdapter.GoodAdapterOnClickHandler {
    private static final String TAG = "AskFragment";

    private RecyclerView mRecyclerView;
    private GoodAdapter mGoodAdapter;
    // private Button mButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_ask, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_ask);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        ThingDAO test=new ThingDAO(getContext());
        List<Item> thing=test.getAll();

        mGoodAdapter = new GoodAdapter(thing, this);
        mRecyclerView.setAdapter(mGoodAdapter);
//        mButton = (Button) view.findViewById(R.id.bt_detail);
        return  view;
    }
    public void onClick(String thisGood) {



        Intent i = new Intent(getActivity(), SellActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        Bundle bundle = new Bundle();
        bundle.putLong("goodId", Long.valueOf(thisGood));
        i.putExtras(bundle);

        startActivity(i);



//        Intent detailIntent = new Intent(getActivity(), SellActivity.class);
//        detailIntent.putExtra(Intent.EXTRA_TEXT, thisGood);
////        mButton.setText("Add to Cart");
//        startActivity(detailIntent);
    }
}