package com.example.leejaewon.quickchoice_rider;

import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class join extends AppCompatActivity{
    EditText id;
    EditText pw;
    EditText pw_check;
    EditText name;
    EditText phonNumber;

    RadioButton bt_bike;
    RadioButton bt_rabo;
    RadioButton bt_ton;
    RadioButton bt_damas;
    RadioButton bt_ban;

    Button bt_ok;
Button bt_cancle;
    int car;

    RadioGroup group_car;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_join);

        Typeface typeface1 = Typeface.createFromAsset(getAssets(), "fonts/godic.ttf");
        TextView textView1 = (TextView)findViewById(R.id.join_name);
        TextView textView2 = (TextView)findViewById(R.id.join_id);
        TextView textView3 = (TextView)findViewById(R.id.join_pw);
        TextView textView4 = (TextView)findViewById(R.id.join_pw_check);
        TextView textView5 = (TextView)findViewById(R.id.join_phone);
        TextView textView6= (TextView)findViewById(R.id.join_bike);
        TextView textView7 = (TextView)findViewById(R.id.join_damas);
        TextView textView8 = (TextView)findViewById(R.id.join_rabo);
        TextView textView9= (TextView)findViewById(R.id.join_ban);
        TextView textView10 = (TextView)findViewById(R.id.join_ton);
        TextView textView11 = (TextView)findViewById(R.id.join_getImg1);
        TextView textView12 = (TextView)findViewById(R.id.join_getImg2);
        TextView textView13 = (TextView)findViewById(R.id.join_ok);
        TextView textView14 = (TextView)findViewById(R.id.join_cancle);

        textView1.setTypeface(typeface1);
        textView2.setTypeface(typeface1);
        textView3.setTypeface(typeface1);
        textView4.setTypeface(typeface1);
        textView5.setTypeface(typeface1);
        textView6.setTypeface(typeface1);
        textView7.setTypeface(typeface1);
        textView8.setTypeface(typeface1);
        textView9.setTypeface(typeface1);
        textView10.setTypeface(typeface1);
        textView11.setTypeface(typeface1);
        textView12.setTypeface(typeface1);
        textView13.setTypeface(typeface1);
        textView14.setTypeface(typeface1);

        id=(EditText) findViewById(R.id.join_id);
        pw=(EditText) findViewById(R.id.join_pw);
        pw_check=(EditText) findViewById(R.id.join_pw_check);
        name=(EditText) findViewById(R.id.join_name);
        phonNumber=(EditText) findViewById(R.id.join_phone);
        bt_ban=(RadioButton)findViewById(R.id.join_ban);
        bt_rabo=(RadioButton)findViewById(R.id.join_rabo);
        bt_bike=(RadioButton)findViewById(R.id.join_bike);
        bt_ton=(RadioButton)findViewById(R.id.join_ton);
        bt_damas=(RadioButton)findViewById(R.id.join_damas);
        bt_ok=(Button)findViewById(R.id.join_ok);
        bt_cancle=(Button)findViewById(R.id.join_cancle);

        group_car=(RadioGroup)findViewById(R.id.group_car);
        listener lis = new listener();

        bt_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    if(bt_bike.isChecked()) car=0;
                    if(bt_damas.isChecked()) car=1;
                    if(bt_rabo.isChecked()) car=2;
                    if(bt_ban.isChecked()) car=3;
                    if(bt_ton.isChecked()) car=4;
                    String result;
                    CustomTask task = new CustomTask();
                    result = task.execute(id.getText().toString(),pw.getText().toString(),name.getText().toString(),phonNumber.getText().toString(),String.valueOf(car)).get();
                    Log.i("리턴 값",result);
                    Toast.makeText(getBaseContext(),result, Toast.LENGTH_LONG).show();
                } catch (Exception e) {

                }
            }
        });
        bt_ban.setOnClickListener(lis);
        bt_bike.setOnClickListener(lis);
        bt_damas.setOnClickListener(lis);
        bt_rabo.setOnClickListener(lis);
        bt_ton.setOnClickListener(lis);




    }
    class listener implements View.OnClickListener{
        public void onClick(View v){
            switch (v.getId()){
                case R.id.join_bike:
                    car=0;
                    break;
                case R.id.join_damas:
                    car=1;
                    break;
                case R.id.join_rabo:
                    car=2;
                    break;
                case R.id.join_ban:
                    car=3;
                    break;
                case R.id.join_ton:
                    car=4;
                    break;
            }
        }
    }

    class CustomTask extends AsyncTask<String, Void, String> {
        String sendMsg, receiveMsg;
        @Override
        protected String doInBackground(String... strings) {
            try {
                String str;
                URL url = new URL("http://220.122.180.160:8080/riderjoin.jsp");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                sendMsg = "&id="+strings[0]+"&pw="+strings[1]+"&name="+strings[2]+"&phon="+strings[3]+"&car="+strings[4];
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

//    TextWatcher watcher=new TextWatcher() {
//        @Override
//        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//        }
//
//        @Override
//        public void onTextChanged(CharSequence s, int start, int before, int count) {
//            if(id.isFocusable()){
//                login.CustomTask1 task= new login.CustomTask1();
//
//
//            }
//        }
//
//        @Override
//        public void afterTextChanged(Editable s) {
//
//        }
//    }

    public void join_Cancle(View v){
        finish();
    }
}
