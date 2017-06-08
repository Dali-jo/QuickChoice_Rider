package com.example.leejaewon.quickchoice_rider;

/**
 * Created by LeeJaeWon on 2017-05-29.
 */

public class worklist_item {
    private String start;
    private String desti;
    private String pickup;
    private String category;
    private String money;
    private String phone;
    private String no;
    private String pay;
    private String fast;
    private String memo;



    public worklist_item(String start,String desti,String pickup,String category,String money,String phone,String no,String pay,String fast,String memo){
        this.start=start;
        this.desti=desti;
        this.pickup=pickup;
        this.category=category;
        this.money=money;
        this.phone=phone;
        this.no=no;
        this.pay=pay;
        this.fast=fast;
        this.memo=memo;

    }

    public String getStart(){return start;}
    public String getDesti(){return desti;}
    public String getPickup(){return pickup;}
    public String getCategory(){return category;}
    public String getMoney(){return money;}
    public String getPhone(){return phone;}
    public String getNo(){return no;}
    public String getPay(){return pay;}
    public String getFast(){return fast;}
    public String getMemo(){return memo;}
}
