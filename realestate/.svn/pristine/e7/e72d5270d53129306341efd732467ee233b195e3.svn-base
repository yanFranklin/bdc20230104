var zdList = {};
var bgbh = $("#bgbh").val();
$.ajax({
    url: "../zd/mul",
    dataType: "json",
    data: {zdDoNames: "SZdFwlxDO,SZdFwxzDO"},
    async: false,
    success: function (data) {
        zdList = $.extend({}, data)
    },
    error: function (xhr, status, error) {
        delAjaxErrorMsg(xhr,this)
    }
});
layui.use(['form', 'jquery', 'laytpl', 'element', 'laydate'], function () {
    var form = layui.form;
    var $ = layui.jquery;
    var laytpl = layui.laytpl;
    //处理列表选择
    var tpl = $("#DmMcTpl").html();
    $.each(zdList, function (key, value) {
        laytpl(tpl).render(value, function (html) {
            $("." + key).append(html);
        });
    })

    //form初始化
    form.render();
    if ($("#fwXmxxIndex").val()) {
        loadFwXmxx();
    }
    //提交表单
    form.on("submit(saveInfo)", function (data) {
        var postData = data.field;
        //删除不动产单元号中的空格
        postData.bdcdyh = postData.bdcdyh.replace(/\s*/g,"");
        if (bgbh) {
            $.xmbg._xmxxJbxxBg(postData);
        }else{
            layer.alert("变更编号不能为空");
        }
        return false;
    })

    function writeFwXmxx(data) {
        if(data.bdcdyh){
            data.bdcdyh = splitBdcdyh(data.bdcdyh);
        }
        form.val("form", data)
    }

    //页面入口
    function loadFwXmxx() {
        //获取数据
        addModel();
        $.ajax({
            url: "../xmxx/infofwxmxx",
            dataType: "json",
            data: {
                fwXmxxIndex: $("#fwXmxxIndex").val()
            },
            success: function (data) {
                removeModal();
                layer.closeAll();
                //处理查询出来的数据
                writeFwXmxx(data)
            },
            error: function (xhr, status, error) {
                delAjaxErrorMsg(xhr,this)
            }
        });
    }
});