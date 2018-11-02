package com.y.mvp.activity.nested;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.NestedScrollingParentHelper;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

public class NestedParentLinearLayout extends LinearLayout {
    private static final String TAG = "Parent";

    public NestedParentLinearLayout(Context context) {
        super(context);
        init();
    }

    public NestedParentLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public NestedParentLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private NestedScrollingParentHelper mScrollingParentHelper;

    private void init(){
        mScrollingParentHelper = new NestedScrollingParentHelper(this);
    }

    /**
     * 有嵌套滑动到来了，问下该父View是否接受嵌套滑动
     *
     * @param child            嵌套滑动对应的父类的子类(因为嵌套滑动对于的父View不一定是一级就能找到的，可能挑了两级父View的父View，child的辈分>=target)
     * @param target           具体嵌套滑动的那个子类
     * @param nestedScrollAxes 支持嵌套滚动轴。水平方向，垂直方向，或者不指定
     * @return 是否接受该嵌套滑动
     */
    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        return true;
    }

    /**
     * 该父View接受了嵌套滑动的请求该函数调用。onStartNestedScroll返回true该函数会被调用。
     * 参数和onStartNestedScroll一样
     */
    @Override
    public void onNestedScrollAccepted(View child, View target, int axes) {
        mScrollingParentHelper.onNestedScrollAccepted(child, target, axes);
    }

    /**
     * 获取嵌套滑动的轴
     *
     * @see ViewCompat#SCROLL_AXIS_HORIZONTAL 垂直
     * @see ViewCompat#SCROLL_AXIS_VERTICAL 水平
     * @see ViewCompat#SCROLL_AXIS_NONE 都支持
     */
    @Override
    public int getNestedScrollAxes() {
        return mScrollingParentHelper.getNestedScrollAxes();
    }

    /**
     * 停止嵌套滑动
     *
     * @param child 具体嵌套滑动的那个子类
     */
    @Override
    public void onStopNestedScroll(View child) {
        mScrollingParentHelper.onStopNestedScroll(child);
    }

    /**
     * 嵌套滑动的子View在滑动之后报告过来的滑动情况
     *
     * @param target       具体嵌套滑动的那个子类
     * @param dxConsumed   水平方向嵌套滑动的子View滑动的距离(消耗的距离)
     * @param dyConsumed   垂直方向嵌套滑动的子View滑动的距离(消耗的距离)
     * @param dxUnconsumed 水平方向嵌套滑动的子View未滑动的距离(未消耗的距离)
     * @param dyUnconsumed 垂直方向嵌套滑动的子View未滑动的距离(未消耗的距离)
     */
    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
    }

    /**
     * 在嵌套滑动的子View未滑动之前告诉过来的准备滑动的情况
     *
     * @param target   具体嵌套滑动的那个子类
     * @param dx       水平方向嵌套滑动的子View想要变化的距离
     * @param dy       垂直方向嵌套滑动的子View想要变化的距离
     * @param consumed 这个参数要我们在实现这个函数的时候指定，回头告诉子View当前父View消耗的距离
     *                 consumed[0] 水平消耗的距离，consumed[1] 垂直消耗的距离 好让子view做出相应的调整
     */
    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        super.onNestedPreScroll(target,dx,dy,consumed);
        final View child = target;
        if (dx > 0) {
            if (child.getRight() + dx > getWidth()) {
                dx = child.getRight() + dx - getWidth();//多出来的
                offsetLeftAndRight(dx);
                consumed[0] += dx;//父亲消耗
            }
        } else {
            if (child.getLeft() + dx < 0) {
                dx = dx + child.getLeft();
                offsetLeftAndRight(dx);
                consumed[0] += dx;//父亲消耗
            }
        }

        if (dy > 0) {
            if (child.getBottom() + dy > getHeight()) {
                dy = child.getBottom() + dy - getHeight();
                offsetTopAndBottom(dy);
                consumed[1] += dy;
            }
        } else {
            if (child.getTop() + dy < 0) {
                dy = dy + child.getTop();
                offsetTopAndBottom(dy);
                consumed[1] += dy;//父亲消耗
            }
        }
    }

    /**
     * 嵌套滑动的子View在fling之后报告过来的fling情况
     *
     * @param target    具体嵌套滑动的那个子类
     * @param velocityX 水平方向速度
     * @param velocityY 垂直方向速度
     * @param consumed  子view是否fling了
     * @return true 父View是否消耗了fling
     */
    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        return super.onNestedFling(target, velocityX, velocityY, consumed);
    }

    /**
     * 在嵌套滑动的子View未fling之前告诉过来的准备fling的情况
     *
     * @param target    具体嵌套滑动的那个子类
     * @param velocityX 水平方向速度
     * @param velocityY 垂直方向速度
     * @return true 父View是否消耗了fling
     */
    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        return super.onNestedPreFling(target, velocityX, velocityY);
    }
}
