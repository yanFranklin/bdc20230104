/**
 * 海门档案移交单处理JS
 */
layui.use(['table', 'laytpl', 'laydate', 'layer', 'form'], function () {
    var $ = layui.jquery,
        table = layui.table,
        layer = layui.layer,
        laydate = layui.laydate,
        form = layui.form;

    /**
     * 渲染时间
     */
    lay('.test-item').each(function () {
        laydate.render({
            elem: '#yjdsj'
            , type: 'date'
            , range: '到'
            , format: 'yyyy-MM-dd'
            , trigger: 'click'
        });
    });

    // 设置字符过多，省略样式
    var reverseList = ['zl'];

    /**
     * 加载Table数据列表
     */
    table.render({
        elem: '#yjdTable',
        id: 'yjdTable',
        toolbar: '#toolbar',
        defaultToolbar: [],
        data: [],
        request: {
            limitName: 'size' //每页数据量的参数名，默认：limit
        },
        cellMinWidth: 80,
        even: true,
        limits: commonLimits,
        cols: [[
            {type: 'checkbox', fixed: 'left'},
            {field: 'yjdbh', title: '移交单编号', minWidth: 200},
            {field: 'yjr',   title: '移交人', minWidth: 150},
            {field: 'jsr',  title: '接收人', minWidth: 150},
            {field: 'yjzt', title: '移交状态', minWidth: 200,
                templet: function (d) {
                    return getYjdzt(d);
                }
            },
            {field: 'yjsj',  title: '移交时间', minWidth: 200,
                templet: function (d) {
                    return format(d.yjsj);
                }
            },
            {templet: '#barAction', title: '操作', fixed: 'right', width: 120}
        ]],
        text: {
            none: '未查询到数据'
        },
        autoSort: false,
        page: true,
        parseData: function (res) {
            return {
                code: res.code, //解析接口状态
                msg: res.msg, //解析提示文本
                count: res.totalElements, //解析数据长度
                data: res.content //解析数据列表
            }
        },
        done: function () {
            var searchHeight = 131;
            setTableHeight(searchHeight);
            reverseTableCell(reverseList);
        }
    });

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

    function getYjdzt(data) {
        if(!data || isNullOrEmpty(data.yjzt)) {
            return "";
        }

        if("4" == data.yjzt) {
            return "尚未交接";
        } else if ("5" == data.yjzt) {
            return "已交接，尚未接受";
        } else if ("6" == data.yjzt) {
            return "交接成功";
        } else if("7" == data.yjzt) {
            return "交接失败，退回";
        } else {
            return "";
        }
    }

    document.onkeydown = function (event) {
        var code = event.keyCode;
        if (code == 13) {
            $('#search').click();
        }
    }

    /**
     * 点击查询
     */
    $('#search').on('click', function () {
        search();
        return false;
    });

    function search() {
        // 获取查询参数
        var obj = {};
        $(".search").each(function (i) {
            var value = $(this).val();
            if (!isNullOrEmpty(value)) {
                var name = $(this).attr('name');
                obj[name] = value;

                if (name == "yjdsj" && !isNullOrEmpty(value)) {
                    var yjsj = value.split("到");
                    obj["qssj"] = yjsj[0].trim();
                    obj["jzsj"] = yjsj[1].trim();
                } else {
                    obj[name] = value;
                }
            }
        });
        obj["version"] = "haimen";

        addModel();
        // 重新请求
        table.reload("yjdTable", {
            url:  "/realestate-register-ui/rest/v1.0/yjd/page"
            , where: obj
            , page: {
                curr: 1  //重新从第 1 页开始
            },
            done: function (res, curr, count) {
                removeModel();
                setTableHeight(131);
                reverseTableCell(reverseList);
            }
        });
    }

    /**
     * 表格头按钮事件
     */
    table.on('toolbar(yjdTable)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id);
        switch (obj.event) {
            // 新增移交单
            case 'jjd':
                jjd();
                break;

            // 进行移交
            case 'yj':
                yj(checkStatus.data);
                break;
            // 打印移交单
            case 'printYjd':
                printYjd(checkStatus.data);
                break;

        }
    });

    /**
     * 弹出新增移交单页面，进行生成移交单操作
     */
    function jjd() {
        layer.open({
            title: '新增移交单',
            type: 2,
            area: ['80%','80%'],
            content: 'addGdxxYjd.html'
            ,cancel: function(){
            }
            ,success: function (layero, index) {
            }
            ,end: function () {
                search();
            }
        });
    }

    /**
     * 进行移交操作
     */
    function yj(data) {
        if(!data || data.length == 0) {
            warnMsg("请选择需要移交的记录!");
            return;
        }

        for(var index = 0; index < data.length; index++) {
            // modified by lixin bug 26159 移交状态为 7 无法再次移交
            if (data[index].yjzt == 5 || data[index].yjzt == 6) {
                warnMsg("移交单存在已移交记录，请重新选择！");
                return;
            }
        }

        addModel();
        $.ajax({
            url: "/realestate-register-ui/rest/v1.0/yjd/yj",
            type: "POST",
            dataType: "text",
            data: JSON.stringify(data),
            contentType: 'application/json',
            success: function (res) {
                if("移交成功" == res) {
                    successMsg("移交成功！");
                    search();
                } else {
                    failMsg(res);
                }
                removeModel();
            },
            error: function (e) {
                failMsg("移交操作失败，请重试！");
                removeModel();
            }
        });
    }


    /**
     * 打印移交单
     */
    function printYjd(data) {
        if(!data || data.length == 0) {
            warnMsg("请选择需要移交的记录!");
            return;
        }
        if(data.length > 1) {
            warnMsg("目前只支持单条打印!");
            return;
        }
        var yjdid = data[0].yjdid;
        if (yjdid == null || yjdid == '') {
            warnMsg("没有生成移交单编号！");
            return false;
        }
        var dataUrl = getIP() + "/realestate-register-ui/rest/v1.0/print/jjd/" + yjdid + "/xml";
        print(jjdModelUrl, dataUrl, false);

    }

    /**
     * 表格行操作
     */
    table.on('tool(yjdTable)', function (obj) {
        var data = obj.data;
        if (obj.event === 'view') {
            view(data);
        }
    });

    /**
     * 查看移交单关联的项目信息
     */
    function view(data) {
        layer.open({
            title: '移交单项目信息查看',
            type: 2,
            area: ['80%','80%'],
            content: 'viewGdxxYjd.html?yjdid=' + data.yjdid
            ,cancel: function(){
            }
            ,success: function (layero, index) {
            }
        });
    }


    /**
     * 重新加载数据
     */
    window.reload = function () {
        search();
    };
});