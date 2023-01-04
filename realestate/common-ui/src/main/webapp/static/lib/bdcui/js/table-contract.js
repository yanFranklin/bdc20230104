/**
 * Created by Ypp on 2019/10/10.
 * 对比表格js
 */
layui.use(['table','laytpl','layer'],function () {
    var table = layui.table,
        $ = layui.jquery,
        layer = layui.layer;
        form =layui.form;
    $(function () {
        //对比表格数据
        var currentData =[];
        //此处获取比对数据
        var data =  [
            {
                "cs": '厂商',
                "bdc1": "华晨宝马",
                "bdc2": "华晨宝马"
            },{
                "cs": '厂商',
                "bdc1": "华晨宝马",
                "bdc2": "华晨宝马"
            },{
                "cs": '厂商',
                "bdc1": "华晨宝马",
                "bdc2": "华晨宝马"
            },{
                "cs": '厂商',
                "bdc1": "华晨宝马",
                "bdc2": "华晨宝马"
            },{
                "cs": '厂商',
                "bdc1": "华晨宝马",
                "bdc2": "华晨宝马"
            },{
                "cs": '厂商',
                "bdc1": "华晨宝马",
                "bdc2": "华晨宝马"
            },{
                "cs": '厂商',
                "bdc1": "华晨宝马",
                "bdc2": "华晨宝马"
            },{
                "cs": '厂商',
                "bdc1": "华晨宝马",
                "bdc2": "华晨宝马"
            },{
                "cs": '厂商',
                "bdc1": "华晨宝马",
                "bdc2": "华晨宝马"
            },{
                "cs": '厂商',
                "bdc1": "华晨宝马",
                "bdc2": "华晨宝马"
            },{
                "cs": '厂商',
                "bdc1": "华晨宝马",
                "bdc2": "华晨宝马"
            },{
                "cs": '厂商',
                "bdc1": "华晨宝马",
                "bdc2": "华晨宝马"
            },{
                "cs": '厂商',
                "bdc1": "华晨宝马",
                "bdc2": "华晨宝马"
            },{
                "cs": '厂商',
                "bdc1": "华晨宝马",
                "bdc2": "华晨宝马"
            },{
                "cs": '厂商',
                "bdc1": "华晨宝马",
                "bdc2": "华晨宝马"
            },{
                "cs": '厂商',
                "bdc1": "华晨宝马",
                "bdc2": "华晨宝马"
            },{
                "cs": '厂商',
                "bdc1": "华晨宝马",
                "bdc2": "华晨宝马"
            },{
                "cs": '厂商',
                "bdc1": "华晨宝马",
                "bdc2": "华晨宝马"
            },{
                "cs": '厂商',
                "bdc1": "华晨宝马",
                "bdc2": "华晨宝马"
            },{
                "cs": '厂商',
                "bdc1": "华晨宝马",
                "bdc2": "华晨宝马"
            },{
                "cs": '厂商',
                "bdc1": "华晨宝马",
                "bdc2": "华晨宝马"
            },{
                "cs": '厂商',
                "bdc1": "华晨宝马",
                "bdc2": "华晨宝马"
            },{
                "cs": '厂商',
                "bdc1": "华晨宝马",
                "bdc2": "华晨宝马"
            }];
        currentData=data;
        var reverseList = ['zl'];
        table.render({
            elem: '#pageTable',
            height: 'full-196',
            title: '对比表格',
            even: true,
            limit: 3000,
            cols: [[
                {type: 'checkbox', width:50, fixed: 'left'},
                {field:'cs', title:'参数', width: 100},
                {field:'bdc1', title:'不动产1', width: 400},
                {field:'bdc2', title:'不动产2'}
            ]],
            data: data,
            done: function () {
                $('.layui-table-tool-self').css('right',$('.bdc-export-tools').width()+ 17 + 'px');

                reverseTableCell(reverseList);

                if($('.bdc-table-box .layui-table-main>.layui-table').height() == 0){
                    $('.layui-table-body .layui-none').html('<img src="../image/table-none.png" alt="">无数据');
                }else if($('.layui-table-main.layui-table-body>table').height() < $('.layui-table-main.layui-table-body').height()) {
                    $('.layui-table-main.layui-table-body').height($('.layui-table-main.layui-table-body>table').height());
                    $('.layui-table-view').height($('.layui-table-main.layui-table-body>table').height() + 88);
                }
            }
        });
        //筛选相同项
        form.on('switch(switchXt)', function(data){
            var dealData=[];
            if(data.elem.checked){
                currentData.forEach(function (val,index) {
                    if(1==1){//此处写判断条件，比如val的对象属性值
                        dealData.push(val);
                    }
                })
            }else{
                dealData=currentData;
            }
            table.reload('pageTable', {
                data: dealData,
                done: function () {
                //  此处填写对于重载数据后的处理
                }
            });
        })

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
    });
});