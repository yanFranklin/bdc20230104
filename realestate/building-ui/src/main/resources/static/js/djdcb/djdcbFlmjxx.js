$("#flmjxx").click(function(){
    if($("#flmjxxInit").val()){
        return;
    }
    //获取数据
    addModel();
    $("#flmjxxInit").val("1");
    layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl'], function () {
        var tableConfig = {
            url: '../djdcb/cbzdflmj?bdcdyh='+bdcdyh+"&qjgldm="+qjgldm //数据接口
            , cols: [[
                {field: 'dldm', title: '地类名称',
                    templet: function (d) {
                        return convertZdDmToMc("SZdDldmDO", d.dldm);
                    }},
                {field: 'qsxz', title: '权属性质',
                    templet: function (d) {
                        return convertZdDmToMc("SZdTdsyqlxDO", d.qsxz);
                    }},
                {field: 'jlrq', title: '记录日期'},
                {field: 'bz', title: '备注'}
            ]],
            page:false,
            parseData: function (res) { //res 即为原始返回的数据
                if(res && res.content.length == 1 && res.content[0] == null){
                    res.content = [];
                }
                return res;
            },
            done: function(res, curr, count){
                if(!res.hasContent){
                    $("#flmjxxTable .layui-table-page").addClass("layui-hide");
                    $('#flmjxxTable .layui-table-body .layui-none').html('<img src="../static/lib/bdcui/images/table-none.png" alt="">无数据');
                }
                removeModal();
                // layer.closeAll();
            }
        };
        //加载表格
        loadDataTablbeByUrl("#flmjxxList", tableConfig);
    })
})
