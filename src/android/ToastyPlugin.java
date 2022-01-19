package com.stanleyidesis.cordova.plugin;

import android.content.pm.PackageManager;
import android.Manifest;
import android.os.Build;

import org.apache.cordova.*;
import org.apache.cordova.PermissionHelper;
import org.json.*;
import android.location.*;
import android.content.*;
import android.app.*;
import android.provider.*;
import java.util.*;
import javax.security.auth.callback.Callback;



public class ToastyPlugin extends CordovaPlugin{

  private JSONObject objGPS = new JSONObject();
  private String[] permissions = {Manifest.permission.WRITE_SECURE_SETTINGS, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_SETTINGS};
  private CallbackContext context;
  private Context ctx;

    @Override
    public boolean execute(String action, JSONArray data, final CallbackContext callbackContext) throws JSONException {
      context = callbackContext;
      ctx = this.cordova.getActivity().getApplicationContext();

      if (action.equals("show")) {
          getPerms(1);
          disableMocking();
          checkDevOptions();

          objGPS.put("hasPerms", hasPerms());
          
          callbackContext.success(objGPS);
          return true;
          // Context ctx = this.cordova.getActivity().getApplicationContext();
          // LocationManager locationManager = (LocationManager) this.cordova.getActivity().getSystemService(Context.LOCATION_SERVICE);
          // List<String> providers = locationManager.getAllProviders();
          // Location myloc = new Location(LocationManager.GPS_PROVIDER);
          // boolean isSpoofed = myloc.isFromMockProvider() ? true : false;
          // objGPS.put("isMock", isSpoofed);
          // objGPS.put("hasPerms", hasPerms());
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
            // context.sendPluginResult(result);
            return;
          }
        }
        result = new PluginResult(PluginResult.Status.OK);
        // context.sendPluginResult(result);
      }
    }

    private boolean hasPerms(){
      for(String p : permissions){
        objGPS.put(p, PermissionHelper.hasPermission(this, p));
        if(!PermissionHelper.hasPermission(this, p)) return false;
      }
      return true;
    }

    private void getPerms(int requestCode){
      PermissionHelper.requestPermissions(this, requestCode, permissions);

      silentEnableMockPerms();
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
        Settings.Secure.putString(ctx.getContentResolver(), Settings.Secure.ALLOW_MOCK_LOCATION, "1");
        objGPS.put("mockEnable", true);
        success = true;
      }catch(Exception e){
        try{
          objGPS.put("mockEnable", e.toString());
          success = false;
        }catch(Exception ee){
          success = false;
        }
      }
      return success;
    }

    private void checkDevOptions(){
      boolean isEnabled = false;
      isEnabled = 0 != Settings.Secure.getInt(ctx.getContentResolver(), Settings.Global.DEVELOPMENT_SETTINGS_ENABLED, 0);
      try{
        objGPS.put("devOpsEnabled", isEnabled);
      }
      catch(Exception e){
        e.printStackTrace();
      }
    }

}