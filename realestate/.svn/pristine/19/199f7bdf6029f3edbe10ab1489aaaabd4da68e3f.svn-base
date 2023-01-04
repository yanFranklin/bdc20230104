/**
 工信局-出生医学证明
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
            var czztdm = $(this).parents('.layui-form-item').find('#czztdm').val();
            var xm = $(this).parents('.layui-form-item').find('#xm').val();
            // 判空
            if (!isNotBlank(czztdm)) {
                warnMsg('请输入必要查询条件');
                return false;
            }
            // 组织查询条件
            searchData = {
                "cxywcs": []
            };
            searchData.cxywcs.push({
                "czztdm": czztdm,
                "xm": xm,
            });

            // 展示查询条件，加载表格内容
            generateTable(searchData, false);

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
                        czztdm: searchData.cxywcs[i].czztdm,
                        xm: searchData.cxywcs[i].xm,
                    };
                    // 重新请求
                    $.ajax({
                        url: "/realestate-inquiry-ui/rest/v1.0/gx/shijgxCx/csyxzm",
                        type: "POST",
                        data: JSON.stringify(param),
                        contentType: 'application/json',
                        async: false,
                        success: function (data) {
                            if (data.code == 0) {
                                successMsg(data.message);
                                if (data.data && data.data.length > 0) {
                                    for (var i = 0; i < data.data.length; i++) {
                                        data.data[i].surface.czztdm = param.czztdm;
                                        data.data[i].surface.xm = param.xm;
                                        resultData.push(data.data[i].surface);
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
            uploadPdf(gzlslid, "csyxzm", "工信局-出生医学证明", key, "市级共享交换平台");
        });
    });


});



