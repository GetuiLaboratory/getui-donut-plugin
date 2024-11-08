#个推SDK donut插件集成文档



# **概述**

个推消息推送SDK在 [donut平台](https://dev.weixin.qq.com)的插件示例，使用此模块实现服务端向客户端推送通知和透传消息的功能等。开发者需要将插件源码上传到donut插件管理库中使用。

流程： 开发者需要先加载插件，后调用插件具体API，比如startSdk


## Android 使用说明：
插件桥接了原生SDK API，原生SDK API具体说明可参考[官网文档中心 Android API](https://docs.getui.com/getui/mobile/android/api/)

### Android appid配置：
由于donut还未开放多meta-data配置，和manifestPlaceholders配置.
还需要在插件中的AndroidManifest.xml配置相关推送参数
参考：https://docs.getui.com/getui/mobile/vendor/androidstudio/ 的1.3 配置应用参数
```js
如:
查看插件plugin AndroidManifest.xml,已经有例子
      <!--TODO：测试参数，上传插件时替换成自己的-->
        <meta-data
            android:name="GETUI_APPID"
            android:value="djYjSlFVMf6p5YOy2OQUs8"
            tools:replace="android:value" />

        <!--TODO：厂商全不用可以填android:value="*" 不能删除meta-data，
        TODO：下面例子中部分value携带"厂商_",如XM_ ， 填写value值"厂商_"不要动，正确值如："XM_你的值" -->
        <meta-data
            android:name="com.huawei.hms.client.appid"
            android:value="102527745"
            tools:replace="android:value" />
```

## iOS 使用说明：
插件桥接了原生SDK API，原生SDK API具体说明可参考[官网文档中心 iOS API](https://docs.getui.com/getui/mobile/ios/api/)

### 插件js api说明：
```js
usage() { 

	  //加载插件
    wx.miniapp.loadNativePlugin({
      pluginId: miniAppPluginId,
      success: (plugin) => {
        console.log('load plugin success', plugin)
        //监听原生sdk向js发送的事件 （lisenner定义后续说明）
        plugin.onMiniPluginEvent(listener)
        this.setData({
          myPlugin: plugin
        })
      },
      fail: (e) => {
        console.log('load plugin fail', e)
      }
    });
    
    const {
      myPlugin
    } = this.data;
    const deviceInfo = wx.getDeviceInfo()

    //需要第一时间调用startSDK，避免通知处理不及时
    if ("android" === deviceInfo.platform) {
      myPlugin.initialize()
    }else{
      myPlugin.startSdk({
        'appId': 'xXmjbbab3b5F1m7wAYZoG2',
        'appKey': 'BZF4dANEYr8dwLhj6lRfx2',
        'appSecret': 'yXRS5zRxDt8WhMW8DD8W05'
      })
    }
   
		//(IOS)模式切换， mode入参0 或 1
    myPlugin.setPushMode({
      'mode': 1
    })

  	//(IOS)后台模式，入参0 或 1
    myPlugin.runBackgroundEnable({
      'enable': 1
    }) 

   //(IOS)同步服务端角标
    myPlugin.setBadge({
      'badge': 3
    })

    //(IOS)注册ActivityToken PushToStartToken
    myPlugin.registerPushToStartToken({
      'attribute': 'MyAttribute',
      'token': 'token2',
      'sn': '0002'
    })

    //(Android) 关闭推送
   myPlugin.turnOffPush()
    // (Android) 打开推送
   myPlugin.turnOnPush()
   // (Android)推送状态 
   myPlugin.isPushTurnedOn()
   // (Android)推送状态 
   myPlugin.queryTag({"an":"121212"})
   // (Android)静默时段
   myPlugin.setSilentTime({"beginHour":10,"duration":5})
  //(Android)自定义回执
  myPlugin.sendFeedbackMessage({"taskid":"sddddd","messageid":"ddd","actionid":90002})

  	//绑定别名
    myPlugin.bindAlias({
      'alias': 'superman',
      'sn': '0001'
    })
    
    //解绑别名
    myPlugin.unbindAlias({
      'alias': 'superman',
      'sn': '0001',
      'isSelf': true//(Android参数,可空,默认true) 如果是true，只对当前cid做解绑；如果是false，对所有绑定该别名的cid列表做解绑.
    })
    
    //设置标签
    myPlugin.setTags({
      'tags': ['t1', 't2', 't2'],
      'sn': '0001'//(Android参数,必填)
    })

	 //修改当前App角标
    myPlugin.setLocalBadge({
      'badge': 4
    }) 

		//注册ActivityToken pushToken
    myPlugin.registerActivityToken({
      'aid': '1234',
      'token': 'token1',
      'sn': '0001'
    })

    //获取原生sdk版本号
    let ver = myPlugin.getVersion()
    console.log(ver)

```

### 插件回调说明：

```js
const listener = (param) => {
      console.log('onMiniPluginEvent listener:', JSON.stringify(param))
      //IOS 监听事件总览：
			/*
			method:onGrantAuthorization
			param:true 或者 false, 用户是否授权通知
			
		  
			method:onWillPresentNotification
			param:apns下发的userInfo
			说明:
			通知展示回调
			
		  
			method:onReceiveNotificationResponse
			param:apns下发的userInfo
			说明:
			通知点击回调

			
			
			method:onOpenSettingsForNotification
			param:apns下发的userInfo
			
			
			method:onReceivePayload
			param:{"taskId":"","messageId":"","payloadMsg":"","offLine":true 或者 false, "fromGetui":true 或者 false}
			说明:
			个推透传, 从个推通道下发的消息 或者 苹果静默通知		
			
			
			method:onReceiveOnlineState
			param:true 或者 false  个推是否在线
			
			
			method:GeTuiSdkDidRegisterClient
			param:ed1e648a2b35d4b9e7902bd334c1e214 个推cid
			
			
			method:onPushModeResult
			param: {error:"","mode":true或false, "result":true或false}
			说明:
			开发者调用setPushMode的回调
			error：错误信息
			mode：当前推送模式是否打开，true是 false否
			result：当前操作是否成功

			
			method:onAliasResult
			param: {"action":"bindAlias 或者 unbindAlias", "error":"",result:true或false, "sn":""}
			说明:
			开发者调用bindAlias\unbindAlias的回调
			action:bindAlias或unbindAlias
			result:当前操作是否成功
			error:错误描述
		  sn:序列号
		  
			
			method:onSetTagResult
			param:{error:"","sn":"", "result":true或false}
			说明:
			开发者调用setTags的回调
			result:当前操作是否成功
			error:错误描述
		  sn:序列号，默认是tags内容
		  
		  
			method:onLiveActivityResult
			param:{error:"","sn":"", "result":true或false}
			说明:
      开发者调用registerActivityToken的回调
			result:当前操作是否成功
			error:错误描述
		  sn:序列号，默认是tags内容
		  
			
			method:onRegisterPushToStartTokenResult
			param:{error:"","sn":"", "result":true或false}
			说明:
      开发者调用registerPushToStartToken的回调
			result:当前操作是否成功
			error:错误描述
		  sn:序列号，默认是tags内容
		  
      */
      

       //ANDROID 监听事件总览：
       /**
        * 参考Android api文档:https://docs.getui.com/getui/mobile/android/api/ 中GTIntentService类回调函数
        * 
        * //初始化CID回掉
        * method: onReceiveClientId
        * param: "234242"
        * 
        * //在现状态回调
        * method: onReceiveOnlineState
        * param: true
        * 
        * 
        * //PushManager指令失败与成功回调
        * method: onReceiveCommandResult
        * param: {......}
        * 
        * 
        * //通知到达
        * method: onNotificationMessageArrived
        * param: {......}
        * 
        * 
        * //通知点击
        * method: onNotificationMessageClicked
        * param: {......}
        * 
        *
        * //
        * method: debugLog
        * param: "debug下日志"
        * 
        * 
        * 
        */
    }
```





