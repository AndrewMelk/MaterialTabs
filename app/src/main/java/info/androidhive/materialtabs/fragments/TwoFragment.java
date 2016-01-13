package info.androidhive.materialtabs.fragments;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.util.List;

import info.androidhive.materialtabs.DatabaseHandler;
import info.androidhive.materialtabs.Debt;
import info.androidhive.materialtabs.DebtsDbAdapter;
import info.androidhive.materialtabs.IndexedListAdapter;
import info.androidhive.materialtabs.R;


public class TwoFragment extends Fragment{

    ListView mList;


//    DatabaseHandler sqlHelper;
    SQLiteDatabase db;
    Cursor userCursor;
//    private SimpleCursorAdapter dataAdapter;

    private DebtsDbAdapter dbHelper;
    private SimpleCursorAdapter dataAdapter;
    private IndexedListAdapter ILAdapter;

    public TwoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbHelper = new DebtsDbAdapter(this.getActivity());
        dbHelper.open();
        //Clean all data
        dbHelper.deleteAllCountries();
        //Add some data
        dbHelper.insertSomeCountries();


    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_two, null);
        mList = (ListView) v.findViewById(R.id.debts_listView);
        displayListView(mList);

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_two, container, false);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



    }

    @Override
    public void onResume() {
        super.onResume();


    }

//    public void initData(){
//
//        db = sqlHelper.getReadableDatabase();
//        List<Debt> debtList = sqlHelper.getAllBooks();
//        userCursor = db.rawQuery("select * from " + DatabaseHandler.TABLE_DEBT, null);
//        if(debtList.isEmpty()){
//
//            Toast.makeText(getActivity(),"DataBase is Empty",Toast.LENGTH_LONG).show();
//
//
//        }else {
//
//            Toast.makeText(getActivity(),sqlHelper.getAllBooks().toString(),Toast.LENGTH_LONG).show();
//
//
//                String[] headers = new String[]{DatabaseHandler.KEY_NAME,DatabaseHandler.KEY_PH_NO,DatabaseHandler.DEBT_Q};
//                int[] to = new int[]{R.id.debt_display_name, R.id.display_number, R.id.debt_display};
//            dataAdapter = new SimpleCursorAdapter(this.getActivity(), R.layout.fragment_two,
//                        userCursor, headers, to, 0);
//                mList.setAdapter(dataAdapter);
//
//
//        }
//
//    }

    private void displayListView(ListView v){


        Cursor cursor = dbHelper.fetchAllDebts();

        // The desired columns to be bound
        String[] columns = new String[] {
                DebtsDbAdapter.KEY_NAME,
                DebtsDbAdapter.KEY_PH_NO,
                DebtsDbAdapter.KEY_DEBT,

        };

        // the XML defined views which the data will be bound to
        int[] to = new int[] {
                R.id.debt_display_name,
                R.id.debt_display_number,
                R.id.debt_display,
        };

        // create the adapter using the cursor pointing to the desired data
        //as well as the layout information
        ILAdapter = new IndexedListAdapter(
                this.getActivity(), R.layout.debts_list_item,
                cursor,
                columns,
                to);

        // Assign adapter to ListView
        v.setAdapter(dataAdapter);
    }



    @Override
    public void onDestroy(){
        super.onDestroy();
        // Закрываем подключения
        db.close();
        userCursor.close();
    }


}
