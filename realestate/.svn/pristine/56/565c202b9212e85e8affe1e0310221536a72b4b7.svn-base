layui.use(['jquery', 'form', 'layer', 'laytpl', 'laydate'], function () {
    var $ = layui.jquery,
        form = layui.form,
        layer = layui.layer,
        laytpl = layui.laytpl,
        laydate = layui.laydate;

    // 获取URL参数
    var tsxxid = $.getUrlParam("tsxxid");
    var yzwbh = $.getUrlParam("yzwbh");

    $(function () {
        var firstClick = true;
        // 点击按钮展示修改内容
        $(document).on('click','.xgmx',function () {
            $(this).parents('.layui-form-item').find('.hide-mx').removeClass('bdc-hide');
            if(firstClick){
                $('.bdc-major-btn').removeClass('bdc-hide');
                firstClick = false;
            }
        })

        $.ajax({
            url: "/realestate-exchange/yzw/getYzwYzsbTsxx?tsxxid="+ tsxxid +"&yzwbh="+yzwbh,
            type: "GET",
            async: false,
            success: function (data) {
                console.log(data);
                if(data){
                    generateZbmx(data);
                }else {
                    layer.alert("无数据!");
                }
            },
            error: function (err) {
                delAjaxErrorMsg(err);
            }
        });

        //提交
        form.on("submit(saveData)", function (data) {
            var submitData={};
            submitData.yzwbh =yzwbh;
            $.each($('.layui-form-item'),function(index,item){
                var obj={};
                // 获取指标明细id标识
                var mxid=$(item).attr('id');
                if(mxid &&$(item).find("p").hasClass("p-error")){
                    $(item).find(":input").each(function (i) {
                        var name = $(this).attr('name');
                        var value = $(this).val();
                        obj[name] = value;
                    });
                    submitData[mxid]=obj;
                }
            });
            $.ajax({
                url: "/realestate-exchange/yzw/saveYzwYzsbTsxgxx",
                type: "POST",
                data: JSON.stringify(submitData),
                contentType: 'application/json',
                async: false,
                success: function (data) {
                    layer.msg('<img src="../static/lib/bdcui/images/success-small.png" alt="">修改成功');
                    var cznr="修改数据"+JSON.stringify(submitData);
                    saveDetailLog("YZWSJXG", "一张网数据修改操作",yzwbh,{"XGNR": JSON.stringify(cznr)});
                    // removeModal();
                },
                error: function (err) {
                    delAjaxErrorMsg(err);
                }
            });

            return false;
        });

        // 加载指标明细内容
        function generateZbmx(data) {
            var getTpl = zbmxTpl.innerHTML;
            laytpl(getTpl).render(data, function(html){
                $('#bdc-popup-large').html(html);
                form.render();
            });
            //初始化日期控件
            lay('.test-item').each(function () {
                laydate.render({
                    elem: this,
                    type: 'datetime'
                });
            });
        }

        // 保存记录操作信息
        function saveDetailLog(logType, viewTypeName,yzwbh, data){
            var json = JSON.parse(JSON.stringify(data));
            json.logType = logType;
            json.viewTypeName = viewTypeName;
            json.viewType = "exchange";
            json.yzwbh = yzwbh;
            saveLogInfo(json);
        }

        /**
         * 保存日志信息
         * @param data 需要保存的日志数据
         */
        function saveLogInfo(data) {
            $.ajax({
                url: "/rest/v1.0/log/info",
                type: "POST",
                data: JSON.stringify(data),
                contentType: 'application/json',
                success: function (key) {
                },
                error: function () {
                    console.debug("保存日志出错！");
                }
            });
        }

    });

});