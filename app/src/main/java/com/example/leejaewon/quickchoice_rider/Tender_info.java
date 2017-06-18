package com.example.leejaewon.quickchoice_rider;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.MapView;
import com.skp.Tmap.TMapMarkerItem;
import com.skp.Tmap.TMapPoint;
import com.skp.Tmap.TMapView;

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
    private TMapView tMapView;
    MapView locationview;

    private Double startlati=0.0;
    private Double startlongi=0.0;
    private Double destinationlati=0.0;
    private Double destinationlongi=0.0;
    private String dis="0";

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
        startlati=Double.valueOf(intent.getStringExtra("startlati"));
        startlongi=Double.valueOf(intent.getStringExtra("startlongi"));
        destinationlati=Double.valueOf(intent.getStringExtra("destinationlati"));
        destinationlongi=Double.valueOf(intent.getStringExtra("destinationlongi"));
        dis=intent.getStringExtra("disable");
        if(dis.equals("1")) {
            tender.setVisibility(View.GONE);
        }




        listener lis = new listener();
        tender.setOnClickListener(lis);

        locationview=(MapView)findViewById(R.id.mapView);
        tMapView= new TMapView(this);
//        RelativeLayout mapView = new RelativeLayout(this);
        tMapView.setSKPMapApiKey("8c430cbd-0174-365a-879a-98909c5e6f73"); //발급받은 api 키


        tMapView.setCompassMode(false);
        tMapView.setIconVisibility(false);
        tMapView.setZoomLevel(15);
        tMapView.setMapType(TMapView.MAPTYPE_STANDARD);
        tMapView.setLanguage(TMapView.LANGUAGE_KOREAN);
        tMapView.setTrackingMode(false);
        tMapView.setSightVisible(true);
        tMapView.setCenterPoint(startlongi, startlati);

        TMapMarkerItem tourMarkerItem = new TMapMarkerItem();
        TMapPoint start = new TMapPoint(startlati,startlongi);
        tourMarkerItem.setTMapPoint(start);
        tourMarkerItem.setVisible(TMapMarkerItem.VISIBLE);

        TMapMarkerItem tourMarkerItem2 = new TMapMarkerItem();
        TMapPoint desti = new TMapPoint(destinationlati,destinationlongi);
        tourMarkerItem2.setTMapPoint(desti);
        tourMarkerItem2.setVisible(TMapMarkerItem.VISIBLE);
        locationview.addView(tMapView);
        tMapView.addMarkerItem("destination",tourMarkerItem2);
        tMapView.addMarkerItem("start", tourMarkerItem);
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
