/**
 * @author <a href ="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @description 已缴费查询
 */
var BASE_URL = '/realestate-inquiry-ui/rest/v1.0/';


function tableHasLoad(res) {
    //处理落宗状态，匹配状态，限制状态的值
    if (res && res.content) {
        res.content.forEach(function (v, index) {
            $(".layui-table-body tr[data-index='" + index + "']")
                .find("td[data-field='SLBH']")
                .find("div")
                .html('<span style="color:#1d87d1;cursor:pointer" onclick="jumpDetail(\''+ v.GXGZLSLID +'\')"> ' +v.SLBH+ ' </span>');
        });
    }
}

//受理编号点击跳转到详情页面
function jumpDetail(processId) {
    if(isNullOrEmpty(processId)) {
        layer.msg('当前记录数据，无法查看！');
        return;
    }
    window.open('/realestate-portal-ui/view/new-page.html?type=lsgx&processinsid=' + processId );
}
