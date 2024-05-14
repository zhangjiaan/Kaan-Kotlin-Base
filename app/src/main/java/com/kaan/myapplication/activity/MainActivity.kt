package com.kaan.myapplication.activity

import android.Manifest
import android.os.Bundle
import com.kaan.myapplication.BR
import com.kaan.myapplication.R
import com.kaan.myapplication.base.BaseActivity
import com.kaan.myapplication.databinding.ActivityMainBinding
import com.kaan.myapplication.utils.LogUtils
import com.kaan.myapplication.utils.PermissionUtils
import com.kaan.myapplication.viewmodel.MainViewModel

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    override fun initView() {
        LogUtils.d()
    }

    override fun initData() {
        super.initData()
    }

    private fun requestPermission() {
        val permissionsList = arrayListOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        LogUtils.d()
        PermissionUtils.with(this)
            .permission(*permissionsList.toTypedArray())
            .callback(object : PermissionUtils.SingleCallback {
                override fun callback(
                    isAllGranted: Boolean,
                    granted: List<String>,
                    deniedForever: List<String>,
                    denied: List<String>
                ) {
                    if (isAllGranted) {
                        // 所有请求的权限都被授予
                    } else {
                        // 处理被拒绝的权限
                    }
                }
            }).request()
    }

    override fun initContentView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_main
    }

    override fun initVariableId(): Int {
        return BR.mainViewModel
    }

    override fun initViewModel(): Class<MainViewModel> {
        return MainViewModel::class.java
    }
}