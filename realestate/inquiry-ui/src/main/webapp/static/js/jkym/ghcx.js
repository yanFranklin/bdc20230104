/**
 * @author "<a href="mailto:yaoyi@gtmap.cn>yaoyi</a>"
 * @version 1.0, 2021/8/30
 * @description 南通过户信息查询结果js
 */
// 查询参数
var key ="";
var searchData={};
layui.use(['jquery', 'element', 'form', 'table', 'laydate'], function () {
    //获取字典
    var $ = layui.jquery;
    var form = layui.form;
    var laytpl = layui.laytpl;

    form.render();

    $(function () {

        $('#search').on('click', function () {
            // 获取查询条件
            var contractId = $(this).parents('.layui-form-item').find('#contractId').val();
            // 判空
            if (!isNotBlank(contractId)) {
                warnMsg('请输入必要查询条件');
                return false;
            }
            // 组织查询条件
            searchData = {
                "cxywcs": []
            };
            searchData.cxywcs.push({"contractId": contractId});

            // 展示查询条件，加载表格内容
            generateTable(searchData, false);

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

                // 重新请求
                $.ajax({
                    url: "/realestate-inquiry-ui/rest/v1.0/nantong/sjpt/dianghxx",
                    type: "GET",
                    data: {
                        "contractId": searchData.cxywcs[0].contractId,
                    },
                    contentType: 'application/json',
                    async:false,
                    success: function (data) {
                        removeModal();
                        successMsg("查询成功");
                        // 结果数据不为空，重新加载表格
                        if(isNotBlank(data)){
                            resultData.push(data);
                        }
                    },
                    error: function (xhr, status, error) {
                        removeModal();
                        delAjaxErrorMsg(xhr)
                    }
                });
            }
            removeModal();
            if(resultData.length >0) {
                // key =saveJkDataToRedis(resultData);
                generateTable(resultData, true);
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
           // uploadPdf(gzlslid,"ghjgcx","过户结果查询",key,"市级共享交换平台");
        });


    });

});

function fmtsqzt(data){
    if(isNotBlank(data)){
        if(data == "2"){
            return "过户中";
        }
        if(data == "3"){
            return "过户失败";
        }
        if(data == "4"){
            return "过户成功";
        }
    }
    return "";
}