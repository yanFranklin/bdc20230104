/**
 * @author "<a href="mailto:yousiyi@gtmap.cn>yousiyi</a>"
 * @version 1.0, 2021/02/02
 * @description 银保监会-金融许可证查询接口接口台账
 */
// 查询参数
var gzlslid= $.getUrlParam("gzlslid");
var formStateId = $.getUrlParam("formStateId");
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

    getStateById(false, formStateId, 'lcbjJrxkz');

    $(function () {

        getSearch();

        $('#search').on('click', function () {
            // 获取查询条件
            var typeId = $(this).parents('.layui-form-item').find('#typeId').val();
            var certCode = $(this).parents('.layui-form-item').find('#certCode').val();

            // 判空
            if (!isNotBlank(typeId) || !isNotBlank(certCode)) {
                warnMsg('请输入必要查询条件');
                return false;
            }

            // 组织查询条件
            var getData ={
                "slbh":"",
                "paramDTOS":[]
            };
            getData.paramDTOS.push({"typeId": typeId, "certCode": certCode});
            searchData = getData;

            // 展示查询条件，加载表格内容
            generateTable(searchData,false);

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
                data: JSON.stringify({"gzlslid":gzlslid,"excludeQlrlx":excludeQlrlx}),
                contentType: 'application/json',
                success: function (data) {
                    removeModal();

                    if(data && data.length>0){
                        var getData ={
                            "slbh":"",
                            "paramDTOS":[]
                        };
                        for(var i=0; i<data.length;i++){
                            getData.paramDTOS.push({"typeId":"01", "certCode":data[i].zjh});
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
            addModel();
            // 重新请求
            $.ajax({
                url:"/realestate-inquiry-ui/rest/v1.0/gx/naturalResources/cbirc/financialQuery",
                type: "POST",
                data: JSON.stringify(searchData),
                contentType: 'application/json',
                success: function (data) {
                    dealCxjgxx("success",jkname);
                    key =saveJkDataToRedis(data);
                    removeModal();

                    // 结果数据不为空，重新加载表格
                    if(data && data!=null && data.length>0){
                        // 将需要展示的查询条件加入结果数据
                        for(var i=0; i<data.length; i++){
                            data[i].paramDTOS = [];
                            for(var j=0; j < searchData.paramDTOS.length; j++){
                                if(data[i].certCode ==  searchData.paramDTOS[j].certCode &&
                                    data[i].typeId ==  searchData.paramDTOS[j].typeId){
                                    data[i].paramDTOS.push(searchData.paramDTOS[j]);
                                }
                            }
                        }
                        generateTable(data,true);
                    }
                },
                error: function (xhr, status, error) {
                    dealCxjgxx("fail",jkname);
                    removeModal();
                    delAjaxErrorMsg(xhr)
                }
            });
        }


        function generateTable(data,isSearch) {
            $('#lcsbj').removeClass('layui-hide');
            if(isSearch){
                var getTpl = tableTpl.innerHTML;
            }else {
                var getTpl = cxywcsTpl.innerHTML;
            }

            laytpl(getTpl).render(data, function (html) {
                $('.bdc-table-box tbody').html(html);
            });
        }

        //上传PDF
        $(".upload-pdf").click(function () {
            uploadPdf(gzlslid,"bjjrxkz","银保监会-金融许可证查询",key,"自然资源部");
        });
    });

});



