layui.config({
    base: '../../static/lib/form-select/' //此处路径请自行处理, 可以使用绝对路径
}).extend({
    formSelects: 'formSelects-v4'
});
var reverseList = ['zl'];
var searchParam = {};
var moduleCode = getQueryString("moduleCode");
var ymlx = getQueryString("ymlx");
var gzlslid = getQueryString("processInsId");
var processInsId = getQueryString("processInsId");
var zxlc = false;
var $, table, form, laytpl, formSelects;
var zdList = {};
var yhList = listBdcXtJg();
var jfData ={};
var cqUrl = getContextPath() + "/rest/v1.0/cz/sfxx/tsjf/slbh?gzlslid=" + gzlslid;
var dyaqUrl = getContextPath() + "/rest/v1.0/cz/sfxx/tsjf/dyaq?gzlslid=" + gzlslid;
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
    var currentPageData = [];
    $(function () {
        loadData();
        loadSearch();

        var tableData =[];
        var url = cqUrl;
        if (ymlx === "dyaq") {
            url =  dyaqUrl;
        }
        // 加载Table
        var col = [[
            {type: 'checkbox', width: 50, },
            {field: 'xh', type: 'numbers', width: 60, title: '序号'},
            {field: 'slbh', title: '受理编号', width: 130},
            {field: 'djyy', title: '登记原因', width: 160},
            {field: 'gzldymc', title: '流程名称', width: 160},
            {field: 'zl', title: '坐落', align: 'center', width: 280},
            {field: 'qlr', title: '权利人(产权批量)', align: 'center', width: 200},
            {field: 'qlrdlr', title: '代理人', align: 'center', width: 180},
            {field: 'jfje', title: '金额', align: 'center', width: 100},
            {field: 'sfztmc', title: '缴费状态', align: 'center', width: 100},
            {field: 'fph', title: '票号', width: 150}
        ]];

        if (ymlx === "dyaq") {
            col = [[
                {type: 'checkbox', width: 50},
                {field: 'xh', type: 'numbers', width: 60, title: '序号'},
                {field: 'slbh', title: '受理编号', width: 130},
                {field: 'djyy', title: '登记原因', width: 160},
                {field: 'gzldymc', title: '流程名称', width: 160},
                {field: 'zl', title: '坐落', align: 'center', width: 280},
                {field: 'qlr', title: '抵押权人', align: 'center', width: 200},
                {field: 'ywr', title: '抵押人(抵押批量)', align: 'center', width: 200},
                {field: 'qlrdlr', title: '代理人', align: 'center', width: 180},
                {field: 'jfje', title: '金额', align: 'center', width: 100},
                {field: 'sfztmc', title: '缴费状态', align: 'center', width: 100},
                {field: 'fph', title: '票号', width:150}
            ]];
        }
        table.render({
            elem: '#tssfxxTable',
            toolbar: "#toolbar",
            title: '推送收费台账',
            defaultToolbar: ["filter"],
            url: url,
            even: true,
            cols: col,
            data: tableData,
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
                if (ymlx !== "dyaq") {
                    $(".dyatslxr")[0].classList.add('bdc-hide');
                }
                setHeight();
                reverseTableCell(reverseList, "tssfxxTable");
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
                btn2: function (index, layero) {
                },
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
            if(isNotBlank(formSelects.value("dyaqr",'name')[0])){
                count += 1;
            }

            if (0 === count) {
                ityzl_SHOW_WARN_LAYER("请输入必要查询条件")
                return false;
            }

            // 获取查询参数
            var searchData = {};
            var url = "";
            var slbh = $('#slbh').val();
            if (ymlx === "cq") {
                // 获取查询参数
                //判断受理编号和 权利人名称证件号
                var qlrmc = $('#qlrmc').val();
                var qlrzjh = $('#qlrzjh').val();
                if (isNullOrEmpty(slbh)) {
                    if (isNullOrEmpty(qlrmc) && isNullOrEmpty(qlrzjh)) {
                        ityzl_SHOW_WARN_LAYER("请输入名称和证件号");
                        return false;
                    }
                }
                searchData.slbhList = trim($('#slbh').val().replace(/\，/g, ','));
                searchData.qlrmc = trim(qlrmc);
                searchData.qlrzjh = trim(qlrzjh);
                searchData.mhlx = $("input[name='mhlx']:checked").val();
                url = cqUrl;
            } else {
                var dyaqr = formSelects.value("dyaqr",'name')[0];
                if (isNullOrEmpty(slbh) && isNullOrEmpty(dyaqr)) {
                    ityzl_SHOW_WARN_LAYER('请输入抵押权人或受理编号查询!');
                    return false;
                }
                searchData.slbhList = trim($('#slbh').val().replace(/\，/g, ','));
                searchData.dyfs = trim($("#dyfs").val());
                if(isNotBlank(dyaqr)){
                    searchData.dyaqr =trim(dyaqr);
                }
                searchData.dlrmc = trim($("#dlr").val());
                searchData.mhlx = $("input[name='mhlx']:checked").val();
                searchData.slkssj = $("#slkssj").val();
                searchData.sljssj = $("#sljssj").val();
                url = dyaqUrl;
            }
            addModel();
            // 重新请求
            searchParam = searchData;
            loadTable("tssfxxTable", url, searchData);
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
    table.on('toolbar(tssfxxTable)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id);
        var data = checkStatus.data;
        switch (obj.event) {
            // 获取发票号
            case 'getFph':
                getFph();
                break;
            // 推送产权
            case 'push':
                if (ymlx === "dyaq") {
                    pushDyaq();
                } else {
                    pushCq();
                }
                break;
            // 查询缴费状态
            case 'jfcx':
                jfcx();
                break;
            case 'zfFph':
                //作废发票号
                zfFph();
                break;
            case 'jffs':
                jffs(data);
                break;
            case 'export':
                addModel("导出中");
                var data1 = layui.checked
                var checkStatus = table.checkStatus(obj.config.id);
                exportExcel(checkStatus.data, obj.config.cols[0]);
                break;
            case 'del':
                //作废发票号
                del(data);
                break;
            case 'printjfs':
                //打印缴费书
                var dylx = "jfspl";
                if (ymlx === "dyaq") {
                    dylx = "dyaqjfspl";
                }
                printjfs(dylx);
                break;
            case 'dyatslxr':
                //抵押推送联系人信息
                showDyatsQlrxx();
                break;
            case 'plzf':
                //批量转发流程
                plzf();
                break;
            case 'plth':
                //批量退回
                plth();
                break;
            case 'jfzt':
                jfzt(data);
                break;
            case 'revokeFph':
                revokeFph(data);
                break;
            case 'reCountSfxm':
                reCountSfxm(data);
                break;

        }
    });

});

//查询项目信息
function getBdcXm(xmid) {
    var xmxx = "";
    getReturnData("/slym/xm/listBdcXm", {xmid: xmid}, "GET", function (data) {
        if (data) {
            xmxx = data[0];
        }
    }, function (xhr) {
        delAjaxErrorMsg(xhr);
    }, false);
    return xmxx;
}

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
            if (ymlx === "dyaq") {
                $("#mainDiv")[0].classList.remove('bdc-line-search-container');
            }
            setHeight();
            $("#slbh").focus();
            var lcsl = 0;
            var hj = 0.00;
            var lcslbh = "";
            if(jfData.content != null&&jfData.content.length >0){
                lcsl =jfData.lcsl;
                hj=jfData.hj;
            }
            if(isNotBlank(jfData.lcslbh)){
                lcslbh = jfData.lcslbh;
            }
            $("#lcCount").text(lcsl);
            $("#sumhj").text(hj);
            $("#lcslbh").text(lcslbh);
            if (ymlx !== "dyaq") {
                $(".dyatslxr")[0].classList.add('bdc-hide');
            }
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
        url: getContextPath() + "/bdczd",
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
    if(yhList.length == 0){
        yhList = listBdcXtJg();
    }
    var json = {
        ymlx: ymlx,
        slbhList: getSlbhQueryParam(),
    }
    var tpl = searchTpl.innerHTML;
    var view = document.getElementById('cxtj');
    //渲染数据
    laytpl(tpl).render(json, function (html) {
        view.innerHTML = html;
    });

    formSelects.data('dyaqr', 'local', {
        arr: yhList
    });

    // 下拉搜索框显示选中值
    var $yhmcparent = $('#cxtj').find('select[lay-filter="dyaqr"]').parent();
    $yhmcparent.find('.xm-dl-input .xm-select-input').val($yhmcparent.find('.xm-select-title span font').text());
    //监听下拉框选择事件
    formSelects.on('dyaqr', function (id, vals, val, isAdd, isDisabled) {
        if (isAdd) {
            // 下拉搜索框显示选中值
            var $yhmcparent = $('#cxtj').find('select[lay-filter="dyaqr"]').parent();
            $yhmcparent.find('.xm-dl-input .xm-select-input').val($yhmcparent.find('.xm-select-title span font').text());
        }
    });

    form.render();
}

//推送产权
function pushCq() {
    addModel("推送中")
    getReturnData("/sf/tscq", {gzlslid: gzlslid, ignoreError: false}, "GET", function (data) {
        removeModal();
        if(data.success){
            ityzl_SHOW_SUCCESS_LAYER("推送成功");
            return;
        }
        if(data.fail.code == 1){
            chooseJfsewm(data);
        }else{
            showMsg("推送财政异常：" + data.fail.message);
        }
    }, function (xhr) {
        removeModal();
        showMsg("推送财政异常：" + (JSON.parse(xhr.responseText)).msg);
    });
}
// 选择需要作废的收费缴费书二维码
function chooseJfsewm(res){
    $(".jfsbm-chk").html("");
    $.each(res.data,function(index, val){
        $(".jfsbm-chk").append('<input type="checkbox" name="jfsbmchk" value="' + val.sfxxid +'" lay-skin="primary" title="' + val.slbh + '" >');
    });
    layer.open({
        title: '信息',
        type: 1,
        area: ['960px', '360px'],
        btn: ['作废', '批量作废', '继续推送','取消'],
        content: $('#bdc-popup-large-jfsbm') ,
        yes: function (index, layero) {  // 作废
            var sfxxData = [];
            $("input:checkbox[name='jfsbmchk']:checked").each(function () {
                sfxxData.push({
                    sfxxid:  $(this).val(),
                    slbh: $(this).attr("title"),
                });
            });
            if(sfxxData.length == 0){
                ityzl_SHOW_WARN_LAYER("至少选一条数据进行作废。");
                return;
            }
            layer.close(index);
            invalid(sfxxData);
        },
        btn2: function (index, layero) {  // 批量作废功能
            var sfxxData = [];
            $.each(res.data, function(index, val){
                sfxxData.push({
                    sfxxid:  val.sfxxid,
                    slbh: val.slbh,
                });
            });
            invalid(sfxxData);
            layer.close(index);
        },
        btn3: function (index, layero) { //继续推送功能
            layer.close(index);
            proceedPush();
        },
        btn4: function (index, layero) {
            layer.close(index);
        },
        success: function () {
            form.render();
        }
    });
}
// 继续推送
function proceedPush(){
    addModel();
    getReturnData("/sf/tscq", {gzlslid: gzlslid, ignoreError: true}, "GET", function (data) {
        removeModal();
        if(data.success){
            ityzl_SHOW_SUCCESS_LAYER("推送成功");
            return;
        }
        showMsg(data.fail.message + "，详细查看系统日志");
    }, function (xhr) {
        removeModal();
        showMsg((JSON.parse(xhr.responseText)).msg + "，详细查看系统日志");
    });
}

// 作废缴费书二维码
function invalid(data){
    addModel();
    $.ajax({
        url: getContextPath() + "/rest/v1.0/cz/sfxx/ewm/delete/pl",
        type: 'POST',
        // dataType: 'json',
        contentType: "application/json;charset=UTF-8",
        data: JSON.stringify(data),
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

//推送抵押权收费信息
function pushDyaq() {
    addModel("推送中")
    getReturnData("/sf/tsdyaq", {gzlslid: gzlslid}, "GET", function (data) {
        removeModal();
        if(data.success){
            ityzl_SHOW_SUCCESS_LAYER("推送成功");
            return;
        }
        if(data.fail.code == 1){
            chooseDyaqJfsewm(data);
        }else{
            showMsg("推送财政异常：" + data.fail.message);
        }
    }, function (xhr) {
        removeModal();
        showMsg("推送财政异常：" + (JSON.parse(xhr.responseText)).msg);
    })
}

function chooseDyaqJfsewm(res){
    $(".jfsbm-chk").html("");
    var slbhList = [];
    $.each(res.data,function(index, val){
        slbhList.push(val.slbh);
    });
    $(".jfsbm-chk").append('<div style="padding: 10px 10px;" class="layui-text">  ' + slbhList.join(", ") + '</div>' );
    layer.open({
        title: '信息',
        type: 1,
        area: ['960px', '350px'],
        btn: ['作废', '取消'],
        content: $('#bdc-popup-large-jfsbm') ,
        yes: function (index, layero) {
            layer.close(index);
            invalidDyapl();
        },
        btn2: function (index, layero) {
            layer.close(index);
        },
        success: function () {
            form.render();
        }
    });
}

// 抵押批量作废缴费书二维码
function invalidDyapl(){
    addModel();
    getReturnData("/rest/v1.0/cz/sfxx/ewm/delete/dyapl", {
        gzlslid: gzlslid,
    }, "GET", function (data) {
        removeModal();
        ityzl_SHOW_SUCCESS_LAYER("作废成功");
        reloadTable();
    }, function (xhr) {
        removeModal();
        delAjaxErrorMsg(xhr);
    });
}

//手动获取发票号
function getFph() {
    addModel("获取中");
    getReturnData("/sf/fph", {gzlslid: gzlslid, ymlx: ymlx}, "GET", function (data) {
        removeModal();
        ityzl_SHOW_SUCCESS_LAYER("获取成功");
        reloadTable();
    }, function (xhr) {
        removeModal();
        delAjaxErrorMsg(xhr);
    }, false);
}

//作废发票号
function zfFph() {
    //作废前判断是否是当前数据库中的fph ，接口获取的发票号不允许作废
    addModel("作废中");
    getReturnData("/sf/fph/zf", {gzlslid: gzlslid}, "GET", function (data) {
        removeModal();
        reloadTable();
    }, function (xhr) {
        removeModal();
        delAjaxErrorMsg(xhr);
    });
}

// 导出excel
function exportExcel(checkedData, cols) {
    if ($.isEmptyObject(checkedData)) {
        removeModal();
        warnMsg("请选择需要导出Excel的数据行!");
        return;
    }

    // 标题
    var showColsTitle = [];
    // 列内容
    var showColsField = [];
    // 列宽
    var showColsWidth = [5, 21, 19, 19, 35, 20, 20, 14];
    if (ymlx === "dyaq") {
        showColsTitle = ['序号','受理编号','登记原因','流程名称','坐落','抵押人(抵押批量)','代理人','金额'];
        showColsField = ['xh','slbh','djyy','gzldymc','zl','ywr','qlrdlr','jfje'];
    }else{
        showColsTitle = ['序号','受理编号','登记原因','流程名称','坐落','权利人(产权批量)','代理人','金额'];
        showColsField = ['xh','slbh','djyy','gzldymc','zl','qlr','qlrdlr','jfje'];
    }

    var data = new Array();
    $.each(checkedData, function (key, value) {
        if (value.jfje && value.jfje.indexOf("<br>") > -1) {
            value.jfje = value.jfje.replace(/<br>/g, "\n ")
        }
        data.push(value);
    });

    var sfxxzj = {xh: '', slbh: '笔数:' + $("#lcCount").text(), djyy: '', zl: '', qlr: '', ywr:'', qlrdlr: '总金额', jfje: $("#sumhj").text()};
    data.push(sfxxzj);
    for (var i = 0; i < data.length-1; i++) {
        data[i].xh = i + 1;
    }

    // 设置Excel基本信息
    $("#fileName").val('收费台账导出');
    $("#sheetName").val('收费台账明细');
    $("#cellTitle").val(showColsTitle);
    $("#cellWidth").val(showColsWidth);
    $("#cellKey").val(showColsField);
    $("#addBorder").val(true);
    $("#fontSize").val(13);
    $("#data").val(JSON.stringify(data));
    $("#form").submit();
    removeModal();
}

// 缴费方式修改
function jffs(checkData){
    if(checkData.length == 0){
        ityzl_SHOW_WARN_LAYER("请选择数据！");
        return;
    }
    var checkSfzt = false;
    $.each(checkData, function (key, item) {
        if(item.sfztmc == "已缴费"){
            checkSfzt = true;
            return false;
        }
    });
    if(checkSfzt){
        ityzl_SHOW_WARN_LAYER("请选择未缴费的数据！")
        return;
    }
//大弹出层
    var index = layer.open({
        title: '缴费方式',
        type: 1,
        area: ['450px', '200px'],
        btn: ['确定', '取消'],
        content: $('#bdc-popup-small')
        , yes: function (index, layero) {
            var jkfs = $("input[name='jffs']:checked").val();
            var ids = [];
            $.each(checkData, function (key, item) {
                ids.push(item.sfxxid);
            });
            addModel();
            $.ajax({
                url: getContextPath() + "/sf/updateJkfsBySfxxid/" + jkfs,
                type: 'POST',
                dataType: 'json',
                contentType: "application/json;charset=UTF-8",
                data: JSON.stringify(ids),
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
        }
        , btn2: function (index, layero) {
            //取消 的回调
        }
        , cancel: function () {
            //右上角关闭回调
            //return false 开启该代码可禁止点击该按钮关闭
        }
        , success: function () {
            $("#slbhs").focus();
        }
    });
}

//删除
function del(checkData){
    var checkSfzt = false;
    $.each(checkData, function (key, item) {
        if(item.sfztmc == "已缴费"){
            checkSfzt = true;
            return false;
        }
    });
    if(checkSfzt){
        ityzl_SHOW_WARN_LAYER("已缴费的不能删除！")
        removeModal();
        return;
    }
    var ids = [];
    $.each(checkData, function (key, item) {
        ids.push(item.sfxxid);
    });
    addModel("删除中");
    getReturnData("/sf/sctsjfgx/" + gzlslid, JSON.stringify(ids), "DELETE", function (data) {
        removeModal();
        ityzl_SHOW_SUCCESS_LAYER("删除成功");
        $("#slbh").val("");
        $("#slbhs").val("");
        $("#qlrmc").val("");
        $("#qlrzjh").val("");
        $("#dyfs").val("");
        formSelects.value("dyaqr",[]);
        $("#dlr").val("");
        reloadTable();
    }, function (xhr) {
        removeModal();
        delAjaxErrorMsg(xhr);
    });
}

// 打印方法
function printjfs(dylx) {
    startNewPrintmode(dylx, "all", "");
}

// 缴费查询
function jfcx() {
    addModel("查询中")
    getReturnData("/sf/tsjf/jfcx", {gzlslid: gzlslid}, "GET", function () {
        removeModal();
        reloadTable();
    }, function (xhr) {
        removeModal();
        showMsg((JSON.parse(xhr.responseText)).msg + "，详细查看系统日志");
    })
}

function showDyatsQlrxx() {
    //查询数据库的记录，有值赋值
    getReturnData("/sf/lctsjfgx", {gzlslid: gzlslid}, "GET", function (data) {
        if (data) {
            $("#lxrmc").val(data.lxrmc);
            $("#lxdh").val(data.lxdh);
        }
    }, function (xhr) {
        delAjaxErrorMsg(xhr)
    }, false);
    var index = layer.open({
        title: '抵押推送联系人',
        type: 1,
        area: ['700px', '200px'],
        btn: ['确定', '取消'],
        content: $('#bdc-popup-small-lxrxx'),
        yes: function (index, layero) {
            addModel();
            //填写的信息更新到数据库中，推送的时候用到
            var lxrmc = $("#lxrmc").val();
            var lxdh = $("#lxdh").val();
            getReturnData("/sf/dyats/lxrxx", {gzlslid: gzlslid, lxrmc: lxrmc, lxdh: lxdh}, "GET", function (data) {
                layer.close(index);
                removeModal();
            }, function (xhr) {
                removeModal();
                delAjaxErrorMsg(xhr);
            })
        }
        , cancel: function () {
            //右上角关闭回调
            //return false 开启该代码可禁止点击该按钮关闭
        }
    });
}

function reloadTable() {
    var url = "";
    if (ymlx === "dyaq") {
        url = dyaqUrl;
    } else {
        url = cqUrl;
    }
    searchParam = {};
    loadTable("tssfxxTable", url, searchParam);
}

//收费原流程批量转发
function plzf() {
    addModel("转发中");
    getReturnData("/sf/ylc/plzf", {gzlslid: gzlslid}, "GET", function (data) {
        removeModal();
        if (data) {
            var successMsg = isNotBlank(data.success) ? ("转发成功：" + data.success) : "";
            var failMsg = isNotBlank(data.fail) ? ("转发失败：" + data.fail) : "";
            var warnMsg = isNotBlank(data.warn) ? ("非收费节点未转发:" + data.warn) : "";
            showMsg(successMsg + failMsg + warnMsg);
        }
    }, function (xhr) {
        showMsg((JSON.parse(xhr.responseText)).msg + "，详细查看系统日志");
    });
}

function plth() {
    addModel("退回中");
    getReturnData("/sf/ylc/plth", {gzlslid: gzlslid}, "GET", function (data) {
        removeModal();
        if (data) {
            var successMsg = isNotBlank(data.success) ? ("退回成功：" + data.success) : "";
            var failMsg = isNotBlank(data.fail) ? ("退回失败：" + data.fail) : "";
            var warnMsg = isNotBlank(data.warn) ? ("非收费节点未退回:" + data.warn) : "";
            showMsg(successMsg + failMsg + warnMsg);
        }
    }, function (xhr) {
        showMsg((JSON.parse(xhr.responseText)).msg + "，详细查看系统日志");
    });
}

// 缴费状态
function jfzt(checkData){
    if(checkData.length == 0){
        layer.confirm("未勾选数据,是否修改所有数据？", {title:'提示'}, function(index){
            layer.close(index);
            showJfzt(checkData, true);
        });
    }else if(checkData.length > 100){
        layer.confirm("勾选数据超过100条,是否修改所有数据？", {
            btn: ['是', '否']
        }, function(index){
            layer.close(index);
            showJfzt(checkData, true);
        }, function(index){
            layer.close(index);
            showJfzt(checkData, false);
        });
    }else{
        showJfzt(checkData, false);
    }

    function showJfzt(checkData, updateAll){
        layer.open({
            title: '缴费状态',
            type: 1,
            area: ['450px', '200px'],
            btn: ['确定', '取消'],
            content: $('#bdc-popup-small-jfzt'),
            yes: function (index, layero) {
                var sfzt = $("input[name='jfzt']:checked").val();
                var ids = [];
                if(!updateAll){
                    $.each(checkData, function (key, item) {
                        ids.push(item.sfxxid);
                    });
                }
                addModel();
                $.ajax({
                    url: getContextPath() + "/sf/updateSfztBySfxxid/" + gzlslid  +"/" + sfzt,
                    type: 'POST',
                    dataType: 'json',
                    contentType: "application/json;charset=UTF-8",
                    data: JSON.stringify(ids),
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
            }, btn2: function (index, layero) {
            }
        });
    }

}

// 取消发票号
function revokeFph(checkData){
    if(checkData.length == 0){
        layer.confirm("未勾选数据,是否所有数据取消发票号？", {title:'提示'}, function(index){
            layer.close(index);
            revoke(checkData);
        });
    }else{
        revoke(checkData);
    }

    function revoke(checkData){
        var ids = [];
        $.each(checkData, function (key, item) {
            ids.push({sfxxid: item.sfxxid});
        });
        addModel();
        $.ajax({
            url: getContextPath() + "/rest/v1.0/cz/sfxx/qxfph/pl",
            type: 'POST',
            dataType: 'json',
            contentType: "application/json;charset=UTF-8",
            data: JSON.stringify(ids),
            success: function (data) {
                console.info(data);
                removeModal();
                if(isNotBlank(data)){
                    ityzl_SHOW_WARN_LAYER("取消发票号失败。");
                }else{
                    ityzl_SHOW_SUCCESS_LAYER("取消成功");
                    reloadTable();
                }
            },
            error: function (xhr, status, error) {
                removeModal();
                delAjaxErrorMsg(xhr);
            }
        });
    }
}

// 重新计算
function reCountSfxm(checkData){
    if (ymlx === "cq") {
        if(checkData.length == 0){
            warnMsg("至少选择一条数据");
            return;
        }
        var data = [];
        $.each(checkData,function(index, val){
            data.push({
                sfxxid: val.sfxxid
            });
        });
        addModel();
        $.ajax({
            url: getContextPath() + "/rest/v1.0/cz/sfxx/rechange/sfxxid",
            type: 'POST',
            dataType: 'json',
            contentType: "application/json;charset=UTF-8",
            data: JSON.stringify(data),
            success: function (data) {
                removeModal();
                ityzl_SHOW_SUCCESS_LAYER("重新计算成功");
                reloadTable();
            },
            error: function (xhr, status, error) {
                removeModal();
                delAjaxErrorMsg(xhr);
            }
        });
    } else {
        addModel();
        getReturnData("/rest/v1.0/cz/sfxx/clearTsid/" + gzlslid, {}, "GET", function (data) {
            removeModal();
            ityzl_SHOW_SUCCESS_LAYER("重新计算成功");
            reloadTable();
        }, function (xhr) {
            removeModal();
            delAjaxErrorMsg(xhr);
        });
    }
}

// 加载抵押权人信息
function listBdcXtJg() {
    var yhList = [];
    if (ymlx === "dyaq") {
        $.ajax({
            url: getContextPath() + "/slym/qlr/bdcxtjg",
            type: 'GET',
            dataType: 'json',
            data: {jglb: 1},
            async: false,
            success: function (data) {
                if (isNotBlank(data)) {
                    $.each(data, function (index, item) {
                        yhList.push({name: item.jgmc, value: item.jgmc});
                    });
                }
            }
        });
    }
    return yhList;
}

// 获取受理编号查询参数
function getSlbhQueryParam(){
    var slbh = "";
    if(isNotBlank(gzlslid)){
        $.ajax({
            url: getContextPath() + "/rest/v1.0/cz/sfxx/getQueryParam/" + gzlslid,
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