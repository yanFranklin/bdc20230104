/**
 住房和城乡建设局-建筑工程竣工验收备案表
 不用自动查询，输入条件手动查询
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
        $('#search').on('click', function () {
            // 获取查询条件
            var sqdwmc = $(this).parents('.layui-form-item').find('#sqdwmc').val();
            var baxmxxmc = $(this).parents('.layui-form-item').find('#baxmxxmc').val();
            var bah = $(this).parents('.layui-form-item').find('#bah').val();
            // 判空
            if (isNullOrEmpty(sqdwmc) && isNullOrEmpty(baxmxxmc) && isNullOrEmpty(bah)) {
                warnMsg('至少输入一个查询条件');
                return false;
            }
            // 组织查询条件
            searchData = {
                "cxywcs": []
            };
            searchData.cxywcs.push({
                "sqdwmc": sqdwmc,
                "baxmxxmc": baxmxxmc,
                "bah": bah,
            });

            // 查询条件不为空进行查询
            if (searchData && searchData.cxywcs && searchData.cxywcs.length > 0) {
                search();
            }
        });

        //查询
        function search() {
            var resultData = [];
            addModel();
            if (searchData.cxywcs && searchData.cxywcs.length > 0) {
                for (var i = 0; i < searchData.cxywcs.length; i++) {
                    // 获取查询参数
                    var param = {
                        sqdwmc: searchData.cxywcs[i].sqdwmc,
                        baxmxxmc: searchData.cxywcs[i].baxmxxmc,
                        bah: searchData.cxywcs[i].bah,
                    };
                    // 重新请求
                    $.ajax({
                        url: "/realestate-inquiry-ui/rest/v1.0/gx/shijgxCx/jzgcjgysba",
                        type: "POST",
                        data: JSON.stringify(param),
                        contentType: 'application/json',
                        async: false,
                        success: function (data) {
                            if (!isNullOrEmpty(data.data)) {
                                //successMsg(data.message);
                                if (data.data && data.data.length > 0) {
                                    for (var i = 0; i < data.data.length; i++) {
                                        data.data[i].sqdwmc = param.sqdwmc;
                                        data.data[i].baxmxxmc = param.baxmxxmc;
                                        data.data[i].bah = param.bah;
                                        resultData.push(data.data[i]);
                                    }
                                }
                                dealCxjgxx("success", jkname);
                            } else {
                                warnMsg("操作异常");
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
            uploadPdf(gzlslid, "jzgcjgysba", "住房和城乡建设局-建筑工程竣工验收备案表", key, "市级共享交换平台");
        });
    });


});



