package com.hrw.calendarlibrary;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * @auther:Herw
 * @describtion:
 * @date：2016/12/14
 */

public class CalendarViewPageAdapter extends PagerAdapter {
    private CalendarView[] views;

    public CalendarViewPageAdapter(CalendarView[] views) {
        this.views = views;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(View container, int position) {
        ((ViewPager) container).addView(views[position % views.length], 0);
        return views[position % views.length];
    }


    @Override
    public void destroyItem(View container, int position, Object object) {
        ((ViewPager) container).removeView(views[position % views.length]);
    }
}
