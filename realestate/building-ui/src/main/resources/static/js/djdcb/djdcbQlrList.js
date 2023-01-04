
var bdcdyh= $("#bdcdyh").val();
var zdList = {};
var zdparam = "SZdZjllxDO";
$.ajax({
    url: "../zd/mul",
    data: "zdDoNames=" + zdparam,
    dataType: "json",
    async: false,
    success: function (data) {
        zdList = $.extend({}, data);
    }
});


layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl'], function () {
    var tableConfig = {
        url: '../djdcb/qlr/listbypage?bdcdyh='+ bdcdyh //数据接口
        , cols: [[
            {field: 'qlr', title: '权利人'},
            {
                field: 'qlrzjlx', title: '权利人证件类型',
                templet: function (d) {
                    return convertZdDmToMc("SZdZjllxDO", d.qlrzjlx);
                }
            },
            {field: 'qlrzjh', title: '权利人证件号'}
        ]]
        ,page: false
    };

    //加载表格
    loadDataTablbeByUrl("#qlrList", tableConfig);
})