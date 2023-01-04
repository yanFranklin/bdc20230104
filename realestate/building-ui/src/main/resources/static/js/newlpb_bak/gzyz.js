layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl'], function () {
    generate();
});
var alertSize = 0;
var confirmSize = 0;
var alertList = [];
var confirmList = [];
var gltdzList = [];
var gltdzxmid = "";
var qxyzFwHsIndexArr = [];
function loadTsxx(data) {
    alertList = [];
    confirmList = [];
    $.each(data, function (index, item) {
        if (item.yzlx === "alert") {
            alertSize++;
            alertList.push(item.fwHsIndex);
            $("#alertInfo").append('<p><img src="../static/lib/bdcui/images/error.png" alt="">' + item.msg + '</p>');
        }
    });

    $.each(data, function (index, item) {
        if (item.yzlx === "alert-exclude") {
            alertSize++;
            var xmidArr = [];
            var count = 0;
            //循环获取所有需要外联证书
            $.each(item.xzxx, function (index, xzxxitem) {
                var wlxmid = xzxxitem.XMID;
                xmidArr.push(wlxmid);
                count++;
            });
            alertList.push(item.fwHsIndex);
            $("#alertInfo").append('<p><img src="../static/lib/bdcui/images/error.png" alt="">' + item.msg + '<a href="javascrit:;" onclick="removeAlert(\'' + item.gzid + '\',\'' + item.gzmc + '\',\'' + item.bdcdyh + '\',\'' + xmidArr.join(",") +'\',\''+ item.fwHsIndex +'\',this);return false" >例外</a></p>');
        }
    });

    //关联土地证提示数量
    var gltdzNum= 0;
    //存储关联土地证与历史关系对照
    var gltdzMsg = "";
    $.each(data, function (index, item) {
        if (item.yzlx === "confirm" || item.yzlx === "confirmAndCreate") {
            if (item.msg && item.msg.indexOf("GLTDZ") > -1) {
                gltdzNum++;
                //关联土地证验证的特殊处理
                confirmSize ++;
                gltdzList.push(item.fwHsIndex);
                gltdzxmid = item.xzxx[0].XMID;
                gltdzMsg = item.msg.replace("GLTDZ", "");
            } else {
                confirmSize++;
                confirmList.push(item.fwHsIndex);
                // 如果该BDCDYH已经存在alert 不允许忽略
                if(alertList.indexOf(item.fwHsIndex) > -1){
                    $("#confirmInfo").append('<p><img src="../static/lib/bdcui/images/warn.png" alt="">' + item.msg +'</p>');
                }else{
                    $("#confirmInfo").append('<p><img src="../static/lib/bdcui/images/warn.png" alt="">' + item.msg + '<a href="javascrit:;" onclick="remove(this,\''+ item.fwHsIndex +'\');return false" >忽略</a></p>');
                }
            }
        }
    });

    //当只存在提示信息时展现全部忽略按钮，存在警告不展示忽略按钮
    if(alertSize > 0) {
        $("#ignoreAll").remove();
    }
    if(gltdzNum >0 && gltdzMsg && gltdzxmid) {
        $("#confirmInfo").append('<p><img src="../static/lib/bdcui/images/warn.png" alt="">' + gltdzMsg + '<a href="javascrit:;" onclick="glTdzConfirm(this,\''+ gltdzxmid +'\');return false">确定</a><a href="javascrit:;" onclick="hlGlTd(this);return false">忽略</a></p>');
    }
}

//关联土地证提示是否注销
var bdcWlSlXmLsgxDO ={};
function glTdzConfirm(elem) {
    var index = layer.open({
        type: 1,
        skin: 'bdc-small-tips',
        title: "提示",
        area: ['320px'],
        content: "是否需要注销？",
        btn: ['确定', '取消'],
        btnAlign: 'c',
        yes: function () {
            bdcWlSlXmLsgxDO.zxyql =1;
            glTdz(elem,bdcWlSlXmLsgxDO);
            layer.close(index);
        },
        btn2: function () {
            bdcWlSlXmLsgxDO.zxyql =0;
            glTdz(elem,bdcWlSlXmLsgxDO);
            layer.close(index);
        }
    });
}

function glTdz(elem, bdcWlSlXmLsgxDO) {
    bdcWlSlXmLsgxDO.yxmid = gltdzxmid;
    for(var i = 0;i<gltdzList.length;i++ ){
        confirmSize--;
        qxyzFwHsIndexArr.push(gltdzList[i]);
    }
    remove(elem);
}

function hlGlTd(elem){
    bdcWlSlXmLsgxDO = {};
    for(var i = 0;i<gltdzList.length;i++ ){
        confirmSize--;
        qxyzFwHsIndexArr.push(gltdzList[i]);
    }
    remove(elem);
}

function generate() {
    alertSize = 0;
    confirmSize = 0;
    alertList = [];
    confirmList = [];
    qxyzFwHsIndexArr = [];
    gltdzList = [];
    layui.use(['form', 'jquery', 'laytpl', 'element', 'laydate'], function () {
        var form = layui.form;
        var laytpl = layui.laytpl;
        var json = {};
        var tpl = tsxxTpl.innerHTML, view = document.getElementById("tsxx");
        //渲染数据
        laytpl(tpl).render(json, function (html) {
            view.innerHTML = html;
        });
        form.render();
    })
}

function remove(elem,fwHsIndex) {
    $(elem).parent().remove();
    if(parent.length > 0) {
        //点击忽略时增加日志记录，同步处理
        var data = elem.parentElement.innerText.replace(/忽略/g, "").replace(/查看/g, "");
        parent.addRemoveLog(data);
    }
    if(fwHsIndex){
        confirmSize--;
        qxyzFwHsIndexArr.push(fwHsIndex);
    }
    if(alertSize == 0 && confirmSize == 0){
        //将fwHsIndex 重新放回 checkedList
        for(var i = 0;i<qxyzFwHsIndexArr.length;i++){
            currentRes.checkedList.push(qxyzFwHsIndexArr[i]);
        }
        addCartWithSelect(qxyzFwHsIndexArr,true,bdcWlSlXmLsgxDO);
        //在没有警告提示下创建
        layer.close(warnLayer);
        generate();
    }
}

function removeAlert(gzid,gzmc,bdcdyh,xzxx,fwHsIndex,elem) {
    // 例外原因
    $("#exceptionReason").val('');
    var htmlStr = '<div id="exception-reason" class="bdc-layer-textarea">\n' +
        '        <form class="layui-form" action="">\n' +
        '            <div class="layui-form-item pf-form-item">\n' +
        '                <div class="layui-inline">\n' +
        '                    <label class="layui-form-label"><span class="required-span"><sub>*</sub></span>例外原因</label>\n' +
        '                    <div class="layui-input-inline bdc-end-time-box">\n' +
        '                        <textarea name="exceptionReason" id="exceptionReason" placeholder="请输入内容" class="layui-textarea"></textarea>\n' +
        '                    </div>\n' +
        '                </div>\n' +
        '            </div>\n' +
        '        </form>\n' +
        '    </div>';
    layer.open({
        title: '例外原因',
        type: 1,
        area: ['430px'],
        btn: ['确定', '取消'],
        content: htmlStr,
        yes: function(index, layero) {
            var lwyy = $("#exceptionReason").val();
            if (isNullOrEmpty(lwyy)) {
                layer.msg('请输入例外原因!');
                return false;
            }
            // 增加规则例外审核信息
            try {
                if(parent.length > 0){
                    parent.addShxx(gzid, gzmc, bdcdyh, xzxx, elem, lwyy);
                    var elemInfo = elem.parentElement.innerText.replace(/例外/g, "").replace(/查看/g, "");
                    parent.addLwLog(elemInfo);
                }
                $(elem).parent().remove();
                alertSize--;
                qxyzFwHsIndexArr.push(fwHsIndex);
                if(alertSize == 0 && confirmSize == 0){
                    //将fwHsIndex 重新放回 checkedList
                    //将fwHsIndex 重新放回 checkedList
                    for(var i = 0;i<qxyzFwHsIndexArr.length;i++){
                        currentRes.checkedList.push(qxyzFwHsIndexArr);
                    }
                    addCartWithSelect(qxyzFwHsIndexArr,true,bdcWlSlXmLsgxDO);
                    //在没有警告提示下创建
                    layer.close(warnLayer);
                    generate();
                }
            } catch (e){

            }
            layer.close(index);
        },
        btn2: function(index, layero){
            layer.close(index);
        }
    });
}

//忽略全部
function removeAll() {
    //在没有警告提示下创建
    layer.close(warnLayer);
    addModel();
    setTimeout(function() {
        if(confirmList.length > 0){
            for(var i=0 ;i < confirmList.length;i++){
                var fwHsIndex = confirmList[i];
                currentRes.checkedList.push(fwHsIndex);
            }
            addCartWithSelect(confirmList,true);
        }
        generate();
    },50);
}