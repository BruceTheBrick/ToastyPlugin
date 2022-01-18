package com.stanleyidesis.cordova.plugin;

import android.content.pm.PackageManager;
import android.Manifest;
import android.os.Build;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaArgs;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PermissionHelper;
import org.apache.cordova.PluginResult;
import org.apache.cordova.LOG;
import org.json.JSONArray;
import org.json.JSONException;

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