function qlrReadIDCardNew(element) {
    try {
       var objTest = readIdCard();
       if (objTest.ReadCard()) {
            layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl'], function () {
                var pName = objTest.Name;
                //var pName = "测试";
                var forms = element.parentNode.parentNode.parentNode;
                $(forms).find("input[name='qlr']").val(trimStr(pName));

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
                var pAddress = objTest.Address;
                var forms =$(element).parents("form");
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
