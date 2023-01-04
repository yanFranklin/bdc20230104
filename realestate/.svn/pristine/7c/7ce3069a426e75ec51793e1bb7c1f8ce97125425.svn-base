/**
 * 机构配置 js
 */
var form;
layui.use(['form', 'jquery', 'laydate', 'element', 'table'], function () {
    var $ = layui.jquery,
        form = layui.form;

    $(function () {

        // 加载备案年份
        if($("#nf").length > 0){
            var date = new Date;
            var currentYear = date.getFullYear();
            var allYears = [];
            allYears.push(currentYear);
            for(var i = 1 ; i <= 5; i++){
                allYears.push(currentYear+i);
                allYears.push(currentYear-i);
            }
            allYears.sort();
            console.info(allYears);

            $.each(allYears,function(index, value){
                $("#nf").append("<option value='" + value + "'>" + value + "</option>")
            });
            $("#nf").val(currentYear);
            form.render("select");
        }
    });

});

/**
 * 新增
 */
function xzjgpz() {
    layer.open({
        type: 2,
        shade: false,
        shadeClose: true,
        isOutAnim: false,
        content: "../jgpz/jgpzEdit.html?moduleCode=4111",
        area: ['960px', '600px'],
        title: '新增机构配置',
    });
}
/**
 * 修改
 */
function jgxg(obj, data) {
    layer.open({
        type: 2,
        shade: false,
        shadeClose: true,
        isOutAnim: false,
        content: "../jgpz/jgpzEdit.html?moduleCode=4111&jgid=" + data.JGID,
        area: ['960px', '600px'],
        title: '修改机构配置',
    });
}

/**
 * 附件查看
 */
function fjck(obj, data){
    if(isNotBlank(data.WJZXID)){
        viewWjglFj(data.JGID,"clientId", data.WJZXID,'查看附件',1);
    }else{
        getReturnData("/rest/v1.0/fjcl/folder", {gzlslid: data.JGID, wjjmc: "机构材料"}, 'GET', function (result) {
            if (isNotBlank(result)) {
                viewWjglFj(data.JGID,"clientId", result.id,'查看附件',1);
            }else{
                layer.msg("查看附件失败，生成文件夹目录失败");
            }
        }, function (err) {
            delAjaxErrorMsg(err)
        });
    }
}
/**
 * 删除机构配置
 */
function scjgpz() {
    var jgidList = new Array();

    if (checkeddata.length == 0){
        warnMsg("请选择至少一条数据！");
        return;
    }
    $.each(checkeddata, function (index, data) {
        jgidList.push(data.JGID);
    });

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
                url: "/realestate-inquiry-ui/rest/v1.0/jgpz/delete?jgidList=" + jgidList,
                type: 'GET',
                success: function (data) {
                    removeModal();
                    setTimeout(removeModal(), 100);
                    layui.table.reload('pageTable',{page:{curr:1}});
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

function submitCheckParam(){
    var msg = "";
    if($("#nf").length > 0){
        var nf = $("#nf").val();
        if(isNullOrEmpty(nf)){
            msg = "请选择备案年份";
        }
    }
    return msg;
}