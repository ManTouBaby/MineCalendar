package com.hrw.minecalendar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.hrw.calendarlibrary.CalendarView;
import com.hrw.calendarlibrary.CellBO;
import com.hrw.calendarlibrary.SlideCalendarView;

public class MainActivity extends AppCompatActivity {
    SlideCalendarView slideCalendarView;
    TextView tvYear;
    TextView tvMonth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvYear = findViewById(R.id.tv_calendar_year);
        tvMonth = findViewById(R.id.tv_calendar_month);
        slideCalendarView = findViewById(R.id.scv_show);
//        slideCalendarView.isShowFestival(true);
        slideCalendarView.setOnCalendarPageChangeListener(new SlideCalendarView.OnCalendarPageChangeListener() {
            @Override
            public void onPageChange(CalendarView mCurrentCalendarView, String year, String month, String day) {
                tvYear.setText(year + "年");
                tvMonth.setText(month + "月");
            }

            @Override
            public void onPageClickChange(CellBO cellBO) {

            }
        });

    }
}
