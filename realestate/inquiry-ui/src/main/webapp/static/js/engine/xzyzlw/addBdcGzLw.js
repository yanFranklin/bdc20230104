/**
 * author: <a href="mailto:zhuyong@gtmap.cn>zhuyong</a>
 * version 1.0.0  2019/03/13
 * describe: 添加验证例外规则处理JS
 */
layui.use(['table','laytpl','laydate','layer', 'form'],function () {
    var $ = layui.jquery,
        table = layui.table,
        layer = layui.layer,
        form = layui.form;

    //var gzlslid = data.gzlslid;

    // 此处数据测试，实际应由上个页面传值
    var data = [];
    for(var i = 0; i < 15; i++){
        var item = {
            "lwwh": "10001"
            ,"gzmc": "杜甫"
            ,"yxj": "1"
            ,"tsxx": "人生恰似一场修行"
            ,"gzid": i
            ,"gzlslid": i
        };

        data.push(item);
    }

    table.render({
        elem: '#gzlb'
        ,cols: [[
            {type: 'checkbox', fixed: 'left', width: 50}
            ,{type: 'numbers', title: '序号' ,width:70, align: 'center'}
            ,{field: 'lwwh', title: '不动产单元号', width: 250, align: 'center'}
            ,{field: 'gzmc', title: '规则名称', minWidth: 50, align: 'center'}
            ,{field: 'yxj', title: '优先级', width: 150, align: 'center'}
            ,{field: 'tsxx', title: '提示信息', minWidth:100, align: 'center'}
        ]]
        ,data:data
        ,page: true
        ,limit:5
        ,limits:[5,10,15,20]
    });

    window.submit = function(){
        var checkStatus = table.checkStatus('gzlb');
        var data = checkStatus.data;
        var lwyy = $("#lwyy").val();

        for(var i in data){
            data[i].lwyy = lwyy;
        }

        $.ajax({
            url: "/realestate-inquiry-ui/rest/v1.0/bdcgzlw",
            type: "POST",
            data: JSON.stringify(data),
            contentType: 'application/json',
            dataType: "json",
            success: function (data) {
                if(data && $.isNumeric(data)){
                    layer.msg("提交成功！", {time: 2000});
                } else {
                    layer.alert("提交失败，请重试！");
                }
            },
            error:function(){
                layer.alert("提交失败，请重试！");
            }
        });
    }
});