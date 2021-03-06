package info.androidhive.materialtabs.fragments;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AlphabetIndexer;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SectionIndexer;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenuListView;

import info.androidhive.materialtabs.IndexedListAdapter;
import info.androidhive.materialtabs.R;

import static android.provider.ContactsContract.*;

public class OneFragment extends ListFragment implements
        LoaderManager.LoaderCallbacks<Cursor> {

private OnContactSelectedListener mContactsListener;
private IndexedListAdapter mAdapter;



private String mCurrentFilter = null;

private static final String[] CONTACTS_SUMMARY_PROJECTION = new String[] {
        Contacts._ID,
        Contacts.DISPLAY_NAME,
        Contacts.HAS_PHONE_NUMBER,
        Contacts.LOOKUP_KEY
        };




    @Override
public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.contact_list_fragment, container, false);

        }

@Override
public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setHasOptionsMenu(true);

        getLoaderManager().initLoader(0, null, this);





        mAdapter = new IndexedListAdapter(
        this.getActivity(),
        R.layout.contact_list_item,
        null,
        new String[] {ContactsContract.Contacts.DISPLAY_NAME},
        new int[] {R.id.display_name});

        setListAdapter(mAdapter);






        // Включить или отключить быстрый скролл с алфавитом!!!!!
        getListView().setFastScrollEnabled(true);

        }

@Override
public void onListItemClick(ListView l, View v, int position, long id) {
		/* Retrieving the phone numbers in order to see if we have more than one */
        String phoneNumber = null;
        String name = null;

        String[] projection = new String[] {CommonDataKinds.Phone.DISPLAY_NAME, CommonDataKinds.Phone.NUMBER};
final Cursor phoneCursor = getActivity().getContentResolver().query(
        CommonDataKinds.Phone.CONTENT_URI,
        projection,
        Data.CONTACT_ID + "=?",
        new String[]{String.valueOf(id)},
        null);

        if(phoneCursor.moveToFirst() && phoneCursor.isLast()) {
final int contactNumberColumnIndex 	= phoneCursor.getColumnIndex(CommonDataKinds.Phone.NUMBER);
        phoneNumber = phoneCursor.getString(contactNumberColumnIndex);
        name = phoneCursor.getString(phoneCursor.getColumnIndex(CommonDataKinds.Phone.DISPLAY_NAME));
        }

        if (phoneNumber != null){
        mContactsListener.onContactNumberSelected(phoneNumber, name);
        }
        else {
        mContactsListener.onContactNameSelected(id);
        }
        }

@Override
public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
        mContactsListener = (OnContactSelectedListener) activity;
        } catch (ClassCastException	e) {
        throw new ClassCastException(activity.toString() + " must implement OnContactSelectedListener");
        }
        }

@Override
public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
        Uri baseUri;

        if (mCurrentFilter != null) {
        baseUri = Uri.withAppendedPath(Contacts.CONTENT_FILTER_URI,
        Uri.encode(mCurrentFilter));
        } else {
        baseUri = Contacts.CONTENT_URI;
        }

        String selection = "((" + Contacts.DISPLAY_NAME + " NOTNULL) AND ("
        + Contacts.HAS_PHONE_NUMBER + "=1) AND ("
        + Contacts.DISPLAY_NAME + " != '' ))";

        String sortOrder = Contacts.DISPLAY_NAME + " COLLATE LOCALIZED ASC";

        return new CursorLoader(getActivity(), baseUri, CONTACTS_SUMMARY_PROJECTION, selection, null, sortOrder);
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
                mAdapter.swapCursor(data);
                }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
        }




//    class IndexedListAdapter extends SimpleCursorAdapter implements SectionIndexer {
//
//    AlphabetIndexer alphaIndexer;
//
//    public IndexedListAdapter(Context context, int layout, Cursor c,
//                              String[] from, int[] to) {
//        super(context, layout, c, from, to, 0);
//    }
//
//    @Override
//    public Cursor swapCursor(Cursor c) {
//        if (c != null) {
//            alphaIndexer = new AlphabetIndexer(c,
//                    c.getColumnIndex(Contacts.DISPLAY_NAME),
//                    " ABCDEFGHIJKLMNOPQRSTUVWXYZАБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ");
//            c.getColumnIndex(Contacts.PHOTO_ID);
//
//        }
//
//        return super.swapCursor(c);
//    }
//
//    @Override
//    public int getPositionForSection(int section) {
//        return alphaIndexer.getPositionForSection(section);
//    }
//
//    @Override
//    public int getSectionForPosition(int position) {
//        return alphaIndexer.getSectionForPosition(position);
//    }
//
//    @Override
//    public Object[] getSections() {
//        return alphaIndexer == null ? null : alphaIndexer.getSections();
//    }

//}



}