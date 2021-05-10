package com.batproject;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Environment.getExternalStorageDirectory()
        final File audioFile = new File(getApplicationContext().getFilesDir(), "bat-proj-audio.3gp");
        final MediaRecorder recorder = new MediaRecorder();
        final MediaPlayer mediaPlayer = new MediaPlayer();
        final Button recordButton = findViewById(R.id.recordBtn);
        final Button playButton = findViewById(R.id.playBtn);

        recordButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.RECORD_AUDIO}, REQUEST_RECORD_AUDIO_PERMISSION);
                    return;
                }

                /// Initialize Media Recorder.
                if (recordButton.getText().toString().equals("Record")) {
                    recordButton.setText("Stop");

                    // TODO check difference between AudioSource.UNPROCESSED and AudioSource.MIC
                    recorder.setAudioSource(MediaRecorder.AudioSource.UNPROCESSED);
                    recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                    recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                    recorder.setAudioEncodingBitRate(16);
                    recorder.setAudioSamplingRate(44100);
                    recorder.setOutputFile(audioFile.getAbsolutePath());
                    try {
                        recorder.prepare();
                        /// Start Recording
                        recorder.start();
                    } catch (Exception e) {
                        Log.e("SHARA", "recorder start: " + e.getMessage());
                        e.printStackTrace();
                    }


                } else {
                    try {
                        recorder.stop();
                        recordButton.setText("Record");
                    }
                    catch (Exception ex) {
                        Log.e("SHARA", "recorder stop: " + ex.getMessage());
                    }
                }
            }

        });


        playButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(playButton.getText().toString().equals("Play")) {
                    playButton.setText("Stop play");
                    mediaPlayer.stop();
                    mediaPlayer.reset();

                    //File audioFile = new File(getApplicationContext().getFilesDir(), "");
                    if(!audioFile.exists()) {
                        Log.e("SHARA","audio file does not exist: " + audioFile.getAbsolutePath());
                        return;
                    }
                    try {
                        mediaPlayer.setDataSource(audioFile.getAbsolutePath());
                        mediaPlayer.prepare();
                        mediaPlayer.start();
                    }
                    catch (Exception ex) {
                        Log.e("SHARA", "Cannot load audio file: " + ex.getMessage());
                    }
                }
                else{
                    playButton.setText("Play");
                    mediaPlayer.stop();
                }

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissionts[], int[] grantResults){
        switch(requestCode){
            case REQUEST_RECORD_AUDIO_PERMISSION:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted.
                }
                else{
                    // permission denied.
                    // TODO disable the functionality to record audio.

                }
                break;

        }
    }
}
