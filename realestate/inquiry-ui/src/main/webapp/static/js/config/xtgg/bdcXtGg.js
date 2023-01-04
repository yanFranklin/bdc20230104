/**
 * @author <a href ="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @description 公告配置js
 */
layui.use(['table', 'laytpl', 'laydate', 'layer', 'form'], function () {
    var $ = layui.jquery,
        table = layui.table,
        layer = layui.layer,
        formSelects = layui.formSelects,
        form = layui.form;
    var BASE_URL = '/realestate-inquiry-ui/rest/v1.0';
    formSelects.config('selectGzldymc', {
        keyName: 'name',            //自定义返回数据中name的key, 默认 name
        keyVal: 'key'            //自定义返回数据中value的key, 默认 value
    }, true);
    formSelects.config('selectGglx', {
        keyName: 'MC',            //自定义返回数据中name的key, 默认 name
        keyVal: 'DM'            //自定义返回数据中value的key, 默认 value
    }, true);
    formSelects.config('selectSply', {
        keyName: 'MC',            //自定义返回数据中name的key, 默认 name
        keyVal: 'DM'            //自定义返回数据中value的key, 默认 value
    }, true);
    var gzldyid = $.getUrlParam('gzldyid');
    var ggpageUrl=BASE_URL + "/xtgg/page";
    var zdData ={};

    /**
     * 加载字典
     */
    $.ajax({
        url: BASE_URL+"/lcpz/zdxx",
        type: "POST",
        data:'gglx,sply',
        contentType: 'application/json',
        dataType: "json",
        async:false,
        success: function (data) {
            if(data){
                zdData=data;
                formSelects.data('selectGglx','local',{arr:data.gglx});
                formSelects.data('selectSply','local',{arr:data.sply});
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
        elem: '#xtggTable',
        toolbar: '#toolbar',
        title: '公告配置列表',
        defaultToolbar: ['filter'],
        url: ggpageUrl,
        where: {gzldyid: gzldyid},
        request: {
            limitName: 'size' //每页数据量的参数名，默认：limit
        },
        cellMinWidth: 80,
        even: true,
        cols: [[
            {type: 'checkbox', fixed: 'left'},
            {field: 'gzldymc', title: '工作流名称', width: 250},
            {field: 'sply', title: '审批来源', width: 100,
                templet: function (d) {
                    return changeDmToMc(d.sply,zdData.sply);
                }},
            {field: 'ggbtpz', title: '公告标题配置', style: 'text-align:left'},
            {field: 'ggnrpz', title: '公告内容配置', style: 'text-align:left'},
            {
                field: 'gglx', title: '公告类型', width: 100,
                templet: function (d) {
                    return changeDmToMc(d.gglx,zdData.gglx);
                }
            }
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
    table.on('toolbar(xtggTable)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id);
        switch (obj.event) {
            case 'add':
                addBdcXtGg(gzldyid);
                break;
            case 'edit':
                editBdcXtGg(checkStatus.data);
                break;
            case 'delete':
                deleteBdcXtGg(checkStatus.data);
                break;
        }
    });

    /**
     * 监听排序事件
     */
    table.on('sort(xtggTable)', function (obj) {
        table.reload('xtggTable', {
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
        var obj = {'gzldyid':'','gglx':''};
        var gzlmcArray=formSelects.value('selectGzldymc');
        var gglxArray=formSelects.value('selectGglx');
        var splyArray=formSelects.value('selectSply');
        if(gzlmcArray.length!=0){
            obj['gzldyid']=gzlmcArray[0].value;
        }
        if(gglxArray.length!=0){
            obj['gglx']=gglxArray[0].value;
        }
        if(splyArray.length!=0){
            obj['sply']=splyArray[0].value;
        }

        // 重新请求
        table.reload("xtggTable", {
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
        table.reload("xtggTable", {
            where: []
            , page: {
                curr: 1  //重新从第 1 页开始
            }
        });
    };

    /**
     * 新增
     */
    function addBdcXtGg(gzldyid) {
        addModel('');
        layer.open({
            title: '新增公告配置',
            type: 2,
            area: ['960px', '350px'],
            content: ["../xtgg/addOrEditBdcXtGg.html?action=add&gzldyid="+gzldyid, 'yes']
            , end: function () {
                removeModal();
                table.reload('xtggTable');
            }
        });
    }

    /**
     * 编辑
     * @param data
     */
    function editBdcXtGg(data) {
        if (!data || data.length != 1) {
            layer.alert("请选择需要编辑的某一条记录！", {title: '提示'});
            return;
        }
        addModel('');
        layer.open({
            type: 2,
            title: '编辑公告配置',
            anim: -1,
            shadeClose: true,
            maxmin: true,
            shade: false,
            area: ['960px', '350px'],
            offset: 'auto',
            content: ["../xtgg/addOrEditBdcXtGg.html?action=edit", 'yes'],
            success: function (layero, index) {
                var iframe = window['layui-layer-iframe' + index];
                iframe.setData(data[0]);
            },
            end: function () {
                removeModal();
                table.reload('xtggTable');
            }
        });
    }

    /**
     * 删除
     * @param data
     */
    function deleteBdcXtGg(data) {
        if (!data || data.length === 0) {
            layer.alert("请选择需要删除的记录！", {title: '提示'});
            return;
        }
        var xtggidList =[];
        for(var i = 0; i < data.length; i++){
            xtggidList.push(data[i].xtggid);

        }
        var deleteIndex = layer.open({
            type: 1,
            title: '确认删除',
            area: ['320px'],
            skin: 'bdc-small-tips',
            content: '确定要删除所选公告配置？',
            btn: ['确定', '取消'],
            btnAlign: 'c',
            yes: function () {
                $.ajax({
                    url: "/realestate-inquiry-ui/rest/v1.0/xtgg",
                    type: "DELETE",
                    data: JSON.stringify(xtggidList),
                    contentType: 'application/json',
                    dataType: "json",
                    success: function (data) {
                        if (data && $.isNumeric(data) && data > 0) {
                            delSuccessMsg();
                            table.reload('xtggTable');
                        } else {
                            delFailMsg();
                        }
                    },
                    error: function () {
                        delFailMsg();
                    }
                });
                layer.close(deleteIndex);
            },
            btn2: function () {
                //取消
            }
        });

    }

    // 获取工作流名称
    var gzldymcs;
    getGzldymcs();
    function getGzldymcs() {
        $.ajax({
            url: BASE_URL + '/mryj/gzldymcs',
            type: "GET",
            dataType: "json",
            success: function (data) {
                if(data){
                    formSelects.data('selectGzldymc','local',{arr:data});
                    if(gzldyid!=null && gzldyid!=''){
                        formSelects.value('selectGzldymc', [gzldyid]);
                        formSelects.disabled('selectGzldymc');
                    }
                }
                gzldymcs = data;
            }
        });
    }

    //字典项代码转名称
    function changeDmToMc(dm, zdList) {
        if (dm) {
            var mc = dm;
            if (isNotBlank(zdList)) {
                for (var i = 0; i < zdList.length; i++) {
                    var zd = zdList[i];
                    if (dm ==zd.DM) {
                        mc = zd.MC;
                        break;
                    }
                }
            }
            return mc;
        } else {
            return "";
        }
    }

});