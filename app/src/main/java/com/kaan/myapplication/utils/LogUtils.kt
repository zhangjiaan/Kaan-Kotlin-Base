package com.kaan.myapplication.utils

import android.util.Log
import com.kaan.myapplication.DEFAULT_TAG
import com.kaan.myapplication.isDevelopmentMode

/**
 * Description: Provides simplified logging for Android apps.
 *
 * Features:
 * - Tag is not required. If no tag is passed, the default tag is [DEFAULT_TAG]
 * - Message In Development Mode, the default prefix is [class name][number of lines][method name].
 * - Debug level log, message can be called without parameters, print suffix add Start by default.
 *
 * Version History:
 * - 1.0 (2023/12/16): Initial release, optimized for Kotlin.
 * - 1.1 (2024/01/30): Added Java support.
 * - 1.2 (2024/02/01): Changes the type passed in as String to Any, accepting any input type.
 *
 * Author: Kaan.cheung (Zhangja)
 * Email: Kaan.cheung@outlook.com
 */
object LogUtils {

    private var logOutput: ((level: Int, tag: String, message: String) -> Unit) =
        { level, tag, message ->
            when (level) {
                Log.VERBOSE -> Log.v(tag, message)
                Log.DEBUG -> Log.d(tag, message)
                Log.INFO -> Log.i(tag, message)
                Log.WARN -> Log.w(tag, message)
                Log.ERROR -> Log.e(tag, message)
                Log.ASSERT -> Log.wtf(tag, message)
            }
        }

    private fun log(level: Int, message: String?, tag: String?) {
        val logTag = tag ?: DEFAULT_TAG
        val appendInfo = if (isDevelopmentMode) getAppendInfo() else ""
        val logMessage = message ?: "Start."
        logOutput.invoke(level, logTag, "$appendInfo $logMessage")
    }

    @JvmStatic
    @JvmOverloads
    fun v(message: Any, tag: String? = null) {
        log(Log.VERBOSE, message.toString(), tag)
    }

    @JvmStatic
    @JvmOverloads
    fun d(message: Any? = null, tag: String? = null) {
        log(Log.DEBUG, message?.toString(), tag)
    }

    @JvmStatic
    @JvmOverloads
    fun i(message: Any, tag: String? = null) {
        log(Log.INFO, message.toString(), tag)
    }


    @JvmOverloads
    @JvmStatic
    fun w(message: Any, tag: String? = null) {
        log(Log.WARN, message.toString(), tag)
    }

    @JvmOverloads
    @JvmStatic
    fun e(message: Any, tag: String? = null) {
        log(Log.ERROR, message.toString(), tag)
    }

    @JvmOverloads
    @JvmStatic
    fun wtf(message: Any, tag: String? = null) {
        log(Log.ASSERT, message.toString(), tag)
    }

    private fun getAppendInfo(): String {
        return Thread.currentThread().stackTrace
            .asSequence()
            .firstOrNull { isRelevantStackTraceElement(it) }
            ?.let { stackElement ->
                formatStackTraceElement(stackElement)
            } ?: "UNKNOWN"

    }

    private fun isRelevantStackTraceElement(element: StackTraceElement): Boolean {
        return !element.className.startsWith("java.") &&
                !element.className.startsWith("javax.") &&
                !element.className.startsWith("dalvik.") &&
                !element.className.startsWith("android.") &&
                !element.className.startsWith("com.android.") &&
                element.className != LogUtils::class.java.name
    }

    private fun formatStackTraceElement(element: StackTraceElement): String {
        return "[${element.className.substringAfterLast('.')}][${element.lineNumber}]:[${element.methodName}]"
    }

}
