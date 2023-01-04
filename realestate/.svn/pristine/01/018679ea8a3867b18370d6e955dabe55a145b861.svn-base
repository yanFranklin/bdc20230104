/**
 * @author "<a href="mailto:wangyaqing@gtmap.cn>wangyaqing</a>"
 * @version 1.0, 2021/2/4
 * @description 工改
 */
// 查询参数
var gzlslid = $.getUrlParam("gzlslid");
var qlrlx = '1';
var formStateId = $.getUrlParam("formStateId");
var excludeQlrlx = '1';
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

        getStateById(false, formStateId, 'lcsjDqjg');

        $('#search').on('click', function () {
            // 获取查询条件
            var centralProjectCode = $(this).parents('.layui-form-item').find('#centralProjectCode').val();
            var localProjectCode = $(this).parents('.layui-form-item').find('#localProjectCode').val();

            // 判空
            if (!isNotBlank(centralProjectCode)) {
                warnMsg('请输入必要查询条件');
                return false;
            }

            // 组织查询条件
            var getData = [];
            getData.push({
                "centralProjectCode": centralProjectCode,
                "localProjectCode": localProjectCode
            });
            searchData = getData;

            // 展示查询条件，加载表格内容
            generateTable(searchData);

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
                var resultData = [];
                for (var paramlength = 0; paramlength < searchData.length; paramlength++) {
                    var param = {
                        "centralProjectCode": encodeURIComponent(searchData[paramlength].centralProjectCode),
                        "localProjectCode": encodeURIComponent(searchData[paramlength].localProjectCode),
                        "gzlslid": gzlslid
                    };
                    // 重新请求
                    $.ajax({
                        url: "/realestate-inquiry-ui/rest/v1.0/gx/ggxt/gcxxcx",
                        type: "POST",
                        data: JSON.stringify(param),
                        contentType: 'application/json',
                        async: false,
                        success: function (data) {
                            // 结果数据不为空，重新加载表格
                            if (data && data.success == true) {
                                if (data.data && data.data.length > 0) {
                                    for (var i = 0; i < data.data.length; i++) {
                                        var datum = data.data[i];
                                        // 将需要展示的查询条件加入结果数据
                                        datum.centralProjectCode = param.centralProjectCode;
                                        datum.localProjectCode = param.localProjectCode;
                                        resultData.push(datum);
                                    }
                                    $("#upload-files").show();
                                } else {
                                    $("#upload-files").hide();
                                }
                            } else {
                                $("#upload-files").hide();
                            }
                            dealCxjgxx("success", jkname);
                        },
                        error: function (xhr, status, error) {
                            dealCxjgxx("fail", jkname);
                            removeModal();
                            delAjaxErrorMsg(xhr);
                            $("#upload-gzsxx").hide();
                            $("#upload-qtws").hide();
                        }
                    });
                }

                key = saveJkDataToRedis(resultData);
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

        //文件上传到流程
        $("#upload-files").click(function () {
            var param = {
                "centralProjectCode": searchData[0].centralProjectCode,
                "localProjectCode": searchData[0].localProjectCode,
                "gzlslid": gzlslid
            };
            // 重新请求
            $.ajax({
                url: "/realestate-inquiry-ui/rest/v1.0/gx/ggxt/gcxxsc",
                type: "POST",
                data: JSON.stringify(param),
                contentType: 'application/json',
                async: false,
                success: function (data) {
                    layer.msg('文件获取成功');
                },
                error: function (xhr, status, error) {
                    warnMsg('文件获取失败');
                }
            });
        });

    });

});