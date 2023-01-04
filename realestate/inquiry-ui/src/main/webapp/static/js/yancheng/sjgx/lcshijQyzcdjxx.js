/**
 * @author "<a href="mailto:yousiyi@gtmap.cn>yousiyi</a>"
 * @version 1.0, 2020/11/17
 * @description 中编办-事业单位信息查询
 */
// 查询参数
var gzlslid= $.getUrlParam("gzlslid");
var excludeQlrlx= '1';
var key ="";
var searchData={};
var jkname= $.getUrlParam("jkname");
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
            var tydm = $(this).parents('.layui-form-item').find('#tydm').val();
            // 判空
            if (!isNotBlank(tydm)) {
                warnMsg('请输入必要查询条件');
                return false;
            }
            // 组织查询条件
            searchData = {
                "cxywcs": []
            };
            searchData.cxywcs.push({"tydm": tydm});

            // 展示查询条件，加载表格内容
            generateTable(searchData, false);

            // 查询条件不为空进行查询
            if(searchData &&searchData.cxywcs &&searchData.cxywcs.length >0){
                search();
            }
        });


        // 获取查询参数
        function getSearch() {
            $.ajax({
                url:"/realestate-inquiry-ui/rest/v1.0/gx/cxywcs/qlr",
                type: "POST",
                data: JSON.stringify({"gzlslid":gzlslid, "excludeQlrlx":excludeQlrlx}),
                contentType: 'application/json',
                success: function (data) {
                    removeModal();

                    if (data && data.length > 0) {
                        var getData = {
                            "cxywcs": []
                        };
                        for (var i = 0; i < data.length; i++) {
                            getData.cxywcs.push({"tydm": data[i].zjh});
                        }

                        searchData = getData;
                    }
                    // 展示查询条件，加载表格内容
                    generateTable(searchData, false);

                    // 查询条件不为空进行查询
                    if(searchData &&searchData.cxywcs &&searchData.cxywcs.length >0){
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
            var resultData =[];

            addModel();
            if(searchData.cxywcs.length >0){
                for (var i = 0; i < searchData.cxywcs.length; i++) {

                    // 获取查询参数
                    var param ={
                        "tydm":searchData.cxywcs[i].tydm
                    };
                    // 重新请求
                    $.ajax({
                        url: "/realestate-inquiry-ui/rest/v1.0/gx/shijgxCx/ycsjgjqyzcdjxx",
                        type: "POST",
                        data: JSON.stringify(param),
                        contentType: 'application/json',
                        async:false,
                        success: function (data) {
                            if (data.code == 200) {
                                successMsg(data.message);
                                // 结果数据不为空，重新加载表格
                                if(data.data && data.data.length>0){
                                    for(var i=0;i<data.data.length;i++){
                                        data.data[i].tydm = param.tydm;
                                        resultData.push(data.data[i]);
                                    }
                                }
                                dealCxjgxx("success",jkname);
                            } else {
                                warnMsg(data.message);
                            }

                        },
                        error: function (xhr, status, error) {
                            dealCxjgxx("fail",jkname);
                            removeModal();
                            delAjaxErrorMsg(xhr)
                        }
                    });
                }
            }
            removeModal();
            if(resultData.length >0) {
                key =saveJkDataToRedis(resultData);
                generateTable(resultData, true);
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
            $(document).find('input').each(function() {
                $(this).attr('title', $(this).val());
            });
            form.render();
        }

        //上传PDF
        $(".upload-pdf").click(function () {
            uploadPdf(gzlslid,"shijycsjgjqyzcdjxx","企业注册登记信息查询",key,"市级共享交换平台");
        });
    });
});



