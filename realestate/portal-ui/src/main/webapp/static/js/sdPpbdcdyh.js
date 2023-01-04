layui.use(['jquery', 'table', 'element', 'form', 'laytpl', 'laydate', 'layer','response'], function () {
    var $ = layui.jquery,
        table = layui.table,
        layer = layui.layer,
        laydate = layui.laydate,
        response = layui.response;
    var dzwtzm;
    var xnbdcdyh;
    $(function () {
        var $paramArr =getIpHz();
        if ($paramArr['xnbdcdyh'] && $paramArr['xnbdcdyh'].length==28) {
            dzwtzm = $paramArr['xnbdcdyh'].slice(19,20);
            xnbdcdyh=$paramArr['xnbdcdyh'];
        }
        //监听台账查询 input 点击
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

        // 渲染表格
        var waitUrl = getContextPath() + "/rest/v1.0/task/listBdcdyhByPageJson";
        var waitTableId = '#waitTable';
        var waitCurrentId = 'dbTable';
        var waitToolbar = '#toolbarDemo';
        renderWaitTable(waitUrl, waitTableId, waitCurrentId, waitToolbar);

        //查询
        $('#dbSearch').on('click', function () {
            addModel();
            var obj={};
            obj["dzwtzm"] = dzwtzm;
            // 获取查询内容
            $(".dbSearch").each(function () {
                var value = $(this).val();
                var name = $(this).attr('name');
                obj[name] = value;
            });
            // 重新请求
            table.reload("dbTable", {
                where: obj,
                page: {
                    curr: 1  //重新从第 1 页开始
                },
                done: function(){
                    tableDone();
                }
            });
        });

        /**
         * 复制反转的 坐落
         */
        $('.bdc-table-box').on('mouseenter', 'td', function () {
            if ($(this).text() && $(this).attr("data-field") == "zl") {
                $(this).append('<div class="layui-table-grid-down"><i class="layui-icon layui-icon-down"></i></div>')
            }
            $('.layui-table-grid-down').on('click', function () {
                setTimeout(function () {
                    if (!isNullOrEmpty($('.layui-table-tips-main .bdc-table-date').html())) {
                        $('.layui-table-tips-main .bdc-table-date').html(reverseString($('.layui-table-tips-main .bdc-table-date').html()));
                    }
                }, 20);
            });
        });


        table.on('tool(waitTableFilter)', function (obj) { //bdcdyhList为table的lay-filter对应的值
            if (obj.event === 'ppbdcdy') {
                //得到当前行数据
                if(obj.data.bdcdyh && xnbdcdyh){
                    addModel("手动匹配中");
                    $.ajax({
                        url: getContextPath() + "/rest/v1.0/task/matchClSdData",
                        type: 'POST',
                        dataType: 'json',
                        traditional: true,
                        data: {bdcdyh: obj.data.bdcdyh,xnbdcdyh:xnbdcdyh},
                        success: function () {
                            removeModal();
                            layer.msg("匹配成功！");
                            parent.renderTable();
                            closeWin();
                        }, error: function (e) {
                            removeModal();
                            response.fail(e, '');
                        }
                    });
                }
            }
        });
    });


    /**
     * 表格加载完成后渲染
     */
    function tableDone() {
        removeModal();
        $('.layui-table-tool-self').css('right', $('.bdc-export-tools').width() + 17 + 'px');

        $('.layui-table-tool-self').css('right', $('.bdc-export-tools').width() + 17 + 'px');
        if ($('.layui-table-body>.layui-table').height() == 0) {
            $('.layui-table-body .layui-none').html('<img src="../static/lib/bdcui/images/table-none.png" alt="">无数据');
        }
        var reverseList = ['zl'];
        reverseTableCell(reverseList);
    }

    //渲染表格
    function renderWaitTable(url, tableId, currentId, toolbar) {
        addModel();
        var obj={};
        obj["dzwtzm"] = dzwtzm;
        obj["dyhcxlx"] = 1;
        table.render({
            elem: tableId,
            id: currentId,
            url: url,
            toolbar: toolbar,
            title: '匹配不动产单元号',
            method: 'post',
            even: true,
            request: {
                limitName: 'size'
            },
            where:obj,
            limits: [10,30,50,70,90,110,130,150],
            defaultToolbar: ['filter'],
            cols: [[
                {field: 'bdcdyh', title: '不动产单元号', minWidth: 300,templet: '#bdcdyhTpl', event: 'ppbdcdy'},
                {field: 'zl', title: '坐落', minWidth: 250},
                {field: 'qlr', title: '权利人', minWidth: 124},
                {field: 'fjh', title: '房间号', minWidth: 70, hide: true}
            ]],
            parseData: function (res) { //res 即为原始返回的数据
                return {
                    "code": res.code, //解析接口状态
                    "msg": res.message, //解析提示文本
                    "count": res.totalElements, //解析数据长度
                    "data": res.content //解析数据列表
                };
            },
            page: true,
            done: function () {
                tableDone();
            }
        });
    }
});
