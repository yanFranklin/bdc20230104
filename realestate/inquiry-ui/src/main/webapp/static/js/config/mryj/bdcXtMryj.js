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
    var BASE_URL = '/realestate-inquiry-ui/rest/v1.0';
    formSelects.config('selectGzldymc', {
        keyName: 'name',            //自定义返回数据中name的key, 默认 name
        keyVal: 'key'            //自定义返回数据中value的key, 默认 value
    }, true);
    formSelects.config('selectJdmc', {
        keyName: 'name',            //自定义返回数据中name的key, 默认 name
        keyVal: 'id'            //自定义返回数据中value的key, 默认 value
    }, true);
    var gzldyid = $.getUrlParam('gzldyid');
    var mryjUrl=BASE_URL + "/mryj/page";
    /**
     * 加载Table数据列表
     */
    table.render({
        elem: '#mryjTable',
        toolbar: '#toolbar',
        title: '默认意见配置列表',
        defaultToolbar: ['filter'],
        url: mryjUrl,
        where: {gzldyid: gzldyid},
        request: {
            limitName: 'size' //每页数据量的参数名，默认：limit
        },
        cellMinWidth: 80,
        even: true,
        cols: [[
            {type: 'checkbox', fixed: 'left'},
            {field: 'mryjid', title: 'mryjid', hide: true},
            {field: 'gzldyid', title: 'gzldyid', hide: true},
            {field: 'cjrid', title: '创建人id', hide: true},
            {field: 'gzldymc', title: '工作流名称', width: 300},
            {field: 'jdmc', title: '节点名称', width: 200},
            {field: 'cjrmc', title: '创建人', width: 200},
            {
                field: 'sfky', title: '是否可用', width: 100,
                templet: function (d) {
                    return 1 == d.sfky ? "是" : "否";
                }
            },
            {field: 'yj', title: '意见', style: 'text-align:left'},
            {
                field: 'yjlx', title: '意见类型', width: 200,
                templet: function (d) {
                    return 0 == d.yjlx ? "默认意见" : "可选意见";
                }
            },
            {field: 'sjlx', title: 'sjlx', hide: true}
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
    table.on('toolbar(mryjTable)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id);
        switch (obj.event) {
            case 'add':
                addBdcXtMryj(gzldyid);
                break;
            case 'edit':
                editBdcXtMryj(checkStatus.data);
                break;
            case 'delete':
                deleteBdcXtMryj(checkStatus.data);
                break;
            case 'sl':
                mryjSl();
                break;
        }
        ;
    });

    /**
     * 监听排序事件
     */
    table.on('sort(mryjTable)', function (obj) {
        table.reload('mryjTable', {
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
        var obj = {'gzldymc':'','jdmc':'','sfky':'','yjlx':''};
        var gzlmcArray=formSelects.value('selectGzldymc');
        var jdmcArray=formSelects.value('selectJdmc');
        var sfkyArray=formSelects.value('selectSfky');
        var yjlxArray=formSelects.value('selectYjlx');
        if(gzlmcArray.length!=0){
            obj['gzldymc']=gzlmcArray[0].name;
        }
        if(jdmcArray.length!=0){
            obj['jdmc']=jdmcArray[0].name;
        }
        if(sfkyArray.length!=0){
            obj['sfky']=sfkyArray[0].value;
        }
        if(yjlxArray.length!=0){
            obj['yjlx']=yjlxArray[0].value;
        }
        //获取创建人id
        var cjrmc = $('#cjrmc').val();
        var checkResult = true;
        if (cjrmc != null && cjrmc != '') {
            $.ajax({
                url: BASE_URL + '/mryj/cjr?cjrmc=' + cjrmc,
                type: "GET",
                async: false,
                success: function (data) {
                    if (!data) {
                        layer.alert("不存在此创建人，请核对创建人信息！", {title: '提示'});
                        checkResult = false;
                    }
                    obj['cjrid'] = data;
                },
                error: function () {
                    layer.alert("核对创建人信息出错！", {title: '提示'});
                    checkResult = false;
                }
            });
            if (!checkResult) {
                return checkResult;
            }
        }

        // 重新请求
        table.reload("mryjTable", {
            where: obj
            , page: {
                curr: 1  //重新从第 1 页开始
            }
        });
    });

    /**
     * 点击重置，默认工作流名称不重置
     */
    $('#reset').on('click',function () {
        getGzldymcs();
    });

    /**
     * 重新加载数据
     */
    window.reload = function () {
        table.reload("mryjTable", {
            where: []
            , page: {
                curr: 1  //重新从第 1 页开始
            }
        });
    };

    /**
     * 新增
     */
    function addBdcXtMryj(gzldyid) {
        addModel('');
        layer.open({
            title: '新增意见配置',
            type: 2,
            area: ['960px', '350px'],
            content: ["../mryj/addOrEditBdcXtMryj.html?action=add&gzldyid="+gzldyid, 'yes']
            , end: function () {
                removeModal();
                table.reload('mryjTable');
            }
        });
    }

    /**
     * 编辑
     * @param data
     */
    function editBdcXtMryj(data) {
        if (!data || data.length != 1) {
            layer.alert("请选择需要编辑的某一条记录！", {title: '提示'});
            return;
        }
        addModel('');
        layer.open({
            type: 2,
            title: '编辑意见配置',
            anim: -1,
            shadeClose: true,
            maxmin: true,
            shade: false,
            area: ['960px', '350px'],
            offset: 'auto',
            content: ["../mryj/addOrEditBdcXtMryj.html?action=edit", 'yes'],
            success: function (layero, index) {
                var iframe = window['layui-layer-iframe' + index];
                iframe.setData(data[0]);
            },
            end: function () {
                removeModal();
                table.reload('mryjTable');
            }
        });
    }

    /**
     * 删除
     * @param data
     */
    function deleteBdcXtMryj(data) {
        if (!data || data.length == 0) {
            layer.alert("请选择需要删除的记录！", {title: '提示'});
            return;
        }
        var deleteIndex = layer.open({
            type: 1,
            title: '确认删除',
            area: ['320px'],
            skin: 'bdc-small-tips',
            content: '确定要删除所选意见配置？',
            btn: ['确定', '取消'],
            btnAlign: 'c',
            yes: function () {
                $.ajax({
                    url: "/realestate-inquiry-ui/rest/v1.0/mryj",
                    type: "DELETE",
                    data: JSON.stringify(data),
                    contentType: 'application/json',
                    dataType: "json",
                    success: function (data) {
                        if (data && $.isNumeric(data) && data > 0) {
                            delSuccessMsg();
                            table.reload('mryjTable');
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

    function mryjSl() {
        layer.open({
            title: '新增意见配置示例',
            type: 2,
            area: ['960px','500px'],
            content: ["../mryj/mryjSl.html", 'yes']
            , end: function () {
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
                    formSelects.data('selectGzldymc','local',{arr:data})
                    if(gzldyid!=null && gzldyid!=''){
                        formSelects.value('selectGzldymc', [gzldyid]);
                        formSelects.disabled('selectGzldymc');
                        $.ajax({
                            url: BASE_URL + '/mryj/jdmcs?processDefKey=' + gzldyid,
                            type: "GET",
                            dataType: "json",
                            success: function (data) {
                                if(data){
                                    formSelects.data('selectJdmc','local',{arr:data})
                                }
                            }
                        });
                    }
                }
                gzldymcs = data;
            }
        });
    }

    // 联动对应处理
    formSelects.on('selectGzldymc', function(id, vals, val, isAdd, isDisabled){
        //id:           点击select的id
        //vals:         当前select已选中的值
        //val:          当前select点击的值
        //isAdd:        当前操作选中or取消
        //isDisabled:   当前选项是否是disabled
        $('#gzldymc').val(val.name);
        $.ajax({
            url: BASE_URL + '/mryj/jdmcs?processDefKey=' + val.key,
            type: "GET",
            dataType: "json",
            success: function (data) {
                if(data){
                    formSelects.data('selectJdmc','local',{arr:data})
                }
            }
        });
    });

    /**
     * 行双击编辑
     */
    table.on('rowDouble(mryjTable)', function (obj) {
        var array = new Array();
        array.push(obj.data);
        editBdcXtMryj(array);
    });

});