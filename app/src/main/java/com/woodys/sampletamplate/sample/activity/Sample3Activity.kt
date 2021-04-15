package com.woodys.sampletamplate.sample.activity

import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.TextView
import com.woodys.flowlayout.AutoFlowLayout
import com.woodys.sampletamplate.ToolBarActivity
import com.woodys.sampletamplate.sample.R
import com.woodys.sampletamplate.view.RadioLayout
import com.woodys.sampletamplate.view.SeekLayout
import java.util.*

/**
 * @author :Created by yeutao
 * @date 2019-07-30 14:56
 * @email yuetao.315@qq.com
 * 演示3 见配置
 * @see com.woodys.sampletamplate.sample.SampleApplication.onCreate
 */
class Sample3Activity : ToolBarActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo3)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)


        val layout = findViewById(R.id.gl_layout) as AutoFlowLayout
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

        val colorItems = resources.getIntArray(R.array.color_items)
        findViewById<View>(R.id.btn_add).setOnClickListener(View.OnClickListener {
            val childView = TextView(this)
            childView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
            val childCount = layout.getChildCount()
            val text = "Text"
            childView.text = text + childCount
            childView.setPadding(50, 0, 4, 0)
            childView.setBackgroundColor(colorItems[Random().nextInt(colorItems.size)])
            childView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT)
            layout.addView(childView)
        })
        findViewById<View>(R.id.btn_last).setOnClickListener(View.OnClickListener {
            val childCount = layout.getChildCount()
            if (0 < childCount) {
                layout.removeViewAt(childCount - 1)
            }
        })
    }

}
