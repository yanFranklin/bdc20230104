/**
 * @author "<a href="mailto:liaoxiang@gtmap.cn>liaoxiang</a>"
 * @version 1.0, 2021/02/02
 * @description 市场监管总局-企业基本信息验证
 */
// 查询参数
var gzlslid= $.getUrlParam("gzlslid");
var formStateId = $.getUrlParam("formStateId");
var excludeQlrlx= '1';
var searchData=[];
var key ="";
var jkname= $.getUrlParam("jkname");
layui.use(['jquery','form', 'table', 'laydate', 'laytpl'], function () {
    //获取字典
    var $ = layui.jquery;
    var form = layui.form;
    var table = layui.table;
    var laydate = layui.laydate;
    var laytpl = layui.laytpl;


    $(function () {

        getStateById(false, formStateId, 'lcsjQyjbxxyz');

        $('#search').on('click', function () {
            // 获取查询条件
            var entname = $(this).parents('.layui-form-item').find('#entname').val();
            var uniscid = $(this).parents('.layui-form-item').find('#uniscid').val();
            var authcode = $(this).parents('.layui-form-item').find('#authcode').val();

            // 判空
            if (!isNotBlank(entname) || !isNotBlank(uniscid)) {
                warnMsg('请输入必要查询条件');
                return false;
            }

            // 组织查询条件
            var getData ={
                "paramDTOList":[],
            };
            getData.paramDTOList.push({"entname": entname, "uniscid": uniscid});
            searchData = getData;

            // 展示查询条件，加载表格内容
            generateTable(searchData,false);

            // 查询条件不为空进行查询
            if(searchData &&searchData.paramDTOList &&searchData.paramDTOList.length >0){
                search();
            }
        });

        //查询
        function search() {
            addModel();
            //循环查询
            if(searchData.paramDTOList.length >0){
                var resultData =[];
                for (var paramlength = 0; paramlength < searchData.paramDTOList.length; paramlength++) {
                    var param = searchData.paramDTOList[paramlength];
                    $.ajax({
                        url:"/realestate-inquiry-ui/rest/v1.0/gx/sheng/qyxxyz",
                        type: "POST",
                        data: JSON.stringify(param),
                        contentType: 'application/json',
                        async:false,
                        success: function (data) {
                            // 结果数据不为空，重新加载表格
                            if(data ){
                                // 将需要展示的查询条件加入结果数据
                                data.entname = param.entname;
                                data.uniscid = param.uniscid;
                                resultData.push(data);

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
                key =saveJkDataToRedis(resultData);
                if(resultData.length >0) {
                    dealCxjgxx("success",jkname);
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
            uploadPdf(gzlslid,"sjqyjbxxyz","市场监管总局-企业基本信息验证",key,"省级数据共享交换平台");
        });

    });

});