package cs.nuim.maps;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by kacl2 on 11/19/2016.
 */

public class ClueDb {
	
	private static final String TABLE_NAME = "Clues_DB";
	private static final String COLUMN_NAME_PLACES = "Places";
	private static final String COLUMN_NAME_VISITED = "Visited";
	private static final String COLUMN_NAME_CLUE = "Clue";
    private static final String COLUMN_NAME_LAT = "Latitude";
    private static final String COLUMN_NAME_LON = "Longitude";
	private static final int DATABASE_VERSION = 1;
	
	private DbHelper ourHelper;
    private final Context ourContext;
    private SQLiteDatabase ourDatabase;

	private static class DbHelper extends SQLiteOpenHelper {

        private static DbHelper sInstance;

        private DbHelper(Context context) {
            super(context, TABLE_NAME, null, DATABASE_VERSION);
        }

        public static synchronized DbHelper getInstance(Context context) {

            // Use the application context, which will ensure that you
            // don't accidentally leak an Activity's context.
            // See this article for more information: http://bit.ly/6LRzfx
            if (sInstance == null) {
                sInstance = new DbHelper(context.getApplicationContext());
            }
            return sInstance;
        }

        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_NAME_PLACES + " TEXT NOT NULL, " +
                    COLUMN_NAME_VISITED + " INTEGER NOT NULL, " +
                    COLUMN_NAME_CLUE + " TEXT NOT NULL, " +
                    COLUMN_NAME_LAT + " REAL NOT NULL, " +
                    COLUMN_NAME_LON + " REAL NOT NULL);"
            );
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }

    public ClueDb(Context c){
        ourContext = c;
    }



    public ClueDb open() throws SQLException {
        ourHelper = DbHelper.getInstance(ourContext);
        ourDatabase = ourHelper.getWritableDatabase();  //will call onCreate() or onUpgrade() or onOpen() above
        return this;
    }

    public void close(){
        ourHelper.close();
    }

    public void createEntry(String place, String clue, double Latitude, double Longitude) {
        // TODO Auto-generated method stub
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME_PLACES, place);
        cv.put(COLUMN_NAME_CLUE, clue);
		cv.put(COLUMN_NAME_VISITED, 0);
        cv.put(COLUMN_NAME_LAT, Latitude);
        cv.put(COLUMN_NAME_LON, Longitude);
        ourDatabase.insert(TABLE_NAME, null, cv);
    }

    public int checkPlaceVisited(String place) throws SQLException{
        // TODO Auto-generated method stub
        String[] columns = new String[]{ COLUMN_NAME_VISITED};
        Cursor c = ourDatabase.query(TABLE_NAME, columns, COLUMN_NAME_PLACES + "='" + place + "'", null, null, null, null);
        if (c != null){
            c.moveToFirst();
            int isVisited = c.getInt(0);
            return isVisited;
        }
        return -1;
    }
	
	public int checkClueFound(String clue) throws SQLException{
        // TODO Auto-generated method stub
        String[] columns = new String[]{ COLUMN_NAME_VISITED};
        Cursor c = ourDatabase.query(TABLE_NAME, columns, COLUMN_NAME_CLUE + "='" + clue + "'", null, null, null, null);
        if (c != null){
            c.moveToFirst();
            int isVisited = c.getInt(0);
            return isVisited;
        }
        return -1;
    }

    public String getClue(String place) throws SQLException{
        String[] columns = new String[]{ COLUMN_NAME_CLUE };
        Cursor c = ourDatabase.query(TABLE_NAME, columns, COLUMN_NAME_PLACES + "= '" + place + "'", null, null, null, null);
        if (c != null){
            c.moveToFirst();
            String clue = c.getString(0);
            return clue;
        }
        return null;
    }

	//method used to mark locations off as 'visited'
    public void markVisited(String place) throws SQLException{
        ContentValues cvUpdate = new ContentValues();
        cvUpdate.put(COLUMN_NAME_VISITED, 1);
        ourDatabase.update(TABLE_NAME, cvUpdate, COLUMN_NAME_PLACES + "='" + place + "'", null);
    }

    public double[] geoCoords(String place){
        String[] columns = new String[]{ COLUMN_NAME_LAT, COLUMN_NAME_LON };
        Cursor c = ourDatabase.query(TABLE_NAME, columns, COLUMN_NAME_PLACES + "='" + place + "'", null, null, null, null);
        double[] coords = new double[2];
        if (c != null){
            c.moveToFirst();
            coords[0] = c.getDouble(0);
            coords[1] = c.getDouble(1);
            return coords;
        }
        return null;
    }

    public void setData(){
        open();
        /*createEntry("HUMANITY HOUSE", "butcherknife", 53.379333, -6.59635);
        createEntry("MARYS HOUSE", "arrow", 53.378992, -6.599074);
        createEntry("LOGIC HOUSE", "Dr. White", 53.378128, -6.596220);
        createEntry("LIBRARY", "Janitor Green", 53.381181, -6.599524);
        createEntry("SCIENCE BUILDING", "Dean Plum", 53.383178, -6.600479);
        createEntry("JOHN HUME", "Scarlet", 53.383984, -6.600361);
        createEntry("ARTS BUILDING", "Nurse White", 53.383613, -6.601960);
        createEntry("CALLAN BUILDING", "Dr. Peacock", 53.382557, -6.602334);
        createEntry("STUDENTS UNION", "leadpipe", 53.383066, -6.603628);
        createEntry("EOLAS", "chandelier", 53.384532, -6.601702);
        createEntry("PHOENIX", "revolver", 53.384289, -6.603676);
        createEntry("AULA MAXIMA", "rope", 53.380219, -6.597786);
        createEntry("NEW HOUSE", "New House", 53.380163, -6.597047);*/

        Locations locations = new Locations();

        List<String> clues = Arrays.asList("butcherknife", "arrow", "Dr. Mustard", "Janitor Green", "Dean Plum",
                "Scarlet", "Nurse White", "Dr. Peacock", "leadpipe", "chandelier", "revolver", "rope", "New House");

        int i = 0;
        for(String s : locations.keySet()) {
            createEntry(s,clues.get(i),locations.get(s).latitude,locations.get(s).longitude);
            i++;
        }

        close();
    }
}
