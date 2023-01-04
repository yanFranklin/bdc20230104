/**
 * author: 前端组
 * date: 2018-12-14
 * version 3.0.0
 * describe: 初始化表格，工具栏事件
 */
layui.use(['table', 'laytpl', 'layer'], function () {
    var table = layui.table,
        $ = layui.jquery,
        layer = layui.layer;

    //监听滚动事件
    var scrollTop = 0,
        scrollLeft = 0;
    var tableTop = 0;
    var $nowBtnMore;
    $(window).on('scroll',function () {
        scrollTop = $(this).scrollTop();
        scrollLeft = $(this).scrollLeft();
        if($nowBtnMore){
            if(tableTop + 26 + $nowBtnMore.height() < document.body.offsetHeight){
                $nowBtnMore.css({top: tableTop + 26 -scrollTop,right: 20});
            }else {
                $nowBtnMore.css({top: tableTop -scrollTop - $nowBtnMore.height(),right: 20});
            }
        }
    });

    //点击表格中的更多
    $('.bdc-table-box').on('click','.bdc-more-btn',function (event) {
        // console.log( document.body.offsetHeight);
        // console.log($(this).offset().top + 26);
        tableTop = $(this).offset().top;
        event.stopPropagation();
        $nowBtnMore = $(this).siblings('.bdc-table-btn-more');
        var $btnMore = $(this).siblings('.bdc-table-btn-more');
        // console.log($btnMore.height());
        $('.layui-table-tool').css('z-index', 100);
        if($(this).offset().top + 26 + $btnMore.height() < document.body.offsetHeight){
            $btnMore.css({top: $(this).offset().top + 26 -scrollTop,right: 20});
        }else {
            $btnMore.css({top: $(this).offset().top - scrollTop - $btnMore.height() + 330, right: 20});
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

});


//------------工程部异步请求分页数据问题修改---------------------------
//通过url来进行加载数据
function loadDataTablbeByUrl(_domId, _tableConfig) {
    layui.use(['table', 'laypage', 'jquery'], function () {
        if (_domId) {
            var table = layui.table;
            var $ = layui.jquery;
            var tableDefaultConfig = {
                defaultToolbar: ['filter'],
                even: true,
                elem: _domId
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
                ,defaultToolbar : []
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

function tableReloadWithUrl(tableid, postData,url) {
    layui.use(['table'], function () {
        var table = layui.table;
        table.reload(tableid, {
            url: url,
            where: postData
            , page: {
                //重新从第 1 页开始
                curr: 1
            }
        });
    });
}

function reverseTableCell(reverseList, tableid) {
    var getTd = $("[lay-id='" + tableid + "'] tbody td");
    for (var i = 0; i < getTd.length; i++) {
        reverseList.forEach(function (v) {
            if ($(getTd[i]).attr('data-field') == v) {
                var getTdCell = $(getTd[i]).find('.layui-table-cell');
                getTdCell.css('direction', 'rtl');
                getTdCell.css('unicode-bidi', 'bidi-override');
                if (getTdCell.find('span').length == 0) {
                    var tdHtml = reverseString(getTdCell.html());
                    getTdCell.html('<span class="bdc-table-date">' + tdHtml + '</span>');
                }
            }
        });
    }
}