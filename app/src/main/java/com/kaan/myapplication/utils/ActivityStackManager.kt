package com.kaan.myapplication.utils

import android.app.Activity
import java.util.Stack

/**
 * File Description
 *
 * @author zhangja
 * @version 1.0
 * @since 2024/2/1
 */
class ActivityStackManager private constructor() {
    private val activityStack: Stack<Activity> = Stack()

    companion object {
        val instance: ActivityStackManager by lazy { ActivityStackManager() }
    }

    fun addActivity(activity: Activity) {
        activityStack.push(activity)
    }

    fun removeActivity(activity: Activity) {
        activityStack.remove(activity)
    }

    fun finishAllActivity() {
        while (!activityStack.empty()) {
            activityStack.pop()?.finish()
        }
    }

    fun finishActivity(cls: Class<*>) {
        activityStack.filter { it.javaClass == cls }
            .forEach { finishActivity(it) }
    }

    fun checkActivity(cls: Class<*>): Boolean {
        return activityStack.any { it.javaClass == cls }
    }

    fun finishActivity(activity: Activity) {
        activityStack.remove(activity)
        activity.finish()
    }

    fun finishToActivity(actCls: Class<out Activity>, isIncludeSelf: Boolean): Boolean {
        val buf = mutableListOf<Activity>()
        for (activity in activityStack.reversed()) {
            when {
                activity.javaClass.isAssignableFrom(actCls) -> {
                    buf.forEach { it.finish() }
                    return true
                }
                activityStack.lastElement() == activity && isIncludeSelf -> buf.add(activity)
                else -> buf.add(activity)
            }
        }
        return false
    }

    val currentTopActivity: Activity?
        get() = if (activityStack.empty()) null else activityStack.peek()
}