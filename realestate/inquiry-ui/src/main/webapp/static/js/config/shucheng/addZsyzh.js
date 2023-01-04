/**
 * author: <a href="mailto:zhuyong@gtmap.cn>zhuyong</a>
 * version 1.0.0  2019/01/31
 * describe: 新增或者编辑证书印制号模板配置处理JS
 */
layui.config({
    base: '../../../static/lib/form-select/' //此处路径请自行处理, 可以使用绝对路径
}).extend({
    formSelects: 'formSelects-v4'
});
var formSelectsVar
// 人员下拉数组
var rymcSelList = [];
layui.use(['table','laytpl','laydate','layer', 'form','formSelects'],function () {
    var $ = layui.jquery,
        layer = layui.layer,
        form = layui.form;
    formSelectsVar = layui.formSelects;

    // 默认隐藏印制号领取方式配置项
    $("#lqbm_div").hide();
    $("#lqr_div").hide();

    var qxdmCount = 0;

    // 获取区县代码字典
    $.ajax({
        url: '/realestate-inquiry-ui/rest/v1.0/zsbhmb/zd/qxdmbyuser',
        type: "GET",
        dataType: "json",
        async: false,
        timeout : 10000,
        success: function(data) {
            if(data && data.length > 0){
                // 渲染字典项
                $.each(data, function(index,item) {
                    $('#qxdm').append('<option value="'+item.XZDM +'">'+item.XZMC + '</option>');
                });
                form.render('select');
                if(data.length == 1){
                    $('#qxdm').find('option:eq(1)').prop('selected', true).attr('selected', 'selected');
                    $('#qxdm').attr('disabled','disabled');
                }
                qxdmCount = data.length;
            }
            form.render('select');
        }
    });

    // 默认获取所有用户
    $.ajax({
        url: '/realestate-inquiry-ui/rest/v1.0/user/list',
        type: "GET",
        dataType: "json",
        success: function (data) {
            if (data) {
                $.each(data, function (index, item) {
                    $('#rymc').append('<option value="' + item.alias + '">' + item.alias + '</option>');
                    rymcSelList.push({
                        name: item.alias,
                        value: item.alias,
                        id: item.id
                    })
                });
                $.unique(rymcSelList);
                formSelectsVar.data('selectBjry', 'local', {
                    arr: rymcSelList
                });
            }
        }
    });


    // 获取印制号区县对应领取方式
    var lqfs;
    $.ajax({
        url: '/realestate-inquiry-ui/rest/v1.0/zsyzh/lqfs',
        type: "GET",
        dataType: "json",
        success: function(data) {
            lqfs = data;
            // 海门页面因为固定一个区划，只有一个的时候，需要设置领取方式字段
            if(qxdmCount == 1){
                for(var key in lqfs){
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

    // 获取字典
    $.ajax({
        url: '/realestate-inquiry-ui/rest/v1.0/zsyzh/zd',
        type: "GET",
        async: false,
        dataType: "json",
        success: function(data) {
            if(data && data.zslx){
                $.each(data.zslx, function(index,item) {
                    $('#zslx').append('<option value="'+item.DM +'">' + item.MC + '</option>');
                });
            }
            form.render('select');
            // 下拉选择添加删除按钮
            renderSelectClose(form);
        }
    });

    // 获取省区代码
    $.ajax({
        url: '/realestate-inquiry-ui/rest/v1.0/zsyzh/sfdm',
        type: "GET",
        dataType: "json",
        success: function(data) {
            if(data){
                $("input[name='sqdm']").val(data);
            }
        }
    });

    // 默认设值
    var date = new Date();
    $("input[name='nf']").val(date.getFullYear());
    $("input[name='bhws']").val(11);

    /**
     * 表单校验
     */
    form.verify({
        nf : [/^[0-9]*$/, '请输入正确的年份数字'],
        number : [/^[1-9][0-9]*$/, '请输入大于0的整数'],
        bhws : function(value){
            if(value < 11){
                return '印制号位数一般至少11位';
            }
        },
        qsbh : function(qsbh){
            var jsbh = $("input[name='jsbh']").val();
            if(qsbh && jsbh && parseInt(qsbh) > parseInt(jsbh)){
                return '起始编号不能大于结束编号';
            }

            // 一次生成印制号数量控制在10万
            if(parseInt(jsbh) - parseInt(qsbh) + 1 > 100000){
                return '单次生成印制号数量不能超过10万，请修改起始编号、结束编号！';
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
        } else{
            $.each(rymcSelList, function (index, item) {
                if($("input[name='lqr']").val() == item.name ){
                    data.field.lqrid = item.id;
                }
            });
        }

        var loadIndex = loadLayer();
        $.ajax({
            url: "/realestate-inquiry-ui/rest/v1.0/zsyzh",
            type: "POST",
            data: JSON.stringify(data.field),
            contentType: 'application/json',
            dataType: "json",
            success: function (data) {
                // 提交成功会返回更新数量，正常应该大于0，失败会返回状态信息JSON
                if(data && $.isNumeric(data) && data > 0){
                    //closeWin();
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

    // 根据选择的区划设置对应的印制号领取方式配置项
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
      /*      var index = parent.parent.parent.layer.getFrameIndex(window.name);
            parent.parent.parent.layer.close(index);*/
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
            msg = "提交失败，指定区间的部分印制号已经存在！";
        }

        alertMsg(msg);
    }
});


