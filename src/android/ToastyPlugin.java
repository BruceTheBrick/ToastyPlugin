package com.stanleyidesis.cordova.plugin;

import org.apache.cordova.*;
import org.json.JSONArray;
import org.json.JSONException;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

public class ToastyPlugin extends CordovaPlugin{

    private int MY_PERMISSIONS_REQUEST = 0;

    // flag for GPS status
    boolean isGPSEnabled = false;

    // flag for network status
    boolean isNetworkEnabled = false;
    private String LOCATION_PROVIDER = "";
    LocationListener locationListener;
    private boolean listenerON = false;
    private String statusMock = "";
    private JSONArray arrayGPS = new JSONArray();
    private JSONObject objGPS = new JSONObject();

    @Override
    public boolean execute(String action, JSONArray data, final CallbackContext callbackContext) throws JSONException {

        if (action.equals("show")) {

                // Acquire a reference to the system Location Manager
          LocationManager locationManager = (LocationManager) this.cordova.getActivity().getSystemService(Context.LOCATION_SERVICE);

          // getting GPS status
        //   isGPSEnabled = locationManager
        //           .isProviderEnabled(LocationManager.GPS_PROVIDER);

        // // getting network status
        //   isNetworkEnabled = locationManager
        //           .isProviderEnabled(LocationManager.NETWORK_PROVIDER);


        //   if(!isGPSEnabled && !isNetworkEnabled) {
        //       // no network provider is enabled
        //   }else{
        //       if(isGPSEnabled){
        //           LOCATION_PROVIDER = LocationManager.GPS_PROVIDER;
        //       }
        //       if(isNetworkEnabled){
        //           LOCATION_PROVIDER = LocationManager.NETWORK_PROVIDER;
        //       }
        //   }

          Location myloc = new Location(LocationManager.GPS_PROVIDER);
          boolean isSpoofed = myloc.isFromMockProvider() ? true : false;
          try{
            objGPS.put("isMock", isSpoofed);
          }
          catch(JSONException e){
            callbackContext.error(e.toString());
          }
        
          return true;
        }
        else {
          return false;
        }

    }
}