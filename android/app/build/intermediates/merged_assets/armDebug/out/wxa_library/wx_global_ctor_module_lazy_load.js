;(function(globalThis) {
    let module = '${__module__}'
    let NativeGlobal = globalThis.NativeGlobal
    let logInfo = function(...args) {
        console.info('wx_global_ctor_module_lazy_load.js >>', module, ...args)
    }
    let logError = function(...args) {
        console.error('wx_global_ctor_module_lazy_load.js >>', module, ...args)
    }

    logInfo('try proxy NativeGlobal')

    if (NativeGlobal && NativeGlobal.initModule) {
        // ok
    } else {
        logError('NativeGlobal not valid')
        return
    }

    if (typeof NativeGlobal[module] !== 'undefined') {
        logError('NativeGlobal', 'already injected', (typeof NativeGlobal[module]))
        return
    }

    // 基础库会在很早的时机获取构造函数并存在闭包里，但new只在开发者访问时才调用，
    // 所以这类模块的懒加载核心就在于对暴露在NativeGlobal的构造函数做占坑处理

    const OriginClassHolder = {}
    const LazyClass = function() {
        let originCtor = OriginClassHolder['class']
        if (typeof originCtor === "undefined") {
            NativeGlobal.initModule(module) // sync op
            originCtor = OriginClassHolder['class']
        }
        return new originCtor()
    }
    Object.defineProperty(NativeGlobal, module, {
        get: function() {
            return LazyClass
        },
        set: function(value) {
            OriginClassHolder['class'] = value
        }
    })

    logInfo('after proxy NativeGlobal', (typeof NativeGlobal[module]))
})(this);