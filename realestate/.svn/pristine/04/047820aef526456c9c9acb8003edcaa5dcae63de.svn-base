var cxdh;
var rzlx;
var rzmc;
var cxtjList = [];
var cxjgList = [];
var zdList = [];
var globalTooList = [];
var globalToolbarList = [];
var recordColsLength = 0;
var canmhcx;
var hgjArr = [];
var checkeddata;
var searchFlag = false;
var jkid;
var jk;
var fhzkey;
var cxid;

var tableConfig = {
    elem: '#pageTable'
    , even: true
    , defaultToolbar: ['filter']
    , toolbar: "#toolbarDemo"
    , url: ''
    , data: []
    , page: true
    , cols: [[]]
    , where: {}
    , cellMinWidth: 80
    , limit: 10
    , request: {
        limitName: 'size' //每页数据量的参数名，默认：limit
        , loadTotal: true
    }, parseData: function (res) {
        if (!res || !res.hasContent) {
            res = $.extend({}, res);
            res.msg = '<img src="../../static/lib/registerui/image/table-none.png" alt="">无数据'
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
};

var cxfs;
/** 数据源*/
var dataUrl = '/realestate-inquiry-ui/rest/v1.0/jk/dtcx/list/result';
/** 查询条件*/
var queryObject = {};
layui.use(['form', 'table', 'layer', 'laydate', 'jquery'], function () {
    var laydate = layui.laydate;
    var form = layui.form;
    var layer = layui.layer;
    var table = layui.table;
    var $ = layui.jquery;

    $(function () {

        /**
         * added by cyc
         * 因为筛选框是动态拼接，layui.css等先绑定事件不适用，
         * 需要自行处理x图标的逻辑
         */
        $(".layui-input-inline").find('input').live('focus', function (item) {
            $(this).next('.inputreseticon').show();
        })
        $(".layui-input-inline").find('input').live('blur', function (item) {
            if ($(this).val() == "") {
                $(this).next('.inputreseticon').hide();
            }
        })
        $('.inputreseticon').live('click', function (item) {
            $(this).prev('input').val('');
            $(this).hide();
        })

        //select 的 x 的事件
        form.on('select', function (data) {
            if ($(this).text() == "请选择") {
                $(this).parents('.layui-input-inline').find('.reseticon').hide();
            } else {
                $(this).parents('.layui-input-inline').find('.reseticon').show();
            }
        });
        $('.reseticon').live('click', function (item) {
            $(this).parent().find("option:contains('请选择')").attr("selected", "selected");
            $(this).hide();
            layui.form.render("select");
        })
        $('#reset').live('click', function (item) {
            $('.bdc-percentage-container').find('.layui-form')
                .find('select').find("option:eq(0)")
                .attr("selected", "selected");
            setTimeout($('.bdc-percentage-container').find('.layui-form')
                .find('select').parent().find('input').val(''), 100);
            $('.reseticon').hide();
            $('.inputreseticon').hide();
        })

        /*获取url查询代号参数*/
        cxdh = $.getUrlParam("cxdh");
        var cxmc = decodeURI($.getUrlParam("cxmc"));
        // 窗口标题设置
        if (!isNullOrEmpty(cxmc)) {
            document.title = cxmc;
        }
        if (isNullOrEmpty(cxdh)) {
            layer.alert("缺失查询代号参数，请联系管理员！");
            window.close();
        } else {
            addModel();
            // 获取查询条件和查询结果配置内容
            $.ajax({
                url: "/realestate-inquiry-ui/dtcx/get/" + cxdh,
                type: "get",
                async: false,
                success: function (data) {
                    cxtjList = data.cxtjDOList;
                    cxjgList = data.cxjgDOList;
                    canmhcx = data.canmhcx;
                    jkid = data.jkff;
                    jk = data.jk;
                    fhzkey = data.fhzkey;
                    rzlx = data.rzlx;
                    rzmc = data.rzmc;
                    if (isNullOrEmpty(rzlx)){
                        rzlx = data.cxdh;
                    }
                    if (isNullOrEmpty(rzmc)){
                        rzmc = data.cxmc;
                    }
                    /*时间型的条件记录在这里，后面统一渲染*/
                    var dateTjlist = [];
                    /*记录条件个数，超过6个时放在高级搜索中*/
                    var cxtjJs = 0;
                    // var hiddenContent =
                    //     '<input name="cxid" value="' + data.cxid + '"hidden="hidden"/>' +
                    //     '<input name="cxdh" value="' + data.cxdh + '"hidden="hidden"/>';
                    queryObject["cxid"] = data.cxid;
                    // $(".layui-form").html($(".layui-form").html() + hiddenContent);
                    cxid = data.cxid;
                    $.each(cxtjList, function (i, cxtj) {
                        cxtjJs = cxtjJs + 1;
                        var tjlx = cxtj.tjtype;
                        var cxtjDom;
                        if (cxtj.tjzdname.replace(/[^\x00-\xff]/g, '**').length <= 13) {
                            cxtjDom =
                                '<div class="layui-inline">' +
                                '<label class="layui-form-label">' + cxtj.tjzdname + '</label>' +
                                '<div class="layui-input-inline">';
                        } else {
                            cxtjDom =
                                '<div class="layui-inline">' +
                                '<label class="layui-form-label bdc-two-lines">' + cxtj.tjzdname + '</label>' +
                                '<div class="layui-input-inline">';
                        }
                        if (tjlx === 'text' || tjlx === 'plcx') {
                            if ('qlr' == cxtj.tjzdid || 'qlrmc' == cxtj.tjzdid) {
                                cxtjDom +=
                                    '<input id="' + cxtj.tjzdid + '" class="layui-input" type="text" name="' + cxtj.tjzdid + '" placeholder="请输入" ondblclick="ReadIDCardNewQlrAndZjh(this)"/><i class="layui-icon layui-icon-close inputreseticon" style="display:none;"></i>\n';
                            } else {
                                cxtjDom +=
                                    '<input id="' + cxtj.tjzdid + '" class="layui-input" type="text" name="' + cxtj.tjzdid + '" placeholder="请输入"/><i class="layui-icon layui-icon-close inputreseticon" style="display:none;"></i>\n';
                            }
                        } else if (tjlx === 'date') {
                            dateTjlist.push(cxtj.tjzdid);
                            cxtjDom +=
                                '<input id="' + cxtj.tjzdid + '" class="layui-input" type="text" name="' + cxtj.tjzdid + '" placeholder="请选择"/>';
                        } else if (tjlx === 'dropdown') {
                            cxtjDom +=
                                '<select id="' + cxtj.tjzdid + '"name="' + cxtj.tjzdid + '" class="layui-select">' +
                                '<option value="" selected="selected">请选择</option>';
                            var zdList = getMulZdList(cxtj.zdid);
                            $.each(zdList[cxtj.zdid], function (i, zd) {
                                cxtjDom += '<option value="' + zd.DM + '">' + zd.MC + '</option>';
                            });
                            cxtjDom += '</select>';
                            cxtjDom += '<i class="layui-icon layui-icon-close bdc-hide' +
                                ' reseticon" style="display:none;right: 29px!important;"></i>\n';
                        } else {
                            cxtjDom +=
                                '<input id="' + cxtj.tjzdid + '" class="layui-input" type="text" name="' + cxtj.tjzdid + '" placeholder="请输入"/><i class="layui-icon layui-icon-close inputreseticon" style="display:none;"></i>\n';
                        }
                        cxtjDom +=
                            ' </div>' +
                            '</div>';
                        var dom = $(cxtjDom);
                        dom.find('input').addClass("cxtj");
                        dom.find('select').addClass("cxtj");
                        if (canmhcx === 1) {
                            if (cxtjJs <= 5) {
                                $("#cxtj-area").append(dom);
                            } else {
                                $("#gjSearch-area").append(dom);
                            }
                        } else {
                            if (cxtjJs <= 6) {
                                $("#cxtj-area").append(dom);
                            } else {
                                $("#gjSearch-area").append(dom);
                            }
                        }

                        $("#" + cxtj.tjzdid).val(getUrlParam(cxtj.tjzdid));
                    });

                    if (canmhcx === 1) {
                        // 是否显示高级搜索按钮
                        if (cxtjJs > 5) {
                            $("#gjSearch").css({
                                "display": "inline-block"
                            });
                        } else if (cxtjJs < 3) {
                            $('.bdc-percentage-container').addClass('bdc-line-search-container')
                        }
                    } else {
                        $("#searchdiv").removeClass("bdc-screen-search-box");
                        // 是否显示高级搜索按钮
                        if (cxtjJs > 6) {
                            $("#gjSearch").css({
                                "display": "inline-block"
                            });
                        } else if (cxtjJs < 4) {
                            $('.bdc-percentage-container').addClass('bdc-line-search-container')
                        }
                    }

                    form.render();


                    // 渲染layui时间控件
                    $.each(dateTjlist, function (i, dateDomId) {
                        laydate.render({
                            elem: '#' + dateDomId
                        })
                    });
                    var cols = [];

                    var zdStr = "";
                    // 遍历查询结果list，数据放在cols中
                    var countWidth = 0;
                    $.each(cxjgList, function (i, cxjg) {
                        if (cxjg.lk !== null && !isNaN(cxjg.lk)) {
                            countWidth += parseInt(cxjg.lk);
                        }
                        var colObject;
                        if (cxjg.jgtype === 'button') {
                            if (isNullOrEmpty(cxjg.url)) {
                                var hgj = '{"title":"' + cxjg.jgzdname + '","name":"' + cxjg.jgzdid.toLowerCase() + '","id":"' + cxjg.jgzdid + '"}';
                                hgjArr.push(hgj);
                                colObject = {};
                            } else {
                                var url =
                                    '<input  id="' + cxjg.jgzdid.toLowerCase() + '" name="' + cxjg.jgzdid.toLowerCase() + '" value="' + cxjg.url + '">';
                                var dom = $(url);
                                dom.find('input').addClass("cxtj");

                                var hgj = '{"title":"' + cxjg.jgzdname + '","name":"openwin","id":"' + cxjg.jgzdid.toLowerCase() + '"}';
                                hgjArr.push(hgj);
                                $("#btn-url-area").append(dom);
                                colObject = {};
                            }
                        } else if (cxjg.sortable === 1) {
                            colObject = {
                                field: cxjg.jgzdid.toLowerCase(),
                                title: cxjg.jgzdname,
                                width: cxjg.lk,
                                align: cxjg.dqfs,
                                fixed: cxjg.dqfs,
                                sort: true
                            };
                        } else {
                            colObject = {field: cxjg.jgzdid.toLowerCase(), title: cxjg.jgzdname, width: cxjg.lk, fixed: cxjg.dqfs};
                        }

                        if (!isNullOrEmpty(cxjg.fxkpd)) {
                            var fxkpd = eval('(' + cxjg.fxkpd + ')');
                            // 需要通过字典项转换的
                            if (!isNullOrEmpty(cxjg.zdid)) {
                                colObject["zdid"] = cxjg.zdid;
                                colObject["templet"] = function (d) {
                                    var checkflag = false;
                                    var result;
                                    $.each(fxkpd, function (i, fxky) {
                                        if (convertZdDmToMc(cxjg.zdid, d[cxjg.jgzdid.toLowerCase()], 'zdList') == fxky.value) {
                                            checkflag = true;
                                            result = '<span class="' + fxky.class + '">' + convertZdDmToMc(cxjg.zdid, d[cxjg.jgzdid.toLowerCase()], 'zdList') + '</span>';
                                        }
                                    });
                                    if (checkflag === false) {
                                        result = convertZdDmToMc(cxjg.zdid, d[cxjg.jgzdid.toLowerCase()], 'zdList');
                                    }
                                    return result;
                                };
                                zdStr = zdStr + cxjg.zdid + ",";
                            }
                            // 单元号加空格处理
                            else if (cxjg.jgzdid === "bdcdyh" || cxjg.jgzdid === "BDCDYH") {
                                colObject["templet"] = function (d) {
                                    var checkflag = false;
                                    var result;
                                    $.each(fxkpd, function (i, fxky) {
                                        if (d[cxjg.jgzdid.toUpperCase()] == fxky.value) {
                                            checkflag = true;
                                            result = '<span class="' + fxky.class + '">' + formatBdcdyh(d.bdcdyh) + '</span>';
                                        }
                                    });
                                    if (checkflag === false) {
                                        result = formatBdcdyh(d.bdcdyh);
                                    }
                                    return result;
                                };
                            } else {
                                colObject["templet"] = function (d) {
                                    var checkflag = false;
                                    var result;
                                    $.each(fxkpd, function (i, fxky) {
                                        if (d[cxjg.jgzdid.toLowerCase()] == fxky.value) {
                                            checkflag = true;
                                            result = '<span class="' + fxky.class + '">' + d[cxjg.jgzdid.toLowerCase()] + '</span>';
                                        }
                                    });
                                    if (checkflag === false) {
                                        result = d[cxjg.jgzdid.toLowerCase()];
                                    }
                                    return result;
                                }
                            }
                        } else {
                            // 需要通过字典项转换的
                            if (!isNullOrEmpty(cxjg.zdid)) {
                                colObject["zdid"] = cxjg.zdid;
                                colObject["templet"] = function (d) {
                                    return convertZdDmToMc(cxjg.zdid, d[cxjg.jgzdid.toLowerCase()], 'zdList');
                                };
                                zdStr = zdStr + cxjg.zdid + ",";
                            }
                            // 单元号加空格处理
                            if (cxjg.jgzdid === "bdcdyh" || cxjg.jgzdid === "BDCDYH") {
                                colObject["templet"] = function (d) {
                                    return formatBdcdyh(d.bdcdyh);
                                };
                            }
                        }
                        // 是否隐藏
                        if (cxjg.mrxs == 0) {
                            colObject["hide"] = true;
                        }
                        if (cxjg.jgtype !== "button") {
                            cols.push(colObject);
                        }
                    });

                    tableConfig.cols[0] = cols;
                    tableConfig.where = {data: ""};

                    var hascheckbox = false;
                    /*头部工具
                     * name:工具点击事件名
                     * title：工具显示名*/
                    if (data.ymgj) {

                        $(".layui-table-tool").css({
                            'display': "block"
                        });
                        var ymgj = eval('(' + data.ymgj + ')');
                        $.each(ymgj, function (i, gj) {
                            hascheckbox = true;
                            globalToolbarList.push({
                                layEvent: gj.name,
                                text: gj.title,
                                renderer: gj.name + '()',
                                class: 'bdc-major-btn'
                            });
                        });
                    }

                    if (data.zdyymgj) {

                        $(".layui-table-tool").css({
                            'display': "block"
                        });
                        var zdyymgj = eval('(' + data.zdyymgj + ')');
                        $.each(zdyymgj, function (i, gj) {
                            hascheckbox = true;
                            globalToolbarList.push({
                                layEvent: gj.name,
                                text: gj.title,
                                renderer: gj.name + '()',
                                class: gj.class
                            });
                        });
                    }
                    if (hascheckbox === true) {
                        countWidth += 60;
                        cols.unshift({type: 'checkbox', fixed: 'left'});
                    }

                    /*
                     * 加载自定义js
                     */
                    if (data.js) {
                        var jsList = eval('(' + data.js + ')');
                        $.each(jsList, function (i, js) {
                            jQuery.getScript(js)
                                .done(function () {
                                    if (typeof loadComplete !== 'undefined' && loadComplete instanceof Function) {
                                        load = loadComplete;
                                    }
                                });
                        });
                    }

                    if (hgjArr.length !== 0) {
                        /*行内工具
                         * name:工具点击事件名
                         * title：工具显示名*/
                        cols.push({
                            title: '操作',
                            fixed: 'right',
                            align: 'center',
                            minWidth: 80,
                            toolbar: '#rowTools',
                            width: hgjArr.length * 80
                        });

                        $.each(hgjArr, function (i, hgjdata) {
                            var hgj = eval('(' + hgjdata + ')');
                            var className;
                            if (i == 0) {
                                className = 'bdc-major-btn';
                            } else {
                                className = 'bdc-secondary-btn';
                            }
                            globalTooList.push({
                                layEvent: hgj.id,
                                class: className,
                                text: hgj.title,
                                renderer: hgj.name
                            });
                        });
                    }
                    tableConfig.done = function (res, curr, count) {
                        setHeight();
                        if (!isNullOrEmpty(data.fxkys)) {
                            changeCheckboxBackground(eval('(' + data.fxkys + ')'));
                        }
                        load(res);
                    };
                    table.render(tableConfig);
                    tableConfig.url = '/realestate-inquiry-ui/dtcx/list/result';
                    tableConfig.data = undefined;
                    // 行按钮监听事件
                    table.on("tool(dataTable)", function (obj) {
                        console.log(obj);
                        $.each(globalTooList, function (index, item) {
                            if (obj.event == item.layEvent && item.renderer) {
                                eval(item.renderer + "(" + JSON.stringify(obj) + "," + JSON.stringify(obj.data) + "," + JSON.stringify(item.text) + ")");
                            }
                        });

                    });

                    // 工具栏监听事件
                    table.on("toolbar(dataTable)", function (obj) {
                        var checkStatus = table.checkStatus(obj.config.id);
                        checkeddata = checkStatus.data;
                        console.log(obj);
                        $.each(globalToolbarList, function (index, item) {
                            if (obj.event == item.layEvent && item.renderer) {
                                eval(item.renderer);
                            }
                        });
                    });
                    /**
                     * 监听排序事件
                     */
                    table.on('sort(dataTable)', function (obj) {
                        queryObject["cxsql"] = $("#cxsql").val();
                        queryObject["sidx"] = obj.field;
                        queryObject["sord"] = obj.type;
                        tableReload('pageTable', {data: JSON.stringify(queryObject)}, dataUrl);
                        if (recordColsLength < 4) {
                            $(".layui-table-tool-self").hide();
                        }
                    });
                    // 获取页面所需的zdlist
                    if (!isNullOrEmpty(zdStr)) {
                        zdStr = zdStr.substring(zdStr.length - 1, zdStr);
                        zdList = getMulZdList(zdStr);
                    }
                    //自定义查询的字段
                    if (cols.length < 5) {
                        $(".layui-table-tool-self").hide();
                    }
                    recordColsLength = cols.length;
                    setTimeout(removeModal(), 100);
                },
                error: function (e) {
                    showErrorInfo(e);
                }
            });
        }
        var changeColor = function changeColor() {
            changeTrBackgroundExceptRight([
                {name: 'bdc-sd', color: '#333', background: '#a7afb6'}, true]);
            load();
        }
        form.on('submit(search)', function (data) {
            var searchParamFlag = false;
            var searchParamArr = $('.cxtj');
            for (var i = 0; i < searchParamArr.length; i++) {
                var id = $(searchParamArr[i]).attr("id");
                var isMh = id.substring(id.length - 2, id.length);
                //当输入框不是模糊类型的选择框， 并且输入框有值，则说明已输入条件
                if (isMh != "mh" && $(searchParamArr[i]).val() != "") {
                    searchParamFlag = true;
                    break;
                }
            }
            if (!searchParamFlag) {
                layer.msg('<img src="../../static/lib/bdcui/images/warn-small.png" alt="">请输入至少一个条件');
                return false;
            }
            // 保存查询日志
            saveDetailLog(rzlx , rzmc ,data);

            queryObject = data.field;
            queryObject["cxid"] = cxid;
            tableReload('pageTable', {data: JSON.stringify(queryObject) , jkid: jkid, jk: jk, fhzkey: fhzkey}, dataUrl, changeColor);

            if (recordColsLength < 4) {
                $(".layui-table-tool-self").hide();
            }
            return false;
        });

        if (searchFlag === true) {
            tableReload('pageTable', {data: JSON.stringify(queryObject)}, dataUrl, changeColor);
        }
    });

});


/**
 * 获取url中的参数
 * @param name
 * @returns {*}
 */
function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r != null) {
        searchFlag = true;
        queryObject[name] = decodeURIComponent(r[2]);
        return decodeURIComponent(r[2]);
    }
    return null; //返回参数值
}

/**
 * 查看登记簿
 * @date 2019/07/23
 * @author hanyi
 * @return
 */
function showGjSearch() {
    var isActive = $("#gjSearch-area").attr("active");
    if (isActive == 'true' || isActive == true) {
        $("#gjSearch-area").attr("active", "false");
        $("#gjSearch-area").hide();
    } else {
        $("#gjSearch-area").attr("active", "true");
        $("#gjSearch-area").show();
    }
    $('#search-div').toggleClass('bdc-button-box-four');
    $('.bdc-percentage-container').css('padding-top', $('.bdc-search-content').height() + 10);
}

function load(res) {

}

/**
 * 导出查询结果
 * @date 2019/07/23
 * @author hanyi
 * @return
 */
function openwin(obj, data, sjmc) {
    var url = $("#" + obj.event).val();
    $.each(data, function (key, value) {
        url = url.replace("#{" + key + "}", value);
        url = url.replace("#{" + key.toLowerCase() + "}", value);
    });
    // 保存Excel导出日志
    saveDetailLog(rzlx + "_" + obj.event.toUpperCase(), rzmc + "-" + sjmc,data);
    window.open(url);
}

/**
 * 导出excel文件
 */
function exportExcel() {
    var dataString = JSON.stringify(queryObject);

    if (dataString === '{}') {
        layer.msg("请先查询数据");
        return;
    }

    if (checkeddata.length === 0) {
        layer.confirm("是否导出全部数据？", {
            title: "提示",
            btn: ["确认", "取消"],
            btn1: function (index) {
                var data = JSON.stringify(checkeddata);

                var tempForm = $("<form>");
                tempForm.attr("id", "tempForm1");
                tempForm.attr("style", "display:none");
                tempForm.attr("target", "export");
                tempForm.attr("method", "post");
                tempForm.attr("action", '/realestate-inquiry-ui/dtcx/export/excel');

                var dataInput = $("<input>");
                dataInput.attr("type", "hidden");
                dataInput.attr("name", "dataString");
                dataInput.attr("value", dataString);
                tempForm.append(dataInput);

                var dataInputSelect = $("<input>");
                dataInputSelect.attr("type", "hidden");
                dataInputSelect.attr("name", "data");
                dataInputSelect.attr("value", data);
                tempForm.append(dataInputSelect);

                tempForm.on("submit", function () {
                });
                tempForm.trigger("submit");
                $("body").append(tempForm);
                tempForm.submit();
                $("tempForm1").remove();

                layer.close(index);
            },
            btn2: function (index) {
                return;
            }
        });
    } else {
        var data = JSON.stringify(checkeddata);

        var tempForm = $("<form>");
        tempForm.attr("id", "tempForm1");
        tempForm.attr("style", "display:none");
        tempForm.attr("target", "export");
        tempForm.attr("method", "post");
        tempForm.attr("action", '/realestate-inquiry-ui/dtcx/export/excel');

        var dataInput = $("<input>");
        dataInput.attr("type", "hidden");
        dataInput.attr("name", "dataString");
        dataInput.attr("value", dataString);
        tempForm.append(dataInput);

        var dataInputSelect = $("<input>");
        dataInputSelect.attr("type", "hidden");
        dataInputSelect.attr("name", "data");
        dataInputSelect.attr("value", data);
        tempForm.append(dataInputSelect);

        tempForm.on("submit", function () {
        });
        tempForm.trigger("submit");
        $("body").append(tempForm);
        tempForm.submit();
        $("tempForm1").remove();
    }
}
