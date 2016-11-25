package cs.nuim.maps;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Main2Activity extends AppCompatActivity {
    private int knife = R.drawable.butcherknife;
    private int redx = R.drawable.redx;

    public final static String itemName = "itemName";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        clueDb entry = new clueDb(this);
        entry.setData();
        entry.open();
        String testclue = entry.getClue("HUMANITY HOUSE");
        entry.close();
        System.out.println(testclue);

/*        Intent toFdItm = new Intent(this, FoundItem.class);
        toFdItm.putExtra(itemName, knife);
        startActivity(toFdItm);*/
    }
}
