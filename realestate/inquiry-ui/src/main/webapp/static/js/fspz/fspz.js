/**
 * 非税配置 js,自定义查询台账调用
 */


/**
 * 新增
 */
function addfspz() {
    layer.open({
        type: 2,
        shade: false,
        shadeClose: true,
        isOutAnim: false,
        content: "../fspz/fspzEdit.html?moduleCode=4111",
        area: ['960px', '550px'],
        title: '新增非税配置',
    });
}

/**
 * 修改按钮，修改非税配置
 */
function updatefspz(obj, data) {
    layer.open({
        type: 2,
        shade: false,
        shadeClose: true,
        isOutAnim: false,
        content: "../fspz/fspzEdit.html?moduleCode=4111&fspzid=" + data.FSPZID,
        area: ['960px', '550px'],
        title: '修改非税配置',
    });
}

/**
 * 删除非税配置
 */
function deletefspz() {
    var idList = new Array();

    if (checkeddata.length == 0) {
        warnMsg("请选择至少一条数据！");
        return;
    }

    $.each(checkeddata, function (index, data) {
        idList.push(data.FSPZID);
    });
    console.log(idList);
    var deleteIndex = layer.open({
        type: 1,
        skin: 'bdc-small-tips',
        title: '确认删除',
        area: ['320px'],
        content: '是否确认删除？',
        btn: ['确定', '取消'],
        btnAlign: 'c',
        yes: function () {
            addModel();
            $.ajax({
                url: "/realestate-inquiry-ui/rest/v1.0/fspz/delete?fspzidList=" + idList,
                type: 'GET',
                success: function (data) {
                    removeModal();
                    setTimeout(removeModal(), 100);
                    layui.table.reload('pageTable', {page: {curr: 1}});
                    initUploadInst();
                    layer.msg("删除成功");
                }, error: function (xhr, status, error) {
                    removeModal();
                }
            })
            layer.close(deleteIndex);
        },
        btn2: function () {
            //取消
            layer.close(deleteIndex);
        }
    });

}

function submitCheckParam() {
    var msg = "";
    if ($("#nf").length > 0) {
        var nf = $("#nf").val();
        if (isNullOrEmpty(nf)) {
            msg = "请选择备案年份";
        }
    }
    return msg;
}