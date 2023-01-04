var moduleCode = GetQueryString("moduleCode");
layui.config({
    base: '../static/lib/form-select/'
}).extend({
    formSelects: 'formSelects-v4'
});
layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl', 'laydate','formSelects'], function () {
    var $ = layui.jquery;
    var form = layui.form;
    var table = layui.table;
    var laydate = layui.laydate;
    var laytpl = layui.laytpl;
    var formSelects = layui.formSelects;
    $(function () {
        getJkmc(formSelects);
        // 加载字典
        form.render();
        //初始化日期控件
        var kssj = laydate.render({
            elem: '#kssj',
            type: 'datetime',
            trigger: 'click',
            done: function (value, date) {
                //选择的结束时间大
                if ($('#jssj').val() != '' && !completeDate($('#jssj').val(), value)) {
                    $('#jssj').val('');
                    $('.laydate-disabled.layui-this').removeClass('layui-this');
                }
                jssj.config.min = {
                    year: date.year,
                    month: date.month - 1,
                    date: date.date,
                    hours: date.hours,
                    minutes: date.minutes,
                    seconds: date.seconds
                }
            }
        });
        var jssj = laydate.render({
            elem: '#jssj',
            type: 'datetime',
            trigger: 'click'
        });


        //比较起止时间
        function completeDate(date1, date2) {
            var oDate1 = new Date(date1);
            var oDate2 = new Date(date2);
            if (oDate1.getTime() > oDate2.getTime()) {
                return true;
            } else {
                return false;
            }
        }

        //2.默认渲染部门名称，选中名称后，根据名称渲染办理人员
        getDataByAjax('/rest/v1.0/listOrgs','','GET',function (data) {
            var bmData = [];
            data.forEach(function (v) {
                bmData.push({"name": v.name, "value": v.id});
            });
            formSelects.data('selectBmmc', 'local', {
                arr: bmData
            })
        });
        formSelects.on('selectBmmc', function(id, vals, val){
            getDataByAjax('/rest/v1.0/users/'+val.value,'','GET',function (param) {
                console.info(param);
                var ryData = [];
                param.forEach(function (v) {
                    ryData.push({"name": v.alias, "value": v.alias});
                });
                formSelects.data('selectBjry', 'local', {
                    arr: ryData
                });
            });
        });

        var shijigxtableConfig = {
            url: '../rest/v1.0/yancheng/shiji/list/query/log'
            , limits: [10, 50, 100, 200, 500]
            // ,method: 'post'
            ,toolbar: "#toolbar"
            //查询接口名称、查询人、查询人所属部门、查询时间
            , cols: [[
                {type: 'checkbox', width: 50, fixed: 'left'},
                {field: 'interfaceName', title: '查询接口名称'},
                {field: 'creater', title: '查询人'},
                {field: 'department', title: '查询人所属部门'},
                // {field: 'queryTime', title: '查询时间'}
                {field: 'queryTime', title: '查询时间',
                    templet: function (d) {
                        return formatDate(d.queryTime);
                    }
                }
            ]],
            data: [],
            done: function () {
                $('.layui-table-tool-self').css('right', $('.bdc-export-tools').width() + 17 + 'px');

                if ($('.bdc-table-box .layui-table-main>.layui-table').height() == 0) {
                    $('.layui-table-body').height('60px');
                    $('.layui-table-fixed .layui-table-body').height('60px');
                    $('.layui-table-body .layui-none').html('<img src="../static/images/table-none.png" alt="">无数据');
                } else {
                    $('.layui-table-body').height($('.bdc-table-box').height() - 131);
                    $('.layui-table-fixed .layui-table-body').height($('.bdc-table-box').height() - 131 - 17);
                }
            }
        };
        //台账查询地址
        var shijigxUrl = "../rest/v1.0/yancheng/shiji/list/query/log";

        loadDataTablbeByUrl("#shijigxTable", shijigxtableConfig);
        //提交表单
        form.on("submit(query)", function (data) {
            layui.use(['table'], function () {
                var table = layui.table;
                table.reload("shijigxTable", {
                    where: data.field
                    ,toolbar: "#toolbar"
                    , page: {
                        //重新从第 1 页开始
                        curr: 1
                    },
                    url: shijigxUrl
                });
            });
            return false;
        });

        table.on('toolbar(shijigxTable)', function (obj) {
            switch (obj.event) {
                case 'export':
                    $("#downloadExcel").submit();
            }
        });
        // table.on('tool(dataTable)', function (obj) {
        //     var data = obj.data;
        //     if (data) {
        //         if (obj.event === "download") {
        //             if (data.certinstCode || data.gcbm) {
        //                 addModel();
        //                 $("#gcbm").val(data.gcbm);
        //                 $("#downloadZip").submit();
        //             } else {
        //                 layer.msg("所选数据没有工程编码或竣工验收备案表编号")
        //             }
        //         }
        //     } else {
        //         layer.alert("当前数据缺失，请检查数据");
        //         return false
        //     }
        // });

    });

    function getDataByAjax(_path, _param,_type, _fn, async) {
        var _url = getContextPath() + _path;
        var getAsync = false;
        if(async){
            getAsync = async
        }
        $.ajax({
            url: _url,
            type: _type,
            async: getAsync,
            contentType: 'application/json;charset=utf-8',
            data: _param,
            success: function (data) {
                _fn.call(this, data, data);
            },
            error: function (err) {
                console.log(err);
            }
        });
    }

    function getJkmc(formSelects){
        $.ajax({
            url: getContextPath() + "/rest/v1.0/interface/info",
            dataType: "json",
            async: false,
            success: function (data) {
                formSelects.data('jkmc', 'local', {
                    arr: data
                });
            },
            error: function (e) {
                layer.msg("获取接口名称异常", {icon: 5, time: 3000});
            }
        });
    }
});