
var $;
var layer;
layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl'], function () {
    var $ = layui.jquery,
        layer = layui.layer;
});


/**
 * 点击明细按钮
 */
function ts(obj, data) {
    debugger;
    var url = "/realestate-inquiry-ui/sdqgh/nantong/sdq?gzlslid="+data.GZLSLID+"&ywlx=1";
    $.ajax({
        url: url,
        type: "GET",
        async: true,
        success: function (data) {
            // 申报成功
            if (data && data.blzt == 3) {
                var successMsg = "办理成功!";
                document.getElementById("span_id").setAttribute("style", "font-size: 30px; color: green");
                popBox(successMsg);
                xgdy(obj,"办理成功",null);
            } else if (data && data.blzt == 4) {
                var failMsg = data.sdqshyj;
                document.getElementById("span_id").setAttribute("style", "font-size: 30px; color: red");
                popBox(failMsg);
                xgdy(obj,"办理失败",failMsg);
            }
        }
    });

}


/*弹窗提示*/
function popBox(msg) {
    //获取背景div
    var popBox = document.getElementById("popBox");
    //设置显示内容
    document.getElementById("span_id").innerHTML = msg;
    //设置定时关闭时间 此处为5s
    setTimeout("document.getElementById('popBox').style.display='none'",1000);
    //设置为可见
    popBox.style.display = "block";
};

//改变layui表格单元格显示内容
function xgdy(obj,blzt,shyj) {
    debugger;
    var blztDom = $("tr > th[data-field=\"BLZT\"]");
    var blztWidth = blztDom.width();
    // var shyjDom = $("tr > th[data-field=\"SDQSHYJ\"]");
    // var shyjWidth = shyjDom.width();
    var selector = obj.tr.selector;
    if (isNotBlank(blzt)) {
    // .layui-table-body tr[data-index="3"] td[data-field="BLZT"]
    // .layui-table-header .layui-table tr[data-index="0"] td[data-field="BLZT"]
        var selectorBlzt = selector + " td[data-field=\"BLZT\"]";
        var blztHtml = $(selectorBlzt);
        blztHtml[0].innerText = blzt;
        blztHtml.width(blztWidth);
    }
    // if (isNotBlank(shyj)) {
    //     var selectorShyj = selector + " td[data-field=\"SDQSHYJ\"]";
    //     var shyjHtml = $(selectorShyj);
    //     shyjHtml[0].innerText = shyj;
    //     shyjHtml.width(shyjWidth);
    // }

}

