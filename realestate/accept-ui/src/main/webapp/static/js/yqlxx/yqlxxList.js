var zdData;
$(function () {
    /**
     * 获取字典
     */
    $.ajax({
        url: getContextPath() + "/bdczd",
        type: 'POST',
        dataType: 'json',
        async: false,
        success: function (data) {
            if (isNotBlank(data)) {
                zdData = data;
            }
        }, error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });

    layui.use(['form', 'table', 'jquery'], function () {
        var table = layui.table;
        czTemplet = '#bdcdyczTpl'
        if (yqlfj) {
            czTemplet = '#bdcdyfjczTpl'
        }
        //不动产单元号的表头
        var unitTableTitle = [
            {
                field: '', title: '', width: "10%", templet: function (d) {
                    var qllxmc = "";
                    if (d.yqllx) {
                        if (xzqlList.indexOf(d.yqllx) > -1) {
                            qllxmc = changeDmToMc(d.yqllx, zdData.qllx) + "原权利";
                        } else {
                            qllxmc = "产权原权利";
                        }
                    }
                    return qllxmc;
                }
            },
            {field: 'bdcdyh', title: '不动产单元号', width: "20%", templet: '#bdcdyhTpl'},
            {field: 'zl', title: '坐落', width: "25%"},
            {field: 'qlrmc', title: '权利人', width: "10%"},
            {field: 'djxl', title: '登记小类', width: "25%", templet: '#djxlTpl'},
            {field: 'cz', title: '操作', width: "10%", templet: czTemplet, fixed: "right"}
        ];
        var url = getContextPath() + '/slym/ql/listyqlxxbypage';

        var tableConfig = {
            id: 'xmid',
            url: url,
            where: {gzlslid: processInsId},
            defaultToolbar: ['filter'],
            cols: [unitTableTitle],
            done: function () {
                setHeight();
            }
        };

        //加载表格
        loadDataTablbeByUrl('#yqlxxTable', tableConfig);
        //监听行工具事件
        table.on('tool(yqlxxTable)', function (obj) {
            if (yqlfj) {
                var url;
                url = '/realestate-portal-ui/view/popup.html?processinsid=' + obj.data.gzlslid + "&slbh=" + obj.data.slbh;
                var index = layer.open({
                    type: 2,
                    area: ['1150px', '600px'],
                    fixed: false, //不固定
                    title: "原权利附件",
                    maxmin: true,
                    content: url,
                    btnAlign: "c",
                    zIndex: 2147483647,
                    success: function () {
                    },
                    cancel: function () {
                    }
                });
                layer.full(index);
            } else {
                showQl(obj.data.xmid, obj.data.qllx, obj.data.bdcdyfwlx);
            }
        });

    });

});


function changeDjxl(djxl) {
    for (var i = 0; i < zdData.djxl.length; i++) {
        if (djxl == zdData.djxl[i].DM) {
            djxl = zdData.djxl[i].MC
        }
    }
    return djxl;
}


//展现权利信息(受理批量页面）
function showQl(xmid, qllx, bdcdyfwlx) {
    if (xmid !== "" && xmid !== null) {
        qllx = parseInt(qllx);
        bdcdyfwlx = parseInt(bdcdyfwlx);
        var qllxym = getQlxxYm(qllx, bdcdyfwlx);
        var url;
        //展示原权利，不可编辑
        url = "/realestate-register-ui/view/qlxx/" + qllxym + ".html?xmid=" + xmid + "&formStateId=" + formStateId + "&readOnly=true" + "&isCrossOri=false" + "&yqlfj=true";
        var index = layer.open({
            type: 2,
            area: ['1150px', '600px'],
            fixed: false, //不固定
            title: "权利信息",
            maxmin: true,
            content: url,
            btnAlign: "c",
            zIndex: 2147483647,
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