/**
 * author: <a href="mailto:zhuyong@gtmap.cn>zhuyong</a>
 * version 1.0.0  2019/01/11
 * describe: 证书编号模板配置页面处理JS
 */
layui.use(['table','laytpl','laydate','layer', 'form'],function () {
    var $ = layui.jquery,
        table = layui.table,
        layer = layui.layer,
        form = layui.form;

    var qdxmData;
    $.ajax({
        url: '/realestate-inquiry-ui/rest/v1.0/zsbhmb/zd/qxdm',
        type: "GET",
        dataType: "json",
        timeout : 10000,
        success: function(data) {
            if(data && data.length > 0){
                // 渲染字典项
                $.each(data, function(index,item) {
                    $('#szsxqc').append('<option value="'+item.XZMC +'">'+item.XZMC + '</option>');
                });
            }

            qdxmData = data;
            form.render('select');
            // 下拉选择添加删除按钮
            renderSelectClose(form);
        }
    });

    // 联动对应处理
    form.on('select(szsxqc)', function(data){
        if(qdxmData && qdxmData.length > 0){
            for(var index in qdxmData){
                if(qdxmData[index].XZMC == data.value){
                    $("#qxdm").val(qdxmData[index].XZDM);
                    return;
                }
            }
        }
        form.render('select');
    });

    /**
     * 加载Table数据列表
     */
    table.render({
        elem: '#zsbhmbTable',
        toolbar: '#toolbar',
        title: '证书编号模板列表',
        defaultToolbar: ['filter'],
        url: "/realestate-inquiry-ui/rest/v1.0/zsbhmb",
        request: {
            limitName: 'size' //每页数据量的参数名，默认：limit
        },
        cellMinWidth: 80,
        even: true,
        cols: [[
            {type: 'checkbox', fixed: 'left'},
            {minWidth:100, sort:false, field:'sqsjc', title:'省份简称'},
            {minWidth:100, sort:true,  field:'nf',    title:'年份' },
            {minWidth:120, sort:true,  field:'szsxqc',title:'市县全称'},
            {minWidth:100, sort:true,  field:'qxdm',  title:'市县代码'},
            {minWidth:250, sort:false, field:'djjg',  title:'登记部门'},
            {minWidth:150, sort:false, field:'djbmdm',title:'部门代码'},
            {minWidth:100, sort:false, field:'bdcqzhws', title:'证号位数'},
            {minWidth:100, sort:false, field:'sqdm',  title:'流水编码'},
            {minWidth:100, sort:false, field:'cssxh', title:'初始编号'},
            {minWidth:100, sort:false, field:'ylzhkg',title:'预留证号',
                templet: function(d){
                    return 1 == d.ylzhkg? "<span style='color: red;'>开启</span>" : "关闭";
                }
            },
            {minWidth:100, sort:false, field:'zsfhkg',title:'证书废号',
                templet: function(d){
                    return 1 == d.zsfhkg? "<span style='color: red;'>开启</span>" : "关闭";
                }
            },
            {minWidth:120, sort:false, field:'sqdmkg',title:'拼接流水编码',
                templet: function(d){
                    return 1 == d.sqdmkg? "<span style='color: red;'>开启</span>" : "关闭";
                }
            },
            {minWidth:270, sort:false, field:'bdcqzh',title:'证号格式'},
            {minWidth:150, sort:false, field:'djjgdh',title:'登记机构电话'}
        ]],
        text: {
            none: '未查询到数据'
        },
        autoSort: false,
        page: true,
        parseData: function (res) {
            return {
                code: res.code, //解析接口状态
                msg: res.msg, //解析提示文本
                count: res.totalElements, //解析数据长度
                data: res.content //解析数据列表
            }
        },
        done: function () {
            $('.layui-table-tool-self').css('right',$('.bdc-export-tools').width()+ 17 + 'px');


            if($('.bdc-table-box .layui-table-main>.layui-table').height() == 0){
                $('.layui-table-body .layui-none').html('<img src="../../../static/lib/registerui/image/table-none.png" alt="">无数据');
            }else {
                $('.layui-table-body').height($('.bdc-table-box').height() - 131);
                $('.layui-table-fixed .layui-table-body').height($('.bdc-table-box').height() - 131 - 17);
            }
        }
    });

    //头工具栏事件
    table.on('toolbar(zsbhmbTable)', function(obj){
        var checkStatus = table.checkStatus(obj.config.id);
        switch(obj.event){
            case 'add':
                addZsbhmb();
                break;
            case 'edit':
                editZsbhmb(checkStatus.data);
                break;
            case 'delete':
                deleteZsbhmb(checkStatus.data);
                break;
            case 'example':
                showExample();
                break;
            case 'copy':
                copyZsbhmb(checkStatus.data);
                break;
        };
    });

    /**
     * 行双击编辑
     */
    table.on('rowDouble(zsbhmbTable)', function(obj){
        var array = new Array();
        array.push(obj.data);
        editZsbhmb(array);
    });

    /**
     * 监听排序事件
     */
    table.on('sort(zsbhmbTable)', function(obj){
        table.reload('zsbhmbTable', {
            initSort: obj
            ,where: {
                field: obj.field //排序字段
                ,order: obj.type //排序方式
            }
        });

    });

    /**
     * 新增证书编号模板
     */
    function addZsbhmb(){
        layer.open({
            type: 2,
            title: '新增证书编号模板',
            anim: -1,
            shadeClose: true,
            maxmin: true,
            shade: false,
            area: ['960px', '450px'],
            offset: 'auto',
            content: [ "addOrEditZsbhmb.html?action=add", 'yes'],
            end:function(){
                table.reload('zsbhmbTable');
            }
        });
    }

    /**
     * 编辑证书编号模板
     */
    function editZsbhmb(data){
        if(!data || data.length != 1){
            alertMsg("请选择需要编辑的某一条记录！");
            return;
        }

        layer.open({
            type: 2,
            title: '编辑证书编号模板',
            anim: -1,
            shadeClose: true,
            maxmin: true,
            shade: false,
            area: ['960px', '450px'],
            offset: 'auto',
            content: [ "addOrEditZsbhmb.html?action=edit", 'yes'],
            success: function (layero, index) {
                var iframe = window['layui-layer-iframe' + index];
                iframe.setData(data[0]);
            },
            end:function(){
                table.reload('zsbhmbTable');
            }
        });
    }

    /**
     * 显示示例
     */
    function showExample(){
        layer.open({
            title: '证号模板示例',
            type: 2,
            area: ['960px','500px'],
            content: 'zsbhmbsl.html'
        });
    }

    /**
     * 删除证书编号模板
     */
    function deleteZsbhmb(data){
        if(!data || data.length == 0){
            alertMsg("请选择需要删除的记录！");
            return;
        }

        var deleteIndex = layer.open({
            type: 1,
            title: '确认删除',
            area: ['320px'],
            content: '确定要删除所选证书编号模板？',
            skin: 'bdc-small-tips',
            btn: ['确定','取消'],
            btnAlign: 'c',
            yes: function(){
                $.ajax({
                    url: "/realestate-inquiry-ui/rest/v1.0/zsbhmb",
                    type: "DELETE",
                    data: JSON.stringify(data),
                    contentType: 'application/json',
                    dataType: "json",
                    success: function (data) {
                        if(data && $.isNumeric(data) && data > 0){
                            delSuccessMsg();
                            reload();
                        } else {
                            delFailMsg();
                        }
                    },
                    error:function(){
                        delFailMsg();
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
     * 证书编号模板复制
     * @param data
     */
    function copyZsbhmb(data){
        if(!data || data.length == 0 || data.length > 1){
            layer.alert("<div style='text-align: center'>请选择需要复制的一条记录！</div>", {title: '提示'});
            return;
        }
        //获取data[0].zsbhmbid,发送ajax请求，进行后台数据拷贝。
        $.ajax({
            url: "/realestate-inquiry-ui/rest/v1.0/copyZsbhmb/"+data[0].zsbhmbid,
            type: 'POST',
            contentType: 'application/json',
            dataType: "text",
            success: function (result) {
                if(!isNullOrEmpty(result)){
                    layer.msg('<img src="../../../static/lib/bdcui/images/success-small.png" alt="">复制成功');
                    table.reload("zsbhmbTable",  {
                        url: "/realestate-inquiry-ui/rest/v1.0/zsbhmb",
                        page: {
                            //重新从第 1 页开始
                            curr: 1
                        }
                    });
                } else {
                    layer.msg('<img src="../../../static/lib/bdcui/images/error-small.png" alt="">复制失败，请重试');
                }
            },
            error: function ($xhr, textStatus, errorThrown) {
                layer.msg('<img src="../../../static/lib/bdcui/images/error-small.png" alt="">复制失败，请重试');
            }
        });
    }

    /**
     * 点击查询
     */
    $('#search').on('click',function () {
        // 获取查询内容
        var obj = {};
        var count = 0;
        $(".search").each(function(i){
            var value= $(this).val();
            if(!isNullOrEmpty(value)){
                count += 1;
            }
            var name= $(this).attr('name');
            obj[name]=value;
        });

        // if(0 == count){
        //     alertMsg("请输入查询条件！");
        //     return false;
        // }

        // 重新请求
        table.reload("zsbhmbTable", {
            where: obj
            , page: {
                curr: 1  //重新从第 1 页开始
            }
        });
    });

    /**
     * 重置
     */
    $('#reset').on('click',function () {
        // table.reload("zsbhmbTable", {
        //     where: []
        //     , page: {
        //         curr: 1  //重新从第 1 页开始
        //     }
        // });
    });

    /**
     * 重新加载数据
     */
    window.reload = function(){
        var obj = {};
        $(".search").each(function(i){
            var value= $(this).val();
            var name= $(this).attr('name');
            obj[name]=value;
        });
        table.reload("zsbhmbTable", {
            where: obj
            , page: {
                curr: 1  //重新从第 1 页开始
            }
        });
    };
});