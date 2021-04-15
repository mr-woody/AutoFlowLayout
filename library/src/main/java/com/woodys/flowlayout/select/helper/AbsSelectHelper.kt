package com.woodys.flowlayout.select.helper

import android.view.View
import android.view.ViewGroup

/**
 * @author Created by woodys
 * @date 2019/4/2 下午4:24
 * @email yuetao.315@qq.com
 *
 */
abstract class AbsSelectHelper<T>{
    /**
     * 设置允许选中最大个数,默认值-1，表示不做限制
     */
    var selectCount = -1

    /**
     * 选中一个位置
     */
    abstract fun setSelectPosition(parent: ViewGroup, selectView: View)


    /**
     * 初始化数据
     */
    abstract fun setSelectBindData(parent: ViewGroup, selectView: View, isSelected: Boolean)


    /**
     * 当选中的数据发生改变时触发
     */
    abstract fun onItemSelectChanged(parent: ViewGroup, position: Int,isSelected:Boolean): T?

    /**
     * 获得选中数据列的Position集合
     */
    abstract fun getSelectPositionItems():List<Int>

    /**
     * 获得选中数据列的具体数据对象
     */
    abstract fun getMultiSelectItems():List<T?>

    /**
     * 清空数据列的具体数据对象
     */
    abstract fun clearSelectItems()


    /**
     * 设置最大选中个数
     */
    fun setMaxSelectCount(maxSelectCount: Int) {
        this.selectCount=maxSelectCount
    }
    /**
     * 可选的最大数
     */
    fun getMaxSelectCount() = selectCount

}