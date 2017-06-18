package com.example.leejaewon.quickchoice_rider;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;


public class main extends AppCompatActivity {
    public String start;
    public String desti;
    public String hopemoney;
    public String pickup;
    public String memo;
    public int paytype;
    public int fast;
    static public String userid = "";
    public int category;
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

    public String mylongi = "";
    public String mylati = "";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent=this.getIntent();
        userid=intent.getStringExtra("id");


//        while(ActivityCompat.checkSelfPermission(this,"Manifest.permission.ACCESS_FINE_LOCATION")==0) {

            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
//        }
        FirebaseMessaging.getInstance().subscribeToTopic("news");
        String token = FirebaseInstanceId.getInstance().getToken();

        CustomTask1 customTask1 = new CustomTask1();
        try {
            String result = customTask1.execute(userid, token).get();
            Log.i("토큰저장:", result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        //현재 위치 전송
        LocationManager mLM = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        LocationListener mLocationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
//                //여기서 위치값이 갱신되면 이벤트가 발생한다.
//                //값은 Location 형태로 리턴되며 좌표 출력 방법은 다음과 같다.
//
                mylongi = String.valueOf(location.getLongitude()); //경도
                mylati = String.valueOf(location.getLatitude());   //위도
                Log.i("long", mylongi);
                Log.i("lati", mylati);
            }

            public void onProviderDisabled(String provider) {
            }

            public void onProviderEnabled(String provider) {

            }

            public void onStatusChanged(String provider, int status, Bundle extras) {

            }
        };
//

//        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mLM.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 100, mLocationListener);
        mLM.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 100, mLocationListener);
//
        locationUpdate loUpdate = new locationUpdate();
//        loUpdate.start();


        toolbar = (Toolbar) findViewById(R.id.toolbarInclude);
        setSupportActionBar(toolbar);
        btnShowNavigationDrawer = (Button) toolbar.findViewById(R.id.btnShowNavigationDrawer);
        btnShowNavigationDrawer.setOnClickListener(onClickListener);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        navigationView = (NavigationView) findViewById(R.id.navigationView);
        setUpDrawerContent(navigationView);

        if (findViewById(R.id.fragment_main) != null) {
            if (savedInstanceState != null) {
                return;
            }
            content_main fr_main = new content_main();
            fr_main.setArguments(getIntent().getExtras());

            getFragmentManager().beginTransaction().add(R.id.fragment_main, fr_main).commit();
        }
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnShowNavigationDrawer:
                    drawerLayout.openDrawer(GravityCompat.START);
                    break;
            }
        }
    };

    private void setUpDrawerContent(final NavigationView navigationView) {

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            //            Fragment fr;
//            FragmentManager fm;
//            FragmentTransaction ft;
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.first_navigation_request:
//                        fr= new content_order_sub1();
                        fm = getFragmentManager();
                        ft = fm.beginTransaction();
                        ft.replace(R.id.fragment_main, fr_main);
                        ft.commit();
                        drawerLayout.closeDrawer(navigationView);
                        break;
                    case R.id.second_navigation_work:
                        fm = getFragmentManager();
                        ft = fm.beginTransaction();
                        ft.replace(R.id.fragment_main, fr_work);
                        ft.commit();
                        drawerLayout.closeDrawer(navigationView);

                        break;

                    case R.id.third_navigation_information:
//                        fr= new content_information();
                        fm = getFragmentManager();
                        ft = fm.beginTransaction();
                        ft.replace(R.id.fragment_main, fr_information);
                        ft.commit();
                        drawerLayout.closeDrawer(navigationView);
                        break;

                    case R.id.forth_navigation_profit:
//                        fr= new content_service();
                        fm = getFragmentManager();
                        ft = fm.beginTransaction();
                        ft.replace(R.id.fragment_main, fr_profit);
                        ft.commit();
                        drawerLayout.closeDrawer(navigationView);
                        break;

                    case R.id.fifth_navigation_setting:
//                        fr= new content_setting();
                        fm = getFragmentManager();
                        ft = fm.beginTransaction();
                        ft.replace(R.id.fragment_main, fr_setting);
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
                sendMsg = "&start=" + strings[0] + "&desti=" + strings[1] + "&hopemoney=" + strings[2] + "&pickup=" + strings[3] + "&memo=" + strings[4] + "&paytype=" + strings[5] + "&fast=" + strings[6] + "&userid=" + strings[7] + "&category=" + strings[8] + "&finish" + strings[9];
                osw.write(sendMsg);
                osw.flush();
                if (conn.getResponseCode() == conn.HTTP_OK) {
                    InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "EUC-KR");
                    BufferedReader reader = new BufferedReader(tmp);
                    StringBuffer buffer = new StringBuffer();
                    while ((str = reader.readLine()) != null) {
                        buffer.append(str);
                    }
                    receiveMsg = buffer.toString();

                } else {
                    Log.i("통신 결과", conn.getResponseCode() + "에러");
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return receiveMsg;
        }

    }

    private class CustomTask1 extends AsyncTask<String, Void, String> {
        String sendMsg, receiveMsg;

        @Override
        protected String doInBackground(String... strings) {
            try {
                String str;
                URL url = new URL("http://220.122.180.160:8080/savetoken.jsp");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                sendMsg = "&id=" + strings[0] + "&token=" + strings[1];
                osw.write(sendMsg);
                osw.flush();
                if (conn.getResponseCode() == conn.HTTP_OK) {
                    InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "EUC-KR");
                    BufferedReader reader = new BufferedReader(tmp);
                    StringBuffer buffer = new StringBuffer();
                    while ((str = reader.readLine()) != null) {
                        buffer.append(str);
                    }
                    receiveMsg = buffer.toString();

                } else {
                    Log.i("통신 결과", conn.getResponseCode() + "에러");
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return receiveMsg;
        }


    }

    private class locationUpdate extends Thread {

//        LocationManager mLM = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        String latitude = "0";
//        String longitude = "0";
//        int i = 0;
//
//        public LocationListener mLocationListener = new LocationListener() {
//            public void onLocationChanged(Location location) {
//                //여기서 위치값이 갱신되면 이벤트가 발생한다.
//
//                if (location.getProvider().equals(LocationManager.GPS_PROVIDER)) {
//                    //Gps 위치제공자에 의한 위치변화. 오차범위가 좁다.
//                    longitude = String.valueOf(location.getLongitude());    //경도
//                    latitude = String.valueOf(location.getLatitude());         //위도
//                    //float accuracy = location.getAccuracy();        //신뢰도
//                }
//            }
//
//            public void onProviderDisabled(String provider) {
//            }
//
//            public void onProviderEnabled(String provider) {
//            }
//
//            public void onStatusChanged(String provider, int status, Bundle extras) {
//            }
//        };
//
        @Override
        public void run() {
            while (true) {

//                registerLocationUpdates();
////                Toast.makeText(getBaseContext(), latitude, Toast.LENGTH_LONG).show();
////                Toast.makeText(getBaseContext(), longitude, Toast.LENGTH_LONG).show();
////                Toast.makeText(getBaseContext(), i++, Toast.LENGTH_LONG).show();

                Log.i("위도", mylati+"a");
                Log.i("경도", mylongi+"b");

                try {
                    CustomTask2 cus= new CustomTask2();
                    String s=cus.execute(userid,mylati,mylongi).get();
                    Log.i("aa",s);
                    sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
//


            }
        }
//
//        public void registerLocationUpdates() {
//
//            if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                // TODO: Consider calling
//                //    ActivityCompat#requestPermissions
//                // here to request the missing permissions, and then overriding
//                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                //                                          int[] grantResults)
//                // to handle the case where the user grants the permission. See the documentation
//                // for ActivityCompat#requestPermissions for more details.
//                return;
//            }
//            mLM.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10, 100, mLocationListener);
//
////1000은 1초마다, 1은 1미터마다 해당 값을 갱신한다는 뜻으로, 딜레이마다 호출
//
//        }
//
//
//
    }
    class CustomTask2 extends AsyncTask<String, Void, String> {
        String sendMsg, receiveMsg;

        @Override
        protected String doInBackground(String... strings) {
            try {
                String str;
                URL url = new URL("http://220.122.180.160:8080/locationupdate.jsp");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                sendMsg = "&riderid=" + strings[0] + "&lati=" + strings[1] + "&longi=" + strings[2];
                osw.write(sendMsg);
                osw.flush();
                if (conn.getResponseCode() == conn.HTTP_OK) {
                    InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "EUC-KR");
                    BufferedReader reader = new BufferedReader(tmp);
                    StringBuffer buffer = new StringBuffer();
                    while ((str = reader.readLine()) != null) {
                        buffer.append(str);
                    }
                    receiveMsg = buffer.toString();

                } else {
                    Log.i("통신 결과", conn.getResponseCode() + "에러");
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (requestCode == 1) {
            if (resultCode == 2) {

            }
        }
    }

}
