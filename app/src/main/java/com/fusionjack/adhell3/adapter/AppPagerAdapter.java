package com.fusionjack.adhell3.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.fusionjack.adhell3.BuildConfig;
import com.fusionjack.adhell3.R;
import com.fusionjack.adhell3.fragments.AppTabPageFragment;

public class AppPagerAdapter extends FragmentPagerAdapter {
    private static final int PAGE_COUNT = 4;
    private String tabTitles[];

    public AppPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        tabTitles = new String[] {
                context.getString(R.string.package_disabler_fragment_title),
                context.getString(R.string.mobile_restricter_fragment_title),
                context.getString(R.string.wifi_restricter_fragment_title),
                context.getString(R.string.whitelist_fragment_title) };
    }

    @Override
    public Fragment getItem(int position) {
        return AppTabPageFragment.newInstance(BuildConfig.DISABLE_APPS ? position : position + 1);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        return BuildConfig.DISABLE_APPS ? PAGE_COUNT : PAGE_COUNT - 1;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[BuildConfig.DISABLE_APPS ? position : position + 1];
    }
}
