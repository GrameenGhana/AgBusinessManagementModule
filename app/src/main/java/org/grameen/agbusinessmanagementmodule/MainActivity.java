package org.grameen.agbusinessmanagementmodule;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import java.io.File;
import java.io.FileOutputStream;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    String TAG = MainActivity.class.getSimpleName();

    String sectionName = "";

    int drawable = 0;

    LinearLayout section1;
    LinearLayout section2;
    LinearLayout section3;
    LinearLayout section4;
    LinearLayout section5;
    LinearLayout section6;
    LinearLayout section7;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        findViewById(R.id.session1).setOnClickListener(this);
        findViewById(R.id.session2).setOnClickListener(this);
        findViewById(R.id.session3).setOnClickListener(this);
        findViewById(R.id.session4).setOnClickListener(this);
        findViewById(R.id.session5).setOnClickListener(this);
        findViewById(R.id.session6).setOnClickListener(this);
        findViewById(R.id.session7).setOnClickListener(this);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    checkPermission();
                }
            }
        }, 2000);


    }


    @Override
    public void onClick(View v) {


        switch (v.getId()) {

            case R.id.session1:

                drawable = 1;
                sectionName = "session1";

                break;


            case R.id.session2:

                drawable = 2;
                sectionName = "session2";

                break;

            case R.id.session3:

                drawable = 3;
                sectionName = "session3";

                break;

            case R.id.session4:

                drawable = 4;
                sectionName = "session4";

                break;

            case R.id.session5:

                drawable = 5;
                sectionName = "session5";


                break;

            case R.id.session6:

                drawable = 6;
                sectionName = "session6";


                break;

            case R.id.session7:

                drawable = 7;
                sectionName = "session7";
                break;


        }

        Log.i(TAG, "Selected drawable is " + drawable);
        Log.i(TAG, "Selected session is " + sectionName);


        Intent intent = new Intent(MainActivity.this, SubSectionActivity.class);
        intent.putExtra("ID", drawable);
        intent.putExtra("sectionName", sectionName);
        Log.i(TAG, "Starting SubSection Activity!");

        startActivity(intent);

    }


    @TargetApi(Build.VERSION_CODES.M)
    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {//Can add more as per requirement

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                    123);

        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case 123: {


                if (grantResults[0] == PackageManager.PERMISSION_DENIED) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AppDialog);
                    builder.setTitle("Permissions");
                    builder.setCancelable(false);
                    builder.setMessage("Please provide storage permissions");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            checkPermission();
                        }
                    });
                    builder.setNegativeButton("EXIT", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            supportFinishAfterTransition();
                        }
                    });
                    builder.show();



                }else{


                    createNoMediaFile();


                }
            }
        }


    }

    private void createNoMediaFile() {

        try{
            FileOutputStream out;


            File ROOT = new File("FFH" + File.separator);

            if(!ROOT.exists())
                ROOT.mkdirs();


            File file = new File("FFH" + File.separator, ".nomedia");
            if (!file.exists()) {
                out = new FileOutputStream(file);
                out.write(0);
                out.close();


                Log.i(TAG, "No media file created!  " + file);
            } else {
                Log.i(TAG, "No media already exists!!!!!!  " + file);

            }


        }catch(Exception e){e.printStackTrace();}
 }
}
