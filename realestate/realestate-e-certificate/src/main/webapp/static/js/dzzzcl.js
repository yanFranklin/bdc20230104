layui.config({
    base: '../../static/' //静态资源所在路径
}).extend({response: 'lib/bdcui/js/response'});

var reverseList = ['zl'];
layui.use(['jquery', 'element', 'form', 'table', 'laydate', 'response'], function () {
    var $ = layui.jquery;
    var table = layui.table;
    var response = layui.response;
    var laydate = layui.laydate;

    //判断缮证起始时间与结束时间关系
    laydate.render({
        elem: '#szrqStart',
        type: 'datetime',
        done: function (value, date, endDate) {
            var szrqStart = new Date(value).getTime();
            var szrqEnd = new Date($('#szrqEnd').val()).getTime();
            if (szrqEnd < szrqStart) {
                layer.msg('<img src="../../static/lib/bdcui/images/error-small.png" alt="">缮证结束时间不能小于开始时间');
            }
        }
    });
    laydate.render({ //结束时间
        elem: '#szrqEnd',
        type: 'datetime',
        done: function (value, date, endDate) {
            var szrqStart = new Date($('#szrqStart').val()).getTime();
            var szrqEnd = new Date(value).getTime();
            if (szrqEnd < szrqStart) {
                layer.msg('<img src="../../static/lib/bdcui/images/error-small.png" alt="">缮证结束时间不能小于开始时间');
            }
        }
    });

    //判断签发日期起始时间与结束时间关系
    laydate.render({
        elem: '#qfrqStart',
        type: 'datetime',
        done: function (value, date, endDate) {
            var qfrqStart = new Date(value).getTime();
            var qfrqEnd = new Date($('#qfrqEnd').val()).getTime();
            if (qfrqEnd < qfrqStart) {
                layer.msg('<img src="../../static/lib/bdcui/images/error-small.png" alt="">签发结束时间不能小于开始时间');
            }
        }
    });
    laydate.render({ //结束时间
        elem: '#qfrqEnd',
        type: 'datetime',
        done: function (value, date, endDate) {
            var qfrqStart = new Date($('#qfrqStart').val()).getTime();
            var qfrqEnd = new Date(value).getTime();
            if (qfrqEnd < qfrqStart) {
                layer.msg('<img src="../../static/lib/bdcui/images/error-small.png" alt="">签发结束时间不能小于开始时间');
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
            title: '电子证照制证列表',
            defaultToolbar: ['filter'],
            request: {
                limitName: 'size', //每页数据量的参数名，默认：limit
                loadTotal: true,
                loadTotalBtn: false
            },
            even: true,
            cols: [[
                {type: 'checkbox', fixed: 'left'},
                {field: 'BDCQZH', title: '产权证号', minWidth: 300, align: 'center'},
                {field: 'ZL', title: '坐落', minWidth: 300, align: 'center'},
                {
                    field: 'ZZBFRQ', title: '缮证日期', align: 'center',
                    templet: function (d) {
                        return formatDate(d.ZZBFRQ);
                    }
                },
                {
                    field: 'STATUS', title: '状态', align: 'center', width: 200
                    , templet: function (d) {
                        if (notNull(d.STATUS)) {
                            return statusDict(d.STATUS);
                        } else {
                            return "";
                        }
                    }
                },
                {
                    field: 'ZZQZSJ', title: '签发日期', align: 'center',
                    templet: function (d) {
                        return formatDate(d.ZZQZSJ);
                    }
                },
                {
                    field: 'MSG', title: '信息'
                    , templet: function (d) {
                        if (notNull(d.MSG)) {
                            return msgDict(d.MSG);
                        } else {
                            return "";
                        }
                    }
                },
                {field: 'ZZBS', title: '证照标识', width: 100, align: 'center', hide: true}
            ]],
            data: [],
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
                setHeight();
                reverseTableCell(reverseList);
            }
        });


        // 监听表格操作栏按钮
        table.on('toolbar(dzzzTable)', function (obj) {
            var checkStatus = table.checkStatus(obj.config.id);
            console.info(checkStatus.data);
            switch (obj.event) {
                case 'createDzzz':
                    createDzzz(checkStatus.data);
                    break;
                case 'downloadDzzz':
                    downloadDzzz(checkStatus.data);
                    break;
            }

        });

        function dealResponse(type, size, length) {
            if (size === 0) {
                warnMsg("全部" + type + "失败");
            } else if (size < length && size !== 0) {
                successMsg(type + "成功 :" + size + "条");
            } else {
                successMsg("全部" + type + "成功");
            }
        }

        //签发电子证照
        function createDzzz(data) {
            var flag = true;
            $.each(data, function (key, value) {
                if (value.STATUS != '3') {
                    warnMsg("仅申请签发异常的数据可以重新签发!")
                    flag = false;
                    return;
                }
            });
            if (false == flag) {
                return;
            }
            var bdcqzhs = [];
            $.each(data, function (key, value) {
                if (isNotBlank(value.BDCQZH)) {
                    bdcqzhs.push(value.BDCQZH);
                }
            });

            if (bdcqzhs.length == 0) {
                warnMsg(" 未选择数据，请至少选择其中一条数据！");
                return false;
            }

            addModel();
            $.ajax({
                url: "/realestate-e-certificate/clxx/dzzz",
                type: "POST",
                dataType: 'json',
                contentType: "application/json;charset=UTF-8",
                data: JSON.stringify(bdcqzhs),
                success: function (data) {
                    dealResponse("签发", data, bdcqzhs.length);
                    removeModal();
                },
                error: function (err) {
                    removeModal();
                    response.fail(err);
                }
            });
        }

        //下载电子证照 pdf
        function downloadDzzz(data, col) {
            var flag = true;
            $.each(data, function (key, value) {
                if (value.STATUS != '4' && value.STATUS != '1') {
                    warnMsg("仅下载 pdf 异常或未下载 pdf 数据可以重新下载！")
                    flag = false;
                    return;
                }
            });
            if (false == flag) {
                return;
            }
            var zzbss = [];
            $.each(data, function (key, value) {
                if (isNotBlank(value.ZZBS)) {
                    zzbss.push(value.ZZBS);
                }
            });

            if (data.length!= 0 && zzbss.length != data.length) {
                warnMsg("数据异常，zzbs 为空无法下载 pdf");
                return false;
            }

            if (zzbss.length == 0) {
                warnMsg(" 未选择数据，请至少选择其中一条数据！");
                return false;
            }

            addModel();
            $.ajax({
                url: "/realestate-e-certificate/clxx/dzzz/pdf",
                type: "POST",
                dataType: 'json',
                contentType: "application/json;charset=UTF-8",
                data: JSON.stringify(zzbss),
                success: function (data) {
                    dealResponse("下载", data, zzbss.length);
                    removeModal();
                },
                error: function (err) {
                    removeModal();
                    response.fail(err);
                }
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

            var szrqStart = new Date($('#szrqStart').val()).getTime();
            var szrqEnd = new Date($('#szrqEnd').val()).getTime();
            if (szrqStart > szrqEnd) {
                layer.msg('<img src="../../static/lib/bdcui/images/error-small.png" alt="">缮证结束时间不能小于开始时间');
                return false;
            }
            var qfrqStart = new Date($('#qfrqStart').val()).getTime();
            var qfrqEnd = new Date($('#qfrqEnd').val()).getTime();
            if (qfrqStart > qfrqEnd) {
                layer.msg('<img src="../../static/lib/bdcui/images/error-small.png" alt="">签发结束时间不能小于开始时间');
                return false;
            }
            // 获取查询参数
            var obj = {};
            $(".cxtj").each(function (i) {
                var name = $(this).attr('name');
                if (!isNullOrEmpty($(this).val())) {
                    obj[name] = $(this).val();
                }
            });

            addModel();
            // 重新请求
            table.reload("dzzzTable", {
                url: "/realestate-e-certificate/clxx",
                where: obj,
                page: {
                    curr: 1, //重新从第 1 页开始
                    limits: [10, 50, 100, 200, 500]
                },
                done: function (res, curr, count) {
                    currentPageData = res.data;
                    reverseTableCell(reverseList);
                    removeModal();
                    setHeight();
                }
            });
        }

        /**
         * 重置清空查询条件
         */
        $('#reset').on('click', function () {
        });

        function statusDict(status) {
            if ("1" == status) {
                return "签发成功未下载";
            } else if ("2" == status) {
                return "签发电子证照完成";
            } else if ("3" == status) {
                return "申请签发异常";
            } else if ("4" == status) {
                return "下载 pdf 异常";
            } else {
                return status;
            }
        }

        function msgDict(msg) {
            if ("success" == msg) {
                return "成功";
            } else if ("0" == msg) {
                return "成功";
            } else if ("1" == msg) {
                return "未获得授权";
            } else if ("2" == msg) {
                return "服务超时";
            } else if ("3" == msg) {
                return "未检索到记录";
            } else if ("4" == msg) {
                return "请求参数错误";
            } else if ("5" == msg) {
                return "缺失必填字段";
            } else if ("6" == msg) {
                return "已生成过电子证照PDF";
            } else if ("7" == msg) {
                return "电子证照信息已入库";
            } else if ("8" == msg) {
                return "上传附件中心失败";
            } else if ("9" == msg) {
                return "生成电子证照PDF失败";
            } else if ("10" == msg) {
                return "电子证照签章失败";
            } else if ("11" == msg) {
                return "电子证照已注销";
            } else if ("12" == msg) {
                return "生成zzbs失败";
            } else if ("13" == msg) {
                return "验证数字签名失败";
            } else if ("22" == msg) {
                return "base64码转文件失败";
            } else if ("-1" == msg) {
                return "系统请求错误";
            } else {
                return msg;
            }
        }
    });
});



