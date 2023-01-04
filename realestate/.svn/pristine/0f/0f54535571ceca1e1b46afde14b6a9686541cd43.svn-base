/**
 * 身份证
 */
//调用控件方法如下，具体返回值参考/lib/idCard/readIdCard.html
function readIdCard(){
    try {
        return new ActiveXObject("GtMap.GxFrameActiveX.IDCard.IDCard");
    } catch(objError) {
        layer.alert("Fail to create object. error:" + objError.description);
    }
}

function trimStr(str){
    if(str !== null && str !== undefined){
        return str.replace(/(^\s*)|(\s*$)/g,"");
    }
}


var yhList = listBdcXtJg();
function listBdcXtJg() {
    var yhList;
    //机关类型
    var jglb = 1;
    var qllx = getQueryString("qllx");
    if (qllx === "98") {
        jglb = 2;
    } else {
        jglb = 1;
    }
    $.ajax({
        url: getContextPath() + "/slym/qlr/bdcxtjg",
        type: 'GET',
        dataType: 'json',
        data: {jglb: jglb},
        async: false,
        success: function (data) {
            yhList = data
        }
    });
    return yhList;
}

var readId = {};

function ReadIDCardXztzQlr(element) {
    try {
        var objTest = readIdCard();
        if (objTest.ReadCard()) {
            layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl'], function () {
                var pName = objTest.Name;
                var pCardNo = objTest.ID;
                var pAddress = objTest.Address;
                var forms = element.parentNode.parentNode.parentNode;
                $(forms).find("input[name='qlrmc']").val(trimStr(pName));
                $(forms).find("input[name='qlrzjh']").val($.trim(pCardNo));
                var form = layui.form;
                form.render('select');
            });
        } else {
            layer.alert("请检查是否安装成功并重新放置！");
        }
    } catch (objError) {
        layer.alert("Fail to create object. error:" + objError.description);
    }
}


function ReadIDCardNewSqr(element, fzklb) {
    try {
        var objTest = readIdCard();
        if (objTest.ReadCard()) {
            layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl', 'formSelects'], function () {
                var pName = objTest.Name;
                var pCardNo = objTest.ID;
                var pAddress = objTest.Address;
                var forms = element.parentNode.parentNode.parentNode;

                if (fzklb == 1){
                    var formSelects = layui.formSelects;
                    //渲染下拉框数据
                    var yharr = [];
                    //判断当前值是否存在下拉列表，不存在则新增
                    if (isNotBlank(yhList)) {
                        $.each(yhList, function (index, item) {
                            var yhobject = {};
                            yhobject.name = item.jgmc;
                            yhobject.value = item.jgmc;
                            yharr.push(yhobject);
                        });
                        readId.name = trimStr(pName);
                        readId.value = trimStr(pName);
                        yharr.push(readId);
                    }
                    formSelects.data('yhmc', 'local', {
                        arr: yharr
                    });
                    formSelects.value('yhmc', [pName]);
                }else {
                    $(forms).find("input[name='qlrmc']").val(trimStr(pName));
                }

                $(forms).find("select[name='zjzl']").val("1");
                $(forms).find("input[name='zjh']").val($.trim(pCardNo));
                $(forms).find("input[name='txdz']").val(trimStr(pAddress));
                $(forms).find("select[name='qlrlx']").val("1");
                var form = layui.form;
                form.render('select');
            });
        } else {
            layer.alert("请检查是否安装成功并重新放置！");
        }
    } catch (objError) {
        layer.alert("Fail to create object. error:" + objError.description);
    }
}

function ReadIDCardNewDlr(element) {
    try {
        var objTest = readIdCard();
        if (objTest.ReadCard()) {
            layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl'], function () {
                var pName = objTest.Name;
                var pCardNo = objTest.ID;
                var pAddress = objTest.Address;
                var forms = element.parentNode.parentNode.parentNode;
                $(forms).find("input[name='dlrmc']").val(trimStr(pName));
                $(forms).find("select[name='dlrzjzl']").val("1");
                $(forms).find("input[name='dlrzjh']").val($.trim(pCardNo));
                var form = layui.form;
                form.render('select');
            });
        } else {
            layer.alert("请检查是否安装成功并重新放置！");
        }
    } catch (objError) {
        layer.alert("Fail to create object. error:" + objError.description);
    }
}

function ReadIDCardNewFddbr(element) {
    try {
        var objTest = readIdCard();
        if (objTest.ReadCard()) {
            layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl'], function () {
                var pName = objTest.Name;
                var pCardNo = objTest.ID;
                var pAddress = objTest.Address;
                var forms = element.parentNode.parentNode.parentNode;
                $(forms).find("input[name='frmc']").val(trimStr(pName));
                $(forms).find("select[name='frzjzl']").val("1");
                $(forms).find("input[name='frzjh']").val($.trim(pCardNo));
                var form = layui.form;
                form.render('select');
            });
        } else {
            layer.alert("请检查是否安装成功并重新放置！");
        }
    } catch (objError) {
        layer.alert("Fail to create object. error:" + objError.description);
    }
}

function trimStr(str) {
    return str.replace(/(^\s*)|(\s*$)/g, "");
}

/**
 * 身份证读卡器读取信息到页面
 */
function readxxByIdCard(element,zjzlName,zjhName) {
    try {
        var objTest = readIdCard();
        if (objTest.ReadCard()) {
            layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl'], function () {
                var pName = objTest.Name;
                var pCardNo = objTest.ID;
                var pAddress = objTest.Address;
                var forms = element.parentNode.parentNode.parentNode;
                var elementName = $(element)[0].name;
                $(forms).find("input[name=" + elementName + "]").val(trimStr(pName));
                $(forms).find("select[name=" + zjzlName + "]").val("1");
                $(forms).find("input[name=" + zjhName + "]").val($.trim(pCardNo));
                var form = layui.form;
                form.render('select');
            });
        } else {
            layer.alert("请检查是否安装成功并重新放置！");
        }
    } catch (objError) {
        layer.alert("Fail to create object. error:" + objError.description);
    }

}

/**
 * 通过身份证读卡器与系统中义务人信息进行匹配核验
 */
function checkYwrByIdCard(name,zjzl,zjh) {
    try {
        var objTest = readIdCard();
        if (objTest.ReadCard()) {
            layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl'], function () {
                var pName = objTest.Name;
                var pCardNo = objTest.ID;
                var pAddress = objTest.Address;
                //当老证件15位，新证件号18位。处理新老证件号不匹配问题，将18位转为15位进行判断
                if (pCardNo.length != zjh.length) {
                    zjh = convertIdCard18To15(zjh);
                    pCardNo = convertIdCard18To15(pCardNo);
                }
                if (name === pName && zjzl === "1" && zjh === pCardNo) {
                    showAlertDialog("核验成功！");
                } else {
                    showAlertDialog("义务人信息不一致，请检查！");
                }
            });
        } else {
            layer.alert("请检查是否安装成功并重新放置！");
        }
    } catch (objError) {
        layer.alert("Fail to create object. error:" + objError.description);
    }
}

/**
 * 身份证读卡,根据读卡获取权利人信息,并插入数据库
 */
function readIdCardToQlr(xmid,djxl) {
    try {
        var objTest = readIdCard();
        if (objTest.ReadCard()) {
            var pName = objTest.Name;
            var pCardNo = objTest.ID;
            var pAddress = objTest.Address;
            var bdcQlrData = {};
            bdcQlrData.qlrmc = pName;
            bdcQlrData.zjzl = "1";
            bdcQlrData.zjh = pCardNo;
            bdcQlrData.txdz = pAddress;
            bdcQlrData.xmid = xmid;
            //权利人类别默认权利人
            bdcQlrData.qlrlb = "1";
            //权利人类型默认个人
            bdcQlrData.qlrlx = "1";
            insertIdCardQlr(bdcQlrData, djxl);
        } else {
            layer.alert("请检查是否安装成功并重新放置！");
        }
    } catch (objError) {
        layer.alert("Fail to create object. error:" + objError.description);
    }
}

/**
 * 身份证信息插入数据库
 */
function insertIdCardQlr(bdcQlrData,djxl) {
    var url = "";
    if (lclx === "pllc") {
        url = getContextPath() + "/slym/qlr/pllc?processInsId=" + processInsId;
    }
    if (lclx === "zhlc") {
        url = getContextPath() + "/slym/qlr/zhlc?processInsId=" + processInsId;
    }
    if (lclx === "plzh") {
        url = getContextPath() + "/slym/qlr/plzh?processInsId=" + processInsId +"&djxl="+djxl;
    }
    $.ajax({
        url: url,
        type: 'post',
        dataType: 'json',
        contentType: "application/json",
        data: JSON.stringify(bdcQlrData),
        success: function (data) {
            if (isNotBlank(data)) {
                loadQlr();
            }
        },
        error: function (e) {
            removeModal();
            delAjaxErrorMsg(e);
        }
    });
}

function readIdCardToSqr(xmid, qllx, djxl){
    try {
        var objTest = readIdCard();
        if (objTest.ReadCard()) {
            var pName = objTest.Name;
            var pCardNo = objTest.ID;
            var pAddress = objTest.Address;
            var bdcSqrData = {};
            bdcSqrData.sqrmc = pName;
            bdcSqrData.zjzl = "1";
            bdcSqrData.zjh = pCardNo;
            bdcSqrData.txdz = pAddress;
            bdcSqrData.xmid = xmid;
            //权利人类别默认权利人
            bdcSqrData.sqrlb = "1";
            //权利人类型默认个人
            bdcSqrData.sqrlx = "1";
            insertIdCardSqr(bdcSqrData, qllx, xmid, djxl);
        } else {
            layer.alert("请检查是否安装成功并重新放置！");
        }
    } catch (objError) {
        layer.alert("Fail to create object. error:" + objError.description);
    }
}

/**
 * 南通一窗页面读卡器增加权利人信息
 */
function insertIdCardSqr(bdcSqrData, qllx, xmid, djxl){
    addModel();
    var url =  getContextPath() + "/slym/sqr/sqrxx/save?gzlslid=" + processInsId +"&qllx="+qllx + "&djxl="+ djxl;
    var bdcSlSqrList =[];
    bdcSlSqrList.push(bdcSqrData);

    $.ajax({
        url: url,
        type: 'PATCH',
        dataType: 'json',
        contentType: "application/json",
        data: JSON.stringify(bdcSlSqrList),
        success: function (data) {
            if (isNotBlank(data)) {
                layer.msg('<img src="../../static/lib/bdcui/images/success-small.png" alt="">保存成功', {
                    time: 1000
                }, function () {
                    addModel();
                    loadYcslxx(xmid, $(".layui-show"));
                });
            }
            removeModal();
        },
        error: function (e) {
            removeModal();
            delAjaxErrorMsg(e);
        }
    });
}

//南通一窗页面读取领证人保存

function readIdCardToLzr() {
    try {
        var objTest = readIdCard();
        if (objTest.ReadCard()) {
            var pName = objTest.Name;
            var pCardNo = objTest.ID;
            var pAddress = objTest.Address;
            addModel();
            // var pName = "张三测试";
            // var pCardNo = "320102199003076072";
            // var pAddress = "江苏省南京市鼓楼区集庆门大街";
            var bdcLzr = {};
            bdcLzr.lzrmc = pName;
            bdcLzr.lzrzjzl = "1";
            bdcLzr.lzrzjh = pCardNo;
            // bdcLzr.txdz = pAddress;
            var $nowTab = $('.layui-show');
            var tabXmid = $nowTab.data('xmid');
            bdcLzr.xmid = tabXmid;
            bdcLzr.gzlslid = processInsId;
            insertIdCardToLzr(bdcLzr);
            removeModal();
        } else {
            layer.alert("请检查是否安装成功并重新放置！");
        }
    } catch (objError) {
        layer.alert("Fail to create object. error:" + objError.description);
    }
}

function insertIdCardToLzr(bdcLzr) {
    var lzrxxList = [];
    lzrxxList.push(bdcLzr);
    if (lzrxxList.length > 0) {
        $.ajax({
            url: getContextPath() + "/slym/lzr/lzrxx",
            type: 'PATCH',
            dataType: 'json',
            async: false,
            contentType: "application/json",
            data: JSON.stringify(lzrxxList),
            success: function (data) {
                loadLzr();
            }, error: function (xhr, status, error) {
                delAjaxErrorMsg(xhr)
            }
        });
    }
}