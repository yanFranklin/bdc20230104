/**
 * @author "<a href="mailto:yousiyi@gtmap.cn>yousiyi</a>"
 * @version 1.0, 2021/1/23
 * @description 民政部-地名信息查询
 */
// 查询参数
var searchData=[];
var key ="";
var gzlslid= $.getUrlParam("gzlslid");
var jkname= $.getUrlParam("jkname");
layui.use(['jquery', 'element', 'form', 'table', 'laydate'], function () {
    //获取字典
    var $ = layui.jquery;
    var form = layui.form;
    var laytpl = layui.laytpl;
    form.render();

    $(function () {

        getSearch();

        // 获取查询参数
        function getSearch() {
            $.ajax({
                url:"/realestate-inquiry-ui/rest/v1.0/gx/cxywcs/dmxx",
                type: "POST",
                contentType: 'application/json',
                success: function (data) {
                    removeModal();

                    if(data && isNotBlank(data.dmxzqdm) && isNotBlank(data.dmmc)){
                        var getData ={
                            "cxywcs":[]
                        };
                        getData.cxywcs.push({"code":data.dmxzqdm, "name":data.dmmc});
                        searchData = getData;

                        // 展示查询条件，加载表格内容
                        generateTable(searchData,false);

                    }
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
                url:"/realestate-inquiry-ui/rest/v1.0/gx/naturalResources/dmxxcx",
                type: "POST",
                data: JSON.stringify(searchData),
                contentType: 'application/json',
                success: function (data) {
                    key =saveJkDataToRedis(data);
                    dealCxjgxx("success",jkname);
                    removeModal();

                    // 结果数据不为空，重新加载表格
                    if(data && data.length>0){
                        // 将需要展示的查询条件加入结果数据
                        for(var i=0; i<data.length; i++){
                            for(var j=0; j < searchData.cxywcs.length; j++){
                                if(data[i].standard_name ==  searchData.cxywcs[j].name &&
                                    data[i].place_code.substring(0,6) == searchData.cxywcs[j].code){
                                    data[i].code = searchData.cxywcs[j].code;
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
            uploadPdf(gzlslid,"bjdmxx","民政部-地名信息查询",key,"自然资源部");
        });

    });

});



