package cs.nuim.maps;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

public class CloseActivity extends AppCompatActivity {
    Button returnButton;
    String clueName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_close);
        Intent current = getIntent();
        final Float zoom = current.getFloatExtra("zoom",14.7f);
        clueName = current.getStringExtra("clue");
        System.out.println(clueName);
        textSetup();
        returnButton = (Button) findViewById(R.id.returnButton);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MapsActivity.class);
                intent.putExtra("zoom",zoom);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }
    void drawImg(){//draws the picture of clue found
        ImageView clueView = (ImageView) findViewById(R.id.clueFound);
        Resources res = getResources();
        //find 'drawable friendly' clueName, ie Dr. Mustard becomes drmustard
        String resName=clueName.replaceAll("[^a-zA-Z]","");
        resName=resName.toLowerCase();
        //set image to correct image
        int itmID = res.getIdentifier(resName, "drawable", getPackageName());
        clueView.setImageResource(itmID);
    }

    void textSetup(){
        TextView captions = (TextView) findViewById(R.id.foundText);
        List<String> suspects = Arrays.asList(getResources().getStringArray(R.array.suspects_array));
        List<String> weapons = Arrays.asList(getResources().getStringArray(R.array.weapons_array));

        //create appropriate caption for clue found
        if(suspects.contains(clueName)){
            captions.setText("You have eliminated \n"+clueName+" as a suspect");
            drawImg();
        } else if (weapons.contains(clueName)) {
            captions.setText("A "+clueName+ " has been added\nto your inventory");
            drawImg();
        } else if (clueName.equals("CRIME SCENE")){
            String filename = "GamePreferences";
            SharedPreferences prefs = getSharedPreferences(filename,Context.MODE_PRIVATE);
            //display name of crime scene and red x instead of clue picture
            String crimeScene = prefs.getString("Crime Scene", "PROBLEM");
            captions.setText("You found the crime scene at "+crimeScene);
            ImageView clueView = (ImageView) findViewById(R.id.clueFound);
            clueView.setImageResource(R.drawable.redx);
        }
    }
}
