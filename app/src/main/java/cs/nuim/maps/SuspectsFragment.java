package cs.nuim.maps;

import android.app.ActionBar;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Chloë on 18/11/2016.
 */


public class SuspectsFragment extends Fragment {

    private List<String> suspects = Arrays.asList("Dr. Mustard", "Janitor Green", "Dean Plum", "Scarlet", "Nurse White", "Dr. Peacock");
    private TableLayout table;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // The last two arguments ensure LayoutParams are inflated
        // properly.

        View rootView = inflater.inflate(
                R.layout.fragment_layout_suspect, container, false);

        TextView tv = (TextView) rootView.findViewById(R.id.suspects);
        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "SEVESBRG.TTF");
        Typeface font2 = Typeface.createFromAsset(getActivity().getAssets(), "FiddumsFamily.ttf");

        tv.setTypeface(font);

        table = (TableLayout) rootView.findViewById(R.id.table);

        ClueDb clueDb = new ClueDb(getActivity());
        clueDb.open();

        for(String suspect: suspects) {
            TableRow row = new TableRow(getContext());
            TextView name = new TextView(getContext());
            name.setText(suspect);
            name.setTypeface(font2);
            name.setTextSize(30);
            row.setPadding(40,10,10,10);
            row.addView(name);

            TextView seen = new TextView(getContext());
            if(clueDb.checkClueFound(suspect)==1) {
                seen.setText("X");
                seen.setTextColor(Color.RED);
            } else {
                seen.setText("?");
                seen.setTextColor(Color.DKGRAY);
            }

            seen.setTypeface(font);
            seen.setTextSize(30);
            row.addView(seen);
            table.addView(row);
        }

        clueDb.close();
        return rootView;
    }
}