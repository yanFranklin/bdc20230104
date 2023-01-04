/**
 * 新增委托书
 */
layui.use(['table','laytpl','laydate','layer', 'form'],function () {
    var $ = layui.jquery,
        layer = layui.layer,
        laydate = layui.laydate,
        form = layui.form;
    addModel();
    laydate.render({
        elem: '#wtksrq',
        type: 'date',
        format: 'yyyy-MM-dd',
        trigger: 'click',
        done: function (value, date, endDate) {
                date = new Date();
                var year = date.getFullYear(); //获取完整的年份(4位)
                var month = date.getMonth() + 1; //获取当前月份(0-11,0代表1月)
                var strDate = date.getDate();
                $("input[name='wtksrq']").val(year+'-'+month+'-'+strDate);
        }
    });

    csh();


    /**
     * 表单校验
     */
    form.verify({
        wtsbh : function(){
            var wtsbh = $("input[name='wtsbh']").val();
            if (isNullOrEmpty(wtsbh)){
                return '请输入委托编号';
            }
        },
        wtrxm : function(){
            var wtrxm = $("input[name='wtrxm']").val();
            var wtrxb = $("input[name='wtrxb']").val();
            var wtrzjh = $("input[name='wtrzjh']").val();

            if(isNullOrEmpty(wtrxm)){
                return '委托人姓名不可为空';
            }
            if (wtrxm.split(',').length !== wtrzjh.split(',').length || wtrxm.split(',').length !== wtrxb.split(',').length
                || wtrzjh.split(',').length !== wtrxb.split(',').length ){
                return '委托人姓名、证件号、性别需一一对应';
            }
        },
        wtrxb : function () {
            var wtrxb = $("input[name='wtrxb']").val();
            if(isNullOrEmpty(wtrxb)){
                return '委托人性别不可为空';
            }
        },
        wtrzjh : function () {
            var wtrzjh = $("input[name='wtrzjh']").val();
            if(isNullOrEmpty(wtrzjh)){
                return "委托人证件号不可为空";
            }
        },
        strxm : function () {
            var strxm = $("input[name='strxm']").val();
            var strxb = $("input[name='strxb']").val();
            var strzjh = $("input[name='strzjh']").val();
            if(isNullOrEmpty(strxm)){
                return "受托人姓名不可为空";
            }
            if (strxm.split(',').length !== 1 || strxb.split(',').length !== 1 || strzjh.split(',').length !== 1){
                return '受托人只能有一个';
            }
        },
        strxb : function () {
            var strxb = $("input[name='strxb']").val();
            if(isNullOrEmpty(strxb)){
                return "受托人性别不可为空";
            }
        },
        strzjh : function () {
            var strzjh = $("input[name='strzjh']").val();
            if(isNullOrEmpty(strzjh)){
                return "受托人证件号不可为空";
            }
        },
        zl : function () {
            var zl = $("input[name='zl']").val();
            if(isNullOrEmpty(zl)){
                return "坐落不可为空";
            }
        },
        wtyy : function () {
            var wtyy = $("input[name='wtyy']").val();
            if(isNullOrEmpty(wtyy)){
                return "委托原因不可为空";
            }
        },
        wtsx : function () {
            var wtsx = $("#wtsx option:selected").val();
            if(isNullOrEmpty(wtsx)){
                return "委托事项不可为空";
            }
        },

    });

    /**
     * 表单数据提交
     */
    form.on('submit(submitBtn)', function(data) {
        var loadIndex = loadLayer();
        $.ajax({
            url: "/realestate-inquiry-ui/rest/v1.0/saveWts",
            type: "POST",
            data: JSON.stringify(data.field),
            contentType: 'application/json',
            dataType: "json",
            success: function (data) {
                if(data && $.isNumeric(data) && data > 0){
                    success();
                } else {
                    fail(data);
                }
            },
            error:function($xhr,textStatus,errorThrown){
                fail(JSON.parse($xhr.responseText));
            },
            complete: function(){
                layer.close(loadIndex);
            }
        });

        // 禁止提交后刷新
        return false;
    });

    // 获取流程信息
    $.ajax({
        url: '/realestate-inquiry-ui/bdcZhGz/process/definitions',
        type: "GET",
        dataType: "json",
        success: function (data) {
            if (data) {
                $.each(data, function (index, item) {
                    $('#wtsx').append('<option value="' + item.name + '">' + item.name + '</option>');
                });
                form.render('select');
            }
        }
    });

    /**
     * 关闭弹出页面
     */
    window.closeWin = function(){
        parent.layer.closeAll();
    };

    window.closeParentWindow = function(){
        var index = parent.parent.layer.getFrameIndex(window.name);
        parent.parent.layer.close(index);
    };

    /**
     * 提交成功提示
     */
    window.success = function(){
        layui.layer.msg('<img src="../../static/lib/bdcui/images/success-small.png" alt="">提交成功');
        setTimeout(function(){
            parent.layer.closeAll();
            layui.table.reload('pageTable');
        },2000);
    }

    /**
     * 提交失败提示
     * @param data
     */
    window.fail = function(data){
        var msg = "提交失败，请检查填写内容!";
        if(data && data.code){
            msg = "提交失败";
        }

        alertMsg(msg);
    }
});

function csh() {
    $.ajax({
        url: "/realestate-inquiry-ui/rest/v1.0/getWts",
        type: 'GET',
        data: {},
        success: function (res) {
            $("input[name='wtsbh']").val(res.data);
            removeModal();
        }
    })
}

//身份证读卡器设置
window.onReadIdCard = function (param) {
    try {
        var cardInfo = readIdCard();
        if (cardInfo.ReadCard()) {
            var name = cardInfo.Name;
            var zjh = cardInfo.ID;
            var sex = cardInfo.Sex;

            if (param === 1) {
                var wtrxm = $("input[name='wtrxm']").val();
                var wtrzjh = $("input[name='wtrzjh']").val();
                var wtrxb = $("input[name='wtrxb']").val();

                if (isNullOrEmpty(wtrxm) && isNullOrEmpty(wtrzjh) && isNullOrEmpty(wtrxb)) {
                    $("input[name='wtrxm']").val(name);
                    $("input[name='wtrxb']").val(sex);
                    $("input[name='wtrzjh']").val(zjh);
                }else {
                    $("input[name='wtrxm']").val(wtrxm+','+name);
                    $("input[name='wtrxb']").val(wtrxb+','+sex);
                    $("input[name='wtrzjh']").val(wtrzjh+','+zjh);
                }

            }
            if (param === 2) {
                $("input[name='strxm']").val(name);
                $("input[name='strxb']").val(sex);
                $("input[name='strzjh']").val(zjh);
            }


        } else {
            warnMsg("请检查读卡器是否安装成功并且重新放置身份证！");
        }
    } catch (objError) {
        warnMsg(" 请检查读卡器是否安装成功并重新放置身份证！");
    }
}

