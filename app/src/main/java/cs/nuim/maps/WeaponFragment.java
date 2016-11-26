package cs.nuim.maps;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

public class WeaponFragment extends Fragment {

    private List<String> weapons = Arrays.asList("butcherknife","arrow", "chandelier","leadpipe","revolver", "rope");
    private TableLayout table;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // The last two arguments ensure LayoutParams are inflated
        // properly.
        View rootView = inflater.inflate(
                R.layout.fragment_layout_weapon, container, false);
        Bundle args = getArguments();


        TextView tv = (TextView) rootView.findViewById(R.id.weapons);
        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "SEVESBRG.TTF");
        tv.setTypeface(font);

        GridView grid = (GridView) rootView.findViewById(R.id.gridview);
        ImageAdapter imageAdapter = new ImageAdapter(getActivity());

        ClueDb database = new ClueDb(getActivity());
        database.open();
        Integer[] imageIDs = new Integer[6];
        for (int i = 0 ; i< imageIDs.length; i++){
            String image;
            String clue = weapons.get(i);
            System.out.println(database.getClue("HUMANITY HOUSE"));
            if (database.checkClueFound(clue)==0) {
                image = "blurry" + clue;
            } else {
                image = clue;
            }

            Resources res = getResources();
            int resID = res.getIdentifier(image , "drawable", getActivity().getPackageName());
            imageIDs[i] = resID;
        }
        database.close();

        imageAdapter.setmThumbIds(imageIDs);
        grid.setAdapter(imageAdapter);

        return rootView;
    }
}
