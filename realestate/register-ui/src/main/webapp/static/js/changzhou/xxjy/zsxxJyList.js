/**
 * author: 前端组
 * date: 2018-12-14
 * version 3.0.0
 * describe: 初始化表格，工具栏事件
 */
/**
 * 证书预览的列表所有的数值
 */
layui.config({
    base: '../../static/' //静态资源所在路径
}).extend({response: 'lib/bdcui/js/response'});
var zsylListStr;
var resourceName = "bdcZsList";
// 获取是否是证书预览
var zsyl = "true";
$(function () {
    //绑定回车键
    $(document).keydown(function (event) {
        if (event.keyCode == 13) {
            $("#search").click();
        }
    });
    // 获取参数
    var processInsId = $.getUrlParam('processInsId');
    var gzlslid = processInsId;
    var zsmodel = $.getUrlParam('zsmodel');
    // 表单ID
    var formStateId = $.getUrlParam('formStateId');
    // 获取表单权限参数readOnly
    var readOnly = $.getUrlParam('readOnly');


    var xmid = $.getUrlParam('xmid');
    // 是否历史项目
    var lsxm = $.getUrlParam('lsxm');

    var maxNum = 200;
    // 打印时选择的打印的证书类型（默认证书）
    var zslxChecked = zsModel;

    // 初始化一些参数值
    checkSfyzws(processInsId);
    // 初始化其它按钮权限
    initQtButtonAtrr(processInsId);

    if (!isNullOrEmpty(zsyl) && zsyl == "true") {
        hideSearch();
    }
    //点击高级查询
    $('#seniorSearch').on('click', function () {
        $('.pf-senior-show').toggleClass('bdc-hide');
    });

    layui.use(['form', 'table', 'jquery', 'response'], function () {
        var form = layui.form;
        var table = layui.table;
        var response = layui.response;
        // 当前查询条件
        var searchData;
        // 缮证时间，默认取查询到的第一条信息
        var szsj;
        // 定义table展示数据列
        var colYl = [[
            {checkbox: true, fixed: true},
            {field: 'bdcqzh', title: '不动产权证号', minWidth: 270},
            {field: 'qzysxlh', title: '权证印刷序列号', minWidth: 130},
            {field: 'bdcdyh', title: '不动产单元号', minWidth: 250},
            {field: 'zl', title: '坐落', minWidth: 250},
            {field: 'qlr', title: '权利人', minWidth: 90},
            {field: 'mj', title: '面积', minWidth: 80},
            {field: 'zslxmc', title: '证书类型', minWidth: 80},
            {field: 'zsid', title: '证书ID', hide: true},
            {fixed: 'right', title: '操作', toolbar: '#barDemo'}
        ]];
        var col = [[
            {checkbox: true, fixed: true},
            {
                field: 'dyzt', title: '打印状态', minWidth: 100,
                templet: function (d) {
                    if (d && 1 == d.dyzt) {
                        return '<p style="color:#f54743">已打证</p>';
                    } else {
                        return '<p style="color:#10a54a">未打证</p>';
                    }
                }
            },
            {
                field: 'qszt', title: '状态',
                templet: function (d) {
                    if (d && qsztHistory == d.qszt) {
                        return '<p style="color:#f54743">已注销</p>';
                    } else {
                        return '<p style="color:#10a54a">正常</p>';
                    }
                }
            },
            {
                field: 'bdcqzh', title: '不动产权证', minWidth: 270,
                templet: function (d) {
                    if (isNullOrEmpty(d.bdcqzh)) {
                        verifyBdcqzhNotNull = false;
                        return "";
                    } else {
                        return d.bdcqzh;
                    }
                }
            },
            {field: 'qzysxlh', title: '权证印刷序列号', minWidth: 130},
            {field: 'bdcdyh', title: '不动产单元号', minWidth: 250},
            {field: 'zl', title: '坐落', minWidth: 250},
            {field: 'qlr', title: '权利人', minWidth: 90},
            {field: 'djyy', title: '登记原因', minWidth: 90},
            {field: 'mj', title: '面积', minWidth: 80},
            {field: 'zslxmc', title: '证书类型', minWidth: 90},
            {field: 'zsid', title: '证书ID', hide: true},
            {fixed: 'right', title: '操作', toolbar: '#barDemo'}
        ]];

        // 设置字符过多，省略样式
        var reverseList = ['zl'];
        // 证书预览情况和非证书预览table加载方式分开
        if (!isNullOrEmpty(zsyl) && zsyl == 'true') {
            zsylTableRender();
        }

        //根据选择的数据进行操作时，如果isSelectAll为true，操作包含本页的全部数据
        var isSelectAll = false;
        //监听全选复选鼠标覆盖
        $('.bdc-table-box').on('mouseenter', '.layui-table-header th .laytable-cell-checkbox', function () {
            $('.bdc-select-all-box').removeClass('bdc-hide');
        });
        $('.bdc-select-all-box p').on('click', function (e) {
            e.stopPropagation();
            $('.bdc-select-all-box').addClass('bdc-hide');
            $('.layui-table-header th .layui-form-checkbox').click();

            var checkStatus = table.checkStatus('bdcZsTable');
            if (checkStatus.isAll) {
                $('.layui-table-header th .layui-form-checkbox').addClass('layui-form-checked');
                if ($(this).hasClass('bdc-select-all')) {
                    isSelectAll = true;
                } else {
                    isSelectAll = false;
                }
            } else {
                if ($(this).hasClass('bdc-select-all')) {
                    isSelectAll = false;
                }
            }
        });
        $('.bdc-select-all-box').on('mouseleave', function () {
            $('.bdc-select-all-box').addClass('bdc-hide');
        });
        $('body').on('click', function () {
            $('.bdc-select-all-box').addClass('bdc-hide');
        });

        $('.bdc-table-box').on('mouseenter', 'td', function () {
            if ($(this).text() && reverseList.indexOf($(this).attr("data-field")) != -1) {
                $(this).append('<div class="layui-table-grid-down"><i class="layui-icon layui-icon-down"></i></div>');
            }
            $('.layui-table-grid-down').on('click', function () {
                setTimeout(function () {
                    $('.layui-table-tips-main .bdc-table-date').html(reverseString($('.layui-table-tips-main .bdc-table-date').html()));
                }, 20);
            });
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

        form.on("submit(search)", function (data) {
            searchData = data;
            tableReload("bdcZsTable", data.field);
            return false;
        });
        // 监听单选
        form.on('radio(zslxRadio)', function (data) {
            // console.log(data.elem); //得到radio原始DOM对象
            // console.log(data.value); //被点击的radio的value值
            zslxChecked = data.value;
        });


        //监听行工具事件
        table.on('tool(dataTable)', function (obj) {
            // 点击详情默认选中该行
            $(".bdc-zs-list-table [type='checkbox']").prop("checked", '');
            var index = $(this).parents('tr').attr('data-index');
            $(".bdc-zs-list-table tr[data-index=" + index + "]").find("[type='checkbox']").prop("checked", "checked");
            form.render('checkbox');

            var data = obj.data;
            var url = "";
            var title = '证书详情';
            var layerModel;
            var zsData = JSON.stringify(data);
            sessionStorage.zsjyData = zsData;
            if (!isNullOrEmpty(zsyl) && zsyl == 'true') {
                url = initZsjyUrl(data);
                layerModel = parent.layer;
            } else {
                url = initZsUrl(data);
                layerModel = layer;
            }

            layerModel.open({
                title: [title, 'font-size:16px;font-weight: 700;'],
                type: 2,
                area: ['100%', '100%'],
                resize: true,
                content: url,
                id: 'zsListCs',
                cancel: function (index, layero) {
                    refreshTable();
                    layerModel.close(index);
                    return false;
                }
            });

        });

        // 预览情况下的证书url（此时数据库中未生成数据）
        function initZsjyUrl(data) {

            var url = "/realestate-register-ui/view/xxjy/xxjy.html?processInsId=" + processInsId;
            // 获取和证书相关的所有的xmid拼接的字符串，用于详细页面的操作
            url = appendZsXmids(url, processInsId, data);
            return url;
        }

        // 证书url
        function initZsUrl(data) {
            var url = "/realestate-register-ui/view/xxjy/xxjy.html?processInsId=" + processInsId+"&zsid="+data.zsid;
            return url;
        }

        // 预览情况下的表格渲染
        function zsylTableRender() {
            var zsylList = getZsylDataList(processInsId);
            zsylListStr = JSON.stringify(zsylList);
            sessionStorage.zsylListStr = zsylListStr;
            table.render({
                elem: '#bdcZsTable',
                // toolbar: '#toolbarDemo',
                id: 'bdcZsTable',
                title: '证书列表',
                defaultToolbar: [],
                cols: colYl,
                limits: [10, 20, 50, 100, 200, 500],
                data: zsylList,
                page: true,
                done: function () {
                    readOnly = true;

                    var searchHeight = 80;
                    setTableHeight(searchHeight);

                    reverseTableCell(reverseList);
                }
            });
        }

        // 重新加载当前页的表格数据
        window.refreshTable = function () {
            console.log("重新加载当前页的表格数据");
            // 当前页码
            var page = $('.layui-laypage-curr em:last-child').html();
            // 关闭时自动刷新列表
            if (isEmptyObject(searchData)) {
                tableReload("bdcZsTable", null, page)
            } else {
                tableReload("bdcZsTable", searchData.field, page);
            }
            // 刷新表单后，重新设置按钮属性
            setZsListBtnAttr(processInsId, lsxm, response);
        }
    });

    // 表格重载
    function tableReload(table_id, postData, page) {
        layui.use(['table'], function () {
            var table = layui.table;
            table.reload(table_id, {
                where: postData
                , page: {
                    curr: page,
                    limits: [10, 20, 50, 100, 200, 500],
                }
            });
        });
    }

    //隐藏查询条件
    function hideSearch() {
        $('.bdc-percentage-container').addClass('bdc-percentage-hide-search');
        $('.bdc-search-content').addClass('bdc-hide');
    }
});

