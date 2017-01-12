package cs.nuim.maps;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class VictoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_victory);
        Button newGame = (Button)findViewById(R.id.newgamebutton);
        Button exit = (Button)findViewById(R.id.exitbuttonend);

        TextView congrats = (TextView) findViewById(R.id.congratulations);
        Typeface font = Typeface.createFromAsset(getAssets(), "Casper.ttf");
        congrats.setTypeface(font);
    }
    public void goToMap(View view){ //button returns to map without starting new game
        Intent mapIntent = new Intent(this, MapsActivity.class);
        startActivity(mapIntent);
    }
    public void startNewGame(View view){
        String filename = "GamePreferences";
        SharedPreferences prefs;
        prefs = getSharedPreferences(filename, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear(); //clear shared preferences so weapon/suspect/crime scene are rechosen
        editor.apply();

        this.deleteDatabase("Clues_DB"); //delete database to clear inventory/ 'visited locations'
        Intent toStart = new Intent(this, SplashActivity.class);
        startActivity(toStart);
    }
}
