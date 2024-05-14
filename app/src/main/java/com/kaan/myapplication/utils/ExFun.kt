package com.kaan.myapplication.utils

import com.kaan.myapplication.data.BaseResponse
import com.kaan.myapplication.base.BaseViewModel
import com.kaan.myapplication.http.Status

/**
 * File Description
 *
 * @author zhangja
 * @version 1.0
 * @since 2024/2/1
 */
fun <T> businessHandler(
    data: BaseResponse<T>,
    doingSom: (() -> Unit)? = null
): T {
    LogUtils.v(data)
    when (data.return_code) {
        Status.SUCCESS -> doingSom?.invoke()
//        else -> ToastUtils.showShort(data.return_msg)
    }
    return data.return_data
}
