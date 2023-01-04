/**
 * 异常情况主动申报台账台账
 */
layui.use(['form', 'jquery', 'laydate', 'element', 'table'], function () {
    var $ = layui.jquery,
        form = layui.form,
        laydate = layui.laydate,
        element = layui.element,
        table = layui.table;

    var kssj = laydate.render({
        elem: '#tbsjq',
        type: 'datetime',
        trigger: 'click',
        done: function (value, date) {
            //选择的结束时间大
            if ($('#tbsjz').val() != '' && !completeDate($('#tbsjz').val(), value)) {
                $('#tbsjz').val('');
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
        elem: '#tbsjz',
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
        url: "/realestate-supervise/rest/v1.0/ycbjyj/ycqksb/page",
        title: '授权台账',
        defaultToolbar: ['filter'],
        request: {
            limitName: 'size' //每页数据量的参数名，默认：limit
        },
        cellMinWidth: 80,
        even: true,
        cols: [[
            {type: 'checkbox', fixed: 'left'},
            {width: 200, sort: false, field: 'slbh', title: '受理编号'},
            {width: 200, sort: false, field: 'slsj', title: '受理时间',
                templet: function (d) {
                    return formatDate(d.slsj);
                }
            },
            {width: 200, sort: false, field: 'tbr', title: '填报人'},
            {width: 200, sort: false, field: 'tbsj', title: '填报时间',
                templet: function (d) {
                    return formatDate(d.tbsj);
                }
            },
            {minWidth: 150, sort: false, field: 'yysm', title: '原因说明'},
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
            url: "/realestate-supervise/rest/v1.0/ycbjyj/ycqksb/page"
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
        if (obj.event === 'downloadFjxx') {
            downloadFjxx(data);
        }
    });

    //头工具栏事件
    table.on('toolbar(pageTable)', function (obj) {
        var data = table.checkStatus('pageTable').data;
        if(obj.event === 'view'){
            view(data);
        }
    });

    /**
     * 查看申报信息
     */
    function view(data) {
        if(!data || data.length === 0) {
            warnMsg("请选择需要查看的记录！");
            return;
        }
        window.open("ycqksb.html?type=view&id=" + data[0].id);
    }

    /**
     * 查看审批文件
     * @param data
     * @returns {boolean}
     */
    function downloadFjxx(data) {
        layer.open({
            type: 2,
            title: '附件信息',
            anim: -1,
            shadeClose: true,
            maxmin: true,
            shade: false,
            area: ['960px', '500px'],
            offset: 'auto',
            content: [ "../file/files.html?page=view&ywid=" + data.id, 'yes'],
            success: function (layero, index) {
            },
            end:function(){
            }
        });
    }
});
