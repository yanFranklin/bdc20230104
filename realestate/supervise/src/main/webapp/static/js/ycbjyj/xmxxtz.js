/**
 * 项目信息台账
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

    // 获取页面表单标识，用于权限控制
    var moduleCode = $.getUrlParam('moduleCode');
    if (moduleCode) {
        setElementAttrByModuleAuthority(moduleCode);
    }

    table.render({
        elem: '#pageTable',
        title: '项目信息台账',
        defaultToolbar: ['filter'],
        request: {
            limitName: 'size' //每页数据量的参数名，默认：limit
        },
        cellMinWidth: 80,
        even: true,
        cols: [[
            {width: 150, sort: false, field: 'slbh', title: '受理编号'},
            {width: 150, sort: false, field: 'qlr', title: '权利人'},
            {width: 150, sort: false, field: 'ywr', title: '义务人'},
            {width: 250, sort: false, field: 'zl', title: '坐落'},
            {width: 150, sort: false, field: 'slr', title: '受理人'},
            {width: 200, sort: false, field: 'gzldymc', title: '流程名称'},
            {width: 200, sort: false, field: 'djyy', title: '登记原因'},
            {width: 150, sort: false, field: 'slsj', title: '开始时间',
                templet: function (d) {
                    return formatDate(d.slsj);
                }
            },
            {width: 280, sort: false, field: 'bdcqzh', title: '不动产权证号'},
            {width: 280, sort: false, field: 'bdcdyh', title: '不动产单元号',
                templet: function (d) {
                    return formatBdcdyh(d.bdcdyh);
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
            url: "/realestate-supervise/rest/v1.0/ycbjyj/xmxx"
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
        if (obj.event === 'lct') {
            lct(data);
        } else if (obj.event === 'glyw') {
            glyw(data);
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
     * 关联业务
     * @param data
     */
    function glyw(data) {
        layui.sessionData('xmxx', {
            key: 'data', value: data
        });
        closeWin();
    }
});


/**
 * 关闭弹出页面
 */
window.closeWin = function(){
    var index=parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
};

window.closeParentWindow = function(){
    var index = parent.parent.layer.getFrameIndex(window.name);
    parent.parent.layer.close(index);
};