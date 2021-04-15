package com.woodys.flowlayout.select.helper

import android.view.View
import android.view.ViewGroup
import java.util.*

/**
 * @author Created by yuetao
 * @date 2019/7/29 下午3:38
 * @email yuetao.315@qq.com
 * 复选的选中辅助对象
 */
open abstract class MultipleSelectionHelper<T>(selectCount: Int): AbsSelectHelper<T>() {

    /**
     * 选中数据列
     */
    private val selectPositions= LinkedList<Int>()
    private val multiSelectItems = LinkedList<T?>()

    init {
        setMaxSelectCount(selectCount)
    }

    override fun setSelectPosition(parent: ViewGroup, selectView: View) {
        val isSelected = !selectView.isSelected
        //如果反选后是选中状态,执行选中操作;反选后是未选中状态,移除选择位置
        setSelectBindData(parent,selectView,isSelected)
    }

    override fun setSelectBindData(parent: ViewGroup, selectView: View,isSelected:Boolean) {
        val selectPosition = parent.indexOfChild(selectView)
        if(isSelected){
            selectPosition(parent, selectPosition)
        } else {
            unSelectPosition(parent,selectPosition)
        }
    }

    override fun clearSelectItems(){
        selectPositions.clear()
        multiSelectItems.clear()
    }

    /**
     * 选中当前选中位置控件
     */
    private fun selectPosition(parent: ViewGroup, selectPosition: Int){
        val maxSelectCount = getMaxSelectCount()
        if(maxSelectCount<=0 || selectPositions.size <= maxSelectCount){
            selectPositions.offerLast(selectPosition)
            val selectItem = onItemSelectChanged(parent, selectPosition, true)
            multiSelectItems.offerLast(selectItem)
            if(selectPosition in (0 until parent.childCount)){
                val childView = parent.getChildAt(selectPosition)
                childView.isSelected = true
            }
        }
    }

    /**
     * 选中当前选中位置控件
     */
    private fun unSelectPosition(parent: ViewGroup,selectPosition: Int){
        selectPositions.remove(selectPosition)
        val selectItem = onItemSelectChanged(parent, selectPosition, false)
        multiSelectItems.remove(selectItem)
        if(selectPosition in (0 until parent.childCount)){
            val childView = parent.getChildAt(selectPosition)
            childView.isSelected = false
        }
    }

    override fun getSelectPositionItems(): List<Int> {
        return selectPositions
    }

    override fun getMultiSelectItems(): List<T?> {
        return multiSelectItems
    }

}
