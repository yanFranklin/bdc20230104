/**
 * author:前端组
 * data:2019-03-09
 * version 3.0.0
 * describe:分页插件配置说明
 */

//以下参数请写在layui.use外围定义为全局变量
// url tableID tablecol ajaxmethod  为必填项 缺一不可
// toolbar  defaultToolbar  需要则写不需要则不写
// 所有配置的参数都可以从当前已有的表格加载方法中提取，注意，提取的参数要完整
// 提取完毕后将原有的加载表格部分方法注释或删除
// html以及引用方式详见fenye.html

//下方为原内容
layui.use(['table','laytpl','layer'],function () {
    var table = layui.table,
        $ = layui.jquery,
        layer = layui.layer,
        laypage=layui.laypage;
    $(function () {
        table.render({
            elem: '#pageTable',
            id: 'idTest',
            toolbar: '#toolbarDemo',
            title: '用户数据表',
            defaultToolbar: ['filter'],
            method: 'post',
            even: true,
            request: {
                limitName: 'size' //每页数据量的参数名，默认：limit
            },
            url: "http://192.168.50.139:8087/realestate-portal-ui/rest/v1.0/task/all",
            cols: [[
                {type: 'checkbox', width:50, fixed: 'left'},
                {type:'numbers', title:'ID', width:80, fixed: 'left', unresize: true},
                {field:'processInstanceName', title:'中心名称', width:200},
                {field:'procDueTime', title:'时间', width:160, sort: true},
                {field:'procDefName', title:'登记类型'},
                {field:'startUserName', title:'抽取人',width:100, sort: true},
                {field:'processInstanceName', title:'质检人'},
                {field:'startUserId', title:'坐落'},
                {fixed:'right', title:'操作', toolbar: '#barDemo', width:180}
            ]],
            // page: true,
            limits: [10,50,100,200,500],
            parseData: function (res) { //res 即为原始返回的数据
                res.content.forEach(function (v) {
                    if (v.startTime) {
                        var newStartTime = new Date(v.startTime);
                        v.newStartTime = newStartTime.toLocaleString();
                    }
                    if (v.endTime) {
                        var newEndTime = new Date(v.endTime);
                        v.newEndTime = newEndTime.toLocaleString();
                    }
                });
                return {
                    "code": res.code, //解析接口状态
                    "msg": res.message, //解析提示文本
                    "count": res.totalElements, //解析数据长度
                    "data": res.content //解析数据列表
                };
            },
            data: [111],
            done: function () {
                $('.layui-table-tool-self').css('right',$('.bdc-export-tools').width()+ 17 + 'px');

                if($('.layui-table-body>.layui-table').height() == 0){
                    $('.layui-table-body .layui-none').html('<img src="../images/table-none.png" alt="">无数据');
                }
            }
        });


    });
});