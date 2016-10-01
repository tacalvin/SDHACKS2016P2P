package com.cta.sdhacks2016p2p;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager
import android.os.IBinder;

import java.util.ArrayList;

/**
 * Created by cta on 10/1/16.
 */
public class P2PBroadcastReceiver extends BroadcastReceiver
{
    private WifiP2pManager manager;
    private WifiP2pManager.Channel channel;
    private Activity activity;
    private P2PBroadcastReceiverAL actListener;
    private P2PBroadcastReciverPL peerListener;

    //Action Listener
    private class P2PBroadcastReceiverAL implements WifiP2pManager.ActionListener
    {
        @Override
        public void onSuccess()
        {

        }

        @Override
        public void onFailure(int i)
        {

        }
    }

    //Peer listen
    private class P2PBroadcastReciverPL implements WifiP2pManager.PeerListListener
    {
        @Override
        public void onPeersAvailable(WifiP2pDeviceList wifiP2pDeviceList) {

        }
    }

    public P2PBroadcastReceiver(WifiP2pManager manager, WifiP2pManager.Channel channel, Activity act)
    {
        super();
        this.manager = manager;
        this.channel = channel;
        this.activity = act;
        this.peerListener = new P2PBroadcastReciverPL();
        this.actListener = new P2PBroadcastReceiverAL();
    }

    @Override
    public IBinder peekService(Context myContext, Intent service)
    {
        return super.peekService(myContext, service);
    }



    @Override
    public void onReceive(Context context, Intent intent)
    {
        String action = intent.getAction();

        if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action))
        {
            // Check to see if Wi-Fi is enabled and notify appropriate activity
            manager.discoverPeers(channel,actListener);
        }
        else if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action))
        {
            manager.requestPeers(channel,peerListener);
            // Call WifiP2pManager.requestPeers() to get a list of current peers
        }
        else if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action))
        {
            // Respond to new connection or disconnections
        }
        else if (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action))
        {
            // Respond to this device's wifi state changing
        }
    }

}


