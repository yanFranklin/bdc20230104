/**
 *
 */
function testJs() {
    var data = JSON.stringify(checkeddata);

    if (data === {}) {
        alert("空数据");
    } else {
        alert(data);
    }
}

function test2(obj, data) {

    if (data === {}) {
        alert("空数据");
    } else {
        alert("受理编号："+data.SLBH);
    }
}

layui.use(['table', 'layer'], function () {
    var table = layui.table;
    var layer = layui.layer;
    var $ = layui.jquery;

    function loadComplete(res) {
        LayUIDataTable.SetJqueryObj($);

        var currentRowDataList = LayUIDataTable.ParseDataTable(function (index, currentData, rowData) {
        });

        $.each(currentRowDataList, function (index, obj) {
            if (obj['slbh'] && obj['slbh'].value === "201903060032") {
                obj['slbh'].row.css("background-color", "#FAB000");
            }
        })
    }
});

function loadComplete(res) {
    alert(JSON.stringify(res));
    LayUIDataTable.SetJqueryObj($);

    var currentRowDataList = LayUIDataTable.ParseDataTable(function (index, currentData, rowData) {
    });

    $.each(currentRowDataList, function (index, obj) {
        if (obj['slbh'] && obj['slbh'].value === "201903060032") {
            obj['slbh'].row.css("background-color", "#FAB000");
        }
    })
}