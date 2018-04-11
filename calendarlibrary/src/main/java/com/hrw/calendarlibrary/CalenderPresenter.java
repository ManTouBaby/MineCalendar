package com.hrw.calendarlibrary;

import java.util.ArrayList;
import java.util.List;

/**
 * @auther:Herw
 * @describtion:
 * @date：2016/12/14
 */

public class CalenderPresenter {


    public static List<CellBO> getCalendarDate(SlideCalendarView slideCalendarView, int currentYear, int currentMonth, int currentDay) {
        LunarCalendar lunarCalendar = LunarCalendar.getInstance();//初始化农历管理器
        List<CellBO> cellBOs = new ArrayList<>();//数据存储
        List<CellBO> mainCellBOs = new ArrayList<>();//主要数据
        int currentMonthDays = DateManger.getMonthDays(currentYear, currentMonth);//本月对应的天数
        int lastMonth = currentMonth - 1;
        int lastMonthDays;//上一个月对应的天数
        int lastMonth2Year = currentYear;//上个月对应的年份
        if (lastMonth < 1) {
            lastMonth = 12;
            lastMonth2Year = currentYear - 1;
            lastMonthDays = DateManger.getMonthDays(lastMonth2Year, lastMonth);
        } else {
            lastMonthDays = DateManger.getMonthDays(lastMonth2Year, lastMonth);
        }

        //判断当月第一天的是否为第一周的第一天
        int firstDay = DateManger.getDayOfWeek(currentYear, currentMonth, 1);
        CellBO cellBO;
        if (firstDay != 0) {
            for (int i = lastMonthDays; i > (lastMonthDays - firstDay); i--) {
                int showDays = lastMonthDays - firstDay + (lastMonthDays - i) + 1;
                cellBO = new CellBO();
                String month = lastMonth < 10 ? "0" + lastMonth : "" + lastMonth;
                String day = showDays < 10 ? "0" + showDays : "" + showDays;
                cellBO.setDate(lastMonth2Year + "-" + month + "-" + day);
                cellBO.setYearMonth(lastMonth2Year + "-" + lastMonth);
                cellBO.setDayOfMonth(showDays);
                cellBO.setClickable(false);
                cellBO.setExistIcon(false);
                cellBO.setSelect(false);
                cellBO.setValid(false);
                cellBO.setCurrentDay(false);
                cellBO.setStatue(-1);
                cellBO.setIndex(cellBOs.size());
                cellBO.setDefaultBgColor(slideCalendarView.bgColor.invalidColor);
                cellBO.setDefaultTextColor(slideCalendarView.textColor.invalidColor);
                lunarCalendar.set(lastMonth2Year, lastMonth, showDays);
                String bText = lunarCalendar.getLunarDay();
                cellBO.setLunarData(bText);

                cellBOs.add(cellBO);
            }
        }
        //日历主要数据
        for (int i = 0; i < currentMonthDays; i++) {
            cellBO = new CellBO();
            cellBO.setIndex(cellBOs.size());
            cellBO.setExistIcon(false);
            cellBO.setClickable(true);
            cellBO.setSelect(false);
            cellBO.setValid(true);
            String month = currentMonth < 10 ? "0" + currentMonth : "" + currentMonth;
            String day = (i + 1) < 10 ? "0" + (i + 1) : "" + (i + 1);
            cellBO.setDate(currentYear + "-" + month + "-" + day);
            cellBO.setYearMonth(currentYear + "-" + currentMonth);
            cellBO.setDayOfMonth((i + 1));
            if ((i + 1) == DateManger.getCurrentDay() && currentMonth == DateManger.getCurrentMonth()) {
                cellBO.setCurrentTextColor(slideCalendarView.textColor.currentDayColor);
                cellBO.setCurrentBgColor(slideCalendarView.bgColor.currentBgColor);
                cellBO.setCurrentDay(true);
            } else {
                cellBO.setDefaultBgColor(slideCalendarView.bgColor.validColor);
                cellBO.setDefaultTextColor(slideCalendarView.textColor.validColor);
                cellBO.setCurrentDay(false);
            }
            lunarCalendar.set(currentYear, currentMonth, (i + 1));
            String bText = lunarCalendar.getLunarDay();
            cellBO.setLunarData(bText);
            cellBOs.add(cellBO);
            mainCellBOs.add(cellBO);
        }

        //日历尾部数据
        int lastShow = ((6 * 7) - cellBOs.size());
        for (int i = 0; i < lastShow; i++) {
            cellBO = new CellBO();
            cellBO.setIndex(cellBOs.size());
            cellBO.setClickable(false);
            cellBO.setExistIcon(false);
            cellBO.setSelect(false);
            cellBO.setValid(false);
            cellBO.setCurrentDay(false);
            cellBO.setStatue(-1);
            cellBO.setDayOfMonth((i + 1));
            cellBO.setDefaultBgColor(slideCalendarView.bgColor.invalidColor);
            cellBO.setDefaultTextColor(slideCalendarView.textColor.invalidColor);

            if (currentMonth + 1 > 12) {
                cellBO.setDate((currentYear + 1) + "-1-" + (i + 1));
                cellBO.setYearMonth((currentYear + 1) + "-1");
                lunarCalendar.set((currentYear + 1), 1, (i + 1));
                String bText = lunarCalendar.getLunarDay();
                cellBO.setLunarData(bText);
            } else {
                cellBO.setDate(currentYear + "-" + (currentMonth + 1) + "-" + (i + 1));
                cellBO.setYearMonth(currentYear + "-" + (currentMonth + 1));
                lunarCalendar.set(currentYear, (currentMonth + 1), (i + 1));
                String bText = lunarCalendar.getLunarDay();
                cellBO.setLunarData(bText);
            }

            cellBOs.add(cellBO);
        }

        /**
         * 将缓存的数据设置到日历中
         */
       /* Map<String, CellBO> bos = slideCalendarView.getCacheDate(currentYear + "-" + currentMonth);
        if (bos != null) {
            Set<Map.Entry<String, CellBO>> entries = bos.entrySet();
            for (Map.Entry<String, CellBO> entry : entries) {
                CellBO bo = entry.getValue();
                int index = bo.getDayOfMonth() - 1;
                CellBO bo1 = mainCellBOs.get(index);
                slideCalendarView.setCellSelect(bo1);
            }
        }*/

        return cellBOs;
    }


}
