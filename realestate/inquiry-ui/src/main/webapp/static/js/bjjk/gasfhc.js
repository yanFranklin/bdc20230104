var gzlslid = $.getUrlParam("gzlslid");
var qlrlx = '1';
var jkname= $.getUrlParam("jkname");
var key ="";
var mlmc= getQueryString("mlmc");
layui.use(['form', 'jquery', 'laydate', 'element', 'table'], function () {
    var $ = layui.jquery,
        table = layui.table,
        form = layui.form;
    $(function () {
        table.render({
            elem: '#pageTable',
            toolbar: '#toolbarDemo',
            title: '公安身份核查结果展示信息',
            defaultToolbar: ['filter', 'print', 'exports'],
            even: true,
            cols: [[
                {field: 'checkCode', title: '核查结果比中状态码', width: 400},
                {field: 'message', title: '核查结果比中状态码名称'}
            ]],
            data: [],
            page: true,
            done: function () {
                setHeight();
            }
        });

        if(isNullOrEmpty(gzlslid)){
            $(".upload-pdf").hide();
        }

        //头工具栏事件
        table.on('toolbar(pageTable)', function (obj) {
            switch (obj.event) {
                case 'upload-pdf':
                    uploadPdf(gzlslid,jkname,mlmc,key,"省级数据共享交换平台");
                    break;
            }

        });

        getSearch();

        // 点击查询
        $('#qtqlr').on('click', function () {
            qtqlr();
        });

        // 点击查询
        $('#search').on('click', function () {
            search();
        });

        function search() {
            var slbh = $("#slbh").val();
            var zjh = $("#zjh").val();

            if (isNullOrEmpty(slbh) || isNullOrEmpty(zjh)) {
                warnMsg("请输入核查接口条件！");
                return;
            }

            addModel();
            $.ajax({
                url: "/realestate-inquiry-ui/rest/v1.0/bjjk/gasfhc",
                type: 'POST',
                dataType: 'json',
                contentType: "application/json;charset=UTF-8",
                data: JSON.stringify({"slbh": slbh, "zjh": zjh}),
                success: function (data) {
                    if (data) {
                        var checkCode = data[0].checkCode;
                        var message = "";
                        if ("000" == checkCode) {
                            message = "核查一致";
                        } else if ("999" == checkCode) {
                            message = "无匹配记录";
                        } else {
                            message = "核查项不一致";
                        }

                        var tableData = [
                            {
                                "checkCode": checkCode,
                                "message": message
                            }];
                        dealCxjgxx("success",jkname);
                        key = saveJkDataToRedis(tableData);
                        table.render({
                            elem: '#pageTable',
                            toolbar: '#toolbarDemo',
                            title: '公安身份核查结果展示信息',
                            defaultToolbar: ['filter', 'print', 'exports'],
                            even: true,
                            cols: [[
                                {field: 'checkCode', title: '核查结果比中状态码', width: 400},
                                {field: 'message', title: '核查结果比中状态码名称'}
                            ]],
                            data: tableData,
                            page: true,
                            done: function () {
                                setHeight();
                            }
                        });
                    } else {
                        warnMsg("身份核查失败，请重试！");
                    }
                }, complete: function () {
                    removeModal();
                }, error: function () {
                    dealCxjgxx("fail",jkname);
                    warnMsg("查询失败，请重试！");
                }
            });
        }
        var qlrDataList = [];
        function getSearch() {
            $.ajax({
                url:"/realestate-inquiry-ui/rest/v1.0/gx/cxywcs/xmxx",
                type: "POST",
                data: JSON.stringify({"gzlslid":gzlslid}),
                contentType: 'application/json',
                success: function (data) {
                    removeModal();

                    if(data && data.length>0){
                        $("#slbh").val(data[0].slbh);
                        $.ajax({
                            url:"/realestate-inquiry-ui/rest/v1.0/gx/cxywcs/qlr",
                            type: "POST",
                            data: JSON.stringify({"gzlslid":gzlslid,"qlrlx":qlrlx}),
                            contentType: 'application/json',
                            success: function (data) {
                                removeModal();

                                if(data && data.length>0){
                                    var optionList = '';

                                    for (var i = 0; i < data.length; i++) {
                                        qlrDataList.push({ qlrmc: data[i].qlrmc, zjh: data[i].zjh });
                                        optionList += '<option value="'+ data[i].zjh + '">' + data[i].zjh + '</option>'
                                    }
                                    $("#zjh").append(optionList);
                                    if (qlrDataList.length > 0) {
                                        $("#zjh").val(qlrDataList[0].zjh);
                                    }
                                    form.render("select");
                                    $('#search').click();
                                }

                            },
                            error: function (xhr, status, error) {
                                removeModal();
                                delAjaxErrorMsg(xhr)
                            }
                        });
                    }

                },
                error: function (xhr, status, error) {
                    removeModal();
                    delAjaxErrorMsg(xhr)
                }
            });
        }

        //扫描下载
        function qtqlr() {
            layer.open({
                type: 1,
                title: '查询其他权利人',
                skin: 'bdc-ckgdxx-layer',
                area: ['430px'],
                content:
                    '<div id="bdc-popup-small ckgdxx-layer">' +
                    '<form class="layui-form" action="">' +
                    '<div class="layui-form-item pf-form-item">' +
                    // '<div class="layui-inline" style="width: 100%;">' +
                    // ' <label class="layui-form-label">权利人名称:</label>' +
                    // ' <div class="layui-input-inline">' +
                    // '<input type="text" autofocus="true" name="newqlrmc" id="newqlrmc" autocomplete="off" placeholder="请输入" class="layui-input">' +
                    // '</div>' +
                    // '</div>' +
                    '<div class="layui-inline" style="width: 100%;">' +
                    ' <label class="layui-form-label">证件号:</label>' +
                    ' <div class="layui-input-inline">' +
                    '<input type="text" autofocus="true" name="newzjh" id="newzjh" autocomplete="off" placeholder="请输入" class="layui-input">' +
                    '</div>' +
                    '</div>' +
                    '</div>' +
                    '</form>' +
                    '</div>',
                btn: ['查询'],
                btnAlign: 'c',
                yes: function (index, layero) {
                    //确定操作
                    var newqlrmc = layero.find("input[name='newqlrmc']").val();
                    var newzjh = layero.find("input[name='newzjh']").val();

                    qlrDataList.push({ qlrmc: "", zjh: newzjh });
                    var optionList = '<option value="'+ newzjh + '">' + newzjh + '</option>';


                    $("#zjh").append(optionList);
                    $("#zjh").val(newzjh);
                    form.render("select");
                    $('#search').click();

                    layer.close(index);
                },
                cancle: function (index) {
                    layer.close(index);
                },
                done: function (index) {

                }
            });
        }
    });
});