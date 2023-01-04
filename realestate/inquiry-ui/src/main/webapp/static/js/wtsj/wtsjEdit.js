var form;
var isSubmit = true;
var zdList = [];
layui.use(['layer','jquery', 'form'], function () {
    var layer = layui.layer;
    var $ = layui.jquery;
    form = layui.form;


    $(function () {

        form.on("submit(saveWtsj)", function (data) {
            var wtnr =$("#wtnr").val();
            if(isNullOrEmpty(wtnr)){
                warnMsg("问题内容不能为空！")
            }
            var jjfa =$("#jjfa").val();
            var wtsjzt =$("#wtsjzt").val();
            if(isNullOrEmpty(wtnr) &&wtsjzt ==2){
                warnMsg("解决方案不能为空！");
            }
            if (!isSubmit) {
                layer.msg(verifyMsg, {icon: 5, time: 3000});
                isSubmit = true;
                return false;
            } else {
                saveWtsj();
                return false;
            }
        });

    });
});

function saveWtsj(){
    var obj = {};
    $(".search").each(function (i) {
        var name = $(this).attr('name');
        var value = $(this).val();
        if (value) {
            value = value.replace(/\s*/g, "");
        }
        obj[name] = value;
    });
    addModel();
    $.ajax({
        url: getContextPath() + '/rest/v1.0/wtsj/save',
        type: "POST",
        data: JSON.stringify(obj),
        contentType: 'application/json',
        dataType: "json",
        success: function (data) {
            if(isNotBlank(data)){
                $("#wtsjid").val(data.wtsjid);
            }
            removeModal();
            saveSuccessMsg();
            parent.layer.closeAll();
        },
        error: function (e) {
            removeModal();
            delAjaxErrorMsg(e);
        }
    });
}

//渲染表单数据
function renderForm(data){
    $("#wtsj").val(data.WTSJ);
    $("#wtsjid").val(data.WTSJID);
    $("#wtnr").val(data.WTNR);
    $("#jjfa").val(data.JJFA);
    zdList = getMulZdList("wtsjlb,wtsjzt,lsylwtlx");
    if(isNotBlank(zdList)){
        if(isNotBlank(zdList['wtsjlb'])) {
            $.each(zdList['wtsjlb'], function (index, item) {
                $('#wtsjlb').append('<option value="' + item.DM + '">' + item.MC + '</option>');
            });
        }
        if(isNotBlank(zdList['wtsjzt'])) {
            $.each(zdList['wtsjzt'], function (index, item) {
                $('#wtsjzt').append('<option value="' + item.DM + '">' + item.MC + '</option>');
            });
        }
        if(isNotBlank(zdList['lsylwtlx'])) {
            $.each(zdList['lsylwtlx'], function (index, item) {
                $('#lsylwtlx').append('<option value="' + item.DM + '">' + item.MC + '</option>');
            });
        }
    }
    $("#wtsjlb").val(data.WTSJLB);
    $("#wtsjzt").val(data.WTSJZT);
    $("#lsylwtlx").val(data.LSYLWTLX);
    if(data.WTSJZT ==1){
        $("#wtnr").removeAttr('disabled');
    }else if(data.WTSJZT ==2){
        $("#jjfa").removeAttr('disabled');
    }
    layui.form.render();
}
