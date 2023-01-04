// 获取查询内容方法
function getSearchObj(id) {
    // 获取查询内容
    var obj = {};
    $("." + id).each(function (i) {
        var value = $(this).val();
        var name = $(this).attr('name');
        obj[name] = value;
        if (this.tagName === 'SELECT') {
            var selectVal = $(this).find("option:selected").val();
            if (!isNullOrEmpty(selectVal)) {
                obj[name] = selectVal;
            }
        }
    });
    return obj;
}
layui.use(['table'], function () {
    var table = layui.table;
    // 监听已办 排序事件
    table.on('sort(doneTableFilter)', function (obj) {
        // 获取查询内容
        var search = getSearchObj("ybSearch");
        if (obj.type != null) {
            var name = obj.field + "_complete_sort"
            search[name] = obj.type;
        }
        table.reload('doneTableId', {
            initSort: obj,
            where: search, page: {
                curr: 1  //重新从第 1 页开始
            }
        });
    });
})