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

// 判断高级查询是否居中
var dbSearch = '';
var ybSearch = '';
var xmSearch = '';
var rlSearch = '';
var dajjybSearch = '';

// 认领列表 li 标签
var rlContentLi = '<li id="rlTab">认领列表</li>';

var dylxArr = ["bdcSqSpb", "daml", "spbdaml"];
var sessionKey = "bdcShxx";
//setDypzSession(dylxArr, sessionKey);
var formSelects, form;
var lcPageEdition = '';

layui.use(['jquery', 'table', 'formSelects', 'form'], function () {
    var $ = layui.jquery,
        table = layui.table;
        form = layui.form;
        formSelects = layui.formSelects;

    $(function () {
        //渲染待办表格
        window.renderWaitTable = function (url, tableId, currentId, toolbar) {
            table.render({
                elem: tableId,
                id: currentId,
                url: url,
                toolbar: toolbar,
                title: '待办任务表格',
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
                    {type: 'checkbox', width: 50, fixed: 'left'},
                    {field: '', title: '流程状态', templet: '#stateTpl', minWidth: 90},
                    {field: 'slbh', minWidth: 110, title: '受理编号', templet: '#slbhTpl', event: 'openpage'},
                    {field: 'zrzydjdyh', minWidth: 160, title: '自然资源登记单元号'},
                    {field: 'zrzydjdymc', minWidth: 160, title: '登记单元名称'},
                    // {field: 'zrzycqzh', minWidth: 150, title: '自然资源产权证号'},
                    {field: 'zl', minWidth: 200, title: '坐落'},
                    {field: 'processDefName', templet: '#lcmcTpl', title: '流程名称', minWidth: 160},
                    // {field: 'djyy', minWidth: 200, title: '登记原因'},
                    {field: 'taskName', title: '节点名称', width: 90},
                    {field: 'startTime', title: '开始时间', width: 150, sort: true},
                    {title: '项目名称', templet: '#rwNameTpl', minWidth: 200, hide: true},
                    {field: 'category', title: '业务类型', width: 90, hide: true},
                    {field: 'claimStatusName', title: '认领状态', width: 90, hide: true},
                    {fixed: 'right', title: '流程图', templet: '#lcTpl', minWidth: 75}
                ]],
                parseData: function (res) { //res 即为原始返回的数据
                    // console.log(res);
                    if (res.totalElements > 99) {
                        $('.bdc-list-num-tips').html('99+');
                    } else {
                        $('.bdc-list-num-tips').html(res.totalElements);
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

        //渲染已办表格
        window.renderDoneTable = function (tableId, currentId, toolbar) {
            table.render({
                elem: tableId,
                id: currentId,
                data: [],
                toolbar: toolbar,
                title: '已办任务表格',
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
                    {type: 'checkbox', width: 50, fixed: 'left'},
                    {field: '', title: '流程状态', templet: '#stateTpl', minWidth: 90},
                    {field: 'slbh', minWidth: 110, title: '受理编号', templet: '#slbhTpl', event: 'openpage'},
                    {field: 'zrzydjdyh', minWidth: 160, title: '自然资源登记单元号'},
                    {field: 'zrzydjdymc', minWidth: 160, title: '登记单元名称'},
                    // {field: 'zrzycqzh', minWidth: 150, title: '自然资源产权证号'},
                    {field: 'zl', minWidth: 200, title: '坐落'},
                    {field: 'processDefName', title: '流程名称', minWidth: 160},
                    // {field: 'djyy', minWidth: 200, title: '登记原因'},
                    {field: 'taskName', title: '节点名称', width: 90},
                    {field: 'startTime', title: '开始时间', width: 150, sort: true},
                    {field: 'endTime', title: '结束时间', width: 150, sort: true},
                    {title: '项目名称', templet: '#rwNameTpl', minWidth: 200, hide: true},
                    {field: 'category', title: '业务类型', width: 90, hide: true},
                    {field: 'taskAssName', title: '处理人', width: 90, hide: true},
                    {fixed: 'right', title: '流程图', templet: '#lcTpl', minWidth: 75}
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

        //渲染项目列表
        window.renderListTable = function (tableId, currentId, toolbar) {
            table.render({
                elem: tableId,
                id: currentId,
                data: [],
                toolbar: toolbar,
                title: '项目列表',
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
                    {type: 'checkbox', width: 50, fixed: 'left'},
                    {field: 'procStatus', title: '流程状态', width: 90, templet: '#flowStateTpl'},
                    {field: 'slbh', minWidth: 110, title: '受理编号', templet: '#slbhTpl', event: 'openpage'},
                    {field: 'zrzydjdyh', minWidth: 160, title: '自然资源登记单元号'},
                    {field: 'zrzydjdymc', minWidth: 160, title: '登记单元名称'},
                    // {field: 'zrzycqzh', minWidth: 150, title: '自然资源产权证号'},
                    {field: 'zl', minWidth: 200, title: '坐落'},
                    {field: 'procDefName', title: '流程名称', minWidth: 160},
                    // {field: 'djyy', minWidth: 200, title: '登记原因'},
                    {field: 'startUserName', title: '受理人', minWidth: 100},
                    {field: 'startTime', title: '受理时间', width: 150, sort: true},
                    {title: '项目名称', templet: '#rwNameTpl', minWidth: 200, hide: true},
                    {field: 'categoryName', title: '业务类型', width: 90, hide: true},
                    {fixed: 'right', title: '流程图', templet: '#lcTpl', width: 75},
                    {fixed: 'right', field: 'procTimeoutStatus', width: 90, title: '超期状态', templet: '#overStateTpl'}
                ]],
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
        window.renderRlTable = function (tableId, currentId, toolbar) {
            table.render({
                elem: tableId,
                id: currentId,
                data: [],
                toolbar: toolbar,
                title: '认领任务表',
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
                    {type: 'checkbox', width: 50, fixed: 'left'},
                    {field: 'slbh', minWidth: 110, title: '受理编号', templet: '#rlslbhTpl', event: 'openpage'},
                    {field: 'zrzydjdyh', minWidth: 160, title: '自然资源登记单元号'},
                    {field: 'zrzydjdymc', minWidth: 160, title: '登记单元名称'},
                    // {field: 'zrzycqzh', minWidth: 150, title: '自然资源产权证号'},
                    {field: 'zl', minWidth: 200, title: '坐落'},
                    {title: '流程名称', field: 'processDefName', minWidth: 160},
                    // {field: 'djyy', minWidth: 200, title: '登记原因'},
                    {field: 'procStartUserName', title: '受理人', minWidth: 100},
                    {field: 'taskName', title: '节点名称', width: 90},
                    {field: 'startTime', title: '开始时间', width: 150, sort: true},
                    {field: 'endTime', title: '结束时间', width: 150, sort: true},
                    // {field: 'bdcdyh', minWidth: 270, templet: '#bdcdyhTpl', title: '不动产单元号'},
                    {title: '项目名称', templet: '#rwNameTpl', minWidth: 200, hide: true},
                    {field: 'category', title: '业务类型', width: 90, hide: true},
                    {field: 'taskAssName', title: '处理人', width: 90, hide: true},
                    {fixed: 'right', title: '流程图', templet: '#lcTpl', minWidth: 75}
                ]],
                parseData: function (res) { //res 即为原始返回的数据
                    //获取互联网+的处理
                    if ($('.bdc-rl-num-word').length > 0 && $('.bdc-rl-num-word').html().indexOf("互联网") != -1) {
                        var rlUrl = getContextPath() + "/rest/v1.0/task/claim/list";
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
                        if (res.totalElements > 99) {
                            $('.bdc-rl-num-js').html('99+');
                        } else {
                            $('.bdc-rl-num-js').html(res.totalElements);
                        }
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

});

/**
 * 查询节点名称信息
 * @param processDefKey 流程定义key
 */
function queryTaskNameList($this, processDefKey, isDefault) {
    $.ajax({
        type: "GET",
        url: getContextPath() + "/rest/v1.0/task/taskName",
        data: {processDefKey: processDefKey},
        success: function (data) {
            if (isDefault) {
                defaultRenderSelect($this, data);
            } else {
                updateRenderSelect($this, data);
            }
        }, error: function (e) {
            response.fail(e, '');
        }
    });
}

/**
 * 查询登记原因信息
 * @param processDefKey 流程定义key
 * @param isDefault     是否在默认渲染
 * @param $select      获取当前select
 */
function queryDjyyList(processDefKey, isDefault, $select) {
    $.ajax({
        type: "GET",
        url: getContextPath() + "/rest/v1.0/task/djyy",
        data: {processDefKey: processDefKey},
        success: function (data) {
            if (isDefault) {
                defaultRenderSelect($select, data);
            } else {
                updateRenderSelect($select, data);
            }
        }, error: function (e) {
            response.fail(e, '');
        }
    });
}

/**
 * 默认渲染下拉框       节点名称---登记原因
 * @param $select       select的calss
 * @param data          渲染数据
 */
function defaultRenderSelect($select, data) {
    for (var i = 0, len = $select.length; i < len; i++) {
        var jdOption = '<option value="">请选择</option>';
        data.forEach(function (v) {
            jdOption += '<option value="' + v + '">' + v + '</option>';
        });
        $($select[i]).html(jdOption);
    }
    formSelects.render();
}

//修改流程名称时 重新渲染节点名称,登记原因
function updateRenderSelect($this, data) {
    var jdOption = '<option value="">请选择</option>';
    data.forEach(function (v) {
        jdOption += '<option value="' + v + '">' + v + '</option>';
    });
    $this.html(jdOption);
    formSelects.render();
}

function getIP() {
    return document.location.protocol + "//" + window.location.host;
}


function qlrReadIdCard(element) {
    qlrReadIDCardNew(element);
}