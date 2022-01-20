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
  private String[] permissions = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
  private String packageName = "com.outsystemsenterprise.itpltst2.TTTamperingTesting";
  private CallbackContext context;
  private Context ctx;

    @Override
    public boolean execute(String action, JSONArray data, final CallbackContext callbackContext) throws JSONException {
      context = callbackContext;
      ctx = this.cordova.getActivity().getApplicationContext();

      if (action.equals("show")) {
          getPerms(1);
          checkDevOptions();
          checkPackageName();      
          callbackContext.success(objGPS);
          return true;
        }
        else{
          return false;
        }

    }

    private boolean hasPerms() throws JSONException{
      boolean hasPerms = true;
      for(String p : permissions){
        if(!PermissionHelper.hasPermission(this, p)) hasPerms = false;
      }
      return hasPerms;
    }

    private void getPerms(int requestCode){
      PermissionHelper.requestPermissions(this, requestCode, permissions);
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

    private void checkPackageName() throws JSONException{
      String currName = ctx.getPackageName();
      objGPS.put("PkgName", currName);
      objGPS.put("PkgNameModified", currName.equals(packageName));
    }

}