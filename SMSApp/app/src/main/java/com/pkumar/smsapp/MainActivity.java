package com.pkumar.smsapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateUtils;
import android.view.View;



import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private FloatingActionButton fab;
    private static MainActivity inst;
    ArrayList<String> smsMessagesList = new ArrayList<String>();
    ArrayList<String> shortSMS = new ArrayList<String>();


    //new
    ArrayList<SmsInfo> smsInfos = new ArrayList<SmsInfo>();
    ArrayAdapter smsAdapter;

    public static MainActivity instance() {
        return inst;
    }

    @Override
    public void onStart() {
        super.onStart();
        inst = this;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.sms_list);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);

        smsAdapter = new SmsAdapter(this,smsInfos);
        ListView smsListView = (ListView) findViewById(R.id.SMSList);
        smsListView.setAdapter(smsAdapter);
        smsListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                try {
                    SmsInfo smsMessage = smsInfos.get(position);
                    Toast.makeText(MainActivity.this, smsMessage.body, Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        refreshSmsInbox();
    }

    public void refreshSmsInbox() {
        //constants
        int indexAddress = 2;
        int indexBody = 13;
        int indexTime = 6;
        int indexTimeSent = 5;

        ContentResolver contentResolver = getContentResolver();

        // For Inbox sms
        Cursor smsCursor = contentResolver.query(Uri.parse("content://sms/inbox"), null, null, null, null);

        if (indexBody < 0 || !smsCursor.moveToFirst()) return;
        smsAdapter.clear();
        int i=0;
        do{
            String sender = smsCursor.getString(indexAddress).toLowerCase();
            if(shortSMS.contains(sender) == false){
                shortSMS.add(i,sender);
                i++;

                Date smsDate = new Date(smsCursor.getLong(indexTime));
                String formattedDate = new SimpleDateFormat("MM/dd/yyyy HH:MM:SS").format(smsDate);
                long timeNow = System.currentTimeMillis();
                long smsDateLong = smsCursor.getLong(indexTime);

                String time = (String) DateUtils.getRelativeTimeSpanString(smsDateLong, timeNow, DateUtils.MINUTE_IN_MILLIS);
                String body = smsCursor.getString(indexBody);
                String senderAddress = smsCursor.getString(indexAddress);
                int senderImage = R.drawable.heart;
                smsAdapter.add(new SmsInfo(senderAddress,senderImage,time,body));
            }
        }while(smsCursor.moveToNext());

    }

    public void updateList(final String smsMessageStr) {
        smsAdapter.insert(new SmsInfo("add",R.drawable.heart,smsMessageStr,"date"), 0);
        smsAdapter.notifyDataSetChanged();
    }


    @Override
    public void onClick(View v){
        Intent intent = new Intent(MainActivity.this, NewMessageActivity.class);
        startActivity(intent);
    }

}