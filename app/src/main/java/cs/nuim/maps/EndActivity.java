package cs.nuim.maps;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Arrays;

import static android.graphics.Color.BLACK;
import static java.sql.DriverManager.println;

public class EndActivity extends AppCompatActivity {
    private Spinner wepspin;
    private Spinner susspin;
    private Button submitEnd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);

        wepspin = (Spinner)findViewById(R.id.spinnerweapon);

        susspin = (Spinner)findViewById(R.id.spinnersuspect);

        submitEnd = (Button)findViewById(R.id.submitEnd);

        TextView title = (TextView) findViewById(R.id.titleMystery);
        Typeface font2 = Typeface.createFromAsset(getAssets(), "youmurdererbb_reg.ttf");
        title.setTypeface(font2);

        Typeface font = Typeface.createFromAsset(getAssets(), "GingerbreadHouse.ttf");

        TextView weapon = (TextView) findViewById(R.id.weapon);
        Typeface font3 =  Typeface.createFromAsset(getAssets(),"LikeCockatoosBold.ttf");
        TextView murderer = (TextView) findViewById(R.id.murderer);
        weapon.setTypeface(font);
        murderer.setTypeface(font);

        //Set text to reflect the crime scene/ guessing location
        String filename = "GamePreferences";
        SharedPreferences prefs = getSharedPreferences(filename,Context.MODE_PRIVATE);
        String crimeScene = prefs.getString("Crime Scene", "PROBLEM");
        TextView crimeText = (TextView)findViewById(R.id.crimeText);

        crimeText.setTypeface(font);
        crimeText.setText(crimeScene);
    }

    private String[] getCorrect(){//method to retrieve killer and murder weapon info
        String filename = "GamePreferences";
        SharedPreferences prefs = getSharedPreferences(filename,Context.MODE_PRIVATE);
        return new String[]{ prefs.getString("Killer", "PROBLEM"),
                prefs.getString("Murder Weapon", "PROBLEM") };
    }

    public void checkSubmit(View view){//checks if guess is correct or false
        String weaponChoice = wepspin.getSelectedItem().toString();
        String suspectChoice = susspin.getSelectedItem().toString();
        String[] answer = getCorrect();
        //if guess is correct, go to victory screen
        if (Arrays.equals(answer, new String[]{ suspectChoice, weaponChoice })){
            Intent finalIntent = new Intent(this, VictoryActivity.class);
            startActivity(finalIntent);
        }else{ //otherwise, tell user they are wrong via toast
            Context context = getApplicationContext();
            CharSequence text = "You guessed wrong. \n Try again.";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }

}
