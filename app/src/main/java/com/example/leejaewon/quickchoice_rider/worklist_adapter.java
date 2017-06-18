package com.example.leejaewon.quickchoice_rider;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by LeeJaeWon on 2017-05-29.
 */

public class worklist_adapter extends RecyclerView.Adapter<worklist_viewholder> {
    private Activity mContext;
    private ArrayList<worklist_item> worklist_items;
    private String startlati;
    private String startlongi;
    private String destinationlati;
    private String destinationlongi;

    public worklist_adapter(Activity context, ArrayList<worklist_item> worklist_items){
        mContext=context;
        this.worklist_items = worklist_items;
    }

    @Override
    public worklist_viewholder onCreateViewHolder(ViewGroup parent, int viewType){
        View baseView =View.inflate(mContext,R.layout.worklist,null);

        worklist_viewholder worklist_viewholder = new worklist_viewholder(baseView);
        return worklist_viewholder;
    }

    @Override
    public void onBindViewHolder(final worklist_viewholder holder, int position){
        worklist_item item = worklist_items.get(position);
        startlati=item.getStartlati();
        startlongi=item.getStartlongi();
        destinationlati=item.getDestinationlati();
        destinationlongi=item.getDestinationlongi();
        holder.item_start.setText("출발지:"+item.getStart());
        holder.item_desti.setText("도착지:"+item.getDesti());
        holder.item_category.setText("서류");
        holder.item_pickup.setText("픽업시간:"+item.getPickup());
        holder.item_money.setText("금액:"+item.getMoney());
        holder.item_no=item.getNo();
        holder.item_finish.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                try {
                    String result;
                    CustomTask customTask = new CustomTask();
                    result=customTask.execute(holder.item_no).get();
                    Log.i("배송완료:",result);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,Tender_info.class);
                intent.putExtra("startlati",startlati);
                intent.putExtra("startlongi",startlongi);
                intent.putExtra("destinationlati",destinationlati);
                intent.putExtra("destinationlongi",destinationlongi);
                intent.putExtra("disable","1");
                mContext.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount(){
        return worklist_items.size();
    }

    private class CustomTask extends AsyncTask<String, Void, String> {
        String sendMsg, receiveMsg;
        @Override
        protected String doInBackground(String... strings) {
            try {
                String str;
                URL url = new URL("http://220.122.180.160:8080/complete.jsp");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                sendMsg = "&no="+strings[0];
                osw.write(sendMsg);
                osw.flush();
                if(conn.getResponseCode() == conn.HTTP_OK) {
                    InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "EUC-KR");
                    BufferedReader reader = new BufferedReader(tmp);
                    StringBuffer buffer = new StringBuffer();
                    while ((str = reader.readLine()) != null) {
                        buffer.append(str);
                    }
                    receiveMsg = buffer.toString();

                } else {
                    Log.i("통신 결과", conn.getResponseCode()+"에러");
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return receiveMsg;
        }



    }
}
