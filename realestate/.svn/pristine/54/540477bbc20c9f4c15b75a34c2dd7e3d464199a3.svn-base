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
var lcPageEdition = 'changzhou';
// 判断高级查询是否居中
var dbSearch = '';
var ybSearch = '';
var xmSearch = '';
var rlSearch = '';
var dajjybSearch = "";
var dajjdbSearch = "";
var dajjrlSearch = "";
//是否管理员
var isAdmin = false;

// 认领列表 li 标签
var rlContentLi = '<li id="rlTab">认领列表</li>';

// 档案交接流程工作流实例ID
var dajjProcessKey;
var dajjConfig;
var dylxArr = ["bdcSqSpb", "daml", "spbdaml","dafm"];
var sessionKey = "bdcShxx";
var formSelects, form;
layui.use(['jquery', 'table', 'formSelects', 'form'], function () {
    var $ = layui.jquery,
        table = layui.table;
        form = layui.form;
        formSelects = layui.formSelects;

    $(function () {
        setDypzSession(dylxArr, sessionKey);
        // 获取档案交接流程定义ID
        getDajjProcessKey();

        $.ajax({
            type: "GET",
            url: getContextPath() + "/rest/v1.0/user/admin",
            data: {},
            async: false,
            success: function (data) {
                isAdmin = data;
            }, error: function (e) {
                response.fail(e, '');
            }
        });



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
        cols: [[
            {type: 'checkbox', width: 50, fixed: 'left'},
            {field: '', title: '流程状态', templet: '#stateTpl', minWidth: 90},
            {field: 'slbh', minWidth: 110, title: '受理编号', templet: '#slbhTpl', event: 'openpage'},
            {field: 'taskId', hide: true, title: '任务id'},
            {field: 'processInstanceId', hide: true, title: '流程实例id'},
            {field: 'qlr', minWidth: 100, title: '权利人'},
            {field: 'ywr', minWidth: 100, title: '义务人'},
            {field: 'jfzt', minWidth: 90, templet: '#jfztTpl', title: '收费状态'},
            {field: 'zl', minWidth: 200, title: '坐落'},
            {field: 'procStartUserName', minWidth: 100, title: '受理人'},
            {field: 'processDefName', templet: '#lcmcTpl', title: '流程名称', minWidth: 160},
            {field: 'djyy', minWidth: 200, title: '登记原因'},
            {field: 'taskName', title: '节点名称', width: 90},
            {field: 'startTime', title: '开始时间', width: 100, sort: true},
            {field: 'bdcdyh', minWidth: 270, templet: '#bdcdyhTpl', title: '不动产单元号'},
            {title: '项目名称', templet: '#rwNameTpl', minWidth: 200, hide: true},
            {field: 'category', title: '业务类型', width: 90, hide: true},
            {field: 'claimStatusName', title: '认领状态', width: 90, hide: true},
            {fixed: 'right', title: '流程图', templet: '#lcTpl', minWidth: 75}
        ]],
        parseData: function (res) { //res 即为原始返回的数据
            dbTableCache = res.content;
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
            if(isAdmin){
                //管理员待办可以增加受理编号查询条件
                $(".bdc-search-hide").show();
            }
            changeTableHeight();
            var reverseList = ['zl'];
            reverseTableCell(reverseList);
        }
    });
};

//渲染已办表格
function renderDoneTable(tableId, currentId, toolbar) {
    layui.table.render({
        elem: tableId,
        id: currentId,
        data: [],
        toolbar: toolbar,
        title: '已办任务表格',
        method: 'post',
        even: true,
        request: {
            limitName: 'size'//每页数据量的参数名，默认：limit

        },
        limits: [10, 30, 50, 70, 90, 110, 130, 150],
        defaultToolbar: ['filter'],
        cols: [[
            {type: 'checkbox', width: 50, fixed: 'left'},
            {field: '', title: '流程状态', templet: '#stateTpl', minWidth: 90},
            {field: 'slbh', minWidth: 110, title: '受理编号', templet: '#slbhTpl', event: 'openpage'},
            {field: 'taskId', hide: true, title: '任务id'},
            {field: 'processInstanceId', hide: true, title: '流程实例id'},
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
        ]],
        parseData: function (res) { //res 即为原始返回的数据
            ybTableCache = res.content;
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
        cols: [[
            {type: 'checkbox', width: 50, fixed: 'left'},
            {field: 'procStatus', title: '流程状态', width: 90, templet: '#flowStateTpl'},
            {field: 'slbh', minWidth: 110, title: '受理编号', templet: '#slbhTpl', event: 'openpage'},
            {field: 'taskId', hide: true, title: '任务id'},
            {field: 'processInstanceId', hide: true, title: '流程实例id'},
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
        ]],
        parseData: function (res) { //res 即为原始返回的数据
            // console.log(res);
            xmTableCache = res.content;
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
    layui.table.render({
        elem: tableId,
        id: currentId,
        data: [],
        toolbar: toolbar,
        title: '认领任务表',
        method: 'post',
        even: true,
        request: {
            limitName: 'size' //每页数据量的参数名，默认：limit

        },
        limits: [10, 30, 50, 70, 90, 110, 130, 150],
        defaultToolbar: ['filter'],
        cols: [[
            {type: 'checkbox', width: 50, fixed: 'left'},
            {field: 'slbh', minWidth: 110, title: '受理编号', templet: '#rlslbhTpl', event: 'openpage'},
            {field: 'taskId', hide: true, title: '任务id'},
            {field: 'processInstanceId', hide: true, title: '流程实例id'},
            {field: 'qlr', minWidth: 100, title: '权利人'},
            {field: 'ywr', minWidth: 100, title: '义务人'},
            {field: 'jfzt', minWidth: 90, templet: '#jfztTpl', title: '收费状态'},
            {field: 'zl', minWidth: 200, title: '坐落'},
            {title: '流程名称', field: 'processDefName', minWidth: 160},
            {field: 'djyy', minWidth: 200, title: '登记原因'},
            {field: 'procStartUserName', title: '受理人', minWidth: 100},
            {field: 'taskName', title: '节点名称', width: 90},
            {field: 'startTime', title: '开始时间', width: 100, sort: true},
            {field: 'endTime', title: '结束时间', width: 100, sort: true},
            {field: 'bdcdyh', minWidth: 270, templet: '#bdcdyhTpl', title: '不动产单元号'},
            {field: 'lzfs', templet: '#lzfsTpl',title: '领证方式',},
            {title: '项目名称', templet: '#rwNameTpl', minWidth: 200, hide: true},
            {field: 'category', title: '业务类型', width: 90, hide: true},
            {field: 'taskAssName', title: '处理人', width: 90, hide: true},
            {fixed: 'right', title: '流程图', templet: '#lcTpl', minWidth: 75}
        ]],
        parseData: function (res) { //res 即为原始返回的数据
            rlTableCache = res.content;
            //获取互联网+的处理
            var rlUrl = getContextPath() + "/rest/v1.0/task/claim/list";
            if ($('.bdc-rl-num-word').length > 0 && $('.bdc-rl-num-word').html().indexOf("互联网") != -1) {
                $.ajax({
                    type: "POST",
                    url: rlUrl,
                    data: {sply: "3"},
                    success: function (data) {
                        if (data && data.hasOwnProperty("totalElements")) {
                            $('.bdc-rl-num-js').html(data.totalElements)
                        }
                    }
                });
            } else {
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
            changeTableHeight();
            var reverseList = ['zl'];
            reverseTableCell(reverseList);
        }
    });
};

//渲染个人项目列表
function renderGrListTable(tableId, currentId, toolbar) {
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
        limits: [10, 30, 50, 70, 90, 110, 130, 150],
        defaultToolbar: ['filter'],
        cols: [[
            {type: 'checkbox', width: 50, fixed: 'left'},
            {field: 'procStatus', title: '流程状态', width: 90, templet: '#flowStateTpl'},
            {field: 'slbh', minWidth: 110, title: '受理编号', templet: '#slbhTpl', event: 'openpage'},
            {field: 'taskId', hide: true, title: '任务id'},
            {field: 'processInstanceId', hide: true, title: '流程实例id'},
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
        ]],
        parseData: function (res) { //res 即为原始返回的数据
            grTableCache = res.content;
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

//渲染档案交接认领列表
function renderDajjRlTable(url, tableId, currentId, toolbar) {
    layui.table.render({
        elem: tableId,
        id: currentId,
        url: url,
        toolbar: toolbar,
        title: '档案交接认领任务表格',
        method: 'post',
        even: true,
        where: {
            processDefName : getDajjProcessKey("processDefName"),
            lx: "dajjrlContent",
            ylcmc_not: getDajjProcessKey("ylcmc_not"),
            order_type: "dajjrl_order",
        },
        request: {
            limitName: 'size' //每页数据量的参数名，默认：limit

        },
        limits: [10, 30, 50, 70, 90, 110, 130, 150],
        defaultToolbar: ['filter'],
        cols: [[
            {type: 'checkbox', width: 50, fixed: 'left'},
            {field: 'yslbh', minWidth: 110, title: '受理编号', templet: '#yslbhTpl', event: 'openpage'},
            {field: 'taskId', hide: true, title: '任务id'},
            {field: 'processInstanceId', hide: true, title: '流程实例id'},
            {field: 'ylcmc', minWidth: 160, title: '流程名称'},
            {field: 'qlr', minWidth: 100, title: '权利人'},
            {field: 'ywr', minWidth: 100, title: '义务人'},
            {field: 'startTime', title: '受理时间', width: 150, sort: true},
            {field: 'zl', minWidth: 200, title: '坐落'},
            {field: 'procStartUserName', title: '受理人', minWidth: 100},
            {field: 'taskAssName', title: '交接人', minWidth: 100},
            {field: 'taskName', minWidth: 100, title: '状态'},
            {field: '', title: '流程状态', templet: '#stateTpl', minWidth: 90},
            {field: 'slbh', minWidth: 110, title: '受理编号', templet: '#slbhTpl', hide: true},
            {fixed: 'right', title: '流程图', templet: '#lcTpl', minWidth: 75},
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
            changeTableHeight();
            var reverseList = ['zl'];
            reverseTableCell(reverseList);
        }
    });
};

//渲染档案交接待办列表
function renderDajjDbTable(url, tableId, currentId, toolbar) {
    layui.table.render({
        elem: tableId,
        id: currentId,
        url: url,
        toolbar: toolbar,
        title: '档案交接待办任务表格',
        method: 'post',
        even: true,
        where: {
            processDefName : getDajjProcessKey("processDefName"),
            lx: "dajjdbContent",
            prefix: "order_dajj_todo",
        },
        request: {
            limitName: 'size' //每页数据量的参数名，默认：limit

        },
        limits: [10, 30, 50, 70, 90, 110, 130, 150],
        defaultToolbar: ['filter'],
        cols: [[
            {type: 'checkbox', width: 50, fixed: 'left'},
            {field: 'yslbh', minWidth: 110, title: '受理编号', templet: '#yslbhTpl', event: 'openpage'},
            {field: 'taskId', hide: true, title: '任务id'},
            {field: 'processInstanceId', hide: true, title: '流程实例id'},
            {field: 'ylcmc', minWidth: 160, title: '流程名称'},
            {field: 'qlr', minWidth: 100, title: '权利人'},
            {field: 'ywr', minWidth: 100, title: '义务人'},
            {field: 'startTime', title: '受理时间', width: 150, sort: true},
            {field: 'zl', minWidth: 200, title: '坐落'},
            {field: 'dathsj', minWidth: 150, title: '退回时间'},
            {field: 'thyy', minWidth: 150, title: '退回原因'},
            {field: 'procStartUserName', title: '受理人', minWidth: 100},
            {field: 'taskAssName', title: '交接人', minWidth: 100},
            {field: 'taskName', minWidth: 100, title: '状态'},
            {field: '', title: '流程状态', templet: '#stateTpl', minWidth: 90},
            {field: 'slbh', minWidth: 110, title: '受理编号', templet: '#slbhTpl', hide: true},
            {fixed: 'right', title: '流程图', templet: '#lcTpl', minWidth: 75},
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
            changeTableHeight();
            var reverseList = ['zl'];
            reverseTableCell(reverseList);
        }
    });
};

//渲染档案交接已办列表
function renderDajjYbTable(url, tableId, currentId, toolbar) {
    layui.table.render({
        elem: tableId,
        id: currentId,
        url: url,
        toolbar: toolbar,
        title: '档案交接已办任务表格',
        method: 'post',
        even: true,
        where: {
            processDefName : getDajjProcessKey("processDefName"),
            lx : "dajjybContent",
            order_type : "dajjyb_order",
        },
        request: {
            limitName: 'size' //每页数据量的参数名，默认：limit

        },
        limits: [10, 30, 50, 70, 90, 110, 130, 150],
        defaultToolbar: ['filter'],
        cols: [[
            {type: 'checkbox', width: 50, fixed: 'left'},
            {field: 'yslbh', minWidth: 110, title: '受理编号', templet: '#yslbhTpl', event: 'openpage'},
            {field: 'taskId', hide: true, title: '任务id'},
            {field: 'processInstanceId', hide: true, title: '流程实例id'},
            {field: 'ylcmc', minWidth: 160, title: '流程名称'},
            {field: 'qlr', minWidth: 100, title: '权利人'},
            {field: 'ywr', minWidth: 100, title: '义务人'},
            {field: 'startTime', title: '受理时间', width: 150, sort: true},
            {field: 'zl', minWidth: 200, title: '坐落'},
            {field: 'dathsj', minWidth: 150, title: '退回时间'},
            {field: 'thyy', minWidth: 150, title: '退回原因'},
            {field: 'procStartUserName', title: '受理人', minWidth: 100},
            {field: 'taskAssName', title: '交接人', minWidth: 100},
            {field: 'taskName', minWidth: 100, title: '状态'},
            {field: '', title: '流程状态', templet: '#stateTpl', minWidth: 90},
            {field: 'slbh', minWidth: 110, title: '受理编号', templet: '#slbhTpl', hide: true},
            {fixed: 'right', title: '流程图', templet: '#lcTpl', minWidth: 75},
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
            changeTableHeight();
            var reverseList = ['zl'];
            reverseTableCell(reverseList);
        }
    });
};

// 获取原流程工作流实例ID
function queryYlcGzlslid(gzlslidList){
    var deferred = $.Deferred();
    addModel();
    $.ajax({
        url: getContextPath() + "/rest/v1.0/dajj/getYlcGzlslid",
        type: "POST",
        dataType: 'json',
        data: JSON.stringify(gzlslidList),
        contentType: "application/json;charset=UTF-8",
        success: function (data) {
            removeModal();
            if(isNotBlank(data) && data.length > 0){
                deferred.resolve(data);
            }else{
                deferred.reject();
            }
        },
        error: function (xhr, status, error) {
            removeModal();
            delAjaxErrorMsg(xhr);
        }
    });
    return deferred;
}

/**
 * 档案交接打印（常州市本级）
 * @param checkeddata
 */
function printDajj(checkeddata) {
    if (checkeddata.length === 0) {
        warnMsg('请勾选需要打印的数据！');
        return;
    }

    var gzlslids = [], allSameProcess = true, ylcmc = checkeddata[0].ylcmc;
    var slbhs = [];
    var taskids = [];
    $.each(checkeddata, function (index, data) {
        if (ylcmc != data.ylcmc) {
            allSameProcess = false;
        }
        slbhs.push(data.slbh);
        taskids.push(data.taskId);
        gzlslids.push(data.processInstanceId);
    });
    console.info(gzlslids);
    if (checkeddata.length == 1 || allSameProcess) {
        queryYlcGzlslid(gzlslids).done(function(data){
            recordLog(JSON.stringify(slbhs), JSON.stringify(taskids), data[0], "市本级档案交接打印");
            showPrintMessage(data, true)
        });
    }else{
        warnMsg("只有同一流程，才能进行批量打印。");
    }
}

/**
 * 档案交接武进打印
 * @param checkeddata
 */
function printDajjWJ(checkeddata) {
    if (checkeddata.length === 0) {
        warnMsg('请勾选需要打印的数据！');
        return;
    }

    var gzlslids = [], allSameProcess = true, ylcmc = checkeddata[0].ylcmc;
    var slbhs = [];
    var taskids = [];
    $.each(checkeddata, function (index, data) {
        if (ylcmc != data.ylcmc) {
            allSameProcess = false;
        }
        gzlslids.push(data.processInstanceId);
        slbhs.push(data.slbh);
        taskids.push(data.taskId);
    });
    console.info(gzlslids);
    if (checkeddata.length == 1 || allSameProcess) {
        queryYlcGzlslid(gzlslids).done(function(data){
            recordLog(JSON.stringify(slbhs), JSON.stringify(taskids), data[0], "武进档案交接打印");
            showPrintMessageWJ(data, true)
        });
    }else{
        warnMsg("只有同一流程，才能进行批量打印。");
    }
}

/**
 * 档案交接金坛打印
 * @param checkeddata
 */
function printDajjJT(checkeddata) {
    if (checkeddata.length === 0) {
        warnMsg('请勾选需要打印的数据！');
        return;
    }

    var gzlslids = [], allSameProcess = true, ylcmc = checkeddata[0].ylcmc;
    var slbhs = [];
    var taskids = [];
    $.each(checkeddata, function (index, data) {
        if (ylcmc != data.ylcmc) {
            allSameProcess = false;
        }
        gzlslids.push(data.processInstanceId);
        slbhs.push(data.slbh);
        taskids.push(data.taskId);
    });
    console.info(gzlslids);
    if (checkeddata.length == 1 || allSameProcess) {
        queryYlcGzlslid(gzlslids).done(function(data){
            recordLog(JSON.stringify(slbhs), JSON.stringify(taskids), data[0], "金坛档案交接打印");
            showPrintMessageJT(data, true)
        });
    }else{
        warnMsg("只有同一流程，才能进行批量打印。");
    }
}

/**
 * 档案交接溧阳打印
 * @param checkeddata
 */
function printDajjLY(checkeddata) {
    if (checkeddata.length === 0) {
        warnMsg('请勾选需要打印的数据！');
        return;
    }

    var gzlslids = [], allSameProcess = true, ylcmc = checkeddata[0].ylcmc;
    var slbhs = [];
    var taskids = [];
    $.each(checkeddata, function (index, data) {
        if (ylcmc != data.ylcmc) {
            allSameProcess = false;
        }
        gzlslids.push(data.processInstanceId);
        slbhs.push(data.slbh);
        taskids.push(data.taskId);
    });
    console.info(gzlslids);
    if (checkeddata.length == 1 || allSameProcess) {
        queryYlcGzlslid(gzlslids).done(function(data){
            recordLog(JSON.stringify(slbhs), JSON.stringify(taskids), data[0], "溧阳档案交接打印");
            showPrintMessageLY(data, true)
        });
    }else{
        warnMsg("只有同一流程，才能进行批量打印。");
    }
}
// 导出档案交接信息
function exportDajj(checkedData){
    if (checkedData.length === 0) {
        warnMsg('请勾选需要打印的数据！');
        return;
    }
    addModel();
    var data = checkedData;
    // 标题
    var showColsTitle = ["序号","受理编号","申请人","义务人","房屋坐落","登记类别","登记时间","备注"];
    // 列内容
    var showColsField = ["xh","yslbh","qlr","ywr","zl","ylcmc","startTime","bz"];
    // 列宽
    var showColsWidth = [7,20,15,15,40,30,35,15];

    $("#sheetName").val("业务传递手续交接明细表");
    $("#fileName").val('业务传递手续交接明细表');


    // 设置Excel基本信息 2020年4月单位非住宅抵押登记
    $("#cellTitle").val(showColsTitle);
    $("#cellWidth").val(showColsWidth);
    $("#cellKey").val(showColsField);

    // 导出增加序号字段
    var exportData = [];
    for (var i = 0,j = 1 ; i < data.length; i++,j++) {
        exportData[i] = data[i];
        exportData[i].xh = j;
        exportData[i].bz = "";
    }
    removeModal();
    $("#data").val(JSON.stringify(exportData));
    var hzData =queryTailSummaryContent(exportData[0].processInstanceId);
    $("#tailSummaryContent").val(JSON.stringify(hzData));
    $("#mergeSameCell").val(false);
    $("#addBorder").val(true);
    $("#form").submit();
}

var spbModelPerfix = getIP() + "/realestate-register-ui/static/printModel/";
/**
 * 档案交接市本级打印
 * @param gzlslids
 * @param sfpl
 */
function showPrintMessage(gzlslids, sfpl) {
    addModel();
    var spbdylx, damldylx, qbdylx;
    $.ajax({
        type: "GET",
        url: getContextPath() +"/rest/v1.0/dajj/dypz/dylx",
        data: {gzlslid: gzlslids[0]},
        async: false,
        success: function (data) {
            removeModal();
            if(isNotBlank(data)){
                spbdylx = data.dylx;
                damldylx = data.damldylx;
                // 打印全部的打印类型为
                qbdylx = spbdylx + damldylx;
                dylxArr.push(spbdylx);
                dylxArr.push(damldylx);
                dylxArr.push(qbdylx);
            }
            setDypzSession(dylxArr,sessionKey);
        }, error: function (e) {
            removeModal();
            response.fail(e, '');
         }
    });

    var c = layer.open({
        title: '一次只允许打印一种类型，请选择',
        type: 1,
        area: ['430px','180px'],
        btn: ['确定', '取消'],
        content: '<div id="bdc-popup-radio">\n' +
            '    <form class="layui-form" action="">\n' +
            '        <div class="layui-form-item pf-form-item">\n' +
            '            <div class="layui-inline">\n' +
            '                <label class="layui-form-label">打印类型：</label>\n' +
            '                <div class="layui-input-inline">\n' +
            '                </div>\n' +
            '            </div>\n' +
            '        </div>\n' +
            '    </form>\n' +
            '</div>',
        success: function () {
            var form = layui.form;
            var radio = "";
            radio += "<input type=\"radio\" name=\"dylxRadio\" data-type=\"bdcSqSpb\" value=\""+ spbdylx +"\" title=\"审批表\" lay-filter=\"zslxRadio\" checked>";
            radio += "<input type=\"radio\" name=\"dylxRadio\" data-type=\"daml\" value=\""+ damldylx +"\" title=\"档案目录\" lay-filter=\"zslxRadio\">";
            radio += "<input type=\"radio\" name=\"dylxRadio\" data-type=\"spbdaml\" value=\""+ qbdylx +"\" title=\"全部\" lay-filter=\"zslxRadio\">";
            $('#bdc-popup-radio').find(".layui-input-inline").html(radio);
            layui.form.render("radio");
        },
        yes: function (index, layero) {
            var modelUrl = "";
            var dataUrl = "";
            var dylx = $("input[name='dylxRadio']:checked").val();
            var type = $("input[name='dylxRadio']:checked").data("type");
            if (type === "bdcSqSpb") {
                modelUrl = getIP() + "/realestate-register-ui/static/printModel/" + dylx + ".fr3";
                if (sfpl) {
                    dataUrl = getIP() + "/realestate-register-ui/rest/v1.0/print/spb/" + gzlslids.join(",") + "/" + dylx + "/xml/pl?zxlc=" + false;
                } else {
                    dataUrl = getIP() + "/realestate-register-ui/rest/v1.0/print/spb/" + gzlslids[0] + "/" + dylx + "/xml?zxlc=" + false;
                }
            }
            if (type === "daml") {
                modelUrl = getIP() + "/realestate-register-ui/static/printModel/" + dylx + ".fr3";
                dataUrl = getIP() + "/realestate-register-ui/rest/v1.0/print/dajj/" + dylx + "/" + gzlslids.join(",");
            }
            if (type === "spbdaml") {
                modelUrl = getIP() + "/realestate-register-ui/static/printModel/" + dylx + ".fr3";
                dataUrl = getIP() + "/realestate-register-ui/rest/v1.0/print/dajj/spbdaml/" + dylx + "/" + gzlslids.join(",");
            }
            if (isNullOrEmpty(modelUrl)) {
                warnMsg("请选择打印类型再打印");
                return;
            }
            console.info(dylxArr);
            console.log("打印类型：" + dylx + "工作流实例id：" + gzlslids);
            printChoice(dylx, "realestate-register-ui", dataUrl, modelUrl, false, sessionKey);
        }
        , btn2: function (index, layero) {
            //取消 的回调
            layer.close(c)
        }
        , cancel: function (index, layero) {
            //右上角关闭回调
            layer.close(c)
        }
    });
}

/**
 * 档案交接武进打印
 * @param gzlslids
 * @param sfpl
 */
function showPrintMessageWJ(gzlslids, sfpl) {
    addModel();
    var spbdylx, damldylx, qbdylx,dafmdylx;
    $.ajax({
        type: "GET",
        url: getContextPath() +"/rest/v1.0/dajj/dypz/dylx",
        data: {gzlslid: gzlslids[0]},
        async: false,
        success: function (data) {
            removeModal();
            if(isNotBlank(data)){
                spbdylx = data.dylx;
                damldylx = data.damldylx;
                dafmdylx = data.dafmdylx;
                // 打印全部的打印类型为
                qbdylx = spbdylx + damldylx + dafmdylx;
                dylxArr.push(spbdylx);
                dylxArr.push(damldylx);
                dylxArr.push(dafmdylx);
                dylxArr.push(qbdylx);
            }
            setDypzSession(dylxArr,sessionKey);
        }, error: function (e) {
            removeModal();
            response.fail(e, '');
        }
    });
    var c = layer.open({
        title: '一次只允许打印一种类型，请选择',
        type: 1,
        area: ['430px','180px'],
        btn: ['确定', '取消'],
        content: '<div id="bdc-popup-radio">\n' +
            '    <form class="layui-form" action="">\n' +
            '        <div class="layui-form-item pf-form-item">\n' +
            '            <div class="layui-inline">\n' +
            '                <label class="layui-form-label">打印类型：</label>\n' +
            '                <div class="layui-input-inline">\n' +
            '                </div>\n' +
            '            </div>\n' +
            '        </div>\n' +
            '    </form>\n' +
            '</div>',
        success: function () {
            var form = layui.form;
            var radio = "";
            radio += "<input type=\"radio\" name=\"dylxRadio\" data-type=\"bdcSqSpb\" value=\""+ spbdylx +"\" title=\"审批表\" lay-filter=\"zslxRadio\" checked>";
            radio += "<input type=\"radio\" name=\"dylxRadio\" data-type=\"daml\" value=\""+ damldylx +"\" title=\"档案目录\" lay-filter=\"zslxRadio\">";
            radio += "<input type=\"radio\" name=\"dylxRadio\" data-type=\"dafm\" value=\""+ dafmdylx +"\" title=\"封面\" lay-filter=\"zslxRadio\">";
            radio += "<input type=\"radio\" name=\"dylxRadio\" data-type=\"spbdaml\" value=\""+ qbdylx +"\" title=\"全部\" lay-filter=\"zslxRadio\">";
            $('#bdc-popup-radio').find(".layui-input-inline").html(radio);
            layui.form.render("radio");
        },
        yes: function (index, layero) {
            var modelUrl = "";
            var dataUrl = "";
            var dylx = $("input[name='dylxRadio']:checked").val();
            var type = $("input[name='dylxRadio']:checked").data("type");
            if (type === "bdcSqSpb") {
                modelUrl = getIP() + "/realestate-register-ui/static/printModel/" + dylx + ".fr3";
                if (sfpl) {
                    dataUrl = getIP() + "/realestate-register-ui/rest/v1.0/print/spb/" + gzlslids.join(",") + "/" + dylx + "/xml/pl?zxlc=" + false;
                } else {
                    dataUrl = getIP() + "/realestate-register-ui/rest/v1.0/print/spb/" + gzlslids[0] + "/" + dylx + "/xml?zxlc=" + false;
                }
            }
            if (type === "daml") {
                modelUrl = getIP() + "/realestate-register-ui/static/printModel/" + dylx + ".fr3";
                dataUrl = getIP() + "/realestate-register-ui/rest/v1.0/print/dajj/" + dylx + "/" + gzlslids.join(",");
            }
            if (type === "dafm") {
                modelUrl = getIP() + "/realestate-register-ui/static/printModel/" + dylx + ".fr3";
                dataUrl = getIP() + "/realestate-register-ui/rest/v1.0/print/dajj/dafm/" + dylx + "/" + gzlslids.join(",");
            }
            if (type === "spbdaml") {
                modelUrl = getIP() + "/realestate-register-ui/static/printModel/" + dylx + ".fr3";
                dataUrl = getIP() + "/realestate-register-ui/rest/v1.0/print/dajj/spbdaml/" + dylx + "/" + gzlslids.join(",");
            }
            if (isNullOrEmpty(modelUrl)) {
                warnMsg("请选择打印类型再打印");
                return;
            }
            console.info(dylxArr);
            console.log("打印类型：" + dylx + "工作流实例id：" + gzlslids);
            printChoice(dylx, "realestate-register-ui", dataUrl, modelUrl, false, sessionKey);
        }
        , btn2: function (index, layero) {
            //取消 的回调
            layer.close(c)
        }
        , cancel: function (index, layero) {
            //右上角关闭回调
            layer.close(c)
        }
    });
}


/**
 * 档案交接金坛打印
 * @param gzlslids
 * @param sfpl
 */
function showPrintMessageJT(gzlslids, sfpl) {
    addModel();
    var spbdylx, damldylx, qbdylx,dafmdylx;
    $.ajax({
        type: "GET",
        url: getContextPath() +"/rest/v1.0/dajj/dypz/dylx",
        data: {gzlslid: gzlslids[0]},
        async: false,
        success: function (data) {
            removeModal();
            if(isNotBlank(data)){
                spbdylx = data.dylx;
                damldylx = data.damldylx;
                dafmdylx = data.jtdafmdDylx;
                // 打印全部的打印类型为
                qbdylx = spbdylx + damldylx + dafmdylx;
                dylxArr.push(spbdylx);
                dylxArr.push(damldylx);
                dylxArr.push(dafmdylx);
                dylxArr.push(qbdylx);
            }
            setDypzSession(dylxArr,sessionKey);
        }, error: function (e) {
            removeModal();
            response.fail(e, '');
        }
    });
    var c = layer.open({
        title: '一次只允许打印一种类型，请选择',
        type: 1,
        area: ['430px','180px'],
        btn: ['确定', '取消'],
        content: '<div id="bdc-popup-radio">\n' +
            '    <form class="layui-form" action="">\n' +
            '        <div class="layui-form-item pf-form-item">\n' +
            '            <div class="layui-inline">\n' +
            '                <label class="layui-form-label">打印类型：</label>\n' +
            '                <div class="layui-input-inline">\n' +
            '                </div>\n' +
            '            </div>\n' +
            '        </div>\n' +
            '    </form>\n' +
            '</div>',
        success: function () {
            var form = layui.form;
            var radio = "";
            radio += "<input type=\"radio\" name=\"dylxRadio\" data-type=\"bdcSqSpb\" value=\""+ spbdylx +"\" title=\"审批表\" lay-filter=\"zslxRadio\" checked>";
            radio += "<input type=\"radio\" name=\"dylxRadio\" data-type=\"daml\" value=\""+ damldylx +"\" title=\"档案目录\" lay-filter=\"zslxRadio\">";
            radio += "<input type=\"radio\" name=\"dylxRadio\" data-type=\"dafm\" value=\""+ dafmdylx +"\" title=\"封面\" lay-filter=\"zslxRadio\">";
            radio += "<input type=\"radio\" name=\"dylxRadio\" data-type=\"spbdaml\" value=\""+ qbdylx +"\" title=\"全部\" lay-filter=\"zslxRadio\">";
            $('#bdc-popup-radio').find(".layui-input-inline").html(radio);
            layui.form.render("radio");
        },
        yes: function (index, layero) {
            var modelUrl = "";
            var dataUrl = "";
            var dylx = $("input[name='dylxRadio']:checked").val();
            var type = $("input[name='dylxRadio']:checked").data("type");
            if (type === "bdcSqSpb") {
                modelUrl = getIP() + "/realestate-register-ui/static/printModel/" + dylx + ".fr3";
                if (sfpl) {
                    dataUrl = getIP() + "/realestate-register-ui/rest/v1.0/print/spb/" + gzlslids.join(",") + "/" + dylx + "/xml/pl?zxlc=" + false;
                } else {
                    dataUrl = getIP() + "/realestate-register-ui/rest/v1.0/print/spb/" + gzlslids[0] + "/" + dylx + "/xml?zxlc=" + false;
                }
            }
            if (type === "daml") {
                modelUrl = getIP() + "/realestate-register-ui/static/printModel/" + dylx + ".fr3";
                dataUrl = getIP() + "/realestate-register-ui/rest/v1.0/print/dajj/" + dylx + "/" + gzlslids.join(",");
            }
            if (type === "dafm") {
                modelUrl = getIP() + "/realestate-register-ui/static/printModel/" + dylx + ".fr3";
                dataUrl = getIP() + "/realestate-register-ui/rest/v1.0/print/dajj/dafm/" + dylx + "/" + gzlslids.join(",");
            }
            if (type === "spbdaml") {
                modelUrl = getIP() + "/realestate-register-ui/static/printModel/" + dylx + ".fr3";
                dataUrl = getIP() + "/realestate-register-ui/rest/v1.0/print/dajj/spbdaml/" + dylx + "/" + gzlslids.join(",");
            }
            if (isNullOrEmpty(modelUrl)) {
                warnMsg("请选择打印类型再打印");
                return;
            }
            console.info(dylxArr);
            console.log("打印类型：" + dylx + "工作流实例id：" + gzlslids);
            printChoice(dylx, "realestate-register-ui", dataUrl, modelUrl, false, sessionKey);
        }
        , btn2: function (index, layero) {
            //取消 的回调
            layer.close(c)
        }
        , cancel: function (index, layero) {
            //右上角关闭回调
            layer.close(c)
        }
    });
}

/**
 * 档案交接武进打印
 * @param gzlslids
 * @param sfpl
 */
function showPrintMessageLY(gzlslids, sfpl) {
    addModel();
    var spbdylx, damldylx, qbdylx,dafmdylx;
    $.ajax({
        type: "GET",
        url: getContextPath() +"/rest/v1.0/dajj/dypz/dylx",
        data: {gzlslid: gzlslids[0]},
        async: false,
        success: function (data) {
            removeModal();
            if(isNotBlank(data)){
                spbdylx = data.dylx;
                damldylx = data.damldylx;
                dafmdylx = data.lydafmdDylx;
                // 打印全部的打印类型为
                qbdylx = spbdylx + damldylx + dafmdylx;
                dylxArr.push(spbdylx);
                dylxArr.push(damldylx);
                dylxArr.push(dafmdylx);
                dylxArr.push(qbdylx);
            }
            setDypzSession(dylxArr,sessionKey);
        }, error: function (e) {
            removeModal();
            response.fail(e, '');
        }
    });
    var c = layer.open({
        title: '一次只允许打印一种类型，请选择',
        type: 1,
        area: ['430px','180px'],
        btn: ['确定', '取消'],
        content: '<div id="bdc-popup-radio">\n' +
            '    <form class="layui-form" action="">\n' +
            '        <div class="layui-form-item pf-form-item">\n' +
            '            <div class="layui-inline">\n' +
            '                <label class="layui-form-label">打印类型：</label>\n' +
            '                <div class="layui-input-inline">\n' +
            '                </div>\n' +
            '            </div>\n' +
            '        </div>\n' +
            '    </form>\n' +
            '</div>',
        success: function () {
            var form = layui.form;
            var radio = "";
            radio += "<input type=\"radio\" name=\"dylxRadio\" data-type=\"bdcSqSpb\" value=\""+ spbdylx +"\" title=\"审批表\" lay-filter=\"zslxRadio\" checked>";
            radio += "<input type=\"radio\" name=\"dylxRadio\" data-type=\"daml\" value=\""+ damldylx +"\" title=\"档案目录\" lay-filter=\"zslxRadio\">";
            radio += "<input type=\"radio\" name=\"dylxRadio\" data-type=\"dafm\" value=\""+ dafmdylx +"\" title=\"封面\" lay-filter=\"zslxRadio\">";
            radio += "<input type=\"radio\" name=\"dylxRadio\" data-type=\"spbdaml\" value=\""+ qbdylx +"\" title=\"全部\" lay-filter=\"zslxRadio\">";
            $('#bdc-popup-radio').find(".layui-input-inline").html(radio);
            layui.form.render("radio");
        },
        yes: function (index, layero) {
            var modelUrl = "";
            var dataUrl = "";
            var dylx = $("input[name='dylxRadio']:checked").val();
            var type = $("input[name='dylxRadio']:checked").data("type");
            if (type === "bdcSqSpb") {
                modelUrl = getIP() + "/realestate-register-ui/static/printModel/" + dylx + ".fr3";
                if (sfpl) {
                    dataUrl = getIP() + "/realestate-register-ui/rest/v1.0/print/spb/" + gzlslids.join(",") + "/" + dylx + "/xml/pl?zxlc=" + false;
                } else {
                    dataUrl = getIP() + "/realestate-register-ui/rest/v1.0/print/spb/" + gzlslids[0] + "/" + dylx + "/xml?zxlc=" + false;
                }
            }
            if (type === "daml") {
                modelUrl = getIP() + "/realestate-register-ui/static/printModel/" + dylx + ".fr3";
                dataUrl = getIP() + "/realestate-register-ui/rest/v1.0/print/dajj/" + dylx + "/" + gzlslids.join(",");
            }
            if (type === "dafm") {
                modelUrl = getIP() + "/realestate-register-ui/static/printModel/" + dylx + ".fr3";
                dataUrl = getIP() + "/realestate-register-ui/rest/v1.0/print/dajj/dafm/" + dylx + "/" + gzlslids.join(",");
            }
            if (type === "spbdaml") {
                modelUrl = getIP() + "/realestate-register-ui/static/printModel/" + dylx + ".fr3";
                dataUrl = getIP() + "/realestate-register-ui/rest/v1.0/print/dajj/spbdaml/" + dylx + "/" + gzlslids.join(",");
            }
            if (isNullOrEmpty(modelUrl)) {
                warnMsg("请选择打印类型再打印");
                return;
            }
            console.info(dylxArr);
            console.log("打印类型：" + dylx + "工作流实例id：" + gzlslids);
            printChoice(dylx, "realestate-register-ui", dataUrl, modelUrl, false, sessionKey);
        }
        , btn2: function (index, layero) {
            //取消 的回调
            layer.close(c)
        }
        , cancel: function (index, layero) {
            //右上角关闭回调
            layer.close(c)
        }
    });
}



function getIP() {
    return document.location.protocol + "//" + window.location.host;
}

function getDajjProcessKey(key){
    if(isNotBlank(key) && isNotBlank(dajjConfig)){
        return dajjConfig[key];
    }
    $.ajax({
        type: "GET",
        url: getContextPath() + "/rest/v1.0/config/dajj/processkey",
        async: false,
        success: function (data) {
            dajjConfig = data;
        }, error: function (e) {
            response.fail(e,'');
        }
    });
    return dajjConfig[key];
}

function qlrReadIdCard(element) {
    qlrReadIDCardNew(element);
}

//获取汇总数据
function queryTailSummaryContent(gzlslid){
    var tailSummaryContent =[];
    var jjrVal ="交接人:";
    var jjsjVal ="交接日期:";
    var jsrVal ="接收人:";
    var jssjVal ="接收日期:";
    $.ajax({
        type: "GET",
        url: getContextPath() + "/rest/v1.0/dajj/damx/tailSummaryContent",
        data: {gzlslid: gzlslid},
        async: false,
        success: function (data) {
            if(isNotBlank(data)){
                if(isNotBlank(data.zfr)){
                    jjrVal +="  "+data.zfr;
                }
                if(isNotBlank(data.zfsj)) {
                    jjsjVal += "  "+new Date(data.zfsj.replace(/-/g, "/")).Format("yyyy/MM/dd");
                }
                if(isNotBlank(data.jsr)) {
                    jsrVal += "  "+data.jsr;
                }
                if(isNotBlank(data.jssj)) {
                    jssjVal += "  "+new Date(data.jssj.replace(/-/g, "/")).Format("yyyy/MM/dd");
                }
            }
        }, error: function (e) {
            response.fail(e,'');
        }
    });
    var jjr ={};
    jjr.mergeCellNum=3;
    jjr.mergeCellValue=jjrVal;
    tailSummaryContent.push(jjr);
    var jjsj ={};
    jjsj.mergeCellNum=2;
    jjsj.mergeCellValue=jjsjVal;
    tailSummaryContent.push(jjsj);
    var jsr={};
    jsr.mergeCellNum=1;
    jsr.mergeCellValue=jsrVal;
    tailSummaryContent.push(jsr);
    var jssj ={};
    jssj.mergeCellNum=2;
    jssj.mergeCellValue=jssjVal;
    tailSummaryContent.push(jssj);
    return tailSummaryContent;

}