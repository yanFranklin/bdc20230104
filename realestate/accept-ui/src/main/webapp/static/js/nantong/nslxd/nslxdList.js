var nslxdUrl = getIP() + "/realestate-accept-ui/static/printmodel/nslxd.fr3";
var processInsId = getQueryString("processInsId");

layui.use(['table','laytpl','layer'],function () {
    var table = layui.table,
        $ = layui.jquery,
        layer = layui.layer;

    // 加载页面纳税联系单数据
    var loadQlxx = function(processInsId) {
        var deferred = $.Deferred();
        $.ajax({
            url: getContextPath()+'/rest/v1.0/nslxd/data',
            type: 'POST',
            dataType: 'json',
            data: {processInsId: processInsId},
            success: function (data) {
                console.info(data);
                deferred.resolve(data);
            },
            error : function(err){
                console.info(err);
                deferred.reject(err);
            }
        });
        return deferred;
    }


    $(function () {
        var cols = [
            {type: 'numbers', title:'ID', width:50, fixed: 'left', unresize: true},
            {field:'zrfxm', title:'转让方姓名(名称)',minWidth: 140},
            {field:'csfxm', title:'承受方姓名(名称)',minWidth: 140},
            {field:'fwzl', title:'坐落',minWidth: 130},
            {field:'fwmj', title:'房屋面积(平方米)',minWidth: 140},
            {field:'glmj', title:'阁楼面积(平方米)',minWidth: 140},
            {field:'ckmj', title:'车库面积(平方米)',minWidth: 140},
            {field:'htydjg', title:'合同约定价格(万元)',minWidth: 145},
            {field:'htqdsj', title:'合同签订时间',minWidth: 130},
            {fixed:'right', title:'操作', toolbar: '#barDemo', width:80}
        ];
        loadQlxx(processInsId).done(function (data) {
            var reverseList = ['zrfxm','zl','htqdsj'];
            table.render({
                elem: '#pageTable',
                title: '纳税联系单列表',
                toolbar: '#toolbarDemo',
                defaultToolbar: ['filter'],
                even: true,
                limits: 1000,
                cols: [cols],
                data: data,
                page: false,
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
        });

        function reverseString(str) {
            if (!isNotBlank(str)) {
                return str;
            }
            str = str.replace(/&lt;/g, '>');
            str = str.replace(/&gt;/g, '<');
            var RexStr = /\（|\）|\(|\)|\【|\】|\【|\】|\[|\]|\[|\]/g;
            str = str.replace(RexStr, function (MatchStr) {
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

        //监听行工具事件
        table.on('tool(nslxdList)', function(obj){
            var data = obj.data;
            if(obj.event === 'details'){
                sessionStorage.setItem("nslxdData",JSON.stringify(data));
                var index = layer.open({
                    type: 2,
                    area: ['1150px', '600px'],
                    fixed: false, //不固定
                    title: "详情页",
                    maxmin: true,
                    content: getContextPath()+'/nantong/nslxd/nslxd.html',
                    btnAlign: "c",
                    zIndex: 2147483647,
                    success: function () {
                    },
                    cancel: function () {
                    }
                });
                layer.full(index);
            }
        });

        // 纳税联系单列表打印服务
        table.on('toolbar(nslxdList)', function (obj) {
            if(obj.event === 'printNslxdList'){
                var dataUrl = getIP() + getContextPath() + "/rest/v1.0/nslxd/print/"+processInsId+"/xml/list";
                print(nslxdUrl, dataUrl,false);
            }
        });

    });
});