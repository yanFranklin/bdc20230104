var bdcdyh=$("#bdcdyh").val();
var dzwtzm=$("#dzwtzm").val();
var cbzd=$("#cbzd").val();
var jyq=$("#jyq").val();
var qjgldm=$("#qjgldm").val();
if (!bdcdyh) {
    $("#djdcbContent").addClass("layui-hide");
}
if(dzwtzm != 'F'){
    $("#fwxx").addClass("layui-hide");
    $("#fwxxContent").addClass("layui-hide");
}
if(dzwtzm == 'L'){
    $("#sllmxx").removeClass("layui-hide");
}

if(cbzd){
    $("#cbzdxx").removeClass("layui-hide");
    $("#flmjxx").removeClass("layui-hide");
}
if(jyq){
    $("#jyqdkxx").removeClass("layui-hide");

}
var zdList = {};
$.ajax({
    url: "../zd/mul",
    dataType: "json",
    data: {
        zdDoNames: "SZdDldmDO,SZdFwytDO,SZdHxjgDO,SZdTdsyqlxDO,SZdLdlzDO,SZdZjllxDO,SZdCbjyqqdfsDO,SZdBoolDO,SZdCbsyttlxDO,SZdCbdldjDO,SZdCbyzfsDO,SZdMjdwDO,SZdLdqyDO,SZdCbtdytDO,SZdCbyhzgxDO"
    },
    async: false,
    success: function (data) {
        zdList = $.extend({}, data)
    }
});
// 初始化TAB标签
initTab();
titleShowUi();
function initTab(){
    if(tabArr){
        for(var i = 0;i<tabArr.length;i++){
            if (tabArr[i] == 'fcfht' || tabArr[i] == 'fwxx' || tabArr[i] == 'fcfht2' || tabArr[i] == 'fcfhthefei') {
                if ($("#dzwtzm").val() == "F") {
                    $("#" + tabArr[i]).removeClass("layui-hide");
                } else {
                    $("#" + tabArr[i]).addClass("layui-hide");
                }
            } else if ((tabArr[i] == 'zddcb' || tabArr[i] == 'jzbsb') && jyq) {
                $("#" + tabArr[i]).addClass("layui-hide");
            } else {
                $("#" + tabArr[i]).removeClass("layui-hide");
            }
        }
    }
}

//按钮监听
function titleShowUi() {
    //打印点击
    $(".print-btn").click(function (e) {
        var e = e || window.event; //浏览器兼容性
        if (e.stopPropagation) {
            e.stopPropagation();
        } else {
            e.cancelBubble = true;
        }
        $("#jyxx").hide();
        $("#query").hide();
        $("#print").show();
        setUiWidth($(this), $("#print"));
    });

    $(".print-btn li").click(function () {
        $("#print").hide();
    });

    $(document).click(function (e) {
        var e = e || window.event; //浏览器兼容性
        var elem = e.target || e.srcElement;
        while (elem) { //循环判断至跟节点，防止点击的是div子元素
            if (elem.id && (elem.id === 'print' || elem.id === 'jyxx' || elem.id === 'query' || elem.id === 'third-prinbtn-sjd' || elem.id === 'third-prinbtn-sqs')) {
                return;
            }
            $("#print").hide();
            elem = elem.parentNode;
        }
    });
}

function setUiWidth(buttonElement, uiElement) {
    var X = buttonElement.offset().top;
    var Y = buttonElement.offset().left;
    var w = buttonElement.innerWidth();
    var scrollH = $(document).scrollTop();
    if (scrollH > 0) {
        uiElement.css({top: X + 52 - scrollH, right: $('body').width() - Y - w});
    } else {
        uiElement.css({top: X + 42, right: $('body').width() - Y - w});
    }
    var uiWidth = 0;
    var liArray = uiElement.find("li");
    if (liArray.length > 0) {
        var textNumber = 0;
        for (var i = 0; i < liArray.length; i++) {
            var liEl = liArray[i];
            var liText = $(liEl).text();
            if (textNumber < liText.length) {
                textNumber = liText.length;
            }
        }
        var countWidth = (textNumber + 3) * 13;
        if (uiWidth < countWidth) {
            uiWidth = countWidth;
        }
    }
    uiElement.css({"min-width": w, "width": uiWidth + 20});
}