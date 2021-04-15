package com.woodys.sampletamplate.sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

/**
 * @author :Created by woodys
 * @date 2019-05-09 15:44
 * @email yuetao.315@qq.com
 * 注册此类只是作为演示入口,不论写不写contentView,都不会影响演示列表
 * @see com.woodys.sample.library.lifecycle.SampleActivityLifecycleCallback
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
