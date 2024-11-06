package com.donut.wxed34e654870cba42

import MainProcessTask
import android.app.Activity
import android.app.Application
import android.app.Application.ActivityLifecycleCallbacks
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.donut.wxed34e654870cba42.BuildConfig
import com.igexin.push.extension.mod.BaseActionBean
import com.igexin.sdk.IUserLoggerInterface
import com.igexin.sdk.PushConsts
import com.igexin.sdk.PushManager
import com.igexin.sdk.Tag
import com.igexin.sdk.message.GTCmdMessage
import com.igexin.sdk.message.GTNotificationMessage
import com.igexin.sdk.message.GTTransmitMessage
import com.tencent.luggage.wxa.SaaA.plugin.NativePluginBase
import com.tencent.luggage.wxa.SaaA.plugin.NativePluginInterface
import com.tencent.luggage.wxa.SaaA.plugin.SyncJsApi
import com.tencent.mm.sdk.json.JSONUtils.forEach
import com.tencent.mm.sdk.storage.sql.Sql
import org.json.JSONArray
import org.json.JSONObject
import kotlin.reflect.full.memberFunctions
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible
import kotlin.reflect.jvm.javaMethod


class PluginManager : NativePluginBase(), NativePluginInterface {
    private val TAG = "PluginManager"

    init {
        val mainProcessTask = MainProcessTask(Intent())
        mainProcessTask.setClientCallback { intent: Intent ->
            val bundle = intent.extras!!
            Log.d(TAG, " Received data: ${bundle.getInt(PushConsts.CMD_ACTION)}")
            val action = bundle.getInt(PushConsts.CMD_ACTION)

            val map = HashMap<String, Any>()
            when (action) {
                PushConsts.GET_MSG_DATA -> {
                    map["method"] = "onReceiveMessageData"
                    var gtTransmitMessage = intent.getSerializableExtra(PushConsts.KEY_MESSAGE_DATA) as GTTransmitMessage
                    map["param"] = toMap(gtTransmitMessage)
                }
                PushConsts.GET_CLIENTID -> {
                    map["method"] = "onReceiveClientId"
                    map["param"] = bundle.getString(PushConsts.KEY_CLIENT_ID)!!
                }
                PushConsts.GET_DEVICETOKEN -> {
                    map["method"] = "onReceiveDeviceToken"
                    map["param"] = bundle.getString(PushConsts.KEY_DEVICE_TOKEN)!!

                }
                PushConsts.GET_SDKONLINESTATE -> {
                    map["method"] = "onReceiveOnlineState"
                    map["param"] = bundle.getBoolean(PushConsts.KEY_ONLINE_STATE)
                }
                PushConsts.GET_SDKSERVICEPID -> {
                    map["method"] = "onReceiveServicePid"
                    map["param"] = bundle.getInt(PushConsts.KEY_SERVICE_PIT)
                }
                PushConsts.KEY_CMD_RESULT -> {
                    val gtCmdMessage = intent.getSerializableExtra(PushConsts.KEY_CMD_MSG) as GTCmdMessage?
                    map["method"] = "onReceiveCommandResult"
                    map["param"] = toMap(gtCmdMessage!!)
                    Log.d(TAG, " Received data: ${map["param"]}")
                }
                PushConsts.ACTION_NOTIFICATION_ARRIVED -> {
                    map["method"] = "onNotificationMessageArrived"
                    map["param"] = toMap(intent.getSerializableExtra(PushConsts.KEY_NOTIFICATION_ARRIVED) as GTNotificationMessage)
                }
                PushConsts.ACTION_NOTIFICATION_CLICKED -> {
                    map["method"] = "onNotificationMessageClicked"
                    map["param"] = toMap(intent.getSerializableExtra(PushConsts.KEY_NOTIFICATION_CLICKED) as GTNotificationMessage)
                }
                PushConsts.ACTION_NOTIFICATION_ENABLE -> {
//                    areNotificationsEnabled(context, CheckUtils.areNotificationsEnabled(context))
//                    map["method"] = "areNotificationsEnabled"
//                    map["param"] = CheckUtils.areNotificationsEnabled()
                }
                PushConsts.ACTION_POPUP_SHOW -> {
//                    onPopupMessageShow(context, bundle.getSerializable(PushConsts.KEY_POPUP_SHOW) as GTPopupMessage?)

                }
                PushConsts.ACTION_POPUP_CLICKED -> {
//                    onPopupMessageClicked(context, bundle.getSerializable(PushConsts.KEY_POPUP_CLICKED) as GTPopupMessage?)
                }
                -1->{
                    map["method"] = "debugLog"
                    map["param"] = intent.getStringExtra("log")!!
                }
                else -> {

                }
            }
            if (map["method"] != null) {
                this.sendMiniPluginEvent(map)
            }
        }
        mainProcessTask.execAsync()
    }

    override fun getPluginID(): String {
        Log.d(TAG, "getPluginID")
        return BuildConfig.PLUGIN_ID
    }

    @SyncJsApi(methodName = "initialize")
    fun initialize(data: JSONObject?, activity: Activity) {
        Log.d(TAG, "initialize")

        PushManager.getInstance().initialize(activity.applicationContext);
    }

    @SyncJsApi(methodName = "turnOnPush")
    fun turnOnPush(data: JSONObject?, activity: Activity) {
        PushManager.getInstance().turnOnPush(activity.applicationContext);
    }

    @SyncJsApi(methodName = "turnOffPush")
    fun turnOffPush(data: JSONObject?, activity: Activity) {
        PushManager.getInstance().turnOffPush(activity.applicationContext);
    }

    @SyncJsApi(methodName = "isPushTurnedOn")
    fun isPushTurnedOn(data: JSONObject?, activity: Activity): Boolean {
        return PushManager.getInstance().isPushTurnedOn(activity.applicationContext);
    }

    @SyncJsApi(methodName = "getVersion")
    fun getVersion(data: JSONObject?, activity: Activity): String {
        return PushManager.getInstance().getVersion(activity.applicationContext);
    }

    @SyncJsApi(methodName = "queryTag")
    fun queryTag(obj: JSONObject, activity: Activity) {
        PushManager.getInstance().queryTag(activity.applicationContext, obj["sn"] as String);
    }

    @SyncJsApi(methodName = "setTag")
    fun setTag(obj: JSONObject, activity: Activity) {
        if (obj["tags"] == null) {
            return
        }
        val tags = obj["tags"] as JSONArray
        val array = mutableListOf<Tag>()
        tags.forEach<String> { s ->
            val tag = Tag()
            tag.name = s
            array.add(tag)
        }
        val toTypedArray = array.toTypedArray()

        PushManager.getInstance().setTag(activity.applicationContext, toTypedArray, obj["sn"] as String);
    }

    //Context context, int beginHour, int duration
    @SyncJsApi(methodName = "setSilentTime")
    fun setSilentTime(obj: JSONObject, activity: Activity) {
        PushManager.getInstance().setSilentTime(activity.applicationContext, obj["beginHour"] as Int, obj["duration"] as Int);
    }

    @SyncJsApi(methodName = "setHeartbeatInterval")
    fun setHeartbeatInterval(obj: JSONObject, activity: Activity) {
        PushManager.getInstance().setHeartbeatInterval(activity.applicationContext, obj["interval"] as
                Int);
    }

    @SyncJsApi(methodName = "sendFeedbackMessage")
    fun sendFeedbackMessage(obj: JSONObject, activity: Activity) {
        PushManager.getInstance().sendFeedbackMessage(activity.applicationContext, obj["taskid"] as String, obj["messageid"] as String,
                obj["actionid"] as Int);
    }

    @SyncJsApi(methodName = "getClientid")
    fun getClientid(obj: JSONObject?, activity: Activity): String {
        return PushManager.getInstance().getClientid(activity.applicationContext);
    }

    @SyncJsApi(methodName = "bindAlias")
    fun bindAlias(obj: JSONObject, activity: Activity) {
        val sn = if (obj.has("sn")) obj["sn"] as String else "bindAlias" + System.currentTimeMillis()
        PushManager.getInstance().bindAlias(activity.applicationContext, obj["alias"] as String, sn);
    }

    @SyncJsApi(methodName = "unBindAlias")
    fun unBindAlias(obj: JSONObject, activity: Activity) {
        val isSelf = if (obj.has("isSelf")) obj["isSelf"] as Boolean else true
        val sn = if (obj.has("sn")) obj["sn"] as String else "unBindAlias_" + System.currentTimeMillis()
        PushManager.getInstance().unBindAlias(activity.applicationContext,
                obj["alias"] as String,
                isSelf, sn);
    }

    //    @SyncJsApi(methodName = "setDebugLogger")
    fun setDebugLogger(loggerInterface: IUserLoggerInterface, activity: Activity) {
        PushManager.getInstance().setDebugLogger(activity.applicationContext, loggerInterface);
    }

    @SyncJsApi(methodName = "setLocalBadge")
    fun setBadgeNum(obj: JSONObject, activity: Activity) {
        PushManager.getInstance().setBadgeNum(activity.applicationContext, obj["badge"] as Int);
    }


    fun toMap(bean: Any): Map<String, Any?> {
        val memberProperties = bean::class.memberProperties
        val hashMap = HashMap<String, Any?>()
        for (property in memberProperties) {
            property.isAccessible = true
            // 获取属性的名称和值
            val name = property.name
            var value: Any? = null
            if ("tags".equals(name)) {
                val array = JSONArray()
                for (tag in property.getter.call(bean) as Array<Tag>) {
                    array.put(tag.name)
                }
                value = array
            } else {
                value = property.getter.call(bean)
            }
            hashMap.put(name, value ?: "")
            Log.d(TAG, "toMap  ${bean::class.simpleName} ${name} : ${value}")

        }
        return hashMap
    }

}
