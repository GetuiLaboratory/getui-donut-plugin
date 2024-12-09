// pages/android/android.js
const { miniAppPluginId } = require('../../constant');

Page({
  /**
   * 页面的初始数据
   */
  data: {
    myPlugin: undefined,
    quickStartContents: [
      '在「设置」->「安全设置」中手动开启多端插件服务端口',
      '在「工具栏」->「运行设备」中选择 Android 点击「运行」，快速准备运行环境',
      '在打开的 Android Stuido 中点击「播放」运行原生工程',
      '保持开发者工具开启，修改小程序代码和原生代码仅需在 Android Stuido 中点击「播放」查看效果',
    ]
  },

  onLoadPlugin() {
    const listener = (param) => {
      console.log('onMiniPluginEvent listener '+JSON.stringify(param))
      switch(param["method"]){
          case "onReceiveClientId":
              this.sdkMethod()
            break
      }
      }

    wx.miniapp.loadNativePlugin({
      pluginId: miniAppPluginId,
      success: (plugin) => {
        console.log('load plugin success', plugin)
        plugin.onMiniPluginEvent(listener)
        this.setData({
          myPlugin: plugin
        })
        var enable = plugin.areNotificationsEnabled()
        console.log('areNotificationsEnabled'+ enable)
        //打开通知权限的设置页面
        plugin.openNotification()
      },
      fail: (e) => {
        console.log('load plugin fail', JSON.stringify(e))
      }
    })
  },

  onUsePlugin() {
    const { myPlugin } = this.data
    if (!myPlugin) {
      console.log('plugin is undefined')
      return
    }
    myPlugin.initialize()
    console.log('initialize')
  },
  sdkMethod(){
    const { myPlugin } = this.data
    // myPlugin.setTag({"tags":["a","b","c"],"sn":"1111"})
    // myPlugin.queryTag({"sn":"ddd"})
   var id =  myPlugin.getClientid()
   console.log('plugin getClientid '+id)
  var version = myPlugin.getVersion()
   console.log('plugin getVersion '+version)
  //  myPlugin.turnOffPush()
  //  myPlugin.turnOnPush()
   var isPushTurnedOn = myPlugin.isPushTurnedOn()
   console.log('plugin isPushTurnedOn '+isPushTurnedOn)
  //  myPlugin. bindAlias({"alias":"12345"})
  //  myPlugin. unBindAlias({"alias":"12345"})
  //  myPlugin. setLocalBadge({"badge":2})
  //  myPlugin.sendFeedbackMessage({"taskid":"dddd",
  // "messageid":"ddddd",actionid:90002})

  var enable =   myPlugin.areNotificationsEnabled()
  console.log('areNotificationsEnabled'+ enable)
  myPlugin.requestNotifyPermission()
  },
  copyLink() {
    wx.setClipboardData({
      data: 'https://dev.weixin.qq.com/docs/framework/dev/plugin/androidPlugin.html',
    })
  }
})