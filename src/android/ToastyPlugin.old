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

// import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
// import android.content.pm.PackageManager;
// import android.util.Log;

import java.util.List;

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
          Context ctx = this.cordova.getActivity().getApplicationContext();
          LocationManager locationManager = (LocationManager) this.cordova.getActivity().getSystemService(Context.LOCATION_SERVICE);
          Location myloc = new Location(LocationManager.GPS_PROVIDER);
          boolean isSpoofed = myloc.isFromMockProvider() ? true : false;
          objGPS.put("isMock", isSpoofed);
          objGPS.put("hasRunningMock", hasMockAppRunning(ctx));
          objGPS.put("hasSetMock", hasSetMock(ctx));

          callbackContext.success(objGPS);
          return true;
        }


        else{
          return false;
        }

    }

    public static boolean hasMockAppRunning(Context context){
      // ActivityManager am = new ActivityManager();
      // List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
      return false;
      // List<String> runningApps = getRunningApps(context);
      // // List<String> fakeApps = new ArrayList<>();
      // for(String app : runningApps){
      //   if(!isSystemPackage(context, app) && hasAppPermission(context, app, "android.permission.ACCESS_MOCK_LOCATION")){
      //       return true;
      //   }
      // }
      // return false;
    }

    public static boolean hasSetMock(Context context){
      PackageManager pm = context.getPackageManager();
      List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);
      if(packages != null){
        for(ApplicationInfo appInfo : packages){
          try{
            PackageInfo pi = pm.getPackageInfo(appInfo.packageName, PackageManager.GET_PERMISSIONS);
            String[] reqPerms = pi.requestedPermissions;
            if(reqPerms != null){
              for(int i = 0; i < reqPerms.length; i++){
                if(reqPerms[i].equals("android.permission.ACCESS_MOCK_LOCATION") && !appInfo.packageName.equals(context.getPackageName())){
                  return true;
                }
              }
            }
          } catch(Exception e){
            Log.e("Mock location check error", e.getMessage());
          }
        }
      }
      return false;
    }
}