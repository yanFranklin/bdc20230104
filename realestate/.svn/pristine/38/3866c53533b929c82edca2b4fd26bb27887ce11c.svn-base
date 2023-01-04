/**
 * author: <a href="mailto:yaoyi@gtmap.cn>yaoyi</a>
 * version 1.0.0  2021/10/18
 * describe: 质检核查
 */
var laydate, form;
var djlx = $.getUrlParam("djlx");
var ywfl = getParam("ywfl");
var zdList = [];
var categoryProcess = {};
layui.config({
    base: '../../static/lib/form-select/'
}).extend({
    formSelects: 'formSelects-v4'
});
layui.use(['form', 'jquery', 'element', 'table', 'laydate','formSelects'], function () {
    var $ = layui.jquery,
        laydate = layui.laydate,
        table = layui.table,
        laytpl = layui.laytpl,
        form = layui.form,
        formSelects = layui.formSelects;


    $(function () {
        zdList = getMulZdList("djlx");

        //2.渲染起止时间，限制截止时间大于开始时间
        lay('.test-item').each(function () {
            //初始化日期控件
            var id = this.id;
            var startDate = laydate.render({
                elem: '#' + id ,
                format: 'yyyy-MM-dd HH:mm:ss',
                trigger: 'click',
                done: function (value, date) {
                    var jssjid = $(this)[0].elem[0].id.replace("kssj", "jssj");
                    //选择的结束时间大
                    if ($('#' + jssjid).val() != '' && !completeDate($('#'+ jssjid).val(), value)) {
                        $('#'+ jssjid).val('');
                        $('.laydate-disabled.layui-this').removeClass('layui-this');
                        layer.msg('<img src="../../static/lib/bdcui/images/error-small.png" alt="">结束时间不能小于开始时间');
                    }
                    endDate.config.min = {
                        year: date.year,
                        month: date.month - 1,
                        date: date.date
                    }
                }
            });
            var endId = id.replace("kssj", "jssj");
            var endDate = laydate.render({
                elem: '#'+ endId ,
                format: 'yyyy-MM-dd HH:mm:ss',
                trigger: 'click'
            });
        });

        table.on('tool(pageTable)', function (obj) {
            if (obj.event === 'zjDad') {
                openZjDad(obj.data);
            }
            if (obj.event === 'ywDad') {
                openYwDad(obj.data);
            }
        });
        //头工具栏事件
        table.on('toolbar(pageTable)', function (obj) {
            if (obj.event === 'plsc') {
                //批量生成质检
                plzj(obj);
            }
            if (obj.event === 'export') {
                exportZjxx(obj.config.data, obj.config.cols[0]);
            }
        });

        //查询条件
        generateCxtj();

        /**
         * 点击查询
         */
        $('#search').on('click', function () {
            // 初始化加载质检项目信息
            randomZjXmxx(false);
        });

        // 全部查询
        $('#allSearch').on('click', function () {
            randomZjXmxx(true);
        });
    });

    /**
     * 随机筛选 100 条质检项目信息
     * @param obj 查询条件
     */
    function randomZjXmxx(allsearch){
        //  获取查询参数
        var obj = {};
        obj["djlx"] = $("#djlx").val();
        if(isNullOrEmpty(obj["djlx"])){
            alertMsg("选择登记类型");
            return;
        }
        var ywfl = formSelects.value('ywflSelect', 'name');
        if(isNullOrEmpty(ywfl)){
            alertMsg("选择业务分类");
            return;
        }
        if(ywfl.length > 0){
            obj["ywfl"] = ywfl[0];
        }
        obj["gzldyids"] = formSelects.value('bhlcSelect', 'val');

        $(".search").each(function (i) {
            var value = $(this).val();
            var name = $(this).attr('name');
            if(isNotBlank(value)){
                obj[name] = value;
            }
        });
        obj.allsearch = allsearch;
        addModel();
        $.ajax({
            url: getContextPath()+"/rest/changzhou/zjhc/list/zjxmxx",
            type: 'POST',
            dataType: 'json',
            contentType: "application/json;charset=UTF-8",
            data: JSON.stringify(obj),
            success: function (data) {
                removeModal();
                loadTable(data);
            },
            error: function (xhr, status, error) {
                removeModal();
                delAjaxErrorMsg(xhr);
            }
        });
    }

    function loadTable(data){
        // 加载Table
        table.render({
            elem: "#pageTable",
            title: "质检核查",
            defaultToolbar: ['filter'],
            toolbar: "#toolbarDemo",
            even: true,
            page: false,
            limit: 9999,
            cols: [[
                {type: 'checkbox', fixed: 'left'},
                {field: 'slbh', minWidth: 100, title: '受理编号'},
                {field: 'gzldymc', minWidth: 200, title: '业务类别'},
                {field: 'qlr', minWidth: 100, title: '申请人'},
                {field: 'slsj', minWidth: 150, title: '受理时间'},
                {field: 'slr', minWidth: 100, title: '受理人'},
                {field: 'dbr', minWidth: 100, title: '登簿人'},
                {field: 'zl', minWidth: 250, title: '坐落'},
                {field: 'hdr', minWidth: 100, title: '质检人员'},
                {field: 'cz', title: '操作', width: 210, templet: '#zjhccz', align: "center", fixed:"right"}
            ]],
            data: data,
            page: false,
            done: function () {
                removeModal();
                $('.layui-table-tool-self').css('right', $('.bdc-export-tools').width() + 17 + 'px');
                if ($('.bdc-list-tab .layui-tab-content .layui-show .layui-table-main>.layui-table').height() == 0) {
                    $('.bdc-list-tab .layui-tab-content .layui-show .layui-table-body .layui-none').html('<img src="../static/lib/bdcui/images/table-none.png" alt="">无数据');
                } else {
                    $('.bdc-list-tab .layui-tab-content .layui-show .layui-table-body').height($('.bdc-content-box').height() - 196 - $('.bdc-task-tab').innerHeight() - $('.bdc-list-tab .layui-show .bdc-search-box').height());
                    $('.bdc-list-tab .layui-tab-content .layui-show .layui-table-fixed .layui-table-body').height($('.bdc-content-box').height() - 196 - $('.bdc-task-tab').innerHeight() - $('.bdc-list-tab .layui-show .bdc-search-box').height() - 17);
                }
                var reverseList = ['zl'];
                reverseTableCell(reverseList,"pageTable");
            }
        });
    }

    //批量质检
    function plzj(obj){
        if (obj.config.data.length == 0) {
            warnMsg("请至少选择一条数据进行操作！")
            return false;
        }
        var list = [];
        $.each(obj.config.data, function (key, item) {
            list.push(item.xmid);
        });
        var data = {};
        data['xmids'] = list;
        //提交数据
        $.ajax({
            url: getContextPath() + "/rest/changzhou/zjhc/sc/plzj",
            type: 'POST',
            dataType: 'json',
            contentType: "application/json;charset=UTF-8",
            data: JSON.stringify(data),
            success: function (res) {
                console.info(res);
                //打开列表页面
                var url = getContextPath()+"/changzhou/zj/zjdxx.html?zjdbh=" + res.zjdbh ;
                layer.open({
                    type: 2,
                    area: ['100%', '100%'],
                    maxmin: true,
                    fixed: false, //不固定
                    content: url,
                    btnAlign: "c"
                });
            },
            error: function (xhr, status, error) {
                removeModal();
                delAjaxErrorMsg(xhr);
            }
        });
    }

    //生成查询条件
    function generateCxtj() {
        // 加载下拉登记小类
        if(isNotBlank(zdList.djlx)){
            $.each(zdList.djlx, function(index, item){
                $("#djlx").append('<option value="' +  item.DM + '">' + item.MC + '</option>');
            });
            form.render('select');
        }

        // 加载下拉区县代码
        getDataByAjax(getContextPath() + "/rest/changzhou/zjhc/getQxdm", '','GET',function (data){
            if(data){
                data.forEach(function (v) {
                    $("#qxdm").append('<option value="' +  v.XZDM + '">' + v.XZMC + '</option>');
                });
                form.render('select');
            }
        });

        //下拉
        getDataByAjax(getContextPath() + '/rest/v1.0/process/listAllCategoryProcess','','GET',function (data) {
            filterCategoryProcess(data.content);
            var ywflSelect = [];
            for(var key in categoryProcess){
                ywflSelect.push({"name": categoryProcess[key].name, "value": key});
            }
            formSelects.data('ywflSelect', 'local', {
                arr: ywflSelect
            });
        });

        formSelects.on('ywflSelect', function(id, vals, val){
            console.info(categoryProcess[val.value]);
            var nodeData = [];
            var processList = categoryProcess[val.value].processList;
            if(processList.length > 0){
                processList.forEach(function (process){
                    nodeData.push({"name": process.name, "value": process.processDefKey});
                });
                formSelects.data('bhlcSelect', 'local', {
                    arr: nodeData
                });
            }
        });
    }
});

function filterCategoryProcess(data){
    if(isNotBlank(data)){
        $.each(data, function(index, value){
            categoryProcess[value.id] =  {id: value.id, name: value.name, processList: value.processList};
            if(value.children.length > 0){
                filterCategoryProcess(value.children);
            }
        });
    }
}

function openZjDad(data){
    console.info(data);
    getZjid(data).done(function(res){
        var url = getContextPath()+"/changzhou/zj/zjqk.html?zjid=" + res ;
        layer.open({
            type: 2,
            area: ['960px', '575px'],
            fixed: false, //不固定
            content: url,
            btnAlign: "c"
        });
    });
}

function getZjid(data){
    var deferred = $.Deferred();
    if(!isNotBlank(data.zjid)){
        addModel();
        $.ajax({
            url: getContextPath() + "/rest/changzhou/zjhc/sc/zjqk",
            type: 'POST',
            dataType: 'json',
            contentType: "application/json;charset=UTF-8",
            data: JSON.stringify(data),
            success: function (res) {
                console.info(res);
                removeModal();
                deferred.resolve(res.zjid);
            },
            error: function (xhr, status, error) {
                removeModal();
                delAjaxErrorMsg(xhr);
                deferred.reject();
            }
        });
    }else{
        deferred.resolve(data.zjid);
    }
    return deferred;
}

function openYwDad(data){
    window.open("/realestate-portal-ui/view/new-page.html?name=" + data.gzlslid +"&type=list", data.slbh);
}

function exportZjxx(d, cols){
    if ($.isEmptyObject(d)) {
        warnMsg("没有可导出的数据！");
        return;
    }
    // 标题
    var showColsTitle = new Array();
    // 列内容
    var showColsField = new Array();
    // 列宽
    var showColsWidth = new Array();
    for (var index in cols) {
        if (cols[index].hide == false && cols[index].type != 'checkbox' && !cols[index].toolbar) {
            if(cols[index].field == 'cz'){
                continue;
            }
            showColsTitle.push(cols[index].title);
            showColsField.push(cols[index].field);
            if (isNullOrEmpty(cols[index].width)) {
                showColsWidth.push(30);
            } else {
                showColsWidth.push(parseInt(cols[index].width / 100 * 15));
            }
        }
    }

    // 设置Excel基本信息
    $("#fileName").val('质检信息导出表');
    $("#sheetName").val('质检信息导出表');
    $("#cellTitle").val(showColsTitle);
    $("#cellWidth").val(showColsWidth);
    $("#cellKey").val(showColsField);

    var data = new Array();
    $.each(d, function (key, value) {
        data.push(value);
    })
    for (var i = 0; i < data.length; i++) {
        data[i].xh = i + 1;
    }
    $("#data").val(JSON.stringify(data));
    $("#form").submit();
}

// 中文字符编码
function getParam(name){
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if(null != r){
        return decodeURIComponent(r[2]);
    }else{
        return null;
    }
}

function getDataByAjax(_path, _param, _type, _fn, async) {
    var _url = _path;
    var getAsync = false;
    if (async) {
        getAsync = async
    }
    $.ajax({
        url: _url,
        type: _type,
        async: getAsync,
        contentType: 'application/json;charset=utf-8',
        data: _param,
        success: function (data) {
            _fn.call(this, data, data);
        },
        error: function (err) {
            console.log(err);
        }
    });
}

function completeDate(date1, date2) {
    var oDate1 = new Date(date1);
    var oDate2 = new Date(date2);
    if (oDate1.getTime() > oDate2.getTime()) {
        return true;
    } else {
        return false;
    }
}