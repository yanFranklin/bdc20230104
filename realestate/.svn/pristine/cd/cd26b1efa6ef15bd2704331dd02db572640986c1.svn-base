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

function ReadIDCardNewSqr(element, fzklb) {
    try {
       var objTest = readIdCard();
       if (objTest.ReadCard()) {
            layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl', 'formSelects'], function () {
                var pName = objTest.Name;
                var pCardNo = objTest.ID;
                var pAddress = objTest.Address;
                var xb = objTest.Sex;
                var csrq = objTest.BirthDate;
                var mz = objTest.Native;
                var qlrzjqfjg = objTest.Organ;
                var yxqx = objTest.Begin + "到" + objTest.End;
                // var pName = "张三测试";
                // var pCardNo = "320102199003076072";
                // var pAddress = "江苏省南京市鼓楼区集庆门大街";
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
                if(isNotBlank(xb)){
                    if(xb.indexOf("男") >-1){
                        xb ="1";
                    }else if(xb.indexOf("女") >-1){
                        xb ="2";
                    }else{
                        xb ="3";
                    }
                    $(forms).find("input[name='xb']").val(trimStr(xb));
                    $(forms).find("select[name='xb']").val(trimStr(xb));
                }
                $(forms).find("input[name='csrq']").val(trimStr(csrq));
                $(forms).find("input[name='mz']").val(trimStr(mz));
                $(forms).find("input[name='qlrzjqfjg']").val(trimStr(qlrzjqfjg));
                $(forms).find("input[name='yxqx']").val(trimStr(yxqx));


                if($(forms).find("select[name='qlrly']") && $(forms).find("select[name='qlrly']").length > 0){
                    console.log("读卡成功，为权利人来源赋值为2");
                    $(forms).find("select[name='qlrly']").val("2");
                }


                var form = layui.form;
                form.render('select');
                resetSelectDisabledCss();
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

                // 代理人支持多个 读卡器这里赋值  如果文本框里面有值 则自动加 , 分隔
                var cunrrentName = $(forms).find("input[name='dlrmc']").val();
                if(cunrrentName){
                    $(forms).find("input[name='dlrmc']").val(cunrrentName + "," + trimStr(pName));
                }else{
                    $(forms).find("input[name='dlrmc']").val(trimStr(pName));
                }

                var currentZjh = $(forms).find("input[name='dlrzjh']").val();
                if(currentZjh){
                    $(forms).find("input[name='dlrzjh']").val(currentZjh + "," + $.trim(pCardNo));
                }else{
                    $(forms).find("input[name='dlrzjh']").val($.trim(pCardNo));
                }

                $(forms).find("select[name='dlrzjzl']").val("1");

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
                if(isNotBlank(zjzlName)){
                    $(forms).find("select[name="+zjzlName+"]").val("1");
                }
                if(isNotBlank(zjhName)){
                    $(forms).find("input[name="+zjhName+"]").val($.trim(pCardNo));
                }
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
 * 身份证读卡器读取信息至领证人信息
 */
function ReadIDCardNewLzr(element){
    try {
        var objTest = readIdCard();
        if (objTest.ReadCard()) {
            layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl'], function () {
                var pName = objTest.Name;
                var pCardNo = objTest.ID;
                var pAddress = objTest.Address;
                var forms = element.parentNode.parentNode.parentNode;
                $(forms).find("input[name='lzrmc']").val(trimStr(pName));
                $(forms).find("select[name='lzrzjzl']").val("1");
                $(forms).find("input[name='lzrzjh']").val($.trim(pCardNo));
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

/*
* 读取查档人信息--常州查档
* */

function readIdCardCdr(element) {
    try {
        var objTest = readIdCard();
        if (objTest.ReadCard()) {
            layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl'], function () {
                var pName = objTest.Name;
                var pCardNo = objTest.ID;
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

//首套房证明页面新增查询申请人
function readIdCardStfCxSqr(element) {
    try {
        var objTest = readIdCard();
        if (objTest.ReadCard()) {
            layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl'], function () {
                var pName = objTest.Name;
                var pCardNo = objTest.ID;
                // var pName = "张三测试";
                // var pCardNo = "320102199003076072";
                // var pAddress = "江苏省南京市鼓楼区集庆门大街";
                var forms = element.parentNode.parentNode.parentNode;
                $(forms).find("input[name='sqrmc']").val(trimStr(pName));
                // $(forms).find("select[name='zjzl']").val("1");
                $(forms).find("input[name='zjh']").val(trimStr(pCardNo));
            });
        } else {
            layer.alert("请检查是否安装成功并重新放置！");
        }
    } catch (objError) {
        layer.alert("Fail to create object. error:" + objError.description);
    }
}

