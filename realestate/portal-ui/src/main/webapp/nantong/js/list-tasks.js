/**
 * Created by Ypp on 2019/8/19.
 * 南通 特殊版本 新建任务js
 */
layui.config({
    base: '../static' //此处路径请自行处理, 可以使用绝对路径
}).extend({
    formSelects: '/lib/form-select/formSelects-v4'
});

//新建任务内容高度
var carouselHeight = '132px';
//新建任务默认展示个数
var carouselCount = 15;
//新建任务一行展示个数
var carouselline = 5;
//查询宽度
var taskToolsWidth = "230px";
var taskToolsClickWidth = "90%";

//认领询问 特殊功能
var isConfirmRl = true;
//转发需要根据lcPageEdition判断显示哪个send.html
var lcPageEdition = 'nt';
// 判断待办高级查询是否居中
var dbSearch = '';
var ybSearch = 'center';
var xmSearch = '';
var rlSearch = 'center';
var grSearch = 'center';
var wwsqSearch = '';

// 认领列表 li 标签
var rlContentLi = '<li id="rlTab">认领列表<span class="bdc-rl-num-tips bdc-rl-num-js"></span></li>';
var wwsqContentLi = '<li id="wwsqTab">外网申请<span class="bdc-rl-num-tips bdc-wwsq-num-js"></span></li>'

var layer, $;
var formSelects;
layui.use(['jquery', 'layer', 'form', 'laytpl', 'table','formSelects'], function () {
    var form = layui.form,
        carousel = layui.carousel,
        laytpl = layui.laytpl;
    table = layui.table;
    layer = layui.layer;
    $ = layui.jquery;
    formSelects = layui.formSelects;
    var selectTaskObj = {
        "dbSearch": "#selectedTaskName",
        "ybSearch": "#selectedDoneTaskName",
        "xmSearch": "#selectedXmTaskName",
        "grSearch": "#selectedGrTaskName",
        "rlSearch": "#selectedRlTaskName",
        "wwsqSearch": "#selectedWwsqTaskName"
    };
    // 联动对应处理
    form.on('select(selectedDefName)', function(data){
        if(data.value==undefined||data.value==null){
            return;
        }
        var id=selectTaskObj[data.elem.className];
        console.log(data);
        $.ajax({
            url: getContextPath()  + '/rest/v1.0/task/jdmcs?processDefKey=' + data.value,
            type: "GET",
            dataType: "json",
            success: function (data) {
                if(data){
                    $(id).empty();
                    $(id).append(new Option("请选择", ""));
                    $.each(data, function (index, item) {
                        $(id).append(new Option(item.name, item.name));// 下拉菜单里添加元素
                    });
                    layui.form.render("select");
                }
            }
        });
    });




});
/**
 * 去除时分秒
 * @param timestamp
 * @returns {string}
 */
function formatNYR(timestamp) {
    if (!timestamp) {
        return '';
    }
    var arr = timestamp.split(" ");
    return arr[0];
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
            {field: '', title: '任务状态', templet: '#stateTpl', minWidth: 90},
            {field: 'slbh', minWidth: 110, title: '受理编号', templet: '#slbhTpl', event: 'openpage'},
            {field: 'qlr', minWidth: 100, title: '权利人'},
            {field: 'ywr', minWidth: 100, title: '义务人'},
            {field: 'zl', minWidth: 200, title: '坐落'},
            {field: 'procStartUserName', minWidth: 100, title: '受理人'},
            {field: 'startUserDepName', minWidth: 100, title: '部门名称'},
            {field: 'processDefName', templet: '#lcmcTpl', title: '流程名称', minWidth: 160},
            {field: 'lzrq', minWidth: 150, title: '承诺领证日期'},
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
            res.content.forEach(function (v) {
                if (v.lzrq) {
                    v.lzrq = formatNYR(v.lzrq);
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
            limitName: 'size' //每页数据量的参数名，默认：limit

        },
        limits: [10,30,50,70,90,110,130,150],
        defaultToolbar: ['filter'],
        cols: [[
            {type: 'checkbox', width: 50, fixed: 'left'},
            {field: '', title: '任务状态', templet: '#stateTpl', minWidth: 90},
            {field: 'slbh', minWidth: 110, title: '受理编号', templet: '#slbhTpl', event: 'openpage'},
            {field: 'qlr', minWidth: 100, title: '权利人'},
            {field: 'ywr', minWidth: 100, title: '义务人'},
            {field: 'zl', minWidth: 200, title: '坐落'},
            {field: 'startUserDepName', minWidth: 100, title: '部门名称'},
            {field: 'processDefName', title: '流程名称', minWidth: 160},
            {field: 'lzrq', minWidth: 150, title: '承诺领证日期'},
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
            res.content.forEach(function (v) {
                if (v.lzrq) {
                    v.lzrq = formatNYR(v.lzrq);
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
            limitName: 'size'//每页数据量的参数名，默认：limit

        },
        limits: [10,30,50,70,90,110,130,150],
        defaultToolbar: ['filter'],
        cols: [[
            {type: 'checkbox', width: 50, fixed: 'left'},
            {field: 'procStatus', title: '流程状态', width: 90, templet: '#flowStateTpl'},
            {field: 'slbh', minWidth: 110, title: '受理编号', templet: '#slbhTpl', event: 'openpage'},
            {field: 'qlr', minWidth: 100, title: '权利人'},
            {field: 'ywr', minWidth: 100, title: '义务人'},
            {field: 'zl', minWidth: 200, title: '坐落'},
            {field: 'startUserDepName', minWidth: 100, title: '部门名称'},
            {field: 'procDefName', title: '流程名称', minWidth: 160},
            {field: 'lzrq', minWidth: 150, title: '承诺领证日期'},
            {field: 'djyy', minWidth: 200, title: '登记原因'},
            {field: 'startUserName', title: '受理人', minWidth: 100},
            {field: 'startTime', title: '受理时间', width: 100, sort: true},
            {field: 'bdcdyh', minWidth: 270, templet: '#bdcdyhTpl', title: '不动产单元号'},
            {field: 'szr', minWidth: 100, title: '缮证人'},
            {title: '项目名称', templet: '#rwNameTpl', minWidth: 200, hide: true},
            {field: 'categoryName', title: '业务类型', width: 90, hide: true},
            {fixed: 'right', title: '流程图', templet: '#lcTpl', width: 75},
            {fixed: 'right', field: 'procTimeoutStatus', width: 90, title: '超期状态', templet: '#overStateTpl'}
        ]],
        parseData: function (res) { //res 即为原始返回的数据
            res.content.forEach(function (v) {
                if (v.lzrq) {
                    v.lzrq = formatNYR(v.lzrq);
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
        limits: [10,30,50,70,90,110,130,150],
        defaultToolbar: ['filter'],
        cols: [[
            {type: 'checkbox', width: 50, fixed: 'left'},
            {field: 'slbh', minWidth: 110, title: '受理编号', templet: '#rlslbhTpl', event: 'openpage'},
            {field: 'qlr', minWidth: 100, title: '权利人'},
            {field: 'ywr', minWidth: 100, title: '义务人'},
            {field: 'zl', minWidth: 200, title: '坐落'},
            {field: 'startUserDepName', minWidth: 100, title: '部门名称'},
            {title: '流程名称', field: 'processDefName', minWidth: 160},
            {field: 'lzrq', minWidth: 150, title: '承诺领证日期'},
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
        ]],
        parseData: function (res) { //res 即为原始返回的数据
            res.content.forEach(function (v) {
                if (v.lzrq) {
                    v.lzrq = formatNYR(v.lzrq);
                }
            });
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

            changeTableHeight();
            var reverseList = ['zl'];
            reverseTableCell(reverseList);
        }
    });
};

//外网申请列表
function renderWwsqTable(url, tableId, currentId, toolbar) {
    layui.table.render({
        elem: tableId,
        id: currentId,
        url: url,
        data: [],
        toolbar: toolbar,
        title: '外网申请',
        method: 'post',
        even: true,
        request: {
            limitName: 'size'//每页数据量的参数名，默认：limit

        },
        limits: [10,30,50,70,90,110,130,150],
        defaultToolbar: ['filter'],
        cols: [[
            {type: 'checkbox', width: 50, fixed: 'left'},
            {field: 'slbh', minWidth: 110, title: '受理编号', templet: '#rlslbhTpl', event: 'openpage'},
            {field: 'qlr', minWidth: 100, title: '权利人'},
            {field: 'ywr', minWidth: 100, title: '义务人'},
            {field: 'zl', minWidth: 200, title: '坐落'},
            {field: 'startUserDepName', minWidth: 100, title: '部门名称'},
            {title: '流程名称', field: 'processDefName', minWidth: 160},
            {field: 'lzrq', minWidth: 150, title: '承诺领证日期'},
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
        ]],
        parseData: function (res) { //res 即为原始返回的数据
            res.content.forEach(function (v) {
                if (v.lzrq) {
                    v.lzrq = formatNYR(v.lzrq);
                }
            });
            var wwsqUrl = getContextPath() + "/rest/v1.0/task/claim/wwsq";
            //获取互联网+的处理
            if($('.bdc-wwsq-num-word').length>0 && $('.bdc-wwsq-num-word').html().indexOf("互联网")!=-1){
                $.ajax({
                    type: "POST",
                    url: wwsqUrl,
                    data: {sply: "3"},
                    success: function (data) {
                        if(data && data.hasOwnProperty("totalElements")){
                            $('.bdc-wwsq-num-js').html(data.totalElements)
                        }
                    }
                });
            }else{
                $.ajax({
                    type: "POST",
                    url: wwsqUrl,
                    data: {},
                    success: function (data) {
                        if (data && data.hasOwnProperty("totalElements")) {
                            if (data.totalElements > 99) {
                                $('.bdc-wwsq-num-js').html('99+');
                            } else {
                                $('.bdc-wwsq-num-js').html(data.totalElements);
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
            {field: 'bdcdyh', minWidth: 270, templet: '#bdcdyhTpl', title: '不动产单元号'},
            {title: '项目名称', templet: '#rwNameTpl', minWidth: 200, hide: true},
            {field: 'categoryName', title: '业务类型', width: 90, hide: true},
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