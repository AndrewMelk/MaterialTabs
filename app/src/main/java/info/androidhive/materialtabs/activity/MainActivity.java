package info.androidhive.materialtabs.activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;


import com.parse.Parse;
import com.parse.ParseObject;

import info.androidhive.materialtabs.Constants;
import info.androidhive.materialtabs.DatabaseHandler;
import info.androidhive.materialtabs.Debt;
import info.androidhive.materialtabs.DebtsDbAdapter;
import info.androidhive.materialtabs.R;
import info.androidhive.materialtabs.fragments.OnContactSelectedListener;
import info.androidhive.materialtabs.fragments.OneFragment;
import info.androidhive.materialtabs.fragments.ThreeFragment;
import info.androidhive.materialtabs.fragments.TwoFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
                          implements OnContactSelectedListener {


private Cursor cursor;

//    public static final String SELECTED_CONTACT_ID 	= "contact_id";
//    public static final String KEY_PHONE_NUMBER 	= "phone_number";
//    public static final String KEY_CONTACT_NAME 	= "contact_name";
    private ParseObject parseObject;

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private int[] tabIcons = {
            R.drawable.ic_tab_contacts,
            R.drawable.ic_tab_call,
            R.drawable.ic_tab_favourite
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_icon_text_tabs);

        Parse.enableLocalDatastore(this);
        Parse.initialize(this);


//        ParseObject testObject = new ParseObject("TestObject");
//        testObject.put("foo", "bar");
//        testObject.saveInBackground();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
    }

    @Override
    protected void onPause() {
        super.onPause();

        viewPager.setCurrentItem(1);

    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new OneFragment(), "Contacts");
        adapter.addFrag(new TwoFragment(), "Debts");
        adapter.addFrag(new ThreeFragment(), "Create group debt");
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(1);
    }

    @Override
    public void onContactNameSelected(long contactId) {
        Intent intent = new Intent(this, DebtActivity.class);
        startActivity(intent);
    }

    @Override
    public void onContactNumberSelected(String contactNumber, String contactName) {




        Toast.makeText(this, contactNumber + " " + contactName, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, DebtActivity.class);
        Bundle bundle= new Bundle();
        bundle.putString(Constants.CONTACT_NAME_KEY,contactName);
        bundle.putString(Constants.CONTACT_NUMBER_KEY,contactNumber);
        intent.putExtras(bundle);
        startActivityForResult(intent, 2);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // запишем в лог значения requestCode и resultCode
        Log.d("myLogs", "requestCode = " + requestCode + ", resultCode = " + resultCode);

        // если пришло ОК
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Constants.DEBT_ACTIVITY_CODE:
                    if (data.getExtras().getParcelable(Constants.NEW_DEBT).toString() !=null)
                    {
                        Toast.makeText(MainActivity.this,data.getExtras().getParcelable(Constants.NEW_DEBT).toString() ,Toast.LENGTH_SHORT).show();

                        Debt recieveDebt = data.getExtras().getParcelable(Constants.NEW_DEBT);
                        Log.d("Get DEBT in MAin", recieveDebt.toString());
                        DebtsDbAdapter db = new DebtsDbAdapter(this);
                        parseObject = new ParseObject("Debt");
                        parseObject.put("Name",recieveDebt.getName());
                        parseObject.put("Number",recieveDebt.getNumber());
                        parseObject.put("Money",recieveDebt.getMoney());
                        parseObject.put("Currency",recieveDebt.getCurrency());
                        parseObject.saveInBackground();

//                        if(db.fetchDebtByNumber(recieveDebt.getNumber()).){
//
//                        }
                        db.open();
                        db.createCountry(recieveDebt);
                        db.close();

                        viewPager.setCurrentItem(1);


                    }
                    else
                        Toast.makeText(MainActivity.this, "is null",Toast.LENGTH_SHORT).show();

                    break;


            }

            // если вернулось не ОК
        } else {
            Toast.makeText(this, "Wrong result", Toast.LENGTH_SHORT).show();
        }
    }
}
