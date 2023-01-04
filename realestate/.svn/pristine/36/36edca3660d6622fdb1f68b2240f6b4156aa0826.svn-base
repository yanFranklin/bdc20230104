var zdList = {}
$.ajax({
    url: "../zd/mul",
    dataType: "json",
    data: {
        zdDoNames: "SZdFwytDO,SZdQtgsDO,SZdBdcdyFwlxDO,SZdFwjgDO,SZdJzwztDO"
    },
    async: false,
    success: function (data) {
        zdList = $.extend({}, data)
    }
});
layui.use(['form', 'jquery', 'laytpl', 'element', 'laydate'], function () {
    var form = layui.form;
    var $ = layui.jquery;
    var laydate = layui.laydate;
    var laytpl = layui.laytpl;
    var element = layui.element;
    //处理列表选择
    var tpl = $("#DmMcTpl").html();
    if (tpl && zdList) {
        $.each(zdList, function (key, value) {
            laytpl(tpl).render(value, function (html) {
                $("." + key).append(html);
            });
        })
    }
    laydate.render({
        elem: '#jgrq'
    });
    laydate.render({
        elem: '#ztrq'
        , type: 'datetime'
    });
    //form初始化
    form.render();
    //表单初始化
    if ($("#fwDcbIndex").val()) {
        loadFwLjz();
    }
    //提交表单
    form.on("submit(saveInfo)", function (data) {
        var postData = data.field
        var url = "../ljz/insertorupdate";
        var formLayer = layer;
        if (parent.layer) {
            formLayer = parent.layer
        }
        $.ajax({
            url: url,
            dataType: "json",
            data: postData,
            success: function (data) {
                if (data && data.success) {
                    formLayer.msg("提交成功")
                    if (data.data) {
                        form.val("form", data.data)
                    }
                } else if (data && data.msg) {
                    formLayer.alert(data.msg)
                } else {
                    formLayer.alert("提交失败")
                }
            },
            error: function (xhr, status, error) {
                delAjaxErrorMsg(xhr,this)
            }
        });
        return false;
    })

    form.on('select(bdcdyfwlx)', function (data) {
        if (parent.changeTabShow) {
            parent.changeTabShow(data.value)
        }
        if (data.value != "2") {
            $("input[name='bdcdyh']").parent().parent().addClass("layui-hide")
            $("select[name='bdczt']").parent().parent().addClass("layui-hide")
            $("input[name='cb']").parent().parent().addClass("layui-hide")
        } else {
            $("input[name='bdcdyh']").parent().parent().removeClass("layui-hide")
            $("select[name='bdczt']").parent().parent().removeClass("layui-hide")
            $("input[name='cb']").parent().parent().removeClass("layui-hide")
        }
    });

    function writeFormxx(data) {
        form.val("form", data)
    }

    //页面入口
    function loadFwLjz() {
        //获取数据
        addModel();
        $.ajax({
            url: "../ljz/infoljz",
            dataType: "json",
            data: {
                fwDcbIndex: $("#fwDcbIndex").val()
            },
            success: function (data) {
                removeModal();
                layer.closeAll();
                //处理查询出来的数据
                writeFormxx(data)
            },
            error: function (xhr, status, error) {
                delAjaxErrorMsg(xhr,this)
            }
        });
    }
});