package info.androidhive.materialtabs.activity;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.provider.ContactsContract;
import android.widget.Toast;


import info.androidhive.materialtabs.Constants;
import info.androidhive.materialtabs.DatabaseHandler;
import info.androidhive.materialtabs.Debt;
import info.androidhive.materialtabs.R;
import info.androidhive.materialtabs.fragments.TwoFragment;

/**
 * Created by HP on 25.11.2015.
 */
public class DebtActivity extends Activity {
    private static final String TAG = "DebtActivity";
    String mContactName;
    String mContactNumber;
    Context ctx;
    EditText mMoney;
    TextView textView;
    TextView textView1;
    Button btnSave;


    //    String mContactNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.debt_layout);
        if (getIntent().getExtras().getString(Constants.CONTACT_NAME_KEY) != null) {
            mContactName = getIntent().getExtras().getString(Constants.CONTACT_NAME_KEY);
            mContactNumber = getIntent().getExtras().getString(Constants.CONTACT_NUMBER_KEY);

            textView = (TextView) findViewById(R.id.name);
            textView1 = (TextView) findViewById(R.id.phone);

            textView.setText(mContactName);
            textView1.setText(mContactNumber);

            btnSave = (Button) findViewById(R.id.BtnSave);
            mMoney = (EditText) findViewById(R.id.edit_money);
            final DatabaseHandler dbH = new DatabaseHandler(this);


            final Intent intent = new Intent(this,MainActivity.class);
            final Bundle bundle = new Bundle();

        btnSave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {



                Debt debtContact = new Debt(mContactName,mContactNumber,mMoney.getText().toString());

                    bundle.putParcelable(Constants.NEW_DEBT, debtContact);
                    intent.putExtras(bundle);

                    setResult(Activity.RESULT_OK, intent);

                    finish();

//                Intent intent1 = new Intent(this, MainActivity.class);
//                Bundle bundle1= new Bundle();
//                bundle1.putString(Constants.CONTACT_NAME_KEY, mContactName);
//                bundle1.putString(Constants.CONTACT_NUMBER_KEY, mContactNumber);
//                bundle1.putString(Constants.CONTACT_QUALITY, mMoney.getText().toString());
//                intent1.putExtras(bundle1);


            }});


        }}
    }

//                if (rMoney.isChecked()) {
//                    Debt debtContact = new Debt(mContactName, mContactNumber, nMoney.getText().toString(), rMoney.isChecked());
//
//                    bundle.putParcelable(Constants.NEW_DEBT, debtContact);
//                    intent.putExtras(bundle);
//
//                    setResult(Activity.RESULT_OK, intent);
//
//                    finish();
//                }
//                else {
//                    Debt debtContact = new Debt(mContactName, mContactNumber, nItem.getText().toString(), rMoney.isChecked());
//
//
//                    bundle.putString(Constants.NEW_DEBT, debtContact.toString());
//                    intent.putExtras(bundle);
//
//                    setResult(Activity.RESULT_OK, intent);
//
//                    finish();
//                }


