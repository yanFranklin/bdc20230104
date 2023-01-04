var globalQueryList = [];
/** 工具栏数据*/
var globalToolbarList = [];
/** 行工具栏数据*/
var globalTooList = [];
/** 查询接口返回数据*/
var globalData = null;
/** 查询接口返回数据xmid*/
var globalXmid = [];
/** 限制状态*/
var globalXzzt = [];
/** 是否已查询限制状态信息*/
var loadXzztFlag = false;
/** 重现日志记录id */
var logid = $.getUrlParam("logid");
/** 查询台账重现数据加载url*/
var replayDataUrl = '/realestate-inquiry-ui/log/query/review';
/** 是否重现*/
var replayFlag = !isNullOrEmpty(logid);

/**
 * 加载配置
 * @param _domId tableId
 * @param _tableConfig
 * @param _url  加载配置信息的url
 */
function loadTableConfig(_domId, _tableConfig, _url, reversList) {
    if (_url) {
        if (!_tableConfig.cols || !_tableConfig.cols[0] || _tableConfig.cols[0].length < 1) {
            _tableConfig.cols = [];
        }
        $.ajax({
            url: _url,
            type: 'GET',
            async: false,
            contentType: 'application/json',
            success: function (data) {
                if (data.grid) {
                    _tableConfig.cols.push(data.grid);
                }
                // 日志重现时只加载列 隐藏搜索框
                if (replayFlag) {
                    // 隐藏搜索框
                    $('.bdc-search-box').addClass('bdc-hide');
                    _tableConfig.url = replayDataUrl + "/" + logid;
                } else {
                    // 工具栏
                    if (data.toolbar) {
                        globalToolbarList = data.toolbar;
                    }
                    if (data.tool) {
                        globalTooList = data.tool;
                    }
                    if (data.query) {
                        globalQueryList = data.query;
                    }
                }
            }
        });
    }
    loadDataTablbeByUrl(_domId, _tableConfig, reversList);
}


//通过url来进行加载数据
function loadDataTablbeByUrl(_domId, _tableConfig, reversList) {
    layui.use(['table', 'laypage', 'jquery'], function () {
        if (_domId) {
            var table = layui.table;
            var $ = layui.jquery;
            var tableDefaultConfig = {
                elem: _domId
                , toolbar: "#toolbarDemo"
                , cellMinWidth: 80
                , even: true
                , page: !replayFlag //开启分页
                , limit: 10
                , request: {
                    limitName: 'size' //每页数据量的参数名，默认：limit
                }, parseData: function (res) {
                    if (!res || !res.hasContent) {
                        res = $.extend({}, res);
                        res.msg = "无数据"
                    } else {
                        //res 即为原始返回的数据
                        globalData = res.content;
                        loadXzztFlag = false;
                        globalXmid.splice(0, globalXmid.length);
                        globalXzzt.splice(0, globalXzzt.length);
                        $.each(globalData, function (index, item) {
                            if (item.xmid != undefined && $.inArray(item.xmid, globalXmid) == -1) {
                                globalXmid.push(item.xmid);
                            }
                        });
                    }
                    return res;
                }
                , response: {
                    countName: 'totalElements' //数据总数的字段名称，默认：count
                    , dataName: 'content' //数据列表的字段名称，默认：data
                    , statusName: 'hasContent' //规定数据状态的字段名称，默认：code
                    , statusCode: true //规定成功的状态码，默认：0
                }
                , data: [],
                done: function () {
                    // $('.layui-none').width($('.layui-table-header>table').width());
                    if (reversList) {
                        reverseTableCell(reversList);
                    }
                    $('.layui-table-tool-self').css('right', $('.bdc-export-tools').width() + 17 + 'px');

                    if ($('.bdc-table-box .layui-table-main>.layui-table').height() == 0) {
                        $('.layui-table-body').height('56px');
                        $('.layui-table-fixed .layui-table-body').height('56px');
                    } else {
                        $('.layui-table-body').height($('.bdc-table-box').height() - 131);
                        $('.layui-table-fixed .layui-table-body').height($('.bdc-table-box').height() - 131 - 17);
                    }
                    /*$('.layui-table-tool-self').css('right',$('.bdc-export-tools').width()+17+'px');
                     $('.layui-table-body').height($('.bdc-table-box').height()-129);
                     $('.layui-table-fixed .layui-table-body').height($('.bdc-table-box').height()-129-17);*/
                }
            };
            if (!_tableConfig.cols || !_tableConfig.cols[0] || _tableConfig.cols[0].length < 1) {
                _tableConfig.cols = [[]]
            }
            var tableRenderConfig = $.extend({}, tableDefaultConfig, _tableConfig);
            table.render(tableRenderConfig);

            // 工具栏监听事件
            table.on("toolbar(dataTable)", function (obj) {
                console.log(obj);
                $.each(globalToolbarList, function (index, item) {
                    if (obj.event == item.layEvent && item.renderer) {
                        eval(item.renderer);
                    }
                });
            });

            // 行按钮监听事件
            table.on("tool(dataTable)", function (obj) {
                console.log(obj);
                $.each(globalTooList, function (index, item) {
                    if (obj.event == item.layEvent && item.renderer) {
                        eval(item.renderer + "(" + JSON.stringify(obj.data) + ")");
                    }
                });

            });
            //头工具栏事件
            /*table.on('toolbar(test)', function (obj) {
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
             });*/

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
            /* $(".downpanel").on("click", ".layui-select-title", function (e) {
             var $select = $(this).parents(".layui-form-select");
             $(".layui-form-select").not($select).removeClass("layui-form-selected");
             $select.addClass("layui-form-selected");
             e.stopPropagation();
             }).on("click", ".layui-form-checkbox", function (e) {
             e.stopPropagation();
             });*/
        }
    });
}

// 当前页数据
var currentTablePageData = new Array();
var cxdh;
function tableReload(table_id, postData, url, callFn) {
    addModel();
    layui.use(['table'], function () {
        var table = layui.table;
        var options = {
            where: postData
            , page: {
                //重新从第 1 页开始
                curr: 1
            }
            , data: undefined
            , done: function (res, curr, count) {
                debugger;
                cxdh = $.getUrlParam("cxdh");
                if (!isNullOrEmpty(cxdh)) {
                    // addModel();
                    // 获取查询条件和查询结果配置内容
                    $.ajax({
                        url: "/realestate-inquiry-ui/dtcx/get/" + cxdh,
                        type: "get",
                        async: false,
                        success: function (data) {
                            if (moduleCode){
                                setElementAttrByModuleAuthorityBeforeRendering(moduleCode,data);
                            }
                        }});
                }
                currentTablePageData = res.content;
                removeModal();
                setHeight();
                if (typeof callFn == "function") {
                    callFn();
                }

                try{
                    // 列表加载完成回调事件，由相关自定义查询JS定义
                    if(tableHasLoad && typeof(tableHasLoad) == "function"){
                        tableHasLoad(res);
                    }
                } catch(e){}

                // Excel批量查询需要保存日志
                saveExcelQueryLog(JSON.parse(postData.data), currentTablePageData);
            }
        };
        if (url != undefined && url != '') {
            options.url = url;
        }
        table.reload(table_id, options);
    });
}


/**
 * 权属状态格式化
 * @param cellValue
 * @returns {string}
 */
function formatQszt(qszt) {
    if (qszt == 0) {
        return '<span class="" style="color:orange;">临时</span>';
    } else if (qszt == 2) {
        return '<span class="" style="color:gray;">历史</span>'
    } else {
        return '<span class="" style="color:green;">现势</span>'
    }
}

/**
 * 限制状态格式化
 * @param xmid
 * @returns {string}
 */
function formatXzztByXmid(xmid) {
    var html = '';
    loadXzzt();
    for (var i = 0; i < globalXzzt.length; i++) {
        if (globalXzzt[i].key === xmid) {
            html = formatXzzt(globalXzzt[i]);
            break;
        }
    }
    return html;
}

/**
 * 不动产单元格式化
 * @param bdcdyh
 * @returns {string}
 */
function formatBdcdyh(bdcdyh) {
    if (!isNullOrEmpty(bdcdyh)) {
        return (bdcdyh.substring(0, 6) + " " + bdcdyh.substring(6, 12)
        + " " + bdcdyh.substring(12, 19) + " " + bdcdyh.substring(19));
    } else {
        return "";
    }

}

/**
 * 根据xmid加载限制状态信息
 */
function loadXzzt() {
    if (!loadXzztFlag && globalXmid.length > 0) {
        $.ajax({
            url: '/realestate-inquiry-ui/xzzt',
            type: 'GET',
            dataType: 'json',
            async: false,
            data: {
                xmidList: globalXmid
            },
            traditional: true,
            success: function (data) {
                if (data != undefined) {
                    globalXzzt = globalXzzt.concat(data);
                }
            },
            error: function (data) {
                layui.layer.msg("请注意，限制状态获取失败");
            }
        });
        loadXzztFlag = true;
    }
}

function formatXzzt(data) {
    var value = '';
    if (data != undefined) {
        if (data.sfcf === true) {
            value += '<span class="" style="color:#e50012;">查封 </span>';
        }
        if (data.sfycf === true) {
            value += '<span class="" style="color:#ee847d;">预查封 </span>';
        }
        if (data.sfdiya === true) {
            value += '<span class="" style="color:#009944;">抵押 </span>';
        }
        if (data.sfydya === true) {
            value += '<span class="" style="color:#aacd06;">预抵押 </span>';
        }
        if (data.sfyy === true) {
            value += '<span class="" style="color:#ef8200;">异议 </span>';
        }
        if (data.sfyg === true) {
            value += '<span class="" style="color:#ffdd05;">预告 </span>';
        }
        if (data.sfdiyi === true) {
            value += '<span class="" style="color:#855e6e;">地役 </span>';
        }
        if (data.sfsd === true) {
            value += '<span class="" style="color:#e5007f;">锁定 </span>';
        }
        if (data.jzq === true) {
            value += '<span class="" style="color:#13b1c4;">居住 </span>';
        }
        if (value === '') {
            value += '<span class="" style="color:green;">正常</span>';
        }
    }
    return value;
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
function reverseTableCell(reverseList) {
    var getTd = $('.layui-table-view .layui-table td');
    for (var i = 0; i < getTd.length; i++) {
        reverseList.forEach(function (v) {
            if ($(getTd[i]).attr('data-field') == v) {
                var getTdCell = $(getTd[i]).find('.layui-table-cell');
                getTdCell.css('direction', 'rtl');
                var tdHtml = reverseString(getTdCell.html());
                getTdCell.html('<span class="bdc-table-date">' + tdHtml + '</span>');
            }
        });
    }
}

