package com.kaan.myapplication.viewmodel

import android.app.Application
import com.kaan.myapplication.base.BaseViewModel
import com.kaan.myapplication.usecase.UserManagementUseCase
import org.koin.java.KoinJavaComponent.inject

/**
 * Description: Demo.
 *
 *
 * Author: Kaan.cheung (Zhangja)
 * Email: Kaan.cheung@outlook.com
 */
class MainViewModel(application: Application) : BaseViewModel(application) {

    private val userManagementUseCase: UserManagementUseCase by inject(UserManagementUseCase::class.java)

    override fun initData() {

        // http demo
            launch {
                userManagementUseCase.login("", "",
                    onSuccess = {
                        // 处理登录成功的情况，例如更新UI，保存登录状态等
                    },
                    onFailure = {
                        // 处理登录失败的情况，显示错误信息等
                    }
                )
            }

    }
}