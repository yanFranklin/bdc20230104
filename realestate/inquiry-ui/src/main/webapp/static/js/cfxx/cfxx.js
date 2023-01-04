/**
 * author: <a href="mailto:chenyucheng@gtmap.cn>chenyucheng</a>
 * version 1.0.0  2019/07/10
 * describe: 查封信息
 */
var reverseList = ['zl'];
var cols = [];
var isBeyondDeadLineParam = $.getUrlParam("isBeyondDeadLine");

layui.use(['form', 'jquery', 'element', 'table', 'laydate'], function () {
    var form = layui.form;
    var layer = layui.layer;
    var laydate = layui.laydate;
    var table = layui.table;
    layui.sessionData('checkedData', null);
    //绑定回车键
    $(document).keydown(function (event) {
        if (event.keyCode == 13) {
            $("#search").click();
        }
    });


    //x 的事件
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

    cols = [
        {type: 'qlid', title: 'qlid', hide: true},
        {type: 'xmid', title: 'xmid', hide: true},
        {type: 'checkbox', fixed: 'left'},
        {type: 'numbers', field: 'xh', title: '序号', width: 70, fixed: 'left'},
        {type: 'btn', title: '续封', toolbar: '#barDemo', width: 70, fixed: 'left'},
        {field: 'bdcdyh',sort:true, title: '不动产单元号', width: 280},
        {field: 'cfwh',sort:true, title: '查封文号', width: 180},
        {field: 'bdcqzh', sort:true,title: '原产权证号', width: 250},
        {field: 'slbh', sort:true,title: '业务编号', width: 150},
        {field: 'qlr',sort:true, title: '权利人', width: 200},
        {field: 'bzxr',sort:true, title: '被执行人', width: 200},
        {
            field: 'cflx',sort:true, title: '查封类型', width: 120,
            templet: function (d) {
                return formatCflx(d.cflx);
            }
        },
        {field: 'cfqssj',sort:true, title: '查封起始时间', width: 180},
        {field: 'cfjssj',sort:true, title: '查封结束时间', width: 180},
        {field: 'cfsj', sort:true,title: '查封送达时间', width: 180},
        {field: 'djsj', sort:true,title: '登记时间', width: 180},
        {field: 'cfjg', sort:true,title: '查封机关', width: 160},
        {field: 'zl', sort:true,minWidth: 300, title: '坐落'},
        {field: 'gyqk',sort:true, title: '共有情况', width: 120, hide: true},
        {field: 'djjg', sort:true,title: '登记机构', width: 180},
        {
            field: 'qszt',sort:true, title: '权属状态', fixed: 'right', width: 120, templet: function (d) {
                return formatQszt(d.qszt);
            }
        },
        {
            field: 'isBeyondDeadLine',sort:true, title: '是否到期', fixed: 'right', width: 120, templet: function (d) {
                return formatIsDeadLine(d.cfjssj);
            }
        },
        {title:'操作', fixed:'right', toolbar: '#barDemoOp', width:90}

    ];
    // 加载Table
    table.render({
        elem: '#cfTable',
        toolbar: '#toolbarDemo',
        title: '查封信息列表',
        defaultToolbar: ['filter'],
        request: {
            limitName: 'size' //每页数据量的参数名，默认：limit
            //,
            //loadTotal:true,
            //loadTotalBtn:false
        },
        even: true,
        cols: [cols],
        data: [],
        page: true,
        limits: [10, 20, 50, 100, 200, 500],
        parseData: function (res) {
            // 设置选中行
            if (res.content && res.content.length > 0) {
                var checkedData = layui.sessionData('checkedData');
                res.content.forEach(function (v) {
                    $.each(checkedData, function (key, value) {
                        if (key == v.xmid) {
                            v.LAY_CHECKED = true;
                        }
                    })
                });
            }
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
        }
    });

    // 设置默认查询一周内的日志
    //var nowDate = new Date();
    //var preDate = new Date(nowDate.getTime() - 30*24*60*60*1000);
    //精确到时分秒日期控件
    laydate.render({
        elem: '#cfqssj',
        type: 'datetime',
        format: 'yyyy-MM-dd HH:mm:ss',
        // value: preDate,
        done: function (value, date, endDate) {
            var startDate = new Date(value).getTime();
            var endTime = new Date($('#cfjssj').val()).getTime();
            if (endTime < startDate) {
                layer.msg('<img src="../../static/lib/bdcui/images/error-small.png" alt="">结束时间不能小于开始时间');
            }
        }
    });
    laydate.render({
        elem: '#cfjssj',
        type: 'datetime',
        format: 'yyyy-MM-dd HH:mm:ss',
        done: function (value, date, endDate) {
            var startDate = new Date(value).getTime();
            var endTime = new Date($('#cfqssj').val()).getTime();
            if (endTime > startDate) {
                layer.msg('<img src="../../static/lib/bdcui/images/error-small.png" alt="">结束时间不能小于开始时间');
            }
        }
    });
    // 监听表单提交事件
    form.on('submit(search)', function (data) {
        if (data.field) {
            addModel();
            // 重新请求
            // 获取查询参数
            var obj = data.field;
            if (obj.cfqssj != "") {
                obj["cfqssj"] = obj.cfqssj + " 00:00:00";
            }
            if (obj.cfjssj != "") {
                obj["cfjssj"] = obj.cfjssj + " 23:59:59";
            }
            console.log(obj);
            table.reload("cfTable", {
                url: '/realestate-inquiry-ui/cfxx/list'
                , where: obj
                , page: {
                    curr: 1  //重新从第 1 页开始
                }
                , done: function (res, curr, count) {
                    currentPageData = res.data;
                    removeModal();
                    setHeight();
                    reverseTableCell(reverseList);
                    table.resize('cfTable');
                }
            });
        } else {
            layer.msg("请输入查询条件！");
        }
        return false;
    });
    // 监听表格操作栏按钮
    table.on('toolbar(cfTable)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id);
        switch (obj.event) {
            case 'exportExcel':
                exportExcel(checkStatus.data, obj.config.cols[0], "checked");
                break;
            case 'exportAllExcel':
                exportAllExcel(checkStatus.data, obj.config);
                break;
        }
    });

    // 监听复选框事件，缓存选中的行数据
    table.on('checkbox(cfTable)', function (obj) {
        // 获取选中或者取消的数据
        var data = obj.type == 'one' ? [obj.data] : currentPageData;
        $.each(data, function (i, v) {
            var keyT = v.xmid;
            if (obj.checked) {
                //.增加已选中项
                layui.sessionData('checkedData', {
                    key: keyT, value: v
                });
            } else {
                //.删除
                layui.sessionData('checkedData', {
                    key: keyT, remove: true
                });
            }
        });
    });

    //点击高级查询
    $('#seniorSearch').on('click', function () {
        $('.pf-senior-show').toggleClass('bdc-hide');
        //$(this).parent().toggleClass('bdc-button-box-four');
        $('.bdc-percentage-container').css('padding-top', $('.bdc-search-content').height() + 10);
        if($('.bdc-table-box .layui-table-main>.layui-table').height() == 0){
            $('.layui-table-body .layui-none').html('<img src="../../static/lib/registerui/image/table-none.png" alt="">无数据');
        }else {
            $('.layui-table-body').height($('.bdc-table-box').height() - 131);
            $('.layui-table-fixed .layui-table-body').height($('.bdc-table-box').height() - 131 - 17);
        }
    });

    $("#xfxq").live('click',function(data){
        var xmid = $(this).attr("xmid");
        window.open("/realestate-register-ui/view/qlxx/cfdj.html?xmid="+xmid+"&readOnly=true&isCrossOri=false");
    });

    //监听行工具事件
    table.on('tool(cfTable)', function (obj) {
        var data = obj.data;
        if(obj.event === 'cfxq'){
            window.open("/realestate-register-ui/view/qlxx/cfdj.html?xmid="+data.xmid+"&readOnly=true&isCrossOri=false");
            return;
        }

        var $tr = $(obj.tr[0]);
        var $trHeader = $(obj.tr[1]);
        var $right = $(obj.tr[2]);
        if (obj.event === 'show') {
            var xmid = obj.data.xmid;
            var item = $(this);
            if (xmid) {
                getXfxx(obj.data, xmid, $tr, $trHeader, item, $right);
            } else {
                layer.alert("xmid为空，无法查询！", {title: '提示'});
            }
        } else if (obj.event === 'hide') {
            //隐藏按钮列
            $(this).toggleClass('bdc-hide').siblings('.bdc-show-btn').toggleClass('bdc-hide');
            $tr.next().remove();
            $trHeader.next().remove();
            $right.next().remove();
        }
    });

    if(isBeyondDeadLineParam != "" && isBeyondDeadLineParam != null){
        $("#isBeyondDeadLine option[value='"+isBeyondDeadLineParam+"']").attr("selected", "selected");
        layui.form.render("select");
        $("#search").click();

    }

});

/**
 * 导出Excel通过隐藏form提交方式，避免ajax方式不支持下载文件
 * @date 2019.05.24 14:45
 * @author zhuyong
 * @return
 */
function exportExcel(d, cols, type) {
    var checkedData = layui.sessionData('checkedData');
    if ($.isEmptyObject(checkedData) && type == "checked") {
        layer.alert("请选择需要导出Excel的数据行！", {title: '提示'});
        return;
    }

    // 标题
    var showColsTitle = [];
    // 列内容
    var showColsField = [];
    // 列宽
    var showColsWidth = [];
    for (var index in cols) {
        if (cols[index].hide == false && cols[index].type != 'checkbox' && !cols[index].toolbar) {
            showColsTitle.push(cols[index].title);
            showColsField.push(cols[index].field);
            if (cols[index].width > 0) {
                showColsWidth.push(parseInt(cols[index].width / 100 * 15));
            } else if (cols[index].minWidth > 0) {
                showColsWidth.push(parseInt(cols[index].minWidth / 100 * 15));
            } else {
                showColsWidth.push(200 / 100 * 15);
            }
        }
    }

    var data = new Array();
    if (type == "checked") {
        $.each(checkedData, function (key, value) {
            data.push(value);
        })
    } else {
        data = d;
    }
    for (var i = 0; i < data.length; i++) {
        data[i].xh = i + 1;
        data[i].qszt = delHtmltag(formatQszt(data[i].qszt));
        data[i].cflx = formatCflx(data[i].cflx);
        data[i].isBeyondDeadLine = delHtmltag(formatIsDeadLine(data[i].cfjssj));
    }

    // 设置Excel基本信息
    $("#fileName").val('查封信息');
    $("#sheetName").val('统计表');
    $("#cellTitle").val(showColsTitle);
    $("#cellWidth").val(showColsWidth);
    $("#cellKey").val(showColsField);
    $("#data").val(JSON.stringify(data));
    $("#form").submit();
}

/**
 * 导出Excel通过隐藏form提交方式，避免ajax方式不支持下载文件
 * @date 2019.05.25 14:45
 * @author chenyucheng
 * @return
 */
function exportAllExcel(data, obj) {
    var cols = obj.cols[0]
    var url = obj.url;
    var paramData = obj.where;
    paramData["type"] = "export";
    $.ajax({
        url: url,
        dataType: "json",
        data: paramData,
        success: function (data) {
            if (data.code == 0) {//查询成功
                exportExcel(data.content, cols, "all");
            }
        }
    });
}

// 重写方法，自定义格式化日期
function toLocalStringDate(date) {
    // 按自定义拼接格式返回
    return date.getFullYear() + "-" + addZero(date.getMonth() + 1) + "-" + addZero(date.getDate()) + " " +
        addZero(date.getHours()) + ":" + addZero(date.getMinutes()) + ":" + addZero(date.getSeconds());
}

// 补0   例如 2018/7/10 14:7:2  补完后为 2018/07/10 14:07:02
function addZero(num) {
    if (num < 10)
        return "0" + num;
    return num;
}

// 格式化权属状态
function formatQszt(qszt) {
    if (qszt == 0) {
        return '<span class="" style="color:#1d87d1;">临时</span>';
    } else if (qszt == 1) {
        return '<span class="" style="color:#32b032;">现势</span>'
    } else if (qszt == 2) {
        return '<span class="" style="color:#f28943;">历史</span>'
    } else if (qszt == 3) {
        return '<span class="" style="color:#f24b43;">终止</span>'
    } else {
        return '';
    }
}

function formatCflx(data) {
    if (data == "1") {
        return "查封";
    } else if (data == "2") {
        return "轮候查封";
    } else if (data == "3") {
        return "预查封";
    }else if (data == "4") {
        return "轮候预查封";
    }else if (data == "5") {
        return "续封";
    }else{
        return data;
    }

}

function formatIsDeadLine(data) {
    //console.log(data);
    if (new Date(data).getTime() <= new Date().getTime()) {
        return '<span class="" style="color:#f24b43;">到期</span>'
    } else {
        return "未到期"
    }
}


function getXfxx(clickdata, xmid, $tr, $trHeader, $item, $right) {
    var url = "/realestate-inquiry-ui/cfxx/xf/list/" + xmid;
    $.ajax({
        url: url,
        type: 'get',
        data: {},
        async: false,
        success: function (data) {
            if (data == null || data == undefined || data.length == 0) {
                layer.alert("没有续封信息！", {title: '提示'});
                return;
            }
            var html = "<tr><td colspan=\"3\"><div class=\"layui-table-cell\"></div></td>";
            var fixedRightHtml = "<tr>";
            for (var i = 0; i < data.length; i++) {
                var dataObj = data[i];
                for (var j = 0; j < cols.length; j++) {
                    if (!cols[j].hide && cols[j].type != "qlid" && cols[j].type != "xmid" && cols[j].type != "checkbox"
                        && cols[j].title != "操作"&& cols[j].type != "btn" && cols[j].type != "numbers" && cols[j].type != "xh") {
                        var classStr = $tr.find('td:eq(' + j + ')').find('div').attr('class');
                        var innerHtml = dataObj[cols[j].field.toUpperCase()];
                        if (cols[j].field == "qszt") {
                            fixedRightHtml += "<td data-field=\"extractingSum\" data-key=\"1-0-9\"" +
                                " class=\"\"><div\" +\n" +
                                "                            \" class=\"layui-table-cell" +
                                " laytable-cell-1-0-9\">" + formatQszt(innerHtml) + "</div></td>";
                            continue;
                        } else if (cols[j].field == "cflx") {
                            innerHtml = formatCflx(innerHtml);
                        } else if (cols[j].field == "isBeyondDeadLine") {
                            fixedRightHtml += "<td data-field=\"extractingSum\" data-key=\"1-0-9\"" +
                                " class=\"\"><div\" +\n" +
                                "                            \" class=\"layui-table-cell" +
                                " laytable-cell-1-0-9\">" + formatIsDeadLine(clickdata.isBeyondDeadLine) + "</div></td>";
                            continue;
                        }
                        if (!innerHtml) {
                            innerHtml = "";
                        }
                        html += "<td><div class='" + classStr + "'>" + innerHtml + "</div></td>"
                    }
                }
            }

            fixedRightHtml += '<td data-field="21" data-key="2-0-21" data-off="true"' +
                    ' class="layui-table-col-special"><div class="layui-table-cell laytable-cell-2-0-21"> <a' +
                ' id= "xfxq" xmid="'+data[0].XMID+'" class="layui-btn layui-btn-xs bdc-major-btn" style="margin-left: -32px;">查封详情</a>' +
                ' </div></td>';
            fixedRightHtml += "</tr>";
            html += "</tr>"
            // 显示按钮
            $tr.after(html);
            $right.after(fixedRightHtml)
            $trHeader.after('<tr class="bdc-fixed-left"><td colspan="3"><div class="layui-table-cell"></div></td></tr>');
            $item.toggleClass('bdc-hide').siblings('.bdc-show-btn').toggleClass('bdc-hide');
            $('.layui-table-fixed .layui-table-body').height($('.layui-table-fixed-1 .layui-table-body .layui-table').height());
            reverseAddCell(reverseList);
        }
    })
}

function warningDeadline(endDate) {
    var nowMi = new Date().getTime();
    var endDateMi = new Date(endDate).getTime();
    var minus = endDateMi - nowMi;
    if (30 * 24 * 60 * 60 * 1000 <= minus && minus < 184 * 24 * 60 * 60 * 1000) { // 一个月到半年
        return '<span class="" style="color:#f28943;">' + endDate + '</span>'
    } else if (1 * 24 * 60 * 60 * 1000 <= minus && minus < 30 * 24 * 60 * 60 * 1000) {// 一个月以内
        return '<span class="" style="color:#f24b43;">' + endDate + '</span>'
    } else {
        return endDate;
    }

}

function delHtmltag(str) {
    var returnStr = "";
    if (str != "" && str != null) {
        returnStr = str.replace(/<\/?.+?>/g, "");
        returnStr = returnStr.replace(/ /g, "");
    }
    return returnStr;
}

function reverseAddCell(reverseList) {
    reverseList.forEach(function (v) {
        var getClass = '.bdc-' + v;
        for (var i = 0; i < $(getClass).length; i++) {
            var $this = $($(getClass)[i]);
            var tdHtml = reverseString($this.html());
            // console.log(tdHtml);
            $this.html(tdHtml);
        }

    });
}
