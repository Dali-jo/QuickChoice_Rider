package com.example.leejaewon.quickchoice_rider;

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

        item_start=(TextView) itemview.findViewById(R.id.request_start);
        item_desti=(TextView)itemview.findViewById(R.id.request_desti);
        item_category=(TextView)itemview.findViewById(R.id.request_category);
        item_pickup=(TextView)itemview.findViewById(R.id.request_pickup);
        item_money=(TextView)itemview.findViewById(R.id.request_money);
        item_tender=(Button)itemview.findViewById(R.id.request_tender);
    }

}
