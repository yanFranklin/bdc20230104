/**
 * @author zenglihuan
 * @create 2018/08/28 16:20
 * @discription 模块的操作权限控制
 * 一、页面的配置如下：
 * 1、authority="add"，这个控件是用于控制新增操作
 * 2、invalid="hidden"，控件无权限时，则隐藏。如果不配置，默认置为失效，不隐藏。
 * 二、页面上的控件（目前只支持按钮和超链接）配置了authority和invalid，只需在js中加载authority模块，即可生效
 * 三、注意事项
 * 1、若保存按钮即要控制新增也要控制更新，在按钮控件上配置authority="add,update"，默认让按钮失效或隐藏。
 * 即使有登录的用户有add权限，按钮也是失效的，需在业务代码中手动控制。
 */

layui.define("form", function(exports){
    var $ = layui.$;
    var that = {};
    that.authorityCode = "";

    var moduleAuthority = {
        load: function(config){
            that.authorityCode = config.authorityCode;
            authorize();
        },
        operationAccess: function(operation){

            if (operation == null || operation == undefined || operation == true) {
                return true;
            }

            if (that.authorityCode.indexOf("all:all") != -1){
                return true;
            }
            var arr = operation.split("_");
            if (arr != null && arr != undefined) {
                for (var j = 0; j < arr.length; j++) {
                    if (that.authorityCode.indexOf(arr[j]) == -1){
                        return false;
                    }
                }
            }

            return true;
        },
        valid: function(element){
            valid(element);
        },
        invalid: function(element){
            invalid(element);
        }
    };

    /**
     * 给按钮或超链接授权
     * @param data
     */
    function authorize(){
        var authorityCode = that.authorityCode;
        var selectors =  $("[authority]");
        if (selectors != null && selectors != undefined && selectors != ""){
            if (authorityCode == null || authorityCode == undefined || authorityCode == ""){
                for (var i = 0; i < selectors.length; i++){
                    var selector = selectors[i];
                    var opt = selector.getAttribute("authority");
                    var element = $("[authority=" + opt + "]");
                    invalid(element);
                }
            }else{
                for (var i = 0; i < selectors.length; i++){
                    var selector = selectors[i];
                    var opt = selector.getAttribute("authority");
                    if (opt.indexOf('_') == -1) {
                        var element = $("[authority=" + opt + "]");
                        if (authorityCode.indexOf(opt) > -1){
                            valid(element);
                        }else{
                            invalid(element);
                        }
                    } else {
                       var arr = opt.split("_");
                       if (arr != null && arr != undefined) {
                           for (var j = 0; j < arr.length; j++) {
                               if (authorityCode.indexOf(arr[j]) == -1){
                                   var element = $("[authority=" + opt + "]");
                                   invalid(element);
                                   break;
                               }
                           }
                       }
                    }
                }
            }
        }else {
            return;
        }

    }

    /**
     * 所有operations失效
     * @param operations
     */
    function invalidAll(operations){
        for(var i = 0; i<operations.length; i++){
            var element = $("[authority$=" + operations[i] + "]");
            invalid(element);
        }
    }

    /**
     * element置为有效
     * @param element
     */
    function valid(element){
        if(element.attr("invalid") == "hidden"){
            show(element);
        }else{
            if(element.prop("tagName") == "BUTTON"){
                enableBtn(element);
            }else if(element.prop("tagName") == "A"){
                enableA(element);
            }
        }
    }

    /**
     * element置为无效
     * @param element
     */
    function invalid(element){
        if(element.attr("invalid") == "hidden"){
            hide(element);
        }else{
            if(element.prop("tagName") == "BUTTON"){
                disableBtn(element);
            }else if(element.prop("tagName") == "A"){
                disableA(element);
            }
        }
    }

    function hide(element){
        element.css("display", "none");
    }

    function show(element){
        element.css("display", "inline-block");
    }

    /**
     * 超链接失效
     * @param element
     */
    function disableA(element){
        element.css("cursor", "pointer");
        element.css("pointer-events", "none");
        element.css("background-color", "#D2D2D2");
    }

    function enableA(element){
        element.css("cursor", "auto");
        element.css("pointer-events", "auto");
    }

    function disableBtn(element){
        element.addClass("layui-btn-disabled");
        element.attr("disabled", "disabled");
        element.css("background-color", "#FBFBFB");
    }

    function enableBtn(element){
        element.removeClass("layui-btn-disabled");
        element.removeAttr("disabled");
        element.css("background-color", "#6D9DFD");
    }

    exports("moduleAuthority", moduleAuthority);
});