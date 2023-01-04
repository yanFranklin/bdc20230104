/**
 * author: <a href="mailto:zhuyong@gtmap.cn>zhuyong</a>
 * version 1.0.0  2019/01/30
 * describe: 证书印制号模板配置页面处理JS
 */
var version;
layui.use(['table', 'laytpl', 'laydate', 'layer', 'form'], function () {
    var $ = layui.jquery,
        table = layui.table,
        layer = layui.layer,
        form = layui.form;
        laydate = layui.laydate;

    // 当前页数据
    var currentPageData = new Array();

    var start = laydate.render({
        elem: '#startTime',
        type: 'datetime',
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
        elem: '#endTime',
        type: 'datetime',
        done: function (value, date) {
            if ($.trim(value) === '') {
                var curDate = new Date();
                date = {'date': curDate.getDate(), 'month': curDate.getMonth() + 1, 'year': curDate.getFullYear(),'hours': curDate.getHours(),
                    'minutes': curDate.getMinutes(), 'seconds': curDate.getSeconds()};
            }
            start.config.max = date;
            start.config.max.month = date.month - 1;
        }
    });

    var moduleCode = $.getUrlParam('moduleCode');
    $.ajax({
        url:'/realestate-inquiry-ui/rest/v1.0/zsyzh/version',
        type:'GET',
        dataType: "json",
        async: false,
        timeout: 10000,
        success: function (data) {
            if (data.res == 'standard'){
                version = 'standard';
            }else if (data.res==='huaian'){
                version = 'huaian';
            }
        }
    })
    /**
     * 查询区县代码字典
     */
    var xzqhData;
    $.ajax({
        url: '/realestate-inquiry-ui/rest/v1.0/zsbhmb/zd/qxdmbyuser',
        type: "GET",
        dataType: "json",
        async: false,
        timeout: 10000,
        success: function (data) {
            if (data && data.length > 0) {
                xzqhData = data;
                // 渲染字典项
                $.each(data, function (index, item) {
                    $('#qxdm').append('<option value="' + item.XZDM + '">' + item.XZMC + '（' + item.XZDM + '）' + '</option>');
                });

                if(data.length == 1){
                    $('#qxdm').find('option:eq(1)').prop('selected', true).attr('selected', 'selected');
                    $('#qxdm').attr('disabled','disabled')
                 }
            }
            form.render('select');
        }
    });
    /**
     * 获取字典
     */
    var zdData;
    $.ajax({
        url: '/realestate-inquiry-ui/rest/v1.0/zsyzh/zd',
        type: "GET",
        async: false,
        dataType: "json",
        timeout: 10000,
        success: function (data) {
            zdData = data;

            if (data) {
                if (data.zslx) {
                    $.each(data.zslx, function (index, item) {
                        $('#zslx').append('<option value="' + item.DM + '">' + item.MC + '</option>');
                    });
                }

                if (data.zssyqk) {
                    $.each(data.zssyqk, function (index, item) {
                        $('#syqk').append('<option value="' + item.DM + '">' + item.MC + '</option>');
                    });
                }
                form.render('select');

                // 下拉选择添加删除按钮
                renderSelectClose(form);
            }
        }
    });

    /**
     * 加载Table数据列表
     */
    table.render({
        elem: '#zsyzhTable',
        toolbar: '#toolbar',
        title: '证书编号模板列表',
        defaultToolbar: ['filter'],
        url: "/realestate-inquiry-ui/rest/v1.0/zsyzh",
        request: {
            limitName: 'size' //每页数据量的参数名，默认：limit
        },
        cellMinWidth: 80,
        even: true,
        cols: [[
            {type: 'checkbox', fixed: 'left'},
            {minWidth: 80, sort: true, field: 'nf', title: '年份'},
            {
                minWidth: 100, sort: true, field: 'qxdm', title: '行政区划',
                templet: function (d) {
                    if (xzqhData) {
                        for (var index in xzqhData) {
                            if (xzqhData[index].XZDM == d.qxdm) {
                                return xzqhData[index].XZMC;
                            }
                        }
                    } else {
                        return d.qxdm;
                    }
                }
            },
            {
                minWidth: 100, sort: true, field: 'zslx', title: '证书类型',
                templet: function (d) {
                    if (zdData && zdData.zslx) {
                        for (var index in zdData.zslx) {
                            if (zdData.zslx[index].DM == d.zslx) {
                                return zdData.zslx[index].MC;
                            }
                        }
                    } else {
                        return d.zslx;
                    }
                }
            },
            {minWidth: 200, sort: true, field: 'qzysxlh', title: '权证印刷序列号'},
            {
                minWidth: 100, sort: false, field: 'syqk', title: '使用情况',
                templet: function (d) {
                    if (zdData && zdData.zssyqk) {
                        for (var index in zdData.zssyqk) {
                            if (zdData.zssyqk[index].DM == d.syqk) {
                                if(2 == d.syqk){
                                    return '<span style="color: red">' + zdData.zssyqk[index].MC + '</span>';
                                }
                                if(6 == d.syqk){
                                    return '<span style="color: green">' + zdData.zssyqk[index].MC + '</span>';
                                }
                                return zdData.zssyqk[index].MC;
                            }
                        }
                    } else {
                        return d.syqk;
                    }
                }
            },
            // {minWidth:150, sort:false, field:'slbh',  title:'受理编号'},
            {minWidth: 200, sort: true, field: 'lqbm', title: '领取部门'},
            {minWidth: 150, sort: true, field: 'lqr', title: '领取人'},
            // {minWidth:200,   field:'sybmmc',title:'使用部门'},
            // {minWidth:150,   field:'syr',   title:'使用人'},
            {minWidth: 150, sort: false, field: 'cjr', title: '创建人'},
            {
                minWidth: 200, sort: false, field: 'cjsj', title: '创建时间',
                templet: function (d) {
                    return format(d.cjsj);
                }
            },
            {minWidth:200, sort:false, field:'sysj',  title:'使用时间',
                templet: function(d){
                    return format(d.sysj);
                }
            },
            {minWidth: 150, sort: false, templet: '#barAction', title: '操作', fixed: 'right'}
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
            setElementAttrByModuleAuthority(moduleCode);
            $('.layui-table-tool-self').css('right', $('.bdc-export-tools').width() + 17 + 'px');

            if ($('.bdc-table-box .layui-table-main>.layui-table').height() == 0) {
                $('.layui-table-body .layui-none').html('<img src="../../../static/lib/registerui/image/table-none.png" alt="">无数据');
            } else {
                $('.layui-table-body').height($('.bdc-table-box').height() - 131);
                $('.layui-table-fixed .layui-table-body').height($('.bdc-table-box').height() - 131 - 17);
            }
        }
    });

    //头工具栏事件
    table.on('toolbar(zsyzhTable)', function (obj) {
        console.log(obj);
        var checkStatus = table.checkStatus(obj.config.id);
        switch (obj.event) {
            case 'add':
                addZsyzh();
                break;
            case 'addHuaian':
                addHuaian();
                break;
            case 'extract':
                extract(checkStatus.data);
                break;
            case 'getYzh':
                getYzh(checkStatus.data);
                break;
            case 'revocation':
                revocation();
                break;
            case 'edit':
                editZsyzh(checkStatus.data);
                break;
            case 'delete':
                deleteZsyzh(checkStatus.data);
                break;
            case 'example':
                showExample();
                break;
            case 'exportExcel':
                exportExcel(checkStatus.data, obj.config.cols[0]);
                break;
        }
        ;
    });

    function addHuaian() {
        layer.open({
            type: 2,
            title: '新增',
            anim: -1,
            shadeClose: true,
            maxmin: true,
            shade: false,
            area: ['960px','400px'],
            offset: 'auto',
            content: ["/realestate-inquiry-ui/huaian/yzh/addZsyzh.html",'yes'],
            end: function () {
                table.reload('zsyzhTable')
            }
        });
    }
    /**
     * 提取业务
     * @param data
     */
    function extract(data){
        layer.open({
            type: 2,
            title: '提取至部门',
            anim: -1,
            shadeClose: true,
            maxmin: true,
            shade: false,
            area: ['960px','400px'],
            offset: 'auto',
            content: ["/realestate-inquiry-ui/huaian/yzh/extractData.html",'yes'],
            end: function () {
                table.reload('zsyzhTable')
            }
        });
    }

    function getYzh(data){
        layer.open({
            type: 2,
            title: '领取至人',
            anim: -1,
            shadeClose: true,
            maxmin: true,
            shade: false,
            area: ['960px','400px'],
            offset: 'auto',
            content: ["/realestate-inquiry-ui/huaian/yzh/getYzh.html",'yes'],
            end: function () {
                table.reload('zsyzhTable')
            }
        });
    }

    function revocation(){
        layer.open({
            type: 2,
            title: '撤销',
            anim: -1,
            shadeClose: true,
            maxmin: true,
            shade: false,
            area: ['960px','400px'],
            offset: 'auto',
            content: ["/realestate-inquiry-ui/huaian/yzh/revocation.html",'yes'],
            end: function () {
                table.reload('zsyzhTable')
            }
        });
    }

    //监听工具条
    table.on('tool(zsyzhTable)', function (obj) {
        var data = obj.data;
        if (obj.event === 'del') {
            delZsyzh(data);
        } else if (obj.event === 'symx') {
            viewSymxData(data.yzhid);
        }else if (obj.event === 'revert') {
            revertZsyzh(data);
        }
    });

    /**
     * 行双击编辑
     */
    table.on('rowDouble(zsyzhTable)', function (obj) {
        var array = new Array();
        array.push(obj.data);
        editZsyzh(array);
    });

    /**
     * 监听排序事件
     */
    table.on('sort(zsyzhTable)', function (obj) {
        table.reload('zsyzhTable', {
            initSort: obj
            , where: {
                field: obj.field //排序字段
                , order: obj.type //排序方式
            }
        });

    });

    /**
     * 已选数据查看
     */
    function viewSymxData(yzhid) {
        addModel();
        $.ajax({
            url: "/realestate-inquiry-ui/rest/v1.0/zsyzh/" + yzhid + "/symx",
            type: "GET",
            contentType: 'application/json',
            dataType: "json",
            success: function (data) {
                if (data) {
                    layer.open({
                        type: 1,
                        title: "印制号使用明细",
                        content: $('#popup'),
                        area: ['985px', '500px'],
                        cancel: function () {
                            $("#popup").css('display', 'none');
                        },
                        success: function (layero, index) {
                            viewTableRender(data);
                        }
                    });
                }
            }, error: function () {
                alertMsg("查询异常！");
            }, complete: function () {
                removeModal();
            }
        });
    }

    /**
     * 印制号使用明细
     */
    function viewTableRender(symxData) {
        table.render({
            elem: '#viewTable',
            toolbar: '#toolbarDemo',
            id: 'test1',
            title: '档案移交清单',
            defaultToolbar: [],
            cols: [[
                {type: 'numbers', fixed: 'left', width: 60},
                {field: 'slbh', title: '受理编号', width: 112},
                {field: 'sybmmc', title: '使用部门名称', width: 130},
                {field: 'syr', title: '使用人', width: 110},
                {field: 'sysj', title: '使用时间', width: 200},
                {field: 'syyy', title: '使用原因', width: 250},
                {
                    field: 'syqk', title: '使用情况', width: 95,
                    templet: function (d) {
                        if (zdData && zdData.zssyqk) {
                            for (var index in zdData.zssyqk) {
                                if (zdData.zssyqk[index].DM == d.syqk) {
                                    if (2 == d.syqk) {
                                        return '<span style="color: red">' + zdData.zssyqk[index].MC + '</span>';
                                    } else {
                                        return zdData.zssyqk[index].MC;
                                    }
                                }
                            }
                        } else {
                            return d.syqk;
                        }
                    }
                }
            ]],
            data: symxData,
            page: false,
            done: function () {
                $('.layui-table-tool-self').css('right', $('.bdc-export-tools').width() + 17 + 'px');
            }
        });
    }

    /**
     * 新增证书印制号
     */
    function addZsyzh() {
        layer.open({
            type: 2,
            title: '新增证书印制号',
            anim: -1,
            shadeClose: true,
            maxmin: true,
            shade: false,
            area: ['960px', '400px'],
            offset: 'auto',
            content: ["addZsyzh.html", 'yes'],
            end: function () {
                table.reload('zsyzhTable');
            }
        });
    }

    /**
     * 编辑证书印制号
     */
    function editZsyzh(data) {
        if (!data || data.length != 1) {
            alertMsg("请选择需要编辑的某一条记录！");
            return;
        }

        layer.open({
            type: 2,
            title: '编辑证书印制号',
            anim: -1,
            shadeClose: true,
            maxmin: true,
            shade: false,
            area: ['960px', '400px'],
            offset: 'auto',
            content: ["editZsyzh.html", 'yes'],
            success: function (layero, index) {
                var iframe = window['layui-layer-iframe' + index];
                iframe.setData(data[0]);
            },
            end: function () {
                table.reload('zsyzhTable');
            }
        });
    }

    /**
     * 显示示例
     */
    function showExample() {
        layer.open({
            title: '印制号示例',
            type: 2,
            area: ['960px', '500px'],
            content: 'zsyzhsl.html'
        });
    }

    /**
     * 删除证书印制号
     */
    function deleteZsyzh(data) {
        if (!data || data.length == 0) {
            alertMsg("请选择需要删除的记录！");
            return;
        }
        for(var i=0;i<data.length;i++){
            if(data[i].syqk == 1 || data[i].syqk == 2 || data[i].syqk == 3
                || data[i].syqk == 4 || data[i].syqk == 5 || data[i].syqk == 99){
                alertMsg("有不能删除的印制号，请检查！");
                return;
            }
        }

        var deleteIndex = layer.open({
            type: 1,
            title: '确认删除',
            area: ['320px'],
            skin: 'bdc-small-tips',
            content: '确定要删除所选证书印制号？',
            btn: ['确定', '取消'],
            btnAlign: 'c',
            yes: function () {
                $.ajax({
                    url: "/realestate-inquiry-ui/rest/v1.0/zsyzh",
                    type: "DELETE",
                    data: JSON.stringify(data),
                    contentType: 'application/json',
                    dataType: "json",
                    success: function (data) {
                        if (data && $.isNumeric(data) && data > 0) {
                            delSuccessMsg();
                            reload();
                        } else {
                            delFailMsg();
                        }
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
     * 作废证书印制号
     */
    function delZsyzh(data) {
        if (!data) {
            alertMsg("请选择需要作废的记录！");
            return;
        }

        if (data.syqk == 2) {
            alertMsg("当前印制号已经作废！");
            return;
        }

        if (data.syqk == 3 || data.zsid ) {
            alertMsg("当前印制号已使用，无法作废！");
            return;
        }

        var zfyzhIndex = layer.open({
            title: '作废印制号',
            type: 2,
            area: ['430px', '220px'],
            content: 'delZsyzh.html',
            success: function (layero, index) {
                var iframe = window['layui-layer-iframe' + index];
                iframe.setData(data);
            },
            end: function () {
                table.reload('zsyzhTable');
            }
        });
    }

    /**
     * 作废证书印制号
     */
    function revertZsyzh(data) {

        if (!data) {
            alertMsg("请选择需要还原的记录！");
            return;
        }

        if (data.syqk != 2) {
            alertMsg("请选择作废状态的印制号！");
            return;
        }
        addModel();
        $.ajax({
            url: "/realestate-inquiry-ui/rest/v1.0/zsyzh/hy",
            type: "POST",
            data: JSON.stringify(data),
            contentType: 'application/json',
            dataType: "json",
            success: function (data) {
                removeModal();
                successMsg("还原成功,请刷新页面");
                //$('#search').click();
            },
            error:function($xhr,textStatus,errorThrown){
                saveFailMsg();
            }
        });


    }

    /**
     * 点击查询
     */
    $('#search').on('click', function () {
        //这里处理下因为下拉树框数据清除但是隐藏ID值未清理问题
        if (isNullOrEmpty($("input[name='lqbm']").val())) {
            $("input[name='lqbmid']").val(null);
        }
        if (isNullOrEmpty($("input[name='lqr']").val())) {
            $("input[name='lqrid']").val(null);
        }

        // 获取查询内容
        var obj = {};
        var count = 0;
        $(".search").each(function (i) {
            var value = $(this).val();
            if (!isNullOrEmpty(value)) {
                count += 1;
            }
            var name = $(this).attr('name');
            obj[name] = value;
        });

        if (0 == count) {
            alertMsg("请输入查询条件！");
            return false;
        }

        // 重新请求
        table.reload("zsyzhTable", {
            where: obj
            , page: {
                curr: 1  //重新从第 1 页开始
            }
        });
    });

    //点击高级查询--4的倍数
    $('#seniorSearch').on('click', function () {
        $('.pf-senior-show').toggleClass('bdc-hide');
        $(this).parent().toggleClass('bdc-button-box-four');

        if ($(this).html() == '高级查询') {
            $(this).html('收起');
        } else {
            $(this).html('高级查询');
        }

        $('.bdc-percentage-container').css('padding-top', $('.bdc-search-content').height() + 10);
        $('.layui-table-body').height($('.bdc-table-box').height() - 129);
        $('.layui-table-fixed .layui-table-body').height($('.bdc-table-box').height() - 129 - 17);
    });

    /**
     * 重置
     */
    $('#reset').on('click', function () {
        $("input[name='lqbm']").val(null);
        table.reload("zsyzhTable", {
            where: []
            , page: {
                curr: 1  //重新从第 1 页开始
            }
        });
    });

    /**
     * 重新加载数据
     */
    window.reload = function () {
        // 获取查询内容
        var obj = {};
        $(".search").each(function (i) {
            var value = $(this).val();
            var name = $(this).attr('name');
            obj[name] = value;
        });

        table.reload("zsyzhTable", {
            where: obj
            , page: {
                curr: 1  //重新从第 1 页开始
            }
        });
    };

// 监听复选框事件，缓存选中的行数据
    table.on('checkbox(zsyzhTable)', function (obj) {
        // 获取选中或者取消的数据
        var data = obj.type == 'one' ? [obj.data] : layui.table.cache['zsyzhTable'];
        $.each(data, function (i, v) {
            if (obj.checked) {
                //.增加已选中项
                layui.sessionData('checkedData', {
                    key: v.qzysxlh, value: v
                });
            } else {
                //.删除
                layui.sessionData('checkedData', {
                    key: v.qzysxlh, remove: true
                });
            }
        });
    });

    /**
     * 已选数据查询
     */
    function findSymxData(yzhidList) {
        var symxData = [];
        $.ajax({
            async:false,
            url: "/realestate-inquiry-ui/rest/v1.0/zsyzh/listSymx",
            type: "POST",
            contentType: 'application/json',
            data: JSON.stringify(yzhidList),
            dataType: "json",
            success: function (data) {
                if (data.length > 0) {
                    for (var index in data) {
                        symxData.push(data[index]);
                    }
                }
            }, error: function () {
                alertMsg("查询异常！");
            }
        });
        return symxData;
    }

    /**
     * 导出Excel
     * 通过隐藏form提交方式，避免ajax方式不支持下载文件
     */
    function exportExcel(d, cols) {
        var checkedData = layui.sessionData('checkedData');
        if ($.isEmptyObject(checkedData)) {
            warnMsg(" 请选择需要导出Excel的数据行！");
            return;
        }

        // 标题
        var showColsTitle = new Array();
        // 列内容
        var showColsField = new Array();
        // 列宽
        var showColsWidth = new Array();

        showColsTitle.push(['领取部门','领取人','证书类型','证书编号','使用情况','使用时间','使用原因']);
        showColsField.push(['lqbm','lqr','zslx','qzysxlh','syqk','sysj','syyy']);
        showColsWidth.push([30,20,10,20,10,20,20]);

        // 设置Excel基本信息
        $("#fileName").val('不动产登记印制号管理查询信息结果导出Excel表');
        $("#sheetName").val('统计表');
        $("#cellTitle").val(showColsTitle);
        $("#cellWidth").val(showColsWidth);
        $("#cellKey").val(showColsField);

        var data = new Array();
        var listSymx = [];

        $.each(checkedData, function (key, value) {
            listSymx.push(value.yzhid)
        })

        var findSymx = findSymxData(listSymx);

        $.each(findSymx, function (key, value) {
            if (zdData && zdData.zslx) {
                for (var index in zdData.zslx) {
                    if (zdData.zslx[index].DM == value.zslx) {
                        value.zslx = zdData.zslx[index].MC;
                    }
                }
            }
            if (zdData && zdData.zssyqk) {
                for (var index in zdData.zssyqk) {
                    if (zdData.zssyqk[index].DM == value.syqk) {
                        value.syqk = zdData.zssyqk[index].MC;
                    }
                }
            }
            data.push(value);
        });


        $("#data").val(JSON.stringify(data));
        $("#form").submit();
    }

});