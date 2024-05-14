package com.kaan.myapplication.base

import com.kaan.myapplication.data.BaseResponse
import com.kaan.myapplication.http.Status
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * File Description
 *
 * @author zhangja
 * @version 1.0
 * @since 2024/2/2
 */
abstract class BaseUseCase {

    protected suspend fun <T> executeWithResult(action: suspend () -> BaseResponse<T>): Result<BaseResponse<T>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = action()
                if (response.return_code == Status.SUCCESS) { // 假设Status.SUCCESS为0表示成功
                    Result.success(response) // 直接返回整个BaseResponse对象
                } else {
                    Result.failure(Exception("API call failed with code: ${response.return_code}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}



