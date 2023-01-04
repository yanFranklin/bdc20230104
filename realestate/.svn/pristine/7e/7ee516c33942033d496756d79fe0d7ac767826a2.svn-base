/**
 * 匹配台账js
 */
layui.use(['table', 'form', 'layer'], function () {
    var table = layui.table,
        form = layui.form,
        $ = layui.jquery,
        layer = layui.layer;
});

/**
 * 列表加载完成回调事件
 * @param res 当前查询结果
 */
function tableHasLoad(res) {
    //处理落宗状态，匹配状态，限制状态的值
    if (res && res.content) {
        res.content.forEach(function (v, index) {
            $(".layui-table-body tr[data-index='" + index + "']")
                .find("td[data-field='CZZT']")
                .find("div")
                .html(getCzZt(v.CZZT));
        });
    }
}

/*
* 获取落宗状态
* */
function getCzZt(czzt) {
    var Zt = '推送中';
    if (czzt == '-1') {
        Zt = "待处理";
    } else if (czzt == '0') {
        Zt = "推送失败";
    } else if (czzt == '1') {
        Zt = "推送成功";
    } else if (czzt == '-2') {
        Zt = "推送中";
    }
    return Zt
}

//详情页面
function sdts(obj, objdata) {
    layui.use(['table', 'form', 'layer'], function () {
        var table = layui.table,
            form = layui.form,
            $ = layui.jquery,
            layer = layui.layer;
        if (objdata.CZZT == '1') {
            layer.alert("已推送");
            return;
        }
        //layer.msg('正在推送', { icon: 16, shade: 0.3, time: 0 });
        layer.alert("推送任务已提交,刷新页面查看结果");
        $.ajax({
            url: "/realestate-inquiry-ui/rest/v1.0/yth/tscjfxx?processInsId=" + objdata.GZLSLID,
            type: 'GET',
            dataType: 'json',
            async: true,
            success: function (data) {
                // if(data.success) {
                //     layer.alert("推送成功");
                // } else {
                //     layer.alert("推送失败");
                // }
            }, error: function (xhr, status, error) {
                layer.close();
            }
        });
        layer.close();
    });
    return;
}