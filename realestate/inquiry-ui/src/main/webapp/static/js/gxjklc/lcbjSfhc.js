/**
 * @author "<a href="mailto:yousiyi@gtmap.cn>yousiyi</a>"
 * @version 1.0, 2021/1/23
 * @description 公安部-身份核查
 */
// 查询参数
var gzlslid= $.getUrlParam("gzlslid");
var formStateId = $.getUrlParam("formStateId");
var qlrlx= 1;
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

        getStateById(false, formStateId, 'lcbjSfhc');

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
            var getData ={
                "gzlslid": gzlslid,
                "cxywcs": [],
            };
            getData.cxywcs.push({"xm": xm, "zjh": zjh});
            searchData = getData;

            // 展示查询条件，加载表格内容
            generateTable(searchData,false);

            // 查询条件不为空进行查询
            if(searchData &&searchData.cxywcs &&searchData.cxywcs.length >0){
                search();
            }
        });

        // 根据gzlslid获取查询参数
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
                            "gzlslid":gzlslid,
                            "cxywcs":[]
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
            // 重新请求
            $.ajax({
                url:"/realestate-inquiry-ui/rest/v1.0/gx/naturalResources/gasfhc",
                type: "POST",
                data: JSON.stringify(searchData),
                contentType: 'application/json',
                success: function (data) {
                    removeModal();

                    // 结果数据不为空，重新加载表格
                    if(data.data && data.data!=null && data.data.length>0){
                        dealCxjgxx("success",jkname);
                        key =saveJkDataToRedis(data.data);
                        // 将需要展示的查询条件加入结果数据
                        for(var i=0; i<data.data.length; i++){
                            for(var j=0; j < searchData.cxywcs.length; j++){
                                if(data.data[i].xm ==  searchData.cxywcs[j].xm){
                                    data.data[i].zjh = searchData.cxywcs[j].zjh;
                                }
                            }
                        }
                        generateTable(data.data,true);
                    }else{
                        failMsg(data &&data.msg?data.msg:"查询失败");
                        dealCxjgxx("fail",jkname);
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
            uploadPdf(gzlslid,"bjsfhc","公安部-身份核查",key,"自然资源部");
        });
    });

});



