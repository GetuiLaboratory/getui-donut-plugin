import android.content.Intent
import android.os.Parcel
import android.util.Log
import com.donut.wx341dde1fd385d9eb.PluginIntentService
import com.tencent.luggage.wxa.SaaA.plugin.NativePluginMainProcessTask
import kotlinx.android.parcel.Parcelize

@Parcelize
class MainProcessTask(private var msgCallback: Intent) :
    NativePluginMainProcessTask() {
    private var clientCallback: ((Intent) -> Unit)? = null

    companion object {
        private var hasSetGT = false
        private var TAG: String = "MainProcessTask"

    }

    fun setClientCallback(callback: (data: Intent) -> Unit) {
        this.clientCallback = callback
    }

    override fun parseFromParcel(parcel: Parcel?) {
        // 如果需要获得主进程数据，需要重写parseFromParcel，手动解析Parcel
        this.msgCallback = parcel?.readParcelable(Intent::class.java.classLoader) ?: Intent()
    }

    /**
     * 运行在主进程的逻辑，不建议在主进程进行耗时太长的操作
     */
    override fun runInMainProcess() {
        // 如果需要把主进程的数据回调到小程序进程，就赋值后调用 callback 函数
        when (msgCallback.action) {
            "gt_initialize" -> {
                if (!hasSetGT) {
                    Log.d(TAG, "runInMainProcess execute hasSetGT ")
                    hasSetGT = true
                    // 如果需要把主进程的数据回调到小程序进程，就赋值后调用 callback 函数
                    PluginIntentService.setMainProcessTask(this);
                }
            }
        }
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


    fun setIntent(intent: Intent) {
        this.msgCallback = intent
    }

}