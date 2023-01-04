var tableName;
layui.use(['form','jquery','laydate','element','layer'],function () {
    var $ = layui.jquery,
        form = layui.form,
        layer = layui.layer,
        laydate = layui.laydate;

    $(function () {

        //滚动时头部固定
        var $cnotentTitle = $('.bdc-content-fix');
        var $navContainer = $('.bdc-nav-container');
        function defaultStyle() {
            if($cnotentTitle.length == 1){
                $('.bdc-form-div').css('padding-top','68px');
            }
            if($(window).scrollTop() > 85){
                $cnotentTitle.css('top','0');
                $navContainer.css('top','58px');
            }else if($(window).scrollTop() <= 85){
                $cnotentTitle.css('top','10px');
                $navContainer.css('top','68px');
            }
        }

        $(window).resize(function(){
            defaultStyle();
        });
        $(window).on('scroll',function () {
            if($(this).scrollTop() > 85){
                $cnotentTitle.css('top','0');
                $navContainer.css('top','58px');
            }else if($(this).scrollTop() <= 85){
                $cnotentTitle.css('top','10px');
                $navContainer.css('top','68px');
            }
        });

        form.on("submit(save)", function (data) {
            addModel();
            setTimeout(function () {
                try {
                    $.when(saveXmxx(), saveQlxx()).done(function () {
                        removeModal();
                        ityzl_SHOW_SUCCESS_LAYER("保存成功");
                    })
                } catch (e) {
                    removeModal();
                    delAjaxErrorMsg(e,"保存失败");
                    return
                }
            }, 10);
            return false;
        });

        //全选监听
        form.on('checkbox(checkFilter)', function (data) {
            if(data.elem.checked){
                $(this).parent().find("input[type='checkbox']").each(function(){
                    $(this).next().addClass("layui-form-checked");
                    $(this).prop("checked",true);
                });

            }else{
                $(this).parent().find("input[type='checkbox']").each(function(){
                    $(this).next().removeClass("layui-form-checked");
                    $(this).prop("checked",false);
                });
            }
        });

        loadZdData();



    });

});
var zdList;
function loadZdData(){
    addModel();
    $.ajax({
        url: getContextPath()+"/bdczd",
        type: 'POST',
        dataType: 'json',
        success: function (data) {
            if (isNotBlank(data)) {
                zdList = data;
                getTableName();

            }
        },error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });

}

//加载页面数据
function generateXx(tableName){
    layui.use(['form', 'jquery', 'laytpl', 'element','laydate'], function () {
        var form = layui.form;
        var laytpl = layui.laytpl;
        var tpl = bdcdyxxTpl.innerHTML,view = document.getElementById('bdcdyxx');
        //渲染数据
        laytpl(tpl).render(zdList, function (html) {
            view.innerHTML = html;
        });
        if(isNotBlank(tableName)) {
            var qllxTpl = document.getElementById(tableName);
            if(qllxTpl != null) {
                //目前暂不支持所有权利的批量修改，不支持不展示
                tpl = qllxTpl.innerHTML, view = document.getElementById('qlxx');
                //渲染数据
                laytpl(tpl).render(zdList, function (html) {
                    view.innerHTML = html;
                });
            }

        }
        form.render("select");
        form.render("checkbox");
        //给下拉框添加删除图标
        renderSelectClose(form);
        renderDate(form);
        getStateById("", formStateId, 'plxg');
    })

}

//权利信息保存
function saveQlxx(){
    var xmids =sessionStorage.getItem('plxg_xmids');
    if(isNotBlank(tableName)) {
        var qlData = {};
        var qlxxArray = $(".qlxx").serializeArray();
        qlxxArray.forEach(function (item, index) {
            var name =item.name;
            if(isNotBlank(item.value)){
                qlData[name] = item.value;
            }
        });
        var className ="";
        if(tableName ==="bdc_fdcq") {
            className = "cn.gtmap.realestate.common.core.domain.BdcFdcqDO";
        }else if(tableName ==="bdc_dyaq"){
            className = "cn.gtmap.realestate.common.core.domain.BdcDyaqDO";
            if(qlData.dyfs == "2" && qlData.bdbzzqse != ""){
                qlData.zgzqqdse = qlData.bdbzzqse;
                qlData.zgzqqdss = '抵押贷款;' + qlData.bdbzzqse;
            }
        }else if(tableName ==="bdc_jsydsyq"){
            className = "cn.gtmap.realestate.common.core.domain.BdcJsydsyqDO";
        }else if(tableName ==="bdc_cf"){
            className = "cn.gtmap.realestate.common.core.domain.BdcCfDO";
        }else if(tableName ==="bdc_yg"){
            className = "cn.gtmap.realestate.common.core.domain.BdcYgDO";
        }else if( tableName ==="bdc_fdcq3"){ // 同步更新共有信息数据
            className = "cn.gtmap.realestate.common.core.domain.BdcFdcq3GyxxDO";
            qlData.jgzwmj = $("input[name='dzwmj']").val();
        }
        if (!$.isEmptyObject(qlData) &&isNotBlank(className)) {
            //验证所在层与总层数
            checkSzcAndZcs(qlData.szc,qlData.zcs);
            var bdcDjxxUpdateQO = {};
            var whereMap = {};
            whereMap.gzlslid = processInsId;
            if(isNotBlank(xmids)) {
                whereMap.xmids = xmids.split(",");
            }
            if(isNotBlank(djxl)){
                whereMap.djxl = djxl;
            }
            bdcDjxxUpdateQO.whereMap = whereMap;
            bdcDjxxUpdateQO.className = className;
            bdcDjxxUpdateQO.jsonStr = JSON.stringify(qlData);
            getReturnData("/slym/ql/updateBatchBdcQl", JSON.stringify(bdcDjxxUpdateQO), 'PATCH', function (data) {

            }, function (err) {
                throw err;
            });
        }
    }


}

//项目信息保存
function saveXmxx(){
    var xmids =sessionStorage.getItem('plxg_xmids');
    var bdcXmData = {};
    var bdcdyxxArray = $(".bdcdyxx").serializeArray();
    bdcdyxxArray.forEach(function (item, index) {
        var name =item.name;
        if(isNotBlank(item.value)){
            bdcXmData[name] = item.value;
        }
    });
    if(!$.isEmptyObject(bdcXmData)) {
        var bdcDjxxUpdateQO = {};
        var whereMap = {};
        whereMap.gzlslid = processInsId;
        if(isNotBlank(xmids)) {
            whereMap.xmids = xmids.split(",");
        }
        if(isNotBlank(djxl)){
            whereMap.djxl = djxl;
        }
        bdcDjxxUpdateQO.whereMap = whereMap;
        bdcDjxxUpdateQO.jsonStr = JSON.stringify(bdcXmData);
        getReturnData("/slym/xm/updateBatchBdcXm", JSON.stringify(bdcDjxxUpdateQO), 'PATCH', function (data) {

        }, function (err) {
            throw err;
        });
    }

}

//判断表名
function getTableName(){
    $.ajax({
        url: getContextPath()+"/slym/ql/tableName",
        type: 'GET',
        data: {xmid:xmid,zxlc:zxlc},
        success: function (data) {
            tableName =data;
            removeModal();
            generateXx(data);
        },error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });
}

