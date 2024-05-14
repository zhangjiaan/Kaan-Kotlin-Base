package com.kaan.myapplication.http

import com.kaan.myapplication.data.BaseResponse
import com.kaan.myapplication.data.LoginBean
import kotlinx.coroutines.Deferred
import retrofit2.http.*

interface ApiService {

    /**
     * 登录
     */
    @POST("api/login/login")
    fun login(@Body map: Map<String, String>): Deferred<BaseResponse<LoginBean>>

    /**
     * 登录2
     */
    @GET("api/login/login_2")
    fun login2(@Body map: Map<String, String>): Deferred<BaseResponse<LoginBean>>

}