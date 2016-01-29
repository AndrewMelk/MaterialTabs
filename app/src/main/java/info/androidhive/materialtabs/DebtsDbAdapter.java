package info.androidhive.materialtabs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by HP on 13.01.2016.
 */
public class DebtsDbAdapter {

    public static final String KEY_ID = "_id";
    public static final String KEY_NAME = "name";
    public static final String KEY_PH_NO = "number";
    public static final String KEY_DEBT = "debt";
    public static final String KEY_CURRENCY = "currency";


    private static final String TAG = "DebtsDbAdapter";
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;

    private static final String DATABASE_NAME = "Debt";
    private static final String SQLITE_TABLE = "Debts";
    private static final int DATABASE_VERSION = 4;

    private final Context mCtx;

    private static final String DATABASE_CREATE ="CREATE TABLE " + SQLITE_TABLE + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_NAME + " TEXT,"
            + KEY_PH_NO + " TEXT,"
            + KEY_DEBT + " TEXT,"
            + KEY_CURRENCY + " TEXT" + ")";

    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }


        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.w(TAG, DATABASE_CREATE);
            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS " + SQLITE_TABLE);
            onCreate(db);
        }
    }

    public DebtsDbAdapter(Context ctx) {
        this.mCtx = ctx;
    }

    public DebtsDbAdapter open() throws SQLException {
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        if (mDbHelper != null) {
            mDbHelper.close();
        }
    }

    public void createCountry(Debt debt) {

        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_NAME, debt.getName());
        initialValues.put(KEY_PH_NO, debt.getNumber());
        initialValues.put(KEY_DEBT, debt.getMoney());
        initialValues.put(KEY_CURRENCY, debt.getCurrency());


        mDb.insert(SQLITE_TABLE, null, initialValues);
    }

    public boolean clearDB() {

        int doneDelete = 0;
        doneDelete = mDb.delete(SQLITE_TABLE, null , null);
        Log.w(TAG, Integer.toString(doneDelete));
        return doneDelete > 0;

    }

    public Cursor fetchDebtByNumber(String inputText) throws SQLException {
        Log.w(TAG, inputText);
        Cursor mCursor = null;
        if (inputText == null  ||  inputText.length () == 0)  {
            mCursor = mDb.query(SQLITE_TABLE, new String[] {KEY_ID,
                            KEY_NAME, KEY_PH_NO, KEY_DEBT,KEY_CURRENCY},
                    null, null, null, null, null);

        }
        else {
            mCursor = mDb.query(true, SQLITE_TABLE, new String[] {KEY_ID,
                            KEY_NAME, KEY_PH_NO, KEY_DEBT, KEY_CURRENCY},
                    KEY_NAME + " like '%" + inputText + "%'", null,
                    null, null, null, null);
        }
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;

    }

    public Cursor fetchAllDebts() {

        Cursor mCursor = mDb.query(SQLITE_TABLE, new String[] {KEY_ID,
                        KEY_NAME, KEY_PH_NO, KEY_DEBT, KEY_CURRENCY},
                null, null, null, null, null);

        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public void insertSomeCountries() {
        Debt debt = new Debt("Helen","(066)141-47-26","100","₴ Hryvnia");
        createCountry(debt);


    }
}
