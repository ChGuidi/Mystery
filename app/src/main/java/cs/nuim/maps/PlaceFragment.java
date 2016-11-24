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

        tv.setTypeface(font);
        return rootView;
    }
}
