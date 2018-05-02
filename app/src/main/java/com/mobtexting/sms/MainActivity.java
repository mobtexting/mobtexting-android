package com.mobtexting.sms;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.mobtexting.Communicator;
import com.mobtexting.event.ErrorEvent;
import com.mobtexting.event.ServerEvent;
import com.squareup.otto.Subscribe;


public class MainActivity extends AppCompatActivity {
    private Communicator communicator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        communicator=new Communicator();
        communicator.loginPost("sdfsdfsfsdfsdf","dsfsdfsdf","7250705072","MOBTXT");
    }

    @Subscribe
    public void onServerEvent(ServerEvent serverEvent){
        Toast.makeText(this, ""+serverEvent.getServerResponse().getMessage(), Toast.LENGTH_SHORT).show();
        if(serverEvent.getServerResponse().getUsername() != null){
            Toast.makeText(getBaseContext(),serverEvent.getServerResponse().getUsername(),Toast.LENGTH_SHORT).show();
        }
        Toast.makeText(getBaseContext(),serverEvent.getServerResponse().getMessage(),Toast.LENGTH_SHORT).show();
    }

    @Subscribe
    public void onErrorEvent(ErrorEvent errorEvent){
        Toast.makeText(this,""+errorEvent.getErrorMsg(),Toast.LENGTH_SHORT).show();
    }
}
