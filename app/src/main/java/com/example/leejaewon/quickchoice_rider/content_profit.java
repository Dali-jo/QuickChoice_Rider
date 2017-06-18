package com.example.leejaewon.quickchoice_rider;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

/**
 * Created by LeeJaeWon on 2017-05-19.
 */

public class content_profit extends Fragment {
    CalendarView calendarView;

    String year1;
    String month1;
    String day1;

    private TextView profitDay;
    private TextView profitDayCount;
    private TextView profitDayMoney;
    private TextView profitMonth;
    private TextView profitMonthCount;
    private TextView profitMonThMoney;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view= inflater.inflate(R.layout.sulik,container,false);

        profitDay=(TextView)view.findViewById(R.id.profit_day);
        profitDayCount=(TextView)view.findViewById(R.id.profit_day_count);
        profitDayMoney=(TextView)view.findViewById(R.id.profit_day_money);
        profitMonth=(TextView)view.findViewById(R.id.profit_month);
        profitMonthCount=(TextView)view.findViewById(R.id.profit_month_count);
        profitMonThMoney=(TextView)view.findViewById(R.id.profit_month_money);

        calendarView=(CalendarView) view.findViewById(R.id.calendarView);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String selectedDate = sdf.format(new Date(calendarView.getDate()));
        StringTokenizer st=new StringTokenizer(selectedDate,"-");
        while(st.hasMoreTokens()){
            year1=st.nextToken();
            month1=st.nextToken();
            day1=st.nextToken();
        }
        getcountmoney();



        Log.i("현재 날짜",String.valueOf(selectedDate));
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                switch (month){
                    case 0:
                        month1="01";
                        break;
                    case 1:
                        month1="02";
                        break;
                    case 2:
                        month1="03";
                        break;
                    case 3:
                        month1="04";
                        break;
                    case 4:
                        month1="05";
                        break;
                    case 5:
                        month1="06";
                        break;
                    case 6:
                        month1="07";
                        break;
                    case 7:
                        month1="08";
                        break;
                    case 8:
                        month1="09";
                        break;
                    case 9:
                        month1="10";
                        break;
                    case 10:
                        month1="11";
                        break;
                    case 11:
                        month1="12";
                        break;
                }
                switch (dayOfMonth){
                    case 1:
                        day1="01";
                        break;
                    case 2:
                        day1="02";
                        break;
                    case 3:
                        day1="03";
                        break;
                    case 4:
                        day1="04";
                        break;
                    case 5:
                        day1="05";
                        break;
                    case 6:
                        day1="06";
                        break;
                    case 7:
                        day1="07";
                        break;
                    case 8:
                        day1="08";
                        break;
                    case 9:
                        day1="09";
                        break;
                    default:
                        day1=String.valueOf(dayOfMonth);
                        break;
                }
                getcountmoney();
                Log.i("달력",String.valueOf(year)+month1+day1);
            }
        });

        return view;
    }

    public void getcountmoney(){

        CustomTask1 customTask1=new CustomTask1();
        customTask1.execute(main.userid,year1,month1,day1);
    }

    class CustomTask1 extends AsyncTask<String, Void, String> {
        String sendMsg, receiveMsg;
        @Override
        protected String doInBackground(String... strings) {
            try {
                String str;
                URL url = new URL("http://220.122.180.160:8080/profit.jsp");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                sendMsg = "&id="+strings[0]+"&year="+strings[1]+"&month="+strings[2]+"&day="+strings[3];
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

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
//            updateprofit(receiveMsg);
            Log.i("받음", result);
            if (result != "") {


                JSONParser parser = new JSONParser();


                JSONObject wrapObject = null;
                try {
                    wrapObject = (JSONObject) parser.parse(result);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                profitDay.setText(year1+"년 "+month1+"월 "+day1+"일");
                profitMonth.setText(year1+"년 "+month1+"월");
                if((String) wrapObject.get("daycount")!=null){
                    profitDayCount.setText("총 " + (String) wrapObject.get("daycount") + "건");
                } else {
                    profitDayCount.setText("총 " + "0" + "건");
                }

                if((String) wrapObject.get("daymoney")!=null){
                    profitDayMoney.setText((String) wrapObject.get("daymoney") + "원");
                } else {
                    profitDayMoney.setText("0원");
                }

                if((String) wrapObject.get("monthcount")!=null){
                    profitMonthCount.setText("총 " + (String) wrapObject.get("monthcount") + "건");
                } else {
                    profitMonthCount.setText("총 " + "0" + "건");
                }

                if((String) wrapObject.get("monthmoney")!=null){
                    profitMonThMoney.setText((String) wrapObject.get("monthmoney") + "원");
                } else {
                    profitMonthCount.setText("0원");
                }


            }
        }

    }

}
