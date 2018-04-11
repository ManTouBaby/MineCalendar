package com.hrw.calendarlibrary;

/**
 * @auther:Herw
 * @describtion:
 * @date：2016/12/12
 */

public class CellBO {
    private boolean isValid;//是否有效--本月日历之外的日期
    private boolean isSelect;//是否被选中
    private boolean isClickable;//是否能被单击使用，
    private boolean isExistIcon;//是否存在，处于休假中
    private boolean isCurrentDay;//是否是当天日期
    private String lunarData;//农历
    private String yearMonth;//存储年月
    private String bottomLabel;//存储底部文字，比如当日收入
    private String date;//时间
    private int dayOfMonth; //用于展示的时间
    private int currentBgColor;  //当天背景颜色
    private int currentTextColor;  //当天日期颜色


    private int bgColor;  //背景颜色
    private int textColor;//字体颜色
    private int clickBgColor;  //背景颜色
    private int clickTextColor;//字体颜色
    private int defaultBgColor;  //背景颜色
    private int defaultTextColor;//字体颜色


    private int index;//所在集合的位置
    private int statue;//单元格状态
    private float top;
    private float left;
    private float bottom;
    private float right;


    public boolean isCurrentDay() {
        return isCurrentDay;
    }

    public void setCurrentDay(boolean currentDay) {
        isCurrentDay = currentDay;
    }

    public int getCurrentBgColor() {
        return currentBgColor;
    }

    public void setCurrentBgColor(int currentBgColor) {
        this.currentBgColor = currentBgColor;
    }

    public int getCurrentTextColor() {
        return currentTextColor;
    }

    public void setCurrentTextColor(int currentTextColor) {
        this.currentTextColor = currentTextColor;
    }

    public int getDefaultBgColor() {
        return defaultBgColor;
    }

    public void setDefaultBgColor(int defaultBgColor) {
        this.defaultBgColor = defaultBgColor;
    }

    public int getDefaultTextColor() {
        return defaultTextColor;
    }

    public void setDefaultTextColor(int defaultTextColor) {
        this.defaultTextColor = defaultTextColor;
    }

    public int getClickBgColor() {
        return clickBgColor;
    }

    public void setClickBgColor(int clickBgColor) {
        this.clickBgColor = clickBgColor;
    }

    public int getClickTextColor() {
        return clickTextColor;
    }

    public void setClickTextColor(int clickTextColor) {
        this.clickTextColor = clickTextColor;
    }

    public int getStatue() {
        return statue;
    }

    public void setStatue(int statue) {
        this.statue = statue;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(String yearMonth) {
        this.yearMonth = yearMonth;
    }

    public String getBottomLabel() {
        return bottomLabel;
    }

    public void setBottomLabel(String bottomLabel) {
        this.bottomLabel = bottomLabel;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public String getLunarData() {
        return lunarData;
    }

    public void setLunarData(String lunarData) {
        this.lunarData = lunarData;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getDayOfMonth() {
        return dayOfMonth;
    }

    public void setDayOfMonth(int dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    public int getBgColor() {
        return bgColor;
    }

    public void setBgColor(int bgColor) {
        this.bgColor = bgColor;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public boolean isClickable() {
        return isClickable;
    }

    public void setClickable(boolean clickable) {
        isClickable = clickable;
    }

    public boolean isExistIcon() {
        return isExistIcon;
    }

    public void setExistIcon(boolean existIcon) {
        isExistIcon = existIcon;
    }

    public float getTop() {
        return top;
    }

    public void setTop(float top) {
        this.top = top;
    }

    public float getLeft() {
        return left;
    }

    public void setLeft(float left) {
        this.left = left;
    }

    public float getBottom() {
        return bottom;
    }

    public void setBottom(float bottom) {
        this.bottom = bottom;
    }

    public float getRight() {
        return right;
    }

    public void setRight(float right) {
        this.right = right;
    }
}
