package com.donut.wxed34e654870cba42

import android.app.Activity
import com.tencent.luggage.wxa.SaaA.plugin.NativePluginInterface
import com.tencent.luggage.wxa.SaaA.plugin.NativePluginBase
import com.tencent.luggage.wxa.SaaA.plugin.SyncJsApi
import com.tencent.luggage.wxa.SaaA.plugin.AsyncJsApi
import org.json.JSONObject


class TestNativePlugin: NativePluginBase(), NativePluginInterface {
    private val TAG = "TestNativePlugin"

    override fun getPluginID(): String {
        android.util.Log.e(TAG, "getPluginID")
        return BuildConfig.PLUGIN_ID
    }

    @SyncJsApi(methodName = "mySyncFunc")
    fun test(data: JSONObject?, activity: Activity): String {
        android.util.Log.i(TAG, data.toString())

        val componentName = activity.componentName.toString()
        android.util.Log.i(TAG, componentName)

        return "test"
    }

    @AsyncJsApi(methodName = "myAsyncFuncwithCallback")
    fun testAsync(data: JSONObject?, callback: (data: Any) -> Unit, activity: Activity) {
        android.util.Log.i(TAG, data.toString())

        // 有需要的时候向 js 发送消息
        val values1 = HashMap<String, Any>()
        values1["status"] = "testAsync start"
        this.sendMiniPluginEvent(values1)

        callback("async testAsync")
    }

}