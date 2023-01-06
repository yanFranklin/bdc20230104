/**
 * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @description 工作流事件配置查询页面
 * @date : 2022/4/8 14:19
 */
var form, table;
layui.use(['table', 'laytpl', 'laydate', 'layer', 'form', 'upload'], function () {
    var $ = layui.jquery,
        layer = layui.layer,
        upload = layui.upload;
    table = layui.table;
    form = layui.form;

    var BASE_URL = '/realestate-inquiry-ui/rest/v1.0';
    var url = BASE_URL + "/gzl/sj/page";

    // 获取流程信息
    $.ajax({
        url: '/realestate-inquiry-ui/bdcZhGz/process/definitions',
        type: "GET",
        dataType: "json",
        success: function (data) {
            if (data) {
                $.each(data, function (index, item) {
                    $('#lcmc').append('<option value="' + item.processDefKey + '">' + item.name + '</option>');
                    // $('#lcmc-popup').append('<option value="' + item.processDefKey + '">' + item.name + '</option>');
                });
                form.render('select');
            }
        }
    });

    var zdNames = "gzlsjlx";
    var zdList = getMulZdList(zdNames);
    // 渲染字典项
    $.each(zdList.gzlsjlx, function (index, item) {
        $('#sjlx').append('<option value="' + item.DM + '" data-sjbs="' + item.SJBS + '">' + item.MC + '</option>');
    });

    form.on('select(lcmc)', function (data) {   //选择移交单位 赋值给input框
        //流程名称获取当前流程的所有节点
        var gzldyid = data.value;
        if (isNotBlank(gzldyid)) {
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
                        $.each(data, function (index, item) {
                            $("#jdmc").append('<option value="' + item.name + '">' + item.name + '</option>');
                        });
                        form.render('select');
                    }
                }
            });
        }
    })

    //子规则表格
    var unitTableTitle = [
        {type: 'checkbox', fixed: 'left'},
        {type: 'numbers', fixed: 'left', title: '序号', width: 60},
        {field: 'sjid', title: '事件id', hide: true},
        {field: 'sjmc', sort: true, title: '事件名称', align: 'center', minWidth: 500, style: 'text-align:left'},
        {field: 'jdmc', title: '节点名称', align: 'center', width: 200},
        {
            field: 'sycj', title: '事件类型', align: 'center', width: 200,
            templet: function (d) {
                return formatSycj(d.sjbs);
            }
        },
        {field: 'sjbs', title: '事件标识', align: 'center', width: 200},
        {field: 'cjr', title: '创建人', align: 'center', width: 200},
        {
            field: 'cjsj', title: '配置时间', align: 'center', width: 250,
            templet: function (d) {
                return format(d.cjsj);
            }
        },
        {title: '查看', fixed: 'right', toolbar: '#barDemo', width: 150}
    ]

    function formatSycj(sjbs) {
        if (sjbs.indexOf("_ZFQ") > -1) {
            return '<span class="" style="color:limegreen;">转发前事件</span>';
        } else if (sjbs.indexOf("_ZF") > -1) {
            return '<span class="" style="color:orange;">转发事件</span>';
        } else if (sjbs.indexOf("_TH") > -1) {
            return '<span class="" style="color:blue;">退回事件</span>';
        } else if (sjbs.indexOf("_BJ") > -1) {
            return '<span class="" style="color:#d000ff;">办结事件</span>';
        } else if (sjbs.indexOf("_TJBJ") > -1) {
            return '<span class="" style="color:coral;">条件办结事件</span>';
        } else if (sjbs.indexOf("_SC") > -1) {
            return '<span class="" style="color:red;">删除事件</span>';
        } else {
            return sjbs;
        }
    }

    function formatSf(data) {
        if (data && data === 1) {
            return '是';
        } else {
            return '否';
        }
    }

    var tableConfig = {
        id: 'sjid',
        toolbar: "#toolbar",
        defaultToolbar: ["filter"],
        cols: [unitTableTitle]
    }

    var reverseList = [''];
    table.render({
        elem: '#sjTable',
        toolbar: '#toolbar',
        title: '事件查询列表',
        defaultToolbar: ['filter'],
        request: {
            limitName: 'size' //每页数据量的参数名，默认：limit
        },
        data: [],
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
                data: res.content //解析数据列表
            }
        },
        done: function () {
            reverseTableCell(reverseList);
            setHeight();
        }
    });
    //加载表格
    table.reload("sjTable", {
        url: url,
        page: {
            curr: 1  //重新从第 1 页开始
        }
    });

    //查询表单信息
    form.on("submit(search)", function (data) {
        // 重新请求
        var obj = data.field;
        var lcmc = $("#lcmc").val();
        var sjlxSelectObj = document.getElementById("sjlx");
        var index = sjlxSelectObj.selectedIndex;
        var sjbs = sjlxSelectObj.options[index].getAttribute("data-sjbs");
        obj.sjbs = lcmc + "_" + (isNotBlank(sjbs) ? sjbs : "");
        table.reload("sjTable", {
            url: url,
            where: obj
            , page: {
                curr: 1  //重新从第 1 页开始
            }
        });
        return false;
    });

    //头工具栏事件
    table.on('toolbar(sjTable)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id);
        switch (obj.event) {
            case 'add':
                addGzlsj();
                break;
            case 'edit':
                editGzlsj(checkStatus.data);
                break;
            case 'delete':
                deleteGzlsj(checkStatus.data);
                break;
            case 'copy':
                copyGzlsj(checkStatus.data);
        }
    });

    /**
     * 行双击编辑
     */
    table.on('rowDouble(sjTable)', function (obj) {
        var array = new Array();
        array.push(obj.data);

        editGzlsj(array);
    });

    //监听工具条
    table.on('tool(sjTable)', function (obj) {
        var data = obj.data;
        if (isNullOrEmpty(data.sjid)) {
            warnMsg("未查询到工作流事件，无法查看！");
            return;
        }
        layer.open({
            title: '已关联接口',
            type: 2,
            area: ['960px', '450px'],
            content: 'glgzljk.html?sjid=' + data.sjid
            , cancel: function () {
            }
            , success: function () {
            }
        });
    });

    //回车事件
    $("body").keydown(function () {
        if (event.keyCode == "13") {//keyCode=13是回车键
            $('#search').click();
        }
    });
    //渲染应用名称下拉框
    renderYymc();

    function addGzlsj() {
        //新增的时候先生成一个sjid主键
        var sjid = "";
        var index = layer.open({
            type: 2,
            area: ['960px', '500px'],
            fixed: false, //不固定
            title: "新增工作流事件",
            content: getContextPath() + "/view/workflow/editGzlsj.html?sjid=" + sjid + "&type=insert",
            success: function () {

            },
            cancel: function () {
                $("#search").click();
            }
        });
        layer.full(index);
    }

    function editGzlsj(sjdata) {
        if (!sjdata || sjdata.length === 0) {
            layer.alert("<div style='text-align: center'>请选择需要编辑的记录！</div>", {title: '提示'});
            return;
        }
        if (sjdata.length > 1) {
            layer.alert("<div style='text-align: center'>只能选择一条数据编辑！</div>", {title: '提示'});
            return;
        }
        var index = layer.open({
            type: 2,
            area: ['960px', '500px'],
            fixed: false, //不固定
            title: "新增工作流接口",
            content: getContextPath() + "/view/workflow/editGzlsj.html?sjid=" + sjdata[0].sjid + "&type=update",
            btnAlign: "c",
            success: function () {

            },
            cancel: function () {
                $("#search").click();
            }
        });
        layer.full(index);
    }

    function deleteGzlsj(sjdata) {
        if (!sjdata || sjdata.length === 0) {
            layer.alert("<div style='text-align: center'>请选择需要删除的记录！</div>", {title: '提示'});
            return;
        }
        var deleteIndex = layer.open({
            type: 1,
            title: '确认删除',
            area: ['320px'],
            content: '确定要删除所选事件？',
            btn: ['确定', '取消'],
            skin: 'bdc-small-tips',
            btnAlign: 'c',
            yes: function () {
                addModel();
                var sjidList = [];
                $.each(sjdata, function (key, value) {
                    sjidList.push(value.sjid);
                });
                $.ajax({
                    url: "/realestate-inquiry-ui/rest/v1.0/gzl/sj",
                    type: 'DELETE',
                    data: JSON.stringify(sjidList),
                    contentType: 'application/json',
                    success: function (data) {
                        layer.close(deleteIndex);
                        removeModal();
                        ityzl_SHOW_SUCCESS_LAYER("删除成功");
                        $("#search").click();
                    },
                    error: function (xhr, status, error) {
                        removeModal();
                        delAjaxErrorMsg(xhr)
                    }
                })
            },
            btn2: function () {
                //取消
            }
        });


    }
});

function copyGzlsj(gzlsjData) {
    layer.open({
        title: '复制流程事件',
        type: 2,
        area: ['430px', '500px'],
        content: 'fzgzlsj.html'
        , cancel: function () {
            //右上角关闭回调
            //return false 开启该代码可禁止点击该按钮关闭
            //加载表格
            var BASE_URL = '/realestate-inquiry-ui/rest/v1.0';
            var url = BASE_URL + "/gzl/sj/page";
            table.reload("sjTable", {
                url: url,
                page: {
                    curr: 1  //重新从第 1 页开始
                }
            });
        }
        , success: function () {

        }
    });
}

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