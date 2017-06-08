package com.example.leejaewon.quickchoice_rider;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class main extends AppCompatActivity {
    public String start;
    public String desti;
    public String hopemoney;
    public String pickup;
    public String memo ;
    public int paytype;
    public int fast;
    public String userid = "12";
    public int  category;
    public int finish = 0;


    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;

    private Button btnShowNavigationDrawer;

    public FragmentManager fm;
    public FragmentTransaction ft;

    public Fragment fr_main = new content_main();
    public Fragment fr_work = new content_work();
    public Fragment fr_information = new content_information();
    public Fragment fr_profit = new content_profit();
    public Fragment fr_setting = new content_setting();


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbarInclude);
        setSupportActionBar(toolbar);
        btnShowNavigationDrawer = (Button) toolbar.findViewById(R.id.btnShowNavigationDrawer);
        btnShowNavigationDrawer.setOnClickListener(onClickListener);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        navigationView = (NavigationView) findViewById(R.id.navigationView);
        setUpDrawerContent(navigationView);

        if(findViewById(R.id.fragment_main) !=null){
            if(savedInstanceState != null) {
                return;
            }
            content_main fr_main = new content_main();
            fr_main.setArguments(getIntent().getExtras());

            getFragmentManager().beginTransaction().add(R.id.fragment_main,fr_main).commit();
        }
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btnShowNavigationDrawer:
                    drawerLayout.openDrawer(GravityCompat.START);
                    break;
            }
        }
    };

    private void setUpDrawerContent(final NavigationView navigationView){

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            //            Fragment fr;
//            FragmentManager fm;
//            FragmentTransaction ft;
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.first_navigation_request:
//                        fr= new content_order_sub1();
                        fm=getFragmentManager();
                        ft= fm.beginTransaction();
                        ft.replace(R.id.fragment_main,fr_main);
                        ft.commit();
                        drawerLayout.closeDrawer(navigationView);
                        break;
                    case R.id.second_navigation_work:
                        fm=getFragmentManager();
                        ft= fm.beginTransaction();
                        ft.replace(R.id.fragment_main,fr_work);
                        ft.commit();
                        drawerLayout.closeDrawer(navigationView);

                        break;

                    case R.id.third_navigation_information:
//                        fr= new content_information();
                        fm=getFragmentManager();
                        ft= fm.beginTransaction();
                        ft.replace(R.id.fragment_main,fr_information);
                        ft.commit();
                        drawerLayout.closeDrawer(navigationView);
                        break;

                    case R.id.forth_navigation_profit:
//                        fr= new content_service();
                        fm=getFragmentManager();
                        ft= fm.beginTransaction();
                        ft.replace(R.id.fragment_main,fr_profit);
                        ft.commit();
                        drawerLayout.closeDrawer(navigationView);
                        break;

                    case R.id.fifth_navigation_setting:
//                        fr= new content_setting();
                        fm=getFragmentManager();
                        ft= fm.beginTransaction();
                        ft.replace(R.id.fragment_main,fr_setting);
                        ft.commit();
                        drawerLayout.closeDrawer(navigationView);
                        break;
                }

                return false;
            }
        });
    }


    class CustomTask extends AsyncTask<String, Void, String> {
        String sendMsg, receiveMsg;
        @Override
        protected String doInBackground(String... strings) {
            try {
                String str;
                URL url = new URL("http://220.122.180.160:8080/order.jsp");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                sendMsg = "&start="+strings[0]+"&desti="+strings[1]+"&hopemoney="+strings[2]+"&pickup="+strings[3]+"&memo="+strings[4]+"&paytype="+strings[5]+"&fast="+strings[6]+"&userid="+strings[7]+"&category="+strings[8]+"&finish"+strings[9];
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){


        if(requestCode==1){
            if(resultCode==2){

            }
        }
    }

}
