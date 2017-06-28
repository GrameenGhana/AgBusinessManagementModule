package org.grameen.agbusinessmanagementmodule;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.net.Uri;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;

import java.io.File;
import java.io.IOException;

/**
 * Created by aangjnr on 28/03/2017.
 */

public class Module {

    static String TAG = Module.class.getSimpleName();


    public static Uri get(String mediaPath) {
        return Uri.parse("file:///android_asset/" + mediaPath);
    }



    public static AssetFileDescriptor descriptor(Context context, String mediaPath ) {
        try {
            return context.getAssets().openFd(mediaPath);
        } catch (IOException e) {
            return null;
        }
    }




    public static Uri getRawVideo(Context context, String relativeVideoName) {
        int res = context.getResources().getIdentifier(relativeVideoName, "raw", context.getPackageName());
        if (res == 0) {
            throw new IllegalArgumentException("Unable to resolve identifier for resource " + relativeVideoName);
        }
        return Uri.parse("android.resource://" + context.getPackageName() + "/" + res);
    }




    public static Uri getFile(Context context, String videoFileName) {

        String file = "";
        File videoFile;

        if(PreferenceManager.getDefaultSharedPreferences(context).getBoolean("sdcard", false)){


              videoFile = new File(Environment
                    .getExternalStorageDirectory().getPath() +  File.separator + "FFH" +  File.separator + context.getString(R.string.app_name) + File.separator + videoFileName + ".mp4" );

            Log.i(TAG, "Load from SD " + videoFile);


            if(!videoFile.exists()){
                Log.i(TAG, "File doesn't exist in internal storage. Checking external");

                videoFile = new File("/sdcard/" +  "FFH" +  File.separator + context.getString(R.string.app_name) + File.separator + videoFileName + ".mp4" );

                Log.i(TAG, "Load from ExtSD " + videoFile);

                if(videoFile.exists()){
                    Log.i(TAG, "File exist in external storage");
                    file = videoFile.getAbsolutePath();

                }else{
                    Log.i(TAG, "File doesn't exist on any storage at all! returned null");
                    return null;
                }


            }else {

                Log.i(TAG, "File exist in internal storage");
                file = videoFile.getAbsolutePath();


            }











        }
        else {
            Log.i(TAG, "Load from APP");
            file = "android.resource://" + context.getPackageName() + "/raw/" + videoFileName;
        }




        Log.i(TAG, "File location is " + file);




            return Uri.parse(file);

        }








}
