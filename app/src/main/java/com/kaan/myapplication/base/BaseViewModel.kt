package com.kaan.myapplication.base

import android.app.Application
import android.os.Bundle
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.kaan.myapplication.http.CommonRepository
import com.kaan.myapplication.utils.SingleLiveEvent
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject

/**
 * Description: Base for all ViewModels
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
abstract class BaseViewModel(application: Application) : AndroidViewModel(application){
    //上下文
    val mContext = application
    //结束页面
    val finishStatus = SingleLiveEvent<Boolean>()
    //跳转页面
    val startActivityStatus = SingleLiveEvent<Map<String, Any>>()
    val commonRepository: CommonRepository by inject(CommonRepository::class.java)

    abstract fun initData()

    open fun <T> launch(block: suspend () -> T) = viewModelScope.launch {
        try {
            block()
        } catch (t: Throwable) {
            t.printStackTrace()
        }
    }

    /**
     * 跳转页面
     *
     * @param clz 所跳转的目的Activity类
     */
    fun startActivity(clz: Class<*>) {
        startActivity(clz, null)
    }

    /**
     * 跳转页面
     *
     * @param clz    所跳转的目的Activity类
     * @param bundle 跳转所携带的信息
     */
    fun startActivity(clz: Class<*>, bundle: Bundle? = null) {
        val params = HashMap<String, Any>()
        params["CLASS"] = clz
        if (bundle != null) {
            params["BUNDLE"] = bundle
        }
        startActivityStatus.postValue(params)
    }
    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}