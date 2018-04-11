package com.hrw.calendarlibrary;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

import java.util.List;

import static com.hrw.calendarlibrary.DateManger.getCurrentDay;
import static com.hrw.calendarlibrary.DateManger.getCurrentMonth;
import static com.hrw.calendarlibrary.DateManger.getCurrentYear;


/**
 * @auther:Herw
 * @describtion:
 * @date：2016/12/13
 */

public class SlideCalendarView extends ViewPager {
    private OnCalendarPageChangeListener listener;
    public Attribute attribute;
    public TextColor textColor;
    public BgColor bgColor;
    private CalendarView[] views;

    private int currentYear;
    private int currentMonth;
    private int currentDay;
    private int currentIndex = 498;
    private CalendarView mCurrentCalendarView;


    public SlideCalendarView(Context context) {
        super(context);
    }

    public SlideCalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        textColor = new TextColor();
        bgColor = new BgColor();
        attribute = new Attribute();
        float density = context.getResources().getDisplayMetrics().density;
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.SlideCalendarView);
        bgColor.validColor = array.getColor(R.styleable.SlideCalendarView_BgColor_validColor, Color.parseColor("#FFFFFF"));
        bgColor.invalidColor = array.getColor(R.styleable.SlideCalendarView_BgColor_invalidColor, Color.parseColor("#FFFFFF"));
        bgColor.mainColor = array.getColor(R.styleable.SlideCalendarView_BgColor_mainColor, Color.parseColor("#FFFFFF"));
        bgColor.clickColor = array.getColor(R.styleable.SlideCalendarView_BgColor_clickColor, Color.parseColor("#058AE9"));
        bgColor.currentBgColor = array.getColor(R.styleable.SlideCalendarView_BgColor_currentBgColor, Color.GREEN);

        textColor.invalidColor = array.getColor(R.styleable.SlideCalendarView_TextColor_invalidColor, Color.parseColor("#c5c5c5"));
        textColor.clickColor = array.getColor(R.styleable.SlideCalendarView_TextColor_clickColor, Color.parseColor("#FFFFFF"));
        textColor.validColor = array.getColor(R.styleable.SlideCalendarView_TextColor_validColor, Color.parseColor("#323232"));
        textColor.currentDayColor = array.getColor(R.styleable.SlideCalendarView_TextColor_currentDayColor, Color.RED);

        attribute.isCircleBg = array.getBoolean(R.styleable.SlideCalendarView_Attribute_isCircleBg, true);
        attribute.isShowFestival = array.getBoolean(R.styleable.SlideCalendarView_Attribute_isShowFestival, false);
        attribute.textSize = array.getDimensionPixelOffset(R.styleable.SlideCalendarView_Attribute_textSize, (int) (13 * density));
        attribute.radius = array.getDimensionPixelOffset(R.styleable.SlideCalendarView_Attribute_radius, (int) (20 * density));
        attribute.cellPadding = array.getDimensionPixelOffset(R.styleable.SlideCalendarView_Attribute_cellPadding, (int) (2 * density));
        attribute.paddingLeft = array.getDimensionPixelOffset(R.styleable.SlideCalendarView_Attribute_paddingLeft, (int) (16 * density));
        attribute.paddingRight = array.getDimensionPixelOffset(R.styleable.SlideCalendarView_Attribute_paddingRight, (int) (16 * density));
        attribute.iconBitmap = array.getDrawable(R.styleable.SlideCalendarView_Attribute_iconBitmap);
        init(context);
    }

    public void isCircleBg(boolean isCircleBg) {
        attribute.isCircleBg = isCircleBg;
    }

    public void isShowFestival(boolean isShowFestival) {
        attribute.isShowFestival = isShowFestival;
    }

    private void init(Context context) {
        currentYear = getCurrentYear();
        currentMonth = getCurrentMonth();
        currentDay = getCurrentDay();
        CalendarView[] views = createCalendarViewsForPager(context, 5);
        setAdapter(new CalendarViewPageAdapter(views));
        setCurrentItem(currentIndex);
        addOnPageChangeListener(new PageChangeListener() {
            @Override
            public void onDateChange(CalendarView calendarView, List<CellBO> cellBOs, int year, int month, int day) {
                calendarView.setDate(cellBOs);
                if (listener != null) {
                    listener.onPageChange(calendarView, formatInteger(year), formatInteger(month), formatInteger(day));
                }
                mCurrentCalendarView = calendarView;
            }
        });
    }


    /**
     * 生产出多个的CalendarView
     *
     * @param context
     * @param count
     * @return
     */
    public CalendarView[] createCalendarViewsForPager(Context context, int count) {
        views = new CalendarView[count];
        for (int i = 0; i < count; i++) {
            List<CellBO> cellBOs = CalenderPresenter.getCalendarDate(this, currentYear, currentMonth, currentDay);
            views[i] = new CalendarView(context, cellBOs, this);
            views[i].setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                @Override
                public void onValidClick(CellBO cellBO) {
                    if (listener != null) listener.onPageClickChange(cellBO);
                }
            });
        }
        return views;
    }


    /**
     * 格式化显示时间
     *
     * @param time
     * @return
     */
    private String formatInteger(int time) {
        return time <= 9 ? "0" + time : time + "";
    }

    public void setOnCalendarPageChangeListener(@NonNull OnCalendarPageChangeListener listener) {
        this.listener = listener;
        /*接口是数据初始化，实现接口时马上获取数据*/
        mCurrentCalendarView = views[currentIndex % views.length];
//        List<CellBO> cellBOs = CalenderPresenter.getCalendarDate(SlideCalendarView.this, getCurrentYear(), getCurrentMonth(), getCurrentDay());
        listener.onPageChange(mCurrentCalendarView, formatInteger(getCurrentYear()),
                formatInteger(getCurrentMonth()),
                formatInteger(getCurrentDay()));
        listener.onPageClickChange(mCurrentCalendarView.getCurrentCellBO());
    }

    public interface OnCalendarPageChangeListener {
        void onPageChange(CalendarView mCurrentCalendarView, String year, String month, String day);

        void onPageClickChange(CellBO cellBO);
    }


    public class TextColor {
        int invalidColor;//无效字体颜色
        int validColor; //有效字体颜色
        int clickColor;//选中的字体颜色
        int currentDayColor;//当天字体颜色
    }

    public class BgColor {
        int mainColor;//整体背景色

        int currentBgColor;
        int validColor;
        int invalidColor;
        int clickColor;//选中背景色
    }

    public class Attribute {
        boolean isShowFestival; //是否存在农历
        boolean isCircleBg;     //是否用圆形背景
        float textSize;         //显示文字大小
        float radius;           //显示圆的半径
        float cellPadding;      //单元格间距
        float paddingLeft;      //左内边距
        float paddingRight;     //右内边距
        Drawable iconBitmap;
    }

    public class PageChangeListener implements ViewPager.OnPageChangeListener {

        private Direction direction = Direction.NO;

        public PageChangeListener() {
            initDate();
        }

        @Override
        public void onPageSelected(int position) {

            getDirection(position);
            upData();
            //获得当前Calendar
            CalendarView calendarView = views[position % views.length];
            List<CellBO> cellBOs = CalenderPresenter.getCalendarDate(SlideCalendarView.this, currentYear, currentMonth, currentDay);
//            calendarView.setDate(cellBOs);
            onDateChange(calendarView, cellBOs, currentYear, currentMonth, currentDay);
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }

        /**
         * 滚动后更新数据
         */
        private void upData() {
            switch (direction) {
                case RIGHT:
                    currentMonth--;
                    currentIndex--;
                    if (currentMonth == 0) {
                        currentMonth = 12;
                        currentYear--;
                    }
                    break;
                case LEFT:
                    currentMonth++;
                    currentIndex++;
                    if (currentMonth > 12) {
                        currentMonth = 1;
                        currentYear++;
                    }
                    break;
                default:
                    break;
            }

            direction = Direction.NO;

//            System.out.println("当前年月：" + currentYear + "-" + currentMonth + "-" + currentDay);
        }

        /**
         * 获取滚动方向
         *
         * @param position
         */
        private void getDirection(int position) {
            if (position < currentIndex) {
                direction = Direction.RIGHT;
            } else if (position > currentIndex) {
                direction = Direction.LEFT;
            } else {
                direction = Direction.NO;
            }
        }

        public void onDateChange(CalendarView calendarView, List<CellBO> cellBOs, int year, int month, int day) {
        }

        /**
         * 数据初始化
         */
        private void initDate() {
            CalendarView calendarView = views[currentIndex % views.length];
            List<CellBO> cellBOs = CalenderPresenter.getCalendarDate(SlideCalendarView.this, currentYear, currentMonth, currentDay);
            onDateChange(calendarView, cellBOs, currentYear, currentMonth, currentDay);
        }


    }

    enum Direction {
        LEFT, RIGHT, NO
    }
}
