package com.donut.wx341dde1fd385d9eb

import MainProcessTask
import android.Manifest
import android.app.Activity
import android.app.AppOpsManager
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
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
import java.lang.reflect.Method
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible


class PluginManager : NativePluginBase(), NativePluginInterface {
    private var callback: (Intent) -> Unit
    private val TAG = "PluginManager"
    private val mainProcessTask: MainProcessTask = MainProcessTask(Intent())

    companion object {
        val GT_LOG = -1
        const val GT_NOTIFY_ENABLE = "-2"
        const val ACTION_NOTIFICATION_ENABLE = 10014
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
                ACTION_NOTIFICATION_ENABLE -> {
                    if (bundle.containsKey(PluginManager.GT_NOTIFY_ENABLE)) {
                        map["method"] = "areNotificationsEnabled"
                        map["param"] = bundle.getBoolean(GT_NOTIFY_ENABLE)
                    }
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
        areNotificationsEnabled(data, activity)
    }


    @SyncJsApi(methodName = "areNotificationsEnabled")
    fun areNotificationsEnabled(data: JSONObject?, activity: Activity): Boolean {
        return areNotificationsEnabledGT(activity);
    }

    @SyncJsApi(methodName = "requestNotifyPermission")
    fun requestNotifyPermission(data: JSONObject?, activity: Activity) {
        val callback = this.callback;
        this.requestPermission(
            activity,
            arrayOf(Manifest.permission.POST_NOTIFICATIONS)
        ) { permissions, grantResults ->
            if (permissions.contains(Manifest.permission.POST_NOTIFICATIONS) && grantResults != null && grantResults.size > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED
            ) {
                val intent = Intent()
                val bundle = Bundle()
                bundle.putInt(PushConsts.CMD_ACTION, ACTION_NOTIFICATION_ENABLE)
                bundle.putBoolean(PluginManager.GT_NOTIFY_ENABLE, true)
                intent.putExtras(bundle)
                callback(intent)
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    Log.d(TAG, "requestPermissions")
                    activity.requestPermissions(arrayOf(Manifest.permission.POST_NOTIFICATIONS), 1);
                }
            }
        }
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



    @SyncJsApi(methodName = "setLocalBadge")
    fun setBadgeNum(obj: JSONObject, activity: Activity) {
        PushManager.getInstance().setBadgeNum(activity.applicationContext, obj["badge"] as Int);
    }

    @SyncJsApi(methodName = "openNotification")
    fun openNotification(obj: JSONObject?, activity: Activity) {
        openNotification(activity.applicationContext)
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

    fun areNotificationsEnabledGT(context: Context): Boolean {
        try {
            val CHECK_OP_NO_THROW = "checkOpNoThrow"
            val OP_POST_NOTIFICATION = "OP_POST_NOTIFICATION"
            if (Build.VERSION.SDK_INT >= 24) {
                val mNotificationManager =
                    context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                val mtd: Method =
                    NotificationManager::class.java.getDeclaredMethod("areNotificationsEnabled")
                return mtd.invoke(mNotificationManager) as Boolean
            } else if (Build.VERSION.SDK_INT >= 19) {
                val appOps = context.getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
                val appInfo: ApplicationInfo = context.applicationInfo
                val pkg: String = context.applicationContext.packageName
                val uid: Int = appInfo.uid
                val appOpsClass = Class.forName(AppOpsManager::class.java.name)
                val checkOpNoThrowMethod: Method = appOpsClass.getMethod(
                    CHECK_OP_NO_THROW,
                    Int::class.javaPrimitiveType,
                    Int::class.javaPrimitiveType,
                    String::class.java
                )
                val opPostNotificationValue = appOpsClass.getDeclaredField(OP_POST_NOTIFICATION)
                val value = opPostNotificationValue.get(Int::class.javaPrimitiveType) as Int
                return checkOpNoThrowMethod.invoke(
                    appOps,
                    value,
                    uid,
                    pkg
                ) as Int == AppOpsManager.MODE_ALLOWED
            }
        } catch (e: Throwable) {
            Log.e(TAG, "Error checking notifications enabled", e)
        }

        return true
    }

    fun openNotification(context: Context) {
        try {
            val intent = Intent()
            if (Build.VERSION.SDK_INT >= 26) {
                intent.action = "android.settings.APP_NOTIFICATION_SETTINGS"
                intent.putExtra("android.provider.extra.APP_PACKAGE", context.packageName)
                intent.putExtra("android.provider.extra.CHANNEL_ID", context.applicationInfo.uid)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            } else if (Build.VERSION.SDK_INT >= 21) {
                intent.action = "android.settings.APP_NOTIFICATION_SETTINGS"
                intent.putExtra("app_package", context.packageName)
                intent.putExtra("app_uid", context.applicationInfo.uid)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            } else {
                intent.action = "android.settings.APPLICATION_DETAILS_SETTINGS"
                val var2 = Uri.fromParts("package", context.packageName, null as String?)
                intent.data = var2
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            context.startActivity(intent)
        } catch (var3: Throwable) {
            var3.printStackTrace()
        }
    }
}
