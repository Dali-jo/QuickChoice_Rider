package com.example.leejaewon.quickchoice_rider;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
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

public class login extends AppCompatActivity {
    EditText id;
    EditText pw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_login);

        Typeface typeface1 = Typeface.createFromAsset(getAssets(), "fonts/godic.ttf");
        TextView textView1 = (TextView)findViewById(R.id.login_id);
        TextView textView2 = (TextView)findViewById(R.id.button_login);
        TextView textView3 = (TextView)findViewById(R.id.login_pw);
        TextView textView4 = (TextView)findViewById(R.id.button_join);

        textView1.setTypeface(typeface1);
        textView2.setTypeface(typeface1);
        textView3.setTypeface(typeface1);
        textView4.setTypeface(typeface1);


        id=(EditText) findViewById(R.id.login_id);
        pw=(EditText) findViewById(R.id.login_pw);
    }




    public void goJoin(View v) {
        Intent intent = new Intent(this,join.class);
        startActivity(intent);
    }
    public void goMain(View v) {

        try {

            String result;
            CustomTask1 task = new CustomTask1();
            result = task.execute(id.getText().toString(),pw.getText().toString()).get();
            Log.i("리턴 값",result);
            Toast.makeText(this,result, Toast.LENGTH_LONG).show();
            if(result.equals("로그인 성공")){
                Intent intent = new Intent(this,main.class);
                intent.putExtra("id",id.getText().toString());
                startActivity(intent);
        } ;


    } catch (Exception e) {

        }

    }

    class CustomTask1 extends AsyncTask<String, Void, String> {
        String sendMsg, receiveMsg;
        @Override
        protected String doInBackground(String... strings) {
            try {
                String str;
                URL url = new URL("http://220.122.180.160:8080/login.jsp");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                sendMsg = "&id="+strings[0]+"&pw="+strings[1];
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
