/**
 * @author "<a href="mailto:lixin1@gtmap.cn>lixin</a>"
 * @version 1.0, 2019/6/19
 * @description 信息补录修改项目基本信息 JS
 */
layui.config({
    base: '../../static/lib/form-select/' //此处路径请自行处理, 可以使用绝对路径
}).extend({
    formSelects: 'formSelects-v4'
});
var zdList;
var xmid;
var processInsId;
// 编辑类型
var type;
// 是否生成证书
var sfsczs;
var djyyList = {};
var moduleCode = 'xxblUpdateinfo';
layui.use(['jquery', 'form', 'laytpl', 'laydate', 'layer', 'formSelects'], function () {
    var $ = layui.jquery,
        laytpl = layui.laytpl,
        layer = layui.layer,
        laydate = layui.laydate,
        formSelects = layui.formSelects,
        form = layui.form;
    $(function () {
        // 获取参数
        processInsId = $.getUrlParam('processInsId');
        xmid = $.getUrlParam('xmid');
        // 获取操作类型（check、update 和 new）
        type = $.getUrlParam('type');
        // 获取必要参数
        $.ajax({
            url: "/realestate-register-ui/rest/v1.0/blxx/blxz/sfscql",
            type: 'GET',
            data: {
                gzlslid: processInsId,
                xmid:xmid
            },
            dataType: 'json',
            async: false,
            success: function (data) {
                xmid = data.xmid;
                processInsId = data.gzlslid;
                /**
                 * 设置权限控制样式
                 */
                if (!isNullOrEmpty(type) && type === "check") {
                    $('#addQlr').addClass('bdc-hide');
                    $('#saveData').addClass('bdc-hide');
                }

                $('.bdc-content').css('min-height', $('body').height() - 56);

                zdList = getZdList();

                /**
                 * 加载页面信息
                 */
                if (!isNullOrEmpty(processInsId)) {
                    loadXmxx(processInsId);
                }
            }
        });

        /**
         * 监听下一页按钮
         */
        form.on("submit(next)", function (data) {
            var bdcxm = data.field;
            var qllx = parseInt(bdcxm.qllx);
            var bdcdyfwlx = parseInt(bdcxm.bdcdyfwlx);
            window.location.href = getContextPath() + "/view/xxbl/xxblUpdateQlxx.html?qllx=" + qllx + "&bdcdyfwlx=" + bdcdyfwlx + "&xmid=" + xmid + "&processInsId=" + processInsId + "&type=" + type + "&sfsczs=" + sfsczs;
        });

        /**
         * 监听保存数据按钮
         */
        form.on("submit(saveData)", function (data) {
            addModel();
            try {
                $.when(saveQlr(), saveXmxx(data), updateRyzd(data)).done(function () {
                    removeModel();
                    successMsg("保存成功");
                });
            } catch (e) {
                removeModel();
                ERROR_CONFIRM("保存失败", e.message);

            }
        });

        /**
         * 监听添加权利人按钮
         */
        form.on("submit(addQlr)", function (data) {
            addQlr(data.field.qllx, data.field.xmid);
        });

        /**
         * 加载项目信息（发起请求）
         * @param processInsId
         */
        function loadXmxx(processInsId) {
            addModel();
            $.ajax({
                url: "/realestate-register-ui/rest/v1.0/blxx/xmxx",
                type: 'GET',
                data: {
                    processInstanceId: processInsId,
                    xmid: xmid
                },
                dataType: 'json',
                success: function (data) {
                    bdcXm = data;
                    xmid = data.xmid;
                    //面积单位为空时默认为平方米
                    if (data.mjdw === null || data.mjdw === '') {
                        data.mjdw = '1'
                    }
                    if (!isNullOrEmpty(data.djxl)) {
                        getDjyyList(data.djxl);
                    }
                    generateXmxx(data);
                    loadQlr();
                    // 判断是否生成证书
                    checkSfsczs(data.djxl);
                    removeModel();
                }
            });
        }

        /**
         * 根据 djxl 获取登记原因
         * @param djxl 登记小类
         */
        function getDjyyList(djxl) {
            $.ajax({
                url: "/realestate-register-ui/rest/v1.0/blxx/djyy",
                type: 'GET',
                dataType: 'json',
                data: {djxl: djxl},
                async: false,
                success: function (data) {
                    if (!isNullOrEmpty(data)) {
                        djyyList = data;
                    }
                }
            });
        }

        /**
         * 渲染项目信息
         * @param bdcxmxx
         */
        function generateXmxx(bdcxmxx) {
            var json = {
                bdcxmxx: bdcxmxx,
                zd: zdList,
                djxldjyy: djyyList
            };
            var tpl = xmxxTpl.innerHTML, view = document.getElementById('xmxx');
            laytpl(tpl).render(json, function (html) {
                view.innerHTML = html;
            });
            form.render();
            renderDate(form);
            if (!isNullOrEmpty(type) && (type === "check" || type === "update")) {
                setAllElementDisabled();
            }
            form.render('select');
            if (isNullOrEmpty(bdcxmxx.djyy)) {
                formSelects.render('djyy', {
                    create: function (id, name) {
                        return name;  //返回该标签对应的val
                    }
                });
            } else {
                formSelects.render('djyy', {
                    init: [bdcxmxx.djyy, bdcxmxx.djyy],
                    create: function (id, name) {
                        return name;  //返回该标签对应的val
                    }
                });
            }
            form.render();
            if (type === "update") {
                setElementAttrByModuleAuthority(moduleCode);
            }
        }

        /**
         * 新增权利人展示
         * @param qllx 权力类型
         * @param xmid 项目 ID
         * @param dydj 抵押登记，判断抵押增加的权利人类型
         */
        function addQlr(qllx, xmid, dydj) {
            var url = getIP() + "/realestate-accept-ui/view/slym/qlr.html?xmid=" + xmid + "&lclx=jdlc&processInsId=" + processInsId + "&qllx=" + qllx;
            // var url = "http://127.0.0.1:8687/realestate-accept-ui/view/slym/qlr.html?xmid=" + xmid + "&lclx=jdlc&processInsId=" + processInsId + "&qllx=" + qllx;
            if (qllx === commonDyaq_qllx || dydj) {
                layer.open({
                    type: 2,
                    area: ['960px', '500px'],
                    fixed: false, //不固定
                    title: "新增抵押权人",
                    content: url + "&dydj=true",
                    btnAlign: "c"
                });
            } else {
                layer.open({
                    type: 2,
                    area: ['960px', '500px'],
                    fixed: false, //不固定
                    title: "新增申请人",
                    content: url,
                    btnAlign: "c"
                });
            }
            form.render();
        }

        /**
         * 保存项目信息
         * @param data 需要保存的项目信息
         */
        function saveXmxx(data) {
            var bdcxm = data.field;
            bdcxm.djyy = formSelects.value('djyy', 'valStr');
            if (bdcxm.xmid !== "" && bdcxm.xmid !== null) {
                $.ajax({
                    url: "/realestate-register-ui/rest/v1.0/blxx/xmxx",
                    type: 'POST',
                    contentType: "application/json",
                    dataType: "json",
                    data: JSON.stringify(bdcxm),
                    success: function () {
                    },
                    error: function (err) {
                        throw err;
                    }
                });
            } else {
                throw err;
            }
        }

        /**
         * 保存项目表内容时同步更新冗余字段
         * @param data
         */
        function updateRyzd(data) {
            var qlData = {};
            // 同步登记原因 (补录暂时没有注销流程)
            var djyy = formSelects.value('djyy', 'valStr');
            var qllx = data.field.qllx;
            if (qllx == "98") {
                qlData.cfyy = djyy;
            } else if (qllx !== "97") {
                qlData.djyy = djyy;
            }
            if (qllx == "3") {
                // 土地权利的修改需要同步以下两个字段内容
                qlData.qlxz = data.field.qlxz;
                qlData.syqmj = data.field.zdzhmj;
            }
            qlData.zl = data.field.zl;
            // 定着物面积与建筑面积同步
            qlData.jzmj = data.field.dzwmj;
            // 定着物用途与规划用途同步
            qlData.ghyt = data.field.dzwyt;

            if (!$.isEmptyObject(qlData) && !isNullOrEmpty(xmid)) {
                $.ajax({
                    url: "/realestate-register-ui/rest/v1.0/blxx/updateQlRyzd?processInsId=&xmid=" + xmid,
                    type: 'PATCH',
                    contentType: "application/json",
                    dataType: "json",
                    data: JSON.stringify(qlData),
                    success: function () {
                    }, error: function (err) {
                        throw err;
                    }
                });
            } else {
                throw err;
            }

        }

        /**
         * 判断是否生成证书
         * 不生成证书修改页面样式，隐藏第 4 步内容
         * @param djxl 登记小类
         */
        function checkSfsczs(djxl) {
            $.ajax({
                url: "/realestate-register-ui/rest/v1.0/blxx/sfsczs?xmid=" + xmid + "&djxl=" + djxl,
                type: "GET",
                async: false,
                success: function (data) {
                    sfsczs = data;
                    if (sfsczs === "0" || sfsczs === 0) {
                        // 进度栏修改为 3 等分
                        $('.bdc-header > p ').css("width", "33.3%");
                        // 隐藏 4. 证书/明信息
                        $('#zsm').addClass('bdc-hide');
                        // 修改 2/4 为 2/3
                        $('#sczs').html("2/3：核验基本信息");
                    }
                    if (sfsczs === "-1" || sfsczs === -1) {
                        console.log("未获取到生否生成证书信息，默认为生成证书");
                    }
                },
                error: function (xhr, status, error) {
                    delAjaxErrorMsg(xhr);
                }
            });
        }
    });
});

/**
 * 加载权利人信息（向后台发起请求获取数据）
 * @param xmid 项目ID
 */
function loadQlr() {
    $.ajax({
        url: "/realestate-register-ui/rest/v1.0/blxx/qlr",
        type: 'GET',
        dataType: 'json',
        data: {xmid: xmid},
        success: function (data) {
            if (!isNullOrEmpty(data)) {
                generateQlrxx(data, xmid);
            }
        }
    });
}

/**
 * 渲染权利人信息
 * @param data 从后台获取的数据
 * @param xmid 项目 ID
 */
function generateQlrxx(data, xmid) {
    var json = {
        xmid: xmid,
        bdcQlrDOList: data,
        zd: zdList
    };
    layui.use(['form', 'jquery', 'laytpl', 'element', 'laydate'], function () {
        var $ = layui.jquery,
            laytpl = layui.laytpl,
            layer = layui.layer,
            laydate = layui.laydate,
            form = layui.form;
        var tpl = sqrTpl.innerHTML, view = document.getElementById('qlrxx');
        laytpl(tpl).render(json, function (html) {
            view.innerHTML = html;
        });
        renderDate(form);
        form.render();
        if (!isNullOrEmpty(type) && (type === "check" || type === "update")) {
            setAllElementDisabled();
            var qlrxx = $('button[name="qlrxx"]');
            $.each(qlrxx, function (index, item) {
                item.setAttribute('disabled', 'off');
                item.classList.add('layui-btn-disabled');
                item.classList.remove('bdc-major-btn');
            });
        }
        if(type === "update"){
            setElementAttrByModuleAuthority(moduleCode);
        }
        form.render('select');
        disabledAddFa();
    });
}

/**
 * 权利人详情展示
 * @param qlrid 权利人 ID
 * @param xmid 项目 ID
 * @param qlrlb 权利人类别
 */
function showQlr(qlrid, xmid, qlrlb) {
    addModel();
    var qllx = $(".qllx").val();
    layui.use(['form', 'jquery', 'laytpl', 'element', 'laydate'], function () {
        var $ = layui.jquery,
            laytpl = layui.laytpl,
            layer = layui.layer,
            laydate = layui.laydate,
            form = layui.form;
        var readOnly = false;
        if (!isNullOrEmpty(type) && (type === "check" || type === "update")) {
            readOnly = true;
        }
        var url = getIP() + "/realestate-accept-ui/view/slym/qlr.html?readOnly=" + readOnly + "&processInsId=" + processInsId + "&qlrid=" + qlrid + "&lclx=jdlc" + "&xmid=" + xmid + "&qllx=" + qllx;
        // var url = "http://127.0.0.1:8687/realestate-accept-ui/view/slym/qlr.html?readOnly=" + readOnly + "&processInsId=" + processInsId + "&qlrid=" + qlrid + "&lclx=jdlc" + "&xmid=" + xmid + "&qllx=" + qllx;
        layer.open({
            type: 2,
            area: ['960px', '475px'],
            fixed: false, //不固定
            title: "申请人详细信息",
            content: url,
            btnAlign: "c"
        });

        removeModel();
        form.render();
        renderDate(form);
        disabledAddFa();
    })
}

/**
 * 保存权利人
 */
function saveQlr() {
    // 证件号验证
    var $zjhs = $('td input[name=zjh]');
    $zjhs.each(function (i,v) {
        var zjzl = $(v).parents('td').prev().find('select').val();
        if (zjzl === '1') {
            var msg = checkSfzZjh($(v).val());
            if (isNotBlank(msg)) {
                layer.msg(msg);
                throw err = new Error(msg);
            }
        }
    });

    var qlrArray = $(".qlr").serializeArray();
    var qlrList = [];
    var qlr = {};
    var qlrnum = 0;
    var gyfs = "";

    for (var j = 0; j < qlrArray.length; j++) {
        var name = qlrArray[j].name;
        qlr[name] = qlrArray[j].value;
        if ((j + 1 < qlrArray.length && qlrArray[j + 1].name === 'xmid') || j + 1 == qlrArray.length) {
            if (qlr.qlrlb === "1") {
                qlrnum++;
                gyfs += qlr.gyfs + ",";
            }
            qlrList.push(qlr);
            qlr = {};
        }
    }
    //验证权利人共有方式
    if (!checkGyfs(gyfs, qlrnum)) {
        layer.msg('共有方式不正确，请检查！');
        throw err = new Error('共有方式不正确，请检查！');
    }

    if (!checkAddGybl(qlrList)) {
        layer.msg('共有比例不正确，请检查！');
        throw err = new Error('共有比例不正确，请检查！');
    }
    var url = getContextPath() + "/rest/v1.0/blxx/qlr?processInsId=" + processInsId;

    if (!isNullOrEmpty(qlrList)) {
        $.ajax({
            url: url,
            type: 'PATCH',
            dataType: 'json',
            async: false,
            contentType: "application/json",
            data: JSON.stringify(qlrList),
            success: function (data) {
                if (data > 0) {
                    loadQlr();
                }
            }, error: function (err) {
                throw err;
            }
        });
    }
}

/**
 * 删除权利人
 * @param qlrid 权利人 id
 * @param sxh 顺序号
 * @param qlrlb 权利人类别
 */
function deleteQlr(qlrid, sxh, qlrlb) {
    var url = "/realestate-register-ui/rest/v1.0/blxx/qlr?xmid=" + xmid + "&qlrid=" + qlrid + "&sxh=" + sxh + "&qlrlb=" + qlrlb + "&processInsId=" + processInsId;
    var deleteIndex = layer.open({
        type: 1,
        skin: 'bdc-small-tips',
        title: '确认删除',
        area: ['320px'],
        content: '是否确认删除？',
        btn: ['确定', '取消'],
        btnAlign: 'c',
        yes: function () {
            addModel();
            $.ajax({
                url: url,
                type: 'DELETE',
                success: function (data) {
                    removeModel();
                    loadQlr();
                    layer.msg("删除成功");
                }
            });
            layer.close(deleteIndex);
        },
        btn2: function () {
            //取消
            layer.close(deleteIndex);
        }
    });
}

/**
 * 改变权利人的顺序号
 * @param qlrid 权利人 id
 * @param czlx 操作类型 UP DOWN
 */
function changeQlrSxh(qlrid, czlx) {
    $.ajax({
        url: "/realestate-register-ui/rest/v1.0/blxx/qlr/sxh",
        type: 'get',
        dataType: 'json',
        data: {qlrid: qlrid, czlx: czlx},
        success: function (data) {
            if (data > 0) {
                loadQlr();
            }
        }, error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });
}

//验证共有比例之和
function checkAddGybl(qlrList) {
    var sumgybl = 0;
    var gyblList = [];
    var qlrs = [];
    //分母相乘
    var fenmumultiply = 1;
    //分子之和
    var fenzicount = 0;
    for (var k in qlrList) {
        if (qlrList[k].qlrlb === "1") {
            qlrs.push(qlrList[k]);
        }
    }
    if (!isNullOrEmpty(qlrs)) {
        for (var k in qlrs) {
            if (qlrs[k].gyfs === "2") {
                if (!isNullOrEmpty(qlrs[k].qlbl)) {
                    var qlbl = qlrs[k].qlbl;
                    if (isNaN(qlbl)) {
                        if (qlbl.indexOf("%") > -1) {
                            gyblList.push(parseFloat(qlbl.substr(0, qlbl.length - 1)));
                        } else if (qlbl.indexOf("/") > -1) {
                            var fenmu = parseInt(qlbl.split("/")[1]);
                            fenmumultiply = fenmumultiply * fenmu;
                        }
                    } else {
                        gyblList.push(parseFloat(qlrs[k].qlbl) * 100);

                    }
                } else {
                    //按份共有共有比例必须有值
                    return false;
                }
            } else if (!isNullOrEmpty(qlrs[k].qlbl)) {
                layer.msg("非按份共有请勿填写共有比例！");
                throw err = new Error('非按份共有请勿填写共有比例!');
            }
        }
    } else {
        //无权利人直接通过
        return true;
    }
    if (gyblList.length > 0) {
        for (var i in gyblList) {
            sumgybl += gyblList[i];
        }
        if (sumgybl === 100) {
            return true;

        }
    } else if (fenmumultiply > 1) {
        for (var k in qlrList) {
            if (qlrList[k].qlrlb === "1" && !isNullOrEmpty(qlrList[k].qlbl)) {
                var qlbl = qlrList[k].qlbl;
                if (qlbl.indexOf("/") > -1) {
                    var fenmu = parseInt(qlbl.split("/")[1]);
                    var fenzi = parseInt(qlbl.split("/")[0]);
                    fenzicount += fenmumultiply / fenmu * fenzi;
                }
            }
        }
        if (fenmumultiply === fenzicount) {
            return true;

        }
    } else {
        return true;

    }
    return false;
}

function checkGyfs(gyfs, qlrnum) {
    if (qlrnum === 1) {
        //一个权利人时共有方式必须为单独所有
        if (gyfs.indexOf("1") < 0 && gyfs.indexOf("2") < 0 && gyfs.indexOf("3") < 0) {
            return true;
        }
    } else if (qlrnum === 2) {
        //两个权利人时共有方式不能存在单独所有，且两个共有方式保持一致
        var gyfsArr = gyfs.split(",");
        if (gyfsArr.length > 1 && gyfsArr[0] === gyfsArr[1] && gyfsArr[0] !== "0") {
            return true;
        }
    } else {
        //多个权利人时共有方式不能存在单独所有
        if (gyfs.indexOf("0") < 0) {
            return true;
        }
    }
    return false;
}

//关闭panel (新增申请人弹窗关闭方法)
function cancelEdit() {
    layui.use(['layer'], function () {
        var layer = layui.layer;
        layer.closeAll();
    });
}


/**
 * value 证件号内容
 * 返回提示信息：verifyMsg
 * 验证身份证证件号码:15位身份证，18位身份证
 */
function checkSfzZjh(value) {
    //验证提示信息
    var verifyMsg = "";
    var city = {
        11: "北京",
        12: "天津",
        13: "河北",
        14: "山西",
        15: "内蒙古",
        21: "辽宁",
        22: "吉林",
        23: "黑龙江 ",
        31: "上海",
        32: "江苏",
        33: "浙江",
        34: "安徽",
        35: "福建",
        36: "江西",
        37: "山东",
        41: "河南",
        42: "湖北 ",
        43: "湖南",
        44: "广东",
        45: "广西",
        46: "海南",
        50: "重庆",
        51: "四川",
        52: "贵州",
        53: "云南",
        54: "西藏 ",
        61: "陕西",
        62: "甘肃",
        63: "青海",
        64: "宁夏",
        65: "新疆",
        71: "台湾",
        81: "香港",
        82: "澳门",
        91: "国外 "
    };

    if (!value || !/(^\d{15}$)|(^\d{6}(18|19|20)?\d{2}(0[1-9]|1[012])(0[1-9]|[12]\d|3[01])\d{3}(\d|X)$)/i.test(value) || (value.length !== 15 && value.length !== 18)) {
        verifyMsg = "身份证号格式错误";
    }
    else if (!city[value.substr(0, 2)]) {
        verifyMsg = "地址编码错误";
    }
    else if (value !== null && value !== "") {
        //18位身份证需要验证最后一位校验位
        if (value.length === 18) {
            value = value.split('');
            //∑(ai×Wi)(mod 11)
            //加权因子
            var factor = [7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2];
            //校验位
            var parity = [1, 0, 'X', 9, 8, 7, 6, 5, 4, 3, 2];
            var sum = 0;
            var ai = 0;
            var wi = 0;
            for (var i = 0; i < 17; i++) {
                ai = value[i];
                wi = factor[i];
                sum += ai * wi;
            }
            if (parity[sum % 11] != value[17].toUpperCase()) {
                verifyMsg = "校验位错误";
            }
        } else if (value.length === 15) {
            value = value.toString();
            var re = new RegExp(/^(\d{6})(\d{2})(\d{2})(\d{2})(\d{3})$/);
            var arrSplit = value.match(re); //检查出生日期是否正确
            if (arrSplit !== null) {
                if (parseInt(arrSplit[2].substr(1)) > 0) {
                    arrSplit[2] = "19" + arrSplit[2];
                } else {
                    arrSplit[2] = "20" + arrSplit[2]
                }
                if (!YearMonthDayValidate(arrSplit[2], arrSplit[3], arrSplit[4])) {
                    verifyMsg = "出生日期不正确";

                }
            } else {
                verifyMsg = "出生日期不正确";
            }
        }
    }

    return verifyMsg;

}