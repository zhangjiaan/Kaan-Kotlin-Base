package com.kaan.myapplication.http

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * File Description
 *
 * @author zhangja
 * @version 1.0
 * @since 2024/2/1
 */
object RetrofitServiceProvider {
    fun provideApiService(): ApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.example.com/") // 设置 API 基础 URL
            .addConverterFactory(GsonConverterFactory.create()) // 设置转换器，用于 JSON 的序列化和反序列化
            .build()

        return retrofit.create(ApiService::class.java) // 为 ApiService 接口创建实现
    }
}
