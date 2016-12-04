package cs.nuim.maps;

import android.content.Context;
import android.content.Intent;
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
        drawImg();
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
    void drawImg(){
        ImageView clueView = (ImageView) findViewById(R.id.clueFound);
        Resources res = getResources();
        String resName=clueName.replaceAll("[^a-zA-Z]","");
        resName=resName.toLowerCase();
        int itmID = res.getIdentifier(resName, "drawable", getPackageName());
        clueView.setImageResource(itmID);
    }

    void textSetup(){
        TextView captions = (TextView) findViewById(R.id.foundText);
        String Suspects[] = { "Dr. Mustard", "Janitor Green", "Dean Plum",
                "Scarlet", "Nurse White", "Dr. Peacock" };
        String Weapons[] = { "butcherknife", "arrow", "leadpipe", "chandelier", "revolver", "rope" };

        if(Arrays.asList(Suspects).contains(clueName)){
            captions.setText("You have eliminated \n"+clueName+" as a suspect");
        } else if (Arrays.asList(Weapons).contains(clueName)) {
            captions.setText("A "+clueName+ " has been added\nto your inventory");
        } else {
            captions.setText("You found the crime scene at "+clueName);
        }
    }
}
