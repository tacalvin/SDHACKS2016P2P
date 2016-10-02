package com.cta.sdhacks2016p2p;

import android.net.wifi.WpsInfo;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.WifiP2pInfo;
import android.widget.Toast;

import java.net.InetAddress;

/**
 * Created by cta on 10/1/16.
 */
public class PeerService
{
    P2PBroadcastReceiver radio;
    PeerService(P2PBroadcastReceiver r)
    {
        radio = r;
    }


    void connect(WifiP2pDevice dev)
    {
        WifiP2pConfig config = new WifiP2pConfig();
        config.deviceAddress = dev.deviceAddress;
        config.wps.setup = WpsInfo.PBC;

        WifiP2pManager mManager = radio.getManager();

        mManager.connect(radio.getChannel(), config, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess()
            {
                //Wifi Direct Broadcast receiver will notify us

            }

            @Override
            public void onFailure(int i)
            {
                Toast.makeText(radio.getActivity(),"Connection failed sorry",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onConnectionInfoAvailable(final WifiP2pInfo info)
    {

        // InetAddress from WifiP2pInfo struct.
        String groupOwnerAddress = info.groupOwnerAddress.getHostAddress();

        // After the group negotiation, we can determine the group owner.
        if (info.groupFormed && info.isGroupOwner)
        {
            // Do whatever tasks are specific to the group owner.
            // One common case is creating a server thread and accepting
            // incoming connections.
        }
        else if (info.groupFormed)
        {
            // The other device acts as the client. In this case,
            // you'll want to create a client thread that connects to the group
            // owner.
        }


    }
}

