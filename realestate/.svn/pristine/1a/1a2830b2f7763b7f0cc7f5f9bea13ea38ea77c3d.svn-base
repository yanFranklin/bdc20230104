layui.use(['layer', 'table', 'jquery', 'form'], function () {
    var layer = layui.layer;
    var $ = layui.jquery;
    var table = layui.table;
    var form = layui.form;
    /** 配置项*/
    var configUrl = '/realestate-inquiry-ui/rest/v1.0/jk/dtcx/config';
    var dataUrl = '/realestate-inquiry-ui/rest/v1.0/jk/dtcx/list/page';

    $(function () {

        /*时间输入框控件*/
        layui.use('laydate', function () {
            var laydate = layui.laydate;
            //执行一个laydate实例
            laydate.render({
                elem: '#cjqssj'
            });
            laydate.render({
                elem: '#cjjssj'
            });
        });

        var tableConfigs = {
            defaultToolbar: ['filter'],
            even: true,
            toolbar: '#toolbarDemo',
            url: dataUrl,
            request: {
                limitName: 'size' //每页数据量的参数名，默认：limit
                ,loadTotal: true
            }
        };
        // 加载配置文件
        loadTableConfig('#pageTable', tableConfigs, configUrl);

    });

    form.on('submit(search)', function (data) {
        queryObject = data.field;
        tableReload('pageTable', queryObject, dataUrl);
        return false;
    });

    table.on('tool(dataTable)', function (obj) {
        var data = obj.data;
        if (obj.event === 'info') {
            seeInfo(data);
        } else if (obj.event === 'del') {
            delOne(obj);
        } else if (obj.event === 'change') {
            changeOne(obj.data);
        } else if (obj.event === 'export') {
            exportToExcel(obj.data);
        }
    });
});

function importExcel() {
    $("#importExcelFile").val("");
    $("#importExcelFile").click();
}

/**
 * 动态查询文件导入
 */
function importFile(fileObj) {
    addModel();
    $("#uploadFile").ajaxSubmit({
        type: 'post',
        url: '/realestate-inquiry-ui/rest/v1.0/jk/dtcx/config/import/excel',
        success: function (data) {
            removeModal();
            layer.msg("导入成功！");
        },
        error: function (XmlHttpRequest, textStatus, errorThrown) {
            removeModal();
            layer.msg("导入失败！");
        }
    });
    return false;
}

function configAddOrChange(data, model) {
    var content = "config.html";
    var title = "新增配置";
    if (model === "update") {
        if (isNullOrEmpty(data.cxdh)) {
            layer.msg("缺少参数！");
            return false;
        }
        title = "修改配置";
        content = content + "?cxdh=" + data.cxdh;
    }
    layui.use(['layer', 'jquery'], function () {
        var layer = layui.layer;
        layer.open({
            type: 2,
            shade: false,
            shadeClose: true,
            isOutAnim: false,
            content: content,
            area: ['960px', '530px'],
            title: title,
            btn: ['保存', '取消'],
            yes: function (index, layero) {
                var body = layer.getChildFrame('body', index);
                var iframeWin = window[layero.find('iframe')[0]['name']];//得到iframe页的窗口对象，执行iframe页的方法：
                iframeWin.saveAction();//调用子页面的方法，得到子页面返回的ids
            }
            , btn2: function (index, layero) {
                layer.close(index);

            }
            , cancel: function (index) {
                layer.close(index);
            }
            , success: function () {

            }
            , end: function () {
                //window.location.reload();
            }
        });
    });
}

function seeInfo(data) {
    var url = getIP() + "/realestate-inquiry-ui/view/jkdtcx/commonCx.html?cxdh=" + data.cxdh + "&cxmc=" + encodeURI(data.cxmc);
    window.open(encodeURI(url));
}

function exportToExcel(data) {
    var url = "/realestate-inquiry-ui/rest/v1.0/jk/dtcx/config/export/excel?cxdh=" + data.cxdh;
    window.open(url);
}

function changeOne(data) {
    configAddOrChange(data, "update");
}

function delOne(obj) {
    layui.layer.confirm("该操作不可恢复，确认删除？", {
        title: "提示",
        btn: ["删除", "取消"],
        btn1: function (index) {
            var cxid = obj.data.cxid;
            $.ajax({
                url: '/realestate-inquiry-ui/rest/v1.0/jk/dtcx/del/' + cxid,
                type: 'delete',
                success: function (data) {
                    layer.msg("删除成功");
                    obj.del();
                },
                error: function (e) {
                    showErrorInfo();
                }
            });
            layer.close(index);
        },
        btn2: function (index, window) {
            layer.close(index);
        }
    })
}

//设置IP
function getIp() {
    return document.location.protocol + "//" + window.location.host;
}