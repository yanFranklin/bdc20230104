/*
* 常州自定义查询档案信息页面js
* */
// 审批表模板地址
var sqSpbModelUrl = getIP() + "/realestate-register-ui/static/printModel/bdcSqSpb.fr3";

layui.use(['form', 'table', 'jquery', 'response'], function () {
    var form = layui.form;
});

var dylxArr = ["bdcSqSpb", "daml", "spbdaml"];
var sessionKey = "bdcShxx";
setDypzSession(dylxArr, sessionKey);

function printfun(obj) {
    if (!obj.data.XMID) {
        layer.msg('当前记录无工作流实例id数据，无法打印！');
        return;
    }
    var gzlslid = obj.data.XMID;
    var gzlslids = [];
    gzlslids.push(gzlslid)
    showPrintMessage(gzlslids, false);

}

function pldy() {
    if (checkeddata.length === 0) {
        warnMsg('请勾选需要打印的数据！');
        return;
    }
    var gzlslids = [];
    for (var i = 0; i < checkeddata.length; i++) {
        gzlslids.push(checkeddata[i].XMID);
    }
    showPrintMessage(gzlslids, true)

}


function showPrintMessage(gzlslids, sfpl) {
    var c = layer.open({
        title: '一次只允许打印一种类型，请选择',
        type: 1,
        area: ['430px','180px'],
        btn: ['确定', '取消'],
        content: '<div id="bdc-popup-radio">\n' +
            '    <form class="layui-form" action="">\n' +
            '        <div class="layui-form-item pf-form-item">\n' +
            '            <div class="layui-inline">\n' +
            '                <label class="layui-form-label">打印类型：</label>\n' +
            '                <div class="layui-input-inline">\n' +
            '                </div>\n' +
            '            </div>\n' +
            '        </div>\n' +
            '    </form>\n' +
            '</div>',
        success: function () {
            var form = layui.form;
            var radio = "";
            radio += "<input type=\"radio\" name=\"dylxRadio\" value=\"bdcSqSpb\" title=\"审批表\" lay-filter=\"zslxRadio\" checked>";
            radio += "<input type=\"radio\" name=\"dylxRadio\" value=\"daml\" title=\"档案目录\" lay-filter=\"zslxRadio\">";
            radio += "<input type=\"radio\" name=\"dylxRadio\" value=\"spbdaml\" title=\"全部\" lay-filter=\"zslxRadio\">";
            $('#bdc-popup-radio').find(".layui-input-inline").html(radio);
            form.render("radio");
        },
        yes: function (index, layero) {
            var modelUrl = "";
            var dataUrl = "";
            var dylx = $("input[name='dylxRadio']:checked").val();
            if (dylx === "bdcSqSpb") {
                modelUrl = sqSpbModelUrl;
                if (sfpl) {
                    dataUrl = getIP() + "/realestate-register-ui/rest/v1.0/print/spb/" + gzlslids.join(",") + "/xml/pl?zxlc=" + false;
                } else {
                    dataUrl = getIP() + "/realestate-register-ui/rest/v1.0/print/spb/" + gzlslids[0] + "/" + dylx + "/xml?zxlc=" + false;
                }
            }
            if (dylx === "daml") {
                modelUrl = getIP() + "/realestate-register-ui/static/printModel/daml.fr3";
                dataUrl = getIP() + "/realestate-register-ui/rest/v1.0/print/dajj/daml/" + gzlslids.join(",");
            }

            if(dylx == "spbdaml"){
                modelUrl = getIP() + "/realestate-register-ui/static/printModel/spbdaml.fr3";
                dataUrl = getIP() + "/realestate-register-ui/rest/v1.0/print/dajj/spbdaml/" + gzlslids.join(",");
            }
            if (isNullOrEmpty(modelUrl)) {
                layer.warn("请选择打印类型再打印");
                return;
            }
            console.log("打印类型：" + dylx + "工作流实例id：" + gzlslids);
            printChoice(dylx, "realestate-register-ui", dataUrl, modelUrl, false, sessionKey);
        }
        , btn2: function (index, layero) {
            //取消 的回调
            layer.close(c)
        }
        , cancel: function (index, layero) {
            //右上角关闭回调
            layer.close(c)
            //return false 开启该代码可禁止点击该按钮关闭
        }
    })
}

function showsjcl(obj) {
    if (!obj.data.XMID) {
        layer.msg('当前记录无工作流实例id数据，无法打印！');
        return;
    }

    var bdcSlWjscDTO = queryBdcSlWjscDTO(true);
    bdcSlWjscDTO.spaceId = obj.data.XMID;
    bdcSlWjscDTO.storageUrl = document.location.protocol + "//" + document.location.host + "/storage";
    var url = getIP() + "/realestate-accept-ui/view/slym/sjd.html?paramJson=" + encodeURI(JSON.stringify(bdcSlWjscDTO));
    openSjcl(url, "附件上传");


}

function queryBdcSlWjscDTO(sfhqqx) {
    var bdcSlWjscDTO = {};
    $.ajax({
        url: '/realestate-inquiry-ui/rest/v1.0/dwgz/getFileCs',
        type: 'GET',
        async: false,
        data: {sfhqqx: sfhqqx},
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

function openSjcl(url, title) {
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