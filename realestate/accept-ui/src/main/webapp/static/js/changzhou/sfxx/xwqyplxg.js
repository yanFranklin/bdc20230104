layui.config({
    base: '../../static/lib/form-select/' //此处路径请自行处理, 可以使用绝对路径
}).extend({
    formSelects: 'formSelects-v4'
});
var reverseList = ['zl'];
var searchParam = {};
var moduleCode = getQueryString("moduleCode");
var processInsId = getQueryString("processInsId");
var $, table, form, laytpl, formSelects;
var zdList = {};
var jfData ={};
var requestUrl = getContextPath() + "/rest/v1.0/cz/sfxx/xwqy/sfxx?gzlslid=" + processInsId;
layui.use(['jquery', 'element', 'form', 'table', 'laydate', 'laytpl', 'formSelects'], function () {
    //获取字典
    $ = layui.jquery;
    form = layui.form;
    table = layui.table;
    laytpl = layui.laytpl;
    formSelects = layui.formSelects;

    var isSearch = true;
    $('.bdc-table-box').on('focus', '.layui-laypage-skip .layui-input', function () {
        isSearch = false;
    }).on('blur', '.layui-laypage-skip .layui-input', function () {
        isSearch = true;
    });

    // 当前页数据
    $(function () {
        loadData();
        loadSearch();

        var tableData =[];
        // 加载Table
        var col = [[
            {type: 'checkbox', width: 50, },
            {field: 'xh', type: 'numbers', width: 60, title: '序号'},
            {field: 'slbh', title: '受理编号', width: 130},
            {field: 'djyy', title: '登记原因', width: 160},
            {field: 'zl', title: '坐落', align: 'center', width: 280},
            {field: 'qlr', title: '权利人', align: 'center', width: 200},
            {field: 'qlrdlr', title: '代理人', align: 'center', width: 180},
            {field: 'jfje', title: '金额', align: 'center', width: 100},
            {field: 'sfztmc', title: '缴费状态', align: 'center', width: 100},
            {field: 'fph', title: '票号', width: 150}
        ]];
        table.render({
            elem: '#xwqyTable',
            toolbar: "#toolbar",
            title: '小微企业批量更改台账',
            defaultToolbar: ["filter"],
            url: requestUrl,
            even: true,
            cols: col,
            data: tableData,
            page: false,
            parseData: function (res) {
                jfData = res;
                return {
                    code: res.code, //解析接口状态
                    msg: res.msg, //解析提示文本
                    count: res.totalElements, //解析数据长度
                    data: res.content //解析数据列表
                }
            },
            done: function (res) {
                $(".bdc-table-zj").removeClass("bdc-hide");
                var height = $('.bdc-table-zj').height();
                $(".tssfxx").css("padding-top", height + 91);
                var lcsl = 0;
                var hj = 0.00;
                var lcslbh = "";
                if(jfData.content != null &&jfData.content.length >0){
                    lcsl = jfData.lcsl;
                    hj= jfData.hj;
                }
                if(isNotBlank(jfData.lcslbh)){
                    lcslbh = jfData.lcslbh;
                }
                $("#lcCount").text(lcsl);
                $("#sumhj").text(hj);
                $("#lcslbh").text(lcslbh);
                setHeight();
                reverseTableCell(reverseList, "xwqyTable");
            }
        });

        /**
         * 点击查询
         */
        $('#search').on('click', function () {
            search();
        });

        $('.slbh').on('click', function () {
            layer.open({
                title: '请输入受理编号',
                type: 1,
                area: ['960px', '360px'],
                btn: ['确定', '取消'],
                content: $('#bdc-popup-large') ,
                yes: function (index, layero) {
                    $("#slbh").val($("#slbhs").val());
                    layer.close(index);
                },
                btn2: function (index, layero) {},
                success: function () {
                    $("#slbhs").val($("#slbh").val());
                    $("#slbhs").focus();
                }
            });
        });

        $(document).keydown(function (event) {
            if (event.keyCode == "13") {
                if (isSearch) {
                    var slbh = $("#slbhs").val();
                    if (!isNullOrEmpty(slbh) && slbh.length !== (slbh.lastIndexOf(",") + 1)) {
                        slbh += ",";
                        $("#slbhs").val(slbh);
                        $("#slbh").val(slbh);
                    }
                }
            }
        });

        $("textarea").keydown(function (e) {
            if (e.keyCode == 13 && !e.shiftKey) {
                e.preventDefault();
            }
        });

        $('.bdc-table-box').on('mouseenter', 'td', function () {
            if ($(this).text() && reverseList.indexOf($(this).attr("data-field")) !== -1) {
                $(this).append('<div class="layui-table-grid-down"><i class="layui-icon layui-icon-down"></i></div>');
            }
            $('.layui-table-grid-down').on('click', function () {
                setTimeout(function () {
                    $('.layui-table-tips-main .bdc-table-date').html(reverseString($('.layui-table-tips-main .bdc-table-date').html()));
                }, 20);
            });
        });


        //查询
        function search() {
            // 判断必填条件
            var count = 0;
            $(".required").each(function (i) {
                if (!isNullOrEmpty($(this).val())) {
                    count += 1;
                }
            });

            if (0 === count) {
                ityzl_SHOW_WARN_LAYER("请输入必要查询条件")
                return false;
            }
            // 获取查询参数
            var slbh = $('#slbh').val();
            var qlrmc = $('#qlrmc').val();
            var qlrzjh = $('#qlrzjh').val();
            //判断受理编号和 权利人名称证件号
            if (isNullOrEmpty(slbh)) {
                if (isNullOrEmpty(qlrmc) && isNullOrEmpty(qlrzjh)) {
                    ityzl_SHOW_WARN_LAYER("请输入名称和证件号");
                    return false;
                }
            }
            var searchData = {};
            searchData.slbhList = trim(slbh.replace(/\，/g, ','));
            searchData.qlrmc = trim(qlrmc);
            searchData.qlrzjh = trim(qlrzjh);
            searchData.slkssj = $("#slkssj").val();
            searchData.sljssj = $("#sljssj").val();
            searchData.mhlx = $("input[name='mhlx']:checked").val();
            searchParam = searchData;
            addModel();
            loadTable("xwqyTable", requestUrl, searchData);
            return false;
        }

        if (!(navigator.appName == "Microsoft Internet Explorer" && navigator.appVersion.match(/9./i) == "9.")) {
            //监听input点击
            $('.layui-form-item .layui-input-inline').on('click', '.layui-icon-close', function () {
                $(this).siblings('.layui-input').val('');
                $(this).siblings().find('.layui-input').val('');
            });

            $('.layui-form-item .layui-input-inline .layui-input').on('focus', function () {
                $(this).siblings('.layui-icon-close').removeClass('bdc-hide');
                $(this).parents('.layui-input-inline').find('.layui-icon-close').removeClass('bdc-hide');
                $(this).siblings('.layui-edge').addClass('bdc-hide');
            }).on('blur', function () {
                var $this = $(this);
                setTimeout(function () {
                    $this.siblings('.layui-icon-close').addClass('bdc-hide');
                    $this.parents('.layui-input-inline').find('.layui-icon-close').addClass('bdc-hide');
                    $this.siblings('.layui-edge').removeClass('bdc-hide');
                }, 150)
            });
        }

        //渲染起止时间，限制截止时间大于开始时间
        lay('.test-item').each(function(){
            //初始化日期控件
            var startDate = laydate.render({
                elem: '#slkssj',
                type: 'datetime',
                trigger: 'click',
                done: function(value,date){
                    //选择的结束时间大
                    if($('#sljssj').val() != '' && !completeDate($('#sljssj').val(),value)){
                        $('#sljssj').val('');
                        $('.laydate-disabled.layui-this').removeClass('layui-this');
                        layer.msg('<img src="../../static/lib/bdcui/images/error-small.png" alt="">结束时间不能小于开始时间');
                    }
                    endDate.config.min ={
                        year:date.year,
                        month:date.month-1,
                        date: date.date
                    }
                }
            });
            var endDate = laydate.render({
                elem: '#sljssj',
                type: 'datetime',
                trigger: 'click'
            });

        });

    });

    // 监听表格头操作按钮
    table.on('toolbar(xwqyTable)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id);
        var data = checkStatus.data;
        switch (obj.event) {
            case 'del':
                del(data);
                break;
            case 'jmyy':
                jmyy(data);
                break;
            case 'plth':
                plth(data);
            case 'plzfjkm':
                plzfjkm(data);
                break;
        }
    });

});

function loadTable(tableId, url, param) {
    table.reload(tableId, {
        url: url,
        where: param,
        page: false,
        parseData: function (res) {
            jfData =res;
            return {
                code: res.code, //解析接口状态
                msg: res.msg, //解析提示文本
                count: res.totalElements, //解析数据长度
                data: res.content //解析数据列表
            }
        },
        done: function (res, curr, count) {
            currentPageData = res.data;
            reverseTableCell(reverseList, tableId);
            removeModal();
            setHeight();
            $("#slbh").focus();
            var lcsl = 0;
            var hj = 0.00;
            var lcslbh = "";
            if(jfData.content != null&&jfData.content.length >0){
                lcsl = jfData.lcsl;
                hj = jfData.hj;
            }
            if(isNotBlank(jfData.lcslbh)){
                lcslbh = jfData.lcslbh;
            }
            $("#lcCount").text(lcsl);
            $("#sumhj").text(hj);
            $("#lcslbh").text(lcslbh);
            if (res.code === 1) {
                var msg = isNotBlank(res.msg)? res.msg : "未查询到收费信息";
                layer.confirm(msg, {btn: '确认', title: '信息', area: '650px'}, function (index) {
                    layer.close(index);
                });
            }
        }
    });
}

function loadData() {
    $.ajax({
        url: getContextPath() + "/bdczd/sl?zdmc=jmyy",
        type: 'POST',
        dataType: 'json',
        async: false,
        success: function (data) {
            if (isNotBlank(data)) {
                zdList = data;
            }
        },
        error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });
}

function loadSearch() {
    var json = {
        slbhList: getSlbhQueryParam(),
    }
    var tpl = searchTpl.innerHTML;
    var view = document.getElementById('cxtj');
    //渲染数据
    laytpl(tpl).render(json, function (html) {
        view.innerHTML = html;
    });
    form.render();
}

//删除
function del(checkData){
    var ids = [];
    $.each(checkData, function (key, item) {
        ids.push(item.sfxxid);
    });
    addModel("删除中");
    getReturnData("/sf/sctsjfgx/" + processInsId, JSON.stringify(ids), "DELETE", function (data) {
        removeModal();
        ityzl_SHOW_SUCCESS_LAYER("删除成功");
        $("#slbh").val("");
        $("#slbhs").val("");
        $("#qlrmc").val("");
        $("#qlrzjh").val("");
        reloadTable();
    }, function (xhr) {
        removeModal();
        delAjaxErrorMsg(xhr);
    });
}
// 减免原因
function jmyy(checkData){
    if(checkData.length == 0){
        ityzl_SHOW_WARN_LAYER("请选择数据！");
        return;
    }
    var tpl = jmyyTpl.innerHTML, view = document.getElementById('bdc-popup-small-jmyy');
    laytpl(tpl).render({zd: zdList}, function (html) {
        view.innerHTML = html;
    });
    form.render();

    layer.open({
        title: '减免原因',
        type: 1,
        area: ['450px', '350px'],
        btn: ['确定', '取消'],
        content: $('#bdc-popup-small-jmyy'),
        success: function (){
          form.render("select");
        },
        yes: function (index, layero) {
            var jmyy = $("#jmyySel").val();
            var ids = [];
            $.each(checkData, function (key, item) {
                ids.push(item.sfxxid);
            });
            addModel();
            var sfxx = {
                sfxxidList: ids,
                jmyy: jmyy,
                gzlslid: processInsId,
            }
            $.ajax({
                url: getContextPath() + "/rest/v1.0/cz/sfxx/updateSfxxJmyy",
                type: 'POST',
                dataType: 'json',
                contentType: "application/json;charset=UTF-8",
                data: JSON.stringify(sfxx),
                success: function (data) {
                    removeModal();
                    layer.close(index);
                    reloadTable();
                },
                error: function (xhr, status, error) {
                    removeModal();
                    delAjaxErrorMsg(xhr)
                }
            });
        },
        btn2: function (index, layero) {
        }
    });
}

function plth(checkData){
    if(checkData.length == 0){
        ityzl_SHOW_WARN_LAYER("请选择数据！");
        return;
    }
    addModel("退回中");
    getReturnData("/sf/ylc/plth", {gzlslid: processInsId}, "GET", function (data) {
        removeModal();
        if (data) {
            var successMsg = isNotBlank(data.success) ? ("退回成功：" + data.success) : "";
            var failMsg = isNotBlank(data.fail) ? ("退回失败：" + data.fail) : "";
            var warnMsg = isNotBlank(data.warn) ? ("非收费节点未退回:" + data.warn) : "";
            showMsg(successMsg + failMsg + warnMsg);
        }
    }, function (xhr) {
        removeModal();
        showMsg((JSON.parse(xhr.responseText)).msg + "，详细查看系统日志");
    });
}
function plzfjkm(checkData){
    if(checkData.length == 0){
        ityzl_SHOW_WARN_LAYER("请选择数据！");
        return;
    }
    var slbh="";
    $.each(checkData, function (index, item) {
        if (item.sfzt === 2) {
            if(isNullOrEmpty(slbh)){
                slbh = item.slbh;
            }else{
                slbh += "，"+ item.slbh;
            }
        }
    });
    if (!isNullOrEmpty(slbh)) {
        ityzl_SHOW_WARN_LAYER('受理编号：' + slbh + '已缴费无法作废')
        return;
    }
    addModel();
    $.ajax({
        url: getContextPath() + "/rest/v1.0/cz/sfxx/ewm/delete/pl",
        type: 'POST',
        // dataType: 'json',
        contentType: "application/json;charset=UTF-8",
        data: JSON.stringify(checkData),
        async: false,
        success: function (data) {
            removeModal();
            if(isNotBlank(data)){
                showMsg(data);
            }else{
                ityzl_SHOW_SUCCESS_LAYER("作废成功");
                reloadTable();
            }
        },
        error: function (xhr, status, error) {
            removeModal();
            delAjaxErrorMsg(xhr);
        }
    });
}


function reloadTable() {
    loadTable("xwqyTable", requestUrl, {});
}

// 获取受理编号查询参数
function getSlbhQueryParam(){
    var slbh = "";
    if(isNotBlank(processInsId)){
        $.ajax({
            url: getContextPath() + "/rest/v1.0/cz/sfxx/getQueryParam/" + processInsId,
            type: 'GET',
            dataType: 'json',
            async: false,
            success: function (data) {
                if(isNotBlank(data) && isNotBlank(data.cxcs)){
                    var cxcs = JSON.parse(data.cxcs);
                    var slbhList = cxcs.slbh;
                    slbh = slbhList.join(",")
                }
            }
        });
    }
    return slbh;
}

// 去除空格
function trim(val){
    return val.replace(/\s*/g,"");
}

// 展示提示信息
function showMsg(msg){
    layer.confirm(msg, {btn: '确认', title: '信息', area: '650px'}, function (index) {
        layer.close(index);
    });
}

function completeDate(date1,date2){
    var oDate1 = new Date(date1);
    var oDate2 = new Date(date2);
    if(oDate1.getTime() >= oDate2.getTime()){
        return true;
    } else {
        return false;
    }
}