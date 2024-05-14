package com.kaan.myapplication.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.kaan.myapplication.utils.autoCleared
import kotlinx.coroutines.launch


abstract class BaseFragment<V : ViewDataBinding, VM : BaseViewModel> : Fragment() {

    private var binding by autoCleared<V>()
    private var viewModel by autoCleared<VM>()

    private var root: View? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        if (root != null) return root

        binding = DataBindingUtil.inflate(
            inflater,
            initContentView(inflater, container, savedInstanceState),
            container,
            false
        )

        root = binding.root

        viewModel = ViewModelProvider(this)[initViewModel()]
        binding.lifecycleOwner = this
        binding.setVariable(initVariableId(), viewModel)

        initView(root!!)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                initBaseLiveDataCallBack()
                initVVMObserver()
                initData()
            }
        }

        return root

    }

    private fun initBaseLiveDataCallBack() {
//        val baseActivity = activity as BaseActivity<*, *>
    }

    /**
     * 初始化V-VM之间的观察者回调
     */
    abstract fun initVVMObserver()

    /**
     * 初始化部分点击时间或者 view事件
     */
    abstract fun initView(view: View)


    /**
     * 初始化页面数据
     */
    open fun initData() {
        viewModel.initData()
    }

    /**
     * 初始化根布局
     *
     * @return 布局layout的id
     */
    abstract fun initContentView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): Int

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
}