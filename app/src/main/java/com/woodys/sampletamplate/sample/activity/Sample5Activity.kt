package com.woodys.sampletamplate.sample.activity

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.TextView
import com.woodys.flowlayout.AutoFlowLayout
import com.woodys.flowlayout.adapter.FlowBaseAdapter
import com.woodys.sampletamplate.ToolBarActivity
import com.woodys.sampletamplate.sample.R
import com.woodys.sampletamplate.view.RadioLayout
import com.woodys.sampletamplate.view.SeekLayout
import java.util.*

/**
 * @author :Created by yeutao
 * @date 2019-07-30 16:34
 * @email yuetao.315@qq.com
 * 演示5 见配置
 * @see com.woodys.sampletamplate.sample.SampleApplication.onCreate
 */
class Sample5Activity : ToolBarActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo4)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        val layout = findViewById(R.id.gl_layout) as AutoFlowLayout


        val colorItems = resources.getIntArray(R.array.color_items)
        val mData = arrayOf("学校的名字学校的名字", "C++", "学校的名字学校的名字学校的名字学校的名字", "JavaScript", "学校的名字学校的名字", "学校的名字", "学校的名字学校的名字", "学校的名字学校校的校的校的的名字")
        val flowBaseAdapter = object : FlowBaseAdapter<String>(mData.toList()) {
            override fun getView(context: Context, parent: ViewGroup, position: Int): View {
                return LayoutInflater.from(context).inflate(R.layout.item_5_text_view, null)
            }

            override fun bindView(view: View, position: Int) {
                val item = getItem(position)
                val textView1 = view.findViewById<TextView>(R.id.tv_classname)
                textView1.setBackgroundColor(colorItems[Random().nextInt(colorItems.size)])
                textView1.text = item

                val textView2 = view.findViewById<TextView>(R.id.tv_classcode)
                textView2.setBackgroundColor(colorItems[Random().nextInt(colorItems.size)])
                textView2.text = item + "2"
            }
        }
        layout.setAdapter(flowBaseAdapter)


        val rawSeek = findViewById(R.id.sl_raw) as SeekLayout
        rawSeek.setOnSeekProgressChangeListener(object : SeekLayout.OnSeekProgressChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int) {
                layout.setFixRaw(progress)
            }
        })
        val widthSeek = findViewById(R.id.sl_width) as SeekLayout
        widthSeek.setOnSeekProgressChangeListener(object : SeekLayout.OnSeekProgressChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int) {
                layout.setItemWidth(progress)
            }
        })
        val heightSeek = findViewById(R.id.sl_height) as SeekLayout
        heightSeek.setOnSeekProgressChangeListener(object : SeekLayout.OnSeekProgressChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int) {
                layout.setItemHeight(progress)
            }
        })
        val horizontalPaddingSeek = findViewById(R.id.sl_horizontal_padding) as SeekLayout
        horizontalPaddingSeek.setOnSeekProgressChangeListener(object : SeekLayout.OnSeekProgressChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int) {
                layout.setItemHorizontalSpacing(progress)
            }
        })
        val verticalPaddingSeek = findViewById(R.id.sl_vertical_padding) as SeekLayout
        verticalPaddingSeek.setOnSeekProgressChangeListener(object : SeekLayout.OnSeekProgressChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int) {
                layout.setItemVerticalSpacing(progress)
            }
        })
        val typeLayout = findViewById(R.id.rl_type) as RadioLayout
        typeLayout.check(0)
        typeLayout.setOnCheckedListener(object : RadioLayout.OnCheckedListener {
            override fun onChecked(v: View, position: Int, isChecked: Boolean) {
                layout.setItemSizeMode(position)
            }
        })

        findViewById<View>(R.id.btn_add).setOnClickListener(View.OnClickListener {
            val childText = "Text" + layout.childCount
            flowBaseAdapter.addItemNotify(childText)


        })
        findViewById<View>(R.id.btn_last).setOnClickListener(View.OnClickListener {
            val childCount = layout.childCount
            if (0 < childCount) {
                flowBaseAdapter.removeItemNotify(childCount - 1)
            }
        })
    }

}
