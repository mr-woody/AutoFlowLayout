package com.woodys.flowlayout.adapter

import android.content.Context
import android.database.DataSetObserver
import android.view.View
import android.view.ViewGroup

/**
 * Created by woodys on 2017/12/1.
 */
interface IAdapter {
    fun getView(context: Context, parent: ViewGroup, position: Int): View
    fun bindView(view: View, position: Int)
    fun registerDataSetObserver(observer: DataSetObserver)
    fun unregisterDataSetObserver(observer: DataSetObserver)
    fun notifyDataChanged()
}