/**
 * @author "<a href="mailto:yousiyi@gtmap.cn>yousiyi</a>"
 * @version 1.0, 2021/02/02
 * @description 市银保监分局—金融许可证信息
 */
// 查询参数
var gzlslid= $.getUrlParam("gzlslid");
var excludeQlrlx= '1';
var searchData=[];
var key ="";
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
            var tyshxydm = $(this).parents('.layui-form-item').find('#tyshxydm').val();
            // 判空
            if (!isNotBlank(tyshxydm)) {
                warnMsg('请输入必要查询条件');
                return false;
            }
            // 组织查询条件
            searchData = {
                "paramDTOS": []
            };
            searchData.paramDTOS.push({"tyshxydm": tyshxydm});

            // 展示查询条件，加载表格内容
            generateTable(searchData, false);

            // 查询条件不为空进行查询
            if(searchData &&searchData.paramDTOS &&searchData.paramDTOS.length >0){
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

                    if(data && data.length>0){
                        var getData ={
                            "paramDTOS":[]
                        };
                        for(var i=0; i<data.length;i++){
                            getData.paramDTOS.push({"tyshxydm":data[i].zjh});
                        }

                        searchData = getData;
                    }
                    // 展示查询条件，加载表格内容
                    generateTable(searchData,false);
                    // 查询条件不为空进行查询
                    if(searchData &&searchData.paramDTOS &&searchData.paramDTOS.length >0){
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

            if(searchData.paramDTOS.length > 0) {
                for (var i = 0; i < searchData.paramDTOS.length; i++) {
                    var param = {
                        "tyshxydm": searchData.paramDTOS[i].tyshxydm,
                    }

                    // 重新请求
                    $.ajax({
                        url:"/realestate-inquiry-ui/rest/v1.0/gx/shijgxCx/shttmcbgdjjlcx",
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
                                        data.data[i].tyshxydm = param.tyshxydm;
                                        resultData.push(data.data[i]);
                                    }
                                }
                                dealCxjgxx("success",jkname);
                            } else {
                                warnMsg(data.message);
                            }

                            removeModal();
                        },
                        error: function (xhr, status, error) {
                            dealCxjgxx("fail",jkname);
                            removeModal();
                            delAjaxErrorMsg(xhr)
                        }
                    });

                }

                if(resultData.length >0) {
                    key = saveJkDataToRedis(resultData);
                    generateTable(resultData, true);
                }
            }
        }

        function generateTable(data,isSearch) {
            if(isSearch){
                var getTpl = tableTpl.innerHTML;
            }else {
                var getTpl = cxywcsTpl.innerHTML;
            }
            laytpl(getTpl).render(data, function (html) {
                $('.bdc-table-box tbody').html(html);
            });
            $(document).find('input').each(function() {
                $(this).attr('title', $(this).val());
            });
        }


        //上传PDF
        $(".upload-pdf").click(function () {
            uploadPdf(gzlslid,"shijshttmcbgdjjlcx","民政部-社会团体名称变更登记记录查询",key,"市级共享交换平台");
        });
    });

});



