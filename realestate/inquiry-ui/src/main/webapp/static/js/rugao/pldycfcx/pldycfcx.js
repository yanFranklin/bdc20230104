layui.use(['form', 'jquery', 'element', 'table', 'laydate', 'upload'], function () {
    var laydate = layui.laydate;
    var form = layui.form;
    var layer = layui.layer;
    var table = layui.table;
    var upload = layui.upload;

    var currentPageData = new Array();

    var BASE_URL = '/realestate-inquiry-ui/rest/v1.0';
    var zjhs = "";
    var qlrmcs = "";
    var data = [];
    layui.sessionData('checkedData', null);

    /**
     * 加载Table数据列表
     */
    table.render({
        elem: '#pldycfcxTable',
        toolbar: "#toolbar",
        defaultToolbar: ['filter'],
        request: {
            limitName: 'size' //每页数据量的参数名，默认：limit
        },
        cellMinWidth: 80,
        where: {},
        even: true,
        cols: [[
            {type: 'checkbox', fixed: 'left', width: '5%'},
            {
                fixed: 'left', field: '', title: '序号', width: '5%', templet: function (d) {
                    return (d.LAY_INDEX);
                }
            },
            {field: 'bdcqzh', title: '产权证号', width: '20%'},
            {field: 'fwmj', title: '房屋面积', width: '15%'},
            {field: 'tdmj', title: '土地面积', width: '15%'},
            {field: 'zl', title: '坐落', width: '20%'},
            {
                field: 'dyzt', title: '抵押状态', width: '10%',
                templet: function (d) {
                    return formatDyzt(d.dyzt);
                }
            },
            {
                field: 'cfzt', title: '查封状态', width: '10%',
                templet: function (d) {
                    return formatCfzt(d.cfzt);
                }
            }
        ]],
        data: [],
        autoSort: false,
        page: true,
        parseData: function (res) {
            // 设置选中行
            if (res.content && res.content.length > 0) {
                var checkedData = layui.sessionData('checkedData');
                res.content.forEach(function (v) {
                    $.each(checkedData, function (key, value) {
                        if (key == v.xmid) {
                            v.LAY_CHECKED = true;
                        }
                    })
                });
            }

            return {
                code: res.code, //解析接口状态
                msg: res.msg, //解析提示文本
                count: res.totalElements, //解析数据长度
                data: res.content //解析数据列表
            }
        },
        done: function () {
            setHeight();
        }
    });

    function formatDyzt(data) {
        var value = '';
        if (data) {
            value += '<span class="" style="color:red;">抵押 </span>';
        }
        return value;
    }

    function formatCfzt(data) {
        var value = '';
        if (data) {
            value += '<span class="" style="color:red;">查封 </span>';
        }
        return value;
    }

    // 监听复选框事件，缓存选中的行数据
    table.on('checkbox(pldycfcxTable)', function (obj) {
        // 获取选中或者取消的数据
        var data = obj.type == 'one' ? [obj.data] : currentPageData;
        $.each(data, function (i, v) {
            var keyT = v.xmid;
            if (obj.checked) {
                //.增加已选中项
                layui.sessionData('checkedData', {
                    key: keyT, value: v
                });
            } else {
                //.删除
                layui.sessionData('checkedData', {
                    key: keyT, remove: true
                });
            }
        });
    });

    initUploadInst();
    /**
     * 点击查询
     */
    $('#search').on('click', function () {
        // 获取查询内容
        var qlrmc = $('#qlrmc').val();
        var qlrzjh = $('#qlrzjh').val();
        var cxlx = $('#cxlx').val();
        var qszt = $("#qszt").val();
        if ((qlrmc === '' || qlrmc == null) && (qlrzjh === '' || qlrzjh == null)) {
            warnMsg(" 请输入必要查询条件，例如权利人名称");
            return false;
        }
        addModel();
        var obj = {
            "qlrmc": qlrmc,
            "qlrzjh": qlrzjh,
            'cxlx': cxlx,
            "qszt": qszt
        };
        // 重新请求
        table.reload("pldycfcxTable", {
            url: BASE_URL + '/cqcx/dycfcq/page/search'
            , where: obj
            , page: {
                curr: 1,  //重新从第 1 页开始
                limits: [10, 50, 100, 200, 500]
            }
            , done: function (res, curr, count) {
                $("#filterData").hide();
                $("#exportExcel").removeClass("bdc-table-second-btn");
                $("#exportExcel").addClass("bdc-major-btn");

                currentPageData = res.data;
                data = res.data;
                removeModal();
                setHeight();
            }
        });

        return false;
    });

    table.on('toolbar(pldycfcxTable)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id);
        switch (obj.event) {
            case 'exportExcel':
                exportExcel(checkStatus.data, obj.config.cols[0], "checked");
                break;
            case 'import' :
                zjhs = "";
                qlrmcs = "";
                $("#importHide").click();
                break;
            case 'download':
                var url = "/realestate-inquiry-ui/static/js/rugao/model/model.xls";
                window.location.href = url;
                break;
        }
    });

    /**
     * 导出Excel通过隐藏form提交方式，避免ajax方式不支持下载文件
     */
    function exportExcel(d, cols, type) {
        var checkedData = layui.sessionData('checkedData');
        if ($.isEmptyObject(checkedData) && type == "checked") {
            d = data;
        }
        var bdcdyhLits = []
        for (var i in d) {
            bdcdyhLits.push(d[i].bdcdyh)
        }
        var excelData = [];
        var param = {
            "ids":bdcdyhLits
        }
        $.ajax({
            url: BASE_URL + "/cqcx/dycfcq/search",
            type: 'POST',
            async: false,
            dataType: 'json',
            contentType: "application/json",
            data: JSON.stringify(param),
            success: function (data) {
                if(data){
                    excelData = data;
                }
            }, error: function (xhr, status, error) {
                return;
                removeModal();
                delAjaxErrorMsg(xhr)
            }
        });

        layer.open({
            title: '选择导出列',
            type: 1,
            area: ['440px', '300px'],
            btn: ['确认'],
            content: $('#popup')
            , yes: function (index, layero) {
                // 导出列
                var exportCol = [];
                $("input:checkbox[name='col']:checked").each(function (i) {
                    console.log($(this).val());
                    exportCol.push($(this).val())
                });
                if(exportCol.length === 0){
                    exportCol.push("bdcqzh");
                    exportCol.push("fwmj");
                    exportCol.push("tdmj");
                    exportCol.push("zl");
                    exportCol.push("cfxx");
                    exportCol.push("dyxx");
                }
                // 导出数据
                var dataList = [];
                for (var i in excelData) {
                    var data = {
                        "xmid": excelData[i].xmid,
                        "zl": excelData[i].zl,
                        "qszt": excelData[i].qszt,
                        "fwmj": excelData[i].fwmj,
                        "tdmj": excelData[i].tdmj,
                        "cfzt": excelData[i].cfzt,
                        "dyzt": excelData[i].dyzt,
                        "bdcdyh": excelData[i].bdcdyh,
                        "bdcqzh": excelData[i].bdcqzh
                    }
                    if (excelData[i].cfzt && excelData[i].bdcCqCfxxDTO) {
                        data.cfwh = excelData[i].bdcCqCfxxDTO.cfwh;
                        data.cfkssj = excelData[i].bdcCqCfxxDTO.cfkssj;
                        data.cfkssj = excelData[i].bdcCqCfxxDTO.cfkssj;
                    }
                    if (excelData[i].dyzt && excelData[i].bdcCqDyxxDTO) {
                        data.dyje = excelData[i].bdcCqDyxxDTO.dyje;
                        data.dyjssj = excelData[i].bdcCqDyxxDTO.dyjssj;
                        data.dykssj = excelData[i].bdcCqDyxxDTO.dykssj;
                        data.dyzmh = excelData[i].bdcCqDyxxDTO.dyzmh;
                    }
                    dataList.push(data);
                }

                console.log(dataList)
                console.log(exportCol)

                // 标题
                var showColsTitle = [];
                // 列内容
                var showColsField = [];
                // 列宽
                var showColsWidth = [];
                if(exportCol.indexOf("bdcqzh") > -1){
                    showColsTitle.push("产权证号");
                    showColsField.push("bdcqzh");
                    showColsWidth.push(50)
                }
                if(exportCol.indexOf("fwmj") > -1){
                    showColsTitle.push("房屋面积");
                    showColsField.push("fwmj");
                    showColsWidth.push(10)
                }
                if(exportCol.indexOf("tdmj") > -1){
                    showColsTitle.push("土地面积");
                    showColsField.push("tdmj");
                    showColsWidth.push(10)
                }
                if(exportCol.indexOf("zl") > -1){
                    showColsTitle.push("坐落");
                    showColsField.push("zl");
                    showColsWidth.push(50)
                }
                if (exportCol.indexOf("cfxx") > -1){
                    showColsTitle.push("查封文号");
                    showColsTitle.push("查封开始时间");
                    showColsTitle.push("查封结束时间");

                    showColsField.push("cfwh");
                    showColsField.push("cfkssj");
                    showColsField.push("cfjssj");

                    showColsWidth.push(30);
                    showColsWidth.push(30);
                    showColsWidth.push(30);
                }
                if (exportCol.indexOf("dyxx") > -1) {
                    showColsTitle.push("抵押金额");
                    showColsTitle.push("抵押开始时间");
                    showColsTitle.push("抵押结束时间");
                    showColsTitle.push("抵押证明号");

                    showColsField.push("dyje");
                    showColsField.push("dykssj");
                    showColsField.push("dyjssj");
                    showColsField.push("dyzmh");

                    showColsWidth.push(30);
                    showColsWidth.push(30);
                    showColsWidth.push(30);
                    showColsWidth.push(50);
                }
                if(showColsTitle.length <= 0){
                    layer.alert("请选择导出列！", {title: '提示'});
                    return;
                }
                // 设置Excel基本信息
                console.log(showColsTitle);
                console.log(showColsWidth);
                console.log(showColsField);
                console.log(dataList);
                $("#fileName").val('批量抵押查封');
                $("#sheetName").val('统计表');
                $("#cellTitle").val(showColsTitle);
                $("#cellWidth").val(showColsWidth);
                $("#cellKey").val(showColsField);
                $("#data").val(JSON.stringify(dataList));
                $("#form").submit();

                layer.close(index);
            }
            , btn2: function (index, layero) {

            }
            , cancel: function () {

            }
            , success: function () {

            }
        });
    }

    function initUploadInst() {
        uploadInst = upload.render({
            elem: '#importHide' //绑定元素
            , accept: 'file'
            , url: BASE_URL + '/cqcx/import/excel' //上传接口
            , done: function (res) {
                if (res == null || res.length == 0) {
                    layer.alert('导入失败，请重试！', {title: '提示'});
                }
                for (var i = 0; i < res.length; i++) {
                    if(res[i].zjh != null && res[i].zjh != ""){
                        zjhs += res[i].zjh + ",";
                    }
                    if(res[i].qlrmc != null && res[i].qlrmc != ""){
                        qlrmcs += res[i].qlrmc + ",";
                    }
                }
                zjhParams = zjhs.substring(0, zjhs.length - 1);
                qlrParams = qlrmcs.substring(0, qlrmcs.length - 1);
                $("#qlrzjh").val(zjhParams);
                $("#qlrmc").val(qlrParams);
                removeModal();

            }
            , error: function (e) {
                removeModal();
                layer.alert('导入数据解析失败，请检查模板是否正确！', {title: '提示'});
            }
        });

    }

    form.on('checkbox(chooseAll)', function (data) {
        var child = $(".col-box input[type='checkbox']");
        child.each(function (index, item) {
            item.checked = data.elem.checked;
        });
        form.render('checkbox');
    });

    form.on('checkbox(chooseCol)', function (data) {

    });

    form.on('submit(submit)', function (data) {
        $("input:checkbox[name='col']:checked").each(function (i) {
            console.log($(this).val());
            exportCol.push($(this).val())
        });
        return false
    });

    // /**
    //  * 重新加载数据
    //  */
    // window.reload = function () {
    //     table.reload("pldycfcxTable", {
    //         url: BASE_URL + '/pldycfcx/page/search'
    //         , where: obj
    //         , page: {
    //             curr: 1,  //重新从第 1 页开始
    //             limits: [10, 50, 100, 200, 500]
    //         }
    //         , done: function (res, curr, count) {
    //             $("#filterData").hide();
    //             $("#exportExcel").removeClass("bdc-table-second-btn");
    //             $("#exportExcel").addClass("bdc-major-btn");
    //
    //             currentPageData = res.data;
    //             reverseTableCell(reverseList);
    //             removeModal();
    //             setHeight();
    //         }
    //     });
    // };
})