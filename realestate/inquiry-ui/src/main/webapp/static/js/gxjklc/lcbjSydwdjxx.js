/**
 * @author "<a href="mailto:yousiyi@gtmap.cn>yousiyi</a>"
 * @version 1.0, 2020/02/03
 * @description 中编办-事业单位登记信息（含机关、群团信息）查询
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


    $(function () {

        getSearch();

        getStateById(false, formStateId, 'lcbjSydwdjxx');

        $('#search').on('click', function () {
            // 获取查询条件
            var name = $(this).parents('.layui-form-item').find('#name').val();
            var tyshxydm = $(this).parents('.layui-form-item').find('#tyshxydm').val();

            // 判空
            if (!isNotBlank(name) || !isNotBlank(tyshxydm)) {
                warnMsg('请输入必要查询条件');
                return false;
            }

            // 组织查询条件
            var getData ={
                "paramDTOList":[]
            };
            getData.paramDTOList.push({"name": name, "tyshxydm": tyshxydm});
            searchData = getData;

            // 展示查询条件，加载表格内容
            generateTable(searchData,false);

            // 查询条件不为空进行查询
            if(searchData &&searchData.paramDTOList &&searchData.paramDTOList.length >0){
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
                    var tableData =[];
                    if(data && data.length>0){
                        var getData ={
                            "slbh":"",
                            "paramDTOList":[],
                            "loadpage":false
                        };
                        for(var i=0; i<data.length;i++){
                            getData.paramDTOList.push({"name":data[i].qlrmc, "tyshxydm":data[i].zjh});
                            var paramDTOList={"name":data[i].qlrmc, "tyshxydm":data[i].zjh};
                            tableData.push({paramDTOList:paramDTOList});

                        }
                        searchData = getData;
                    }
                    // 展示查询条件，加载表格内容
                    generateTable(tableData);
                    // 查询条件不为空进行查询
                    if(searchData &&searchData.paramDTOList &&searchData.paramDTOList.length >0){
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
            if(searchData.paramDTOList.length >0){
                var resultData =[];
                for (var paramlength = 0; paramlength < searchData.paramDTOList.length; paramlength++) {
                    var param ={
                        "paramDTOList":[{"name":searchData.paramDTOList[paramlength].name, "tyshxydm":searchData.paramDTOList[paramlength].tyshxydm}]
                    };
                    // 重新请求
                    $.ajax({
                        url:"/realestate-inquiry-ui/rest/v1.0/gx/naturalResources/scopsr/organQuery",
                        type: "POST",
                        data: JSON.stringify(param),
                        async:false,
                        contentType: 'application/json',
                        success: function (data) {
                            // 结果数据不为空，重新加载表格
                            if(data && data.length>0){
                                // 将需要展示的查询条件加入结果数据
                                data[0].paramDTOList =param.paramDTOList[0];
                                resultData.push(data[0]);

                            }
                        },
                        error: function (xhr, status, error) {
                            dealCxjgxx("fail",jkname);
                            removeModal();
                            delAjaxErrorMsg(xhr)
                        }
                    });

                }
                key =saveJkDataToRedis(resultData);
                removeModal();
                if(resultData.length >0) {
                    dealCxjgxx("success",jkname);
                    generateTable(resultData);
                }

            }

        }

        function generateTable(data) {
            $('#lcsbj').removeClass('layui-hide');
            var getTpl = sydwTpl.innerHTML;
            laytpl(getTpl).render(data, function (html) {
                $('.bdc-table-box tbody').html(html);
            });
            form.render();
        }

        //上传PDF
        $(".upload-pdf").click(function () {
            uploadPdf(gzlslid,"bjsydwdjxx","中编办-事业单位登记信息（含机关、群团信息）查询",key,"自然资源部");
        });

    });
});



