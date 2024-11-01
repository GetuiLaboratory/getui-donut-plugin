#import <Foundation/Foundation.h>
#import <UIKit/UIKit.h>
#import "WeAppNativePlugin.framework/WeAppNativePlugin.h"
#import "GetuiSdkPlugin.h"
#import <GTSDK/GeTuiSdk.h>

__attribute__((constructor))
static void initPlugin() {
    [GetuiSdkPlugin registerPluginAndInit:[[GetuiSdkPlugin alloc] init]];
};

@interface GetuiSdkPlugin()<GeTuiSdkDelegate> {
    BOOL _started;
    NSDictionary *_launchOptions;
}
@end

@implementation GetuiSdkPlugin

// 声明插件ID
WEAPP_DEFINE_PLUGIN_ID(wxed34e654870cba42)

// 声明插件同步方法
//WEAPP_EXPORT_PLUGIN_METHOD_SYNC(mySyncFunc, @selector(mySyncFunc:))

// 声明插件异步方法
//WEAPP_EXPORT_PLUGIN_METHOD_ASYNC(myAsyncFuncwithCallback, @selector(myAsyncFunc:withCallback:))

WEAPP_EXPORT_PLUGIN_METHOD_SYNC(startSdk, @selector(startSdk:))
WEAPP_EXPORT_PLUGIN_METHOD_SYNC(runBackgroundEnable, @selector(runBackgroundEnable:))
WEAPP_EXPORT_PLUGIN_METHOD_SYNC(bindAlias, @selector(bindAlias:))
WEAPP_EXPORT_PLUGIN_METHOD_SYNC(unbindAlias, @selector(unbindAlias:))
WEAPP_EXPORT_PLUGIN_METHOD_SYNC(setTags, @selector(setTags:))
WEAPP_EXPORT_PLUGIN_METHOD_SYNC(setBadge, @selector(setBadge:))
WEAPP_EXPORT_PLUGIN_METHOD_SYNC(setLocalBadge, @selector(setLocalBadge:))
WEAPP_EXPORT_PLUGIN_METHOD_SYNC(setPushMode, @selector(setPushMode:))
WEAPP_EXPORT_PLUGIN_METHOD_SYNC(registerActivityToken, @selector(registerActivityToken:))
WEAPP_EXPORT_PLUGIN_METHOD_SYNC(registerPushToStartToken, @selector(registerPushToStartToken:))
WEAPP_EXPORT_PLUGIN_METHOD_SYNC(getVersion, @selector(getVersion))

- (id)startSdk:(NSDictionary *)param {
    NSLog(@"GTSDK>>>startSdk appId:%@ appKey:%@ appSecret:%@",param[@"appId"],param[@"appKey"],param[@"appSecret"]);
    [GeTuiSdk startSdkWithAppId:param[@"appId"] appKey:param[@"appKey"] appSecret:param[@"appSecret"] delegate:self launchingOptions:_launchOptions ?: @{}];
    
    dispatch_async(dispatch_get_main_queue(), ^{
        [GeTuiSdk registerRemoteNotification: (UNAuthorizationOptionSound | UNAuthorizationOptionAlert | UNAuthorizationOptionBadge)];
    });
    return @(1);
}

- (id)runBackgroundEnable:(NSDictionary *)param {
    NSLog(@"GTSDK>>>runBackgroundEnable %@",param);
    BOOL value = [param[@"enable"] boolValue];
    NSLog(@"runBackgroundEnable %@",@(value));
    [GeTuiSdk runBackgroundEnable:value];
    return @(1);
}

- (id)bindAlias:(NSDictionary *)param {
    NSLog(@"GTSDK>>>bindAlias %@",param);
    [GeTuiSdk bindAlias:param[@"alias"] andSequenceNum:param[@"sn"]];
    return @(1);
}

- (id)unbindAlias:(NSDictionary *)param {
    NSLog(@"GTSDK>>>unbindAlias %@",param);
    [GeTuiSdk unbindAlias:param[@"alias"] andSequenceNum:param[@"sn"] andIsSelf:param[@"isSelf"]];
    return @(1);
}

- (id)setTags:(NSDictionary *)param {
    NSLog(@"GTSDK>>>setTag %@",param);
    [GeTuiSdk setTags:param[@"tags"]];
    return @(1);
}

- (id)setBadge:(NSDictionary *)param {
    NSLog(@"GTSDK>>>setBadge %@",param);
    NSUInteger value = [param[@"badge"] integerValue];
    [GeTuiSdk setBadge:value];
    return @(1);
}

- (id)setLocalBadge:(NSDictionary *)param {
    NSLog(@"GTSDK>>>setLocalBadge %@",param);
    NSUInteger value = [param[@"badge"] integerValue];
    dispatch_async(dispatch_get_main_queue(), ^{
        [UIApplication sharedApplication].applicationIconBadgeNumber = value;
    });
    return @(1);
}

- (id)setPushMode:(NSDictionary *)param {
    NSLog(@"GTSDK>>>setPushMode %@",param);
    BOOL value = [param[@"mode"] boolValue];
    [GeTuiSdk setPushModeForOff:value];
    return @(1);
}

- (id)registerActivityToken:(NSDictionary *)param {
    NSLog(@"GTSDK>>>registerActivityToken %@",param);
    BOOL ret = [GeTuiSdk registerLiveActivity:param[@"aid"] activityToken:param[@"token"] sequenceNum:param[@"sn"]];
    return [NSNumber numberWithBool:ret];
}

- (id)registerPushToStartToken:(NSDictionary *)param {
    NSLog(@"GTSDK>>>registerPushToStartToken %@",param);
    BOOL ret = [GeTuiSdk registerLiveActivity:param[@"attribute"] pushToStartToken:param[@"token"] sequenceNum:param[@"sn"]];
    return [NSNumber numberWithBool:ret];
}

- (id)registerDeviceToken:(NSDictionary *)param {
    NSLog(@"GTSDK>>>registerDeviceToken %@",param);
    BOOL ret = [GeTuiSdk registerDeviceToken:param[@"token"]];
    NSLog(@"registerDeviceToken isSuccess %@ %@ " , @(ret) , param[@"token"]);
    return [NSNumber numberWithBool:ret];
}

- (id)getVersion {
    NSLog(@"GTSDK>>>getVersion");
    return [GeTuiSdk version];
}

- (id)mySyncFunc:(NSDictionary *)param {
    NSLog(@"mySyncFunc %@", param);
        
    return @"mySyncFunc";
}

- (void)myAsyncFunc:(NSDictionary *)param withCallback:(WeAppNativePluginCallback)callback {
    NSLog(@"myAsyncFunc %@", param);
    callback(@{ @"a": @"1", @"b": @[@1, @2], @"c": @3 });
}

- (void)sendMsg:(NSString*)method params:(id)params {
    [self sendMiniPluginEvent:@{ @"method": method, @"param":params}];
}


// 插件初始化方法，在注册插件后会被自动调用
- (void)initPlugin {
    NSLog(@"GTSDK>>>initPlugin");
    [self registerAppDelegateMethod:@selector(application:didFinishLaunchingWithOptions:)];
    [self registerAppDelegateMethod:@selector(application:didRegisterForRemoteNotificationsWithDeviceToken:)];
}

//MARK: - application

- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions {
    NSLog(@"GTSDK>>>didFinishLaunchingWithOptions _launchOptions=%@",launchOptions);
    _launchOptions = launchOptions;
    return YES;
}

- (void)application:(UIApplication *)application didRegisterForRemoteNotificationsWithDeviceToken:(NSData *)deviceToken {
    NSString *token = [self getHexStringForData:deviceToken];
    NSLog(@"GTSDK>>>didRegisterForRemoteNotificationsWithDeviceToken %@",token);
}

- (NSString *)getHexStringForData:(NSData *)data {
    NSUInteger len = [data length];
    char *chars = (char *) [data bytes];
    NSMutableString *hexString = [[NSMutableString alloc] init];
    for (NSUInteger i = 0; i < len; i++) {
        [hexString appendString:[NSString stringWithFormat:@"%0.2hhx", chars[i]]];
    }
    return hexString;
}

//MARK: - GeTuiSdkDelegate

- (void)GetuiSdkGrantAuthorization:(BOOL)granted error:(NSError *)error {
    NSLog(@"GTSDK>>>GetuiSdkGrantAuthorization: granted:%@ error:%@", @(granted), error);
    [self sendMsg:@"onGrantAuthorization" params:@(granted)];
}

/** SDK启动成功返回cid */
- (void)GeTuiSdkDidRegisterClient:(NSString *)clientId {
    // [ GTSdk ]：个推SDK已注册，返回clientId
    NSLog(@"GTSDK>>>RegisterClient:%@", clientId);
    if ([clientId isEqualToString:@""]) {
        return;
    }
    [self sendMsg:@"GeTuiSdkDidRegisterClient" params:clientId];
}

/// 通知展示（iOS10及以上版本）
/// @param center center
/// @param notification notification
/// @param completionHandler completionHandler
- (void)GeTuiSdkNotificationCenter:(UNUserNotificationCenter *)center willPresentNotification:(UNNotification *)notification completionHandler:(void (^)(UNNotificationPresentationOptions))completionHandler {
    NSLog(@"GTSDK>>>willPresentNotification :%@", notification.request.content.userInfo);
    // 根据APP需要，判断是否要提示用户Badge、Sound、Alert
    completionHandler(UNNotificationPresentationOptionBadge | UNNotificationPresentationOptionSound | UNNotificationPresentationOptionAlert);
    [self sendMsg:@"onWillPresentNotification" params:notification.request.content.userInfo];
}

- (void)GeTuiSdkDidReceiveNotification:(NSDictionary *)userInfo notificationCenter:(UNUserNotificationCenter *)center response:(UNNotificationResponse *)response fetchCompletionHandler:(void (^)(UIBackgroundFetchResult))completionHandler {
    NSDate *time = response.notification.date;
//    NSDictionary *userInfo = response.notification.request.content.userInfo;
    NSLog(@"GTSDK>>> %@\nTime:%@\n%@", NSStringFromSelector(_cmd), time, userInfo);
    [self sendMsg:@"onReceiveNotificationResponse" params:userInfo];
    if (completionHandler) {
        completionHandler(UIBackgroundFetchResultNoData);
    }
}

- (void)GeTuiSdkNotificationCenter:(UNUserNotificationCenter *)center openSettingsForNotification:(UNNotification *)notification {
    NSLog(@"GTSDK>>>openSettingsForNotification :%@", notification.request.content.userInfo);
    [self sendMsg:@"onOpenSettingsForNotification" params:notification.request.content.userInfo];
}

- (void)GeTuiSdkDidReceiveSlience:(NSDictionary *)userInfo fromGetui:(BOOL)fromGetui offLine:(BOOL)offLine appId:(NSString *)appId taskId:(NSString *)taskId msgId:(NSString *)msgId fetchCompletionHandler:(void (^)(UIBackgroundFetchResult))completionHandler {
    NSString *payloadMsg = userInfo[@"payload"];
    NSDictionary *payloadMsgDic = @{ @"taskId": taskId ?: @"", @"messageId": msgId ?: @"", @"payloadMsg" : payloadMsg, @"offLine" : @(offLine), @"fromGetui": @(fromGetui)};
    NSLog(@"GTSDK>>>GeTuiSdkDidReceiveSlience:%@", payloadMsgDic);
    [self sendMsg:@"onReceivePayload" params:payloadMsgDic];
}

/** SDK设置推送模式回调 */
- (void)GeTuiSdkDidSetPushMode:(BOOL)isModeOff error:(NSError *)error {
    NSLog(@"GTSDK>>>GeTuiSdkDidSetPushMode isModeOff:%@ error:%@", @(isModeOff), error);
    NSDictionary *dic = @{
        @"mode": @(isModeOff),
        @"result": @(error==nil ? YES : NO),
        @"error": error ? [error localizedDescription] : @""};
    [self sendMsg:@"onPushModeResult" params:dic];
}

- (void)GeTuiSdkDidSetTagsAction:(NSString *)sequenceNum result:(BOOL)isSuccess error:(NSError *)aError {
    NSLog(@"GTSDK>>>GeTuiSdkDidSetTagsAction sn:%@ result:%@ error:%@", sequenceNum, @(isSuccess), aError);
    NSDictionary *dic = @{@"sn": sequenceNum?:@"", @"result": @(isSuccess), @"error": aError ? [aError localizedDescription] : @""};
    [self sendMsg:@"onSetTagResult" params:dic];
}

- (void)GeTuiSdkDidAliasAction:(NSString *)action result:(BOOL)isSuccess sequenceNum:(NSString *)aSn error:(NSError *)aError {
    NSLog(@"GTSDK>>>GeTuiSdkDidAliasAction action: %@ sn:%@ result:%@ error:%@",[kGtResponseBindType isEqualToString:action] ? @"绑定" : @"解绑", aSn, @(isSuccess), aError);
    NSDictionary *dic = @{@"action": action, @"sn": aSn?:@"", @"result": @(isSuccess), @"error": aError ? [aError localizedDescription] : @""};
    [self sendMsg:@"onAliasResult" params:dic];
}

- (void)GetuiSdkDidQueryTag:(NSArray *)tags sequenceNum:(NSString *)sn error:(NSError *)error {
    NSLog(@"GTSDK>>>GetuiSdkDidQueryTag : %@, SN : %@, error :%@", tags, sn, error);
    NSDictionary *dic = @{@"tags": tags, @"sn": sn?:@"", @"error": error ? [error localizedDescription] : @""};
    [self sendMsg:@"onQueryTagResult" params:dic];
}

- (void)GeTuiSdkDidRegisterLiveActivity:(NSString *)sn result:(BOOL)isSuccess error:(NSError *)error {
    NSLog(@"GTSDK>>>GeTuiSdkDidRegisterLiveActivity SN : %@, success: %@, error :%@", sn, @(isSuccess), error);
    NSDictionary *dic = @{@"result" : @(isSuccess), @"sn": sn?:@"", @"error": error ? [error localizedDescription] : @""};
    [self sendMsg:@"onLiveActivityResult" params:dic];
}

-(void)GeTuiSdkDidRegisterPushToStartToken:(NSString *)sn result:(BOOL)isSuccess error:(NSError *)error {
    NSLog(@"GTSDK>>>GeTuiSdkDidRegisterPushToStartToken SN : %@, success: %@, error :%@", sn, @(isSuccess), error);
    NSDictionary *dic = @{@"result" : @(isSuccess), @"sn": sn?:@"", @"error": error ? [error localizedDescription] : @""};
    [self sendMsg:@"onRegisterPushToStartTokenResult" params:dic];
}

- (void)GeTuiSDkDidNotifySdkState:(SdkStatus)status {
    NSLog(@"GTSDK>>>[GetuiSdk Status]:%@", @(status));
    BOOL isOnLine = status == SdkStatusStarted;
    [self sendMsg:@"onReceiveOnlineState" params:@(isOnLine)];
}

@end
