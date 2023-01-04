/**
 * author: <a href="mailto:zhuyong@gtmap.cn>zhuyong</a>
 * version 1.0.0  2019/01/14
 * describe: 新增或者编辑证书编号模板配置处理JS
 */
layui.use(['table','laytpl','laydate','layer', 'form'],function () {
    var $ = layui.jquery,
        layer = layui.layer,
        form = layui.form;

    // 获取区县代码
    var qdxmData;
    $.ajax({
        url: '/realestate-inquiry-ui/rest/v1.0/zsbhmb/zd/qxdm',
        type: "GET",
        dataType: "json",
        timeout : 10000,
        success: function(data) {
            if(data && data.length > 0){
                // 渲染字典项
                $.each(data, function(index,item) {
                    $('#szsxqc').append('<option value="'+item.XZMC +'">'+item.XZMC + '</option>');
                });
            }

            qdxmData = data;
            form.render('select');

            // 设置市县名称下拉选中
            if(szsxqc){
                form.val("zsmbform", {"szsxqc": szsxqc});
            }

            // 下拉选择添加删除按钮
            renderSelectClose(form);
        }
    });

    // 联动对应处理
    form.on('select(szsxqc)', function(data){
        if(qdxmData && qdxmData.length > 0){
            for(var index in qdxmData){
                if(qdxmData[index].XZMC == data.value){
                    $("#qxdm").val(qdxmData[index].XZDM);
                    return;
                }
            }
        }
    });

    // 新增或者编辑标识
    var action = $.getUrlParam('action');
    // 编辑时传递的证书编号模板ID
    var zsbhmbid = $.getUrlParam('zsbhmbid');

    // 新增操作默认设值
    if("add" == action){
        var date = new Date();
        $("input[name='nf']").val(date.getFullYear());

        $("input[name='bdcqzh']").val("省份简称 (年份) 区县简称 不动产权证或证明 第 流水数字 号");
        $("input[name='cssxh']").val("1");
        $("input[name='bdcqzhws']").val("7");

        $("input[name='ylzhkg']").val(0);
        $("input[name='zsfhkg']").val(0);
        $("input[name='sqdmkg']").val(0);
        $("input[name='sqdm']")[0].setAttribute('lay-verify', '');
    }

    /**
     * 表单校验
     */
    form.verify({
        nf : [/^[0-9]*$/, '请输入正确的年份数字'],
        number : [/^[1-9][0-9]*$/, '请输入大于0的整数']
    });

    //监听指定开关
    form.on('switch(ylzhkg)', function(data){
        $("input[name='ylzhkg']").val(this.checked ? 1 : 0);
    });
    form.on('switch(zsfhkg)', function(data){
        $("input[name='zsfhkg']").val(this.checked ? 1 : 0);
    });
    form.on('switch(sqdmkg)', function(data){
        $("input[name='sqdmkg']").val(this.checked ? 1 : 0);
        if (this.checked) {
            $("input[name='sqdm']")[0].setAttribute('lay-verify', 'required');
        } else {
            $("input[name='sqdm']")[0].setAttribute('lay-verify', '');
        }
    });


    /**
     * 表单数据提交
     * 1、不管新增还是编辑需要保证：年份和区县代码 组合是唯一的
     * 2、前台验证不可靠，在后台服务逻辑中验证
     */
    form.on('submit(submitBtn)', function(data) {
        //这里处理下因为下拉树框数据清除但是隐藏ID值未清理问题
        if(isNullOrEmpty($("input[name='djjg']").val())){
            data.field.djbmdm = null;
        }

        data.field.ylzhkg = $("input[name='ylzhkg']").val();
        data.field.zsfhkg = $("input[name='zsfhkg']").val();
        data.field.sqdmkg = $("input[name='sqdmkg']").val();

        $.ajax({
            url: "/realestate-inquiry-ui/rest/v1.0/zsbhmb",
            type: "PUT",
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
            error:function(data){
                fail(JSON.parse(data.responseText));
            }
        });

        // 禁止提交后刷新
        return false;
    });

    var szsxqc ;
    /**
     * 编辑时填充数据
     * @param data
     */
    window.setData = function(data){
        // 这里直接用data赋值无效（原因未知），需要重新转JSON
        form.val('zsmbform', JSON.parse(JSON.stringify(data)));
        szsxqc = data.szsxqc;

        // 设置开关状态
        $("input[name='ylzhkg']").prop('checked', 1 == data.ylzhkg? true : false);
        $("input[name='ylzhkg']").val(data.ylzhkg);

        $("input[name='zsfhkg']").prop('checked', 1 == data.zsfhkg? true : false);
        $("input[name='zsfhkg']").val(data.zsfhkg);

        $("input[name='sqdmkg']").prop('checked', 1 == data.sqdmkg? true : false);
        $("input[name='sqdmkg']").val(data.sqdmkg);
        if (1 == data.sqdmkg) {
            $("input[name='sqdm']")[0].setAttribute('lay-verify', 'required');
        } else {
            $("input[name='sqdm']")[0].setAttribute('lay-verify', '');
        }
    };

    /**
     * 关闭弹出页面
     */
    window.closeWin = function(){
        var index=parent.layer.getFrameIndex(window.name);
        parent.layer.close(index);
    };

    /**
     * 提交成功提示
     */
    window.success = function(){
        saveSuccessMsg();
        setTimeout(function(){
            var index = parent.layer.getFrameIndex(window.name);
            parent.layer.close(index);
        },2000);
    }

    /**
     * 提交失败提示
     * @param data
     */
    window.fail = function(data){
        var msg = "提交失败，请检查填写内容!";
        if (data && data.code) {
            if (72 == data.code) {
                msg = "提交失败，该地区指定年度模板配置已经存在！";
            } else if (data.msg) {
                msg = data.msg;
            }
        }

        alertMsg(msg);
    }
});