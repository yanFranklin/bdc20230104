layui.config({
    base: '../../../static/lib/form-select/'
}).extend({
    formSelects: 'formSelects-v4'
});
/**
 * 二级下拉框数据载入与刷新
 */
//一台服务器下的主机数量阈值
var applyNumber=500;
function rzxzfresh() {
    //所有主机、及主机下的服务、日期等数据存放地址(符合二级下拉格式)
    rzxzdata=[];
    //主机名称存放地址
    var  applay=[];
    $.ajax({
        url: '/realestate-inquiry-ui/config/v1.0/rzxz/applicationName'+"?t="+Math.random(),
        async: false,
        data: {},
        success: function (result) {
            applay = JSON.parse(JSON.stringify(result));
        }
    });
    applay.forEach(function (applicationName, index) {
        var applist = [];
        $.ajax({
            url: '/realestate-inquiry-ui/config/v1.0/rzxz/instanceName?applicationName=' + encodeURIComponent(applicationName)+"&t="+Math.random(),
            data: {},
            type: 'GET',
            async: false,
            success: function (result) {
                if(result.length!==0) {
                    for (let i = 0; i < result.length; i++) {
                        let application = {name: '', value: 0}
                        application.name = result[i].serviceId;
                        application.value = index * applyNumber + i + 1;
                        applist.push(application);
                    }
                    var app = {};
                    app.name = applicationName;
                    app.value = -(index + 1);
                    app.children = applist;
                    rzxzdata.push(app);
                }
            }
        });
    })
    layui.form.render();
    /*二级下拉树*/
    layui.formSelects.data('rzxzCheck', 'local', {
        arr: rzxzdata,
    });
    layui.formSelects.btns('rzxzCheck', [{
        icon: 'layui-icon layui-icon-ok',   //自定义图标, 可以使用layui内置图标
        name: '全选',
        click: function(id, vals, val, isAdd){
            //点击后的操作
            layui.formSelects.value('rzxzCheck', []);
            var arr=[];
            rzxzdata.forEach(function (value){
                arr.push(value.value);
                value.children.forEach(function (value){
                    arr.push(value.value);
                })
            })
            layui.formSelects.value('rzxzCheck', arr, true);
        }
    } ,'remove'],{ space: '10px'});
    // 简单的一种处理方式, 在多选第一次打开的时候收缩所有的子节点,收缩全部节点
    var isFirst = true;
    layui.formSelects.opened('rzxzCheck', function (id) {
        if (isFirst) {
            isFirst = false;
            $('[fs_id="rzxzCheck"]').find('.xm-cz i.icon-caidan').click();
        }
    });
    return rzxzdata;
}

layui.use(['table','laytpl','laydate','layer', 'form', 'formSelects'],function () {
    var $ = layui.jquery,
        table = layui.table,
        form = layui.form,
        layer = layui.layer;
    element = layui.element;
    laydate = layui.laydate;
    var formSelects = layui.formSelects;
    //所有主机、及主机下的服务、日期等数据存放地址(符合二级下拉格式)
    var rzxzdata = [];
    rzxzdata = rzxzfresh();
    layui.form.render();
    ['showFolderIcon', 'showLine', 'strict', 'simple'].forEach(function (key) {
        form.on('checkbox(' + key + ')', function (data) {
            var treeConfig = {};
            treeConfig[key] = data.elem.checked;
            demo1.update({
                tree: treeConfig
            })
        });
    })
    $("#downLog").on("click", function () {
        //存储最终传输到后端的值 ，即需要下载日志的主机、及主机下的服务、日期等数据存放地址
        var list = [];
        //二级下拉选择的数据
        let fields = formSelects.value('rzxzCheck');
        /*存储最终传输到后端的数据*/
        var list = [];
        for (let index = 0; fields.length !== 0;) {
            var value = fields[index].value;
            if (value < 0) {
                fields.splice(index, 1);
                continue;
            }
            /*具体的服务名称存储*/
            var uiapply = [];
            var obj = new Object();
            /*具体服务对应的主机名称存储进obj实体*/
            obj.apply = rzxzdata[Math.floor(value / applyNumber)].name;
            var len = fields.length;
            for (let i = 0; i < len; i++) {
                var value = fields[0].value;
                if (value < 0) {
                    fields.splice(index, 1);
                    continue;
                } else {
                    if (rzxzdata[Math.floor(value / applyNumber)].name === obj.apply) {
                        uiapply.push(rzxzdata[Math.floor(value / applyNumber)].children[value % applyNumber - 1].name);
                        fields.splice(i, 1);
                        i--;
                        len--;
                    }
                }
            }
            obj.uiapply = uiapply.toString();
            obj.start_time = document.getElementById("start_time").value;
            obj.end_time = document.getElementById("end_time").value;
            list.push(obj);
        }
        addModel("下载中");
        var path = "";
        $.ajax({
            url: "/realestate-inquiry-ui/config/v1.0/rzxz/downLog",
            data: JSON.stringify(list),
            type: "POST",
            contentType: 'application/json',
            dataType: "json",
            success: function (res) {
                if (res.length === 0) {
                    alertMsg("无对应日志下载");
                    removeModal();
                } else {
                    res.forEach(function (value){
                        path += value + ",";
                    })
                    if (path !== "") {
                        DownLoadFile({
                            url: "/realestate-inquiry-ui/config/v1.0/rzxz/downLogZip",
                            data: path,
                        });
                        removeModal();
                    }
                }
            }
        })
    });
    //监听上级下拉框，选中默认全选,如果已经选择则取消选择
    layui.formSelects.on('rzxzCheck', function (id, vals, val, isAdd, isDisabled) {
        //如果return false, 那么将取消本次操作
        if (val.value < 0 && isAdd === true) {
            for (let i = 0; i < rzxzdata.length; i++) {
                if (rzxzdata[i].value === val.value) {
                    var arr = [];
                    for (let j = 0; j < rzxzdata[i].children.length; j++) {
                        arr.push(rzxzdata[i].children[j].value)
                    }
                    layui.formSelects.value('rzxzCheck', arr, true);
                }
            }
        } else if (val.value < 0 && isAdd === false) {
            for (let i = 0; i < rzxzdata.length; i++) {
                if (rzxzdata[i].value === val.value) {
                    var arr = [];
                    for (let j = 0; j < rzxzdata[i].children.length; j++) {
                        arr.push(rzxzdata[i].children[j].value)
                    }
                    layui.formSelects.value('rzxzCheck', arr, false);
                }
            }
        }
    });

    var start = laydate.render({
        elem: '#start_time',
        type: 'date',
        btns: ['clear', 'confirm'],
        done: function (value, date) {
            endmax = end.config.max;
            end.config.min = date;
            end.config.min.month = date.month - 1;
        },
        change: function (value, date, endDate) {
            var timestamp2 = Date.parse(new Date(value));
            timestamp2 = timestamp2 / 1000;
            end.config.min = timestamp2;
            end.config.min.month = date.month - 1;
        }
    });
    var end = laydate.render({
        elem: '#end_time',
        type: 'date',
        done: function (value, date) {
            console.log("=======" + date);
            if ($.trim(value) === '') {
                var curDate = new Date();
                date = {'date': curDate.getDate(), 'month': curDate.getMonth() + 1, 'year': curDate.getFullYear()};
            }
            start.config.max = date;
            start.config.max.month = date.month - 1;
        }
    });

    var DownLoadFile = function (options) {
        data = options.data;
        var url = options.url;
        var $iframe = $('<iframe id="down-file-iframe" />');
        var $form = $(
            '<form target="down-file-iframe" method="POST" />'
        );
        $form.attr("action", url);
        $form.attr("encoding", "multipart/form-data");
        $form.attr("nctype", "multipart/form-data")
        // 封装参数
        $form.append('<input type="hidden" name="list" value=' + data.toString() + ' />');
        $(document.body).append($iframe);
        $iframe.append($form);
        // 提交
        $form[0].submit();
        $iframe.remove();
    }

    /**
     * 加载Table数据列表
     */
    table.render({
        elem: '#rzxzTable',
        toolbar: '#toolbar',
        title: '日志下载配置列表',
        defaultToolbar: ['filter'],
        url: "/realestate-inquiry-ui/config/v1.0/rzxz",
        request: {
            limitName: 'size' //每页数据量的参数名，默认：limit
        },
        cellMinWidth: 80,
        even: true,
        cols: [[
            {type: 'checkbox', fixed: 'left'},
            {minWidth: 100, sort: false, field: 'host', title: '主机'},
            {minWidth: 100, sort: false, field: 'port', title: '端口'},
            {minWidth: 120, sort: false, field: 'username', title: '用户名'},
            {
                minWidth: 100, sort: false, field: 'pwd', title: '密码', templet: function () {
                    return "******"
                },
            },
            {minWidth: 250, sort: false, field: 'path', title: '应用所在目录'},
            {minWidth: 150, sort: false, field: 'applyname', title: '日志存放临时目录'},
            {minWidth: 150, sort: false, field: 'name', title: '名称'},
        ]],
        text: {
            none: '未查询到数据'
        },
        autoSort: false,
        page: false,
        parseData: function (res) {
            return {
                code: res.code, //解析接口状态
                msg: res.msg, //解析提示文本
                count: res.count, //解析数据长度
                data: res.data//解析数据列表
            }
        },
        done: function () {
            $('.layui-table-tool-self').css('right', $('.bdc-export-tools').width() + 17 + 'px');

            if ($('.bdc-table-box .layui-table-main>.layui-table').height() == 0) {
                $('.layui-table-body .layui-none').html('<img src="../../../static/lib/registerui/image/table-none.png" alt="">无数据');
            } else {
                $('.layui-table-body').height($('.bdc-table-box').height() - 131);
                $('.layui-table-fixed .layui-table-body').height($('.bdc-table-box').height() - 131 - 17);
            }
        }
    });

    /**
     * 头工具栏事件
     */
    table.on('toolbar(rzxzTable)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id);
        switch (obj.event) {
            // 新增数据
            case 'add':
                addRzxz();
                break;
            // 编辑数据
            case 'edit':
                editRzxz(checkStatus.data);
                break;
            // 删除数据
            case 'delete':
                deleteRzxz(checkStatus.data);
                break;
        }
        ;
    });

    /**
     * 行双击编辑
     */
    table.on('rowDouble(rzxzTable)', function (obj) {
        var array = [];
        array.push(obj.data);
        editRzxz(array);
    });

    /**
     * 新增日志下载配置
     */
    function addRzxz() {
        layer.open({
            type: 2,
            title: '新增日志下载配置',
            anim: -1,
            shadeClose: true,
            maxmin: true,
            shade: false,
            area: ['760px', '340px'],
            offset: 'auto',
            content: ["addOrEditRzxz.html?action=add", 'yes'],
            end: function () {
                table.reload('rzxzTable');
                //所有主机、及主机下的服务、日期等数据存放地址(符合二级下拉格式)
                rzxzdata = rzxzfresh();

            }
        });
    }

    /**
     * 编辑日志下载配置
     */
    function editRzxz(data) {
        if (!data || data.length !== 1) {
            alertMsg("请选择需要编辑的某一条记录！");
            return;
        }

        layer.open({
            type: 2,
            title: '编辑日志下载配置',
            anim: -1,
            shadeClose: true,
            maxmin: true,
            shade: false,
            area: ['760px', '340px'],
            offset: 'auto',
            content: ["addOrEditRzxz.html?action=edit", 'yes'],
            success: function (layero, index) {
                var iframe = window['layui-layer-iframe' + index];
                iframe.setData(data[0]);
            },
            end: function () {
                table.reload('rzxzTable');
                //所有主机、及主机下的服务、日期等数据存放地址(符合二级下拉格式)
                rzxzdata = rzxzfresh();
            }
        });
    }

    /**
     * 删除日志下载配置
     */
    function deleteRzxz(data) {
        if (!data || data.length === 0) {
            alertMsg("请选择需要删除的记录！");
            return;
        }

        var deleteIndex = layer.open({
            type: 1,
            title: '确认删除',
            area: ['320px'],
            content: '确定要删除所选日志下载配置？',
            skin: 'bdc-small-tips',
            btn: ['确定', '取消'],
            btnAlign: 'c',
            yes: function () {
                // for (let i in data){
                //     base64Encode(data[i]);
                // }
                $.ajax({
                    url: "/realestate-inquiry-ui/config/v1.0/rzxz/delete",
                    type: "DELETE",
                    data: JSON.stringify(data),
                    contentType: 'application/json',
                    dataType: "json",
                    success: function (data) {
                        if (data) {
                            delSuccessMsg();
                            reload();
                        } else {
                            delFailMsg();
                        }
                        //所有主机、及主机下的服务、日期等数据存放地址(符合二级下拉格式)
                        rzxzdata = rzxzfresh();
                    },
                    error: function () {
                        delFailMsg();
                    }
                });
                layer.close(deleteIndex);
            },
            btn2: function () {
                //取消
            }
        });
    }

    /**
     * 重新加载数据
     */
    window.reload = function () {
        var obj = {};
        $(".search").each(function (i) {
            var value = $(this).val();
            var name = $(this).attr('name');
            obj[name] = value;
        });
        table.reload("rzxzTable", {
            where: obj
            , page: {
                curr: 1  //重新从第 1 页开始
            }
        });
    };
})
