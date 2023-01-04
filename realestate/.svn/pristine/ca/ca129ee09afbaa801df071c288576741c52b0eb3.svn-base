/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @description 默认意见js
 */
var allLcdy = new Array();
layui.use(['table', 'laytpl', 'laydate', 'layer', 'form'], function () {
    var $ = layui.jquery,
        table = layui.table,
        layer = layui.layer,
        formSelects = layui.formSelects,
        form = layui.form;

    var BASE_URL = '/realestate-inquiry-ui/rest/v1.0';

    formSelects.config('selectDjxl', {
        keyName: 'MC',            //自定义返回数据中name的key, 默认 name
        keyVal: 'DM'            //自定义返回数据中value的key, 默认 value
    }, true);
    formSelects.config('selectPzGzldymc', {
        keyName: 'name',            //自定义返回数据中name的key, 默认 name
        keyVal: 'key'            //自定义返回数据中value的key, 默认 value
    }, true);

    /**
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取工作流定义名称
     */
    $.ajax({
        url: BASE_URL + '/mryj/gzldymcs',
        type: "GET",
        dataType: "json",
        async:false,
        success: function (data) {
            if(data){
                $.each(data, function (index, item) {
                    if (item.processDefKey) {
                        allLcdy.push(item.processDefKey);
                    }
                })
                formSelects.data('selectPzGzldymc','local',{arr:data})
            }
        }
    });

    $.ajax({
        url: BASE_URL+"/lcpz/zdxx",
        type: "POST",
        data:'djlx,djxl',
        contentType: 'application/json',
        dataType: "json",
        async:false,
        success: function (data) {
            if(data){
                formSelects.data('selectDjxl','local',{arr:data.djxl});
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
        elem: '#lcpzTable',
        toolbar: '#toolbar',
        title: '收件材料配置列表',
        defaultToolbar: ['filter'],
        url: BASE_URL+'/lcpz/bdcDjxlPz/list',
        request: {
            limitName: 'size' //每页数据量的参数名，默认：limit
        },
        cellMinWidth: 80,
        where: {},
        even: true,
        cols: [[
            {type: 'checkbox', fixed: 'left'},
            {field: 'sxh', title: '序号',hide:true,width:60},
            {field: 'sxh', title: 'sxh', hide: true},
            {field: 'pzid', title: 'pzid', hide: true},
            {field: 'qllx', title: 'qllx', hide: true},
            {field: 'dyhqllx', title: 'dyhqllx', hide: true},
            {field: 'djxl', title: 'djxl', hide: true},
            {field: 'gzldyid', title: 'gzldyid', hide: true},
            {field: 'bdclx', title: 'bdclx', hide: true},
            {field: 'gzldymc', title: '工作流定义名称'},
            {field: 'djxlMc', title: '登记小类'},
            {field: 'qllxMc', title: '权利类型'},
            {field: 'dyhqllxMc', title: '单元号权利类型'},
            {field: 'bdclxMc', title: '不动产类型'},
            {field: 'sjddylx', title: 'sjddylx', hide: true},
            {field: 'sqsdylx', title: 'sqsdylx', hide: true},
            {field: 'spbdylx', title: 'spbdylx', hide: true},
            {field: 'damldylx', title: 'damldylx', hide: true},
            {
                field: 'sfsf', title: '是否收费', templet: '#sfsfTpl'
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
                $('.layui-table-body').height($('.bdc-table-box').height() - 131);
                $('.layui-table-fixed .layui-table-body').height($('.bdc-table-box').height() - 131 - 17);
            }
        }
    });

    //头工具栏事件
    table.on('toolbar(lcpzTable)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id);
        switch (obj.event) {
            case 'add':
                addBdclcpz();
                break;
            case 'edit':
                editBdclcpz(checkStatus.data);
                break;
            case 'delete':
                deleteBdclcpz(checkStatus.data);
                break;
            case 'pz':
                pz(checkStatus.data);
                break;
            case 'fz':
                fz(checkStatus.data);
                break;
            case 'sbpzjy':
                sbpzjy(checkStatus.data);
                break;
        }
    });

    /**
     * 监听排序事件
     */
    table.on('sort(lcpzTable)', function (obj) {
        table.reload('lcpzTable', {
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
        var obj = {'gzldyid':'','djxl':'','slbh':''};
        var gzldyidArray=formSelects.value('selectPzGzldymc');
        var djxlArray=formSelects.value('selectDjxl');
        var slbh = $("#slbh").val();
        if(gzldyidArray.length!=0){
            obj['gzldyid']=gzldyidArray[0].value;
        }
        if(djxlArray.length!=0){
            obj['djxl']=djxlArray[0].value;
        }
        if(!slbh.isNullOrUndefined) {
            obj['slbh'] = slbh;
        }
        // 重新请求
        table.reload("lcpzTable", {
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
        table.reload("lcpzTable", {
            where: []
            , page: {
                curr: 1  //重新从第 1 页开始
            }
        });
    };

    /**
     * 新增
     */
    function addBdclcpz() {
        addModel('');
        layer.open({
            title: '新增登记小类配置',
            type: 2,
            area: ['960px', '300px'],
            content: ["bdcDjxlPz/addOrEditDjxlPz.html?action=add", 'yes']
            , end: function () {
                removeModal();
                table.reload('lcpzTable');
            }
        });
    }

    /**
     * 编辑
     * @param data
     */
    function editBdclcpz(data) {
        if (!data || data.length != 1) {
            layer.alert("请选择需要编辑的某一条记录！", {title: '提示'});
            return;
        }
        if (allLcdy.length > 0 && $.inArray(data[0].gzldyid, allLcdy) < 0) {
            layer.alert("[" + data[0].gzldymc + "]工作流定义已经挂起，不能操作！", {title: '提示'});
            return;
        }
        addModel('');
        layer.open({
            type: 2,
            title: '编辑登记小类配置',
            anim: -1,
            shadeClose: true,
            maxmin: true,
            shade: false,
            area: ['960px', '300px'],
            offset: 'auto',
            content: ["bdcDjxlPz/addOrEditDjxlPz.html?action=edit", 'yes'],
            success: function (layero, index) {
                var iframe = window['layui-layer-iframe' + index];
                iframe.setData(data[0]);
            },
            end: function () {
                removeModal();
                table.reload('lcpzTable');
            }
        });
    }

    /**
     * 删除
     * @param data
     */
    function deleteBdclcpz(data) {
        if (!data || data.length == 0) {
            layer.alert("请选择需要删除的记录！", {title: '提示'});
            return;
        }
        var deleteIndex = layer.open({
            type: 1,
            title: '确认删除',
            area: ['320px'],
            skin: 'bdc-small-tips',
            content: '确定要删除所选配置？',
            btn: ['确定', '取消'],
            btnAlign: 'c',
            yes: function () {
                $.ajax({
                    url: BASE_URL+"/lcpz/bdcDjxlPz",
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

    function pz(data){
        if (!data || data.length != 1) {
            layer.alert("请选择一条数据进行配置！", {title: '提示'});
            return;
        }
        if (allLcdy.length > 0 && data.length > 0 && $.inArray(data[0].gzldyid, allLcdy) < 0) {
            layer.alert("[" + data[0].gzldymc + "]工作流定义已经挂起，不能操作！", {title: '提示'});
            return;
        }
        var url='';
        if(data.length==0){
            url='step1.html';
        } else if(data.length==1){
            url+='step1.html?gzldyid='+data[0].gzldyid+'&djxl='+data[0].djxl+'&qllx='+data[0].qllx
        }
        window.open(url);
    }

    function fz(data){
        if (!data || data.length !=1) {
            layer.alert("请选择一条数据进行复制！", {title: '提示'});
            return;
        }
        if (allLcdy.length > 0 && $.inArray(data[0].gzldyid, allLcdy) < 0) {
            layer.alert("[" + data[0].gzldymc + "]工作流定义已经挂起，不能操作！", {title: '提示'});
            return;
        }
        layer.open({
            title: '复制流程配置',
            type: 2,
            area: ['430px','360px'],
            content: 'fz.html?gzldyid='+data[0].gzldyid+'&bfzDjxl='+data[0].djxl
            , cancel: function () {
                //右上角关闭回调
                //return false 开启该代码可禁止点击该按钮关闭
            }
            , success: function () {

            }
        });
    }

    function sbpzjy(checkData) {
        var pzidList = [];
        if (checkData.length === 0) {
            addModel('');
            var deleteIndex = layer.open({
                type: 1,
                title: '确认',
                area: ['320px'],
                skin: 'bdc-small-tips',
                content: '未勾选数据确认查询所有流程？',
                btn: ['确定', '取消'],
                btnAlign: 'c',
                yes: function () {
                    $.ajax({
                        url: BASE_URL + "/lcpz/sbpzjy",
                        type: "POST",
                        data: JSON.stringify(pzidList),
                        contentType: 'application/json',
                        dataType: "json",
                        success: function (data) {
                            removeModal();
                            layer.msg('<img src="../../../static/lib/bdcui/images/success-small.png" alt="">检查完成，请稍后在检测台账查看结果', {
                                    time: 3000
                                }
                            );
                        },
                        error: function () {
                            removeModal();
                            layer.alert("检查失败，请重试！", {title: '提示'});
                        }
                    });
                    layer.close(deleteIndex);
                },
                btn2: function () {
                    removeModal();
                    //取消
                },
                cancel: function (index) {
                    removeModal();
                    layer.close(index);
                }
            });
        } else {
            addModel('');
            for (var i = 0; i < checkData.length; i++) {
                pzidList.push(checkData[i].pzid);
            }
            $.ajax({
                url: BASE_URL + "/lcpz/sbpzjy",
                type: "POST",
                data: JSON.stringify(pzidList),
                contentType: 'application/json',
                dataType: "json",
                success: function (data) {
                    removeModal();
                    layer.msg('<img src="../../../static/lib/bdcui/images/success-small.png" alt="">检查完成，请在检测台账查看结果', {
                            time: 1000
                        }
                    );
                },
                error: function () {
                    removeModal();
                    layer.alert("检查失败，请重试！", {title: '提示'});
                }
            });
        }
    }

    /**
     * 行双击编辑
     */
    table.on('rowDouble(lcpzTable)', function (obj) {
        var array = new Array();
        array.push(obj.data);
        editBdclcpz(array);
    });


});