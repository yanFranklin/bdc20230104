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
var searchData = [];
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
            var epbno = $(this).parents('.layui-form-item').find('#epbno').val();
            var name = $(this).parents('.layui-form-item').find('#name').val();
            var traname = $(this).parents('.layui-form-item').find('#traname').val();
            var authcode = $(this).parents('.layui-form-item').find('#authcode').val();

            // 判空
            if (!isNotBlank(epbno) || !isNotBlank(name) || !isNotBlank(traname) || !isNotBlank(authcode)) {
                warnMsg('请输入必要查询条件');
                return false;
            }

            // 组织查询条件
            var getData = [];
            getData.push({"epbno": epbno, "name": name,"traname": traname,"authcode": authcode});
            searchData = getData;

            // 查询条件不为空进行查询
            if (searchData && searchData.length > 0) {
                search();
            }
        });

        //查询
        function search() {
            addModel();
            // 重新请求
            var resultData = [];
            if (searchData && searchData.length > 0) {
                var searchObj = {};
                for (var i = 0; i < searchData.length; i++) {
                    // 获取查询参数
                    var cxywcs = {
                        "epbno": searchData[i].epbno,
                        "name": searchData[i].name,
                        "traname": searchData[i].traname,
                        "authcode": searchData[i].authcode,
                    };
                    // 重新请求
                    $.ajax({
                        url: "/realestate-inquiry-ui/rest/v1.0/gx/sheng/gtgshcx",
                        type: "POST",
                        data: JSON.stringify(cxywcs),
                        contentType: 'application/json',
                        async: false,
                        success: function (data) {
                            if (data && data.length > 0) {
                                for (var j = 0; j < data.length; j++) {
                                    resultData.push(data[j]);
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
                //处理缓存数据
                if (resultData.length > 0) {
                    key = saveJkDataToRedis(resultData);
                }
                generateTable(resultData);
            }
            removeModal();
        }

        function generateTable(data) {
            $('#lcsbj').removeClass('layui-hide');
            var getTpl = cxywcsTpl.innerHTML;
            laytpl(getTpl).render(data, function (html) {
                $('.bdc-table-box tbody').html(html);
            });
            form.render();
        }

        //上传PDF
        $(".upload-pdf").click(function () {
            uploadPdf(gzlslid, "sjcxczrk", "常住人口查询", key, "省专线接口");
        });


    });

});



