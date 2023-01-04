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
        var data =  [
            {
                "id": '01',
                "city": "南京市",
                "centerName": "南京市不动产登记中心",
                "time": "2016-10-14 19:17",
                "extractingConditions": "首次登记、转移登记",
                "extractingSum": 50,
                "person": "刘芸",
                "located": "江苏省南京市鼓楼区集慧路2号，幸福小区，18栋一单元201室"
            },{
                "id": '02',
                "city": "南京市",
                "centerName": "南京市不动产登记中心",
                "time": "2016-10-14 19:17",
                "extractingConditions": "首次登记、转移登记",
                "extractingSum": 50,
                "person": "刘芸",
                "located": "江苏省南京市鼓楼区集慧路2号幸福小区18栋（一单元201室）"
            },{
                "id": '03',
                "city": "南京市",
                "centerName": "南京市不动产登记中心",
                "time": "2016-10-14 19:17",
                "extractingConditions": "首次登记、转移登记",
                "extractingSum": 50,
                "person": "刘芸",
                "located": "江苏省南京市鼓楼区集慧路2号幸福小区(18栋一单元)201室"
            },{
                "id": '04',
                "city": "南京市",
                "centerName": "南京市不动产登记中心",
                "time": "2016-10-14 19:17",
                "extractingConditions": "首次登记、转移登记",
                "extractingSum": 50,
                "person": "刘芸",
                "located": "江苏省南京市鼓楼区集慧路2号幸福小区18栋一单元201室"
            },{
                "id": '05',
                "city": "南京市",
                "centerName": "南京市不动产登记中心",
                "time": "2016-10-14 19:17",
                "extractingConditions": "首次登记、转移登记",
                "extractingSum": 50,
                "person": "刘芸",
                "located": "江苏省南京市鼓楼区集慧路2号幸福小区18栋一单元201室"
            },{
                "id": '06',
                "city": "南京市",
                "centerName": "南京市不动产登记中心",
                "time": "2016-10-14 19:17",
                "extractingConditions": "首次登记、转移登记",
                "extractingSum": 50,
                "person": "刘芸",
                "located": "江苏省南京市鼓楼区集慧路2号幸福小区18栋一单元201室"
            },{
                "id": '07',
                "city": "南京市",
                "centerName": "南京市不动产登记中心",
                "time": "2016-10-14 19:17",
                "extractingConditions": "首次登记、转移登记",
                "extractingSum": 50,
                "person": "刘芸",
                "located": "江苏省南京市鼓楼区集慧路2号幸福小区18栋一单元201室"
            },{
                "id": '08',
                "city": "南京市",
                "centerName": "南京市不动产登记中心",
                "time": "2016-10-14 19:17",
                "extractingConditions": "首次登记、转移登记",
                "extractingSum": 50,
                "person": "刘芸",
                "located": "江苏省南京市鼓楼区集慧路2号幸福小区18栋一单元201室"
            },{
                "id": '09',
                "city": "南京市",
                "centerName": "南京市不动产登记中心",
                "time": "2016-10-14 19:17",
                "extractingConditions": "首次登记、转移登记",
                "extractingSum": 50,
                "person": "刘芸",
                "located": "江苏省南京市鼓楼区集慧路2号幸福小区18栋一单元201室"
            },{
                "id": '10',
                "city": "南京市",
                "centerName": "南京市不动产登记中心",
                "time": "2016-10-14 19:17",
                "extractingConditions": "首次登记、转移登记",
                "extractingSum": 50,
                "person": "刘芸",
                "located": "江苏省南京市鼓楼区集慧路2号幸福小区18栋一单元201室"
            }];
        var reverseList = ['located','time'];
        table.render({
            elem: '#pageTable',
            toolbar: '#toolbarDemo',
            title: '用户数据表',
            defaultToolbar: ['filter'],
            even: true,
            cols: [[
                {type: 'checkbox', width:50, fixed: 'left'},
                {field:'id', title:'ID', width:80, fixed: 'left', unresize: true},
                {field:'city', title:'市', width:80},
                {field:'centerName', title:'中心名称', width:200},
                {field:'time', title:'时间', width:100},
                {field:'extractingConditions', title:'抽取条件',width:200},
                {field:'extractingSum', title:'抽取总数',width:100, sort: true},
                {field:'person', title:'质检人'},
                {field:'located', title:'坐落'},
                {fixed: 'right', title:'操作', toolbar: '#barDemo', width:180}
            ]],
            data: data,
            page: true,
            done: function () {
                $('.layui-table-tool-self').css('right',$('.bdc-export-tools').width()+ 17 + 'px');

                reverseTableCell(reverseList);

                if($('.layui-table-body>.layui-table').height() == 0){
                    $('.layui-table-body .layui-none').html('<img src="../images/table-none.png" alt="">无数据');
                }
            }
        });
        $('.bdc-table-box').on('mouseenter','td',function () {
            if($(this).text() && reverseList.indexOf($(this).attr("data-field")) != -1){
                $(this).append('<div class="layui-table-grid-down"><i class="layui-icon layui-icon-down"></i></div>');
            }
            $('.layui-table-grid-down').on('click',function () {
                setTimeout(function () {
                    $('.layui-table-tips-main .bdc-table-date').html(reverseString($('.layui-table-tips-main .bdc-table-date').html()));
                },20);
            });
        });
        function reverseString(str) {
            // 实际应用需加上str判空处理
            str = str.replace(/&lt;/g, '>');
            str = str.replace(/&gt;/g, '<');
            var RexStr = /\（|\）|\(|\)|\【|\】|\【|\】|\[|\]|\[|\]/g;
            str = str.replace(RexStr, function(MatchStr) {
                switch (MatchStr) {
                    case "(":
                        return ")";
                        break;
                    case ")":
                        return "(";
                        break;
                    case "（":
                        return '）';
                        break;
                    case "）":
                        return "（";
                        break;
                    case "[":
                        return "]";
                        break;
                    case "]":
                        return "[";
                        break;
                    case "【":
                        return "】";
                        break;
                    case "】":
                        return "【";
                        break;
                }
            });
            return str.split("").reverse().join("");
        }
        function reverseTableCell(reverseList) {
            var getTd = $('.layui-table-view .layui-table td');
            for(var i = 0; i < getTd.length; i++){
                reverseList.forEach(function (v) {
                    if($(getTd[i]).attr('data-field') == v){
                        var getTdCell = $(getTd[i]).find('.layui-table-cell');
                        getTdCell.css('direction','rtl');
                        var tdHtml = reverseString(getTdCell.html());
                        // console.log(tdHtml);
                        getTdCell.html('<span class="bdc-table-date">'+ tdHtml +'</span>');
                        // getTdCell.html('<span class="bdc-table-date">'+ getTdCell.html() +'</span>');
                    }
                });
            }
        }


        //头工具栏事件
        table.on('toolbar(test)', function(obj){
            var checkStatus = table.checkStatus(obj.config.id);
            switch(obj.event){
                case 'add':
                    layer.open({
                        type: 2,
                        title: '一级iframe',
                        area: ['100%', '100%'],
                        maxmin: true,
                        content: '../view/layer-iframe.html',
                        success: function(layero){
                            
                        }
                    });
                    break;
            }
        });
    });
});