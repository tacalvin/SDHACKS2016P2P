package com.cta.sdhacks2016p2p;

import android.net.wifi.p2p.WifiP2pDevice;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ChatScreen extends AppCompatActivity {

    WifiP2pDevice recipiant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_chat_screen);

    }


    protected void sendButton(View view)
    {

    }
}
