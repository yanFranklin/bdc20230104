/**
 * Created by Ypp on 2019/8/19.
 * 标准版中新建任务js
 */
var zdList ;

$.ajax({
    url: getContextPath() + "/rest/v1.0/config/zd/yyfzx",
    type:"GET",
    success:function (data) {
        zdList = eval(data);
    }
});

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
var lcPageEdition = 'hf';
// 判断高级查询是否居中
var dbSearch = '';
var ybSearch = 'center';
var xmSearch = '';
var rlSearch = '';
var grSearch = 'center';
// 认领列表 li 标签
var rlContentLi = '<li class="bdc-word-li" id="rlTab">认领列表<span class="bdc-rl-num-tips"><i class="bdc-rl-num-js"></i><i class="bdc-rl-num-word" id="wlwRlTab">(互联网+)</i></span></li>';
//默认渲染节点名称
var jdDefaultData = ["受理", "审核", "登簿制证", "发证"];
// 项目列表数据
var listCacheData = [];
var defaultDate = new Date(new Date().setMonth(new Date().getMonth() - 1));
var defaultDateStr = defaultDate.Format("yyyy-MM-dd hh:mm:ss");
var $, form, response, formSelects;
layui.use(['jquery', 'table', 'form', 'response', 'formSelects'], function () {
    var table = layui.table;
    $ = layui.jquery;
    form = layui.form;
    response = layui.response;
    formSelects = layui.formSelects;

    $(function () {

        form.on('select(selectedDefName)', function (data) {
            if ($(data.elem).attr('class') != 'rlSearch') {
                var $currentJdmc = $(data.elem).parents('.bdc-search-box').find('.bdc-jdmc');
                queryTaskNameList($currentJdmc, data.value, false);
            }

            var $currentDjyy = $(data.elem).parents('.bdc-search-box').find('.bdc-djyy');
            queryDjyyList(data.value, false, $currentDjyy);
        });
    });

});
//表格高度自适应
function changeTableHeight() {
    if ($('.bdc-list-tab .layui-tab-content .layui-show .layui-table-main>.layui-table').height() == 0) {
        $('.bdc-list-tab .layui-tab-content .layui-show .layui-table-body .layui-none').html('<img src="../static/lib/bdcui/images/table-none.png" alt="">无数据');
    } else {
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
            {field: 'qlr', minWidth: 100, title: '权利人'},
            {field: 'ywr', minWidth: 100, title: '义务人'},
            {field: 'zl', minWidth: 200, title: '坐落'},
            {field: 'procStartUserName', minWidth: 100, title: '受理人'},
            {field: 'processDefName', templet: '#lcmcTpl', title: '流程名称', minWidth: 160},
            {field: 'djyy', minWidth: 200, title: '登记原因'},
            {field: 'taskName', title: '节点名称', width: 90},
            {field: 'startTime', title: '开始时间', width: 100, sort: true},
            {field: 'ywlx', minWidth: 100, title: '业务类型'},
            {field: 'bdcdyh', minWidth: 270, templet: '#bdcdyhTpl', title: '不动产单元号'},
            {
                field: 'yyfzx', title: '预约登记中心', width: 150,
                templet: function (d) {
                    return yyfzx(d);
                }
            },
            {
                field: 'yykssj', title: '预约时间', width: 300,
                templet: function (d) {
                    return yysjFormat(d);
                }
            },
            {field: 'jfzt', minWidth: 90, templet: '#jfztTpl', title: '缴费状态'},
            {field: 'yhjkrkzt', minWidth: 90, templet: '#jkztTpl', title: '缴库状态'},
            {title: '项目名称', templet: '#rwNameTpl', minWidth: 200, hide: true},
            {field: 'category', title: '业务类别', width: 90, hide: true},

            {field: 'claimStatusName', title: '认领状态', width: 90, hide: true},
            {fixed: 'right', title: '流程图', templet: '#lcTpl', minWidth: 75}
        ]],
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
        where: {
            'startTime_complete_ks': defaultDateStr
        },
        limits: [10, 30, 50, 70, 90, 110, 130, 150],
        defaultToolbar: ['filter'],
        cols: [[
            {type: 'checkbox', width: 50, fixed: 'left'},
            {field: '', title: '流程状态', templet: '#stateTpl', minWidth: 90},
            {field: 'slbh', minWidth: 110, title: '受理编号', templet: '#slbhTpl', event: 'openpage'},
            {field: 'qlr', minWidth: 100, title: '权利人'},
            {field: 'ywr', minWidth: 100, title: '义务人'},
            {field: 'zl', minWidth: 200, title: '坐落'},
            {field: 'processDefName', templet: '#lcmcTpl', title: '流程名称', minWidth: 160},
            {field: 'djyy', minWidth: 200, title: '登记原因'},
            {field: 'taskName', title: '节点名称', width: 90},
            {field: 'startTime', title: '开始时间', width: 100, sort: true},
            {field: 'endTime', title: '结束时间', width: 100, sort: true},
            {field: 'ywlx', minWidth: 100, title: '业务类型'},
            {field: 'bdcdyh', minWidth: 270, templet: '#bdcdyhTpl', title: '不动产单元号'},
            {
                field: 'yyfzx', title: '预约登记中心', width: 150,
                templet: function (d) {
                    return yyfzx(d);
                }
            },
            {
                field: 'yykssj', title: '预约时间', width: 300,
                templet: function (d) {
                    return yysjFormat(d);
                }
            },
            {title: '项目名称', templet: '#rwNameTpl', minWidth: 200, hide: true},
            {field: 'category', title: '业务类别', width: 90, hide: true},
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
function renderListTable(tableId, currentId, toolbar) {
    layui.table.render({
        elem: tableId,
        id: currentId,
        data: [],
        toolbar: toolbar,
        title: '用户数据表',
        method: 'post',
        even: true,
        where: {
            'startTime_list_ks': defaultDateStr
        },
        request: {
            limitName: 'size' //每页数据量的参数名，默认：limit

        },
        limits: [10, 30, 50, 70, 90, 110, 130, 150],
        defaultToolbar: ['filter'],
        cols: [[
            {type: 'checkbox', width: 50, fixed: 'left'},
            {field: 'procStatus', title: '流程状态', width: 90, templet: '#flowStateTpl'},
            {field: 'slbh', minWidth: 110, title: '受理编号', templet: '#slbhTpl', event: 'openpage'},
            {field: 'qlr', minWidth: 100, title: '权利人'},
            {field: 'ywr', minWidth: 100, title: '义务人'},
            {field: 'zl', minWidth: 200, title: '坐落'},
            {field: 'procDefName', templet: '#lcmcListTpl', title: '流程名称', minWidth: 160},
            {field: 'djyy', minWidth: 200, title: '登记原因'},
            {field: 'startUserName', title: '受理人', minWidth: 100},
            {field: 'startTime', title: '受理时间', width: 100, sort: true},
            {field: 'ywlx', minWidth: 100, title: '业务类型'},
            {field: 'bdcdyh', minWidth: 270, templet: '#bdcdyhTpl', title: '不动产单元号'},
            {
                field: 'yyfzx', title: '预约登记中心', width: 150,
                templet: function (d) {
                    return yyfzx(d);
                }
            },
            {
                field: 'yykssj', title: '预约时间', width: 300,
                templet: function (d) {
                    return yysjFormat(d);
                }
            },
            {title: '项目名称', templet: '#rwNameTpl', minWidth: 200, hide: true},
            {field: 'categoryName', title: '业务类别', width: 90, hide: true},
            {fixed: 'right', title: '流程图', templet: '#lcTpl', width: 75},
            {fixed: 'right', field: 'procTimeoutStatus', width: 90, title: '超期状态', templet: '#overStateTpl'}
        ]],
        parseData: function (res) { //res 即为原始返回的数据
            // 解决合肥项目列表跳转错误 bug
            listCacheData = res.content;
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
            limitName: 'size'//每页数据量的参数名，默认：limit

        },
        limits: [10, 30, 50, 70, 90, 110, 130, 150],
        defaultToolbar: ['filter'],
        cols: [[
            {type: 'checkbox', width: 50, fixed: 'left'},
            {field: 'slbh', minWidth: 110, title: '受理编号', templet: '#slbhTpl', event: 'openpage'},
            {field: 'qlr', minWidth: 100, title: '权利人'},
            {field: 'ywr', minWidth: 100, title: '义务人'},
            {field: 'zl', minWidth: 200, title: '坐落'},
            {field: 'processDefName', templet: '#lcmcTpl', title: '流程名称', minWidth: 160},
            {field: 'djyy', minWidth: 200, title: '登记原因'},
            {field: 'procStartUserName', title: '受理人', minWidth: 100},
            {field: 'taskName', title: '节点名称', width: 90},
            {field: 'startTime', title: '开始时间', width: 100, sort: true},
            {field: 'endTime', title: '结束时间', width: 100, sort: true},
            {field: 'ywlx', minWidth: 100, title: '业务类型'},
            {field: 'bdcdyh', minWidth: 270, templet: '#bdcdyhTpl', title: '不动产单元号'},
            {
                field: 'yyfzx', title: '预约登记中心', width: 150,
                templet: function (d) {
                    return yyfzx(d);
                }
            },
            {
                field: 'yykssj', title: '预约时间', width: 300,
                templet: function (d) {
                    return yysjFormat(d);
                }
            },
            {field: 'jfzt', minWidth: 90, templet: '#jfztTpl', title: '缴费状态'},
            {field: 'yhjkrkzt', minWidth: 90, templet: '#jkztTpl', title: '缴库状态'},
            {title: '项目名称', templet: '#rwNameTpl', minWidth: 200, hide: true},
            {field: 'category', title: '业务类别', width: 90, hide: true},
            {field: 'taskAssName', title: '处理人', width: 90, hide: true},
            {fixed: 'right', title: '流程图', templet: '#lcTpl', minWidth: 75}
        ]],
        parseData: function (res) { //res 即为原始返回的数据
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
            limitName: 'size' //每页数据量的参数名，默认：limit

        },
        limits: [10, 30, 50, 70, 90, 110, 130, 150],
        defaultToolbar: ['filter'],
        cols: [[
            {type: 'checkbox', width: 50, fixed: 'left'},
            {field: 'procStatus', title: '流程状态', width: 90, templet: '#flowStateTpl'},
            {field: 'slbh', minWidth: 110, title: '受理编号', templet: '#slbhTpl', event: 'openpage'},
            {field: 'qlr', minWidth: 100, title: '权利人'},
            {field: 'ywr', minWidth: 100, title: '义务人'},
            {field: 'zl', minWidth: 200, title: '坐落'},
            {field: 'procDefName', title: '流程名称', minWidth: 160},
            {field: 'startUserName', title: '受理人', minWidth: 100},
            {field: 'startTime', title: '受理时间', width: 100, sort: true},
            {field: 'ywlx', minWidth: 100, title: '业务类型'},
            {field: 'bdcdyh', minWidth: 270, templet: '#bdcdyhTpl', title: '不动产单元号'},
            {
                field: 'yyfzx', title: '预约登记中心', width: 150,
                templet: function (d) {
                    return yyfzx(d);
                }
            },
            {
                field: 'yykssj', title: '预约时间', width: 300,
                templet: function (d) {
                    return yysjFormat(d);
                }
            },
            {title: '项目名称', templet: '#rwNameTpl', minWidth: 200, hide: true},
            {field: 'categoryName', title: '业务类别', width: 90, hide: true},
            {fixed: 'right', title: '流程图', templet: '#lcTpl', width: 75},
            {fixed: 'right', field: 'procTimeoutStatus', width: 90, title: '超期状态', templet: '#overStateTpl'}
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
}

// 渲染认领列表
function renderFcwqRlTable(tableId, currentId, toolbar) {
    layui.table.render({
        elem: tableId,
        id: currentId,
        data: [],
        toolbar: toolbar,
        title: '房产网签认领任务表',
        method: 'post',
        even: true,
        request: {
            limitName: 'size'//每页数据量的参数名，默认：limit

        },
        limits: [10, 30, 50, 70, 90, 110, 130, 150],
        defaultToolbar: ['filter'],
        cols: [[
            {type: 'checkbox', width: 50, fixed: 'left'},
            {field: 'slbh', minWidth: 110, title: '受理编号', templet: '#slbhTpl', event: 'openpage'},
            {field: 'qlr', minWidth: 100, title: '权利人'},
            {field: 'ywr', minWidth: 100, title: '义务人'},
            {field: 'zl', minWidth: 200, title: '坐落'},
            {field: 'processDefName', templet: '#lcmcTpl', title: '流程名称', minWidth: 160},
            {field: 'djyy', minWidth: 200, title: '登记原因'},
            {field: 'procStartUserName', title: '受理人', minWidth: 100},
            {field: 'taskName', title: '节点名称', width: 90},
            {field: 'startTime', title: '开始时间', width: 100, sort: true},
            {field: 'endTime', title: '结束时间', width: 100, sort: true},
            {field: 'ywlx', minWidth: 100, title: '业务类型'},
            {field: 'bdcdyh', minWidth: 270, templet: '#bdcdyhTpl', title: '不动产单元号'},
            {
                field: 'yyfzx', title: '预约登记中心', width: 150,
                templet: function (d) {
                    return yyfzx(d);
                }
            },
            {
                field: 'yykssj', title: '预约时间', width: 300,
                templet: function (d) {
                    return yysjFormat(d);
                }
            },
            {field: 'jfzt', minWidth: 90, templet: '#jfztTpl', title: '缴费状态'},
            {field: 'yhjkrkzt', minWidth: 90, templet: '#jkztTpl', title: '缴库状态'},
            {title: '项目名称', templet: '#rwNameTpl', minWidth: 200, hide: true},
            {field: 'category', title: '业务类别', width: 90, hide: true},
            {field: 'taskAssName', title: '处理人', width: 90, hide: true},
            {fixed: 'right', title: '流程图', templet: '#lcTpl', minWidth: 75}
        ]],
        parseData: function (res) { //res 即为原始返回的数据
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
 * 预约时间格式化处理，进行拼接展示
 * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
 * @param data 台账数据
 * @return 时间拼接
 */
function yysjFormat(d) {
    var yysj;
    if (d) {
        if (d.yykssj && d.yyjssj) {
            yysj = d.yykssj + "至" + d.yyjssj;
        }
    }
    return yysj == undefined ? '' : yysj;
}

/**
 * 预约分中心代码转换
 * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
 * @param 代码
 * @return 名称
 */
function yyfzx(d) {
    var yyzx ;
            if (isNotBlank(zdList)){
                for (var i = 0 ;i< zdList.length;i++){
                    var zd = zdList[i];
                    if (d.yyfzx == zd.DM){
                        return yyzx = zd.MC;
                    }
                }
            }

    return yyzx == undefined ? '' : yyzx;
}


function loadDateHf() {
    layui.use(['laydate', 'jquery'], function () {
        var $ = layui.jquery,
            laydate = layui.laydate;
        lay('.test-item').each(function () {
            var kssjdy = laydate.render({
                elem: '#kssjdy',
                type: 'datetime',
                trigger: 'click',
                done: function (value, date) {
                    //选择的结束时间大
                    if ($('#kssjxy').val() != '' && !completeDate($('#kssjxy').val(), value)) {
                        $('#kssjxy').val('');
                        $('.laydate-disabled.layui-this').removeClass('layui-this');
                    }
                    kssjxy.config.min = {
                        year: date.year,
                        month: date.month - 1,
                        date: date.date,
                        hours: date.hours,
                        minutes: date.minutes,
                        seconds: date.seconds
                    }
                }
            });
            var kssjxy = laydate.render({
                elem: '#kssjxy',
                type: 'datetime',
                trigger: 'click'
            });

            var ybkssjdy = laydate.render({
                elem: '#ybkssjdy',
                type: 'datetime',
                value: defaultDate,
                trigger: 'click',
                done: function (value, date) {
                    //选择的结束时间大
                    if ($('#ybkssjxy').val() != '' && !completeDate($('#ybkssjxy').val(), value)) {
                        $('#ybkssjxy').val('');
                        $('.laydate-disabled.layui-this').removeClass('layui-this');
                    }
                    ybkssjxy.config.min = {
                        year: date.year,
                        month: date.month - 1,
                        date: date.date,
                        hours: date.hours,
                        minutes: date.minutes,
                        seconds: date.seconds
                    }
                }
            });
            var ybkssjxy = laydate.render({
                elem: '#ybkssjxy',
                type: 'datetime',
                trigger: 'click'
            });

            var xmkssjdy = laydate.render({
                elem: '#xmkssjdy',
                type: 'datetime',
                value: defaultDate,
                trigger: 'click',
                done: function (value, date) {
                    console.info(date);
                    //选择的结束时间大
                    if ($('#xmkssjxy').val() != '' && !completeDate($('#xmkssjxy').val(), value)) {
                        $('#xmkssjxy').val('');
                        $('.laydate-disabled.layui-this').removeClass('layui-this');
                    }
                    xmkssjxy.config.min = {
                        year: date.year,
                        month: date.month - 1,
                        date: date.date,
                        hours: date.hours,
                        minutes: date.minutes,
                        seconds: date.seconds
                    }
                }
            });
            var xmkssjxy = laydate.render({
                elem: '#xmkssjxy',
                type: 'datetime',
                trigger: 'click'
            });

            var grkssjdy = laydate.render({
                elem: '#grkssjdy',
                type: 'datetime',
                trigger: 'click',
                done: function (value, date) {
                    //选择的结束时间大
                    if ($('#grkssjxy').val() != '' && !completeDate($('#grkssjxy').val(), value)) {
                        $('#grkssjxy').val('');
                        $('.laydate-disabled.layui-this').removeClass('layui-this');
                    }
                    grkssjxy.config.min = {
                        year: date.year,
                        month: date.month - 1,
                        date: date.date,
                        hours: date.hours,
                        minutes: date.minutes,
                        seconds: date.seconds
                    }
                }
            });
            var grkssjxy = laydate.render({
                elem: '#grkssjxy',
                type: 'datetime',
                trigger: 'click'
            });

            var rlkssjdy = laydate.render({
                elem: '#rlkssjdy',
                type: 'datetime',
                trigger: 'click',
                done: function (value, date) {
                    //选择的结束时间大
                    if ($('#rlkssjxy').val() != '' && !completeDate($('#rlkssjxy').val(), value)) {
                        $('#rlkssjxy').val('');
                        $('.laydate-disabled.layui-this').removeClass('layui-this');
                    }
                    rlkssjxy.config.min = {
                        year: date.year,
                        month: date.month - 1,
                        date: date.date,
                        hours: date.hours,
                        minutes: date.minutes,
                        seconds: date.seconds
                    }
                }
            });
            var rlkssjxy = laydate.render({
                elem: '#rlkssjxy',
                type: 'datetime',
                trigger: 'click'
            });

            var fcwqrlkssjdy = laydate.render({
                elem: '#fcwqrlkssjdy',
                type: 'datetime',
                trigger: 'click',
                done: function (value, date) {
                    //选择的结束时间大
                    if ($('#fcwqrlkssjxy').val() != '' && !completeDate($('#fcwqrlkssjxy').val(), value)) {
                        $('#fcwqrlkssjxy').val('');
                        $('.laydate-disabled.layui-this').removeClass('layui-this');
                    }
                    rlkssjxy.config.min = {
                        year: date.year,
                        month: date.month - 1,
                        date: date.date,
                        hours: date.hours,
                        minutes: date.minutes,
                        seconds: date.seconds
                    }
                }
            });
            var fcwqrlkssjxy = laydate.render({
                elem: '#fcwqrlkssjxy',
                type: 'datetime',
                trigger: 'click'
            });
            if (lcPageEdition == 'nt') {
                var cnlzkssjdy = laydate.render({
                    elem: '#cnlzkssjdy',
                    trigger: 'click',
                    done: function (value, date) {
                        //选择的结束时间大
                        if ($('#cnlzkssjxy').val() != '' && !completeDate($('#cnlzkssjxy').val(), value)) {
                            $('#cnlzkssjxy').val('');
                            $('.laydate-disabled.layui-this').removeClass('layui-this');
                        }
                        cnlzkssjxy.config.min = {
                            year: date.year,
                            month: date.month - 1,
                            date: date.date
                        }
                    }
                });
                var cnlzkssjxy = laydate.render({
                    elem: '#cnlzkssjxy',
                    trigger: 'click'
                });

                var ybcnlzkssjdy = laydate.render({
                    elem: '#ybcnlzkssjdy',
                    trigger: 'click',
                    done: function (value, date) {
                        //选择的结束时间大
                        if ($('#cnlzkssjxy').val() != '' && !completeDate($('#cnlzkssjxy').val(), value)) {
                            $('#cnlzkssjxy').val('');
                            $('.laydate-disabled.layui-this').removeClass('layui-this');
                        }
                        ybcnlzkssjxy.config.min = {
                            year: date.year,
                            month: date.month - 1,
                            date: date.date
                        }
                    }
                });
                var ybcnlzkssjxy = laydate.render({
                    elem: '#ybcnlzkssjxy',
                    trigger: 'click'
                });

                var xmcnlzkssjdy = laydate.render({
                    elem: '#xmcnlzkssjdy',
                    trigger: 'click',
                    done: function (value, date) {
                        //选择的结束时间大
                        if ($('#cnlzkssjxy').val() != '' && !completeDate($('#cnlzkssjxy').val(), value)) {
                            $('#cnlzkssjxy').val('');
                            $('.laydate-disabled.layui-this').removeClass('layui-this');
                        }
                        xmcnlzkssjxy.config.min = {
                            year: date.year,
                            month: date.month - 1,
                            date: date.date
                        }
                    }
                });
                var xmcnlzkssjxy = laydate.render({
                    elem: '#xmcnlzkssjxy',
                    trigger: 'click'
                });

                var rlcnlzkssjdy = laydate.render({
                    elem: '#rlcnlzkssjdy',
                    trigger: 'click',
                    done: function (value, date) {
                        //选择的结束时间大
                        if ($('#cnlzkssjxy').val() != '' && !completeDate($('#cnlzkssjxy').val(), value)) {
                            $('#cnlzkssjxy').val('');
                            $('.laydate-disabled.layui-this').removeClass('layui-this');
                        }
                        rlcnlzkssjxy.config.min = {
                            year: date.year,
                            month: date.month - 1,
                            date: date.date
                        }
                    }
                });
                var rlcnlzkssjxy = laydate.render({
                    elem: '#rlcnlzkssjxy',
                    trigger: 'click'
                });
            }
        });
    });
}