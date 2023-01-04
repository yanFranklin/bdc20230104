var gzlslid = $.getUrlParam("gzlslid");
var qlrlx = '1';
var jkname= $.getUrlParam("jkname");
var key ="";
var mlmc= getQueryString("mlmc");
layui.use(['form', 'jquery', 'laydate', 'element', 'table'], function () {
    var $ = layui.jquery,
        table = layui.table,
        form = layui.form;

    // 中编办信息
    var jrxkzData = [];
    $(function () {
        // 初始化
        init();
        getSearch();

        // 点击查询
        $('#qtqlr').on('click', function () {
            qtqlr();
        });

        // 点击查询
        $('#search').on('click', function () {
            search();
        });

        // 表格信息
        function init() {
            table.render({
                elem: '#pageTable',
                toolbar: '#toolbarDemo',
                title: '金融许可证查询',
                defaultToolbar: ['filter', 'print', 'exports'],
                even: true,
                cols: [[
                    {type: 'numbers', width: 50, fixed: 'left'},
                    {field: 'id', title: '金融许可证ID', width: 150},
                    {field: 'certFlowNo', title: '流水号', width: 150},
                    {field: 'certCode', title: '机构编码', width: 200},
                    {field: 'fullName', title: '机构全称', width: 200},
                    {field: 'simpleName', title: '机构简称', width: 150},
                    {field: 'englishName', title: '英文名称', width: 150},
                    {field: 'address', title: '机构地址', width: 150},
                    {field: 'setDate', title: '设立日期', width: 150},
                    {field: 'printDate', title: '打印日期', width: 150},
                    {field: 'scope', title: '经营范围', width: 150},
                    {
                        field: 'typeId', title: '查询类型', width: 150,
                        templet: function (d) {
                            if (d && (01 == d.typeId || '01' == d.typeId)) {
                                return '<p>银行</p>';
                            } else if (d && (02 == d.typeId || '02' == d.typeId)) {
                                return '<p>保险</p>';
                            }
                        }
                    },
                    {field: 'organName', title: '监管机构名称', width: 150}
                ]],
                data: jrxkzData,
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
        }

        // 查询事假
        function search() {
            var slbh = $("#slbh").val();
            var zjh = $("#zjh").val();
            var type = $("#type").val();

            if (isNullOrEmpty(slbh) || isNullOrEmpty(zjh) || isNullOrEmpty(type)) {
                warnMsg("请输入查询接口完整条件！");
                return;
            }
            addModel();
            var searchData = {"slbh": slbh, "zjh": zjh, "type": type};

            // 查询婚姻登记信息
            $.ajax({
                url: "/realestate-inquiry-ui/rest/v1.0/bjjk/jrxkz",
                type: 'POST',
                dataType: 'json',
                contentType: "application/json;charset=UTF-8",
                data: JSON.stringify(searchData),
                success: function (data) {
                    if (data) {
                        jrxkzData = [data];
                        dealCxjgxx("success",jkname);
                        key = saveJkDataToRedis(jrxkzData);
                    }else {
                        warnMsg("查询失败，请重试！");
                    }
                }, error: function () {
                    init();
                    dealCxjgxx("fail",jkname);
                    warnMsg("查询失败，请重试！");
                }, complete: function () {
                    init();
                    removeModal();
                }
            });
        }

        var qlrDataList = [];

        function getSearch() {
            if(isNullOrEmpty(gzlslid)){
                return false;
            }
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
                            data: JSON.stringify({"gzlslid": gzlslid,excludeQlrlx:qlrlx}),
                            contentType: 'application/json',
                            success: function (data) {
                                removeModal();
                                if (data && data.length > 0) {
                                    var optionList = '';

                                    for (var i = 0; i < data.length; i++) {
                                        qlrDataList.push({qlrmc: data[i].qlrmc, zjh: data[i].zjh});
                                        optionList += '<option value="' + data[i].zjh + '">' + data[i].zjh + '</option>';
                                    }
                                    $("#zjh").append(optionList);
                                    $("#zjh").val(data[0].zjh);
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