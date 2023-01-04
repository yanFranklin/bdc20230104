/**
 * 自定义查询 “征迁：信息汇总” 扩展JS
 */

/**
 * 当前页面隐藏查询条件
 */
$(".bdc-search-box").hide();
$(".bdc-percentage-container").css("padding-top", "0");

/**
 * 将父页面的查询条件传递进来进行查询
 */
var queryParam;
setTimeout(function () {
    var data = layui.data('zqxxhz');
    if(data && data.data) {
        queryParam = data.data;
        if(!isNullOrEmpty(queryParam)) {
            $.ajax({
                url: "/realestate-inquiry-ui/dtcx/get/zqxxhz",
                type: "get",
                success: function (data) {
                    if(!data || isNullOrEmpty(data.cxid)) {
                        warnMsg("未配置zqxxhz自定义查询，请联系管理员");
                    } else {
                        queryParam = JSON.parse(queryParam);
                        queryParam.cxdh = data.cxdh;
                        queryParam.cxid = data.cxid;
                        tableReload('pageTable', {data: JSON.stringify(queryParam)}, dataUrl);
                    }
                }
            });
        }
    }
}, 500);

/**
 * 导出汇总Excel
 * @param obj
 * @param data
 */
function dchzb(obj, data) {
    var xxhzForm = $("<form>");
    xxhzForm.attr("id", "xxhzForm");
    xxhzForm.attr("style", "display:none");
    xxhzForm.attr("target", "export");
    xxhzForm.attr("method", "post");
    xxhzForm.attr("action", '/realestate-inquiry-ui/rest/v1.0/zq/cqcx/xxhz');

    var dataInput = $("<input>");
    dataInput.attr("type", "hidden");
    dataInput.attr("name", "param");
    dataInput.attr("value", JSON.stringify(queryParam));
    xxhzForm.append(dataInput);

    xxhzForm.trigger("submit");
    $("body").append(xxhzForm);
    xxhzForm.submit();
    $("#xxhzForm").remove();
}