//页面入口
var zdList = {qdfs: [{MC: "转让", DM: 1}, {MC: "买卖", DM: 2}, {MC: "赠予", DM: 3}, {MC: "继承", DM: 3}, {MC: "个人存量房买卖转移", DM: 4}]};
var slbh = window.parent.parent.$("#slbh").val();
var slsj = window.parent.parent.$("#slsj").val();
var xmid = getQueryString("xmid");
var zxlc = window.parent.parent.getQueryString('zxlc');
var formStateId = window.parent.parent.getQueryString("formStateId");
var type = getQueryString("type");
var blxxid = getQueryString("blxxid");
var saveStatus = 0;
var form;
var laydate;
layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl', 'laydate'], function () {

    $("#queryByZl").on('click', function () {
        queryByZl();
    })
    form = layui.form;
    laydate = layui.laydate;
    addModel();
    $("#slbh").val(slbh);
    $("#slsj").val(slsj);
    $("#xmid").val(xmid);
    // 渲染字典
    if (type === "view") {
        loadBlxx(blxxid);
    } else {
        loadBlxx();
    }
    $.each(zdList.qdfs, function (index, item) {
        $('#qdfs').append('<option value="' + item.DM + '">' + item.MC + '</option>');
    });
    form.render("select");
    // 保存
    form.on("submit(saveData)", function (data) {
        // if(saveStatus == 1){
        //     ityzl_SHOW_WARN_LAYER("不可重复保存");
        //     return false;
        // }
        addModel();
        saveBlxx()
        return false;
    });


    $("#addNewBlxx").on('click', function () {
        addNewBlxx();
    })

    // 日期控件
    laydate.render({
        elem: '#djsj',
        type: 'datetime',
        format: 'yyyy-MM-dd HH:mm:ss'
    });
    laydate.render({
        elem: '#zysj',
        type: 'datetime',
        format: 'yyyy-MM-dd HH:mm:ss'
    });
});

// 保存补录信息
function saveBlxx(){
    var blxxData = $('.cdbl').serializeArray();
    var cdblObj = {};
    if (blxxData.length > 0) {
        blxxData.forEach(function (item, index) {
            cdblObj[item.name] = item.value;
        });
    }
    console.log(cdblObj);
    $.ajax({
        url: getContextPath() + "/cdlc/saveBdcCdblxx",
        type: "PATCH",
        data: JSON.stringify(cdblObj),
        contentType: 'application/json',
        dataType: "json",
        success: function (data) {
            removeModal();
            saveStatus = 1;
            form.val('blxx',data);
            ityzl_SHOW_SUCCESS_LAYER("保存成功");
            // $("#saveData").attr("disabled",true);

        },
        error: function (e) {
            removeModal();
            ityzl_SHOW_WARN_LAYER("查档补录信息保存失败");
        }
    });
}

//新增补录信息
function addNewBlxx() {
    addModel("");
    loadBlxx();
}

function loadBlxx(id) {
    getReturnData("/cdlc/blxx", {blxxid: id}, "GET", function (data) {
        //补录信息id为空的时候未查询到补录信息
        if (isNullOrEmpty(data.blxxid)) {
            data.xmid = xmid;
        }
        form.val('blxx', data);
        form.render();
        disabledAddFa();
        renderDate(form);
        getStateById("false", formStateId, "blbd", "", "");
        removeModal();
    }, function (data) {
        throw data;
    })

}
// 根据坐落查询
function queryByZl(){
    var zl = $("#zl").val();

    if(isNullOrEmpty(zl)){
        ityzl_SHOW_WARN_LAYER("请输入查询条件！");
        return;
    }

    getReturnData("/cdlc/queryByZl", {zl: zl}, "GET", function (data) {
        $("#fwzl").val(data.ZL);
        $("#jzmj").val(data.JZMJ);
        $("#qdfs").val(data.CQLY)
        $("#djsj").val(data.DJSJ);
        form.render();
        removeModal();
    }, function (data) {
        throw data;
    })
}
