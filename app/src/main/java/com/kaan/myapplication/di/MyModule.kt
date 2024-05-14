package com.kaan.myapplication.di

import com.kaan.myapplication.http.RetrofitServiceProvider
import com.kaan.myapplication.http.CommonRepository
import com.kaan.myapplication.usecase.UserManagementUseCase
import org.koin.dsl.module

/**
 * File Description
 *
 * @author zhangja
 * @version 1.0
 * @since 2024/2/1
 */
val myModule = module {
    // 单例模式提供 ApiService
    single { RetrofitServiceProvider.provideApiService() }

    // 单例模式提供 Repository
    single { CommonRepository(get()) } // get() 会自动解析 ApiService

    factory { UserManagementUseCase(get()) }
}
