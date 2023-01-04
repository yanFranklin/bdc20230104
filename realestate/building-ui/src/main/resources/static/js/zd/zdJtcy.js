var $, form, element, table, laytpl, laydate;
var djh = getQueryString('djh');
var zdList = {};
$.ajax({
    url: "../zd/mul",
    dataType: "json",
    data: {
        zdDoNames: "SZdCbyhzgxDO,SZdQlrxbDO"
    },
    async: false,
    success: function (data) {
        zdList = $.extend({}, data)
    }
});
layui.use(['jquery', 'form', 'element', 'table', 'laytpl', 'laydate'], function () {
    var $ = layui.jquery,
        form = layui.form,
        element = layui.element,
        table = layui.table,
        laytpl = layui.laytpl,
        laydate = layui.laydate;

    if (!isNullOrEmpty(djh)) {
        $.ajax({
            url: '/building-ui/zdjtcy?djh=' + djh,
            type: "GET",
            async: false,
            dataType: "json",
            success: function (data) {
                if (data.length > 0) {
                    generateTable(data);
                } else {
                    generateTable([]);
                }
            }
        });
    }

    function generateTable(data) {
        var json ={
            zd:zdList,
            jtcy:data
        }
        var getTpl = tpl.innerHTML
            , view = document.getElementById('view');
        laytpl(getTpl).render(json, function (html) {
            view.innerHTML = html;
        });
        $("select").attr("disabled",true);
        form.render(null, 'zdJtcyForm');
        $("input").attr("readonly",true);
    }

});

//获取URL参数
function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (null !== r) {
        if (decodeURI(r[2]) !== "null") {
            return decodeURI(r[2]);
        } else {
            return null
        }
    }
    return null;
}