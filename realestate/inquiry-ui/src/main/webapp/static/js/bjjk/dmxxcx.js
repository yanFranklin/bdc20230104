/**
 * 地名信息查询
 */
var gzlslid = getQueryString('gzlslid');
var jkname= $.getUrlParam("jkname");
var key ="";
var mlmc= getQueryString("mlmc");
layui.use(['form', 'jquery', 'laydate', 'element', 'table'], function () {
    var $ = layui.jquery,  form = layui.form,  table = layui.table;

    // 婚姻登记信息
    var dmxxcxData = [];
    $(function () {
        // 初始化
        init();

        // 获取行政区划字典
        $.ajax({
            url: "/realestate-inquiry-ui/rest/v1.0/bjjk/qjxzq",
            dataType: "json",
            async: false,
            success: function (zdList) {
                if(zdList) {
                    // 渲染字典项
                    $.each(zdList, function (index, item) {
                        $('#xzqh').append('<option value="' + item.XZDM + '">' + item.XZMC + '</option>');
                    });
                    form.render();
                }
            }
        });

        // 选择区划赋值默认地名
        form.on('select(xzqh)', function(data){
            $("#dm").val(this.innerText);
        });

        // 点击查询
        $('#search').on('click', function () {
            search();
        });

        // 表格信息
        function init() {
            table.render({
                elem: '#dmxxcxTable',
                toolbar: '#toolbarDemo',
                title: '地名信息查询',
                defaultToolbar: ['filter', 'print', 'exports'],
                even: true,
                cols: [[
                    {field: 'standard_name', title: '地名', width: 120},
                    {field: 'place_type', title: '地名类别', width: 120},
                    {field: 'place_code', title: '区划代码', width: 200},
                    {field: 'roman_alphabet_spelling', title: '罗马字母拼写', width: 140},
                    {field: 'place_meaning', title: '地名含义', minWidth: 120},
                    {field: 'place_origin', title: '地名来历', minWidth: 120},
                    {field: 'place_history', title: '历史沿革', minWidth: 250},
                ]],
                data: dmxxcxData,
                page: true,
                done: function () {
                    setHeight();
                }
            });

            if(isNullOrEmpty(gzlslid)){
                $(".upload-pdf").hide();
            }

            //头工具栏事件
            table.on('toolbar(dmxxcxTable)', function (obj) {
                switch (obj.event) {
                    case 'upload-pdf':
                        uploadPdf(gzlslid,jkname,mlmc,key,"省级数据共享交换平台");
                        break;
                }

            });
        }

        // 查询事件
        function search() {
            var ywh = $("#ywh").val();
            var xzqh = $("#xzqh").val();
            var dm = $("#dm").val();
            if (isNullOrEmpty(ywh) || isNullOrEmpty(xzqh) || isNullOrEmpty(dm)) {
                warnMsg("请输入查询接口完整条件！");
                return;
            }

            addModel();
            // 查询地名信息查询信息
            $.ajax({
                url: "/realestate-inquiry-ui/rest/v1.0/bjjk/dmxxcx",
                type: 'POST',
                dataType: 'json',
                contentType: "application/json;charset=UTF-8",
                data: JSON.stringify({"ywh": ywh, "xzqh": xzqh, "dm": dm}),
                success: function (data) {
                    if (data) {
                        dmxxcxData = data;
                        dealCxjgxx("success",jkname);
                        key = saveJkDataToRedis(dmxxcxData);
                    }
                }, error: function () {
                    dealCxjgxx("fail",jkname);
                    warnMsg("地名信息查询失败，请重试！");
                }, complete: function () {
                    init();
                    removeModal();
                }
            });
        }
    });
});