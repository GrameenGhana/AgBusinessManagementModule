package org.grameen.agbusinessmanagementmodule;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.IOException;

/**
 * Created by aangjnr on 28/03/2017.
 */

public class SubSectionActivity extends BaseActivity implements View.OnClickListener{

    String TAG = SubSectionActivity.class.getSimpleName();

    private MediaPlayer mPlayer;
    private Handler mHandler;
    private static final int MSG_PAUSE = 100;
    private ProgressBar mProgress;


    int imageId;
    String stepFileName;
    String sectionName;

    public void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subsection);

        try {
            imageId = getIntent().getIntExtra("ID", R.drawable.step1);
            sectionName = getIntent().getStringExtra("sectionName");

        }catch(Exception e){

            e.printStackTrace();

        }
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        mProgress = (ProgressBar) findViewById(R.id.progressBar);
        ImageView header = (ImageView)findViewById(R.id.header);
        setHeaderImage(imageId, header);

        mPlayer = new MediaPlayer();

        AssetFileDescriptor  audioPath =  Module.descriptor(this, sectionName + "/" +  "title.mp3");

        Log.i(TAG, "Audio file is in path " + audioPath);

        if(audioPath != null && !audioPath.equals("null"))
        AsyncTask.execute( new PlayAudio(audioPath));

        findViewById(R.id.button_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                supportFinishAfterTransition();
            }
        });



        mHandler = new Handler(new Handler.Callback() {
            // Handle the "pause" message
            @Override
            public boolean handleMessage(Message message) {
                if (message.what != MSG_PAUSE) {
                    return false;
                }
                if (mPlayer != null) {
                    try {
                        if (mPlayer.isPlaying()) {
                            Log.d(TAG, "Pausing because step over : " + message.arg1);
                            mPlayer.pause();
                        }
                    } catch (IllegalStateException e) {
                        Log.w(TAG, "Player error while pausing", e);
                    }
                }
                return true;
            }
        });









        findViewById(R.id.step1).setOnClickListener(this);
        findViewById(R.id.step2).setOnClickListener(this);
        findViewById(R.id.step3).setOnClickListener(this);
        findViewById(R.id.step4).setOnClickListener(this);
        findViewById(R.id.step5).setOnClickListener(this);






    }





    @Override
    public void onClick(View v) {


       /* if(v.getId() == R.id.step2){

            stepFileName = "video";


            String video_name = stepFileName + sectionName.substring(sectionName.length() -1 , sectionName.length());

            Log.i(TAG, "The video file  name is " + video_name);

            Uri videoURL = Module.getFile(this, video_name);

            if(videoURL != null) {

                startPlayVideoActivity(sectionName, String.valueOf(videoURL));

            }else{
                Toast.makeText(this, "Video was not found on Sdcard", Toast.LENGTH_SHORT).show();

            }



        }else
*/
        switch(v.getId()) {
            case R.id.step1:
                stepFileName = "step1";


                break;


            case R.id.step2:
                stepFileName = "step2";


                break;

             case R.id.step3:
                 stepFileName = "step3";


                 break;

             case R.id.step4:
                 stepFileName = "step4";


                 break;


            case R.id.step5:
                 stepFileName = "step5";


                break;


        }

        AssetFileDescriptor  audioPath =  Module.descriptor(this, sectionName + "/" + stepFileName + ".mp3");
        if(audioPath != null)
        AsyncTask.execute( new PlayAudio(audioPath));


    }










    private void startPlayVideoActivity(String sectionName, String videoFileName) {

        Intent videoPlaybackActivity = new Intent(this, ViewVideoActivity.class);
        videoPlaybackActivity.putExtra("sectionName", sectionName);
        videoPlaybackActivity.putExtra("videoUri", videoFileName);

        startActivity(videoPlaybackActivity);
    }











    class PlayAudio implements Runnable {

        String TAG = PlayAudio.class.getSimpleName();
        AssetFileDescriptor mPath;



        public PlayAudio(AssetFileDescriptor audioPath ) {
            this.mPath = audioPath;


            Log.i(TAG, "path is " + mPath);
        }

        @Override
        public void run() {
            if (mPlayer == null) {
                throw new IllegalStateException("Player not initialized");
            }


            // Pause any ongoing track
            try {
                if (mPlayer.isPlaying()) {
                    mPlayer.pause();
                }
            } catch (Exception e) {
                Log.w(TAG, "Unable to release player", e);
            }

            Log.d(TAG, "Playing file " + mPath);

            // If MediaPlayer already used before and in the wrong state
            mPlayer.reset();

            mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

            try {
                Log.d(TAG,"Playing audio: " + mPath);

                mPlayer.setDataSource(mPath.getFileDescriptor(), mPath.getStartOffset(), mPath.getLength());

            } catch (IOException e) {
                Log.w(TAG, "Unable to open player", e);
            }
            mPlayer.prepareAsync();

            mPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                    Log.w(TAG, "Resetting. Got Media Player error code " + i + ", " + i1);
                    try {
                        mediaPlayer.reset();
                    } catch (Exception e) {
                        Log.w(TAG, "Unable to reset", e);
                    }
                    stopProgress();
                    return false;
                }
            });


            mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(final MediaPlayer mediaPlayer) {
                    Log.d(TAG, "Player prepared ");

                    try {
                        if (mediaPlayer.isPlaying()) {
                            Log.d(TAG, "Interupting current playback");
                            mediaPlayer.pause();
                        }
                    } catch (IllegalStateException e) {
                        Log.w(TAG, "Player error", e);
                    }

                    try {
                        // Eliminate any pending pause requests
                        mHandler.removeMessages(MSG_PAUSE);

                        // Start progress bar
                        if (mProgress != null) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mProgress.setVisibility(View.VISIBLE);
                                }
                            });
                        }

                        // Start playing
                        mediaPlayer.start();


                    } catch (IllegalStateException e) {
                        Log.w(TAG, "Player error", e);
                    }
                }
            });
            mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    stopProgress();

                }
            });
            // Init handler that will pause playback
        }

        private void stopProgress() {
            // Start progress bar
            if (mProgress != null) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mProgress.setVisibility(View.INVISIBLE);
                    }
                });
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopProgress();
        if (mPlayer != null) {
            try {
                if (mPlayer.isPlaying()) {
                    mPlayer.pause();
                }
            } catch (Exception e) {
                Log.w(TAG, "Unable to release player", e);
            }
        }
    }


    private void stopProgress() {
        // Start progress bar
        if (mProgress != null) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mProgress.setVisibility(View.INVISIBLE);
                }
            });
        }
    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPlayer != null) {
            mPlayer.release();
        }
    }




    void setHeaderImage(int id, ImageView header){

        if(id != 0){

            if(id == 1) header.setImageResource(R.drawable.session1);
            else if(id == 2) header.setImageResource(R.drawable.session2);
            else if(id == 3) header.setImageResource(R.drawable.session3);
            else if(id == 4) header.setImageResource(R.drawable.session4);
            else if(id == 5) header.setImageResource(R.drawable.session5);
            else if(id == 6) header.setImageResource(R.drawable.session6);
            else if(id == 7) header.setImageResource(R.drawable.session7);

        }
    }
}
