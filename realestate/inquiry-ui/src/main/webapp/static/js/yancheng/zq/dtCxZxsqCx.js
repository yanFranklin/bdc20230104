/**
 * 征迁注销2：注销申请台账 JS
 */
layui.use(['form', 'jquery'], function () {
    var form = layui.form;
    var $ = layui.jquery;

    form.on('submit(submitZxsq)', function(data) {
        if(isNullOrEmpty(data.field.scyy)) {
            warnMsg("请输入删除原因！");
            return ;
        }

        var param = {
            "sqxxid" : data.field.sqxxid,
            "scyy": data.field.scyy
        }
        console.log(param)
        $.ajax({
            type: 'POST',
            url: getContextPath() + "/rest/v1.0/zq/zxsq/delete",
            data: JSON.stringify(param),
            dataType: "json",
            contentType: "application/json",
            success: function (data) {
                layer.msg('<img src="../../static/lib/bdcui/images/success-small.png" alt="">删除注销申请成功!');
                parent.setDeleteState();
                setTimeout(function(){parent.layer.closeAll()},1000);
             },
            error: function (xhr, status, error) {
                parent.layer.closeAll();
                delAjaxErrorMsg(xhr);
                removeModal();
            }
        });
        return false;
    });

    $('.bdc-frame-close').on('click',function () {
        var index=parent.layer.getFrameIndex(window.name);
        parent.layer.close(index);
    });
})

/**
 * 删除注销申请
 * @type {string}
 */
var deleteState = "";
function deletezxsq(obj,data){
    if(data.SHZT == 2){
        warnMsg('注销申请已提交,不能删除！');
        return;
    }
    if(data.SHZT == 3){
        warnMsg('注销申请已审核通过,不能删除！');
        return;
    }
    if(data.SHZT == 4){
        warnMsg('注销申请已删除,不能重复删除！');
        return;
    }
    console.log(obj);
    console.log(data);
    deleteState = "";
    layer.open({
        type: 2,
        title: '删除注销申请',
        anim: -1,
        shadeClose: true,
        maxmin: true,
        shade: false,
        area: ['960px', '250px'],
        offset: 'auto',
        content: [ "../../yancheng/zq/deleteZxsq.html", 'yes'],
        success: function (layero, index) {
            frames[0].$("#sqxxid").val(data.SQXXID);
        },
        end:function(){
            if("delete" == deleteState) {
                tableReload('pageTable', {data: JSON.stringify(queryObject)}, dataUrl);
            }
        }
    });
}

function setDeleteState() {
    deleteState = "delete";
}

/**
 * 编辑注销申请信息
 * @param obj
 * @param data
 */
function editzxsq(obj,data){
    if(data.SHZT == 2){
        warnMsg('注销申请已提交,不能编辑！');
        return;
    }
    if(data.SHZT == 3){
        warnMsg('注销申请已审核通过,不能编辑！');
        return;
    }
    if(data.SHZT == 4){
        warnMsg('注销申请已删除,不能编辑！');
        return;
    }

    var index = layer.open({
        type: 2,
        title: "产权信息",
        area: ['960px','400px'],
        fixed: false, //不固定
        maxmin: true, //开启最大化最小化按钮
        content: "/realestate-inquiry-ui/yancheng/zq/zxsq.html?state=edit&xmid=" + data.XMID + "&sqxxid=" + data.SQXXID,
        success: function () {
        },
        end: function(){
            tableReload('pageTable', {data: JSON.stringify(queryObject)}, dataUrl);
        }
    });
}

function setStateOfSave(stateInfo) {
    var state = stateInfo;
}

/**
 * 查看注销申请详情
 * @param obj
 * @param data
 */
function detailzxsq(obj,data){
    var index = layer.open({
        type: 2,
        title: "产权信息",
        area: ['960px','400px'],
        fixed: false, //不固定
        maxmin: true, //开启最大化最小化按钮
        content: "/realestate-inquiry-ui/yancheng/zq/zxsq.html?state=view&xmid=" + data.XMID + "&sqxxid=" + data.SQXXID,
        success: function () {
        },
        end: function(){
        }
    });
}

/**
 * 查看附件
 * @param obj
 * @param data
 */
function fj(obj,data){
    var sqxxid = data.SQXXID;
    var bdcSlWjscDTO = queryBdcSlWjscDTO();
    bdcSlWjscDTO.spaceId = sqxxid;
    bdcSlWjscDTO.storageUrl =document.location.protocol + "//" + document.location.host + "/storage";
    var url = "/realestate-inquiry-ui/yancheng/zq/sjd.html?paramJson=" + encodeURI(JSON.stringify(bdcSlWjscDTO));
    openSjcl(url, "附件上传");
}

/**
 * 查询收件材料所需参数（打开附件上传使用）
 */
function queryBdcSlWjscDTO() {
    var bdcSlWjscDTO = {};
    $.ajax({
        url: '/realestate-inquiry-ui/rest/v1.0/dwgz/getFileCs',
        type: 'GET',
        async: false,
        contentType: 'application/json;charset=utf-8',
        success: function (data) {
            if (isNotBlank(data)) {
                bdcSlWjscDTO = data;
            }
        }, error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });
    return bdcSlWjscDTO;
}

/**
 * 打开附件上传
 */
function openSjcl(url, title, xmid) {
    var width = $(window).width() + "px";
    var height = $(window).height() + "px";
    var index = layer.open({
        title: title,
        type: 2,
        content: url,
        area: [width, height],
        maxmin: true
    });
    layer.full(index);
}

/**
 * 提交注销申请
 * @param obj
 * @param data
 * @returns {boolean}
 */
function submitzxsq(obj,data){
    if(data.SHZT == 2){
        warnMsg('注销申请已提交,不能重复提交！');
        return;
    }
    if(data.SHZT == 3){
        warnMsg('注销申请已审核通过,不能再次提交！');
        return;
    }
    if(data.SHZT == 4){
        warnMsg('注销申请已删除,不能提交！');
        return;
    }

    layer.confirm("是否确认提交？",{
        btn: ["确认","取消"],
    },function(index, layero){
        var param = {
            "sqxxid" : data.SQXXID,
            "shzt": 2
        }
        console.log(param)
        $.ajax({
            type: 'POST',
            url: getContextPath() + "/rest/v1.0/zq/zxsq/shzt/update",
            data: JSON.stringify(param),
            dataType: "json",
            contentType: "application/json",
            success: function (data) {
                layer.msg('<img src="../../static/lib/bdcui/images/success-small.png" alt="">提交成功!');
                setTimeout(function(){parent.layer.closeAll()},1000);
                tableReload('pageTable', {data: JSON.stringify(queryObject)}, dataUrl);
            },
            error: function (xhr, status, error) {
                delAjaxErrorMsg(xhr);
                removeModal();
            }
        });
    },function(index){
        return;
    });
    return false;
}