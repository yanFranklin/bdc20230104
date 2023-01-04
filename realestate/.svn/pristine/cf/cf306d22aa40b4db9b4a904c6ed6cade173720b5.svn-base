var lclx;
layui.use(['jquery', 'table', 'layer', 'form'], function () {
    var $ = layui.jquery,
        table = layui.table,
        layer = layui.layer,
        form = layui.form;
    var bdcdyh;
    $(function () {
        var xmid = $.getUrlParam('xmid');
        var qllx = $.getUrlParam('qllx');
        var bdcdyfwlx = $.getUrlParam('bdcdyfwlx');
        var processInsId = $.getUrlParam('processInsId');
        var type = $.getUrlParam('type');
        var taskId = $.getUrlParam("taskId");
        ygzlslid = $.getUrlParam('ygzlslid');
        var readOnly = $.getUrlParam('readOnly');
        lclx = $.getUrlParam('lclx');
        // 判断按钮权限
        if (isNullOrEmpty(type) || type === "check" || readOnly === "true") {
            $('#end').addClass('bdc-hide');
            //$('#prev').removeClass('bdc-hide').removeClass('bdc-secondary-btn').addClass('bdc-major-btn');
            $('#initZs').addClass('bdc-hide');
            $('#zxql').addClass('bdc-hide');
        }

        /**
         * 渲染补录证书信息表格
         * @type {string}
         */
        var url = "/realestate-register-ui/rest/v1.0/blxx/zs/list?gzlslid="+processInsId;
        var tableId = '#blTable';
        var toolbar = '#toolbar';
        renderblTable(url, tableId, toolbar);

        /**
         * 监听工具栏事件，工具栏进入到编辑页面可以对于数据进行操作
         */
        table.on('tool(blTable)', function (obj) {
            var layEvent = obj.event; //获得 lay-event 对应的值
            var data = obj.data; //获得当前行数据
            console.info(data);
            // 确认 gzlslid 是有值的
            if (isNullOrEmpty(data.zsid)) {
                warnMsg("缺少证书 ID");
            } else {
                if (layEvent === 'more') {
                    var url = "";
                    if (data.zslx == 1) {
                        url = getContextPath() + "/view/xxbl/lc_zs.html?xmid=" + data.xmid + "&qllx=" + data.qllx + "&bdcdyfwlx=" + data.bdcdyfwlx +
                            "&processInsId=" + processInsId + "&type=" + type + "&zsid=" + data.zsid + "&readOnly=" + readOnly + "&lclx=" + lclx + "&hidenPrevButton=true";
                    } else {
                        url = getContextPath() + "/view/xxbl/lc_zm.html?xmid=" + data.xmid + "&qllx=" + data.qllx + "&bdcdyfwlx=" + data.bdcdyfwlx +
                            "&processInsId=" + processInsId + "&type=" + type + "&zsid=" + data.zsid + "&readOnly=" + readOnly  + "&lclx=" + lclx + "&hidenPrevButton=true";
                    }
                    var index = layer.open({
                        type: 2,
                        area: ['100%', '100%'],
                        title: " 证书/明信息",
                        content: url,
                        btnAlign: "c",
                        cancel: function () {
                            layer.close(index);
                        }
                    });
                    layer.full(index);
                    //window.open(url);
                }
            }
        });

        /**
         * 监听完成按钮操作
         * 更新权利其他状况和附记
         * 保存印刷序列号
         */
        form.on('submit(end)', function (data) {
            layer.msg('办理成功，即将关闭页面', {
                time: 1000
            }, function () {
                window.close();
                //关闭父页面
                if(window.parent){
                    window.parent.close()
                }
            });
        });


        // 监听上一步按钮
        form.on("submit(prev)", function () {
            var backUrl = getContextPath() + "/view/xxbl/lc_qlxx.html?qllx=" + qllx + "&bdcdyfwlx=" + bdcdyfwlx + "&xmid=" + xmid + "&processInsId=" + processInsId + "&type="
                + type + "&taskId=" + taskId + "&ygzlslid=" + ygzlslid + "&readOnly=" + readOnly+ "&lclx=" + lclx;
            window.location.href = backUrl;
        });


        /**
         * 监听重新生成证书
         * type 为 update 时，用户选择生成证书时调用
         */
        form.on('submit(initZs)', function (data) {
            confirmZs();
        });

        function confirmZs() {
            layer.confirm("是否确认生成证书？", {
                title: "提示",
                btn: ["确认", "取消"],
                btn1: function (index) {
                    layer.close(index);
                    addModel();
                    checkQlr();
                },
                btn2: function (index) {
                    layer.close(index);
                }
            });
        }

        /**
         * 判断是否添加权利人
         */
        function checkQlr() {
            $.ajax({
                url: "/realestate-register-ui/rest/v1.0/blxx/qlr",
                type: 'GET',
                dataType: 'json',
                data: {xmid: xmid},
                success: function (data) {
                    if (!isNullOrEmpty(data)) {
                        InitZs()
                    } else {
                        removeModel();
                        warnMsg("请先添加权利人信息！")
                    }
                },
                error: function (xhr, status, error) {
                    removeModel();
                    delAjaxErrorMsg(xhr)
                }
            });
        }

        /**
         * 重新生成证书
         * @return {int} 返回zslx
         */
        function InitZs() {
            // TODO 重新生成证书是否需要还从 session 中获取
            var bdcqzh = sessionStorage.getItem("xxbl" + processInsId);
            $.ajax({
                url: "/realestate-register-ui/rest/v1.0/blxx/zs/initZs",
                type: 'GET',
                data: {
                    gzlslid: processInsId,
                    bdcqzh: bdcqzh,
                    xmid: xmid
                },
                success: function (data) {
                    window.location.reload();
                },
                error: function (xhr, status, error) {
                    removeModel();
                    delAjaxErrorMsg(xhr)
                }
            });
        }

        /**
         * 监听注销权利按钮
         * type 为 update 时，提供一键注销功能
         */
        form.on("submit(zxql)", function () {
            zxQl(checkQlxx);
            return false;
        });

        /**
         * 判断是否存现限制权利
         */
        function checkQlxx(index) {
            layer.close(index);
            addModel();
            // 去除 页面 中的空格
            var selectDataList = [];
            var bdcGzYzsjDTO = {};
            bdcGzYzsjDTO.bdcdyh = bdcdyh;
            selectDataList.push(bdcGzYzsjDTO);

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
                        postObj.xmidList = [xmid];
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
     * 渲染补录证书数据
     * @param url 地址
     * @param tableId 表格 id
     * @param toolbar 表格工具栏
     */
    function renderblTable(url, tableId, toolbar) {
        addModel();
        table.render({
            elem: tableId,
            url: url,
            toolbar: false,
            title: '补录证书表单',
            method: 'GET',
            even: true,
            request: {
                limitName: 'size' //每页数据量的参数名，默认：limit
            },
            defaultToolbar: ['filter'],
            limits: commonLimits,
            cols: [[
                {field: 'bdcqzh', minWidth: 100, title: '不动产权证号',align:'center'},
                {field: 'qlr', minWidth: 100, title: '权利人',align: 'center'},
                {field: 'zl', minWidth: 200, title: '坐落',align: 'center'},
                {field: 'zslxmc', title: '证书类型',align: 'center'},
                {field: 'zslx', title: '证书类型', hide: true,align: 'center'},
                {field: 'gyfsmc', title: '共有方式',align: 'center'},
                {field: 'yt', title: '用途',align: 'center'},
                {field: 'qlxz', title: '权利性质',align: 'center'},
                {field: 'bdcdyh', templet: '#bdcdyhTpl', minWidth: 270, title: '不动产单元号',align: 'center'},
                {fixed: 'right', title: '操作', toolbar: '#editbar', width: 80}
            ]],
            parseData: function (res) { //res 即为原始返回的数据
                // 分别持证不动产单元号相同，所以取第一个不动产单元号即可
                if(res.content.length > 0 ){
                    bdcdyh = res.content[0].bdcdyh;
                }
                return {
                    code: res.code, //解析接口状态
                    msg: res.msg, //解析提示文本
                    count: res.totalElements, //解析数据长度
                    data: res.content //解析数据列表
                }
            },
            page: true,
            done: function () {
                removeModel();
                $('.layui-table-tool-self').css('right', $('.bdc-export-tools').width() + 17 + 'px');
                if ($('.layui-table-body>.layui-table').height() == 0) {
                    $('.layui-table-body .layui-none').html('<img src="../../static/lib/bdcui/images/table-none.png" alt="">无数据');
                }
            }
        });
    }
});
