package com.example.leejaewon.quickchoice_rider;

import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by LeeJaeWon on 2017-05-19.
 */

public class content_information extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        View view= inflater.inflate(R.layout.myprofile,container,false);

        Typeface typeface1 = Typeface.createFromAsset(getActivity().getAssets(), "fonts/godic.ttf");
        TextView textView1 = (TextView)view.findViewById(R.id.textView);
        TextView textView2 = (TextView)view.findViewById(R.id.textView7);
        TextView textView3 = (TextView)view.findViewById(R.id.textView5);
        TextView textView4 = (TextView)view.findViewById(R.id.textView4);
        TextView textView5 = (TextView)view.findViewById(R.id.textView6);
        TextView textView6 = (TextView)view.findViewById(R.id.button3);
        TextView textView7 = (TextView)view.findViewById(R.id.button2);
        TextView textView8 = (TextView)view.findViewById(R.id.textView9);
        TextView textView9 = (TextView)view.findViewById(R.id.textView3);

        textView1.setTypeface(typeface1);
        textView2.setTypeface(typeface1);
        textView3.setTypeface(typeface1);
        textView4.setTypeface(typeface1);
        textView5.setTypeface(typeface1);
        textView6.setTypeface(typeface1);
        textView7.setTypeface(typeface1);
        textView8.setTypeface(typeface1);
        textView9.setTypeface(typeface1);
        return view;
    }
}
