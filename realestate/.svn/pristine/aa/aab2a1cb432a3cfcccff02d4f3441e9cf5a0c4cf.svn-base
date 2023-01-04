/**
 * 督察质检监管-质检整改情况明细台账
 */
layui.use(['form', 'jquery', 'laydate', 'element', 'table'], function () {
    var $ = layui.jquery,
        form = layui.form,
        laydate = layui.laydate,
        element = layui.element,
        table = layui.table;

    var sfsh = $.getUrlParam('sfsh');
    var zjxxid = $.getUrlParam('zjxxid');
    if(isNullOrEmpty(zjxxid)) {
        warnMsg("未指定要查询的关联质检记录！");
    } else {
        queryZgqk(zjxxid);
    }

    function queryZgqk(zjxxid) {
        addModel();
        $.ajax({
            url: "/realestate-supervise/rest/v1.0/dczjjg/zjxx/" + zjxxid + "/zjqks",
            type: "GET",
            contentType: 'application/json',
            dataType: "json",
            success: function (data) {
                if (data && data.length > 0) {
                    pageTableRender(data);
                } else {
                    pageTableRender([]);
                    warnMsg("未查询到整改报告！");
                }
            }, error: function () {
                failMsg("查询整改报告异常！");
            }, complete: function () {
                removeModal();
            }
        });
    }

    /**
     * 质检整改情况
     */
    function pageTableRender(data) {
        var action = sfsh === '1' ? '#zgqkBarAction2' : '#zgqkBarAction';
        table.render({
            elem: '#pageTable',
            id: 'pageTable',
            title: '质检整改情况',
            cols: [[
                {width: 150, sort: false, field: 'scry', title: '上传人员'},
                {width: 200, sort: false, field: 'zgbm', title: '整改部门'},
                {width: 200, sort: false, field: 'zgsj', title: '整改时间',
                    templet: function (d) {
                        if (!isNullOrEmpty(d.zgsj)) {
                            return formatDate(d.zgsj);
                        }
                    }
                },
                {width: 250, sort: false, field: 'zgqksm', title: '整改情况说明'},
                {width: 250, sort: false, field: 'zgwjmc', title: '整改文件名称'},
                {width: 250, sort: false, field: 'zgwjlj', title: '整改文件路径'},
                {width: 200, sort: false, field: 'zgwjscsj', title: '整改文件上传时间',
                    templet: function (d) {
                        if (!isNullOrEmpty(d.zgwjscsj)) {
                            return formatDate(d.zgwjscsj);
                        }
                    }
                },
                {width: 150, sort: false, field: 'zgsfwc', title: '是否完成',
                    templet: function (d) {
                        if(d.zgsfwc === 1) {
                            return "是";
                        } else {
                            return "否";
                        }
                    }
                },
                {width: 200, sort: false, templet: action, title: '操作', fixed: 'right'}
            ]],
            data: data,
            page: false,
            done: function () {
                $('.layui-table-tool-self').css('right', $('.bdc-export-tools').width() + 17 + 'px');
            }
        });
    }

    //监听工具条
    table.on('tool(pageTable)', function (obj) {
        var data = obj.data;
        if (obj.event === 'deleteZgbg') {
            deleteZgbg(data);
        } else if(obj.event === "downloadZgbg") {
            downloadZgbg(data);
        }
    });

    /**
     * 删除整改报告
     * @param data
     */
    function deleteZgbg(data) {
        if(!data || isNullOrEmpty(data.id)) {
            warnMsg("请选择需要删除的记录");
            return false;
        }

        var deleteIndex = layer.open({
            type: 1,
            skin: 'bdc-small-tips',
            title: '确认删除',
            area: ['320px'],
            content: '是否确认删除？',
            btn: ['确定', '取消'],
            btnAlign: 'c',
            yes: function () {
                //确定操作
                var list = new Array();
                list.push(data);

                addModel();
                $.ajax({
                    url: "/realestate-supervise/rest/v1.0/dczjjg/zgqk",
                    type: "DELETE",
                    data: JSON.stringify(list),
                    contentType: "application/json;charset=UTF-8",
                    dataType: "text",
                    success: function (data) {
                        if(data && data > 0) {
                            successMsg("删除成功！");
                            queryZgqk(zjxxid);
                        } else {
                            failMsg("删除失败！");
                        }
                    },
                    complete: function () {
                        removeModal();
                    }
                });
                layer.close(deleteIndex);
            },
            btn2: function () {
                //取消
                layer.close(deleteIndex);
            }
        });
    }

    /**
     * 下载整改报告
     * @param data
     */
    function downloadZgbg(data) {
        if(!data) {
            warnMsg("请选择需要下载的整改报告记录！");
            return false;
        }

        if(isNullOrEmpty(data.zgwjlj)) {
            warnMsg("当前记录没有整改报告，无法下载！");
            return false;
        }

        window.open("/realestate-supervise/rest/v1.0/files/download?wjlj=" +  encodeURI(data.zgwjlj) + "&wjmc=" + encodeURI(data.zgwjmc));
    }

    /**
     * 日期格式化
     * @param timestamp
     * @returns {*}
     */
    function formatDate(timestamp){
        if(!timestamp){
            return '';
        }

        var time = NewDate(timestamp);
        var y = time.getFullYear();
        var m = time.getMonth() + 1;
        var d = time.getDate();
        var h = time.getHours();
        var mm = time.getMinutes();
        var s = time.getSeconds();
        return y+'-'+add0(m)+'-'+add0(d)+' '+add0(h)+':'+add0(mm)+':'+add0(s);
    }

    /**
     * 解决IE不兼容其他浏览器Date对象的问题
     * @param str
     * @returns {Date|number}
     * @constructor
     */
    function NewDate(str) {
        if (!str) {
            return 0;
        }

        arr = str.split(" ");
        d = arr[0].split("-");
        t = arr[1].split(":");
        var date = new Date();
        date.setUTCFullYear(d[0], d[1] - 1, d[2]);
        date.setUTCHours(t[0], t[1], t[2], 0);
        return date;
    }

});
