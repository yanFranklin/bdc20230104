/**
 * 撤销查询自定义查询js文件
 */
var form;
var $;
var layer;
layui.use(['form', 'jquery','layer'], function () {
    form = layui.form;
    $ = layui.jquery;
    layer = layui.layer;
});

/**
 * @param cxdata
 * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
 * @description 上传撤销材料
 * @date : 2022/12/27 10:37
 */
function cxcl(cxdata) {
    console.log(cxdata);
    var gzlslid = cxdata.data.GZLSLID;
    var taskName =  cxdata.data.TASK_NAME;
    if (isNullOrEmpty(gzlslid)) {
        errorsMsg("当前工作流实例id为空，无法上传撤销材料！");
        return;
    }

    if (isNullOrEmpty(taskName) && taskName != '受理') {
        errorsMsg("当前节点非受理节点，无法上传撤销材料！");
        return;
    }
    uploadFj(gzlslid);
}

/**
 * @param cxdata
 * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
 * @description 确认撤销
 * @date : 2022/12/27 10:37
 */
function qrcx(cxdata) {
    console.log(cxdata);
    var gzlslid = cxdata.data.GZLSLID;
    var taskName =  cxdata.data.TASK_NAME;
    if (isNullOrEmpty(gzlslid)) {
        errorsMsg("当前工作流实例id为空，无法撤销！");
        return;
    }

    if (isNullOrEmpty(taskName) && taskName != '受理') {
        errorsMsg("当前节点非受理节点，无法撤销！");
        return;
    }
    var data = [{"gzlslid": gzlslid}];
    // 规则验证
    check(data, "cxlcgzyz").fail(function(){
        return;
    }).done(function(){
        // 执行撤销流程
        cxlc();
    });

}

// 撤销流程
function cxlc() {
    layer.open({
        type: 1,
        title: ['撤销'],
        area: ['480px', '200px'],
        fixed: false, //不固定
        content: "<div class=\"bdc-layer-textarea\" id=\"abandon-reason\">\n" +
            "        <form action=\"\" class=\"layui-form\">\n" +
            "            <div class=\"layui-form-item pf-form-item\">\n" +
            "                <div class=\"layui-inline\">\n" +
            "                    <label class=\"layui-form-label\"><span class=\"required-span\"><sub>*</sub></span>撤消原因</label>\n" +
            "                    <div class=\"layui-input-inline bdc-end-time-box\">\n" +
            "                        <textarea class=\"layui-textarea\" id=\"abandonreason\" name=\"desc\" placeholder=\"请输入内容\"></textarea>\n" +
            "                    </div>\n" +
            "                </div>\n" +
            "            </div>\n" +
            "        </form>\n" +
            "    </div>",
        btn: ['确定', '取消'],
        yes: function (index, layero) {
            var reason = $("#abandonreason").val();
            if (isNullOrEmpty(reason)) {
                warnMsg("请填写撤销原因！");
                return;
            }
            addModel();
            getReturnData("/rest/v1.0/cxcx/abandon", JSON.stringify({"processInstanceId": gzlslid, "reason": reason}), "POST", function (data) {
                removeModal();
                if (isNotBlank(data) && isNotBlank(data.code == '200')) {
                    successMsg(data.msg);
                } else {
                    errorsMsg(data.msg);
                }
                layer.close(index);
            }, function (xhr) {
                delAjaxErrorMsg(xhr);
            })
        },
        cancel: function (index, layero) {
            layer.close(index);
        }
    });
}
/**
 *  附件上传
 */
function uploadFj(gzlslid){
    $.ajax({
        url: '/realestate-inquiry-ui/rest/v1.0/cxcx/folder',
        type: 'GET',
        async: false,
        contentType: 'application/json;charset=utf-8',
        data: {spaceId: gzlslid},
        success: function (data) {
            if (isNotBlank(data)) {
                var bdcSlWjscDTO = queryBdcSlWjscDTO();
                bdcSlWjscDTO.spaceId = encodeURI(data.name);
                bdcSlWjscDTO.nodeId = data.id;;
                bdcSlWjscDTO.storageUrl = document.location.protocol + "//" + document.location.host + "/storage";
                var url = "/realestate-inquiry-ui/bengbu/sdqgh/sjd.html?paramJson=" + encodeURI(JSON.stringify(bdcSlWjscDTO));
                openSjcl(url, "附件上传");
            } else {
                layer.msg("撤销材料文件夹生成失败");
            }
        }, error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });


}

//查询撤销材料所需参数（打开附件上传使用）
function queryBdcSlWjscDTO() {
    var bdcSlWjscDTO = {};
    $.ajax({
        url: '/realestate-inquiry-ui/rest/v1.0/cxcx/getBdcSlWjscDTO',
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
 * 撤销之前进行规则验证
 * @param data 验证数据
 * @param zhbs 规则标识
 */
function check(data, zhbs){
    if(isNullOrEmpty(zhbs)){
        warnMsg("未配置验证规则，请联系管理员！");
    }

    var deferred = $.Deferred();
    if ($.isEmptyObject(data)) {
        return deferred.resolve();
    }

    var checkParams = new Array();
    $.each(data,function (index,value) {
        checkParams.push({
            gzlslid  : value.gzlslid ,
        });
    });

    addModel();
    $.ajax({
        url: "/realestate-inquiry-ui/rest/v1.0/cxcx/gzyz",
        type: 'POST',
        dataType: 'json',
        contentType: "application/json;charset=UTF-8",
        data: JSON.stringify({
            bdcGzYzsjDTOList : checkParams,
            zhbs: zhbs
        }),
        success: function (data) {
            removeModel();
            if(data.length == 0){
                return deferred.resolve();
            }
            var content = new Array();
            $.each(data,function(i,value){
                content.push("<img src=\"../../static/lib/bdcui/images/warn.png\" style='width: 16px;' alt=\"\">");
                content.push(value.tsxx);
                content.push("<br/>");
            });
            layer.confirm(content.join(""),{
                btn: ["取消撤销"],
                area: '530px'
            },function(index){
                deferred.reject();
                layer.close(index);
            })
        },
        error: function (xhr, status, error) {
            removeModel();
            failMsg("系统验证发生异常，请重试或联系管理员！");
        }
    });
    return deferred;
}