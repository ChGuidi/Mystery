package cs.nuim.maps;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by kacl2 on 11/19/2016.
 */

public class clueDb {
	
	public static final String TABLE_NAME = "Clues_DB";
	public static final String COLUMN_NAME_PLACES = "Places";
	public static final String COLUMN_NAME_VISITED = "Visited";
	public static final String COLUMN_NAME_CLUE = "Clue";
    public static final String COLUMN_NAME_LAT = "Latitude";
    public static final String COLUMN_NAME_LON = "Longitude";
	public static final int DATABASE_VERSION = 1;
	
	private DbHelper ourHelper;
    private final Context ourContext;
    private SQLiteDatabase ourDatabase;
	
	private static class DbHelper extends SQLiteOpenHelper {
        public DbHelper(Context context) {
            super(context, TABLE_NAME, null, DATABASE_VERSION);
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

    public clueDb(Context c){
        ourContext = c;
    }

    public clueDb open() throws SQLException {
        ourHelper = new DbHelper(ourContext);
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
        createEntry("HUMANITY HOUSE", "knife", 53.379333, -6.59635);
        createEntry("MARYS HOUSE", "bat", 53.378992, -6.599074);
        close();
    }
}
