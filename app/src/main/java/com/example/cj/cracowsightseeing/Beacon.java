package com.example.cj.cracowsightseeing;

import android.Manifest;
//import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.kontakt.sdk.android.ble.connection.OnServiceReadyListener;
import com.kontakt.sdk.android.ble.manager.ProximityManager;
import com.kontakt.sdk.android.ble.manager.ProximityManagerFactory;
import com.kontakt.sdk.android.ble.manager.listeners.EddystoneListener;
import com.kontakt.sdk.android.ble.manager.listeners.IBeaconListener;
import com.kontakt.sdk.android.ble.manager.listeners.simple.SimpleEddystoneListener;
import com.kontakt.sdk.android.ble.manager.listeners.simple.SimpleIBeaconListener;

import com.kontakt.sdk.android.common.KontaktSDK;
import com.kontakt.sdk.android.common.profile.IBeaconDevice;
import com.kontakt.sdk.android.common.profile.IBeaconRegion;
import com.kontakt.sdk.android.common.profile.IEddystoneDevice;
import com.kontakt.sdk.android.common.profile.IEddystoneNamespace;

public class Beacon extends AppCompatActivity{

    private ProximityManager proximityManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.beacon);
//        checkPermissionAndStart();
//        int permissionCheck = ContextCompat.checkSelfPermission(this,
//                Manifest.permission.ACCESS_COARSE_LOCATION);
        KontaktSDK.initialize("HrIntLkGdnKSpkrrSJZpesIMXcOkUTht");

        proximityManager = ProximityManagerFactory.create(this);
        proximityManager.setIBeaconListener(createIBeaconListener());
        proximityManager.setEddystoneListener(createEddystoneListener());

    }

    @Override
    protected void onStart() {
        super.onStart();
//        checkPermissionAndStart();
        startScanning();
    }

    @Override
    protected void onStop() {
        proximityManager.stopScanning();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        proximityManager.disconnect();
        proximityManager = null;
        super.onDestroy();
    }

    private void startScanning() {
        proximityManager.connect(new OnServiceReadyListener() {
            @Override
            public void onServiceReady() {
                proximityManager.startScanning();
            }
        });
    }

    private IBeaconListener createIBeaconListener() {
        return new SimpleIBeaconListener() {
            @Override
            public void onIBeaconDiscovered(IBeaconDevice ibeacon, IBeaconRegion region) {
                Log.i("Sample", "IBeacon discovered: " + ibeacon.toString());
            }
        };
    }

    private EddystoneListener createEddystoneListener() {
        return new SimpleEddystoneListener() {
            @Override
            public void onEddystoneDiscovered(IEddystoneDevice eddystone, IEddystoneNamespace namespace) {
                Log.i("Sample", "Eddystone discovered: " + eddystone.toString());
            }
        };
    }

//    public void checkPermissionAndStart() {
//        int checkSelfPermissionResult = ContextCompat.checkSelfPermission(Beacon.this, Manifest.permission.ACCESS_COARSE_LOCATION);
////        ContextCompat.checkSelfPermission(Beacon.this, Manifest.permission.BLUETOOTH);
//
//        if (PackageManager.PERMISSION_GRANTED == checkSelfPermissionResult) {
//            Log.i("checkPermission: ", "zaakceptowane");
//            //already granted
////            startScanning();
//        } else {
//            //request permission
//            Log.i("checkPermission:", "niezaakpcetowane");
//            ActivityCompat.requestPermissions(Beacon.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
//
//        }
//    }
}
