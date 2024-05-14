package com.kaan.myapplication

import android.app.Application
import android.content.pm.PackageManager
import com.kaan.myapplication.di.myModule
import com.kaan.myapplication.utils.LogUtils
import com.tencent.mmkv.MMKV
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

/**
 * Description: Application class.
 *
 * Features:
 * - The application starts printing Version APP Number
 * - Initialize Koin and MMKV.
 *
 * Version History:
 * - 1.0 (2024/1/31): Initial release.
 *
 * Author: Kaan.cheung (Zhangja)
 * Email: Kaan.cheung@outlook.com
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        //Print App Version Number
        printAppVersion()
        startKoin {
            androidLogger() // Koin Log
            androidContext(this@App)
            modules(myModule) // Load module
        }
        //Init MMKV
        MMKV.initialize(this);
    }

    /**
     * Get the version number of the current app and print it
     */
    private fun printAppVersion() {
        try {
            val packageInfo = packageManager.getPackageInfo(packageName, 0)
            val version = packageInfo.versionName
            LogUtils.i("App Version: $version")
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            LogUtils.e("NameNotFoundException: ${e.message}")
        }
    }

}