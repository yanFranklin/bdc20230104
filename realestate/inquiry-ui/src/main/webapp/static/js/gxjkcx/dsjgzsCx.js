/**
 * @author "<a href="mailto:wangyaqing@gtmap.cn>wangyaqing</a>"
 * @version 1.0, 2021/2/4
 * @description 建设工程规划许可信息查询
 */
// 查询参数
var gzlslid = $.getUrlParam("gzlslid");
var qlrlx = '1';
var formStateId = $.getUrlParam("formStateId");
var excludeQlrlx = '1';
var key = "";
var searchData = {};
var jkname = $.getUrlParam("jkname");
var resultData = [];
layui.use(['jquery', 'element', 'form', 'table', 'laydate'], function () {

    var $ = layui.jquery;
    var form = layui.form;
    var laytpl = layui.laytpl;
    form.render();

    $(function () {
        getStateById(false, formStateId, 'lcsjDqjg');

        $('#search').on('click', function () {
            // 获取查询条件
            var xm = $(this).parents('.layui-form-item').find('#xm').val();
            var sfzhm = $(this).parents('.layui-form-item').find('#sfzhm').val();
            var gzsbh = $(this).parents('.layui-form-item').find('#gzsbh').val();
            var jgbm = $(this).parents('.layui-form-item').find('#jgbm').val();

            // 判空
            if (!isNotBlank(xm)) {
                warnMsg('请输入必要查询条件');
                return false;
            }
            if (!isNotBlank(sfzhm)) {
                warnMsg('请输入查询类型');
                return false;
            }
            if (!isNotBlank(gzsbh)) {
                warnMsg('请输入查询类型');
                return false;
            }
            if (!isNotBlank(jgbm)) {
                warnMsg('请输入查询类型');
                return false;
            }

            // 组织查询条件
            var getData = [];
            getData.push({
                "xm": xm,
                "sfzhm": sfzhm,
                "gzsbh": gzsbh,
                "jgbm": jgbm
            });
            searchData = getData;


            // 查询条件不为空进行查询
            if (searchData && searchData.length > 0) {
                search();
            }
        });

        //查询
        function search() {
            addModel();
            //循环调用
            if (searchData.length > 0) {
                resultData = [];
                for (var paramlength = 0; paramlength < searchData.length; paramlength++) {
                    var param = {
                        "baseParams": "<xm>" + searchData[paramlength].xm + "</xm>" +
                            "<sfzhm>" + searchData[paramlength].sfzhm + "</sfzhm>" +
                            "<gzsbh>" + searchData[paramlength].gzsbh + "</gzsbh>" +
                            "<jgbm>" + searchData[paramlength].jgbm + "</jgbm>"
                    };
                    // 重新请求
                    $.ajax({
                        url: "/realestate-inquiry-ui/rest/v1.0/gx/dsjgzs/xxcx",
                        type: "POST",
                        data: JSON.stringify(param),
                        contentType: 'application/json',
                        async: false,
                        success: function (data) {
                            // 结果数据不为空，重新加载表格
                            if (!isNullOrEmpty(data)) {
                                data.xm = searchData[paramlength].xm;
                                data.sfzhm = searchData[paramlength].sfzhm;
                                data.gzsbh = searchData[paramlength].gzsbh;
                                data.jgbm = searchData[paramlength].jgbm;
                                resultData.push(data);
                                $("#download-files").show();
                            } else {
                                $("#download-files").hide();
                            }
                        },
                        error: function (xhr, status, error) {
                            removeModal();
                            delAjaxErrorMsg(xhr);
                            $("#download-files").hide();
                        }
                    });
                }
                removeModal();
                if (resultData.length > 0) {
                    generateTable(resultData);
                }

            }

        }


        function generateTable(data) {
            $('#lcsbj').removeClass('layui-hide');
            var getTpl = tableTpl.innerHTML;

            laytpl(getTpl).render(data, function (html) {
                $('.bdc-table-box tbody').html(html);
            });
        }

        //文件下载--改为下载zip文件
        $("#download-files").click(function () {
            var pdfUrl = document.location.protocol + "//" + document.location.host +
                "/realestate-inquiry-ui/rest/v1.0/gx/ydgh/fjxz?xm=" + encodeURIComponent(searchData[0].xm)
                + "&sfzhm=" + encodeURIComponent(searchData[0].sfzhm) + "&gzsbh=" + encodeURIComponent(searchData[0].gzsbh)
                + "&jgbm=" + encodeURIComponent(searchData[0].jgbm);
            window.open(pdfUrl, "PDFDOWNLOAD" + searchData[0].gzsbh);
        });

    });

});