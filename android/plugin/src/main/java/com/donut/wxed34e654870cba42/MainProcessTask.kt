import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.*
import android.util.Log
import com.donut.wxed34e654870cba42.PluginIntentService
import com.getui.gtc.base.GtcProvider
import com.igexin.sdk.IUserLoggerInterface
import com.igexin.sdk.PushConsts
import com.igexin.sdk.PushManager
import com.tencent.luggage.wxa.SaaA.plugin.NativePluginMainProcessTask
import kotlinx.android.parcel.Parcelize

@Parcelize
class MainProcessTask(private var msgCallback: Intent) :
        NativePluginMainProcessTask() {

    private var clientCallback: ((Intent) -> Unit)? = null
    private var TAG: String = "MainProcessTask"

    fun setClientCallback(callback: (data: Intent) -> Unit) {
        this.clientCallback = callback
    }

    /**
     * 运行在主进程的逻辑，不建议在主进程进行耗时太长的操作
     */
    override fun runInMainProcess() {
        PushManager.getInstance().setDebugLogger(GtcProvider.context(), object : IUserLoggerInterface {
            override fun log(log: String) {
                Log.e(TAG, log)
                val intent = Intent()
                val bundle = Bundle()
                bundle.putInt(PushConsts.CMD_ACTION, -1)
                bundle.putString("log",log)
                intent.putExtras(bundle)
                callbackByMainProcess(intent)
            }
        })
        // 如果需要把主进程的数据回调到小程序进程，就赋值后调用 callback 函数
        PluginIntentService.setMainProcessTask(this);
    }

    public fun callbackByMainProcess(obtain: Intent) {
//        Log.e("MainProcessTask", "callbackByMainProcess")
        msgCallback = obtain
        this.callback()
    }

    /**
     * 运行在小程序进程的逻辑
     */
    override fun runInClientProcess() {
//        Log.e("ClientProcess", "msgCallback:${msgCallback}")
        this.clientCallback?.let { callback ->
            callback(msgCallback)
        }
    }

    override fun parseFromParcel(parcel: Parcel?) {
        // 如果需要获得主进程数据，需要重写parseFromParcel，手动解析Parcel
        this.msgCallback = parcel?.readParcelable(Intent::class.java.classLoader) ?: Intent()
    }

}