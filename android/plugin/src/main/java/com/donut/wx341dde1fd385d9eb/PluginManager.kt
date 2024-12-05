package com.donut.wx341dde1fd385d9eb

import MainProcessTask
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import org.json.JSONArray
import org.json.JSONObject
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible


class PluginManager : NativePluginBase(), NativePluginInterface {
    private var callback: (Intent) -> Unit
    private val TAG = "PluginManager"
    private val mainProcessTask: MainProcessTask = MainProcessTask(Intent())

    companion object {
        val GT_LOG = -1
    }

    init {
        val callback: ((Intent) -> Unit) = { intent ->
            val bundle = intent.extras!!
            Log.d(TAG, " Received data: ${bundle.getInt(PushConsts.CMD_ACTION)}")
            val action = bundle.getInt(PushConsts.CMD_ACTION)

            val map = HashMap<String, Any>()
            when (action) {
                PushConsts.GET_MSG_DATA -> {
                    map["method"] = "onReceiveMessageData"
                    var gtTransmitMessage =
                        intent.getSerializableExtra(PushConsts.KEY_MESSAGE_DATA) as GTTransmitMessage
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
                    val gtCmdMessage =
                        intent.getSerializableExtra(PushConsts.KEY_CMD_MSG) as GTCmdMessage?
                    map["method"] = "onReceiveCommandResult"
                    map["param"] = toMap(gtCmdMessage!!)
                    Log.d(TAG, " Received data: ${map["param"]}")
                }
                PushConsts.ACTION_NOTIFICATION_ARRIVED -> {
                    map["method"] = "onNotificationMessageArrived"
                    map["param"] =
                        toMap(intent.getSerializableExtra(PushConsts.KEY_NOTIFICATION_ARRIVED) as GTNotificationMessage)
                }
                PushConsts.ACTION_NOTIFICATION_CLICKED -> {
                    map["method"] = "onNotificationMessageClicked"
                    map["param"] =
                        toMap(intent.getSerializableExtra(PushConsts.KEY_NOTIFICATION_CLICKED) as GTNotificationMessage)
                }
                GT_LOG -> {
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
        this.callback = callback;
        mainProcessTask.setClientCallback(callback)
        mainProcessTask.execAsync()
    }

    override fun getPluginID(): String {
        Log.d(TAG, "getPluginID")
        return BuildConfig.PLUGIN_ID
    }

    @SyncJsApi(methodName = "initialize")
    fun initialize(data: JSONObject?, activity: Activity) {
        Log.d(TAG, "initialize")
        val intent = Intent("gt_initialize")
        this.mainProcessTask.setIntent(intent)
        this.mainProcessTask.execAsync()
        val callback = this.callback;
        PushManager.getInstance()
            .setDebugLogger(activity.applicationContext, object : IUserLoggerInterface {
                override fun log(log: String) {
                    Log.e(TAG, log)
                    val intent = Intent()
                    val bundle = Bundle()
                    bundle.putInt(PushConsts.CMD_ACTION, PluginManager.GT_LOG)
                    bundle.putString("log", log)
                    intent.putExtras(bundle)
                    callback(intent)
                }
            })
        com.igexin.sdk.PushManager.getInstance().initialize(activity.applicationContext);
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

        PushManager.getInstance()
            .setTag(activity.applicationContext, toTypedArray, obj["sn"] as String);
    }

    //Context context, int beginHour, int duration
    @SyncJsApi(methodName = "setSilentTime")
    fun setSilentTime(obj: JSONObject, activity: Activity) {
        PushManager.getInstance().setSilentTime(
            activity.applicationContext,
            obj["beginHour"] as Int,
            obj["duration"] as Int
        );
    }

    @SyncJsApi(methodName = "setHeartbeatInterval")
    fun setHeartbeatInterval(obj: JSONObject, activity: Activity) {
        PushManager.getInstance().setHeartbeatInterval(
            activity.applicationContext, obj["interval"] as
                    Int
        );
    }

    @SyncJsApi(methodName = "sendFeedbackMessage")
    fun sendFeedbackMessage(obj: JSONObject, activity: Activity) {
        PushManager.getInstance().sendFeedbackMessage(
            activity.applicationContext, obj["taskid"] as String, obj["messageid"] as String,
            obj["actionid"] as Int
        );
    }

    @SyncJsApi(methodName = "getClientid")
    fun getClientid(obj: JSONObject?, activity: Activity): String {
        return PushManager.getInstance().getClientid(activity.applicationContext);
    }

    @SyncJsApi(methodName = "bindAlias")
    fun bindAlias(obj: JSONObject, activity: Activity) {
        val sn =
            if (obj.has("sn")) obj["sn"] as String else "bindAlias" + System.currentTimeMillis()
        PushManager.getInstance()
            .bindAlias(activity.applicationContext, obj["alias"] as String, sn);
    }

    @SyncJsApi(methodName = "unBindAlias")
    fun unBindAlias(obj: JSONObject, activity: Activity) {
        val isSelf = if (obj.has("isSelf")) obj["isSelf"] as Boolean else true
        val sn =
            if (obj.has("sn")) obj["sn"] as String else "unBindAlias_" + System.currentTimeMillis()
        PushManager.getInstance().unBindAlias(
            activity.applicationContext,
            obj["alias"] as String,
            isSelf, sn
        );
    }

    //    @SyncJsApi(methodName = "setDebugLogger")
//    fun setDebugLogger(loggerInterface: IUserLoggerInterface, activity: Activity) {
//        PushManager.getInstance().setDebugLogger(activity.applicationContext, loggerInterface);
//    }

    @SyncJsApi(methodName = "setLocalBadge")
    fun setBadgeNum(obj: JSONObject, activity: Activity) {
        PushManager.getInstance().setBadgeNum(activity.applicationContext, obj["badge"] as Int);
    }

    @SyncJsApi(methodName = "openNotification")
    fun openNotification(obj: JSONObject?, activity: Activity) {
//        PushManager.getInstance().openNotification(activity.applicationContext);
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
