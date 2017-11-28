package com.unglibaaz.ungli;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.VideoView;

import SessionManagement.SessionManager;

/**
 * Created by abhishek.maurya on 11/21/2017.
 */

public class Splash extends AppCompatActivity implements MediaPlayer.OnCompletionListener {

    SessionManager session;
    private VideoView mVideo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        this.session = new SessionManager(getApplicationContext());
        mVideo = (VideoView) findViewById(R.id.videoView1);
        try {
            mVideo.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.intro));
            mVideo.requestFocus();
            mVideo.setOnCompletionListener(this);
            mVideo.start();
        } catch (Exception e) {
            mVideo.setVisibility(View.GONE);
            findViewById(R.id.splash_layout).setBackgroundResource(R.drawable.splash);
        }
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        jumpMain();
    }

    private synchronized void jumpMain() {
        if (session.isLoggedIn()) {
            startActivity(new Intent(this, MainActivity.class));
            //startActivity(new Intent(this, LoginActivity.class));

            finish();
        } else {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
    }

    protected void onPause() {
        super.onPause();
        this.mVideo.pause();
    }

    protected void onResume() {
        super.onResume();
        this.mVideo.start();
    }
}
