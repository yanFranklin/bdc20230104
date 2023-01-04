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
var lcPageEdition = 'yancheng';
// 判断高级查询是否居中
var dbSearch = '';
var ybSearch = '';
var xmSearch = '';
var rlSearch = 'center';
var grSearch = 'center';
var defalut_wsyw_sply = '1,6,7';
var defalut_sply = '0,1,2,3,4,5';

// 认领列表 li 标签
var rlContentLi = '<li id="rlTab">认领列表</li>';
var formSelects;


var wsywSplyData;

layui.use(['jquery', 'table', 'formSelects'], function () {
    var $ = layui.jquery,
        form = layui.form,
        table = layui.table;
    formSelects = layui.formSelects;

    $(function () {
        // 获取网上业务审批来源配置
        $.ajax({
            url: getContextPath() + "/rest/v1.0/task/wsyw/sply",
            type: "GET",
            async: false,
            dataType: "json",
            success: function (data) {
                if (data && data.length > 0) {
                    wsywSplyData = data;
                } else {
                    warnMsg("未获取到审批来源配置，请联系管理员！");
                }
            },
            error: function (e) {
                warnMsg("审批来源配置有误，请联系管理员！");
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
            limitName: 'size' //每页数据量的参数名，默认：limit

        },
        limits: [10, 30, 50, 70, 90, 110, 130, 150],
        defaultToolbar: ['filter'],
        cols: [[
            {type: 'checkbox', width: 50, fixed: 'left'},
            {
                fixed: 'left', field: '', title: '序号', width: '5%', templet: function (d) {
                    return (d.LAY_INDEX);
                }
            },
            {field: '', title: '流程状态', templet: '#stateTpl', minWidth: 90},
            {field: 'sply', title: '审批来源', minWidth: 90, templet: function(d){
                    var splymc = "";
                    if (isNotBlank(splyzdx)) {
                        $.each(splyzdx, function (i, item) {
                            if (d.sply == item.DM) {
                                splymc = item.MC;
                                return false;
                            }
                        });
                    }
                    return splymc;
                }
            },
            {field: 'slbh', minWidth: 170, title: '受理编号', templet: '#slbhTpl', event: 'openpage'},
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
            {fixed: 'right',field: 'jszt', title: '缴税状态', templet: '#jsztTpl', minWidth: 90},
            {fixed: 'right', title: '流程图', templet: '#lcTpl', minWidth: 75},
            {fixed: 'right', title: '附件', templet: '#fjTpl', minWidth: 75}
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
        limits: [10, 30, 50, 70, 90, 110, 130, 150],
        defaultToolbar: ['filter'],
        cols: [[
            {type: 'checkbox', width: 50, fixed: 'left'},
            {
                fixed: 'left', field: '', title: '序号', width: '5%', templet: function (d) {
                    return (d.LAY_INDEX);
                }
            },
            {field: '', title: '流程状态', templet: '#stateTpl', minWidth: 90},
            {field: 'sply', title: '审批来源', minWidth: 90, templet: function(d){
                    var splymc = "";
                    if (isNotBlank(splyzdx)) {
                        $.each(splyzdx, function (i, item) {
                            if (d.sply == item.DM) {
                                splymc = item.MC;
                                return false;
                            }
                        });
                    }
                    return splymc;
                }
            },
            {field: 'slbh', minWidth: 170, title: '受理编号', templet: '#slbhTpl', event: 'openpage'},
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
            {fixed: 'right', title: '流程图', templet: '#lcTpl', minWidth: 75},
            {fixed: 'right', title: '附件', templet: '#fjTpl', minWidth: 75}
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
        request: {
            limitName: 'size' //每页数据量的参数名，默认：limit

        },
        limits: [10, 30, 50, 70, 90, 110, 130, 150],
        defaultToolbar: ['filter'],
        cols: [[
            {type: 'checkbox', width: 50, fixed: 'left'},
            {
                fixed: 'left', field: '', title: '序号', width: '5%', templet: function (d) {
                    return (d.LAY_INDEX);
                }
            },
            {field: 'procStatus', title: '流程状态', width: 90, templet: '#flowStateTpl'},
            {field: 'sply', title: '审批来源', minWidth: 90, templet: function(d){
                    var splymc = "";
                    if (isNotBlank(splyzdx)) {
                        $.each(splyzdx, function (i, item) {
                            if (d.sply == item.DM) {
                                splymc = item.MC;
                                return false;
                            }
                        });
                    }
                    return splymc;
                }
            },
            {field: 'slbh', minWidth: 170, title: '受理编号', templet: '#slbhTpl', event: 'openpage'},
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
            {fixed: 'right', title: '附件', templet: '#fjTpl', minWidth: 75},
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
        where: {
            'sply': defalut_sply
        },
        limits: [10, 30, 50, 70, 90, 110, 130, 150],
        defaultToolbar: ['filter'],
        cols: [[
            {type: 'checkbox', width: 50, fixed: 'left'},
            {
                fixed: 'left', field: '', title: '序号', width: '5%', templet: function (d) {
                    return (d.LAY_INDEX);
                }
            },
            {field: 'sply', title: '审批来源', minWidth: 90, templet: function(d){
                    var splymc = "";
                    if (isNotBlank(splyzdx)) {
                        $.each(splyzdx, function (i, item) {
                            if (d.sply == item.DM) {
                                splymc = item.MC;
                                return false;
                            }
                        });
                    }
                    return splymc;
                }
            },
            {field: 'slbh', minWidth: 170, title: '受理编号', templet: '#rlslbhTpl', event: 'openpage'},
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
            {fixed: 'right', title: '流程图', templet: '#lcTpl', minWidth: 75},
            {fixed: 'right', title: '附件', templet: '#fjTpl', minWidth: 75}
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

// 渲染网上业务列表
function renderWsywTable(tableId, currentId, toolbar) {
    layui.table.render({
        elem: tableId,
        id: currentId,
        data: [],
        toolbar: toolbar,
        title: '网上业务表',
        method: 'post',
        even: true,
        request: {
            limitName: 'size' //每页数据量的参数名，默认：limit

        },
        where: {
            'sply': defalut_wsyw_sply
        },
        limits: [10, 30, 50, 70, 90, 110, 130, 150],
        defaultToolbar: ['filter'],
        cols: [[
            {type: 'checkbox', width: 50, fixed: 'left'},
            {
                fixed: 'left', field: '', title: '序号', width: '5%', templet: function (d) {
                    return (d.LAY_INDEX);
                }
            },
            {field: 'sply', minWidth: 90, title: '业务来源' , templet:
                    function(d){
                        var splymc = "";
                        if (wsywSplyData && wsywSplyData.length > 0) {
                            $.each(wsywSplyData, function (i, item) {
                                if (d.sply === item.value) {
                                    var color = isNullOrEmpty(item.color) ? "#333" : item.color;
                                    splymc = '<span style="color:' + color + ';cursor:pointer">' + item.name + '</span>';
                                    return false;
                                }
                            });
                        }

                        // 如果没有手动配置指定来源，则取默认字典项名称
                        if (isNullOrEmpty(splymc) && isNotBlank(splyzdx)) {
                            $.each(splyzdx, function (i, item) {
                                if (d.sply === item.DM) {
                                    splymc = item.MC;
                                    return false;
                                }
                            });
                        }

                        return splymc;
                    }
            },
            {field: 'slbh', minWidth: 170, title: '受理编号', templet: '#rlslbhTpl', event: 'openpage'},
            {field: 'qlr', minWidth: 100, title: '权利人'},
            {field: 'ywr', minWidth: 100, title: '义务人'},
            {field: 'zl', minWidth: 200, title: '坐落'},
            {title: '流程名称', field: 'processDefName', minWidth: 160},
            {field: 'startTime', title: '开始时间', width: 160, sort: true},
            {field: 'zxcqzh', title: '不动产权证号/证明号', width: 230, sort: true},
            {field: 'djyy', minWidth: 200, title: '登记原因'},
            {field: 'procStartUserName', title: '受理人', minWidth: 100},
            {field: 'taskName', title: '节点名称', width: 90},
            {field: 'endTime', title: '结束时间', width: 100, sort: true},
            {field: 'bdcdyh', minWidth: 270, templet: '#bdcdyhTpl', title: '不动产单元号'},
            {title: '项目名称', templet: '#rwNameTpl', minWidth: 200, hide: true},
            {field: 'category', title: '业务类型', width: 90, hide: true},
            {field: 'taskAssName', title: '处理人', width: 90, hide: true},
            {fixed: 'right',field: 'sctjr', title: '退件人', width: 90},
            {fixed: 'right',field: 'sctjyy', title: '退件原因', minWidth: 200},
            {fixed: 'right', title: '流程图', templet: '#lcTpl', minWidth: 75},
            {fixed: 'right', title: '附件', templet: '#fjTpl', minWidth: 75}
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
            // 控制非管理员用户的权限
            if (todoPermission === true) {
                $('.bdc-search-rl').css("display", "none");
                $('.bdc-search-db').css("display", "none");
            }
            changeTableHeight();
            var reverseList = ['zl'];
            reverseTableCell(reverseList);

            if (wsywSplyData && wsywSplyData.length > 0) {
                $.each(wsywSplyData, function (index, item) {
                    $('#wsywsply').append('<option value="' + item.value + '">' + item.name + '</option>');
                });
                layui.form.render('select');
            }
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
            {
                fixed: 'left', field: '', title: '序号', width: '5%', templet: function (d) {
                    return (d.LAY_INDEX);
                }
            },
            {field: 'procStatus', title: '流程状态', width: 90, templet: '#flowStateTpl'},
            {field: 'sply', title: '审批来源', minWidth: 90, templet: function(d){
                    var splymc = "";
                    if (isNotBlank(splyzdx)) {
                        $.each(splyzdx, function (i, item) {
                            if (d.sply == item.DM) {
                                splymc = item.MC;
                                return false;
                            }
                        });
                    }
                    return splymc;
                }
            },
            {field: 'slbh', minWidth: 170, title: '受理编号', templet: '#slbhTpl', event: 'openpage'},
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
            {fixed: 'right', title: '附件', templet: '#fjTpl', minWidth: 75},
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

/**
 * 默认渲染下拉框       节点名称---登记原因
 * @param $select       select的calss
 * @param data          渲染数据
 */
function defaultRenderSelect($select,data){
    for(var i = 0,len = $select.length; i < len; i++){
        var jdOption = '<option value="">请选择</option>';
        data.forEach(function(v){
            jdOption += '<option value="'+ v +'">'+ v +'</option>';
        });
        $($select[i]).html(jdOption);
    }
    formSelects.render();
}

//修改流程名称时 重新渲染节点名称,登记原因
function updateRenderSelect($this,data){
    var jdOption = '<option value="">请选择</option>';
    data.forEach(function(v){
        jdOption += '<option value="'+ v +'">'+ v +'</option>';
    });
    $this.html(jdOption);
    formSelects.render();
}


/**
 * 查询节点名称信息
 * @param processDefKey 流程定义key
 */
function queryTaskNameList($this,processDefKey,isDefault) {
    $.ajax({
        type: "GET",
        url: getContextPath() + "/rest/v1.0/task/taskName",
        data: {processDefKey: processDefKey},
        success: function (data) {
            if(isDefault){
                defaultRenderSelect($this,data);
            }else {
                updateRenderSelect($this,data);
            }
        }, error: function (e) {
            response.fail(e, '');
        }
    });
}