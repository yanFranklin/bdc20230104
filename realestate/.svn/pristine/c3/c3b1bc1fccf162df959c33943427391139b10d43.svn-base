/*
*
* 外网预约登记台账
* */
layui.use(['jquery', 'element', 'form', 'table', 'laydate', 'layer'], function () {
    //获取字典
    var $ = layui.jquery;
    var form = layui.form;
    var table = layui.table;
    var layer = layui.layer;
    var laydate = layui.laydate;

    form.render();

    // 获取页面表单标识，用于权限控制
    var moduleCode = $.getUrlParam('moduleCode');

    //判断查询起始时间与结束时间关系
    laydate.render({
        elem: '#yyqssj',
        format: 'yyyy-MM-dd',
        value: new Date(),
        done: function (value, date, endDate) {
            var startDate = new Date(value).getTime();
            var endTime = new Date($('#yyjssj').val()).getTime();
            if (endTime < startDate) {
                layer.msg('<img src="../../static/lib/bdcui/images/error-small.png" alt="">结束时间不能小于开始时间');
            }
        }
    });
    laydate.render({ //结束时间
        elem: '#yyjssj',
        format: 'yyyy-MM-dd',
        value: new Date(),
        done: function (value, date, endDate) {
            var startDate = new Date($('#yyqssj').val()).getTime();
            var endTime = new Date(value).getTime();
            if (endTime < startDate) {
                layer.msg('<img src="../../static/lib/bdcui/images/error-small.png" alt="">结束时间不能小于开始时间');
            }
        }
    });


    $(function () {

        var isSearch = true;
        //监听回车事件
        $(document).keydown(function (event) {
            if (event.keyCode == 13 && isSearch) { //绑定回车
                $(".bdc-search-box #search").click();
            }
        });
        //判断回车操作
        $('.bdc-table-box').on('focus', '.layui-laypage-skip .layui-input', function () {
            isSearch = false;
        }).on('blur', '.layui-laypage-skip .layui-input', function () {
            isSearch = true;
        });


        // 加载Table
        table.render({
            elem: '#yyTable',
            id: 'yyTable',
            toolbar: '#toolbarDemo',
            defaultToolbar: ["filter"],
            request: {
                limitName: 'size', //每页数据量的参数名，默认：limit
                loadTotal: true,
                loadTotalBtn: false
            },
            even: true,
            cols: [[
                {type: 'checkbox', fixed: 'left'},
                {field: 'yyh', title: '预约号', hide: 'true'},
                {field: 'djzxmc', title: '办理地点', minWidth: 160},
                {field: 'djlxmc', title: '登记类型', minWidth: 120},
                {field: 'yysj', title: '预约日期', minWidth: 150},
                {field: 'yysdms', title: '预约时间', minWidth: 250},
                {field: 'yyrmc_tm', title: '姓名', minWidth: 80},
                {field: 'yyrzjh_tm', title: '身份证号码', minWidth: 80},
                {field: 'lxdh_tm', title: '手机号', minWidth: 80},
                {field: 'yyztmc', title: '状态', minWidth: 150, fixed: 'right'}
            ]],
            data: [],
            page: true,
            parseData: function (res) {
                if (isNotBlank(res)) {
                    return {
                        code: res.code, //解析接口状态
                        msg: res.msg, //解析提示文本
                        count: res.totalElements, //解析数据长度
                        data: res.content //解析数据列表
                    }
                } else {
                    return {
                        code: 0, //解析接口状态
                        msg: "成功", //解析提示文本
                        count: 0, //解析数据长度
                        data: [] //解析数据列表
                    }
                }
            },
            done: function () {
                setHeight();
                //无数据时显示内容调整
                if ($('.layui-show .layui-table-body>.layui-table').height() == 0) {
                    $('.layui-table-body .layui-none').html('<img src="../../static/lib/bdcui/images/table-none.png" alt="">无数据');
                }
            }
        });

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

        $('.bdc-table-box').on('mouseenter', 'td', function () {
            $('.layui-table-grid-down').on('click', function () {
                setTimeout(function () {
                    $('.layui-table-tips-main .bdc-table-date').html(reverseString($('.layui-table-tips-main .bdc-table-date').html()));
                }, 20);
            });
        });


        //查询
        function search() {

            // 获取查询参数
            var obj = {};
            $(".search").each(function (i) {
                var name = $(this).attr('name');
                var value = $(this).val();
                if (value) {
                    value = value.replace(/\s*/g, "");
                }
                obj[name] = value;
            });
            //状态处理

            addModel();
            // 重新请求
            table.reload("yyTable", {
                url: "/realestate-etl/wwsq/yydj/page"
                , where: obj
                , page: {
                    curr: 1, //重新从第 1 页开始
                    limits: [10, 50, 100, 200, 500]
                }
                , done: function (res, curr, count) {
                    removeModal();
                    setHeight();
                }
            });
        }


        //监听工具条
        table.on('toolbar(yyTable)', function (obj) {
            var data = obj.data;
            if (obj.event === "exportExcel") {
                var checkStatus = table.checkStatus(obj.config.id);
                exportExcel(checkStatus.data, obj.config.cols[0], "checked");
            } else if (obj.event === 'exportAll') {
                //先后台请求查询所有的数据
                layer.confirm("是否导出全部数据？", {
                    title: "提示",
                    btn: ["确认", "取消"],
                    btn1: function (index) {
                        exportAllExcel(obj.config);
                        layer.close(index);
                    },
                    btn2: function (index) {
                        obj.config.where.type = "";
                        return;
                    }
                })
            } else if (obj.event === 'accept') {
                var checkStatus = table.checkStatus(obj.config.id);
                acceptYydj(checkStatus.data);
            }
        });


        /**
         * 重置清空查询条件
         */
        $('#reset').on('click', function () {
        });

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
    });

    //导出外网申请信息
    // 导出Excel
    // 通过隐藏form提交方式，避免ajax方式不支持下载文件
    /*
    * @param datas 需要导出的数据
    * @cols  导出的列
    * @type 类型 判断是否勾选数据
    * */
    function exportExcel(datas, cols, type) {
        console.log("cols----" + cols);
        if (!isNullOrEmpty(datas)) {
            // isNullOrEmpty(d);
            addModel();
            if ($.isEmptyObject(datas) && type == "checked") {
                warnMsg(" 请选择需要导出Excel的数据行！");
                return;
            }

            // 标题
            var showColsTitle = new Array();
            // 列内容
            var showColsField = new Array();
            // 列宽
            var showColsWidth = new Array();
            for (var index in cols) {
                if (cols[index].hide == false && cols[index].type != 'checkbox' && !cols[index].toolbar) {
                    showColsTitle.push(cols[index].title);
                    showColsField.push(cols[index].field);
                    var width = parseInt(cols[index].width / 100 * 15);
                    if (width <= 20) {
                        width = 20;
                    }
                    showColsWidth.push(width);
                }
            }
            var kssj = $("#yyqssj").val();
            var jssj = $("#yyjssj").val();
            // 设置Excel基本信息
            $("#fileName").val(kssj + '-' + jssj + '外网预约登记查询结果导出excel表');
            $("#sheetName").val(kssj + '-' + jssj + '外网预约登记查询结果导出excel表');
            $("#cellTitle").val(showColsTitle);
            $("#cellWidth").val(showColsWidth);
            $("#cellKey").val(showColsField);
            var data = new Array();
            data = datas;
            $("#data").val(JSON.stringify(data));
            $("#wwyyexcel").submit();

            setTimeout(function () {
                removeModal();
            }, 2000);
        } else {
            warnMsg("请先勾选数据");
        }

    }

    function exportAllExcel(obj) {
        var cols = obj.cols[0];
        var url = '/realestate-etl/wwsq/yydj/export/all';
        var paramData = obj.where;
        $.ajax({
            url: url,
            dataType: "json",
            data: paramData,
            success: function (data) {
                if (data) {//查询成功
                    exportExcel(data, cols, '');
                }
            },
            error: function (xhr) {
                delAjaxErrorMsg(xhr);
            }
        });
    }

    function acceptYydj(data) {
        if (data.length === 0) {
            warnMsg("请选择需要受理的数据");
            return false;
        }
        if (data && data.length > 0) {
            var yybhList = [];
            for (var i = 0; i < data.length; i++) {
                yybhList.push(data[i].yyh);
            }
            if (yybhList) {
                $.ajax({
                    url: '/realestate-etl/wwsq/yydj/yyzt',
                    contentType: "application/json;charset=UTF-8",
                    type: 'POST',
                    data: JSON.stringify(yybhList),
                    success: function (data) {
                        if (data) {//查询成功
                            layer.alert(data);
                        } else {
                            successMsg("受理成功");
                            $('#search').click();
                        }
                    },
                    error: function (xhr) {
                        delAjaxErrorMsg(xhr);
                    }
                });
            }
        }
    }
});