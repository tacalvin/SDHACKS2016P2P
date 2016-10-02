package com.cta.sdhacks2016p2p;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Context;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {

    WifiP2pManager mManager;
    WifiP2pManager.Channel mChannel;

    P2PBroadcastReceiver receiver;
    PeerService pserv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample_main);
        Log.d("Init","Probably failed to init");
        mManager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        mChannel = mManager.initialize(this, getMainLooper(), null);


        receiver = new P2PBroadcastReceiver(mManager,mChannel,this);
        pserv = new PeerService(receiver);
        WifiP2pDeviceList listOdev = receiver.getPeers();
        Collection<WifiP2pDevice> devices = listOdev.getDeviceList();
        Log.d("Attempt","Attempt to find something");
        for (Iterator<WifiP2pDevice> it = devices.iterator(); it.hasNext();)
        {
            WifiP2pDevice dev = it.next();
            Log.d("Devices",dev.deviceName);
            if(dev.status == dev.FAILED || dev.status == dev.UNAVAILABLE)
            {
                devices.remove(dev);
                break;
            }
            Log.d("Attempt","Attempt to find something");
            pserv.connect(dev);
        }
    }
}
