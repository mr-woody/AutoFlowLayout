package com.woodys.sampletamplate.sample

import android.app.Application
import com.woodys.sampletamplate.configurtion.Document
import com.woodys.sampletamplate.configurtion.TemplateConfiguration
import com.woodys.sampletamplate.sample.activity.*

/**
 * @author Created by woodys
 * @date 2019-05-09 14:45
 * @email yuetao.315@qq.com
 * 演示程序入口
 */
@Document("readme.md")
class SampleApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        //初始化演示模板
        initTemplate()
    }

    /**
     * 初始化演示模板
     */
    private fun initTemplate() {
        TemplateConfiguration.init(this) {
            item {
                id = 1
                title = "AutoFlowLayout演示1(高度必须一样，不一样的话，不建议使用这个控件)"
                desc = "配器Adapter进行数据操作"
                clazz = Sample1Activity::class.java
            }
            item {
                id = 2
                title = "AutoFlowLayout演示2(高度必须一样，不一样的话，不建议使用这个控件)"
                desc = "配器Adapter进行数据操作，实现复选"
                clazz = Sample2Activity::class.java
            }

            item {
                id = 3
                title = "AutoFlowLayout演示3(高度必须一样，不一样的话，不建议使用这个控件)"
                desc = "不通过适配器Adapter进行数据操作，只是为了开发验证bug使用"
                clazz = Sample3Activity::class.java

            }

            item {
                id = 4
                title = "AutoFlowLayout演示4"
                desc = "开发验证bug使用"
                clazz = Sample4Activity::class.java

            }
            item {
                id = 5
                title = "AutoFlowLayout演示5"
                desc = "开发验证bug使用"
                clazz = Sample5Activity::class.java

            }
        }
    }
}