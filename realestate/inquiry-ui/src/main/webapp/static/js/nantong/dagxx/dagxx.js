var gzlslid = getQueryString('gzlslid');
var dataUrl = getContextPath() + '/rest/v1.0/dag/list?gzlslid=' + gzlslid;
layui.use(['layer', 'table', 'jquery', 'form'], function () {
    var layer = layui.layer;
    var $ = layui.jquery;
    var table = layui.table;
    var form = layui.form;

    $(function () {
        // table配置对象
        var tableConfigs = {
            elem: '#pageTable',
            defaultToolbar: ['filter'],
            even: true,
            toolbar: '#toolbarDemo',
            url: dataUrl,
            cols: [[
                {field: 'slbh', title: '受理编号', minWidth: 100, sort: true, fixed: 'left'}
                , {field: 'rgwz', title: '入柜位置', minWidth: 100}
                , {field: 'rgsj', title: '入柜时间', minWidth: 100, sort: true}
                , {
                    field: 'dazt', title: '档案状态', minWidth: 80, templet: function (d) {
                        if (d.dazt == 1) {
                            return "入柜";
                        } else if (d.dazt == 2) {
                            return "出柜";
                        }
                    }
                }
                , {field: 'cgsj', title: '出柜时间', minWidth: 100, sort: true}
            ]],
            parseData: function (res) {
                return {
                    "data": res.content,
                    "code": res.code
                }
            },
            request: {
                limitName: 'size' //每页数据量的参数名，默认：limit
                , loadTotal: true
            }
        };

        // 无数据table配置对象
        var defaultTableConfigs = {
            elem: '#pageTable',
            defaultToolbar: ['filter'],
            even: true,
            toolbar: '#toolbarDemo',
            data: [],
            cols: [[
                {field: 'slbh', title: '受理编号', minWidth: 100, sort: true, fixed: 'left'}
                , {field: 'rgwz', title: '入柜位置', minWidth: 100}
                , {field: 'rgsj', title: '入柜时间', minWidth: 100, sort: true}
                , {field: 'dazt', title: '档案状态', minWidth: 80}
                , {field: 'cgsj', title: '出柜时间', minWidth: 100, sort: true}
            ]],
            parseData: function (res) {
                return {
                    "data": res.content,
                    "code": res.code
                }
            },
            request: {
                limitName: 'size'
                , loadTotal: true
            }
        };

        // 自动根据gzlslid查询
        if (!isNullOrEmpty(gzlslid)) {
            table.render(tableConfigs);
        } else {
            // 无数据时渲染空table
            table.render(defaultTableConfigs);
        }

    });

    // 点击查询，根据受理编号查询
    $('#search').click(function() {
        var slbh = $('#slbh').val();
        if (!isNullOrEmpty(slbh)) {
            var queryUrl =getContextPath() + '/rest/v1.0/dag/list?slbh=' + slbh;
            var configs = {
                elem: '#pageTable',
                defaultToolbar: ['filter'],
                even: true,
                toolbar: '#toolbarDemo',
                url: queryUrl,
                cols: [[
                    {field: 'slbh', title: '受理编号', minWidth: 100, sort: true, fixed: 'left'}
                    , {field: 'rgwz', title: '入柜位置', minWidth: 100}
                    , {field: 'rgsj', title: '入柜时间', minWidth: 100, sort: true}
                    , {
                        field: 'dazt', title: '档案状态', minWidth: 80, templet: function (d) {
                            if (d.dazt == 1) {
                                return "入柜";
                            } else if (d.dazt == 2) {
                                return "出柜";
                            }
                        }
                    }
                    , {field: 'cgsj', title: '出柜时间', minWidth: 100, sort: true}
                ]],
                parseData: function (res) {
                    return {
                        "data": res.content,
                        "code": res.code
                    }
                },
                request: {
                    limitName: 'size' //每页数据量的参数名，默认：limit
                    , loadTotal: true
                }
            };
            table.render(configs);
        } else {
            warnMsg('请输入受理编号');
        }
        return false;
    });

});
