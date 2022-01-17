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
    LocationManager locationManager;
    Location currentLocation;
    private boolean listenerON = false;
    private String statusMock = "";
    private JSONArray arrayGPS = new JSONArray();
    private JSONObject objGPS = new JSONObject();
    private Location GLOBAL_LOCATION;

    @Override
    public boolean execute(String action, JSONArray data, final CallbackContext callbackContext) throws JSONException {
      getLocation();
        if (action.equals("show")) {
          // LocationManager locationManager = (LocationManager) this.cordova.getActivity().getSystemService(Context.LOCATION_SERVICE);
          LocationProvider lProvider = locationManager.getProvider(LocationManager.GPS_PROVIDER);
          // Location myloc = new Location(LocationManager.GPS_PROVIDER);
          boolean isSpoofed = currentLocation.isFromMockProvider() ? true : false;
          objGPS.put("isMock", isSpoofed);
          objGPS.put("location", currentLocation);
          callbackContext.success(objGPS);
          return true;
        }
        else{
          return false;
        }

    }

    void getLocation(){
      try{
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 5, this);
      }
      catch(Exception e){
        e.printStackTrace();
      }
    }

    @Override
    public void onLocationChanged(Location location){
      currentLocation = location;
    }
}