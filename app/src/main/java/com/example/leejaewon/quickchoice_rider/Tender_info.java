package com.example.leejaewon.quickchoice_rider;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by LeeJaeWon on 2017-05-25.
 */

public class Tender_info extends AppCompatActivity {
    private TextView start;
    private TextView desti;
    private TextView pickup;
    private TextView category;
    private TextView money;

    private Button tender;

    private String id;
    private String no;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tender_info);

        start = (TextView) findViewById(R.id.tender_info_start);
        desti = (TextView) findViewById(R.id.tender_info_desti);
        pickup = (TextView) findViewById(R.id.tender_info_pickup);
        category = (TextView) findViewById(R.id.tender_info_category);
        money = (TextView) findViewById(R.id.tender_info_money);
        tender = (Button) findViewById(R.id.tender_info_tdbutton);

        Intent intent=this.getIntent();
        id=intent.getStringExtra("id");
        no=intent.getStringExtra("no");
        start.setText(intent.getStringExtra("start"));
        desti.setText(intent.getStringExtra("desti"));
        category.setText(intent.getStringExtra("category"));
        pickup.setText(intent.getStringExtra("pickup"));
        money.setText(intent.getStringExtra("money"));

        listener lis = new listener();
        tender.setOnClickListener(lis);
    }

    class listener implements View.OnClickListener{
        public void onClick(View v){
            switch (v.getId()){
                case R.id.tender_info_tdbutton:
                    Intent intent = new Intent(getBaseContext(),bid.class);
                    intent.putExtra("no",no);
                    intent.putExtra("id",id);
                    startActivity(intent);
                    finish();
                    break;
            }



        }
    }



}
