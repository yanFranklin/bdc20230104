layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl'], function () {
    generate();
});
var alertSize = 0;
var confirmSize = 0;
//例外
var lwsize =0;
var alertList = [];
var confirmList = [];
//例外户室
var lwhsList = [];
var gltdzList = [];
var gltdzBdcdyhList = [];
var gltdzXMidList = [];
var gltdzxmid = "";
var qxyzFwHsIndexArr = [];
//所有例外审核信息
var lwshxxData= [];
function loadTsxx(data) {
    alertList = [];
    confirmList = [];
    lwhsList =[];
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
            lwsize++;
            lwhsList.push(item.fwHsIndex);
            var xmidArr = [];
            var count = 0;
            //循环获取所有需要外联证书
            $.each(item.xzxx, function (index, xzxxitem) {
                if (xzxxitem && xzxxitem.XMID) {
                    var wlxmid = xzxxitem.XMID;
                    xmidArr.push(wlxmid);
                    var lwshObj = JSON.parse(JSON.stringify(item));
                    lwshObj.xmid =wlxmid;
                    lwshxxData.push(lwshObj);
                    count++;
                }
            });
            // alertList.push(item.fwHsIndex);
            $("#alertInfo").append('<p class="bdc-lwtsxx-p"><img src="../static/lib/bdcui/images/error.png" alt="">' + item.msg + '<a href="javascrit:;" onclick="removeAlert(\'' + item.gzid + '\',\'' + item.gzmc + '\',\'' + item.bdcdyh + '\',\'' + xmidArr.join(",") +'\',\''+ item.fwHsIndex +'\',this);return false" >例外</a></p>');
        }
    });

    //关联土地证提示数量
    var gltdzNum= 0;
    //存储关联土地证与历史关系对照
    var gltdzMsg = "";
    var needConfirm = false;
    $.each(data, function (index, item) {
        if (item.yzlx === "confirm" || item.yzlx === "confirmAndCreate") {
            confirmList.push(item.fwHsIndex);
            if (item.msg && item.msg.indexOf("GLTDZ") > -1) {
                gltdzNum++;
                //关联土地证验证的特殊处理
                confirmSize ++;
                gltdzBdcdyhList.push(item.bdcdyh)
                gltdzList.push(item.fwHsIndex);
                if (item && item.xzxx[0]) {
                    gltdzxmid = item.xzxx[0].XMID;
                    gltdzXMidList.push(gltdzxmid);
                }
                gltdzMsg = item.msg.replace("GLTDZ", "");
                if (gltdzMsg.indexOf("YZX") > -1) {
                    gltdzMsg = gltdzMsg.replace("YZX", "");
                } else {
                    needConfirm = true;
                }
            } else if (item.msg && item.msg.indexOf("HSHB") > -1) {
                //单元号户室合并
                confirmSize++;
                var hshbMsg = "";
                hshbMsg = item.msg.replace("HSHB", "");
                $("#ignoreAll").remove();
                $("#confirmInfo").append('<p><img src="../static/lib/bdcui/images/warn.png" alt="">' + hshbMsg + '<a href="javascrit:;" onclick="removeHshb(this,\'' + item.fwHsIndex + '\');return false" >忽略</a></p>');
            } else {
                confirmSize++;
                // confirmList.push(item.fwHsIndex);
                // 如果该BDCDYH已经存在alert 不允许忽略
                if (alertList.indexOf(item.fwHsIndex) > -1) {
                    $("#confirmInfo").append('<p><img src="../static/lib/bdcui/images/warn.png" alt="">' + item.msg + '</p>');
                } else {
                    $("#confirmInfo").append('<p><img src="../static/lib/bdcui/images/warn.png" alt="">' + item.msg + '<a href="javascrit:;" name=\'' + item.gzid + '\' fwHsIndex = \'' + item.fwHsIndex + '\'  onclick="remove(this,\'' + item.fwHsIndex + '\');return false" >忽略</a></p>');
                }
            }
        }
    });

    //当只存在提示信息时展现全部忽略按钮，存在警告不展示忽略按钮
    if(alertSize > 0) {
        $("#ignoreAll").remove();
    }
    //当警告全部可例外展示例外全部按钮,存在不可例外警告不展示
    if(lwsize ===0 ||alertSize >lwsize){
        $("#lwAll").remove();
    }
    if(gltdzNum >0 && gltdzMsg && gltdzxmid) {
        $("#confirmInfo").append('<p><img src="../static/lib/bdcui/images/warn.png" alt="">' + gltdzMsg + '<a href="javascrit:;" onclick="glTdzConfirm(this, '+ needConfirm +');return false">确定</a><a href="javascrit:;" onclick="hlGlTd(this);return false">忽略</a></p>');
    }
}

//关联土地证提示是否注销
var bdcWlSlXmLsgxDO ={};
function glTdzConfirm(elem, needConfirm) {
    if(needConfirm){
        var index = layer.open({
            type: 1,
            skin: 'bdc-small-tips',
            title: "提示",
            area: ['320px'],
            content: "是否需要注销？",
            btn: ['确定', '取消'],
            btnAlign: 'c',
            yes: function () {
                // 判断土地证的权利
                // added by cyc at 2020-4-17
                var flag = yztdzQl();
                if(flag){
                    layer.alert("土地证包含限制权利，不能注销！");
                }else{
                    bdcWlSlXmLsgxDO.zxyql =1;
                    glTdz(elem,bdcWlSlXmLsgxDO);
                    layer.close(index);
                }
            },
            btn2: function () {
                bdcWlSlXmLsgxDO.zxyql =0;
                glTdz(elem,bdcWlSlXmLsgxDO);
                layer.close(index);
            }
        });
    }else{
        bdcWlSlXmLsgxDO.zxyql =0;
        glTdz(elem,bdcWlSlXmLsgxDO);
        layer.close(index);
    }
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
    lwsize =0;
    alertList = [];
    confirmList = [];
    lwhsList =[];
    qxyzFwHsIndexArr = [];
    gltdzList = [];
    gltdzBdcdyhList = [];
    gltdzXMidList = [];
    lwshxxData =[];
    layui.use(['form', 'jquery', 'laytpl', 'element', 'laydate'], function () {
        var form = layui.form;
        var laytpl = layui.laytpl;
        var json = {};
        var tpl = tsxxTpl.innerHTML, view = document.getElementById("tsxx");
        //渲染数据
        laytpl(tpl).render(json, function (html) {
            view.innerHTML = html;
        });
        // form.render();
    })
}

function remove(elem,fwHsIndex) {
    $(elem).parent().remove();
    if (parent.length > 0) {
        //点击忽略时增加日志记录，同步处理
        var data = elem.parentElement.innerText.replace(/忽略/g, "").replace(/查看/g, "");
        parent.addRemoveLog(data);
    }
    if (fwHsIndex) {
        confirmSize--;
        qxyzFwHsIndexArr.push(fwHsIndex);
    }
    if (gzyzHlxtZgz && gzyzHlxtZgz === "true") {
        //根据name=gzid 找到所有的提示信息，点击remove
        var gzid = $(elem).attr("name");
        var fwhsIndex = $(elem).attr("fwhsIndex");
        if (isNotBlank(gzid)) {
            var elements = $("#confirmInfo").find('a[name="' + gzid + '"]');
            for (var i = 0; i < elements.length; i++) {
                //不包含当前单元号
                if (fwhsIndex !== $(elements[i]).attr("fwhsIndex")) {
                    removeSameTsxx(elements[i], $(elements[i]).attr("fwhsIndex"));
                }
            }
        }
    }
    if (alertSize == 0 && confirmSize == 0) {
        addCartWithFwhsIndexArr(qxyzFwHsIndexArr, true, bdcWlSlXmLsgxDO);
        //在没有警告提示下创建
        layer.close(warnLayer);
        generate();
    }
}

function removeSameTsxx(elem, fwHsIndex) {
    $(elem).parent().remove();
    if (parent.length > 0) {
        //点击忽略时增加日志记录，同步处理
        var data = elem.parentElement.innerText.replace(/忽略/g, "").replace(/查看/g, "");
        parent.addRemoveLog(data);
    }
    if (fwHsIndex) {
        confirmSize--;
        qxyzFwHsIndexArr.push(fwHsIndex);
    }
}

function removeHshb(elem) {
    $(elem).parent().remove();
    //点击忽略时增加日志记录，同步处理
    var data = elem.parentElement.innerText.replace(/忽略/g, "").replace(/查看/g, "");
    addLog("户室合并时" + data + "点击忽略按钮");
    confirmSize--;
    if (alertSize === 0 && confirmSize === 0) {
        //合并户室操作
        // 打开合并页面
        layer.open({
            type: 2,
            title: "请选择一条数据作为主户室，其他户室信息将合并到该户室",
            area: ['960px', '300px'],
            content: "../fwhsbg/inithb?fwHsIndexList=" + encodeURI($.hsbg.hbYfwhs) + ""
        });
        //在没有警告提示下创建
        layer.close(warnLayer);
        generate();
    }
}

//增加验证忽略日志
function addLog(data) {
    $.ajax({
        url: "../check/log",
        data: JSON.stringify(data),
        type: 'POST',
        dataType: 'json',
        contentType: "application/json;charset=UTF-8",
        success: function (data) {
        },
        error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr);
        }
    });
}

function removeAlert(gzid, gzmc, bdcdyh, xzxx, fwHsIndex, elem) {
    if (alertSize > lwsize) {
        ityzl_SHOW_WARN_LAYER("存在其他警告验证,不允许例外");
        return false;
    } else if (confirmSize > 0) {
        ityzl_SHOW_WARN_LAYER("请先忽略其他提示验证,再进行例外");
        return false;
    }
    // 例外原因
    $("#exceptionReason").val('');
    var htmlStr = '<div id="exception-reason" class="bdc-layer-textarea">\n' +
        '        <form class="layui-form" action="">\n' +
        '            <div class="layui-form-item pf-form-item">\n' +
        '                <div class="layui-inline" style="width: 100%">\n' +
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
                var zl;
                if (parent.length > 0) {
                    if (currentRes.data.allHsCells && currentRes.data.allHsCells.length > 0) {
                        for(var i = 0 ; i < currentRes.data.allHsCells.length;i++){
                            var temp = currentRes.data.allHsCells[i];
                            if (fwHsIndex === temp.info.fwHsIndex.value) {
                                zl = temp.info.zl.value;
                            }
                        }
                    }
                    var flag =parent.addShxx(gzid, gzmc, bdcdyh, xzxx, lwyy, zl, elem);
                    if(!flag){
                        return false;
                    }
                    var elemInfo = elem.parentElement.innerText.replace(/例外/g, "").replace(/查看/g, "");
                    parent.addLwLog(elemInfo);
                }
                $(elem).parent().remove();
                alertSize--;
                lwsize--;
                qxyzFwHsIndexArr.push(fwHsIndex);
                if(alertSize == 0 && confirmSize == 0){
                    addCartWithFwhsIndexArr(qxyzFwHsIndexArr,true,bdcWlSlXmLsgxDO);
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
            addCartWithFwhsIndexArr(confirmList,true);
        }
        else {
            removeModal();
        }
        generate();
    },50);
}

//全部例外
function lwAll(){
    // 例外原因
    $("#exceptionReason").val('');
    var htmlStr = '<div id="exception-reason" class="bdc-layer-textarea">\n' +
        '        <form class="layui-form" action="">\n' +
        '            <div class="layui-form-item pf-form-item">\n' +
        '                <div class="layui-inline" style="width: 100%">\n' +
        '                    <label class="layui-form-label"><span class="required-span"><sub>*</sub></span>例外原因</label>\n' +
        '                    <div class="layui-input-inline bdc-end-time-box" style="width: 100%">\n' +
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
            try{
                if (parent.length > 0) {
                    var cells = [];
                    for(var i = 0 ; i < currentRes.data.allHsCells.length;i++){
                        var temp = currentRes.data.allHsCells[i];
                        if(lwhsList.indexOf(temp.info.fwHsIndex.value) >= 0){
                            cells.push(temp.info);
                        }
                    }
                    if (cells && cells.length > 0 && lwshxxData && lwshxxData.length > 0) {
                        for (let i = 0; i < lwshxxData.length; i++) {
                            var lwshxx = lwshxxData[i];
                            for (let j = 0; j < cells.length; j++) {
                                var cell = cells[j];
                                if (lwshxx.fwHsIndex === cell.fwHsIndex.value) {
                                    lwshxx.zl = cell.zl.value;
                                }
                            }
                        }
                    }
                    var flag =parent.addShxxDataAll(lwshxxData, lwyy);
                    if(!flag){
                        return false;
                    }
                    var data = $(".bdc-lwtsxx-p").text().replace(/例外/g, "").replace(/查看/g, "");
                    parent.addLwLog(data);
                }
                $(".bdc-lwtsxx-p").remove();
                alertSize =alertSize -lwsize;
                lwsize =0;
                if(alertSize == 0 && confirmSize == 0){
                    if(lwhsList.length > 0){
                        addCartWithFwhsIndexArr(lwhsList,true);
                    }
                    //在没有警告提示下创建
                    layer.close(warnLayer);
                    generate();
                }
                removeAll();

            }catch (e){

            }
            layer.close(index);
        },
        btn2: function(index, layero){
            layer.close(index);
        }
    });
}

/**
 * 查询限制权利
 * @returns {boolean}
 */
function yztdzQl(){
    var xzQlFlag = false;
    console.log("查询限制权利土地证的xmid单元集合："+gltdzXMidList);
    if(gltdzXMidList.length > 0){
        xzQlFlag = plQueryXzztByXmids(gltdzXMidList);
        // for (var k = 0; k < gltdzXMidList.length; k++){
        //     var wlxmid = gltdzXMidList[k];
        //     console.log("土地证xmid："+wlxmid);
        //     xzQlFlag = queryXzztBybdcdyh(wlxmid);
        //     if(xzQlFlag){
        //         break;
        //     }
        // }
    }
    return xzQlFlag;
}

/**
 * 根据bdcdyh查询限制状态
 * @param bdcdyh
 * @returns {boolean}
 */
function plQueryXzztByXmids(xmids){
    var xzQlFlag = false;
    var paramJson = JSON.parse(param.replace(/\'/g , "\"")); //将字符串中所有‘'’转换为‘"’;
    $.ajax({
        url: getContextPath() + "../lpb/plQueryXzQl?processDefKey=" + paramJson.gzldyid,
        data:JSON.stringify(xmids),
        type: 'POST',
        dataType: 'json',
        contentType: "application/json;charset=UTF-8",
        async: false,
        success: function (data) {
            if (isNotBlank(data)) {
                xzQlFlag = data;
            }
        },
        error: function (xhr, status, error) {
            xzQlFlag = false;
        }
    });
    return xzQlFlag;
}
function queryXzztBybdcdyh(xmid){
    var xzQlFlag = false;
    // 通过bdcdyh和xmid查询这个土地证是否含有抵押、查封，还有异议和锁定的限制权利
    var paramJson = JSON.parse(param.replace(/\'/g , "\"")); //将字符串中所有‘'’转换为‘"’;
    $.ajax({
        url: "../lpb/queryxzQl",
        data : {
            xmid : xmid,
            processDefKey : paramJson.gzldyid
        },
        type: 'GET',
        dataType: 'json',
        async: false,
        success: function (data) {
            console.log("查询限制权利返回值："+data);
            if (data !== null) {
                xzQlFlag = data;
            }
        },
        error: function (xhr, status, error) {
            xzQlFlag = false;
        }
    })
    return xzQlFlag;
}