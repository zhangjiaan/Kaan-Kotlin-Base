package com.kaan.myapplication.data

/**
 * File Description
 *
 * @author zhangja
 * @version 1.0
 * @since 2024/2/1
 */

/**
 * 基类实体类
 */
data class BaseResponse<T>(
    var return_code: Int,
    var strSign: String,
    var return_data: T
)
data class LoginBean(
    val response_time: Int,
    val result: Boolean,
    val sign: String,
    val user: User
)
data class User(
    val avatar_url: String,
    val is_bank: Int
)