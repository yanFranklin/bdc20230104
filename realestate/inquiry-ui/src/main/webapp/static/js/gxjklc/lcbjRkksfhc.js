/**
 * @author "<a href="mailto:yousiyi@gtmap.cn>yousiyi</a>"
 * @version 1.0, 2020/11/17
 * @description 民政部-婚姻登记信息核验（个人）查询
 */
// 查询参数
var gzlslid = $.getUrlParam("gzlslid");
var qlrlx = '1';
var searchData = {};
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
            var xm = $(this).parents('.layui-form-item').find('#xm').val();
            var zjh = $(this).parents('.layui-form-item').find('#zjh').val();
            // 判空
            if (!isNotBlank(xm) || !isNotBlank(zjh)) {
                warnMsg('请输入必要查询条件');
                return false;
            }
            // 组织查询条件
            searchData = {
                "cxywcs": []
            };
            searchData.cxywcs.push({"xm": xm, "zjh": zjh});

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
                                "zjh": data[i].zjh,
                                "xm": data[i].qlrmc
                            });
                            if (data[i].dlrmc && data[i].dlrzjh) {
                                searchData.cxywcs.push({
                                    "zjh": data[i].dlrzjh,
                                    "xm": data[i].dlrmc
                                });
                            }
                        }
                        // 展示查询条件
                        generateTable(searchData, false);

                    }
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
                    // var xm = searchData.cxywcs[i].xm;
                    // var zjh = searchData.cxywcs[i].zjh;
                    var obj = [], list = {};
                    list.xm = searchData.cxywcs[i].xm;
                    list.sfzh = searchData.cxywcs[i].zjh;
                    obj.push(list);
                    $.ajax({
                        url: "/realestate-inquiry-ui/rest/v1.0/gx/naturalResources/id/check",
                        type: "POST",
                        data: JSON.stringify(obj),
                        contentType: 'application/json',
                        async: false,
                        success: function (data) {
                            if (data && data.content) {
                                for (var i = 0; i < data.content.length; i++) {
                                    var content = data.content[i];
                                    content.xm = list.xm;
                                    content.zjh = list.sfzh;
                                    content.gmsfzhppddm = gmsfzhppddmDemo(content);
                                    content.xmppddm = xmppddmDemo(content);
                                    content.swbs = swbsDemo(content);
                                    resultData.push(content);
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
            uploadPdf(gzlslid, "shijrkksfhc", "人口库身份核查", key, "公安部");
        });


        function gmsfzhppddmDemo(d) {
            if (d.gmsfzh_ppddm == "1") {
                return "一致"
            } else if (d.gmsfzh_ppddm == "2") {
                return "完全匹配"
            } else if (d.gmsfzh_ppddm == "3") {
                return "相似匹配"
            } else if (d.gmsfzh_ppddm == "4") {
                return "未匹配";
            }
        }

        function xmppddmDemo(d) {
            if (d.xm_ppddm == "1") {
                return "一致"
            } else if (d.xm_ppddm == "2") {
                return "完全匹配"
            } else if (d.xm_ppddm == "3") {
                return "相似匹配"
            } else if (d.xm_ppddm == "4") {
                return "未匹配";
            }
        }

        function swbsDemo(d) {
            if (d.swbs == "0") {
                return "有效人口"
            } else if (d.swbs == "1") {
                return "死亡人口"
            }
        }
    });
});



