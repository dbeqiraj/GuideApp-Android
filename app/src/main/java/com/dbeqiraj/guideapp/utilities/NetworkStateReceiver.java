package com.dbeqiraj.guideapp.utilities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkStateReceiver extends BroadcastReceiver {

    protected NetworkStateReceiverListener listener;
    protected Boolean connected;

    public NetworkStateReceiver() {
        listener = null;
        connected = false;
    }

    public void onReceive(Context context, Intent intent) {
        if(intent == null || intent.getExtras() == null)
            return;

        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = manager.getActiveNetworkInfo();

        connected   =   (ni != null && ni.isConnected());

        notifyState(listener);
    }

    private void notifyState(NetworkStateReceiverListener listener) {
        if(connected == null || listener == null)
            return;

        if(connected)
            listener.networkAvailable();
        else
            listener.networkUnavailable();
    }

    public void setListener(NetworkStateReceiverListener l) {
        listener    =   l;
    }

    public interface NetworkStateReceiverListener {
        public void networkAvailable();
        public void networkUnavailable();
    }
}
