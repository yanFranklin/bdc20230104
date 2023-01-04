/**
 * 房屋拆迁 js
 */

/**
 * 房屋拆迁
 */
function fwcq(){
    if (!checkeddata || checkeddata.length ===0) {
        layer.alert("请选择需要拆迁的数据！", {title: '提示'});
        return false;
    }

    var zxsqList =[];
    console.log(checkeddata);
    for (var i = 0; i < checkeddata.length; i++){
        if(isNotBlank(checkeddata[i]['SQXXID'])){
            warnMsg("选中的数据存在已拆迁的数据,请重新选择!");
            return false;
        }
        var zxsq ={};
        zxsq.bdcdyh =checkeddata[i]['BDCDYH'];
        zxsq.xmid =checkeddata[i]['XMID'];
        zxsq.bdcqzh =checkeddata[i]['BDCQZH'];
        zxsq.zl =checkeddata[i]['ZL'];
        zxsqList.push(zxsq);
    }

    addModel();
    $.ajax({
        url: "/realestate-inquiry-ui/rest/v1.0/zq/batch/zxsq",
        type: "POST",
        contentType: "application/json;charset=UTF-8",
        data: JSON.stringify(zxsqList),
        dataType: "text",
        success: function (data) {
            if(!isNullOrEmpty(data)){
                layui.table.reload('pageTable', {page: {curr: 1}});
                successMsg("操作成功");

            } else {
                failMsg("保存注销申请信息失败，请重试");
            }
        },
        error: function (xhr, status, error) {
            failMsg("保存注销申请信息失败，请重试");
        },
        complete: function () {
            removeModal();
        }
    });



}

//表格加载完操作
function loadComplete() {
    //翻转
    var reverseList = ['ZL'];
    reverseTableCell(reverseList);
    var getTd = $('.layui-table-view .layui-table td');
    for (var i = 0; i < getTd.length; i++) {
        if ($(getTd[i]).attr('data-field') == "FWCQZT") {
            var getTdCell = $(getTd[i]).find('.layui-table-cell');
            if (getTdCell[0].innerText == '已拆迁') {
                $(getTd[i]).children('div').empty();
                $(getTd[i]).children('div').append('<span class="bdc-fwcq">已拆迁 </span>');
            }
        }
    }
    var colorList = [];
    $.ajax({
        url: '/realestate-inquiry-ui/rest/v1.0/status/color',
        type: "GET",
        async: false,
        success: function(data) {
            for(var i in data) {
                if (data[i]) {
                    colorList.push({name: i, color: '#333', background: data[i]});
                }
            }
            changeTrBackgroundExceptRight(colorList, false);
        }
    });

}