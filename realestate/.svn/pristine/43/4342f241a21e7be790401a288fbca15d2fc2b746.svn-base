/**
 * 获取字典
 */
var zdData;
$.ajax({
    url: '/realestate-register-ui/rest/v1.0/qlxx/zd',
    type: "GET",
    async: false,
    dataType: "json",
    timeout: 10000,
    success: function (data) {
        zdData = data;
    }
});


/**
 * 提交保存领证人信息
 * @param url
 * @param bdcFzjlZsDTO
 * @returns {boolean}
 */
function ajaxPostLzrxx(url, bdcFzjlZsDTO) {
    // loading加载
    addModel();
    $.ajax({
        url: url,
        type: "POST",
        data: JSON.stringify(bdcFzjlZsDTO),
        contentType: 'application/json',
        dataType: "json",
        success: function (data) {
            if (data && data.code && data.code == 1) {
                warnMsg(data.msg);
            } else if (data && data > 0) {
                successMsg("领证人信息保存成功！");
                window.location.reload();
            } else if (data < 0) {
                warnMsg("保存领证人信息失败，请重试！");
            }
        }
    });

    removeModel();
    // 禁止提交后刷新
    return false;
}

/**
 * 批量修改
 * @param obj
 */
function plxg(selectZsid, isSelectAll) {
    //小弹出层
    layer.open({
        title: '领证人批量修改',
        type: 1,
        area: ['450px', '300px'],
        btn: ['保存', '重置'],
        content: $('#bdc-popup-small')
        , yes: function (index, layero) {
            //提交 的回调
            plxgUpdateLzrxx(selectZsid, isSelectAll);
            // 更新发证人信息
            updateFzr();
        }
        , btn2: function (index, layero) {
            //重置 的回调
            $("#lzrForm")[0].reset();
            return false;
        }
        , cancel: function () {
            //右上角关闭回调
            $('#bdc-popup-small').removeAttr('style');
            $("#lzrForm")[0].reset();
            //return false 开启该代码可禁止点击该按钮关闭
        }
        , success: function () {

        }
    });
}

/**
 * 批量修改领证人信息
 */
function plxgUpdateLzrxx(selectZsid, isSelectAll) {
    var bdcFzjlZsDTO = {};
    var data = $('#lzrForm').serializeArray();
    var isSubmit = true;
    var msg = "必填项不能为空";
    var lzrzjzl;
    $.each(data, function (index, item) {
        if (item.name == "plxg_lzr") {
            if (isNullOrEmpty(item.value)) {
                $("input[name='plxg_lzr']").addClass('layui-form-danger');
                isSubmit = false;
                return false;
            }
            bdcFzjlZsDTO["lzr"] = item.value;
        }
        if (item.name == "plxg_lzrzjh") {
            if (isNullOrEmpty(item.value)) {
                $("input[name='plxg_lzrzjh']").addClass('layui-form-danger');
                isSubmit = false;
                return false;
            }
            if (lzrzjzl == '1') {
                var yzxx = verifyIdNumber(item.value, item);
                if (!isEmptyObject(yzxx)) {
                    isSubmit = false;
                    msg = yzxx.verifyMsg;
                    return false;
                }
            }
            bdcFzjlZsDTO["lzrzjh"] = item.value;
        }
        if (item.name == "plxg_lzrzjzl") {
            if (isNullOrEmpty(item.value)) {
                $("select[name='plxg_lzrzjzl']").addClass('layui-form-danger');
                isSubmit = false;
                return false;
            }
            bdcFzjlZsDTO["lzrzjzl"] = item.value;
            lzrzjzl = item.value;
        }
    });
    if (!isSubmit) {
        warnMsg(msg);
        isSubmit = true;
        return false;
    }

    // 全选传gzlslid，部分数据选择传选择的zsid
    var url = "/realestate-register-ui/rest/v1.0/fzjl/lzr";
    if (isSelectAll) {
        url = "/realestate-register-ui/rest/v1.0/fzjl/lzr?gzlslid=" + processInsId;
    } else {
        bdcFzjlZsDTO["zsidList"] = selectZsid;
    }
    ajaxPostLzrxx(url, bdcFzjlZsDTO);
    // 禁止提交后刷新
    return false;
}

/**
 * 自动更新发证人
 */
function updateFzr() {
    var url = "/realestate-register-ui/rest/v1.0/fzjl/fzr?gzlslid=" + processInsId;
    $.ajax({
        url: url,
        contentType: "application/json;charset=utf-8",
        type: "GET",
        async: false,
        dataType: "json",
        success: function (data) {
        }
    });
}

/**
 * 批量修改，身份证读卡器设置
 * @param element
 */
function plxgLzrReadIdCard(element) {
    try {
        var objTest = readIdCard();
        if (objTest.ReadCard()) {
            var pName = objTest.Name;
            var pCardNo = objTest.ID;

            $("#plxg_lzr").val(trimStr(pName));
            $("#plxg_lzrzjh").val(trimStr(pCardNo));
        } else {
            layer.alert("请检查是否安装成功并重新放置！");
        }
    } catch (objError) {
        layer.alert("Fail to create object. error:" + objError.description);
    }
}

/**
 * 点击页面上“领证人签字”单独签字，单独签字优先级高于批量签字，即单独签字内容会覆盖右上角签字的内容
 * @param lzrqzIndex 领证人顺序号
 */
var signStreamData2 = {};
window.lzrqz2 = function(lzrqzIndex) {
    if(!lzrqzIndex && 0 !== lzrqzIndex) {
        warnMsg("未查找到领证人顺序信息，请刷新页面重试");
        return;
    }

    layer.open({
        title: '领证人签字',
        type: 2,
        area: ['700px','430px'],
        content: "/realestate-register-ui/nantong/fzjl/qzb.html"
        ,end: function (index, layero) {
            var signStream = layui.data('signStream');
            // TODO 测试
            // signStream.data = "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";
            if(signStream && signStream.data) {
                signStreamData2[lzrqzIndex] = signStream.data;
                $(".lzrqz" + lzrqzIndex).attr("src","data:image/png;base64," + signStream.data);
                $(".lzrqz" + lzrqzIndex).show();
            }
        }
    });
}

function checkState(slbh) {
    var result = {};
    result.state = false;
    $.ajax({
        url: "/realestate-register-ui/rest/v1.0/fzjl/check?slbh=" + slbh,
        type: "GET",
        async: false,
        success: function (data) {
            result.state = data.state;
            result.taskData = data.taskData;
            result.sfzt = data.sfzt;
        }, complete: function (XMLHttpRequest, textStatus) {

        }
    });
    return result;
}