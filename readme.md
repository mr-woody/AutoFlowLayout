# AutoFlowLayout
### 一、AutoFlowLayout应用场景

流式布局，在很多标签类的场景中可以用的；而网格布局在分类中以及自拍九宫格等场景很常见。

### 二、AutoFlowLayout实现效果

先介绍下自己撸的这个控件的功能及效果。


#### 1.功能

**流式布局**
- 自动换行
- 支持单选/多选（需要借助辅助类com.woodys.flowlayout.select.helper.AbsSelectHelper类的实现类：MultipleSelectionHelper和RadioSelectionHelper）
- 自定义width、height
- 自定义上下间距
- 支持子控件padding、margin设置
- 支持添加/删除子View

**网格布局**
- 支持单选/多选（需要借助辅助类com.woodys.flowlayout.select.helper.AbsSelectHelper类的实现类：MultipleSelectionHelper和RadioSelectionHelper）
- 自定义width、height
- 自定义上下间距
- 支持子控件padding、margin设置
- 支持横向最小间距收缩
- 支持添加/删除子View


#### 2.示例介绍
1. 配器Adapter进行数据操作
2. 配器Adapter进行数据操作，实现复选
3. 不通过适配器Adapter进行数据操作，只是为了开发验证bug使用


### 演示下载
[*Sample Apk*](/apk/app-debug.apk)

### 三、AutoFlowLayout使用

#### 1.添加依赖

①.在项目的 build.gradle 文件中添加
```
allprojects {
		repositories {
			...
			 maven { url 'https://jitpack.io' }
		}
	}
```
②.在build.gradle 文件中添加依赖
```
dependencies {
     implementation "com.github.mr-woody:AutoFlowLayout:1.0.0"
}
```


#### 2.属性说明
> 样式设定

下表是自定义的属性说明，可在xml中声明，同时有对应的set方法，可在代码中动态添加。

| 样式声明 | 示例 | 说明 |
| ------ | ------ | ------ |
| name="afl_fixRaw" format="integer" | app:afl_fixRaw="4" | 表示网格样式的列数（一行多少列） |
| name="afl_itemWidth" format="dimension" | app:afl_itemWidth="50dp" | 子view的宽度，假如设置为小于0，表示自己进行计算，大于0使用你设置的值，默认为0 |
| name="afl_itemHeight" format="dimension" | app:afl_itemHeight="50dp" | 子view的高度，假如设置为小于0，表示自己进行计算，大于0使用你设置的值，默认为0（这里高度必须都一样，不然布局出错不负责） |
| name="afl_itemHorizontalSpacing" format="dimension" | app:afl_itemHorizontalSpacing="12dp" | 横向内间距 |
| name="afl_itemMinHorizontalSpacing" format="dimension" | app:afl_itemMinHorizontalSpacing="5dp" | 支持横向最小内间距收缩 |
| name="afl_itemVerticalSpacing" format="dimension" | app:afl_itemVerticalSpacing="12dp" | 纵向内间距 |
| name="afl_itemSizeMode" format="enum" | app:layout_choiceMode="item_width" | 当前子view的布局排版模式，需要结合网格布局和流式布局结合使用 |


<attr name="afl_fixRaw" format="integer" />
        <attr name="afl_itemWidth" format="dimension" />
        <attr name="afl_itemHeight" format="dimension"/>
        <attr name="afl_itemHorizontalSpacing" format="dimension" />
        <attr name="afl_itemMinHorizontalSpacing" format="dimension" />
        <attr name="afl_itemVerticalSpacing" format="dimension" />
        <attr name="afl_itemSizeMode" format="enum">
            <enum name="item_width" value="0x00" />
            <enum name="item_padding" value="0x01" />
        </attr>



#### 3.使用示例
**布局**

属性自己设定

```
<com.woodys.flowlayout.AutoFlowLayout
    android:id="@+id/gl_layout"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_marginLeft="20dp"
    android:background="@color/red"
    android:paddingLeft="10dp"
    android:paddingRight="10dp"
    android:paddingTop="10dp"
    android:paddingBottom="10dp"/>
```
**代码设置数据**
```
val flowBaseAdapter = object : FlowBaseAdapter<String>(mData.toList()) {
    override fun getView(context: Context, parent: ViewGroup, position: Int): View {
        return LayoutInflater.from(context).inflate(R.layout.item_text_view, null)
    }

    override fun bindView(view: View, position: Int) {
        val item = getItem(position)
        val textView = view.findViewById<TextView>(R.id.tv_attr_tag)
        textView.text = item
    }
}
layout.setAdapter(flowBaseAdapter)

```
与ListView,GridView使用方式一样，实现FlowBaseAdapter即可。

#### 4.注意事项

> flowBaseAdapter里面的getView 获取的布局里面，暂时不支持子view里面设置如下属性：

1. 暂时不支持android:layout_width="match_parent"  或者设置定植，比如：100dp，但是可以设置padding或者margin，以及通过在外层AutoFlowLayout里面设置 auto:afl_itemWidth="具体高度值"
2. 暂时不支持android:layout_height="match_parent"  或者设置定植，比如：100dp，但是可以设置padding或者margin，以及通过在外层AutoFlowLayout里面设置 auto:afl_itemHeight="具体高度值"
3. **特殊** ：暂时不支持ScrollView和RelativeLayout高度设置，就算设置成 android:layout_height="wrap_content"控件也没有办法确定其高度，需要通过在外层AutoFlowLayout里面设置 auto:afl_itemHeight="具体高度值"


> FlowBaseAdapter使用注意事项

> 错误的使用方式

![错误的使用方式](/image/image_01.png)

> 正确的使用方式

![正确的使用方式](/image/image_02.png)

**解答** ：

1. FlowBaseAdapter的swapItemsNotify(items: List<T?>?)方法不是同步方法
```

/**
 * 替换数据
 */
open fun swapItemsNotify(items: List<T?>?) {
    if (0 < getItemCount()) {
        clearItems()
    }
    addItemsNotify(items)
}

```
2. DataSetObservable类的 notifyChanged 方法是同步方法
```
public void notifyChanged() {
    synchronized(mObservers) {
        // since onChanged() is implemented by the app, it could do anything, including
        // removing itself from {@link mObservers} - and that could cause problems if
        // an iterator is used on the ArrayList {@link mObservers}.
        // to avoid such problems, just march thru the list in the reverse order.
        for (int i = mObservers.size() - 1; i >= 0; i--) {
            mObservers.get(i).onChanged();
        }
    }
}


```

而对应的AutoFlowLayout类中的notifyChanged方法也是同步代码，具体代码如下

```

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

```

假如在刚开始执行notifyChanged时，外面又执行了FlowBaseAdapter的swapItemsNotify方法，这个时候外面的it[position]获取的是新值，而通过notifyChanged方法执行的bindView因为执行同步方法，所以先执行老数据操作

这个时候就会出现position下标不一致的问题





### 其他文档
* [ChangeLog](/document/CHANGE_LOG.MD)
