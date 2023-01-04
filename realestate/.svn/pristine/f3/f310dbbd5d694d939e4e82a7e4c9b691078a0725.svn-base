var fwHsIndex = $("#fwHsIndex").val();
var bgbh = $("#bgbh").val();
var zdList = {};
$.ajax({
    url: "../zd/mul",
    dataType: "json",
    data: {
        zdDoNames: "SZdFwhxDO,SZdJczxcdDO,SZdFwytDO,SZdDldmDO,SZdTdsyqlxDO,SZdFwlxDO,SZdFwxzDO,SZdQtgsDO,SZdBoolDO,SZdHxjgDO,SZdGyfsDO,SZdQlrxzDO,SZdQlrxbDO,SZdZjllxDO"
    },
    async: false,
    success: function (data) {
        zdList = $.extend({}, data)
    }
});
layui.use(['form', 'jquery', 'laytpl', 'element', 'laydate', 'table', 'upload'], function () {
    var form = layui.form;
    var $ = layui.jquery;
    var laydate = layui.laydate;
    var laytpl = layui.laytpl;
    var element = layui.element;
    var table = layui.table;
    var upload = layui.upload;
    laydate.render({
        elem: '#qsrq'
    });
    laydate.render({
        elem: '#zzrq'
    });
    laydate.render({
        elem: '#dcsj'
        , type: 'datetime'
    });
    //处理列表选择
    var tpl = $("#DmMcTpl").html();
    $.each(zdList, function (key, value) {
        laytpl(tpl).render(value, function (html) {
            $("." + key).append(html);
        });
    });
    //form验证
    form.verify({
        double: function(value){
            if(value){
                var doubleReg = /^[0-9]+(.[0-9]{1,3})?$/;
                var intReg = /^[1-9]\d*$/;
                var r = doubleReg.test(value);
                var i = intReg.test(value);
                if(!r && !i){
                    return '输入正确数值';
                }
            }
        }
    });
    form.render();
    if (fwHsIndex) {
        loadFwhs(fwHsIndex);
    }
    //提交表单
    form.on("submit(saveForm)", function (data) {
        $.hsbg._fwhsBgMain(data);
        return false;
    });
    function writeFwhsxx(data) {
        form.val("form", data);
        if(data.fwDcbIndex){
            //根据逻辑幢类型判断是否显示不动产单元号
            $.ajax({
                url: "../ljz/infoljz",
                dataType: "json",
                data: {
                    fwDcbIndex: data.fwDcbIndex
                },
                success: function (data) {
                    if (data && data.bdcdyfwlx) {
                        bdcdyfwlx = data.bdcdyfwlx;
                        if (data.bdcdyfwlx == "4") {
                            $("input[name='bdcdyh']").parent().parent().removeClass("layui-hide")
                        }
                    }
                }
            });
        }
    }
    //页面入口
    function loadFwhs(fwHsIndex) {
        //获取数据
        addModel();
        $.ajax({
            url: "../fwhs/infofwhs",
            dataType: "json",
            data: {
                fwHsIndex: fwHsIndex
            },
            success: function (data) {
                removeModal();
                //处理查询出来的数据
                writeFwhsxx(data)
            },
            error: function (xhr, status, error) {
                delAjaxErrorMsg(xhr,this)
            }
        });
    }
});