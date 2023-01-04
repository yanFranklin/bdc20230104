layui.use(['table', 'laytpl', 'laydate', 'layer', 'form'], function () {
    var $ = layui.jquery,
        table = layui.table,
        layer = layui.layer,
        form = layui.form;
        laydate = layui.laydate;

    var BASE_URL = '/realestate-inquiry-ui/rest/v1.0';

    var start = laydate.render({
        elem: '#cjqssj',
        format: 'yyyy-MM-dd HH:mm:ss',
        done: function (value, date, endDate) {
            end.config.min = {
                year: date.year,
                month: date.month - 1,
                date: date.date,
            }; //开始日选好后，重置结束日的最小日期
            end.config.value = {
                year: date.year,
                month: date.month - 1,
                date: date.date,
            }; //将结束日的初始值设定为开始日
        }
    });

    var end = laydate.render({
        elem: '#cjjssj',
        format: 'yyyy-MM-dd HH:mm:ss',
        done: function (value, date, endDate) {
            start.config.max = {
                year: date.year,
                month: date.month - 1,
                date: date.date,
            }; //结束日选好后，重置开始日的最大日期
        }
    });

    /**
     * 加载Table数据列表
     */
    table.render({
        elem: '#nwcjrzTable',
        toolbar: "#toolbar",
        defaultToolbar: ['filter'],
        url: BASE_URL + '/bengbu/nwcjrz/list',
        request: {
            limitName: 'size' //每页数据量的参数名，默认：limit
        },
        cellMinWidth: 80,
        where: {},
        even: true,
        cols: [[
            {
                fixed: 'left', field: '', title: '序号', width: '5%', templet: function (d) {
                    return (d.LAY_INDEX);
                }
            },
            {field: 'slbh', title: '受理编号', width: '20%'},
            {
                field: 'qqsj', title: '创建时间', width: '15%',
                templet: function (d) {
                    return format(d.qqsj);
                }
            },
            {
                field: 'cjjg', title: '创建状态', width: '15%',
                templet: function (d) {
                    if (d.cjjg === 0) {
                        return "成功"
                    } else if (d.cjjg === 1) {
                        return "失败"
                    } else {
                        return "";
                    }
                }
            },
            {field: 'xyjg', title: '创建说明', width: '45%'}
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
                $('.layui-table-body .layui-none').html('<img src="../../static/lib/registerui/image/table-none.png" alt="">无数据');
            } else {
                $('.layui-table-body').height($('.bdc-table-box').height() - 131);
                $('.layui-table-fixed .layui-table-body').height($('.bdc-table-box').height() - 131 - 17);
            }
        }
    });

    /**
     * 点击查询
     */
    $('#search').on('click', function () {

        var obj = {
            "slbh": $('#slbh').val(),
            "cjjg": $('#cjjg').val(),
            "cjqssj": $('#cjqssj').val(),
            "cjjssj": $('#cjjssj').val()
        }
        // 重新请求
        table.reload("nwcjrzTable", {
            where: obj
            , page: {
                curr: 1  //重新从第 1 页开始
            }
        });
        return false;
    });

    /**
     * 重新加载数据
     */
    window.reload = function () {
        table.reload("nwcjrzTable", {
            where: []
            , page: {
                curr: 1  //重新从第 1 页开始
            }
        });
    };

    function format(timestamp){
        if(!timestamp){
            return '';
        }

        var time = new Date(timestamp);
        var y = time.getFullYear();
        var m = time.getMonth() + 1;
        var d = time.getDate();
        var h = time.getHours();
        var mm = time.getMinutes();
        var s = time.getSeconds();
        return y+'-'+add0(m)+'-'+add0(d)+' '+add0(h)+':'+add0(mm)+':'+add0(s);
    }

});
