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
          // LocationManager locationManager = (LocationManager) this.cordova.getActivity().getSystemService(Context.LOCATION_SERVICE);
          // Location myloc = new Location(LocationManager.GPS_PROVIDER);
          // boolean isSpoofed = myloc.isFromMockProvider() ? true : false;
          // objGPS.put("isMock", isSpoofed);

          LocationManager locationManager = (LocationManager) this.cordova.getActivity().getSystemService(Context.LOCATION_SERVICE);

                // getting GPS status
                isGPSEnabled = locationManager
                        .isProviderEnabled(LocationManager.GPS_PROVIDER);

                // getting network status
                isNetworkEnabled = locationManager
                        .isProviderEnabled(LocationManager.NETWORK_PROVIDER);


                if(!isGPSEnabled && !isNetworkEnabled) {
                    // no network provider is enabled
                }else{
                    if(isGPSEnabled){
                        LOCATION_PROVIDER = LocationManager.GPS_PROVIDER;
                    }
                    if(isNetworkEnabled){
                        LOCATION_PROVIDER = LocationManager.NETWORK_PROVIDER;
                    }
                }

                if(listenerON != true) {

                    // Define a listener that responds to location updates
                    locationListener = new LocationListener() {
                        public void onLocationChanged(Location location) {


                            // Called when a new location is found by the network location provider.

                            Date dateGPS = new Date(location.getTime());

                            String datetime = formatDate(dateGPS);

                             Log.e("DATA-GPS", "Lat:" + location.getLatitude() + " - Long:" + location.getLongitude() + " - Data e hora:" + datetime);

                             try{

                                 objGPS.put("lat",location.getLatitude());
                                 objGPS.put("long",location.getLongitude());
                                 objGPS.put("time",location.getTime());
                                 objGPS.put("formatTime",datetime);
                                 objGPS.put("extra",null);

                                 if (location.isFromMockProvider() == true) {
                                     objGPS.put("info","mock-true");
                                     statusMock = "mock-true";
                                 } else {
                                     objGPS.put("info","mock-false");
                                     statusMock = "mock-false";
                                 }

                                 if(arrayGPS.length() == 0){
                                     arrayGPS.put(objGPS);
                                 }

                                 Log.e("GPS-LOCATION-ARRAY", arrayGPS.toString());

                                 callbackContext.success(arrayGPS);


                             } catch (JSONException e) {
                                e.printStackTrace();
                                callbackContext.error(e.toString());
                             }
                            }
                        };
                      }
          callbackContext.success(objGPS);
          return true;
        }


        else{
          return false;
        }
        // }
        
        // else {
        //   return false;
        // }

    }
}