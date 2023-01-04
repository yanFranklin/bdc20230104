var gzlslid = $.getUrlParam('gzlslid');
var form;
var isSubmit = true;
var zdList = [];
layui.use(['layer', 'table', 'jquery', 'form','laydate'], function () {
    var layer = layui.layer;
    var $ = layui.jquery;
    var laydate = layui.laydate;
    var table = layui.table;
    form = layui.form;

    zdList = getMulZdList("sdlx");
    // 渲染锁定时间
    laydate.render({
        elem: '#cjsj',
        format: 'yyyy-MM-dd',
        //type: 'datetime',
    });

    $(function () {
        var verifyMsg = "必填项不能为空";
        form.verify({
            identitynew: function (value, item) {
                var msg = checkSfzZjh(value);
                if (isNotBlank(msg)) {
                    $(item).addClass('layui-form-danger');
                    isSubmit = false;
                    verifyMsg = msg;
                }
            }
        });

        addModel();
        setTimeout(function () {
            try {
                $.when(generateBdcZssdxx()).done(function () {
                    removeModal();
                })
            } catch (e) {
                removeModal();
                delAjaxErrorMsg(e,"加载失败");
                return
            }
        }, 10);

        form.on("submit(saveBljl)", function (data) {
            if (!isSubmit) {
                layer.msg(verifyMsg, {icon: 5, time: 3000});
                isSubmit = true;
                return false;
            } else {
                saveBljl();
                return false;
            }
        });

    });
});

function generateBdcZssdxx(){
    if (isNotBlank(gzlslid)){
        if(isNotBlank(zdList) && isNotBlank(zdList['sdlx'])){
            $.each(zdList['sdlx'], function (index, item) {
                $('#sdlx').append('<option value="' + item.DM + '">' + item.MC + '</option>');
            });
        }

        $.ajax({
            url: "/realestate-inquiry-ui/rest/v1.0/hmd/info",
            type: 'GET',
            dataType: 'json',
            async: false,
            data: {gzlslid: gzlslid},
            success: function (data) {
                form.val("searchform", data);
                form.render();
            },
            error: function (xhr, status, error) {
                delAjaxErrorMsg(xhr)
            }
        })
    }
    disabledAddFa();
}

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

function saveBljl(){
    var obj = {};
    $(".search").each(function (i) {
        var name = $(this).attr('name');
        var value = $(this).val();
        if (value) {
            value = value.replace(/\s*/g, "");
        }
        obj[name] = value;
    });
    obj.hmdzt = 1;
    obj.hmdztlb = 3;
    addModel();
    $.ajax({
        url: getContextPath() + '/rest/v1.0/hmd/save',
        type: "POST",
        data: JSON.stringify(obj),
        contentType: 'application/json',
        dataType: "json",
        success: function (data) {
            if(isNotBlank(data)){
                $("#hmdid").val(data.hmdid);
            }
            removeModal();
            saveSuccessMsg();
        },
        error: function (e) {
            removeModal();
            delAjaxErrorMsg(e);
        }
    });
}

function renderForm(data){
    $("#bdcqzh").val(data.bdcqzh);
    $("#hmdid").val(data.hmdid);
    $("#gzldymc").val(data.gzldymc);
    $("#cjr").val(data.cjr);
    $("#cjsj").val(data.cjsj);
    $("#cjyy").val(data.cjyy);
    $("#ztmc").val(data.ztmc);
    $("#ztzjh").val(data.ztzjh);
    $("#bz").val(data.bz);
    $("#bdcdyh").val(data.bdcdyh);
    zdList = getMulZdList("sdlx");
    if(isNotBlank(zdList) && isNotBlank(zdList['sdlx'])){
        $.each(zdList['sdlx'], function (index, item) {
            $('#sdlx').append('<option value="' + item.DM + '">' + item.MC + '</option>');
        });
    }
    layui.form.render();
}

//身份证读卡器设置
window.onReadIdCard = function () {
    try {
        var cardInfo = readIdCard();
        if (cardInfo.ReadCard()) {
            var name = cardInfo.Name;
            var number = cardInfo.ID;

            $('input[name="ztmc"]').val(trimStr(name));
            $('input[name="ztzjh"]').val(trimStr(number));
        } else {
            warnMsg(" 请检查读卡器是否安装成功并重新放置身份证！");
        }
    } catch (objError) {
        warnMsg(" 请检查读卡器是否安装成功并重新放置身份证！");
    }
}