/**
 * @author "<a href="mailto:yousiyi@gtmap.cn>yousiyi</a>"
 * @version 1.0, 2020/11/17
 * @description 省公安厅-居民户成员信息在线查询
 */
// 查询参数
var gzlslid = $.getUrlParam("gzlslid");
var formStateId = $.getUrlParam("formStateId");
var qlrlx = '1';
var key = "";
var searchData = {};
var jkname = $.getUrlParam("jkname");
layui.use(['jquery', 'element', 'form', 'table', 'laydate'], function () {
    //获取字典
    var $ = layui.jquery;
    var form = layui.form;
    var laytpl = layui.laytpl;

    form.render();


    $(function () {

        //getSearch();

        getStateById(false, formStateId, 'lydjxxcx');

        $('#search').on('click', function () {
            // 获取查询条件
            var name = $(this).parents('.layui-form-item').find('#name').val();
            var cert_num = $(this).parents('.layui-form-item').find('#certNumber').val();
            var querytype = $(this).parents('.layui-form-item').find('#querytype').val();
            // 判空
            if (!isNotBlank(cert_num) || !isNotBlank(querytype)) {
                warnMsg('请输入必要查询条件');
                return false;
            }

            // 组织查询条件
            var getData = {
                "cxywcs": []
            };

            getData.cxywcs.push({
                "name": name,
                "cert_num": cert_num,
                "querytype": querytype});
            searchData = getData;

            // 展示查询条件，加载表格内容
            generateTable(searchData, false);

            // 查询条件不为空进行查询
            if (searchData && searchData.cxywcs && searchData.cxywcs.length > 0) {
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
                        var getData = {
                            "cxywcs": []
                        };
                        for (var i = 0; i < data.length; i++) {
                            getData.cxywcs.push({
                                "cert_num": data[i].zjh,
                                "name": data[i].qlrmc,
                                "querytype": "2"
                            });
                            if (data[i].dlrmc && data[i].dlrzjh) {
                                getData.cxywcs.push({
                                    "name": data[i].dlrmc,
                                    "cert_num": data[i].dlrzjh,
                                    "querytype": "2"
                                });
                            }
                        }

                        searchData = getData;

                    }
                    // 展示查询条件，加载表格内容
                    generateTable(searchData, false);
                    // 查询条件不为空进行查询
                    if (searchData && searchData.cxywcs && searchData.cxywcs.length > 0) {
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
            var resultData = [];

            addModel();
            if (searchData.cxywcs.length > 0) {
                var saveRedisData = [];
                for (var i = 0; i < searchData.cxywcs.length; i++) {
                    var result = {};
                    // 获取查询参数
                    var param = {
                        "cxywcs": [],
                    };
                    param.cxywcs.push({
                        cert_num: searchData.cxywcs[i].cert_num,
                        name: searchData.cxywcs[i].name,
                        querytype : searchData.cxywcs[i].querytype,
                    });

                    // 重新请求
                    $.ajax({
                        url: "/realestate-inquiry-ui/rest/v1.0/gx/gjpt/naturalResources/sydjxxcx",
                        type: "POST",
                        data: JSON.stringify(param),
                        contentType: 'application/json',
                        async: false,
                        success: function (data) {
                            removeModal();
                            console.log("前台返回数据：");
                            console.log(data);
                            if (data && data.length > 0) {
                                data[0].cert_num = param.cxywcs[0].cert_num;
                                data[0].name = param.cxywcs[0].name;
                                data[0].querytype = formatQueryType(param.cxywcs[0].querytype);
                                result = data[0];
                                saveRedisData = saveRedisData.concat(data);
                                resultData.push(result);
                            }else {
                                warnMsg('未查询到数据！');
                            }
                        },
                        error: function (xhr, status, error) {
                            dealCxjgxx("fail", jkname);
                            removeModal();
                            delAjaxErrorMsg(xhr)
                        }
                    });
                }
            }

            console.log(saveRedisData);
            key = saveJkDataToRedis(saveRedisData);
            removeModal();
            if (resultData.length > 0) {
                dealCxjgxx("success", jkname);
                console.log('resultData');

                console.log(resultData);
                generateTable(resultData, true);
            }
        }

        function generateTable(data, isSearch) {
            $('#lcsbj').removeClass('layui-hide');
            if (isSearch) {
                var getTpl = tableTpl.innerHTML;
            } else {
                var getTpl = cxywcsTpl.innerHTML;
            }

            laytpl(getTpl).render(data, function (html) {
                $('.bdc-table-box tbody').html(html);
            });
            form.render();
        }

        function formatQueryType(querytype) {
            switch (querytype) {
                case "2":
                    return "男收养人姓名和男收养人身份证号";
                case "3":
                    return "女收养人姓名和女收养人身份证号";
                case "4":
                    return "被收养人姓名和被收养人身份证号";
            }
        };

        //上传PDF
        $(".upload-pdf").click(function () {
            uploadPdf(gzlslid, "sydjxxcx", "省民政部-收养登记信息", key, "省专线接口");
        });


    });

});



