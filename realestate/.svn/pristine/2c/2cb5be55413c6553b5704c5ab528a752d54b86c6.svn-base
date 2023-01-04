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
                "located": "江苏省南京市鼓楼区集慧路2号幸福小区18栋一单元201室"
            },{
                "id": '03',
                "city": "南京市",
                "centerName": "南京市不动产登记中心",
                "time": "2016-10-14 19:17",
                "extractingConditions": "首次登记、转移登记",
                "extractingSum": 50,
                "person": "刘芸",
                "located": "江苏省南京市鼓楼区集慧路2号幸福小区18栋一单元201室"
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
        var data1 = [];
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

                var reverseList = ['located','time'];
                reverseTableCell(reverseList);

                if($('.bdc-table-box .layui-table-main>.layui-table').height() == 0){
                    $('.layui-table-body .layui-none').html('<img src="../image/table-none.png" alt="">无数据');
                }else {
                    $('.layui-table-body').height($('.bdc-table-box').height() - 131);
                    $('.layui-table-fixed .layui-table-body').height($('.bdc-table-box').height() - 131 - 17);
                }
            }
        });

        $('.bdc-table-box').on('mouseenter','td',function () {
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
                        getTdCell.html('<span class="bdc-table-date">'+ tdHtml +'</span>');
                    }
                });
            }
        }


        //头工具栏事件
        table.on('toolbar(test)', function(obj){
            var checkStatus = table.checkStatus(obj.config.id);
            switch(obj.event){
                case 'getCheckData':
                    var data = checkStatus.data;
                    layer.alert(JSON.stringify(data));
                    break;
                case 'getCheckLength':
                    var data = checkStatus.data;
                    layer.msg('选中了：'+ data.length + ' 个');
                    break;
                case 'isAll':
                    layer.msg(checkStatus.isAll ? '全选': '未全选');
                    break;
            }
        });

        //监听行工具事件
        table.on('tool(test)', function(obj){
            var data = obj.data;
            if(obj.event === 'del'){
                // 删除操作
            } else if(obj.event === 'edit'){
                //编辑操作
            }
        });

        //监听排序事件
        table.on('sort(test)', function(obj){
            console.log(obj.field); //当前排序的字段名
            console.log(obj.type); //当前排序类型：desc（降序）、asc（升序）、null（空对象，默认排序）
            // console.log(this); //当前排序的 th 对象

            //尽管我们的 table 自带排序功能，但并没有请求服务端。
            //有些时候，你可能需要根据当前排序的字段，重新向服务端发送请求，从而实现服务端排序，如：
            table.reload('idTest', {
                initSort: obj //记录初始排序，如果不设的话，将无法标记表头的排序状态。
                ,where: { //请求参数（注意：这里面的参数可任意定义，并非下面固定的格式）
                    field: obj.field //排序字段
                    ,order: obj.type //排序方式
                }
            });
        });

        // function reserveElliipsisText(){
        //     $(".layui-table-cell").each(function(index, el) {
        //         var $self = $(el);
        //         var maxLength = ($self.width()-10)/14;
        //         if($self.text().length>maxLength){
        //             $self.text("..."+$self.text().substr(-maxLength,maxLength));
        //         }
        //     });
        // }

        //监听滚动事件
        var scrollTop = 0,
            scrollLeft = 0;
        var tableTop = 0,tableLeft = 0;
        var $nowBtnMore = '';
        $(window).on('scroll',function () {
            scrollTop = $(this).scrollTop();
            scrollLeft = $(this).scrollLeft();

            if($nowBtnMore != ''){
                if(tableTop + 26 + $nowBtnMore.height() < document.body.offsetHeight){
                    // $nowBtnMore.css({top: tableTop + 26 -scrollTop,right: 20});
                    $nowBtnMore.css({top: tableTop + 26 -scrollTop,left: tableLeft - 30});
                }else {
                    // $nowBtnMore.css({top: tableTop -scrollTop - $nowBtnMore.height(),right: 20});
                    $nowBtnMore.css({top: tableTop -scrollTop - $nowBtnMore.height(),left: tableLeft - 30});
                }
            }
        });

        //点击表格中的更多
        $('.bdc-table-box').on('click','.bdc-more-btn',function (event) {
            tableTop = $(this).offset().top;
            tableLeft = $(this).offset().left;
            event.stopPropagation();
            $nowBtnMore = $(this).siblings('.bdc-table-btn-more');
            var $btnMore = $(this).siblings('.bdc-table-btn-more');
            if($(this).offset().top + 26 + $btnMore.height() < document.body.offsetHeight){
                // $btnMore.css({top: $(this).offset().top + 26 -scrollTop,right: 20});
                $btnMore.css({top: tableTop + 26 -scrollTop,left:tableLeft - 30});
            }else {
                // $btnMore.css({top: $(this).offset().top -scrollTop - $btnMore.height(),right: 20});
                $btnMore.css({top: tableTop -scrollTop - $btnMore.height(),left: tableLeft - 30});
            }
            $('.bdc-table-btn-more').hide();
            $btnMore.show();
        });
        //点击更多内的任一内容，隐藏
        $('.bdc-table-btn-more a').on('click',function () {
            $(this).parent().hide();
        });
        //点击页面任一空白位置，消失
        $('body').on('click',function () {
            $('.bdc-table-btn-more').hide();
        });


        //------------工程部异步请求分页数据问题修改---------------------------
        //通过url来进行加载数据
        function loadDataTablbeByUrl(_domId, _tableConfig) {
            layui.use(['table', 'laypage', 'jquery'], function () {
                if (_domId) {
                    var table = layui.table;
                    var $ = layui.jquery;
                    var tableDefaultConfig = {
                        elem: _domId
                        , toolbar: "#toolbarDemo"
                        , cellMinWidth: 80
                        , page: true //开启分页
                        , limit: 10
                        , parseData: function (res) { //res 即为原始返回的数据
                            if (!res.hasContent) {
                                res.msg = "无数据"
                            }
                            return res;
                        }
                        , request: {
                            limitName: 'size' //每页数据量的参数名，默认：limit
                        }
                        , response: {
                            countName: 'totalElements' //数据总数的字段名称，默认：count
                            , dataName: 'content' //数据列表的字段名称，默认：data
                            , statusName: 'hasContent' //规定数据状态的字段名称，默认：code
                            , statusCode: true //规定成功的状态码，默认：0
                        }
                    };
                    if (!_tableConfig.cols || !_tableConfig.cols[0] || _tableConfig.cols[0].length < 1) {
                        _tableConfig.cols = [[]]
                    }
                    var tableRenderConfig = $.extend({}, tableDefaultConfig, _tableConfig);
                    table.render(tableRenderConfig);
                }
            });
        }
        function tableReload(tableid, postData) {
            layui.use(['table'], function () {
                var table = layui.table;
                table.reload(tableid, {
                    where: postData
                    , page: {
                        //重新从第 1 页开始
                        curr: 1
                    }
                });
            });
        }
        var tableConfig = {
            toolbar: "#toolbarDemo"
            , url: '../xmxx/listbypage' //数据接口
            , cols: [[
                {type: 'checkbox', fixed: 'left', align: 'center', width: '4%'},
                {type: 'numbers', fixed: 'left', width: '6%'},
                {field: 'xmmc', title: '项目名称', width: '15%'},
                {field: 'lszd', title: '隶属宗地', width: '15%'},
                {field: 'bdcdyh', title: '不动产单元号', width: '15%'},
                {field: 'zl', title: '坐落', width: '15%'},
                {
                    field: 'bdczt', title: '不动产状态', width: '10%',
                    templet: function (d) {
                        var returnVal = d.bdczt
                        if (d.bdczt == "0") {
                            returnVal = "不可用"
                        } else if (d.bdczt == "1") {
                            returnVal = "可用"
                        }
                        return returnVal;
                    }
                },
                {title: '操作', align: 'center', fixed: 'right', toolbar: '#xmxxListToolBarTmpl', width: '20%'}
            ]]
        };
        //加载表格
        loadDataTablbeByUrl("#zdList", tableConfig);
    });
});