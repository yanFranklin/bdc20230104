/**
 * @author <a href ="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @description 未缴费查询
 */
var BASE_URL = '/realestate-inquiry-ui/rest/v1.0/';

/**
 * 缴款查询
 */
function jkcx(obj, data){
    var sfxxid = data.SFXXID;
    var sftdsyj = data.SFTDSYJ;
    addModel();
    getReturnData("/rest/v1.0/sfxxcx/querySfzt", {sfxxid: sfxxid, sftdsyj: sftdsyj}, "GET", function (res) {
        removeModal();
        if(isNotBlank(res)){
            ityzl_SHOW_SUCCESS_LAYER("查询成功,缴费状态为:" + res.sfztMc);
        }else{
            warnMsg("未获取到缴费状态信息。");
        }
    }, function (xhr) {
        removeModal();
        delAjaxErrorMsg(xhr);
    });
}

function tableHasLoad(res) {
    //处理落宗状态，匹配状态，限制状态的值
    if (res && res.content) {
        res.content.forEach(function (v, index) {
            $(".layui-table-body tr[data-index='" + index + "']")
                .find("td[data-field='SLBH']")
                .find("div")
                .html('<span style="color:#1d87d1;cursor:pointer" onclick="jumpDetail(\''+ v.GZLSLID +'\',\''+ v.XMID +'\')"> ' +v.SLBH+ ' </span>');
        });
    }
}

//受理编号点击跳转到详情页面
function jumpDetail(processId,xmid) {
    if(isNullOrEmpty(processId) || isNullOrEmpty(xmid)) {
        layer.msg('当前记录数据，无法查看！');
        return;
    }

    //window.open('/realestate-accept-ui/rest/v1.0/slym?processInsId='+data.GZLSLID+'&readOnly=true');
    window.open('/realestate-portal-ui/view/new-page.html?type=lsgx&xmid=' + xmid + '&processinsid=' + processId );
}

//同步缴费数据
function jfsj() {

    layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl', 'laydate'], function () {
        laydate = layui.laydate;
        layer.open({
            type: 1,
            title:"选择同步日期",
            content: '<div class="layui-tab-content bdc-tab-content" id="contentTable">\n' +
                '    <!--查询条件-->\n' +
                '    <div class="bdc-search-content">\n' +
                '        <div class="bdc-search-box">\n' +
                '            <!--默认两行的-->\n' +
                '            <form class="layui-form" action="" lay-filter="searchform" onsubmit="return false;">\n' +
                '                <div class="layui-form-item pf-form-item">\n' +
                '                    <div class="layui-inline" style="width: 100%">\n' +
                '                        <label class="layui-form-label">缴费同步日期</label>\n' +
                '                        <div class="layui-input-inline">\n' +
                '                            <input name="jftbsj" id="jftbsj" type="text" autocomplete="off" placeholder=""\n' +
                '                                   class="layui-input search jkrq test-item">\n' +
                '                            <i class="layui-icon layui-icon-close bdc-hide"></i>\n' +
                '                        </div>\n' +
                '                    </div>\n' +
                '                </div>\n' +
                '            </form>\n' +
                '        </div>\n' +
                '    </div>\n' +
                '</div>' +
                ' <script>\n' +
                ' laydate.render({\n' +
                '        elem: \'#jftbsj\',\n' +
                '    }); ' +
                '</script>' ,

            area: ['500px', '300px'],
            offset: 'auto',
            btn: ['确认'],
            yes: function(index, layero){
                var jftbsj = $("#jftbsj").val();
                if(isNullOrEmpty(jftbsj)){
                    layer.msg("选择同步日期");
                    return false;
                }
                addModel();
                getReturnData("/rest/v1.0/sfxxcx/wjftb", {"jftbsj": jftbsj }, "GET", function (res) {
                    removeModal();
                    ityzl_SHOW_SUCCESS_LAYER("同步成功" );
                }, function (xhr) {
                    removeModal();
                    delAjaxErrorMsg(xhr);
                });
                layer.close(index);
            }
        });
    });

}
