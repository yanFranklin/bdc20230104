layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl'], function() {
    var $ = layui.jquery;
    var element = layui.element;
    var form = layui.form;
    var table = layui.table;
    var laytpl = layui.laytpl;

    //不动产规则限制验证例外的表头
    var unitTableTitle = [
        {type: 'checkbox', fixed: 'left', width: 50},
        {type: 'numbers', title: '序号' ,width:70}
        ,{field: 'lwwh', title: '例外文号', width: 250}
        ,{field: 'gzmc', title: '例外规则', width:230}
        ,{field: 'lwyy', title: '例外原因'}
        ,{field: 'czjqip', title: '操作机器IP', width:150}
        ,{field: 'czry', title: '操作人员', width:150}
        ,{field: 'czsj', title: '操作时间', width:200}
    ];

    var tableConfig = {
        id: 'lwid',
        toolbar: '#toolbar',
        cols: [unitTableTitle],
        defaultToolbar: ["filter"]
    };

    var url = "/realestate-inquiry-ui/rest/v1.0/bdcgzlw";

    //查询表单信息
    form.on("submit(queryGzXzYzLw)", function (data) {
        tableReload('lwid', data.field, url);
        return false;
    });

    //加载表格
    loadDataTablbeByUrl('#gzxzyzlwList', tableConfig);
    //表格初始化
    table.init('dataTable',tableConfig)
    tableReload('lwid', null, url);

    //头工具栏事件
    table.on('toolbar(dataTable)', function(obj){
        var checkStatus = table.checkStatus(obj.config.id);
        if('delete' == obj.event){
            deleteGzLw(checkStatus.data);
        }
    });

    /**
     * 删除证书印制号
     */
    function deleteGzLw(data){
        if(!data || data.length == 0){
            layer.alert("请选择需要删除的记录！", {title: '提示'});
            return;
        }

        var deleteIndex = layer.open({
            type: 1,
            skin: 'bdc-small-tips',
            title: '确认删除',
            area: ['320px'],
            content: '确定要删除所选验证例外规则？',
            btn: ['确定','取消'],
            btnAlign: 'c',
            yes: function(){
                $.ajax({
                    url: url,
                    type: "DELETE",
                    data: JSON.stringify(data),
                    contentType: 'application/json',
                    dataType: "json",
                    success: function (data) {
                        if(data && $.isNumeric(data) && data > 0){
                            layer.msg('删除成功！', {time: 2000});
                            tableReload('lwid', null, url);
                        } else {
                            layer.alert("删除失败，请重试！", {title: '提示'});
                        }
                    },
                    error:function(){
                        layer.alert("删除失败，请重试！", {title: '提示'});
                    }
                });

                layer.close(deleteIndex);
            },
            btn2: function(){
                //取消
            }
        });
    }

    /**
     * 重置
     */
    $('#reset').on('click',function () {
        tableReload('lwid', null, url);
    });
});