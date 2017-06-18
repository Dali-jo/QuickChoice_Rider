package com.example.leejaewon.quickchoice_rider;

import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by LeeJaeWon on 2017-05-14.
 */

public class requestlist_viewholder extends RecyclerView.ViewHolder {
    public TextView item_start;
    public TextView item_desti;
    public TextView item_category;
    public TextView item_pickup;
    public TextView item_money;

    public Button item_tender;

    public String item_no;

    public requestlist_viewholder(View itemview){
        super(itemview);

        Typeface typeface1 = Typeface.createFromAsset(itemview.getContext().getAssets(), "fonts/godic.ttf");
        TextView textView1 = (TextView)itemview.findViewById(R.id.request_start);
        TextView textView2 = (TextView)itemview.findViewById(R.id.request_desti);
        TextView textView3 = (TextView)itemview.findViewById(R.id.request_category);
        TextView textView4 = (TextView)itemview.findViewById(R.id.request_pickup);
        TextView textView5 = (TextView)itemview.findViewById(R.id.request_money);
        TextView textView6 = (TextView)itemview.findViewById(R.id.request_tender);
        TextView textView7 = (TextView)itemview.findViewById(R.id.request_cancle);

        item_start=(TextView) itemview.findViewById(R.id.request_start);
        item_desti=(TextView)itemview.findViewById(R.id.request_desti);
        item_category=(TextView)itemview.findViewById(R.id.request_category);
        item_pickup=(TextView)itemview.findViewById(R.id.request_pickup);
        item_money=(TextView)itemview.findViewById(R.id.request_money);
        item_tender=(Button)itemview.findViewById(R.id.request_tender);


        textView1.setTypeface(typeface1);
        textView2.setTypeface(typeface1);
        textView3.setTypeface(typeface1);
        textView4.setTypeface(typeface1);
        textView5.setTypeface(typeface1);
        textView6.setTypeface(typeface1);
        textView7.setTypeface(typeface1);
    }

}
