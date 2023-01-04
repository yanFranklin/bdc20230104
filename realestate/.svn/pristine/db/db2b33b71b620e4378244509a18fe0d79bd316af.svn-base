var chooseBdcXm = false;

// 获取字典
function loadZdData() {
    var zdList = [];
    $.ajax({
        url: getContextPath() + "/bdczd",
        type: 'POST',
        dataType: 'json',
        async: false,
        success: function (data) {
            if (isNotBlank(data)) {
                zdList = data;
            }
        }, error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });
    return zdList;
}


// 标红触发后移除红框
layui.use(['jquery'], function () {
    var $ = layui.jquery;
    $(function () {
        $('.bdc-form-div').on('focus', '.layui-input', function () {
            if ($(this).hasClass('layui-form-danger')) {
                $(this).removeClass('layui-form-danger');
            }
            if ($(this).parents('.layui-form-select').siblings().hasClass('layui-form-danger')) {
                $(this).parents('.layui-form-select').siblings().removeClass('layui-form-danger');
            }
        });
        $('.bdc-form-div').on('click', '.xm-input', function () {
            if ($(this).parent().siblings('.xm-hide-input').hasClass('layui-form-danger')) {
                $(this).parent().siblings('.xm-hide-input').removeClass('layui-form-danger');
            }
        });
    });
});

// 设置按钮下拉选项事件
function titleShowUi() {
    $(".print-btn").click(function (e) {
        var e = e || window.event; //浏览器兼容性
        if (e.stopPropagation) {
            e.stopPropagation();
        } else {
            e.cancelBubble = true;
        }

        $("#print").show();
        $("#qzxx").hide();
        setUiWidth($(this), $("#print"));
    });

    $(".print-btn li").click(function () {
        $("#print").hide();
    });

    $(".qzxx-btn").click(function (e) {
        var e = e || window.event; //浏览器兼容性
        if (e.stopPropagation) {
            e.stopPropagation();
        } else {
            e.cancelBubble = true;
        }

        $("#qzxx").show();
        $("#print").hide();
        setUiWidth($(this), $("#qzxx"));
        // 调整下拉框位置
        var Y = $(this).offset().left;
        $("#qzxx").offset({left: Y - 100});
    });

    $(".qzxx-btn li").click(function () {
        $("#qzxx").hide();
    });

    $(document).click(function (e) {
        var e = e || window.event; //浏览器兼容性
        var elem = e.target || e.srcElement;
        while (elem) { //循环判断至跟节点，防止点击的是div子元素
            if (elem.id && (elem.id == 'print' || elem.id == 'qzxx')) {
                return;
            }
            elem = elem.parentNode;
        }
        $("#print").hide();
        $("#qzxx").hide();
    });
}

function setUiWidth(buttonElement, uiElement) {
    var X = buttonElement.offset().top;
    var Y = buttonElement.offset().left;
    uiElement.offset({top: X + 40, left: Y - 0});
    var uiWidth = 90;
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
    uiElement.width(uiWidth);
}