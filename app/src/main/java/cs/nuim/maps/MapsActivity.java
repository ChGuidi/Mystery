package cs.nuim.maps;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.maps.android.SphericalUtil;

import java.util.HashMap;
import java.util.Map;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private TextView tv;
    private static final Locations locations = new Locations();
    private HashMap<String, LatLng> notVisited;
    private static final float MINZOOM = 14.7f;
    private Map<String, Circle> circles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Change fonts
        TextView looking = (TextView) findViewById(R.id.looking);
        Typeface font = Typeface.createFromAsset(getAssets(), "youmurdererbb_reg.ttf");
        looking.setTypeface(font);

        Button clues = (Button) findViewById(R.id.clues);
        Button rules = (Button) findViewById(R.id.rules);
        Typeface font2 = Typeface.createFromAsset(getAssets(), "GingerbreadHouse.ttf");

        clues.setTypeface(font2);
        rules.setTypeface(font2);

        circles = new HashMap<>();
        tv = (TextView) findViewById(R.id.textView);

        ClueDb database = new ClueDb(this);
        database.open();

        // Save map with not yet visited locations out of database
        notVisited = new HashMap<>();
        for(String location : locations.keySet()) {
            if(database.checkPlaceVisited(location)==0) {
                notVisited.put(location, locations.get(location));
            }
        }

        database.close();


    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Show location on map
        if (checkLocationPermission()) {
            mMap.setMyLocationEnabled(true);
        }

        // Get zoom possibility
        mMap.getUiSettings().setZoomControlsEnabled(true);


        // Make sure that it's not possible to zoom out too much
        mMap.setMinZoomPreference(MINZOOM);
        // Set boundaries such that only the campus is visible
        LatLngBounds CAMPUS = new LatLngBounds(
                new LatLng(53.37720507237538, -6.605383873247774), new LatLng(53.38583170211977, -6.594912529253634));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(CAMPUS.getCenter(), MINZOOM));
        mMap.setLatLngBoundsForCameraTarget(CAMPUS);

        // Set all the locations with clues on the map
        putLocations();

        // Check with every movement if you're not close enough to a target location
        checkNearby();
    }

    public void checkNearby() {
        LocationListener locationListener = new LocationListener() {
            @Override
            // List with locations you still have to check, if you're close enough remove location from list
            public void onLocationChanged(Location location) {
                LatLng currentLoc = new LatLng(location.getLatitude(), location.getLongitude());
                if(notVisited.size()==0){ //if all locations have been visited
                    SharedPreferences prefs=getApplicationContext().getSharedPreferences("GamePreferences",0);
                    SharedPreferences.Editor editor = prefs.edit();
                    String crimeScene = prefs.getString("Crime Scene", "PROBLEM");
                    //if within range of crime scene
                    if (SphericalUtil.computeDistanceBetween(currentLoc, locations.get(crimeScene)) < 20){
                        //bring to guessing screen
                        Intent endIntent = new Intent(getBaseContext(), EndActivity.class);
                        startActivity(endIntent);
                    }
                }
                for(String cite : notVisited.keySet()) {
                    if (SphericalUtil.computeDistanceBetween(currentLoc, notVisited.get(cite)) < 20) {
                        Intent intent = new Intent(getApplicationContext(), CloseActivity.class);
                        intent.putExtra("zoom", mMap.getCameraPosition().zoom + 1f);

                        ClueDb entry = new ClueDb(MapsActivity.this);
                        entry.open();
                        String clueFound = entry.getClue(cite);
                        intent.putExtra("clue", clueFound);
                        startActivityForResult(intent, 1);
                        circles.get(cite).setFillColor(Color.GREEN);
                        circles.get(cite).setStrokeColor(Color.GREEN);
                        notVisited.remove(cite);

                        // Change visited in database
                        entry.markVisited(cite);
                        entry.close();

                        break;
                    }
                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        // control the changing of the location and react on it
        LocationManager mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (checkLocationPermission()) {
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,
                    1, locationListener);
            mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0,
                    1, locationListener);
        }

    }

    // Add locations to the map as circles
    public void putLocations() {
        // Instantiates a new CircleOptions object and defines the center and radius
        for(String s : locations.keySet()) {
            CircleOptions circleOptions;
            if(notVisited.containsKey(s)) {
                 circleOptions = new CircleOptions()
                        .center(locations.get(s))
                        .radius(3)
                        .fillColor(Color.RED)
                        .strokeColor(Color.RED)
                        .clickable(true);
            } else {
                circleOptions = new CircleOptions()
                        .center(locations.get(s))
                        .radius(3)
                        .fillColor(Color.GREEN)
                        .strokeColor(Color.GREEN)
                        .clickable(true);
            }

            circles.put(s,mMap.addCircle(circleOptions));
            mMap.setOnCircleClickListener(new GoogleMap.OnCircleClickListener() {
                @Override
                public void onCircleClick(Circle circle) {
                    LatLng id = circle.getCenter();
                    for(String loc : locations.keySet()){
                        LatLng current = locations.get(loc);
                        if (id.latitude == current.latitude && id.longitude == current.longitude) {
                            tv.setText(loc);
                            break;
                        }
                    }

                }
            });
        }
    }

    public boolean checkLocationPermission() {
        String permission = "android.permission.ACCESS_FINE_LOCATION";
        int res = this.checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }


    // Keep zoom at the same value as it was before
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode == 1) {
            if(resultCode == RESULT_OK) {
                Float zoom = data.getFloatExtra("zoom",MINZOOM);
                mMap.animateCamera(CameraUpdateFactory.zoomTo(zoom));
               // mMap.animateCamera(CameraUpdateFactory.newLatLng());
            }
        }
    }

    public void goToClues(View view) {
        Intent intent = new Intent(this, CluesActivity.class);
        startActivity(intent);
    }

    public void goToRules(View view) {
        Intent intent = new Intent(this, RulesActivity.class);
        startActivity(intent);
    }

}
