/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @description 【常州】不动产异议
 */
var BASE_URL = '/realestate-inquiry-ui/rest/v1.0/bdcYyXx';
// 分页重置查询参数
var searchParam =[];
// 当前页数据
var reverseList = ['zl'];
var currentPageData = new Array();
layui.use(['table', 'laytpl', 'laydate', 'layer', 'form'], function () {
    var $ = layui.jquery,
        table = layui.table,
        layer = layui.layer,
        formSelects = layui.formSelects,
        form = layui.form;

    // 获取页面表单标识，用于权限控制
    var moduleCode = $.getUrlParam('moduleCode');
    /**
     * 加载Table数据列表
     */
    var reverseList = ['zl','bdcdyh','bdcqzh'];
    table.render({
        elem: '#bdcYyTable',
        toolbar: '#toolbar',
        title: '不动产异议列表',
        defaultToolbar: ['filter'],
        request: {
            limitName: 'size' //每页数据量的参数名，默认：limit
        },
        data: [],
        where: {},
        even: true,
        cols: [[
            {type: 'checkbox', fixed: 'left',minWidth:60},
            {field: 'qlid', title: '权利ID',hide:true},
            {field: 'xmid', title: '项目ID',hide:true},
            {field: 'slbh', title: '受理编号',hide:true},
            {field: 'qlr', title: '权利人'},
            {field: 'zjh', title: '权利人证件号', minWidth: 300},
            {
                field: 'bdcdyh', title: '不动产单元号', minWidth: 280, templet: function (d) {
                    return formatBdcdyh(d.bdcdyh);
                }},
            {field: 'bdcqzh', title: '不动产权证号', minWidth: 300},
            {field: 'bdcdywybh', title: '不动产单元唯一编号',hide:true},
            {field: 'zl', title: '坐落', minWidth: 300},
            {field: 'djjg', title: '登记机构',hide:true},
            {field: 'dbr', title: '登簿人',hide:true},
            {field: 'gyqk', title: '共有情况',hide:true},
            {field: 'yysx', title: '异议事项', minWidth: 300},
            {field: 'zxyyywh', title: '注销异议业务号',hide:true},
            {field: 'zxyydbr', title: '注销异议登簿人', hide:true},
            {field: 'djsj', title: '登记时间',templet: function (d) {
                    return formatDate(d.djsj);},minWidth:180},
            {field: 'zxyydjsj', title: '注销异议登记时间',templet: function (d) {
                    return formatDate(d.zxyydjsj);},minWidth:180},
            {field: 'zxyyyy', title: '注销异议原因', minWidth: 300},
            {field: 'qszt', title: '权属状态', fixed: 'right', templet: '#qsztTpl', minWidth: 100},
            {field: 'qx',title: '到期时间（天）', fixed: 'right',templet:'#qxTpl',minWidth:130},
            {fixed: 'right', title:'操作', toolbar: '#barDemo', minWidth:120}
        ]],
        text: {
            none: '未查询到数据'
        },
        page: true,
        limits: [10,  50, 100, 200, 500],
        parseData: function (res) {
            // 设置选中行
            if (res.content && res.content.length > 0) {
                var checkedData = layui.sessionData('checkedData');
                res.content.forEach(function (v) {
                    $.each(checkedData, function (key, value) {
                        if (key == v.qlid) {
                            v.LAY_CHECKED = true;
                        }
                    })
                });
            };
            return {
                code: res.code, //解析接口状态
                msg: res.msg, //解析提示文本
                count: res.totalElements, //解析数据长度
                data: res.content //解析数据列表
            }
        },
        done: function () {
            reverseTableCell(reverseList);
            setHeight();
            if(moduleCode){
                setElementAttrByModuleAuthority(moduleCode);
            }
        }
    });

    // 监听复选框事件，缓存选中的行数据
    table.on('checkbox(bdcYyTable)', function (obj) {
        // 获取选中或者取消的数据
        var data = obj.type == 'one' ? [obj.data] : currentPageData;
        $.each(data, function (i, v) {
            if (obj.checked) {
                //.增加已选中项
                layui.sessionData('checkedData', {
                    key: v.qlid, value: v
                });
            } else {
                //.删除
                layui.sessionData('checkedData', {
                    key: v.qlid, remove: true
                });
            }
        });
    });

    $('.bdc-table-box').on('mouseenter','td',function () {
        if($(this).text() && reverseList.indexOf($(this).attr("data-field")) != -1){
            $(this).append('<div class="layui-table-grid-down"><i class="layui-icon layui-icon-down"></i></div>');
        }
        $('.layui-table-grid-down').on('click',function () {
            setTimeout(function () {
                $('.layui-table-tips-main .bdc-table-date').html(reverseString($('.layui-table-tips-main .bdc-table-date').html()));
            },20);
        });
    });

    function reverseString(str) {
        if (!isNotBlank(str)) {
            return str;
        }
        str = str.replace(/&lt;/g, '>');
        str = str.replace(/&gt;/g, '<');
        var RexStr = /\（|\）|\(|\)|\【|\】|\【|\】|\[|\]|\[|\]/g;
        str = str.replace(RexStr, function (MatchStr) {
            switch (MatchStr) {
                case "(":
                    return ")";
                    break;
                case ")":
                    return "(";
                    break;
                case "（":
                    return '）';
                    break;
                case "）":
                    return "（";
                    break;
                case "[":
                    return "]";
                    break;
                case "]":
                    return "[";
                    break;
                case "【":
                    return "】";
                    break;
                case "】":
                    return "【";
                    break;
            }
        });
        return str.split("").reverse().join("");
    }
    function reverseTableCell(reverseList) {
        var getTd = $('.layui-table-view .layui-table td');
        for(var i = 0; i < getTd.length; i++){
            reverseList.forEach(function (v) {
                if($(getTd[i]).attr('data-field') == v){
                    var getTdCell = $(getTd[i]).find('.layui-table-cell');
                    getTdCell.css('direction','rtl');
                    var tdHtml = reverseString(getTdCell.html());
                    getTdCell.html('<span class="bdc-table-date">'+ tdHtml +'</span>');
                }
            });
        }
    }

    //头工具栏事件
    table.on('toolbar(bdcYyTable)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id);
        switch (obj.event) {
            case 'zx':zx(checkStatus.data);
                break;
            case 'export':
                if(searchParam.length == 0){
                    warnMsg("请选择需要导出Excel的数据行!");
                    return
                }
                if(checkStatus.data.length  > 0){
                    exportExcel(checkStatus.data, obj.config.cols[0], "checked");
                }else{
                    warnMsg("请选择需要导出Excel的数据行!");
                    // exportAllExcel(checkStatus.data, obj.config);
                }
                obj.config.where.type = "";
                break;
        }
    });
    //监听行工具事件
    table.on('tool(bdcYyTable)', function(obj){
        var data = obj.data;
        var url = htmlConfig.djbHtml.bdcYyHtml + '?bdcdyh=' + data.bdcdyh;
        if(obj.event === 'openBdcYy'){
            layer.open({
                title: '不动产单元列表',
                type: 2,
                area: ['960px','530px'],
                content: url,
                cancel: function(index,layero){
                },
                success: function () {
                }
            });
            saveDetailLog("YYCX_CKXQ", "异议查询-查看详情",data);
        }
    });

    /**
     * 监听排序事件
     */
    table.on('sort(bdcYyTable)', function (obj) {
        table.reload('bdcYyTable', {
            initSort: obj,
            where: {
                field: obj.field, //排序字段
                order: obj.type //排序方式
            }
        });
    });

    /**
     * 点击查询
     */
    $('#search').on('click', function (data) {
        // 每次查询清除下导出缓存数据
        layui.sessionData("checkedData", null);

        // 判断必填条件
        var count = 0;
        $(".required").each(function (i) {
            if (!isNullOrEmpty($(this).val())) {
                count += 1;
            }
        });
        if (0 == count) {
            warnMsg(" 请输入必要查询条件，例如权利人、证件号等");
            return false;
        }

        // 查询类型默认： 精确
        var mhlx = {
            "bdcdyhmhlx": 0, "bdcqzhmhlx": 0, "qlrmcmhlx": 0, "qlrzjhmhlx": 0, "ywrmhlx": 0,
            "ywrzjhmhlx": 0, "zlmhlx": 0, "ycqzhmhlx": 0, "zhmhlx": 0, "fjhmhlx": 0, "slbhmhlx": 0,
            "fwbhmhlx": 0, "zhlshmhlx": 0, "yxtcqzhmhlx": 0, "zsyzhmhlx": 0, "bdcqzhjcmhlx": 0,

        };
        // 选择方式 全模糊
        if(3 == $('input:radio[name="cxfs"]:checked').val()){
            mhlx = {
                "bdcdyhmhlx": 3, "bdcqzhmhlx": 3, "qlrmcmhlx": 3, "qlrzjhmhlx": 3, "ywrmhlx": 3,
                "ywrzjhmhlx": 3, "zlmhlx": 3, "ycqzhmhlx": 3, "zhmhlx": 3, "fjhmhlx": 3, "slbhmhlx": 3,
                "fwbhmhlx": 3, "zhlshmhlx": 3, "yxtcqzhmhlx": 3, "zsyzhmhlx": 3,"bdcqzhjcmhlx": 3,
            };
        }
        form.val('searchform', JSON.parse(JSON.stringify(mhlx)));

        // 获取查询参数
        var obj = getSearchData();

        searchParam = obj;
        searchParam["type"] = "";

        addModel();
        // 重新请求
        table.reload("bdcYyTable", {
            url: BASE_URL + '/page/cz',
            where: searchParam,
            page: {
                curr: 1  //重新从第 1 页开始
            },
            done: function (res, curr, count) {
                currentPageData = res.data;
                reverseTableCell(reverseList);
                setHeight();
                removeModal();
            }
        });
    });

    function getSearchData(){
        // 获取查询参数
        var obj = {};
        $(".search").each(function (i) {
            var name = $(this).attr('name');
            var value = $(this).val();
            if (value) {
                value = value.replace(/\s*/g, "");
            }
            obj[name] = replaceBracket(value);
        });

        // 获取复选框参数
        obj.qsztList = getQsztCheckedVal();
        return obj;
    }

    /**
     * 重新加载数据
     */
    window.reload = function () {
        var obj = getSearchData();
        table.reload("bdcYyTable", {
            where: obj,
            page: {
                curr: 1  //重新从第 1 页开始
            }
        });
    };


    form.on('select', function (data) {
        if($(this).text() == "请选择"){
            $(this).parents('.layui-input-inline').find('.reseticon').hide();
        }else{
            $(this).parents('.layui-input-inline').find('.reseticon').show();
        }
    });
    $('.reseticon').on('click',function(item){
        $(this).parent().find("option:contains('请选择')").attr("selected", "selected");
        $(this).hide();
        layui.form.render("select");
    })
    $('#reset').on('click',function(item){
        $('.bdc-percentage-container').find('.layui-form')
            .find('select').find("option:eq(0)")
            .attr("selected","selected");
        setTimeout($('.bdc-percentage-container').find('.layui-form')
            .find('select').parent().find('input').val(''),100);
        $('.reseticon').hide();
    })

    function zx(checkeddata) {
        if (!checkeddata || checkeddata.length ==0) {
            layer.alert("请选择需要编辑的记录！", {title: '提示'});
            return;
        }
        var msg='';
        $.each(checkeddata, function (key, val) {
            if(val.qszt!=1){
                msg+=val.slbh+'非现势状态异议信息，不能注销！';
            }
            if(val.qx>0){
                msg+=val.slbh+'为未到期异议信息，请登记异议注销流程！';
            }
        });
        if(!isNullOrEmpty(msg)){
            warnMsg(msg);
            return;
        }
        showDialog(checkeddata);
    }

    function showDialog(data) {

        var div = " <div id=\"bdc-popup-textarea\"><form class=\"layui-form\" action=\"\">" +
            "            <div class=\"layui-form-item pf-form-item\">" +
            "                <div class=\"layui-inline\">" +
            "                    <label class=\"layui-form-label\"><span class=\"required-span\"><sub>*</sub></span>注销原因</label>" +
            "                    <div class=\"layui-input-inline bdc-end-time-box\">" +
            "                        <textarea name=\"zxyy\" id='zxyy' placeholder=\"请输入内容\" class=\"layui-textarea\"></textarea>" +
            "                    </div>" +
            "                </div>" +
            "            </div>" +
            "        </form>" +
            "    </div>";
        //小弹出层
        layer.open({
            title: '注销原因',
            type: 1,
            area: ['430px'],
            btn: ['提交', '取消'],
            content: div
            , yes: function (index, layero) {
                //提交 的回调
                updateBdcYy(data,index);
            }
            , btn2: function (index, layero) {
                //取消 的回调
                layer.close(index)
            }
            , cancel: function () {
                //右上角关闭回调
                //return false 开启该代码可禁止点击该按钮关闭
                layer.close(index)
            }
            , success: function () {

            }
        });
    }

    function updateBdcYy(data,index) {
        $.each(data, function (key, val) {
           val.zxyyyy=$('#zxyy').val();
        });
        $.ajax({
            url: BASE_URL,
            type: "POST",
            data: JSON.stringify(data),
            contentType: 'application/json',
            dataType: "json",
            success: function (data) {
                saveSuccessMsg();
            },
            error: function (e) {
                saveFailMsg();
            },complete:function () {
                $('#zxyy').val('');
                layer.close(index);
                reload();
            }
        });
    }

});

// 导出Excel
// 通过隐藏form提交方式，避免ajax方式不支持下载文件
// function exportExcel(data, cols) {
//     var checkedData = layui.sessionData('checkedData');
//     if ($.isEmptyObject(checkedData)) {
//         layer.alert("<div style='text-align: center'>请选择需要导出Excel的数据行！</div>", {title: '提示'});
//         return;
//     }
//     // 标题
//     var showColsTitle = new Array();
//     // 列内容
//     var showColsField = new Array();
//     // 列宽
//     var showColsWidth = new Array();
//     for(var index in cols){
//         if(cols[index].hide == false && cols[index].type != 'checkbox' && !cols[index].toolbar){
//             showColsTitle.push(cols[index].title);
//             showColsField.push(cols[index].field);
//             if(cols[index].width > 0){
//                 showColsWidth.push(parseInt(cols[index].width / 100 * 15));
//             }else if(cols[index].minWidth > 0){
//                 showColsWidth.push(parseInt(cols[index].minWidth / 100 * 15));
//             }else{
//                 showColsWidth.push(200 / 100 * 15);
//             }
//         }
//     }
//
//     // 设置Excel基本信息
//     $("#fileName").val('不动产异议');
//     $("#sheetName").val('统计表');
//     $("#cellTitle").val(showColsTitle);
//     $("#cellWidth").val(showColsWidth);
//     $("#cellKey").val(showColsField);
//     var data = new Array();
//     $.each(checkedData, function (key, value) {
//         data.push(value);
//     })
//
//     for (var i = 0; i < data.length; i++) {
//         data[i].xh = i + 1;
//         data[i].qszt = formatQsztOfData(data[i].qszt);
//         data[i].qx = formatIsDeadLine(data[i].qx);
//         data[i].zxyydjsj = formatDate(data[i].zxyydjsj);
//         data[i].djsj = formatDate(data[i].djsj);
//     }
//     $("#data").val(JSON.stringify(data));
//     $("#form").submit();
// }

// 格式化权属状态
function formatQsztOfData(qszt){
    if (qszt == 0) {
        return '临时';
    } else if (qszt == 1){
        return '现势'
    } else if (qszt == 2) {
        return '历史'
    } else if (qszt == 3) {
        return '终止'
    } else {
        return '';
    }
}

function formatIsDeadLine(data) {
    if(parseInt(data) == 0){
        return "到期";
    } else if(parseInt(data) < 0) {
        return "超期"
    } else{
        return data
    }
}

// 保存记录操作信息
function saveDetailLog(logType, viewTypeName, data){
    var json = JSON.parse(JSON.stringify(data));
    json.logType = logType;
    json.ipaddress = ipaddress;
    json.viewTypeName = viewTypeName;
    saveLogInfo(json);
}

/**
 * 导出Excel通过隐藏form提交方式，避免ajax方式不支持下载文件
 * @date 2019.05.25 14:45
 * @author chenyucheng
 * @return
 */
function exportAllExcel(data, obj){
    layer.confirm("是否导出全部数据？", {
        title: "提示",
        btn: ["确认", "取消"],
        btn1: function (index) {
            var cols = obj.cols[0]
            var url = obj.url;
            var paramData = obj.where;
            paramData["type"] = "export";

            $.ajax({
                url: url,
                dataType: "json",
                data: paramData,
                success: function (data) {
                    obj.where.type = "";
                    if(data.length > 0){//查询成功
                        exportExcel(data,cols, "all");
                    }
                }
            });

            layer.close(index);
        },
        btn2: function (index) {
            obj.where.type = "";
            return;
        }
    });

}


/**
 * 导出Excel通过隐藏form提交方式，避免ajax方式不支持下载文件
 * @date 2019.05.24 14:45
 * @author zhuyong
 * @return
 */
function exportExcel(d, cols, type){
    var checkedData = d;
    // 标题
    var showColsTitle = new Array();
    // 列内容
    var showColsField = new Array();
    // 列宽
    var showColsWidth = new Array();
    for(var index in cols){
        if(cols[index].hide == false && cols[index].type != 'checkbox' && !cols[index].toolbar){
            showColsTitle.push(cols[index].title);
            showColsField.push(cols[index].field);
            if(cols[index].width > 0){
                showColsWidth.push(parseInt(cols[index].width / 100 * 15));
            }else if(cols[index].minWidth > 0){
                showColsWidth.push(parseInt(cols[index].minWidth / 100 * 15));
            }else{
                showColsWidth.push(200 / 100 * 15);
            }
        }
    }

    // 设置Excel基本信息
    $("#fileName").val('不动产异议');
    $("#sheetName").val('统计表');
    $("#cellTitle").val(showColsTitle);
    $("#cellWidth").val(showColsWidth);
    $("#cellKey").val(showColsField);
    var data = new Array();
    $.each(checkedData, function (key, value) {
        data.push(value);
    })

    for (var i = 0; i < data.length; i++) {
        data[i].xh = i + 1;
        data[i].qszt = formatQsztOfData(data[i].qszt);
        data[i].qx = formatIsDeadLine(data[i].qx);
        data[i].zxyydjsj = formatDate(data[i].zxyydjsj);
        data[i].djsj = formatDate(data[i].djsj);
    }
    $("#data").val(JSON.stringify(data));
    saveDetailLog("YYCX_EXPORT", "异议查询-导出excel",{"DCNR":data})
    searchParam["type"]="";
    $("#form").submit();
}

function reloadPageTable(searchData){
    layui.table.reload("bdcYyTable", {
        url: BASE_URL + "/page/cz",
        where: searchData,
        page: {
            curr: 1, //重新从第 1 页开始
            limits: [10, 50, 100, 200, 500]
        },
        done: function (res, curr, count) {
            currentPageData = res.data;
            reverseTableCell(reverseList);
            removeModal();
            setHeight();
        }
    });
}

window.getQsztCheckedVal = function() {
    var checkedArray = [];
    $("input:checkbox[name='qsztmc']:checked").each(function (i) {
        var strArray = $(this).val().split(",");
        $.each(strArray, function (index, val) {
            if (null != val && "" != val) {
                checkedArray.push(val);
            }
        });
    });
    return checkedArray.join(",");
}


window.getQllxCheckedVal = function () {
    return "";
}

