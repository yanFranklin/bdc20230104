var layer;
layui.use(['layer'], function () {
    layer = layui.layer;
});
$(function () {
    getReturnData("/slym/ql/listyqlxxbypage", {gzlslid: gzlslid}, "GET", function (data) {
        console.log(data);
        showQl(data.content[0].xmid, data.content[0].qllx, data.content[0].bdcdyfwlx)
    }, function () {

    });

    //展现权利信息(受理批量页面）
    function showQl(xmid, qllx, bdcdyfwlx) {
        if (xmid !== "" && xmid !== null) {
            qllx = parseInt(qllx);
            bdcdyfwlx = parseInt(bdcdyfwlx);
            var qllxym = getQlxxYm(qllx, bdcdyfwlx);
            var url;
            //展示原权利，不可编辑
            url = "/realestate-register-ui/view/qlxx/" + qllxym + ".html?xmid=" + xmid + "&formStateId=" + formStateId + "&readOnly=true" + "&isCrossOri=false";
            var index = layer.open({
                type: 2,
                area: ['1150px', '600px'],
                fixed: false, //不固定
                title: "权利信息",
                maxmin: false,
                content: url,
                btnAlign: "c",
                zIndex: 2147483647,
                closeBtn: 0,
                success: function () {
                },
                cancel: function () {
                }
            });
            layer.full(index);
        } else {
            ityzl_SHOW_INFO_LAYER("无原权利信息")
        }
    }
});