/**
 * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @description 上报销账台账js
 * @date : 2022/6/21 8:59
 */

var form, $, table, layer;
var type = GetQueryString("type");
var moduleCode = GetQueryString("moduleCode");
var reverseList = ['zl'];
var zdList = [];
layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl', 'laydate'], function () {
    form = layui.form;
    $ = layui.jquery;
    table = layui.table;
    layer = layui.layer;

    var element = layui.element;
    var laytpl = layui.laytpl;
    var laydate = layui.laydate;

    $(function () {
        //初始化日期控件
        laydate.render({
            elem: '#kssj'
            , type: 'datetime'
        });
        laydate.render({
            elem: '#jssj'
            , type: 'datetime'
        });

        zdList = getMulZdList("xzzt,xzbwlx");
        if (isNotBlank(zdList)) {
            $.each(zdList.xzzt, function (index, item) {
                $('#xzzt').append(new Option(item.MC, item.DM));// 下拉菜单里添加元素
            });
            $.each(zdList.xzbwlx, function (index, item) {
                $('#xzbwlx').append(new Option(item.MC, item.DM));// 下拉菜单里添加元素
            });
            form.render();
        }


        var tableConfig = {
            toolbar: "#toolbarDemo"
            , url: '../accessLog/sbxz/page'
            , limits: [10, 50, 100, 200, 500]
            , cols: [[
                {type: 'checkbox', width: 50, fixed: 'left'},
                {field: 'xh', title: '序号', width: 60},
                {field: 'bwid', title: '报文id', width: 200},
                {field: 'slbh', title: '受理编号', width: 200},
                {field: 'bdcdyh', title: '不动产单元号', width: 250},
                {field: 'zszmh', title: '证书证明号', width: 200},
                //{field: 'SQLX', title: '申请类型'},
                {
                    field: 'djsj', title: '登簿时间', width: 200, templet: function (d) {
                        if (d.djsj) {
                            return formatDate(d.djsj);
                        }
                        return '';
                    }
                },
                {
                    field: 'jrsj', title: '接入时间', width: 200, templet: function (d) {
                        if (d.jrsj) {
                            return formatDate(d.jrsj);
                        }
                        return '';
                    }
                },
                {field: 'xyxx', title: '响应信息'},
                {
                    field: 'xzbwlx', title: '销账报文类型', width: 120, templet: function (d) {
                        return changeDmToMc(d.xzbwlx, zdList.xzbwlx)
                    }
                },
                {
                    field: 'xzzt', title: '销账状态', width: 100, templet: function (d) {
                        return changeDmToMc(d.xzzt, zdList.xzzt)
                    }
                }
            ]],
            data: [],
            page: true,
            done: function () {
                // reverseTableCell(reverseList);
                $('.layui-table-tool-self').css('right', $('.bdc-export-tools').width() + 17 + 'px');

                if ($('.bdc-table-box .layui-table-main>.layui-table').height() == 0) {
                    $('.layui-table-body').height('60px');
                    $('.layui-table-fixed .layui-table-body').height('60px');
                    $('.layui-table-body .layui-none').html('<img src="../static/images/table-none.png" alt="">无数据');
                } else {
                    $('.layui-table-body').height($('.bdc-table-box').height() - 131);
                    $('.layui-table-fixed .layui-table-body').height($('.bdc-table-box').height() - 131 - 17);
                }
                setElementAttrInTableByModuleAuthority(moduleCode);
            }
        };

        loadDataTablbeByUrl("#sbxzTable", tableConfig);
        //提交表单
        form.on("submit(query)", function (data) {
            // 判断必填条件
            var count = 0;
            $(".required").each(function (i) {
                if (!isNullOrEmpty($(this).val())) {
                    count += 1;
                }
            });
            if (0 === count) {
                layer.msg('<img src="../static/lib/bdcui/images/success-small.png" alt="">请输入必要查询条件');
                return false;
            }
            layui.use(['table'], function () {
                var table = layui.table;
                table.reload("sbxzTable", {
                    where: data.field,
                    page: {
                        curr: 1, //重新从第 1 页开始
                        limits: [10, 50, 100, 200, 500]
                    },
                    url: '../accessLog/sbxz/page'
                });
            });
            return false;
        });
        //头工具栏事件
        table.on('toolbar(sbxzTable)', function (obj) {
            if (obj.event != "LAYTABLE_COLS") {
                var checkStatus = table.checkStatus(obj.config.id);
                var data = checkStatus.data;
                if (data && data.length > 0) {
                    if (obj.event === "access") {
                        sb(data);
                    }
                } else {
                    layer.alert("请选择一条数据进行操作")
                }
            }
        });

    });
});

/*
* 上报按钮操作
* */
function sb(data) {
    addModel();
    var xmidList = [];
    var zzxzList = [];
    //判断xzzt 是否为2 ，如果是2 直接页面提示，不上报
    for (var i = 0; i < data.length; i++) {
        if (xmidList.indexOf(data[i].ywh) === -1) {
            if (data[i].xzzt !== "2") {
                xmidList.push(data[i].ywh);
            } else {
                zzxzList.push(data[i].slbh);
            }
        }
    }
    if (isNotBlank(zzxzList) && zzxzList.length > 0) {
        removeModal();
        warnMsg("以下受理编号正在销账，不允许上报<br>" + zzxzList);
        return false;
    }
    $.ajax({
        url: "/realestate-report-alone/accessLog/sb",
        type: "POST",
        data: JSON.stringify(xmidList),
        contentType: 'application/json',
        async: false,
        dataType: "json",
        success: function (data) {
            removeModal();
            successMsg("上报成功！");
            layui.table.reload('sbxzTable', {page: {curr: 1}});
        },
        error: function (err) {
            removeModal();
            delAjaxErrorMsg(err)
        }
    });
}


/*
* 全部上报按钮操作
* */
function qbsb(data) {
    addModel();
    $.ajax({
        url: "/realestate-inquiry-ui/accessLog/qbsb",
        type: "POST",
        contentType: 'application/json',
        async: false,
        dataType: "json",
        success: function (data) {
            removeModal();
            successMsg("上报成功！");
            layui.table.reload('pageTable', {page: {curr: 1}});
        },
        error: function (err) {
            removeModal();
            delAjaxErrorMsg(err)
        }
    });
}