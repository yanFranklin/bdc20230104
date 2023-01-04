/**
 * 附件台账
 */
layui.use(['form', 'jquery', 'laydate', 'element', 'table'], function () {
    var $ = layui.jquery,
        form = layui.form,
        laydate = layui.laydate,
        element = layui.element,
        table = layui.table;

    var ywid = $.getUrlParam('ywid');
    var page = $.getUrlParam('page');
    if(isNullOrEmpty(ywid)) {
        warnMsg("未指定要查询的关联业务信息！");
    } else {
        queryZgqk(ywid);
    }

    function queryZgqk(ywid) {
        addModel();
        $.ajax({
            url: "/realestate-supervise/rest/v1.0/files/all/" + ywid,
            type: "GET",
            contentType: 'application/json',
            dataType: "json",
            success: function (data) {
                if (data && data.length > 0) {
                    pageTableRender(data);
                } else {
                    pageTableRender([]);
                    warnMsg("未查询到附件！");
                }
            }, error: function () {
                failMsg("查询附件异常！");
            }, complete: function () {
                removeModal();
            }
        });
    }

    function pageTableRender(data) {
        var action = page === 'view' ? '#fileBarAction2' : '#fileBarAction';
        table.render({
            elem: '#pageTable',
            id: 'pageTable',
            title: '附件情况',
            cols: [[
                {minWidth: 250, sort: false, field: 'wjmc', title: '文件名称'},
                {width: 250, sort: false, field: 'wjlj', title: '文件路径'},
                {width: 200, sort: false, field: 'wjscsj', title: '文件上传时间'},
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
        if (obj.event === 'deleteFile') {
            deleteFile(data);
        } else if(obj.event === "downloadFile") {
            downloadFile(data);
        }
    });

    /**
     * 删除附件
     * @param data
     */
    function deleteFile(data) {
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
                    url: "/realestate-supervise/rest/v1.0/files",
                    type: "DELETE",
                    data: JSON.stringify(list),
                    contentType: "application/json;charset=UTF-8",
                    dataType: "text",
                    success: function (data) {
                        if(data && data > 0) {
                            successMsg("删除成功！");
                            queryZgqk(ywid);
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
     * 下载附件
     * @param data
     */
    function downloadFile(data) {
        if(!data) {
            warnMsg("请选择需要下载的附件记录！");
            return false;
        }

        if(isNullOrEmpty(data.wjlj)) {
            warnMsg("当前记录没有附件，无法下载！");
            return false;
        }

        window.open("/realestate-supervise/rest/v1.0/files/download?wjlj=" +  encodeURI(data.wjlj) + "&wjmc=" + encodeURI(data.wjmc));
    }
});
