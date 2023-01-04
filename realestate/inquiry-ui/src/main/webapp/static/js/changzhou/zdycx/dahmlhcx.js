/**
 * 常州：档案号、目录号查询登记历史操作
 */
function djls(obj, data) {
    if(!data || !data.BDCDYH) {
        layer.msg('无不动产单元号数据，无法查看！');
        return;
    }

    window.open('/realestate-register-ui/changzhou/lsgx/bdcdyDjLsgx.html?bdcdyh=' + data.BDCDYH);
}