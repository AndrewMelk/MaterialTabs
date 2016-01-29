package info.androidhive.materialtabs.fragments;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import info.androidhive.materialtabs.DatabaseHandler;
import info.androidhive.materialtabs.DebtsDbAdapter;
import info.androidhive.materialtabs.IndexedListAdapter;
import info.androidhive.materialtabs.R;


public class TwoFragment extends ListFragment {

    ListView mList;
    private LayoutInflater mLayoutInflater;

//    DatabaseHandler sqlHelper;
    SQLiteDatabase db;
    Cursor userCursor;
//    private SimpleCursorAdapter dataAdapter;

    private DebtsDbAdapter dbHelper;
    private SimpleCursorAdapter dataAdapter;
    private IndexedListAdapter ILAdapter;
    private DatabaseHandler dbHandler;
    public TwoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbHelper = new DebtsDbAdapter(this.getActivity());
        dbHelper.open();
        //Clean all data
        dbHelper.clearDB();
        //Add some data
//        dbHelper.insertSomeCountries();
        displayListView();

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        View v = inflater.inflate(R.layout.fragment_two, null);


        displayListView();
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_two, container, false);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        displayListView();


    }

    @Override
    public void onResume() {
        super.onResume();
        displayListView();

    }


    private void displayListView(){


        Cursor cursor = dbHelper.fetchAllDebts();

        // The desired columns to be bound
        String[] columns = new String[] {
                DebtsDbAdapter.KEY_NAME,
                DebtsDbAdapter.KEY_PH_NO,
                DebtsDbAdapter.KEY_DEBT,
                DebtsDbAdapter.KEY_CURRENCY

        };

        // the XML defined views which the data will be bound to
        int[] to = new int[] {
                R.id.debt_display_name,
                R.id.debt_display_number,
                R.id.debt_display,
                R.id.debt_currency
        };

        // create the adapter using the cursor pointing to the desired data
        //as well as the layout information
        dataAdapter = new SimpleCursorAdapter(
                this.getActivity(), R.layout.debts_list_item,
                cursor,
                columns,
                to,0);
//
        // Assign adapter to ListView
        setListAdapter(dataAdapter);
    }





    @Override
    public void onDestroy(){
        super.onDestroy();
        // Закрываем подключения
//        db.close();
//        userCursor.close();
    }


}
