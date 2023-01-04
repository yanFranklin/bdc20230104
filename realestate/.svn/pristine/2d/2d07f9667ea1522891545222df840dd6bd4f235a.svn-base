/**
 * describe: 抵押统计月报js
 */
layui.config({
    base: '../../static/' //静态资源所在路径
}).extend({response: 'lib/bdcui/js/response', formSelects: 'lib/form-select/formSelects-v4'});
layui.use(['jquery', 'form', 'table', 'laydate', 'formSelects'], function () {
    //获取字典
    var $ = layui.jquery,
        form = layui.form,
        table = layui.table,
        laydate = layui.laydate,
        laytpl = layui.laytpl,
        formSelects = layui.formSelects;
    // 日报数据信息
    var dyatjMonth = [];
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
        renderTbxx();
        renderFormList();

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
        // 打印
        $('#printBtn').on('click', function () {
            tjxx["list"] = dyatjMonth;
            printTjxx(tjxx, "dyatjMonth", dyatjMonthUrl)
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
        table.on('toolbar(jgryFilter)', function (obj) {
            var checkStatus = table.checkStatus(obj.config.id);
            switch (obj.event) {
                case 'exportExcel':
                    exportExcel(checkStatus.data, obj.config.cols[0], "checked");
                    break;
            }
        });
    });

    // 渲染模板数据
    function renderFormList() {
        var getTpl = dytjxx.innerHTML; //获取自己定义的模板元素
        laytpl(getTpl).render(dyatjMonth, function (html) {
            $("#dataTitle").html(html); //加载到主元素中
        });
        form.render();
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
                    form.val('form', data);
                }
            }, error: function () {
            }, complete: function () {
            }
        });
    }

    // 查询抵押统计日报信息
    function search() {
        var cxkssj = $("#kssj").val();
        var cxjssj = $("#jzsj").val();
        if (isNullOrEmpty(cxkssj) || isNullOrEmpty(cxjssj)) {
            warnMsg("开始时间和截至时间不可为空！");
            return;
        }

        // 部门名称为多选
        var bmmc = formSelects.value('selectBmmc', 'val');
        if (isEmptyObject(bmmc)) {
            warnMsg("请选择部门信息！");
            return;
        }

        addModel();
        var searchData = {"cxkssj": cxkssj, "cxjssj": cxjssj, "bmmcList": bmmc};

        $.ajax({
            url: "/realestate-inquiry-ui/rest/v1.0/dya/dyaTjMonth",
            type: 'POST',
            dataType: 'json',
            contentType: "application/json;charset=UTF-8",
            data: JSON.stringify(searchData),
            success: function (data) {
                if (data) {
                    dyatjMonth = data;
                    dealData(dyatjMonth);
                }
            }, error: function () {
            }, complete: function () {
                renderFormList();
                removeModal();
            }
        });
    }
});