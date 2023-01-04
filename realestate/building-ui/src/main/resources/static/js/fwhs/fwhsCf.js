var fwHsIndex = $("#fwHsIndex").val();
var beforeFwhs = {}
var zdList = {}
$.ajax({
    url: "../fwhs/infofwhs",
    dataType: "json",
    async: false,
    data: {
        fwHsIndex: fwHsIndex
    },
    success: function (data) {
        beforeFwhs = $.extend({}, data)
    }
});
$.ajax({
    url: "../zd/mul",
    dataType: "json",
    data: {
        zdDoNames: "SZdFwhxDO,SZdJczxcdDO,SZdFwytDO,SZdDldmDO,SZdTdsyqlxDO,SZdFwlxDO,SZdFwxzDO,SZdQtgsDO,SZdBoolDO,SZdHxjgDO,SZdHbfxDO"
    },
    async: false,
    success: function (data) {
        zdList = $.extend({}, data)
    }
});
layui.use(['jquery', 'element', 'laytpl', 'form', 'laydate'], function () {
    var $ = layui.jquery;
    var element = layui.element;
    var laytpl = layui.laytpl;
    var form = layui.form;
    var laydate = layui.laydate;
    form.render();

    $("#sureCfhs").click(function () {
        $("#saveFwhss").click();
    })

    form.on('submit(cfbtn)', function (data) {
        if (beforeFwhs && beforeFwhs.fjh) {
            var fjhs = []
            var cfhsnum = data.field.cfhsnum;
            if (cfhsnum > 1) {
                if (data.field.cffx == "横向") {
                    for (var i = 0; i < cfhsnum; i++) {
                        fjhs.push({fjh: parseInt(beforeFwhs.fjh) + i})
                    }
                } else if (data.field.cffx == "纵向") {
                    for (var i = 0; i < cfhsnum; i++) {
                        fjhs.push({fjh: parseInt(beforeFwhs.fjh) + i * 100})
                    }
                }
            } else {
                layer.msg("拆分户室数必须大于1")
            }

            $("#fjhLi").html("");
            $("#fjhTab").html("");
            var tpl = $("#cfHsFjhTpl").html();
            laytpl(tpl).render(fjhs, function (html) {
                $("#fjhList").html(html);
            });
            $.each($("#fjhList a"), function (index, data) {
                $(this).click();
            })
        } else {
            layer.alert("当前房屋户室信息没有房间号，请检查")
        }
        return false;
    });

    form.on('submit(saveFwhss)', function (data) {
        if (beforeFwhs) {
            var postData = data.field;
            postData.bgbh = $("#bgbh").val();
            postData["yFwHsIndexList[0]"] = fwHsIndex;
            // loading加载
            addModel();
            $.ajax({
                url: "../fwhsbg/cf",
                dataType: "json",
                data: postData,
                success: function (data) {
                    removeModal();
                    if (data && data.success) {
                        refreshAndDelete(data.msg, true);
                    } else {
                        layer.alert("拆分失败")
                    }
                },
                error: function (xhr, status, error) {
                    delAjaxErrorMsg(xhr,this)
                }
            });
        } else {
            layer.alert("当前房屋户室信息没有房间号，请检查")
        }
        return false;
    });
})

function reloadNowFwHsInfo(fjh, index) {
    layui.use(['jquery', 'element', 'laytpl', 'form', 'laydate'], function () {
        var $ = layui.jquery;
        var element = layui.element;
        var laytpl = layui.laytpl;
        var form = layui.form;
        var laydate = layui.laydate;
        if ($("#" + fjh).length < 1) {
            var tplData = {
                fjh: fjh,
                length: index,
                fwhsInfo: beforeFwhs,
                zdList: zdList
            }
            var fjhLiTpl = $("#fjhLiTpl").html();
            laytpl(fjhLiTpl).render(tplData, function (html) {
                $("#fjhLi").append(html);
            });
            var fjhTabTpl = $("#fjhTabTpl").html();
            laytpl(fjhTabTpl).render(tplData, function (html) {
                $("#fjhTab").append(html);
            });
            laydate.render({
                elem: '#qsrq' + index
            });
            laydate.render({
                elem: '#zzrq' + index
            });
            form.render()
        }
        element.tabChange('tab', fjh);
    });
}