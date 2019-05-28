package com.example.user.project.Utils.Home;
import android.app.ProgressDialog;
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
import android.widget.Button;
import android.widget.Toast;

import com.example.user.project.Item;
import com.example.user.project.ItemDAO;
import com.example.user.project.R;
import com.example.user.project.Utils.Detail.DetailActivity;

import java.util.List;

public class BidFragment extends Fragment implements GoodAdapter.GoodAdapterOnClickHandler {
    private static final String TAG = "BidFragment";
    public String[] testDataA = {"", "", "", "", "", ""};
    private RecyclerView mRecyclerView;
    private GoodAdapter mGoodAdapter;
//    private Button mButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_bid, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_bid);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        ItemDAO test=new ItemDAO(getContext());
        List<Item> items=test.getAll();

        mGoodAdapter = new GoodAdapter(items, this);

        mRecyclerView.setAdapter(mGoodAdapter);
//        mButton = (Button) view.findViewById(R.id.bt_detail);
        return  view;
    }
    public void onClick(String thisGood) {

//        Toast.makeText(getContext(),   "這項商品的編號是："+thisGood , Toast.LENGTH_SHORT).show();

        ProgressDialog dialog;
        dialog = new ProgressDialog(getContext());
        dialog.setMessage("Loading....");
        dialog.show();

        Intent i = new Intent(getContext(), DetailActivity.class);
//
        Bundle bundle = new Bundle();
        bundle.putLong("GoodID", Long.valueOf(thisGood));
        i.putExtras(bundle);

        startActivity(i);

        dialog.dismiss();

    }
}
