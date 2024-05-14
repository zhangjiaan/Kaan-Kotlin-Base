package com.kaan.myapplication.usecase

import com.kaan.myapplication.base.BaseUseCase
import com.kaan.myapplication.data.BaseResponse
import com.kaan.myapplication.data.LoginBean
import com.kaan.myapplication.http.CommonRepository
import com.kaan.myapplication.http.Status

/**
 * File Description
 *
 * @author zhangja
 * @version 1.0
 * @since 2024/2/2
 */
class UserManagementUseCase(private val commonRepository: CommonRepository) {

    suspend fun login(
        user: String,
        password: String,
        onSuccess: (LoginBean) -> Unit,
        onFailure: (String) -> Unit
    ): BaseResponse<LoginBean> {
        val response = commonRepository.login(user, password)
        if (response.return_code == Status.SUCCESS && response.return_data.result) {
            // 登录成功的处理逻辑
            onSuccess(response.return_data) // 调用成功回调
        } else {
            // 登录失败的处理逻辑
            onFailure("") // 调用失败回调
        }
        return response // 还是返回Repository的响应，以便于有需要时可以直接处理原始响应
    }

    suspend fun logout(): Boolean {
//        val response = commonRepository.login(user, password)
        return true
    }

}

