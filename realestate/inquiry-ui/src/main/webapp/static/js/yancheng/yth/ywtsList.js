/**
 * author: <a href="mailto:yaoyi@gtmap.cn>yaoyi</a>
 * version 1.0.0  2021/2/2
 * describe: 一体化推送数据
 */
var laydate, form;

layui.use(['form', 'jquery', 'element', 'table', 'laydate'], function () {
    var $ = layui.jquery,
        laydate = layui.laydate,
        table = layui.table,
        form = layui.form;


    $(function () {

        // 流程名称
        $.ajax({
            type: "GET",
            url: getContextPath() + "/rest/v1.0/yth/wsyw/process",
            contentType: "application/json;charset=utf-8",
            dataType: "json",
            async: false,
            success: function (data) {
                $('#selectedWsywDefName').append(new Option("请选择", ""));
                $.each(data, function (index, item) {
                    $('#selectedWsywDefName').append(new Option(item.name, item.processDefKey));// 下拉菜单里添加元素
                });
                form.render("select");
            }, error: function (e) {
                response.fail(e, '');
            }
        });

        //2.渲染起止时间，限制截止时间大于开始时间
        lay('.test-item').each(function () {
            //初始化日期控件
            var startDate = laydate.render({
                elem: '#kssj'
                , format: 'yyyy-MM-dd HH:mm:ss'
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
                , format: 'yyyy-MM-dd HH:mm:ss'
                , trigger: 'click'
            });

        });

        // 加载Table
        var pageUrl = '/realestate-inquiry-ui/rest/v1.0/yth/task/complete';
        table.render({
            elem: "#pageTable",
            toolbar: "#toolbar",
            title: "一体化业务台账",
            url: pageUrl,
            method: 'post',
            even: true,
            request: {
                limitName: 'size', //每页数据量的参数名，默认：limit
                loadTotal: true,
                loadTotalBtn: false
            },
            limits: [10, 30, 50, 70, 90, 110, 130, 150],
            defaultToolbar: ['filter'],
            cols: [[
                {field: 'spxtywh', minWidth: 100, title: '外网受理编号'},
                {field: 'slbh', minWidth: 100, title: '登记受理编号'},
                {field: 'qlr', minWidth: 100, title: '权利人'},
                {field: 'zl', minWidth: 250, title: '坐落'},
                {field: 'bdcdyh', minWidth: 270, title: '不动产单元号'},
                {field: 'taskName', title: '流程节点', width: 90},
                {minWidth: 150,  templet: '#barAction', title: '信息推送', fixed: 'right',sort: false,}
            ]],
            parseData: function (res) { //res 即为原始返回的数据
                return {
                    "code": res.code, //解析接口状态
                    "msg": res.message, //解析提示文本
                    "count": res.totalElements, //解析数据长度
                    "data": res.content //解析数据列表
                };
            },
            page: true,
            done: function () {
                $('.layui-table-tool-self').css('right', $('.bdc-export-tools').width() + 17 + 'px');
                if ($('.bdc-list-tab .layui-tab-content .layui-show .layui-table-main>.layui-table').height() == 0) {
                    $('.bdc-list-tab .layui-tab-content .layui-show .layui-table-body .layui-none').html('<img src="../static/lib/bdcui/images/table-none.png" alt="">无数据');
                } else {
                    $('.bdc-list-tab .layui-tab-content .layui-show .layui-table-body').height($('.bdc-content-box').height() - 196 - $('.bdc-task-tab').innerHeight() - $('.bdc-list-tab .layui-show .bdc-search-box').height());
                    $('.bdc-list-tab .layui-tab-content .layui-show .layui-table-fixed .layui-table-body').height($('.bdc-content-box').height() - 196 - $('.bdc-task-tab').innerHeight() - $('.bdc-list-tab .layui-show .bdc-search-box').height() - 17);
                }
                var reverseList = ['zl'];
                reverseTableCell(reverseList);
            }
        });

        //4.查询 按钮
        $('#search').on('click', function () {
            search();
        });

        //监听工具条
        table.on('tool(pageTable)', function (obj) {
            var data = obj.data;
            if (obj.event === 'slxxts') {
                slxxts(data);
            } else if (obj.event === 'shxxts') {
                shxxts(data);
            }else if (obj.event === 'czxxts') {
                czxxts(data);
            }
        });

    });

    // 查询方法
    function search() {
        var requestConditions = new Array();
        requestConditions.push(condition("slbh", "like", $("#slbh").val()));
        requestConditions.push(condition("bdcdyh", "like" , $("#bdcdyh").val()));
        requestConditions.push(condition("zl", "like" , $("#zl").val()));
        requestConditions.push(condition("spxtywh", "like" , $("#spxtywh").val()));
        requestConditions.push(condition("processKey", "in" , $("#selectedWsywDefName").val()));
        requestConditions.push(condition("startTime_complete", "gt" , $("#kssj").val()));
        requestConditions.push(condition("startTime_complete", "elt" , $("#jzsj").val()));
        // 重新请求
        table.reload("pageTable", {
            where: { param : JSON.stringify(requestConditions)},
            page: {
                curr: 1  //重新从第 1 页开始
            }
        });
    }

    function condition(key,judge,value){
        return {
            requestKey : key,
            requestJudge : judge,
            requestValue : value == undefined? "" : value,
        }
    }


    // 推送受理信息
    function slxxts(data){
        console.info(data);
        $.ajax({
            type: "GET",
            url: getContextPath() + "/rest/v1.0/yth/tsslxx?gzlslid=" + data.processInstanceId,
            contentType: "application/json;charset=utf-8",
            dataType: "json",
            async: false,
            success: function (data) {
                console.info(data);
                if(data.success){
                    successMsg("推送成功");
                }else{
                    failMsg(data.fail.message);
                }
            }, error: function (e) {
                console.info(e);
                failMsg("推送失败");
            }
        });
    }

    // 推送审核信息
    function shxxts(data){
        $.ajax({
            type: "GET",
            url: getContextPath() + "/rest/v1.0/yth/tsshxx?gzlslid=" + data.processInstanceId,
            contentType: "application/json;charset=utf-8",
            dataType: "json",
            async: false,
            success: function (data) {
                console.info(data);
                if(data.success){
                    successMsg("推送成功");
                }else{
                    failMsg(data.fail.message);
                }
            }, error: function (e) {
                console.info(e);
                failMsg("推送失败");
            }
        });
    }
    // 推送出证信息
    function czxxts(data){
        $.ajax({
            type: "GET",
            url: getContextPath() + "/rest/v1.0/yth/tsfzxx?gzlslid=" + data.processInstanceId,
            contentType: "application/json;charset=utf-8",
            dataType: "json",
            async: false,
            success: function (data) {
                console.info(data);
                if(data.success){
                    successMsg("推送成功");
                }else{
                    failMsg(data.fail.message);
                }
            }, error: function (e) {
                console.info(e);
                failMsg("推送失败");
            }
        });
    }
});
