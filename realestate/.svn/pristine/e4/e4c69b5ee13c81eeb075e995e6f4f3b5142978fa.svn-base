/**
 * 异常办件预警——异常原因台账
 */
layui.config({
    base: '../../static/lib/form-select/'
}).extend({
    formSelects: 'formSelects-v4'
});
layui.use(['form', 'jquery', 'laydate', 'element', 'table','formSelects'], function () {
    var $ = layui.jquery,
        form = layui.form,
        laydate = layui.laydate,
        element = layui.element,
        table = layui.table;
    var formSelects = layui.formSelects;

    var kssj = laydate.render({
        elem: '#lrsjq',
        type: 'datetime',
        trigger: 'click',
        done: function (value, date) {
            //选择的结束时间大
            if ($('#lrsjz').val() != '' && !completeDate($('#lrsjz').val(), value)) {
                $('#lrsjz').val('');
                $('.laydate-disabled.layui-this').removeClass('layui-this');
            }
            jssj.config.min = {
                year: date.year,
                month: date.month - 1,
                date: date.date,
                hours: date.hours,
                minutes: date.minutes,
                seconds: date.seconds
            }
        }
    });
    var jssj = laydate.render({
        elem: '#lrsjz',
        type: 'datetime',
        trigger: 'click'
    });


    //比较起止时间
    function completeDate(date1, date2) {
        var oDate1 = new Date(date1);
        var oDate2 = new Date(date2);
        if (oDate1.getTime() > oDate2.getTime()) {
            return true;
        } else {
            return false;
        }
    }

    // 加载工作流下拉框
    $.ajax({
        url: "/realestate-supervise/rest/v1.0/ycbjyj/process",
        type: "GET",
        dataType: "json",
        success: function (data) {
            if(data){
                var formSelectData = new Array();
                $.each(data, function (index, item) {
                    formSelectData.push({"name":item.name, "value":item.name});
                });
                formSelects.data('gzldymc', 'local', {arr: formSelectData});
                form.render('select');
            }
        },
        complete: function () {
        }
    });

    // 获取页面表单标识，用于权限控制
    var moduleCode = $.getUrlParam('moduleCode');
    if (moduleCode) {
        setElementAttrByModuleAuthority(moduleCode);
    }

    table.render({
        elem: '#pageTable',
        toolbar: '#toolbar',
        url: "/realestate-supervise/rest/v1.0/ycbjyj/ycyy",
        title: '异常办件原因台账',
        defaultToolbar: ['filter'],
        request: {
            limitName: 'size' //每页数据量的参数名，默认：limit
        },
        cellMinWidth: 80,
        even: true,
        cols: [[
            {type: 'checkbox', fixed: 'left'},
            {width: 150, sort: false, field: 'slbh', title: '受理编号'},
            {width: 250, sort: false, field: 'gzldymc', title: '流程名称'},
            {width: 150, sort: false, field: 'gzlslid', title: '工作流实例ID', hide: true},
            {width: 180, sort: false, field: 'slbh', title: '受理编号'},
            {minWidth: 180, sort: false, field: 'ycbjyy', title: '异常办件原因'},
            {width: 180, sort: false, field: 'lrr', title: '录入人员'},
            {width: 200, sort: false, field: 'lrsj', title: '录入时间',
                templet: function (d) {
                    return formatDate(d.lrsj);
                }
            },
            {width: 200, sort: false, templet: '#barAction', title: '操作', fixed: 'right'}
        ]],
        text: {
            none: '未查询到数据'
        },
        data: [],
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
                $('.layui-table-body .layui-none').html('<img src="../../static/lib/registerui/image/table-none.png" alt="">无数据');
            }else {
                $('.layui-table-body').height($('.bdc-table-box').height() - 131);
                $('.layui-table-fixed .layui-table-body').height($('.bdc-table-box').height() - 131 - 17);
            }
        }
    });

    /**
     * 点击查询
     */
    $('#search').on('click', function () {
        search();
        return false;
    });

    function search() {
        addModel();
        // 获取查询内容
        var obj = {};
        $(".search").each(function (i) {
            var value = $(this).val();
            var name = $(this).attr('name');
            obj[name] = value;
        });
        obj.gzldymc =  formSelects.value('gzldymc', 'valStr');

        // 重新请求
        table.reload("pageTable", {
            url: "/realestate-supervise/rest/v1.0/ycbjyj/ycyy"
            ,where: obj
            , page: {
                curr: 1  //重新从第 1 页开始
            },
            done: function () {
                $('.layui-table-tool-self').css('right',$('.bdc-export-tools').width()+ 17 + 'px');

                if($('.bdc-table-box .layui-table-main>.layui-table').height() == 0){
                    $('.layui-table-body .layui-none').html('<img src="../../static/lib/registerui/image/table-none.png" alt="">无数据');
                }else {
                    $('.layui-table-body').height($('.bdc-table-box').height() - 131);
                    $('.layui-table-fixed .layui-table-body').height($('.bdc-table-box').height() - 131 - 17);
                }
                removeModal();
            }
        });
        return false;
    }

    //绑定回车键
    $(document).keydown(function (event) {
        if (event.keyCode == 13) {
            search();
        }
    });

    //头工具栏事件
    table.on('toolbar(pageTable)', function (obj) {
        var data = table.checkStatus('pageTable').data;
        if(obj.event === 'addYcyy'){
            addYcyy();
        } else if(obj.event === 'editYcyy'){
            editYcyy(data);
        } else if(obj.event === 'deleteYcyy'){
            deleteYcyy(data);
        }
    });

    /**
     * 新增异常原因
     */
    function addYcyy() {
        layer.open({
            type: 2,
            title: '异常原因',
            anim: -1,
            shadeClose: true,
            maxmin: true,
            shade: false,
            area: ['960px', '400px'],
            offset: 'auto',
            content: [ "ycyy.html", 'yes'],
            success: function (layero, index) {
            },
            end:function(){
                search();
            }
        });
    }

    /**
     * 编辑异常原因
     * @param data
     */
    function editYcyy(data) {
        if(!data || !data[0] || isNullOrEmpty(data[0].id)) {
            warnMsg("请选择需要编辑的记录");
            return false;
        }

        layer.open({
            type: 2,
            title: '异常原因',
            anim: -1,
            shadeClose: true,
            maxmin: true,
            shade: false,
            area: ['960px', '400px'],
            offset: 'auto',
            content: [ "ycyy.html?id=" + data[0].id, 'yes'],
            success: function (layero, index) {
            },
            end:function(){
                search();
            }
        });
    }

    /**
     * 删除异常原因
     * @param data
     */
    function deleteYcyy(data) {
        if(!data || 0 === data.length) {
            warnMsg("请选择需要删除的记录");
            return false;
        }

        var deleteIndex = layer.open({
            type: 1,
            skin: 'bdc-small-tips',
            title: '确认删除',
            area: ['320px'],
            content: '是否确认删除？',
            btn: ['确定', '取消'],
            btnAlign: 'c',
            yes: function () {
                //确定操作
                addModel();
                $.ajax({
                    url: "/realestate-supervise/rest/v1.0/ycbjyj/ycyy",
                    type: "DELETE",
                    data: JSON.stringify(data),
                    contentType: "application/json;charset=UTF-8",
                    dataType: "text",
                    success: function (data) {
                        if(data && data > 0) {
                            successMsg("删除成功！");
                            search();
                        } else {
                            failMsg("删除失败！");
                        }
                    },
                    complete: function () {
                        removeModal();
                    }
                });
                layer.close(deleteIndex);
            },
            btn2: function () {
                //取消
                layer.close(deleteIndex);
            }
        });
    }

    //监听工具条
    table.on('tool(pageTable)', function (obj) {
        var data = obj.data;
        if (obj.event === 'lct') {
            lct(data);
        } else if (obj.event === 'viewFile') {
            viewFile(data);
        }
    });

    function lct(data) {
        if(!data || isNullOrEmpty(data.gzlslid)) {
            warnMsg("未查询到流程实例信息，无法查看！");
            return;
        }

        window.open("/bpm-ui/process/processDetail/" + data.gzlslid);
    }

    /**
     * 查看附件
     * @param data
     * @returns {boolean}
     */
    function viewFile(data) {
        layer.open({
            type: 2,
            title: '附件信息',
            anim: -1,
            shadeClose: true,
            maxmin: true,
            shade: false,
            area: ['960px', '500px'],
            offset: 'auto',
            content: [ "../file/files.html?page=edit&ywid=" + data.id, 'yes'],
            success: function (layero, index) {
            },
            end:function(){
            }
        });
    }
});
