var table;
var form;
var processInsId = getQueryString("processInsId");

$(function () {

    layui.use(['form', 'jquery', 'laydate', 'element', 'table'], function () {
        table = layui.table;
        form = layui.form;

        addModel();
        getReturnData("/rest/v1.0/htbaxx/getSpfWqhtxx", {gzlslid: processInsId}, "GET", function (data) {
            removeModal();
            if (isNotBlank(data)) {
                renderWqxx(data);
            } else {
                ityzl_SHOW_WARN_LAYER("未获取到商品房买卖合同信息");
            }
        }, function (error) {
            delAjaxErrorMsg(error);
        });
        $('.printSpfwqht').on('click', function (data) {
            printSpfWqht('spfwqht');
        })
    });

});


function renderWqxx(data) {
    if (data && data.length == 1) {
        var wqxx = data[0];
        if (isNotBlank(wqxx.basj) && istime(wqxx.basj)) {
            wqxx.basj = Format(wqxx.basj, "yyyy年MM月dd日");
        }
        if (isNotBlank(wqxx.zzsyjzrq) && istime(wqxx.zzsyjzrq)) {
            wqxx.zzsyjzrq = Format(wqxx.zzsyjzrq, "yyyy年MM月dd日");
        }
        if (isNotBlank(wqxx.sysyjzrq) && istime(wqxx.sysyjzrq)) {
            wqxx.sysyjzrq = Format(wqxx.sysyjzrq, "yyyy年MM月dd日");
        }
        form.val("spfWqxxForm", wqxx);
    } else {
        var wqxx = data[0];
        if (isNotBlank(wqxx.basj) && istime(wqxx.basj)) {
            wqxx.basj = Format(wqxx.basj, "yyyy年MM月dd日");
        }
        if (isNotBlank(wqxx.zzsyjzrq) && istime(wqxx.zzsyjzrq)) {
            wqxx.zzsyjzrq = Format(wqxx.zzsyjzrq, "yyyy年MM月dd日");
        }
        if (isNotBlank(wqxx.sysyjzrq) && istime(wqxx.sysyjzrq)) {
            wqxx.sysyjzrq = Format(wqxx.sysyjzrq, "yyyy年MM月dd日");
        }
        layui.use(['form', 'jquery', 'laytpl', 'element', 'laydate'], function () {
            var form = layui.form;
            var laytpl = layui.laytpl;
            var json = {
                wqxx: data,
                ggxx: wqxx
            };
            var tpl = spfwqTpl.innerHTML;
            //渲染数据
            laytpl(tpl).render(json, function (html) {
                $('.layui-table').html(html);
            });
            form.render();
            renderDate(form);
        });
    }
}

function querySpfWqxx() {
    addModel();
    var htbh = $('#htbabm').val();
    //网签流程关系数据
    getReturnData("/rest/v1.0/htbaxx/wqlcgx/" + htbh, {gzlslid: processInsId}, "GET", function (data) {
        if (isNotBlank(data)) {
            renderWqxx(data);
        } else {
            ityzl_SHOW_INFO_LAYER("未获取到备案信息")
        }
        removeModal();
    }, function (xhr) {
        delAjaxErrorMsg(xhr);
    })
}