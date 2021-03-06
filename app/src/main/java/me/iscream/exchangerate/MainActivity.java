package me.iscream.exchangerate;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //初始化汇率
        SharedPreferences sp = getSharedPreferences("exchange_rate", MODE_PRIVATE);
        if(!sp.getString(getResources().getString(R.string.initialization),"").equals("true")) {
            SharedPreferences.Editor editor = sp.edit();
            editor.putString(getResources().getString(R.string.initialization), "true");
            editor.putString(getResources().getString(R.string.currency), getResources().getString(R.string.default_currency));
            editor.putString(getResources().getString(R.string.date),getResources().getString(R.string.default_date));
            editor.putString(getResources().getString(R.string.usd),getResources().getString(R.string.default_usd));
            editor.putString(getResources().getString(R.string.jpy),getResources().getString(R.string.default_jpy));
            editor.putString(getResources().getString(R.string.gbp),getResources().getString(R.string.default_gbp));
            editor.putString(getResources().getString(R.string.eur),getResources().getString(R.string.default_eur));
            editor.putString(getResources().getString(R.string.hkd),getResources().getString(R.string.default_hkd));
            editor.commit();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment;
            switch (sectionNumber) {
                case 1:
                    fragment = new PlaceholderFragment();
                    break;
                case 2:
                    fragment = new ExchangeFragment();
                    break;
                case 3:
                    fragment = new AboutFragment();
                    break;
                default:
                    fragment = new PlaceholderFragment();
            }
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_rate, container, false);
            SharedPreferences sp = rootView.getContext().getSharedPreferences("exchange_rate", MODE_PRIVATE);
            ((TextView)rootView.findViewById(R.id.usdRate)).setText(sp.getString(getResources().getString(R.string.usd), ""));
            ((TextView)rootView.findViewById(R.id.jpyRate)).setText(sp.getString(getResources().getString(R.string.jpy), ""));
            ((TextView)rootView.findViewById(R.id.gbpRate)).setText(sp.getString(getResources().getString(R.string.gbp), ""));
            ((TextView)rootView.findViewById(R.id.eurRate)).setText(sp.getString(getResources().getString(R.string.eur), ""));
            ((TextView)rootView.findViewById(R.id.hkdRate)).setText(sp.getString(getResources().getString(R.string.hkd), ""));
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

}
