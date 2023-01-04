/**
 * 案卷查询台账
 */
var reverseList = ['ZL'];
var dylxArr = ["zxqc"];
var sessionKey = "zxqc";
setDypzSession(dylxArr, sessionKey);
layui.use(['table', 'laytpl', 'laydate', 'layer', 'form'], function () {
    var $ = layui.jquery,
        layer = layui.layer,
        form = layui.form,
        table = layui.table;

    var zxqc = layui.data('zxqc');
    var data = JSON.parse(zxqc.data);

    table.render({
        elem: '#zxqcTable',
        toolbar: '#toolbarDemo',
        id: 'zxqcTable',
        title: '案卷交接清单',
        defaultToolbar: [],
        cols: [[
            {type: 'numbers', fixed: 'left', title: '序号'},
            {field: 'XMID', title: 'xmid', hide: true},
            {field: 'SLBH', title: '受理编号', width: 120},
            {field: 'QLR', title: '权利人', width: 150},
            {field: 'ZL', title: '坐落', width: 250},
            {field: 'BDCQZH', title: '不动产权证号', width: 220},
            {field: 'BDCDYH', title: '不动产单元号', width: 220}
        ]],
        data: data,
        page: true,
        done: function () {
            reverseTableCell(reverseList);
        }
    });

    table.on('toolbar(zxqcTable)', function (obj) {
        switch (obj.event) {
            case 'print':
                print();
                break;
        }
    });

    function print() {
        if(!data || data.length == 0) {
            warnMsg("请先选择需要打印的记录");
            return;
        }
        // 缓存要打印的权属证明参数信息
        var param = new Array();
        data.forEach(function (item) {
            var paramItem = {};
            paramItem.xmid = item.XMID;
            param.push(paramItem);
        });

        addModel();
        $.ajax({
            url: "/realestate-inquiry-ui/rest/v1.0/print/zxqc/param",
            type: "POST",
            data: JSON.stringify(param),
            contentType: 'application/json',
            dataType: 'text',
            success: function (text) {
                if (!isNullOrEmpty(text)) {
                    var dataUrl = getIP() + "/realestate-inquiry-ui/rest/v1.0/print/zxqc/" + text + "/xml";
                    var mburl = getIP() + "/realestate-inquiry-ui/static/printModel/zxqc.fr3";
                    var appName = "realestate-inquiry-ui";
                    printChoice("zxqc", appName, dataUrl, mburl, false, "dyqc");
                } else {
                    failMsg("打印失败，请重试");
                }
            },
            error: function (err) {
                failMsg("打印失败，请重试");
            },
            complete:function () {
                removeModal();
            }
        });
    }
})

function closeWin(){
    if(parent && parent.layer) {
        parent.layer.closeAll();
    }
}