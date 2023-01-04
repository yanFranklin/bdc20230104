/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @description 收费项目配置列表
 */
layui.use(['table', 'laytpl', 'laydate', 'layer', 'form'], function () {
    var $ = layui.jquery,
        table = layui.table,
        layer = layui.layer,
        formSelects = layui.formSelects,
        form = layui.form;
    var gzldyid = $.getUrlParam('gzldyid');
    var qllx = $.getUrlParam('qllx');
    var djxl = $.getUrlParam('djxl');
    var jsffzdList =[];

    formSelects.config('selectSfxmbz', {
        keyName: 'SFXMBZ',            //自定义返回数据中name的key, 默认 name
        keyVal: 'SFXMBZ'            //自定义返回数据中value的key, 默认 value
    }, true);

    var BASE_URL = '/realestate-inquiry-ui/rest/v1.0';
    $.ajax({
        url: BASE_URL+"/lcpz/zdxx",
        type: "POST",
        data:'sfbz,sfjsff',
        contentType: 'application/json',
        dataType: "json",
        async:false,
        success: function (data) {
            if(data){
                formSelects.data('selectSfxmbz','local',{arr:data.sfbz});
                jsffzdList =data.sfjsff;
            }
        },
        error: function (e) {
            response.fail(e, '');
        }
    });
    var gzldyid = $.getUrlParam('gzldyid');
    var djxl = $.getUrlParam('djxl');

    /**
     * 加载Table数据列表
     */
    table.render({
        elem: '#sfxmpzTable',
        toolbar: '#toolbar',
        title: '收费项目配置列表',
        defaultToolbar: ['filter'],
        url: BASE_URL+'/lcpz/sfxmpz/page',
        where: {djxl: djxl},
        request: {
            limitName: 'size' //每页数据量的参数名，默认：limit
        },
        cellMinWidth: 80,
        even: true,
        cols: [[
            {type: 'checkbox', fixed: 'left'},
            {field: 'sfxmpzid', title: 'sfxmpzid', hide: true},
            {field: 'djxl', title: 'djxl', hide: true},
            {field: 'xh', title: '序号', hide: true},
            {field: 'sfxmmc', title: '收费项目名称'},
            {field: 'sfxmbz', title: '收费项目标准'},
            {field: 'sl', title: '数量'},
            {field: 'qlrlb', title: '权利人类别',
                templet: '#qlrlbTpl'
                },
            {
                field: 'jedw', title: '金额单位',
                templet: '#jedwTpl'
            },
            {field: 'ysje', title: '应收金额'},
            {
                field: 'jsff', title: '计算方法',
                templet: function (d) {
                    return changeDmToMc(d.jsff, jsffzdList);
                }
            },
            {field: 'djyy', title: '登记原因'},

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
            $('.layui-table-tool-self').css('right', $('.bdc-export-tools').width() + 17 + 'px');

            if ($('.bdc-table-box .layui-table-main>.layui-table').height() == 0) {
                $('.layui-table-body .layui-none').html('<img src="../../../static/lib/registerui/image/table-none.png" alt="">无数据');
            } else {
                $('.layui-table-body').height($('.bdc-table-box').height() - 135);
                $('.layui-table-fixed .layui-table-body').height($('.bdc-table-box').height() - 135 - 17);
            }
        }
    });

    //头工具栏事件
    table.on('toolbar(sfxmpzTable)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id);
        switch (obj.event) {
            case 'add':
                addBdcSfxmpz(djxl);
                break;
            case 'edit':
                editBdcSfxmpz(checkStatus.data);
                break;
            case 'delete':
                deleteBdcSfxmpz(checkStatus.data);
                break;
        }
    });

    /**
     * 监听排序事件
     */
    table.on('sort(sfxmpzTable)', function (obj) {
        table.reload('sfxmpzTable', {
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
        var sfxmmc=$('#sfxmmc').val();
        var obj = {'sfxmbz':'','sfxmmc':replaceBracket(sfxmmc),'djxl':djxl};
        var sfxmdmArray=formSelects.value('selectSfxmbz');
        if(sfxmdmArray.length!=0){
            obj['sfxmbz']=sfxmdmArray[0].value;
        }
        // 重新请求
        table.reload("sfxmpzTable", {
            where: obj
            , page: {
                curr: 1  //重新从第 1 页开始
            }
        });
    });

    /**
     * 重新加载数据
     */
    window.reload = function () {
        var sfxmmc=$('#sfxmmc').val();
        var obj = {'sfxmbz':'','sfxmmc':replaceBracket(sfxmmc),'djxl':djxl};
        var sfxmdmArray=formSelects.value('selectSfxmbz');
        if(sfxmdmArray.length!=0){
            obj['sfxmbz']=sfxmdmArray[0].value;
        }
        table.reload("sfxmpzTable", {
            where: obj
            , page: {
                curr: 1  //重新从第 1 页开始
            }
        });
    };

    /**
     * 新增
     */
    function addBdcSfxmpz(djxl) {
        addModel('');
        layer.open({
            title: '新增收费项目配置',
            type: 2,
            area: ['960px', '460px'],
            content: ["sfxmpz/addOrEditBdcSfxmpz.html?action=add&djxl=" + djxl, 'yes']
            , end: function () {
                removeModal();
                table.reload('sfxmpzTable');
            }
        });
    }

    /**
     * 编辑
     * @param data
     */
    function editBdcSfxmpz(data) {
        if (!data || data.length != 1) {
            layer.alert("请选择需要编辑的某一条记录！", {title: '提示'});
            return;
        }
        addModel('');
        layer.open({
            type: 2,
            title: '编辑收费项目配置',
            anim: -1,
            shadeClose: true,
            maxmin: true,
            shade: false,
            area: ['960px', '460px'],
            offset: 'auto',
            content: ["sfxmpz/addOrEditBdcSfxmpz.html?action=edit", 'yes'],
            success: function (layero, index) {
                var iframe = window['layui-layer-iframe' + index];
                iframe.setData(data[0]);
            },
            end: function () {
                removeModal();
                table.reload('sfxmpzTable');
            }
        });
    }


    /**
     * 删除
     * @param data
     */
    function deleteBdcSfxmpz(data) {
        if (!data || data.length == 0) {
            layer.alert("请选择需要删除的记录！", {title: '提示'});
            return;
        }
        var deleteIndex = layer.open({
            type: 1,
            title: '确认删除',
            area: ['320px'],
            skin: 'bdc-small-tips',
            content: '确定要删除所选收费项目配置？',
            btn: ['确定', '取消'],
            btnAlign: 'c',
            yes: function () {
                $.ajax({
                    url: BASE_URL+"/lcpz/sfxmpz",
                    type: "DELETE",
                    data: JSON.stringify(data),
                    contentType: 'application/json',
                    dataType: "json",
                    success: function (data) {
                        if (data && $.isNumeric(data) && data > 0) {
                            layer.msg('<img src="../../../static/lib/bdcui/images/success-small.png" alt="">删除成功',{
                                time: 1000}
                            );
                            reload();
                        } else {
                            layer.alert("删除失败，请重试！", {title: '提示'});
                        }
                    },
                    error: function () {
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

    //字典项代码转名称
    function changeDmToMc(dm, zdList) {
        var mc = "";
        if (isNotBlank(zdList)) {
            for (var i = 0; i < zdList.length; i++) {
                var zd = zdList[i];
                if (dm == zd.DM) {
                    mc = zd.MC;
                    break;
                }
            }
        }
        return mc;

    }

    /**
     * 行双击编辑
     */
    table.on('rowDouble(sfxmpzTable)', function (obj) {
        var array = new Array();
        array.push(obj.data);
        editBdcSfxmpz(array);
    });
    $('.nextstep').on('click',function () {
        window.location.href='step5.html?gzldyid='+gzldyid+'&djxl='+djxl+'&qllx='+qllx;
    })

    $('.beforestep').on('click',function () {
        window.location.href='step3.html?gzldyid='+gzldyid+'&djxl='+djxl+'&qllx='+qllx;
    })


});