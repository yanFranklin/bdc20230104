function initLayui(){
    layui.config({
        base: '../../../static/lib/form-select/'
    }).extend({
        formSelects: 'formSelects-v4'
    });
    var start = {
        istime: true,
        format: 'YYYY-MM-DD',
        max: '2099-06-16',
        istoday: true,
        choose: function (datas) {
            end.min = datas; //开始日选好后，重置结束日的最小日期
        }
    };

    var end = {
        istime: true,
        format: 'YYYY-MM-DD',
        max: '2099-06-16',
        istoday: true,
        choose: function (datas) {
            start.max = datas; //结束日选好后，重置开始日的最大日期
        }
    };
    var flg = true;
    layui.use(['form', 'layer', 'jquery', 'element', 'laydate','formSelects'], function () {
        var $ = layui.jquery,
            layer = layui.layer,
            form = layui.form;
        element = layui.element;
        laydate = layui.laydate;
        var data = layui.data;
        var formSelects = layui.formSelects;

        form.on('select(apply)', function (data) {
            var pringLog_data = [];
            console.log(data);
            // $("#uiapply").empty();
            $.ajax({
                url:'/realestate-inquiry-ui/xzrz/zdc/instanceName?applicationName='+data.value,
                data:{
                },
                success:function (result) {
                    console.log(result);
                    pringLog_data = result;
                    var htmlStr = '';
                    for(var i=0;i<result.length;i++){
                        htmlStr +='<option  value = "'+result[i].serviceId+'">'+result[i].serviceId+'</option>';
                    }
                    $("#uiapply").html(htmlStr);

                }
            });
            formSelects.config('uiapply', {
                keyName: 'serviceId',
                keyVal: 'serviceId'
            }).data('uiapply', 'server', {
                url: "/realestate-inquiry-ui/xzrz/zdc/instanceName?applicationName="+data.value
            });

        });
        form.on('select(test1)', function (data) {
            console.log(data.value); //得到被选中的值
        });
        form.on('submit(login)', function (data) {
            if (!flg) return false;
            let fields = data.field;
            console.log(fields);
            let submit_state = true;
            Object.keys(fields).forEach(key => {
                console.log(key);
            if (!fields[key]) {
                submit_state = false;
            }
        });
            if (submit_state) {
                flg = false;
                console.log('========POST=====');
                $.post("/realestate-inquiry-ui/xzrz/zdc/judgeLog", {
                    "apply": fields.apply
                    ,"end_time": fields.end_time
                    ,"start_time": fields.start_time
                    ,"uiapply": fields.uiapply
                }, function (data) {
                    document.getElementById("downLoadForm").submit();
                    setTimeout(() => {
                        flg = true;
                }, 600);
                }).error(function(e){
                    setTimeout(() => {
                        flg = true;
                }, 600);
                    layer.msg(JSON.parse(e.responseText).msg, { icon: 5 });
                });
            }
            return false;
        });
        var nowTime = new Date().valueOf();
        var max = null;
        var start = laydate.render({
            elem: '#start_time',
            type: 'date',
            btns: ['clear', 'confirm'],
            done: function (value, date) {
                endmax = end.config.max;
                end.config.min = date;
                end.config.min.month = date.month - 1;
            },
            change: function (value, date, endDate) {
                var timestamp2 = Date.parse(new Date(value));
                timestamp2 = timestamp2 / 1000;
                end.config.min = timestamp2;
                end.config.min.month = date.month - 1;
            }
        });
        var end = laydate.render({
            elem: '#end_time',
            type: 'date',
            done: function (value, date) {
                console.log("=======" + date);
                if ($.trim(value) == '') {
                    var curDate = new Date();
                    date = { 'date': curDate.getDate(), 'month': curDate.getMonth() + 1, 'year': curDate.getFullYear() };
                }
                start.config.max = date;
                start.config.max.month = date.month - 1;
            }
        });
    });
}
$(document).ready(function () {
    $.ajax({
        url:'/realestate-inquiry-ui/xzrz/zdc/applicationName',
        data:{},
        success:function (result) {
            console.log(result);
            var htmlStr = '';
            // console.log(result.length);
            for(var i=0;i<result.length;i++){
                htmlStr +='<option  value = "'+result[i]+'">'+result[i]+'</option>';
            }
            $("#apply").append(htmlStr);
            initLayui()
        }
    });
});