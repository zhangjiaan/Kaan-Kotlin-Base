package com.kaan.myapplication.utils

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

/**
 * Description: Observers using such packages who register multiple observers at the same time will only have one observer respond
 *
 * Features:
 * -
 *
 * Version History:
 * - 1.0 (2023/12/16): Initial release
 *
 * Author: Kaan.cheung (Zhangja)
 * Email: Kaan.cheung@outlook.com
 */
class SingleLiveEvent<T> : MutableLiveData<T>() {

    private val pending = AtomicBoolean(false)

    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {

        if (hasActiveObservers()) {
            LogUtils.w("Multiple observers registered but only one will be notified of changes.")
        }

        super.observe(owner) { t ->
            if (pending.compareAndSet(true, false)) {
                observer.onChanged(t)
            }
        }
    }

    @MainThread
    override fun setValue(t: T?) {
        pending.set(true)
        super.setValue(t)
    }

    @MainThread
    fun call() {
        value = null
    }

}