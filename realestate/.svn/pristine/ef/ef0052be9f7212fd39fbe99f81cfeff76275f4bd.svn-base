/**
 * @author "<a href="mailto:wangyaqing@gtmap.cn>wangyaqing</a>"
 * @version 1.0, 2020/11/13
 * @description 国家公安部—人口库基准信息查询
 */
// 查询参数
var gzlslid= $.getUrlParam("gzlslid");
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

        $('#search').on('click', function () {
            // 获取查询条件
            var zjh = $(this).parents('.layui-form-item').find('#zjh').val();
            var xm = $(this).parents('.layui-form-item').find('#xm').val();
            // 判空
            if (!isNotBlank(zjh) || !isNotBlank(xm)) {
                warnMsg('请输入必要查询条件');
                return false;
            }
            // 组织查询条件
            searchData = {
                "cxywcs": []
            };
            searchData.cxywcs.push({"xm": xm, "zjh": zjh});

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
                data: JSON.stringify({"gzlslid":gzlslid,"qlrlx":qlrlx}),
                contentType: 'application/json',
                success: function (data) {
                    removeModal();

                    if(data && data.length>0){
                        var getData ={
                            "cxywcs":[],
                            "loadpage":false
                        };
                        for(var i=0; i<data.length;i++){
                            getData.cxywcs.push({"xm":data[i].qlrmc, "zjh":data[i].zjh});
                            if (data[i].dlrmc && data[i].dlrzjh){
                                getData.cxywcs.push({
                                    "zjh":data[i].dlrzjh,
                                    "xm": data[i].dlrmc
                                });
                            }
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
            //循环查看
            if(searchData.cxywcs.length >0){
                var resultData =[];
                for (var paramlength = 0; paramlength < searchData.cxywcs.length; paramlength++) {
                    var param ={
                        "data":[{"xm":searchData.cxywcs[paramlength].xm, "zjh":searchData.cxywcs[paramlength].zjh}]
                    };
                    // 重新请求
                    $.ajax({
                        url:"/realestate-inquiry-ui/rest/v1.0/gx/shijgxCx/rkkjzxxcx",
                        type: "POST",
                        data: JSON.stringify(param),
                        contentType: 'application/json',
                        async:false,
                        success: function (data) {
                            if (data.code == 200) {
                                successMsg(data.message);
                                // 结果数据不为空，重新加载表格
                                if(data.data && data.data.length>0) {
                                    data.data[0].xm = param.data[0].xm;
                                    data.data[0].zjh = param.data[0].zjh;
                                    resultData.push(data.data[0]);
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

                removeModal();
                if(resultData.length >0) {
                    key =saveJkDataToRedis(resultData);
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
            uploadPdf(gzlslid,"shijrkkjzxxcx","国家公安部—人口库基准信息查询",key,"市级共享交换平台");
        });
    });

});