/**
 * @author <a href ="mailto:lixin1@gtmap.cn">wutao</a>
 * @description 竣工验收备案查询
 */

layui.config({
    base: '../../static/' //静态资源所在路径
}).extend({response: 'lib/bdcui/js/response'});
layui.use(['form', 'table', 'layer', 'jquery', 'response','laydate'], function () {
    var $ = layui.jquery,
        table = layui.table,
        layer = layui.layer,
        form = layui.form,
        laydate = layui.laydate,
        response = layui.response;
    var BASE_URL = '/realestate-inquiry-ui/rest/v1.0/sjxxgx';
    cols = [
        {
            field: 'xh', type: 'numbers', title: '序号',
            templet: function (d) {
                return d.LAY_TABLE_INDEX + 1;
            }
        },
        {field: 'buildUnit', title: '建设单位名称'},
        {field: 'projName', title: '项目名称'},
        {field: 'projAddr', title: '工程地点'},
        {field: 'buildingArea', title: '建筑面积'},
        {field: 'certCode', title: '备案编号'},
        {field: 'jgysDate', title: '竣工验收日期'},
        {fixed: 'right', title: '操作', toolbar: '#barDemo', width: 100}
    ];

    laydate.render({
        elem: '#startTime',
        type: 'date',
        format: 'yyyy-MM-dd',
        done: function (value, date, endDate) {
            var startDate = new Date(value).getTime();
            var endTime = new Date($('#endTime').val()).getTime();
            if (endTime < startDate) {
                layer.msg('<img src="../../static/image/error-small.png" alt="">结束时间不能小于开始时间');
            }
        }
    });
    laydate.render({
        elem: '#endTime',
        type: 'date',
        format: 'yyyy-MM-dd',
        done: function (value, date, endDate) {
            var startDate = new Date(value).getTime();
            var endTime = new Date($('#startTime').val()).getTime();
            if (endTime > startDate) {
                layer.msg('<img src="../../static/image/error-small.png" alt="">结束时间不能小于开始时间');
            }
        }
    });

    /**
     * 加载Table数据列表
     */
    table.render({
        elem: '#jgysbalistTable',
        toolbar: '#toolbar',
        title: '竣工验收备案列表',
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
    table.on('sort(jgysbalistTable)', function (obj) {
        table.reload('jgysbalistTable', {
            initSort: obj
            , where: {
                field: obj.field //排序字段
                , order: obj.type //排序方式
            }
        });
    });

    //监听行工具事件
    table.on('tool(jgysbalistTable)', function (obj) {
        switch (obj.event) {
            case "info":
                detail(obj);
                break;
        }

    });

    initxzqh();
    /**
     * 点击查询
     */
    $('#search').on('click', function () {
        // 获取查询内容
        var obj = {};
        $(".search").each(function (i) {
            var value = $(this).val();
            if (!isNullOrEmpty(value)) {
                var name = $(this).attr('name');
                obj[name] = value;
            }
        });

        if(isNullOrEmpty(obj.buildUnit) && isNullOrEmpty(obj.projName)){
            warnMsg("建设单位名称和工程项目名称不能同时为空！");
            return;
        }
        // 重新请求
        table.reload("jgysbalistTable", {
            url: BASE_URL + '/jgysbacx',
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
        var obj = {};
        $(".search").each(function (i) {
            var value = $(this).val();
            var name = $(this).attr('name');
            obj[name] = value;
        });
        table.reload("jgysbalistTable", {
            where: obj
            , page: {
                curr: 1  //重新从第 1 页开始
            }
        });
    };

    function initxzqh() {
        var zdlist = getZdList();
        var qxdmArr = zdlist['qx'];
        if (isNotBlank(qxdmArr)) {
            $.each(qxdmArr, function (index, item) {
                $('#xzqhdm').append('<option value="' + item.DM + '">' + item.MC + '</option>');
            });
            form.render();

        }
    }

    /**
     * 查看详情
     */
    function detail(data) {
        var certCode = isNotBlank(data.data.certCode) ? data.data.certCode: "";
        if(isNullOrEmpty(certCode)){
            warnMsg("备案编号为空，无法查看详情！");
        }else{
            layer.open({
                type: 2,
                shade: false,
                shadeClose: true,
                isOutAnim: false,
                content: "../../view/sjpt/jgysbaxq.html?certCode="+certCode,
                area: ['500px', '700px'],
                title: '竣工验收信息',
            });
        }
    }
});