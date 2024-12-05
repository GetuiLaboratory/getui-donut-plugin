package com.donut.wx341dde1fd385d9eb

import MainProcessTask
import android.app.Activity
import android.app.Application
import android.content.Intent
import android.os.Build
import android.os.Bundle
import com.tencent.luggage.wxa.SaaA.plugin.NativePluginApplicationInterface

class PushNativePluginApplication : NativePluginApplicationInterface {
    private val TAG = "application"
    private val mainProcessTask: MainProcessTask = MainProcessTask(Intent())

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
                mainProcessTask.setIntent(Intent("onActivityResumed"))
                mainProcessTask.execAsync()
            }

            override fun onActivityPaused(p0: Activity) {
                android.util.Log.e(TAG, "onActivityPaused")
                mainProcessTask.setIntent(Intent("onActivityPaused"))
                mainProcessTask.execAsync()
            }

            override fun onActivityStopped(p0: Activity) {
                android.util.Log.e(TAG, "onActivityStopped")
                mainProcessTask.setIntent(Intent("onActivityStopped"))
                mainProcessTask.execAsync()
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