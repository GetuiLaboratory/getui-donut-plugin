#个推SDK donut插件集成文档



# **概述**

个推消息推送SDK在 [donut平台](https://dev.weixin.qq.com)的插件示例，使用此模块实现服务端向客户端推送通知和透传消息的功能等。开发者需要将插件源码上传到donut插件管理库中使用。

流程： 开发者需要先加载插件，后调用插件具体API，比如startSdk


## android API说明：

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
    
		//需要第一时间调用startSDK，避免通知处理不及时
    myPlugin.startSdk({
      'appId': 'xXmjbbab3b5F1m7wAYZoG2',
      'appKey': 'BZF4dANEYr8dwLhj6lRfx2',
      'appSecret': 'yXRS5zRxDt8WhMW8DD8W05'
    })
	
		//模式切换， mode入参0 或 1
    myPlugin.setPushMode({
      'mode': 1
    })

  	//后台模式，入参0 或 1
    myPlugin.runBackgroundEnable({
      'enable': 1
    }) 
  
  	//绑定别名
    myPlugin.bindAlias({
      'alias': 'superman',
      'sn': '0001'
    })
    
    //解绑别名
    myPlugin.unbindAlias({
      'alias': 'superman',
      'sn': '0001'
    })
    
    //设置标签
    myPlugin.setTags({
      'tags': ['t1', 't2', 't2']
    })

    //同步服务端角标
    myPlugin.setBadge({
      'badge': 3
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


		//注册ActivityToken PushToStartToken
    myPlugin.registerPushToStartToken({
      'attribute': 'MyAttribute',
      'token': 'token2',
      'sn': '0002'
    })
    
    //获取原生sdk版本号
    let ver = myPlugin.getVersion()
    console.log(ver)

```

### 插件回调说明：

```js
const listener = (param) => {
      console.log('onMiniPluginEvent listener:', param)
      //监听事件总览：
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
    }
```





