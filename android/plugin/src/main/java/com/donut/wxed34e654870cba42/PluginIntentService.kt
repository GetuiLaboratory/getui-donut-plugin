package com.donut.wxed34e654870cba42

import MainProcessTask
import android.content.Context
import android.content.Intent
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import com.igexin.sdk.GTIntentService
import com.igexin.sdk.PushConsts
import com.igexin.sdk.message.GTCmdMessage
import com.igexin.sdk.message.GTNotificationMessage
import com.igexin.sdk.message.GTPopupMessage
import com.igexin.sdk.message.GTTransmitMessage

class PluginIntentService() : GTIntentService() {

    companion object {
        private lateinit var mainProcessTask: MainProcessTask

        fun setMainProcessTask(mainProcessTask: MainProcessTask) {
            this.mainProcessTask = mainProcessTask
        }
    }

    constructor(parcel: Parcel) : this() {
    }

    override fun processOnHandleIntent(context: Context?, intent: Intent?) {
        var context = context
        if (intent == null || context == null) {
            return
        }
        try {
            val bundle = intent.extras
            if (bundle == null || bundle["action"] == null || bundle["action"] !is Int) {
                return
            }
            Log.d(TAG,"action ${bundle["action"]}")
            mainProcessTask.callbackByMainProcess(intent)
        } catch (e: Exception) {
          e.printStackTrace()
        }
    }


//    override fun onReceiveServicePid(p0: Context?, p1: Int) {
//        super.onReceiveServicePid(p0, p1)
//        Log.d(TAG, "PluginIntentService Received push process pid: ${p1} ")
//    }
//
//    override fun onReceiveClientId(context: Context, cid: String) {
//        super.onReceiveClientId(context, cid)
//        Log.d(TAG, "onReceiveClientId cid  ${cid} ")
//        val obtain = Message.obtain();
//        obtain.what = GtEnum.onReceiveClientId.ordinal
//        obtain.obj = cid
//        mainProcessTask.callbackByMainProcess(obtain)
//    }
//
//    override fun onNotificationMessageArrived(p0: Context?, p1: GTNotificationMessage?) {
//        super.onNotificationMessageArrived(p0, p1)
//
//        Log.d(TAG, "onNotificationMessageArrived notify  ${p1} ")
//        val obtain = Message.obtain();
//        obtain.what = GtEnum.onNotificationMessageArrived.ordinal
//        obtain.obj = p1
//        mainProcessTask.callbackByMainProcess(obtain)
//    }
//
//    override fun onNotificationMessageClicked(p0: Context?, p1: GTNotificationMessage?) {
//        super.onNotificationMessageClicked(p0, p1)
//        Log.d(TAG, "onNotificationMessageClicked notify  ${p1} ")
//        val obtain = Message.obtain();
//        obtain.what = GtEnum.onNotificationMessageClicked.ordinal
//        obtain.obj = p1
//        mainProcessTask.callbackByMainProcess(obtain)
//    }
//
//    override fun onReceiveCommandResult(p0: Context?, p1: GTCmdMessage?) {
//        super.onReceiveCommandResult(p0, p1)
//
//        Log.d(TAG, "onReceiveCommandResult   ${p1} ")
//        val obtain = Message.obtain();
//        obtain.what = GtEnum.onReceiveCommandResult.ordinal
//        obtain.obj = p1
//        mainProcessTask.callbackByMainProcess(obtain)
//    }
//
//    override fun onReceiveOnlineState(p0: Context?, p1: Boolean) {
//        super.onReceiveOnlineState(p0, p1)
//
//        Log.d(TAG, "onReceiveOnlineState   ${p1} ")
//        val obtain = Message.obtain();
//        obtain.what = GtEnum.onReceiveOnlineState.ordinal
//        obtain.obj = p1
//        mainProcessTask.callbackByMainProcess(obtain)
//
//    }
//
//    override fun onReceiveDeviceToken(p0: Context?, p1: String?) {
//        super.onReceiveDeviceToken(p0, p1)
//        Log.d(TAG, "onReceiveOnlineState   ${p1} ")
//        val obtain = Message.obtain();
//        obtain.what = GtEnum.onReceiveDeviceToken.ordinal
//        obtain.obj = p1
//        mainProcessTask.callbackByMainProcess(obtain)
//    }
//
//    override fun onReceiveMessageData(p0: Context?, p1: GTTransmitMessage?) {
//        super.onReceiveMessageData(p0, p1)
//
//        Log.d(TAG, "onReceiveMessageData   ${p1} ")
//        val obtain = Message.obtain();
//        obtain.what = GtEnum.onReceiveMessageData.ordinal
//        obtain.obj = p1
//        mainProcessTask.callbackByMainProcess(obtain)
//    }



}