package com.woodys.sampletamplate.sample.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.woodys.flowlayout.adapter.FlowBaseAdapter
import com.woodys.flowlayout.select.SelectMode
import com.woodys.flowlayout.select.Selectable
import com.woodys.flowlayout.select.helper.AbsSelectHelper
import com.woodys.flowlayout.select.helper.MultipleSelectionHelper
import com.woodys.flowlayout.select.helper.RadioSelectionHelper
import com.woodys.sampletamplate.sample.R
import com.woodys.sampletamplate.sample.modle.TextEntity


/**
 * Created by woodys on 2019/7/31.
 */
open class SelectTextAdapter(items:List<TextEntity>, mode: SelectMode): FlowBaseAdapter<TextEntity>(items),Selectable {

    var selectionHelper: AbsSelectHelper<TextEntity>? = null

    init {
        setCheckedMode(mode)
    }

    override fun bindView(view: View, position: Int) {
        val item = getItem(position)
        val textView = view.findViewById<TextView>(R.id.tv_attr_tag)
        textView.text = item?.text
    }

    override fun getView(context: Context, parent: ViewGroup, position: Int): View {
        return LayoutInflater.from(context).inflate(R.layout.item_select_view, null)
    }


    override fun onInit(parent: ViewGroup) {
        selectionHelper?.clearSelectItems()
    }

    override fun onBindSelectView(parent: ViewGroup,v: View, position: Int) {
        val item : TextEntity? = getItem(position)
        selectionHelper?.setSelectBindData(parent,v,item?.isSelected==true)
    }

    override fun onSelectChanged(parent: ViewGroup,v: View, position: Int) {
        selectionHelper?.setSelectPosition(parent,v)
    }

    open fun setCheckedMode(mode: SelectMode){
        selectionHelper = when(mode){
            SelectMode.CHECK-> object:MultipleSelectionHelper<TextEntity>(-1){
                override fun onItemSelectChanged(parent: ViewGroup, position: Int, isSelected: Boolean): TextEntity? {
                    val item : TextEntity? = getItem(position)
                    item?.isSelected = isSelected
                    return item
                }

            }
            else-> object:RadioSelectionHelper<TextEntity>(){
                override fun onItemSelectChanged(parent: ViewGroup, position: Int, isSelected: Boolean): TextEntity? {
                    val item : TextEntity? = getItem(position)
                    item?.isSelected = isSelected
                    return item
                }
            }
        }
        notifyDataChanged()
    }
}