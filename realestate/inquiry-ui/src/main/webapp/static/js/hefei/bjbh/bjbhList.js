layui.use(['form', 'jquery', 'laydate', 'element', 'table'], function () {
    var $ = layui.jquery,
        form = layui.form,
        table = layui.table,
        laydate = layui.laydate;
    $(function () {

        loadProcessDefName()


        // 加载Table
        table.render({
            elem: '#pageTbale',
            toolbar: "#toolBar",
            defaultToolbar: ['filter'],
            url: getContextPath() + "/rest/v1.0/bjbh/page/search",
            request: {
                limitName: 'size' //每页数据量的参数名，默认：limit
            },
            cellMinWidth: 80,
            where: {},
            even: true,
            cols: [[
                {field: 'xh', type: 'numbers', width: '5%', title: '序号'},
                {title: '受理编号', width: '10%', field: 'slbh'},
                {title: '权利人名称', width: '20%', field: 'qlrmc'},
                {title: '权利人证件号', width: '20%', field: 'qlrzjh'},
                {title: '流程名称', width: '20%', field: 'lcmc'},
                {title: '办件编号', width: '15%', field: 'zfxzspbh'},
                {title: '操作', field: 'cz', fixed: 'right', toolbar: '#tool_bar', width: '10%'}
            ]],
            text: {
                none: '未查询到数据'
            },
            autoSort: false,
            page: true,
            parseData: function (res, curr, count) {
                return {
                    code: res.code, //解析接口状态
                    msg: res.msg, //解析提示文本
                    count: res.totalElements, //解析数据长度
                    data: res.content, //解析数据列表
                }
            },
            done: function () {
                $('.layui-table-tool-self').css('right', $('.bdc-export-tools').width() + 17 + 'px');

                if ($('.bdc-table-box .layui-table-main>.layui-table').height() == 0) {
                    $('.layui-table-body .layui-none').html('<img src="../../static/lib/registerui/image/table-none.png" alt="">无数据');
                } else {
                    $('.layui-table-body').height($('.bdc-table-box').height() - 131);
                    $('.layui-table-fixed .layui-table-body').height($('.bdc-table-box').height() - 131 - 17);
                }
            }
        });
        table.on('tool(pageTbale)', function(obj) {
            var data = obj.data; //获得当前行数据
            var layEvent = obj.event;
            if(layEvent === 'takeNumber'){
                takeNumber(data.gzlslid);
            }
        })

        /**
         * 点击查询
         */
        $('#query').on('click', function () {
            // 获取查询内容
            var obj = {
                "slbh" : $('#slbh').val(),
                "lcmc" : $("#selectedDefName").find("option:selected").text(),
                'djkssj' : $('#startTime').val(),
                "djjssj" : $("#endTime").val()
            };
            // 重新请求
            table.reload("pageTbale", {
                where: obj
                , page: {
                    curr: 1  //重新从第 1 页开始
                }
            });
            return false;
        });

        /**
         * 根据工作流示例id取号
         * @param gzlslid
         */
        function takeNumber(gzlslid){
            var obj = {

            }
            $.ajax({
                url: getContextPath() + "/rest/v1.0/bjbh/takeNumber/" + gzlslid,
                dataType: "json",
                type: 'GET',
                async: false,
                success: function (data) {
                    if(data){
                        if(data.success){
                            successMsg("取号成功!");
                        }else{
                            failMsg(data.errorMsg);
                        }
                    }
                    // 重新请求
                    table.reload("pageTbale", {
                        where: obj
                        , page: {
                            curr: 1  //重新从第 1 页开始
                        }
                    });
                },
                error: function (e) {
                    failMsg(e.msg);
                }
            });
        }

        laydate.render({
            elem: '#startTime' //指定元素
            ,type:'datetime',
            format: "yyyy-MM-dd HH:mm:ss",
            isInitValue: false,
            value:new Date(new Date().toLocaleDateString()),
            done: function(value, data, endData) {
                var startDate = new Date($('#endTime').val()).getTime();
                var endTime = new Date(value).getTime();
                if (endTime > startDate) {
                    layer.msg('<img src="../../static/lib/bdcui/images/error-small.png" alt="">登记开始时间不能大于登记结束时间!');
                }
            }
        });
        laydate.render({
            elem: '#endTime' //指定元素
            ,type:'datetime',
            format: "yyyy-MM-dd HH:mm:ss",
            isInitValue: false,
            value:new Date(),
            done: function(value, date) {
                var startDate = new Date($('#startTime').val()).getTime();
                var endTime = new Date(value).getTime();
                if (endTime < startDate) {
                    layer.msg('<img src="../../static/lib/bdcui/images/error-small.png" alt="">登记开始时间不能大于登记结束时间!');
                }
            }
        });
    })
})
/**
 * 渲染流程名称下拉框
 */
function loadProcessDefName() {
    $.ajax({
        type: "GET",
        url: getContextPath() + "/rest/v1.0/bjbh/process/all",
        contentType: "application/json;charset=utf-8",
        dataType: "json",
        async: false,
        success: function (data) {
            $('#selectedDefName').append(new Option("", ""));
            $.each(data, function (index, item) {
                $('#selectedDefName').append(new Option(item.name, item.processDefKey));// 下拉菜单里添加元素
            });
            layui.form.render("select");
        }, error: function (e) {
            failMsg(e.msg);
        }
    });
}


