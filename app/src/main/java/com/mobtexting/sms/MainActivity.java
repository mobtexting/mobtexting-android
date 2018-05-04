package com.mobtexting.sms;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.mobtexting.Mobtexting;
import com.mobtexting.MobtextingInterface;
import com.mobtexting.ModelError;
import com.mobtexting.ServerResponse;

import java.util.Random;


public class MainActivity extends AppCompatActivity implements MobtextingInterface{
    private Mobtexting mobtexting;
    private String otp_sixdigit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        otp_sixdigit=String.valueOf(generateSixDigitRandomNumber());

        Log.d("otp_sixdigit",otp_sixdigit);

        mobtexting=new Mobtexting(this);
        mobtexting.sendSMS("This is a test "+otp_sixdigit,"7488792140",this);
    }

    //Generate 6 digit number
    private int generateSixDigitRandomNumber(){
        Random rnd = new Random();
        int n = 100000 + rnd.nextInt(900000);
        return n;
    }

    @Override
    public void onResponse(ServerResponse serverResponse) {
        Log.d("response",serverResponse.getStatus()+"  "+serverResponse.getDescription()+"  "+serverResponse.getSmsId());

        //pass the 6 digit OTP to OTPActivity class
        Intent intent=new Intent(getBaseContext(),OTPActivity.class);
        intent.putExtra("otp",otp_sixdigit);
        startActivity(intent);
    }

    @Override
    public void onError(ModelError modelError) {
        Log.d("response",modelError.getStatus()+"  "+modelError.getDescription());
    }
}
