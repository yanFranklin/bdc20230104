/**
 * 持件信息编辑页面
 */
var form;
var isSubmit = true;
layui.use(['layer', 'table', 'jquery', 'form','laydate'], function () {
    var layer = layui.layer;
    var $ = layui.jquery;
    var laydate = layui.laydate;
    var table = layui.table;
    form = layui.form;

    // 渲染持件时间
    laydate.render({
        elem: '#cjsj',
        format: 'yyyy-MM-dd HH:mm:ss',
    });
});

//渲染页面数据
function renderForm(data){
    $("#cjxxid").val(data.cjxxid);
    $("#lsh").val(data.lsh);
    $("#slbh").val(data.slbh);
    $("#qlr").val(data.qlr);
    $("#cjr").val(data.cjr);
    $("#cjsj").val(Format(formatDate(new Date()), "yyyy-MM-dd hh:mm:ss"));
    $("#bz").val(data.bz);
    $("#cjrid").val(data.cjrid);
    $("#qlr").val(data.qlr);
    $("#zl").val(data.zl);
    $("#bz").val(data.bz);
    setTimeout(function () {
        layui.form.render();
        disabledAddFa();
    }, 10);
}

function getBdcCjxx(){
    var obj = {
        cjxxid : $("#cjxxid").val(),
        bz : $("#bz").val()
    };
    return obj;
}

function slbhInputEvent(){
    var slbh = $("#slbh").val();
    // if(slbh.length < 17){
    if(slbh.length < 8){
        return;
    }
    var obj = {};
    $(".search").each(function (i) {
        var name = $(this).attr('name');
        var value = $(this).val();
        obj[name] = value;
    });
    addModel();
    $.ajax({
        url: getContextPath() + '/rest/v1.0/cjxx/xmxx/save',
        type: "POST",
        data: JSON.stringify(obj),
        contentType: 'application/json',
        dataType: "json",
        success: function (data) {
            removeModal();
            if(isNotBlank(data) && isNotBlank(data.cjxxid)){
                $("#qlr").val(data.qlr);
                $("#zl").val(data.zl);
                // 新增成功
                $("#slbh").val("");
                $("#cjsj").val(Format(formatDate(new Date()), "yyyy-MM-dd hh:mm:ss"));
                layer.tips('已添加', '#slbh', {tips:[2,'#32B032'],time:1500});
            }else{
                layer.tips('未查询到', '#slbh', {tips:[2,'#EE0000'],time:1500});
            }
        },
        error: function (e) {
            removeModal();
            delAjaxErrorMsg(e);
        }
    });
}

