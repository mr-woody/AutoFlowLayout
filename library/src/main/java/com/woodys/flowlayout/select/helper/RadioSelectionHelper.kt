package com.woodys.flowlayout.select.helper

import android.view.View
import android.view.ViewGroup
import java.util.*

/**
 * @author Created by yuetao
 * @date 2019/7/29 下午3:38
 * @email yuetao.315@qq.com
 * 单选的选中辅助对象
 */
open abstract class RadioSelectionHelper<T>: AbsSelectHelper<T>() {

    /**
     * 上次选中的互斥位置
     */
    private var lastMutexPosition=-1

    private var multiSelectItem:T? = null

    init {
        setMaxSelectCount(1)
    }

    override fun setSelectPosition(parent: ViewGroup, selectView: View) {
        //此模式下不支持单选项反选,如果己经选中,将不操作
        setSelectBindData(parent,selectView,true)
    }

    override fun setSelectBindData(parent: ViewGroup, selectView: View,isSelected:Boolean) {
        val selectPosition = parent.indexOfChild(selectView)
        if(isSelected){
            //如果己经选中,将不操作
            if(selectPosition!=lastMutexPosition){
                selectView.isSelected = true
                mutexPosition(parent, selectPosition)
            }
        }else{
            unSelectPosition(parent,selectPosition)
        }
    }

    override fun clearSelectItems(){
        lastMutexPosition=-1
        multiSelectItem = null
    }

    /**
     * 选中位置与己存在位置互换
     */
    private fun mutexPosition(parent: ViewGroup, selectPosition: Int) {
        if (selectPosition != lastMutexPosition) {
            unSelectPosition(parent,lastMutexPosition)
            if (lastMutexPosition in (0 until parent.childCount)) onItemSelectChanged(parent, lastMutexPosition, false)
            lastMutexPosition = selectPosition
            multiSelectItem = onItemSelectChanged(parent, selectPosition, true)
        }
    }

    private fun unSelectPosition(parent: ViewGroup, position:Int) {
        if (position in (0 until parent.childCount)) {
            val childView = parent.getChildAt(position)
            childView.isSelected = false
        }
    }


    override fun getSelectPositionItems():List<Int> {
        if(lastMutexPosition == -1){
            return LinkedList()
        }
        return listOf(lastMutexPosition)
    }

    override fun getMultiSelectItems(): List<T?> {
        if(multiSelectItem==null){
            return LinkedList()
        }
        return listOf(multiSelectItem)
    }


}