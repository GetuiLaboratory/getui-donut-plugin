package com.donut.wxed34e654870cba42

import android.app.Activity
import android.app.Application
import android.os.Build
import android.os.Bundle
import com.donut.wxed34e654870cba42.BuildConfig
import com.tencent.luggage.wxa.SaaA.plugin.NativePluginApplicationInterface

class PushNativePluginApplication : NativePluginApplicationInterface {
    private val TAG = "application"

    override fun getPluginID(): String {
        android.util.Log.e(TAG, "getPluginID")
        return BuildConfig.PLUGIN_ID
    }

    override fun onCreate(application: Application) {
        android.util.Log.e(TAG, "oncreate!")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            android.util.Log.e(TAG, "process : "+Application.getProcessName())
        }
        application.registerActivityLifecycleCallbacks(object: Application.ActivityLifecycleCallbacks {
            override fun onActivityCreated(p0: Activity, p1: Bundle?) {
                android.util.Log.e(TAG, "onActivityCreated ${p0.javaClass.simpleName}")
            }

            override fun onActivityStarted(p0: Activity) {
                android.util.Log.e(TAG, "onActivityStarted")
            }

            override fun onActivityResumed(p0: Activity) {
                android.util.Log.e(TAG, "onActivityResumed")
            }

            override fun onActivityPaused(p0: Activity) {
                android.util.Log.e(TAG, "onActivityPaused")
            }

            override fun onActivityStopped(p0: Activity) {
                android.util.Log.e(TAG, "onActivityStopped")
            }

            override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {
                android.util.Log.e(TAG, "onActivitySaveInstanceState")
            }

            override fun onActivityDestroyed(p0: Activity) {
                android.util.Log.e(TAG, "onActivityDestroyed  ${p0.javaClass.simpleName}")
            }
        })
    }

}