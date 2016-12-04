package cs.nuim.maps;

import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class PlaceFragment extends Fragment {

    private String placeName = "New House";
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // The last two arguments ensure LayoutParams are inflated
        // properly.
        View rootView = inflater.inflate(
                R.layout.fragment_layout_place, container, false);
        Bundle args = getArguments();

        TextView tv = (TextView) rootView.findViewById(R.id.crimescene);
        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "SEVESBRG.TTF");

        TextView place = (TextView) rootView.findViewById(R.id.crimesign);
        Typeface font2 = Typeface.createFromAsset(getActivity().getAssets(), "FiddumsFamily.ttf");
        place.setTypeface(font2);

        ClueDb clueDb = new ClueDb(getActivity());
        clueDb.open();
        if (clueDb.checkClueFound(placeName) == 1) {
            place.setText(placeName);
            place.setTextSize(50);
        }
        clueDb.close();
        tv.setTypeface(font);
        return rootView;
    }
}
