//通过url来进行加载数据
function loadDataTablbeByUrl(_domId, _tableConfig) {
    layui.use(['table', 'laypage', 'jquery'], function () {
        if (_domId) {
            var table = layui.table;
            var $ = layui.jquery;
            var tableDefaultConfig = {
                elem: _domId,
                toolbar: "#toolbarDemo",
                even: true,
                cellMinWidth: 80,
                page: true,  //开启分页
                limits: [10, 50, 100, 200, 500],
                data: [],
                request: {
                    limitName: 'size' //每页数据量的参数名，默认：limit
                },
                response: {
                    countName: 'totalElements',  //数据总数的字段名称，默认：count
                    dataName: 'content' //数据列表的字段名称，默认：data
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
var reverseList = ['zl', 'zldz'];
function tableReload(tableid, postData, url,isChangeHeight) {
    console.log(isChangeHeight);
    layui.use(['table'], function () {
        var table = layui.table;
        table.reload(tableid, {
            url: url,
            where: postData,
            // data:[11],
            page: {
                //重新从第 1 页开始
                curr: 1,
                limits: [10, 50, 100, 200, 500]
            },
            done: function () {
                removeModal();
                $('.layui-table-tool-self').css('right', $('.bdc-export-tools').width() + 17 + 'px');
                reverseTableCell(reverseList, tableid);
                if ($('.bdc-form-div .layui-show .bdc-table-box .layui-table-main>.layui-table').height() === 0) {
                    $('.layui-table-body .layui-none').html('<img src="../../static/lib/bdcui/images/table-none.png" alt="">无数据');

                } else {
                    if(isChangeHeight == undefined){
                        $('.bdc-form-div .layui-show .layui-table-main.layui-table-body').height($('.bdc-form-div .layui-show .bdc-table-box').height() - 131);
                    }
                }
                //批量页面实时加载按钮权限
                var a = setInterval(function () {
                    if ($('.bdc-form-div .layui-show .bdc-table-box .layui-table-main>.layui-table').height() !== 0) {
                        var readOnly = getQueryString("readOnly");
                        if (isNotBlank(readOnly)) {
                            getStateById(readOnly, formStateId, "slympl");
                        }
                        clearInterval(a);
                    }
                }, 50);
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
    });
}

function tableReloadNew(tableid, postData, url) {
    layui.use(['table'], function () {
        var table = layui.table;
        table.reload(tableid, {
            url: url,
            where: postData,
            // data:[11],
            page: {
                //重新从第 1 页开始
                curr: 1
            },
            done: function () {
                $('.layui-table-tool-self').css('right', $('.bdc-export-tools').width() + 17 + 'px');
                if ($('.bdc-table-box .layui-table-main>.layui-table').height() === 0) {
                    $('.layui-table-body .layui-none').html('<img src="../../static/lib/bdcui/images/table-none.png" alt="">无数据');
                } else {
                    $('.layui-table-main.layui-table-body').height($('.bdc-table-box').height() - 131);
                }
                reverseTableCellOne(reverseList);

                //批量页面实时加载按钮权限
                var a = setInterval(function () {
                    if ($('.bdc-form-div .layui-show .bdc-table-box .layui-table-main>.layui-table').height() !== 0) {
                        var readOnly = getQueryString("readOnly");
                        if (isNotBlank(readOnly)) {
                            getStateById(readOnly, formStateId, "slympl");
                        }
                        clearInterval(a);
                    }
                }, 50);
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
    });
}

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

//用于多个tab表格的翻转
function reverseTableCell(reverseList, tableid) {
    var getTd = $("[lay-id='" + tableid + "'] tbody td");
    for (var i = 0; i < getTd.length; i++) {
        reverseList.forEach(function (v) {
            if ($(getTd[i]).attr('data-field') == v) {
                var getTdCell = $(getTd[i]).find('.layui-table-cell');
                getTdCell.css('direction', 'rtl');
                if (getTdCell.find('span').length == 0) {
                    var tdHtml = reverseString(getTdCell.html());
                    getTdCell.html('<span class="bdc-table-date">' + tdHtml + '</span>');
                }
            }
        });
    }
}

//用于一个表格的翻转
function reverseTableCellOne(reverseList) {
    var getTd = $('.bdc-table-box .layui-table-view .layui-table td');
    for (var i = 0; i < getTd.length; i++) {
        reverseList.forEach(function (v) {
            if ($(getTd[i]).attr('data-field') == v) {
                var getTdCell = $(getTd[i]).find('.layui-table-cell');
                getTdCell.css('direction', 'rtl');
                if (getTdCell.find('span').length == 0) {
                    var tdHtml = reverseString(getTdCell.html());
                    getTdCell.html('<span class="bdc-table-date">' + tdHtml + '</span>');
                }
            }
        });
    }
}

//查询条件框添加删除图标
function renderSearchInput() {
    if (!(navigator.appName == "Microsoft Internet Explorer" && navigator.appVersion.match(/9./i) == "9.")) {
        //监听input点击
        $('.layui-form-item .layui-input-inline').on('click', '.layui-icon-close', function () {
            $(this).siblings('.layui-input').val('');
        });

        $('.layui-form-item .layui-input-inline .layui-input').on('focus', function () {
            $(this).siblings('.layui-icon-close').removeClass('bdc-hide');
        }).on('blur', function () {
            var $this = $(this);
            setTimeout(function () {
                $this.siblings('.layui-icon-close').addClass('bdc-hide');
            }, 150)
        });
    }
}

function setTableHeight(searchHeight) {
    $('.layui-table-tool-self').css('right', $('.bdc-export-tools').width() + 17 + 'px');
    if ($('.bdc-table-box .layui-table-main>.layui-table').height() == 0) {
        $('.layui-table-body .layui-none').html('<img src="../../static/lib/bdcui/images/table-none.png" alt="">无数据');
    } else {
        $('.layui-table-body').height($('.bdc-table-box').height() - searchHeight);
        $('.layui-table-fixed .layui-table-body').height($('.bdc-table-box').height() - searchHeight - 17);
    }
}
