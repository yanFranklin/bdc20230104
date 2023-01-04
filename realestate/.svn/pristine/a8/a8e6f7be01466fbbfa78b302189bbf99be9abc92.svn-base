var selectDataList = [];
var processDefKey;
layui.use(['jquery', 'table', 'layer'], function () {
    var $ = layui.jquery,
        table = layui.table,
        layer = layui.layer;
    $(function () {
        processDefKey = $.getUrlParam('processDefKey');

        // 渲染提示信息
        generate();
        /**
         * 监听台账查询 input 点击 显示 x 清空 input 中的内容
         */
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
        $('.bdc-content').css('min-height', $('body').height() - 56);

        /**
         * 渲染补录信息表格
         * @type {string}
         */
        var url = "/realestate-register-ui/rest/v1.0/blxx/page";
        var tableId = '#blTable';
        var toolbar = '#toolbar';
        renderblTable(url, tableId, toolbar);

        /**
         * 查询点击事件
         */
        $('#search').on('click', function () {
            // 获取查询内容
            var obj = {};
            $(".search").each(function (i) {
                var value = $(this).val();
                var name = $(this).attr('name');
                // 此处去除查询条件中的空格
                obj[name] = deleteWhitespace(value);
            });
            obj['qszt'] = '2';
            // 查询时间长，添加遮罩
            addModel();
            // 重新请求
            table.reload("blTable", {
                url: url,
                where: obj,
                page: {
                    curr: 1  //重新从第 1 页开始
                },
                done: function(){
                    // 判断当前登录用户角色
                    var user = queryCurrentUser();
                    if (user && 1 != user.admin) {
                        console.log(user);
                        $.each($(".bdc-delete-btn"), function (index, item) {
                            $(item).attr("style", "display:none");
                        });
                    }

                    removeModel();
                    $('.layui-table-tool-self').css('right', $('.bdc-export-tools').width() + 17 + 'px');
                    if ($('.layui-table-body>.layui-table').height() == 0) {
                        $('.layui-table-body .layui-none').html('<img src="../../static/lib/bdcui/images/table-none.png" alt="">无数据');
                    }
                }
            });
        });

        function checkEdit(obj) {
            addModel();
            // 组织验证数据
            var selectDataList = [];
            var bdcGzYzsjDTO = {};
            bdcGzYzsjDTO.bdcdyh = obj.bdcdyh;
            bdcGzYzsjDTO.bdcqzh = obj.bdcqzh;
            bdcGzYzsjDTO.xmid = obj.xmid;
            selectDataList.push(bdcGzYzsjDTO);

            var bdcGzYzQO = {};
            bdcGzYzQO.zhbs = processDefKey + "_XZBDCDY";
            bdcGzYzQO.paramList = selectDataList;
            $.ajax({
                url: getContextPath() + '/rest/v1.0/blxx/bdcGzyz',
                type: 'POST',
                dataType: 'json',
                contentType: "application/json;charset=UTF-8",
                data: JSON.stringify(bdcGzYzQO),
                success: function (data) {
                    if (data.length > 0) {
                        removeModel();
                        loadTsxx(data);
                        gzyztsxx();
                    } else {
                        var bdcBlxxDTO = {};
                        bdcBlxxDTO["ptgzldyid"] = processDefKey;
                        bdcBlxxDTO["ygzlslid"] = obj.gzlslid;
                        bdcBlxxDTO["yxmid"] = obj.xmid;
                        bdcBlxxDTO["bdcdyh"] = obj.bdcdyh;
                        csh(bdcBlxxDTO);
                    }
                }, error: function (xhr, status, error) {
                    removeModel();
                    delAjaxErrorMsg(xhr);
                }
            });

        }

        /**
         * 监听工具栏事件，工具栏进入到编辑页面可以对于数据进行操作
         */
        table.on('tool(blTable)', function (obj) {
            var layEvent = obj.event; //获得 lay-event 对应的值
            if (isNullOrEmpty(obj.data.gzlslid)) {
                layer.msg("数据异常，未获取到工作流实例 id");
            } else {
                if (layEvent === 'edit') { //编辑
                    checkEdit(obj.data);
                }
            }
        });
    });

    function csh(bdcBlxxDTO) {
        addModel();
        console.info(bdcBlxxDTO);
        $.ajax({
            type: "POST",
            url: getContextPath() + "/rest/v1.0/blxx/bllc/cshDeleteXxblLc",
            contentType: "application/json;charset=utf-8",
            data: JSON.stringify(bdcBlxxDTO),
            async: true,
            success: function (taskData) {
                if (isNullOrEmpty(taskData)) {
                    removeModel();
                    warnMsg("创建数据失败！");
                } else {
                    parent.window.location.href = "/realestate-portal-ui/view/new-page.html?taskId=" + taskData.taskId;
                }
            },
            error: function (xhr, status, error) {
                removeModel();
                delAjaxErrorMsg(xhr);
            }
        });
    }

    /**
     * 渲染补录数据
     * @param url 地址
     * @param tableId 表格 id
     * @param toolbar 表格工具栏
     */
    function renderblTable(url, tableId, toolbar) {
        addModel();
        table.render({
            elem: tableId,
            // url: url,
            data: [],
            toolbar: false,
            title: '补录任务表格',
            method: 'GET',
            even: true,
            request: {
                limitName: 'size' //每页数据量的参数名，默认：limit
            },
            defaultToolbar: ['filter'],
            limits: commonLimits,
            cols: [[
                {field: 'bdcqzh', templet: "#bdcqzhTpl", minWidth: 100, title: '不动产权证号', event: 'edit'},
                {field: 'ycqzh', minWidth: 100, title: '原产权证号', event: 'check'},
                {field: 'qlrmc', minWidth: 100, title: '权利人'},
                {field: 'gzldymc', minWidth: 100, title: '流程名称'},
                {field: 'zl', minWidth: 200, title: '坐落'},
                {field: 'bdcdyh', templet: '#bdcdyhTpl', minWidth: 270, title: '不动产单元号'},
                {field: 'blsj', title: '补录时间', width: 200, sort: true},
                {field: 'qszt', title: '权属状态', templet: '#qsztTemplate', minWidth: 90, hide: false},
                {field: 'gzlslid', title: '流程ID', width: 90, hide: true},
                {field: 'qszt', title: '限制状态', templet: '#xzqlZtTemplate', minWidth: 90},
            ]],
            parseData: function (res) { //res 即为原始返回的数据
                res.content.forEach(function (v) {
                    if (v.slsj) {
                        var blsj = new Date(v.slsj);
                        v.blsj = format(blsj);
                    }
                });
                return {
                    code: res.code, //解析接口状态
                    msg: res.msg, //解析提示文本
                    count: res.totalElements, //解析数据长度
                    data: res.content //解析数据列表
                }
            },
            page: true,
            done: function () {
                removeModel();
                $('.layui-table-tool-self').css('right', $('.bdc-export-tools').width() + 17 + 'px');
                if ($('.layui-table-body>.layui-table').height() == 0) {
                    $('.layui-table-body .layui-none').html('<img src="../../static/lib/bdcui/images/table-none.png" alt="">无数据');
                }
            }
        });
    }
});

//关闭 panel
function cancelEdit() {
    layui.use(['layer'], function () {
        var layer = layui.layer;
        layer.closeAll();
    });
}