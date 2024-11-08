
;(function(global) {
    try {
        if (typeof global.NativeGlobal !== 'object') {
            global.NativeGlobal = {}
        }
        let NativeGlobal = global.NativeGlobal
        let BindingHolder = undefined
        let nativeModuleLoaded = false
        if (typeof global.SkiaCanvasExternalTexture === 'function') {
            BindingHolder = global
            nativeModuleLoaded = true
        } else if (typeof global.SkiaCanvasNativeGlobal === 'object') {
            BindingHolder = global.SkiaCanvasNativeGlobal
            Object.freeze(BindingHolder)
            nativeModuleLoaded = true
        } else {
            global.SkiaCanvasNativeGlobal = {} //占坑
            BindingHolder = global.SkiaCanvasNativeGlobal
            nativeModuleLoaded = false
        }
        let console = global.console
        console.info('SkiaCanvas', 'nativeModuleLoaded:', (nativeModuleLoaded ? 1 : 0))

        let requireNativeModule = nativeModuleLoaded
            ? function() { /* empty stub */ }
            : function() {
                if (!nativeModuleLoaded) {
                    console.info('SkiaCanvas', 'before NativeGlobal.initModule', ',typeof BindingHolder.SkiaCanvasExternalTexture:', typeof BindingHolder.SkiaCanvasExternalTexture)
                    if (typeof BindingHolder.SkiaCanvasExternalTexture !== 'function') {
                        NativeGlobal.initModule("SkiaCanvas");
                    } else {
                        // insertXWebCanvas事先触发了注入了，这里继续往下走做个freeze
                    }
                    nativeModuleLoaded = true
                    Object.freeze(BindingHolder)
                    console.info('SkiaCanvas', 'after NativeGlobal.initModule', ',typeof BindingHolder.SkiaCanvasExternalTexture:', typeof BindingHolder.SkiaCanvasExternalTexture)
                }
            }

        class SkiaCanvasProperty {
            exportName = ""
            bindingName = ""
            isCtor = false

            constructor(exportName, bindingName, isCtor = false) {
                this.exportName = exportName
                this.bindingName = bindingName
                this.isCtor = isCtor
            }
        }

        [
            new SkiaCanvasProperty("SkiaCanvas", "Canvas", true),
            new SkiaCanvasProperty("SkiaCanvasView", "CanvasView", true),
            new SkiaCanvasProperty("SkiaImages", "Image", true),
            new SkiaCanvasProperty("SkiaImage", "Image", true),
            new SkiaCanvasProperty("SkiaImageData", "ImageData", true),
            new SkiaCanvasProperty("SkiaPath2D", "Path2D", true),
            new SkiaCanvasProperty("SkiaCanvasLoadNewFont", "skiacanvasLoadNewFont", false),
            new SkiaCanvasProperty("SkiaCanvasRequestAnimationFrame", "skiacanvasRequestAnimationFrame", false),
            new SkiaCanvasProperty("SkiaCanvasCancelAnimationFrame", "skiacanvasCancelAnimationFrame", false)
        ].forEach(prop => {
            NativeGlobal[prop.exportName] = function(...args) {
                requireNativeModule()
                let originFunc = BindingHolder[prop.bindingName]
                if (prop.isCtor) {
                    return new originFunc(...args)
                } else {
                    return originFunc(...args)
                }
            }
        })

        // 兼容基础库使用 global.SkiaCanvasExternalTexture 来访问
        if (BindingHolder !== global) {
            global.SkiaCanvasExternalTexture = function(...args) {
                requireNativeModule()
                return new BindingHolder.SkiaCanvasExternalTexture(...args)
            }
        }

        // new
        /*
        NativeGlobal.SkiaCanvas = global.Canvas;
        NativeGlobal.SkiaCanvasView = global.CanvasView;
        NativeGlobal.SkiaImages = global.Image;
        NativeGlobal.SkiaImage = global.Image;
        NativeGlobal.SkiaImageData = global.ImageData;
        NativeGlobal.SkiaPath2D = global.Path2D;
        NativeGlobal.SkiaCanvasLoadNewFont = global.skiacanvasLoadNewFont;
        NativeGlobal.SkiaCanvasRequestAnimationFrame = global.skiacanvasRequestAnimationFrame;
        NativeGlobal.SkiaCanvasCancelAnimationFrame = global.skiacanvasCancelAnimationFrame;
         */

        //NativeGlobal.globalSkiaCanvasView = new global.CanvasView(3);
        //console.log("skia globalSkiaCanvasView", NativeGlobal.globalSkiaCanvasView.id);

        // old
        NativeGlobal.HTMLCanvasView = {
             createView: function(id) {
                 return new NativeGlobal.SkiaCanvasView(id);
             }
        }

        NativeGlobal.HTMLCanvasElement = {
            createElement: function(canvasView) {
                return new NativeGlobal.SkiaCanvas(canvasView.id)
            }
        };

        console.info("skia inject success");
    } catch (err) {
        console.error("skia inject fail", err);
    }
})(this);
