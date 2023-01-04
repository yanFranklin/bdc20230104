var gzlslid = $.getUrlParam("gzlslid");
var qlrlx = '1';
var searchData = [];
var key = "";
var jkname = $.getUrlParam("jkname");
var sfzjzl = [{"DM": "1", "MC": "内地居民身份证"}, {"DM": "2", "MC": "香港居民身份证"},
    {"DM": "3", "MC": "澳门居民身份证"}, {"DM": "4", "MC": "台湾居民身份证"},
    {"DM": "5", "MC": "护照"}, {"DM": "6", "MC": "军官证"},
    {"DM": "7", "MC": "士兵证"}, {"DM": "8", "MC": "其他有效国籍旅行证件"}];

/**
 * 配偶身份类型 字典
 */
var posflx = [{"DM": "1", "MC": "内地居民"}, {"DM": "2", "MC": "香港居民"},
    {"DM": "3", "MC": "澳门居民"}, {"DM": "4", "MC": "台湾居民"},
    {"DM": "5", "MC": "华侨或出国人员"}, {"DM": "6", "MC": "外国人"}];

/**
 * 婚姻登记类型 字典
 */
var hydjlx = [{"DM": "IA", "MC": "结婚登记类"}, {"DM": "IB", "MC": "离婚登记类"},
    {"DM": "ID", "MC": "撤销婚姻类"}, {"DM": "ICA", "MC": "补发结婚登记证类"},
    {"DM": "ICB", "MC": "补发离婚登记证类"}];
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
            var zjh = $(this).parents('.layui-form-item').find('#zjh').val();
            // 判空
            if (!isNotBlank(zjh)) {
                warnMsg('请输入必要查询条件');
                return false;
            }
            // 组织查询条件
            searchData = {
                "cxywcs": []
            };
            searchData.cxywcs.push({"zjh": zjh});

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
                        searchData = {
                            "cxywcs": []
                        };
                        for (var i = 0; i < data.length; i++) {
                            searchData.cxywcs.push({
                                "zjh": data[i].zjh
                            });
                        }
                    }
                    // 展示查询条件
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
            if (searchData.cxywcs && searchData.cxywcs.length > 0) {
                for (var i = 0; i < searchData.cxywcs.length; i++) {
                    // 获取查询参数
                    var param = {
                        cert_num: searchData.cxywcs[i].zjh
                    };
                    // 重新请求
                    $.ajax({
                        url: "/realestate-inquiry-ui/rest/v1.0/sjpt/hyxx" + "?qlrzjh=" + param.cert_num,
                        type: "GET",
                        contentType: 'application/json',
                        async: false,
                        success: function (data) {
                            if (data.code == 0) {
                                successMsg(data.msg);
                                if (data.content && data.content.length > 0) {
                                    for (var i = 0; i < data.content.length; i++) {
                                        data.content[i].zjh = param.cert_num;

                                        if (sfzjzl && data.content[i].ID_TYPE) {
                                            for (var index in sfzjzl) {
                                                if (sfzjzl[index].DM == data.content[i].ID_TYPE) {
                                                    data.content[i].ID_TYPE = sfzjzl[index].MC;
                                                }
                                            }
                                        } else {
                                            data.content[i].ID_TYPE = '';
                                        }

                                        if (data.content[i].SEX == '01') {
                                            data.content[i].SEX = '男';
                                        } else {
                                            data.content[i].SEX = '女';
                                        }

                                        if (sfzjzl && data.content[i].SPOUSE_CERT_TYPE) {
                                            for (var index in sfzjzl) {
                                                if (sfzjzl[index].DM == data.content[i].SPOUSE_CERT_TYPE) {
                                                    data.content[i].SPOUSE_CERT_TYPE = sfzjzl[index].MC;
                                                }
                                            }
                                        } else {
                                            data.content[i].SPOUSE_CERT_TYPE = '';
                                        }

                                        if (posflx && data.content[i].SPOUSE_ID_TYPE) {
                                            for (var index in posflx) {
                                                if (posflx[index].DM == data.content[i].SPOUSE_ID_TYPE) {
                                                    data.content[i].SPOUSE_ID_TYPE = posflx[index].MC;
                                                }
                                            }
                                        } else {
                                            data.content[i].SPOUSE_ID_TYPE = '';
                                        }

                                        if (hydjlx && data.content[i].OP_TYPE) {
                                            for (var index in hydjlx) {
                                                if (hydjlx[index].DM == data.content[i].OP_TYPE) {
                                                    data.content[i].OP_TYPE = hydjlx[index].MC;
                                                }
                                            }
                                        } else {
                                            data.content[i].OP_TYPE = '';
                                        }

                                        resultData.push(data.content[i]);
                                    }
                                }
                                dealCxjgxx("success", jkname);
                            } else {
                                warnMsg(data.message);
                            }

                            removeModal();
                        },
                        error: function (xhr, status, error) {
                            dealCxjgxx("fail", jkname);
                            removeModal();
                            delAjaxErrorMsg(xhr)
                        }
                    });
                }

                if (resultData.length > 0) {
                    //处理缓存数据
                    key = saveJkDataToRedis(resultData);
                    generateTable(resultData, true);
                }
            }


        }

        function generateTable(data, isSearch) {
            if (isSearch) {
                var getTpl = tableTpl.innerHTML;
            } else {
                var getTpl = cxywcsTpl.innerHTML;
            }

            laytpl(getTpl).render(data, function (html) {
                $('.bdc-table-box tbody').html(html);
            });
            $(document).find('input').each(function () {
                $(this).attr('title', $(this).val());
            });
            form.render();
        }

        //上传PDF
        $(".upload-pdf").click(function () {
            uploadPdf(gzlslid, "lcbjHyxxcx", "省民政厅-婚姻信息查询", key, "自然资源部");
        });
    });


});



