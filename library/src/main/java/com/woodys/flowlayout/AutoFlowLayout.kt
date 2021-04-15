package com.woodys.flowlayout

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import com.woodys.flowlayout.adapter.FlowBaseAdapter
import com.woodys.flowlayout.callback.OnItemClickListener
import com.woodys.flowlayout.select.Selectable
import android.database.DataSetObserver
import com.woodys.flowlayout.utils.Debugger

/**
 * Created by woodys on 19/7/29.
 * 一个组控件组
 */
class AutoFlowLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : ViewGroup(context, attrs, defStyleAttr) {
    companion object {
        const val ITEM_WIDTH = 0x00
        const val ITEM_PADDING = 0x01
    }

    private var fixRaw: Int = 0//固定列表
    private var itemWidth: Int = 0//指定条目宽
    private var fixItemWidth: Int = 0//保留的横向间距,不会用作计算
    private var itemHeight: Int = 0//指定条目高
    private var horizontalSpacing: Int = 0//横向间距
    private var fixHorizontalSpacing: Int = 0//保留的横向间距,不会用作计算修改

    private var minHorizontalSpacing: Int = 0//横向间距,表示最小压缩距离

    private var verticalSpacing: Int = 0//纵向间距
    private var itemSizeMode: Int = 0
    private var listener: OnItemClickListener? = null

    private var adapter: FlowBaseAdapter<*>?=null

    /**
     * 设置数据监听变化
     */
    private var mDataObserver:DataSetObserver?=null

    init {
        context.obtainStyledAttributes(attrs, R.styleable.AutoFlowLayout).apply {
            setFixRaw(getInteger(R.styleable.AutoFlowLayout_afl_fixRaw, 0))
            setItemWidth(getDimension(R.styleable.AutoFlowLayout_afl_itemWidth, 0f).toInt())
            setItemHeight(getLayoutDimension(R.styleable.AutoFlowLayout_afl_itemHeight, 0))
            setItemHorizontalSpacing(getDimension(R.styleable.AutoFlowLayout_afl_itemHorizontalSpacing, 0f).toInt())
            setItemMinHorizontalSpacing(getDimension(R.styleable.AutoFlowLayout_afl_itemMinHorizontalSpacing, -1f).toInt())
            setItemVerticalSpacing(getDimension(R.styleable.AutoFlowLayout_afl_itemVerticalSpacing, 0f).toInt())
            setItemSizeMode(getInt(R.styleable.AutoFlowLayout_afl_itemSizeMode, ITEM_WIDTH))
            recycle()
        }
    }


    /**
     * Log开关。建议测试环境开启，线上环境应该关闭。
     * @param isEnable
     */
    fun setEnableLog(isEnable: Boolean) {
        Debugger.setEnableLog(isEnable)
    }


    fun <E> setAdapter(adapter: FlowBaseAdapter<E>) {
        removeAllViews()
        if (null != adapter) {
            if(null != mDataObserver){
                mDataObserver?.let { adapter.unregisterDataSetObserver(it) }
            }
            if(adapter is Selectable){
                adapter.onInit(this)
            }
            val itemCount = adapter.getItemCount()
            (0 until itemCount).forEach { position ->
                var view = adapter.getView(context, this, position)
                adapter.bindView(view, position)
                addView(view,position)
                //当adapter实现了Selectable接口，说明其实使用选择器
                if(adapter is Selectable){
                    adapter.onBindSelectView(this,view,position)
                }
            }

            if(null == mDataObserver) {
                mDataObserver = object : DataSetObserver() {
                    override fun onChanged() {
                        notifyChanged()
                    }

                    override fun onInvalidated() {
                        requestLayout()
                    }
                }
            }
            mDataObserver?.let { adapter.registerDataSetObserver(it) }
        }
        this.adapter = adapter

    }


    /**
     * 当数据适配器数据改变时执行
     */
    fun notifyChanged(){
        removeAllViews()
        adapter?.let {
            if(it is Selectable){
                it.onInit(this)
            }
            val itemCount = it.getItemCount()
            (0 until itemCount).forEach { position ->
                var view = it.getView(context, this, position)
                it.bindView(view, position)
                addView(view, position)
                //当adapter实现了Selectable接口，说明其实使用选择器
                if (it is Selectable) {
                    it.onBindSelectView(this,view, position)
                }
            }
        }
    }


    /**
     * 获得选择数据适配器对象
     */
    fun <E> getAdapter(): FlowBaseAdapter<E>?{
        return adapter as? FlowBaseAdapter<E>?
    }


    /**
     * 设置条目尺寸适应模式
     * item_width:宽度为主
     * horizontal_padding:横向边矩为主
     *
     * @param mode
     */
    fun setItemSizeMode(mode: Int) {
        itemSizeMode = mode
        requestLayout()
    }


    fun setFixRaw(fixRaw: Int) {
        this.fixRaw = fixRaw
        requestLayout()
    }


    fun setItemVerticalSpacing(verticalSpacing: Int) {
        this.verticalSpacing = verticalSpacing
        requestLayout()
    }

    fun setItemHorizontalSpacing(horizontalSpacing: Int) {
        this.horizontalSpacing = horizontalSpacing
        this.fixHorizontalSpacing = horizontalSpacing
        //当没有设置最小间距的时候，使用horizontalSpacing间距值
        this.minHorizontalSpacing = horizontalSpacing
        requestLayout()
    }

    fun setItemMinHorizontalSpacing(minHorizontalSpacing: Int) {
        if(minHorizontalSpacing>=0){
            this.minHorizontalSpacing = minHorizontalSpacing
            requestLayout()
        }
    }

    fun setItemWidth(itemWidth: Int) {
        this.itemWidth = itemWidth
        this.fixItemWidth = itemWidth
        requestLayout()
    }

    fun setItemHeight(itemHeight: Int) {
        this.itemHeight = itemHeight
        requestLayout()
    }


    @SuppressLint("Range")
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val childCount = childCount
        var childWidth: Int = 0
        val paddingLeft = paddingLeft
        val paddingTop = paddingTop
        val paddingRight = paddingRight
        val paddingBottom = paddingBottom
        val width = MeasureSpec.getSize(widthMeasureSpec) - paddingLeft - paddingRight
        var row = 1
        if (0 < fixRaw) {
            //设定raw,不设定width,则动态计算,否则,而分个数内边距
            if (0 < itemWidth) {
                //动态改变横向间距
                when (itemSizeMode) {
                    ITEM_WIDTH -> {
                        if (width < fixItemWidth * fixRaw) {
                            //缩小width宽度,以适应fixRaw
                            itemWidth = (width - (fixRaw - 1) * fixHorizontalSpacing) / fixRaw
                        } else {
                            itemWidth = fixItemWidth
                        }
                        horizontalSpacing = (width - fixRaw * itemWidth) / if (fixRaw - 1 > 0) fixRaw - 1 else 1
                    }
                    ITEM_PADDING -> {
                        //当固定尺寸大小,以及固定边距超出宽时
                        //缩小width宽度,以适应fixRaw
                        horizontalSpacing = fixHorizontalSpacing
                        itemWidth = (width - (fixRaw - 1) * fixHorizontalSpacing) / fixRaw
                    }
                }
                childWidth = itemWidth
            } else {
                horizontalSpacing = fixHorizontalSpacing
                childWidth = (width - (fixRaw - 1) * fixHorizontalSpacing) / fixRaw
            }
            row = if (childCount > 0) if (0 == childCount % fixRaw) childCount / fixRaw else childCount / fixRaw + 1 else 1
        } else {
            when (itemSizeMode) {
                ITEM_WIDTH -> {
                    childWidth = fixItemWidth
                    //不设定raw,则取width
                    var raw = width / if (childWidth > 0) childWidth else 1
                    if (0 < childCount && childCount < raw) {
                        raw = childCount
                    }
                    if (childWidth <= 0) {
                        horizontalSpacing = fixHorizontalSpacing
                    } else {
                        horizontalSpacing = (width - childWidth * raw) / if (raw - 1 > 0) raw - 1 else 1
                    }
                    row = if (childCount > 0) if (0 == childCount % raw) childCount / raw else childCount / raw + 1 else 1
                }
                ITEM_PADDING -> {
                    childWidth = itemWidth
                    horizontalSpacing = fixHorizontalSpacing
                }
            }
        }


        var childHeight = 0
        var cellHeightSpec = heightMeasureSpec
        if (itemHeight > 0 ) {
            cellHeightSpec = MeasureSpec.makeMeasureSpec(itemHeight, MeasureSpec.EXACTLY)
        }else{
            cellHeightSpec = MeasureSpec.makeMeasureSpec(LayoutParams.WRAP_CONTENT, MeasureSpec.AT_MOST)
        }

        var cellWidthSpec = widthMeasureSpec
        if(childWidth > 0){
            cellWidthSpec = MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.EXACTLY)
        }else{
            cellWidthSpec = MeasureSpec.makeMeasureSpec(LayoutParams.WRAP_CONTENT, MeasureSpec.AT_MOST)
        }

        //流式布局的测量模式
        var rowNumber = 1
        var rawNumber = 0
        var lineWidth = 0
        var previousHorizontalSpacing = horizontalSpacing

        var index = 0
        // 遍历每个子元素
        while (index < childCount) {
            val child = getChildAt(index)
            var marginParams: MarginLayoutParams? = null
            val params = child.layoutParams
            //获取view的margin设置参数
            if (params is MarginLayoutParams) {
                marginParams = params
            } else {
                //不存在时创建一个新的参数,基于View本身原有的布局参数对象
                marginParams = MarginLayoutParams(params)
            }
            // 测量每一个child的宽和高
            child.measure(cellWidthSpec, cellHeightSpec)
            // 当前child最大宽度
            val maxChildWidth = (width  - paddingLeft - marginParams.leftMargin - paddingRight - marginParams.rightMargin)
            // child宽度超过最大宽度，需要按照最大宽度进行宽度计算，解决TextView内容超出一行，不显示省略的符号
            if(Math.min(child.measuredWidth,width)>maxChildWidth){
                child.measure(MeasureSpec.makeMeasureSpec(maxChildWidth, MeasureSpec.EXACTLY), cellHeightSpec)
            }
            // 当前子空间实际占据的高度
            childHeight = (child.measuredHeight + marginParams.topMargin + marginParams.bottomMargin)

            if (0 >= fixRaw) {
                rawNumber++
                // 当前子空间实际占据的宽度
                val measuredChildWidth = (child.measuredWidth  + marginParams.leftMargin + marginParams.rightMargin)
                lineWidth +=  measuredChildWidth
                if((width < lineWidth + (rawNumber - 1) * horizontalSpacing) && (width >= lineWidth + (rawNumber - 1) * minHorizontalSpacing)){
                    while (width < lineWidth + (rawNumber - 1) * horizontalSpacing){
                        horizontalSpacing--
                    }
                    index -= (rawNumber-1)
                    rawNumber = 1
                    lineWidth =  getChildAt(index).measuredWidth + marginParams.leftMargin + marginParams.rightMargin
                }else if((width >= lineWidth + (rawNumber - 1) * minHorizontalSpacing) && (width >= lineWidth + (rawNumber - 1) * horizontalSpacing)){

                }else if(width <= lineWidth + (rawNumber - 1) * previousHorizontalSpacing) {
                    horizontalSpacing = previousHorizontalSpacing
                    rowNumber++
                    rawNumber = 1
                    lineWidth = measuredChildWidth
                }
                //循环结束以后，可以取到正确的行数
                row = rowNumber
            }
            //计数器，自增
            index++
        }


        //计算总高度
        var totalHeight = 0
        if (0 < childCount) {
            totalHeight = paddingTop + paddingBottom + childHeight * row + verticalSpacing * (row - 1)
        }
        val minHeight = suggestedMinimumHeight
        if (totalHeight < minHeight) {
            totalHeight = minHeight
        }
        setMeasuredDimension(View.resolveSize(width + paddingLeft + paddingRight, widthMeasureSpec), View.resolveSize(totalHeight, heightMeasureSpec))
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        //流式布局的测量模式
        var rowNumber = 1
        var rawNumber = 0
        var lineWidth = paddingLeft + paddingRight

        var index = 0
        //宽度动态改变，每一行可能都不一样
        var dynamicWidth = 0
        var previousWidth = 0
        var previousHorizontalSpacing = horizontalSpacing
        while (index < childCount) {
            val child = getChildAt(index)
            //三种状态,靠左==leftPadding/中间horizontalPadding/靠右==rightPadding
            val itemWidth = child.measuredWidth
            val itemHeight = child.measuredHeight

            var marginParams: MarginLayoutParams? = null
            val params = child.layoutParams
            //获取view的margin设置参数
            if (params is MarginLayoutParams) {
                marginParams = params
            } else {
                //不存在时创建一个新的参数,基于View本身原有的布局参数对象
                marginParams = MarginLayoutParams(params)
            }
            //列数自增
            rawNumber++
            if (0 < fixRaw) {
                //当设置了raw时,让条目居中,否则,条目靠左
                if (rawNumber > fixRaw) {
                    rawNumber = 1
                    rowNumber ++
                }
                dynamicWidth = (rawNumber-1) * itemWidth
            }else{
                // 当前子空间实际占据的宽度
                val measuredChildWidth = (itemWidth + marginParams.leftMargin + marginParams.rightMargin)
                lineWidth += measuredChildWidth
                dynamicWidth += previousWidth
                Debugger.d("0==> : index=${index} horizontalSpacing=$horizontalSpacing,reslut=${lineWidth + (rawNumber - 1) * horizontalSpacing}")

                if((width <  lineWidth + (rawNumber - 1) * horizontalSpacing) && (width > lineWidth + (rawNumber - 1) * minHorizontalSpacing)){
                    while (width < lineWidth + (rawNumber - 1) * horizontalSpacing){
                        horizontalSpacing--
                    }
                    index -= (rawNumber-1)
                    rawNumber = 1
                    lineWidth =  paddingLeft +  measuredChildWidth + paddingRight
                    dynamicWidth = 0
                    previousWidth = getChildAt(index).measuredWidth

                    Debugger.e("1==> : index=${index} horizontalSpacing=$horizontalSpacing")
                }else if((width >= lineWidth + (rawNumber - 1) * minHorizontalSpacing) && (width >= lineWidth + (rawNumber - 1) * horizontalSpacing)){
                    Debugger.e("2==> : index=${index} horizontalSpacing=$horizontalSpacing")
                    //记录上一次的控件width
                    previousWidth = itemWidth
                }else if(width < lineWidth + (rawNumber - 1) * previousHorizontalSpacing) {
                    horizontalSpacing = previousHorizontalSpacing
                    rowNumber++
                    rawNumber = 1
                    lineWidth = paddingLeft +  measuredChildWidth + paddingRight
                    dynamicWidth = 0
                    previousWidth = itemWidth
                    Debugger.d("3==> : index=${index} horizontalSpacing=$horizontalSpacing")
                }else{
                    //记录上一次的控件width
                    previousWidth = itemWidth
                }

            }

            // 当前child最大宽度
            val maxChildWidth = (width  - paddingLeft - marginParams.leftMargin - paddingRight - marginParams.rightMargin)

            val left = paddingLeft + rawNumber * marginParams.leftMargin + (rawNumber-1) * horizontalSpacing + (rowNumber-1) * marginParams.rightMargin + dynamicWidth
            val right = if(left + itemWidth > width) maxChildWidth else left + itemWidth
            val top = paddingTop + rowNumber * marginParams.topMargin + (rowNumber-1) * verticalSpacing + (rowNumber-1) * marginParams.bottomMargin + (rowNumber-1) * itemHeight
            val bottom = top + itemHeight
            Debugger.d("child.layout => : width=$width, index=${index} => left=${left},right=${right},top=${top},bottom=${bottom},itemWidth=$itemWidth,itemHeight=$itemHeight,horizontalSpacing=$horizontalSpacing")
            child.layout(left, top, right, bottom)
            //计数器，自增
            index++
        }
    }

    override fun addView(child: View, index: Int, params: LayoutParams) {
        super.addView(child, index, params)
        child.setOnClickListener { v ->
            val indexOfChild = indexOfChild(v)
            if(null!=adapter && adapter is Selectable){
                val selectable = adapter as Selectable
                selectable.onSelectChanged(this,v,indexOfChild)
            }
            if (null != listener) {
                listener?.onItemClick(v, indexOfChild)
            }
        }
    }

    override fun generateLayoutParams(attrs: AttributeSet): LayoutParams {
        return MarginLayoutParams(context, attrs)
    }

    override fun generateLayoutParams(p: LayoutParams): LayoutParams {
        return MarginLayoutParams(p)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }
}