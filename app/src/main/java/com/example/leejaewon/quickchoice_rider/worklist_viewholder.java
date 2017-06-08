package com.example.leejaewon.quickchoice_rider;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by LeeJaeWon on 2017-05-29.
 */

public class worklist_viewholder extends RecyclerView.ViewHolder {
    public TextView item_start;
    public TextView item_desti;
    public TextView item_pickup;
    public TextView item_category;
    public TextView item_money;

    public Button item_call;
    public Button item_finish;
    public String item_no;

    public worklist_viewholder(View itemview){
        super(itemview);

        item_start=(TextView) itemview.findViewById(R.id.worklist_start);
        item_desti=(TextView) itemview.findViewById(R.id.worklist_desti);
        item_pickup=(TextView) itemview.findViewById(R.id.worklist_pickup);
        item_category=(TextView) itemview.findViewById(R.id.worklist_category);
        item_money=(TextView) itemview.findViewById(R.id.worklist_money);

        item_call=(Button) itemview.findViewById(R.id.worklist_call);
        item_finish=(Button) itemview.findViewById(R.id.worklist_finish);



    }
}
