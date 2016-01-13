package info.androidhive.materialtabs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by HP on 27.11.2015.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 5;

    // Database Name
    private static final String DATABASE_NAME = "debtManager";

    // Contacts table name
    public static final String TABLE_DEBT = "debt";

    // Contacts Table Columns names
     public static final String KEY_ID = "_id";
     public static final String KEY_NAME = "name";
     public static final String KEY_PH_NO = "phone_number";
     public static final String DEBT_Q = "debt_quality";



    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_DEBT_TABLE = "CREATE TABLE " + TABLE_DEBT + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_NAME + " TEXT,"
                + KEY_PH_NO + " TEXT,"
                + DEBT_Q + " TEXT" + ")";
        db.execSQL(CREATE_DEBT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DEBT);

        // Create tables again
        onCreate(db);

    }
    public void addDebt(Debt debt){
        //for logging
        Log.d("addDebt", debt.toString());
        Log.d("addDebt getName", debt.getName());
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();

        values.put(KEY_NAME, debt.getName()); // get name
        values.put(KEY_PH_NO, debt.getNumber()); // get Number
        values.put(DEBT_Q, debt.getMoney()); // get money

        // 3. insert
        db.insert(TABLE_DEBT, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values

        // 4. close
        db.close();
    }



    public List<Debt> getAllBooks() {

        List<Debt> debts = new LinkedList<Debt>();

        // 1. build the query
        String query = "SELECT * FROM " + TABLE_DEBT;

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build book and add it to list
        Debt debt = null;
        if (cursor.moveToFirst()) {
            do {
                debt = new Debt();
                debt.setId(Integer.parseInt(cursor.getString(0)));
                debt.setName(cursor.getString(1));
                debt.setNumber(cursor.getString(2));
                debt.setMoney(cursor.getString(3));
                // Add book to books
                debts.add(debt);


            } while (cursor.moveToNext());
        }



        // return books
        return debts;
    }
    private SQLiteDatabase mDb;
    public Cursor fetchAllDebts(){
        Cursor mCursor = mDb.query(TABLE_DEBT, new String[] {KEY_ID,
                        KEY_NAME, KEY_PH_NO, DEBT_Q,},
                null, null, null, null, null);

        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;

    }
}
