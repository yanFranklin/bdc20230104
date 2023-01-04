/**
 * @author "<a href="mailto:yaoyi@gtmap.cn>yaoyi</a>"
 * @version 1.0, 2022/10/18
 * @description 税费统缴对账单查询
 */
// 查询参数
var searchData={};
layui.use(['jquery', 'element', 'form', 'laydate', 'laytpl', 'layer'], function () {
    //获取字典
    var $ = layui.jquery;
    var form = layui.form;
    var layer = layui.layer;
    var laytpl = layui.laytpl;
    var laydate = layui.laydate;


    $(function () {

        laydate.render({
            elem: '#dzrq',
            type:'date',
            format: "yyyyMMdd",
            isInitValue: true,
            value: new Date(),
        });

        $('#search').on('click', function () {
            // 获取查询条件
            var dzrq = $(this).parents('.layui-inline').prev().find('#dzrq').val();
            // 判空
            if (!isNotBlank(dzrq)) {
                warnMsg('请日期后，在进行查询');
                return false;
            }
            // 组织查询条件
            searchData = {
                "cxywcs": {
                    "dzrq": dzrq,
                }
            };

            // 展示查询条件，加载表格内容
            generateTable(searchData, false);

            // 查询条件不为空进行查询
            if(searchData && searchData.cxywcs){
                search();
            }
        });

        //查询
        function search() {
            // 获取查询参数
            var param ={
                "dzrq": searchData.cxywcs.dzrq
            };
            // 重新请求
            $.ajax({
                url: getContextPath()+ "/rest/v1.0/queryByExchange/sftjdzd",
                type: "GET",
                data: param,
                async:false,
                success: function (data) {
                    if(data && data.svcRspSt == "00"){
                        generateTable({
                            "dzrq": searchData.cxywcs.dzrq,
                            "data": data,
                        }, true);
                    } else {
                        generateTable({
                            "dzrq": searchData.cxywcs.dzrq,
                        }, true);
                        warnMsg("调用政融支付平台获取对账单文件失败，" + data.rspInf);
                    }
                },
                error: function (xhr, status, error) {
                    removeModal();
                    delAjaxErrorMsg(xhr)
                }
            });
            removeModal();
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
    });
});



