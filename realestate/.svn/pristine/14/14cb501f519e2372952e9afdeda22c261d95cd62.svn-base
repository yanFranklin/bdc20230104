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
var resultData = [];
layui.use(['jquery', 'element', 'form', 'table', 'laydate'], function () {
    // 获取字典信息
    var zdList = {};
    getReturnData("/bdczd", {}, "POST", function (data) {
        zdList = data;
    }, function () {
    }, false);

    var $ = layui.jquery;
    var form = layui.form;
    var laytpl = layui.laytpl;
    form.render();

    $(function () {
        //生成下拉框
        if(!isNullOrEmpty(zdList.ythspywlx)) {
            $("#type").append("<option value=''></option>");
            for (var i = 0; i < zdList.ythspywlx.length; i++) {
                $("#type").append("<option value='" + zdList.ythspywlx[i].MC + "' title='" + zdList.ythspywlx[i].MC + "'>" + zdList.ythspywlx[i].MC + "</option>");
            }
        }
        form.render();
        getStateById(false, formStateId, 'lcsjDqjg');

        $('#search').on('click', function () {
            // 获取查询条件
            var number = $(this).parents('.layui-form-item').find('#number').val();
            var type = $(this).parents('.layui-form-item').find('#type').val();

            // 判空
            if (!isNotBlank(number)) {
                warnMsg('请输入必要查询条件');
                return false;
            }

            // 组织查询条件
            var getData = [];
            getData.push({
                "number": number,
                "type": type
            });
            searchData = getData;

            // 展示查询条件，加载表格内容
            //generateTable(searchData);

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
                        "number": searchData[paramlength].number,
                        "type": searchData[paramlength].type,
                        "gzlslid": gzlslid
                    };
                    // 重新请求
                    $.ajax({
                        url: "/realestate-inquiry-ui/rest/v1.0/gx/yth/xxcx",
                        type: "POST",
                        data: JSON.stringify(param),
                        contentType: 'application/json',
                        async: false,
                        success: function (data) {
                            // 结果数据不为空，重新加载表格
                            if (data) {
                                data.number = param.number;
                                data.type = param.type;
                                if (isNullOrEmpty(data.tdgyInfo)){
                                    data.tdgyInfo = {};
                                }
                                resultData.push(data);
                                $("#upload-files").show();
                                $("#prev-files").show();
                            } else {
                                $("#upload-files").hide();
                                $("#prev-files").hide();
                            }
                            dealCxjgxx("success", jkname);
                        },
                        error: function (xhr, status, error) {
                            dealCxjgxx("fail", jkname);
                            removeModal();
                            delAjaxErrorMsg(xhr);
                            $("#upload-files").hide();
                            $("#prev-files").hide();
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
                "number": searchData[0].number,
                "type": searchData[0].type,
                "gzlslid": gzlslid
            };
            // 重新请求
            $.ajax({
                url: "/realestate-inquiry-ui/rest/v1.0/gx//yth/fjxz",
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

        //文件下载--改为下载zip文件
        $("#prev-files").click(function () {
            var param = {
                "number": searchData[0].number,
                "type": searchData[0].type,
                "gzlslid": gzlslid
            };
            var pdfUrl = document.location.protocol + "//" + document.location.host +
                "/realestate-inquiry-ui/rest/v1.0/gx/yth/fjxzzip?number=" + encodeURIComponent(searchData[0].number)
                + "&type="+  encodeURIComponent(searchData[0].type);
            window.open(pdfUrl, "PDFDOWNLOAD"+ searchData[0].number);
        });

    });

});