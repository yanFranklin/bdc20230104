/**
 * author: 前端组
 * date: 2018-12-14
 * version 3.0.0
 * describe: 初始化表格，工具栏事件
 */
layui.use(['table','laytpl','layer'],function () {
    var table = layui.table,
        $ = layui.jquery,
        layer = layui.layer;
    $(function () {
        var tableData1 =  [
            {
                "jjdh": '2016006125',
                "jjdlx": "注销交接单",
                "zfr": "刘天香",
                "zfsj": "2016-10-14 19:17",
                "zfks": "发证科",
                "jssj": "2016-10-14 20:15",
                "jsks": "接收科",
                "jjdzt": "已接收"
            },{
                "jjdh": '2016006125',
                "jjdlx": "注销交接单",
                "zfr": "刘天香",
                "zfsj": "2016-10-14 19:17",
                "zfks": "发证科",
                "jssj": "2016-10-14 20:15",
                "jsks": "接收科",
                "jjdzt": "已接收"
            },{
                "jjdh": '2016006125',
                "jjdlx": "注销交接单",
                "zfr": "刘天香",
                "zfsj": "2016-10-14 19:17",
                "zfks": "发证科",
                "jssj": "2016-10-14 20:15",
                "jsks": "接收科",
                "jjdzt": "已接收"
            },{
                "jjdh": '2016006125',
                "jjdlx": "注销交接单",
                "zfr": "刘天香",
                "zfsj": "2016-10-14 19:17",
                "zfks": "发证科",
                "jssj": "2016-10-14 20:15",
                "jsks": "接收科",
                "jjdzt": "已接收"
            },{
                "jjdh": '2016006125',
                "jjdlx": "注销交接单",
                "zfr": "刘天香",
                "zfsj": "2016-10-14 19:17",
                "zfks": "发证科",
                "jssj": "2016-10-14 20:15",
                "jsks": "接收科",
                "jjdzt": "已接收"
            },{
                "jjdh": '2016006125',
                "jjdlx": "注销交接单",
                "zfr": "刘天香",
                "zfsj": "2016-10-14 19:17",
                "zfks": "发证科",
                "jssj": "2016-10-14 20:15",
                "jsks": "接收科",
                "jjdzt": "已接收"
            },{
                "jjdh": '2016006125',
                "jjdlx": "注销交接单",
                "zfr": "刘天香",
                "zfsj": "2016-10-14 19:17",
                "zfks": "发证科",
                "jssj": "2016-10-14 20:15",
                "jsks": "接收科",
                "jjdzt": "已接收"
            },
            {
                "jjdh": '2016006125',
                "jjdlx": "注销交接单",
                "zfr": "刘天香",
                "zfsj": "2016-10-14 19:17",
                "zfks": "发证科",
                "jssj": "2016-10-14 20:15",
                "jsks": "接收科",
                "jjdzt": "已接收"
            },{
                "jjdh": '2016006125',
                "jjdlx": "注销交接单",
                "zfr": "刘天香",
                "zfsj": "2016-10-14 19:17",
                "zfks": "发证科",
                "jssj": "2016-10-14 20:15",
                "jsks": "接收科",
                "jjdzt": "已接收"
            },
            {
                "jjdh": '2016006125',
                "jjdlx": "注销交接单",
                "zfr": "刘天香",
                "zfsj": "2016-10-14 19:17",
                "zfks": "发证科",
                "jssj": "2016-10-14 20:15",
                "jsks": "接收科",
                "jjdzt": "已接收"
            },{
                "jjdh": '2016006125',
                "jjdlx": "注销交接单",
                "zfr": "刘天香",
                "zfsj": "2016-10-14 19:17",
                "zfks": "发证科",
                "jssj": "2016-10-14 20:15",
                "jsks": "接收科",
                "jjdzt": "已接收"
            }];
        var tableData2 =  [
            {
                "slh": 'F1200925925',
                "xdah": "000222222",
                "djlx": "抵押权登记＞抵押权登记＞设立",
                "fwdz": "百花园47栋125室",
                "sqr": "王丽",
                "cqr": "王丽"
            }];
        var tableData3 =  [
            {
                "slh": 'F1200925925',
                "xdah": "000222222",
                "djlx": "抵押权登记＞抵押权登记＞设立",
                "fwdz": "百花园47栋125室",
                "sqr": "王丽",
                "cqr": "王丽"
            },{
                "slh": 'F1200925925',
                "xdah": "000222222",
                "djlx": "抵押权登记＞抵押权登记＞设立",
                "fwdz": "百花园47栋125室",
                "sqr": "王丽",
                "cqr": "王丽"
            },{
                "slh": 'F1200925925',
                "xdah": "000222222",
                "djlx": "抵押权登记＞抵押权登记＞设立",
                "fwdz": "百花园47栋125室",
                "sqr": "王丽",
                "cqr": "王丽"
            },{
                "slh": 'F1200925925',
                "xdah": "000222222",
                "djlx": "抵押权登记＞抵押权登记＞设立",
                "fwdz": "百花园47栋125室",
                "sqr": "王丽",
                "cqr": "王丽"
            },{
                "slh": 'F1200925925',
                "xdah": "000222222",
                "djlx": "抵押权登记＞抵押权登记＞设立",
                "fwdz": "百花园47栋125室",
                "sqr": "王丽",
                "cqr": "王丽"
            }];
        table.render({
            elem: '#tableOne',
            title: '表格一',
            even: true,
            cols: [[
                {type: 'radio', width:50, fixed: 'left'},
                {field:'jjdh', title:'交接单号', width:200, sort: true},
                {field:'jjdlx', title:'交接单类型', width:200},
                {field:'zfr', title:'转发人', width:100},
                {field:'zfsj', title:'转发时间',width:200},
                {field:'zfks', title:'转发科室',width:100},
                {field:'jsr', title:'接收人'},
                {field:'jssj', title:'接收时间'},
                {field:'jsks', title:'接收科室'},
                {field:'jjdzt', title:'交接单状态'}
            ]],
            data: tableData1,
            page: true,
            done: function () {
                $('.layui-table-tool-self').css('right',$('.bdc-export-tools').width()+ 17 + 'px');

                if($('.layui-table-body>.layui-table').height() == 0){
                    $('.layui-table-body .layui-none').html('<img src="../images/table-none.png" alt="">无数据');
                }
            }
        });

        var clickIndex = 0;
        table.on('radio(oneFilter)', function(obj){
            console.log(obj.data); //选中行的相关数据

            clickIndex++;
            if(clickIndex == 1){
                renderTable();
            }else {
                reloadTable();
            }
        });
        function renderTable() {
            table.render({
                elem: '#tableTwo',
                id: 'twoId',
                title: '表格二',
                even: true,
                cols: [[
                    {type: 'checkbox', width:50, fixed: 'left'},
                    {field:'slh', title:'受理号', width:200, sort: true},
                    {field:'xdah', title:'新档案号', width:200},
                    {field:'djlx', title:'登记类型名称', width:100},
                    {field:'fwdz', title:'房屋地址',width:200},
                    {field:'sqr', title:'申请人',width:100},
                    {field:'cqr', title:'产权人'}
                ]],
                data: tableData2,
                limits: 1000,
                done: function () {
                    $('.layui-table-tool-self').css('right',$('.bdc-export-tools').width()+ 17 + 'px');

                    if($('.layui-table-body>.layui-table').height() == 0){
                        $('.layui-table-body .layui-none').html('<img src="../images/table-none.png" alt="">无数据');
                    }
                }
            });
        }
        function reloadTable() {
            table.reload('twoId', {
                // where: { //设定异步数据接口的额外参数，任意设
                //     aaaaaa: 'xxx'
                //     ,bbb: 'yyy'
                //     //…
                // }
                // ,page: {
                //     curr: 1 //重新从第 1 页开始
                // }
                data: tableData3
            });
        }
    });
});