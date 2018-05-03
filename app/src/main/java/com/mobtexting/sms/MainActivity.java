package com.mobtexting.sms;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.mobtexting.Mobtexting;
import com.mobtexting.MobtextingInterface;
import com.mobtexting.ModelError;
import com.mobtexting.ServerResponse;


public class MainActivity extends AppCompatActivity implements MobtextingInterface{
    private Mobtexting mobtexting;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mobtexting=new Mobtexting(this);
        mobtexting.setFlashMessageSend(true);
        mobtexting.sendSMS("This is a test","7488792140",this);
    }

    @Override
    public void onResponse(ServerResponse serverResponse) {
        Log.d("response",serverResponse.getStatus()+"  "+serverResponse.getDescription()+"  "+serverResponse.getSmsId());
    }

    @Override
    public void onError(ModelError modelError) {
        Log.d("response",modelError.getStatus()+"  "+modelError.getDescription());
    }
}
