/**
 * 最高人民法院接口（国家级）回执信息查询接口页面 JS
 */
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
                title: '最高人民法院接口（国家级）回执信息查询接口结果',
                defaultToolbar: ['filter', 'print', 'exports'],
                even: true,
                cols: [[
                    {type: 'numbers', width: 50, fixed: 'left', title: '序号'},
                    {field: 'cNr', title: '回执信息内容', minWidth: 400},
                    {title: '查看', fixed: 'right', toolbar: '#barDemo', width: 120}
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

        function search() {
            var slbh = $("#slbh").val();
            var cxqqdh = $("#cxqqdh").val();

            if (isNullOrEmpty(slbh) || isNullOrEmpty(cxqqdh)) {
                warnMsg("请输入查询接口完整条件！");
                return;
            }

            addModel();
            $.ajax({
                url: "/realestate-inquiry-ui/rest/v1.0/bjjk/sfpjcxfk",
                type: 'POST',
                dataType: 'json',
                contentType: "application/json;charset=UTF-8",
                data: JSON.stringify({"slbh":slbh, "cxqqdh":cxqqdh}),
                success: function (data) {
                    removeModal();
                    if (data) {
                        dealCxjgxx("success",jkname);
                        key = saveJkDataToRedis(data);
                        table.render({
                            elem: '#pageTable',
                            toolbar: '#toolbarDemo',
                            title: '回执信息结果查询',
                            defaultToolbar: ['filter', 'print', 'exports'],
                            even: true,
                            cols: [[
                                {type: 'numbers', width: 50, fixed: 'left', title: '序号'},
                                {field: 'cNr', title: '回执信息内容', minWidth: 400},
                                {title: '查看', fixed: 'right', toolbar: '#barDemo', width: 120}
                            ]],
                            data: data,
                            page: true,
                            done: function () {
                                setHeight();
                            }
                        });
                    } else {
                        warnMsg("未查询到回执信息！");
                    }
                }, error: function () {
                    init();
                    dealCxjgxx("fail",jkname);
                    warnMsg("回执信息查询失败，请重试！");
                }, complete: function () {
                    removeModal();
                }
            });
        }

        //监听工具条
        table.on('tool(pageTable)', function (obj) {
            var data = obj.data;
            if (isNullOrEmpty(data.cNr)) {
                warnMsg("没有回执信息，无法查看！");
                return;
            }

            if (obj.event === 'nrck') {
                layui.sessionData('PJSNR', {
                    key: "HZXX", value: data.cNr
                });
                window.open("iframe.html");
            }
        });
    });
});