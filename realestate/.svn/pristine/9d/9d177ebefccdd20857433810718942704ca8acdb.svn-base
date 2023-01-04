/**
 * 征迁：冻结、解冻操作
 */
layui.use(['form', 'jquery', 'element'], function () {
    var form = layui.form;

    /**
     * 表单数据提交,编辑
     */
    form.on('submit(submitBtnEdit)', function(data) {
        if(isNullOrEmpty(data.field.jdyy)) {
           warnMsg("请输入解冻原因！");
           return ;
        }

        var param = [];
        var djxxIds = data.field.djxxid;
        var djxxIdArr = djxxIds.split(",");
        for(var i in djxxIdArr){
            var obj = {
                "djxxid" : djxxIdArr[i],
                "jdyy" : data.field.jdyy
            }
            param.push(obj);
        }
        $.ajax({
            type: 'POST',
            url: getContextPath() + "/rest/v1.0/zq/dj/djzt/batch/update",
            data: JSON.stringify(param),
            dataType: "json",
            contentType: "application/json",
            success: function (data) {
                successMsg('解冻成功!');
                parent.setState();
                setTimeout(function(){parent.layer.closeAll()},1000);
            },
            error: function (xhr, status, error) {
                delAjaxErrorMsg(xhr);
                removeModal();
            }
        });
        return false;
    });

})

// 操作状态标识
var state = "";

/**
 * 解冻
 */
function jd(obj,data){
    state = "";
    var djxxIds = "";

    if(obj && data){
        //这两个参数有值 说明是单个解封
        if(2 == data.DJZT) {
            warnMsg("当前单元未冻结，无法解冻！");
            return;
        }
        djxxIds = data.DJXXID;
    }else{
        // 参数没有值，说明是勾选的批量解封
        if(checkeddata.length == 0){
            warnMsg('请勾选需要解冻的数据！');
            return;
        }
        // 判断批量解封的查封文号是否一致，是否都到期，是否都是现势状态
        for(var i = 0 ; i < checkeddata.length ; i++){
            if(2 == checkeddata[i].DJZT) {
                warnMsg("所选记录包含未冻结单元，无法解冻！");
                return;
            }
            djxxIds += checkeddata[i].DJXXID + ",";
        }
        djxxIds = djxxIds.substring(0,djxxIds.length-1);
    }

    layer.open({
        type: 2,
        title: '解冻',
        anim: -1,
        shadeClose: true,
        maxmin: true,
        shade: false,
        area: ['960px', '250px'],
        offset: 'auto',
        content: [ "../../yancheng/zq/jd.html", 'yes'],
        success: function (layero, index) {
            frames[0].$("#djxxid").val(djxxIds);
        },
        end:function(){
            if("jd" == state) {
                tableReload('pageTable', {data: JSON.stringify(queryObject)}, dataUrl);
            }
        }
    });
}

function setState() {
    state = "jd";
}


function closeWin(){
    parent.layer.closeAll();
}