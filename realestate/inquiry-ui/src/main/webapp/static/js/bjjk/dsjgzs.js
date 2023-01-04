var gzlslid = $.getUrlParam("gzlslid");
var qlrlx = '1';
var jkname = $.getUrlParam("jkname");
var key = "";
var mlmc = getQueryString("mlmc");
layui.use(['form', 'jquery', 'laydate', 'element', 'table'], function () {
    var $ = layui.jquery,
        table = layui.table,
        form = layui.form;
    $(function () {
        table.render({
            elem: '#pageTable',
            toolbar: '#toolbarDemo',
            title: '大数据平台公证书基本信息',
            defaultToolbar: ['filter', 'print', 'exports'],
            even: true,
            cols: [[
                {field: 'gzcmc', title: '公证处中文名称'},
                {field: 'gzy', title: '公证员名称'},
                {field: 'gzsbh', title: '公证书编号'},
                {field: 'czsj', title: '出证时间'},
                {field: 'gzsx', title: '公证事项名称'},
                {field: 'yt', title: '用途'},
            ]],
            data: [],
            page: true,
            done: function () {
                setHeight();
            }
        });

        if (isNullOrEmpty(gzlslid)) {
            $(".upload-pdf").hide();
        }

        //头工具栏事件
        table.on('toolbar(pageTable)', function (obj) {
            switch (obj.event) {
                case 'upload-pdf':
                    upload();
                    break;
                case 'download-pdf':
                    download();
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
            var xm = $("#xm").val();
            var sfzhm = $("#sfzhm").val();
            var gzsbh = $("#gzsbh").val();
            var jgbm = $("#jgbm").val();

            if (isNullOrEmpty(xm) || isNullOrEmpty(sfzhm) || isNullOrEmpty(gzsbh) || isNullOrEmpty(jgbm)) {
                warnMsg("请输入查询条件！");
                return;
            }

            var param = {
                "baseParams": "<xm>" + xm + "</xm>" +
                    "<sfzhm>" + sfzhm + "</sfzhm>" +
                    "<gzsbh>" + gzsbh + "</gzsbh>" +
                    "<jgbm>" + jgbm + "</jgbm>"
            };
            addModel();
            $.ajax({
                url: "/realestate-inquiry-ui/rest/v1.0/gx/dsjgzs/xxcx",
                type: 'POST',
                dataType: 'json',
                contentType: "application/json;charset=UTF-8",
                data: JSON.stringify(param),
                success: function (data) {
                    if (data) {
                        var tableData = [data];
                        dealCxjgxx("success", jkname);
                        key = saveJkDataToRedis(tableData);
                        table.render({
                            elem: '#pageTable',
                            toolbar: '#toolbarDemo',
                            title: '大数据平台公证书基本信息',
                            defaultToolbar: ['filter', 'print', 'exports'],
                            even: true,
                            cols: [[
                                {field: 'gzcmc', title: '公证处中文名称'},
                                {field: 'gzy', title: '公证员名称'},
                                {field: 'gzsbh', title: '公证书编号'},
                                {field: 'czsj', title: '出证时间'},
                                {field: 'gzsx', title: '公证事项名称'},
                                {field: 'yt', title: '用途'},
                            ]],
                            data: tableData,
                            page: true,
                            done: function () {
                                setHeight();
                            }
                        });
                    } else {
                        warnMsg("查询失败，请重试！");
                    }
                }, complete: function () {
                    removeModal();
                }, error: function () {
                    dealCxjgxx("fail", jkname);
                    warnMsg("查询失败，请重试！");
                }
            });
        }

        var qlrDataList = [];

        function getSearch() {
            $.ajax({
                url: "/realestate-inquiry-ui/rest/v1.0/gx/cxywcs/xmxx",
                type: "POST",
                data: JSON.stringify({"gzlslid": gzlslid}),
                contentType: 'application/json',
                success: function (data) {
                    removeModal();

                    if (data && data.length > 0) {
                        $("#slbh").val(data[0].slbh);
                        $.ajax({
                            url: "/realestate-inquiry-ui/rest/v1.0/gx/cxywcs/qlr",
                            type: "POST",
                            data: JSON.stringify({"gzlslid": gzlslid, "qlrlx": qlrlx}),
                            contentType: 'application/json',
                            success: function (data) {
                                removeModal();

                                if (data && data.length > 0) {
                                    var optionList = '';

                                    for (var i = 0; i < data.length; i++) {
                                        qlrDataList.push({qlrmc: data[i].qlrmc, zjh: data[i].zjh});
                                        optionList += '<option value="' + data[i].qlrmc + '">' + data[i].qlrmc + '</option>'
                                    }
                                    $('#xm').append(optionList);
                                    if (qlrDataList.length > 0) {
                                        $("#xm").val(qlrDataList[0].qlrmc);
                                        $("#sfzhm").val(qlrDataList[0].zjh);
                                    }
                                    form.render("select");
                                    //$('#search').click();
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

        //渲染
        form.on('select(xm)', function (data) {
            if (isNotBlank(data.value)) {
                for (var i = 0; i < qlrDataList.length; i++) {
                    if (data.value == qlrDataList[i].qlrmc) {
                        $("#sfzhm").val(qlrDataList[i].zjh);
                    }
                }
            }
        });

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
                    '<div class="layui-inline" style="width: 100%;">' +
                    ' <label class="layui-form-label">权利人名称:</label>' +
                    ' <div class="layui-input-inline">' +
                    '<input type="text" autofocus="true" name="newqlrmc" id="newqlrmc" autocomplete="off" placeholder="请输入" class="layui-input">' +
                    '</div>' +
                    '</div>' +
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

                    qlrDataList.push({qlrmc: newqlrmc, zjh: newzjh});
                    var optionList = '<option value="' + newqlrmc + '">' + newqlrmc + '</option>';


                    $('#xm').append(optionList);
                    $("#xm").val(newqlrmc);
                    $("#sfzhm").val(newzjh);

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

        function download() {
            var xm = $("#xm").val();
            var sfzhm = $("#sfzhm").val();
            var gzsbh = $("#gzsbh").val();
            var jgbm = $("#jgbm").val();
            var pdfUrl = document.location.protocol + "//" + document.location.host +
                "/realestate-inquiry-ui/rest/v1.0/gx/ydgh/fjxz?"
                + "xm=" + encodeURIComponent(xm)
                + "&sfzhm=" + encodeURIComponent(sfzhm)
                + "&gzsbh=" + encodeURIComponent(gzsbh)
                + "&jgbm=" + encodeURIComponent(jgbm);
            window.open(pdfUrl, "PDFDOWNLOAD" + gzsbh);
        }

        function upload() {
            var xm = $("#xm").val();
            var sfzhm = $("#sfzhm").val();
            var gzsbh = $("#gzsbh").val();
            var jgbm = $("#jgbm").val();
            var uploadUrl = document.location.protocol + "//" + document.location.host +
                "/realestate-inquiry-ui/rest/v1.0/gx/ydgh/fjsc?" +
                "xm=" + encodeURIComponent(xm)
                + "&sfzhm=" + encodeURIComponent(sfzhm)
                + "&gzsbh=" + encodeURIComponent(gzsbh)
                + "&jgbm=" + encodeURIComponent(jgbm)
                + "&gzlslid=" + encodeURIComponent(gzlslid)
            ;
            $.ajax({
                url: uploadUrl,
                type: "GET",
                // data: JSON.stringify(param),
                // contentType: 'application/json',
                async: false,
                success: function (data) {
                    layer.msg('文件上传成功');
                },
                error: function (xhr, status, error) {
                    warnMsg('文件上传失败');
                }
            });
        }
    });
});