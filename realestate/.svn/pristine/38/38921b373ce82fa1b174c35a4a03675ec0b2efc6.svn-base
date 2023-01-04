/**
 * @author <a href ="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
 * @description 公告台账
 */
var isSubmit = true;
var form;
layui.use(['form', 'jquery', 'element', 'table', 'laydate', 'upload'], function () {
    form = layui.form;
    var layer = layui.layer;
    var verifyMsg = "必填项不能为空";
    var laydate = layui.laydate;

})



function cjdajj(obj,data){
    state = "";
    var gzlslids = [];
        if(checkeddata.length == 0){
            warnMsg('请勾选需要创建档案交接流程的数据！');
            return;
        }
        for(var i = 0 ; i < checkeddata.length ; i++){
            if(isNotBlank(checkeddata[i].GZLSLID)){
                $.ajax({
                    url: "/realestate-inquiry-ui/rest/v1.0/changzhou/dajjlc/cjdajj",
                    type: "POST",
                    data: {"gzlslid" : checkeddata[i].GZLSLID},
                    async: false,
                    dataType: "json",
                    success: function (data) {
                        removeModal();
                        successMsg("操作成功！");
                        layui.table.reload('pageTable', {page: {curr: 1}});
                    },
                    error: function (err) {
                        removeModal();
                        delAjaxErrorMsg(err)
                    }
                });
            }
        }


}



function cancel(){
    var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
    parent.layer.close(index); //再执行关闭
}