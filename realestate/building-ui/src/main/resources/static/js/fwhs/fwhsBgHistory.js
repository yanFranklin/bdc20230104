/**
 * 关闭弹出页面
 */
window.closeWin = function () {
    var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
};
//获取数据
var _data = [];
var fwHsIndex = $("#fwHsIndex").val();
var layerIndex = parent.layer.getFrameIndex(window.name);

addModel();
$.ajax({
    url: "../fwhs/hsbghistory",
    dataType: "json",
    data: {
        fwHsIndex: fwHsIndex
    },
    async: false,
    success: function (data) {
        removeModal();
        _data = data
    }
});

//配置项
reloadTable(_data);


$("#revokeBg").click(function () {
    var length = _data.length;
    var bgDate = _data[length - 1]
    var bgbh = bgDate[0].bgbh;
    var bglx = bgDate[0].bglx;
    var fjh = bgDate[0].fjh;
    var yfwHsIndex = bgDate[0].yfwHsIndex;

    if (bgbh) {
        layer.open({
            content: '是否确定撤销本房屋最后一次变更？'
            , btn: ['确定', '取消']
            , yes: function (index, layero) {
                addModel();
                $.ajax({
                    url: "../fwhsbg/revokebg",
                    dataType: "json",
                    traditional: true,
                    data: {
                        bglx: bglx,
                        bgbh: bgbh,
                        fjh: fjh,
                        yfwHsIndexList: yfwHsIndex
                    },
                    success: function (data) {
                        removeModal();
                        var formLayer = layer;
                        if (parent.layer) {
                            formLayer = parent.layer
                        }
                        var index = parent.layer.getFrameIndex(window.name);
                        if (data.success && data.fwHsIndex) {
                            $.ajax({
                                url: "../fwhs/hsbghistory",
                                dataType: "json",
                                data: {
                                    fwHsIndex: data.fwHsIndex
                                },
                                async: false,
                                success: function (newdata) {
                                    formLayer.msg("撤销成功");
                                    if (newdata && newdata.length > 1) {
                                        _data = newdata
                                        reloadTable(_data);
                                    } else {
                                        parent.layer.close(index);
                                    }
                                }
                            });
                        }
                    },
                    error: function (xhr, status, error) {
                        delAjaxErrorMsg(xhr,this)
                    }
                });
            }
        });
    }
})