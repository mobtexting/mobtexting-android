package com.mobtexting.sms.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

public class SmsReceiver extends BroadcastReceiver{
    private static SmsListener smsListener;
    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            Bundle data = intent.getExtras();

            Object[] pdus = (Object[]) data.get("pdus");
            for (int i = 0; i < pdus.length; i++) {
                SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdus[i]);
                String sender = smsMessage.getDisplayOriginatingAddress();
                if (sender != null) {
                    String messageBody = smsMessage.getMessageBody();
                    String msgLower = messageBody.toLowerCase();
                    smsListener.messageRceived(msgLower);
                }
            }
        }catch (Exception e){
            smsListener.messageRceived("something went wrong!");
        }
    }

    public static void bindListener(SmsListener listener) {
        smsListener = listener;
    }
}
