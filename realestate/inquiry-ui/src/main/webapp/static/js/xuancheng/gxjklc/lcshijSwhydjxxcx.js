/**
 * @author "<a href="mailto:yousiyi@gtmap.cn>yousiyi</a>"
 * @version 1.0, 2020/11/17
 * @description 民政部涉外婚姻登记信息查询接口
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
                        searchData = {
                            "cxywcs": []
                        };
                        for (var i = 0; i < data.length; i++) {
                            searchData.cxywcs.push({
                                "zjh":data[i].zjh,
                                "xm": data[i].qlrmc
                            });
                            if (data[i].dlrmc && data[i].dlrzjh){
                                searchData.cxywcs.push({
                                    "zjh":data[i].dlrzjh,
                                    "xm": data[i].dlrmc
                                });
                            }
                        }
                    }
                    // 展示查询条件
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
            var resultData=[];
            addModel();
            if(searchData.cxywcs &&searchData.cxywcs.length >0) {
                for (var i = 0; i < searchData.cxywcs.length; i++) {
                    // 获取查询参数
                    var param = {
                        zjh : searchData.cxywcs[i].zjh,
                        xm:searchData.cxywcs[i].xm
                    };
                    // 重新请求
                    $.ajax({
                        url:"/realestate-inquiry-ui/rest/v1.0/gx/gjpt/naturalResources/swhydjxxcx",
                        type: "POST",
                        data: JSON.stringify(param),
                        contentType: 'application/json',
                        async:false,
                        success: function (data) {
                            if(data  && data.length>0) {
                                for(var i=0;i<data.length;i++){
                                    data[i].xm = param.xm;
                                    data[i].zjh = param.zjh;
                                    data[i].op_type = optype(data[i].op_type);
                                    data[i].id_type_man = idtype(data[i].id_type_man);
                                    data[i].id_type_woman = idtype(data[i].id_type_woman);
                                    resultData.push(data[i]);
                                }
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
            uploadPdf(gzlslid,"shijswhydjxxcx","民政部-涉外婚姻登记信息查询",key,"市级共享交换平台");
        });



        function optype(op_type){
            if(op_type == "INR"){
                return "结婚";
            }

            if(op_type == "IA"){
                return "结婚";
            }
            if(op_type == "IB"){
                return "离婚";
            }
            if(op_type == "ICA"){
                return "补发结婚证";
            }
            if(op_type == "ICB"){
                return "补发离婚证";
            }
            if(op_type == "ID"){
                return "撤销婚姻";
            }
            return op_type;
        }


        function idtype(id_type){
            if(id_type == "1"){
                return "内地居民";
            }
            if(id_type == "2"){
                return "香港居民";
            }
            if(id_type == "3"){
                return "澳门居民";
            }
            if(id_type == "4"){
                return "台湾居民";
            }
            if(id_type == "5"){
                return "华侨或出国人员";
            }
            if(id_type == "6"){
                return "外国人";
            }
            if(id_type == "7"){
                return "军人";
            }
            return id_type;
        }
    });



});



