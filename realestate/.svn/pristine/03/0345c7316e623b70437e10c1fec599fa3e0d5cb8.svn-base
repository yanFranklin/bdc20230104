/**
 * author: <a href="mailto:huangjian@gtmap.cn>huangjian</a>
 * version 1.0.0  2020/02/24
 * describe: 电子证照查看查询
 */
var reverseList = ['zl'];
layui.use(['jquery', 'element', 'form', 'table', 'laydate'], function () {
    var $ = layui.jquery;
    var form = layui.form;
    var table = layui.table;
    var laydate = layui.laydate;
    var laytpl = layui.laytpl;
    // 下拉选择添加删除按钮
    renderSelectClose(form);
    loadProcessDefName();
    //判断查询起始时间与结束时间关系
    laydate.render({
        elem: '#djqsrq',
        format: 'yyyy-MM-dd',
        done: function (value, date, endDate) {
            var startDate = new Date(value).getTime();
            var endTime = new Date($('#djjsrq').val()).getTime();
            if (endTime < startDate) {
                layer.msg('<img src="../../static/lib/bdcui/images/error-small.png" alt="">结束时间不能小于开始时间');
            }
        }
    });
    laydate.render({ //结束时间
        elem: '#djjsrq',
        format: 'yyyy-MM-dd',
        done: function (value, date, endDate) {
            var startDate = new Date($('#djqsrq').val()).getTime();
            var endTime = new Date(value).getTime();
            if (endTime < startDate) {
                layer.msg('<img src="../../static/lib/bdcui/images/error-small.png" alt="">结束时间不能小于开始时间');
            }
        }
    });

    // 当前页数据
    var currentPageData = [];
    $(function () {
        // 获取页面表单标识，用于权限控制
        var moduleCode = $.getUrlParam('moduleCode');
        // 加载Table
        table.render({
            elem: '#dzzzTable',
            toolbar: '#toolbarDemo',
            title: '电子证照证书证明单元列表',
            defaultToolbar: ['filter'],
            request: {
                limitName: 'size', //每页数据量的参数名，默认：limit
                loadTotal: true,
            },
            even: true,
            cols: [[
                {type: 'checkbox', fixed: 'left'},
                {field: 'xh', type: 'numbers', width: 60, title: '序号'},
                {field: 'dzzzzt',title: '制证状态',width: 150,align: 'center',
                    templet: function (d) {
                        return formatDzzzzt(d.dzzzzt)
                    }
                },
                {
                    field: 'slbh', width: 300, title: '受理编号'
                },
                {field: 'qlr', title: '权利人', width: 320, align: 'center'},
                {field: 'zl', title: '坐落', width: 370, align: 'center'},
                {
                    field: 'djsj', title: '登记时间', width: 170, align: 'center',
                    templet: function (d) {
                        return formatDate(d.djsj);
                    }
                },
                {
                    field: 'bdcqzh', width: 300, title: '不动产产权证号'
                },
                {field: 'zzbs', title: '电子证照标识', width: 200, align: 'center'},
                {
                    field: 'bdcdyh', width: 280, title: '不动产单元号', hide: true,
                    templet: function (d) {
                        return formatBdcdyh(d.bdcdyh);
                    }
                },


                {field: 'fzr', title: '发证人', width: 150, align: 'center', hide: true},
                {
                    field: 'fzsj', title: '发证时间', width: 150, align: 'center', hide: true,
                    templet: function (d) {
                        return formatDate(d.fzsj);
                    }
                },
                /* {field: 'dzzzStatus', title: '请求结果标识', width: 150, align: 'center'},
                 {field: 'dzzzMessage', title: '请求失败原因', width: 250, align: 'center'},*/
                {field: 'storageid', title: '电子证照附件id', width: 150, align: 'center', hide: true},
                {field: 'zsid', title: '证书id', width: 150, align: 'center', hide: true},
                {field: 'qszt', title: '权属状态', width: 150, align: 'center', hide: true},
                {field: 'xmid', title: 'xmid', width: 150, align: 'center', hide: true},
                {field: 'gzlslid', title: '工作流实例id', width: 150, align: 'center', hide: true},
                {title: '查看', fixed: 'right', renderer: 'detailView', toolbar: '#barDemo', width: 100}
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
                if(moduleCode){
                    setElementAttrByModuleAuthority(moduleCode);
                }
            }
        });


        // 监听表格操作栏按钮
        table.on('toolbar(dzzzTable)', function (obj) {
            var checkStatus = table.checkStatus(obj.config.id);
            switch (obj.event) {
                case 'createDzzz':
                    createDzzz(checkStatus.data, obj.config.cols[0]);
                    saveDetailLog("DZZZCJ", "创建电子证照", {"data": checkStatus.data});
                    break;
                case  'cancelDzzz':
                    cancelDzzz(checkStatus.data, obj.config.cols[0]);
                    saveDetailLog("DZZZZX", "注销电子证照", {"data": checkStatus.data});
                    break;
                case "zfDzzz":
                    zfDzzz(checkStatus.data, obj.config.cols[0]);
                    saveDetailLog("DZZZZF", "作废电子证照", {"data": checkStatus.data});
                    break;
            }

        });

        //监听工具条
        table.on('tool(dzzzTable)', function (obj) {
            var param = obj.data;
            if (param && param.gzlslid && param.bdcqzh && param.xmid) {
                if (obj.event === "ckdzzz") {
                    // 判断是否有zzbs，如果没有，则提示未生成过电子证照，无法查看
                    if (param.zzbs) {
                        //获取文件中心文件地址，没有 storageid 传递 zsid 到后台处理历史数据
                        if (param.storageid) {
                            viewPdf(param.gzlslid, param.bdcqzh, param.xmid, param.zsid);
                        } else {
                            viewPdf(param.gzlslid, param.bdcqzh, param.xmid, param.zsid);
                        }
                        return false;
                    } else {
                        //证照标识为空但是storageid不为空，是查看带注销水印的证照
                        if (param.storageid) {
                            viewPdf(param.gzlslid, param.bdcqzh, param.xmid);
                        }else{
                            layer.alert("未生成证照pdf，无法查看!");
                        }
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
                url: "/realestate-inquiry-ui/rest/v1.0/dzzzcz/queryDzzzZt",
                type: "POST",
                dataType: 'json',
                contentType: "application/json;charset=UTF-8",
                data: JSON.stringify(zsids),
                // async: false,
                success: function (data) {
                    if (data == 2) {
                        layer.alert("电子证照已为历史状态，不允许再操作！", {title: '提示'});
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


        function viewPdf(gzlslid, bdcqzh, xmid, zsid) {
            var pdfUrl;
            addModel();
            $.ajax({
                url: "/realestate-inquiry-ui/rest/v1.0/dzzzcz/getFilePath",
                type: "GET",
                async: false,
                dataType: 'json',
                data: {gzlslid: gzlslid, bdcqzh: bdcqzh, zsid: zsid},
                success: function (data) {
                    removeModal();
                    if (data == null || data == undefined || data.length == 0 || data.id == null) {
                        reUpload(gzlslid, bdcqzh, xmid);
                        return false;
                    } else {
                        pdfUrl = data.url + '/rest/files/download/' + data.id;
                        var url = getContextPath() + "/view/bdcDzzzCz/bdcDzzzImg.html?storageid="+data.id+"&url="+pdfUrl;
                        //直接弹出下载框
                        var view = layer.open({
                            type: 2,
                            title: "证照查看",
                            maxmin: true,
                            area: ['100%', '100%'],
                            fixed: false, //不固定
                            // content: pdfUrl
                            content: url
                            , end: function (index, layero) {
                                return false;
                            }
                        });
                    }
                },
                error: function (xhr, status, error) {
                    removeModal();
                    failMsg("系统验证发生异常，请重试或联系管理员！");
                }
            });
            // if (pdfUrl) {
            //     pdfUrl = appendIpPort(pdfUrl);
            //     var url = getContextPath() + "/view/bdcDzzzCz/bdcDzzzImg.html?";
            //     //直接弹出下载框
            //     var view = layer.open({
            //         type: 2,
            //         title: "证照查看",
            //         maxmin: true,
            //         area: ['100%', '100%'],
            //         fixed: false, //不固定
            //         // content: pdfUrl
            //         content: url
            //         , end: function (index, layero) {
            //             return false;
            //         }
            //     });
            // }
        }

        // 证照签发并未下载成功
        function reUpload(gzlslid, bdcqzh, xmid) {
            var reSure = layer.open({
                type: 1,
                skin: 'bdc-small-tips',
                title: '提示',
                area: ['320px'],
                content: '获取电子证照文件地址失败！是否重新获取证照？',
                btn: ['确定', '取消'],
                yes: function () {
                    layer.close(reSure);
                    setTimeout(function () {
                        addModel();
                        $.ajax({
                            url: "/realestate-inquiry-ui/rest/v1.0/dzzzcz/reUpload",
                            type: "POST",
                            async: false,
                            dataType: 'json',
                            data: {xmid: xmid},
                            success: function () {
                                removeModal();
                                viewPdf(gzlslid, bdcqzh, xmid);
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

        //创建电子证照
        function createDzzz(d, col) {
            // 获取分页勾选的所有数据
            var checkedData = layui.sessionData('checkedData');
            var zsids = [];
            // var zzbss = new Array();
            // var qszts = new Array();
            var msg = "";
            $.each(checkedData, function (key, value) {
                /* if (value.zzbs && !isNullOrEmpty(value.zzbs)) {
                     msg = value.bdcqzh + ",已生成过电子证照，不能再次生成！";
                     return false;
                 }*/
                // 判断证书对应项目的权属状态
                if (!("1" === value.qszt)) {
                    // qszts.push(value.qszt);
                    msg = value.bdcqzh + ",相关权属状态非现势，不能生成证照！";
                    return false;
                }
                zsids.push(value.zsid);
                console.log(zsids.toString());
            });

            if (msg && !isNullOrEmpty(msg)) {
                warnMsg(msg);
                return false;
            }

            if (zsids.length == 0) {
                warnMsg(" 未选择数据，请选择其中一条数据！");
                return false;
            }

            addModel();
            $.ajax({
                url: "/realestate-inquiry-ui/rest/v1.0/dzzzcz/createHefeiDzzz",
                type: "POST",
                dataType: 'json',
                contentType: "application/json;charset=UTF-8",
                data: JSON.stringify(zsids),
                success: function (data) {
                    if (data.code == 0) {
                        successMsg(data.msg);
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

        //注销电子证照
        function cancelDzzz(d, col) {
            // 获取分页勾选的所有数据
            var checkedData = layui.sessionData('checkedData');
            var xmids = [];
            var zzbss = [];
            var zsids = [];
            $.each(checkedData, function (key, value) {
                xmids.push(value.xmid);
                zsids.push(value.zsid);
                if (checkedData[key].zzbs) {
                    zzbss.push(value.zzbs);
                }
                console.log(zzbss.toString());
            });
            if (xmids.length != zzbss.length) {
                warnMsg("  勾选数据包含未生成电子证照的数据，请检查！");
                return;
            }
            if (xmids.length == 0) {
                warnMsg(" 请选择其中一条数据！");
                return;
            }
            addModel();

            $.ajax({
                url: "/realestate-inquiry-ui/rest/v1.0/dzzzcz/cancelHefeiDzzz",
                type: "POST",
                dataType: 'json',
                contentType: "application/json;charset=UTF-8",
                data: JSON.stringify(zsids),
                success: function (data) {
                    if (data.code == 0) {
                        successMsg(data.msg);
                    }
                    removeModal();
                },
                error: function (xhr, status, error) {
                    delAjaxErrorMsg(xhr);
                    removeModal();
                }
            })

        }

        //作废电子证照
        function zfDzzz(d, col) {
            // 获取分页勾选的所有数据
            var checkedData = layui.sessionData('checkedData');
            var xmids = [];
            var zzbss = [];
            var zsids = [];
            $.each(checkedData, function (key, value) {
                xmids.push(value.xmid);
                zsids.push(value.zsid);
                if (checkedData[key].zzbs) {
                    zzbss.push(value.zzbs);
                }
                console.log(zzbss.toString());
            });
            if (xmids.length != zzbss.length) {
                warnMsg("  勾选数据包含未生成电子证照的数据，请检查！");
                return;
            }
            if (xmids.length == 0) {
                warnMsg(" 请选择其中一条数据！");
                return;
            }
            addModel();

            iszf(zsids).done(function () {
                //判断选择数据是否已经作废，已经作废提示不用再作废
                if (zzzf) {
                    removeModal();
                    return false;
                }

                $.ajax({
                    url: "/realestate-inquiry-ui/rest/v1.0/dzzzcz/zfDzzz",
                    type: "POST",
                    dataType: 'json',
                    contentType: "application/json;charset=UTF-8",
                    data: JSON.stringify(xmids),
                    //async: false,
                    success: function (data) {
                        if (data == null || data == undefined || data.length == 0 || data == 0) {
                            layer.alert("作废电子证照失败！", {title: '提示'});
                        } else {
                            successMsg("作废成功！");
                            $('#search').click();
                        }
                        removeModal();
                    },
                    error: function (xhr, status, error) {
                        delAjaxErrorMsg(xhr);
                        //removeModal();
                    }
                })
            });
        }

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
                    if(moduleCode){
                        setElementAttrByModuleAuthority(moduleCode);
                    }
                    saveDetailLog("DZZZCX", "电子证照查询", obj);
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

    // 登记簿查看
    window.viewBdcDjb = function (zsid, zslx) {
        if (isNullOrEmpty(zsid) || isNullOrEmpty(zslx)) {
            return;
        }

        if ("1" == zslx) {
            window.open("/realestate-register-ui/view/zsxx/bdcZs.html?zsid=" + zsid + "&readOnly=false");
        } else if ("2" == zslx) {
            window.open("/realestate-register-ui/view/zsxx/bdcZm.html?zsid=" + zsid + "&readOnly=false");
        }
    }
    /**
     * 渲染流程名称下拉框
     */
    function loadProcessDefName() {
        $.ajax({
            type: "GET",
            url: getContextPath() + "/rest/v1.0/dzzzcz/lcmc/all",
            contentType: "application/json;charset=utf-8",
            dataType: "json",
            async: false,
            success: function (data) {
                $('#processDefKey').append(new Option("请选择", ""));
                $.each(data, function (index, item) {
                    $('#processDefKey').append(new Option(item.name, item.processDefKey));// 下拉菜单里添加元素
                });
                layui.form.render("select");
            }, error: function (e) {
                response.fail(e,'');
            }
        });
    }
    function formatDzzzzt(dzzzzt) {
        if (dzzzzt == 0){
            return '<span class="">未制证</span>';
        } else if (dzzzzt == 1) {
            return '<span class="">已制证</span>';
        } else if (dzzzzt == 2) {
            return '<span class="">已注销</span>';
        } else {
            return '';
        }
    }
});



