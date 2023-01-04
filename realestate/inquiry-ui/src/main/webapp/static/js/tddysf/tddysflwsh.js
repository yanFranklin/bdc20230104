//审核通过
function shtg() {
    if (!checkeddata || checkeddata.length == 0) {
        layer.alert("请选择需要审核的记录！", {title: '提示'});
        return;
    }
    debugger;

    for (var i = 0; i < checkeddata.length; i++){
        if(checkeddata[i]['SHZT'] !=0){
            warnMsg("选中数据中存在已审核的数据，请重新选择！");
            return false;
        }
    }
    lwsh(1,checkeddata);

}

//审核不通过
function shbtg() {
    if (!checkeddata || checkeddata.length == 0) {
        layer.alert("请选择需要审核的记录！", {title: '提示'});
        return;
    }

    for (var i = 0; i < checkeddata.length; i++){
        if(checkeddata[i]['SHZT'] !=0){
            warnMsg("选中数据中存在已审核的数据，请重新选择！");
            return false;
        }
    }
    lwsh(2,checkeddata);

}

function lwsh(shzt,checkData){
    var htmlStr = '<div id="exception-reason" class="bdc-layer-textarea">\n' +
        '        <form class="layui-form" action="">\n' +
        '            <div class="layui-form-item pf-form-item">\n' +
        '                <div class="layui-inline">\n' +
        '                    <label class="layui-form-label"><span class="required-span"><sub>*</sub></span>审核意见</label>\n' +
        '                    <div class="layui-input-inline bdc-end-time-box">\n' +
        '                        <textarea name="shyj" id="shyj" placeholder="请输入内容" class="layui-textarea"></textarea>\n' +
        '                    </div>\n' +
        '                </div>\n' +
        '            </div>\n' +
        '        </form>\n' +
        '    </div>';
    layer.open({
        title: '审核意见',
        type: 1,
        area: ['430px'],
        btn: ['确定', '取消'],
        content: htmlStr,
        yes: function(index, layero) {
            var shyj = $("#shyj").val();
            if (isNullOrEmpty(shyj)) {
                layer.msg('请输入审核意见!');
                return false;
            }
            var bdcTddysfDyhDOList=[];
            for (var i = 0; i < checkData.length; i++) {
                var tddysfDyh ={};
                var bdcdyh = checkData[i].BDCDYH;
                var qjgldm = checkData[i].QJGLDM;
                tddysfDyh.bdcdyh=bdcdyh;
                tddysfDyh.qjgldm=qjgldm;
                bdcTddysfDyhDOList.push(tddysfDyh);
            }
            var bdcTddysfDyhShDTO = {};
            bdcTddysfDyhShDTO.shyj=shyj;
            bdcTddysfDyhShDTO.shzt =shzt;
            bdcTddysfDyhShDTO.bdcTddysfDyhDOList = bdcTddysfDyhDOList;
            getReturnData("/rest/v1.0/tddysf/lwsh", JSON.stringify(bdcTddysfDyhShDTO), "POST", function () {
                successMsg("操作成功");
                $("#search").click();
                layer.close(index);
            }, function (error) {
                delAjaxErrorMsg(error);

            });
        },
        btn2: function(index, layero){
            layer.close(index);
        }
    });
}