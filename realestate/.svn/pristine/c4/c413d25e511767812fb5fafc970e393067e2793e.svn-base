/**
 * 案卷查询台账
 */
// 是否生成交接单
var isGenerate = false;
// 选中的数据集合
var dataArr = [];
// 选中的数据对象的ID集合
var idArr = [];
layui.use(['table', 'laytpl', 'laydate', 'layer', 'form'], function () {
    var $ = layui.jquery,
        table = layui.table,
        layer = layui.layer,
        form = layui.form;
    var BASE_URL = '/realestate-register-ui/rest/v1.0';

    var isSearch = true;
    /**
     * 回车键模拟 click 事件
     */
    $('.bdc-table-box').on('focus', '.layui-laypage-skip .layui-input', function () {
        isSearch = false;
    }).on('blur', '.layui-laypage-skip .layui-input', function () {
        isSearch = true;
    });

    $(document).keydown(function (event) {
        if (event.keyCode == "13") {
            if (isSearch) {
                if ($("#search").length > 0) {
                    var smqsr = $("#smqsr").val();
                    if (!isEmptyObject(smqsr)) {
                        if (smqsr.length !== (smqsr.lastIndexOf(",") + 1)) {
                            smqsr += ",";
                        }
                        console.info(smqsr);
                        var slbhs = smqsr.split(",");
                        if (slbhs.length > 1) {
                            var slbh = slbhs[slbhs.length - 2];
                            if (!isEmptyObject(slbh)) {
                                $.ajax({
                                    url: BASE_URL + '/jjd/add?slbh=' + slbh,
                                    type: "GET",
                                    contentType: 'application/json',
                                    dataType: "json",
                                    async: false,
                                    success: function (data) {
                                        if (data == "0") {
                                            $("#search").click();
                                        } else if (data == "1") {
                                            alterMsg("该受理编号" + slbh + "处于未接收状态!");
                                        } else if (data == "2") {
                                            alterMsg("该受理编号" + slbh + "已被创建!");
                                        }
                                    }
                                });
                            }
                        }
                    }
                }
            }
        }
    });


    /**
     * 提示消息
     */
    function alterMsg(msg) {
        var submitIndex = layer.open({
            type: 1,
            title: '提示',
            skin: 'bdc-strong-tips',
            area: ['320px'],
            content: '<p class="bdc-zf-tips-p"><img src="../../static/lib/bdcui/images/info-small.png" alt="">' + msg + '</p>',
            btn: ['确定'],
            btnAlign: 'c',
            closeBtn: 0,
            yes: function () {
                var smqsr = $("#smqsr").val();
                if (!isEmptyObject(smqsr)) {
                    if (smqsr.length !== (smqsr.lastIndexOf(",") + 1)) {
                        smqsr += ",";
                    }
                    let slbhs = smqsr.split(",");
                    var slbh = slbhs[slbhs.length - 2];
                    $("#smqsr").val("");
                    // 去除无法通过验证的受理编号
                    $("#smqsr").val(smqsr.substr(0, smqsr.length - slbh.length - 1));
                }
                layer.close(submitIndex);
            }
        });
    }

    // 设置字符过多，省略样式
    var reverseList = ['zl'];

    /**
     * 加载Table数据列表
     */
    table.render({
        elem: '#ajxxTable',
        id: 'ajxxTable',
        toolbar: '#toolbar',
        title: '案卷信息',
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
            {field: 'xmid', title: 'xmid', hide: true},
            {field: 'gzlslid', title: 'gzlslid', hide: true},
            {field: 'slbh', title: '受理编号', minWidth: 110},
            {field: 'bdcdyh', title: '不动产单元号', minWidth: 250},
            {field: 'bdcqzh', title: '不动产权证号', minWidth: 250},
            {field: 'zl', title: '坐落', minWidth: 250},
            // {field: 'gdrxm', title: '归档人'},
            // {
            //     field: 'gdsj', title: '归档时间',
            //     templet: function (d) {
            //         return format(d.gdsj);
            //     }
            // },
            // {
            //     field: 'gdzt', title: '归档状态',
            //     templet: function (d) {
            //         return isNullOrEmpty(d.gdxxid) ? "未归档" : "已归档";
            //     }
            // },
            {
                field: 'jjzt', title: '交接状态',
                templet: function (d) {
                    return isNullOrEmpty(d.jjdid) ? "未交接" : "已交接";
                }
            }
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
            if (!isEmptyObject($("#smqsr").val())) {
                $("#smqsr").focus();
                var smqsr = $("#smqsr").val();
                $("#smqsr").val("");
                $("#smqsr").val(smqsr);
            }
        }
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

    //主页面头工具栏事件
    table.on('toolbar(ajxxTable)', function (obj) {
        switch (obj.event) {
            case 'addToDataArr':
                if (isGenerate) { // 已经生成了交接单默认重新添加新的交接单所以清空
                    // 还原所有属性
                    dataArr = [];
                    idArr = [];
                    $("#jjdid").val('');
                    $("#jjdh").html('');
                    isGenerate = false;
                }
                addToDataArr();
                break;
            case 'viewData':
                viewData();
                break;
            case 'selectAll':
                selectAll();
                break;
        }
    });

    // 弹框页面头工具栏事件
    table.on('toolbar(viewFilter)', function (obj) {
        switch (obj.event) {
            case 'generatejjd':
                generatejjd(idArr, dataArr);
                break;
            case 'printjjd':
                var jjdid = $("#jjdid").val();
                printjjd(jjdid);
                break;
        }
    });

    //监听弹框页面的行工具事件
    table.on('tool(viewFilter)', function (obj) {
        if (obj.event === 'del') {
            var submitIndex = layer.open({
                type: 1,
                skin: 'bdc-small-tips',
                title: '确认删除',
                area: ['320px'],
                content: '您确定要删除吗？',
                btn: ['确定', '取消'],
                btnAlign: 'c',
                yes: function () {
                    //确定操作
                    obj.del();
                    dataArrDel(obj.data);
                    layer.close(submitIndex);
                },
                btn2: function () {
                    //取消
                }
            });
        }
    });

    /**
     * 行双击编辑
     */
    table.on('rowDouble(ajxxTable)', function (obj) {
        var array = [];
        array.push(obj.data);
    });

    /**
     * 监听排序事件
     */
    table.on('sort(ajxxTable)', function (obj) {
        table.reload('ajxxTable', {
            initSort: obj
            , where: {
                field: obj.field //排序字段
                , order: obj.type //排序方式
            }
        });

    });

    /**
     * 点击查询
     */
    $('#search').on('click', function () {
        // 获取查询内容
        var obj = {};
        $(".search").each(function (i) {
            var value = $(this).val();
            if (!isNullOrEmpty(value)) {
                var name = $(this).attr('name');
                obj[name] = value;
            }
        });
        if (isEmptyObject(obj)) {
            layer.alert("请输入查询条件！", {
                title: '提示'
            });
            return false;
        }
        var smqsr = $("#smqsr").val();
        if (!isEmptyObject(smqsr) && smqsr.length !== (smqsr.lastIndexOf(",") + 1)) {
            smqsr += ",";
        }
        $("#smqsr").val(smqsr);
        // 重新请求
        table.reload("ajxxTable", {
            url: BASE_URL + "/jjd/ajcq"
            , where: obj
            , page: {
                curr: 1  //重新从第 1 页开始
            }
        });
        return false;
    });

    $('#reset').on('click', function () {
        $("#smqsr").val(null);
        $("#slbh").val(null);
        $("#zl").val(null);
    });

    /**
     * 重新加载数据
     */
    window.reload = function () {
        table.reload("ajxxTable", {});
    };

    /**
     * 添加到已选数据
     * @param data
     */
    function addToDataArr() {
        var checkStatus = table.checkStatus('ajxxTable');
        var data = checkStatus.data;
        addToYx(data);
    }

    /**
     * 已选数据查看
     */
    function viewData() {
        if (dataArr.length == 0) {
            warnMsg("请先添加数据！");
            return false;
        }
        layer.open({
            type: 1,
            title: "已添加信息",
            content: $('#popup'),
            area: ['960px', '500px'],
            cancel: function () {
                $("#popup").css('display', 'none');
            },
            success: function (layero, index) {
                viewTableRender();
                var jjdh = $("#jjdh").html();
                if (jjdh != null && jjdh != '') {
                    generatejjdBtnDisabled();
                }
            }
        });
    }

    /**
     * 已选择窗口数据渲染
     */
    function viewTableRender() {
        table.render({
            elem: '#viewTable',
            toolbar: '#toolbarDemo',
            id: 'test1',
            title: '案卷交接清单',
            defaultToolbar: [],
            cols: [[
                {type: 'numbers', fixed: 'left'},
                {field: 'xmid', title: 'xmid', hide: true},
                {field: 'gzlslid', title: 'gzlslid', hide: true},
                {field: 'ajzt', title: 'ajzt', hide: true},
                {field: 'slbh', title: '受理编号', minWidth: 110},
                {field: 'bdcdyh', title: '不动产单元号', minWidth: 250},
                {field: 'bdcqzh', title: '不动产权证号', minWidth: 250},
                {field: 'zl', title: '坐落'},
                {fixed: 'right', title: '操作', toolbar: '#barDemo', width: 80}
            ]],
            data: dataArr,
            page: true,
            done: function () {
                $('.layui-table-tool-self').css('right', $('.bdc-export-tools').width() + 17 + 'px');
                if ($('.bdc-table-box .layui-table-main>.layui-table').height() == 0) {
                    $('.layui-table-body .layui-none').html('<img src="../image/table-none.png" alt="">无数据');
                } else {
                    $('.layui-table-main.layui-table-body').height($('#popup .bdc-table-box').height() - 191);
                }
            }
        });
    }

    /**
     * 根据已知的值删除数组元素
     * @param data
     */
    function dataArrDel(data) {
        $.each(idArr, function (key, val) {
            if (idArr[key] == data.gzlslid) {
                idArr.splice(key, 1);
                dataArr.splice(key, 1);
                $("#dataNum").html('（' + dataArr.length + '）');
                // 删除成功，重新渲染表格
                viewTableRender();
                return false;
            }
        });
    }
});

/**
 * 生成交接单信息
 */
function selectAll() {
    // 获取查询内容
    var obj = {};
    $(".search").each(function (i) {
        var value = $(this).val();
        if (!isNullOrEmpty(value)) {
            var name = $(this).attr('name');
            obj[name] = value;
        }
    });
    if (isEmptyObject(obj)) {
        layer.alert("请输入查询条件！", {
            title: '提示'
        });
        return false;
    }
    var smqsr = $("#smqsr").val();
    if (!isEmptyObject(smqsr) && smqsr.length !== (smqsr.lastIndexOf(",") + 1)) {
        smqsr += ",";
    }
    $("#smqsr").val(smqsr);
    // 重新请求
    $.ajax({
        url: "/realestate-register-ui/rest/v1.0/jjd/ajcq/all",
        type: "GET",
        data: obj,
        contentType: 'application/json',
        dataType: "json",
        async: false,
        success: function (data) {
            addToYx(data);
        }
    });
}

function addToYx(data) {
    var errorSize = 0;
    $.each(data, function (key, val) {
        $.ajax({
            url: "/realestate-register-ui/rest/v1.0/jjd/iscreate?gzlslid=" + val.gzlslid,
            type: "GET",
            contentType: 'application/json',
            dataType: "json",
            async: false,
            success: function (zt) {
                if (zt == 0) {
                    if (idArr.length == 0 || idArr.length > 0 && $.inArray(val.gzlslid, idArr) == -1) {
                        idArr.push(val.gzlslid);
                        dataArr.push(val);
                    }
                }
                // else if (zt == 1) {
                //     if (data.length == 1) {
                //         warnMsg("当前状态不允许添加！");
                //     } else {
                //         errorSize++;
                //     }
                // }
                else {
                    if (data.length == 1) {
                        warnMsg("已经创建，请勿重复添加！");
                    } else {
                        errorSize++;
                    }
                }
            }
        });
    });

    if (errorSize > 0) {
        warnMsg(errorSize + "条无法添加！");
    }
    if (dataArr.length > 0) {
        $("#dataNum").html('（' + dataArr.length + '）');
    } else if (dataArr.length == 0) {
        $("#dataNum").html('');
    }
}

/**
 * 生成交接单信息
 */
function generatejjd(idArr, dataArr) {
    var submitIndex = layer.open({
        type: 1,
        title: '确认提交',
        skin: 'bdc-small-tips',
        area: ['320px'],
        content: '确定当前项目生成交接单？',
        btn: ['确定', '取消'],
        btnAlign: 'c',
        yes: function () {
            //确定操作
            generateAndSavejjdxx(idArr, dataArr);
            layer.close(submitIndex);
        },
        btn2: function () {
            //取消
        }
    });
}

/**
 * 生成并保存交接单信息
 * @param idArr
 * @param dataArr
 */
function generateAndSavejjdxx(idArr, dataArr) {
    var param = {};
    param["listGzlslid"] = idArr;
    $.ajax({
        url: "/realestate-register-ui/rest/v1.0/jjd/jjdxx",
        type: "POST",
        data: JSON.stringify(param),
        contentType: 'application/json',
        dataType: "json",
        async: false,
        success: function (data) {
            if (data && data.jjdh) {
                generatejjdBtnDisabled();
                $("#jjdid").val(data.jjdid);
                $("#jjdh").html(data.jjdh);
                isGenerate = true;
                var submitIndex = layer.open({
                    type: 1,
                    skin: 'bdc-small-tips',
                    title: '确认打印',
                    area: ['320px'],
                    content: '交接单编号已生成，请打印！',
                    btn: ['确定', '取消'],
                    btnAlign: 'c',
                    yes: function () {
                        //确定操作
                        printjjd(data.jjdid);
                        layer.close(submitIndex);
                    },
                    btn2: function () {
                        //取消
                    }
                });
            }
        }
    });
}

/**
 * 打印交接单
 * @param jjdid
 * @returns {boolean}
 */
function printjjd(jjdid) {
    if (jjdid == null || jjdid == '') {
        warnMsg("没有生成交接单编号！");
        return false;
    }
    var dataUrl = getIP() + "/realestate-register-ui/rest/v1.0/print/jjd/" + jjdid + "/xml";
    print(jjdModelUrl, dataUrl, false);
}

/**
 *  设置生成交接单编号的按钮不可用
 */
function generatejjdBtnDisabled() {
    // 设置生成交接单编号的按钮不可用
    $("#generatejjd").attr('disabled', true);
    $("#generatejjd").addClass("bdc-prohibit-btn");
    // 设置预览页面的删除按钮不可用
    $(".layui-table-cell .bdc-delete-btn").attr('disabled', true);
    $(".layui-table-cell .bdc-delete-btn").addClass("bdc-prohibit-btn");
    $(".layui-table-cell .bdc-delete-btn").removeAttr('lay-event');
}