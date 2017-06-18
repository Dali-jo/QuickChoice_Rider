package com.example.leejaewon.quickchoice_rider;

/**
 * Created by LeeJaeWon on 2017-05-14.
 */

public class requestlist_item {
    private String no;
    private String start;
    private String desti;
    private String money;
    private String state;
    private String userid;
    private String category;
    private String pay;
    private String fast;
    private String memo;
    private String pickup;
    private String startlati;
    private String startlongi;
    private String destinationlati;
    private String destinationlongi;



    public requestlist_item(String no, String start, String desti, String money, String state,String userid,String category,String pay,String fast,String memo,String pickup,String startlati,String startlongi,String destinationlati,String destinationlongi) {
        this.no=no;
        this.start=start;
        this.desti=desti;
        this.money=money;
        this.state=state;
        this.userid=userid;
        this.category=category;
        this.pay=pay;
        this.fast=fast;
        this.memo=memo;
        this.pickup=pickup;
        this.startlati=startlati;
        this.startlongi=startlongi;
        this.destinationlati=destinationlati;
        this.destinationlongi=destinationlongi;
    }

    public String getNo(){
        return no;
    }
    public String getStart(){
        return start;
    }
    public String getDesti(){
        return desti;
    }
    public String getMoney(){
        return money;
    }
    public String getState(){
        return state;
    }
    public String getUserid(){return userid;}
    public String getCategory(){return category;}
    public String getPay(){return pay;}
    public String getFast(){return fast;}
    public String getMemo(){return memo;}
    public String getPickup(){return pickup;}
    public String getStartlati(){return startlati;}
    public String getStartlongi(){return startlongi;}
    public String getDestinationlati(){return destinationlati;}
    public String getDestinationlongi(){return destinationlongi;}



}
