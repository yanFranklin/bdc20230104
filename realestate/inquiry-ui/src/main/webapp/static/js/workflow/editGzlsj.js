var form, $, table, layer;
var sjid = $.getUrlParam("sjid");
var type = $.getUrlParam("type");
var formSelects;
var sjbsList = [];
layui.config({
    base: '../../static/' //静态资源所在路径
}).extend({response: 'lib/bdcui/js/response', formSelects: 'lib/form-select/formSelects-v4'});
layui.use(['jquery', 'formSelects', 'layer', 'element', 'form', 'table', 'laytpl'], function () {
    form = layui.form;
    $ = layui.jquery;
    table = layui.table;
    layer = layui.layer;
    formSelects = layui.formSelects;
    formSelects.config('lcmc', {
        keyName: 'name',            //自定义返回数据中name的key, 默认 name
        keyVal: 'processDefKey'            //自定义返回数据中value的key, 默认 value
    }, true);
    formSelects.config('jdmc', {
        keyName: 'name',            //自定义返回数据中name的key, 默认 name
        keyVal: 'name'            //自定义返回数据中value的key, 默认 value
    }, true);
    var BASE_URL = '/realestate-inquiry-ui/rest/v1.0';
    var url = BASE_URL + "/gzl/jk/page";
    // 当前页数据
    var currentPageData = [];
    // 保存当前选中的子规则ID
    var jsonArray = [];
    // 子规则数据
    var jkList;

    var zdNames = "gzlsjlx";
    var zdList = getMulZdList(zdNames);
    var jdmc;
    // 查询工作流事件信息
    $.ajax({
        url: BASE_URL + "/gzl/sj?sjid=" + sjid,
        type: "GET",
        contentType: 'application/json',
        dataType: "json",
        success: function (data) {
            if (data) {
                data.sjid = sjid;
                form.val('gzlsjForm', data);
                //事件类型渲染
                var sjbs = [];
                if (data.sjbs) {
                    sjbs = data.sjbs.split('_');
                    sjbsList.push(data.sjbs);
                }
                jdmc = data.jdmc;
                $('#sjlx').append('<option value="" data-sjbs="">请选择</option>');
                $.each(zdList.gzlsjlx, function (index, item) {
                    if (sjbs && sjbs.length > 1 && sjbs[1] === item.SJBS) {
                        $('#sjlx').append('<option value="' + item.DM + '" data-sjbs="' + item.SJBS + '" selected>' + item.MC + '</option>');
                    } else {
                        $('#sjlx').append('<option value="' + item.DM + '" data-sjbs="' + item.SJBS + '">' + item.MC + '</option>');
                    }
                });
                //流程名称渲染
                // 获取流程信息
                $.ajax({
                    url: '/realestate-inquiry-ui/bdcZhGz/process/definitions',
                    type: "GET",
                    dataType: "json",
                    success: function (data) {
                        if (data) {
                            formSelects.data('lcmc', 'local', {arr: data});
                            formSelects.value('lcmc', [sjbs[0]]);
                            form.render('select');
                        }
                    }
                });
                //渲染节点名称
                $.ajax({
                    url: BASE_URL + '/gzl/jdmc',
                    type: "GET",
                    data: {gzldyid: sjbs[0]},
                    dataType: "json",
                    success: function (data) {
                        if (data) {
                            //清除之前的下拉选项
                            $("#jdmc").append('<option value="">请选择</option>');
                            formSelects.data('jdmc', 'local', {arr: data});
                            formSelects.value('jdmc', [jdmc]);
                            form.render('select');
                        }
                    }
                });
            }
        }
    });

    //加载工作流事件和接口的关联关系
    initSjJkGxSelect(sjid);

    function initSjJkGxSelect(sjid) {
        $.ajax({
            url: BASE_URL + "/gzl/gx",
            dataType: "json",
            async: false,
            cache: false,
            type: 'GET',
            data: {sjid: sjid},
            success: function (data) {
                if (data) {
                    data.forEach(function (v) {
                        jsonArray.push(v.jkid)
                    })
                }
            },
            error: function (xhr, status, error) {
                delAjaxErrorMsg(xhr)
            },
            complete: function () {
                getSelJkList();
            }
        });
    }

    // 加载关联接口列表
    function getSelJkList(data) {
        $.ajax({
            url: url + "?page=1&size=100000",
            type: "GET",
            dataType: "json",
            data: data,
            cache: false,
            async: false,
            timeout: 10000,
            success: function (res) {
                if (res && jsonArray) {
                    jkList = res.content;
                    jkList.forEach(function (value) {
                        jsonArray.forEach(function (v) {
                            if (value.jkid === v) {
                                value.LAY_CHECKED = true;
                            }
                        })
                    });
                } else {
                    jkList = res.content;
                }
            },
        });
    }

    //接口表格
    var unitTableTitle = [
        {type: 'checkbox', fixed: 'left'},
        {type: 'numbers', fixed: 'left', title: '序号', width: 60},
        {field: 'jkid', title: '接口id', hide: true},
        {field: 'jkmc', sort: true, title: '接口地址', align: 'center', width: 520, style: 'text-align:left'},
        {field: 'jksm', title: '接口说明', align: 'center', style: 'text-align:left', minWidth: 200},
        {
            field: 'jklx', title: '接口类型', align: 'center', width: 130,
            templet: function (d) {
                return formatJklx(d.jklx);
            }
        },
        {field: 'yymc', title: '应用名称', align: 'center', width: 130},
        {field: 'qqfs', title: '请求方式', align: 'center', width: 130},
        {
            field: 'sftb', title: '是否同步', align: 'center', width: 130,
            templet: function (d) {
                return formatSf(d.sftb);
            }
        },
        {
            field: 'sfhlyc', title: '是否忽略异常', align: 'center', width: 130,
            templet: function (d) {
                return formatSf(d.sfhlyc);
            }
        },
        {field: 'cjr', title: '配置人', align: 'center', width: 120},
        {field: 'jkxq', title: '详情', fixed: 'right', align: 'center', width: 80, toolbar: '#barDemo'},

    ];

    function formatJklx(jklx) {
        if (isNullOrEmpty(jklx)) {
            return "";
        } else if (jklx === 1) {
            return '<span class="" style="color:limegreen;">一般接口</span>';
        } else if (jklx === 2) {
            return '<span class="" style="color:orange;">登簿接口</span>';
        } else if (jklx === 3) {
            return '<span class="" style="color:blue;">办结接口</span>';
        } else if (jklx === 4) {
            return '<span class="" style="color:#d000ff;">退回接口</span>';
        } else if (jklx === 5) {
            return '<span class="" style="color:red;">删除接口</span>';
        } else {
            return jklx;
        }
    }

    function formatSf(data) {
        if (data && data === 1) {
            return '是';
        } else {
            return '否';
        }
    }

    //监听工具条
    table.on('tool(jkTable)', function (obj) {
        var data = obj.data;
        if (isNullOrEmpty(data.jkid)) {
            warnMsg("未获取到接口信息无法查看！");
            return;
        }

        if (obj.event === 'jkxq') {
            layer.open({
                type: 2,
                area: ['960px', '500px'],
                fixed: false, //不固定
                title: "工作流接口详情",
                content: getContextPath() + "/view/workflow/editGzljk.html?jkid=" + data.jkid,
                btnAlign: "c"
            });
        }
    });
    //加载表格
    var reverseList = [''];
    table.render({
        elem: '#jkTable',
        toolbar: '#toolbar',
        title: '接口查询列表',
        defaultToolbar: ['filter'],
        request: {
            limitName: 'size' //每页数据量的参数名，默认：limit
        },
        data: jkList,
        cellMinWidth: 80,
        where: {},
        even: true,
        cols: [unitTableTitle],
        text: {
            none: '未查询到数据'
        },
        autoSort: false,
        page: true,
        limits: [10, 50, 100, 200, 500],
        parseData: function (res) {
            return {
                code: res.code, //解析接口状态
                msg: res.msg, //解析提示文本
                count: res.totalElements, //解析数据长度
                data: jkList //解析数据列表
            }
        },
        done: function () {
        }
    });
    renderYymc();

    // 子规则列表选中事件
    table.on('checkbox(jkTable)', function (obj) {
        // 获取选中或者取消的数据
        var data = obj.type == 'one' ? [obj.data] : currentPageData;

        if (obj.checked) {
            //.增加已选中项
            for (var index in data) {
                jsonArray.push(data[index].jkid);
                //后台直接保存关联信息
                // saveGzlsjGx(data[index].jkid);
            }
        } else {
            //.删除
            for (var index in jsonArray) {
                data.forEach(function (v) {
                    if (jsonArray[index] == v.jkid) {
                        jsonArray.splice(index, 1);
                        //后台直接删除关联信息
                        // deleteGzlsjGx(v.jkid);
                    }
                });
            }
        }

        jsonArray = $.unique(jsonArray);
    });

    //回车刷新事件
    $("body").keydown(function () {
        if (event.keyCode == "13") {//keyCode=13是回车键
            $('#search').click();
        }
    });

    //查询表单信息
    form.on("submit(search)", function (data) {
        //渲染选中的接口
        getSelJkList(data.field);
        table.reload("jkTable", {
            url: url,
            data: jkList,
            where: data.field
            , page: {
                curr: 1  //重新从第 1 页开始
            }
        });
        return false;
    });

    form.on('select(sjlx)', function (data) {
        var lcmcList = formSelects.value('lcmc', 'val');
        var sjlxSelectObj = document.getElementById("sjlx");
        var index = sjlxSelectObj.selectedIndex;
        var bs = sjlxSelectObj.options[index].getAttribute("data-sjbs");
        $("#sjbs").val(lcmcList[0] + '_' + bs);
        //循环改变sjbsList 的值
        var newsjbsList = [];
        for (var i = 0; i < sjbsList.length; i++) {
            var oldSjbs = sjbsList[i].split('_');
            var newsjbs = oldSjbs[0] + '_' + bs;
            newsjbsList.push(newsjbs);
        }
        sjbsList = newsjbsList;
    });

    formSelects.on('lcmc', function (id, vals, val, isAdd, isDisabled) {
        //勾选
        var sjlxSelectObj = document.getElementById("sjlx");
        var index = sjlxSelectObj.selectedIndex;
        var bs = sjlxSelectObj.options[index].getAttribute("data-sjbs");
        if (isAdd) {
            //id:           点击select的id
            //vals:         当前select已选中的值
            //val:          当前select点击的值
            //isAdd:        当前操作选中or取消
            //isDisabled:   当前选项是否是disabled
            //false为清空不查询
            if (vals.length >= 1) {
                //事件名称设置只读，去掉必填
                $("#sjmc").val("");
                $("#sjmc").attr('disabled', 'off');
                sjbsList.push(val.value + '_' + bs);
                disabledAddFa();
            } else {
                if (val.value) {
                    queryJdmc(val.value);
                    //渲染事件标识
                    $("#sjbs").val(val.value + '_' + bs);
                    sjbsList.push(val.value + '_' + bs);
                }
            }
            form.render('select');
            // formSelects.render('jdmc');
        } else {
            //取消勾选
            //判断vals的长度，如果长度为2，取消后只有一条数据，需要获取节点名称
            if (vals.length === 2) {
                for (var i = 0; i < vals.length; i++) {
                    if (vals[i].value !== val.value) {
                        //查询节点名称并拼接
                        queryJdmc(vals[i].value);
                        $("#sjbs").val(vals[i].value + '_' + bs);
                        break;
                    }
                }
                $("#sjmc").removeAttr('disabled');
                //移除小锁
                var imgArray = $("#sjmc").parent().find("img");
                if (imgArray.length > 0) {
                    $("#sjmc").parent().find("img").remove();
                }
            }
            sjbsList.splice(val.value, 1);
            form.render('select');
            formSelects.render('jdmc');
        }
    });

    form.on('select(lcmc)', function (data) {
        var sjlxSelectObj = document.getElementById("sjlx");
        var index = sjlxSelectObj.selectedIndex;
        var bs = sjlxSelectObj.options[index].getAttribute("data-sjbs");
        $("#sjbs").val(data.value + '_' + bs);
        if (isNotBlank(data.value)) {
        }
    })

    // 保存表单
    window.submitForm = function () {
        var data = {};
        data.sjid = $("#sjid").val();
        data.sjmc = $("#sjmc").val();
        data.sjbs = $("#sjbs").val();
        data.jdmc = $("#jdmc").val();
        var sjlx = $("#sjlx").val();
        data.jkidList = jsonArray;
        if (isNullOrEmpty($("#sjbs").val()) || isNullOrEmpty(sjlx)) {
            layer.alert("<div style='text-align: center'>事件类型、事件标识不可为空！</div>", {title: '提示'});
            return false;
        }

        var loadMsg = layer.msg('正在保存中', {
            icon: 16, shade: 0.01
        });
        var lcmcList = formSelects.value('lcmc', 'val');
        var jdmcList = formSelects.value('jdmc', 'val');
        if (lcmcList.length > 1 || jdmcList.length > 1) {
            //走批量保存逻辑
            data.sjbsList = sjbsList;
            data.jdmcList = jdmcList;
            plSubmit(data);
        } else {
            data.jdmc = jdmcList[0];
            //处理事件名称
            if (isNullOrEmpty(data.sjmc)) {
                //默认处理为【流程名称】 + 节点名称 +事件类型
                var lcmc = formSelects.value('lcmc', 'name');
                var jdmc = formSelects.value('jdmc', 'val');
                var sjlxSelectObj = document.getElementById("sjlx");
                var index = sjlxSelectObj.selectedIndex;
                var sjlxmc = sjlxSelectObj.options[index].text;
                data.sjmc = "【" + lcmc[0] + "】" + (isNullOrEmpty(jdmc[0]) ? "" : jdmc[0]) + sjlxmc;
            }
            var gzldyids = formSelects.value('lcmc', 'val');
            data.gzldyid = gzldyids[0];
            doSubmit(data);
        }
        return false;
    }

    /**
     * @param gzlsj
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 执行提交操作
     * @date : 2022/4/21 11:15
     */
    function doSubmit(gzlsj) {
        $.ajax({
            url: BASE_URL + "/gzl/sj?type=" + type,
            data: JSON.stringify(gzlsj),
            // data: data,
            type: "POST",
            contentType: 'application/json',
            dataType: "json",
            success: function (data) {
                if (data) {
                    success();
                    $("#sjid").val(data.sjid);
                    //关闭当前页面
                    removeModal();
                    var index = parent.layer.getFrameIndex(window.name);
                    parent.layer.close(index);
                    parent.$('#search').click();
                } else {
                    fail();
                }
            },
            error: function (xhr, status, error) {
                delAjaxErrorMsg(xhr)
            }
        });
    }

    /**
     * @param null
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 批量保存信息
     * @date : 2022/4/21 15:09
     */
    function plSubmit(gzlsj) {
        $.ajax({
            url: BASE_URL + "/gzl/sj/pl?type=" + type,
            data: JSON.stringify(gzlsj),
            // data: data,
            type: "POST",
            contentType: 'application/json',
            dataType: "json",
            success: function (data) {
                success();
                //关闭当前页面
                removeModal();
                var index = parent.layer.getFrameIndex(window.name);
                parent.layer.close(index);
                parent.$('#search').click();
            },
            error: function (xhr, status, error) {
                delAjaxErrorMsg(xhr)
            }
        });

    }


    //查询节点名称拼接
    function queryJdmc(gzldyid) {
        $.ajax({
            url: BASE_URL + '/gzl/jdmc',
            type: "GET",
            data: {gzldyid: gzldyid},
            dataType: "json",
            success: function (data) {
                if (data) {
                    //清除之前的下拉选项
                    document.getElementById("jdmc").options.length = 0;
                    $("#jdmc").append('<option value="">请选择</option>');
                    formSelects.data('jdmc', 'local', {arr: data});
                    formSelects.value('jdmc', [jdmc]);
                    form.render('select');
                }
            }
        });
    }

    /**
     * 提交成功提示
     */
    window.success = function () {
        layer.msg('<img src="../../static/lib/bdcui/images/success-small.png" alt="">提交成功');
    };

    /**
     * 提交失败提示
     * @param data
     */
    window.fail = function () {
        layer.msg('<img src="../../static/lib/bdcui/images/error-small.png" alt="">提交失败，请检查填写内容');
    }

    $('#reset').on('click', function () {
        $("#jkmc").val(null);
        $("#jklx").val(null);
        $("#yymc").val(null);
        $("#gljk").val(null);
        form.render('select');
    });

});

function renderYymc() {
    $.ajax({
        url: "/realestate-inquiry-ui/rest/v1.0/gzl/app",
        type: 'GET',
        success: function (data) {
            if (data) {
                $.each(data, function (index, item) {
                    $('#yymc').append('<option value="' + item + '">' + item + '</option>');
                });
                form.render('select');
            }
        },
        error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    })
}

function saveGzlsjGx(jkid) {
    $.ajax({
        url: "/realestate-inquiry-ui/rest/v1.0/gzl/gx?jkid=" + jkid + "&sjid=" + sjid,
        type: 'POST',
        success: function (data) {
            if (data) {
                $.each(data, function (index, item) {
                    $('#yymc').append('<option value="' + item + '">' + item + '</option>');
                });
                form.render('select');
            }
        },
        error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    })
}

function deleteGzlsjGx(jkid) {
    $.ajax({
        url: "/realestate-inquiry-ui/rest/v1.0/gzl/gx?jkid=" + jkid + "&sjid=" + sjid,
        type: 'DELETE',
        success: function (data) {
            if (data) {
                $.each(data, function (index, item) {
                    $('#yymc').append('<option value="' + item + '">' + item + '</option>');
                });
                form.render('select');
            }
        },
        error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    })
}

