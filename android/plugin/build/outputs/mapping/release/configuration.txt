# The proguard configuration file for the following section is /Users/yangsihao/Downloads/AndroidStudioProjects/donut/getui-donut-plugin/android/plugin/build/intermediates/proguard-files/proguard-android-optimize.txt-4.1.3
# This is a configuration file for ProGuard.
# http://proguard.sourceforge.net/index.html#manual/usage.html
#
# Starting with version 2.2 of the Android plugin for Gradle, this file is distributed together with
# the plugin and unpacked at build-time. The files in $ANDROID_HOME are no longer maintained and
# will be ignored by new version of the Android plugin for Gradle.

# Optimizations: If you don't want to optimize, use the proguard-android.txt configuration file
# instead of this one, which turns off the optimization flags.
# Adding optimization introduces certain risks, since for example not all optimizations performed by
# ProGuard works on all versions of Dalvik.  The following flags turn off various optimizations
# known to have issues, but the list may not be complete or up to date. (The "arithmetic"
# optimization can be used if you are only targeting Android 2.0 or later.)  Make sure you test
# thoroughly if you go this route.
-optimizations !code/simplification/arithmetic,!code/simplification/cast,!field/*,!class/merging/*
-optimizationpasses 5
-allowaccessmodification

-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-verbose

# Preserve some attributes that may be required for reflection.
-keepattributes *Annotation*,Signature,InnerClasses,EnclosingMethod

-keep public class com.google.vending.licensing.ILicensingService
-keep public class com.android.vending.licensing.ILicensingService
-keep public class com.google.android.vending.licensing.ILicensingService
-dontnote com.android.vending.licensing.ILicensingService
-dontnote com.google.vending.licensing.ILicensingService
-dontnote com.google.android.vending.licensing.ILicensingService

# For native methods, see http://proguard.sourceforge.net/manual/examples.html#native
-keepclasseswithmembernames,includedescriptorclasses class * {
    native <methods>;
}

# Keep setters in Views so that animations can still work.
-keepclassmembers public class * extends android.view.View {
    void set*(***);
    *** get*();
}

# We want to keep methods in Activity that could be used in the XML attribute onClick.
-keepclassmembers class * extends android.app.Activity {
    public void *(android.view.View);
}

# For enumeration classes, see http://proguard.sourceforge.net/manual/examples.html#enumerations
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keepclassmembers class * implements android.os.Parcelable {
    public static final ** CREATOR;
}

# Preserve annotated Javascript interface methods.
-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}

# The support libraries contains references to newer platform versions.
# Don't warn about those in case this app is linking against an older
# platform version. We know about them, and they are safe.
-dontnote android.support.**
-dontnote androidx.**
-dontwarn android.support.**
-dontwarn androidx.**

# This class is deprecated, but remains for backward compatibility.
-dontwarn android.util.FloatMath

# Understand the @Keep support annotation.
-keep class android.support.annotation.Keep
-keep class androidx.annotation.Keep

-keep @android.support.annotation.Keep class * {*;}
-keep @androidx.annotation.Keep class * {*;}

-keepclasseswithmembers class * {
    @android.support.annotation.Keep <methods>;
}

-keepclasseswithmembers class * {
    @androidx.annotation.Keep <methods>;
}

-keepclasseswithmembers class * {
    @android.support.annotation.Keep <fields>;
}

-keepclasseswithmembers class * {
    @androidx.annotation.Keep <fields>;
}

-keepclasseswithmembers class * {
    @android.support.annotation.Keep <init>(...);
}

-keepclasseswithmembers class * {
    @androidx.annotation.Keep <init>(...);
}

# These classes are duplicated between android.jar and org.apache.http.legacy.jar.
-dontnote org.apache.http.**
-dontnote android.net.http.**

# These classes are duplicated between android.jar and core-lambda-stubs.jar.
-dontnote java.lang.invoke.**

# End of content from /Users/yangsihao/Downloads/AndroidStudioProjects/donut/getui-donut-plugin/android/plugin/build/intermediates/proguard-files/proguard-android-optimize.txt-4.1.3
# The proguard configuration file for the following section is /Users/yangsihao/Downloads/AndroidStudioProjects/donut/getui-donut-plugin/android/plugin/proguard-rules.pro
-printconfiguration build/outputs/fullProguardConfig.pro

-dontshrink





# End of content from /Users/yangsihao/Downloads/AndroidStudioProjects/donut/getui-donut-plugin/android/plugin/proguard-rules.pro
# The proguard configuration file for the following section is /Users/yangsihao/Downloads/AndroidStudioProjects/donut/getui-donut-plugin/android/plugin/build/intermediates/aapt_proguard_file/release/aapt_rules.txt
# Generated by the gradle plugin
-keep class com.donut.wxed34e654870cba42.PluginIntentService { <init>(...); }
-keep class com.donut.wxed34e654870cba42.PluginPushService { <init>(...); }
-keep class com.getui.gtc.GtcService { <init>(...); }
-keep class com.getui.gtc.base.GtcProvider { <init>(...); }
-keep class com.huawei.agconnect.core.ServiceDiscovery { <init>(...); }
-keep class com.huawei.agconnect.core.provider.AGConnectInitializeProvider { <init>(...); }
-keep class com.huawei.hms.aaid.InitProvider { <init>(...); }
-keep class com.huawei.hms.activity.BridgeActivity { <init>(...); }
-keep class com.huawei.hms.activity.EnableServiceActivity { <init>(...); }
-keep class com.huawei.hms.support.api.push.PushMsgReceiver { <init>(...); }
-keep class com.huawei.hms.support.api.push.PushProvider { <init>(...); }
-keep class com.huawei.hms.support.api.push.PushReceiver { <init>(...); }
-keep class com.huawei.hms.support.api.push.TransActivity { <init>(...); }
-keep class com.huawei.hms.support.api.push.service.HmsMsgService { <init>(...); }
-keep class com.igexin.assist.control.stp.StpService { <init>(...); }
-keep class com.igexin.sdk.FlymePushReceiver { <init>(...); }
-keep class com.igexin.sdk.GService { <init>(...); }
-keep class com.igexin.sdk.GTIntentService { <init>(...); }
-keep class com.igexin.sdk.GetuiActivity { <init>(...); }
-keep class com.igexin.sdk.HmsPushMessageService { <init>(...); }
-keep class com.igexin.sdk.HonorPushMessageService { <init>(...); }
-keep class com.igexin.sdk.MiuiPushReceiver { <init>(...); }
-keep class com.igexin.sdk.OppoAppPushService { <init>(...); }
-keep class com.igexin.sdk.OppoPushService { <init>(...); }
-keep class com.igexin.sdk.PopupActivity { <init>(...); }
-keep class com.igexin.sdk.PushService { <init>(...); }
-keep class com.igexin.sdk.VivoPushMessageReceiver { <init>(...); }
-keep class com.meizu.cloud.pushsdk.MzPushSystemReceiver { <init>(...); }
-keep class com.meizu.cloud.pushsdk.NotificationService { <init>(...); }
-keep class com.vivo.push.sdk.service.CommandClientService { <init>(...); }
-keep class com.xiaomi.mipush.sdk.MessageHandleService { <init>(...); }
-keep class com.xiaomi.mipush.sdk.NotificationClickedActivity { <init>(...); }
-keep class com.xiaomi.mipush.sdk.PushMessageHandler { <init>(...); }

# End of content from /Users/yangsihao/Downloads/AndroidStudioProjects/donut/getui-donut-plugin/android/plugin/build/intermediates/aapt_proguard_file/release/aapt_rules.txt
# The proguard configuration file for the following section is /Users/yangsihao/Downloads/AndroidStudioProjects/donut/getui-donut-plugin/android/plugin/build/intermediates/generated_proguard_file/release/proguard.txt

# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/keith/Library/Android/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

#个推
-dontwarn com.igexin.**
-dontwarn com.huawei.hms.**
-dontwarn com.meizu.**
-dontwarn com.xiaomi.push.**

-keep class com.igexin.** { *; }
-keep class org.json.** { *; }
-keep class com.huawei.hms.** { *; }
-keep class com.meizu.** { *; }
-keep class com.xiaomi.** { *; }
-keep class org.apache.thrift.** { *; }

-keep class com.getui.gtc.** {*;}
#不要添加以下注释[当内置了GBD或GWS时,keep GBD/GWS]在官网版本，否则个推主包在依赖此GTC打包时会无法混淆
#-keep class com.igexin.** {*;}
#-keep class igexin.** {*;}

-keepattributes Signature, InnerClasses, EnclosingMethod, Exceptions, *Annotation*
-keep class com.getui.gtc.dyc.** {*;}


-keepattributes Signature, InnerClasses, EnclosingMethod, Exceptions, *Annotation*
-keep class com.getui.gtc.dim.** {*;}

-keepattributes Signature, InnerClasses, EnclosingMethod, Exceptions, *Annotation*
-keep class com.getui.gtc.base.** {*;}
-keep class com.igexin.base.** {*;}


-keepattributes Signature, InnerClasses, EnclosingMethod, Exceptions, *Annotation*
-keep class com.getui.gtc.dyc.** {*;}


-keepattributes Signature, InnerClasses, EnclosingMethod, Exceptions, *Annotation*
-keep class com.getui.gtc.dim.** {*;}

-keepattributes Signature, InnerClasses, EnclosingMethod, Exceptions, *Annotation*
-keep class com.getui.gtc.base.** {*;}
-keep class com.igexin.base.** {*;}


# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/zhangjinfeng/Library/Android/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

-keep class com.igexin.** { *; }
-dontwarn com.igexin.**

#华为
-keepattributes *Annotation*
-keepattributes Exceptions
-keepattributes InnerClasses
-keepattributes Signature
-keepattributes SourceFile,LineNumberTable

-dontwarn com.huawei.**
-dontwarn com.hianalytics.android.**
-keep class com.hianalytics.android.**{*;}
-keep class com.huawei.updatesdk.**{*;}
-keep class com.huawei.hms.**{*;}
-keep class com.huawei.android.** { *; }
-keep interface com.huawei.android.hms.agent.common.INoProguard {*;}
-keep class * extends com.huawei.android.hms.agent.common.INoProguard {*;}

-keep public class com.huawei.hms.support.api.entity.push.* { *; }

-keep class com.igexin.** { *; }
-keep class com.xiaomi.** { *; }
-keep class com.igexin.sdk.MiuiPushReceiver {*;}
-dontwarn com.igexin.**
-dontwarn com.xiaomi.**

-dontwarn com.igexin.**
-keep class com.igexin.** { *; }

-keep class com.heytap.** { *; }
-keep class com.mcs.aidl.** { *; }
-dontwarn com.heytap.**
-dontwarn com.mcs.**
-dontwarn com.mcs.aidl.**
-keep public class * extends android.app.Service
-keep class com.heytap.msp.** { *;}
-keep class com.mcs.**

# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/zhourh/Library/Android/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
-dontwarn com.igexin.**
-keep class com.igexin.** { *; }
-keep class org.json.** { *; }
-keep class com.vivo.** { *; }
-dontwarn com.vivo.**


# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/xz/Documents/android/adt-bundle-mac-x86_64-20131030/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
-keep class com.igexin.** { *; }
-dontwarn com.igexin.**

-keep class com.meizu.** { *; }
-dontwarn com.meizu.**
-keep class org.apache.http.entity.mime.** { *; }

# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/zhangjinfeng/Library/Android/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

-keep class com.igexin.** { *; }
-dontwarn com.igexin.**

-keep class com.gtups.** { *; }
-dontwarn com.gtups.**
-keep public class * extends android.app.Service


-ignorewarnings
-keepattributes *Annotation*
-keepattributes Exceptions
-keepattributes InnerClasses
-keepattributes Signature
-keepattributes SourceFile,LineNumberTable
-keep class com.hihonor.push.**{*;}

-keep class com.igexin.** { *; }
-dontwarn com.igexin.**

-keep class com.hihonor.** { *; }
-dontwarn com.hihonor.**

# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
-dontwarn com.zx.sdk.api.**

-keep public class com.zx.sdk.api.** {*;}

-dontwarn com.zx.**
-keep class com.zx.** {*;}



-dontwarn com.zx.**
-keep class com.zx.** {*;}



-keep public class com.huawei.hms.support.api.entity.opendevice.* { public *; protected *; }
-keep public class com.huawei.hms.aaid.entity.* { *;}

# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in D:\Android\sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# 防止内部类被混淆，无法访问,务必加上，不然外部引用无法使用内部类

#-renamesourcefileattribute SourceFile
#-keepattributes SourceFile,LineNumberTable

# 防止内部类被混淆，无法访问
-keepattributes Exceptions,InnerClasses,Signature,Deprecated,*Annotation*,EnclosingMethod

# 保留所有重要组件
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver

## 保留所有 Parcelable 实现类的特殊属性.
-keepclassmembers class * implements android.os.Parcelable {
     static android.os.Parcelable$Creator CREATOR;
}

-keepclassmembers enum * {
        public static **[] values();
        public static ** valueOf(java.lang.String);
}

## 用到序列化的实体类
-keepclassmembers class * implements java.io.Serializable {
     static final long serialVersionUID;
         static final java.io.ObjectStreamField[] serialPersistentFields;
     private void writeObject(java.io.ObjectOutputStream);
     private void readObject(java.io.ObjectInputStream);
     java.lang.Object writeReplace();
     java.lang.Object readResolve();
}


## for pushManager
-keep class com.meizu.cloud.pushsdk.PushManager{ *; }
-dontwarn com.meizu.cloud.pushsdk.PushManager

-keep class com.meizu.cloud.pushsdk.notification.MPushMessage{ *; }
-dontwarn com.meizu.cloud.pushsdk.notification.MPushMessage

-keep class com.meizu.cloud.pushsdk.handler.MessageV3 {*;}
-dontwarn com.meizu.cloud.pushsdk.handler.MessageV3

-keep class com.meizu.cloud.pushsdk.handler.MessageV4 {*;}
-dontwarn com.meizu.cloud.pushsdk.handler.MessageV4

-keep class com.meizu.cloud.pushsdk.handler.MzPushMessage {*;}
-dontwarn com.meizu.cloud.pushsdk.handler.MzPushMessage

-keep class com.meizu.cloud.pushsdk.notification.PushNotificationBuilder{ *; }
-dontwarn com.meizu.cloud.pushsdk.notification.PushNotificationBuilder


-keep class com.meizu.cloud.pushsdk.platform.message.BasicPushStatus{*;}
-dontwarn com.meizu.cloud.pushsdk.platform.message.BasicPushStatus

-keep class com.meizu.cloud.pushsdk.platform.message.PushSwitchStatus{*;}
-dontwarn com.meizu.cloud.pushsdk.platform.message.PushSwitchStatus

-keep class com.meizu.cloud.pushsdk.platform.message.RegisterStatus{*;}
-dontwarn com.meizu.cloud.pushsdk.platform.message.RegisterStatus

-keep class com.meizu.cloud.pushsdk.platform.message.SubAliasStatus{*;}
-dontwarn com.meizu.cloud.pushsdk.platform.message.SubAliasStatus

-keep class com.meizu.cloud.pushsdk.platform.message.UnRegisterStatus{*;}
-dontwarn com.meizu.cloud.pushsdk.platform.message.UnRegisterStatus

-keep class com.meizu.cloud.pushsdk.platform.message.SubTagsStatus{*;}
-dontwarn com.meizu.cloud.pushsdk.platform.message.SubTagsStatus

-keep class com.meizu.cloud.pushsdk.platform.message.SubTagsStatus$*{*;}

-keep class com.meizu.cloud.pushsdk.notification.model.styleenum.BaseStyleModel{*;}
-dontwarn com.meizu.cloud.pushsdk.notification.model.styleenum.BaseStyleModel

-keep class com.meizu.cloud.pushsdk.notification.model.styleenum.InnerStyleLayout{*;}
-dontwarn com.meizu.cloud.pushsdk.notification.model.styleenum.InnerStyleLayout

-keep class com.meizu.cloud.pushsdk.notification.model.ActVideoSetting{*;}
-dontwarn com.meizu.cloud.pushsdk.notification.model.ActVideoSetting
-keep class com.meizu.cloud.pushsdk.notification.model.AdvanceSetting{*;}
-dontwarn com.meizu.cloud.pushsdk.notification.model.AdvanceSetting
-keep class com.meizu.cloud.pushsdk.notification.model.AppIconSetting{*;}
-dontwarn com.meizu.cloud.pushsdk.notification.model.AppIconSetting
-keep class com.meizu.cloud.pushsdk.notification.model.NotificationStyle{*;}
-dontwarn com.meizu.cloud.pushsdk.notification.model.NotificationStyle
-keep class com.meizu.cloud.pushsdk.notification.model.NotifyType{*;}
-dontwarn com.meizu.cloud.pushsdk.notification.model.NotifyType
-keep class com.meizu.cloud.pushsdk.notification.model.TimeDisplaySetting{*;}
-dontwarn com.meizu.cloud.pushsdk.notification.model.TimeDisplaySetting
-keep class com.meizu.cloud.pushsdk.notification.model.BrightRemindSetting{*;}
-dontwarn com.meizu.cloud.pushsdk.notification.model.BrightRemindSettin
-keep class com.meizu.cloud.pushsdk.notification.model.AdvanceSettingEx{*;}
-dontwarn com.meizu.cloud.pushsdk.notification.model.AdvanceSettingEx
 -keep class com.meizu.cloud.pushsdk.notification.model.AdvertisementOption{*;}
 -dontwarn com.meizu.cloud.pushsdk.notification.model.AdvertisementOption

-keep class com.meizu.cloud.pushsdk.platform.PlatformMessageSender{
    public void launchStartActivity(android.content.Context, java.lang.String, java.lang.String,java.lang.String);
    public void showQuickNotification(android.content.Context,java.lang.String,java.lang.String);
 }

-keep class com.meizu.cloud.pushsdk.constants.PushConstants{ *; }
-dontwarn com.meizu.cloud.pushsdk.constants.PushConstants

-keep class com.meizu.cloud.pushsdk.util.MzSystemUtils{*;}
-dontwarn com.meizu.cloud.pushsdk.util.MzSystemUtils

-keep class com.meizu.cloud.pushsdk.util.MinSdkChecker{ *;}
-dontwarn com.meizu.cloud.pushsdk.util.MinSdkChecker

-keep class com.meizu.cloud.pushsdk.MzPushMessageReceiver{ *; }
-dontwarn com.meizu.cloud.pushsdk.MzPushMessageReceiver

-keep class com.meizu.cloud.pushinternal.DebugLogger{*;}
-dontwarn com.meizu.cloud.pushinternal.DebugLogger



-keep class com.hihonor.push.framework.aidl.**{*;}
-keep class com.hihonor.push.sdk.common.data.**{*;}

-keep class com.hihonor.push.framework.aidl.**{*;}
-keep class com.hihonor.push.sdk.common.data.**{*;}


-keep interface com.huawei.hms.support.api.transport.DatagramTransport
-keepclasseswithmembers class * implements com.huawei.hms.support.api.transport.DatagramTransport {
  <init>(...);
}
-keep public class com.huawei.hms.support.api.client.* {public *;}
-keep public class com.huawei.hms.support.api.ResolvePendingResult {public *;}
-keep public class com.huawei.hms.support.api.ResolveResult {public *;}
-keep public class com.huawei.hms.support.api.PendingResultImpl {public *;}
-keep public class com.huawei.hms.support.api.ErrorResultImpl {public *;}
-keep public class com.huawei.hms.support.api.entity.core.* {public *;}
-keep public class com.huawei.hms.support.api.entity.auth.AbstractResp {public *;}
-keep public class com.huawei.hms.support.api.entity.auth.AuthCode {public *;}
-keep public class com.huawei.hms.support.api.entity.auth.Scope {public *;}
-keep public class com.huawei.hms.support.api.entity.auth.PermissionInfo {public *;}
-keep class com.huawei.hms.support.api.entity.auth.AuthCode$* {public *;}
-keep public class com.huawei.hms.support.api.core.ConnectService {public *;}
-keep public class com.huawei.hms.support.hianalytics.HiAnalyticsClient {public *;}
-keep public class com.huawei.hms.support.hianalytics.HiAnalyticsUtil {public *;}

-keep class !com.huawei.hms.update.manager.HmsApkReallySizeManager$*,
    !com.huawei.hms.update.manager.ThirdPartyMarketConfigManager$*,
    !com.huawei.hms.update.ui.AppTouchWizard$SizeCallback,
    !com.huawei.hms.update.ui.DownloadProgress$*,
    !com.huawei.hms.update.ui.ConfirmDialogs$*,
    !com.huawei.hms.update.ui.HiappWizard$SizeCallback,
    !com.huawei.hms.update.ui.HwAlertController$CheckedItemAdapter,
    !com.huawei.hms.update.ui.HwAlertController$ListViewScrollListener,
    !com.huawei.hms.update.ui.HwAlertController$BtnHandler,
    !com.huawei.hms.update.ui.SilentUpdateWizard$*,
    !com.huawei.hms.update.ui.PromptDialogs$*,
    !com.huawei.hms.update.ui.ThirdPartyMarketWizard$*,
    !com.huawei.hms.update.ui.UpdateWizard$SizeCallback,
    com.huawei.hms.update.** {public *;}

-keep class !com.huawei.hms.adapter.BaseAdapter$MPendingResultImpl, com.huawei.hms.adapter.** {public *;}
-keep class com.huawei.hms.base.** {public *;}
-keep class com.huawei.hms.availableupdate.** {public *;}

-keep class com.huawei.hms.utils.** {public *;}
-keep public class com.huawei.hms.support.hianalytics.HiAnalyticsClient {public *;}
-keep public class com.huawei.hms.support.hianalytics.HiAnalyticsConstant {public *;}
-keep public class com.huawei.hms.support.hianalytics.HiAnalyticsUtils {public *;}
-keep class com.huawei.hms.support.hianalytics.HiAnalyticsConstant$KeyAndValue {public *;}
-keep class com.huawei.hms.support.hianalytics.HiAnalyticsConstant$HaKey {public *;}
-keep class com.huawei.hms.support.hianalytics.HiAnalyticsConstant$Direction {public *;}
-keep class com.huawei.hms.stats.** {public *;}

-keep class com.huawei.hms.framework.network.grs.BuildConfig {   *; }
-keep class com.huawei.hms.framework.network.grs.GrsApi {   *; }
-keep class com.huawei.hms.framework.network.grs.GrsBaseInfo {   *; }
-keep class com.huawei.hms.framework.network.grs.GrsBaseInfo$* {*;}
-keep class com.huawei.hms.framework.network.grs.IQueryUrlCallBack {   *; }
-keep class com.huawei.hms.framework.network.grs.IQueryUrlsCallBack {   *; }
-keep class com.huawei.hms.framework.network.grs.GrsClient {   *; }
-keep class com.huawei.hms.framework.network.grs.local.model.CountryCodeBean {   *; }
-keep class com.huawei.hms.framework.network.grs.GrsApp {   *; }


-keep class com.huawei.hms.framework.common** {!private *; }


-keep class com.huawei.hianalytics.** {public *;}

-keep class org.apache.http.conn.ssl.BrowserCompatHostnameVerifier {public *;}
-keep class org.apache.http.conn.ssl.StrictHostnameVerifier {public *;}
-keep class org.apache.http.conn.ssl.X509HostnameVerifier {public *;}

-keep class org.apache.http.conn.ssl.SSLSocketFactory {public *;}

-keep public class com.huawei.hms.api.* {*;}
-keep public class com.huawei.hms.common.api.* {*;}
-keep public class com.huawei.hms.common.internal.AutoLifecycleFragment {*;}
-keep public class com.huawei.hms.support.api.client.* {*;}
-keep public class com.huawei.hms.support.api.core.* {*;}
-keep public class com.huawei.hms.support.api.entity.core.* {*;}
-keep public class com.huawei.hms.support.api.ResolvePendingResult {*;}

-keep class com.huawei.hms.ui.** {public *;}
-keep class !com.huawei.hms.activity.ForegroundBusDelegate$*, com.huawei.hms.activity.** {public *;}
-keep class com.huawei.hms.utils.** {public *;}
-keep class com.huawei.hms.base.** {public *;}
-keep public class com.huawei.hms.support.common.ActivityMgr {public *;}

-keep class com.huawei.hms.utils.** {public *;}

-keep class !com.huawei.hms.common.internal.BindResolveClients$*,
    !com.huawei.hms.common.internal.HmsClient$BaseAdapterCallBack,
    !com.huawei.hms.common.HuaweiApi$RequestRunnable,
    com.huawei.hms.common.** {public *;}

-keep class com.huawei.hms.android.** {public *;}
-keep public class com.huawei.hms.support.log.common.Base64 {public *;}
-keep public class com.huawei.hms.support.gentyref.GenericTypeReflector {public *;}
-keep class com.huawei.hms.device.** {public *;}

-keep class org.apache.http.conn.ssl.BrowserCompatHostnameVerifier {public *;}
-keep class org.apache.http.conn.ssl.StrictHostnameVerifier {public *;}
-keep class org.apache.http.conn.ssl.X509HostnameVerifier {public *;}

-keep class org.apache.http.conn.ssl.SSLSocketFactory {public *;}

-keep class com.huawei.hms.base.** {public *;}
-keep public class com.huawei.hms.support.log.HMSDebugger {public *;}
-keep public class com.huawei.hms.support.log.LogLevel {public *;}
-keep public class com.huawei.hms.support.log.HMSLog {public *;}
-keep class com.huawei.hms.log.** {public *;}

-keep class * implements com.huawei.agconnect.core.ServiceRegistrar
-keepclassmembers class **{
    public <init>(android.content.Context,com.huawei.agconnect.AGConnectInstance);
}

# 业务引用打点SDK时，要保留这几个类,用于动态加载打点
-keep class com.huawei.hianalytics.process.HiAnalyticsInstance{
    public <methods>;
}
-keep class com.huawei.hianalytics.process.HiAnalyticsManager{
    public <methods>;
}
-keep class com.huawei.hianalytics.v2.HiAnalytics{
    public <methods>;
}
# 解决HBase打点的能力问题,避免业务混淆后，NetworkKit无法打点的问题
-keep class com.huawei.hms.utils.HMSBIInitializer{
    public static com.huawei.hms.utils.HMSBIInitializer getInstance(android.content.Context);
    public void initBI();
}
-keep class com.huawei.hms.support.hianalytics.HiAnalyticsUtils{
    public static com.huawei.hms.support.hianalytics.HiAnalyticsUtils getInstance();
    public void onNewEvent(android.content.Context,java.lang.String,java.util.Map);
}
-keep public enum com.huawei.hms.network.SceneType {
    *;
}
# 保留所有的public protected的api实现类，httpclient目录下的
-keep class com.huawei.secure.android.common.util.SafeBase64{
 public *;
 protected *;
 }
-keep class com.huawei.secure.android.common.util.SafeString{
 public *;
 protected *;
}

# End of content from /Users/yangsihao/Downloads/AndroidStudioProjects/donut/getui-donut-plugin/android/plugin/build/intermediates/generated_proguard_file/release/proguard.txt
# The proguard configuration file for the following section is /Users/yangsihao/.gradle/caches/transforms-2/files-2.1/24252e9d32bfb7bbe2fc4ae1320ad0ca/material-1.0.0/proguard.txt
# Copyright (C) 2015 The Android Open Source Project
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

# CoordinatorLayout resolves the behaviors of its child components with reflection.
-keep public class * extends androidx.coordinatorlayout.widget.CoordinatorLayout$Behavior {
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>();
}

# Make sure we keep annotations for CoordinatorLayout's DefaultBehavior
-keepattributes *Annotation*

# End of content from /Users/yangsihao/.gradle/caches/transforms-2/files-2.1/24252e9d32bfb7bbe2fc4ae1320ad0ca/material-1.0.0/proguard.txt
# The proguard configuration file for the following section is /Users/yangsihao/.gradle/caches/transforms-2/files-2.1/3629546f2457f52345c7fc61481d0f83/rules/lib/META-INF/proguard/androidx-annotations.pro
-keep @android.support.annotation.Keep class * {*;}

-keepclasseswithmembers class * {
    @android.support.annotation.Keep <methods>;
}

-keepclasseswithmembers class * {
    @android.support.annotation.Keep <fields>;
}

-keepclasseswithmembers class * {
    @android.support.annotation.Keep <init>(...);
}

# End of content from /Users/yangsihao/.gradle/caches/transforms-2/files-2.1/3629546f2457f52345c7fc61481d0f83/rules/lib/META-INF/proguard/androidx-annotations.pro
# The proguard configuration file for the following section is /Users/yangsihao/.gradle/caches/transforms-2/files-2.1/d37116f996a98f4be2a59256b515c2fa/appcompat-1.0.0/proguard.txt
# Copyright (C) 2018 The Android Open Source Project
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

# Ensure that reflectively-loaded inflater is not obfuscated. This can be
# removed when we stop supporting AAPT1 builds.
-keepnames class androidx.appcompat.app.AppCompatViewInflater

# aapt is not able to read app::actionViewClass and app:actionProviderClass to produce proguard
# keep rules. Add a commonly used SearchView to the keep list until b/109831488 is resolved.
-keep class androidx.appcompat.widget.SearchView { <init>(...); }
# End of content from /Users/yangsihao/.gradle/caches/transforms-2/files-2.1/d37116f996a98f4be2a59256b515c2fa/appcompat-1.0.0/proguard.txt
# The proguard configuration file for the following section is /Users/yangsihao/.gradle/caches/transforms-2/files-2.1/95ca15ec93309cc9cc950afc1dc3a51c/recyclerview-1.0.0/proguard.txt
# Copyright (C) 2015 The Android Open Source Project
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

# When layoutManager xml attribute is used, RecyclerView inflates
#LayoutManagers' constructors using reflection.
-keep public class * extends androidx.recyclerview.widget.RecyclerView$LayoutManager {
    public <init>(android.content.Context, android.util.AttributeSet, int, int);
    public <init>();
}

# End of content from /Users/yangsihao/.gradle/caches/transforms-2/files-2.1/95ca15ec93309cc9cc950afc1dc3a51c/recyclerview-1.0.0/proguard.txt
# The proguard configuration file for the following section is /Users/yangsihao/.gradle/caches/transforms-2/files-2.1/dc421bcf41a01193e53cc2504ee980f1/vectordrawable-animated-1.0.0/proguard.txt
# Copyright (C) 2016 The Android Open Source Project
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

# keep setters in VectorDrawables so that animations can still work.
-keepclassmembers class androidx.vectordrawable.graphics.drawable.VectorDrawableCompat$* {
   void set*(***);
   *** get*();
}

# End of content from /Users/yangsihao/.gradle/caches/transforms-2/files-2.1/dc421bcf41a01193e53cc2504ee980f1/vectordrawable-animated-1.0.0/proguard.txt
# The proguard configuration file for the following section is /Users/yangsihao/.gradle/caches/transforms-2/files-2.1/971baffc4a75353005e06f53e84ef35d/transition-1.0.0/proguard.txt
# Copyright (C) 2017 The Android Open Source Project
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

# Keep a field in transition that is used to keep a reference to weakly-referenced object
-keepclassmembers class androidx.transition.ChangeBounds$* extends android.animation.AnimatorListenerAdapter {
  androidx.transition.ChangeBounds$ViewBounds mViewBounds;
}

# End of content from /Users/yangsihao/.gradle/caches/transforms-2/files-2.1/971baffc4a75353005e06f53e84ef35d/transition-1.0.0/proguard.txt
# The proguard configuration file for the following section is /Users/yangsihao/.gradle/caches/transforms-2/files-2.1/5795b53fd7da81afcd5019f1bd020468/coordinatorlayout-1.0.0/proguard.txt
# Copyright (C) 2016 The Android Open Source Project
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

# CoordinatorLayout resolves the behaviors of its child components with reflection.
-keep public class * extends androidx.coordinatorlayout.widget.CoordinatorLayout$Behavior {
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>();
}

# Make sure we keep annotations for CoordinatorLayout's DefaultBehavior and ViewPager's DecorView
-keepattributes *Annotation*

# End of content from /Users/yangsihao/.gradle/caches/transforms-2/files-2.1/5795b53fd7da81afcd5019f1bd020468/coordinatorlayout-1.0.0/proguard.txt
# The proguard configuration file for the following section is /Users/yangsihao/.gradle/caches/transforms-2/files-2.1/d47816087778382cf46e6d3905c4d0d7/core-1.0.0/proguard.txt
# aapt2 is not (yet) keeping FQCNs defined in the appComponentFactory <application> attribute
-keep class androidx.core.app.CoreComponentFactory

# End of content from /Users/yangsihao/.gradle/caches/transforms-2/files-2.1/d47816087778382cf46e6d3905c4d0d7/core-1.0.0/proguard.txt
# The proguard configuration file for the following section is /Users/yangsihao/.gradle/caches/transforms-2/files-2.1/a5f277ca267cff3e919b5a7b2b708c42/versionedparcelable-1.0.0/proguard.txt
-keep public class * extends androidx.versionedparcelable.VersionedParcelable
-keep public class android.support.**Parcelizer { *; }
-keep public class androidx.**Parcelizer { *; }
-keep public class androidx.versionedparcelable.ParcelImpl

# End of content from /Users/yangsihao/.gradle/caches/transforms-2/files-2.1/a5f277ca267cff3e919b5a7b2b708c42/versionedparcelable-1.0.0/proguard.txt
# The proguard configuration file for the following section is /Users/yangsihao/.gradle/caches/transforms-2/files-2.1/2dd9db07d8272179a7ee6aa0ed0fbd8f/lifecycle-runtime-2.0.0/proguard.txt
-keepattributes *Annotation*

-keepclassmembers enum androidx.lifecycle.Lifecycle$Event {
    <fields>;
}

-keep !interface * implements androidx.lifecycle.LifecycleObserver {
}

-keep class * implements androidx.lifecycle.GeneratedAdapter {
    <init>(...);
}

-keepclassmembers class ** {
    @androidx.lifecycle.OnLifecycleEvent *;
}
# End of content from /Users/yangsihao/.gradle/caches/transforms-2/files-2.1/2dd9db07d8272179a7ee6aa0ed0fbd8f/lifecycle-runtime-2.0.0/proguard.txt
# The proguard configuration file for the following section is /Users/yangsihao/.gradle/caches/transforms-2/files-2.1/68d35232f08b20c612457f6eb40beffa/lifecycle-viewmodel-2.0.0/proguard.txt
-keepclassmembers,allowobfuscation class * extends androidx.lifecycle.ViewModel {
    <init>();
}

-keepclassmembers,allowobfuscation class * extends androidx.lifecycle.AndroidViewModel {
    <init>(android.app.Application);
}

# End of content from /Users/yangsihao/.gradle/caches/transforms-2/files-2.1/68d35232f08b20c612457f6eb40beffa/lifecycle-viewmodel-2.0.0/proguard.txt
# The proguard configuration file for the following section is /Users/yangsihao/.gradle/caches/transforms-2/files-2.1/411d427d2612b4c03a26177668845b48/rules/lib/META-INF/proguard/androidx-annotations.pro
-keep,allowobfuscation @interface androidx.annotation.Keep
-keep @androidx.annotation.Keep class * {*;}

-keepclasseswithmembers class * {
    @androidx.annotation.Keep <methods>;
}

-keepclasseswithmembers class * {
    @androidx.annotation.Keep <fields>;
}

-keepclasseswithmembers class * {
    @androidx.annotation.Keep <init>(...);
}

# End of content from /Users/yangsihao/.gradle/caches/transforms-2/files-2.1/411d427d2612b4c03a26177668845b48/rules/lib/META-INF/proguard/androidx-annotations.pro
# The proguard configuration file for the following section is <unknown>
-ignorewarnings
-keep class **.R
-keep class **.R$* {*;}
# End of content from <unknown>