/**
 * @author "<a href="mailto:yousiyi@gtmap.cn>yousiyi</a>"
 * @version 1.0, 2021/02/02
 * @description 盐城市司法局-委托权公证书信息
 */
// 查询参数
var gzlslid= $.getUrlParam("gzlslid");
var qlrlx= '1';
var searchData = [];
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
            var sfzh = $(this).parents('.layui-inline').prev().find('#sfzh').val();
            // 判空
            if (!isNotBlank(sfzh)) {
                warnMsg('请输入必要查询条件');
                return false;
            }
            // 组织查询条件
            searchData = {
                "cxywcs": []
            };
            searchData.cxywcs.push({"sfzh": sfzh});

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
                data: JSON.stringify({"gzlslid":gzlslid, "qlrlx":qlrlx}),
                contentType: 'application/json',
                success: function (data) {
                    removeModal();

                    if(data && data.length>0){
                        searchData ={
                            "cxywcs":[]
                        };
                        for(var i=0; i<data.length;i++){
                            searchData.cxywcs.push({"sfzh":data[i].zjh});
                        }
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
            var resultData =[];

            addModel();

            if(searchData.cxywcs && searchData.cxywcs.length > 0) {
                for (var i = 0; i < searchData.cxywcs.length; i++) {
                    var param = {
                        "zjh": searchData.cxywcs[i].sfzh,
                    }

                    // 重新请求
                    $.ajax({
                        url:"/realestate-inquiry-ui/rest/v1.0/gx/shijgxCx/jcqgzsxx",
                        type: "POST",
                        data: JSON.stringify(param),
                        contentType: 'application/json',
                        async: false,
                        success: function (data) {
                            if (data.code == 200) {
                                successMsg(data.message);
                                // 结果数据不为空，重新加载表格
                                if(data.data && data.data.length>0){
                                    // 将需要展示的查询条件加入结果数据
                                    for(var i=0;i<data.data.length;i++){
                                        data.data[i].sfzh = param.sfzh;
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
            }

            removeModal();

            if(resultData.length >0) {
                key = saveJkDataToRedis(resultData);
                generateTable(resultData, true);
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
            })
            $(document).find('input').each(function() {
                $(this).attr('title', $(this).val());
            });
            form.render();
        }


        //上传PDF
        $(".upload-pdf").click(function () {
            uploadPdf(gzlslid,"shijjcqgzsxx","盐城市司法局-委托权公证书信息",key,"市级共享交换平台");
        });
    });

});



