/**
 * @author "<a href="mailto:zedeqiang@gtmap.cn>zedq</a>"
 * @version 1.0, 2021/08/05
 * @description 居民户口簿证照信息
 */
var gzlslid= $.getUrlParam("gzlslid");
var qlrlx= '1';
var key ="";
var searchData=[];
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
            searchData = [];
            // 获取查询条件
            var zjh = $(this).parents('.layui-form-item').find('#zjh').val();
            // var bjry = $(this).parents('.layui-form-item').find('#bjry').val();
            // var bjrzjh = $(this).parents('.layui-form-item').find('#bjrzjh').val();
            // 判空
            if (!isNotBlank(zjh)) {
                warnMsg('请输入必要查询条件');
                return false;
            }
            // 组织查询条件
            searchData.push({
                "zjh":zjh,
                // "bjry":bjry,
                // "bjrzjh":bjrzjh,
                "certificateType":"shijjk_jmhkbxz",
                "gzlslid": gzlslid
            });;

            // 展示查询条件，加载表格内容
            generateTable(searchData, false);

            // 查询条件不为空进行查询
            if(searchData &&searchData.length >0){
                search();
            }
        });

        // 回车查询
        $('.required').on('keydown', function (event) {
            if (event.keyCode == 13) {
                search();
            }
        });

        //查询
        function search() {
            var resultData=[];
            addModel();
            if(searchData &&searchData.length >0) {
                for (var i = 0; i < searchData.length; i++) {
                    // 获取查询参数
                    // 重新请求
                    $.ajax({
                        url:"/realestate-inquiry-ui/rest/v1.0/gx/shijgxCx/zzxz/jmhkbxz",
                        type: "POST",
                        data: JSON.stringify(searchData[i]),
                        contentType: 'application/json',
                        async:false,
                        success: function (data) {
                            if (data.success) {
                                successMsg("success");

                                if (!isNullOrEmpty(data.data)) {
                                    data.data.zjh = searchData[0].zjh;
                                    // data.data.bjry = searchData[0].bjry;
                                    // data.data.bjrzjh = searchData[0].bjrzjh;
                                    resultData.push(data.data);
                                }
                                dealCxjgxx("success",jkname);
                            } else {
                                warnMsg(data.fail.message);
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
                    //处理缓存数据
                    key =saveJkDataToRedis(resultData);
                    generateTable(resultData, true);
                }
            }


        }

        function generateTable(data, isSearch) {
            // if (isSearch) {
                var getTpl = tableTpl.innerHTML;
            // } else {
            //     var getTpl = cxywcsTpl.innerHTML;
            // }

            laytpl(getTpl).render(data, function (html) {
                $('.bdc-table-box tbody').html(html);
            });
            $(document).find('input').each(function() {
                $(this).attr('title', $(this).val());
            });
            form.render();
        }

        // 获取查询参数
        function getSearch() {
            $.ajax({
                url:"/realestate-inquiry-ui/rest/v1.0/gx/cxywcs/qlr",
                type: "POST",
                data: JSON.stringify({"gzlslid":gzlslid,"qlrlx":qlrlx}),
                contentType: 'application/json',
                success: function (data) {
                    removeModal();

                    if (data && data.length > 0) {
                        var dataList = [];
                        for (var i = 0; i < data.length; i++) {
                            if (data[i].qlrlb == '1'){
                                var param = {
                                    "zjh":data[i].zjh,
                                    "gzlslid":gzlslid,
                                    "certificateType":"shijjk_jmhkbxz"
                                };
                                dataList.push(param);
                                if (data[i].dlrzjh){
                                    dataList.push({
                                        "zjh":data[i].dlrzjh,
                                        "certificateType":"shijjk_jmhkbxz",
                                        "gzlslid": gzlslid
                                    });
                                }
                            }else if (data[i].qlrlb == '2'){
                                dataList.push({
                                    "zjh":data[i].zjh,
                                    "certificateType":"shijjk_jmhkbxz",
                                    "gzlslid": gzlslid
                                });
                            }
                        }

                        searchData = dataList;
                    }
                    // 展示查询条件，加载表格内容
                    generateTable(searchData, false);

                    // 查询条件不为空进行查询
                    if(searchData &&searchData.length >0){
                        search();
                    }
                },
                error: function (xhr, status, error) {
                    removeModal();
                    delAjaxErrorMsg(xhr)
                }
            });
        }


        /**
         * 重置清空查询条件
         */
        $('#reset').on('click', function () {
            searchData=[];
        });
    });

});



