/**
 * 分配盒号
 */
layui.use(['form', 'jquery', 'laydate', 'element', 'table','laytpl'], function () {
     var $ = layui.jquery,
         form = layui.form,
         laytpl = layui.laytpl;

    loadXzdm();
    function loadXzdm(){
        $.ajax({
            url: "/realestate-register-ui/rest/v1.0/gdxxfphh/getxzdm",
            type: "GET",
            contentType: 'application/json',
            success: function (data) {
                if(data != null){
                    $.each(data, function(index,item){
                        var xzdm = item.split(":");
                        if(xzdm.length == 2){
                            $("#xzdm").append('<option value="'+xzdm[1]+'">'+xzdm[0]+'</option>');
                        }else{
                            warnMsg("乡镇代码筛选配置项不正确，无法加载！");
                            return false;
                        }
                    })
                    form.render();
                }else{
                    warnMsg("没有配置乡镇代码筛选项！");
                }
            },
            error: function () {
                failMsg("获取页面乡镇代码筛选项失败！");
            }
        });
    };

    // 保存
    form.on("submit(cxhh)", function (data) {
        if(isNullOrEmpty($("#xzdm").val())) {
            // 可以不选择盒号，后台自动赋值现有最大盒号下一数字
            warnMsg("请输入乡镇代码！");
            return false;
        }
        // 支持按输入的归档号范围中的年份进行查询分配
        var nf = "";
        var ajhFirstHalf = $("#ajhFirstHalf").val();
        if(isNotBlank(ajhFirstHalf)){
            nf = ajhFirstHalf.substr(3);
        }
        //加载盒号列表到页面
        $.ajax({
            url: "/realestate-register-ui/rest/v1.0/gdxxfphh/hh?xzdm=" + $("#xzdm").val()+ "&nf=" + nf,
            type: "GET",
            async: false,
            success: function (data) {
                generateHh(data);
            },
            error: function (xhr, status, error) {
                delAjaxErrorMsg(xhr)
            }
        });

        return false;
    });

    function generateHh(hhList) {
        $("#sxh").empty();
        $("#sxh").append('<option value="">请选择</option>');
        hhList.forEach(function(item,index){
            $("#sxh").append('<option value="'+item+'">'+item+'</option>');
        })

        form.render();
    }

    // 保存
    form.on("submit(fphh)", function (data) {
        if(isNullOrEmpty($("#sxh").val())) {
            // 可以不选择盒号，后台自动赋值现有最大盒号下一数字
        }

        if(isNullOrEmpty($("#ajhFirstHalf").val()) || isNullOrEmpty($("#ajhStart").val()) || isNullOrEmpty($("#ajhEnd").val())) {
            warnMsg("请输入要分配的档案号！");
            return false;
        }

        check();
        return false;
    });

    // 查询是否分配
    function check(){
        var cdxxData = $('.search').serializeArray();
        var cdxxObj = {};
        if (cdxxData.length > 0) {
            cdxxData.forEach(function (item, index) {
                cdxxObj[item.name] = item.value;
            });
        }
        $.ajax({
            url:  "/realestate-register-ui/rest/v1.0/gdxxfphh/sffphh" ,
            type: "GET",
            data: cdxxObj,
            contentType: 'application/json',
            dataType: "json",
            success: function (data) {
                if (data.length==0) {
                    // 调用分配盒号方法
                    saveFphh(cdxxObj);
                } else {
                    layer.open({
                        title: '已分配项'
                        ,content: "档案号  "+ data[0].ajh + "  已分配，盒号为  " + data[0].sxh
                        ,success: function () {
                            removeModel();
                        }
                    });
                }
            },
            error: function (e) {
                ityzl_SHOW_WARN_LAYER("保存失败");
            }
        });
    }

    // 分配盒号
    function saveFphh(cdxxObj){
        $.ajax({
            url:  "/realestate-register-ui/rest/v1.0/gdxxfphh/fphh" ,
            type: "GET",
            data: cdxxObj,
            dataType: "text",
            success: function (data) {
                if(data && !isNullOrEmpty(data)) {
                    warnMsg(data);
                } else {
                    layer.msg('分配成功');
                    successMsg("分配成功！");
                    removeModel();
                }
            },
            error: function (e) {
                ityzl_SHOW_WARN_LAYER("分配失败");
                removeModel();
            }
        });
    }
    // 打印盒内目录
    form.on("submit(printhnml)", function (data) {
        var sxh = $("#sxh").val();
        var xzdm = $("#xzdm").val();
        if(isNullOrEmpty(xzdm) || isNullOrEmpty(sxh)){
            warnMsg("请选择乡镇代码和档案盒号！");
            return false;
        }
        //设置打印信息
        var dylxArry = ["hnml",]
        var sessionKey = "hnml";
        setDypzSession(dylxArry, sessionKey);
        var dylx = "hnml";
        var modelUrl = hnmlMpdelUrl;
        var dataUrl = getIP() + "/realestate-register-ui/rest/v1.0/print/hnml/"+xzdm+"/"+sxh+"/xml";
        printChoice(dylx,"realestate-register-ui", dataUrl, modelUrl,false,sessionKey);
        return false;
    });


});
