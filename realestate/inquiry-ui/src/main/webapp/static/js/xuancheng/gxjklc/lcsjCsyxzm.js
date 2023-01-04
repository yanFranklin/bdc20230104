/**
 * @author "<a href="mailto:wangyaqing@gtmap.cn>wangyaqing</a>"
 * @version 1.0, 2020/11/13
 * @description 卫健委（宣城）-出生医学证明查询
 */
// 查询参数
var searchData = [];
var key = "";
var gzlslid = $.getUrlParam("gzlslid");
var jkname = $.getUrlParam("jkname");
layui.use(['jquery', 'element', 'form', 'table', 'laydate'], function () {
    //获取字典
    var $ = layui.jquery;
    var form = layui.form;
    var laytpl = layui.laytpl;
    form.render();

    $(function () {
        /**
         * 点击查询
         */
        $('#search').on('click', function () {
            search();
        });
        // 回车查询
        $('.required').on('keydown', function (event) {
            if (event.keyCode == 13) {
                search();
            }
        });

        //查询
        function search() {
            // 获取查询参数
            var obj = {
                    "cxywcs": [],
                    "loadpage": false
                },
                list = {};
            var cszmbh = $('#cszmbh').val();
            var mqxm = $('#mqxm').val();
            var mqzjbm = $('#mqzjbm').val();
            if (!isNotBlank(cszmbh) && !isNotBlank(mqxm) && !isNotBlank(mqzjbm)) {
                warnMsg('请输入必要查询条件');
                return false;
            }
            list.cszmbh = $('#cszmbh').val();
            list.mqxm = $('#mqxm').val();
            list.mqzjbm = $('#mqzjbm').val();
            obj.cxywcs.push(list);

            addModel();
            // 重新请求
            $.ajax({
                url: "/realestate-inquiry-ui/rest/v1.0/gx/gjpt/naturalResources/csyxzm",
                type: "POST",
                data: JSON.stringify(obj),
                contentType: 'application/json',
                success: function (data) {
                    dealCxjgxx("success", jkname);
                    key = saveJkDataToRedis(data);
                    removeModal();
                    if (data) {
                        generateTable(data);
                    }
                },
                error: function (xhr, status, error) {
                    dealCxjgxx("fail", jkname);
                    removeModal();
                    delAjaxErrorMsg(xhr)
                }
            });
        }

        /**
         * 重置清空查询条件
         */
        $('#reset').on('click', function () {
        });


        function generateTable(data) {
            var getTpl = tableTpl.innerHTML;

            laytpl(getTpl).render(data, function (html) {
                $('.bdc-table-box tbody').html(html);
            });
        }

        //上传PDF
        $(".upload-pdf").click(function () {
            uploadPdf(gzlslid, "sjcsyxzm", "卫健委-出生医学证明查询", key, "省级数据共享交换平台");
        });

    });

});