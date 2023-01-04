var gzlslid = getQueryString("processInsId");
var formStateId = getQueryString("formStateId");
var readOnly = getQueryString("readOnly");
var processInsId = getQueryString("processInsId");
var zxlc = "false";
var $, form, layer, element, table, laydate, laytpl, formselects;
layui.use(['form', 'jquery', 'laydate', 'element', 'layer', 'table', 'laytpl'], function () {
    $ = layui.jquery;
    form = layui.form;
    layer = layui.layer;
    element = layui.element;
    table = layui.table;
    laydate = layui.laydate;
    laytpl = layui.laytpl;

    $(function () {
        addModel("加载中");
        //获取数据
        loadJcgg();
    });
})

function loadJcgg() {
    getReturnData("/slym/jcgg", {gzlslid: gzlslid}, "GET", function (data) {
        data.gzlslid = gzlslid;
        valForm(data);
        removeModal();
    }, function (xhr) {
        delAjaxErrorMsg(xhr);
    })
}

function printJcgg(dylx) {
    printData(dylx);
}

function saveJcgg() {
    addModel("正在保存");

    var dataObj = {};
    var $elems = $('.jcgg');
    $elems.each(function(index, item){
        var id = $(item).attr('id');
        var val = $(item).text();
        dataObj[id] = val;
    });

    getReturnData("/slym/jcgg", JSON.stringify(dataObj), "POST", function (data) {
        removeModal();
        ityzl_SHOW_SUCCESS_LAYER("保存成功");
        //保存后重新加载公告信息
        loadJcgg();
    }, function (xhr) {
        ityzl_SHOW_WARN_LAYER("保存失败")
        delAjaxErrorMsg(xhr);
    });
}

function valForm(data) {
    var keys = Object.getOwnPropertyNames(data);

    keys.forEach(function (key, index) {
        if (!isNullOrEmpty(data[key])) {
            $('#' + key).text(data[key]);
        }
    });
    getStateById(readOnly, formStateId, "jcgg", "", "");
}