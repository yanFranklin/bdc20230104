/**
 * author: <a href="mailto:huangjian@gtmap.cn>huangjian</a>
 * version 1.0.0  2020/02/24
 * describe: 电子印章查看查询
 */
var reverseList = ['zl'];
layui.use(['jquery', 'element', 'form', 'table', 'laydate'], function () {
    var $ = layui.jquery;
    var form = layui.form;
    var table = layui.table;
    var laydate = layui.laydate;
    var laytpl = layui.laytpl;

    //判断查询起始时间与结束时间关系
    laydate.render({
        elem: '#fzqsrq',
        format: 'yyyy-MM-dd',
        done: function (value, date, endDate) {
            var startDate = new Date(value).getTime();
            var endTime = new Date($('#fzjsrq').val()).getTime();
            if (endTime < startDate) {
                layer.msg('<img src="../../static/lib/bdcui/images/error-small.png" alt="">结束时间不能小于开始时间');
            }
        }
    });
    laydate.render({ //结束时间
        elem: '#fzjsrq',
        format: 'yyyy-MM-dd',
        done: function (value, date, endDate) {
            var startDate = new Date($('#fzqsrq').val()).getTime();
            var endTime = new Date(value).getTime();
            if (endTime < startDate) {
                layer.msg('<img src="../../static/lib/bdcui/images/error-small.png" alt="">结束时间不能小于开始时间');
            }
        }
    });

    // 当前页数据
    var currentPageData = [];
    $(function () {
        // 加载Table
        table.render({
            elem: '#dzzzTable',
            toolbar: '#toolbarDemo',
            title: '电子印章证书证明单元列表',
            defaultToolbar: ['filter'],
            request: {
                limitName: 'size', //每页数据量的参数名，默认：limit
                loadTotal: true,
            },
            even: true,
            cols: [[
                {type: 'checkbox', fixed: 'left'},
                // {field: 'xh', type: 'numbers', width: 60, title: '序号'},
                {
                    field: 'bdcqzh', width: 280, title: '不动产产权证号'
                },
                {
                    field: 'slbh', width: 280, title: '受理编号'
                },
                {
                    field: 'bdcdyh', width: 280, title: '不动产单元号',
                    templet: function (d) {
                        return formatBdcdyh(d.bdcdyh);
                    }
                },
                {field: 'qlr', title: '权利人', width: 300, align: 'center'},
                {field: 'ywr', title: '义务人', width: 300, align: 'center'},
                {field: 'zl', title: '坐落', width: 250, align: 'center'},
                {field: 'zzid', title: '电子印章标识', width: 150, align: 'center', hide: true},
                {field: 'zzdz', title: '电子印章附件地址', width: 150, align: 'center', hide: true},
                {field: 'zsid', title: '证书id', width: 150, align: 'center', hide: true},
                {field: 'qszt', title: '权属状态', width: 150, align: 'center', hide: true},
                {field: 'xmid', title: 'xmid', width: 150, align: 'center', hide: true},
                {field: 'gzlslid', title: '工作流实例id', width: 150, align: 'center', hide: true},
                // {title: '查看', fixed: 'right', renderer: 'detailView', toolbar: '#barDemo', width: 100}
            ]],
            data: [],
            page: true,
            parseData: function (res) {
                // 设置选中行
                if (res.content && res.content.length > 0) {
                    var checkedData = layui.sessionData('checkedData');
                    res.content.forEach(function (v) {
                        $.each(checkedData, function (key, value) {
                            if (key == v.xmid) {
                                v.LAY_CHECKED = true;
                            }
                        })
                    });

                }
                return {
                    code: res.code, //解析接口状态
                    msg: res.msg, //解析提示文本
                    count: res.totalElements, //解析数据长度
                    data: res.content //解析数据列表
                }
            },
            done: function () {
                setHeight();
                reverseTableCell(reverseList);
            }
        });


        // 监听表格操作栏按钮
        table.on('toolbar(dzzzTable)', function (obj) {
            var checkStatus = table.checkStatus(obj.config.id);
            switch (obj.event) {
                case 'createDzzz':
                    createDzzz(checkStatus.data, obj.config.cols[0]);
                    saveDetailLog("DZZZCJ", "创建电子印章", {"data": checkStatus.data});
                    break;
                case  'cancelDzzz':
                    cancelDzzz(checkStatus.data, obj.config.cols[0]);
                    saveDetailLog("DZZZZX", "注销电子印章", {"data": checkStatus.data});
                    break;
                case "zfDzzz":
                    zfDzzz(checkStatus.data, obj.config.cols[0]);
                    saveDetailLog("DZZZZF", "作废电子印章", {"data": checkStatus.data});
                    break;
            }

        });

        //监听工具条
        table.on('tool(dzzzTable)', function (obj) {
            var param = obj.data;
            if (param && param.gzlslid && param.xmid) {
                if (obj.event === "ckdzzz") {
                    // 判断是否有 zzid，如果没有，则提示未生成过电子印章，无法查看
                    if (param.zzid) {
                        //获取文件中心文件地址，没有 zzdz 传递 zsid 到后台处理历史数据
                        if (param.zzdz) {
                            viewPdf(param.zzdz);
                        } else {
                            downloadZzqz(param.gzlslid, param.xmid);
                        }
                        return false;
                    } else {
                        layer.alert("未生成印章pdf，无法查看!");
                    }

                }
            } else {
                layer.alert("请检查数据");
                return false
            }
        });

        var zzzf;

        //判断选择数据是否已经作废，已经作废提示不用再作废
        function iszf(zsids) {
            var deferred = $.Deferred();
            zzzf = false;
            $.ajax({
                url: "/realestate-inquiry-ui/rest/v1.0/zzqzcz/queryDzzzZt",
                type: "POST",
                dataType: 'json',
                contentType: "application/json;charset=UTF-8",
                data: JSON.stringify(zsids),
                // async: false,
                success: function (data) {
                    if (data == 2) {
                        layer.alert("电子印章已为历史状态，不允许再操作！", {title: '提示'});
                        zzzf = true;
                    }
                    deferred.resolve();
                },
                error: function (xhr, status, error) {
                    zzzf = false;
                    delAjaxErrorMsg(xhr);
                }
            });
            return deferred;
        }

        // 下载印章签章
        function downloadZzqz(gzlslid, xmid) {
            var reSure = layer.open({
                type: 1,
                skin: 'bdc-small-tips',
                title: '提示',
                area: ['320px'],
                content: '是否下载电子签章？',
                btn: ['确定', '取消'],
                yes: function () {
                    layer.close(reSure);
                    setTimeout(function () {
                        addModel();
                        $.ajax({
                            url: "/realestate-inquiry-ui/rest/v1.0/zzqzcz/xz",
                            type: "GET",
                            async: false,
                            dataType: 'json',
                            data: {xmid: xmid, gzlslid: gzlslid},
                            success: function (zzdz) {
                                removeModal();
                                viewPdf(zzdz);
                            },
                            error: function (xhr, status, error) {
                                delAjaxErrorMsg(xhr);
                            }
                        });
                    }, 50);
                },
                btn2: function () {
                    //取消
                    layer.close(reSure);
                }
            });
        }

        function viewPdf(zzdz) {
            //直接弹出下载框
            layer.open({
                type: 2,
                title: "印章查看",
                maxmin: true,
                area: ['70%', '100%'],
                fixed: false, //不固定
                content: zzdz
                , end: function (index, layero) {
                    return false;
                }
            });
        }

        //创建电子印章
        function createDzzz(d, col) {
            // 获取分页勾选的所有数据
            var checkedData = layui.sessionData('checkedData');
            var xmids = [];
            var msg = "";
            $.each(checkedData, function (key, value) {
                if (value.zzid && !isNullOrEmpty(value.zzid)) {
                    msg = value.bdcqzh + ",已生成过电子印章，不能再次生成！";
                    return false;
                }
                // 判断证书对应项目的权属状态
                if (!("1" === value.qszt)) {
                    msg = value.bdcqzh + ",相关权属状态非现势，不能生成印章！";
                    return false;
                }
                xmids.push(value.xmid);
            });

            if (msg && !isNullOrEmpty(msg)) {
                warnMsg(msg);
                return false;
            }

            if (xmids.length == 0) {
                warnMsg(" 未选择数据，请选择其中一条数据！");
                return false;
            }

            addModel();
            $.ajax({
                url: "/realestate-inquiry-ui/rest/v1.0/zzqzcz/createDzzz",
                type: "POST",
                dataType: 'json',
                contentType: "application/json;charset=UTF-8",
                data: JSON.stringify(xmids),
                success: function (data) {
                    if (data == null || data == undefined || data.length == 0 || data == 0) {
                        layer.alert("创建电子印章失败！", {title: '提示'});
                        return;
                    } else if (1 === data) {
                        successMsg("创建成功！");
                        // 刷新表格数据
                        search();
                    } else if (2 === data) {
                        warnMsg("创建电子印章部分失败！请检查！");
                    } else if (3 === data) {
                        failMsg("电子印章创建全部失败！")
                    }
                    removeModal();
                },
                error: function (xhr, status, error) {
                    delAjaxErrorMsg(xhr);
                    // failMsg("系统验证发生异常，请重试或联系管理员！");
                    removeModal();
                }
            })

        }

        //注销电子印章
        function cancelDzzz(d, col) {
            // 获取分页勾选的所有数据
            var checkedData = layui.sessionData('checkedData');
            var xmids = [];
            var zzids = [];
            var zsids = [];
            $.each(checkedData, function (key, value) {
                xmids.push(value.xmid);
                zsids.push(value.zzid);
                if (checkedData[key].zzid) {
                    zzids.push(value.zzid);
                }
                console.log(zzids.toString());
            });
            if (xmids.length != zzids.length) {
                warnMsg("  勾选数据包含未生成电子印章的数据，请检查！");
                return;
            }
            if (xmids.length == 0) {
                warnMsg(" 请选择其中一条数据！");
                return;
            }
            addModel();

            $.ajax({
                url: "/realestate-inquiry-ui/rest/v1.0/zzqzcz/cancelDzzz",
                type: "POST",
                dataType: 'json',
                contentType: "application/json;charset=UTF-8",
                data: JSON.stringify(xmids),
                success: function (data) {
                    if (data == null || data == undefined || data.length == 0 || data == 0) {
                        layer.alert("注销电子印章失败！", {title: '提示'});
                        return;
                    } else {
                        successMsg("注销成功！");

                    }
                    removeModal();
                },
                error: function (xhr, status, error) {
                    delAjaxErrorMsg(xhr);
                    removeModal();
                }
            });
        }

        //作废电子印章
        function zfDzzz(d, col) {
            // 获取分页勾选的所有数据
            var checkedData = layui.sessionData('checkedData');
            var xmids = [];
            var zzids = [];
            var zsids = [];
            $.each(checkedData, function (key, value) {
                xmids.push(value.xmid);
                zsids.push(value.zsid);
                if (checkedData[key].zzid) {
                    zzids.push(value.zzid);
                }
                console.log(zzids.toString());
            });
            if (xmids.length != zzids.length) {
                warnMsg("  勾选数据包含未生成电子印章的数据，请检查！");
                return;
            }
            if (xmids.length == 0) {
                warnMsg(" 请选择其中一条数据！");
                return;
            }
            addModel();

            $.ajax({
                url: "/realestate-inquiry-ui/rest/v1.0/zzqzcz/zfDzzz",
                type: "POST",
                dataType: 'json',
                contentType: "application/json;charset=UTF-8",
                data: JSON.stringify(xmids),
                //async: false,
                success: function (data) {
                    if (data == null || data == undefined || data.length == 0 || data == 0) {
                        layer.alert("作废电子印章失败！", {title: '提示'});
                    } else {
                        successMsg("作废成功！");

                    }
                    removeModal();
                },
                error: function (xhr, status, error) {
                    delAjaxErrorMsg(xhr);
                    //removeModal();
                }
            });
        }

        /**
         * 默认加载查询
         */
        search();

        /**
         * 点击查询
         */
        $('#search').on('click', function () {
            search();
        });
        // 回车查询
        $('.required').on('keydown', function (event) {
            if (event.keyCode == 13) {
                search();
            }
        });

        //查询
        function search() {
            // 每次查询清除下导出缓存数据
            layui.sessionData("checkedData", null);
            // 判断必填条件
            var count = 0;
            $(".required").each(function (i) {
                if (!isNullOrEmpty($(this).val())) {
                    count += 1;
                }
            });
            if (0 == count) {
                layer.alert("<div style='text-align: center'>请输入必要查询条件！</div>", {title: '提示'});
                return false;
            }
            var startDate = new Date($('#fzqsrq').val()).getTime();
            var endTime = new Date($('#fzjsrq').val()).getTime();
            if (endTime < startDate) {
                layer.msg('<img src="../../static/lib/bdcui/images/error-small.png" alt="">结束时间不能小于开始时间');
                return false;
            }

            // 获取查询参数
            var obj = {};
            $(".cxtj").each(function (i) {
                var name = $(this).attr('name');
                obj[name] = $(this).val();
            });

            addModel();
            // 重新请求
            table.reload("dzzzTable", {
                url: "/realestate-inquiry-ui/rest/v1.0/dzzzcz/page"
                , where: obj
                , page: {
                    curr: 1, //重新从第 1 页开始
                    limits: [10, 50, 100, 200, 500]
                }
                , done: function (res, curr, count) {
                    currentPageData = res.data;
                    reverseTableCell(reverseList);
                    removeModal();
                    setHeight();
                    saveDetailLog("DZZZCX", "电子印章查询", obj);
                }
            });
        }

        // 监听复选框事件，缓存选中的行数据
        table.on('checkbox(dzzzTable)', function (obj) {
            // 获取选中或者取消的数据
            var data = obj.type == 'one' ? [obj.data] : currentPageData;
            $.each(data, function (i, v) {
                if (obj.checked) {
                    //.增加已选中项
                    layui.sessionData('checkedData', {
                        key: v.zsid, value: v
                    });
                    var checkedData = layui.sessionData('checkedData');

                    console.log(layui.sessionData('checkedData'));
                } else {
                    //.删除
                    layui.sessionData('checkedData', {
                        key: v.zsid, remove: true
                    });
                }
            });
        });

        /**
         * 重置清空查询条件
         */
        $('#reset').on('click', function () {
        });

    });
});



