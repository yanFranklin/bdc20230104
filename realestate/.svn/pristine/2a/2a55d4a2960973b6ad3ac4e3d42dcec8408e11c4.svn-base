var jkname= $.getUrlParam("jkname");
var key ="";
var mlmc= getQueryString("mlmc");
var gzlslid = $.getUrlParam("gzlslid");
layui.use(['form', 'jquery', 'laydate', 'element', 'table'], function () {
    var $ = layui.jquery,
        table = layui.table;
    $(function () {
        init();

        function init() {
            table.render({
                elem: '#pageTable',
                toolbar: '#toolbarDemo',
                title: '司法判决查询\\司法判决响应结果查询',
                defaultToolbar: ['filter', 'print', 'exports'],
                even: true,
                cols: [[
                    {type: 'numbers', width: 50, fixed: 'left'},
                    {field: 'businessNumber', title: '业务号', width: 150},
                    {field: 'cxqqdh', title: '查询请求单号', width: 150},
                    {field: 'msg', title: '响应信息', width: 200},
                    {field: 'requestType', title: '查询接口标识', width: 200},
                    {field: 'status', title: '状态码', width: 150},
                    {field: 'cNr', title: '司法判决响应结果', minWidth: 400, templet: '#cNrTpl'},
                    // {title: '查看', fixed: 'right', toolbar: '#barDemo', width: 120}
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
        }


        // 点击查询
        $('#search').on('click', function () {
            search();
        });

        var pjsnr = "";

        function search() {
            var slbh = $("#slbh").val();
            var cxbm = $("#cxbm").val();
            var cxbmbh = $("#cxbmbh").val();
            var cxr = $("#cxr").val();
            var cxrbh = $("#cxrbh").val();
            var ajbh = $("#ajbh").val();

            if (isNullOrEmpty(slbh) || isNullOrEmpty(cxbm) || isNullOrEmpty(cxbmbh) ||
                isNullOrEmpty(cxr) || isNullOrEmpty(cxrbh) || isNullOrEmpty(ajbh)) {
                warnMsg("请输入查询接口完整条件！");
                return;
            }

            var cxbmArray = cxbm.split(",");
            var cxbmbhArray = cxbmbh.split(",");
            var cxrArray = cxr.split(",");
            var cxrbhArray = cxrbh.split(",");
            var ajbhArray = ajbh.split(",");

            if (!(cxbmArray.length == cxbmbhArray.length && cxbmArray.length == cxrArray.length &&
                cxbmArray.length == cxrbhArray.length && cxbmArray.length == ajbhArray.length)) {
                warnMsg("查询条件数量匹配不一致！");
                return;
            }

            var cxnr = new Array();
            for (var i = 0; i < cxbmArray.length; i++) {
                var qlrxx = {
                    "cxbm": cxbmArray[i],
                    "cxbmbh": cxbmbhArray[i],
                    "cxr": cxrArray[i],
                    "cxrbh": cxrbhArray[i],
                    "ajbh": ajbhArray[i]
                };
                cxnr.push(qlrxx);
            }

            addModel();
            var searchData = {"slbh": slbh, "datas": {"data": cxnr}};
            $.ajax({
                url: "/realestate-inquiry-ui/rest/v1.0/bjjk/sfpjcx",
                type: 'POST',
                dataType: 'json',
                contentType: "application/json;charset=UTF-8",
                data: JSON.stringify(searchData),
                success: function (data) {
                    if (data) {
                        dealCxjgxx("success",jkname);
                        key = saveJkDataToRedis(data);
                        table.render({
                            elem: '#pageTable',
                            toolbar: '#toolbarDemo',
                            title: '司法判决查询、司法判决响应结果查询',
                            defaultToolbar: ['filter', 'print', 'exports'],
                            even: true,
                            cols: [[
                                {type: 'numbers', width: 50, fixed: 'left'},
                                {field: 'businessNumber', title: '业务号', width: 150},
                                {field: 'cxqqdh', title: '查询请求单号', width: 250},
                                {field: 'msg', title: '响应信息', width: 200},
                                {field: 'requestType', title: '查询接口标识', width: 200},
                                {field: 'status', title: '状态码', width: 150},
                                {field: 'cNr', title: '司法判决响应结果', minWidth: 400, templet: '#cNrTpl'},
                                // {title: '查看', fixed: 'right', toolbar: '#barDemo', width: 120}
                            ]],
                            data: [data],
                            page: true,
                            done: function () {
                                setHeight();
                            }
                        });

                        // 进行司法判决响应结果查询
                        if (!isNullOrEmpty(data.cxqqdh)) {
                            warnMsg("正在进行司法判决响应结果查询");
                            $.ajax({
                                url: "/realestate-inquiry-ui/rest/v1.0/bjjk/sfpjcxfk",
                                type: 'POST',
                                dataType: 'json',
                                contentType: "application/json;charset=UTF-8",
                                data: JSON.stringify({"slbh": slbh, "cxqqdh": data.cxqqdh}),
                                success: function (data) {
                                    if (data) {
                                        pjsnr = Base64.decode(data[0].cNr);
                                        $(".cNr").html('<p class="bdc-table-state-th">' + pjsnr + '</p>');
                                    } else {
                                        pjsnr = "";
                                        warnMsg("未查询到司法判决响应结果！");
                                    }
                                }
                            });
                        } else {
                            pjsnr = "";
                            warnMsg("请求单号为空，无法查询司法判决响应结果！");
                        }

                    } else {
                        warnMsg("未查询到司法判决结果！");
                    }
                }, error: function () {
                    init();
                    dealCxjgxx("fail",jkname);
                    warnMsg("司法判决查询失败，请重试！");
                }, complete: function () {
                    removeModal();
                }
            });
        }

        //监听工具条
        table.on('tool(pageTable)', function (obj) {
            if (isNullOrEmpty(pjsnr)) {
                warnMsg("没有判决书内容，无法查看！");
                return;
            }

            if (obj.event === 'pjsnr') {
                $.cookie("PJSNR", pjsnr);
                window.open("iframe.html");
            }
        });
    });
});