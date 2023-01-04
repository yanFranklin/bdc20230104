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
                var elementName =$(element)[0].name;
                $(forms).find("input[name="+elementName+"]").val(trimStr(pName));
                $(forms).find("select[name="+zjzlName+"]").val("1");
                $(forms).find("input[name="+zjhName+"]").val($.trim(pCardNo));
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

function cdrReadIdCardFun(element) {
    try {
        var objTest = readIdCard();
        if (objTest.ReadCard()) {
            layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl'], function () {
                var pName = objTest.Name;
                var pCardNo = objTest.ID;
                var pAddress = objTest.Address;
                var forms = element.parentNode.parentNode.parentNode;
                $(forms).find("input[name='cdr']").val(trimStr(pName));
                $(forms).find("select[name='cdrzjzl']").val("1");
                $(forms).find("input[name='cdrzjh']").val($.trim(pCardNo));
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

function dlrReadIdCardFun(element) {
    try {
        var objTest = readIdCard();
        if (objTest.ReadCard()) {
            layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl'], function () {
                var pName = objTest.Name;
                var pCardNo = objTest.ID;
                var pAddress = objTest.Address;
                var forms = element.parentNode.parentNode.parentNode;
                $(forms).find("input[name='dlr']").val(trimStr(pName));
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

function zjhReadIdCardFun(element) {
    try {
        var objTest = readIdCard();
        if (objTest.ReadCard()) {
            layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl'], function () {
                var pName = objTest.Name;
                var pCardNo = objTest.ID;
                var pAddress = objTest.Address;
                var forms = element.parentNode.parentNode.parentNode;
                $(forms).find("input[name='jtcymc']").val(trimStr(pName));
                $(forms).find("input[name='zjh']").val(trimStr(pCardNo));
                $(forms).find("select[name='bcxrzjzl']").val("1");
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

