function ReadIDCardNewQlr(element) {
    try {
        // var forms = element.parentNode.parentNode.parentNode;
        var objTest = readIdCard();
        if (objTest.ReadCard()) {
            layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl'], function () {
                var pName = objTest.Name;
                var pCardNo = objTest.ID;
                var pAddress = objTest.Address;
                // var pName = "张三测试";
                // var pCardNo = "320682201904241623";
                // var pAddress = "江苏省南京市鼓楼区集庆门大街";
                var forms = element.parentNode.parentNode.parentNode;
                $(forms).find("input[name='qlrmc']").val(trimStr(pName));
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

function ReadIDCardNewQlrAndZjh(element) {
    try {
        var objTest = readIdCard();
        if (objTest.ReadCard()) {
            layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl'], function () {
                var pName = objTest.Name;
                var pCardNo = objTest.ID;
                var pAddress = objTest.Address;
                var forms = element.parentNode.parentNode.parentNode;
                $(forms).find("input[name='qlr']").val(trimStr(pName));
                $(forms).find("input[name='qlrmc']").val(trimStr(pName));
                $(forms).find("input[name='ywr']").val(trimStr(pName));
                $(forms).find("input[name='singleQlr']").val(trimStr(pName));

                $(forms).find("input[name='qlrzjh']").val(trimStr(pCardNo));
                $(forms).find("input[name='ywrzjh']").val(trimStr(pCardNo));
                $(forms).find("input[name='zjh']").val(trimStr(pCardNo));
                $(forms).find("input[name='singleZjh']").val(trimStr(pCardNo));
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
 * 身份证读卡器读取信息到页面
 */
function readxxByIdCard(element,zjzlName,zjhName) {
    try {
        var objTest = readIdCard();
        if (objTest.ReadCard()) {
            layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl'], function () {
                var pName = objTest.Name;
                var pCardNo = objTest.ID;
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

function ReadIDCardNewYwrAndZjh(element) {
    try {
        var objTest = readIdCard();
        if (objTest.ReadCard()) {
            layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl'], function () {
                var pName = objTest.Name;
                var pCardNo = objTest.ID;
                var pAddress = objTest.Address;
                var forms = element.parentNode.parentNode.parentNode;
                $(forms).find("input[name='ywr']").val(trimStr(pName));
                $(forms).find("input[name='ywrzjh']").val(trimStr(pCardNo));
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

function ReadIDCardNewZf(element) {
    try {
        var objTest = readIdCard();
        if (objTest.ReadCard()) {
            layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl'], function () {
                var pName = objTest.Name;
                var pCardNo = objTest.ID;
                var pAddress = objTest.Address;
                var forms = element.parentNode.parentNode.parentNode;
                $(forms).find("input[name='qlr']").val(trimStr(pName));
                $(forms).find("input[name='zjh']").val(trimStr(pCardNo));
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

function ReadIDCardGrzf(element) {
    try {
        var objTest = readIdCard();
        if (objTest.ReadCard()) {
            layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl'], function () {
                var pName = objTest.Name;
                var pCardNo = objTest.ID;
                var pAddress = objTest.Address;
                var forms = element.parentNode.parentNode.parentNode;
                $(forms).find("input[name='qlrmc']").val(trimStr(pName));
                $(forms).find("input[name='zjh']").val(trimStr(pCardNo));
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
