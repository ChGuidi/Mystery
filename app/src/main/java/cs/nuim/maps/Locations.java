package cs.nuim.maps;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Chloë on 18/11/2016.
 */

public class Locations extends HashMap<String,LatLng>{

    public Locations() {
        this.put("HUMANITY HOUSE", new LatLng(53.379333, -6.596350));
        this.put("LOGIC HOUSE", new LatLng(53.378128, -6.596220));
        this.put("LIBRARY", new LatLng(53.381181, -6.599524));
        this.put("SCIENCE BUILDING", new LatLng(53.383178, -6.600479));
        this.put("JOHN HUME", new LatLng(53.383984, -6.600361));
        this.put("ARTS BUILDING", new LatLng(53.383613, -6.601960));
        this.put("CALLAN BUILDING", new LatLng(53.382557, -6.602334));
        this.put("STUDENTS UNION", new LatLng(53.383066, -6.603628));
        this.put("EOLAS", new LatLng(53.384532, -6.601702));
        this.put("PHOENIX", new LatLng(53.384289, -6.603676));
        this.put("AULA MAXIMA", new LatLng(53.380219, -6.597786));
    }
}
