package com.woodys.flowlayout.utils;

import android.view.View;
import android.view.ViewGroup;

public class MeasureSpecUtls {
    /**
     * 根据父 View 规则和子 View 的 ViewGroup.LayoutParams，计算子类的宽度(width)测量规则
     *
     * @param parentWidthMeasureSpec
     * @param view
     */
    public static int createChildWidthMeasureSpec(int parentWidthMeasureSpec,
                                            View view) {
        // 获取父 View 的测量模式
        int parentWidthMode = View.MeasureSpec.getMode(parentWidthMeasureSpec);
        // 获取父 View 的测量尺寸
        int parentWidthSize = View.MeasureSpec.getSize(parentWidthMeasureSpec);

        // 定义子 View 的测量规则
        int childWidthMeasureSpec = 0;

        // 获取子 View 的 ViewGroup.LayoutParams
        ViewGroup.LayoutParams layoutParams = (ViewGroup.LayoutParams) view.getLayoutParams();

        if (parentWidthMode == View.MeasureSpec.EXACTLY) {
            /* 这是当父类的模式是 dp 的情况 */
            if (layoutParams.width > 0) {
                childWidthMeasureSpec = View.MeasureSpec.makeMeasureSpec(
                        layoutParams.width, View.MeasureSpec.EXACTLY);
            } else if (layoutParams.width == ViewGroup.LayoutParams.WRAP_CONTENT) {
                childWidthMeasureSpec = View.MeasureSpec.makeMeasureSpec(
                        parentWidthSize, View.MeasureSpec.AT_MOST);
            } else if (layoutParams.width == ViewGroup.LayoutParams.MATCH_PARENT) {
                childWidthMeasureSpec = View.MeasureSpec.makeMeasureSpec(
                        parentWidthSize, View.MeasureSpec.EXACTLY);
            }
        } else if (parentWidthMode == View.MeasureSpec.AT_MOST) {
            /* 这是当父类的模式是 WRAP_CONTENT 的情况 */
            if (layoutParams.width > 0) {
                childWidthMeasureSpec = View.MeasureSpec.makeMeasureSpec(
                        layoutParams.width, View.MeasureSpec.EXACTLY);
            } else if (layoutParams.width == ViewGroup.LayoutParams.WRAP_CONTENT) {
                childWidthMeasureSpec = View.MeasureSpec.makeMeasureSpec(
                        parentWidthSize, View.MeasureSpec.AT_MOST);
            } else if (layoutParams.width == ViewGroup.LayoutParams.MATCH_PARENT) {
                childWidthMeasureSpec = View.MeasureSpec.makeMeasureSpec(
                        parentWidthSize, View.MeasureSpec.EXACTLY);
            }
        } else if (parentWidthMode == View.MeasureSpec.UNSPECIFIED) {
            /* 这是当父类的模式是 MATCH_PARENT 的情况 */
            if (layoutParams.width > 0) {
                childWidthMeasureSpec = View.MeasureSpec.makeMeasureSpec(
                        layoutParams.width, View.MeasureSpec.EXACTLY);
            } else if (layoutParams.width == ViewGroup.LayoutParams.WRAP_CONTENT) {
                childWidthMeasureSpec = View.MeasureSpec.makeMeasureSpec(0,
                        View.MeasureSpec.UNSPECIFIED);
            } else if (layoutParams.width == ViewGroup.LayoutParams.MATCH_PARENT) {
                childWidthMeasureSpec = View.MeasureSpec.makeMeasureSpec(0,
                        View.MeasureSpec.UNSPECIFIED);
            }
        }

        // 返回子 View 的测量规则
        return childWidthMeasureSpec;
    }

    /**
     * 根据父 View 规则和子 View 的 ViewGroup.LayoutParams，计算子类的宽度(width)测量规则
     *
     * @param parentHeightMeasureSpec
     * @param view
     */
    public static int createChildHeightMeasureSpec(int parentHeightMeasureSpec,
                                             View view) {
        int parentHeightMode = View.MeasureSpec.getMode(parentHeightMeasureSpec);
        int parentHeightSize = View.MeasureSpec.getSize(parentHeightMeasureSpec);

        int childHeightMeasureSpec = 0;

        ViewGroup.LayoutParams layoutParams = (ViewGroup.LayoutParams) view.getLayoutParams();

        if (parentHeightMode == View.MeasureSpec.EXACTLY) {
            if (layoutParams.height > 0) {
                childHeightMeasureSpec = View.MeasureSpec.makeMeasureSpec(
                        layoutParams.height, View.MeasureSpec.EXACTLY);
            } else if (layoutParams.height == ViewGroup.LayoutParams.WRAP_CONTENT) {
                childHeightMeasureSpec = View.MeasureSpec.makeMeasureSpec(
                        parentHeightSize, View.MeasureSpec.AT_MOST);
            } else if (layoutParams.height == ViewGroup.LayoutParams.MATCH_PARENT) {
                childHeightMeasureSpec = View.MeasureSpec.makeMeasureSpec(
                        parentHeightSize, View.MeasureSpec.EXACTLY);
            }
        } else if (parentHeightMode == View.MeasureSpec.AT_MOST) {
            if (layoutParams.height > 0) {
                childHeightMeasureSpec = View.MeasureSpec.makeMeasureSpec(
                        layoutParams.height, View.MeasureSpec.EXACTLY);
            } else if (layoutParams.height == ViewGroup.LayoutParams.WRAP_CONTENT) {
                childHeightMeasureSpec = View.MeasureSpec.makeMeasureSpec(
                        parentHeightSize, View.MeasureSpec.AT_MOST);
            } else if (layoutParams.height == ViewGroup.LayoutParams.MATCH_PARENT) {
                childHeightMeasureSpec = View.MeasureSpec.makeMeasureSpec(
                        parentHeightSize, View.MeasureSpec.EXACTLY);
            }
        } else if (parentHeightMode == View.MeasureSpec.UNSPECIFIED) {
            if (layoutParams.height > 0) {
                childHeightMeasureSpec = View.MeasureSpec.makeMeasureSpec(
                        layoutParams.height, View.MeasureSpec.EXACTLY);
            } else if (layoutParams.height == ViewGroup.LayoutParams.WRAP_CONTENT) {
                childHeightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0,
                        View.MeasureSpec.UNSPECIFIED);
            } else if (layoutParams.height == ViewGroup.LayoutParams.MATCH_PARENT) {
                childHeightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0,
                        View.MeasureSpec.UNSPECIFIED);
            }
        }

        return childHeightMeasureSpec;
    }


    public static void measureChild(View child, int parentWidthMeasureSpec,
                                int parentHeightMeasureSpec) {
        final ViewGroup.LayoutParams lp = child.getLayoutParams();
        final int childWidthMeasureSpec = ViewGroup.getChildMeasureSpec(parentWidthMeasureSpec, 0, lp.width);
        final int childHeightMeasureSpec = ViewGroup.getChildMeasureSpec(parentHeightMeasureSpec, 0, lp.height);

        child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
    }

}
