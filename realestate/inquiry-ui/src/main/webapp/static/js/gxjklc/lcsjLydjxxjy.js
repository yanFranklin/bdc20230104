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

        getStateById(false, formStateId, 'lydjxxjy');
        $('#search').on('click', function () {
            // 获取查询条件
            var parentCertNum = $(this).parents('.layui-form-item').find('#parentCertNum').val();
            var parentName = $(this).parents('.layui-form-item').find('#parentName').val();
            var babyCertNum = $(this).parents('.layui-form-item').find('#babyCertNum').val();
            var babyName = $(this).parents('.layui-form-item').find('#babyName').val();
            var checktype = $(this).parents('.layui-form-item').find('#checktype').val();
            // 判空
            if (!isNotBlank(parentCertNum) || !isNotBlank(parentName) || !isNotBlank(babyCertNum) || !isNotBlank(babyName) || !isNotBlank(checktype)) {
                warnMsg('请输入必要查询条件');
                return false;
            }

            // 组织查询条件
            var getData = {
                "cxywcs": []
            };
            getData.cxywcs.push({
                "parent_cert_num": parentCertNum,
                "parent_name": parentName,
                "baby_cert_num": babyCertNum,
                "baby_name": babyName,
                "check_type": checktype
            });
            searchData = getData;

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
            if (searchData.cxywcs.length > 0) {
                var saveRedisData = [];
                for (var i = 0; i < searchData.cxywcs.length; i++) {
                    var result = {};
                    // 获取查询参数
                    var param = {
                        "cxywcs": []
                    };
                    param.cxywcs.push({
                        "parent_cert_num": searchData.cxywcs[i].parent_cert_num,
                        "parent_name": searchData.cxywcs[i].parent_name,
                        "baby_cert_num": searchData.cxywcs[i].baby_cert_num,
                        "baby_name": searchData.cxywcs[i].baby_name,
                        "check_type": searchData.cxywcs[i].check_type,
                    });
                    result.param = param;

                    // 重新请求
                    $.ajax({
                        url: "/realestate-inquiry-ui/rest/v1.0/gx/naturalResources/sydjxxjy",
                        type: "POST",
                        data: JSON.stringify(param),
                        contentType: 'application/json',
                        async: false,
                        success: function (data) {
                            removeModal();
                            console.log("前台返回数据：");
                            console.log(data);
                            if (data && data.length > 0) {
                                data[0].parent_cert_num = searchData.cxywcs[i].parent_cert_num;
                                data[0].parent_name = searchData.cxywcs[i].parent_name;
                                data[0].baby_cert_num = searchData.cxywcs[i].baby_cert_num;
                                data[0].baby_name = searchData.cxywcs[i].baby_name;
                                data[0].check_type = formatQueryType(searchData.cxywcs[i].check_type);
                                saveRedisData = saveRedisData.concat(data)
                                result = data[0];
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
                case "5":
                    return "男收养人信息和被收养人信息";
                case "6":
                    return "女收养人信息和被收养人信息";
            }
        };

        //上传PDF
        $(".upload-pdf").click(function () {
            uploadPdf(gzlslid, "sydjxxjy", "民政部-收养登记信息校验", key, "省专线接口");
        });
    });

});



