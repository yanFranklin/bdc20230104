/**
 * 匹配台账js
 */
layui.use(['table', 'form', 'layer'], function () {
    var table = layui.table,
        form = layui.form,
        $ = layui.jquery,
        layer = layui.layer;
    $(function () {
        var url = "/realestate-accept-ui/matchData/listMatchDataByPageJson";
        $('#search').on('click', function () {
            var getSearch = form.val("searchFilter");
            console.log(getSearch);
            addModel();
            // 重新请求
            table.reload("matchTableId", {
                url: url,
                where: getSearch
                , page: {
                    curr: 1  //重新从第 1 页开始
                }
            });
        });

        table.render({
            elem: '#matchDataTable',
            id: 'matchTableId'
            , page: true //开启分页
            , defaultToolbar: ['filter']
            , even: true
            , cols: [[ //表头
                {field: 'bdcdyh', title: '不动产单元号', minWidth: 250}
                , {field: 'bdcqzh', title: '产权证号', minWidth: 250}
                , {field: 'zl', title: '坐落', minWidth: 250}
                , {field: 'dzwmj', title: '面积', width: 100}
                , {field: 'qlrmc', title: '权利人', minWidth: 250}
                , {
                    field: 'lzzt', title: '落宗状态', width: 100,fixed: 'right', templet: function (obj) {
                        if (obj.clfsjlzzt === 1) {

                            return '<span class="bdc-ylz">已落宗</span>'
                        } else {
                            return '<span class="bdc-wlz">未落宗</span>';
                        }
                    }
                }
                , {
                    field: 'pzzt', title: '匹配状态', width: 100, fixed: 'right',templet: function (obj) {
                        if (obj.clfsjppzt === 1) {

                            return '<span class="bdc-ypp">已匹配</span>'
                        } else {
                            return '<span class="bdc-wpp">未匹配</span>';
                        }
                    }
                }
                ,{field: 'xzzt', title: '限制状态', minWidth: 100,fixed: 'right', templet: function (obj) {
                        return getCqzZt(obj.bdcdyh,obj.qllx,obj.xmid,obj.qjgldm);
                    }
                }
                , {title: '操作', width: 100, fixed: 'right', toolbar: '#barDemo'}
            ]]
            , data: []
            ,request: {
                limitName: 'size' //每页数据量的参数名，默认：limit
            }
            ,response: {
                countName: 'totalElements',  //数据总数的字段名称，默认：count
                dataName: 'content' //数据列表的字段名称，默认：data
            }
            , done: function (res, curr, count) {
                var reverseList = ['zl','zldz'];
                reverseTableCell(reverseList,'matchTableId');
                $('.layui-table-tool-self').css('right', $('.bdc-export-tools').width() + 17 + 'px');

                if ($('.bdc-table-box .layui-table-main>.layui-table').height() == 0) {
                    $('.layui-table-body .layui-none').html('<img src="../../static/lib/bdcui/images/table-none.png" alt="">无数据');
                } else {
                    $('.layui-table-main.layui-table-body').height($('.bdc-table-box').height() - 81);
                }
                removeModal();
            }
        });

        $('.bdc-table-box').on('mouseenter', 'td', function () {
            if ($(this).text() && reverseList.indexOf($(this).attr("data-field")) !== -1) {
                $(this).append('<div class="layui-table-grid-down"><i class="layui-icon layui-icon-down"></i></div>');
            }
            $('.layui-table-grid-down').on('click', function () {
                setTimeout(function () {
                    $('.layui-table-tips-main .bdc-table-date').html(reverseString($('.layui-table-tips-main .bdc-table-date').html()));
                }, 20);
            });
        });
        $(".bdc-secondary-btn").click(function() {
            setTimeout("mhlx()","5");
        })
        //监听工具条
        table.on('tool(matchDataTable)', function (obj) {
            var data = obj.data; //获得当前行数据
            var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
            console.log(data);

            if (layEvent === 'ppym') {
                $.ajax({
                    url: getContextPath() + "/matchData/zsxm/count",
                    type: "post",
                    data: {xmid: data.xmid},
                    async: true,
                    success: function (count) {
                        if (count > 1) {
                            layer.confirm("该数据是一证多房数据，请注意落宗情况", {title:'提示'}, function(index){
                                layer.close(index);
                                //匹配页面
                                showMatchData(data);
                            });
                        } else {
                            showMatchData(data);
                        }
                    }
                });
                //匹配页面
                // showMatchData(data);
            }

            //匹配界面
            function showMatchData(objdata) {
                var index = layer.open({
                    type: 2,
                    title: "数据匹配",
                    area: ['1150px','80%'],
                    fixed: false, //不固定
                    maxmin: true, //开启最大化最小化按钮
                    content: getContextPath() + '/view/query/matchData.html?lx=check',
                    success: function (layero, index) {
                        var list =[];
                        list.push(objdata);
                        sessionStorage.setItem('matchData', JSON.stringify(list));
                    },
                    cancel: function (layero, index) {

                    }
                });
                layer.full(index);
            }

        });

    });
    renderSearchInput();
    // 默认模糊类型
    mhlx();
});
function mhlx() {
    var mhlx;
    if (isNullOrEmpty(ppMhlx) ){
        querySlymUrl();
        mhlx = ppMhlx;
    }else{
        mhlx = ppMhlx;
    }
    layui.form.val('searchFilter', JSON.parse((mhlx)));
}