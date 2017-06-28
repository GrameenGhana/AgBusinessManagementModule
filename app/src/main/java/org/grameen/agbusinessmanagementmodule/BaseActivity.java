package org.grameen.agbusinessmanagementmodule;

import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by aangjnr on 23/04/2017.
 */

public class BaseActivity extends AppCompatActivity {

    String TAG = BaseActivity.class.getSimpleName();







    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.menu_main, menu);

       /* if (PreferenceManager.getDefaultSharedPreferences(BaseActivity.this).getBoolean("sdcard", false)) {
            Log.i(TAG, "Load from sd card is true");
            menu.findItem(R.id.sdcard).setChecked(true);
        } else {
            Log.i(TAG, "Load from sd card is false");

            menu.findItem(R.id.sdcard).setChecked(false);
        }*/
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.sdcard:

                if (item.isChecked()) {

                    PreferenceManager.getDefaultSharedPreferences(BaseActivity.this).edit().putBoolean("sdcard", false).apply();
                    item.setChecked(false);

                } else {
                    PreferenceManager.getDefaultSharedPreferences(BaseActivity.this).edit().putBoolean("sdcard", true).apply();
                    item.setChecked(true);

                }
                break;

        }
        return super.onOptionsItemSelected(item);
    }





}
