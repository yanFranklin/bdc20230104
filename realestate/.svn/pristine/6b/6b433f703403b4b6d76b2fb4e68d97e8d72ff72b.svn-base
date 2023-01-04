
// 获取受理页面信息
var xmid = getQueryString("xmid");
var htbh= getQueryString("htbh");
var fwlx = getQueryString("fwlx");
var processInsId = getQueryString("processInsId");

var zdList;
var formIds = "";
var $,form;
var dzht;
layui.use(['jquery', 'layer', 'element', 'form', 'laydate', 'laytpl'], function () {
    form = layui.form;
    $ = layui.jquery;
    var  layer = layui.layer,
        element = layui.element,
        laydate = layui.laydate,
        laytpl = layui.laytpl;

    // $(document).on('click','#search',function () {
    //     htbh = $('#cxhtbh').val();
    //     generateHtxx();
    // })

    generateHtxx();
    function generateHtxx(){
        if(!isNotBlank(htbh)){
            ityzl_SHOW_INFO_LAYER("未获取到合同编号信息，请输入合同编号后在进行查询。");
            return;
        }
        var tpl;
        if(fwlx == "clf"){
            tpl = clfHtxxTpl.innerHTML;
        }else{
            tpl = spfHtxxTpl.innerHTML;
        }
        cxHtxx(htbh).done(function(data){
            if(isNotBlank(data)){
                //获取字典
                var zdList = {a:[]};
                getReturnData("/bdczd",{},"POST",function (zddata) {
                    if (isNotBlank(zddata)) {
                        zdList = zddata;
                    }
                },function () {
                },false);
                data.zd=zdList;
                var view = document.getElementById('showHtxx');
                //渲染数据
                laytpl(tpl).render(data, function (html) {
                    view.innerHTML = html;
                });
                form.render();
                resetSelectDisabledCss();
                // 房屋编号类型代码转换文字显示
                    var fwbhlxMc = "";
                    switch (data.htxx.fwbhlx) {
                        case "1":
                            fwbhlxMc = "施工编号";
                            break;
                        case "2":
                            fwbhlxMc = "公安编号";
                            break;
                    }
                    $('#fwbhlx').val(fwbhlxMc);
            }else {
                ityzl_SHOW_WARN_LAYER("未查询到合同信息！");
            }
        });
    }

});

// 根据合同编号查询合同信息
function cxHtxx(htbh) {
    var deferred = $.Deferred();
    addModel();
    $.ajax({
        url: getContextPath()+"/ycsl/jyxx/cz/queryFcjyxxByHtbh",
        type: 'POST',
        dataType: 'json',
        data: {
            xmid: xmid,
            fwlx: fwlx,
            htbh: htbh
        },
        success: function (data) {
            removeModal();
            if(isNotBlank(data)){
                dzht = data.dzht;
                if(isNotBlank(data.basj)){
                    data.basj = Format(format(parseInt(data.basj)),"yyyy-MM-dd");
                }
                if(fwlx == "clf"){
                    var clfData = filterClfHtxx(data);
                    deferred.resolve(clfData);
                }else{
                    var spfData = filterSpfHtxx(data);
                    deferred.resolve(spfData);
                }
            }else{
                deferred.resolve(data);
            }
        },
        error: function (xhr, status, error) {
            removeModal();
            delAjaxErrorMsg(xhr);
            deferred.reject();
        }
    });
    return deferred;
}
// 过滤解析存量房合同信息数据
function filterClfHtxx(data){
    var msrxx = [];
    var cmrxx = [];
    if(data.qlrxx.length >0){
        $.each(data.qlrxx, function(index ,val){
            if(val.qlrlb == "1"){
                val.qlrlbmc="买方";
                msrxx.push(val);
            }else{
                val.qlrlbmc="卖方";
                cmrxx.push(val);
            }
        });
    }
    return {
        htxx :  data,
        cmr : cmrxx,
        msr : msrxx,
        msrdlrxx: filterDlrxx(msrxx),
        cmrdlrxx: filterDlrxx(cmrxx)
    };
}

function filterSpfHtxx(data){
    var cmrxx = [];
    cmrxx.push({
        cmr: data.cmr,
        cmrzjzl : data.cmrzjzl,
        cmrzjh : data.cmrzjh
    });
    var msrxx = [];
    msrxx.push({
        msrmc : data.msrmc,
        msrzjzl: data.msrzjzl,
        msrzjh : data.msrzjh,
        msrlxdz : data.msrlxdz,
        msrlxdh : data.msrlxdh
    });
    return {
        htxx : data,
        cmr : cmrxx,
        msr : msrxx,
        dlrxx : {
            dlrmc: data.wtdlr,
            dlrzjh: data.wtdlrzjh,
            dlrzjzl: data.wtdlrzjzl
        }
    }
}

// 处理代理人信息
function filterDlrxx(array) {
    if(array.length>0){
        return {
            dlrmc: array[0].qlrdlr,
            dlrzjh: array[0].qlrdlrzjh,
            dlrzjzl: array[0].qlrdlrzjzl
        };
    }
    return "";
}

function showDzht(){
    $.ajax({
        url: getContextPath() + "/sjcl/getFileStorageUrl",
        type: 'GET',
        dataType: 'json',
        data: {gzlslid: processInsId, wjjmc : "买卖合同"},
        success: function (data) {
            removeModal();
            console.info(data);
            if(data){
                var htxxIndex = layer.open({
                    title: '查看电子合同',
                    type: 1,
                    area: ['960px', '500px'],
                    content: $('#bdc-popup-large'),
                    success : function (layero, index) {
                        var src = data[0].downUrl;
                        $('#seeImgView').html('<iframe src="' + getContextPath() + "/static/lib/pdf/web/viewer.html?file=" + encodeURIComponent(src) +'"></iframe>');
                    },
                    cancel: function () {
                        //右上角关闭回调
                    }
                });
                layer.full(htxxIndex);
            }else{
                ityzl_SHOW_WARN_LAYER("未获取到电子合同信息");
            }
        }, error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr);
            removeModal();
        }
    });
}

function showFile() {
    if(!isNotBlank(dzht)){
        ityzl_SHOW_INFO_LAYER("未获取到电子合同信息内容。");
        return;
    }
    var dzhtnr = dzht.replace(/\s*/g,""),
        fileName = "电子合同.pdf",
        mime = "application/pdf";
    var byte = base64ToByte(dzhtnr); // base64编码字符串转换二进制流
    if(isIE()){ //修复IE10无法使用Blob进行文件下载
        window.navigator.msSaveOrOpenBlob(new Blob([byte], { type: mime }),fileName);
    }else{
        var reader = new FileReader();
        var blob = new Blob([byte]);
        reader.readAsDataURL(blob);
        reader.onload = function (e) {
            // 转换完成，创建一个a标签用于下载
            var a = document.createElement('a');
            a.download = fileName;
            a.href = e.target.result;
            $("body").append(a);    // 修复firefox中无法触发click
            a.click();
            $(a).remove();
        }
    }
}

//判断是否IE浏览器
function isIE() {
    if (!!window.ActiveXObject || "ActiveXObject" in window) {
        return true;
    } else {
        return false;
    }
}

// base64解码转为二进制流
function base64ToByte(base64Str) {
    var decodeStr = atob(base64Str);
    var len = decodeStr.length;
    var byte = new Uint8Array(len);
    while(len--){
        byte[len] = decodeStr.charCodeAt(len);
    }
    return byte;
}

