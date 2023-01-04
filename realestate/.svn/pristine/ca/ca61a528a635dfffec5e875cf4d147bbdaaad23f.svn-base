/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @description 权利其他状况js
 */
layui.use(['table', 'laytpl', 'laydate', 'layer', 'form', 'upload'], function () {
    var $ = layui.jquery,
        table = layui.table,
        layer = layui.layer,
        formSelects = layui.formSelects,
        upload = layui.upload,
        form = layui.form;
    $(function () {
        importQlqtzkfj();
    })
    var BASE_URL = '/realestate-inquiry-ui/rest/v1.0';
    formSelects.config('selectDjxl', {
        keyName: 'MC',            //自定义返回数据中name的key, 默认 name
        keyVal: 'DM'            //自定义返回数据中value的key, 默认 value
    }, true);
    formSelects.config('selectQllx', {
        keyName: 'MC',            //自定义返回数据中name的key, 默认 name
        keyVal: 'DM'            //自定义返回数据中value的key, 默认 value
    }, true);
    /**
     * 获取字典信息
     */
    var zdData;
    $.ajax({
        url: BASE_URL+'/qlqtzkFj/zd',
        type: "GET",
        async: false,
        dataType: "json",
        timeout : 10000,
        success: function(data) {
            zdData = data;
            if (data) {
                if (data.djxl) {
                    formSelects.data('selectDjxl', 'local', {arr: data.djxl})
                }
                if (data.qllx) {
                    formSelects.data('selectQllx', 'local', {arr: data.qllx})
                }
            }
        }
    });
    importQlqtzkfj();

    /**
     * 加载Table数据列表
     */
    table.render({
        elem: '#qlqtzkFjTable',
        toolbar: '#toolbar',
        title: '默认意见配置列表',
        defaultToolbar: ['filter'],
        url: BASE_URL+"/qlqtzkFj/page",
        request: {
            limitName: 'size' //每页数据量的参数名，默认：limit
        },
        cellMinWidth: 80,
        even: true,
        cols: [[
            {type: 'checkbox', fixed: 'left'},
            {field: 'pzid', title: 'pzid',hide:true},
            {field: 'djxl', title: '登记小类',
                templet: function(d){
                var result=d.djxl;
                    if(zdData && zdData.djxl){
                        for(var index in zdData.djxl){
                            if(zdData.djxl[index].DM == d.djxl){
                                var mc=zdData.djxl[index].MC;
                                result= (mc==undefined?"":mc);
                            }
                        }
                    }
                    return result;
                }
            },
            {field: 'qllx', title: '权利类型',
                templet: function(d){
                    var result=d.qllx;
                    if(zdData && zdData.qllx){
                        for(var index in zdData.qllx){
                            if(zdData.qllx[index].DM == d.qllx){
                                var mc=zdData.qllx[index].MC;
                                result = (mc == undefined ? "" : mc);
                            }
                        }
                    }
                    return result;
                }
            },
            {field: 'qlqtzksjy', title: '权利其他状况数据源'},
            {field: 'qlqtzkmb', title: '权利其他状况模板'},
            {field: 'fjsjy', title: '附记数据源'},
            {field: 'fjmb', title: '附记模板'},
            {field: 'zxqlfjsjy', title: '注销权利附记数据源'},
            {field: 'zxqlfjmb', title: '注销权利附记模板'},
            {
                field: 'pzrq', hide: true, title: '配置日期',
                templet: function (d) {
                    return format(d.pzrq);
                }
            }
        ]],
        text: {
            none: '未查询到数据'
        },
        autoSort: false,
        page: true,
        parseData: function (res) {
            if(isNotBlank(res.content)){
                //数据解密
                res.content.forEach(function (value){
                    decodeQlqtzkFj(value);
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
                $('.layui-table-body .layui-none').html('<img src="../../../static/lib/registerui/image/table-none.png" alt="">无数据');
            }else {
                $('.layui-table-body').height($('.bdc-table-box').height() - 131);
                $('.layui-table-fixed .layui-table-body').height($('.bdc-table-box').height() - 131 - 17);
            }
        }
    });

    //头工具栏事件
    table.on('toolbar(qlqtzkFjTable)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id);
        //对选中的数据进行加密
        checkStatus.data.forEach(function (value){
            encodeQlqtzkFj(value);
        })
        switch (obj.event) {
            case 'add':
                addBdcXtQlqtzkFj();
                break;
            case 'edit':
                editBdcXtQlqtzkFj(checkStatus.data);
                break;
            case 'delete':
                deleteBdcXtQlqtzkFj(checkStatus.data);
                break;
            case 'sl':
                qlqtzkSl();
                break;
            case  'export':
                exportQlqtzkfj(checkStatus.data);
                break;

        }
        ;
    });

    /**
     * 行双击编辑
     */
    table.on('rowDouble(qlqtzkFjTable)', function (obj) {
        var array = new Array();
        array.push(obj.data);
    });

    /**
     * 监听排序事件
     */
    table.on('sort(qlqtzkFjTable)', function (obj) {
        table.reload('qlqtzkFjTable', {
            initSort: obj
            , where: {
                field: obj.field //排序字段
                , order: obj.type //排序方式
            }
        });

    });

    /**
     * 点击查询
     */
    $('#search').on('click', function () {
        // 获取查询内容
        var obj = {'djxl':'','qllx':'','slbh':''};
        var djxlArray=formSelects.value('selectDjxl');
        var qllxArray=formSelects.value('selectQllx');
        var slbh = $("#slbh").val();
        if(djxlArray.length!=0){
            obj['djxl']=djxlArray[0].DM;
        }
        if(qllxArray.length!=0){
            obj['qllx']=qllxArray[0].DM;
        }
        if(slbh != null && slbh !='' && slbh != undefined) {
            obj['slbh'] = slbh;
        }
        // 重新请求
        table.reload("qlqtzkFjTable", {
            where: obj
            , page: {
                curr: 1  //重新从第 1 页开始
            }
        });
        importQlqtzkfj();
    });

    /**
     * 点击重置
     */
    $("#reset").click(function () {
        table.reload("qlqtzkFjTable",{
            where: {
                "djxl": "",
                "qllx": "",
                "slbh": "",
            }
            , page: {
                curr: 1  //重新从第一页开始
            }
        });
        importQlqtzkfj();
    });

    /**
     * 重新加载数据
     */
    window.reload = function () {
        var obj = {};
        $(".search").each(function (i) {
            var value = $(this).val();
            var name = $(this).attr('name');
            obj[name] = value;
        });
        table.reload("qlqtzkFjTable", {
            where: obj
            , page: {
                curr: 1  //重新从第 1 页开始
            }
        });
        importQlqtzkfj();
    };

    /**
     * 新增
     */
    function addBdcXtQlqtzkFj(){
        layer.open({
            type: 2,
            title: '新增权利其他状况、附记配置',
            area: ['1000px', '400px'],
            content: [ "addOrEditBdcXtQlqtzkFj.html?action=add", 'yes'],
            end:function(){
                table.reload('qlqtzkFjTable');
                importQlqtzkfj();
            }
        });
    }

    function qlqtzkSl(){
        layer.open({
            type: 2,
            title: '新增权利其他状况、附记配置示例',
            area: ['960px','500px'],
            content: [ "qlqtzkSl.html", 'yes'],
            end:function(){
                importQlqtzkfj();
            }
        });
    }

    /**
     * 编辑
     * @param data
     */
    function editBdcXtQlqtzkFj(data){
        if(!data || data.length != 1){
            layer.alert("请选择需要编辑的某一条记录！", {title: '提示'});
            return;
        }
        layer.open({
            type: 2,
            title: '编辑权利其他状况、附记配置',
            area: ['1000px', '400px'],
            content: [ "addOrEditBdcXtQlqtzkFj.html?action=edit", 'yes'],
            success: function (layero, index) {
                var iframe = window['layui-layer-iframe' + index];
                iframe.setData(data[0]);
            },
            end:function(){
                table.reload('qlqtzkFjTable');
                importQlqtzkfj();
            }
        });
    }

    /**
     * 删除
     * @param data
     */
    function deleteBdcXtQlqtzkFj(data){
        if(!data || data.length == 0){
            layer.alert("请选择需要删除的记录！", {title: '提示'});
            return;
        }

        var deleteIndex = layer.open({
            type: 1,
            title: '确认删除',
            area: ['320px'],
            skin: 'bdc-small-tips',
            content: '确定要删除所选模板配置？',
            btn: ['确定','取消'],
            btnAlign: 'c',
            yes: function(){
                $.ajax({
                    url: BASE_URL+"/qlqtzkFj",
                    type: "DELETE",
                    data: JSON.stringify(data),
                    contentType: 'application/json',
                    dataType: "json",
                    success: function (data) {
                        if(data && $.isNumeric(data) && data > 0){
                            layer.msg('删除成功！', {time: 2000});
                            reload();
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
            btn2: function () {
                //取消
            }
        });
    }

    function exportQlqtzkfj(data) {
        if (!data || data.length == 0) {
            layer.msg('<img src="../../../static/lib/bdcui/images/warn-small.png" alt="">请选择需要导出的记录');
            return;
        }
        $("#filedata").val(JSON.stringify(data));
        $("#qlqtzkfjfile").submit();
    }

    function importQlqtzkfj() {
        upload.render({
            elem: '#import', //绑定元素,
            url: '/realestate-inquiry-ui/rest/v1.0/qlqtzkFj/importYz', //上传接口
            accept: 'file',
            acceptMime: 'file/txt',
            exts: 'txt',
            before: function (obj) {
                addModel();
            },

            done: function (res) {
                removeModal();
                if (res.code == 2) {
                    // layer.msg('<img src="../../../static/lib/bdcui/images/warn-small.png" alt="">已存在权利类型为:'+res.message+',是否导入并覆盖');
                    var imporezsmb = layer.open({
                        type: 1,
                        area: ['320px'],
                        title: '是否覆盖',
                        content: '已存在登记小类为:' + res.message + '是否导入并覆盖',
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
                layer.msg('<img src="../../../static/lib/bdcui/images/warn-small.png" alt="">导入失败，请选择正确文件！');
            }
        });
    }


    function importAfert(filedata) {
        $.ajax({
            url: "/realestate-inquiry-ui/rest/v1.0/qlqtzkFj/import",
            type: "POST",
            data: JSON.stringify(filedata),
            contentType: 'application/json',
            dataType: "json",
            success: function (data) {
                if (data.code == 1) {
                    layer.msg('<img src="../../../static/lib/bdcui/images/success-small.png" alt="">导入成功,即将刷新页面');
                    /* table.reload('zsmbpzTable');
                     importZsmb();
                     layer.close();*/
                    setTimeout(function () {
                        location.reload();
                    }, 500);
                } else {
                    layer.msg('<img src="../../../static/lib/bdcui/images/warn-small.png" alt="">导入失败，请选择正确文件！');
                }

            },
            error: function ($xhr, textStatus, errorThrown) {
                removeModal();
                layer.msg('<img src="../../../static/lib/bdcui/images/warn-small.png" alt="">导入失败，请选择正确文件！');

            }
        })
    }

    /**
     * 日期格式化
     * @param timestamp
     * @returns {*}
     */
    function format(timestamp) {
        if (!timestamp) {
            return '';
        }

        var time = new Date(timestamp);
        var y = time.getFullYear();
        var m = time.getMonth() + 1;
        var d = time.getDate();
        var h = time.getHours();
        var mm = time.getMinutes();
        var s = time.getSeconds();
        return y + '-' + add0(m) + '-' + add0(d) + ' ' + add0(h) + ':' + add0(mm) + ':' + add0(s);
    }

    function add0(time) {
        if (time < 10) {
            return '0' + time;
        }
        return time;
    }

    function addModel(message) {
        if (isNullOrEmpty(message)) {
            message = '加载中';
        }
        var modalHtml = '<div id="waitModalLayer">' +
            '<p class="bdc-wait-content">' +
            '<i class="layui-icon layui-anim layui-anim-rotate layui-anim-loop">&#xe63d;</i>' +
            '<span>' + message + '</span>' +
            '</p>' +
            '</div>';
        $('body').append(modalHtml);
    }

    function removeModal() {
        $("#waitModalLayer").remove();
    }
});