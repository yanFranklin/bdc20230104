/** 工具栏数据*/
var globalToolbarList = [];
/** 行工具栏数据*/
var globalTooList = [];


//通过url来进行加载数据
function loadDataTablbeByUrl(_domId, _tableConfig) {
    layui.use(['table', 'laypage', 'jquery'], function () {
        if (_domId) {
            var table = layui.table;
            var $ = layui.jquery;
            var tableDefaultConfig = {
                toolbar: true,
                defaultToolbar: ['filter'],
                even: true,
                elem: _domId
                , cellMinWidth: 80
                , page: true //开启分页
                , limit: 10
                , parseData: function (res) { //res 即为原始返回的数据
                    res.hasContent=true
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

/**
 * 不动产单元格式化
 * @param bdcdyh
 * @returns {string}
 */
function formatBdcdyh(bdcdyh) {
    if (!isNullOrEmpty(bdcdyh)) {
        bdcdyh = bdcdyh.replace(' ', '');
        return (bdcdyh.substring(0, 6) + " " + bdcdyh.substring(6, 12)
            + " " + bdcdyh.substring(12, 19) + " " + bdcdyh.substring(19));
    } else {
        return "";
    }

}





