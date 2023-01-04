layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl'], function () {
    var form = layui.form,
        $ = layui.jquery,
        table = layui.table,
        layer = layui.layer;

    var base_url = "/realestate-inquiry-ui";
    var zhid = $.getUrlParam("zhid");
    // 当前页数据
    var currentPageData = new Array();
    // 保存当前选中的子规则ID
    var jsonArray = new Array();
    // 子规则数据
    var zgzList;

    // 查询组合规则信息
    $.ajax({
        url: base_url + "/bdcZhGz/queryBdcGzZhgz?zhid=" + zhid,
        type: "GET",
        contentType: 'application/json',
        dataType: "json",
        success: function (data) {
            if(data){
                form.val('zhgzForm', data);
            }
        }
    });

    //加载组合规则与子规则的关系
    initZhGxSelect(zhid);
    function initZhGxSelect(zhid) {
        $.ajax({
            url: base_url + "/bdcZhGz/listBdcGzGx",
            dataType: "json",
            async: false,
            cache: false,
            data: {zhid: zhid},
            success: function (data) {
                if (data) {
                    data.forEach(function (v) {
                        jsonArray.push(v.gzid)
                    })
                }
            },
            error: function (xhr, status, error) {
                delAjaxErrorMsg(xhr)
            },
            complete: function(){
                getZgzList();
            }
        });
    }

    // 加载子规则列表
    function getZgzList(data) {
        $.ajax({
            url: base_url + "/zgz/page?page=1&size=100000",
            type: "GET",
            dataType: "json",
            data: data,
            cache: false,
            async: false,
            timeout: 10000,
            success: function (res) {
                if (res && jsonArray) {
                    zgzList = res.content;
                    zgzList.forEach(function (value) {
                        jsonArray.forEach(function (v) {
                            if (value.gzid == v) {
                                value.LAY_CHECKED = true;
                            }
                        })
                    });
                } else {
                    zgzList = res.content;
                }
            },
        });
    }

    //子规则表格
    var unitTableTitle = [
        {type: 'checkbox', fixed: 'left'},
        {field: 'gzid', title: '规则id', hide: true},
        {field: 'yxj', title: '验证级别', align: 'center', width: 100,
            templet: function (d) {
                return formatYxj(d.yxj);
            }
        },
        {field: 'gzmc', sort: true, title: '规则名称', align: 'center', width: 350, style: 'text-align:left'},
        {field: 'ytsm', title: '用途说明', align: 'center', style: 'text-align:left'},
        {field: 'pzr', sort: true, title: '配置人', align: 'center', width: 100},
        {
            field: 'pzrq', sort: true, title: '配置日期', align: 'center', width: 180,
            templet: function (d) {
                return format(d.pzrq);
            }
        },
        {title: '查看', fixed: 'right', toolbar: '#barDemo', width: 80}
    ];

    //监听工具条
    table.on('tool(zgzTable)', function (obj) {
        var data = obj.data;
        if (isNullOrEmpty(data.gzid)) {
            warnMsg("未查询到子规则信息，无法查看！");
            return;
        }

        if (obj.event === 'zgz') {
            window.open("/realestate-inquiry-ui/view/engine/zgz/editBdcZgz.html?gzid=" + data.gzid);
        }
    });

    function formatYxj(yxj){
        if(isNullOrEmpty(yxj)){
            return "";
        } else if(yxj == 1) {
            return '<span class="" style="color:blue;">忽略</span>';
        } else if(yxj == 3) {
            return '<span class="" style="color:red;">警告</span>';
        } else if(yxj == 4) {
            return '<span class="" style="color:limegreen;">例外</span>';
        }else {
            return yxj;
        }
    }

    var tableConfig = {
        id: 'zgzTable',
        defaultToolbar: [],
        cols: [unitTableTitle],
        data: zgzList,
        even: true
    }
    //加载表格
    loadDataTablbeByUrl('#zgzTable', tableConfig);
    //表格初始化
    table.init('zgzTable', tableConfig);
    reloadGz('zgzTable', null, zgzList);

    // 子规则列表选中事件
    table.on('checkbox(zgzTable)', function (obj) {
        // 获取选中或者取消的数据
        var data = obj.type == 'one' ? [obj.data] : currentPageData;

        if (obj.checked) {
            //.增加已选中项
            for (var index in data) {
                jsonArray.push(data[index].gzid);
            }
        } else {
            //.删除
            for (var index in jsonArray) {
                data.forEach(function (v) {
                    if (jsonArray[index] == v.gzid) {
                        delete jsonArray[index];
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
        getZgzList(data.field);
        reloadGz('zgzTable', data.field, zgzList);
        return false;//阻止表单提交
    });

    function reloadGz(tableid, postData, gzData) {
        layui.use(['table'], function () {
            var table = layui.table;
            table.reload(tableid, {
                data: gzData,
                where: postData,
                page: {
                    //重新从第 1 页开始
                    curr: 1,
                    limits: [10, 50, 100, 200, 500]
                }
                ,done: function (res, curr, count) {
                    currentPageData = res.data;

                    if($('.bdc-table-box .layui-table-main>.layui-table').height() == 0){
                        $('.layui-table-body .layui-none').html('<img src="../../../static/lib/registerui/image/table-none.png" alt="">无数据');
                    }
                }
            });
        });
    }

    // 组合规则校验事件
    $("#checkbtn").click(function () {
        if (isNullOrEmpty($("#zhid").val())) {
            warnMsg("请先保存组合规则！");
            return false;
        }

        if(0 == jsonArray.length){
            warnMsg("未关联子规则，无法验证！");
            return;
        }

        var zhgzid = $("#zhid").val();
        var zhbs = $("#zhbs").val();
        window.open("checkZhgz.html?zhid=" + zhgzid + "&zhbs=" + zhbs + "&size=" + jsonArray.length);
    });

    // 保存表单
    window.submitForm = function(){
        var data = {};
        data.zhid = $("#zhid").val();
        data.zhmc = $("#zhmc").val();
        data.zhbs = $("#zhbs").val();
        data.zhsm = $("#zhsm").val();
        data.gzid = jsonArray.toString();

        if (isNullOrEmpty($("#zhmc").val()) || isNullOrEmpty($("#zhbs").val())) {
            layer.alert("<div style='text-align: center'>规则名称、规则标识不可为空！</div>", {title: '提示'});
            return false;
        }

        var loadMsg = layer.msg('正在保存中', {
            icon: 16, shade: 0.01
        });

        $.ajax({
            url: base_url + "/bdcZhGz/saveOrUpdatZhGz",
            data: JSON.stringify(data),
            // data: data,
            type: "POST",
            contentType: 'application/json',
            dataType: "json",
            success: function (data) {
                if (data.success == true) {
                    success();
                    $("#zhid").val(data.zhid);
                } else {
                    fail(data);
                }
            },
            error: function (xhr, status, error) {
                delAjaxErrorMsg(xhr)
            },
            complete: function(){
                layer.close(loadMsg);
            }
        });

        saveDetailLog("ZHGZBC", "组合规则配置-保存", {"GZNR": JSON.stringify(data)});
        return false;
    }

    /**
     * 提交成功提示
     */
    window.success = function () {
        layer.msg('<img src="../../../static/lib/bdcui/images/success-small.png" alt="">提交成功');
    };

    /**
     * 提交失败提示
     * @param data
     */
    window.fail = function () {
        layer.msg('<img src="../../../static/lib/bdcui/images/error-small.png" alt="">提交失败，请检查填写内容');
    }

    /**
     * 重置
     */
    $('#reset').on('click', function () {
        $("#gzmc").val(null);
        $("#ytsm").val(null);
        $("#pzr").val(null);
        $("#glzgz").val(null);
        form.render('select');

        getZgzList();
        reloadGz('zgzTable', null, zgzList);
    });

});
