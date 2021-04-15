package com.woodys.sampletamplate.sample.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.woodys.flowlayout.AutoFlowLayout
import com.woodys.flowlayout.callback.OnItemClickListener
import com.woodys.flowlayout.select.SelectMode
import com.woodys.sampletamplate.ToolBarActivity
import com.woodys.sampletamplate.sample.R
import com.woodys.sampletamplate.sample.adapter.SelectTextAdapter
import com.woodys.sampletamplate.sample.modle.TextEntity
import com.woodys.sampletamplate.view.RadioLayout
import kotlin.collections.ArrayList


/**
 * @author :Created by yeutao
 * @date 2019-07-30 15:30
 * @email yuetao.315@qq.com
 * 演示2 见配置
 * @see com.woodys.sampletamplate.sample.SampleApplication.onCreate
 */
class Sample2Activity: ToolBarActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo2)

        val layout = findViewById(R.id.gl_layout) as AutoFlowLayout

        val mData = arrayOf("Java", "C++", "Python", "JavaScript", "Ruby", "Swift")
        val arrayList = ArrayList<TextEntity>()
        mData.forEachIndexed { index, data ->
            arrayList.add(TextEntity(data,index%2==0))
        }
        val selectTextAdapter = SelectTextAdapter(arrayList,SelectMode.CHECK)
        layout.setOnItemClickListener(OnItemClickListener { view, position ->
            run {
                Toast.makeText(view.context, "点击$position", Toast.LENGTH_SHORT).show()
            }
        })
        layout.setAdapter(selectTextAdapter)


        val typeLayout = findViewById(R.id.rl_type) as RadioLayout
        typeLayout.check(0)
        typeLayout.setOnCheckedListener(object : RadioLayout.OnCheckedListener {
            override fun onChecked(v: View, position: Int, isChecked: Boolean) {
                if(position==0){
                    selectTextAdapter.setCheckedMode(SelectMode.CHECK)
                }else{
                    selectTextAdapter.setCheckedMode(SelectMode.RADIO)
                }

            }
        })

        findViewById<View>(R.id.btn_add).setOnClickListener(View.OnClickListener {
            val childText = "Text" + layout.childCount
            selectTextAdapter.addItemNotify(TextEntity(childText, (Math.random()*100).toInt()%2==0))

        })
        findViewById<View>(R.id.btn_remove).setOnClickListener(View.OnClickListener {
            val childCount = layout.childCount
            if (0 < childCount) {
                selectTextAdapter.removeItemNotify(childCount - 1)
            }
        })

        findViewById<View>(R.id.btn_get).setOnClickListener(View.OnClickListener {
            val selectItems = selectTextAdapter.selectionHelper?.getMultiSelectItems()
            val positionItems = selectTextAdapter.selectionHelper?.getSelectPositionItems()
            Toast.makeText(this, "获取对应选中的实体集合：${selectItems.toString()}，获取选中的positions集合：${positionItems.toString()},\n获取集合数据：item=${selectTextAdapter.wrapperItems}", Toast.LENGTH_SHORT).show()
        })
    }
}