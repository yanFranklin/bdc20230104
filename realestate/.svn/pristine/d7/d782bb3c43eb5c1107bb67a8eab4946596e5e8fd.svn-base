/**
 * author: <a href="mailto:zhuyong@gtmap.cn>zhuyong</a>
 * version 1.0.0  2020/08/13
 * describe: 匹配不动产单元号页面JS
 */
var reverseList = ['zl'];

layui.use(['table','laytpl','laydate','layer', 'form'],function () {
    var $ = layui.jquery,
        table = layui.table,
        layer = layui.layer,
        form = layui.form;

    /**
     * 加载Table数据列表
     */
    table.render({
        elem: '#bdcdyTable',
        toolbar: '#toolbar',
        title: '匹配不动产单元号页面JS',
        defaultToolbar: ['filter'],
        request: {
            limitName: 'size' //每页数据量的参数名，默认：limit
        },
        cellMinWidth: 80,
        even: true,
        cols: [[
            {type: 'checkbox', fixed: 'left'},
            {width: 280, sort:false, field: 'bdcdyh',title: '不动产单元号'},
            {minWidth:230, sort:false, field:'zl',    title:'坐落'},
            {minWidth:120, sort:false, field:'qlr',   title:'权利人'},
            {
                field: 'bdcdyZtDTO', width: 120, title: '限制状态', fixed: 'right', sort: false,
                templet: function (d) {
                    return formatXzzt(d.bdcdyZtDTO);
                }
            }
        ]],
        data: [],
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
            setHeight();
            reverseTableCell(reverseList);
        }
    });

    //头工具栏事件
    table.on('toolbar(bdcdyTable)', function(obj){
        var checkStatus = table.checkStatus(obj.config.id);
        switch(obj.event){
            case 'match':
                match(checkStatus.data);
                break;
        };
    });

    function match(data){
        if(!data || data.length != 1){
            warnMsg("请选择一条记录进行匹配！");
            return;
        }

        // 匹配到的不动产单元号，也就是将要合并生成的新纪录的不动产单元号
        var ppbdcdyh = data[0].bdcdyh;
        layui.data('ppbdcdyh', {key: 'data', value: ppbdcdyh});

        successMsg("匹配成功");
    }

    /**
     * 点击查询
     */
    $('#search').on('click',function () {
        // 清除缓存的匹配单元号
        layui.data('ppbdcdyh', null);

        // 获取查询内容
        var obj = {};
        $(".search").each(function(i){
            var value= $(this).val();
            var name= $(this).attr('name');
            obj[name]=value;
        });

        addModel();
        // 重新请求
        table.reload("bdcdyTable", {
            where: obj
            ,url: "/realestate-register-ui/rest/v1.0/bdcdy/ppbdcdy"
            , page: {
                curr: 1  //重新从第 1 页开始
            }
            , done: function (res, curr, count) {
                removeModel();
                setHeight();
                reverseTableCell(reverseList);
            }
        });
    });

    /**
     * 重置
     */
    $('#reset').on('click',function () {
    });
});