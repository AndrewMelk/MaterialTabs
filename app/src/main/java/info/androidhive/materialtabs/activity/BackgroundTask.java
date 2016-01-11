package info.androidhive.materialtabs.activity;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import info.androidhive.materialtabs.DatabaseHandler;
import info.androidhive.materialtabs.Debt;

/**
 * Created by HP on 02.12.2015.
 */
public class BackgroundTask extends AsyncTask<Debt,Void,Void> {

    Context ctx;
    BackgroundTask(Context ctx){

        this.ctx = ctx;

    }

    @Override
    protected Void doInBackground(Debt... params) {




        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }



    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}


