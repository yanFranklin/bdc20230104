var type = getQueryString("type");
var sessionKey = getQueryString("sessionKey");

layui.use(['form','jquery','laydate','element','table'],function () {
    var $ = layui.jquery,
        table = layui.table
    //var data = sessionStorage.getItem("xxblparams");
    var data =  JSON.parse(sessionStorage.getItem(sessionKey)) || [];
    var cols = [];
    cols.push(getTableCols());
        table.render({
        elem: '#chooseTable',
        title: '已选数据',
        defaultToolbar: ['filter'],
        even: true,
        cols: cols,
        data: data,
        page: true,
        done: function () {
            $('.layui-table-tool-self').css('right',$('.bdc-export-tools').width()+ 17 + 'px');
            if($('.bdc-table-box .layui-table-main>.layui-table').height() == 0){
                $('.layui-table-body .layui-none').html('<img src="../../static/lib/bdcui/images/table-none.png" alt="">无数据');
            }else {
                $('.layui-table-main.layui-table-body').height($('.bdc-table-box').height() - 131);
                // $('.layui-table-body').height($('.bdc-table-box').height() - 131);
                // $('.layui-table-fixed .layui-table-body').height($('.bdc-table-box').height() - 131 - 17);
            }
        }
    });
});

function getTableCols(){
    var cols = [];
    if(type=="chooseCq"){
        cols = [
            {type: 'checkbox', width: 50, fixed: 'left'},
            {field: 'bdcdyh', title: '不动产单元号', minWidth:300,templet: function (obj) {
                    if (!isNullOrEmpty(obj.bdcdyh)) {
                        return '<span style="color:#1d87d1; cursor:pointer" title="' + obj.bdcdyh + '">' + formatBdcdyh(obj.bdcdyh) + '</span>'
                    } else {
                        return '<span></span>';
                    }
                }, align: "center"
            },
            {field: 'ycqzh', title: '不动产权证号', minwidth: 450,align: "center"},
            {field: 'zl', title: '坐落', minwidth: 450,align: "center"},
        ];
    }else if(type=="chooseCf"){
        cols = [
            {type: 'checkbox', width: 50, fixed: 'left'},
            {field: 'bdcdyh', title: '不动产单元号', minWidth:300,templet: function (obj) {
                    if (!isNullOrEmpty(obj.bdcdyh)) {
                        return '<span style="color:#1d87d1; cursor:pointer" title="' + obj.bdcdyh + '">' + formatBdcdyh(obj.bdcdyh) + '</span>'
                    } else {
                        return '<span></span>';
                    }
                }, align: "center"
            },
            {field: 'cfwh', title: '查封文号', minwidth: 450,align: "center"},
            {field: 'zl', title: '坐落', minwidth: 450,align: "center"},
        ];
    }else{
        cols = [
            {type: 'checkbox', width: 50, fixed: 'left'},
            {field: 'bdcdyh', title: '不动产单元号', minWidth:300,templet: function (obj) {
                    if (!isNullOrEmpty(obj.bdcdyh)) {
                        return '<span style="color:#1d87d1; cursor:pointer" title="' + obj.bdcdyh + '">' + formatBdcdyh(obj.bdcdyh) + '</span>'
                    } else {
                        return '<span></span>';
                    }
                }, align: "center"
            },
            {field: 'qlr', title: '权利人', minwidth: 450,align: "center"},
            {field: 'zl', title: '坐落', minwidth: 450,align: "center"},
        ];
    }
    return cols;
}

