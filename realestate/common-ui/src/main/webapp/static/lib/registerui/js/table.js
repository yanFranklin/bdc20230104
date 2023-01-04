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
        /*
         * 表格渲染
         * 此处为获取数据后重新渲染表格
         * 使用中方法内的table配置已实际使用情况为准（表头以及table容器ID）
         * 此处仅作为Demo使用
         */
        //页面表格所需数据ajax来源url
        var url="http://192.168.50.139:8087/realestate-portal-ui/rest/v1.0/task/all";
        //表格配置
        var tablecol=[[
            {type: 'checkbox', width:50, fixed: 'left'},
            {type:'numbers', title:'ID', width:80, fixed: 'left', unresize: true},
            {field:'processInstanceName', title:'中心名称', width:200},
            {field:'procDueTime', title:'时间', width:160, sort: true},
            {field:'procDefName', title:'登记类型'},
            {field:'startUserName', title:'抽取人',width:100, sort: true},
            {field:'processInstanceName', title:'质检人'},
            {field:'startUserId', title:'坐落'},
            {fixed:'right', title:'操作', toolbar: '#barDemo', width:180}
        ]];
        //表格容器ID
        var tableID="#pageTable";
        //接口ajax链接方式post或get
        var ajaxmethod='post';
        //工具条
        var toolbar="#toolbarDemo";
        var defaultToolbar=['filter'];
        //layui分页器容器ID
        var laypageID="#laypage";
        Window.pageStart(url,tablecol,tableID,ajaxmethod,toolbar,defaultToolbar,laypageID);




        var urlA="http://192.168.50.139:8087/realestate-portal-ui/rest/v1.0/task/all";
        //表格配置
        var tablecolA=[[
            {type: 'checkbox', width:50, fixed: 'left'},
            {field:'executionId', title:'ID', width:80, fixed: 'left', unresize: true},
            {field:'formKey', title:'市', width:80},
            {field:'processDefId', title:'中心名称', width:200},
            {field:'processDefName', title:'时间', width:160, sort: true},
            {field:'processInstanceId', title:'抽取条件'},
            {field:'processKey', title:'抽取总数',width:100, sort: true},
            {field:'processInstanceName', title:'质检人'},
            {field:'startUserId', title:'坐落'},
            {fixed:'right', title:'操作', toolbar: '#barDemo', width:180}
        ]];
        var tableIDA="#pageTableA";
        var laypageIDA="#laypageA";
        //接口ajax链接方式post或get
        var ajaxmethodA='post';
        //工具条
        var toolbarA="";
        var defaultToolbarA="";
        Window.pageStart(urlA,tablecol,tableIDA,ajaxmethodA,toolbarA,defaultToolbarA,laypageIDA);


    });
});