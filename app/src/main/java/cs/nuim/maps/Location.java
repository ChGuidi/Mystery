package cs.nuim.maps;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Chloë on 26/11/2016.
 */

public class Location {

    private String name;
    private LatLng latLng;

    public Location(String name, LatLng latLng){
        this.latLng = latLng;
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }
}
