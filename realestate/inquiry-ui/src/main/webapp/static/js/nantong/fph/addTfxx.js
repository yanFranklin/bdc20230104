/**
 * 添加退费信息
 */
var zdList = [];
var form,laytpl,laydate,table;
layui.use(['laytpl', 'laydate', 'layer', 'form', 'table'], function () {
    var $ = layui.jquery,
        layer = layui.layer,
        laytpl = layui.laytpl,
        laydate = layui.laydate;
        table = layui.table;
        form = layui.form;

    /**
     * 点击查询
     */
    $('#search').on('click', function () {
        search();
        return false;
    });

    function search(){
        var slbh = $("#slbh").val();
        if(isNotBlank(slbh)){
            $.ajax({
                url: getContextPath() + "/rest/v1.0/tfxx/generate?slbh="+ slbh,
                type: "GET",
                dataType: "json",
                success: function (data) {
                    generateTfxx(data);
                },
                error: function (e) {
                    showErrorInfo(e, "生成退费信息失败");
                    removeModal();
                }
            });
        }else{
            generateTfxx(null);
        }
    }

    // 加载退费信息信息
    function generateTfxx(data){
        var json = {};
        if(isNotBlank(data)){
            json.bdctfxx = data;
        }else{
            json.bdctfxx = [];
        }
        var tpl, view;
        tpl = tfxxTpl.innerHTML;
        view = document.getElementById('tfxx');
        //渲染数据
        laytpl(tpl).render(json, function (html) {
            view.innerHTML = html;
        });
        renderDate();
        renderZtree();
        form.render();
    }
});


function renderDate() {
    var laydate = layui.laydate;
    var format = "yyyy-MM-dd HH:mm:ss";
    lay('.test-item').each(function () {
        laydate.render({
            elem: this,
            format: format,
            trigger: 'click'
        });
    });
}

function renderZtree(){
    $(".ztree").each(function(index, ele){
        var treeId= $(ele).attr("id");
        console.info(treeId);
        loadOrgTree(treeId, function(event, treeId, treeNode){
            var treeDiv = $("#" + treeId).parents(".layui-inline");
            $(treeDiv).find("input[name=bmmc]").val(treeNode.name);
        });

        bindClik(ele);
    });
}

function saveTfxx(){
    var deferred = $.Deferred();
    var tfxxList = [];
    $(".basic-info").each(function(index, val){
        var tfxx = {};
       $(this).find(".tfxx").serializeArray().forEach(function (item, i) {
           tfxx[item.name] = item.value;
       });
        tfxxList.push(tfxx);
    });
    if(!isNotBlank(tfxxList)){
        warnMsg("未获取到退费信息");
    }
    $.ajax({
        url: getContextPath()+ "/rest/v1.0/tfxx/saveTfxx",
        type: 'POST',
        dataType: 'json',
        contentType: "application/json;charset=UTF-8",
        data: JSON.stringify(tfxxList),
        success: function (data) {
           deferred.resolve();
        },
        error: function (e) {
            showErrorInfo(e, "保存退费信息失败");
            removeModal();
            deferred.reject();
        }
    });
    return deferred;
}