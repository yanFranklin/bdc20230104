/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @description 新增发票号
 */
layui.use(['table','laytpl','laydate','layer', 'form'],function () {
    var $ = layui.jquery,
        layer = layui.layer,
        form = layui.form;


    //没渲染之前第一个选项已经有值（不是 请选择 选项）
    //说明是南通的html，则不作任务处理
    if($('#qxdm').find('option:eq(0)').val() == ""){
        // 获取区县代码字典
        $.ajax({
            url: getContextPath() + '/rest/v1.0/zsbhmb/zd/qxdm',
            type: "GET",
            dataType: "json",
            timeout : 10000,
            success: function(data) {
                if(data && data.length > 0){
                    // 渲染字典项
                    $.each(data, function(index,item) {
                        $('#qxdm').append('<option value="'+item.XZDM +'">'+item.XZMC + '</option>');
                    });
                }
                form.render('select');
            }
        });
    }

    // 获取发票号区县对应领取方式
    var lqfs;
    $.ajax({
        url: getContextPath() + '/rest/v1.0/fph/lqfs',
        type: "GET",
        dataType: "json",
        success: function(data) {
            lqfs = data;
        }
    });

    // 默认设值
    var date = new Date();
    $("input[name='nf']").val(date.getFullYear());
    $("input[name='bhws']").val(8);

    /**
     * 表单校验
     */
    form.verify({
        nf : [/^[0-9]*$/, '请输入正确的年份数字'],
        number : [/^[1-9][0-9]*$/, '请输入大于0的整数'],
        bhws : function(value){
            if(value < 8){
                return '发票号位数为8位';
            }
        },
        qsbh : function(qsbh){
            var jsbh = $("input[name='jsbh']").val();
            if(qsbh && jsbh && parseInt(qsbh) > parseInt(jsbh)){
                return '起始编号不能大于结束编号';
            }

            // 一次生成发票号数量控制在10万
            if(parseInt(jsbh) - parseInt(qsbh) + 1 > 100000){
                return '单次生成发票号数量不能超过10万，请修改起始编号、结束编号！';
            }
        },
        lqr : function (lqr) {
            if(isNullOrEmpty(lqr) && $("#lqr_div").is(":visible")){
                return '请设置领取人员！';
            }
        },
        lqbm : function (lqbm) {
            if(isNullOrEmpty(lqbm) && $("#lqbm_div").is(":visible")){
                return '请设置领取部门！';
            }
        }
    });

    /**
     * 表单数据提交
     */
    form.on('submit(submitBtn)', function(data) {
        //这里处理下因为下拉树框数据清除但是隐藏ID值未清理问题
        if(isNullOrEmpty($("input[name='lqbm']").val())){
            data.field.lqbmid = null;
        }
        if(isNullOrEmpty($("input[name='lqr']").val())){
            data.field.lqrid = null;
        }

        var loadIndex = loadLayer();
        $.ajax({
            url: getContextPath() + "/rest/v1.0/fph",
            type: "POST",
            data: JSON.stringify(data.field),
            contentType: 'application/json',
            dataType: "json",
            success: function (data) {
                // 提交成功会返回更新数量，正常应该大于0，失败会返回状态信息JSON
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

    // 默认隐藏发票号领取方式配置项
    $("#lqbm_div").hide();
    $("#lqr_div").hide();

    // 根据选择的区划设置对应的发票号领取方式配置项
    form.on('select(qxdm)', function(data){
        $("input[name='lqbmid']").val(null);
        $("input[name='lqbm']").val(null);
        $("input[name='lqrid']").val(null);
        $("input[name='lqr']").val(null);

        if(lqfs && data && data.value){
           for(var key in lqfs){
               if(key == data.value){
                   if('0' == lqfs[key]){        //按照部门领取
                        $("#lqbm_div").show();
                        $("#lqr_div").hide();
                   }else if('1' == lqfs[key]){  //按照人员领取
                       $("#lqr_div").show();
                       $("#lqbm_div").hide();
                   }else{                       //默认按照区县代码
                       $("#lqr_div").hide();
                       $("#lqbm_div").hide();
                   }
               }
           }
        }
    });

    /**
     * 关闭弹出页面
     */
    window.closeWin = function(){
        var index=parent.layer.getFrameIndex(window.name);
        parent.layer.close(index);
    };

    window.closeParentWindow = function(){
        var index = parent.parent.layer.getFrameIndex(window.name);
        parent.parent.layer.close(index);
    };

    /**
     * 提交成功提示
     */
    window.success = function(){
        saveSuccessMsg();
        setTimeout(function(){
            parent.layer.closeAll();
        },2000);
    }

    /**
     * 提交失败提示
     * @param data
     */
    window.fail = function(data){
        var msg = "提交失败，请检查填写内容!";
        if(data && data.code && data.code == 72){
            msg = "提交失败，指定区间的部分发票号已经存在！";
        }
        errorsMsg(msg);
    }
});