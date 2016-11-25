package cs.nuim.maps;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {

    private String filename = "GamePreferences";
    private MediaPlayer my_mediaPlayer;
    SharedPreferences prefs;


    @Override
    protected void onPause() {
        super.onPause();
        my_mediaPlayer.release();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        prefs = getSharedPreferences(filename, Context.MODE_PRIVATE);

        TextView tv = (TextView) findViewById(R.id.gametitle);
        Typeface font = Typeface.createFromAsset(getAssets(), "GypsyCurse.ttf");
        tv.setTypeface(font);

        my_mediaPlayer = MediaPlayer.create(this, R.raw.squeakingdoor);
        my_mediaPlayer.start();

        Thread timer = new Thread(){
            public void run(){
                try {
                    sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    if(prefs.getBoolean("played", false)) {
                        Intent openMain = new Intent(SplashActivity.this, MapsActivity.class);

                        // puts all info in database
                        clueDb entry = new clueDb(SplashActivity.this);
                        entry.setData();
                        startActivity(openMain);
                    } else {
                        Intent openMain = new Intent(SplashActivity.this, StoryActivity.class);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putBoolean("played",true);
                        editor.apply();

                        startActivity(openMain);
                    }
                }
            }
        };
        timer.start();
    }
}