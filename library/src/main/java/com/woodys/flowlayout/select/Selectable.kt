package com.woodys.flowlayout.select

import android.view.View
import android.view.ViewGroup

/**
 * @author Created by yuetao
 * @date 2019/7/29 下午3:38
 * @email yuetao.315@qq.com
 * 选中数据状态处理器
 */
interface Selectable{

    /**
     * 初始化操作，主要用于数据清空初始化操作
     */
    fun onInit(parent: ViewGroup)

    /**
     * 绑定选中控件数据
     */
    fun onBindSelectView(parent: ViewGroup,v:View, position: Int)

    /**
     * 当选中状态发生变化时回调对象
     */
    fun onSelectChanged(parent: ViewGroup, v:View, position: Int)

}