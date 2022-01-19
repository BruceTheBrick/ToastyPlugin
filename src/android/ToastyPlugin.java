package com.stanleyidesis.cordova.plugin;

import android.content.pm.PackageManager;
import android.Manifest;
import android.os.Build;

import org.apache.cordova.*;
import org.json.*;
import android.location.*;
import android.content.*;
import android.app.*;
import android.provider.*;
import java.util.*;
import javax.security.auth.callback.Callback;



public class ToastyPlugin extends CordovaPlugin{

  private JSONObject objGPS = new JSONObject();
  private String[] permissions = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS};
  private CallbackContext context;

    @Override
    public boolean execute(String action, JSONArray data, final CallbackContext callbackContext) throws JSONException {
      context = callbackContext;
        if (action.equals("show")) {
          disableMocking();
          silentEnableMockPerms();
          if(hasPerms()){
            objGPS.put("hasPerms", hasPerms());

          }
          else{
            getPerms(0);
          }

          // Context ctx = this.cordova.getActivity().getApplicationContext();
          // LocationManager locationManager = (LocationManager) this.cordova.getActivity().getSystemService(Context.LOCATION_SERVICE);
          // List<String> providers = locationManager.getAllProviders();
          // Location myloc = new Location(LocationManager.GPS_PROVIDER);
          // boolean isSpoofed = myloc.isFromMockProvider() ? true : false;
          // objGPS.put("isMock", isSpoofed);
          // objGPS.put("hasPerms", hasPerms());
          callbackContext.success(objGPS);
          return true;
        }


        else{
          return false;
        }

    }

    public void onRequestPermissionResult(int requestCode, String[] permissions, int[] grantResults) throws JSONException{
      PluginResult result;

      if(context != null){
        for(int r : grantResults){
          if(r == PackageManager.PERMISSION_DENIED){
            result = new PluginResult(PluginResult.Status.ILLEGAL_ACCESS_EXCEPTION);
            context.sendPluginResult(result);
            return;
          }
        }
        result = new PluginResult(PluginResult.Status.OK);
        context.sendPluginResult(result);
      }
    }

    private boolean hasPerms(){
      for(String p : permissions){
        if(!PermissionHelper.hasPermission(this, p)) return false;
      }
      return true;
    }

    private void getPerms(int requestCode){
      PermissionHelper.requestPermissions(this, requestCode, permissions);
    }

    private boolean disableMocking(){
      LocationManager lm = (LocationManager) this.cordova.getActivity().getSystemService(Context.LOCATION_SERVICE);
      List<String> providers = lm.getAllProviders();
      try{
        for(String provider : providers){
          lm.removeTestProvider(provider);
          objGPS.put("removedTestProviders", true);
        }
        return true;
      }catch(Exception e){
        try{
        objGPS.put("removedTestProviders", false);
        objGPS.put("error", e.toString());
        }catch(Exception ee){
          return false;
        }
        return false;
      }
    }

    private boolean silentEnableMockPerms(){
      boolean success = false;
      try{
        Settings.Secure.putString(context.getContentResolver(), Settings.Secure.ALLOW_MOCK_LOCATION, "1");
        success = true;
      }catch(Exception e){
        objGPS.put("mockEnable", e.toString());
        success = false;
      }
      return success;
    }

}