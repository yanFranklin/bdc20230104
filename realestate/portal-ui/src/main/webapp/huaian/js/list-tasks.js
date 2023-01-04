/**
 * Created by Ypp on 2019/8/19.
 * 标准版中新建任务js
 */
layui.config({
    base: '../static' //此处路径请自行处理, 可以使用绝对路径
}).extend({
    formSelects: '/lib/form-select/formSelects-v4'
});

//新建任务内容高度
var carouselHeight = '100px';
//新建任务默认展示个数
var carouselCount = 12;
//新建任务一行展示个数
var carouselline = 6;
//查询宽度
var taskToolsWidth = "310px";
var taskToolsClickWidth = "95%";

//认领询问 特殊功能
var isConfirmRl = false;
//转发需要根据lcPageEdition判断显示哪个send.html
var lcPageEdition = 'common';
// 判断高级查询是否居中
var dbSearch = '';
var ybSearch = '';
var xmSearch = '';
var rlSearch = '';

// 认领列表 li 标签
var rlContentLi = '<li id="rlTab">认领列表</li>';
var formSelects;
layui.use(['jquery','table', 'formSelects'], function(){
    var $ = layui.jquery,
        table = layui.table;
    formSelects = layui.formSelects;

    $(function(){


    });

});
//表格高度自适应
function changeTableHeight() {
    if ($('.bdc-list-tab .layui-tab-content .layui-show .layui-table-main>.layui-table').height() == 0) {
        $('.bdc-list-tab .layui-tab-content .layui-show .layui-table-body .layui-none').html('<img src="../static/lib/bdcui/images/table-none.png" alt="">无数据');
        //$('.bdc-list-tab .layui-tab-content .layui-show .layui-table-body').height('56px');
        //$('.bdc-list-tab .layui-tab-content .layui-show .layui-table-fixed .layui-table-body').height('56px');
    } else {
        //$('.bdc-list-tab .layui-tab-content .layui-show .layui-table-main.layui-table-body').height($('.bdc-content-box').height() - 196 - $('.bdc-task-tab').innerHeight() - $('.bdc-list-tab .layui-show .bdc-search-box').height());
        $('.bdc-list-tab .layui-tab-content .layui-show .layui-table-body').height($('.bdc-content-box').height() - 196 - $('.bdc-task-tab').innerHeight() - $('.bdc-list-tab .layui-show .bdc-search-box').height());
        $('.bdc-list-tab .layui-tab-content .layui-show .layui-table-fixed .layui-table-body').height($('.bdc-content-box').height() - 196 - $('.bdc-task-tab').innerHeight() - $('.bdc-list-tab .layui-show .bdc-search-box').height() - 17);
    }
}
//渲染待办表格
function renderWaitTable(url, tableId, currentId, toolbar) {
    var tableTitle = [
        {type: 'checkbox', width: 50, fixed: 'left'},
        {field: '', title: '流程状态', templet: '#stateTpl', minWidth: 90},
        {field: 'slbh', minWidth: 150, title: '受理编号', templet: '#slbhTpl', event: 'openpage'},
        {field: 'qlr', minWidth: 100, title: '权利人'},
        {field: 'ywr', minWidth: 100, title: '义务人'},
        {field: 'zl', minWidth: 200, title: '坐落'},
        {field: 'procStartUserName', minWidth: 100, title: '受理人'},
        {field: 'processDefName', templet: '#lcmcTpl', title: '流程名称', minWidth: 160},
        {field: 'djyy', minWidth: 200, title: '登记原因'},
        {field: 'taskName', title: '节点名称', width: 90},
        {field: 'startTime', title: '开始时间', width: 100, sort: true},
        {field: 'bdcdyh', minWidth: 270, templet: '#bdcdyhTpl', title: '不动产单元号'},
        {field: 'jfzt', minWidth: 90, templet: '#jfztTpl', title: '缴费状态'},
        {title: '项目名称', templet: '#rwNameTpl', minWidth: 200, hide: true},
        {field: 'category', title: '业务类型', width: 90, hide: true},
        {field: 'claimStatusName', title: '认领状态', width: 90, hide: true},
        {fixed: 'right', title: '流程图', templet: '#lcTpl', minWidth: 75}
    ];

    if(ywlbXmly) {
        tableTitle = [
            {type: 'checkbox', width: 50, fixed: 'left'},
            {field: 'sply', title: '审批来源', minWidth: 150, templet: function(d){
                        return getXmlymc(d);
                }
            },
            {field: '', title: '流程状态', templet: '#stateTpl', minWidth: 90},
            {field: 'slbh', minWidth: 150, title: '受理编号', templet: '#slbhTpl', event: 'openpage'},
            {field: 'qlr', minWidth: 100, title: '权利人'},
            {field: 'ywr', minWidth: 100, title: '义务人'},
            {field: 'zl', minWidth: 200, title: '坐落'},
            {field: 'procStartUserName', minWidth: 100, title: '受理人'},
            {field: 'processDefName', templet: '#lcmcTpl', title: '流程名称', minWidth: 160},
            {field: 'djyy', minWidth: 200, title: '登记原因'},
            {field: 'taskName', title: '节点名称', width: 90},
            {field: 'startTime', title: '开始时间', width: 100, sort: true},
            {field: 'bdcdyh', minWidth: 270, templet: '#bdcdyhTpl', title: '不动产单元号'},
            {field: 'jfzt', minWidth: 90, templet: '#jfztTpl', title: '缴费状态'},
            {title: '项目名称', templet: '#rwNameTpl', minWidth: 200, hide: true},
            {field: 'category', title: '业务类型', width: 90, hide: true},
            {field: 'claimStatusName', title: '认领状态', width: 90, hide: true},
            {fixed: 'right', title: '流程图', templet: '#lcTpl', minWidth: 75}
        ];
    }
    layui.table.render({
        elem: tableId,
        id: currentId,
        url: url,
        toolbar: toolbar,
        title: '待办任务表格',
        method: 'post',
        even: true,
        request: {
            limitName: 'size'//每页数据量的参数名，默认：limit
        },
        limits: [10, 30, 50, 70, 90, 110, 130, 150],
        defaultToolbar: ['filter'],
        cols: [tableTitle],
        parseData: function (res) { //res 即为原始返回的数据
            var waitUrl = getContextPath() + "/rest/v1.0/task/todo";
            $.ajax({
                type: "POST",
                url: waitUrl,
                data: {},
                success: function (data) {
                    if (data && data.hasOwnProperty("totalElements")) {
                        if (data.totalElements > 99) {
                            $('.bdc-list-num-tips').html('99+');
                        } else {
                            $('.bdc-list-num-tips').html(data.totalElements);
                        }
                    }
                }
            });
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
            // 控制非管理员用户的权限
            if (todoPermission === true) {
                $('.bdc-search-rl').css("display", "none");
                $('.bdc-search-db').css("display", "none");
            }
            changeTableHeight();
            var reverseList = ['zl'];
            reverseTableCell(reverseList);
        }
    });
};

//渲染已办表格
function renderDoneTable(tableId, currentId, toolbar) {
    var tableTitle = [
        {type: 'checkbox', width: 50, fixed: 'left'},
        {field: '', title: '流程状态', templet: '#stateTpl', minWidth: 90},
        {field: 'slbh', minWidth: 150, title: '受理编号', templet: '#slbhTpl', event: 'openpage'},
        {field: 'qlr', minWidth: 100, title: '权利人'},
        {field: 'ywr', minWidth: 100, title: '义务人'},
        {field: 'zl', minWidth: 200, title: '坐落'},
        {field: 'processDefName', title: '流程名称', minWidth: 160},
        {field: 'djyy', minWidth: 200, title: '登记原因'},
        {field: 'taskName', title: '节点名称', width: 90},
        {field: 'startTime', title: '开始时间', width: 100, sort: true},
        {field: 'endTime', title: '结束时间', width: 100, sort: true},
        {field: 'bdcdyh', minWidth: 270, templet: '#bdcdyhTpl', title: '不动产单元号'},
        {title: '项目名称', templet: '#rwNameTpl', minWidth: 200, hide: true},
        {field: 'category', title: '业务类型', width: 90, hide: true},
        {field: 'taskAssName', title: '处理人', width: 90, hide: true},
        {fixed: 'right', title: '流程图', templet: '#lcTpl', minWidth: 75}
    ];
    if (ywlbXmly){
        tableTitle = [
            {type: 'checkbox', width: 50, fixed: 'left'},
            {field: 'sply', title: '审批来源', minWidth: 150, templet: function(d){
                    return getXmlymc(d);
                }
                },
            {field: '', title: '流程状态', templet: '#stateTpl', minWidth: 90},
            {field: 'slbh', minWidth: 150, title: '受理编号', templet: '#slbhTpl', event: 'openpage'},
            {field: 'qlr', minWidth: 100, title: '权利人'},
            {field: 'ywr', minWidth: 100, title: '义务人'},
            {field: 'zl', minWidth: 200, title: '坐落'},
            {field: 'processDefName', title: '流程名称', minWidth: 160},
            {field: 'djyy', minWidth: 200, title: '登记原因'},
            {field: 'taskName', title: '节点名称', width: 90},
            {field: 'startTime', title: '开始时间', width: 100, sort: true},
            {field: 'endTime', title: '结束时间', width: 100, sort: true},
            {field: 'bdcdyh', minWidth: 270, templet: '#bdcdyhTpl', title: '不动产单元号'},
            {title: '项目名称', templet: '#rwNameTpl', minWidth: 200, hide: true},
            {field: 'category', title: '业务类型', width: 90, hide: true},
            {field: 'taskAssName', title: '处理人', width: 90, hide: true},
            {fixed: 'right', title: '流程图', templet: '#lcTpl', minWidth: 75}
        ];
    }
    layui.table.render({
        elem: tableId,
        id: currentId,
        data: [],
        toolbar: toolbar,
        title: '已办任务表格',
        method: 'post',
        even: true,
        request: {
            limitName: 'size' //每页数据量的参数名，默认：limit

        },
        limits: [10,30,50,70,90,110,130,150],
        defaultToolbar: ['filter'],
        cols: [tableTitle],
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

            changeTableHeight();
            var reverseList = ['zl'];
            reverseTableCell(reverseList);
        }
    });
};

//渲染项目列表
function renderListTable(tableId, currentId, toolbar) {
    var tableTitle = [
        {type: 'checkbox', width: 50, fixed: 'left'},
        {field: 'procStatus', title: '流程状态', width: 90, templet: '#flowStateTpl'},
        {field: 'slbh', minWidth: 150, title: '受理编号', templet: '#slbhTpl', event: 'openpage'},
        {field: 'qlr', minWidth: 100, title: '权利人'},
        {field: 'ywr', minWidth: 100, title: '义务人'},
        {field: 'zl', minWidth: 200, title: '坐落'},
        {field: 'procDefName', title: '流程名称', minWidth: 160},
        {field: 'djyy', minWidth: 200, title: '登记原因'},
        {field: 'startUserName', title: '受理人', minWidth: 100},
        {field: 'startTime', title: '受理时间', width: 100, sort: true},
        {field: 'bdcdyh', minWidth: 270, templet: '#bdcdyhTpl', title: '不动产单元号'},
        {title: '项目名称', templet: '#rwNameTpl', minWidth: 200, hide: true},
        {field: 'categoryName', title: '业务类型', width: 90, hide: true},
        {fixed: 'right', title: '流程图', templet: '#lcTpl', width: 75},
        {fixed: 'right', field: 'procTimeoutStatus', width: 90, title: '超期状态', templet: '#overStateTpl'}
    ];
    if (ywlbXmly) {
        tableTitle = [
            {type: 'checkbox', width: 50, fixed: 'left'},
            {field: 'sply', title: '审批来源', minWidth: 150, templet: function(d){
                    return getXmlymc(d);
                }},
            {field: 'procStatus', title: '流程状态', width: 90, templet: '#flowStateTpl'},
            {field: 'slbh', minWidth: 150, title: '受理编号', templet: '#slbhTpl', event: 'openpage'},
            {field: 'qlr', minWidth: 100, title: '权利人'},
            {field: 'ywr', minWidth: 100, title: '义务人'},
            {field: 'zl', minWidth: 200, title: '坐落'},
            {field: 'procDefName', title: '流程名称', minWidth: 160},
            {field: 'djyy', minWidth: 200, title: '登记原因'},
            {field: 'startUserName', title: '受理人', minWidth: 100},
            {field: 'startTime', title: '受理时间', width: 100, sort: true},
            {field: 'bdcdyh', minWidth: 270, templet: '#bdcdyhTpl', title: '不动产单元号'},
            {title: '项目名称', templet: '#rwNameTpl', minWidth: 200, hide: true},
            {field: 'categoryName', title: '业务类型', width: 90, hide: true},
            {fixed: 'right', title: '流程图', templet: '#lcTpl', width: 75},
            {fixed: 'right', field: 'procTimeoutStatus', width: 90, title: '超期状态', templet: '#overStateTpl'}
        ];
    }
    layui.table.render({
        elem: tableId,
        id: currentId,
        data: [],
        toolbar: toolbar,
        title: '用户数据表',
        method: 'post',
        even: true,
        request: {
            limitName: 'size'//每页数据量的参数名，默认：limit

        },
        limits: [10,30,50,70,90,110,130,150],
        defaultToolbar: ['filter'],
        cols: [tableTitle],
        parseData: function (res) { //res 即为原始返回的数据
            // console.log(res);
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

            changeTableHeight();
            var reverseList = ['zl'];
            reverseTableCell(reverseList);
        }
    });
};

// 渲染认领列表
function renderRlTable(tableId, currentId, toolbar) {
    var tableTitle = [
        {type: 'checkbox', width: 50, fixed: 'left'},
        {field: 'slbh', minWidth: 150, title: '受理编号', templet: '#rlslbhTpl', event: 'openpage'},
        {field: 'qlr', minWidth: 100, title: '权利人'},
        {field: 'ywr', minWidth: 100, title: '义务人'},
        {field: 'zl', minWidth: 200, title: '坐落'},
        {title: '流程名称', field: 'processDefName', minWidth: 160},
        {field: 'djyy', minWidth: 200, title: '登记原因'},
        {field: 'procStartUserName', title: '受理人', minWidth: 100},
        {field: 'taskName', title: '节点名称', width: 90},
        {field: 'startTime', title: '开始时间', width: 100, sort: true},
        {field: 'endTime', title: '结束时间', width: 100, sort: true},
        {field: 'bdcdyh', minWidth: 270, templet: '#bdcdyhTpl', title: '不动产单元号'},
        {title: '项目名称', templet: '#rwNameTpl', minWidth: 200, hide: true},
        {field: 'category', title: '业务类型', width: 90, hide: true},
        {field: 'taskAssName', title: '处理人', width: 90, hide: true},
        {fixed: 'right', title: '流程图', templet: '#lcTpl', minWidth: 75}
    ];
    if (ywlbXmly) {
        tableTitle = [
            {type: 'checkbox', width: 50, fixed: 'left'},
            {field: 'sply', title: '审批来源', minWidth: 150, templet: function(d){
                    return getXmlymc(d);
                }},
            {field: 'slbh', minWidth: 150, title: '受理编号', templet: '#rlslbhTpl', event: 'openpage'},
            {field: 'qlr', minWidth: 100, title: '权利人'},
            {field: 'ywr', minWidth: 100, title: '义务人'},
            {field: 'zl', minWidth: 200, title: '坐落'},
            {title: '流程名称', field: 'processDefName', minWidth: 160},
            {field: 'djyy', minWidth: 200, title: '登记原因'},
            {field: 'procStartUserName', title: '受理人', minWidth: 100},
            {field: 'taskName', title: '节点名称', width: 90},
            {field: 'startTime', title: '开始时间', width: 100, sort: true},
            {field: 'endTime', title: '结束时间', width: 100, sort: true},
            {field: 'bdcdyh', minWidth: 270, templet: '#bdcdyhTpl', title: '不动产单元号'},
            {title: '项目名称', templet: '#rwNameTpl', minWidth: 200, hide: true},
            {field: 'category', title: '业务类型', width: 90, hide: true},
            {field: 'taskAssName', title: '处理人', width: 90, hide: true},
            {fixed: 'right', title: '流程图', templet: '#lcTpl', minWidth: 75}
        ];
    }
    layui.table.render({
        elem: tableId,
        id: currentId,
        data: [],
        toolbar: toolbar,
        title: '认领任务表',
        method: 'post',
        even: true,
        request: {
            limitName: 'size'//每页数据量的参数名，默认：limit

        },
        limits: [10,30,50,70,90,110,130,150],
        defaultToolbar: ['filter'],
        cols: [tableTitle],
        parseData: function (res) { //res 即为原始返回的数据
            //获取互联网+的处理
            var rlUrl = getContextPath() + "/rest/v1.0/task/claim/list";
            if($('.bdc-rl-num-word').length>0 && $('.bdc-rl-num-word').html().indexOf("互联网")!=-1){

                $.ajax({
                    type: "POST",
                    url: rlUrl,
                    data: {sply: "3"},
                    success: function (data) {
                        if(data && data.hasOwnProperty("totalElements")){
                            $('.bdc-rl-num-js').html(data.totalElements)
                        }
                    }
                });
            }else{
                $.ajax({
                    type: "POST",
                    url: rlUrl,
                    data: {},
                    success: function (data) {
                        if (data && data.hasOwnProperty("totalElements")) {
                            if (data.totalElements > 99) {
                                $('.bdc-rl-num-js').html('99+');
                            } else {
                                $('.bdc-rl-num-js').html(data.totalElements);
                            }
                        }
                    }
                });
            }
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
            // 控制非管理员用户的权限
            if (todoPermission === true) {
                $('.bdc-search-rl').css("display", "none");
                $('.bdc-search-db').css("display", "none");
            }
            changeTableHeight();
            var reverseList = ['zl'];
            reverseTableCell(reverseList);
        }
    });
};

//渲染个人项目列表
function renderGrListTable(tableId, currentId, toolbar) {
    var tableTitle = [
        {type: 'checkbox', width: 50, fixed: 'left'},
        {field: 'procStatus', title: '流程状态', width: 90, templet: '#flowStateTpl'},
        {field: 'slbh', minWidth: 150, title: '受理编号', templet: '#slbhTpl', event: 'openpage'},
        {field: 'qlr', minWidth: 100, title: '权利人'},
        {field: 'ywr', minWidth: 100, title: '义务人'},
        {field: 'zl', minWidth: 200, title: '坐落'},
        {field: 'procDefName', title: '流程名称', minWidth: 160},
        {field: 'startUserName', title: '受理人', minWidth: 100},
        {field: 'startTime', title: '受理时间', width: 100, sort: true},
        {field: 'bdcdyh', minWidth: 270, templet: '#bdcdyhTpl', title: '不动产单元号'},
        {title: '项目名称', templet: '#rwNameTpl', minWidth: 200, hide: true},
        {field: 'categoryName', title: '业务类型', width: 90, hide: true},
        {fixed: 'right', title: '流程图', templet: '#lcTpl', width: 75},
        {fixed: 'right', field: 'procTimeoutStatus', width: 90, title: '超期状态', templet: '#overStateTpl'}
    ];

    if (ywlbXmly) {
        tableTitle = [
            {type: 'checkbox', width: 50, fixed: 'left'},
            {field: 'sply', title: '审批来源', minWidth: 150, templet: function(d){
                    return getXmlymc(d);
                }},
            {field: 'procStatus', title: '流程状态', width: 90, templet: '#flowStateTpl'},
            {field: 'slbh', minWidth: 150, title: '受理编号', templet: '#slbhTpl', event: 'openpage'},
            {field: 'qlr', minWidth: 100, title: '权利人'},
            {field: 'ywr', minWidth: 100, title: '义务人'},
            {field: 'zl', minWidth: 200, title: '坐落'},
            {field: 'procDefName', title: '流程名称', minWidth: 160},
            {field: 'startUserName', title: '受理人', minWidth: 100},
            {field: 'startTime', title: '受理时间', width: 100, sort: true},
            {field: 'bdcdyh', minWidth: 270, templet: '#bdcdyhTpl', title: '不动产单元号'},
            {title: '项目名称', templet: '#rwNameTpl', minWidth: 200, hide: true},
            {field: 'categoryName', title: '业务类型', width: 90, hide: true},
            {fixed: 'right', title: '流程图', templet: '#lcTpl', width: 75},
            {fixed: 'right', field: 'procTimeoutStatus', width: 90, title: '超期状态', templet: '#overStateTpl'}
        ];
    }
    layui.table.render({
        elem: tableId,
        id: currentId,
        data: [],
        toolbar: toolbar,
        title: '用户数据表',
        method: 'post',
        even: true,
        request: {
            limitName: 'size' //每页数据量的参数名，默认：limit

        },
        limits: [10, 30, 50, 70, 90, 110, 130, 150],
        defaultToolbar: ['filter'],
        cols: [tableTitle],
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

            changeTableHeight();
            var reverseList = ['zl'];
            reverseTableCell(reverseList);
        }
    });

}

//任务列表项目来源展示
function getXmlymc(data){
    var splymc = "";
    if (rwlbSplyZdx && rwlbSplyZdx.length > 0) {
        $.each(rwlbSplyZdx, function (i, item) {
            if (data.sply == item.value) {
                var color = isNullOrEmpty(item.color) ? "#333" : item.color;
                splymc = '<span style="color:' + color + ';cursor:pointer">' + item.name + '</span>';
                return false;
            }
        });
    }

    // 如果没有手动配置指定来源，则取默认字典项名称
    if (isNullOrEmpty(splymc) && isNotBlank(splyzdx)) {
        $.each(splyzdx, function (i, item) {
            if (data.sply == item.DM) {
                splymc = item.MC;
                return false;
            }
        });
    }
    return splymc;
}