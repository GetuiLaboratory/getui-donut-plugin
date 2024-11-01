// pages/ios/ios.js
const {
  miniAppPluginId
} = require('../../constant');

Page({

  /**
   * 页面的初始数据
   */
  data: {
    myPlugin: undefined,
    quickStartContents: [
      '在「设置」->「安全设置」中手动开启多端插件服务端口',
      '在「工具栏」->「运行设备」中选择 iOS 点击「运行」，快速准备运行环境',
      '在打开的 Xcode 中点击「播放」运行原生工程',
      '保持开发者工具开启，修改小程序代码和原生代码仅需在 Xcode 中点击「播放」查看效果',
    ]
  },

  onLoadPlugin() {

    const listener1 = (param) => {
      console.log('onMiniPluginEvent listener:', param)
    }

    const listener2 = (param) => {
      console.log('onMiniPluginEvent listener2:', param)
    }

    wx.miniapp.loadNativePlugin({
      pluginId: miniAppPluginId,
      success: (plugin) => {
        console.log('load plugin success', plugin)
        //监听native的事件
        plugin.onMiniPluginEvent(listener1)
        //可以设置多个监听
        //plugin.onMiniPluginEvent(listener2)
        this.setData({
          myPlugin: plugin
        })
      },
      fail: (e) => {
        console.log('load plugin fail', e)
      }
    })
  },
  onStartSdk() {
    const {
      myPlugin
    } = this.data;
    console.log("onStartSdk", myPlugin);
    myPlugin.startSdk({
      'appId': 'xXmjbbab3b5F1m7wAYZoG2',
      'appKey': 'BZF4dANEYr8dwLhj6lRfx2',
      'appSecret': 'yXRS5zRxDt8WhMW8DD8W05'
    })
  },
  setPushMode() {
    const {
      myPlugin
    } = this.data;
    myPlugin.setPushMode({
      'mode': 1
    })
  },
  runBackgroundEnable() {
    const {
      myPlugin
    } = this.data;
    myPlugin.runBackgroundEnable({
      'enable': 1
    }) //1 or 0
  },
  bindAlias() {
    const {
      myPlugin
    } = this.data;
    myPlugin.bindAlias({
      'alias': 'superman',
      'sn': '0001'
    })
  },
  unbindAlias() {
    const {
      myPlugin
    } = this.data;
    myPlugin.unbindAlias({
      'alias': 'superman',
      'sn': '0001'
    })
  },
  setTags() {
    const {
      myPlugin
    } = this.data;
    myPlugin.setTags({
      'tags': ['t1', 't2', 't2']
    })
  },

  setBadge() {
    const {
      myPlugin
    } = this.data;
    myPlugin.setBadge({
      'badge': 3
    })
  },

  setLocalBadge() {
    const {
      myPlugin
    } = this.data;
    myPlugin.setLocalBadge({
      'badge': 4
    })
  },

  registerActivityToken() {
    const {
      myPlugin
    } = this.data;
    myPlugin.registerActivityToken({
      'aid': '1234',
      'token': 'token1',
      'sn': '0001'
    })
  },

  registerPushToStartToken() {
    const {
      myPlugin
    } = this.data;
    myPlugin.registerPushToStartToken({
      'attribute': 'MyAttribute',
      'token': 'token2',
      'sn': '0002'
    })
  },
  getVersion() {
    const {
      myPlugin
    } = this.data;
    let ver = myPlugin.getVersion()
    console.log(ver)
  },
  onUsePlugin() {
    const {
      myPlugin
    } = this.data
    if (!myPlugin) {
      console.log('plugin is undefined')
      return
    }
    const ret = myPlugin.mySyncFunc({
      a: 'hello',
      b: [1, 2]
    })
    console.log('mySyncFunc ret:', ret)

    myPlugin.myAsyncFuncwithCallback({
      a: 'hello',
      b: [1, 2]
    }, (ret) => {
      console.log('myAsyncFuncwithCallback ret:', ret)
    })
  },

  copyLink() {
    wx.setClipboardData({
      data: 'https://dev.weixin.qq.com/docs/framework/dev/plugin/iosPlugin.html',
    })
  }
})