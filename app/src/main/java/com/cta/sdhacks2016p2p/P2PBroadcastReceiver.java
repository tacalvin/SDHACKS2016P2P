package com.cta.sdhacks2016p2p;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.IBinder;
import android.util.Log;
import android.support.v7.app.AppCompatActivity;

import java.util.Collection;
import java.util.Iterator;

/**
 * Created by cta on 10/1/16.
 */
public class P2PBroadcastReceiver extends BroadcastReceiver
{
    private WifiP2pManager manager;
    private WifiP2pDeviceList peers;
    private WifiP2pManager.Channel channel;
    private Activity activity;
    private P2PBroadcastReceiverAL actListener;
    private P2PBroadcastReceiverPL peerListener;

    //Action Listener
    private class P2PBroadcastReceiverAL implements WifiP2pManager.ActionListener
    {
        @Override
        public void onSuccess()
        {
            Log.d("GREEN", "Hurrah Wifi is alive");
        }

        @Override
        public void onFailure(int i)
        {
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(i);
        }
    }

    //Peer listen
    private class P2PBroadcastReceiverPL implements WifiP2pManager.PeerListListener
    {
        WifiP2pDeviceList list;
        P2PBroadcastReceiverPL(WifiP2pDeviceList list)
        {
            this.list = list;
        }

        @Override
        public void onPeersAvailable(WifiP2pDeviceList wifiP2pDeviceList)
        {
            list = wifiP2pDeviceList;
        }
    }

    public P2PBroadcastReceiver(WifiP2pManager manager, WifiP2pManager.Channel channel, Activity act)
    {
        super();
        this.manager = manager;
        this.channel = channel;
        this.activity = act;
        this.peers = new WifiP2pDeviceList();
        this.peerListener = new P2PBroadcastReceiverPL(this.peers);
        this.actListener = new P2PBroadcastReceiverAL();
        manager.discoverPeers(channel,actListener);
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
            Collection<WifiP2pDevice> devices = peers.getDeviceList();

            for (Iterator<WifiP2pDevice> it = devices.iterator(); it.hasNext();)
            {
                WifiP2pDevice dev = it.next();
                Log.d("Devices",dev.deviceName);
                if(dev.status == dev.FAILED || dev.status == dev.UNAVAILABLE)
                {
                    devices.remove(dev);
                    break;
                }
            }
            manager.requestPeers(channel,peerListener);


        }
        else if (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action))
        {

            // Respond to this device's wifi state changing
            Log.d("RED","WIFI IS CHANGING");
        }
    }

}


