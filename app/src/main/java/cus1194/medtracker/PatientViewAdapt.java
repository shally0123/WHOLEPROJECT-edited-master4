package cus1194.medtracker;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by pruan086 on 3/7/2017.
 */

public class PatientViewAdapt extends FragmentPagerAdapter
{

    ArrayList<Fragment> fragments = new ArrayList<>();
    ArrayList<String> tabTitles = new ArrayList<>();

    public void addFragments(Fragment fragments, String titles)
    {
        this.fragments.add(fragments);
        this.tabTitles.add(titles);
    }


    public PatientViewAdapt(FragmentManager fm)
    {
        super(fm);
    }



    @Override
    public Fragment getItem(int position)
    {
        return fragments.get(position);
    }

    @Override
    public int getCount()
    {
        return fragments.size();
    }

    public CharSequence getTabTitles (int position)
    {
        return tabTitles.get(position);
    }


}
