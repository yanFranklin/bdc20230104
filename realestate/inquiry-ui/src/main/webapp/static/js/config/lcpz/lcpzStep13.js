/*
* 流程配置上报业务配置
* */
layui.config({
    base: '../../../static/' //静态资源所在路径
}).extend({response: 'lib/bdcui/js/response'});
var gzldyid = $.getUrlParam('gzldyid');
var djxl = $.getUrlParam('djxl');
var qllx = $.getUrlParam('qllx');
var bdclx = "";
var djlx = "";
var table;
var BASE_URL = '/realestate-inquiry-ui/rest/v1.0';
layui.use(['jquery', 'form', 'response', 'table', 'laydate', 'layer'], function () {
    var $ = layui.jquery,
        layer = layui.layer,
        formSelects = layui.formSelects,
        form = layui.form;
    table = layui.table;
    var bdcdyfwlxList = [];
    $(function () {
        $('.bdc-content').css('min-height', $('body').height() - 56);
    });
    formSelects.config('selectSfsb', {
        keyName: 'MC',            //自定义返回数据中name的key, 默认 name
        keyVal: 'DM'            //自定义返回数据中value的key, 默认 value
    }, true);
    var zdData = {};
    window.onbeforeunload = function () {
        window.opener.location.reload();
    };
    /**
     * 加载字典
     */
    $.ajax({
        url: BASE_URL + "/lcpz/zdxx",
        type: "POST",
        data: 'sf,jryw,bdclx,bdcdyfwlx',
        contentType: 'application/json',
        dataType: "json",
        async: false,
        success: function (data) {
            if (data) {
                zdData = data;
                formSelects.data('selectSfsb', 'local', {arr: data.sf});
            }
        },
        error: function (e) {
            response.fail(e, '');
        }
    });

    if (gzldyid !== undefined && gzldyid !== null && djxl !== undefined && djxl != null) {
        //页面展现是否上报
        queryDjxlPz(gzldyid, djxl);

        //获取bdcdyfwlx
        queryXztzPz();
        $('#gzldyid').val(gzldyid);
    }

    /**
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取登记小类配置
     */
    function queryDjxlPz(gzldyid, djxl) {
        $.ajax({
            url: BASE_URL + "/lcpz/bdcDjxlPz",
            type: "GET",
            data: {gzldyid: gzldyid, djxl: djxl},
            contentType: 'application/json',
            dataType: "json",
            async: false,
            success: function (data) {
                if (data) {
                    formSelects.value('selectSfsb', [String(data.sfsb)]);
                    bdclx = data.bdclx;
                    $("#pzid").val(data.pzid);
                    if (data.sfsb === 0 || data.sfsb === 1) {
                        enableFinishBtn();
                    }
                }
            },
            error: function (e) {
                response.fail(e, '');
            }
        });
    }

    function queryXztzPz() {
        //获取配置的不动产单元房屋类型
        if (gzldyid == undefined || gzldyid == null) {
            return false;
        }
        $.ajax({
            url: BASE_URL + '/lcpz/bdcSlxztz',
            type: "GET",
            data: {gzldyid: gzldyid},
            dataType: "json",
            async: false,
            success: function (data) {
                if (data) {
                    if (data.bdcdyfwlx != null) {
                        var bdcdyfwlxs = data.bdcdyfwlx.split(',');
                        for (var i = 0; i < bdcdyfwlxs.length; i++) {
                            if (bdcdyfwlxs[i] === "hs" || bdcdyfwlxs[i] === "ychs") {
                                bdcdyfwlxList.push("4")
                            } else if (bdcdyfwlxs[i] === "ljz") {
                                bdcdyfwlxList.push("2");
                            } else if (bdcdyfwlxs[i] === "xmxx") {
                                bdcdyfwlxList.push("1");
                            }
                        }
                    }
                }
            }
        });
    }


    /**
     * 加载Table数据列表
     */
    table.render({
        elem: '#jrpzTable',
        toolbar: '#toolbar',
        title: '接入配置列表',
        defaultToolbar: ['filter'],
        url: BASE_URL + "/lcpz/jrpz",
        where: {djxl: djxl, qllx: qllx, bdclx: bdclx, bdcdyfwlxList: bdcdyfwlxList.join(",")},
        request: {
            limitName: 'size' //每页数据量的参数名，默认：limit
        },
        cellMinWidth: 80,
        even: true,
        cols: [[
            {type: 'checkbox', fixed: 'left'},
            {field: 'bdcqllxmc', title: '权利类型', width: '15%'},
            {field: 'bdcdjlxmc', title: '登记小类名称', width: '25%'},
            {
                field: 'ywdm', title: '接入业务名称', width: '20%', templet: function (d) {
                    return changeDmToMc(d.ywdm, zdData.jryw);
                }
            },
            {field: 'ywfwdm', title: '业务服务代码', width: '15%'},
            {
                field: 'bdclx', title: '不动产类型', width: '10%', templet: function (d) {
                    return changeDmToMc(d.bdclx, zdData.bdclx);
                }
            },
            {
                field: 'sfdz', title: '不动产单元房屋类型', templet: function (d) {
                    return changeDmToMc(d.sfdz, zdData.bdcdyfwlx);
                }
            }
        ]],
        text: {
            none: '未查询到数据'
        },
        autoSort: false,
        page: true,
        parseData: function (res) {
            return {
                code: res.code, //解析接口状态
                msg: res.msg, //解析提示文本
                count: res.totalElements, //解析数据长度
                data: res.content //解析数据列表
            }
        },
        done: function () {
            $('.layui-table-tool-self').css('right', $('.bdc-export-tools').width() + 17 + 'px');

            if ($('.bdc-table-box .layui-table-main>.layui-table').height() == 0) {
                $('.layui-table-body .layui-none').html('<img src="../../../static/lib/registerui/image/table-none.png" alt="">无数据');
            } else {
                $('.layui-table-body').height($('.bdc-table-box').height() - 135);
                $('.layui-table-fixed .layui-table-body').height($('.bdc-table-box').height() - 135 - 17);
            }
        }
    });

    //头工具栏事件
    table.on('toolbar(jrpzTable)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id);
        switch (obj.event) {
            case 'add':
                addJrywPz();
                break;
            case 'edit':
                editJrywPz(checkStatus.data);
                break;
            case 'delete':
                deleteJrywPz(checkStatus.data);
                break;
        }
    });


    $('.beforestep').on('click', function () {
        window.location.href = 'step12.html?gzldyid=' + gzldyid + '&djxl=' + djxl + '&qllx=' + qllx;
    });
    $('.finish').on('click', function () {
        var sfsb = formSelects.value('selectSfsb', 'val')[0];
        if (sfsb != 0 && sfsb != 1) {
            layer.alert("未选择是否上报不允许关闭！", {title: '提示'});
            return false;
        }
        window.parent.close();
        window.opener.location.href = window.opener.location.href;
    });

    formSelects.on('selectSfsb', function (id, vals, val, isAdd, isDisabled) {
        //id:           点击select的id
        //vals:         当前select已选中的值
        //val:          当前select点击的值
        //isAdd:        当前操作选中or取消
        //isDisabled:   当前选项是否是disabled
    });

    form.on('submit(submitBtn)', function (data) {
        addModel();
        var djxlpz = {};
        djxlpz.pzid = $("#pzid").val();
        djxlpz.sfsb = formSelects.value('selectSfsb', 'val')[0];
        if (djxlpz.pzid) {
            //保存前先去校验数据是否正确
            var pzidList = [];
            pzidList.push(djxlpz.pzid);
            $.ajax({
                url: BASE_URL + "/lcpz/bdcDjxlPz/sfsb",
                type: "PUT",
                data: JSON.stringify(data.field),
                contentType: 'application/json',
                dataType: "json",
                success: function (data) {
                    // 提交成功会返回更新数量，正常应该大于0，失败会返回状态信息JSON
                    // $('#pzid').val(data.pzid);
                    //保存完后验证是否有问题
                    enableFinishBtn();
                    layer.msg('<img src="../../../static/lib/bdcui/images/success-small.png" alt="">提交成功', {
                            time: 1000
                        }
                    )
                    $.ajax({
                        url: BASE_URL + "/lcpz/sbpzjy",
                        type: "POST",
                        data: JSON.stringify(pzidList),
                        contentType: 'application/json',
                        dataType: "json",
                        async: false,
                        success: function (data) {
                            removeModal();
                            if (data > 0) {
                                layer.alert("当前接入业务配置存在问题，请到校验台账查看详情！", {title: '提示'});
                                return false;
                            }
                        },
                        error: function () {
                            layer.alert("检查失败，请重试！", {title: '提示'});
                        }
                    });

                },
                error: function (e) {
                    delAjaxErrorMsg(e);
                }
            });
        }
        // 禁止提交后刷新
        return false;
    });

});

//字典项代码转名称
function changeDmToMc(dm, zdList) {
    var mc = "";
    if (isNotBlank(zdList)) {
        for (var i = 0; i < zdList.length; i++) {
            var zd = zdList[i];
            if (dm == zd.DM) {
                mc = zd.MC;
                break;
            }
        }
    }
    return mc;

}

//新增接入业务配置
function addJrywPz() {
    addModel('');
    layer.open({
        title: '新增接入业务配置',
        type: 2,
        area: ['960px', '460px'],
        content: ["jrywpz/addOrEditJrywPz.html?action=add&bdclx=" + bdclx + "&qllx=" + qllx + "&djxl=" + djxl, 'yes']
        , end: function () {
            removeModal();
            table.reload('jrpzTable');
        }
    });
}

//编辑额接入业务配置
function editJrywPz(data) {
    if (!data || data.length != 1) {
        layer.alert("请选择需要编辑的某一条记录！", {title: '提示'});
        return;
    }
    addModel('');
    layer.open({
        type: 2,
        title: '编辑接入业务配置',
        anim: -1,
        shadeClose: true,
        maxmin: true,
        shade: false,
        area: ['960px', '460px'],
        offset: 'auto',
        content: ["jrywpz/addOrEditJrywPz.html?action=edit&djxl=" + djxl, 'yes'],
        success: function (layero, index) {
            var iframe = window['layui-layer-iframe' + index];
            iframe.setData(data[0]);
        },
        end: function () {
            removeModal();
            table.reload('jrpzTable');
        }
    });
}

//删除接入业务配置
function deleteJrywPz(data) {
    if (data.length === 0) {
        layer.alert("请先勾选数据再删除！", {title: '提示'});
        return false;
    }
    var idList = [];
    for (var i = 0; i < data.length; i++) {
        idList.push(data[i].id);
    }
    var deleteIndex = layer.open({
        type: 1,
        title: '确认删除',
        area: ['320px'],
        skin: 'bdc-small-tips',
        content: '确定要删除所选接入业务配置？',
        btn: ['确定', '取消'],
        btnAlign: 'c',
        yes: function () {
            $.ajax({
                url: BASE_URL + "/lcpz/jrywpz",
                type: "DELETE",
                data: JSON.stringify(idList),
                contentType: 'application/json',
                dataType: "json",
                success: function (data) {
                    layer.msg('<img src="../../../static/lib/bdcui/images/success-small.png" alt="">删除成功', {
                        time: 1000
                    });
                    table.reload('jrpzTable');
                },
                error: function () {
                    layer.alert("删除失败，请重试！", {title: '提示'});
                }
            });
            layer.close(deleteIndex);
        },
        btn2: function () {
            //取消
        }
    });

}
