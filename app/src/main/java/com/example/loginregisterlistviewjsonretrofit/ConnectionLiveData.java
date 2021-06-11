package com.example.loginregisterlistviewjsonretrofit;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.lifecycle.LiveData;

import com.example.loginregisterlistviewjsonretrofit.viewModel.ConnectionModel;

import static com.example.loginregisterlistviewjsonretrofit.view.ListActivity.MobileData;
import static com.example.loginregisterlistviewjsonretrofit.view.ListActivity.WifiData;

//get help from https://medium.com/android-news/connection-detection-using-livedata-android-623bd02b0e30
public class ConnectionLiveData extends LiveData<ConnectionModel> {

    private Context context;

    public ConnectionLiveData(Context context) {
        this.context = context;
    }

    @Override
    protected void onActive() {
        super.onActive();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        context.registerReceiver(networkReceiver, filter);
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        context.unregisterReceiver(networkReceiver);
    }

    //broadcast receiver to listen for any state change. Register and unregister it with LiveData lifecycle
    private BroadcastReceiver networkReceiver = new BroadcastReceiver() {
        @SuppressWarnings("deprecation")
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getExtras()!=null) {
                NetworkInfo activeNetwork = (NetworkInfo) intent.getExtras().get(ConnectivityManager.EXTRA_NETWORK_INFO);
                boolean isConnected = activeNetwork != null &&
                        activeNetwork.isConnectedOrConnecting();
                if(isConnected) {
                    switch (activeNetwork.getType()){
                        case ConnectivityManager.TYPE_WIFI:
                            postValue(new ConnectionModel(WifiData,true));
                            break;
                        case ConnectivityManager.TYPE_MOBILE:
                            postValue(new ConnectionModel(MobileData,true));
                            break;
                    }
                } else {
                    postValue(new ConnectionModel(0,false));
                }
            }
        }
    };
}
