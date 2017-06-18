package com.example.leejaewon.quickchoice_rider;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

/**
 * Created by LeeJaeWon on 2017-05-24.
 */

public class bid extends AppCompatActivity{
    private EditText bid;
    private Button dobid;
    private Button cancle;

    private String id;
    private String no;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.money);

        Typeface typeface1 = Typeface.createFromAsset(getAssets(), "fonts/godic.ttf");
        TextView textView1 = (TextView)findViewById(R.id.do_bid);
        TextView textView2 = (TextView)findViewById(R.id.bid_cancle);
        TextView textView3 = (TextView)findViewById(R.id.textView99);
        TextView textView4 = (TextView)findViewById(R.id.textView88);
        TextView textView5 = (TextView)findViewById(R.id.textView8);
        TextView textView6 = (TextView)findViewById(R.id.bid_money);

        textView1.setTypeface(typeface1);
        textView2.setTypeface(typeface1);
        textView3.setTypeface(typeface1);
        textView4.setTypeface(typeface1);
        textView5.setTypeface(typeface1);
        textView6.setTypeface(typeface1);

        Intent intent =this.getIntent();
        no =intent.getStringExtra("no");
        id = intent.getStringExtra("id");


        bid = (EditText)findViewById(R.id.bid_money);
        dobid = (Button) findViewById(R.id.do_bid);
        cancle = (Button) findViewById(R.id.bid_cancle);

        listener lis = new listener();
        dobid.setOnClickListener(lis);
        cancle.setOnClickListener(lis);

    }

    class listener implements View.OnClickListener{
        public void onClick(View v){
            switch (v.getId()){
                case R.id.do_bid:
                    bid_for_goods();
                    finish();
                    break;
                case R.id.bid_cancle:
                    finish();
                    break;
            }
        }
    }

    public void bid_for_goods(){
        String money=bid.getText().toString();

        CustomTask customTask = new CustomTask();
        try {
            money=customTask.execute(id,no,money).get();
            Toast.makeText(this,money,Toast.LENGTH_LONG).show();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

    class CustomTask extends AsyncTask<String, Void, String> {
        String sendMsg, receiveMsg;
        @Override
        protected String doInBackground(String... strings) {
            try {
                String str;
                URL url = new URL("http://220.122.180.160:8080/tender.jsp");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                sendMsg = "&riderid="+strings[0]+"&goodno="+strings[1]+"&money="+strings[2];
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
