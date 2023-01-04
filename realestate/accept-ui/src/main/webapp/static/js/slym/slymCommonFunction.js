layui.config({
    base: '../../static/' //静态资源所在路径
}).extend({response: 'lib/bdcui/js/response', formSelects: 'lib/form-select/formSelects-v4'});
var formSelects;
var gzldyid = getQueryString("processDefKey");
var sfmInfo = [];
layui.use(['jquery', 'table', 'layer', 'formSelects'], function () {
    table = layui.table;
    layer = layui.layer;
    formSelects = layui.formSelects;
});

function queryQlqtzkFjMb(xmid,$qlqtzkfj,mode,sfgx){
    getReturnData("/slym/ql/queryQlqtzkFjMb/nt",{xmid:xmid,mode:mode,hqysj:true,sfgx:sfgx},"GET",function (data) {
        //获取模板数据，不管是否为空
        $qlqtzkfj.val(data);
    },function (error) {
        delAjaxErrorMsg(error);

    })
}

function saveDagsd(data,processInsId) {
    var bdcXmfbData = {};
    bdcXmfbData.dagsd = data;
    var bdcDjxxUpdateQO = {};
    var whereMap = {};
    whereMap.gzlslid = processInsId;
    bdcDjxxUpdateQO.whereMap = whereMap;
    bdcDjxxUpdateQO.jsonStr = JSON.stringify(bdcXmfbData);
    getReturnData("/slym/xm/plxgxmfb", JSON.stringify(bdcDjxxUpdateQO), 'PATCH', function (data) {
        console.log("批量保存项目附表信息成功");
        ityzl_SHOW_SUCCESS_LAYER("保存成功");
    }, function (err) {
        throw err;
    });
}


// 淮安，领证方式不是电子证照时，带入sxh最小的权利人作为领证人，存量数据会存在没有sxh=1的，只有sxh=2的数据
function saveHfLzr(bdcQlrData, func, lclx, dydj, index, xmfb, djxl, qllx) {
    // 只有权利人才会保存对应的领证人 淮安保存顺序号最小 的作为领证人 当领证方式为“电子证照”时，领证人信息无需从权利人带入
    if (isNotBlank(bdcQlrData) && bdcQlrData.qlrlb === "1" && lzfs != 4) {
        var lzrList = [];
        var bdclzr = {};
        //权利人类型是企业时，将代理人信息带入领证人信息,其余是带入权利人
        if (isNotBlank(bdcQlrData.dlrmc) && 2 == bdcQlrData.qlrlx) {
            bdclzr.qlrid = bdcQlrData.qlrid;
            bdclzr.xmid = bdcQlrData.xmid;
            bdclzr.lzrmc = bdcQlrData.dlrmc;
            bdclzr.lzrzjzl = bdcQlrData.dlrzjzl;
            bdclzr.lzrzjh = bdcQlrData.dlrzjh;
            bdclzr.lzrdh = bdcQlrData.dlrdh;
        } else if (isNotBlank(bdcQlrData.qlrmc)) {
            bdclzr.qlrid = bdcQlrData.qlrid;
            bdclzr.xmid = bdcQlrData.xmid;
            bdclzr.lzrmc = bdcQlrData.qlrmc;
            bdclzr.lzrzjzl = bdcQlrData.zjzl;
            bdclzr.lzrzjh = bdcQlrData.zjh;
            bdclzr.lzrdh = bdcQlrData.dh;
        }
        lzrList.push(bdclzr);
        var url = "";
        if (lclx === "pllc") {
            url = "/slym/lzr/hf/lzrxx/pllc?gzlslid=" + processInsId + "&xmid=" + bdcQlrData.xmid + "&qlrid=" + bdcQlrData.qlrid;
        } else if (lclx === "plzh") {
            url = "/slym/lzr/hf/lzrxx/plzh?gzlslid=" + processInsId + "&qlrid=" + bdcQlrData.qlrid + "&djxl=" + djxl + "&xmid=" + bdcQlrData.xmid;
        } else {
            url = "/slym/lzr/hf/lzrxx?gzlslid=" + processInsId + "&qlrid=" + bdcQlrData.qlrid;
        }
        if (func === "insert") {
            getReturnData(url, JSON.stringify(lzrList), 'POST', function (data) {
                console.log("插入领证人信息成功");
                // 加载
                if (lclx === "pllc") {
                    loadLzrxx(xmfb);
                } else if (lclx === "plzh") {
                    loadLzrxx(bdcQlrData.xmid, djxl, "lzrxx" + index, index, qllx, xmfb);
                } else {
                    loadLzrxx("lzrxx" + index, index, bdcQlrData.xmid, xmfb);
                }
            }, function (xhr) {
                removeModal();
                delAjaxErrorMsg(xhr);
            })
        }

    }

}
// 批量流程保存领证方式
function saveLzfsPl(processInsId, lzfs) {
    var bdcXmfbData = {
        lzfs: lzfs
    };
    var bdcDjxxUpdateQO = {};
    var whereMap = {};
    whereMap.gzlslid = processInsId;
    bdcDjxxUpdateQO.whereMap = whereMap;
    bdcDjxxUpdateQO.jsonStr = JSON.stringify(bdcXmfbData);
    console.log(JSON.stringify(bdcDjxxUpdateQO));
    getReturnData("/slym/xm/plxgxmfb", JSON.stringify(bdcDjxxUpdateQO), 'PATCH', function (data) {
        console.log("批量保存项目附表信息成功");
    }, function (err) {
        throw err;
    });
}

// 批量组合流程保存领证方式
function saveLzfsPlzh(xmidListStr, processInsId, lzfs) {
    var xmidList = xmidListStr.split(",");
    var bdcXmfbData = {
        lzfs: lzfs
    };
    var bdcDjxxUpdateQO = {};
    var whereMap = {};
    whereMap.gzlslid = processInsId;
    whereMap.xmids = xmidList;
    bdcDjxxUpdateQO.whereMap = whereMap;
    bdcDjxxUpdateQO.jsonStr = JSON.stringify(bdcXmfbData);
    console.log(JSON.stringify(bdcDjxxUpdateQO));
    getReturnData("/slym/xm/plxgxmfb", JSON.stringify(bdcDjxxUpdateQO), 'PATCH', function (data) {
        console.log("批量保存项目附表信息成功");
    }, function (err) {
        throw err;
    });
}
// 组合流程保存领证方式
function saveLzfsZh(processInsId, xmid, lzfs) {
    var bdcXmfbData = {
        gzlslid: processInsId,
        lzfs: lzfs,
        xmid: xmid
    };
    console.log(JSON.stringify(bdcXmfbData));
    getReturnData("/slym/xm/fb", JSON.stringify(bdcXmfbData), 'PATCH', function (data) {
        console.log("保存项目附表成功");
    }, function (err) {
        delAjaxErrorMsg(err);
    }, false);
}


//扫描苏服码查询
function scanSfm(lclx, xmid, djxl) {
    //输入框并获取焦点信息.打开弹框，并设置获取隐藏输入框的焦点
    //1.打开弹框选择证照类型
    var index = layer.open({
        title: '选择证照类型',
        type: 1,
        skin: 'bdc-spf-layer',
        area: ['750px', '500px'],
        content: $("#chooseZzlx"),
        btn: ['查询', '增加','取消'],
        yes: function (index, layero) {
            //提交 的回调
            //先判断是否获取到苏服码内容
            var sfmxx = $("#sfmxx").val();
            if (isNotBlank(sfmxx)) {
                addModel();
                var csjzzxx = {};
                csjzzxx.zzlx = formSelects.value('zzlx', 'val').join(",");
                csjzzxx.gzlslid = processInsId;
                csjzzxx.gzldyid = gzldyid;
                csjzzxx.sfmnr = sfmxx;
                csjzzxx.lclx = lclx;
                csjzzxx.xmid = xmid;
                //组合流程新增权利人需要
                csjzzxx.djxl = djxl;
                getReturnData("/slym/qlr/sfm/cx", JSON.stringify(csjzzxx), "POST", function (data) {
                    removeModal();
                    if(isNullOrEmpty(data)){
                        ityzl_SHOW_WARN_LAYER("未获取到信息");
                        layer.close(index);
                        return;
                    }
                    sfmInfo = data;
                    var sfmnr = "  <tr class=\"gray-tr\">\n" +
                        "                    <td style=\"min-width: 250px\">证照名称</td>\n" +
                        "                    <td>证照号码</td>\n" +
                        "                    <td>持证者名称</td>\n" +
                        "                    <td width=\"200px\">操作</td>\n" +
                        "                </tr>";
                    for (var i = 0; i < data.length; i++) {
                        sfmnr += '  <tr>\n' +
                            '                    <td>\n' +
                            '                        <div class="bdc-td-box"> <input type="text" class="layui-input" value="'+data[i].name+'"> </div>\n' +
                            '                    </td>\n' +
                            '                    <td>\n' +
                            '                        <div class="bdc-td-box"> <input type="text" class="layui-input" value="'+data[i].id_code+'"> </div>\n' +
                            '                    </td>\n' +
                            '                    <td>\n' +
                            '                        <div class="bdc-td-box"> <input type="text" class="layui-input" value="'+data[i].holder_name+'"> </div>\n' +
                            '                    </td>\n' +
                            '                    <td align="center">\n' +
                            '                        <button type="button" class="layui-btn layui-btn-xs bdc-major-btn" onclick="sfmxq(\''+data[i].license_code+'\')">详情 </button>\n' +
                            '                        <button type="button" class="layui-btn layui-btn-danger layui-btn-xs bdc-delete-btn" onclick="addsfmfj(\''+data[i].license_code+'\')"> 导入 </button>\n' +
                            '                    </td>\n' +
                            '     </tr>';
                    }
                    $("#sfmlist").html("").append(sfmnr);
                    // layer.close(index);
                }, function (xhr) {
                    delAjaxErrorMsg(xhr);
                })
            } else {
                ityzl_SHOW_WARN_LAYER("请先扫描苏服码再查询");
                $("#sfmxx").focus();
            }

        }, btn2: function (index, layero) {
            //增加 的回调 -- 将个人信息填入业务流程权利人
            var sfmxx = $("#sfmxx").val();
            if (isNotBlank(sfmxx)) {
                addModel();
                var csjzzxx = {};
                csjzzxx.zzlx = formSelects.value('zzlx', 'val').join(",");
                csjzzxx.gzlslid = processInsId;
                csjzzxx.gzldyid = gzldyid;
                csjzzxx.sfmnr = sfmxx;
                csjzzxx.lclx = lclx;
                csjzzxx.xmid = xmid;
                //组合流程新增权利人需要
                csjzzxx.djxl = djxl;
                getReturnData("/slym/qlr/sfm/addqlr", JSON.stringify(csjzzxx), "POST", function (data) {
                    removeModal();
                    loadQlr();
                }, function (xhr) {
                    delAjaxErrorMsg(xhr);
                })
            } else {
                ityzl_SHOW_WARN_LAYER("请先扫描苏服码再查询");
                $("#sfmxx").focus();
            }
        }, btn3: function (index, layero) {
            //取消 的回调
            layer.close(index);
        }, cancel: function () {
            //右上角关闭回调
            layer.close(index);
        }, success: function (layero, index) {
            var zzlx = zdList.csjzzlx;
            formSelects.config('zzlx', {
                keyName: 'MC',            //自定义返回数据中name的key, 默认 name
                keyVal: 'DM'            //自定义返回数据中value的key, 默认 value
            }, true);
            formSelects.data('zzlx', 'local', {arr: zzlx});
            //设置焦点，选中输入框，这样扫码枪扫描信息会直接赋值
            $("#sfmxx").val('').focus();
            $("#sfmlist").html("");
        }
    });
}

function sfmxq(license_code){
    var sfmItem = {};
    for (var i = 0; i < sfmInfo.length; i++) {
        if(sfmInfo[i].license_code == license_code){
            sfmItem = sfmInfo[i];
        }
    }
    if(isNullOrEmpty(sfmItem)){
        return;
    }
    sfmItem.license_name = sfmItem.name;
    var getTpl = sfmxxTpl.innerHTML;

    laytpl(getTpl).render(sfmItem, function (html) {
        var detailIndex = layer.open({
            title: '苏服码详情',
            type: 1,
            skin: 'bdc-spf-layer',
            area: ['750px', '500px'],
            content: html,
            btn: ['确定'],
            yes: function (index, layero) {
                //取消 的回调
                layer.close(index);
            }, cancel: function () {
                //右上角关闭回调
                layer.close(index);
            }
        });
    });
}

function addsfmfj(license_code){
    var csjzzxx = {};
    csjzzxx.zzlx = formSelects.value('zzlx', 'val').join(",");
    csjzzxx.gzlslid = processInsId;
    csjzzxx.gzldyid = gzldyid;
    csjzzxx.sfmnr =  $("#sfmxx").val();
    csjzzxx.license_code =  license_code;
    getReturnData("/slym/qlr/sfm/addfj", JSON.stringify(csjzzxx), "POST", function (data) {
        ityzl_SHOW_SUCCESS_LAYER("导入成功");
    }, function (xhr) {
        delAjaxErrorMsg(xhr);
    })
}

//宣城 验收文件获取
function yswjhq(){
    var url = getContextPath() + "/xuancheng/slym/yswjhq.html?gzlslid=" + processInsId ;
    layer.open({
        type: 2,
        area: ['100%', '100%'],
        fixed: false, //不固定
        title: "获取验收文件",
        content: url
    });
}

//宣城 公证处文件查询
function gzcwjcx(){
    var alertIndex = layer.open({
        title: '信息输入',
        type: 2,
        skin: 'layui-layer-lan',
        area: ['430px', '300px'],
        content: getContextPath() +'/xuancheng/slym/gzcwjInput.html?gzlslid='+ processInsId,
    });
}

// 字符串的像素值
function getLenPx(str, font_size) {
    var str_leng = str.replace(/[^\x00-\xff]/gi, 'aa').length;
    return str_leng * font_size / 2
}

//人证对比，调用摩科评价器
function mkrzdb(sqrmc, sqrzjh) {
    addModel("");
    getReturnData("/pjq/mkrzdb", {qlrmc: sqrmc, qlrzjh: sqrzjh, gzlslid: processInsId}, "GET", function (result) {
        removeModal();
        if (result && result.code == "1") {
            if (result.data.status == "0") {
                showAlertDialog("姓名:" + result.data.name + "身份证号:" + result.data.cardNum + result.data.reason);
            } else {
                ityzl_SHOW_WARN_LAYER(result.data.reason)
            }
        } else {
            ityzl_SHOW_WARN_LAYER(result.message);
        }
    }, function (xhr) {
        delAjaxErrorMsg(xhr);
    })
}


// 宣城，安徽省好差评，调用省好差评的评价页面到评价器
function getXcPjqHcp() {
    var taskId = getQueryString("taskId");
    getReturnData("/pjq/hcp/pjymurl", {gzlslid: processInsId, taskId: taskId}, "GET", function (data) {
        if (data !== null) {
            if (data.code == "1") {
                ityzl_SHOW_SUCCESS_LAYER("调用好差评页面成功");
            }
        }
    }, function (err) {
        removeModal();
        delAjaxErrorMsg(err);
    })
}

function changeZjzlByQlrlx(data) {
    if(!isNullOrEmpty(qlrlxZjzlDzMap)) {
        var qlrlx = data.value;
        var $qlrlx = $(data.elem).parents('tr').find('select[name=qlrlx]');
        var $zjzl = $(data.elem).parents('tr').find('select[name=zjzl]');
        var zjzl = "";
        var zjzlZd = zdList.zjzl;
        for (var key in qlrlxZjzlDzMap) {
            if (qlrlx == key) {
                zjzl = qlrlxZjzlDzMap[key];
            }
        }
        if (isNotBlank(zjzl)) {
            var zjzlList = [];
            for (var i = 0; i < zjzlZd.length; i++) {
                if (zjzl.indexOf(zjzlZd[i].DM) > -1) {
                    zjzlList.push(zjzlZd[i]);
                }
            }
            zjzlList = zjzlList.length > 0 ? zjzlList : zjzlZd;
            $zjzl.empty();
            $zjzl.append('<option value="">请选择</option>');

            $.each(zjzlList, function (index, item) {
                $zjzl.append('<option value="' + item.DM + '">' + item.MC + '</option>');
            });
            form.render('select');
        } else {
            $zjzl.empty();
            $zjzl.append('<option value="">请选择</option>');
            $.each(zjzlZd, function (index, item) {
                $zjzl.append('<option value="' + item.DM + '">' + item.MC + '</option>');
            });
            form.render('select');
        }
    }
}

function changeqlrlxByZjzl(data){
    if(!isNullOrEmpty(qlrlxZjzlDzMap)) {
        var zjzl = data.value;
        for (var key in qlrlxZjzlDzMap) {
            if (qlrlxZjzlDzMap[key].indexOf(zjzl) > -1) {
                var $qlrlx = $(data.elem).parents('tr').find('select[name=qlrlx]');
                $qlrlx.val(key);
                break;
            }
        }
        if (isNotBlank(yzsfhytsfhzjzl)) {
            var $zjh = $(data.elem).parents('tr').find('input[name=zjh]');
            var newattr = $zjh.attr("lay-verify");
            if (yzsfhytsfhzjzl.indexOf(zjzl) > -1) {
                if (isNotBlank(newattr) && newattr.indexOf("sfhytsfh") < 0) {
                    $zjh.attr("lay-verify", newattr + "|sfhytsfh");
                } else if (zjzl !== null && zjzl !== "") {
                    $zjh.attr("lay-verify", "sfhytsfh");
                } else if ((zjzl === null || zjzl === "") && isNotBlank(newattr) && newattr.indexOf("sfhytsfh") > -1) {
                    $zjh.attr("lay-verify", newattr.replace("sfhytsfh", ""));
                }
            } else {
                if (isNotBlank(newattr)) {
                    $zjh.attr("lay-verify", newattr.replace("sfhytsfh", ""));
                }
            }
        }
        form.render('select');
    }
}

/**
 * 添加证件号是否含有特殊符号验证
 */
function addzjhYz(){
    $.each($("select[name=zjzl]"), function (i) {
        if(isNotBlank(yzsfhytsfhzjzl)){
            var zjzl = $(this).val();
            if(yzsfhytsfhzjzl.indexOf(zjzl) > -1){
                var $zjh = $(this).parents('tr').find('input[name=zjh]');
                if($zjh.length > 0 ){
                    var newattr  = $zjh.attr("lay-verify");
                    if (isNotBlank(newattr) && newattr.indexOf("sfhytsfh") < 0) {
                        $zjh.attr("lay-verify", newattr + "|sfhytsfh");
                    } else if (zjzl !== null && zjzl !== "") {
                        $zjh.attr("lay-verify", "sfhytsfh");
                    }else if ((zjzl === null || zjzl === "") && isNotBlank(newattr) && newattr.indexOf("sfhytsfh") > -1) {
                        $zjh.attr("lay-verify", newattr.replace("sfhytsfh", ""));
                    }
                }
            }
        }

    });
}