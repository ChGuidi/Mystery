package cs.nuim.maps;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ActionBarOverlayLayout;
import android.widget.Button;
import android.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class CluesActivity extends FragmentActivity {
    // When requested, this adapter returns a CluesFragment,
    // representing an object in the collection.
    CluesPagerAdapter mCluesPagerAdapter;
    ViewPager mViewPager;
    TabLayout tabLayout;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clues);

        // ViewPager and its adapters use support library
        // fragments, so use getSupportFragmentManager.
        mCluesPagerAdapter =
                new CluesPagerAdapter(
                        getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mCluesPagerAdapter);

        tabLayout = (TabLayout) findViewById(R.id.tab);
        tabLayout.setupWithViewPager(mViewPager);

        ViewGroup vg = (ViewGroup) tabLayout.getChildAt(0);

        Button goBack = (Button) findViewById(R.id.goBack);
        Typeface font2 = Typeface.createFromAsset(getAssets(), "GingerbreadHouse.ttf");
        goBack.setTypeface(font2);

        Typeface font = Typeface.createFromAsset(getAssets(), "SEVESBRG.TTF");
        int tabsCount = vg.getChildCount();
        for (int j = 0; j < tabsCount; j++) {
            ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);
            int tabChildsCount = vgTab.getChildCount();
            for (int i = 0; i < tabChildsCount; i++) {
                View tabViewChild = vgTab.getChildAt(i);
                if (tabViewChild instanceof TextView) {
                    ((TextView) tabViewChild).setTypeface(font);
                }
            }
        }
    }

    public void goBack(View view) {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }
}

class CluesPagerAdapter extends FragmentPagerAdapter {
    public CluesPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment;
        if(i==0) {
            fragment = new SuspectsFragment();
        } else if (i==1) {
            fragment = new WeaponFragment();
        } else {
            fragment = new PlaceFragment();
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if(position == 0) {
            return "Suspects";
        } else if (position == 1) {
            return "Weapons";
        } else {
            return "Crime scene";
        }
    }
}
