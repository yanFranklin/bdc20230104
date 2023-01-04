layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl'], function () {
    var $ = layui.jquery;
    var element = layui.element;
    var form = layui.form;
    var table = layui.table;
    var laytpl = layui.laytpl;

    var isSearch = true;
    $('.bdc-table-box').on('focus', '.layui-laypage-skip .layui-input', function () {
        isSearch = false;
    }).on('blur', '.layui-laypage-skip .layui-input', function () {
        isSearch = true;
    });
    $(document).keydown(function (event) {
        if (event.keyCode == "13") {
            if (isSearch) {
                $("#queryGzXzYzLw").click();
            }
        }
    });
    //不动产规则限制验证例外的表头
    var unitTableTitle = [
        {type: 'checkbox', fixed: 'left', minWidth: 50},
        {type: 'numbers', title: '序号', width: 70}
        , {
            field: 'SHZT', title: '审核状态',
            templet: function (d) {
                return shzt(d.SHZT);
            },
            minWidth: 100
        }
        , {field: 'GZLWID', title: '规则例外id', hide: true, minWidth: 150}
        , {field: 'SLBH', title: '受理编号', minWidth: 120}
        , {field: 'BDCDYH', title: '不动产单元号', minWidth: 250}
        , {field: 'ZL', title: '坐落', minWidth: 250}
        , {field: 'GZMC', title: '例外规则', minWidth: 200}
        // , {field: 'XMID', title: '限制权利项目ID', width: 230}
        , {field: 'CJRMC', title: '创建人', minWidth: 100}
        , {field: 'CJSJ', title: '创建时间', minWidth: 200}
        , {field: 'LWYY', title: '创建原因', minWidth: 200}
        , {field: 'SHRMC', title: '审核人', minWidth: 100}
        , {field: 'SHSJ', title: '审核时间', minWidth: 200}
        , {field: 'SHYJ', title: '审核原因', minWidth: 200}
    ];

    // 审核状态
    function shzt(shzt) {
        if (shzt != null) {
            if (shzt === 0) {
                return '<span style="color:red">未审核</span>';
            } else if (shzt === 1) {
                return '<span style="color:green">审核通过</span>';
            } else {
                return '不通过';
            }
        } else {
            return "未接入";
        }
    }

    var tableConfig = {
        id: 'lwid',
        method: 'POST',
        toolbar: '#toolbar',
        cols: [unitTableTitle],
        defaultToolbar: ["filter"]
    };

    var url = getContextPath() + "/bdcGzyz/bdcgzlw";

    //查询表单信息
    form.on("submit(queryGzXzYzLw)", function (data) {
        tableReloadNew('lwid', data.field, url);
        return false;
    });

    //加载表格
    loadDataTablbeByUrl('#gzxzyzlwList', tableConfig);
    //表格初始化
    table.init('dataTable', tableConfig)
    tableReloadNew('lwid', null, url);
    // 获取页面表单标识，用于权限控制
    var moduleCode = $.getUrlParam('moduleCode');
    if (moduleCode) {
        setElementAttrByModuleAuthority(moduleCode);
    }
    //头工具栏事件
    table.on('toolbar(dataTable)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id);
        if ('accept' == obj.event) {
            acceptGzLw(checkStatus.data, true);
        }
        if ('refuse' == obj.event) {
            acceptGzLw(checkStatus.data, false);
        }
    });

    /**
     * 通过规则例外
     */
    function acceptGzLw(data, accept) {
        if (!data || data.length == 0) {
            layer.alert("请选择需要审核的记录！", {title: '提示'});
            return;
        }
        for (var i = 0; i < data.length; i++) {
            if (data[i].SHZT != "0"){
                ityzl_SHOW_WARN_LAYER("存在已审核数据，不能重复审核！");
                return;
            }
        }
        var ids = new Array();
        data.forEach(function (selecteddata) {
            var BdcGzlwShDO = {};
            BdcGzlwShDO.GZLWID = selecteddata["GZLWID"];
            BdcGzlwShDO.SLBH = selecteddata["SLBH"];
            ids.push(BdcGzlwShDO);
        });
        // 审核意见
        $("#shyj").val('');
        var reasonTip = accept === true ? '通过原因' : '不通过原因';
        var content = '<div id="sh-yj" class="bdc-layer-textarea">\n' +
            '    <form class="layui-form" action="">\n' +
            '        <div class="layui-form-item pf-form-item">\n' +
            '            <div class="layui-inline" style="width: 100%">\n' +
            '                <label class="layui-form-label"><span class="required-span"><sub style="color: red">*</sub></span>' + reasonTip + '</label>\n' +
            '                <div class="layui-input-inline bdc-end-time-box">\n' +
            '                    <textarea name="shyj" id="shyj" placeholder="请输入内容" style="resize: none" class="layui-textarea"></textarea>\n' +
            '                </div>\n' +
            '            </div>\n' +
            '        </div>\n' +
            '    </form>\n' +
            '</div>';
        var acceptIndex = layer.open({
            title: '审核',
            type: 1,
            area: ['430px'],
            btn: ['确定', '取消'],
            content: content,
            yes: function () {
                var shyj = $("#shyj").val();
                if (isNullOrEmpty(shyj)) {
                    layer.msg('请输入' + (accept === true ? '通过原因！' : '不通过原因！'));
                    return false;
                }
                $.ajax({
                    url: getContextPath() + "/bdcGzyz/gzlwsh",
                    type: "POST",
                    data: {
                        data: JSON.stringify(ids),
                        accept: accept,
                        shyj: shyj
                    },
                    success: function (data) {
                        if (data && $.isNumeric(data) && data > 0) {
                            layer.msg('审核成功！', {time: 2000});
                            $("#queryGzXzYzLw").click();
                        } else {
                            layer.alert("审核失败，请重试！", {title: '提示'});
                        }
                    },
                    error: function (xhr) {
                        delAjaxErrorMsg(xhr, "审核失败，请重试！");
                    }
                });

                layer.close(acceptIndex);
            },
            btn2: function () {
                //取消
            }
        });
    }

});