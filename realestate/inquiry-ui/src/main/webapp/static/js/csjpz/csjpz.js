/*
* 长三角自定义查询页面配置
* */
var form;
layui.use(['table', 'laytpl', 'laydate', 'layer', 'form', 'upload'], function () {
    var $ = layui.jquery,
        table = layui.table,
        layer = layui.layer,
        upload = layui.upload;
    form = layui.form;

    var BASE_URL = '/realestate-inquiry-ui/rest/v1.0';
});

function addpz() {
    //弹框页面
    layer.open({
        type: 2,
        shade: false,
        shadeClose: true,
        isOutAnim: false,
        content: "../csjpz/editCsjpz.html",
        area: ['960px', '550px'],
        title: '新增长三角业务配置',
    });
}

/**
 * @param
 * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @description 删除配置
 * @date : 2022/5/10 13:49
 */
function deletepz(obj, data) {
    var pzid = data.PZID;
    //是否确认删除
    var deleteIndex = layer.open({
        type: 1,
        skin: 'bdc-small-tips',
        title: '确认删除',
        area: ['320px'],
        content: '是否确认删除？',
        btn: ['确定', '取消'],
        btnAlign: 'c',
        yes: function () {
            addModel();
            $.ajax({
                url: "/realestate-inquiry-ui/rest/v1.0/csjpz?pzid=" + pzid,
                type: 'DELETE',
                success: function (data) {
                    removeModal();
                    setTimeout(removeModal(), 100);
                    layui.table.reload('pageTable', {page: {curr: 1}});
                    layer.msg("删除成功");
                }, error: function (xhr, status, error) {
                    removeModal();
                }
            })
            layer.close(deleteIndex);
        },
        btn2: function () {
            //取消
            layer.close(deleteIndex);
        }
    });
}


/**
 * @param
 * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @description 编辑配置
 * @date : 2022/5/10 13:49
 */
function editpz(obj, data) {
    //弹框页面
    var pzid = data.PZID;
    layer.open({
        type: 2,
        shade: false,
        shadeClose: true,
        isOutAnim: false,
        content: "../csjpz/editCsjpz.html?pzid=" + pzid,
        area: ['960px', '550px'],
        title: '编辑长三角业务配置',
    });
}