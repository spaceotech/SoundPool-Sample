package com.music.soundpool;

import android.annotation.TargetApi;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private static final String TAG="SoundPool";

    private SoundPool mSoundPool=null;

    private static final int MAX_STREAM = 10;

    private static final int SRC_QULITY = 0;

    private static final int PRIORITY=1;

    int firstMusicFile=0;

    int secondMusicFile=0;


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button btnPlayMusic1=(Button)findViewById(R.id.btnPlayMusic1);
        btnPlayMusic1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // play first sound
            /**
             *
             * soundID - a soundID returned by the load() function
             * leftVolume - left volume value (range = 0.0 to 1.0)
             * rightVolume - right volume value (range = 0.0 to 1.0)
             * priority - stream priority (0 = lowest priority)
             * loop - loop mode (0 = no loop, -1 = loop forever)
             * rate - playback rate (1.0 = normal playback, range 0.5 to 2.0)
             */
               //mSoundPool.play(firstMusicFile, leftVolume, rightVolume, priority, loop, rate);

                mSoundPool.play(firstMusicFile, 1f, 1f, 1, 0, 1f);


            }
        });

        Button btnPlayMusic2=(Button)findViewById(R.id.btnPlayMusic2);
        btnPlayMusic2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // play second sound

                mSoundPool.play(secondMusicFile, 1f, 1f, 1, 0, 1f);


            }
        });


        createSoundPool();

        mSoundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int soundID, int status) {

                // can check status of sound is is load or not with sound ID
                // Sound ID is start with 1 to MAX_STREAM, Its work like FIFO.
                Log.e(TAG, "soundID"+soundID+"    status:"+status);


            }
        });


        // it should be sound ID =1
        firstMusicFile = mSoundPool.load(this, R.raw.music_play_1, PRIORITY);
        // it should be sound ID =2
        secondMusicFile = mSoundPool.load(this, R.raw.music_play_2, PRIORITY);




    }


    protected void createSoundPool() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            createNewSoundPool();
        } else {
            createOldSoundPool();
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    protected void createNewSoundPool() {
        AudioAttributes attributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        mSoundPool = new SoundPool.Builder()
                .setAudioAttributes(attributes)
                .build();
    }

    @SuppressWarnings("deprecation")
    protected void createOldSoundPool() {
        mSoundPool = new SoundPool(MAX_STREAM, AudioManager.STREAM_MUSIC,SRC_QULITY);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mSoundPool != null) {
            mSoundPool.release();
        }

    }
}
