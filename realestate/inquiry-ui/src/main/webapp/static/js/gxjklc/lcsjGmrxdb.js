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
var searchData = {};
var jkname = $.getUrlParam("jkname");
var cxjgResult = [];
layui.use(['jquery', 'element', 'form', 'table', 'laydate'], function () {
    //获取字典
    var $ = layui.jquery;
    var form = layui.form;
    var laytpl = layui.laytpl;

    form.render();
    $(function () {

        getSearch();

        getStateById(false, formStateId, 'lcsjGmjbxx');

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
            var getData = {
                "paramDTOList": []
            };
            getData.paramDTOList.push({"name": name, "identityNumber": identityNumber});
            searchData = getData;
            var resultList = [];
            for (var i = 0; i < cxjgResult.length; i++) {
                if (cxjgResult[i].paramDTOList[0].name === searchData.paramDTOList[0].name && cxjgResult[i].paramDTOList[0].identityNumber === searchData.paramDTOList[0].identityNumber) {
                    resultList.push(cxjgResult[i]);
                }
            }
            // 展示查询条件，加载表格内容
            generateTable(resultList, true);
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
                            "paramDTOList": []
                        };
                        for (var i = 0; i < data.length; i++) {
                            getData.paramDTOList.push({"name": data[i].qlrmc, "identityNumber": data[i].zjh});
                            if (data[i].dlrmc && data[i].dlrzjh) {
                                getData.paramDTOList.push({
                                    "name": data[i].dlrmc,
                                    "identityNumber": data[i].dlrzjh
                                });
                            }
                        }

                        searchData = getData;
                        for (var i = 0; i < searchData.paramDTOList.length; i++) {
                            var cxjgMap = {};
                            cxjgMap.paramDTOList = [];
                            cxjgMap.paramDTOList.push(searchData.paramDTOList[i]);
                            cxjgMap.mc = ''
                            cxjgMap.zjh = '';
                            cxjgMap.exist = '';
                            cxjgMap.verify = '';
                            cxjgResult.push(cxjgMap);
                            console.log(cxjgResult);
                        }


                    }
                    // 展示查询条件，加载表格内容
                    generateTable(searchData, false);
                    // 查询条件不为空进行查询
                    // if (searchData &&searchData.paramDTOList &&searchData.paramDTOList.length >0) {
                    //     search();
                    // }
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
            $.ajax({
                url: "/realestate-inquiry-ui/rest/v1.0/gx/provincialPublicSecurityDepartment/policeRealName",
                type: "POST",
                data: JSON.stringify(searchData),
                contentType: 'application/json',
                success: function (data) {
                    dealCxjgxx("success", jkname);
                    key = saveJkDataToRedis(data);
                    removeModal();
                    // 结果数据不为空，重新加载表格
                    if (data && data.length > 0) {
                        // 将需要展示的查询条件加入结果数据
                        for (var i = 0; i < data.length; i++) {
                            for (var j = 0; j < searchData.paramDTOList.length; j++) {
                                if (data[i].gmsfhm == searchData.paramDTOList[j].identityNumber &&
                                    data[i].xm == searchData.paramDTOList[j].name) {
                                    if (data[i].paramDTOList == null) {
                                        data[i].paramDTOList = [];
                                    }
                                    data[i].paramDTOList.push(searchData.paramDTOList[j]);
                                }
                            }
                        }
                        generateTable(data, true);
                    }
                },
                error: function (xhr, status, error) {
                    dealCxjgxx("fail", jkname);
                    removeModal();
                    delAjaxErrorMsg(xhr)
                }
            });
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

        window.setCxjg = function (cxjg) {
            dealCxjgxx("success", jkname);
            var zjh = cxjg.zjh;
            //重新加载模板
            for (var j = 0; j < searchData.paramDTOList.length; j++) {
                for (var i = 0; i < cxjgResult.length; i++) {
                    if (cxjgResult[i].paramDTOList[0].name === searchData.paramDTOList[j].name && cxjgResult[i].paramDTOList[0].identityNumber === searchData.paramDTOList[j].identityNumber) {
                        if (cxjgResult[i].paramDTOList[0].name === cxjg.mc && cxjgResult[i].paramDTOList[0].identityNumber === cxjg.zjh) {
                            cxjgResult[i].mc = cxjg.mc;
                            cxjgResult[i].zjh = cxjg.zjh;
                            cxjgResult[i].exist = cxjg.exist;
                            cxjgResult[i].verify = cxjg.verify;
                        }
                    }
                }
            }
            var newCxjgResult = [];
            for (var k = 0; k < cxjgResult.length; k++) {
                var result = {};
                result.paramDTOList = [];
                result.paramDTOList.push(cxjgResult[k].paramDTOList[0]);
                result.mc = cxjgResult[k].mc;
                result.zjh = cxjgResult[k].zjh;
                if (cxjgResult[k].exist === "Y") {
                    result.exist = "是";
                } else {
                    result.exist = "否";
                }
                result.verify = cxjgResult[k].verify;
                newCxjgResult.push(result);
            }
            key = saveJkDataToRedis(newCxjgResult);
            generateTable(cxjgResult, true);
        }

        //上传PDF
        $(".upload-pdf").click(function () {
            uploadPdf(gzlslid, "sjgmrxbd", "省公安厅-公民基本信息人像比对", key, "省专线接口");
        });


    });
});


function rxdb(zjh, mc) {
    //打开人像比对页面
    var param = {};
    param.url = getContextPath();
    param.mc = mc;
    param.zjh = zjh;
    var url = "/realestate-inquiry-ui/view/ocx/wjglq.html?paramJson=" + encodeURI(JSON.stringify(param));
    var index = layer.open({
        title: "文件管理器",
        type: 2,
        area: ['1150px', '600px'],
        content: url
    });
    layer.full(index);
}



