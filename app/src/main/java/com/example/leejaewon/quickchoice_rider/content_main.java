package com.example.leejaewon.quickchoice_rider;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by LeeJaeWon on 2017-05-19.
 */

public class content_main extends Fragment {
    private RecyclerView requestlistView;
    private ArrayList<requestlist_item> requestlist_item_ArrayList;
    private requestlist_adapter requestlist_adapter;
    private GestureDetector gestureDetector;
    private String id;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view= inflater.inflate(R.layout.content_main_recyclerview,container,false);
        id = ((main)getActivity()).userid;

        gestureDetector = new GestureDetector(getActivity(),new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onSingleTapUp(MotionEvent e){
                return true;
            }
        });


        requestlist_item_ArrayList=new ArrayList<>();
        requestlistView=(RecyclerView) view.findViewById(R.id.request_recyclerview);
        requestlistView.setHasFixedSize(true);

        CustomTask customTask = new CustomTask();
        customTask.execute();






        return view;
    }






    class CustomTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings){
            InputStream inputStream=null;
            String result="";
            String string =((main)getActivity()).userid;
            StringBuilder sb=null;
//            Log.i("유저아이디: ",string);
            try {



                URL url = new URL("http://220.122.180.160:8080/orderlistrider.jsp");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");

//                JSONObject jsonObject=new JSONObject();
//                JSONArray jsonArray=new JSONArray();
//
//                JSONObject jsonObject1=new JSONObject();
//
//                jsonObject1.put("userid",string);
//                jsonArray.put(jsonObject1);
//                jsonObject.put("list",jsonArray);
//
//
//                String json=jsonObject.toString();
//                httpURLConnection.setRequestProperty("Accept", "application/json");
//                httpURLConnection.setRequestProperty("Content-type", "application/json");




                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream os =httpURLConnection.getOutputStream();

                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "EUC-KR"));
                writer.write("");
                writer.flush();
                writer.close();
                os.close();

                httpURLConnection.connect();

                BufferedReader br = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), "EUC-KR")); //캐릭터셋 설정

                sb = new StringBuilder();
                String line = null;
                while ((line = br.readLine()) != null) {
                    if(sb.length() > 0) {
                        sb.append("\n");
                    }
                    sb.append(line);
                }

                receiveArray(sb.toString());
                publishProgress();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return sb.toString();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            // TODO Auto-generated method stub
            super.onProgressUpdate(values);

            requestlist_adapter = new requestlist_adapter(getActivity(),requestlist_item_ArrayList);
            requestlistView.setLayoutManager(new LinearLayoutManager(getActivity()));

            requestlistView.setAdapter(requestlist_adapter);


        }
    }

    public  void receiveArray(String dataObject){



        try {
            JSONParser parser = new JSONParser();


            JSONObject wrapObject = null;
            wrapObject = (JSONObject)parser.parse(dataObject);

//            wrapObject.get("list");


            JSONArray jsonArray=  (JSONArray)wrapObject.get("list");
//            JSONArray jsonArray=new JSONArray(wrapObject.toString());
            for(int i=0;i<jsonArray.size();i++){
                JSONObject dataObject1 = (JSONObject)jsonArray.get(i);
                requestlist_item item = new requestlist_item((String)dataObject1.get("no"),(String)dataObject1.get("startadd"),(String)dataObject1.get("destinationadd"),(String)dataObject1.get("hopemoney"),(String)dataObject1.get("state"),(String)dataObject1.get("userid"),(String)dataObject1.get("category"),(String)dataObject1.get("pay"),(String)dataObject1.get("fast"),(String)dataObject1.get("memo"),(String)dataObject1.get("pickup"),(String)dataObject1.get("startlati"),(String)dataObject1.get("startlongi"),(String)dataObject1.get("destinationlati"),(String)dataObject1.get("destinationlongi"));


                requestlist_item_ArrayList.add(item);
                Log.i("아이템 등록" , requestlist_item_ArrayList.toString());
            }








        } catch (ParseException e) {
            e.printStackTrace();
        }


    }
}
