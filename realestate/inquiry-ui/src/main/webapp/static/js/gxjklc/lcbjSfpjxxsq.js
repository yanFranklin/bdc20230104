/**
 * @author "<a href="mailto:yousiyi@gtmap.cn>yousiyi</a>"
 * @version 1.0, 2021/02/03
 * @description 最高法-司法判决信息查询
 */
// 查询参数
var gzlslid= $.getUrlParam("gzlslid");
var jkname= $.getUrlParam("jkname");
var searchData=[];
//获取结果数据
var jgfkData=[];
var key ="";
layui.use(['jquery', 'element', 'form', 'laydate', 'laytpl', 'layer'], function () {
    //获取字典
    var $ = layui.jquery;
    var form = layui.form;
    var layer = layui.layer;
    var laytpl = layui.laytpl;

    form.render();

    $(function () {

        // 监听 查询申请
        $(document).on('click', '.get-cxsq', function () {
            var ajbh = $('.layui-table input[name="ajbh"]').val();
            if(isNotBlank(ajbh)){
                searchData ={
                    "paramDTOList":[
                        {
                            "ajbh":ajbh
                        }
                    ]
                };
                search();
            }else {
                warnMsg('请输入案件编号');
            }

        });


        // 监听 获取反馈结果
        $(document).on('click', '.get-fkjg', function () {
            var cxqqdh = $('.layui-table input[name="cxqqdh"]').val();
            if(isNotBlank(cxqqdh)){
                searchJgfk(cxqqdh);
            }else {
                warnMsg('未获取到查询请求单号');
            }

        });

        //监听 文书内容
        $(document).on('click', '#wsnr', function () {
            var wsnr = jgfkData.wsDTO.nr;
            var index = layer.open({
                type: 1,
                title: "文书内容",
                area: ['1300px', '600px'],
                fixed: false, //不固定
                maxmin: true, //开启最大化最小化按钮
                content: wsnr,
                cancel: function () {
                    //刷新页面
                }
            });
            layer.full(index);
        });

        //查询
        function search() {
            addModel();
            // 重新请求
            $.ajax({
                url: "/realestate-inquiry-ui/rest/v1.0/gx/naturalResources/supremecourt/decisionApply",
                type: "POST",
                data: JSON.stringify(searchData),
                contentType: 'application/json',
                success: function (data) {
                    removeModal();
                    if (data && data != null && data.length > 0 && data[0].dataDTO.ajDTOList.length > 0) {
                        var resultData = [];
                        for (var aj = 0; aj < data[0].dataDTO.ajDTOList.length; aj++) {
                            var result = data[0].dataDTO.ajDTOList[aj];
                            if (result.wsDTO) {
                                result.wsnr = result.wsDTO.nr;
                            } else {
                                result.wsnr = "";
                            }
                            resultData.push(result);
                        }
                        key = saveJkDataToRedis(resultData);
                        jgfkData = data[0].dataDTO.ajDTOList[0];
                        if (data[0].dataDTO.ajDTOList[0].ajDsrDTOList && data[0].dataDTO.ajDTOList[0].ajDsrDTOList.length > 0) {
                            jgfkData.rowLength = data[0].dataDTO.ajDTOList[0].ajDsrDTOList.length + 4;
                        } else {
                            jgfkData.rowLength = 5;
                        }
                    }
                    generateTable(jgfkData);
                },
                /*success: function (data) {
                    dealCxjgxx("success",jkname);
                    removeModal();

                    if(data && data!=null && data.length>0){
                        if(isNotBlank(data[0].cxqqdh)){
                            // 将查询结果带入表格
                            $('.layui-table input[name="cxqqdh"]').val(data[0].cxqqdh);
                            $('.layui-table .get-fkjg').removeClass('bdc-hide');
                        }
                    }
                },*/
                error: function (xhr, status, error) {
                    dealCxjgxx("fail",jkname);
                    removeModal();
                    delAjaxErrorMsg(xhr)
                }
            });
        }

        // 获取司法判决信息结果反馈
        function searchJgfk(cxqqdh) {
            var obj={
                "slbh": "",
                "cxqqdhList": [
                    cxqqdh
                ],
                "loadpage": false
            };
            addModel();
            // 重新请求
            $.ajax({
                url:"/realestate-inquiry-ui/rest/v1.0/gx/naturalResources/supremecourt/decisionResponse",
                type: "POST",
                data: JSON.stringify(obj),
                contentType: 'application/json',
                success: function (data) {
                    removeModal();
                    if(data && data!=null && data.length>0 && data[0].dataDTO.ajDTOList.length>0){
                        var resultData =[];
                        for (var aj = 0; aj < data[0].dataDTO.ajDTOList.length; aj++) {
                            var result =data[0].dataDTO.ajDTOList[aj];
                            if(result.wsDTO) {
                                result.wsnr = result.wsDTO.nr;
                            }else{
                                result.wsnr="";
                            }
                            resultData.push(result);
                        }
                        key =saveJkDataToRedis(resultData);
                        jgfkData = data[0].dataDTO.ajDTOList[0];
                        if(data[0].dataDTO.ajDTOList[0].ajDsrDTOList &&data[0].dataDTO.ajDTOList[0].ajDsrDTOList.length > 0){
                            jgfkData.rowLength = data[0].dataDTO.ajDTOList[0].ajDsrDTOList.length + 4;
                        }else{
                            jgfkData.rowLength = 5;
                        }
                    }
                    generateTable(jgfkData);
                },
                error: function (xhr, status, error) {
                    removeModal();
                    delAjaxErrorMsg(xhr)
                }
            });
        }

        function generateTable(data) {
            var getTpl = jgxxTpl.innerHTML;
            laytpl(getTpl).render(data, function (html) {
                $('.bdc-table-box tbody#jgxx').html(html);
            });
            form.render();
        }

        //上传PDF
        $(".upload-pdf").click(function () {
            uploadPdf(gzlslid,"bjsfpjxx","最高法-司法判决信息查询申请",key,"自然资源部");
        });

    });
});



