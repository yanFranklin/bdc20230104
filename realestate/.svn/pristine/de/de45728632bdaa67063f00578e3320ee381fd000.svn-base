/**
 * @author "<a href="mailto:yousiyi@gtmap.cn>yousiyi</a>"
 * @version 1.0, 2020/11/17
 * @description 省公安厅-居民户成员信息在线查询
 */
// 查询参数
var gzlslid = $.getUrlParam("gzlslid");
var formStateId = $.getUrlParam("formStateId");
var qlrlx = '1';
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

        //getSearch();

        //getStateById(false, formStateId, 'wjwswyxzmjy');

        $('#search').on('click', function () {
            // 获取查询条件
            var patientname = $(this).parents('.layui-form-item').find('#patientname').val();
            var idcardcord = $(this).parents('.layui-form-item').find('#idcardcord').val();

            // 判空
            if (!isNotBlank(patientname) || !isNotBlank(idcardcord)) {
                warnMsg('请输入必要查询条件');
                return false;
            }

            // 组织查询条件
            var getData ={
                "cxywcs":[]
            };
            getData.cxywcs.push({"patientname": patientname, "idcardcord": idcardcord});
            searchData = getData;

            // 展示查询条件，加载表格内容
            generateTable(searchData,false);

            // 查询条件不为空进行查询
            if(searchData &&searchData.cxywcs &&searchData.cxywcs.length >0){
                search();
            }
        });

        //查询
        function search() {
            var resultData =[];

            addModel();
            if(searchData.cxywcs.length >0){
                var saveRedisData = [];
                for (var i = 0; i < searchData.cxywcs.length; i++) {
                    var result ={};
                    // 获取查询参数
                    var param = {
                        "cxywcs": []
                    };
                    param.cxywcs.push({
                        idcardcord : searchData.cxywcs[i].idcardcord,
                        patientname : searchData.cxywcs[i].patientname,
                    });
                    // 重新请求
                    $.ajax({
                        url: "/realestate-inquiry-ui/rest/v1.0/gx/naturalResources/wjwswyxzmjy",
                        type: "POST",
                        data: JSON.stringify(param),
                        contentType: 'application/json',
                        async:false,
                        success: function (data) {
                            console.log("前台返回数据：");
                            console.log(data);
                            if(data && data.length > 0){
                                data[0].idcardcord = searchData.cxywcs[i].idcardcord;
                                data[0].patientname = searchData.cxywcs[i].patientname;
                                data[0].cxjg =  formatMb(data[0].cxjg);
                                saveRedisData = saveRedisData.concat(data)
                                result =data[0];
                            }else {
                                warnMsg('未查询到结果!');
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

                    resultData.push(result);
                }
            }

            key =saveJkDataToRedis(saveRedisData);
            removeModal();
            if(resultData.length >0) {
                console.log('resultData');

                console.log(resultData);
                generateTable(resultData, true);
            }
        }

        function generateTable(data, isSearch) {
            $('#lcsbj').removeClass('layui-hide');
            if (isSearch) {
                var getTpl = tableTpl.innerHTML;
            } else {
                var getTpl = cxywcsTpl.innerHTML;
            }

            laytpl(getTpl).render(data, function (html) {
                $('.bdc-table-box tbody').html(html);
            });
            form.render();
        }

        function formatMb(xbdm) {
            switch (xbdm) {
                case "1":
                    return "校验成功";
                case "0":
                    return "校验失败";
            }
        };
    });

});



