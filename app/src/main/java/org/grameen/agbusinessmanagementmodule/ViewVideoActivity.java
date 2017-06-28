package org.grameen.agbusinessmanagementmodule;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.VideoView;

/**
 * Created by aangjnr on 28/03/2017.
 */

public class ViewVideoActivity extends BaseActivity implements MediaPlayer.OnCompletionListener,
        MediaPlayer.OnPreparedListener {

    private static final String TAG = "ViewVideoAct";
    private VideoView mVideoView;

    String videoFileName;
    String sectionName;

    @Override
    public void onCreate(Bundle b) {
        super.onCreate(b);
        Window w = getWindow();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
             // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {

            w.getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                            | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
            );
        }else{

            w.getDecorView().setSystemUiVisibility(
                             View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
             );




        }


        videoFileName = getIntent().getStringExtra("videoUri");
        sectionName = getIntent().getStringExtra("sectionName");


        setContentView(R.layout.activity_video);

        // Init buttons
        findViewById(R.id.button_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                supportFinishAfterTransition();
            }
        });






        mVideoView = (VideoView)findViewById(R.id.video);
        mVideoView.setOnCompletionListener(this);
        mVideoView.setOnPreparedListener(this);
        MediaController mediaController = new
                MediaController(this);
        mediaController.setAnchorView(mVideoView);
        mVideoView.setMediaController(mediaController);

        playVideo();


    }

    private void playVideo() {
        if (videoFileName == null) {
            Log.w(TAG, "No video URI specified");
            finish();
            return;
        }


            mVideoView.setVideoURI(Uri.parse(videoFileName));
            mVideoView.start();

    }

    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        playVideo();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mVideoView != null && mVideoView.isPlaying()) {
            mVideoView.stopPlayback();
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        finish();
    }


    @Override
    public void onPrepared(MediaPlayer mp) {
//        mp.setLooping(true);
    }


}
