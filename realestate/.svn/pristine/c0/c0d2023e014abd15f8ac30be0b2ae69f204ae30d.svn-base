/**
 * author: <a href="mailto:zhuyong@gtmap.cn>zhuyong</a>
 * version 1.0.0  2019/01/17
 * describe: 证书模板配置页面处理JS
 */
layui.use(['table', 'laytpl', 'laydate', 'layer', 'form', 'upload'], function () {
    var $ = layui.jquery,
        table = layui.table,
        layer = layui.layer,
        upload = layui.upload,
        form = layui.form;
    $(function () {
        importZsmb();
    })

    $.ajax({
        url: '/realestate-inquiry-ui/rest/v1.0/zsmbpz/zd',
        type: "GET",
        dataType: "json",
        success: function (data) {
            if (data && data.length > 0) {
                // 渲染字典项
                $.each(data, function (index, item) {
                    $('#qllxmc').append('<option value="' + item.DM + '">' + item.MC + '</option>');
                });
            }

            form.render('select');
            // 下拉选择添加删除按钮
            renderSelectClose(form);
        }
    });

    form.on('select(qllxmc)', function (data) {
        $("#qllx").val(data.value);
    });
    importZsmb();
    // 加载上传事件绑定
    // importZsmb();
    /**
     * 加载Table数据列表
     */
    table.render({
        elem: '#zsmbpzTable',
        toolbar: '#toolbar',
        title: '证书模板列表',
        defaultToolbar: ['filter'],
        url: "/realestate-inquiry-ui/rest/v1.0/zsmbpz?field=pzrq&order=desc",
        request: {
            limitName: 'size' //每页数据量的参数名，默认：limit
        },
        cellMinWidth: 30,
        even: true,
        cols: [[
            {type: 'checkbox', fixed: 'left'},
            {width: 300, sort: true, field: 'qllxmc', title: '权利类型名称'},
            {width: 150, sort: true, field: 'qllx', title: '权利类型代码'},
            {
                width: 150, hide: true, field: 'pzrq', title: '配置日期',
                templet: function (d) {
                    return format(d.pzrq);
                }
            },
            {sort: false, field: 'mbsql', title: '模板SQL'},
        ]],
        text: {
            none: '未查询到数据'
        },
        autoSort: false,
        page: true,
        parseData: function (res) {
            if(isNotBlank(res.content)){
                //数据解密
                res.content.forEach(function (value,index){
                    if(!isNullOrEmpty(value.mbsql)){
                        res.content[index].mbsql = Base64.decode(value.mbsql);
                    }
                })
                return {
                    code: res.code, //解析接口状态
                    msg: res.msg, //解析提示文本
                    count: res.totalElements, //解析数据长度
                    data: res.content, //解析数据列表
                }
            }else{
                return {
                    code: 0, //解析接口状态
                    msg: "成功", //解析提示文本
                    count: 0, //解析数据长度
                    data: [] //解析数据列表
                }
            }
        },
        done: function () {
            $('.layui-table-tool-self').css('right',$('.bdc-export-tools').width()+ 17 + 'px');

            if($('.bdc-table-box .layui-table-main>.layui-table').height() == 0){
                $('.layui-table-body .layui-none').html('<img src="../../static/lib/registerui/image/table-none.png" alt="">无数据');
            }else {
                $('.layui-table-body').height($('.bdc-table-box').height() - 131);
                $('.layui-table-fixed .layui-table-body').height($('.bdc-table-box').height() - 131 - 17);
            }
        }
    });


    //头工具栏事件
    table.on('toolbar(zsmbpzTable)', function(obj){
        var checkStatus = table.checkStatus(obj.config.id);
        switch(obj.event){
            case 'add':
                addZsmbpz();
                break;
            case 'edit':
                editZsmbpz(checkStatus.data);
                break;
            case 'delete':
                deleteZsmbpz(checkStatus.data);
                break;
            case 'example':
                showExample();
                break;
            case 'export':
                exportZsmb(checkStatus.data);
                break;

        };

    });

    /**
     * 行双击编辑
     */
    table.on('rowDouble(zsmbpzTable)', function(obj){
        var array = new Array();
        array.push(obj.data);
        editZsmbpz(array);
    });

    /**
     * 监听排序事件
     */
    table.on('sort(zsmbpzTable)', function(obj){
        table.reload('zsmbpzTable', {
            initSort: obj
            ,where: {
                field: obj.field //排序字段
                ,order: obj.type //排序方式
            }
        });

    });

    /**
     * 新增证书模板
     */
    function addZsmbpz(){
        layer.open({
            type: 2,
            title: '新增证书模板',
            anim: -1,
            shadeClose: true,
            maxmin: true,
            shade: false,
            area: ['960px', '450px'],
            offset: 'auto',
            content: [ "addOrEditZsmbpz.html?action=add", 'yes'],
            end:function(){
                table.reload('zsmbpzTable');
                importZsmb();
            }
        });
    }

    /**
     * 编辑证书模板
     */
    function editZsmbpz(data){
        if(!data || data.length != 1){
            alertMsg("请选择需要编辑的某一条记录！");
            return;
        }

        layer.open({
            type: 2,
            title: '编辑证书模板',
            anim: -1,
            shadeClose: true,
            maxmin: true,
            shade: false,
            area: ['960px', '450px'],
            offset: 'auto',
            content: [ "addOrEditZsmbpz.html?action=edit", 'yes'],
            success: function (layero, index) {
                var iframe = window['layui-layer-iframe' + index];
                iframe.setData(data[0]);
            },
            end:function(){
                table.reload('zsmbpzTable');
                importZsmb();
            }
        });
    }

    /**
     * 显示示例
     */
    function showExample(){
        layer.open({
            title: '证书模板示例',
            type: 2,
            area: ['960px','500px'],
            content: 'zsmbpzsl.html'
        });
    }

    /**
     * 删除证书模板
     */
    function deleteZsmbpz(data){
        //对选择数据中SQL加密处理
        data.forEach(function (value){
            if(!isNullOrEmpty(value.mbsql)){
                value.mbsql = Base64.encode(value.mbsql);
            }
        })
        if(!data || data.length == 0){
            alertMsg("请选择需要删除的记录！");
            return;
        }
        var deleteIndex = layer.open({
            type: 1,
            title: '确认删除',
            area: ['320px'],
            skin: 'bdc-small-tips',
            content: '确定要删除所选证书模板？',
            btn: ['确定','取消'],
            btnAlign: 'c',
            yes: function(){
                $.ajax({
                    url: "/realestate-inquiry-ui/rest/v1.0/zsmbpz",
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
     * 点击查询
     */
    $('#search').on('click',function () {
        var qllx = $("#qllx").val();
        // if(isNullOrEmpty(qllx)){
        //     alertMsg("请输入查询条件！");
        //     return false;
        // }
        var slbh = $("#slbh").val();
        // 重新请求
        table.reload("zsmbpzTable", {
            where: {
                "qllx": qllx,
                "slbh": slbh,
            }
            , page: {
                curr: 1  //重新从第 1 页开始
            }
        });
        importZsmb();
    });

    /**
     * 重置
     */
    $('#reset').on('click',function () {
        table.reload("zsmbpzTable", {
            where: {
                "qllx": "",
                "slbh": "",
            }
            , page: {
                curr: 1  //重新从第 1 页开始
            }
        });
        importZsmb();
    });

    /**
     * 重新加载数据
     */
    window.reload = function () {
        var qllx = $("#qllx").val();
        table.reload("zsmbpzTable", {
            where: []
            , page: {
                curr: 1  //重新从第 1 页开始
            }
        });
        importZsmb();
    };

    /**
     * 导出模板txt
     * @param date
     */
    function exportZsmb(data) {
        if (!data || data.length == 0) {
            layer.msg('<img src="../../static/lib/bdcui/images/warn-small.png" alt="">请选择需要导出的记录');
            return;
        }
        //对选择数据中SQL加密处理
        data.forEach(function (value){
            if(!isNullOrEmpty(value.mbsql)){
                value.mbsql = Base64.encode(value.mbsql);
            }
        })
        $("#filedata").val(JSON.stringify(data));
        $("#zsmbfile").submit();
    }

    function importZsmb() {
        upload.render({
            elem: '#import', //绑定元素,
            url: '/realestate-inquiry-ui/rest/v1.0/zsmbpz/importYz', //上传接口
            accept: 'file',
            acceptMime: 'file/txt',
            exts: 'txt',
            before: function (obj) {
                addModel();
            },
            done: function (res) {
                removeModal();
                /**
                 * 对获得收到数据中sql进行解密
                 * res.savedata.forEach(function (value,index){
                 *   res.savedata[index].mbsql = Base64.decode(value.mbsql);
                 *})
                 */
                if (res.code == 2) {
                    // layer.msg('<img src="../../static/lib/bdcui/images/warn-small.png" alt="">已存在权利类型为:'+res.message+',是否导入并覆盖');
                    var imporezsmb = layer.open({
                        type: 1,
                        area: ['320px'],
                        title: '是否覆盖',
                        content: '已存在权利类型为:' + res.message + '是否导入并覆盖',
                        btn: ['确定', '取消'],
                        skin: 'bdc-small-tips',
                        btnAlign: 'c',
                        yes: function () {
                            importAfert(res.savedata);
                        }
                    });

                } else {
                    importAfert(res.savedata);
                }
                //上传完毕回调
            }
            , error: function () {
                //请求异常回调
                removeModal();
                layer.msg('<img src="../../static/lib/bdcui/images/warn-small.png" alt="">导入失败，请选择正确文件！');
            }
        });
    }

    function importAfert(filedata) {
        $.ajax({
            url: "/realestate-inquiry-ui/rest/v1.0/zsmbpz/import",
            type: "POST",
            data: JSON.stringify(filedata),
            contentType: 'application/json',
            dataType: "json",
            success: function (data) {
                if (data.code == 1) {
                    layer.msg('<img src="../../static/lib/bdcui/images/success-small.png" alt="">导入成功,即将刷新页面');
                    /* table.reload('zsmbpzTable');
                     importZsmb();
                     layer.close();*/
                    setTimeout(function () {
                        location.reload();
                    }, 500);
                } else {
                    layer.msg('<img src="../../static/lib/bdcui/images/warn-small.png" alt="">导入失败，请选择正确文件！');
                }

            },
            error: function ($xhr, textStatus, errorThrown) {
                removeModal();
                layer.msg('<img src="../../static/lib/bdcui/images/warn-small.png" alt="">导入失败，请选择正确文件！');

            }
        })
    }


});