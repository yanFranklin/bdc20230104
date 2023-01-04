$(function () {
    layui.use(['form', 'jquery', 'laytpl', 'element'], function () {
        var form = layui.form;
        var laytpl = layui.laytpl;
        var element = layui.element;
        var nullOnly = true;
        $.ajax({
            url: "../zlsx/getconfig",
            dataType: "json",
            async: false,
            success: function (data) {
                if(data.sxgz){
                    var sxgzList = data.sxgz.split(",");
                    var json = {
                        data : sxgzList
                    };
                    renderPjgz(json);
                }
                if(!data.nullOnly){
                    $("#nullOnly").removeAttr("checked");
                    nullOnly = false;
                }
                layui.use(['form', 'jquery', 'laytpl', 'element'], function () {
                    var form = layui.form;
                    form.render();
                });
            },
            error:function (e) {
                layer.msg("读取默认配置失败");
            }
        });

        // 拼接规则 关闭标签 点击事件
        $('#pjgzBtns').on('click', '.layui-tab-close',function(){
            $(this).parent().remove();
        });

        // 监听提交事件
        form.on("submit(subBtn)",function(data){
            if(!data.field.djh && !data.field.fwDcbIndex){
                layer.alert("请选择宗地或逻辑幢");
                return;
            }
            // 是否只刷新空值
            data.field.nullOnly = nullOnly;
            // 获取拼接规则
            var sxgz = "";
            $.each($("#pjgzBtns").children(),function(i){
                var innerHtml = $(this).html();
                if(innerHtml){
                    var idx = innerHtml.indexOf("<i");
                    var value = innerHtml.substr(0,idx).trim();
                    sxgz += value + ",";
                }
            });
            if(sxgz){
                sxgz = sxgz.substr(0,sxgz.length-1);
            }else{
                layer.alert("请增加拼接规则");
            }
            data.field.sxgz = sxgz;
            addModel();
            $.ajax({
                url: "../zlsx",
                dataType: "json",
                data: data.field,
                async: false,
                success: function (data) {
                    removeModal();
                    layer.alert("刷新成功");
                },
                error:function (e) {
                    layer.alert("读取默认配置失败");
                }
            });

        });

        // 只刷新控制 checkbox 监听
        form.on('switch(nullOnlyF)', function(data){
            if(data.elem.checked){
                nullOnly = true;
            }else{
                nullOnly = false;
            }
        });

        // 监听 拼接规则 字段选择下拉框
        form.on('select(chooseTable)', function(data){
            var json = {
                data: []
            };
            if(data.value == "FW_LJZ"){
                json.data = ["ZLDZ","FWMC"];
            }
            if(data.value == "FW_HS"){
                json.data = ["DYH","FJH"];
            }
            //获取模板
            var tpl = $("#chooseTableTpl").html();
            //渲染数据
            laytpl(tpl).render(json, function (html) {
                $("#chooseColumn").html(html);
            });
            form.render();
        });

        // 监听拼接类型单选框
        form.on('radio(pjtype)', function(data){
            layHide($(".pjtype"));
            // 字段
            if(data.value == "zd"){
                layShow($("#zdDiv"));
            }
            // 行政区
            if(data.value == "xzq"){
                layShow($("#xzqDiv"));
            }
            // 文字
            if(data.value == "wz"){
                layShow($("#wzDiv"));
            }
        });

        // 增加 字段
        $('#addZd').on('click', function(){
            var tableName = $("#chooseTable").val();
            var columnName = $("#chooseColumn").val();
            if(tableName && columnName){
                var gz = tableName + "." + columnName;
                var json = {
                    data:[gz]
                };
                renderPjgz(json);
            }else{
                layer.msg("请选择表名和字段名");
                return;
            }
            form.render();
        });

        // 增加行政区
        $('#addXzq').on('click', function(){
            var xzqdmLen = $("#xzqdmLen").val();
            var json = {
                data:["XZQMC_" + xzqdmLen]
            };
            renderPjgz(json);
            form.render();
        });

        // 增加文字
        $('#addWz').on('click', function(){
            var wz = $("#pjwz").val();
            if(wz){
                var json = {
                    data:[wz]
                };
                renderPjgz(json);
            }else{
                layer.msg("请输入文字");
                return;
            }
            form.render();
        });
    });

    $("#chooseZd").click(function () {
        var index = layer.open({
            type: 2,
            title: "选择宗地",
            area: ['960px', '437px'],
            fixed: false, //不固定
            content: '../zd/picklist'
        });
    })
    $("#chooseLjz").click(function () {
        var index = layer.open({
            type: 2,
            title: "选择逻辑幢",
            area: ['960px', '420px'],
            fixed: false, //不固定
            content: '../ljz/picklist?showQuery=lszd,ljzh'
        });
    })
});


function renderPjgz(json){
    layui.use(['form', 'jquery', 'laytpl', 'element'], function () {
        var form = layui.form;
        var laytpl = layui.laytpl;
        var element = layui.element;
        //获取模板
        var tpl = $("#pjgzTpl").html();
        //渲染数据
        laytpl(tpl).render(json, function (html) {
            if(!$("#pjgzBtns").html()){
                $("#pjgzBtns").html(html);
            }else{
                $("#pjgzBtns").append(html);
            }
        });
        form.render();
        element.render();
    })
}
function layHide(elem) {
    elem.addClass("layui-hide");
}
function layShow(elem) {
    elem.removeClass("layui-hide");
}

function pickLjzCallback(data){
    $("#fwmc").val(data.fwmc);
    $("#djh").val(data.lszd);
    $("#fwDcbIndex").val(data.fw_dcb_index);
    layui.use(['layer','form'], function () {
        var form = layui.form;
        form.render();
        layer.closeAll();
    });
}
// 宗地列表回调函数
function pickZdlistCallback(djh){
    $("#djh").val(djh);
    $("#fwmc").val("");
    $("#fwDcbIndex").val("");
    layui.use(['layer','form'], function () {
        var form = layui.form;
        form.render();
    });
}