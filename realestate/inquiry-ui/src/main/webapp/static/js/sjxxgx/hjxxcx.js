/**
 * @author <a href ="mailto:lixin1@gtmap.cn">lixin</a>
 * @description 社会组织
 */
layui.config({
    base: '../../static/' //静态资源所在路径
}).extend({response: 'lib/bdcui/js/response'});
var rkdata;
layui.use(['table', 'layer', 'jquery', 'response'], function () {
    var $ = layui.jquery,
        table = layui.table,
        layer = layui.layer,
        response = layui.response;

    var BASE_URL = '/realestate-inquiry-ui/rest/v1.0/sjxxgx';
    cols = [
        {
            field: 'xh', type: 'numbers', title: '序号',
            templet: function (d) {
                return d.LAY_TABLE_INDEX + 1;
            }
        },
        {field: 'xM', title: '姓名'},
        {field: 'zJH', title: '身份证号'},
        {field: 'xB', title: '性别'},
        {field: 'hJDZ', title: '户籍地址'},
        {field: 'yHZGX', title: '与户主关系'},
        {fixed: 'right', title: '操作', toolbar: '#barDemo', width: 100}
    ];

    /**
     * 加载Table数据列表
     */
    table.render({
        elem: '#hjxxcxTable',
        toolbar: '#toolbar',
        title: '户籍人口信息列表',
        defaultToolbar: ['filter'],
        request: {
            limitName: 'size' //每页数据量的参数名，默认：limit
        },
        data: [],
        cellMinWidth: 80,
        where: {},
        even: true,
        cols: [cols],
        text: {
            none: '未查询到数据'
        },
        autoSort: false,
        page: true,
        limits: [10, 50, 100, 200, 500],
        parseData: function (res) {
            return {
                code: res.code, //解析接口状态
                msg: res.msg, //解析提示文本
                count: res.totalElements, //解析数据长度
                data: res.content //解析数据列表
            }
        },
        done: function () {
            setHeight();
        }
    });

    $('.bdc-table-box').on('mouseenter', 'td', function () {
        $('.layui-table-grid-down').on('click', function () {
            setTimeout(function () {
                $('.layui-table-tips-main .bdc-table-date').html(reverseString($('.layui-table-tips-main .bdc-table-date').html()));
            }, 20);
        });
    });

    /**
     * 监听排序事件
     */
    table.on('sort(hjxxcxTable)', function (obj) {
        table.reload('hjxxcxTable', {
            initSort: obj
            , where: {
                field: obj.field //排序字段
                , order: obj.type //排序方式
            }
        });
    });

    //监听行工具事件
    table.on('tool(hjxxcxTable)', function (obj) {
        switch (obj.event) {
            case "info":
                detail(obj);
                break;
        }

    });

    /**
     * 点击查询
     */
    $('#search').on('click', function () {
        // 获取查询内容
        var zjh = $("#zjh").val();
        if (isNullOrEmpty(zjh)) {
            warnMsg("请输入证件号！");
            return;
        }

        // 重新请求
        table.reload("hjxxcxTable", {
            url: BASE_URL + '/hjxxcx',
            where: {zjh:zjh}
            , page: {
                curr: 1  //重新从第 1 页开始
            }
        });
    });


    /**
     * 重新加载数据
     */
    window.reload = function () {
        var obj = {};
        $(".search").each(function (i) {
            var value = $(this).val();
            var name = $(this).attr('name');
            obj[name] = value;
        });
        table.reload("hjxxcxTable", {
            where: obj
            , page: {
                curr: 1  //重新从第 1 页开始
            }
        });
    };


    /**
     * 查看详情
     */
    function detail(data) {
        rkdata = data.data;
        if(isNullOrEmpty(rkdata)){
            warnMsg("信息为空，无法查看详情！");
        }else{
            layer.open({
                type: 2,
                shade: false,
                shadeClose: true,
                isOutAnim: false,
                content: "../../view/sjxxgx/hjxxcxxq.html",
                area: ['500px', '700px'],
                title: '人口信息',
            });
        }
    }

});