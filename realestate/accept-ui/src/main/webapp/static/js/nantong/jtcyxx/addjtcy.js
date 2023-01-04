//字典列表
var zdList;
var sqrid =  $.getUrlParam('sqrid');
var form;
var isSubmit = true;
layui.use(['jquery','layer', 'table', 'jquery', 'form'], function () {
    var $ = layui.jquery;
    form = layui.form;

    $(function () {
        var verifyMsg = "必填项不能为空";
        form.verify({
            identitynew: function (value, item) {
                var msg = verifyIdNumber(value);
                if (isNotBlank(msg)) {
                    $(item).addClass('layui-form-danger');
                    isSubmit = msg.isSubmit;
                    verifyMsg = msg.verifyMsg;
                }
            }
        });

        addModel();
        setTimeout(function () {
            try {
                $.when(loadZdData()).done(function () {
                    if(zdList.zjzl && zdList.zjzl.length > 0){
                        $.each(zdList.zjzl, function (index, item) {
                            $('#zjzl').append('<option value="' + item.DM + '">' + item.MC + '</option>');
                        });
                        form.render("select");
                    }
                    if(zdList.ysqrgx && zdList.ysqrgx.length > 0){
                        $.each(zdList.ysqrgx, function (index, item) {
                            $('#ysqrgx').append('<option value="' + item.MC + '">' + item.MC + '</option>');
                        });
                        form.render("select");
                    }
                    removeModal();
                })
            } catch (e) {
                removeModal();
                delAjaxErrorMsg(e,"加载失败");
                return;
            }
        }, 10);

        form.on("submit(saveJtcy)", function (data) {
            if (!isSubmit) {
                layer.msg(verifyMsg, {icon: 5, time: 3000});
                isSubmit = true;
                return false;
            } else {
                saveJtcy();
                return false;
            }
        });

        form.on('select(zjzl)', function (data) {
            addSfzYz(data.value, data.elem);
            form.render('select');
        });
    });
});

// 添加身份证号验证
function addSfzYz(zjzl, elem) {
    var zjhdom = $(elem).parents(".layui-inline").next(".layui-inline").find("input");
    var attrVal = $(zjhdom).attr("lay-verify");
    if (zjzl === "1" || zjzl === 1) {
        //增加身份证验证
        if (isNotBlank(attrVal)) {
            if (attrVal.indexOf("identitynew") < 0) {
                $(zjhdom).attr("lay-verify", attrVal + "|identitynew");
            }
        } else {
            $(zjhdom).attr("lay-verify", "identitynew");
        }
    } else {
        //移除身份证验证
        //增加长度大于五位验证
        if (isNotBlank(attrVal) && attrVal.indexOf("identitynew") > -1) {
            $(zjhdom).attr("lay-verify", attrVal.replace("identitynew", ""));
        }
        var newattr = $(zjhdom).attr("lay-verify");
        if (isNotBlank(newattr) && newattr.indexOf("zjhlength") < 0) {
            $(zjhdom).attr("lay-verify", newattr + "|zjhlength");
        } else if (zjzl !== null && zjzl !== "") {
            $(zjhdom).attr("lay-verify", "zjhlength");
        } else if ((zjzl === null || zjzl === "") && isNotBlank(newattr) && newattr.indexOf("zjhlength") > -1) {
            $(zjhdom).attr("lay-verify", attrVal.replace("zjhlength", ""));
        }
    }
}

function saveJtcy(){
    var obj = {};
    $(".search").each(function (i) {
        var name = $(this).attr('name');
        var value = $(this).val();
        if (value) {
            value = value.replace(/\s*/g, "");
        }
        obj[name] = value;
    });

    if (isNotBlank(sqrid)){
        obj["sqrid"] = sqrid;
    }
    var jtcyxxList = [];
    jtcyxxList.push(obj);
    addModel();
    getReturnData("/slym/jtcy",JSON.stringify(jtcyxxList),"PATCH",function () {
        removeModal();
        ityzl_SHOW_SUCCESS_LAYER("保存成功");
    },function (error) {
        removeModal();
        delAjaxErrorMsg(error);
    })
}

function loadZdData() {
    $.ajax({
        url: getContextPath() + "/bdczd",
        type: 'POST',
        dataType: 'json',
        async: false,
        success: function (data) {
            if (isNotBlank(data)) {
                zdList = data;
            }
        }
    });
}