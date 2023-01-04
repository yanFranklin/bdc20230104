/**
 * 盐城征迁单元冻结页面JS
 */
layui.use(['form', 'jquery', 'element', 'laydate'], function () {
    var form = layui.form;
    var laydate = layui.laydate;

    // 冻结操作类型：批量冻结、全部冻结
    var djlx = $.getUrlParam('djlx');

    // 获取当前登录用户信息
    $.ajax({
        url: "/realestate-inquiry-ui/rest/v1.0/zszm/userinfo",
        type: 'GET',
        dataType: 'json',
        success: function (data) {
            if(data && !isNullOrEmpty(data.alias)) {
                $("#djsqr").val(data.alias);
                $("#djsqrid").val(data.id);
                $("#username").val(data.username);
            }
        }
    });

    // 默认冻结申请时间
    var time = formatDate(new Date().getTime());
    laydate.render({
        elem: '#djsqsj',
        type: 'datetime',
        value: time,
        done: function (value, date, endDate) {
            var startDate = new Date(value).getTime();
            var endTime = new Date($('#djjssj').val()).getTime();
            if (endTime < startDate) {
                layer.msg('<img src="../../static/lib/bdcui/images/error-small.png" alt="">结束时间不能小于开始时间');
            }
        }
    });
    laydate.render({
        elem: '#djkssj',
        done: function (value, date, endDate) {
            var startDate = new Date(value).getTime();
            var endTime = new Date($('#djjssj').val()).getTime();
            if (endTime < startDate) {
                warnMsg("结束时间不能早于开始时间！");
            }
        }
    });
    laydate.render({
        elem: '#djjssj',
        done: function (value, date, endDate) {
            var startDate = new Date($('#djkssj').val()).getTime();
            var endTime = new Date(value).getTime();
            if (endTime < startDate) {
                warnMsg("结束时间不能早于开始时间！");
            }
        }
    });

    /**
     * 表单数据提交,编辑
     */
    form.on('submit(submitBtn)', function(data) {
        if(isNullOrEmpty(data.field.djyy)) {
           warnMsg("请输入冻结原因！");
           return false;
        }

        var startDate = new Date($('#djkssj').val()).getTime();
        var endTime   = new Date($('#djjssj').val()).getTime();
        if (endTime < startDate) {
            warnMsg("结束时间不能早于开始时间！");
            return false;
        }

        if(djlx == "qbdj") {
            qbdj(data);
        } else {
            pldj(data);
        }
        return false;
    });
});

/**
 * 全部冻结
 */
function qbdj(data) {
    addModel();
    $.ajax({
        url: getContextPath() + "/rest/v1.0/zq/dydj/qbdj",
        type: 'POST',
        data: JSON.stringify({"bdjdy": dyxxData, "cxcs": queryObject, "djxx": data.field}),
        dataType: "json",
        contentType: "application/json",
        success: function (res) {
            if(res && "0" == res.responsecode) {
                successMsg('冻结成功!');
                parent.setState("qbdj");
                setTimeout(function () {
                    closeWin();
                }, 1500);
            } else {
                if("1" == res.responsecode ||"2" == res.responsecode) {
                    warnMsg("未传单元信息或冻结原因信息！");
                } else  if("3" == res.responsecode) {
                    warnMsg(res.responseinfo);
                } else {
                    failMsg("冻结失败，请重试！");
                }
            }
        },
        error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr);
            removeModal();
        },
        complete: function () {
            removeModal();
        }
    });
}


/**
 * 批量冻结：选择记录冻结
 */
function pldj(data) {
    // 父页面传递数据
    if(!dyxxData) {
        warnMsg("未获取到要冻结的数据，请选择数据重试！");
        return;
    }

    var param = new Array();
    for (var i = 0; i < dyxxData.length; i++) {
        var djxx = {};
        djxx.bdcdyh  = dyxxData[i].BDCDYH;
        djxx.bdcqzh  = dyxxData[i].BDCQZH;
        djxx.zl      = dyxxData[i].ZL;
        djxx.qllx    = dyxxData[i].QLLX;
        djxx.djlx    = dyxxData[i].DJLX;
        djxx.bdclx   = dyxxData[i].BDCLX;
        djxx.qlrmc   = dyxxData[i].QLR;
        djxx.qlrzjh  = dyxxData[i].QLRZJH;
        djxx.zdzhmj  = dyxxData[i].ZDZHMJ;
        djxx.djyy    = data.field.djyy;
        djxx.djsqr   = data.field.djsqr;
        djxx.djsqrid = data.field.djsqrid;
        djxx.djwh    = data.field.djwh;
        djxx.djsqsj  = data.field.djsqsj;
        djxx.djkssj  = data.field.djkssj;
        djxx.djjssj  = data.field.djjssj;
        param.push(djxx);
    }

    addModel();
    $.ajax({
        type: 'POST',
        url: getContextPath() + "/rest/v1.0/zq/dydj",
        data: JSON.stringify(param),
        dataType: "json",
        contentType: "application/json",
        success: function (res) {
            if(res && "4" != res.responsecode) {
                successMsg('冻结成功!');
                parent.setDjState("dj");
                setTimeout(function () {
                    closeWin();
                }, 1500);
            } else {
                failMsg("冻结失败，请重试！");
            }
        },
        error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr);
            removeModal();
        },
        complete: function () {
            removeModal();
        }
    });
}

function closeWin(){
    if(parent && parent.layer) {
        parent.layer.closeAll();
    }
}

var dyxxData = new Array(), queryObject = {};

/**
 * 冻结产权查询页面传参
 * @param data 批量冻结时候：选中的待冻结数据  全部冻结： 不需要冻结数据
 * @param query 全部冻结时候传参：冻结产权查询页面查询条件
 */
window.setData = function (data, query) {
    dyxxData = data;
    queryObject = query;
}
