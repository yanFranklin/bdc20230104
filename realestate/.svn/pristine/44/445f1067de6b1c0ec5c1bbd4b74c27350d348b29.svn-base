var zdList = {};
$.ajax({
    url: "../zd/mul",
    dataType: "json",
    data: {
        zdDoNames: "SZdFwhxDO,SZdJczxcdDO,SZdFwytDO,SZdFwlxDO,SZdFwxzDO,SZdQtgsDO,SZdHxjgDO"
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
    $.each(zdList, function (key, value) {
        var tpl = $("#" + key + "Tpl").html();
        laytpl(tpl).render(value, function (html) {
            $("." + key).append(html);
        });
    });

    //form验证
    form.render();
    var fwZhsIndex = $("#fwZhsIndex").val();
    if (fwZhsIndex) {
        loadFwzhs();
    }
    function loadFwzhs() {
        var fwZhsIndex = $("#fwZhsIndex").val();
        //获取数据
        // loading加载
        addModel();
        $.ajax({
            url: "../fwzhs/querybyindex",
            dataType: "json",
            data: {
                fwZhsIndex: fwZhsIndex
            },
            success: function (data) {
                removeModal();
                //处理查询出来的数据
                writeFwzhsxx(data)
            },
            error: function (xhr, status, error) {
                delAjaxErrorMsg(xhr,this)
            }
        });
    }

    //提交表单
    form.on("submit(saveFwZhs)", function (data) {
        var postData = data.field;
        var fwHsIndex = $("#fwHsIndex").val();
        if (fwHsIndex) {
            // loading加载
            addModel();
            $.ajax({
                url: "../fwzhs/save",
                dataType: "json",
                data: postData,
                success: function (data) {
                    removeModal();
                    if (data && data.success) {
                        if (data.data) {
                            form.val("form", data.data);
                        }
                        refreshAndDeleteLater("提交成功",true,true);
                    } else {
                        layer.alert("提交失败");
                    }
                },
                error: function (xhr, status, error) {
                    delAjaxErrorMsg(xhr,this);
                }
            });
        } else {
            layer.message("户室主键为空，不能提交")
        }
        return false;
    })

    $("#jczhs").click(function () {
        var confirmIndex = layer.confirm('继承后将覆盖已有数据，是否继承？', function (index) {
            var fwHsIndex = $("#fwHsIndex").val();
            var fwZhsIndex = $("#fwZhsIndex").val();
            var hslx= $("#hslx").val();
            // loading加载
            addModel();
            $.ajax({
                url: "../fwzhs/extendfwhs",
                dataType: "json",
                data: {
                    fwZhsIndex: fwZhsIndex,
                    fwHsIndex: fwHsIndex,
                    hslx:hslx
                },
                success: function (data) {
                    removeModal();
                    layer.close(confirmIndex);
                    //处理查询出来的数据
                    writeFwzhsxx(data)
                },
                error: function (xhr, status, error) {
                    delAjaxErrorMsg(xhr,this)
                }
            });
        })
    })

    function writeFwzhsxx(data) {
        form.val("form", data)
    }
});
