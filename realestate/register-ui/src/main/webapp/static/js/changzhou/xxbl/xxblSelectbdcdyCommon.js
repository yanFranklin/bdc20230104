/**
 * 选择的登记流程对象
 */
var processs;
/**
 * 错误信息提示框
 */
var warnLayer;
/**
 * 需要被初始化的信息
 */
var checkData = {};
// 下拉框选择的工作流定义 id
var gzldyid;
// 选择台账类型
var xztzlx;
var scxnbdcdyh = false;
// 显示生成虚拟不动产单元号的特殊流程
var xnbdcdyhlckeys = [];
layui.use(['jquery', 'selectN', 'form', 'layer', 'laytpl'], function () {
    var $ = layui.jquery,
        layer = layui.layer,
        laytpl = layui.laytpl,
        selectN = layui.selectN,
        form = layui.form;
    $(function () {
        $('.bdc-content').css('min-height', $('body').height() - 56);
        // 渲染登记流程的联动下拉框
        listCategoryProcess();
        // 是否显示虚拟不动产单元号
        queryScXnbdcdyh();

        // 渲染提示信息
        generate();

        // 生成虚拟不动产单元号
        form.on("submit(xnbdcdyh)", function () {
            layer.open({
                title: '虚拟不动产单元号',
                type: 1,
                area: ['960px', '500px'],
                btn: ['添加', '取消'],
                content: $('#bdc-popup-large'),
                yes: function (index, layero) {
                    //  单位代码（6位）+ 000000 （地籍区） + 土地所有权类型 + 宗地特征码 + 00000 （宗地宗海顺序号） + F/W + 0000000X（X 表示顺序号）
                    var dwdm = $("#dwdm").val();
                    var zdtzm = $("#zdtzm").val();
                    var dzwtzm = $("#dzwtzm").val();
                    var bdcdyhPrefix = dwdm + "000000" + zdtzm + "00000" + dzwtzm;
                    document.getElementsByName("bdcdyh")[0].value = bdcdyhPrefix;
                    if(document.getElementsByName("qjgldm").length >0) {
                        document.getElementsByName("qjgldm")[0].value = dwdm;
                    }
                    if(dzwtzm != 'W'){
                        document.getElementById("fwlxselect").classList.remove("bdc-hide");
                    }else{
                        document.getElementById("fwlxselect").classList.add("bdc-hide");
                    }
                    layer.closeAll();
                }
            });
        });

        // 选择不动产单元号
        form.on("submit(bdcdyh)", function () {
            if (!isNullOrEmpty(processs.lastValue) && !isNullOrEmpty(processs.lastName)) {
                // 未重新选择流程
                if (gzldyid == processs.lastValue) {
                    checkIsOpen("1", getContextPath()+'/view/xxbl/xxbl_selectbdcdyh.html?gzldyid=' + gzldyid)
                } else {
                    // 重新选择了流程
                    gzldyid = processs.lastValue;
                    // 判断该流程是否可以选择不动产单元号
                    $.ajax({
                        url: getContextPath() + "/rest/v1.0/blxx/blxz/xztzpz",
                        type: 'GET',
                        async: false,
                        data: {gzldyid: gzldyid},
                        success: function (data) {
                            if (!isNullOrEmpty(data)) {
                                xztzlx = data.xztzlx;
                                checkIsOpen("1", getContextPath()+'/view/xxbl/xxbl_selectbdcdyh.html?gzldyid=' + gzldyid)
                            } else {
                                warnMsg("选择台账未配置，请检查！")
                            }
                        }, error: function (xhr, status, error) {
                            delAjaxErrorMsg(xhr)
                        }
                    });
                }
            } else {
                warnMsg("请选择需要办理的流程！");
            }
        });

        // 选择不动产权
        form.on("submit(bdcq)", function () {
            if (!isNullOrEmpty(processs.lastValue) && !isNullOrEmpty(processs.lastName)) {
                // 未重新选择流程
                if (gzldyid == processs.lastValue) {
                    checkIsOpen("2", getContextPath()+'/view/xxbl/xxbl_selectbdcq.html?gzldyid=' + gzldyid)
                } else {
                    // 重新选择了流程
                    gzldyid = processs.lastValue;
                    $.ajax({
                        url: getContextPath() + "/rest/v1.0/blxx/blxz/xztzpz",
                        type: 'GET',
                        async: false,
                        data: {gzldyid: gzldyid},
                        success: function (data) {
                            if (!isNullOrEmpty(data)) {
                                xztzlx = data.xztzlx;
                                checkIsOpen("2", getContextPath()+'/view/xxbl/xxbl_selectbdcq.html?gzldyid=' + gzldyid)
                            } else {
                                warnMsg("选择台账未配置，请检查！")
                            }
                        }, error: function (xhr, status, error) {
                            delAjaxErrorMsg(xhr)
                        }
                    });
                }

            } else {
                warnMsg("请选择需要办理的流程！");
            }
        });

        // 选择查封
        form.on("submit(cfwh)", function () {
            if (!isNullOrEmpty(processs.lastValue) && !isNullOrEmpty(processs.lastName)) {
                // 未重新选择流程
                if (gzldyid == processs.lastValue) {
                    checkIsOpen("3", getContextPath()+'/view/xxbl/xxbl_selectcf.html?gzldyid=' + gzldyid)
                } else {
                    // 重新选择了流程
                    gzldyid = processs.lastValue;
                    $.ajax({
                        url: getContextPath() + "/rest/v1.0/blxx/blxz/xztzpz",
                        type: 'GET',
                        async: false,
                        data: {gzldyid: gzldyid},
                        success: function (data) {
                            if (!isNullOrEmpty(data)) {
                                xztzlx = data.xztzlx;
                                checkIsOpen("3", getContextPath()+'/view/xxbl/xxbl_selectcf.html?gzldyid=' + gzldyid)
                            } else {
                                warnMsg("选择台账未配置，请检查！")
                            }
                        }, error: function (xhr, status, error) {
                            delAjaxErrorMsg(xhr)
                        }
                    });
                }

            } else {
                warnMsg("请选择需要办理的流程！");
            }
        });

        // 确认初始化数据
        form.on("submit(csh)", function () {
            checkData.bdcdyh = $("#bdcdyh").val();
            checkData.bdcqzh = $("#bdcqzh").val();
            checkData.ycqzh = $("#ycqzh").val();
            checkData.cfwh = $("#cfwh").val();
            checkData.yxmid = $("#yxmid").val();
            var qjgldm = $("#qjgldm").val();
            var bdcdyfwlx = $("#bdcdyfwlx").val();
            if (!isNullOrEmpty(qjgldm)) {
                checkData.qjgldm = qjgldm;
            }
            if (!isNullOrEmpty(bdcdyfwlx)) {
                checkData.bdcdyfwlx = bdcdyfwlx;
            }
            var yqllx =$("#yqllx").val();
            if(!isNullOrEmpty(yqllx)) {
                checkData.yqllx = yqllx;
            }
            if (!isNullOrEmpty(processs.lastValue) && !isNullOrEmpty(processs.lastName)) {
                // 需要生成的虚拟不动产单元号跳过验证
                var flag = isXn(checkData.bdcdyh);
                if (flag === true) {
                    //虚拟单元号判断是否选择了房屋类型
                    if (isNullOrEmpty(bdcdyfwlx) && document.getElementById("fwlxselect").length > 0) {
                        layer.msg("虚拟单元号必须选择不动产单元房屋类型");
                    } else {
                        confirmCsh(checkData);
                    }
                } else if (!isNullOrEmpty(checkData.ycqzh) || !isNullOrEmpty(checkData.bdcdyh)) {
                    addModel();
                    // 如果有选择原产权证号说明存在现势产权，而不是选择的不动产单元号
                    if (choosebdcdyh.indexOf(processs.firstName) != -1 && isNullOrEmpty(checkData.ycqzh)) {
                        cqXzBdcdyhYz(checkData.bdcdyh, function (data) {
                            if (data.length > 0) {
                                removeModel();
                                warnMsg(data[0].msg);

                            } else {
                                checkBdcdyh();
                            }
                        }, function (err) {

                        });
                    } else {
                        checkBdcdyh();
                    }
                } else {
                    layer.msg("原产权证号或单元号至少输入一个条件！");
                }
            } else {
                layer.msg("请选择需要办理的流程！");
            }
        });

        /**
         * 判断是否是需要生成的虚拟不动产单元号
         * @param bdcdyh 不动产单元号
         * @return {boolean} true or false
         */
        function isXn(bdcdyh) {
            var zdzhsxh = bdcdyh.substring(14, 19);
            var dzwtzm = bdcdyh.substring(19, 20);
            return zdzhsxh === "00000" && dzwtzm !== "H" && bdcdyh.length === 20;
        }
    });

    /**
     * 渲染 单位代码 和 宗地特征码
     */
    function rendSelect() {
        zdList = getZdList();

        // 单位代码
        $("#dwdm").empty();
        // 渲染下拉框
        $('#dwdm').append(new Option("请输入", ""));
        $.each(zdList.qx, function (index, item) {
            $('#dwdm').append(new Option(item.MC, item.DM));// 下拉菜单里添加元素
        });

        // 宗地特征码
        $("#zdtzm").empty();
        // 渲染下拉框
        $('#zdtzm').append(new Option("请输入", ""));
        $.each(zdList.zdzhtzm, function (index, item) {
            $('#zdtzm').append(new Option(item.DM, item.DM));// 下拉菜单里添加元素
        });
        form.render("select");
    }


    /**
     * 0. 是否生成虚拟不动产单元号
     */
    function queryScXnbdcdyh() {
        $.ajax({
            type: "GET",
            url: getContextPath() + "/rest/v1.0/blxx/blxz/xnbdcdyh",
            async: false,
            success: function (data) {
                if (data.scxnbdcdyh === true) {
                    scxnbdcdyh = true;
                    $('#xnbdcdyh').removeClass('bdc-hide');
                    // 渲染虚拟不动产单元号的部分下拉框
                    rendSelect();
                } else {
                    xnbdcdyhlckeys = data.tslckeys;
                }
            },
            error: function (xhr, status, error) {
                delAjaxErrorMsg(xhr)
            }
        });
    }

    /**
     * 1. 调用规则验证不动产单元号
     */
    function checkBdcdyh() {
        // 去除 页面 中的空格
        checkData.bdcdyh = deleteWhitespace(checkData.bdcdyh);
        checkData.bdcqzh = deleteWhitespace(checkData.bdcqzh);
        checkData.ycqzh = deleteWhitespace(checkData.ycqzh);
        checkData.cfwh = deleteWhitespace(checkData.cfwh);
        checkData.yxmid = deleteWhitespace(checkData.yxmid);
        // 组织验证数据
        var selectDataList = [];
        var bdcGzYzsjDTO = {};
        bdcGzYzsjDTO.bdcdyh = checkData.bdcdyh;
        bdcGzYzsjDTO.cfwh =checkData.cfwh;
        bdcGzYzsjDTO.yxmid =checkData.yxmid;
        bdcGzYzsjDTO.qjgldm =checkData.qjgldm;
        selectDataList.push(bdcGzYzsjDTO);

        var bdcGzYzQO = {};
        bdcGzYzQO.zhbs = processs.lastValue + "_XZBDCDY";
        bdcGzYzQO.paramList = selectDataList;
        $.ajax({
            url: getContextPath() + '/rest/v1.0/blxx/bdcGzyz',
            type: 'POST',
            dataType: 'json',
            contentType: "application/json;charset=UTF-8",
            data: JSON.stringify(bdcGzYzQO),
            success: function (data) {
                if (data.length > 0) {
                    removeModel();
                    loadTsxx(data);
                    gzyztsxx();
                } else {
                    removeModel();
                    confirmCsh(checkData);
                }
            }, error: function (xhr, status, error) {
                removeModel();
                delAjaxErrorMsg(xhr);
            }
        });
    }

});

/**
 * 2. 确认是否初始化
 * @param postData 页面填写的通过验证的数据
 */
function confirmCsh(postData) {
    var index = layer.confirm("是否确认新增数据？", {
        title: "提示",
        btn: ["确认", "取消"],
        btn1: function () {
            //  点击确认后关闭弹窗，增加模板
            addModel();
            layer.close(index);
            csh(postData);
        },
        btn2: function () {
            layer.close(index);
        }
    });
}

function isNullObj(data) {
    if (JSON.stringify(data) === '{}') {
        return true;
    }
    return false;
}

/**
 * 3. 初始化填写的信息
 * @param postData 页面填写的通过验证的数据
 */
function csh(postData) {
    if (isNullObj(postData) && xmids != null) {
        // 通过验证，组织数据注销权利
        var postObj = {};
        postObj.xmidList = xmids;
        postObj.zxyy = $('#zxyy').val();
        $.ajax({
            type: 'POST',
            url: "/realestate-register-ui/rest/v1.0/blxx/zxql",
            contentType: "application/json;charset=utf-8",
            data: JSON.stringify(postObj),
            dataType: "text",
            success: function (data) {
                removeModel();
                if (data === "history") {
                    warnMsg("当前项目已经被注销，不可以重复注销！");
                } else {
                    successMsg("注销成功");
                }
            },
            error: function (xhr, status, error) {
                delAjaxErrorMsg(xhr)
            }
        });
    } else {
        var bdcBlxxDTO = {};
        bdcBlxxDTO["ycqzh"] = postData.ycqzh;
        bdcBlxxDTO["bdcdyh"] = postData.bdcdyh;
        bdcBlxxDTO["cfwh"] = postData.cfwh;
        bdcBlxxDTO["yxmid"] = postData.yxmid;
        bdcBlxxDTO["gzldyid"] = processs.lastValue;
        bdcBlxxDTO["lcmc"] = processs.lastName;
        bdcBlxxDTO["qjgldm"] = postData.qjgldm;
        bdcBlxxDTO["bdcdyfwlx"] = postData.bdcdyfwlx;
        bdcBlxxDTO["yqllx"] = postData.yqllx;
        if (choosebdcdyh.indexOf(processs.firstName) != -1) {
            bdcBlxxDTO["qlsjly"] = "1";
        }
        if (edition == 'nt') {
            bdcBlxxDTO["blshlx"] = -1;
        } else {
            var blshlx = $('input[name="blshlx"]:checked').val();
            bdcBlxxDTO["blshlx"] = blshlx;
        }
        $.ajax({
            type: "POST",
            url: getContextPath() + "/rest/v1.0/blxx/csh",
            contentType: "application/json;charset=utf-8",
            data: JSON.stringify(bdcBlxxDTO),
            success: function (data) {
                if (isNullOrEmpty(data)) {
                    removeModel();
                    warnMsg("创建数据失败！");
                } else {
                    searchProjectInfo(data[0], postData.bdcqzh);
                }
            },
            error: function (xhr, status, error) {
                removeModel();
                delAjaxErrorMsg(xhr);
            }
        });
    }
}

/**
 * 根据项目id查询相关信息,用于信息补录-新增日志记录
 */
function searchProjectInfo(param,bdcqzh) {
    const url = getContextPath() + "/rest/v1.0/blxx/searchInfoById?xmid=" + param.xmid;
    $.ajax({
        type: "GET",
        url: url,
        contentType: "application/json;charset=utf-8",
        dataType:"json",
        success: function (data) {
            let content = {
                bdcxmxx: data.bdcxmxx,
                bdcxmlsgxlist: data.bdcxmlsgxlist,
                bdcqlrlist: data.bdcqlrlist
            };
            if (typeof Object.assign != 'function') {
                Object.assign = function (target) {
                    'use strict';
                    if (target == null) {
                        throw new TypeError('Cannot convert undefined or null to object');
                    }

                    target = Object(target);
                    for (var index = 1; index < arguments.length; index++) {
                        var source = arguments[index];
                        if (source != null) {
                            for (var key in source) {
                                if (Object.prototype.hasOwnProperty.call(source, key)) {
                                    target[key] = source[key];
                                }
                            }
                        }
                    }
                    return target;
                };
            }

            let result = Object.assign(content,param);
            console.log(result);
            saveDetailLog("XXBL_XZ", "信息补录-新增", result);
            // 将 bdcqzh 保存在 session 中
            sessionStorage.setItem("xxbl" + param.gzlslid,bdcqzh);
            window.location.href = getContextPath() + "/view/xxbl/xxblUpdateinfo.html?processInsId=" + param.gzlslid + "&type=new";
        },
        error: function (xhr, status, error) {
            removeModel();
            delAjaxErrorMsg(xhr);
        }
    });
}

//关闭 panel (新增申请人弹窗关闭方法)
function cancelEdit() {
    layui.use(['layer'], function () {
        var layer = layui.layer;
        layer.closeAll();
    });
}

/**
 * 产权选择不动产单元号验证
 * @param bdcdyh
 */
function cqXzBdcdyhYz(bdcdyh, _fn, _errorFn) {
    addModel();
    var selectDataList = [];
    var bdcGzYzsjDTO = {};
    bdcGzYzsjDTO.bdcdyh = bdcdyh;
    selectDataList.push(bdcGzYzsjDTO);

    var bdcGzYzQO = {};
    bdcGzYzQO.zhbs = "blcqxzbdcdyh";
    bdcGzYzQO.paramList = selectDataList;
    $.ajax({
        url: getContextPath() + '/rest/v1.0/blxx/bdcGzyz',
        type: 'POST',
        async: false,
        dataType: 'json',
        contentType: "application/json;charset=UTF-8",
        data: JSON.stringify(bdcGzYzQO),
        success: function (data) {
            removeModel();
            _fn.call(this, data, data);
        }, error: function (xhr, status, error) {
            removeModel();
            delAjaxErrorMsg(xhr);
            _errorFn.call(this);
        }
    });
}

/**
 * bdcdyh 不动产单元号
 * 验证是否为虚拟单元号(定着物特征码前5位为00000，且非海域)
 */
function checkXndyh(bdcdyh) {
    if (isNotBlank(bdcdyh) && bdcdyh.length === 28) {
        var zdzhsxh = bdcdyh.substr(6, 6);
        var qslxdm = bdcdyh.substr(13, 1);
        //海域特征码
        var hyArr = ["G", "H"];
        if (zdzhsxh === "000000" && hyArr.indexOf(qslxdm) < 0) {
            return true;
        } else {
            return false;
        }
    } else {
        // showAlertDialog("不动产单元号格式错误");
        return false;
    }


}