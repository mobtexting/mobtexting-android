package com.mobtexting.sms;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mobtexting.sms.receivers.SmsListener;
import com.mobtexting.sms.receivers.SmsReceiver;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OTPActivity extends AppCompatActivity {
    private EditText otpEditText;
    private Button btnVerify;
    private String otp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        otpEditText = (EditText) findViewById(R.id.otpEditText);
        btnVerify = (Button) findViewById(R.id.btnVerify);

        //receive the 6 digit generated OTP from previous activity
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            otp = extras.getString("otp");
        }

        //sms receiver to auto verification
        SmsReceiver.bindListener(new SmsListener() {
            @Override
            public void messageRceived(String messageText) {
                try {
                    String sixOTPDigit = extractDigits(messageText);
                    otpEditText.setText(sixOTPDigit);
                }catch (Exception e){
                    Log.d("exception", "Something Went Wrong!");
                }
            }
        });


        //button click listener
        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check the OTP verification
                if ((otp != null) && (otp.equals(otpEditText.getText().toString().trim()))) {
                    Log.d("verified","OTP verified successfully.");
                }else{
                    Log.d("verified","OTP verified not successfully");
                }
            }
        });
    }

    /**
     * extract digit from string
     * @param in
     * @return
     */
    public static String extractDigits(final String in) {
        final Pattern p = Pattern.compile( "(\\d{6})" );
        final Matcher m = p.matcher( in );
        if ( m.find() ) {
            return m.group( 0 );
        }
        return "";
    }
}
