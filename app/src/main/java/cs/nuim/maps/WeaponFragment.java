package cs.nuim.maps;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
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

    private List<String> weapons = Arrays.asList("butcherknife");
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
        grid.setAdapter(new ImageAdapter(getActivity()));

        return rootView;
    }
}
