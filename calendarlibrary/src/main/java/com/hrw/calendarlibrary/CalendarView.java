package com.hrw.calendarlibrary;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.List;


/**
 * @auther:Herw
 * @describtion:
 * @date：2016/12/12
 */

public class CalendarView extends View {
    private OnDateChangeListener onDateChangeListener;
    private SlideCalendarView slideCalendarView;
    private Paint pain;
    private Paint selectPain;


    private List<CellBO> cellBOs;//数据存储
    private List<String> strings;//被选中
    private float cellW, cellH;
    private float width, height;

    private float density;


    public CalendarView(Context context, List<CellBO> cellBOs, SlideCalendarView slideCalendarView) {
        super(context);
        this.slideCalendarView = slideCalendarView;
        this.cellBOs = cellBOs;
        density = context.getResources().getDisplayMetrics().density;
        init();
    }

    public CalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private void init() {
        pain = new Paint();
        pain.setAntiAlias(true);
        pain.setTextAlign(Paint.Align.CENTER);
//        pain.setColor(slideCalendarView.bgColor.defaultColor);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //计算子控件的宽和高
        int measureWidth = MeasureSpec.getSize(widthMeasureSpec);
        setMeasuredDimension(measureWidth, (int) (236 * density));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        setCellPosition();
        drawBG(canvas);
        drawCell(canvas);
    }

    PointF pointF = new PointF();

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                pointF.y = event.getY();
                pointF.x = event.getX() - slideCalendarView.attribute.paddingLeft;
                break;
            case MotionEvent.ACTION_UP:
                int x = (int) (pointF.x / cellW);
                if (x > 6) x = 6;//防止边界单击出错
                int y = (int) (pointF.y / cellH);
                int index = 7 * y + x;
                CellBO cellBO = cellBOs.get(index);
                if (cellBO.isValid()) {  //有效单元格
                    if (cellBO.isClickable()) {
                        if (onDateChangeListener != null)
                            onDateChangeListener.onValidClick(cellBO);
                    }

                    for (int i = 0; i < cellBOs.size(); i++) {
                        CellBO bo = cellBOs.get(i);
                        if (bo.isValid()) {
                            bo.setClickBgColor(0);
                            bo.setClickTextColor(0);
                            bo.setSelect(false);
                        }
                    }
                    cellBO.setClickBgColor(slideCalendarView.bgColor.clickColor);
                    cellBO.setClickTextColor(slideCalendarView.textColor.clickColor);
                    cellBO.setSelect(true);

                    invalidate();
                }
                break;
        }

        return true;
    }


    /**
     * 绘制日期单元格
     *
     * @param canvas
     */
    private void drawCell(Canvas canvas) {
        for (int i = 0; i < cellBOs.size(); i++) {
            CellBO cellBO = cellBOs.get(i);
            drawCellBg(canvas, cellBO);
            if (cellBO.isExistIcon()) drawCellIcon(canvas, cellBO);
            drawCellText(canvas, cellBO);
        }
    }

    public void minePerformClick(String date) {
        CellBO cellBO = null;
        for (int i = 0; i < cellBOs.size(); i++) {
            if (cellBOs.get(i).equals(date)) cellBO = cellBOs.get(i);
        }
        if (onDateChangeListener != null)
            onDateChangeListener.onValidClick(cellBO);
    }

    /**
     * 绘制单元格背景
     *
     * @param canvas
     * @param cellBO
     */
    private void drawCellBg(Canvas canvas, CellBO cellBO) {
        float l = cellBO.getLeft();
        float t = cellBO.getTop();
        float r = cellBO.getRight();
        float b = cellBO.getBottom();
        if (slideCalendarView.attribute.isCircleBg) {
            pain.setColor(cellBO.getDefaultBgColor());
            canvas.drawCircle((l + r) / 2, (t + b) / 2 - 4 * density, slideCalendarView.attribute.radius, pain);

            if (cellBO.isCurrentDay()) {
                pain.setStyle(Paint.Style.STROKE);
                pain.setColor(cellBO.getCurrentBgColor());
                canvas.drawCircle((l + r) / 2, (t + b) / 2 - 4 * density, slideCalendarView.attribute.radius, pain);
                pain.setStyle(Paint.Style.FILL);
            }

            pain.setColor(cellBO.getBgColor());
            canvas.drawCircle((l + r) / 2, (t + b) / 2 - 4 * density, slideCalendarView.attribute.radius, pain);
            pain.setColor(cellBO.getClickBgColor());
            canvas.drawCircle((l + r) / 2, (t + b) / 2 - 4 * density, slideCalendarView.attribute.radius, pain);
        } else {
            pain.setColor(cellBO.getDefaultTextColor());
            canvas.drawRect(l + slideCalendarView.attribute.cellPadding, t + slideCalendarView.attribute.cellPadding,
                    r - slideCalendarView.attribute.cellPadding, b - slideCalendarView.attribute.cellPadding, pain);
            pain.setColor(cellBO.getCurrentBgColor());
            canvas.drawRect(l + slideCalendarView.attribute.cellPadding, t + slideCalendarView.attribute.cellPadding,
                    r - slideCalendarView.attribute.cellPadding, b - slideCalendarView.attribute.cellPadding, pain);
            pain.setColor(cellBO.getBgColor());
            canvas.drawRect(l + slideCalendarView.attribute.cellPadding, t + slideCalendarView.attribute.cellPadding,
                    r - slideCalendarView.attribute.cellPadding, b - slideCalendarView.attribute.cellPadding, pain);
            pain.setColor(cellBO.getClickBgColor());
            canvas.drawRect(l + slideCalendarView.attribute.cellPadding, t + slideCalendarView.attribute.cellPadding,
                    r - slideCalendarView.attribute.cellPadding, b - slideCalendarView.attribute.cellPadding, pain);
        }
    }

    public CellBO getCurrentCellBO() {
        for (int i = 0; i < cellBOs.size(); i++) {
            CellBO cellBO = cellBOs.get(i);
            if (cellBO.isCurrentDay()) {
                return cellBO;
            }
        }
        return null;
    }


    /**
     * 绘制单元格图标
     *
     * @param canvas
     * @param cellBO
     */
    private void drawCellIcon(Canvas canvas, CellBO cellBO) {
        float t = cellBO.getTop();//让图标向下偏移5个像素
        float r = cellBO.getRight();
        if (slideCalendarView.attribute.iconBitmap != null) {
            BitmapDrawable bd = (BitmapDrawable) slideCalendarView.attribute.iconBitmap;
            Bitmap bm = bd.getBitmap();
            canvas.drawBitmap(bm, r - bm.getWidth() - 8 * density, t + 8 * density, pain);
        }
    }

    /**
     * 绘制单元格文字
     *
     * @param canvas
     * @param cellBO
     */
    private void drawCellText(Canvas canvas, CellBO cellBO) {
        float l = cellBO.getLeft();
        float t = cellBO.getTop();
        float r = cellBO.getRight();
        float b = cellBO.getBottom();

        RectF rect = new RectF(l, t, r, b);
        Paint.FontMetricsInt fontMetrics = pain.getFontMetricsInt();
        float baseline = rect.top + (rect.bottom - rect.top - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top;
        pain.setColor(cellBO.getDefaultTextColor());
        pain.setTextSize(slideCalendarView.attribute.textSize);


        if (!slideCalendarView.attribute.isShowFestival) {
            pain.setColor(cellBO.getDefaultTextColor());
            canvas.drawText(cellBO.getDayOfMonth() + "", rect.centerX(), rect.centerY(), pain);

            if (cellBO.isCurrentDay()) {
                pain.setColor(cellBO.getCurrentTextColor());
                canvas.drawText(cellBO.getDayOfMonth() + "", rect.centerX(), rect.centerY(), pain);
            }

            pain.setColor(cellBO.getTextColor());
            canvas.drawText(cellBO.getDayOfMonth() + "", rect.centerX(), rect.centerY(), pain);
            pain.setColor(cellBO.getClickTextColor());
            canvas.drawText(cellBO.getDayOfMonth() + "", rect.centerX(), rect.centerY(), pain);
        }
        if (slideCalendarView.attribute.isShowFestival) { //存在农历时的写法  绘制新历
            pain.setTextSize(slideCalendarView.attribute.textSize);
            canvas.drawText(cellBO.getDayOfMonth() + "", rect.centerX(), rect.centerY() - 4 * density, pain);
            //绘制农历
            String bText = cellBO.getLunarData();
            pain.setTextSize(20);
            if (bText.length() < 5) {
                canvas.drawText(bText, rect.centerX(), baseline + 4 * density, pain);
            } else {
                int cut = (bText.length() + 1) / 2;
                String top = bText.substring(0, cut);
                String bottom = bText.substring(cut, bText.length());
                canvas.drawText(top, rect.centerX(), (float) (rect.centerY() + 7.5 * density), pain);
                canvas.drawText(bottom, rect.centerX(), rect.centerY() + 18 * density, pain);
            }
        }

        if (cellBO.getBottomLabel() != null) {
            if (!cellBO.isClickable()) {
                pain.setColor(slideCalendarView.bgColor.invalidColor);
            }

            if (cellBO.isSelect()) {
                pain.setColor(slideCalendarView.bgColor.clickColor);
            }

            canvas.drawText(cellBO.getBottomLabel(), rect.centerX(), rect.bottom - 0 * density, pain);
        }
    }

    /**
     * 绘制整体背景
     *
     * @param canvas
     */
    private void drawBG(Canvas canvas) {
        pain.setColor(slideCalendarView.bgColor.mainColor);
        canvas.drawRect(0, 0, width, height, pain);
    }


    public void setDate(List<CellBO> cellBOs) {
        this.cellBOs = cellBOs;
        if (strings != null) for (int i = 0; i < strings.size(); i++) {
            String date = strings.get(i);
            for (int j = 0; j < cellBOs.size(); j++) {
                CellBO cellBO = cellBOs.get(j);
                if (cellBO.getDate().equals(date)) {
                    cellBO.setStatue(1);
                }
            }
        }
        invalidate();
    }

    public void setCellDate(List<String> strings, int bgColor, int textColor, int status) {
        this.strings = strings;
        for (int i = 0; i < strings.size(); i++) {
            String date = strings.get(i);
            for (int j = 0; j < cellBOs.size(); j++) {
                CellBO cellBO = cellBOs.get(j);
                if (cellBO.getDate().equals(date)) {
                    cellBO.setBgColor(bgColor);
                    cellBO.setTextColor(textColor);
                    cellBO.setStatue(status);
                }
            }
        }
        invalidate();
    }

    private void setCellPosition() {
        for (int i = 0; i < cellBOs.size(); i++) {
            CellBO bo = cellBOs.get(i);
            if (bo.getTop() != 0 || bo.getBottom() != 0 || bo.getLeft() != 0 || bo.getRight() != 0)
                break;
            bo.setLeft((i % 7) * cellW + (slideCalendarView.attribute.paddingLeft + slideCalendarView.attribute.paddingRight) / 2);
            bo.setTop((i / 7) * cellH);
            bo.setRight(bo.getLeft() + cellW);
            bo.setBottom(bo.getTop() + cellH);
        }
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
        cellH = h / 6f;
        cellW = (w - slideCalendarView.attribute.paddingLeft - slideCalendarView.attribute.paddingRight) / 7;
    }

    public void setOnDateChangeListener(OnDateChangeListener onDateChangeListener) {
        this.onDateChangeListener = onDateChangeListener;
    }

    public interface OnDateChangeListener {

        void onValidClick(CellBO cellBO);
    }

}
