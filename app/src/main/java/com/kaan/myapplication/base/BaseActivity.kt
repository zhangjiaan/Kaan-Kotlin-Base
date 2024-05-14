package com.kaan.myapplication.base

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.kaan.myapplication.utils.ActivityStackManager
import com.kaan.myapplication.utils.PermissionUtils

/**
 * Description:Base for all Activity
 *
 * Features:
 * -
 *
 * Version History:
 * - 1.0 (2023/12/16): Initial release
 *
 * Author: Kaan.cheung (Zhangja)
 * Email: Kaan.cheung@outlook.com
 */
abstract class BaseActivity<V : ViewDataBinding, VM : BaseViewModel> : AppCompatActivity() {
    private var binding: V? = null
    private lateinit var viewModel: VM
    protected lateinit var mContext: Context

    @SuppressLint("PrivateResource")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this
        ActivityStackManager.instance.addActivity(this)
        initViewDataBinding(savedInstanceState)
        //初始化数据
        initData()
        // View 初始化
        initView()
        // Click 初始化
        initClickEvent()
        //初始化 V <--> VM LiveData 改变 V
        initVVMObserver()
        //注册基本的事件回调
        initBaseLiveDataCallBack()
    }

    private fun initBaseLiveDataCallBack() {

        viewModel.finishStatus.observe(this) {
            finish()
        }

        viewModel.startActivityStatus.observe(this) {
            val clz = it["CLASS"] as Class<*>
            if (null == it["BUNDLE"]) {
                startActivity(clz, null)
            } else {
                startActivity(clz, it["BUNDLE"] as Bundle)
            }
        }
    }

    private fun initViewDataBinding(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this, initContentView(savedInstanceState))

        viewModel = ViewModelProvider(this)[initViewModel()]
        binding!!.setVariable(initVariableId(), viewModel)
        binding!!.lifecycleOwner = this
    }

    /**
     * 初始化V-VM之间的观察者回调
     */
    open fun initVVMObserver() {}

    /**
     * 初始化页面数据
     */
    open fun initData() {
        viewModel.initData()
    }

    /**
     * 初始化View
     */
    abstract fun initView()

    /**
     * 初始化View的点击事件
     */
    open fun initClickEvent() {}

    /**
     * 初始化根布局
     *
     * @return 布局layout的id
     */
    abstract fun initContentView(savedInstanceState: Bundle?): Int

    /**
     * 初始化ViewModel的id
     *
     * @return BR的id
     */
    abstract fun initVariableId(): Int

    /**
     * initialize The ViewModel
     *
     * @return 继承BaseViewModel的ViewModel
     */
    abstract fun initViewModel(): Class<VM>

    private fun startActivity(clz: Class<*>, bundle: Bundle? = null) {
        val intent = Intent(this, clz)
        if (bundle != null) {
            intent.putExtras(bundle)
        }
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding!!.unbind()
        binding = null
        ActivityStackManager.instance.removeActivity(this)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        PermissionUtils.with(this).onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

}