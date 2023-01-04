var selectDataList = [];
var xmids = [];
var actionType = '';
var form;
var moduleCode = $.getUrlParam('moduleCode');
layui.use(['jquery', 'table', 'layer'], function () {
    var $ = layui.jquery,
        table = layui.table,
        layer = layui.layer;
        form = layui.form;
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
        var obj = {};
        obj["qszt"] = $("input[name='qszt']:checked").val();
        renderblTable(url, tableId, toolbar,obj);
        //监听工具条
        table.on('tool(blTable)', function (obj) {
            var data = obj.data;


            if (obj.event === 'zx') {
                selectDataList = [];
                var bdcGzYzsjDTO = {};
                bdcGzYzsjDTO.bdcdyh = data.bdcdyh;
                selectDataList.push(bdcGzYzsjDTO);
                xmids = [];
                xmids.push(data.xmid)
                zxQl(checkQlxx);
            } else if (obj.event === 'hy') {
                selectDataList = [];
                var bdcGzYzsjDTO = {};
                bdcGzYzsjDTO.bdcdyh1 = data.bdcdyh;
                bdcGzYzsjDTO.bdcdyh2 = data.bdcdyh;
                selectDataList.push(bdcGzYzsjDTO);
                xmids = [];
                xmids.push(data.xmid)
                hyQl(hyCheckQlxx);
            }
        });
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
            obj["qszt"] = $("input[name='qszt']:checked").val();
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
         * 监听头工具栏事件
         */
        table.on('toolbar(blTable)', function (obj) {
            var layEvent = obj.event; //获得 lay-event 对应的值
            var checkStatus = table.checkStatus(obj.config.id);
            var selectData = checkStatus.data;

            var selectedNum = checkStatus.data.length;
            if (layEvent === "zxQl") {
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
            } else if (layEvent === "hyQl"){
                // 清空全局变量中的值
                selectDataList = [];
                if (selectedNum != 1) {
                    layer.msg('请选择一条数据进行操作！');
                    return false;
                } else {
                    // 重新赋值
                    xmids = [];
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
                        bdcGzYzsjDTO.bdcdyh1 = data.bdcdyh;
                        bdcGzYzsjDTO.bdcdyh2 = data.bdcdyh;
                        selectDataList.push(bdcGzYzsjDTO);
                    }
                    hyQl(hyCheckQlxx);
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
            bdcGzYzQO.zhbs = "ZXQLYZ";
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



    });

    /**
     * 还原权利验证
     * 调用规则验证，HYQLYZ
     */
    function hyCheckQlxx(index) {
        layer.close(index);
        addModel();
        var bdcGzYzQO = {};
        bdcGzYzQO.zhbs = "HYQLYZ";
        bdcGzYzQO.paramMap = selectDataList[0];
        $.ajax({
            url: getContextPath() + '/rest/v1.0/blxx/qtyz',
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
                    postObj.hfyy = $('#hfyy').val();
                    $.ajax({
                        type: 'POST',
                        url: "/realestate-register-ui/rest/v1.0/blxx/hyql",
                        contentType: "application/json;charset=utf-8",
                        data: JSON.stringify(postObj),
                        dataType: "text",
                        success: function (data) {
                            removeModel();
                            if (data === "valid") {
                                warnMsg("当前项目已经被还原，不可以重复还原！");
                            } else if(data === "success"){
                                successMsg("还原成功");
                            }else {
                                warnMsg(data)
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
     * 渲染补录数据
     * @param url 地址
     * @param tableId 表格 id
     * @param toolbar 表格工具栏
     */
    function renderblTable(url, tableId, toolbar,obj) {
        table.render({
            elem: tableId,
            url: url,
            toolbar: toolbar,
            title: '任务表格',
            method: 'GET',
            even: true,
            where: obj,
            request: {
                limitName: 'size' //每页数据量的参数名，默认：limit
            },
            defaultToolbar: ['filter'],
            limits: commonLimits,
            cols: [[
                {type: 'checkbox', width: 50, fixed: 'left'},
                {field: 'bdcqzh', templet: "#bdcqzhTpl", minWidth: 100, title: '不动产权证号', event: 'check'},
                {field: 'ycqzh', minWidth: 100, title: '原产权证号', event: 'check',hide: true},
                {field: 'qlrmc', minWidth: 100, title: '权利人'},
                {field: 'gzldymc', minWidth: 100, title: '流程名称',hide: true},
                {field: 'zl', minWidth: 230, title: '坐落'},
                {field: 'bdcdyh', templet: '#bdcdyhTpl', minWidth: 270, title: '不动产单元号'},
                {field: 'blsj', title: '补录时间', width: 200, sort: true},
                {field: 'qszt', title: '权属状态', templet: '#qsztTemplate', minWidth: 50},
                {field: 'qszt', title: '限制状态', templet: '#xzqlZtTemplate', minWidth: 90},
                {fixed: 'right', title: '操作', toolbar: '#editbar', width: 90}
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
                setElementAttrByModuleAuthority(moduleCode);
                form.render();
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

// 设置默认按钮默认隐藏
function setBtnHidden() {
    $("#zxQl").hide();
    $("#hyQl").hide();
}
