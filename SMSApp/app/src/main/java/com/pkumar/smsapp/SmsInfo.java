package com.pkumar.smsapp;

/**
 * Created by P KUMAR on 31-05-2016.
 */
public class SmsInfo {
    public String senderAddress;
    public int senderImage;
    public String date;
    public String body;

    public SmsInfo(String senderAddress,int senderImage,String date,String body) {
        this.senderAddress = senderAddress;
        this.senderImage = senderImage;
        this.date = date;
        this.body = body;
    }
}
