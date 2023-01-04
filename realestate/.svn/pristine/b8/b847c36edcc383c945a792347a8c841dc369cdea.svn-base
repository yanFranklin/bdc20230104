/**
 * 匹配台账js
 */
var processInsId = $.getUrlParam('processInsId');
layui.use(['table', 'form', 'layer'], function () {
    var table = layui.table,
        form = layui.form,
        $ = layui.jquery,
        layer = layui.layer;

    $(function () {
        var url = "/realestate-exchange/yzw/listShareData";
        $('#search').on('click', function () {
            var getSearch = form.val("searchFilter");
            console.log(getSearch);
            if(isNullOrEmpty(getSearch.sqrmc)
                && isNullOrEmpty(getSearch.sqrzjh)
                && isNullOrEmpty(getSearch.ywbh)){
                warnMsg("请至少输入一个查询条件！");
                return;
            }
            // 重新请求
            table.reload("tbyzwxxTable", {
                url: url,
                where: getSearch
                , page: {
                    curr: 1  //重新从第 1 页开始
                }
            });
        });

        table.render({
            elem: '#tbyzwxxtable',
            id: 'tbyzwxxTable'
            , page: true //开启分页
            , defaultToolbar: ['filter']
            , even: true
            , cols: [[ //表头
                {field: 'CASENO', title: '业务编号', minWidth: 250}
                , {field: 'CASEAPPLICANT', title: '申请人名称', minWidth: 250}
                , {field: 'CASEAPPLICANTPAPERCODE', title: '申请人证件号', minWidth: 250}
                , {field: 'CASELINKMANMOBILE', title: '手机号', minWidth: 250}
                , {field: 'SERVNAME', title: '业务名称', minWidth: 250}
                , {title: '操作', width: 100, fixed: 'right', toolbar: '#barDemo'}
            ]]
            , data: []
            , request: {
                limitName: 'size' //每页数据量的参数名，默认：limit
            }
            , response: {
                countName: 'totalElements',  //数据总数的字段名称，默认：count
                dataName: 'content' //数据列表的字段名称，默认：data
            }
            , done: function (res, curr, count) {
                var reverseList = ['zl', 'zldz'];
                reverseTableCell(reverseList, 'matchTableId');
                $('.layui-table-tool-self').css('right', $('.bdc-export-tools').width() + 17 + 'px');

                if ($('.bdc-table-box .layui-table-main>.layui-table').height() == 0) {
                    $('.layui-table-body .layui-none').html('<img src="../../static/lib/bdcui/images/table-none.png" alt="">无数据');
                } else {
                    $('.layui-table-main.layui-table-body').height($('.bdc-table-box').height() - 81);
                }
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

        //监听工具条
        table.on('tool(tbyzwxxtable)', function (obj) {
            var data = obj.data; //获得当前行数据
            var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
            console.log(data);

            if (layEvent === 'tb') {
                $.ajax({
                    url: "/realestate-exchange/yzw/tbyzwsj",
                    type: 'POST',
                    dataType: 'json',
                    async: false,
                    data: {
                        "caseno": data.CASENO,
                        "processInsId": processInsId
                    },
                    success: function (data) {
                        parent.closetbyzwxx();
                    }
                });
            }
        });

    });
    renderSearchInput();
});