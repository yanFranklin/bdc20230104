var gzlslid = $.getUrlParam("gzlslid");
var qlrlx = '1';
var searchData = [];
var key = "";
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
            var mc = $(this).parents('.layui-form-item').find('#mc').val();

            // 判空
            if (!isNotBlank(mc)) {
                warnMsg('请输入必要查询条件');
                return false;
            }
            // 组织查询条件
            searchData = {
                "cxywcs": []
            };
            searchData.cxywcs.push({"mc": mc});

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
                                "mc": data[i].qlrmc
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
                        cn_name: searchData.cxywcs[i].mc
                    };
                    // 重新请求
                    $.ajax({
                        url: "/realestate-inquiry-ui/rest/v1.0/sjpt/shgx" + "?zzmc=" + param.cn_name,
                        type: "GET",
                        contentType: 'application/json',
                        async: false,
                        success: function (data) {
                            if (data.code == 0) {
                                successMsg(data.msg);
                                if (data.content && data.content.length > 0) {
                                    for (var i = 0; i < data.content.length; i++) {
                                        data.content[i].cn_name = param.cn_name;

                                        // 转化组织类型
                                        switch (data.content[i].SORG_TYPE) {
                                            case 'S': {
                                                data.content[i].SORG_TYPE = '社会团体';

                                                switch (data.content[i].SORG_KIND) {
                                                    case '1':
                                                        data.content[i].SORG_KIND = '学术性';
                                                    case '2':
                                                        data.content[i].SORG_KIND = '行业性';
                                                    case '3':
                                                        data.content[i].SORG_KIND = '专业性';
                                                    case '4':
                                                        data.content[i].SORG_KIND = '联合性';
                                                    default:
                                                        data.content[i].SORG_KIND = '';
                                                }
                                                break;
                                            }
                                            case 'M': {
                                                data.content[i].SORG_TYPE = '民办非企业';

                                                switch (data.content[i].SORG_KIND) {
                                                    case '1':
                                                        data.content[i].SORG_KIND = '法人';
                                                    case '2':
                                                        data.content[i].SORG_KIND = '合伙人';
                                                    case '3':
                                                        data.content[i].SORG_KIND = '个体';
                                                    default:
                                                        data.content[i].SORG_KIND = '';
                                                }
                                                break;
                                            }
                                            case 'J': {
                                                data.content[i].SORG_TYPE = '基金会';

                                                switch (data.content[i].SORG_KIND) {
                                                    case '1':
                                                        data.content[i].SORG_KIND = '公募';
                                                    case '2':
                                                        data.content[i].SORG_KIND = '非公募';
                                                    default:
                                                        data.content[i].SORG_KIND = '';
                                                }
                                                break;
                                            }
                                            default:
                                                data.content[i].SORG_TYPE = '';
                                        }

                                        // 转化社会组织类型


                                        resultData.push(data.content[i]);
                                    }
                                } else {
                                    warnMsg('未查询到结果!');
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
            uploadPdf(gzlslid, "lcbjShzzxxcx", "省民政厅-社会组织信息查询", key, "自然资源部");
        });
    });


});



