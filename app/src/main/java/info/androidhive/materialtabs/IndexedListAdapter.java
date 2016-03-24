package info.androidhive.materialtabs;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v4.widget.SimpleCursorAdapter;
import android.widget.AlphabetIndexer;
import android.widget.SectionIndexer;

/**
 * Created by HP on 13.01.2016.
 */
public class IndexedListAdapter extends SimpleCursorAdapter implements SectionIndexer {

    AlphabetIndexer alphaIndexer;

    @Override
    public int getViewTypeCount() {
        // menu type count
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        // current menu type
        return position;
    }


    public IndexedListAdapter(Context context, int layout, Cursor c,
                              String[] from, int[] to) {
        super(context, layout, c, from, to, 0);
    }

    @Override
    public Cursor swapCursor(Cursor c) {
        if (c != null) {
            alphaIndexer = new AlphabetIndexer(c,
                    c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME),
                    " ABCDEFGHIJKLMNOPQRSTUVWXYZАБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ");
            c.getColumnIndex(ContactsContract.Contacts.PHOTO_ID);

        }

        return super.swapCursor(c);
    }

    @Override
    public int getPositionForSection(int section) {
        return alphaIndexer.getPositionForSection(section);
    }

    @Override
    public int getSectionForPosition(int position) {
        return alphaIndexer.getSectionForPosition(position);
    }

    @Override
    public Object[] getSections() {
        return alphaIndexer == null ? null : alphaIndexer.getSections();
    }

}