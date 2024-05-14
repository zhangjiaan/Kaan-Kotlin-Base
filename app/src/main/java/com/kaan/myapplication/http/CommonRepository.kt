package com.kaan.myapplication.http

import com.kaan.myapplication.http.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


/**
 * @author Bleach1
 * @date 19-12-3 下午4:03
 * @Description 网络请求统一处理
 */

class CommonRepository(private var apiService: ApiService) {

    /**
     * 登陆
     */
    suspend fun login(
        phone: String,
        password: String
    ) = withContext(Dispatchers.IO) {
        val params = HashMap<String, String>()
        params["phone"] = phone
        params["password"] = password
        apiService.login(params)
    }.await()

}