var type = GetQueryString("type");
layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl','laydate'], function () {
    var $ = layui.jquery;
    var element = layui.element;
    var form = layui.form;
    var table = layui.table;
    var laytpl = layui.laytpl;
    var laydate = layui.laydate;
    var reverseList = ['bdcdyh', 'zl', 'bdcqzh'];
    $(function () {
        //初始化日期控件
        laydate.render({
            elem: '#kssj'
            , type: 'date'
        });
        laydate.render({
            elem: '#jssj'
            , type: 'date'
        });

        //提交表单
        form.on("submit(query)", function (data) {
            tableReload('logTable', data.field);
            return false;
        });
        var logTableConfig = {
            toolbar: "#toolbarSqxx"
            , url: '../cxqq/list/getRzByPage'
            , cols: [[
                {type: 'numbers', width: 50, fixed: 'left',title: '序号'},
                {field: 'jlsj', title: '操作时间',templet:'<div>{{ layui.util.toDateString(d.jlsj, "yyyy-MM-dd HH:mm:ss") }}</div>'},
                {field: 'czlx', title: '操作类型'},
                {field: 'xzqdm', title: '行政区代码'},
                {field: 'czr', title: '操作人'},
                {field: 'zt', title: '状态',
                    templet: function (d) {
                        return rzzt(d.zt);
                    }
                },
                {field: 'hqqqs', title: '获取/上报数量'},
                {
                    title: '错误信息',
                    fixed: 'right',
                    align: 'center',
                    toolbar: '#cz',
                    width: 100
                }
            ]],
            done: function () {
                reverseTableCell(reverseList);
                $('.layui-table-tool-self').css('right', $('.bdc-export-tools').width() + 17 + 'px');
                if ($('.bdc-table-box .layui-table-main>.layui-table').height() == 0) {
                    $('.layui-table-body').height('56px');
                    $('.layui-table-fixed .layui-table-body').height('56px');
                } else {
                    $('.layui-table-body').height($('.bdc-table-box').height() - 131);
                    $('.layui-table-fixed .layui-table-body').height($('.bdc-table-box').height() - 131 - 17);
                }
            }
        };

        var tableConfig = logTableConfig;

        loadDataTablbeByUrl("#logTable", tableConfig);

        // 获取操作类型
        $.ajax({
            url:'../cxqq/list/getCzlx',
            type:"post",
            success:function(data) {
                if (data.gxPeZdRzczlxList != null) {
                    var html = '';
                    data.gxPeZdRzczlxList.forEach(function (item) {
                        html += '<option value='+ item.dm +'>'+ item.mc +'</option>'
                    });
                    $('.layui-czlx select').append(html);
                    form.render('select');
                }
            }
        });

        /**
         *@desc  监听操作工具条
         */
        table.on('tool(dataTable)', function (obj) {
            if(obj.event != "LAYTABLE_COLS"){
                var data = obj.data;
                var layEvent = obj.event;
                if (layEvent == "ckxx") {
                    var logMsg= '';
                    var title;
                    if(data.zt==1){
                        title="成功信息";
                        if (data.successMsg != null) {
                            logMsg = data.successMsg;
                        }
                    }else{
                        title="错误信息";
                        if (data.errorMsg != null) {
                            logMsg = data.errorMsg;
                        }
                    }
                    layer.open({
                        title: title,
                        area: ['430px'],
                        content: '<xmp style="white-space: pre-wrap">' + logMsg + '</xmp>'
                    });
                }
            }
        })

        // 日志状态
        function rzzt(zt) {
            if (zt == 1) {
                return "成功";
            } else {
                return "失败";
            }
        }


    });
});