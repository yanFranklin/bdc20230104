//通过url来进行加载数据
function loadDataTablbeByUrl(_domId, _tableConfig) {
    layui.use(['table', 'laypage', 'jquery'], function () {
        if (_domId) {
            var table = layui.table;
            var $ = layui.jquery;
            var tableDefaultConfig = {
                elem: _domId,
                toolbar: "#toolbarDemo",
                cellMinWidth: 80,
                page: true,  //开启分页
                limit: 10,
                even: true,
                request: {
                    limitName: 'size' //每页数据量的参数名，默认：limit
                },
                response: {
                    countName: 'totalElements',  //数据总数的字段名称，默认：count
                    dataName: 'content' //数据列表的字段名称，默认：data
                },
                done: function () {
                    $('.layui-table-tool-self').css('right',$('.bdc-export-tools').width()+ 17 + 'px');

                    if($('.bdc-table-box .layui-table-main>.layui-table').height() == 0){
                        $('.layui-table-body .layui-none').html('<img src="../../../static/lib/registerui/image/table-none.png" alt="">无数据');
                    }else {
                        $('.layui-table-body').height($('.bdc-table-box').height() - 131);
                        $('.layui-table-fixed .layui-table-body').height($('.bdc-table-box').height() - 131 - 17);
                    }
                }
            }
            if (!_tableConfig.cols || !_tableConfig.cols[0] || _tableConfig.cols[0].length < 1) {
                _tableConfig.cols = [[]]
            }
            var tableRenderConfig = $.extend({}, tableDefaultConfig, _tableConfig)
            table.render(tableRenderConfig);
        }
    });
}

function tableReload(tableid, postData,url,fun) {
    layui.use(['table'], function () {
        var table = layui.table;
        table.reload(tableid, {
            url:url,
            where: postData,
            page: {
                //重新从第 1 页开始
                curr: 1,
                limits: [10, 50, 100, 200, 500],
            },
            done: function(res, curr, count){
                if (tableid == "zhid"){
                    fun();
                }

            }
        });
    });
}

function tableReloadGz(tableid, postData, gzData) {
    layui.use(['table'], function () {
        var table = layui.table;
        table.reload(tableid, {
            data: gzData,
            where: postData,
            page: {
                //重新从第 1 页开始
                curr: 1
            }
        });
    });
}
