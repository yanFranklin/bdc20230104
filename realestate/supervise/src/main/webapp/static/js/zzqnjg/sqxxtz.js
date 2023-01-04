/**
 * 4 职责权能监管-授权信息管理台账
 */
layui.use(['form', 'jquery', 'laydate', 'element', 'table'], function () {
    var $ = layui.jquery,
        form = layui.form,
        laydate = layui.laydate,
        element = layui.element,
        table = layui.table;

    var kssj = laydate.render({
        elem: '#sqsjq',
        type: 'datetime',
        trigger: 'click',
        done: function (value, date) {
            //选择的结束时间大
            if ($('#sqsjz').val() != '' && !completeDate($('#sqsjz').val(), value)) {
                $('#sqsjz').val('');
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
        elem: '#sqsjz',
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

    // 获取页面表单标识，用于权限控制
    var moduleCode = $.getUrlParam('moduleCode');
    if (moduleCode) {
        setElementAttrByModuleAuthority(moduleCode);
    }

    table.render({
        elem: '#pageTable',
        toolbar: '#toolbar',
        url: "/realestate-supervise/rest/v1.0/zzqnjg/sqxxs",
        title: '授权台账',
        defaultToolbar: ['filter'],
        request: {
            limitName: 'size' //每页数据量的参数名，默认：limit
        },
        cellMinWidth: 80,
        even: true,
        cols: [[
            {type: 'checkbox', fixed: 'left'},
            {width: 350, sort: false, field: 'sqryxx', title: '授权人员'},
            {width: 300, sort: false, field: 'bmmc', title: '所属单位'},
            {width: 200, sort: false, field: 'ks', title: '所属科室'},
            {width: 160, sort: false, field: 'sqlx', title: '授权类型',
                templet: function (d) {
                    if(1 === d.sqlx) {
                        return "新增";
                    } else if(2 === d.sqlx) {
                        return "调岗";
                    } else if(3 === d.sqlx) {
                        return "离职";
                    } else if(4 === d.sqlx) {
                        return "临时授权";
                    } else {
                        return "";
                    }
                }
            },
            {minWidth: 250, sort: false, field: 'sqsm', title: '授权说明'},
            {width: 200, sort: false, field: 'spwjmc', title: '审批文件名称', hide: true},
            {width: 200, sort: false, field: 'spwjlj', title: '审批文件路径', hide: true},
            {width: 200, sort: false, field: 'spwjscsj', title: '审批文件上传时间', hide: true,
                templet: function (d) {
                    return formatDate(d.spwjscsj);
                }
            },
            {width: 150, sort: false, field: 'czr', title: '操作人'},
            {width: 200, sort: false, field: 'czsj', title: '操作时间',
                templet: function (d) {
                    return formatDate(d.czsj);
                }
            },
            {width: 120, sort: false, templet: '#barAction', title: '操作', fixed: 'right'}
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

    //点击高级查询
    $('#seniorSearch').on('click', function () {
        $('.pf-senior-show').toggleClass('bdc-hide');
        $(this).parent().toggleClass('bdc-button-box-four');
        $('.bdc-percentage-container').css('padding-top',$('.bdc-search-content').height() + 10);
        setHeight();
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

        // 重新请求
        table.reload("pageTable", {
            url: "/realestate-supervise/rest/v1.0/zzqnjg/sqxxs"
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

    //监听工具条
    table.on('tool(pageTable)', function (obj) {
        var data = obj.data;
        if (obj.event === 'downloadSpwj') {
            downloadSpwj(data);
        }
    });

    //头工具栏事件
    table.on('toolbar(pageTable)', function (obj) {
        var data = table.checkStatus('pageTable').data;
        if(obj.event === 'add'){
            add();
        } else if(obj.event === 'edit'){
            edit(data);
        } else if(obj.event === 'deleteSpwj'){
            deleteSpwj(data);
        }
    });

    /**
     * 新增授权信息
     */
    function add() {
        layer.open({
            type: 2,
            title: '授权信息',
            anim: -1,
            shadeClose: true,
            maxmin: true,
            shade: false,
            area: ['960px', '460px'],
            offset: 'auto',
            content: [ "sqxx.html", 'yes'],
            success: function (layero, index) {
            },
            end:function(){
                search();
            }
        });
    }

    /**
     * 编辑授权信息
     * @param data
     */
    function edit(data) {
        if(!data || !data[0] || isNullOrEmpty(data[0].id)) {
            warnMsg("请选择需要编辑的记录");
            return false;
        }

        layer.open({
            type: 2,
            title: '授权信息',
            anim: -1,
            shadeClose: true,
            maxmin: true,
            shade: false,
            area: ['960px', '460px'],
            offset: 'auto',
            content: [ "sqxx.html?id=" + data[0].id, 'yes'],
            success: function (layero, index) {
            },
            end:function(){
                search();
            }
        });
    }

    /**
     * 删除授权信息
     * @param data
     */
    function deleteSpwj(data) {
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
                    url: "/realestate-supervise/rest/v1.0/zzqnjg/sqxx",
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

    /**
     * 查看审批文件
     * @param data
     * @returns {boolean}
     */
    function downloadSpwj(data) {
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
