/**
 * @author "<a href="mailto:wangyaqing@gtmap.cn>wangyaqing</a>"
 * @version 1.0, 2021/2/4
 * @description 市场监管总局-企业基本信息查询
 */
// 查询参数
var gzlslid = $.getUrlParam("gzlslid");
var formStateId = $.getUrlParam("formStateId");
var excludeQlrlx = '1';
var key = "";
var searchData = [];
var jkname = $.getUrlParam("jkname");
layui.use(['jquery', 'element', 'form', 'table', 'laydate'], function () {
    //获取字典
    var $ = layui.jquery;
    var form = layui.form;
    var laytpl = layui.laytpl;
    form.render();

    $(function () {

        getStateById(false, formStateId, 'lcsjQyjbxx');

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
            var getData = {
                "cxywcs": []
            };
            getData.cxywcs.push({
                "entname": entname,
                "uniscid": uniscid, "authcode": authcode
            });
            searchData = getData;

            // 展示查询条件，加载表格内容
            generateTable(searchData, false);

            // 查询条件不为空进行查询
            if (searchData && searchData.cxywcs && searchData.cxywcs.length > 0) {
                search();
            }
        });


        //查询
        function search() {
            addModel();
            // 重新请求
            $.ajax({
                url: "/realestate-inquiry-ui/rest/v1.0/gx/sheng/qyxxcx",
                type: "POST",
                data: JSON.stringify(searchData.cxywcs[0]),
                contentType: 'application/json',
                success: function (data) {
                    dealCxjgxx("success", jkname);
                    key = saveJkDataToRedis(data);
                    removeModal();

                    // 结果数据不为空，重新加载表格
                    if (data && data != null && data.length > 0) {
                        // 将需要展示的查询条件加入结果数据
                        for (var i = 0; i < data.length; i++) {
                            for (var j = 0; j < searchData.cxywcs.length; j++) {
                                if (data[i].entname == searchData.cxywcs[j].entname &&
                                    data[i].uniscid == searchData.cxywcs[j].uniscid) {
                                    data[i].entname = searchData.cxywcs[j].entname;
                                    data[i].uniscid = searchData.cxywcs[j].uniscid;
                                }
                                data[i].estdate = getYMDHMS(data[i].estdate);
                                data[i].apprdate = getYMDHMS(data[i].apprdate);
                                data[i].opfrom = getYMDHMS(data[i].opfrom);
                                data[i].opto = getYMDHMS(data[i].opto);
                            }
                        }
                        generateTable(data, true);
                    }
                },
                error: function (xhr, status, error) {
                    dealCxjgxx("fail", jkname);
                    removeModal();
                    delAjaxErrorMsg(xhr)
                }
            });
        }


        function generateTable(data, isSearch) {
            var getTpl = tableTpl.innerHTML;

            laytpl(getTpl).render(data, function (html) {
                $('.bdc-table-box tbody').html(html);
            });
        }

        //上传PDF
        $(".upload-pdf").click(function () {
            uploadPdf(gzlslid, "sjqyjbxx", "市场监管总局-企业基本信息查询", key, "省级数据共享交换平台");
        });


        function getYMDHMS(timestamp) {
            if (isNullOrEmpty(timestamp)) {
                return "";
            }
            let time = new Date(timestamp)
            let year = time.getFullYear()
            let month = time.getMonth() + 1
            let date = time.getDate()
            let hours = time.getHours()
            let minute = time.getMinutes()
            let second = time.getSeconds()

            if (month < 10) {
                month = '0' + month
            }
            if (date < 10) {
                date = '0' + date
            }
            if (hours < 10) {
                hours = '0' + hours
            }
            if (minute < 10) {
                minute = '0' + minute
            }
            if (second < 10) {
                second = '0' + second
            }
            return year + '-' + month + '-' + date + ' ' + hours + ':' + minute + ':' + second
        }

    });


});