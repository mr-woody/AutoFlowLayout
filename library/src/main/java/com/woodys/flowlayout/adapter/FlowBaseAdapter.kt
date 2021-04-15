package com.woodys.flowlayout.adapter

import android.database.DataSetObservable
import android.database.DataSetObserver

import java.util.ArrayList

/**
 * @author Created by yuetao
 * @date 2019-07-29 11:07
 * @email yuetao.315@qq.com
 * @desc AutoFlowLayout的适配器
 */

abstract class FlowBaseAdapter<T>(items:List<T?>) : IAdapter {
    var wrapperItems: List<T?> = ArrayList()
    private val dataSetObservable = DataSetObservable()

    init {
        if (null != items && items.isNotEmpty()) {
            (this.wrapperItems as ArrayList<T?>).addAll(items)
        }
    }

    override fun registerDataSetObserver(observer: DataSetObserver){
        dataSetObservable.registerObserver(observer)
    }


    override fun unregisterDataSetObserver(observer: DataSetObserver){
        dataSetObservable.unregisterObserver(observer)
    }

    override fun notifyDataChanged(){
        dataSetObservable.notifyChanged()
    }


    /**
     * 获取当前item的对象
     */
    fun getItem(position: Int): T? {
        return wrapperItems[position]
    }

    /**
     * 获得条目个数
     */
    fun getItemCount():Int = wrapperItems.size


    fun getItems(): List<T?>? {
        return wrapperItems
    }

    /**
     * 替换数据
     */
    open fun swapItemsNotify(items: List<T?>?) {
        if (0 < getItemCount()) {
            clearItems()
        }
        addItemsNotify(items)
    }

    /**
     * 添加一个对象体,允许为null
     *
     * @param t
     */
    open fun addItemNotify(item: T?) {
        addItemNotify(getItemCount(), item)
    }


    /**
     * 在指定位置插入一个数据
     *
     * @param index
     * @param t
     */
    open fun addItemNotify(index: Int, item: T?) {
        if (0 <= index && index <= this.getItemCount()) {
            (this.wrapperItems as? ArrayList<T?>)?.add(index, item)
            notifyDataChanged()
        }
    }

    /**
     * 添加数据数据
     */
    open fun addItemsNotify(items: List<T?>?) {
        if (null != items) {
            (this.wrapperItems as? ArrayList<T?>)?.addAll(items)
            notifyDataChanged()
        }
    }

    open fun removeItemNotify(index: Int): T? {
        val item = (this.wrapperItems as? ArrayList<T?>)?.removeAt(index)
        notifyDataChanged()
        return item
    }

    open fun removeItemNotify(removeItem: T?): T? {
        var item: T? = null
        val index = (this.wrapperItems as? ArrayList<T?>)?.indexOf(removeItem)?:-1
        if (-1 != index) {
            item = removeItemNotify(index)
        }
        return item
    }

    /**
     * 移除所有条目的数据，不通知数据适配器更新数据
     */
    private fun clearItems() {
        (this.wrapperItems as? ArrayList<T?>)?.clear()
    }


    /**
     * 移除所有条目
     */
    open fun clearNotify() {
        clearItems()
        notifyDataChanged()
    }


}
