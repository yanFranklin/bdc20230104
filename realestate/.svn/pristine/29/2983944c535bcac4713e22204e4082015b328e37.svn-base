/**
 * author: <a href="mailto:yousiyi@gtmap.cn>yousiyi</a>
 * version 1.0.0  2020/12/29
 * describe: 电子证照制证
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
        elem: '#startTime',
        type: 'datetime',
        done: function (value, date, endDate) {
            var startDate = new Date(value).getTime();
            var endTime = new Date($('#endTime').val()).getTime();
            if (endTime < startDate) {
                layer.msg('<img src="../../static/lib/bdcui/images/error-small.png" alt="">结束时间不能小于开始时间');
            }
        }
    });
    laydate.render({ //结束时间
        elem: '#endTime',
        type: 'datetime',
        done: function (value, date, endDate) {
            var startDate = new Date($('#startTime').val()).getTime();
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
            title: '电子证照制证列表',
            defaultToolbar: ['filter'],
            request: {
                limitName: 'size', //每页数据量的参数名，默认：limit
                // loadTotal: true,
            },
            even: true,
            cols: [[
                {type: 'checkbox', fixed: 'left'},
                {field: 'xh', type: 'numbers', width: 60, title: '序号'},
                {field: 'qlrmc', title: '权利人姓名', align: 'center'},
                {field: 'zl', title: '坐落', align: 'center'},
                {
                    field: 'szsj', title: '缮证时间', align: 'center',
                    templet: function (d) {
                        return formatDate(d.szsj);
                    }
                },
                {field: 'bdcqzh', title: '不动产权证号'},
                {field: 'zzbs', title: '证照标识', align: 'center'},
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
                    break;
            }

        });

        //创建电子证照
        function createDzzz(data, col) {
            var zsids = [];
            $.each(data, function (key, value) {
                zsids.push(value.zsid);
            });

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
                    removeModal();
                }
            })

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
            // 判断必填条件
            var count = 0;
            $(".required").each(function (i) {
                if (!isNullOrEmpty($(this).val())) {
                    count += 1;
                }
            });
            if (0 == count && isNullOrEmpty($("#zslx").val())) {
                layer.alert("<div style='text-align: center'>请输入必要查询条件！</div>", {title: '提示'});
                return false;
            }

            var startDate = new Date($('#startTime').val()).getTime();
            var endTime = new Date($('#endTime').val()).getTime();
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
                url: "/realestate-inquiry-ui/rest/v1.0/dzzzcz/queryDzzzZz",
                where: obj,
                method: 'POST',
                contentType: 'application/json',
                page: {
                    curr: 1, //重新从第 1 页开始
                    limits: [10, 50, 100, 200, 500]
                },
                done: function (res, curr, count) {
                    currentPageData = res.data;
                    reverseTableCell(reverseList);
                    removeModal();
                    setHeight();
                    // saveDetailLog("DZZZCX", "电子证照查询", obj);
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
});



