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

  private static String packageName = "com.outsystemsenterprise.itpltst2.TTTamperingTesting";
  private static double DEV_OPS_ENABLED_SCORE = 0.3;
  private static double PACKAGE_NAME_MODIFIED = 0.5;


  private JSONObject objGPS = new JSONObject();
  private String[] permissions = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
  private double CONFIDENCE_SCORE = 0.0;
  private CallbackContext context;
  private Context ctx;

    @Override
    public boolean execute(String action, JSONArray data, final CallbackContext callbackContext) throws JSONException {
      context = callbackContext;
      ctx = this.cordova.getActivity().getApplicationContext();

      if (action.equals("checkMock")) {
          getPerms(1);
          checkDevOptions();
          checkPackageName();      
          objGPS.put("spoofing_confidence", CONFIDENCE_SCORE);
          callbackContext.success(objGPS);
          return true;
        }
        else{
        // callbackContext.failure();
          return false;
        }

    }

    private boolean hasPerms() throws JSONException{
      for(String p : permissions){
        if(!PermissionHelper.hasPermission(this, p)) return false;
      }
      return true;
    }

    private void getPerms(int requestCode){
      PermissionHelper.requestPermissions(this, requestCode, permissions);
    }

    private void checkDevOptions(){
      boolean isEnabled = false;
      if(Settings.Secure.getInt(ctx.getContentResolver(), Settings.Global.DEVELOPMENT_SETTINGS_ENABLED, 0) != 0){
        CONFIDENCE_SCORE += DEV_OPS_ENABLED_SCORE;
      }
    }

    private void checkPackageName() throws JSONException{
      String currName = ctx.getPackageName();
      if(!currName.equals(packageName)){
        CONFIDENCE_SCORE += PACKAGE_NAME_MODIFIED;
      }
    }

}