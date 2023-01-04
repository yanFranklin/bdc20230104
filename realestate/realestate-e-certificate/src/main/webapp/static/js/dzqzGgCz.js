layui.use(['jquery', 'layer', 'element', 'form', 'table', 'util', 'laydate'], function () {
    var $ = layui.jquery;
    var element = layui.element;
    var form = layui.form;
    var table = layui.table;
    var util = layui.util;
    var laydate = layui.laydate;
    // 签章类型，用于区分公告签章和一般签章
    var qzlx = "1";

    // 获取页面表单标识，用于权限控制
    var moduleCode = getQueryString('moduleCode');
    laydate.render({
        elem: '#cjsjqssj'
        , type: 'datetime'
    });
    laydate.render({
        elem: '#cjsjjssj'
        , type: 'datetime'
    });
    //提交表单
    form.on("submit(query)", function (data) {
        var queryData =data.field;
        queryData.qzlx = qzlx;
        queryData.moduleCode=moduleCode;
        tableReload('dzzzZzxxList', queryData);
        return false;
    });



    var tableConfig = {
        elem: '#dzzzZzxxList'
        , method: "POST"
        , url: '/realestate-e-certificate/Cz/listDzzzZzxxCzSignByPage'
        , where:{moduleCode:moduleCode,qzlx:qzlx}
        , cols: [[
            {checkbox: true}
            , {field: 'czzt', title: '持证主体'}
            , {field: 'ywr', title: '抵押人(义务人)'}
            , {field: 'zl', title: '坐落'}
            , {field: 'bdcdyh', title: '不动产单元号'}
            , {field: 'bdcqzh', title: '不动产权证号'}
            , {field: 'szsxqc', title: '行政区'}
            , {
                field: 'zzbgyy', title: '是否作废', templet: function (d) {
                    return zzbgyyDict(d.zzbgyy);
                }
            }
            , {
                field: 'qzzt', title: '是否签章', templet: function (d) {
                    return qzztDict(d.qzzt);
                }
            }
            , {title: '操作', align: 'center', fixed: 'right', width: 200, toolbar: '#zzxxListToolBarTmpl'}
        ]]
    };
    //加载表格
    loadDataTablbeByUrl("#dzzzZzxxList", tableConfig);

    table.on('tool(dataTable)', function (obj) {
        var data = obj.data;
        if (obj.event === "view") {
            viewPdf(data.zzqzlj);
            return false;
        } else if (obj.event === "sign") {
            if (2 == data.qzzt) {
                layer.msg(data.bdcqzh + "<br/> 已签章，不可重复签章！");
                return false;
            }
            sign("签章", data.zzid);
            return false;
        } else if (obj.event === "cancellation") {
            cancellation(data.zzbs);
            return false;
        }
    });
    $("#signs").click(function () {
        //idTest 即为基础参数 id 对应的值
        var checkStatus = table.checkStatus('dzzzZzxxList');
        var checkArr = checkStatus.data;
        if (checkArr.length == 0) {
            layer.msg("请至少选择一条要签章的数据！");
            return;
        }
        var checkData = [];
        //获取选中行的数据
        for (var i = 0; i < checkArr.length; i++) {
            if (2 == checkArr[i].qzzt) {
                layer.msg(checkArr[i].bdcqzh + "<br/> 已签章，不可重复签章！");
                return false;
            }
            checkData.push(checkArr[i].zzid);
        }
        sign("批量签章", checkData.join(","));
    });
});

function viewPdf(zzqzlj) {
    //文件管理器打开
    var zzqzljArr = zzqzlj.split("fid=");
    var nodeId = zzqzljArr[1];
    var bdcSlWjscDTO = queryBdcSlWjscDTO(nodeId);
    // bdcSlWjscDTO.spaceId = encodeURI("皖(2020)蜀山区不动产证明第6000086号_signa.pdf");
    bdcSlWjscDTO.storageUrl = document.location.protocol + "//" + document.location.host + "/storage";
    // bdcSlWjscDTO.storageUrl = "http://192.168.2.99:8030/storage";
    var url = getContextPath() + "/view/dzqz/sjd.html?paramJson=" + encodeURI(JSON.stringify(bdcSlWjscDTO));
    openSjcl(url, "附件上传");
}

function sign(operate, zzids) {
    var CryptAPICtrl = document.getElementById("CryptAPICtrl");
    var cspName = "IdeaBank Cryptographic Service Provider V1.0";
    var keyNumber = beginCryptAPI(CryptAPICtrl, cspName);
    if (keyNumber == -1) {
        layer.msg("请插入密钥盘或者密钥盘中不存在证书！");
        return false;
    }
    layer.prompt(
        {
            formType: 1
            , title: '请输入密码 ：并确认'
        }, function (value, index, elem) {
            var pwd = value;
            layer.close(index);

            var p;
            if (pwd == null) {
                p = 0;
            } else {
                p = setKeyPwd(CryptAPICtrl, pwd);
            }
            if (p != 0) {
                layer.msg("密钥盘密码错误或密钥盘被锁！");
                return;
            }

            var cert = CryptAPICtrl.KGCryptExportKey(0, 1, 0);
            if (cert == "") {
                layer.msg("获取签名证书失败！");
                return false;
            }

            var s = showLoad();
            // var ret={};
            var ret = ajaxQuery("/realestate-e-certificate/Cz/zzqzLocal", {"zzids": zzids}, "post", false
                , 'application/x-www-form-urlencoded');
            if (!ret || ret.head.status == "1") {
                closeLoad(s);
                layer.msg(ret.head.message);
                return false;
            }
            // ret.data = [];
            // var a = {};
            // var b = [];
            // a.sigKeepData="1";
            // a.needSigMessage="2";
            // a.id="房权证合瑶字第8120113098号_signa.pdf";
            // a.zzid='d998f55b40234fd28edf674d87d97d9e';
            // b.push(a);
            // ret.data = b;
            var pdfObjects = ret.data;
            var sigedData = new Array();
            for (var i = 0; i < pdfObjects.length; i++) {
                var obj = new Object();
                var pdfobj = pdfObjects[i];
                var sigData = sig(CryptAPICtrl, keyNumber, pdfobj.needSigMessage);
                // var sigData = "1122";
                obj.id = pdfobj.id;
                obj.sigData = sigData;
                obj.sigKeepData = pdfobj.sigKeepData;
                obj.needSigMessage = pdfobj.needSigMessage;
                obj.zzid = pdfobj.zzid;
                sigedData.push(obj);
            }

            var ret2 = ajaxQuery("/realestate-e-certificate/Cz/digitalSign"
                , {"sigedData": JSON.stringify(sigedData), "cert": cert, "operate": operate}, "post", false
                , 'application/x-www-form-urlencoded');

            closeLoad(s);
            layer.msg(ret2.head.message, {time: 2000});
            refreshView();
        });
}

function qzztDict(qzzt) {
    if ("2" == qzzt) {
        return "已签章";
    } else {
        return "未签章";
    }
}

function zzbgyyDict(zzbgyy) {
    if ("2" == zzbgyy) {
        return "已作废";
    } else {
        return "未作废";
    }
}

function beginCryptAPI(CryptAPICtrl, cspName, pwd) {
    var ctnArray = getCtnArray(CryptAPICtrl, cspName);
    var a = CryptAPICtrl.KGCryptInitialize(cspName, ctnArray, 1, 0);
    var ret = CryptAPICtrl.KGCryptGetUserKey(2);
    if (ret == 0) {
        return 2;
    } else {
        ret = CryptAPICtrl.KGCryptGetUserKey(1);
        if (ret == 0) {
            return 1;
        } else {
            return -1;
        }
    }
}

//得到容器
function getCtnArray(CryptAPICtrl, cspName) {
    var aCtnName = new Array();
    if (isTrue(CryptAPICtrl.KGCryptInitialize(cspName, "", 1,
        0xF0000000))) {
        aCtnName = CryptAPICtrl.KGCryptGetContainers(0).split("\n");
    }
    CryptAPICtrl.KGCryptFinalize(0);
    end(CryptAPICtrl);
    return aCtnName[0];
}

function isTrue(ret) {
    return ret == 0;
}

function setKeyPwd(CryptAPICtrl, pwd) {
    var hexPwd = toHex(pwd) + "00";
    var ret = CryptAPICtrl.KGCryptSetPin(hexPwd, hexPwd.length, 32);
    return ret;
}

function toHex(str) {
    var hex = '';
    for (var i = 0; i < str.length; i++) {
        hex += str.charCodeAt(i).toString(16);
    }
    return hex;
}

function sig(CryptAPICtrl, key, needSigData) {
    CryptAPICtrl.KGCryptCreateHash(0x00008004);
    CryptAPICtrl.KGCryptSetHashValue(needSigData, needSigData.length);
    var sigData = CryptAPICtrl.KGCryptSignHash(key, 0);
    CryptAPICtrl.KGCryptDestroyHash();
    if (sigData === "none") {
        layer.msg("签名取消");
        return false;
    }
    return sigData;
}

function end(CryptAPICtrl) {
    return CryptAPICtrl.KGCryptFinalize(0);
}

function getKeySN(CryptAPICtrl) {
    return CryptAPICtrl.WebGetKeySN();
}

function cancellation(zzbs) {
    layer.confirm('您确定要作废该证照吗？', {btn: ['确定', '取消'], title: "提示"}, function () {
        postDataToServer("/realestate-e-certificate/Cz/zzzfLocal", {zzbs: zzbs}, "post", true
            , 'application/x-www-form-urlencoded');
        refreshView();
    });
}


//查询收件材料所需参数（打开附件上传使用）
function queryBdcSlWjscDTO(nodeId) {
    var bdcSlWjscDTO = {};
    $.ajax({
        url: '/realestate-e-certificate/Cz/bdcSlWjscDTO',
        type: 'GET',
        async: false,
        data: {nodeId: nodeId},
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
        maxmin: true,
        end: function () {
        }
    });
    layer.full(index);
}

function getContextPath() {
    var pathName = document.location.pathname;
    var index = pathName.substr(1).indexOf("/");
    var result = pathName.substr(0, index + 1);
    return result;
}