package com.stanleyidesis.cordova.plugin;

import android.content.pm.PackageManager;
import android.Manifest;
import android.os.Build;

import org.apache.cordova.*;
import org.json.*;
import android.location.*;
import android.content.*;
import java.util.*;
import javax.security.auth.callback.Callback;



public class ToastyPlugin extends CordovaPlugin{

  private JSONObject objGPS = new JSONObject();

    private String[] permissions = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};

    @Override
    public boolean execute(String action, JSONArray data, final CallbackContext callbackContext) throws JSONException {

        if (action.equals("show")) {
          Context ctx = this.cordova.getActivity().getApplicationContext();
          LocationManager locationManager = (LocationManager) this.cordova.getActivity().getSystemService(Context.LOCATION_SERVICE);
          List<String> providers = locationManager.getAllProviders();
          Location myloc = new Location(LocationManager.GPS_PROVIDER);
          boolean isSpoofed = myloc.isFromMockProvider() ? true : false;
          objGPS.put("isMock", isSpoofed);
          objGPS.put("hasPerms", hasPerms());
          callbackContext.success(objGPS);
          return true;
        }

        else{
          return false;
        }

    }

    private boolean hasPerms(){
      for(String p : permissions){
        if(!PermissionHelper.hasPermission(this, p)) return false;
      }
      return true;
    }

}