/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @description 默认意见js
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

    formSelects.config('selectSjlx', {
        keyName: 'MC',            //自定义返回数据中name的key, 默认 name
        keyVal: 'DM'            //自定义返回数据中value的key, 默认 value
    }, true);

    formSelects.config('selectDjyy', {
        keyName: 'djyy',            //自定义返回数据中name的key, 默认 name
        keyVal: 'djyy'            //自定义返回数据中value的key, 默认 value
    }, true);

    var BASE_URL = '/realestate-inquiry-ui/rest/v1.0';
    var sjlxZdList = [];
    var sqbmZdList = [];
    $.ajax({
        url: BASE_URL + "/lcpz/zdxx?djxl=" + djxl,
        type: "POST",
        data: 'sjlx,sqbm,djyy',
        contentType: 'application/json',
        dataType: "json",
        async: false,
        success: function (data) {
            if (data) {
                formSelects.data('selectSjlx', 'local', {arr: data.sjlx});
                formSelects.data('selectDjyy', 'local', {arr: data.djyy});
                sjlxZdList = data.sjlx;
                sqbmZdList = data.sqbm;
            }
        },
        error: function (e) {
            response.fail(e, '');
        }
    });

    /**
     * 加载Table数据列表
     */
    table.render({
        elem: '#sjclpzTable',
        toolbar: '#toolbar',
        title: '收件材料配置列表',
        defaultToolbar: ['filter'],
        url: BASE_URL+'/lcpz/djyysjclpz/page',
        where: {djxl: djxl},
        request: {
            limitName: 'size' //每页数据量的参数名，默认：limit
        },
        cellMinWidth: 80,
        even: true,
        cols: [[
            {type: 'checkbox', fixed: 'left'},
            {field: 'pzid', title: 'pzid', hide: true},
            {field: 'djxl', title: 'djxl', hide: true},
            {field: 'xh', title: '序号', hide: true},
            {field: 'clmc', title: '材料名称'},
            {
                field: 'sjlx', width: 150, title: '收件类型', templet: function (d) {
                    return changeDmToMc(d.sjlx, sjlxZdList);
                }
            },
            {field: 'mrfs', width: 150, title: '默认份数'},
            {field: 'djyy', title: '登记原因'},
            {
                field: 'sqbm', width: 150, title: '收取部门', templet: function (d) {
                    return changeDmToMc(d.sqbm, sqbmZdList);
                }
            },
            {field: 'sfbc', width: 150, title: '是否必传', templet: function (d) {
                    if(isNotBlank(d.sfbc) && d.sfbc == 1){
                        return "是"
                    }
                    return "否"
                }}
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
    table.on('toolbar(sjclpzTable)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id);
        switch (obj.event) {
            case 'add':
                addBdcSjclpz(djxl);
                break;
            case 'edit':
                editBdcSjclpz(checkStatus.data);
                break;
            case 'delete':
                deleteBdcSjclpz(checkStatus.data);
                break;
        }
    });

    /**
     * 监听排序事件
     */
    table.on('sort(sjclpzTable)', function (obj) {
        table.reload('sjclpzTable', {
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
        var clmc = $('#clmc').val();
        var djyy = $('#djyy').val();
        var obj = {'sjlx': '', 'clmc': replaceBracket(clmc), 'djxl': djxl, 'djyy': replaceBracket(djyy)};
        var sjlxArray = formSelects.value('selectSjlx');
        var djyyArray = formSelects.value('selectDjyy');
        if (sjlxArray.length !== 0) {
            obj['sjlx'] = sjlxArray[0].value;
        }
        if (djyyArray.length !== 0) {
            obj['djyy'] = djyyArray[0].value;
        }
        // 重新请求
        table.reload("sjclpzTable", {
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
        var obj = {'djxl':djxl};
        table.reload("sjclpzTable", {
            where: obj
            , page: {
                curr: 1  //重新从第 1 页开始
            }
        });
    };

    /**
     * 新增
     */
    function addBdcSjclpz(djxl) {
        addModel('');
        layer.open({
            title: '新增收件材料登记原因配置',
            type: 2,
            area: ['960px', '300px'],
            content: ["sjclpz/addOrEditBdcSjclDjyy.html?action=add&djxl="+djxl, 'yes']
            , end: function () {
                removeModal();
                table.reload('sjclpzTable');
            }
        });
    }

    /**
     * 编辑
     * @param data
     */
    function editBdcSjclpz(data) {
        if (!data || data.length != 1) {
            layer.alert("请选择需要编辑的某一条记录！", {title: '提示'});
            return;
        }
        addModel('');
        layer.open({
            type: 2,
            title: '编辑收件材料登记原因配置',
            anim: -1,
            shadeClose: true,
            maxmin: true,
            shade: false,
            area: ['960px', '300px'],
            offset: 'auto',
            content: ["sjclpz/addOrEditBdcSjclDjyy.html?action=edit&djxl=" + data[0].djxl, 'yes'],
            success: function (layero, index) {
                var iframe = window['layui-layer-iframe' + index];
                iframe.setData(data[0]);
            },
            end: function () {
                removeModal();
                table.reload('sjclpzTable');
            }
        });
    }

    /**
     * 删除
     * @param data
     */
    function deleteBdcSjclpz(data) {
        if (!data || data.length == 0) {
            layer.alert("请选择需要删除的记录！", {title: '提示'});
            return;
        }
        var deleteIndex = layer.open({
            type: 1,
            title: '确认删除',
            area: ['320px'],
            skin: 'bdc-small-tips',
            content: '确定要删除收件材料登记原因配置？',
            btn: ['确定', '取消'],
            btnAlign: 'c',
            yes: function () {
                $.ajax({
                    url: BASE_URL+"/lcpz/djyysjclpz",
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
    table.on('rowDouble(sjclpzTable)', function (obj) {
        var array = [];
        array.push(obj.data);
        editBdcSjclpz(array);
    });
    $('.nextstep').on('click',function () {
        window.location.href = 'step11.html?gzldyid=' + gzldyid + '&djxl=' + djxl + '&qllx=' + qllx;
    });

    $('.beforestep').on('click',function () {
        window.location.href='step9.html?gzldyid='+gzldyid+'&djxl='+djxl+'&qllx='+qllx;
    });


});