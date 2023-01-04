/**
 * 常住人口信息查询
 */
layui.use(['form', 'jquery', 'laydate', 'element', 'table'], function () {
    var $ = layui.jquery,
        table = layui.table,
        layer = layui.layer,
        laydate = layui.laydate,
        form = layui.form;

    // 常住人口信息
    var czrkxxData = [];
    $(function () {
        // 表格信息初始化
        table.render({
            elem: '#czrkxxcxTable',
            toolbar: '#toolbarDemo',
            title: '常住人口信息查询',
            defaultToolbar: ['filter'],
            even: true,
            cols: [[
                {type: 'checkbox', width: 50, fixed: 'left'},
                {field: 'XM', title: '姓名', width: 120},
                {field: 'GMSFHM', title: '公民身份号码', width: 180},
                // {field: 'jgssxq', title: '籍贯省市县（区）', width: 150},
                {field: 'LXDH', title: '联系电话', width: 180},
                {field: 'YHZGX', title: '与户主关系', width: 120},
                {field: 'HH', title: '户号', width: 120},
                {field: 'MQ_XM', title: '母亲姓名', width: 120},
                {field: 'FQ_XM', title: '父亲姓名', width: 120},
                // {field: 'hyzk', title: '婚姻状况', minWidth: 120},
                {field: 'CYM', title: '曾用名', width: 120},
                {field: 'HJDZXZ', title: '户籍地址', minWidth: 200},
                {field: 'FQ_GMSFHM', title: '父亲公民身份号码', width: 180},
                {field: 'MQ_GMSFHM', title: '母亲公民身份号码', width: 180},
            ]],
            data: [],
            page: true,
            done: function () {
                setHeight();
            }
        });

        // 监听表格操作栏按钮
        table.on('toolbar(czrkxxcxTable)', function(obj){
            var checkStatus = table.checkStatus(obj.config.id);
            switch(obj.event){
                case 'export':
                    exportExcel(checkStatus.data, obj.config.cols[0], "checked");

                    break;
                case 'print':
                    printData(checkStatus.data);
                    saveQueryLogToDataBase(getSearchParam(), czrkxxData, "print-czrkxxcx");
                    break;
            }
        });


        // 点击查询
        $('#search').on('click', function () {
            search();
        });


        // 查询事件
        function search() {
            var obj = getSearchParam();

            if((isNullOrEmpty(obj.xm)|| (isNullOrEmpty(obj.hjdzxz) && isNullOrEmpty(obj.gmsfhm)))  && isNullOrEmpty(obj.hh)){
                warnMsg("请输入必要查询条件，输入户号或输入姓名和坐落进行查询");
                return false;
            }
            if(isNotBlank(obj.gmsfhm) && isNotBlank(obj.hjdzxz)){
                warnMsg("身份证号和坐落不能同时输入进行查询");
                return false;
            }

            if(isNotBlank(obj.xm) && isNotBlank(obj.hjdzxz) && isNotBlank(obj.hh)){
                // 1、数据为：姓名、坐落、户号时，通过姓名+坐落调用人口信息查询接口。 通过户号 调用人口户号查询接口
                czrkxxcx(obj).done(function(czrkxx){
                    czrkxxhhcx(obj).done(function(czrkxxhh){
                        var data = [];
                        if(isNotBlank(czrkxxhh)&& czrkxxhh.length> 0){
                            $.each(czrkxxhh, function(index, item){
                                $.each(czrkxx, function(index, val){
                                    if(val.GMSFHM == item.GMSFHM){
                                        data.push(val);
                                    }
                                });
                            });
                        }
                        reloadTable(data);
                        saveQueryLogToDataBase(obj, czrkxx, "query-czrkxxcx");
                    });
                });
                return;
            }
            if(isNotBlank(obj.xm) && isNotBlank(obj.gmsfhm) && isNotBlank(obj.hh)){
                // 2、数据为：姓名、身份证、户号时，通过姓名+坐落调用人口信息查询接口。 通过户号 调用人口户号查询接口
                czrkgrxxcx(obj).done(function(czrkxx){
                    czrkxxhhcx(obj).done(function(czrkxxhh){
                        var data = [];
                        if(isNotBlank(czrkxxhh)&& czrkxxhh.length> 0){
                            $.each(czrkxxhh, function(index, item){
                                $.each(czrkxx, function(index, val){
                                    if(val.GMSFHM == item.GMSFHM){
                                        data.push(val);
                                    }
                                });
                            });
                        }
                        reloadTable(data);
                        saveQueryLogToDataBase(obj, czrkxx, "query-czrkxxcx");
                    });
                });
                return;
            }
            if(isNotBlank(obj.xm) && isNotBlank(obj.hjdzxz)){
                // 3、数据为：姓名、坐落时，通过姓名+坐落调用人口信息查询接口
                czrkxxcx(obj).done(function(czrkxx){
                    reloadTable(czrkxx);
                    saveQueryLogToDataBase(obj, czrkxx, "query-czrkxxcx");
                });
                return;
            }
            if(isNotBlank(obj.xm) && isNotBlank(obj.gmsfhm)){
                // 4、数据为：姓名、身份证时，通过姓名+身份证号调用人口信息查询接口
                czrkgrxxcx(obj).done(function(czrkxx){
                    reloadTable(czrkxx);
                    saveQueryLogToDataBase(obj, czrkxx, "query-czrkxxcx");
                });
                return;
            }
            if(isNotBlank(obj.hh)){
                //  5、数据为：户号时，通过户号 调用人口户号查询接口
                czrkxxhhcx(obj).done(function(czrkxxhh){
                    reloadTable(czrkxxhh);
                    saveQueryLogToDataBase(obj, czrkxxhh, "query-czrkxxcx");
                });
                return;
            }
        }
    });

    function getSearchParam(){
        var obj = {};
        $(".search").each(function (i) {
            var value = $(this).val();
            var name = $(this).attr('name');
            if(!isNullOrEmpty(value)){
                obj[name] = value;
            }
        });
        return obj;
    }

    function reloadTable(data){
        addModel();
        table.reload("czrkxxcxTable", {
            data: data,
            done: function () {
                removeModal();
                setHeight();
            }
        });
    }

    // 通过姓名+坐落调用人口信息查询接口
    function czrkxxcx(param){
        var deferred = $.Deferred();
        addModel();
        // 查询人口信息
        $.ajax({
            url: "/realestate-inquiry-ui/rest/v1.0/gx/gxjkCx/czrkxx",
            type: 'POST',
            dataType: 'json',
            contentType: "application/json;charset=UTF-8",
            data: JSON.stringify({
                "XM" : param.xm,
                "HJDZXZ" : param.hjdzxz
            }),
            success: function (res) {
                removeModal();
                if(res.code == "200" && res.data.length > 0){
                    deferred.resolve(res.data);
                }else{
                    deferred.resolve([]);
                }
            },
            error: function (xhr, status, error) {
                removeModal();
                deferred.reject();
                delAjaxErrorMsg(xhr);
            }
        });
        return deferred;
    }

    // 通过姓名+坐落调用常住人口个人信息查询接口
    function czrkgrxxcx(param){
        var deferred = $.Deferred();
        addModel();
        // 查询人口信息
        $.ajax({
            url: "/realestate-inquiry-ui/rest/v1.0/gx/gxjkCx/czrkgrxx",
            type: 'POST',
            dataType: 'json',
            contentType: "application/json;charset=UTF-8",
            data: JSON.stringify({
                "XM" : param.xm,
                "GMSFHM" : param.gmsfhm
            }),
            success: function (res) {
                removeModal();
                if(res.code == "200" && res.data.length > 0){
                    deferred.resolve(res.data);
                }else{
                    deferred.resolve([]);
                }
            },
            error: function (xhr, status, error) {
                removeModal();
                deferred.reject();
                delAjaxErrorMsg(xhr);
            }
        });
        return deferred;
    }
    // 通过户号 调用人口户号查询接口
    function czrkxxhhcx(param){
        var deferred = $.Deferred();
        addModel();
        // 根据户号查询家庭信息
        $.ajax({
            url: "/realestate-inquiry-ui/rest/v1.0/gx/gxjkCx/czrkxxhh",
            type: 'POST',
            dataType: 'json',
            contentType: "application/json;charset=UTF-8",
            data: JSON.stringify({
                "HH" : param.hh,
            }),
            success: function (res) {
                removeModal();
                if(res.code == "200" && res.data.length > 0){
                    deferred.resolve(res.data);
                }else{
                    deferred.resolve([]);
                }
            },
            error: function (xhr, status, error) {
                removeModal();
                deferred.reject();
                delAjaxErrorMsg(xhr);
            }
        });
        return deferred;
    }

    /**
     * 导出Excel通过隐藏form提交方式，避免ajax方式不支持下载文件
     * @date 2019.05.24 14:45
     * @author zhuyong
     * @return
     */
    function exportExcel(d, cols, type) {
        var checkedData = d.length > 0 ? d: czrkxxData;

        if ($.isEmptyObject(checkedData) && type == "checked") {
            layer.alert("请选择需要导出Excel的数据行！", {title: '提示'});
            return;
        }

        // 标题
        var showColsTitle = [];
        // 列内容
        var showColsField = [];
        // 列宽
        var showColsWidth = [];
        for (var index in cols) {
            if (cols[index].hide == false && cols[index].type != 'checkbox' && !cols[index].toolbar) {
                showColsTitle.push(cols[index].title);
                showColsField.push(cols[index].field);
                if (cols[index].width > 0) {
                    showColsWidth.push(parseInt(cols[index].width / 100 * 15));
                } else if (cols[index].minWidth > 0) {
                    showColsWidth.push(parseInt(cols[index].minWidth / 100 * 15));
                } else {
                    showColsWidth.push(200 / 100 * 15);
                }
            }
        }

        var data = new Array();

        $.each(checkedData, function (key, value) {
            data.push(value);
        })

        for (var i = 0; i < data.length; i++) {
            data[i].xh = i + 1;
        }

        // 设置Excel基本信息
        $("#fileName").val('常住人口信息查询');
        $("#sheetName").val('常住人口信息查询');
        $("#cellTitle").val(showColsTitle);
        $("#cellWidth").val(showColsWidth);
        $("#cellKey").val(showColsField);
        $("#data").val(JSON.stringify(data));
        $("#form").submit();

        // 记录导出excel 日志
        //saveQueryLogToDataBase(getSearchParam(), czrkxxData, "export-czrkxxcx");
    }

    function printData(d){
        var dylx = "czrkxxcx";
        var checkedData = d.length > 0 ? d: czrkxxData;
        var gzlslid="";
        if ($.isEmptyObject(checkedData) && type == "checked") {
            layer.alert("请layer选择需要导出Excel的数据行！", {title: '提示'});
            return;
        }

        var key = saveJkDataToRedis(checkedData);
        if(isNullOrEmpty(key)){
            warnMsg("生成打印数据缓存内容失败。");
        }
        var pdfUrl = getContextPath() + "/rest/v1.0/gx/pdf/print/" + dylx + "/" +key+ "?gzlslid=" + gzlslid;
        window.open(getContextPath() + '/static/lib/pdf/web/viewer.html?file=' + encodeURIComponent(pdfUrl), "PDF");
    }

});

//身份证读卡器设置
window.onReadIdCard = function () {
    try {
        var cardInfo = readIdCard();
        if (cardInfo.ReadCard()) {
            var name = cardInfo.Name;
            var number = cardInfo.ID;
            $('input[name="xm"]').val(name);
            $('input[name="gmsfhm"]').val(number);
        } else {
            warnMsg(" 请检查读卡器是否安装成功并重新放置身份证！");
        }
    } catch (objError) {
        warnMsg(" 请检查读卡器是否安装成功并重新放置身份证！");
    }
}