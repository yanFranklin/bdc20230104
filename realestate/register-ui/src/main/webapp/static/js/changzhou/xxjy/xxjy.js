var $, form, table, element, laytpl, layer;
var gzlslid = getQueryString("processInsId");
var xmid = getQueryString("xmid");
var zsid = $.getUrlParam('zsid');
layui.use(['jquery', 'element', 'form', 'table', 'laytpl'], function () {
    $ = layui.jquery;
    form = layui.form;
    laytpl = layui.laytpl;
    table = layui.table;
    element = layui.element;
    layer = layui.layer;

    $(function () {
        addModel('加载中');
        var zsData ={};
        if (!isNullOrEmpty(sessionStorage.zsjyData)) {
            zsData = JSON.parse(sessionStorage.zsjyData);
        }
        var getData = getZsJyxx();


        function getZsJyxx() {
            getReturnData("/rest/v1.0/jyzsxx", {gzlslid: gzlslid, xmid: xmid,zsid:zsid,qlr:zsData.qlr}, "GET", function (data) {
                generateTable(data, true);
            }, function (xhr) {
                delAjaxErrorMsg(xhr)
            })
        }

        function generateTable(data, hasData) {
            if (hasData && data.zsid) {
                var getTpl = tableTpl.innerHTML
                    , view = document.getElementById('tableView');
                laytpl(getTpl).render(data, function (html) {
                    view.innerHTML = html;
                });
                form.render();
            } else {
                var getTpl = nodataTpl.innerHTML
                    , view = document.getElementById('tableView');
                laytpl(getTpl).render(data, function (html) {
                    view.innerHTML = html;
                });
            }
            removeModel();




        }


    });

});



