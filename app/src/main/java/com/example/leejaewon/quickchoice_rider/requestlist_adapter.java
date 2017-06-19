package com.example.leejaewon.quickchoice_rider;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by LeeJaeWon on 2017-05-14.
 */

public class requestlist_adapter extends RecyclerView.Adapter<requestlist_viewholder>{
    private Activity mContext;
    private ArrayList<requestlist_item> requestlist_items;
    private String startlati;
    private String startlongi;
    private String destinationlati;
    private String destinationlongi;

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
        startlati=item.getStartlati();
        startlongi=item.getStartlongi();
        destinationlati=item.getDestinationlati();
        destinationlongi=item.getDestinationlongi();
        holder.item_start.setText("출발지:"+item.getStart());
        holder.item_desti.setText("도착지:"+item.getDesti());
        holder.item_category.setText(item.getState());
//        holder.item_category.setText("종류:"+item.getCategory());
//        holder.item_pickup.setText(item.getPickup());
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

        listener lis = new listener(item);
        holder.itemView.setOnClickListener(lis);
    }

    private class listener implements View.OnClickListener{
        int i;
        requestlist_item data;

        public listener(int i, requestlist_item data){
            this.i=i;
            this.data=data;
        }
        public listener(requestlist_item data){
            this.data=data;
        }

        @Override
        public void onClick(View v){

            Intent intent = new Intent(mContext,Tender_info.class);

            intent.putExtra("start",data.getStart());
            intent.putExtra("desti",data.getDesti());
            intent.putExtra("money",data.getMoney());
            intent.putExtra("id",((main)mContext).userid);
            intent.putExtra("pickup", data.getPickup());
            intent.putExtra("startlati",data.getStartlati());
            intent.putExtra("startlongi",data.getStartlongi());
            intent.putExtra("destinationlati",data.getDestinationlati());
            intent.putExtra("destinationlongi",data.getDestinationlongi());
            intent.putExtra("disable","0");
            mContext.startActivity(intent);
        }
    }

    @Override
    public int getItemCount(){
        return requestlist_items.size();
    }
}
