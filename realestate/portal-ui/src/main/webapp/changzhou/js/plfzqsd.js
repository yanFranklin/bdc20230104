/**
 * describe: 批量发证签收单
 */
var reverseList = ['zl'];
var taskName = "发证";
var sessionKey = "plfzqsd";
layui.use(['jquery', 'form', 'table'], function () {
    //获取字典
    var $ = layui.jquery,
        form = layui.form,
        table = layui.table;
    // 当前页数据
    var currentPageData = new Array();

    layui.sessionData('checkedPlfzQsdDataLc', null);

    $(function () {
        renderTable();

        //6. 输入框删除图标
        if (!(navigator.appName == "Microsoft Internet Explorer" && navigator.appVersion.match(/9./i) == "9.")) {
            //监听input点击
            $('.layui-form-item .layui-input-inline').on('click', '.layui-icon-close', function () {
                $(this).siblings('.layui-input').val('');
                $(this).siblings().find('.layui-input').val('');
            });

            $('.layui-form-item .layui-input-inline .layui-input').on('focus', function () {
                $(this).siblings('.layui-icon-close').removeClass('bdc-hide');
                $(this).parents('.layui-input-inline').find('.layui-icon-close').removeClass('bdc-hide');
                $(this).siblings('.layui-edge').addClass('bdc-hide');
            }).on('blur', function () {
                var $this = $(this);
                setTimeout(function () {
                    $this.siblings('.layui-icon-close').addClass('bdc-hide');
                    $this.parents('.layui-input-inline').find('.layui-icon-close').addClass('bdc-hide');
                    $this.siblings('.layui-edge').removeClass('bdc-hide');
                }, 150)
            });
        }

        // 监听表格操作栏按钮
        table.on('toolbar(plfzqsdFilter)', function (obj) {
            var checkStatus = table.checkStatus(obj.config.id);
            switch (obj.event) {
                case 'print':
                    printPlfzqsd(checkStatus);
                    break;
            }
        });

        // 监听复选框事件，缓存选中的行数据
        table.on('checkbox(plfzqsdFilter)', function (obj) {
            // 获取选中或者取消的数据
            var data = obj.type == 'one' ? [obj.data] : currentPageData;

            $.each(data, function (i, v) {
                var keyT = v.processInstanceId;
                if (obj.checked) {
                    //.增加已选中项
                    layui.sessionData('checkedPlfzQsdDataLc', {
                        key: keyT, value: v
                    });
                } else {
                    //.删除
                    layui.sessionData('checkedPlfzQsdDataLc', {
                        key: keyT, remove: true
                    });
                }
            });
        });

    });

    // 加载表格
    function renderTable() {
        var cols = [[
            {type: 'checkbox', width: 50, fixed: 'left'},
            {field: 'xh', title: '序号', width: 50, type: 'numbers'},
            {field: 'slbh', title: '受理编号', width: 180},
            {field: 'djyy', title: '登记原因', width: 200},
            {field: 'qlr', title: '申请人', width: 180},
            {field: 'zl', title: '不动产坐落', minWidth: 250},
        ]];
        var pageUrl = getContextPath() + "/rest/v1.0/task/todo";
        var whereObj = {
            taskName : taskName,
        };
        table.render({
            elem: '#plfzqsdTable',
            title: '批量发证列表',
            url: pageUrl,
            id: "plfzqsdTable",
            method: 'post',
            even: true,
            limits: [10, 30, 50, 70, 90, 110, 130, 150],
            defaultToolbar: ['filter'],
            toolbar: '#toolbarDemo',
            where: whereObj,
            request: {
                limitName: 'size', //每页数据量的参数名，默认：limit
                loadTotal: true,
                loadTotalBtn: false
            },
            cols: cols,
            page: true,
            parseData: function (res) {
                // 设置选中行
                if (res.content && res.content.length > 0) {
                    var checkedData = layui.sessionData('checkedPlfzQsdDataLc');
                    res.content.forEach(function (v) {
                        $.each(checkedData, function (key, value) {
                            if (key == v.id) {
                                v.LAY_CHECKED = true;
                            }
                        })
                    });
                }
                return {
                    "code": res.code, //解析接口状态
                    "msg": res.msg, //解析提示文本
                    "count": res.totalElements, //解析数据长度
                    "data": res.content //解析数据列表
                };
            },
            done: function () {
                changeTableHeight();
                reverseTableCell(reverseList);
                table.resize('plfzqsdTable');
            }
        });
    }

    //数据交互
    function getContextPath() {
        var pathName = document.location.pathname;
        var index = pathName.substr(1).indexOf("/");
        var result = pathName.substr(0, index + 1);
        return result;
    }

    //表格高度自适应
    function changeTableHeight() {
        if ($('.bdc-list-tab .layui-tab-content .layui-show .layui-table-main>.layui-table').height() == 0) {
            $('.bdc-list-tab .layui-tab-content .layui-show .layui-table-body .layui-none').html('<img src="../static/lib/bdcui/images/table-none.png" alt="">无数据');
        } else {
            $('.bdc-list-tab .layui-tab-content .layui-show .layui-table-body').height($('.bdc-content-box').height() - 196 - $('.bdc-task-tab').innerHeight() - $('.bdc-list-tab .layui-show .bdc-search-box').height());
            $('.bdc-list-tab .layui-tab-content .layui-show .layui-table-fixed .layui-table-body').height($('.bdc-content-box').height() - 196 - $('.bdc-task-tab').innerHeight() - $('.bdc-list-tab .layui-show .bdc-search-box').height() - 17);
        }
    }


    // 打印批量发证签收单
    function printPlfzqsd() {
        var checkedData = layui.sessionData('checkedPlfzQsdDataLc');
        if(!isNotBlank(checkedData)){
            warnMsg("请勾选至少一条需要打印的数据。");
            return;
        }
        console.info(checkedData);
        var printData = [];
        var xh = 1;
        $.each(checkedData, function (key, value) {
            printData.push({
                xh: xh++,
                slbh: value.slbh,
                djyy: value.djyy,
                qlr: value.qlr,
                zl: value.zl
            });
        })

        addModel();
        // 缓存要打印的参数信息
        $.ajax({
            url: getContextPath() + "/rest/v1.0/yw/save/print/data",
            type: "POST",
            data: JSON.stringify(printData),
            contentType: 'application/json',
            dataType: 'text',
            success: function (redisKey) {
                if (!isNullOrEmpty(redisKey)) {
                    var dylx = "plfzqsd";
                    var dataUrl = getIP() + "/realestate-register-ui/rest/v1.0/print/plfzqsd/"+dylx+"/" + redisKey;
                    var modelUrl = getIP() + "/realestate-register-ui/static/printModel/plfzqsd.fr3";
                    setDypzSession([dylx],sessionKey);
                    printChoice(dylx, "realestate-register-ui", dataUrl, modelUrl, false, sessionKey);

                } else {
                    warnMsg("打印出错，请重试！");
                }
            },
            error: function () {
                warnMsg("打印出错，请重试！");
            },
            complete: function () {
                removeModal();
            }
        });
    }

    function getIP() {
        return document.location.protocol + "//" + window.location.host;
    }

});
