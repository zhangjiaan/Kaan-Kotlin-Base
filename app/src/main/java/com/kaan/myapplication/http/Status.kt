package com.kaan.myapplication.http

object Status {
    /**
     * 响应成功
     */
    const val SUCCESS = 200

    /**
     * 未知错误
     */
    const val UNKNOWN_ERROR = 1002

    /**
     * 服务器内部错误
     */
    const val SERVER_ERROR = 500

    /**
     * 未认证（签名错误）
     */
    const val UNAUTHORIZED = 401
    /**
     * 接口不存在
     */
    const val NOT_FOUND = 404

    /**
     * 网络连接超时
     */
    const val NETWORK_ERROR = 1004

    /**
     * API解析异常（或者第三方数据结构更改）等其他异常
     */
    const val API_ERROR = 1005

    /**
     * 签名错误
     */
    const val SignatureError = 1005
}