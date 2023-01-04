/**
 * @author "<a href="mailto:yousiyi@gtmap.cn>yousiyi</a>"
 * @version 1.0, 2020/11/17
 * @description 省公安厅-公民基本信息在线比对
 */
// 查询参数
var gzlslid = $.getUrlParam("gzlslid");
var formStateId = $.getUrlParam("formStateId");
var qlrlx = '1';
var key = "";
var searchData = [];
var jkname = $.getUrlParam("jkname");
layui.use(['jquery', 'element', 'form', 'table', 'laydate'], function () {
    //获取字典
    var $ = layui.jquery;
    var form = layui.form;
    var laytpl = layui.laytpl;

    form.render();
    $(function () {

        getSearch();

        $('#search').on('click', function () {
            // 获取查询条件
            var name = $(this).parents('.layui-form-item').find('#name').val();
            var identityNumber = $(this).parents('.layui-form-item').find('#identityNumber').val();

            // 判空
            if (!isNotBlank(name) || !isNotBlank(identityNumber)) {
                warnMsg('请输入必要查询条件');
                return false;
            }

            // 组织查询条件
            var getData = [];
            getData.push({"name": name, "identityNumber": identityNumber});
            searchData = getData;

            // 查询条件不为空进行查询
            if (searchData && searchData.length > 0) {
                search();
            }
        });

        // 获取查询参数
        function getSearch() {
            $.ajax({
                url: "/realestate-inquiry-ui/rest/v1.0/gx/cxywcs/qlr",
                type: "POST",
                data: JSON.stringify({"gzlslid": gzlslid, "qlrlx": qlrlx}),
                contentType: 'application/json',
                success: function (data) {
                    removeModal();
                    if (data && data.length > 0) {
                        var getData = [];
                        for (var i = 0; i < data.length; i++) {
                            if (data[i].dlrmc && data[i].zjh) {
                                getData.push({
                                    "name": data[i].qlrmc,
                                    "identityNumber": data[i].zjh
                                });
                            }
                        }
                        searchData = getData;
                    }
                    // 查询条件不为空进行查询
                    if (searchData && searchData.length > 0) {
                        search();
                    }
                },
                error: function (xhr, status, error) {
                    removeModal();
                    delAjaxErrorMsg(xhr)
                }
            });
        }

        //查询
        function search() {
            addModel();
            // 重新请求
            var resultData = [];
            if (searchData && searchData.length > 0) {
                var searchObj = {};
                for (var i = 0; i < searchData.length; i++) {
                    // 获取查询参数
                    var cxywcs = {
                        "name": searchData[i].name,
                        "identityNumber": searchData[i].identityNumber,
                        "gzlslid": gzlslid
                    };
                    // 重新请求
                    $.ajax({
                        url: "/realestate-inquiry-ui/rest/v1.0/gx/sheng/czrkcx",
                        type: "POST",
                        data: JSON.stringify(cxywcs),
                        contentType: 'application/json',
                        async: false,
                        success: function (data) {
                            if (data && data.length > 0) {
                                for (var j = 0; j < data.length; j++) {
                                    resultData.push(data[j]);
                                }
                            }

                            dealCxjgxx("success", jkname);
                            removeModal();
                        },
                        error: function (xhr, status, error) {
                            dealCxjgxx("fail", jkname);
                            removeModal();
                            delAjaxErrorMsg(xhr)
                        }
                    });
                }
                //处理缓存数据
                if (resultData.length > 0) {
                    key = saveJkDataToRedis(resultData);
                }
                generateTable(resultData);
            }
            removeModal();
        }

        function generateTable(data) {
            $('#lcsbj').removeClass('layui-hide');
            var getTpl = cxywcsTpl.innerHTML;
            laytpl(getTpl).render(data, function (html) {
                $('.bdc-table-box tbody').html(html);
            });
            form.render();
        }

        //上传PDF
        $(".upload-pdf").click(function () {
            uploadPdf(gzlslid, "sjcxczrk", "常住人口查询", key, "省专线接口");
        });


    });

});



