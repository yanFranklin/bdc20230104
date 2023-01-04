/**
 * @author "<a href="mailto:yousiyi@gtmap.cn>yousiyi</a>"
 * @version 1.0, 2020/11/17
 * @description 民政部-涉外婚姻登记信息核验（单人）查询
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
            var xm = $(this).parents('.layui-form-item').find('#xm').val();
            var zjh = $(this).parents('.layui-form-item').find('#zjh').val();
            // 判空
            if (!isNotBlank(xm) || !isNotBlank(zjh)) {
                warnMsg('请输入必要查询条件');
                return false;
            }
            // 组织查询条件
            searchData = {
                "cxywcs": []
            };
            searchData.cxywcs.push({"name": xm, "cert_num": zjh});

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
                        searchData = {
                            "cxywcs": []
                        };
                        for (var i = 0; i < data.length; i++) {
                            searchData.cxywcs.push({
                                "cert_num": data[i].zjh,
                                "name": data[i].qlrmc
                            });
                            if (data[i].dlrmc && data[i].dlrzjh){
                                searchData.cxywcs.push({
                                    "name":data[i].dlrmc,
                                    "cert_num": data[i].dlrzjh
                                });
                            }
                        }
                        // 展示查询条件
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
            var resultData=[];
            addModel();
            if(searchData.cxywcs &&searchData.cxywcs.length >0){
                var searchObj ={};
                for (var i = 0; i < searchData.cxywcs.length; i++) {
                    // 获取查询参数
                    var param = {
                        cert_num : searchData.cxywcs[i].cert_num,
                        name: searchData.cxywcs[i].name
                    };
                    // 重新请求
                    $.ajax({
                        url:"/realestate-inquiry-ui/rest/v1.0/gx/shijgxCx/swhydjxxdrhy",
                        type: "POST",
                        data: JSON.stringify(param),
                        contentType: 'application/json',
                        async:false,
                        success: function (data) {
                            if (data.code == 200) {
                                successMsg(data.message);
                                if(data.data && Object.keys(data.data).length > 0){
                                    // 将需要展示的查询条件加入结果数据
                                    data.data.cert_num = param.cert_num;
                                    data.data.name = param.name;
                                    resultData.push(data.data);
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
                removeModal();

                if(resultData.length >0) {
                    //处理缓存数据
                    key =saveJkDataToRedis(resultData);
                    generateTable(resultData, true);
                }
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
            uploadPdf(gzlslid,"shijswhydjxxdrhy","民政部-涉外婚姻登记信息核验（单人）查询",key,"市级共享交换平台");
        });
    });



});



