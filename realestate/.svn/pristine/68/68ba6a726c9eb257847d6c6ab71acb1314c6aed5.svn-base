/**
 * @author <a href ="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @description 调拨查询
 */
var isSubmit = true;
layui.use(['form', 'jquery', 'element', 'table', 'laydate','upload'], function () {
    var form = layui.form;
    var layer = layui.layer;
    var verifyMsg = "必填项不能为空";
    form.verify({
    });

    /**
     * 表单数据提交
     */
    form.on('submit(saveZctb)', function(data) {

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
function add(){

    layer.open({
        type: 2,
        shade: false,
        shadeClose: true,
        isOutAnim: false,
        content: "../../yancheng/tbcx/add.html",
        area: ['960px', '300px'],
        title: '新增资产调拨',
    });
}

function edit(data) {
    layer.open({
        type: 2,
        shade: false,
        shadeClose: true,
        isOutAnim: false,
        content: "../../yancheng/tbcx/edit.html?id="+data.data.ID,
        area: ['960px', '300px'],
        title: '修改资产调拨',
    });
}

/**
 * 保存
 * @param data
 */
function saveZctb(data){
    $.ajax({
        url: "/realestate-inquiry-ui/rest/v1.0/tbcx/save",
        type: "POST",
        data: JSON.stringify(data),
        contentType: 'application/json',
        async: false,
        dataType: "json",
        success: function (data) {
            removeModal();
            if(data){
                $("#id").val(data.id);
                if($("#wjzxid").val() == ""){
                    layer.confirm('保存成功，是否上传附件?', {
                        btn: ['是', '否']
                    }, function(index, layero){
                        layer.close(index);
                        clsc();
                        //是
                    }, function(index){
                        layer.close(index);
                        //否
                    });
                }else{
                    successMsg("保存成功！");
                }
            } else {
                alertMsg("提交失败，请检查填写内容!");
            }
        },
        error:function(err){
            removeModal();
            delAjaxErrorMsg(err)
        }
    });
}

/**
 * 附件查看
 */
function view(obj, data){
    if(isNotBlank(data.WJZXID)){
        viewWjglFj(data.ID,"clientId", data.WJZXID,'查看附件',1);
    }else{
        getReturnData("/rest/v1.0/fjcl/folder", {gzlslid: data.ID, wjjmc: data.WH}, 'GET', function (result) {
            if (isNotBlank(result)) {
                viewWjglFj(data.ID,"clientId", result.id,'查看附件',1);
            }else{
                layer.msg("查看附件失败，生成文件夹目录失败");
            }
        }, function (err) {
            delAjaxErrorMsg(err)
        });
    }
}

/**
 * 批量删除
 */
function batchDelete(){
    if(checkeddata.length == 0){
        layer.msg('<img src="../../static/lib/bdcui/images/warn-small.png" alt="">请选择需要删除的数据行!');
        return;
    }
    layer.confirm('确定删除？', function (index) {
        var tbcxList = [];
        for(var i=0;i<checkeddata.length;i++){
            var tbcx = {};
            tbcx["id"] = checkeddata[i].ID;
            tbcx["wjzxid"] = checkeddata[i].WJZXID;
            tbcxList.push(tbcx);
        }
        if(tbcxList.length > 0){
            $.ajax({
                url: '/realestate-inquiry-ui/rest/v1.0/tbcx/batchDelete',
                data:JSON.stringify(tbcxList),
                contentType: 'application/json',
                dataType: "json",
                type: "delete",
                success: function (data) {
                    if(data){
                        successMsg("删除成功，即将刷新页面！");
                        setTimeout(function () {
                            $("#search").click();
                        },2000)
                    }
                },
                error: function (e) {
                }
            });
        }
        return false;
    });
}

/**
 * 导出文件（导出资产调拨对应的附件 非数据）
 */
function exportFiles(){
    if(checkeddata.length == 0){
        layer.msg('<img src="../../static/lib/bdcui/images/warn-small.png" alt="">请选择需要导出文件的数据行!');
        return;
    }
    var fileUrl = "/storage/rest/files/zip/download?";
    var idParam = "";
    for(var i=0;i<checkeddata.length;i++){
        if(checkeddata[i].WJZXID){
            idParam += "id="+checkeddata[i].WJZXID+"&"
        }
    }
    if(idParam){
        idParam = idParam.substring(0,idParam.length-1);
        window.open(fileUrl+idParam);
    }else{
        layer.msg('<img src="../../static/lib/bdcui/images/warn-small.png" alt="">勾选的数据均无附件!');
        return;
    }

}

// 上传时 先要保存资产调拨信息
function clsc(){
    if(!isNotBlank($("#id").val())){
        layer.alert("请先保存资产调拨数据！");
        return false;
    }
    openWjsc($("#id").val(),$("input[name='wh']").val());
}

//打开附件上传页面
function openWjsc(id, wjjmc){
    if(id){
        getReturnData("/rest/v1.0/fjcl/folder", {gzlslid: id, wjjmc: wjjmc}, 'GET', function (data) {
            if (isNotBlank(data)) {
                // 保存文件中心id至资产调拨信息表中
                saveWjzxid(id, data.id);
                var wjglCs = getWjglCs(id,"clientId", data.id, 2);
                if(isNullOrEmpty(wjglCs.token) ||isNullOrEmpty(wjglCs.spaceId)){
                    layer.alert("缺失附件参数");
                    return false;
                }
                var url = "/realestate-accept-ui/view/slym/sjd.html?paramJson=" + encodeURI(JSON.stringify(wjglCs));
                var index = parent.layer.open({
                    title: "上传附件",
                    type: 2,
                    area: ['1150px', '600px'],
                    content: url
                });
                parent.layer.full(index);
            }else{
                layer.msg("文件夹生成失败");
            }
        }, function (err) {
            delAjaxErrorMsg(err)
        }, false);
    }
}

// 更新文件中心ID
function saveWjzxid(id, wjzxid) {
    var obj = {
        id: id,
        wjzxid: wjzxid
    }
    $.ajax({
        url: "/realestate-inquiry-ui/rest/v1.0/tbcx/save",
        type: "POST",
        data: JSON.stringify(obj),
        contentType: 'application/json',
        async: false,
        success: function (data) {
            $("#wjzxid").val(wjzxid);
        }
    })
}

function cancel(){
    var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
    parent.layer.close(index); //再执行关闭
}