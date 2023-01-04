layui.use(['form', 'jquery', 'element', 'table', 'laydate', 'upload'], function () {
    var form = layui.form;
    var layer = layui.layer;
    var verifyMsg = "必填项不能为空";
    form.verify({});

    /**
     * 表单数据提交
     */
    form.on('submit(saveXdryxx)', function (data) {

        if (!isSubmit) {
            layer.msg(verifyMsg, {icon: 5, time: 3000});
            isSubmit = true;
            return false;
        } else {

            addModel();
            saveZctb(data.field);
            return false;
        }
        // 禁止提交后刷新
        return false;
    });
})


/**
 * 新增资产调拨
 */
function add() {

    var layerIndex = layer.open({
        type: 2,
        shade: false,
        shadeClose: true,
        isOutAnim: false,
        content: "../../view/xdryxxb/xdryxxb.html?id=",
        area: ['960px', '450px'],
        title: '新增限定人员',
    });
}

function update(obj, data) {
    var layerIndex = layer.open({
        type: 2,
        shade: false,
        shadeClose: true,
        isOutAnim: false,
        content: "../../view/xdryxxb/xdryxxb.html?id=" + data.ID,
        area: ['960px', '450px'],
        title: '修改限定人员',
    });
    console.log(index);
}

function deleteryxx(obj, data) {
    layer.confirm('确定删除？', function (index) {
        var idList = [];
        idList.push(data.ID)
        if (idList.length > 0) {
            $.ajax({
                url: '/realestate-inquiry-ui/rest/v1.0/xdryxx?ids=' + idList.join(","),
                contentType: 'application/json',
                type: "delete",
                success: function (data) {
                    successMsg("删除成功，即将刷新页面！");
                    setTimeout(function () {
                        $("#search").click();
                    }, 2000)
                },
                error: function (e) {
                }
            });
        }
        return false;
    });
}

/**
 * 批量删除
 */
function deletepl() {
    if (checkeddata.length == 0) {
        layer.msg('<img src="../../static/lib/bdcui/images/warn-small.png" alt="">请选择需要删除的数据行!');
        return;
    }
    layer.confirm('确定删除？', function (index) {
        var idList = [];
        for (var i = 0; i < checkeddata.length; i++) {
            idList.push(checkeddata[i].ID);
        }
        if (idList.length > 0) {
            $.ajax({
                url: '/realestate-inquiry-ui/rest/v1.0/xdryxx?ids=' + idList.join(","),
                contentType: 'application/json',
                type: "delete",
                success: function (data) {
                    successMsg("删除成功，即将刷新页面！");
                    setTimeout(function () {
                        $("#search").click();
                    }, 2000)
                },
                error: function (e) {
                    delAjaxErrorMsg(e)
                }
            });
        }
        return false;
    });
}

function cancel() {
    var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
    parent.layer.close(index); //再执行关闭
}