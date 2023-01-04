/**
 * @author "<a href="mailto:yousiyi@gtmap.cn>yousiyi</a>"
 * @version 1.0, 2021/02/03
 * @description 综合法人库-司法判决信息-案件当事人信息
 */
// 查询参数
var gzlslid = $.getUrlParam("gzlslid");
var searchData = {};
//获取结果数据
var jgfkData = {};
var key = "";
var jkname= $.getUrlParam("jkname");
layui.use(['jquery', 'element', 'form', 'laydate', 'laytpl', 'layer'], function () {
    //获取字典
    var $ = layui.jquery;
    var form = layui.form;
    var layer = layui.layer;
    var laytpl = layui.laytpl;

    form.render();

    $(function () {

        // 监听 查询
        $(document).on('click', '.get-sfah', function () {
            var sfah = $('#sfah').val();
            if(isNotBlank(sfah)){
                searchData = {
                    "sfah": sfah
                };
                search();
            }else {
                warnMsg('请输入司法案号');
            }

        });

        initTable();

        function initTable() {
            var getTpl = cxywcsTpl.innerHTML;

            laytpl(getTpl).render({}, function (html) {
                $('.bdc-table-box tbody').html(html);
            });
            $(document).find('input').each(function() {
                $(this).attr('title', $(this).val());
            });
            form.render();
        }

        //查询
        function search() {
            addModel();
            // 重新请求
            $.ajax({
                url:"/realestate-inquiry-ui/rest/v1.0/gx/shijgxCx/zhfrksfpjxxajxx",
                type: "POST",
                data: JSON.stringify(searchData),
                contentType: 'application/json',
                success: function (data) {
                    if (data.code == 200) {
                        successMsg(data.message);
                        if (data.data && data.data != null && data.data.length > 0) {
                            data.data[0].sfah = searchData.sfah;
                            // 渲染表格
                            generateTable(data.data[0]);
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

        function generateTable(data) {
            var getTpl = tableTpl.innerHTML;
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
            uploadPdf(gzlslid,"shijzhfrksfpjxxajxx","综合法人库-司法判决信息",key,"市级共享交换平台");
        });

    });
});