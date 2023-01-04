//通过url来进行加载数据
function loadDataTablbeByUrl(_domId, _tableConfig, formStateId, readOnly, resourceName) {
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
                , limits: [50, 100, 150, 200]
                , request: {
                    limitName: 'size' //每页数据量的参数名，默认：limit
                }, done: function () {
                }, parseData: function (res) { //res 即为原始返回的数据
                    return {
                        "code": res.code, //解析接口状态
                        "msg": res.msg, //解析提示文本
                        "count": res.totalElements, //解析数据长度
                        "data": res.content //解析数据列表
                    };
                }, done: function () {
                    $('.layui-table-tool-self').css('right', $('.bdc-export-tools').width() + 17 + 'px');
                    $('.layui-table-body').height($('.bdc-table-box').height() - 129);
                    $('.layui-table-fixed .layui-table-body').height($('.bdc-table-box').height() - 129 - 17);
                }
            };
            if (!_tableConfig.cols || !_tableConfig.cols[0] || _tableConfig.cols[0].length < 1) {
                _tableConfig.cols = [[]]
            }
            var tableRenderConfig = $.extend({}, tableDefaultConfig, _tableConfig)
            table.render(tableRenderConfig)

            // 获取表单控制权限
            if (readOnly || !isNullOrEmpty(formStateId)) {
                getStateById(readOnly, formStateId, resourceName);
            }
        }
        //头工具栏事件
        table.on('toolbar(test)', function (obj) {
            var checkStatus = table.checkStatus(obj.config.id);
            switch (obj.event) {
                case 'getCheckData':
                    var data = checkStatus.data;
                    layer.alert(JSON.stringify(data));
                    break;
                case 'getCheckLength':
                    var data = checkStatus.data;
                    layer.msg('选中了：' + data.length + ' 个');
                    break;
                case 'isAll':
                    layer.msg(checkStatus.isAll ? '全选' : '未全选');
                    break;
            }
        });

        //点击表格中的更多
        $('.bdc-more-btn').on('click', function (event) {
            event.stopPropagation();
            $(this).siblings('.bdc-table-btn-more').show();
        });
        //点击更多内的任一内容，隐藏
        $('.bdc-table-btn-more a').on('click', function () {
            $(this).parent().hide();
        });
        //点击页面任一空白位置，消失
        $('body').on('click', function () {
            $('.bdc-table-btn-more').hide();
        });
    });
}

function tableReload(table_id, postData) {
    layui.use(['table'], function () {
        var table = layui.table;
        table.reload(table_id, {
            where: postData
            , page: {
                //重新从第 1 页开始
                curr: 1
            }
        });
    });
}

// 设置列表高度
function setHeight(searchHeight) {
    if (isNullOrEmpty(searchHeight)) {
        searchHeight = 131;
    }
    $('.layui-table-tool-self').css('right',$('.bdc-export-tools').width()+ 17 + 'px');

    if($('.bdc-table-box .layui-table-main>.layui-table').height() == 0){
        $('.layui-table-body .layui-none').html('<img src="../../static/lib/registerui/image/table-none.png" alt="">无数据');
    }else {
        $('.layui-table-body').height($('.bdc-table-box').height() - searchHeight);
        $('.layui-table-fixed .layui-table-body').height($('.bdc-table-box').height() - searchHeight - 17);
    }
}
