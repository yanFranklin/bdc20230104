/**
* @return
* @author <a href = "mailto:fanghao@gtmap.cn">fanghao</a>
* @description 出具委托书js
*/
var form, $, table, layer;
var dylx = ['cjwts'];
setDypzSession(dylx);
layui.use(['jquery', 'formSelects', 'layer', 'element', 'form', 'table', 'laytpl'], function () {
    form = layui.form;
    $ = layui.jquery;
    table = layui.table;
    layer = layui.layer;
});

/*
* 新增按钮
* */
function addWts() {
    debugger;
    layer.open({
        type: 2,
        title: '新增委托书',
        anim: -1,
        shadeClose: true,
        maxmin: true,
        shade: false,
        area: ['1100px','520px'],
        offset: 'auto',
        content: ["/realestate-inquiry-ui/view/cjwts/addWts.html"],
        end: function () {
            table.reload('pageTable');
        }
    })
}

function dy(res) {
    debugger;
    console.log(res.data.WTSBH);
    var wtsArr = {wtsbh:res.data.WTSBH};
    var arr = new Array();
    arr.push(wtsArr)
    var sfcg = printData('cjwts',arr);
    if (sfcg) {
        //更新委托书状态
        $.ajax({
            url: '/realestate-inquiry-ui/rest/v1.0/updateWts',
            type: 'PUT',
            data: {
                wtsbh : res.data.WTSBH
            },
            success: function (res) {
                successMsg("成功");
            }

        })
    }
}