var selectDataList = [];
var xmids = [];
var actionType = '';
layui.use(['jquery', 'table', 'layer'], function () {
    var $ = layui.jquery,
        table = layui.table,
        layer = layui.layer;
    $(function () {
        /**
         * 监听台账查询 input 点击 显示 x 清空 input 中的内容
         */
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
        $('.bdc-content').css('min-height', $('body').height() - 56);

        /**
         * 渲染补录信息表格
         * @type {string}
         */
        var url = "/realestate-register-ui/rest/v1.0/blxx/page/nantong";
        var tableId = '#blTable';
        var toolbar = '#toolbar';
        renderblTable(url, tableId, toolbar);

        /**
         * 查询点击事件
         */
        $('#search').on('click', function () {
            // 获取查询内容
            var obj = {};
            $(".search").each(function (i) {
                var value = $(this).val();
                var name = $(this).attr('name');
                // 此处去除查询条件中的空格
                obj[name] = deleteWhitespace(value);
            });
            // 重新请求
            table.reload("blTable", {
                where: obj
                , page: {
                    curr: 1  //重新从第 1 页开始
                }, done: function (res) {
                    obj['response'] = res.data;
                    saveDetailLog("XXBL_CX", "信息补录-查询", obj);
                    setTableHeight();
                }
            });
        });

        /**
         * 根据所选信息判断是否是产权
         * @param data
         */
        function isCq(data) {
            var qllx = data.qllx;
            // 产权的权利类型如下
            var cq = ["3", "4"];
            // 先判断是否是产权可以进行复制
            if (cq.indexOf(qllx) !== -1) {
                layer.msg("仅支持产权的操作！");
            } else if (!isNullOrEmpty(data)) {
                var yxmid = data[0].xmid;
                var ybdcdyh = data[0].bdcdyh;
                var qllx = data[0].qllx;
                var gzldymc = data[0].gzldymc;
                $.ajax({
                    url: getContextPath() + '/rest/v1.0/blxx/blxz/gzldyid?gzldymc=' + gzldymc,
                    type: 'GET',
                    success: function (gzldyid) {
                        // 打开不动产单元号的选择页面，通过 URL 参数和选择页面区分
                        var index = layer.open({
                            type: 2,
                            title: '选择不动产单元',
                            maxmin: true,
                            area: ['1150px', '600px'],
                            content: '../xxbl/xxbl_selectbdcdyh.html?yxmid=' + yxmid + '&gzldyid=' + gzldyid + '&ybdcdyh=' + ybdcdyh + '&qllx=' + qllx + '&actionType=' + actionType
                        });
                        layer.full(index);
                    }, error: function (xhr, status, error) {
                        removeModel();
                        delAjaxErrorMsg(xhr);
                    }
                });

            }
        }

        /**
         * 根据所选信息判断是否是房屋产权
         * @param data
         */
        function isFw(data) {
            var qllx = data.qllx;
            // 产权的权利类型如下
            var cq = ["6", "4", "8"];
            // 先判断是否是产权可以进行复制
            if (cq.indexOf(qllx) !== -1) {
                layer.msg("仅支持房产权利的挂接！");
            } else if (!isNullOrEmpty(data)) {
                var yxmid = data[0].xmid;
                var ybdcdyh = data[0].bdcdyh;
                if (ybdcdyh.indexOf('F') < 0) {
                    warnMsg("挂接操作仅支持房屋！");
                } else {
                    var qllx = data[0].qllx;
                    var gzldymc = data[0].gzldymc;
                    $.ajax({
                        url: getContextPath() + '/rest/v1.0/blxx/blxz/gzldyid?gzldymc=' + gzldymc,
                        type: 'GET',
                        success: function (gzldyid) {
                            // 打开不动产单元号的选择页面，通过 URL 参数和选择页面区分
                            var index = layer.open({
                                type: 2,
                                title: '选择不动产单元',
                                maxmin: true,
                                area: ['1150px', '600px'],
                                content: '../xxbl/xxbl_selectbdcdyh.html?yxmid=' + yxmid + '&gzldyid=' + gzldyid + '&ybdcdyh=' + ybdcdyh + '&qllx=' + qllx + '&actionType=' + actionType
                            });
                            layer.full(index);
                        }, error: function (xhr, status, error) {
                            removeModel();
                            delAjaxErrorMsg(xhr);
                        }
                    });
                }
            }
        }

        /**
         * 监听头工具栏事件
         */
        table.on('toolbar(blTable)', function (obj) {
            var layEvent = obj.event; //获得 lay-event 对应的值
            var checkStatus = table.checkStatus(obj.config.id);
            var selectedNum = checkStatus.data.length;
            if (layEvent === "addBlxx") {
                window.open('./xxblSelectbdcdy.html');
            } else if (layEvent === "zxQl") {
                // 清空全局变量中的值
                selectDataList = [];
                // 判断是否选择了对应数据
                if (selectedNum == 0) {
                    layer.msg('请选择一条数据进行操作！');
                    return false;
                } else {
                    // 重新赋值
                    xmids = [];
                    var selectData = checkStatus.data;
                    var gzldymc;
                    // 遍历数据判断是否选择了相同的流程办理批量注销
                    selectData.forEach(function (v) {
                        if (isNullOrEmpty(gzldymc)) {
                            gzldymc = v.gzldymc;
                        } else {
                            if (gzldymc !== v.gzldymc) {
                                warnMsg("请选择相同的流程办理批量注销");
                                return;
                            }
                        }
                        xmids.push(v.xmid);
                    });
                    for (var i = 0; i < selectData.length; i++) {
                        var data = selectData[i];
                        var bdcGzYzsjDTO = {};
                        bdcGzYzsjDTO.bdcdyh = data.bdcdyh;
                        selectDataList.push(bdcGzYzsjDTO);
                    }
                    zxQl(checkQlxx);
                }
            } else if (layEvent === "copy") {
                // 判断是否选择了对应数据
                if (selectedNum == 1) {
                    actionType = 'copy';
                    isCq(checkStatus.data);
                } else {
                    layer.msg('请选择一条产权进行复制操作！');
                    return false;
                }
            } else if (layEvent === "gj") {
                // 判断是否选择了对应数据
                if (selectedNum == 1) {
                    actionType = 'gj';
                    isFw(checkStatus.data);
                } else {
                    layer.msg('请选择一条产权进行挂接操作！');
                    return false;
                }
            }  else if (layEvent === "connect"){
                if (selectedNum == 1) {
                    var index = layer.open({
                        type: 2,
                        title: '关联产权',
                        maxmin: true,
                        area: ['1150px', '600px'],
                        content: '../xxbl/xxbl_glcq.html?gzlslid=' + checkStatus.data[0].gzlslid
                    });
                    layer.full(index);
                } else {
                    layer.msg('请选择一条产权进行关联操作！');
                    return false;
                }

            }
        });

        /**
         * 判断是否存现限制权利
         * 调用规则验证
         */
        function checkQlxx(index) {
            layer.close(index);
            addModel();
            var bdcGzYzQO = {};
            bdcGzYzQO.zhbs = $("#process").val() + "_XZBDCDY";
            bdcGzYzQO.paramList = selectDataList;

            $.ajax({
                url: getContextPath() + '/rest/v1.0/blxx/bdcGzyz',
                type: 'POST',
                dataType: 'json',
                contentType: "application/json;charset=UTF-8",
                data: JSON.stringify(bdcGzYzQO),
                success: function (data) {
                    if (data.length > 0) {
                        // 未通过验证，页面提示信息
                        removeModel();
                        loadTsxx(data);
                        gzyztsxx();
                    } else {
                        // 通过验证，组织数据注销权利
                        var postObj = {};
                        postObj.xmidList = xmids;
                        postObj.zxyy = $('#zxyy').val();
                        $.ajax({
                            type: 'POST',
                            url: "/realestate-register-ui/rest/v1.0/blxx/zxql",
                            contentType: "application/json;charset=utf-8",
                            data: JSON.stringify(postObj),
                            dataType: "text",
                            success: function (data) {
                                removeModel();
                                if (data === "history") {
                                    warnMsg("当前项目已经被注销，不可以重复注销！");
                                } else {
                                    successMsg("注销成功");
                                }
                            },
                            error: function (xhr, status, error) {
                                delAjaxErrorMsg(xhr)
                            }
                        });
                    }
                }, error: function (xhr, status, error) {
                    removeModel();
                    delAjaxErrorMsg(xhr);
                }
            });
        }


        /**
         * 监听工具栏事件，工具栏进入到编辑页面可以对于数据进行操作
         */
        table.on('tool(blTable)', function (obj) {
            var layEvent = obj.event; //获得 lay-event 对应的值
            var data = obj.data; //获得当前行数据
            // 确认 gzlslid 是有值的
            if (isNullOrEmpty(obj.data.gzlslid)) {
                layer.msg("数据异常，未获取到工作流实例 id");
            } else {
                if (layEvent === 'del') { //删除
                    var deleteIndex = layer.open({
                        type: 1,
                        skin: 'bdc-small-tips',
                        title: '确认删除',
                        area: ['320px'],
                        content: '是否确认删除？',
                        btn: ['确定', '取消'],
                        btnAlign: 'c',
                        yes: function () {
                            layer.close(deleteIndex);
                            //确定操作
                            addModel();
                            $.ajax({
                                url: "/realestate-register-ui/rest/v1.0/blxx?xmid=" + data.xmid + "&all=false",
                                type: 'DELETE',
                                datetype: 'json',
                                success: function (result) {
                                    removeModel();
                                    if (result === false) {
                                        var reSure = layer.open({
                                            type: 1,
                                            skin: 'bdc-small-tips',
                                            title: '确认',
                                            area: ['320px'],
                                            content: '此项目属于批量流程，删除将会删除全部信息，是否确认删除？',
                                            btn: ['确定', '取消'],
                                            yes: function () {
                                                layer.close(reSure);
                                                addModel();
                                                $.ajax({
                                                    url: "/realestate-register-ui/rest/v1.0/blxx?xmid=" + data.xmid + "&all=true",
                                                    type: 'DELETE',
                                                    datetype: 'json',
                                                    success: function (deleteRsult) {
                                                        removeModel();
                                                        if (deleteRsult === true) {
                                                            saveDetailLog("XXBL_SC", "信息补录-删除", data);
                                                            obj.del(); //删除对应行（tr）的DOM结构，并更新缓存
                                                            layer.msg("删除成功");
                                                        }
                                                    },
                                                    error: function (xhr, status, error) {
                                                        delAjaxErrorMsg(xhr);
                                                    }
                                                });
                                            },
                                            btn2: function () {
                                                //取消
                                                layer.close(deleteIndex);
                                            }
                                        });
                                    } else {
                                        obj.del(); //删除对应行（tr）的DOM结构，并更新缓存
                                        layer.msg("删除成功");
                                    }
                                },
                                error: function (xhr, status, error) {
                                    delAjaxErrorMsg(xhr);
                                }
                            });
                        },
                        btn2: function () {
                            //取消
                            layer.close(deleteIndex);
                        }
                    });
                } else if (layEvent === 'edit') { //编辑
                    saveDetailLog("XXBL_CK", "信息补录-查看", data);
                    window.open("./xxblUpdateinfo.html?processInsId=" + obj.data.gzlslid + "&xmid=" + obj.data.xmid + "&type=update");
                } else if (layEvent === 'back') { //退回
                    window.open("./xxblBack.html?xmid=" + obj.data.xmid);
                } else if (layEvent === 'check') {
                    saveDetailLog("XXBL_CK", "信息补录-查看", data);
                    window.open("./xxblUpdateinfo.html?processInsId=" + obj.data.gzlslid + "&type=check" + "&xmid=" + obj.data.xmid);
                }
            }
        });
    });

    /**
     * 渲染补录数据
     * @param url 地址
     * @param tableId 表格 id
     * @param toolbar 表格工具栏
     */
    function renderblTable(url, tableId, toolbar) {
        table.render({
            elem: tableId,
            url: url,
            toolbar: toolbar,
            title: '补录任务表格',
            method: 'GET',
            even: true,
            request: {
                limitName: 'size' //每页数据量的参数名，默认：limit
            },
            defaultToolbar: ['filter'],
            limits: commonLimits,
            cols: [[
                {type: 'checkbox', width: 50, fixed: 'left'},
                {field: 'bdcqzh', templet: "#bdcqzhTpl", minWidth: 100, title: '不动产权证号', event: 'check'},
                {field: 'ycqzh', minWidth: 100, title: '原产权证号', event: 'check'},
                {field: 'qlrmc', minWidth: 100, title: '权利人'},
                {field: 'gzldymc', minWidth: 100, title: '流程名称'},
                {field: 'zl', minWidth: 200, title: '坐落'},
                {field: 'bdcdyh', templet: '#bdcdyhTpl', minWidth: 270, title: '不动产单元号'},
                {field: 'blsj', title: '补录时间', width: 200, sort: true},
                {field: 'qszt', title: '权属状态', templet: '#qsztTemplate', minWidth: 90, hide: true},
                {field: 'gzlslid', title: '流程ID', width: 90, hide: true},
                {fixed: 'right', title: '操作', toolbar: '#editbar', width: 180}
            ]],
            parseData: function (res) { //res 即为原始返回的数据
                res.content.forEach(function (v) {
                    if (v.slsj) {
                        var blsj = new Date(v.slsj);
                        v.blsj = format(blsj);
                    }
                });
                return {
                    code: res.code, //解析接口状态
                    msg: res.msg, //解析提示文本
                    count: res.totalElements, //解析数据长度
                    data: res.content //解析数据列表
                }
            },
            page: true,
            done: function () {
                setTableHeight();
            }
        });
    }
});

//关闭 panel
function cancelEdit() {
    layui.use(['layer'], function () {
        var layer = layui.layer;
        layer.closeAll();
    });
}