package com.example.leejaewon.quickchoice_rider;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by LeeJaeWon on 2017-05-14.
 */

public class requestlist_adapter extends RecyclerView.Adapter<requestlist_viewholder>{
    private Activity mContext;
    private ArrayList<requestlist_item> requestlist_items;

    public requestlist_adapter(Activity context, ArrayList<requestlist_item> requestlist_items){
        mContext=context;
        this.requestlist_items = requestlist_items;
    }

    @Override
    public requestlist_viewholder onCreateViewHolder(ViewGroup parent, int viewType){
        View baseView =View.inflate(mContext,R.layout.requestlist,null);
//        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_orderlist,parent,true);

        requestlist_viewholder requestlist_viewholder = new requestlist_viewholder(baseView);
        return requestlist_viewholder;
    }

    @Override
    public void onBindViewHolder(final requestlist_viewholder holder, int position){
        requestlist_item item = requestlist_items.get(position);
        holder.item_start.setText("출발지:"+item.getStart());
        holder.item_desti.setText("도착지:"+item.getDesti());
//        holder.item_category.setText(item.getState());
        holder.item_category.setText("종류:서류");
//        holder.item_pickup.setText(item.getDriver());
        holder.item_pickup.setText("픽업시간:"+item.getPickup());
        holder.item_money.setText("금액:"+item.getMoney());
        holder.item_no=item.getNo();
        holder.item_tender.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Log.i("aaaa","aaaaaaaa");
                switch (v.getId()){
                    case R.id.request_tender:

                        Intent intent = new Intent(mContext,bid.class);
                        intent.putExtra("no",holder.item_no);
                        intent.putExtra("id",((main)mContext).userid);
                        mContext.startActivity(intent);

                }

            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
//                String no =holder.item_no.;
                String start =holder.item_start.getText().toString();
                String desti =holder.item_desti.getText().toString();
                String money =holder.item_money.getText().toString();
                Intent intent = new Intent(mContext,Tender_info.class);
//                        intent.putExtra("no",no);
                        intent.putExtra("start",start);
                        intent.putExtra("desti",desti);
                        intent.putExtra("money",money);
                        intent.putExtra("id",((main)mContext).userid);

                        mContext.startActivity(intent);
                Log.i("11","aa");
            }
        });
    }

    @Override
    public int getItemCount(){
        return requestlist_items.size();
    }
}
