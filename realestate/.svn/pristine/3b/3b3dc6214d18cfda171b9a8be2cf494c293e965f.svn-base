/**
 * describe: 抵押统计月报js
 */
layui.config({
    base: '../../static/lib/form-select/'
}).extend({
    formSelects: 'formSelects-v4'
});
layui.use(['jquery', 'form', 'table', 'laydate', 'formSelects'], function () {
    //获取字典
    var $ = layui.jquery,
        form = layui.form,
        table = layui.table,
        laydate = layui.laydate,
        formSelects = layui.formSelects;
    // 日报数据信息
    var dyatjDayData = [];
    var tjxx = {};

    $(function () {
        // 1.默认渲染部门名称，选中名称后，根据名称渲染办理人员
        getDataByAjax('/rest/v1.0/process/rootOrgs', '', 'GET', function (data) {
            var bmData = [];
            data.forEach(function (v) {
                bmData.push({"name": v.name, "value": v.name});
            });
            formSelects.data('selectBmmc', 'local', {
                arr: bmData
            });
        });

        //2.渲染起止时间，限制截止时间大于开始时间
        lay('.test-item').each(function () {
            //初始化日期控件
            var startDate = laydate.render({
                elem: '#kssj'
                , trigger: 'click',
                done: function (value, date) {
                    //选择的结束时间大
                    if ($('#jzsj').val() != '' && !completeDate($('#jzsj').val(), value)) {
                        $('#jzsj').val('');
                        $('.laydate-disabled.layui-this').removeClass('layui-this');
                        layer.msg('<img src="../../static/lib/bdcui/images/error-small.png" alt="">结束时间不能小于开始时间');
                    }
                    endDate.config.min = {
                        year: date.year,
                        month: date.month - 1,
                        date: date.date
                    }
                }
            });
            var endDate = laydate.render({
                elem: '#jzsj'
                , trigger: 'click'
            });

        });

        //4.定义table的cols，显示默认表格
        // 获取填报信息
        renderTbxx();
        init();

        //5.查询 按钮
        $('#search').on('click', function () {
            search();
        });
        //6.回车查询
        $('.bdc-pjtj').on('keydown', function (event) {
            if (event.keyCode == 13) {
                search();
            }
        });

        //7. 输入框删除图标
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
        table.on('toolbar(pageTable)', function (obj) {
            var checkStatus = table.checkStatus(obj.config.id);
            switch (obj.event) {
                case 'print':
                    tjxx["list"] = dyatjDayData;
                    printTjxx(tjxx, "dyatjDay", dyatjDayUrl);
                    break;
                case 'export':
                    exportExcel(dyatjDayData, obj.config.cols[0]);
                    break;
            }
        });
    });

    // 点击查询
    $('#search').on('click', function () {
        search();
    });
    // 打印
    $('#printBtn').on('click', function () {
        tjxx["list"] = dyatjDayData;
        printTjxx(tjxx, "dyatjDay", dyatjDayUrl);
    });

    // 表格信息
    function init() {
        table.render({
            elem: '#pageTable',
            toolbar: '#toolbarDemo',
            title: '抵押统计日报查询',
            //toolbar: '#toolbarDemo',
            defaultToolbar: [],
            even: true,
            cols: [[
                {field: 'djrq', title: '日期', width: 150},
                {field: 'daySum', title: '当日抵押数量', minWidth: 150},
                {field: 'dayDyaJeSum', title: '当日抵押金额（亿）', minWidth: 150},
                {field: 'sum', title: '累计数量', minWidth: 150},
                {field: 'dyaJeSum', title: '累计金额（亿）', minWidth: 150}
            ]],
            data: dyatjDayData,
            page: true,
            done: function () {
                setHeight();
            }
        });
    }

    // 获取填报信息
    function renderTbxx() {
        $.ajax({
            url: "/realestate-inquiry-ui/rest/v1.0/dya/dyaTjTbxx",
            type: 'GET',
            dataType: 'json',
            success: function (data) {
                if (data) {
                    tjxx = data;
                }
            }, error: function () {
            }, complete: function () {
            }
        });
    }


    // 查询事件
    function search() {
        var cxkssj = $("#kssj").val();
        var cxjssj = $("#jzsj").val();
        var bmmc = formSelects.value('selectBmmc', 'val')[0];
        // 更新填报部门名称为当前选择的部门
        tjxx["dwmc"] = bmmc;
        if (isNullOrEmpty(cxkssj) || isNullOrEmpty(cxjssj)) {
            warnMsg("开始时间和截至时间不可为空！");
            return;
        }
        addModel();
        var searchData = {"cxkssj": cxkssj, "cxjssj": cxjssj, "bmmc": bmmc};

        $.ajax({
            url: "/realestate-inquiry-ui/rest/v1.0/dya/dyaTjDay",
            type: 'POST',
            dataType: 'json',
            contentType: "application/json;charset=UTF-8",
            data: JSON.stringify(searchData),
            success: function (data) {
                if (data) {
                    dyatjDayData = data;
                    dealData(dyatjDayData);
                }
            }, error: function () {
            }, complete: function () {
                init();
                removeModal();
            }
        });
    }



    /**
     * 导出Excel通过隐藏form提交方式，避免ajax方式不支持下载文件
     * @date 2019.05.24 14:45
     * @author zhuyong
     * @return
     */
    function exportExcel(d, cols) {
        if(!d || d.length == 0){
            layer.msg('<img src="../../static/lib/bdcui/images/error-small.png" alt="">请先统计数据');
            return;
        }
        // 标题
        var showColsTitle = [];
        // 列内容
        var showColsField = [];
        // 列宽
        var showColsWidth = [];
        for (var index in cols) {
            if (cols[index].hide == false && cols[index].type != 'checkbox' && !cols[index].toolbar) {
                showColsTitle.push(cols[index].title);
                showColsField.push(cols[index].field);
                if (cols[index].width > 0) {
                    showColsWidth.push(parseInt(cols[index].width / 100 * 15));
                } else if (cols[index].minWidth > 0) {
                    showColsWidth.push(parseInt(cols[index].minWidth / 100 * 15));
                } else {
                    showColsWidth.push(200 / 100 * 15);
                }
            }
        }

        var data = d;
        for (var i = 0; i < data.length; i++) {
            data[i].xh = i + 1;
        }

        // 设置Excel基本信息
        $("#fileName").val('抵押日报');
        $("#sheetName").val("抵押日报");
        $("#cellTitle").val(showColsTitle);
        $("#cellWidth").val(showColsWidth);
        $("#cellKey").val(showColsField);
        $("#data").val(JSON.stringify(data));
        $("#form").submit();
    }
});