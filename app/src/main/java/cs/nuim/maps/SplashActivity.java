package cs.nuim.maps;

import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class SplashActivity extends AppCompatActivity {

    private MediaPlayer my_mediaPlayer;

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

        TextView tv = (TextView) findViewById(R.id.gametitle);
        Typeface font = Typeface.createFromAsset(getAssets(), "GypsyCurse.ttf");
        tv.setTypeface(font);

        my_mediaPlayer = MediaPlayer.create(this, R.raw.susanvega);
        my_mediaPlayer.start();

        Thread timer = new Thread(){
            public void run(){
                try {
                    sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    // TODO: change which screen is opened depending of the game is played before or not
                    Intent openMain = new Intent(SplashActivity.this, StoryActivity.class);
                    startActivity(openMain);
                }
            }
        };
        timer.start();
    }
}