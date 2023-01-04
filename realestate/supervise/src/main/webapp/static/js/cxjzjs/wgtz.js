/**
 * 7 诚信机制建设：违规台账
 */
layui.use(['form', 'jquery', 'laydate', 'element', 'table'], function () {
    var $ = layui.jquery,
        form = layui.form,
        laydate = layui.laydate,
        element = layui.element,
        table = layui.table;

    var kssj = laydate.render({
        elem: '#jlsjq',
        type: 'datetime',
        trigger: 'click',
        done: function (value, date) {
            //选择的结束时间大
            if ($('#jlsjz').val() != '' && !completeDate($('#jlsjz').val(), value)) {
                $('#jlsjz').val('');
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
        elem: '#jlsjz',
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
        url: "/realestate-supervise/rest/v1.0/cxjzjs/wgxx",
        title: '违规台账',
        defaultToolbar: ['filter'],
        request: {
            limitName: 'size' //每页数据量的参数名，默认：limit
        },
        cellMinWidth: 80,
        even: true,
        cols: [[
            {type: 'checkbox', fixed: 'left'},
            {width: 250, sort: false, field: 'wgryjgmc', title: '违规人员机构名称'},
            {width: 250, sort: false, field: 'wgryjgzjh', title: '违规人员机构证件号'},
            {width: 200, sort: false, field: 'wgryjgdz', title: '违规人员机构住址'},
            {minWidth: 350, sort: false, field: 'wgxw', title: '违规行为'},
            {width: 200, sort: false, field: 'zmwjmc', title: '证明文件名称', hide: true},
            {width: 200, sort: false, field: 'zmwjlj', title: '证明文件路径', hide: true},
            {width: 200, sort: false, field: 'zmwjscsj', title: '证明文件上传时间', hide: true,
                templet: function (d) {
                    return formatDate(d.zmwjscsj);
                }
            },
            {width: 150, sort: false, field: 'jlr', title: '记录人'},
            {width: 200, sort: false, field: 'jlsj', title: '记录时间',
                templet: function (d) {
                    return formatDate(d.jlsj);
                }
            },
            {width: 150, sort: false, field: 'shry', title: '审核人'},
            {width: 250, sort: false, field: 'shyj', title: '审核意见'},
            {width: 200, sort: false, field: 'shsj', title: '审核时间',
                templet: function (d) {
                    return formatDate(d.shsj);
                }
            },
            {width: 100, sort: false, field: 'shsj', title: '审核状态',
                templet: function (d) {
                    if(d.shzt === 1) {
                        return "已审核";
                    } else {
                        return "未审核";
                    }
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
            url: "/realestate-supervise/rest/v1.0/cxjzjs/wgxx"
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
        if (obj.event === 'downloadWgjl') {
            downloadWgjl(data);
        }
    });

    //头工具栏事件
    table.on('toolbar(pageTable)', function (obj) {
        var data = table.checkStatus('pageTable').data;
        if(obj.event === 'addWgjl'){
            addWgjl();
        } else if(obj.event === 'editWgjl'){
            editWgjl(data);
        } else if(obj.event === 'deleteWgjl'){
            deleteWgjl(data);
        }
    });

    /**
     * 新增违规信息
     */
    function addWgjl() {
        layer.open({
            type: 2,
            title: '违规信息',
            anim: -1,
            shadeClose: true,
            maxmin: true,
            shade: false,
            area: ['960px', '400px'],
            offset: 'auto',
            content: [ "wgxx.html", 'yes'],
            success: function (layero, index) {
            },
            end:function(){
                search();
            }
        });
    }

    /**
     * 编辑违规信息
     * @param data
     */
    function editWgjl(data) {
        if(!data || !data[0] || isNullOrEmpty(data[0].id)) {
            warnMsg("请选择需要编辑的记录");
            return false;
        }

        layer.open({
            type: 2,
            title: '违规信息',
            anim: -1,
            shadeClose: true,
            maxmin: true,
            shade: false,
            area: ['960px', '400px'],
            offset: 'auto',
            content: [ "wgxx.html?id=" + data[0].id, 'yes'],
            success: function (layero, index) {
            },
            end:function(){
                search();
            }
        });
    }

    /**
     * 删除违规信息
     * @param data
     */
    function deleteWgjl(data) {
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
                    url: "/realestate-supervise/rest/v1.0/cxjzjs/wgxx",
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
     * 下载违规证明报告
     * @param data
     * @returns {boolean}
     */
    function downloadWgjl(data) {
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
