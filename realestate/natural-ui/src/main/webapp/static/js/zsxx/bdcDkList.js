var param ;
var jyqList ={};
layui.use(['jquery', 'form', 'table', 'laytpl', 'laydate', ], function() {
    $ = layui.jquery;
    var form = layui.form;
    var table = layui.table;
    var laytpl = layui.laytpl;
    var laydate = layui.laydate;

    $(function () {
        param = $.getUrlParam('param');

        if (param) {
            $.ajax({
                url: '/realestate-register-ui/rest/v1.0/djbxx/qlxx/tdcbjyqdkxx/' + param,
                contentType: "application/json;charset=utf-8",
                type: "GET",
                dataType: "json",
                success: function (data) {

                    if (data) {

                        for (var i = 0, len = data.length; i < len; i++) {
                            // 日期处理
                            data[i].cbqssj = Format(data[i].cbqssj, 'yyyy-MM-dd');
                            data[i].cbjssj = Format(data[i].cbjssj, 'yyyy-MM-dd');
                        }
                        jyqList = data;

                        //转换静态表格
                        table.init('dkxxTable',
                            {
                                data: jyqList,
                                toolbar: "#toolbarDemo",
                                defaultToolbar: ['filter'],
                            });
                    }
                },
                error: function () {
                    layer.alert("获取登记簿信息失败！")
                }
            });
        }


        // 打印证书附表
        $(document).on('click', '#printButton', function () {
            print(dkfbModelUrl, getIP() + "/realestate-register-ui/rest/v1.0/print/dkfb/" + param + "/dkfb/xml", false);

            // 禁止提交后刷新
            return false;
        });
    });
});