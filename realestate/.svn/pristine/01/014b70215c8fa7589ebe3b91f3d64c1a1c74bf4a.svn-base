/**
 * 印制号入库初始化状态为已领用
 * @type {number}
 */
var yly = 6;
/**
 *
 * @type {string} 已使用
 */
var ysy = 3;
/**
 *
 * @type {string} 印制号已使用原因
 */
var ysyyy = "证书印制号获取";

/**
 *
 * @type {string} 印制号已使用原因
 */
var yzhhy = "证书印制号还原";
/**
 * 不需要打印预览，直接打印
 */
var hideModel;

// 验证证号没有空
var verifyBdcqzhNotNull = true;

// 是否已登簿成功
var dbSucceed = false;

// 当前点击登簿的次数，避免遮罩没有限制住，多次点击
var sddbClickNum = 0;

// 非现势的证书权属状态信息（所有的证书都未登簿或已注销，返回-1）
var invalidZsQszt;

// 是否需要验证完税,默认不验证，只有产权，单个流程需要验证
var yzws = false;
// 是否已完税（验证完税信息（合肥不动产登记平台所有涉税业务登簿前检测其税票真伪性。））
var sfyws = false;
// 验证完税提示信息（验证完税信息（合肥不动产登记平台所有涉税业务登簿前检测其税票真伪性。））
var wsyzxx = "";
// 房源编号（验证完税信息（合肥不动产登记平台所有涉税业务登簿前检测其税票真伪性。））
var fybh;
// 验证完税提示信息（验证完税信息（合肥不动产登记平台所有涉税业务登簿前检测其税票真伪性。））
var lzryzxx = "";
// 登簿后需要刷新表格数据的页面
var refreshName = "bdcZsList";

var gzlslid;

var warnLayer;
// 过滤规则验证
var glGzyz = false;

queryByl();

// 该变量记录，进入页面后从数据库中查询的权证印刷序列号
var queryQzysxlh = "";
// 该变量记录，点击获取印制号返回成功的权证印刷序列号
var getQzysxlh = "";
// 该变量记录，点击获取印制号返回成功的印制号id
var getYzhid = "";

var dzzzUrl = '';

var jbr = "";

queryCurrentUser();
function queryCurrentUser() {
    $.ajax({
        url: '/realestate-register-ui/rest/v1.0/user/info',
        type: 'GET',
        dataType: 'json',
        async: false,
        success: function (data) {
            if (data) {
                jbr = data;
            }
        }, error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });
}

// 获取打印配置(不预览)
function queryByl() {
    $.ajax({
        url: '/realestate-register-ui/rest/v1.0/url/dyModel',
        type: 'GET',
        dataType: 'json',
        async: false,
        success: function (data) {
            if (data) {
                hideModel = data;
            }
        }, error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });
}

// 获取证书预览的数据（多本证书）
function getZsylDataList(processInsId) {
    var zsylList;
    $.ajax({
        url: '/realestate-register-ui/rest/v1.0/zsxx/zsylList/' + processInsId,
        type: "GET",
        // data: JSON.stringify(zsidArr),
        contentType: 'application/json',
        dataType: "json",
        async: false,
        success: function (data) {
            zsylList = data;
        }, error: function () {
        }
    });
    return zsylList;
}

// 保存缮证人信息
function saveSzr(zsidArr, gzlslid) {
    var szxx;
    $.ajax({
        url: "/realestate-register-ui/rest/v1.0/zsxx/szr?gzlslid=" + gzlslid,
        type: "POST",
        data: JSON.stringify(zsidArr),
        contentType: 'application/json',
        dataType: "json",
        async: false,
        success: function (data) {
            szxx = data;
        }, error: function () {
        }
    });
    return szxx;
}

// 异步保存缮证人信息
function asyncSaveSzr(zsidArr, gzlslid) {
    $.ajax({
        url: "/realestate-register-ui/rest/v1.0/zsxx/szr?gzlslid=" + gzlslid,
        type: "POST",
        data: JSON.stringify(zsidArr),
        contentType: 'application/json',
        dataType: "json",
        // async: false,
        success: function (data) {
        }, error: function () {
        }
    });
}

/**
 * 验证是不是所有证书已保存印制号
 * @param gzlslid
 */
function allZsGetYzh(gzlslid) {
    var zsxx;
    $.ajax({
        url: "/realestate-register-ui/rest/v1.0/zsxx/checkZsYzh/" + gzlslid,
        type: "GET",
        contentType: 'application/json',
        // dataType: "json",
        async: false,
        success: function (data) {
            if (data) {
                zsxx = data;
            }
        }, error: function () {
        }
    });
    return zsxx;
}

// 获取当前流程的证书类型
function getGzlZslx(gzlslid) {
    var zslxList;
    $.ajax({
        url: "/realestate-register-ui/rest/v1.0/zsxx/zslx?gzlslid=" + gzlslid,
        type: "GET",
        contentType: 'application/json',
        async: false,
        success: function (data) {
            if (data) {
                zslxList = data;
            }
        }, error: function () {
        }
    });
    return zslxList;
}

/**
 * 更新证书的权利其他状况和附记
 */
function updateZsQlqtzkAndFj(param) {
    $.ajax({
        url: "/realestate-register-ui/rest/v1.0/zsxx",
        type: "PATCH",
        data: JSON.stringify(param),
        contentType: 'application/json',
        dataType: "json",
        async: false,
        success: function (data) {
            if (data) {
                successMsg("保存成功！");
            }
        }, error: function () {
            warnMsg("附记和权利其他状况保存失败，请重试！");
        }
    });
}

/**
 * 作废印制号
 * @param bdcYzhQO
 */
function zfYzh(bdcYzhQO) {
    var result = false;
    $.ajax({
        url: "/realestate-register-ui/rest/v1.0/yzh/zfyzh",
        type: "POST",
        data: JSON.stringify(bdcYzhQO),
        contentType: 'application/json',
        async: false,
        success: function (data) {
            if (data && data == true) {
                successMsg("印制号作废成功！");
                result = data;
            } else if (data && data.code) {
                warnMsg(data.msg);
            } else {
                warnMsg(data);
            }
        },
        error: function () {
            warnMsg("作废印制号异常！");
        }
    });
    return result;
}

/**
 * 还原印制号
 * @param bdcYzhQO
 */
function hyYzh(bdcYzhQO) {
    var result = false;
    $.ajax({
        url: "/realestate-register-ui/rest/v1.0/yzh/hyyzh",
        type: "POST",
        data: JSON.stringify(bdcYzhQO),
        contentType: 'application/json',
        async: false,
        success: function (data) {
            if (data && data == true) {
                successMsg("印制号还原成功！");
                result = data;
            } else if (data && data.code) {
                warnMsg(data.msg);
            } else {
                warnMsg(data);
            }
        },
        error: function () {
            warnMsg("还原印制号异常！");
        }
    });
    return result;
}

/**
 * 更新印制号使用情况
 */
function updateBdcYzhSyqk(bdcYzhSyqkQO) {
    var result = false;
    $.ajax({
        url: "/realestate-register-ui/rest/v1.0/yzhsyqk",
        type: "POST",
        data: JSON.stringify(bdcYzhSyqkQO),
        contentType: 'application/json',
        dataType: "json",
        async: false,
        success: function (data) {
            if (data && data.code) {
                warnMsg(data.msg);
            } else if (data) {
                successMsg("印制号更新成功！");
                result = true;
            } else {
                warnMsg("印制号更新异常！");
            }
        },
        error: function () {
            warnMsg("印制保存异常！");
        }
    });
    return result;
}

/**
 * 房屋清单打印
 * @param processInsId
 * @param zsid
 */
function printFwqd(processInsId, zsid, fwqdh) {
    var dataUrl = getIP() + "/realestate-register-ui/rest/v1.0/print/dyfwqd/" + fwqdh + "/xmids/xml?gzlslid=" + processInsId + "&zsid=" + zsid;
    print(fwqdModelUrl, dataUrl, false);
}

/**
 * 单个证书打印
 */
function zsPrint(modelUrl, zsid, zslx) {

    // 权属状态为现势才允许打印(现在由页面控制打印按钮是否展示)
    var userName = "";
    if(isNotBlank(jbr)){
        userName = encodeURI(jbr.username);
    }
    var dataUrl = getIP() + "/realestate-register-ui/rest/v1.0/print/zs/" + zsid + "/" + zslx + "/singleXml?userName="+ userName;
    print(modelUrl, dataUrl, hideModel);
    // 打印完成后再制证
    syncDzzz(zsid);
    // 保存打印数量信息
    saveZszmPrintInfo(zslx, null, zsid);
}

/**
 * @param zsidList 需要打印的证书ID
 * @param modelUrl 打印模板地址
 * @param zslx 打印的证书类型
 * 证书批量打印
 */
function batchZsPrint(modelUrl, zsidList, zslx) {
    // var dataUrl = getIP() + "/realestate-register-ui/rest/v1.0/print/batchZs/" + zslx + "?zsidList=" + zsidList;
    // print(modelUrl, dataUrl, false);
    // 当参数过长时，fr3无法打印，先ajax post方式把参数保存的redis里面，返回key
    // 再根据key取出参数，在后台查询数据
    $.ajax({
        // url: "/realestate-register-ui/rest/v1.0/print/batchzsprint?listZsidsStr=" + zsidList,
        // type: "POST",
        // //data: {listZsidsStr:JSON.stringify(zsidList)},
        // contentType: 'application/json',
        // dataType: "text",
        url: "/realestate-register-ui/rest/v1.0/print/batchzsprintsavetoredis",
        type: "POST",
        data: JSON.stringify({"listZsidsStr":zsidList}),
        contentType: 'application/json',
        success: function (key) {
            if (key) {
                var dataUrl = getIP() + "/realestate-register-ui/rest/v1.0/print/batchZs/" + zslx + "/" + key;
                console.log(dataUrl);
                print(modelUrl, dataUrl, false);
            } else {
                layer.msg('<img src="../../static/lib/bdcui/images/error-small.png" alt="">系统异常，请重试！');
            }
        }
    });
}

/**
 * （南通）证书批量打印
 * @param zsidList 需要打印的证书ID
 * @param modelUrl 打印模板地址
 * @param zslx 打印的证书类型
 */
function ntBatchZsPrint(modelUrl, zsidList, zslx) {
    // var dataUrl = getIP() + "/realestate-register-ui/rest/v1.0/print/batchZs/" + zslx + "?zsidList=" + zsidList;
    // print(modelUrl, dataUrl, false);
    // 当参数过长是，fr3无法打印，先ajax post把参数保存的redis里面，返回key
    // 再根据key取出参数，在后台查询数据眼
    var userName = "";
    if(isNotBlank(jbr)){
        userName = encodeURI(jbr.username);
    }
    $.ajax({
        // url: "/realestate-register-ui/rest/v1.0/print/batchzsprint?listZsidsStr=" + zsidList,
        // type: "POST",
        // contentType: 'application/json',
        // dataType: "text",
        url: "/realestate-register-ui/rest/v1.0/print/batchzsprintsavetoredis",
        type: "POST",
        data: JSON.stringify({"listZsidsStr":zsidList}),
        contentType: 'application/json',
        success: function (key) {
            if (key) {
                var dataUrl = getIP() + "/realestate-register-ui/rest/v1.0/print/nantong/batchZs/" + zslx + "/" + key +"?userName=" + userName;
                console.log(dataUrl);
                print(modelUrl, dataUrl, false);
            } else {
                layer.msg('<img src="../../static/lib/bdcui/images/error-small.png" alt="">系统异常，请重试！');
            }
        }
    });

    // 保存打印数量信息
    saveZszmPrintInfo(zslx, null, zsidList);
}

/**
 *
 * @param modelUrl 打印模板地址
 * @param gzlslid 工作流实例ID
 * @param zslx 证书类型
 * 打印流程的所有证书
 */
function allZsPrint(modelUrl, gzlslid, zslx,zsmodel) {
    var dataUrl = getIP() + "/realestate-register-ui/rest/v1.0/print/allZs/" + zslx + "?gzlslid=" + gzlslid +"&zsmodel="+zsmodel;
    print(modelUrl, dataUrl, false);
}

/**
 * （南通）打印流程的所有证书
 * @param modelUrl 打印模板地址
 * @param gzlslid 工作流实例ID
 * @param zslx 证书类型
 */
function ntAllZsPrint(modelUrl, gzlslid, zslx) {
    var dataUrl = getIP() + "/realestate-register-ui/rest/v1.0/print/nantong/allZs/" + zslx + "?gzlslid=" + gzlslid;
    print(modelUrl, dataUrl, false);

    // 保存打印数量信息
    saveZszmPrintInfo(zslx, gzlslid, null);
}

/**
 * 保存打印的证书、证明、证明单数量信息
 * @param zslx 证书类型
 * @param gzlslid 工作流实例ID
 * @param zsid 证书ID
 */
function saveZszmPrintInfo(zslx, gzlslid, zsid) {
    var zslxcode;
    if ("zs" == zslx) {
        zslxcode = 1;
    } else if ("zm" == zslx) {
        zslxcode = 2;
    } else if ("sczmd" == zslx) {
        zslxcode = 3;
    }

    $.ajax({
        url: "/realestate-register-ui/rest/v1.0/log/zszm/print",
        type: "POST",
        data: JSON.stringify({"zslx": zslxcode, "gzlslid": gzlslid, "zsid": zsid}),
        contentType: 'application/json',
        success: function (data) {
        }
    });
}

/**
 *验证证书项目的权属状态为1
 * @param zsidList 证书idList
 * @param gzlslid 工作流实例ID
 */
function verifyZsXmQszt(zsidList, gzlslid) {
    var result = true;
    var bdcZsQO = {};
    bdcZsQO["zsidList"] = zsidList;
    bdcZsQO["gzlslid"] = gzlslid;
    bdcZsQO["qsztList"] = [qsztTempory, qsztHistory];
    $.ajax({
        url: "/realestate-register-ui/rest/v1.0/zsxx/zsQszt",
        type: "POST",
        data: JSON.stringify(bdcZsQO),
        contentType: 'application/json',
        async: false,
        success: function (data) {
            result = data;
        },
        error: function () {
            warnMsg("验证证书权属状态异常！");
            result = false;
        }
    });
    return result;
}

/**
 * 生成证书以后，证书预览（带背景图的模式下）打印按钮的权限控制
 * @param readOnly
 * @param qszt
 * @param qzysxlh
 * @returns {boolean}
 */
function checkZsViewPrintState(readOnly, qszt, qzysxlh) {
    if ('true' == readOnly || true == readOnly) {
        warnMsg("只读状态下，不予打印！");
        return false;
    }
    if (qszt != qsztValid) {
        warnMsg("证书未登簿或已注销，不予打印！");
        return false;
    }

    if (null == qzysxlh || isNullOrEmpty(qzysxlh)) {
        warnMsg("印制号为空，不允许打印!");
        return false;
    }
    return true;
}

/**
 * 验证完税信息（合肥不动产登记平台所有涉税业务登簿前检测其税票真伪性。）
 * 只有产权，单个流程需要验证，由yzws做判断
 * @param processInsId
 * @param fybh 房源编号
 * @param response
 * @returns {*}
 */
function checkSfws(processInsId, response) {
    var deferred = $.Deferred();
    // 要求验证完税，并且要不过滤规则验证
    if (yzws && !glGzyz) {
        $.ajax({
            url: BASE_URL + '/bdcdy/sfws/swCxxx',
            type: "GET",
            data: {gzlslid: processInsId},
            dataType: "json",
            async: false,
            success: function (data) {
                if (data) {
                    if (data === '1' || data === 1) {
                        sfyws = true;
                    } else {
                        wsyzxx = "当前项目未完税，不予继续登簿";
                    }
                    deferred.resolve();
                } else {
                    // 未查询到一窗税务信息，则验证商品房税务状态
                    layer.prompt({
                        formType: 0,
                        value: ' ',// 这是一个空格字符串，请不要删除
                        title: '请输入房源编号',
                        area: ['500px', '350px'], //自定义文本域宽高
                        btn: ['继续登簿', '取消'],
                        btn2: function (index, layero) {
                            removeModel();
                            layer.close(index);
                        },
                        cancel: function (index, layero) {
                            removeModel();
                            layer.close(index);
                        }
                    }, function (value, index, elem) {
                        // 去除空格
                        fybh = value.trim();
                        if (isNullOrEmpty(fybh)) {
                            warnMsg("房源编号不能为空!");
                            return;
                        }
                        layer.close(index);
                        $.ajax({
                            url: BASE_URL + '/bdcdy/sfws/spfwszt',
                            type: "GET",
                            data: {gzlslid: processInsId, fybh: fybh},
                            dataType: "json",
                            async: false,
                            success: function (data) {
                                if (data) {
                                    if (data === '1' || data === 1) {
                                        sfyws = true;
                                    } else {
                                        wsyzxx = "当前项目未完税，不予继续登簿";
                                    }
                                } else {
                                    wsyzxx = "当前项目未完税，不予继续登簿";
                                }
                                deferred.resolve();
                            }, error: function () {
                                // warnMsg("验证完税接口异常！");
                                // removeModel();
                                sfyws = -1;
                                wsyzxx = "验证完税接口异常！请上传纸质完税凭证。点击确定则继续登簿操作，取消则终止登簿。";
                                deferred.resolve();
                            }
                        });
                    });
                }
            }, error: function () {
                // warnMsg("验证完税接口异常！");
                // removeModel();
                sfyws = -1;
                wsyzxx = "验证完税接口异常！请上传纸质完税凭证。点击确定则继续登簿操作，取消则终止登簿。";
                deferred.resolve();
            }
        });
    } else {
        sfyws = "不需验证完税信息";
        deferred.resolve();
    }
    return deferred;
}

/**
 * 验证是否领证人重复
 * @param processInsId
 * @param response
 * @returns {*}
 */
function checkSflzrcf(processInsId, response) {
    var deferred = $.Deferred();
    $.ajax({
        url: BASE_URL + '/bdcdy/sflzrcf',
        type: "GET",
        data: {gzlslid: processInsId},
        dataType: "json",
        async: false,
        success: function (data) {
            if (data != null) {
                if (data === 'true' || data) {

                } else {
                    lzryzxx = "领证人重复或者未生成证号，不予登簿!";
                }
            }
            deferred.resolve();
        }, error: function () {
            lzryzxx = "领证人重复或者未生成证号，不予登簿!";
            deferred.resolve();
        }
    });
    return deferred;
}
/**
 * 手动登簿
 * @param processInsId
 * @param response
 * @param dbBtn
 * @param fybh 房源编号
 * @param zsQzysxlh 印制号
 * @returns {boolean}
 */
function sddb(processInsId, response, dbBtn, resourceName) {
    gzlslid = processInsId;
    var deferred = $.Deferred();

    sddbClickNum = sddbClickNum + 1;
    console.log("登簿操作次数：" + sddbClickNum);
    if (1 != sddbClickNum) {
        warnMsg("已点击登簿，请勿重复操作！");
        return false;
    }
    if (dbBtn.hasClass("layui-btn-disabled")) {
        return false;
    }
    checkSflzrcf(processInsId,response).done(function (){
        // if (!isNullOrEmpty(lzryzxx)) {
        //     warnMsg(lzryzxx);
        //     return false;
        // }
        addModel();
        setTimeout(function () {
            // 验证完税信息（合肥不动产登记平台所有涉税业务登簿前检测其税票真伪性。）
            checkSfws(processInsId, response).done(
                function () {
                    // 日志记录
                    var param = {};
                    param.swfybh = fybh;
                    param.sfyws = sfyws;
                    param.gzlslid = processInsId;
                    param.dbzt = "请求登簿";
                    saveDbLog(param);

                    if (sfyws === false) {
                        warnMsg(wsyzxx);
                        removeModel();
                        return false;
                    } else if (sfyws === -1) {
                        layer.confirm(wsyzxx, {
                            title: "提示",
                            btn: ["确定", "取消"],
                            btn1: function (index) {
                                layer.close(index);

                                ajaxDb(processInsId, response).done(function () {
                                    if (dbSucceed && !isNullOrEmpty(resourceName) && resourceName == refreshName) {
                                        refreshTable();
                                    }
                                });

                                deferred.resolve();
                            },
                            btn2: function (index) {
                                layer.close(index);
                                removeModel();
                                return false;
                            }, cancel: function (index) {
                                layer.close(index);
                                removeModel();
                                return false;
                            }
                        });
                    } else {
                        ajaxDb(processInsId, response).done(function () {
                            if (dbSucceed && !isNullOrEmpty(resourceName) && resourceName == refreshName) {
                                refreshTable();
                            }
                        });
                        deferred.resolve();
                    }
                }
            );
        }, 50);

    });

    return deferred;
}

/**
 * 首次证明单批量导出
 */
function exportPdf(processInsId) {
    window.open("/realestate-register-ui/rest/v1.0/print/sczmd/pdf?gzlslid=" + processInsId);
}

function ajaxDb(processInsId, response) {
    var deferred = $.Deferred();
    $.ajax({
        url: "/realestate-register-ui/rest/v1.0/zs/sddb",
        type: "POST",
        data: {gzlslid: processInsId, glGzyz: glGzyz},
        async: false,
        success: function (data) {
            if (data && data.length > 0) {
                // 封装提示信息
                showXzxx(data, processInsId);
                removeModel();
            } else {
                // 证书列表页面未登簿设置权限
                invalidZsQszt = verifyZsXmQszt(null, processInsId);
                setZsListQsztBtnAtrr(false, invalidZsQszt);
                dbSucceed = true;
                // 提示登簿成功
                successMsg("登簿成功");
                removeModel();

                //合肥手动登薄后上报
                $.ajax({
                    url: "/realestate-register-ui/rest/v1.0/access",
                    type: "POST",
                    data: {gzlslid: processInsId},
                    success: function (data) {
                    }, error: function (e) {
                    }
                });


                // 登簿成功后需要调用注销dzzz接口
                // 转移、变更、更正、换证等 判断逻辑在后台
                zxDzzz(processInsId,"");

                // 最终登簿成功
                var param = {};
                param.sfyws = sfyws;
                param.yzxx = yzxx;
                param.glGzyz = glGzyz;
                param.access = true;
                param.gzlslid = processInsId;
                param.dbzt = "登簿完成";
                saveDbLog(param);
            }
            deferred.resolve();
        }, error: function (e) {
            response.fail(e);
            removeModel();
            deferred.reject();
        }, complete: function () {
            removeModel();
        }
    });
    return deferred;
}

function showXzxx(yzResult, processInsId) {
    if (yzResult.length > 0) {
        // 加载提示信息
        loadTsxx(yzResult, processInsId);
        layui.use(['jquery', 'layer', 'element', 'form', 'table', 'laytpl'], function () {
            var $ = layui.jquery,
                layer = layui.layer;
            warnLayer = layer.open({
                type: 1,
                title: '提示信息',
                skin: 'bdc-tips-right',
                shade: [0],
                area: ['600px'],
                offset: 'r', //右下角弹出
                time: 5000000, //2秒后自动关闭
                anim: 2,
                content: $('#tsxx').html(),
                success: function () {
                    $('.layui-layer-ico .layui-layer-close .layui-layer-close1').on('click', function () {
                        layer.close(warnLayer);
                        generate();
                    })
                },
                cancel: function () {
                    layer.close(warnLayer);
                    generate();
                }
            });
        });
    }
}

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @description 验证权属状态是否有临时状态
 */
function queryTemporyQszt(processInsId, response) {
    var result = false;
    var bdcZsQO = {};
    bdcZsQO["gzlslid"] = processInsId;
    $.ajax({
        url: "/realestate-register-ui/rest/v1.0/zs/yzlszt",
        type: "POST",
        data: JSON.stringify(bdcZsQO),
        contentType: 'application/json',
        async: false,
        success: function (data) {
            result = data;
        },
        error: function (e) {
            result = false;
            response.fail(e);
        }
    });
    return result;
}

/**
 * 单个证书页面设置按钮属性（依据qszt和印制号）
 * @param qszt
 * @param zsQzysxlh
 * 以下三个方法的执行顺序不可改变
 * 该方法被剥离到_nt.js和_hf.js里面去。
 * 暂时不用，_bak后缀暂时备份，后面可能会用到，先不删除
 */
function setZsBtnAttr_bak(qszt, zsQzysxlh) {
    // 设置和权属状态相关的按钮属性
    setQsztBtnAttr(qszt);
    // 设置和印制号相关的按钮的属性
    setYzhBtnAttr(zsQzysxlh);
    // 设置和权属状态，印制号都相关的属性
    setQsztAndYzhBtnAttr(qszt, zsQzysxlh);
}

/**
 * 单个证书设置和qszt，yzh均有关的按钮的属性
 * @param qszt
 * @param zsQzysxlh
 */
function setQsztAndYzhBtnAttr(qszt, zsQzysxlh) {
    var $zsPrint = $("#printBtn");
    // 印制号输入框
    var $qzysxlh = $("#qzysxlh");
    // 获取印制号的按钮
    var $getYzh = $("#getYzh");

    // 证书打印——有印制号，并且权属状态为现势时才可用，其他条件均不可用
    if (qszt == qsztValid && !isNullOrEmpty(zsQzysxlh) && !isNullOrEmpty(zsQzysxlh.trim())) {
        $zsPrint.removeAttr('disabled');
        $zsPrint.removeClass("layui-btn-disabled");
    } else {
        $zsPrint.attr('disabled', 'off');
        $zsPrint.addClass("layui-btn-disabled");
    }
    // 证书已登簿且为现势，并且输入框中没有值时，印制号编辑框才可用
    if (qszt == qsztValid && (isNullOrEmpty(zsQzysxlh) || (!isNullOrEmpty(zsQzysxlh) && isNullOrEmpty(zsQzysxlh.trim())))) {
        $getYzh.removeAttr('disabled');
        $getYzh.removeClass("layui-btn-disabled");

        $qzysxlh.removeAttr('disabled');
        $qzysxlh.removeClass("bdc-prohibit-text");
    } else {
        $getYzh.attr('disabled', 'off');
        $getYzh.addClass("layui-btn-disabled");

        $qzysxlh.attr('disabled', 'off');
        $qzysxlh.addClass("bdc-prohibit-text");
    }
}

/**
 * 单个证书，设置和印制号相关的按钮
 * @param zsQzysxlh
 */
function setYzhBtnAttr(zsQzysxlh) {
    var $zfYzh = $("#zfYzh");
    var $hyYzh = $("#hyYzh");
    var $getYzh = $("#getYzh");
    var $qzysxlh = $("#qzysxlh");
    var $zsPrint = $("#printBtn");

    // 页面根据当前证书有没有印制号控制印制号获取和作废按钮
    if (isNullOrEmpty(zsQzysxlh) || (!isNullOrEmpty(zsQzysxlh) && isNullOrEmpty(zsQzysxlh.trim()))) {
        $zfYzh.attr('disabled', 'off');
        $zfYzh.addClass("layui-btn-disabled");

        $hyYzh.attr('disabled', 'off');
        $hyYzh.addClass("layui-btn-disabled");

        $zsPrint.attr('disabled', 'off');
        $zsPrint.addClass("layui-btn-disabled");

        // 设置获取印制号按钮可用，输入框可编辑
        $getYzh.removeAttr('disabled');
        $getYzh.removeClass("layui-btn-disabled");

        $qzysxlh.removeAttr('disabled');
        $qzysxlh.removeClass("bdc-prohibit-text");
    } else {
        $getYzh.attr('disabled', 'off');
        $getYzh.addClass("layui-btn-disabled");

        $qzysxlh.attr('disabled', 'off');
        $qzysxlh.addClass("bdc-prohibit-text");

        // 设置作废印制号按钮可用
        $zfYzh.removeAttr('disabled');
        $zfYzh.removeClass("layui-btn-disabled");

        $hyYzh.removeAttr('disabled');
        $hyYzh.removeClass("layui-btn-disabled");

        $zsPrint.removeAttr('disabled');
        $zsPrint.removeClass("layui-btn-disabled");
    }
}

/**
 * 单个证书设置和qszt相关的按钮
 * @param qszt
 */
function setQsztBtnAttr(qszt) {
    // 显示注销标识
    if (qsztHistory == qszt) {
        $("#yzx").removeAttr("style");
    }

    if (qszt || qszt == qsztTempory) {
        var $db = $('#db');
        var $zsPrint = $("#printBtn");
        // 印制号大按钮（该按钮设置为不可用时，下拉按钮不会展示）
        var $yzh = $("#yzh");
        // 获取印制号
        var $getYzh = $("#getYzh");
        // 作废印制号
        // var $zfYzh = $("#zfYzh");
        var $qzysxlh = $("#qzysxlh");
        // 证号信息
        var bdcqzh = $("#bdcqzh").val();
        // 权属状态为临时并且证号已生成时登簿才可用
        if (qszt == qsztTempory && !isNullOrEmpty(bdcqzh)) {
            // 未登簿时，登簿按钮可用
            $db.removeAttr('disabled');
            $db.removeClass("layui-btn-disabled");
        } else {
            // 设置登簿按钮不可用
            $db.addClass("layui-btn-disabled");
            $db.attr("disabled", "disabled");
        }
        // 权属状态为为现势时，打印和获取印制号才可用
        if (qszt == qsztValid) {
            // 印制号可用
            $yzh.removeAttr('disabled');
            $yzh.removeClass("layui-btn-disabled");

            $getYzh.removeAttr('disabled');
            $getYzh.removeClass("layui-btn-disabled");

            // $zfYzh.removeAttr('disabled');
            // $zfYzh.removeClass("layui-btn-disabled");

            $qzysxlh.removeAttr('disabled');
            $qzysxlh.removeClass("bdc-prohibit-text");

            $zsPrint.removeAttr('disabled');
            $zsPrint.removeClass("layui-btn-disabled");
        } else {
            // 注销或者临时，印制号不可用
            $yzh.attr('disabled', 'disabled');
            $yzh.addClass("layui-btn-disabled");

            $getYzh.attr('disabled', 'disabled');
            $getYzh.addClass("layui-btn-disabled");

            // $zfYzh.attr('disabled', 'disabled');
            // $zfYzh.addClass("layui-btn-disabled");

            $qzysxlh.attr('disabled', 'off');
            $qzysxlh.addClass("bdc-prohibit-text");

            $zsPrint.attr('disabled', 'disabled');
            $zsPrint.addClass("layui-btn-disabled");
        }
    }
}

/**
 *  证书列表根据权属状态设置功能按钮
 * @param tempory 有未登簿的信息（true）
 * @param valid 现势的状态（非0）
 */
function setZsListQsztBtnAtrr(tempory, valid) {
    var $batchDb = $("#batchDb");
    var $getBatchYzh = $("#getBatchYzh");
    var $batchPrint = $("#batchPrint");
    var $exportPdf = $("#exportPdf");

    // qszt = true 表示存在临时状态的项目，并且证号都已经生成
    if (tempory && verifyBdcqzhNotNull) {
        // 登簿按钮可用
        $batchDb.removeAttr('disabled');
        $batchDb.removeClass("layui-btn-disabled");
        $batchDb.attr("lay-event", "db");
    } else {
        // 没有临时状态的项目，登簿按钮不可用
        $batchDb.addClass("layui-btn-disabled");
        $batchDb.attr("disabled", "disabled");
        $batchDb.removeAttr("lay-event");
    }
    // 有现势的证书信息
    if (valid && valid != -1) {
        // 获取印制号可用
        $getBatchYzh.removeAttr('disabled');
        $getBatchYzh.removeClass("layui-btn-disabled");
        $getBatchYzh.attr("lay-event", "getBatchYzh");

        // 批量打印可用
        $batchPrint.removeAttr('disabled');
        $batchPrint.removeClass("layui-btn-disabled");
        $batchPrint.attr("lay-event", "batchPrint");

        // 导出PDF按钮可用
        $exportPdf.removeAttr('disabled');
        $exportPdf.removeClass("layui-btn-disabled");
        $exportPdf.attr("lay-event", "exportPdf");
    } else {
        // 获取印制号不可用
        $getBatchYzh.addClass("layui-btn-disabled");
        $getBatchYzh.attr("disabled", "disabled");
        $getBatchYzh.removeAttr("lay-event");

        // 批量打印不可用
        $batchPrint.addClass("layui-btn-disabled");
        $batchPrint.attr("disabled", "disabled");
        $batchPrint.removeAttr("lay-event");

        // 导出PDF按钮不可用
        $exportPdf.addClass('layui-btn-disabled');
        $exportPdf.attr("disabled", "disabled");
        $exportPdf.removeAttr("lay-event");
    }
}

/**
 * 证书列表页面按钮权限控制
 * @param processInsId 工作流实例ID
 * @param lsxm 历史项目关系
 * @param response
 * @param tempory 有临时状态（true）
 * @param valid 现势状态（非0）
 *
 */
function setZsListBtnAttr(processInsId, lsxm, response) {
    var $batchDb = $("#batchDb");
    var $getBatchYzh = $("#getBatchYzh");
    if (!isNullOrEmpty(processInsId)) {
        var tempory = queryTemporyQszt(processInsId, response);
        var valid = verifyZsXmQszt(null, processInsId);
        setZsListQsztBtnAtrr(tempory, valid);
    } else if (!isNullOrEmpty(lsxm) && "true" == lsxm) {
        // 项目历史关系查看
        $batchDb.hide();
        $getBatchYzh.hide();
    }
}


/**
 *  点击表格中的更多
 */

// 印制号按钮
$('.title-btn-area').on('mouseenter', '.bdc-yzh-btn', function (event) {
    if ($(".bdc-yzh-btn").hasClass("layui-btn-disabled")) {
        return false;
    }
    if($("#zz").is(":visible")){
        $('.bdc-yzh-more').css({"right":"336px"});
    }
    $('.bdc-yzh-more').show();
    $('.bdc-yl-more').hide();

});
// 预览按钮
$('.title-btn-area').on('mouseenter', '.bdc-yl-btn', function (event) {
    if ($(".bdc-yl-btn").hasClass("layui-btn-disabled")) {
        return false;
    }
    if($("#zz").is(":visible")){
        $('.bdc-yl-more').css({"right":"245px"});
    }
    $('.bdc-yzh-more').hide();
    $('.bdc-yl-more').show();
});
// 印制号按钮
$('.title-btn-area').on('click', '.bdc-yzh-btn', function (event) {
    return false;
});
// 预览按钮
$('.title-btn-area').on('click', '.bdc-yl-btn', function (event) {
    return false;
});

$('.title-btn-area').on('mouseleave', function () {
    $('.bdc-table-btn-more').hide();
});
//点击更多内的任一内容，隐藏
$('.bdc-table-btn-more a').on('click', function () {
    $(this).parent().hide();
});
//点击页面任一空白位置，消失
$('body').on('click', function () {
    $('.bdc-table-btn-more').hide();
});

/**
 * fr3打印宗地图
 * @param zsid
 */
function printZdt(zsid) {
    var dataUrl = getIP() + "/realestate-register-ui/rest/v1.0/print/zs/image/zdt/" + zsid + "/";
    print(zdtModelUrl, dataUrl, hideModel);
}

/**
 * fr3打印户室图
 * @param zsid
 */
function printHst(zsid) {
    var dataUrl = getIP() + "/realestate-register-ui/rest/v1.0/print/zs/image/hst/" + zsid + "/";
    print(hstModelUrl, dataUrl, hideModel);
}

/**
 * fr3打印宗地图，户室图 针对预览，此时没有生成zsid，利用bdcdyh获取宗地图，户室图信息
 * @param bdcdyh
 * @param zsid
 * @param type
 * @param sessionKey
 */
function printZdtHst(bdcdyh,type, sessionKey) {
    var dataUrl = getIP() + "/realestate-register-ui/rest/v1.0/print/zs/image/"+ type+"/''/" + bdcdyh;
    var appName = "realestate-register-ui";
    if(type ==="zdt"){
        printChoice("zdt", appName, dataUrl, zdtModelUrl, false, sessionKey);
    }else{
        printChoice("hst", appName, dataUrl, hstModelUrl, false, sessionKey);
    }
}
function saveDbLog(param) {
    $.ajax({
        url: '/realestate-register-ui/rest/v1.0/log/dbxx',
        dataType: "json",
        type: "POST",
        data: {'privateAttrMap': param},
        success: function (data) {
        }
    });
}

/**
 * 根据zsid查询证书的打印状态，并更新页面的展示信息
 * @param zsid
 */
function setZsDyzt(zsid) {
    //获取数据
    $.ajax({
        url: "/realestate-register-ui/rest/v1.0/zsxx/" + zsid,
        type: "GET",
        success: function (data) {
            var zsxx = data;
            if (zsxx && zsxx.dyzt) {
                setDyzt(zsxx.dyzt);
            }
        }
    });
}

/**
 * 设置打印状态
 * @param dyzt
 */
function setDyzt(dyzt) {
    var $dyzt = $('#dyzt')[0];
    if (1 === dyzt) {
        $dyzt.innerText = "已打证";
        $dyzt.className = 'dyzt-ydz';
    } else {
        $dyzt.innerText = "未打证";
        $dyzt.className = 'dyzt-wdz';
    }
}

/**
 *
 * @param url 证书列表，查看单个证书的详细页面url
 * @param processInsId 工作流实例ID
 * @param data 选中的行参数
 */
function appendZsXmids(url, processInsId, data) {
    var qllxArr = [];
    qllxArr.push(data.qllx);

    var param = {};
    param.bdcdyh = data.bdcdyh;
    param.qllxs = qllxArr;
    param.qlr = data.qlr;
    param.ywr = data.ywr;
    param.gzlslid = processInsId;

    var bdcdyh = data.bdcdyh;

    // 批量生成一本证时
    if (bdcdyh && bdcdyh.substring(bdcdyh.length - 1, bdcdyh.length) == "等") {
        var bdcdyhTemp = bdcdyh.substring(0, bdcdyh.length - 1);
        param["bdcdyh"] = bdcdyhTemp;
    }

    var xmids = getZsXmidsByBdcdyhAndGzlslid(param, false);
    url = url + "&xmid=" + xmids + "&parentResourceName=bdcZsList";
    return url;
}

/**
 * 根据相关参数获取当前单个证书的所有xmid信息
 * @param xmids 当前证书相关的所有的xmid信息
 * @param xmid 页面获取到的xmid参数
 * @param processInsId 工作流实例ID
 * @param parentResourceName 父页面名称
 * @returns {*}
 */
function getSingleZsXmids(xmid, processInsId, parentResourceName) {
    var xmids = "";
    if (!isNullOrEmpty(xmid)) {
        if (!isNullOrEmpty(parentResourceName) && 'bdcZsList' == parentResourceName || xmid.indexOf(zf_ywdh) > -1) {
            // 父页面是证书台账时，xmid已做过了查询处理
            xmids = xmid;
        } else {
            xmids = getZsXmidsByOneXmid(xmid);
        }
    } else {
        //xmid为空只有一种情况，就是受理审核页面一本证或者一证多房的情况，点击上方的证书预览按钮
        //此时需要通过glzslid去查出所有的xmid
        var param = {};
        param.gzlslid = processInsId;
        xmids = getZsXmidsByBdcdyhAndGzlslid(param, true);
    }
    return xmids;
}

/**
 * 根据一个xmid获取证书相关的所有xmid的拼接字符串
 * @param xmid
 * @returns {*}
 */
function getZsXmidsByOneXmid(xmid) {
    var xmids = "";
    //获取数据
    $.ajax({
        url: "/realestate-register-ui/rest/v1.0/xmZsyl/getAllXmidByOneXmid/" + xmid,
        type: "GET",
        async: false,
        success: function (data) {
            var xmidlist = data;
            var xmidStr = "";
            for (var i = 0; i < xmidlist.length; i++) {
                xmidStr += xmidlist[i] + ","
            }
            xmids = xmidStr.substring(0, xmidStr.length - 1);
        }
    });
    return xmids;
}

/**
 * 根据某本证书的bdcdyh和glzslid获取当前证书相关的所有的xmid拼接的xmid字符串
 * @param bdcdyh
 * @param gzlslid
 */
function getZsXmidsByBdcdyhAndGzlslid(param, isAllLc) {
    var xmidTemp = "";
    $.ajax({
        url: "/realestate-register-ui/rest/v1.0/zsxx/zsXmidStr?isAllLc=" + isAllLc,
        async: false,
        type: "POST",
        data: JSON.stringify(param),
        contentType: 'application/json',
        success: function (data) {
            xmidTemp = data;
        }
    });
    return xmidTemp;
}

/**
 * 将xmids临时存到redis，并返回redis的key值
 * @param xmids
 */
function sendXmidsToRedis(xmids) {
    var keyParam = "";
    // 当参数过长是，fr3无法打印，先ajax post把参数保存的redis里面，返回key
    // 再根据key取出参数，在后台查询数据
    $.ajax({
        url: "/realestate-register-ui/rest/v1.0/xmZsyl/fbyl",
        type: "POST",
        contentType: 'application/json',
        dataType: "text",
        data: JSON.stringify(xmids),
        async: false,
        success: function (key) {
            if (key) {
                keyParam = key;
            } else {
                layer.msg('<img src="../../static/lib/bdcui/images/error-small.png" alt="">系统异常，请重试！');
            }
        }
    });
    return keyParam;
}

/**
 * 获取宗地图
 * @param zsid
 */
function getZdtBase64(zsid,qjgldm) {
    $.ajax({
        url: "/realestate-register-ui/rest/v1.0/zdxx/zdt",
        contentType: "application/json;charset=utf-8",
        data: {zsid: zsid,qjgldm:qjgldm},
        type: "GET",
        success: function (data) {
            $('#zdt').attr('src', 'data:image/png;base64,' + data);
        }, error: function (e) {
        }
    });
}

/**
 * 获取户室图
 * @param zsid
 */
function getHstBase64(zsid,qjgldm) {
    $.ajax({
        url: "/realestate-register-ui/rest/v1.0/fwxx/hst",
        contentType: "application/json;charset=utf-8",
        data: {zsid: zsid,qjgldm:qjgldm},
        type: "GET",
        success: function (data) {
            $('#hst').attr('src', 'data:image/png;base64,' + data);
        }, error: function (e) {
        }
    });
}

/**
 * 换证等流程 登簿需要注销上一手dzzz
 * @param processInsId
 */
function zxDzzz(processInsId,zsid){
    $.ajax({
        url: "/realestate-register-ui/rest/v1.0/zs/zxDzzz",
        type: "POST",
        data: {processInsId: processInsId,zsid: zsid},
        success: function () {
            console.log("注销上一手电子证照成功");
        },
        error: function (xhr, status, error) {
            console.log("注销上一手电子证照失败");
            delAjaxErrorMsg(xhr)
        }
    })
}


/**
 * 制作电子证照
 * @param zsid
 */
function zzDzzz(zsid){
    var sfYlDzzz = getSfYlDzzz();
    addModel();
    $.ajax({
        url: "/realestate-register-ui/rest/v1.0/zs/createDzzz?zsid="+zsid,
        type: "POST",
        contentType: 'application/json',
        dataType: "json",
        success: function (data) {
            removeModel();
            // 多个证书证明电子证照，默认展示第一个
            if(data && data.length > 0){
                data = data[0]
                if(data && data.flag == "200"){
                    if(sfYlDzzz == "false"){
                        successMsg("制证成功");
                        return;
                    }
                    successMsg("制证成功，即将打开电子证照");
                    dzzzUrl = data.data.url;
                    setTimeout(function () {
                        layer.open({
                            title: ['电子证照预览', 'font-size:16px;font-weight: 700;'],
                            type: 2,
                            area: ['100%', '100%'],
                            resize: true,
                            closeBtn: 1,
                            content: '/realestate-register-ui/view/zsxx/dzzzView.html?url=' + dzzzUrl,
                            scrollbar: false,
                            btnAlign: 'c'
                        });
                    },1000)
                }else{
                    warnMsg("制证失败，请重试！");
                }
            }else{
                warnMsg("制证失败，请重试！");
            }

        }, error: function () {
            warnMsg("制证异常，请检查日志！");
            removeModel();
        }
    });
}

/**
 * 每次打印证书 需要同步电子证照
 * 有zzbs先注销再制证，没有的话就不做任何处理
 * @param processInsId
 */
function syncDzzz(zsid){
    $.ajax({
        url: "/realestate-register-ui/rest/v1.0/zs/syncDzzz",
        type: "POST",
        data: {zsid: zsid},
        success: function () {
            console.log("同步电子证照成功");
        },
        error: function (xhr, status, error) {
            console.log("同步电子证照失败");
            delAjaxErrorMsg(xhr)
        }
    })
}

/**
 * 是否预览电子证照
 */
function getSfYlDzzz(){
    var flag = "false";
    $.ajax({
        url: "/realestate-register-ui/rest/v1.0/zs/sfYlDzzz",
        type: "GET",
        async: false,
        success: function (data) {
            flag = data;
        }
    });
    return flag;
}



