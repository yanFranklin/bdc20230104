/**
 * author: <a href="mailto:shaoliyao@gtmap.cn>shaoliyao</a>
 * version 1.0.0  2019/10/11
 * describe: 部分解封解压页面
 */
// 获取参数
var processInsId = $.getUrlParam('processInsId');
//当前流程权利类型
var dqlcqllx;
//当前流程不动产单元号的隶属宗地
var lszd;

layui.use(['form', 'table', 'jquery'], function () {
    var form = layui.form;
    var table = layui.table;

    $(function () {
        //绑定回车键
        $(document).keydown(function (event) {
            if (event.keyCode == 13) {
                $("#search").click();
            }
        });

        if (!(navigator.appName == "Microsoft Internet Explorer" && navigator.appVersion.match(/9./i) == "9.")) {
            //监听input点击
            $('.layui-form-item .layui-input-inline').on('click', '.layui-icon-close', function () {
                $(this).siblings('.layui-input').val('');
            });

            $('.layui-form-item .layui-input-inline .layui-input').on('focus', function () {
                $(this).siblings('.layui-icon-close').removeClass('bdc-hide');
            }).on('blur', function () {
                var $this = $(this);
                setTimeout(function () {
                    $this.siblings('.layui-icon-close').addClass('bdc-hide');
                }, 150)
            });
        }

        /**
         * 加载Table数据列表
         */
        table.render({
            elem: '#bfjfjyTable',
            id: 'bfjfjyTable',
            toolbar: '#toolbar',
            title: '选择规则例外信息',
            method: 'POST',
            defaultToolbar: [],
            url: getContextPath() + '/bdcGzyz/bdcgzlw/bdcdyh/fz?gzlslid=' + processInsId,
            request: {
                limitName: 'size' //每页数据量的参数名，默认：limit
            },
            even: true,
            cols: [[
                {type: 'numbers', fixed: 'left'},
                {field: 'BDCDYH', title: '不动产单元号', minWidth: 300},
                {field: 'ZL', title: '坐落', minWidth: 250},
                {field: 'GZMC', title: '规则名称', minWidth: 250},
                {field: 'SLBH', title: '受理编号', hide:true, minWidth: 250},
                {field: 'LWYY', title: '例外原因', minWidth: 250},
                {fixed: 'right', title: '操作', toolbar: '#barDemo', width: 200}
            ]],
            text: {
                none: '未查询到数据'
            },
            autoSort: false,
            page: true,
            limits: [5, 10, 15, 20],
            parseData: function (res) {
                return {
                    code: res.code, //解析接口状态
                    msg: res.msg, //解析提示文本
                    count: res.totalElements, //解析数据长度
                    data: res.content //解析数据列表
                }
            }
        });

        //监听滚动事件
        var scrollTop = 0,
            scrollLeft = 0;
        var tableTop = 0, tableLeft = 0;
        var $nowBtnMore = '';
        $(window).on('scroll', function () {
            scrollTop = $(this).scrollTop();
            scrollLeft = $(this).scrollLeft();

            if ($nowBtnMore != '') {
                if (tableTop + 26 + $nowBtnMore.height() < document.body.offsetHeight) {
                    $nowBtnMore.css({top: tableTop + 26 - scrollTop, left: tableLeft - 30});
                } else {
                    $nowBtnMore.css({top: tableTop - scrollTop - $nowBtnMore.height(), left: tableLeft - 30});
                }
            }
        });

        //删除该条规则例外
        function deletebdcgzlwsh(bdcdyh, gzlslid) {
            if (bdcdyh && gzlslid) {
                var deleteIndex = layer.open({
                    type: 1,
                    skin: 'bdc-small-tips',
                    title: '确认删除',
                    area: ['320px'],
                    content: '是否确认删除？',
                    btn: ['确定', '取消'],
                    btnAlign: 'c',
                    yes: function () {
                        addModel();
                        //确定操作
                        getReturnData("/bdcGzyz/cleanBdcGzlwSh?bdcdyh="+bdcdyh+"&gzlslid="+gzlslid, {bdcdyh: bdcdyh, gzlslid:gzlslid}, 'DELETE', function (data) {
                            removeModal();
                            $("#search").click();
                            ityzl_SHOW_SUCCESS_LAYER("删除成功");
                        }, function (err) {
                            delAjaxErrorMsg(err)
                        });
                        layer.close(deleteIndex);
                    },
                    btn2: function () {
                        //取消
                        layer.close(deleteIndex);
                    }
                });
            } else {
                layer.alert("数据错误")
            }
        }

        // 监听按钮事件
        form.on("submit(search)", function (data) {
            tableReload("bfjfjyTable", data.field);
            return false;
        });


        //监听行工具事件
        table.on('tool(dataTable)', function (obj) {
            var data = obj.data;
            switch (obj.event) {
                case 'delgzlwsh':
                    deletebdcgzlwsh(data.BDCDYH,data.GZLSLID);
                    break;
                case 'view':
                    showDjdcbxx(data.BDCDYH,data.QJGLDM);
                    break;
            }
        });
        //监听行工具事件
        table.on('toolbar(dataTable)', function (obj) {
            switch (obj.event) {
                case 'add':
                    //新增不动产单元
                    var url = getContextPath() + "/view/query/selectGzlwBdcdyh.html?gzlslid=" + processInsId + "&lwcsh=true"
                        + "&qllx=" + dqlcqllx + "&lszd=" + lszd;
                    var index = layer.open({
                        type: 2,
                        title: "选择不动产单元",
                        area: ['1300px', '600px'],
                        fixed: false, //不固定
                        maxmin: true, //开启最大化最小化按钮
                        content: url
                        , end: function (index, layero) {
                            $("#search").click();
                            return false;
                        }
                    });
                    layer.full(index);
                    break;
                case 'setlwyy':
                    //设置例外原因
                    var url = getContextPath() + "/view/xzyzlw/bdcGzlwYy.html?gzlslid=" + processInsId;
                    var index = layer.open({
                        type: 2,
                        title: "例外原因",
                        area: ['500px', '500px'],
                        fixed: false, //不固定
                        maxmin: true, //开启最大化最小化按钮
                        content: url
                        , end: function (index, layero) {
                        }
                    });
                    break;
                case 'clean':
                    // 清空例外信息
                    if (processInsId) {
                        var deleteIndex = layer.open({
                            type: 1,
                            skin: 'bdc-small-tips',
                            title: '确认清空',
                            area: ['320px'],
                            content: '是否确认清空？',
                            btn: ['确定', '取消'],
                            btnAlign: 'c',
                            yes: function () {
                                addModel();
                                //确定操作
                                getReturnData("/bdcGzyz/cleanBdcGzlwSh?gzlslid=" + processInsId, {gzlslid: processInsId}, 'DELETE', function (data) {
                                    removeModal();
                                    $("#search").click();
                                    ityzl_SHOW_SUCCESS_LAYER("删除成功");
                                }, function (err) {
                                    delAjaxErrorMsg(err)
                                });
                                layer.close(deleteIndex);
                            },
                            btn2: function () {
                                //取消
                                layer.close(deleteIndex);
                            }
                        });
                    } else {
                        layer.alert("数据错误")
                    }
                    break;
            }
        });
    });
    loadxx();
});


//加载权利信息（入口）
function loadxx() {
    addModel();
    getReturnData("/slym/ql/zhlc", {processInsId: processInsId, sfcxql: false}, 'GET', function (data) {
        removeModal();
        if (data) {
            dqlcqllx = data[0].qllx;
            if (data[0].bdcXm.bdcdyh && data[0].bdcXm.bdcdyh.length == 28) {
                lszd = data[0].bdcXm.bdcdyh.substring(0, 19);
            }
        }
    }, function (err) {
        delAjaxErrorMsg(err)
    });
}
