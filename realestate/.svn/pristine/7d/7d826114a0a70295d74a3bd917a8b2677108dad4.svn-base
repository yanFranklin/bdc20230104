/**
 * @author "<a href="mailto:wangyaqing@gtmap.cn>wangyaqing</a>"
 * @version 1.0, 2021/2/4
 * @description 中编办-党群机关信息查询
 */
// 查询参数
var gzlslid= $.getUrlParam("gzlslid");
var formStateId = $.getUrlParam("formStateId");
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

        getStateById(false, formStateId, 'lcsjDqjg');

        $('#search').on('click', function () {
            // 获取查询条件
            var tyshxydm = $(this).parents('.layui-form-item').find('#tyshxydm').val();

            // 判空
            if (!isNotBlank(tyshxydm)) {
                warnMsg('请输入必要查询条件');
                return false;
            }

            // 组织查询条件
            var getData ={
                "cxywcs":[],
            };
            getData.cxywcs.push({"tyshxydm": tyshxydm});
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
                data: JSON.stringify({"gzlslid":gzlslid, "excludeQlrlx":excludeQlrlx}),
                type: "POST",
                contentType: 'application/json',
                success: function (data) {
                    removeModal();

                    if(data && data.length>0){
                        var getData ={
                            "cxywcs":[]
                        };
                        for(var i=0; i<data.length;i++){
                            getData.cxywcs.push({"tyshxydm":data[i].zjh});
                        }
                        searchData = getData;



                    }
                    // 展示查询条件，加载表格内容
                    generateTable(searchData,false);
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
            //循环调用
            if(searchData.cxywcs.length >0){
                var resultData =[];
                for (var paramlength = 0; paramlength < searchData.cxywcs.length; paramlength++) {
                    var param ={
                        "cxywcs":[{"tyshxydm":searchData.cxywcs[paramlength].tyshxydm}]
                    };
                    // 重新请求
                    $.ajax({
                        url:"/realestate-inquiry-ui/rest/v1.0/gx/naturalResources/dqjg",
                        type: "POST",
                        data: JSON.stringify(param),
                        contentType: 'application/json',
                        async:false,
                        success: function (data) {
                            // 结果数据不为空，重新加载表格
                            if(data && data.length>0){
                                // 将需要展示的查询条件加入结果数据
                                data[0].TYSHXYDM = param.cxywcs[0].tyshxydm;
                                resultData.push(data[0]);

                            }
                            dealCxjgxx("success",jkname);
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
                    generateTable(resultData, true);
                }

            }

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
            uploadPdf(gzlslid,"sjdqjg","中编办-党群机关信息查询",key,"省级数据共享交换平台");
        });

    });

});