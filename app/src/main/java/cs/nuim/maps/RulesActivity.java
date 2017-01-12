package cs.nuim.maps;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class RulesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules);

    TextView rules; //Title Text which displays "Rules"
        TextView title = (TextView) findViewById(R.id.titleRules);
        Typeface font = Typeface.createFromAsset(getAssets(), "Friday13.ttf");
        title.setTypeface(font);

        title =(TextView) findViewById(R.id.textRules); //the text for the rules themselves
        Typeface font3 = Typeface.createFromAsset(getAssets(), "LikeCockatoosBold.ttf");
        title.setTypeface(font3);
        title.setMovementMethod(new ScrollingMovementMethod());

        Button play = (Button) findViewById(R.id.buttonPlayRules); //the button which returns to game
        Typeface font2 = Typeface.createFromAsset(getAssets(), "GingerbreadHouse.ttf");
        play.setTypeface(font2);

        // Enable location and check permissions
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);


    }

    public void toGame(View view) { //on button click, returns to map screen
        Intent intent = new Intent(this,MapsActivity.class);
        startActivity(intent);

    }
}
