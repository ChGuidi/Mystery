package cs.nuim.maps;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class StoryActivity extends AppCompatActivity {

    TextView story;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);

        TextView title = (TextView) findViewById(R.id.titleStory);
        Typeface font = Typeface.createFromAsset(getAssets(), "Friday13.ttf");
        title.setTypeface(font);

        story =(TextView) findViewById(R.id.textStory);
        Typeface font3 = Typeface.createFromAsset(getAssets(), "LikeCockatoosBold.ttf");
        story.setTypeface(font3);
        story.setMovementMethod(new ScrollingMovementMethod());

        Button play = (Button) findViewById(R.id.buttonPlay);
        Typeface font2 = Typeface.createFromAsset(getAssets(), "GingerbreadHouse.ttf");
        play.setTypeface(font2);


    }

    public void toGame(View view) {
        Intent intent = new Intent(this,MapsActivity.class);
        startActivity(intent);

    }
}
