/**
 * @author "<a href="mailto:liaoxiang@gtmap.cn>liaoxiang</a>"
 * @version 1.0, 2021/02/01
 * @description 民政部-社会组织统一社会信用代码信息查询
 */
// 查询参数
var gzlslid= $.getUrlParam("gzlslid");
var formStateId = $.getUrlParam("formStateId");
var excludeQlrlx= '1';
var searchData=[];
var key ="";
var jkname= $.getUrlParam("jkname");
layui.use(['jquery','form', 'table', 'laydate', 'laytpl'], function () {
    var $ = layui.jquery;
    var form = layui.form;
    var laytpl = layui.laytpl;

    form.render();

    $(function () {

        getSearch();

        getStateById(false, formStateId, 'lcbjShzztyshxydmxx');

        $('#search').on('click', function () {
            // 获取查询条件
            var name = $(this).parents('.layui-form-item').find('#name').val();
            var tyshxydm = $(this).parents('.layui-form-item').find('#tyshxydm').val();
            var search_type = $(this).parents('.layui-form-item').find('#search_type').val();

            // 判空
            if (!isNotBlank(name) || !isNotBlank(tyshxydm) || !isNotBlank(search_type)) {
                warnMsg('请输入必要查询条件');
                return false;
            }

            // 组织查询条件
            var getData ={
                "cxywcs":[],
                "loadpage":false
            };
            getData.cxywcs.push({"name": name, "tyshxydm": tyshxydm, "search_type": search_type});
            searchData = getData;

            // 展示查询条件，加载表格内容
            generateTable(searchData,false);

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
                    var tableData =[];
                    if(data && data.length>0){
                        var getData ={
                            "cxywcs":[],
                            "loadpage":false
                        };
                        for(var i=0; i<data.length;i++){
                            getData.cxywcs.push({"name":data[i].qlrmc, "tyshxydm":data[i].zjh,"search_type":10});
                            var cxywcs={"name":data[i].qlrmc, "tyshxydm":data[i].zjh,"search_type":10};
                            tableData.push({cxywcs:cxywcs});

                        }
                        searchData = getData;
                    }
                    // 展示查询条件，加载表格内容
                    generateTable(tableData);
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
            addModel();
            // 重新请求
            $.ajax({
                url:"/realestate-inquiry-ui/rest/v1.0/gx/naturalResources/shzztyxxcx",
                type: "POST",
                data: JSON.stringify(searchData),
                contentType: 'application/json',
                success: function (data) {
                    dealCxjgxx("success",jkname);
                    key =saveJkDataToRedis(data);
                    removeModal();

                    // 结果数据不为空，重新加载表格
                    if(data && data.length>0){
                        // 将需要展示的查询条件加入结果数据
                        for(var j=0; j < searchData.cxywcs.length; j++){
                            //是否获取到结果
                            var getResult =false;
                            for(var i=0; i<data.length; i++){
                                if((data[i].usc_code ==  searchData.cxywcs[j].tyshxydm)
                                ){
                                    getResult =true;
                                    data[i].cxywcs =searchData.cxywcs[j];
                                }
                            }
                            if(!getResult){
                                //没有查询到返回结果
                                var result={};
                                result.cxywcs =searchData.cxywcs[j];
                                result.usc_code =searchData.cxywcs[j].tyshxydm;
                                result.org_name =searchData.cxywcs[j].name;
                                data.push(result);
                            }

                        }

                        generateTable(data);
                    }
                },
                error: function (xhr, status, error) {
                    dealCxjgxx("fail",jkname);
                    removeModal();
                    delAjaxErrorMsg(xhr)
                }
            });
        }

        function generateTable(data) {
            $('#lcsbj').removeClass('layui-hide');
            var getTpl = shzzTpl.innerHTML;
            laytpl(getTpl).render(data, function (html) {
                $('.bdc-table-box tbody').html(html);
            });
            form.render();
        }

        //上传PDF
        $(".upload-pdf").click(function () {
            uploadPdf(gzlslid,"bjshzztyshxydmxx","民政部-社会组织统一社会信用代码信息查询",key,"自然资源部");
        });

    });

});