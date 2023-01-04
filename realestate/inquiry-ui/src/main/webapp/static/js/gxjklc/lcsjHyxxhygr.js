/**
 * @author "<a href="mailto:yousiyi@gtmap.cn>yousiyi</a>"
 * @version 1.0, 2020/11/17
 * @description 民政部-婚姻登记信息核验（个人）查询
 */
// 查询参数
var gzlslid= $.getUrlParam("gzlslid");
var formStateId = $.getUrlParam("formStateId");
var qlrlx= '1';
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

        getStateById(false, formStateId, 'lcsjHyxxhygr');

        $('#search').on('click', function () {
            // 获取查询条件
            var name = $(this).parents('.layui-form-item').find('#name').val();
            var certNum = $(this).parents('.layui-form-item').find('#certNum').val();

            // 判空
            if (!isNotBlank(name) || !isNotBlank(certNum)) {
                warnMsg('请输入必要查询条件');
                return false;
            }

            // 组织查询条件
            var getData ={
                "cxywcs":[]
            };
            getData.cxywcs.push({"name_man": name, "cert_num_man": certNum});
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
                data: JSON.stringify({"gzlslid":gzlslid,"qlrlx":qlrlx}),
                contentType: 'application/json',
                success: function (data) {
                    removeModal();

                    if(data && data.length>0){
                        searchData = {
                            "cxywcs": []
                        };
                        for (var i = 0; i < data.length; i++) {
                            searchData.cxywcs.push({
                                "cert_num_man":data[i].zjh,
                                "name_man": data[i].qlrmc
                            });
                            if (data[i].dlrmc && data[i].dlrzjh){
                                searchData.cxywcs.push({
                                    "cert_num_man" : data[i].dlrzjh,
                                    "name_man": data[i].dlrmc
                                });
                            }
                        }
                        // 展示查询条件
                        generateTable(searchData.cxywcs, false);

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
            var resultData=[];
            addModel();
            if(searchData.cxywcs &&searchData.cxywcs.length >0){
                var searchObj ={};
                for (var i = 0; i < searchData.cxywcs.length; i++) {
                    // 获取查询参数
                    var cxywcs = [{
                        cert_num_man : searchData.cxywcs[i].cert_num_man,
                        name_man:searchData.cxywcs[i].name_man
                    }];
                    searchObj.cxywcs =cxywcs;
                    // 重新请求
                    $.ajax({
                        url:"/realestate-inquiry-ui/rest/v1.0/gx/naturalResources/hyxxhygr",
                        type: "POST",
                        data: JSON.stringify(searchObj),
                        contentType: 'application/json',
                        async:false,
                        success: function (data) {
                            if(data && data.length>0) {
                                var result=searchObj.cxywcs[0];
                                result.result =data[0];
                                resultData.push(result);
                            }

                            dealCxjgxx("success",jkname);
                            removeModal();
                        },
                        error: function (xhr, status, error) {
                            dealCxjgxx("fail",jkname);
                            removeModal();
                            delAjaxErrorMsg(xhr)
                        }
                    });
                }
                //处理缓存数据
                if(resultData.length >0) {
                    key =saveJkDataToRedis(resultData);
                    generateTable(resultData, true);
                }
            }

        }

        function generateTable(data, isSearch) {
            $('#lcsbj').removeClass('layui-hide');
            if(isSearch){
                var getTpl = tableTpl.innerHTML;
            }else{
                var getTpl = cxywcsTpl.innerHTML;
            }
            laytpl(getTpl).render(data, function (html) {
                $('.bdc-table-box tbody').html(html);
            });
            form.render();
        }

        //上传PDF
        $(".upload-pdf").click(function () {
            uploadPdf(gzlslid,"sjhyxxhygr","民政部-婚姻登记信息核验（个人）",key,"省级数据共享交换平台");
        });
    });



});



