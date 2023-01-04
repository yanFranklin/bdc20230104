var zdList = getSlMulZdList("jkfs");

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @description 发票号查看
 */
// 当前页数据
var currentPageData = new Array();
layui.use(['table', 'laytpl', 'laydate','element', 'layer', 'form'], function () {
    var $ = layui.jquery,
        table = layui.table,
        layer = layui.layer,
        laydate = layui.laydate,
        form = layui.form;
    var formSelects = layui.formSelects;
    var BASE_URL = '/realestate-inquiry-ui/rest/v1.0/fph';

// 获取银行列表
    $.ajax({
        url: BASE_URL + '/yhlb/data',
        type: "GET",
        dataType: "json",
        contentType: 'application/json',
        async: false,
        success: function (data) {
            if (!isEmptyObject(data)) {
                $.each(data, function (i, yhmc) {
                    $("#jfyh").append("<option value='" + yhmc + "'>" + yhmc + "</option>");
                })
            }
        }, error: function (e) {

        }
    });

    // 获取收费项目列表
    $.ajax({
        url: BASE_URL + '/sfxm/data',
        type: "GET",
        dataType: "json",
        contentType: 'application/json',
        async: false,
        success: function (data) {
            if (!isEmptyObject(data)) {
                $.each(data, function (i, sfxmmc) {
                    $("#sfxmmc").append("<option value='" + sfxmmc + "'>" + sfxmmc + "</option>");
                })
            }
        }, error: function (e) {

        }
    });
    $.ajax({
        url:'/realestate-inquiry-ui/bdczd/bm/list',
        type: "GET",
        dataType: "json",
        success: function (data) {
            if (data) {
                var listData = new Array();
                for (var key in data) {
                    var item = {"name":data[key],"value":key}
                    listData.push(item);
                }
                formSelects.data('selectBmmc', 'local', {
                    arr: listData
                });
            }
        }
    });
    layui.form.render("select");
    // 日期控件
    laydate.render({
        elem: '#kssj',
        format: 'yyyy-MM-dd',
        done: function (value, date, endDate) {
            var startDate = new Date(value).getTime();
            var endTime = new Date($('#jzsj').val()).getTime();
            if (endTime < startDate) {
                layer.msg('<img src="../../static/lib/bdcui/images/error-small.png" alt="">结束时间不能小于开始时间');
            }
        }
    });
    laydate.render({
        elem: '#jzsj',
        format: 'yyyy-MM-dd',
        done: function (value, date, endDate) {
            var startDate = new Date(value).getTime();
            var endTime = new Date($('#kssj').val()).getTime();
            if (endTime > startDate) {
                layer.msg('<img src="../../static/lib/bdcui/images/error-small.png" alt="">结束时间不能小于开始时间');
            }
        }
    });

    var cols = [ //表头
        {type: 'checkbox', fixed: 'left'},
        {field: 'sfxxid', title: 'ID', width: 50, hide: true},
        {field: 'fph', title: '发票号'},
        {field: 'hj', title: '金额合计小写'} ,
        {field: 'jfrxm', title: '缴费人姓名'},
        {field: 'zl', title: '坐落'},
        {field: 'sfrxm', title: '收款人名称'},
        {field: 'sfrzh', title: '收款人账号'},
        {field: 'kprxm', title: '开票人姓名'},
        {field: 'sfrkhyh', title: '收款人银行'},
        {
            field: 'sfztczsj', title: '收费日期', templet: function (d) {
                return Format(formatDate(d.sfztczsj), "yyyy-M-d");
            }, minWidth: 180
        },
        {field: 'jfsbm', title: '缴费书号'},
        {field: 'sfzt', title: '收费状态', templet: '#ztTpl'},
        {title: '操作', fixed: 'right', templet: '#barDemo'},
    ];
    /**
     * 加载Table数据列表
     */
    var reverseList = ['zl'];
    table.render({
        elem: '#fphListTable',
        toolbar: '#toolbar',
        title: '不动产收费信息列表',
        defaultToolbar: ['filter'],

        request: {
            limitName: 'size' //每页数据量的参数名，默认：limit
        },
        data: [],
        where: {},
        even: true,
        cols: [cols],
        text: {
            none: '未查询到数据'
        },
        page: true,
        limits: [10, 20, 50, 100, 200, 500],
        parseData: function (res) {
            if (res.content && res.content.length > 0) {
                var checkedData = layui.sessionData('checkedData');
                res.content.forEach(function (v) {
                    $.each(checkedData, function (key, value) {
                        if (key == v.sfxxid) {
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

    $('.bdc-table-box').on('mouseenter', 'td', function () {
        if ($(this).text() && reverseList.indexOf($(this).attr("data-field")) != -1) {
            $(this).append('<div class="layui-table-grid-down"><i class="layui-icon layui-icon-down"></i></div>');
        }
        $('.layui-table-grid-down').on('click', function () {
            setTimeout(function () {
                $('.layui-table-tips-main .bdc-table-date').html(reverseString($('.layui-table-tips-main .bdc-table-date').html()));
            }, 20);
        });
    });

    //点击高级查询-一般情况（非4的倍数）
    $('#seniorSearch').on('click',function () {
        $('.pf-senior-show').toggleClass('bdc-hide');
        $('.bdc-percentage-container').css('padding-top',$('.bdc-search-content').height() + 10);

        if($(this).html() == '高级查询'){
            $(this).html('收起');
        }else {
            $(this).html('高级查询');
        }

        if($('.bdc-table-box .layui-table-main>.layui-table').height() == 0){
            $('.layui-table-body .layui-none').html('<img src="../../static/lib/registerui/image/table-none.png" alt="">无数据');
        }else {
            $('.layui-table-body').height($('.bdc-table-box').height() - 131);
            $('.layui-table-fixed .layui-table-body').height($('.bdc-table-box').height() - 131 - 17);
        }
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
        for (var i = 0; i < getTd.length; i++) {
            reverseList.forEach(function (v) {
                if ($(getTd[i]).attr('data-field') == v) {
                    var getTdCell = $(getTd[i]).find('.layui-table-cell');
                    getTdCell.css('direction', 'rtl');
                    var tdHtml = reverseString(getTdCell.html());
                    // console.log(tdHtml);
                    getTdCell.html('<span class="bdc-table-date">' + tdHtml + '</span>');
                    // getTdCell.html('<span class="bdc-table-date">'+ getTdCell.html() +'</span>');
                }
            });
        }
    }

    /**
     * 监听排序事件
     */
    table.on('sort(fphListTable)', function (obj) {
        table.reload('fphListTable', {
            initSort: obj
            , where: {
                field: obj.field //排序字段
                , order: obj.type //排序方式
            }
        });
    });

    /**
     * 监听操作栏事件
     */
    table.on('tool(fphListTable)', function (obj) {
        layui.sessionData("bdcFpSfxxData", null);
        var data = obj.data;
        layui.sessionData('bdcFpSfxxData', {
           key: 'sfxx' , value: data
        });
        var index = layer.open({
            type: 2,
            title: "修改",
            area: ['960px', '460px'],
            fixed: false, //不固定bdcFzjl
            maxmin: true, //开启最大化最小化按钮
            content: "edit.html?sfxxid=" + data.sfxxid,
        });
    });

    /**
     * 点击查询
     */
    $('#search').on('click', function () {
        // 每次查询清除下导出缓存数据
        layui.sessionData("checkedData", null);
        // 获取查询内容
        var obj = {};
        $(".search").each(function (i) {
            var value = $(this).val();
            var name = $(this).attr('name');
            obj[name] = replaceBracket($.trim(value));
        });
        var departmentArr = layui.formSelects.value('selectBmmc', 'val');
        if (departmentArr.length>0){
            var data = {"Data":obj,"DepartmentList":departmentArr};
        }else {
            var data = {"Data":obj,"DepartmentList":null};
        }
        // 重新请求
        table.reload("fphListTable", {
            url: BASE_URL + '/page',
            method: 'post',
            contentType: 'application/json',
            where: data,
            page: {
                curr: 1  //重新从第 1 页开始
            },
            done: function (res, curr, count) {
                removeModal();
                if(res.code ==0){
                    currentPageData = res.data;
                    reverseTableCell(reverseList);
                    setHeight();
                }else{
                    $('.layui-table-body .layui-none').html('<img src="../../static/lib/bdcui/images/warn-small.png" alt="">' + res.msg);
                }
            }
        });
    });


    /**
     * 重新加载数据
     */
    window.reload = function () {
        var obj = {};
        $(".search").each(function (i) {
            var value = $(this).val();
            var name = $(this).attr('name');
            obj[name] = replaceBracket($.trime(value));
        });
        table.reload("fphListTable", {
            where: obj
            , page: {
                curr: 1  //重新从第 1 页开始
            }
        });
    };


    form.on('select', function (data) {
        if ($(this).text() == "请选择") {
            $(this).parents('.layui-input-inline').find('.reseticon').hide();
        } else {
            $(this).parents('.layui-input-inline').find('.reseticon').show();
        }
    });
    $('.reseticon').on('click', function (item) {
        $(this).parent().find("option:contains('请选择')").attr("selected", "selected");
        $(this).hide();
        layui.form.render("select");
    })
    $('#reset').on('click', function (item) {
        $('.bdc-percentage-container').find('.layui-form')
            .find('select').find("option:eq(0)")
            .attr("selected", "selected");
        setTimeout($('.bdc-percentage-container').find('.layui-form')
            .find('select').parent().find('input').val(''), 100);
        $('.reseticon').hide();
    })

    // 监听表格操作栏按钮
    table.on('toolbar(fphListTable)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id);
        switch (obj.event) {
            case 'excel':
                exportExcel();
                break;
            case 'xml':
                exportXml();
                break;
            case 'yhexcel':
                exportYhExcel();
                break;
            case 'exportdrmb':
                scExport();
                break;
            case 'hzdc':
                hzdc();
                break;
            case 'mxdc':
                mxdc();
                break;
            case 'pldy':
                pldy();
                break;
        }
    });

    /**
     * @author <a href ="mailto:hanyi@gtmap.cn">hanyi</a>
     * @description 导出登记费excel
     */
    function exportExcel() {
        var obj = {};

        if (isNullOrEmpty($("#kssj").val())
            || isNullOrEmpty($("#jzsj").val())) {
            removeModal();
            warnMsg("请输入开始时间和结束时间！");
            return false;
        }
        obj["kssj"] = $("#kssj").val();
        obj["jzsj"] = $("#jzsj").val();
        var departmentArr = layui.formSelects.value('selectBmmc', 'val');
        if (departmentArr.length>0) {
            obj["DepartmentList"] = departmentArr;
        }else {
            obj["DepartmentList"] = null;
        }
        $.ajax({
            url: BASE_URL + '/excel/data',
            type: "POST",
            dataType: "json",
            data: JSON.stringify(obj),
            contentType: 'application/json',
            async: false,
            success: function (data) {
                checkedData = data;
            }, error: function (e) {

            }
        });

        // 标题
        var showColsTitle = ["", "发票号", "缴费日期", "金额", "付款人", "坐落", "义务人"];
        // 列内容
        var showColsField = ["xh", "fph", "sfztczsj", "hj", "jfrxm", "zl", "ywr"];
        // 列宽
        var showColsWidth = ["10", "20", "20", "20", "40", "40", "20"];

        var data = new Array();
        var hj = 0;
        $.each(checkedData, function (key, value) {
            hj += value.hj;
        });
        var objHj = new Object();
        objHj.fph = '（合计）';
        objHj.hj = hj;
        data.push(objHj);
        $.each(checkedData, function (key, value) {
            data.push(value);
        })
        for (var i = 1; i < data.length; i++) {
            data[i].xh = i;
            data[i].sfzt = formatSfzt(data[i].sfzt);
            data[i].sfztczsj = Format(formatDate(data[i].sfztczsj), "yyyy-M-d");
        }
        // 设置Excel基本信息
        $("#fileName").val('收费信息');
        $("#sheetName").val('非税单位收费明细表');
        $("#summaryContent").val('单位名称：南通市不动产登记中心,1');
        $("#cellTitle").val(showColsTitle);
        $("#cellWidth").val(showColsWidth);
        $("#cellKey").val(showColsField);
        $("#excelData").val(JSON.stringify(data));
        $("#excelForm").submit();
    }

    /**
     * @author <a href ="mailto:hanyi@gtmap.cn">hanyi</a>
     * @description 导出银行excel
     */
    function exportYhExcel() {
        var obj = {};

        if (isNullOrEmpty($("#kssj").val())
            || isNullOrEmpty($("#jzsj").val())
            || isNullOrEmpty($("#jfyh").val())) {
            removeModal();
            warnMsg("请输入开始时间、结束时间以及缴费银行！");
            return false;
        }
        obj["kssj"] = $("#kssj").val();
        obj["jzsj"] = $("#jzsj").val();
        obj["jfyh"] = $("#jfyh").val();

        var departmentArr = layui.formSelects.value('selectBmmc', 'val');
        if (departmentArr.length>0) {
            obj["DepartmentList"] = departmentArr;
        }else {
            obj["DepartmentList"] = null;
        }
        $.ajax({
            url: BASE_URL + '/excel/data/yh',
            type: "POST",
            dataType: "json",
            data: JSON.stringify(obj),
            contentType: 'application/json',
            async: false,
            success: function (data) {
                checkedData = data;
            }, error: function (e) {

            }
        });

        // 标题
        var showColsTitle = ["", "发票号", "缴费日期", "金额", "付款人", "坐落", "义务人"];
        // 列内容
        var showColsField = ["xh", "fph", "sfztczsj", "hj", "jfrxm", "zl", "ywr"];
        // 列宽
        var showColsWidth = ["10", "20", "20", "20", "40", "40", "20"];

        var data = new Array();
        var hj = 0;
        $.each(checkedData, function (key, value) {
            hj += value.hj;
        });
        var objHj = new Object();
        objHj.fph = '（合计）';
        objHj.hj = hj;
        data.push(objHj);
        $.each(checkedData, function (key, value) {
            data.push(value);
        })
        for (var i = 1; i < data.length; i++) {
            data[i].xh = i;
            data[i].sfzt = formatSfzt(data[i].sfzt);
            data[i].sfztczsj = Format(formatDate(data[i].sfztczsj), "yyyy-M-d");
        }
        // 设置Excel基本信息
        $("#fileName").val('银行收费信息');
        $("#sheetName").val('非税单位收费明细表');
        $("#summaryContent").val('单位名称：南通市不动产登记中心,1');
        $("#cellTitle").val(showColsTitle);
        $("#cellWidth").val(showColsWidth);
        $("#cellKey").val(showColsField);
        $("#excelData").val(JSON.stringify(data));
        $("#excelForm").submit();
    }

    function scExport(){

        //单选框弹出层
        layer.open({
            title: '单选框',
            type: 1,
            area: ['430px','180px'],
            btn: ['提交', '取消'],
            content: $('#bdc-popup-radio')
            ,yes: function(index, layero){
                exportDrmb($('#bdc-popup-radio input[name="exportType"]:checked').val());
                layer.close(index);
            }
            ,btn2: function(index, layero){
                layer.close(index);

            }
            ,cancel: function(){
                layer.closeAll();
            }
            ,success: function () {

            }
        });
    }

    /**
     * @author <a href ="mailto:hanyi@gtmap.cn">hanyi</a>
     * @description 导出银行excel
     */
    function exportDrmb(exportType) {
        var checkedData;
        // 判断
        var count = 0;
        $(".search").each(function (i) {
            if (!isNullOrEmpty($.trim($(this).val()))) {
                count += 1;
            }
        });
        var departmentArr = layui.formSelects.value('selectBmmc', 'val');
        if (departmentArr.length>0) {
            count = 1;
        }
        if (0 == count) {
            warnMsg(" 请输入查询条件");
            return false;
        }

        if (isNullOrEmpty($("#kssj").val())
            || isNullOrEmpty($("#jzsj").val())) {
            removeModal();
            warnMsg("请输入开始时间和结束时间！");
            return false;
        }

        var obj = {};
        $(".search").each(function (i) {
            var value = $(this).val();
            var name = $(this).attr('name');
            obj[name] = replaceBracket($.trim(value));
        });
        obj["sfzt"] = 2;
        if (departmentArr.length>0) {
            obj["DepartmentList"] = departmentArr;
        }else {
            obj["DepartmentList"] = null;
        }

        // 导出当前用户的
        if(exportType == "currentUser"){
            if($("#sfxsjf").val() == "1"){
                warnMsg("该导出模式下,只会导出线上缴费的记录,请重新查询！");
                return false;
            }
            // 导出是否线上缴费字段为空的数据，不为空的不需要导出。
            obj["sfxsjf"] = 0;
        }
        // 导出所有用户的
        if(exportType == "allUser"){
            if($("#sfxsjf").val() == "0"){
                warnMsg("该导出模式下,只会导出非线上缴费的记录,请重新查询！");
                return false;
            }
            // 导出是否线上缴费字段为空的数据，不为空的不需要导出。
            obj["sfxsjf"] = 1;
        }
        obj["exportType"] = exportType;

        $.ajax({
            url: BASE_URL + '/excel/data/drmb',
            type: "POST",
            dataType: "json",
            data: JSON.stringify(obj),
            contentType: 'application/json',
            async: false,
            success: function (data) {
                checkedData = data;
            }, error: function (e) {

            }
        });

        // 标题
        var showColsTitle = ["序号", "票据号", "票据版本代码", "票据类型代码", "执收单位代码", "收款人全称"
            , "收款人账号", "收款人开户行", "缴款人名称", "缴款人账号", "缴款人开户行", "开票时间", "缴款方式"
            , "状态", "合计金额", "明细项目代码1", "明细项目金额1", "明细项目代码2", "明细项目金额2", "明细项目代码3"
            , "明细项目金额3", "明细项目代码4", "明细项目金额4", "备注", "POS设备信息"];
        // 列内容
        var showColsField = ["xh", "fph", "pjbbdm", "pjlxdm", "sfdwdm", "sfrxm", "sfrzh", "sfrkhyh", "jfrxm", "jkrzh"
            , "jkrkhh", "sfsj", "jkfs", "zt", "hj", "sfxmdm1", "ssje1", "sfxmdm2", "ssje2", "sfxmdm3", "ssje3"
            , "sfxmdm4", "ssje4", "bz", "possbxx"];
        // 列宽
        var showColsWidth = ["10", "20", "20", "20", "20", "20", "20", "20", "20", "20", "20", "20", "20"
            , "20", "20", "20", "20", "20", "20", "20", "20", "20", "20", "20", "20"];

        var data = new Array();
        $.each(checkedData, function (key, value) {
            data.push(value);
        });
        // 组织导出数据
        for (var i = 0; i < data.length; i++) {
            data[i].xh = i + 1;
            data[i].pjbbdm = 'fs01';
            data[i].pjlxdm = 'fs01';
            data[i].jkfs = convertZdDmToMc("jkfs", data[i].jkfs, 'zdList');
            data[i].zt = '正常';
            data[i].sfsj = Format(formatDate(data[i].sfsj), "yyyy-M-d");
            for (var j = 0; j < data[i].bdcSlSfxmVOList.length; j++) {
                data[i]["sfxmdm" + (j + 1).toString()] = data[i].bdcSlSfxmVOList[j].sfxmdm;
                data[i]["ssje" + (j + 1).toString()] = data[i].bdcSlSfxmVOList[j].ssje;
            }
        }
        // 设置Excel基本信息
        $("#fileName").val('非税收入批量导入模板' + $("#kssj").val());
        $("#sheetName").val('非税收入批量导入模板');
        $("#summaryContent").val('');
        $("#cellTitle").val(showColsTitle);
        $("#cellWidth").val(showColsWidth);
        $("#cellKey").val(showColsField);
        $("#excelData").val(JSON.stringify(data));
        $("#excelForm").submit();
    }

    /**
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 导出xml
     */
    function exportXml() {
        var count = 0;
        $(".search").each(function (i) {
            if (!isNullOrEmpty($.trim($(this).val()))) {
                count += 1;
            }
        });
        if (0 == count) {
            warnMsg(" 请输入查询条件");
            return false;
        }
        layer.confirm('确定要导出当前查询条件下当前用户开票的所有数据吗？', function (index) {
            var obj = {};
            $(".search").each(function (i) {
                var value = $(this).val();
                var name = $(this).attr('name');
                obj[name] = replaceBracket($.trim(value));
            });
            var departmentArr = layui.formSelects.value('selectBmmc', 'val');
            if (departmentArr.length>0) {
                obj["DepartmentList"] = departmentArr;
            }else {
                obj["DepartmentList"] = null;
            }

            var tempForm = $("<form>");
            tempForm.attr("id", "tempForm1");
            tempForm.attr("style", "display:none");
            tempForm.attr("target", "export");
            tempForm.attr("method", "post");
            tempForm.attr("action", '/realestate-inquiry-ui/rest/v1.0/fph/export/xml');

            var dataInput = $("<input>");
            dataInput.attr("type", "hidden");
            dataInput.attr("name", "queryParam");
            dataInput.attr("id", "queryParam");
            dataInput.attr("value", JSON.stringify(obj));
            tempForm.append(dataInput);

            tempForm.on("submit", function () {
            });
            tempForm.trigger("submit");
            $("body").append(tempForm);
            tempForm.submit();
            $("tempForm1").remove();

            layer.close(index);
        });

    }

    /**
     * 汇总导出
     */
    function hzdc() {
        var kssj = $("#kssj").val();
        var jzsj = $("#jzsj").val();
        var departmentArr = layui.formSelects.value('selectBmmc', 'val');
        if(isNullOrEmpty(kssj) || isNullOrEmpty(jzsj)) {
            warnMsg("请选择开始时间、结束时间！");
            return;
        }

        var xxhzForm = $("<form>");
        xxhzForm.attr("id", "xxhzForm");
        xxhzForm.attr("style", "display:none");
        xxhzForm.attr("target", "export");
        xxhzForm.attr("method", "post");
        xxhzForm.attr("action", '/realestate-inquiry-ui/rest/v1.0/fph/hzjfsj');

        var items = ["kssj", "jzsj", "djbmdmList"];
        for(var index in items) {
            var dataInput = $("<input>");
            dataInput.attr("type", "hidden");
            dataInput.attr("name", items[index]);
            dataInput.attr("value", function () {
               if("kssj" == items[index]) {
                    return kssj;
                } else if("jzsj" == items[index]) {
                    return jzsj;
                }else if("djbmdmList" == items[index]){
                   return departmentArr;
               }
            });
            xxhzForm.append(dataInput);
        }

        xxhzForm.trigger("submit");
        $("body").append(xxhzForm);
        xxhzForm.submit();
        $("#xxhzForm").remove();
    }

    /**
     * 明细导出
     */
    function mxdc() {
        var djbmdmList = layui.formSelects.value('selectBmmc', 'val');
        if (!djbmdmList ||  djbmdmList.length == 0){
            warnMsg("请选择部门！");
            return;
        }

        var kssj = $("#kssj").val();
        var jzsj = $("#jzsj").val();

        if(isNullOrEmpty(kssj) || isNullOrEmpty(jzsj)) {
            warnMsg("请选择开始时间、结束时间！");
            return;
        }

        addModel();
        $.ajax({
            url: '/realestate-inquiry-ui/rest/v1.0/fph/mxjfsjsl',
            type: "POST",
            dataType: "text",
            data: JSON.stringify({"DepartmentList":djbmdmList, "kssj": kssj, "jzsj": jzsj, "cxlx": "MXZS"}),
            contentType: 'application/json',
            success: function (data) {
                if(!data || 0 == data) {
                    warnMsg("未查询到明细数据，导出取消！");
                } else if(data > 10000){
                    warnMsg("当前查询数据记录过多，请缩短时间区间！");
                } else {
                    mxdcExcel(djbmdmList, kssj, jzsj);
                }
                removeModal();
            }, error: function (e) {
                warnMsg("导出明细失败，请重试！");
                removeModal();
            }
        });
    }

    /**
     * 明细导出Excel
     */
    function mxdcExcel(djbmdmList, kssj, jzsj) {
        var xxhzForm = $("<form>");
        xxhzForm.attr("id", "xxhzForm");
        xxhzForm.attr("style", "display:none");
        xxhzForm.attr("target", "export");
        xxhzForm.attr("method", "post");
        xxhzForm.attr("action", '/realestate-inquiry-ui/rest/v1.0/fph/mxjfsj');

        var items = ["djbmdmList", "kssj", "jzsj"];
        for(var index in items) {
            var dataInput = $("<input>");
            dataInput.attr("type", "hidden");
            dataInput.attr("name", items[index]);
            dataInput.attr("value", function () {
                if("djbmdmList" == items[index]) {
                    return djbmdmList;
                } else if("kssj" == items[index]) {
                    return kssj;
                } else if("jzsj" == items[index]) {
                    return jzsj;
                }
            });
            xxhzForm.append(dataInput);
        }

        xxhzForm.trigger("submit");
        $("body").append(xxhzForm);
        xxhzForm.submit();
        $("#xxhzForm").remove();
    }

    function formatSfzt(sfzt) {
        if (sfzt == 1) {
            return '未缴费';
        } else if (sfzt == 2) {
            return '已缴费';
        } else if (sfzt == 3) {
            return '部分缴费';
        } else {
            return '未缴费';
        }
    }


    // 监听复选框事件，缓存选中的行数据
    table.on('checkbox(fphListTable)', function (obj) {
        // 获取选中或者取消的数据
        var data = obj.type == 'one' ? [obj.data] : currentPageData;

        $.each(data, function (i, v) {
            if (obj.checked) {
                //.增加已选中项
                layui.sessionData('checkedData', {
                    key: v.sfxxid, value: v
                });
            } else {
                //.删除
                layui.sessionData('checkedData', {
                    key: v.sfxxid, remove: true
                });
            }
        });
    });
    /**
     * 批量打印
     */
    function pldy(){
        // 判断收费信息是否为线上缴费数据
        var sfxsjf = $("#sfxsjf").val();
        if("1"!= sfxsjf){
            warnMsg("请选择是线上缴费的收费信息。");
        }

        // 获取选择的收费信息数据
        var checkedData = layui.sessionData('checkedData');
        var data = new Array();
        $.each(checkedData, function(key,value){
            data.push(value);
        });
        if(data.length==0){
            warnMsg("至少选择一条收费信息。");
        }
        console.info(data);

        $.each(data,function(index, item){
            // sessionId为随意指定的uuid内容
            var billFormElectInvoiceTool = BillFormElectInvoiceTool.getInstance({
                url: '127.0.0.1:31018', svrRandom: '', sessionId: item.sfxxid
            });
            if(!billFormElectInvoiceTool.isConnect()){
                billFormElectInvoiceTool.connect();
            }

            // 判断是否开票，已开票直接下载发票pdf
            if(item.sfkp == "1"){
                downInvoice({billno : item.jfsbm});
            }else{
                queryUserAndDh(item).done(function(res){
                    var param = {
                        jfsbm: item.jfsbm,
                        dh: res.dh,
                        userName : res.userName
                    };
                    // 判断当前websocket连接状态
                    if(billFormElectInvoiceTool.isConnect()){
                        // 请求获取CA序列号
                        getCA(billFormElectInvoiceTool, param);
                    }else{
                        billFormElectInvoiceTool.onopen = function () {
                            getCA(billFormElectInvoiceTool, param);
                        };
                        billFormElectInvoiceTool.connect();
                    }
                });
            }

        });

    }
});

var serialNum = "";

function getCA(billFormElectInvoiceTool, param){
    if(isNotBlank(serialNum)){
        doAfterGetCA(billFormElectInvoiceTool, serialNum, param.jfsbm, param.dh);
    }else{
        // 请求获取CA序列号
        billFormElectInvoiceTool.takeCert(function(data){
            console.info("CA序列号："+ JSON.stringify(data));
            var cert = JSON.parse(data);
            if(cert["result"] == "failed"){
                removeModal();
                warnMsg("请插入CA后，在进行发票打印。");
                return;
            }
            if(cert["IdeaBank cKey 0"].length == 0){
                removeModal();
                warnMsg("未获取到CA序列号内容。");
                return;
            }
            serialNum =  cert["IdeaBank cKey 0"][0].Items.serialNumber;
            var ckeyUser = cert["IdeaBank cKey 0"][0].Items.CN;
            if(param.userName == ckeyUser){
                if(isNotBlank(serialNum)){
                    doAfterGetCA(billFormElectInvoiceTool, serialNum, param.jfsbm, param.dh);
                }else{
                    removeModal();
                    warnMsg("未获取到CA序列号内容。");
                }
            }else{
                removeModal();
                warnMsg("当前ca账户与登录账户不一致。");
            }
        });
    }
}

// 拥有证书号后的操作
function doAfterGetCA(billFormElectInvoiceTool, serialNum, jfsbm, mobile){
    getInvoiceInfo(jfsbm, serialNum, mobile).done(function (value) {
        // 对返回的票据信息进行签名加密
        billFormElectInvoiceTool.signData(value.invoiceData, function(signData){
            console.info("CA签名加密的invoiceData："+ JSON.stringify(signData));
            console.info(value);
            var signJson = JSON.parse(signData);
            if(isNotBlank(signJson.SignData)){
                value.signData = signJson.SignData;
                value.mobile = value.payerMobilePhone;
                value.billno = value.billNo;
                value.serialNumber = serialNum;
                // 生成电子发票
                generateInvoice(value).done(function(fpxx){
                    // 下载电子发票
                    removeModal();
                    downInvoice(value);
                });
            }else{
                removeModal();
                warnMsg("未获取到签名信息。");
            }
        });
    });
}

// 获取权利人联系电话
function queryUserAndDh(param){
    var deferred = $.Deferred();
    addModel();
    $.ajax({
        url: getContextPath() + "/rest/v1.0/fph/queryUserAndDh",
        type: "GET",
        data: {xmid: param.xmid, qlrlb: param.qlrlb},
        success: function (data) {
            removeModal();
            console.log(data);
            deferred.resolve(data);
        }, error: function (xhr, status, error) {
            removeModal();
            delAjaxErrorMsg(xhr)
            deferred.reject();
        }
    });
    return deferred;
}
// 获取票据信息
function getInvoiceInfo(jfsbm, caNo, mobile){
    var deferred = $.Deferred();
    $.ajax({
        url: getContextPath() + "/rest/v1.0/fph/getDzfpxx?beanName=invoiceBeforIssue",
        type: "POST",
        contentType: "application/json;charset=UTF-8",
        data: JSON.stringify({billno : jfsbm, serialNumber : caNo, mobile: mobile}),
        success: function (data) {
            if(isNotBlank(data) && isNotBlank(data.invoiceData)){
                deferred.resolve(data);
            }else{
                warnMsg("未获取到电子票据信息。");
                deferred.reject();
            }
        }, error: function (xhr, status, error) {
            removeModal();
            delAjaxErrorMsg(xhr);
            deferred.reject();
        }
    });
    return deferred;
}
// 生成票据
function generateInvoice(param){
    var deferred = $.Deferred();
    $.ajax({
        url: getContextPath() + "/rest/v1.0/fph/getDzfpxx?beanName=invoiceHandleIssue",
        type: "POST",
        contentType: "application/json;charset=UTF-8",
        data: JSON.stringify(param),
        success: function (data) {
            if(isNotBlank(data)){
                deferred.resolve(data);
            }else{
                warnMsg("未获取到电子票据信息。");
                deferred.reject();
            }
        }, error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr);
            deferred.reject();
        }
    });
    return deferred;
}
// 下载电子票据
function downInvoice(param){
    addModel();
    $.ajax({
        url: getContextPath() + "/rest/v1.0/fph/getDzfpxx?beanName=invoiceDownload",
        type: "POST",
        contentType: "application/json;charset=UTF-8",
        data: JSON.stringify(param),
        success: function (data) {
            removeModal();
            if(isNotBlank(data) && isNotBlank(data.invoiceData)){
                showFile(data.invoiceData, data.invoiceCode);
            }else{
                warnMsg("未获取到电子票据信息。");
            }
        }, error: function (xhr, status, error) {
            removeModal();
            delAjaxErrorMsg(xhr);
        }
    });
}

function showFile(data, code) {
    var dzhtnr = data.replace(/\s*/g,""),
        fileName = "",
        mime = "application/pdf";
    if(isNotBlank(code)){
        fileName = code+".pdf";
    }else{
        fileName = "电子发票.pdf";
    }
    var byte = base64ToByte(dzhtnr); // base64编码字符串转换二进制流
    if(isIE()){ //修复IE10无法使用Blob进行文件下载
        window.navigator.msSaveOrOpenBlob(new Blob([byte], { type: mime }),fileName);
    }else{
        var reader = new FileReader();
        var blob = new Blob([byte]);
        reader.readAsDataURL(blob);
        reader.onload = function (e) {
            // 转换完成，创建一个a标签用于下载
            var a = document.createElement('a');
            a.download = fileName;
            a.href = e.target.result;
            $("body").append(a);    // 修复firefox中无法触发click
            a.click();
            $(a).remove();
        }
    }
}

//判断是否IE浏览器
function isIE() {
    if (!!window.ActiveXObject || "ActiveXObject" in window) {
        return true;
    } else {
        return false;
    }
}

// base64解码转为二进制流
function base64ToByte(base64Str) {
    var decodeStr = atob(base64Str);
    var len = decodeStr.length;
    var byte = new Uint8Array(len);
    while(len--){
        byte[len] = decodeStr.charCodeAt(len);
    }
    return byte;
}

// 通过websocket调用，获取CA序列号
var BillFormElectInvoiceTool = (function(){
    function Singleton(options) {
        options = options || {};
        this.name = "BillFormElectInvoiceTool";
        this.url = options.url || '';
        this.sessionId = options.sessionId || '';
        this.socket = options.socket;
        this.isReconnect = false;
        this.messageSession = {};
    }

    Singleton.prototype.connect = function() {
        if (this.socket !== undefined) {
            return this;
        }
        var protocol = (window.location.protocol == 'http:') ? 'ws://' : 'ws://';
        this.host = protocol + this.url;

        window.WebSocket = window.WebSocket || window.MozWebSocket;
        if(!window.WebSocket) { // 检测浏览器支持
            this.error('浏览器不支持WebSocket，请使用谷歌4.0以上的浏览器');
            return;
        }
        var that = this;
        try {
            this.socket = new WebSocket(this.host); // 创建连接并注册响应函数
            this.socket.onopen = function(e) {
                that.onopen(e);
            };
            this.socket.onmessage = function(e) {
                that.onmessage(e);
            };
            this.socket.onclose = function(e) {
                that.onclose(e);
                that.socket = null; // 清理
            };
            this.socket.onerror = function(e) {
                that.onerror(e);
            };
            return this;
        } catch (e) {
            this.error('连接WebSocket失败!')
        }
    };

    Singleton.prototype.onopen = function (e) {};

    Singleton.prototype.onmessage = function (message) {
        var callback = this.messageSession[this.sessionId];
        if (callback) {
            callback(message.data);
        }
    };

    Singleton.prototype.onclose = function (e) {
        if(this.socket !== undefined && this.socket!= null) {
            this.socket.close();
        } else {
            this.error("WebSocket链接已失效");
        }
    };

    Singleton.prototype.onerror = function (e) {
        removeModal();
        console.log(e);
        warnMsg("连接Websocket服务器异常");
    };

    Singleton.prototype.error = function (errorMsg) {
        this.onerror(errorMsg);
    };

    Singleton.prototype.isConnect = function () {
        return this.socket && this.socket.readyState === 1;
    };

    Singleton.prototype.reconnect = function () {
        if (this.isReconnect) {
            return;
        }
        this.isReconnect = true;
        var that = this;
        setTimeout(function() {
            that.connect();
            that.isReconnect = false;
        }, 2000)
    };

    Singleton.prototype.send = function (message) {
        if (this.socket.readyState !== 1) {
            this.reconnect();
        }
        if(this.socket.readyState === 1) {
            this.socket.send(message);
            return true;
        }
        this.error('请先开启Websocket连接服务器');
        return false;
    };

    Singleton.prototype.signData = function (message, callback) {
        var data = '{"FuncName":"SignData_P7","Parames" :{"SignAlgType":"", "OriginalData":"'+message+'","OriginalDataType":""}}';
        this.send(data);
        this.messageSession[this.sessionId] = callback;
    };

    Singleton.prototype.takeCert = function (callback) {
        this.send('{"FuncName":"GetKeyCert"}');
        this.messageSession[this.sessionId] = callback;
    };

    var instance;
    return {
        name: "BillFormElectInvoiceTool",
        getInstance: function (options) {
            if (instance === undefined) {
                instance = new Singleton(options);
            } else {
                instance.connect(options);
            }
            return instance;
        }
    };
})();