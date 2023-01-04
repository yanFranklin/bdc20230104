var jkname= $.getUrlParam("jkname");
var key ="";
var mlmc= getQueryString("mlmc");
var gzlslid = $.getUrlParam("gzlslid");
layui.use(['form', 'jquery', 'laydate', 'element', 'table'], function () {
    var $ = layui.jquery,
        table = layui.table;
    // 出生证明信息
    var cszmData = [];
    $(function () {
        // 初始化
        init();

        // 点击查询
        $('#search').on('click', function () {
            search();
        });

        // 表格信息
        function init() {
            table.render({
                elem: '#pageTable',
                toolbar: '#toolbarDemo',
                title: '中编办查询',
                defaultToolbar: ['filter', 'print', 'exports'],
                even: true,
                cols: [[
                    {type: 'numbers', width: 50, fixed: 'left'},
                    {field: 'babyName', title: '新生儿姓名', width: 150},
                    {field: 'babySexCode', title: '新生儿性别编码', width: 150},
                    {field: 'babySex', title: '新生儿性别', width: 200},
                    {field: 'birthArea', title: '出生地区', width: 200},
                    {field: 'birthTime', title: '出生时间', width: 150},
                    {field: 'birthCode', title: '出生医学证明编号', width: 150},
                    {field: 'momName', title: '母亲姓名', width: 150},
                    {field: 'momIdCode', title: '母亲证件编号', width: 150},
                    {field: 'dadName', title: '父亲姓名', width: 150},
                    {field: 'dadIdCode', title: '父亲证件编码', width: 150}
                ]],
                data: cszmData,
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
            var cszmh = $("#cszmh").val();
            var mqmc = $("#mqmc").val();
            var mqzjh = $("#mqzjh").val();

            if (isNullOrEmpty(cszmh) || isNullOrEmpty(mqmc) || isNullOrEmpty(mqzjh)) {
                warnMsg("请输入查询接口完整条件！");
                return;
            }
            addModel();
            var searchData = {"cszmh": cszmh, "mqmc": mqmc, "mqzjh": mqzjh};

            // 查询婚姻登记信息
            $.ajax({
                url: "/realestate-inquiry-ui/rest/v1.0/bjjk/hfWjwcszmcx",
                type: 'POST',
                dataType: 'json',
                contentType: "application/json;charset=UTF-8",
                data: JSON.stringify(searchData),
                success: function (data) {
                    if (data && data.body && data.resultinfo) {
                        if ("0" == data.resultinfo[0].success || 0 == data.resultinfo[0].success) {
                            cszmData = data.body[0].data;
                            dealCxjgxx("success",jkname);
                            key = saveJkDataToRedis(cszmData);
                        } else {
                            warnMsg("查询失败！");
                            dealCxjgxx("fail",jkname);
                        }
                    }
                }, error: function () {
                    warnMsg("查询失败，请重试！");
                    dealCxjgxx("fail",jkname);
                }, complete: function () {
                    init();
                    removeModal();
                }
            });
        }
    });
});