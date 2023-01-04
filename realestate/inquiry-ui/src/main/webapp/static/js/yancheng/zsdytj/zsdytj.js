// 盐城证书打印统计，增加部门和人员下拉框

layui.config({
    base: '../../static/lib/form-select/'
}).extend({
    formSelects: 'formSelects-v4'
});



var form;
var formSelects;


layui.use(['form', 'jquery', 'laydate', 'element', 'table','formSelects'], function () {
    var $ = layui.jquery,
        form = layui.form;
    formSelects = layui.formSelects;
    // initFormSelect(formSelects);
    $("#bm").attr("xm-select", "selectBmmc");
    $("#bm").attr("xm-select-skin", "default");
    $("#bm").attr("xm-select-show-count", "2");
    $("#bm").attr("xm-select-height", "34px");
    $("#bm").attr("xm-select-search", "");
    $("#bm").attr("xm-select-search-type", "dl");


    $("#bjry").attr("xm-select", "selectBjry");
    $("#bjry").attr("xm-select-skin", "default");
    $("#bjry").attr("xm-select-show-count", "2");
    $("#bjry").attr("xm-select-height", "34px");
    $("#bjry").attr("xm-select-search", "");
    $("#bjry").attr("xm-select-search-type", "dl");

    $(function (){
        getDataByAjax('/bdczd/childorg/list','','GET',function (data) {
            var bmData = [];
            data.forEach(function (v) {
                bmData.push({"name": v.name, "value": v.name,"id":v.id});
            });
            formSelects.data('selectBmmc', 'local', {
                arr: bmData
            });
            $("#bm").attr("value", "dl");
        });
        formSelects.on('selectBmmc',function(id, vals, val,isAdd ){
            var ryData = [];
            for (var index = 0; index < vals.length; index++) {
                getDataByAjax('/rest/v1.0/process/users/' + vals[index].id, '', 'GET', function (param) {
                    param.forEach(function (v) {
                        ryData.push({"name": v.alias, "value": v.alias});
                    });
                });
            }
            formSelects.data('selectBjry', 'local', {
                arr: ryData
            });
            $("#bjry").attr("value", "dl");
        },true);


    });
    $("#reset").click(function () {
        formSelects.render('selectBmmc', {
            init: []
        });
        formSelects.render('selectBjry', {
            init: []
        });
    });



});

function getDataByAjax(_path, _param,_type, _fn, async) {
    var _url = getContextPath() + _path;
    var getAsync = false;
    if(async){
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

