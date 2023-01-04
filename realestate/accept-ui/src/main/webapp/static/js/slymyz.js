/**
 * @author "<a href="mailto:liaoxiang@gtmap.cn>liaoxiang</a>"
 * @version 1.0, 2019/07/25
 * @description 受理页面验证公共JS
 */

/**
 * 验证土地结束期限,验证规则1：权利性质为划拨，土地使用结束期限应为空
 */
function checkJssjGz1(qlxz, jssj) {
    if (qlxz === "101" && isNotBlank(jssj)) {
        throw error = new Error("权利性质为划拨，不可填写土地使用结束时间");
    }
}

/**
 * 验证土地结束期限,验证规则2：权利性质为出让，土地使用结束期限不能为空
 * noyz:是否无需验证
 */
function checkJssjGz2(qlxz, jssj,noyz) {
    if (qlxz === "102" && jssj === "" && !noyz) {
        throw error = new Error("权利性质为出让，请填写土地使用结束时间");
    }
}

/**
 * 验证土地结束期限,验证规则3：南通地区--权利性质为划拨，土地使用起止时间都必须为空
 */
function checkJssjGz3(qlxz, jssj, qssj) {
    if (qlxz === "101" && (isNotBlank(jssj) || isNotBlank(qssj))) {
        throw error = new Error("权利性质为划拨，不予填写土地期限");
    }
}

/**
 * 验证土地结束期限,验证规则4：南通地区--权利性质为租赁，土地使用结束期限不能为空
 */
function checkJssjGz4(qlxz, jssj) {
    if (qlxz === "104" && !isNotBlank(jssj)) {
        throw error = new Error("权利性质为租赁，请填写土地使用结束时间");
    }
}

/**
 * 验证所在层与总层数,验证规则：所在层不能大于总层数，所在层不能为0
 */
function checkSzcAndZcs(szc, zcs) {
    if (szc === "0") {
        throw error = new Error("所在层不能为0");
    }
    if (isNotBlank(szc) && isNotBlank(zcs) && parseInt(szc) > parseInt(zcs)) {
        throw error = new Error("所在层不能大于总层数");
    }
}

// 只做了面积之和的检验，页面没有加 红* 说明可以是空，没有做判空。
function checkMj(zyjzmj, ftjzmj, jzmj) {
    var ftjzmjX = 0;
    var zyjzmjX = 0;
    var jzmjX = 0;
    if (ftjzmj != "") {
        ftjzmjX = parseFloat(ftjzmj) * 100;
    }
    if (zyjzmj != "") {
        zyjzmjX = parseFloat(zyjzmj) * 100;
    }
    if (jzmj != "") {
        jzmjX = parseFloat(jzmj) * 100;
    }
    // 若专有和分摊面积均为 0 的情况下，不要验证 分摊+专有=建筑面积 的规则。
    var isCheck = !(ftjzmjX === 0 && zyjzmjX === 0);
    if (ftjzmj && zyjzmj && isCheck && jzmjX.toFixed(0) != (ftjzmjX + zyjzmjX).toFixed(0)) {
        throw error = new Error("分摊建筑面积和专有建筑面积之和与建筑面积不相等！");
    }
}

//验证分幢面积之和是否等于建筑面积
function checkFdcqxmMj($jzmj,$fzjzmj){
    if($jzmj.length >0 &&$fzjzmj.length >0){
        var jzmjSum = 0.00;
        $.each($fzjzmj, function (index, item) {
            if(isNotBlank(item.value)) {
                jzmjSum = calculateFloat(jzmjSum + parseFloat(item.value));
            }
        });
        var zjzmj =parseFloat($jzmj.val());
        if(jzmjSum.toFixed(2) !=zjzmj.toFixed(2)){
            throw error = new Error("分幢建筑面积之和与建筑面积不相等！");
        }
    }

}

/**
 * 校验定着物面积与建筑面积是否一致
 * @param dzwmj 定着物面积
 * @param jzmj 建筑面积
 */
function checkDzwmjAndJzmj(dzwmj, jzmj){
    if(dzwmj && jzmj && dzwmj != jzmj) {
        throw error = new Error("定着物面积与建筑面积不相等！");
    }
}

/**
 * 验证联系电话,包括手机号码，固话,为空时直接验证通过
 */
function validatePhone(lxdh) {
    var isPhone = /^\d{3}-\d{7,8}|\d{4}-\d{7,8}$/;//电话号码 带-
    var isPhoneWithoutHg = /^\d{10,12}$/;//电话号码 10到12位的纯数字(区号+正号 没有-)
    var isPhoneWithoutQh = /^\d{8}$/;//电话号码 8纯数字 没有区号

    var isMob = /^0?1[3|4|5|6|7|8|9][0-9]\d{8}$/;//手机号码

    if (!isNotBlank(lxdh) || isMob.test(lxdh) || isPhone.test(lxdh) || isPhoneWithoutHg.test(lxdh) || isPhoneWithoutQh.test(lxdh)) {
        //固定电话判断区号长度
        if(isPhone.test(lxdh)) {
            var dhArr = lxdh.split('-');
            if(dhArr != null) {
                var qh = dhArr[0];
                var regExp = /^\s*\S{2,5}\s*$/;
                if(!regExp.test(qh)) {
                    return false;
                }
            }
        }

        //纯数字的前3位或者前4位 验证区号
        if(isPhoneWithoutHg.test(lxdh)) {
            var dhPre3 = lxdh.substring(0,3);
            var dhPre4 = lxdh.substring(0,4);
            if(dhPre3 && dhPre4) {
                var regExp = /^\s*\S{2,5}\s*$/;
                if(!regExp.test(dhPre3) && !regExp.test(dhPre4)) {
                    return false;
                }
            }
        }
        return true;
    }
    else {
        return false;
    }
}

/**
 * elem 元素
 * 添加必填验证
 */
function addRequired(elem) {
    var attrVal = elem.attr("lay-verify");
    //添加必填验证
    if (!isNotBlank(attrVal)) {
        elem.attr("lay-verify", "required");
    } else if (attrVal.indexOf("required") < 0) {
        elem.attr("lay-verify", attrVal + "|required");
    }
    //添加必填背景色
    elem.parents(".layui-inline").addClass("bdc-not-null");
    var requiredArr = elem.parents(".layui-inline").find(".required-span");
    if(requiredArr.length ===0) {
        elem.parents(".layui-inline").find("label").prepend("<span class=\"required-span\"><sub>*</sub></span>");
    }
}

/**
 * elem 元素
 * 移除必填验证
 */
function removeRequired(elem) {
    var attrVal = elem.attr("lay-verify");
    //移除必填验证
    if (isNotBlank(attrVal) && attrVal.indexOf("required") > -1) {
        elem.attr("lay-verify", attrVal.replace("required", ""));
    }
    //移除必填背景色
    elem.parents(".layui-inline").removeClass("bdc-not-null");
    elem.parents(".layui-inline").find(".required-span").remove();
    changeBtxbjs();

}

/**
 * 修改一般抵押的背景和标志*
 */
function fixBgm() {
    var dyfsElementList = document.getElementsByName('dyfs');
    for ( var j = 0; j< dyfsElementList.length; j++) {
        var dyfs = dyfsElementList[j];
        var $dyfs = $(dyfsElementList[j]);

        if (!(dyfs==null) ) {
            var index = dyfs.selectedIndex;
            var dyfsValue = dyfs.options[index].value;
            var $currentTab = $dyfs.parents(".layui-tab-item");
            //获取当前tab页的zgzqe和bdbzzqse对象；
            var $currentZgzqe = $currentTab.find("#dyaq-zgzqe");
            var $currentBdbzzqse = $currentTab.find("#dyaq-bdbzzqse");

            if (dyfsValue==1 && $currentZgzqe.length > 0 && $currentBdbzzqse.length > 0) {
                $currentZgzqe[0].setAttribute("lay-verify","");
                //移除dyaq-zgzqe必填背景色和标志*
                $currentZgzqe[0].setAttribute("style","");
                $currentZgzqe.parents(".layui-inline").removeClass("bdc-not-null");
                $currentZgzqe.parents(".layui-inline").find(".required-span").remove();

                $currentBdbzzqse[0].setAttribute("lay-verify","required");
                //添加dyaq-bdbzzqse必填背景色和标志*
                $currentBdbzzqse.parents(".layui-inline").addClass("bdc-not-null");
                var requiredArr = $currentBdbzzqse.parents(".layui-inline").find(".required-span");
                if(requiredArr.length ===0) {
                    $currentBdbzzqse.parents(".layui-inline").find("label").prepend("<span class=\"required-span\"><sub>*</sub></span>");
                }
            }
        }
    }

}

/**
 * qlrlx 当前权利人类型
 * btqlrlx 需要必填的权利人类型
 * elem 元素
 * 权利人类别为权利人时，根据权利人类型判断字段是否必填
 */
function checkSfRequiredByqlrlx(qlrlx, btqlrlx, elem) {
    if (qlrlx === btqlrlx) {
        addRequired(elem);
    } else {
        removeRequired(elem)
    }
}
/**
 * qlrlx 当前权利人类型
 * qxbtqlrlx 需要取消必填的权利人类型
 * elem 元素
 * 权利人类别为权利人时，根据权利人类型判断字段是否取消必填
 */
function removeSfRequiredByqlrlx(qlrlx, qxbtqlrlx, elem) {
    if (qlrlx === qxbtqlrlx) {
        removeRequired(elem);
    } else {
        addRequired(elem);
    }
}

/**
 * gyfs 共有方式
 * qlbl 权利比例
 * sfnull 是否允许为空
 * verifyMsg 返回验证信息
 * 验证共有比例:非按份共有共有比例必须为空，按份共有共有比例不为空且当为数字时小于1
 */
function checkQlbl(gyfs, qlbl,sfnull) {
    var verifyMsg = "";
    if (gyfs === "2") {
        if (qlbl !== "") {
            if (isNaN(qlbl)) {
                if (qlbl.indexOf("%") > -1) {
                    if (parseFloat(qlbl.substr(0, qlbl.length - 1)) > 100) {
                        verifyMsg = "共有比例不正确";
                    }
                } else if (qlbl.indexOf("/") > -1) {
                    var fenzi = parseInt(qlbl.split("/")[0]);
                    var fenmu = parseInt(qlbl.split("/")[1]);
                    if (fenzi > fenmu) {
                        verifyMsg = "共有比例不正确";
                    }
                }

            } else {
                if ((parseFloat(qlbl) * 100) > 100) {
                    verifyMsg = "共有比例不正确";
                }
            }
        } else if(sfnull) {
            verifyMsg = "必填项不能为空";
        }

    } else if (isNotBlank(qlbl)) {
        verifyMsg = "非按份共有请勿填写共有比例";
    }
    return verifyMsg;


}


function verifyform(form) {
    var result = {};
    //点击提交时不为空的全部标红
    var isSubmit = true;
    //验证提示信息
    var verifyMsg = "必填项不能为空";
    form.verify({
        required: function (value, item) {
            if (value == '') {
                $(item).addClass('layui-form-danger');
                isSubmit = false;
                verifyMsg = "必填项不能为空";
                if(sfyzbt) {
                    result["isSubmit"] = isSubmit;
                    result["verifyMsg"] = verifyMsg;
                }
            }
        },
        qlrmcrequired: function (value, item) {
            //权利人名称必填
            if (value == '') {
                $(item).addClass('layui-form-danger');
                isSubmit = false;
                verifyMsg = "权利人名称不能为空";
                result["isSubmit"] = isSubmit;
                result["verifyMsg"] = verifyMsg;
            }
        }
        , addrequired: function (value, item) {
            //动态添加的必填属性，与layui自带必填区分开来
            if (value == '') {
                $(item).addClass('layui-form-danger');
                isSubmit = false;
                verifyMsg = "必填项不能为空";
                result["isSubmit"] = isSubmit;
                result["verifyMsg"] = verifyMsg;
            }
        }
        , qlbl: function (value, item) {
            var gyfs = $("select[name=gyfs]").val();
            $(item).val(replaceBeforePointZero(value));
            var msg = checkQlbl(gyfs, value,true);
            if (isNotBlank(msg)) {
                $(item).addClass('layui-form-danger');
                isSubmit = false;
                verifyMsg = msg;
                result["isSubmit"] = isSubmit;
                result["verifyMsg"] = verifyMsg;
            }
        }
        , qlbl1: function (value, item) {
            var gyfs = $("select[name=gyfs]").val();
            $(item).val(replaceBeforePointZero(value));
            var msg = checkQlbl(gyfs, value,false);
            if (isNotBlank(msg)) {
                $(item).addClass('layui-form-danger');
                isSubmit = false;
                verifyMsg = msg;
                result["isSubmit"] = isSubmit;
                result["verifyMsg"] = verifyMsg;
            }
        }
        , identitynew: function (value, item) {
            var msg = checkSfzZjh(value);
            if (isNotBlank(msg)) {
                $(item).addClass('layui-form-danger');
                isSubmit = false;
                verifyMsg = msg;
                result["isSubmit"] = isSubmit;
                result["verifyMsg"] = verifyMsg;
            }
        }
        , lxdh: function (value, item) {
            if (!validatePhone($.trim(value))) {
                $(item).addClass('layui-form-danger');
                isSubmit = false;
                verifyMsg = "联系电话格式不正确";
                result["isSubmit"] = isSubmit;
                result["verifyMsg"] = verifyMsg;
            }
        },
        identitynews: function (value, item) {
            // 多个身份证时  先替换中文括号
            if(isNotBlank(value)) {
                var newValue = value.replace(/，/g, ",");
                var newValueArr = newValue.split(",");
                if (newValueArr) {
                    for (var i = 0; i < newValueArr.length; i++) {
                        var msg = checkSfzZjh(newValueArr[i]);
                        if (isNotBlank(msg)) {
                            $(item).addClass('layui-form-danger');
                            isSubmit = false;
                            verifyMsg = msg;
                            result["isSubmit"] = isSubmit;
                            result["verifyMsg"] = verifyMsg;
                            return;
                        }
                    }
                }
            }
        }
        , lxdhs: function (value, item) {
            if(isNotBlank(value)) {
                // 多个身份证时  先替换中文括号
                var newValue = value.replace(/，/g, ",");
                var newValueArr = newValue.split(",");
                if (newValueArr) {
                    for (var i = 0; i < newValueArr.length; i++) {
                        if (!validatePhone($.trim(newValueArr[i]))) {
                            $(item).addClass('layui-form-danger');
                            isSubmit = false;
                            verifyMsg = "联系电话格式不正确";
                            result["isSubmit"] = isSubmit;
                            result["verifyMsg"] = verifyMsg;
                            return;
                        }
                    }
                }
            }
        }
        , zjhlength: function (value, item) {
            var msg = checkZjhLength(value);
            if (isNotBlank(msg)) {
                $(item).addClass('layui-form-danger');
                isSubmit = false;
                result["isSubmit"] = isSubmit;
                result["verifyMsg"] = msg;
            }
        },sfhytsfh: function (value,item) {
            var msg = checkZjhsfhytsfh(value);
            if (isNotBlank(msg)) {
                $(item).addClass('layui-form-danger');
                isSubmit = false;
                result["isSubmit"] = isSubmit;
                result["verifyMsg"] = msg;
            }
        }
    });
    return result;

}

//出生日期的年月日验证
/**
 * @return {boolean}
 */
function YearMonthDayValidate(year, month, day) {
    year = parseInt(year); //年
    month = parseInt(month);//月
    day = parseInt(day);//日
    //判断年，月,日是否为空
    if (isNaN(year) || isNaN(month) || isNaN(day)) {
        return false;
    }
    //判断月是否是在1-12月之间
    if (month < 1 || month > 12) {
        return false;
    }
    //返回当月的最后一天
    var date = new Date(year, month, 0);
    //判断是否超过天数范围
    return !(day < 1 || day > date.getDate());


}

/**
 * value 证件号内容
 * 返回提示信息：verifyMsg
 * 验证身份证证件号码:15位身份证，18位身份证
 */
function checkSfzZjh(value) {
    //证号先去除空格处理
    value =deleteWhitespace(value, "edge");
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

/**
 * gyfs 共有方式集合，多个用英文逗号隔开
 * qlrnum 权利人个数
 * xmid 用于判断当前登记小类是否默认共同共有
 * 验证共有方式是否正确：1.一个权利人为单独所有 2.两个权利人共有方式不为单独所有且保持一致 3.三个及三个以上共有方式不为单独所有
 * 返回结果：是否验证通过
 */
function checkGyfs(gyfs, qlrnum,xmid) {
    if (qlrnum === 1) {
        // 如果 传递过XMID 则判断当前项目 的登记小类 是否为 默认共有方式为 GTGY
        if(xmid){
            var checkflag = false;
            getReturnData("/slym/qlr/checkdefaultgyfs", {gyfs: gyfs, xmid: xmid}, "GET", function (data) {
                checkflag = data;
            }, function () {
            }, false);
            if(checkflag){
                return true;
            }
        }
        if(!dgGyfsYz){
            return true;
        }
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

/**
 * qlrList 权利人集合
 * 验证共有比例之和为1：支持百分数，小数，分数（多个共有比例为统一类型）
 * 返回结果：是否验证通过
 */
function checkAddGybl(qlrList) {
    var sumgybl = 0;
    var gyblList = [];
    var qlrs = [];
    //分母相乘
    var fenmumultiply = 1;
    //分子之和
    var fenzicount = 0;
    for (var k in qlrList) {
        if (qlrList[k].qlrlb === "1" ||(qlrList[k].sqrlb === "1")) {
            qlrs.push(qlrList[k]);
        }
    }
    if (isNotBlank(qlrs)) {
        for (var k in qlrs) {
            if (qlrs[k].gyfs === "2") {
                if (isNotBlank(qlrs[k].qlbl)) {
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
            } else if (isNotBlank(qlrs[k].qlbl)) {
                throw err = new Error('非按份共有请勿填写共有比例！');
            }

        }
    } else {
        //无权利人直接通过
        return true;
    }
    if (gyblList.length > 0) {
        for (var i in gyblList) {
            // sumgybl += gyblList[i];
            sumgybl =parseFloat(math.format(math.evaluate(sumgybl + gyblList[i]), 14));

        }
        if (sumgybl === 100) {
            return true;

        }
    } else if (fenmumultiply > 1) {
        for (var k in qlrList) {
            if (qlrList[k].qlrlb === "1" && isNotBlank(qlrList[k].qlbl)) {
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

/*
* 验证权申请人的证件号长度是否小于五位
*/
function checkZjhLength(zjh) {
    var reg = /^\S{0,4}$/;
    var verifyMsg = "";
    if (reg.test(zjh.replace(/\s*/g, ""))) {
        verifyMsg = "证件号长度不可小于五位";
    }
    return verifyMsg;
}

/*
* 验证权利人数量
*/
function checkQlrsl(processInsId, xmid) {
    var result;
    getReturnData("/slym/qlr/list/xm", {processInsId: processInsId, xmid: xmid, qlrlb: '1'}, "GET", function (data) {
        result = data !== null && data.length > 0;
    }, function () {
    }, false);
    return result;
}

/*
* qlrlxArr 权利人类型证件种类对照数组
* qlrlx 权利人类型
* lwzjzl 例外证件种类（当权利人类型不在数组时证件种类的默认值）
* 根据权利人类型默认证件种类
*/
function getZjzlMrzByQlrlx(qlrlxArr,qlrlx,lwzjzl) {
    var zjzlMrz ="";
    if (qlrlxArr[qlrlx] !== undefined) {
        zjzlMrz = qlrlxArr[qlrlx];
    }else if(isNotBlank(lwzjzl)){
        zjzlMrz =lwzjzl;
    }
    return zjzlMrz;
}

// 表单保存规则验证权籍数据与登记数据是否一致
var AcceptForm = {
    bdcXmCache : null, // 不动产项目信息缓存，第一次获取项目信息将数据放入缓存中，后续直接读取缓存。
    verifyGz : function (gzlslid) {
        // 采用页面缓存，第一点击保存时获取项目信息，其他时间之间获取之前获取的项目信息。
        var deferred = $.Deferred();
        if(null == AcceptForm.bdcXmCache){
            AcceptForm.getBdcXmxx(gzlslid).done(function(bdcxm){
                AcceptForm.bdcXmCache = bdcxm; // BDC_XM信息，设置页面缓存
                AcceptForm.requestBdcGzyz(bdcxm).done(function(value){ // 请求规则验证服务
                    deferred.resolve(value);
                }).fail(function(message){
                    deferred.reject(message);
                });
            }).fail(function(e){
                deferred.reject(e.responseText);
            });
        }else{
            AcceptForm.requestBdcGzyz(AcceptForm.bdcXmCache).done(function(value){
                deferred.resolve(value);
            }).fail(function(message){
                deferred.reject(message);
            });
        }
        return deferred;
    },

    getBdcXmxx : function(gzlslid){  // 根据工作流实例id获取部分项目信息
        var deferred = $.Deferred();
        $.ajax({
            url: getContextPath()+'/slym/xm/bfxx/gzlslid',
            data : {gzlslid : gzlslid},
            type: 'GET',
            dataType: 'json',
            contentType: "application/json;charset=UTF-8",
            success: function (data) {
                deferred.resolve(data);
            },
            error: function (error) {
                delAjaxErrorMsg(error);
                deferred.reject(error);
            }
        });
        return deferred;
    },
    // 请求规则验证服务，获取规则验证结果
    requestBdcGzyz : function(bdcxm){ // 验证规则
        var deferred = $.Deferred();
        var selectDataList = [];
        var zhbs = "";
        $.each(bdcxm, function(index, val){
            zhbs = val.gzldyid + "_SLYMBCYZ";
            selectDataList.push({
                bdcdyh : val.bdcdyh,
                xmid : val.xmid
            });
        });
        $.ajax({
            url: getContextPath() + '/bdcGzyz',
            type: 'POST',
            dataType: 'json',
            contentType: "application/json;charset=UTF-8",
            data: JSON.stringify({
                zhbs : zhbs,
                paramList : selectDataList
            }),
            success: function (data) {
                deferred.resolve(data);
            },
            error: function (error) {
                delAjaxErrorMsg(error);
                deferred.reject("表单保存规则验证失败。");
            }
        });
        return deferred;
    },

    // 规则验证不通过时，给用户提示，包含 更新（更新权籍数据到登记）、忽略（直接提交）、取消（关闭提示窗体，不进行其他操作）三个选项
    showTsxx : function(yzResult, fn){
        layui.use(['form', 'jquery', 'laytpl', 'element'], function () {
            var form = layui.form, laytpl = layui.laytpl;
            var renderData = [];
            // 封装验证规则数据为表单渲染数据格式
            $.each(yzResult, function(index, value){
                var sjljg = value.sjljg;
                renderData.push({
                    bdcdyh : value.bdcdyh,
                    xmid : value.xmid,
                    qjxx : sjljg.qjxx[0],
                    xmxx : sjljg.xmxx[0]
                });
            });
            var tpl = qjsjyztsTpl.innerHTML, view = document.getElementById('qjsjyzdiv');
            //渲染提示框展示
            laytpl(tpl).render(renderData, function (html) {
                view.innerHTML = html;
            });
            // 渲染checkbox复选框
            form.render(null, "qjsjyzts");
            layer.open({
                title: '提示',
                type: 1,
                area: 'auto',
                maxHeight : 500,
                maxWidth : 550,
                btn: ['更新', '忽略', '取消'],
                content: $("#qjsjyzdiv"),
                yes: function(index, layero){
                    // 封装需要修改的数据为BdcDjxxUpdateQO实体信息
                    var bdcDjxxUpdateQOList = AcceptForm.getCheckedValue();
                    if(bdcDjxxUpdateQOList.length == 0){
                        layer.msg("请选择需要更新的内容");
                        return false;
                    }
                    // 更新不动产项目的数据包含 坐落、面积信息、房地产权等信息
                    getReturnData("/slym/xm/updateBdcXmAndFdcq",
                        JSON.stringify(bdcDjxxUpdateQOList), 'PATCH', function (data) {
                            if (lclx === "zhlc") {
                                refreshQlxx();
                            } else if (lclx === "pllc") {
                                loadBdcdyh();
                                loadPlQlxx();
                            }
                            ityzl_SHOW_SUCCESS_LAYER("更新成功");
                            layer.close(index);
                        }, function (err) {
                            throw err;
                        });
                },
                btn2: function(index, layero){
                    fn(); // 回调表单保存方法
                    layer.close(index);
                },
                btn3: function (index, layero) {
                    layer.close(index);
                }
            });
        });
    },
    getCheckedValue : function(){
        var bdcDjxxUpdateQOList = [];
        $(".bdc-qjsjyz").each(function(i, item){
            var bdcxm = {whereMap :{xmids : [ $(item).find("input[name='xmid']").val() ]},
                jsonStr :{}, className: "BdcXmDO"};
            var fdcq = {whereMap : {xmids : [ $(item).find("input[name='xmid']").val() ]},
                jsonStr :{}, className : "BdcFdcqDO"};

            addbdcDjxxUpdateQO(item, "bdcxmVal", bdcxm, bdcDjxxUpdateQOList);
            addbdcDjxxUpdateQO(item, "fdcqVal", fdcq, bdcDjxxUpdateQOList);

            function addbdcDjxxUpdateQO(item, selector, obj, list) {
                var checkUpdate = false;
                $(item).find("."+selector).each(function(index,val){
                    if($(this).is(":checked")){
                        var name = $(this).data(selector);
                        obj.jsonStr[name] = $(this).val();
                        checkUpdate = true;
                    }
                });
                if(checkUpdate){
                    list.push({
                        jsonStr : JSON.stringify(obj.jsonStr),
                        whereMap : obj.whereMap,
                        className : obj.className
                    });
                }
            }
        });
        console.info(bdcDjxxUpdateQOList);
        return bdcDjxxUpdateQOList;
    },
    verifyData : function(arg1, arg2){
        if(isNotBlank(arg2) && arg1 != arg2){
            return true;
        }
        return false;
    },
    getZdMc : function(category, dm){
        var mc = "";
        if(!isNotBlank(dm)|| !isNotBlank(category)){
            return mc;
        }
        var categoryList = zdList[category];
        $.each(categoryList,function(index, value){
            if(value.DM == dm){
                mc = value.MC;
                return false;
            }
        });
        return mc;
    },
    yzDyje : function (bdbzzqse,fn){
        if(bdbzzqse >= 10000 ) {
            var msg = "页面填写抵押金额超过1亿，请复核";
            var tsxxHtml = '<div className="bdc-right-tips-box bdc-zfyz-tips" ' +
                'style="font-size: 14px;line-height: 28px;padding-left: 26px;position: relative;">\n' +
                '<p><img src="../../static/lib/bdcui/images/warn.png" alt="" style="height: 16px;height: 16px; padding-right: 5px">' + msg +
                '<a href="javascript:;" class="confirmRemove" style="color: #1d87d1; padding-left: 5px;">忽略</a></p>' +
                '</div>';
            warnLayer = layer.open({
                type: 1,
                title: '提示信息',
                anim: -1,
                skin: 'bdc-tips-right bdc-error-layer',
                shade: [0],
                area: ['600px'],
                offset: 'r',
                content: tsxxHtml,
                time: 5000000, //2秒后自动关闭
                success: function () {
                    $('.bdc-tips-right .bdc-right-tips-box .layui-icon-close').on('click', function () {
                        layer.close(warnLayer);
                    });
                    setTimeout(function () {
                        $('.bdc-error-layer').css('opacity', 1)
                    }, 500);
                }
            });
        }else{
            fn();
        }
        $('.confirmRemove').click(function () {
            $(this).parent().remove();
            layer.close(warnLayer);
            fn();
        });
    }
}

/*
* 根据权利人名称查询小微企业信息
*/
function getXwqyxx(qlrmc,callback){
    getReturnData("/slym/qlr/bdcxtjg/jgxx",{jgmc:qlrmc,jglb:3},"GET",function (data) {
        callback(data);
    },function (error) {
        delAjaxErrorMsg(error);

    },false)

}

/**
 * 终生居住为是时,无需填写居住期限，否则居住期限必填
 */
function checkJzqsj(zsjz, jzqqssj,jzqjssj){
    if(parseInt(zsjz) ===1){
        if(isNotBlank(jzqjssj) ||isNotBlank(jzqqssj)) {
            throw error = new Error("选择终生居住,无需填写居住权起始时间和居住权结束时间！");
        }
    }else if(parseInt(zsjz) ===0){
        if(!isNotBlank(jzqjssj)) {
            throw error = new Error("请填写权居住权结束时间！");
        }
    }
}

/**
 * 林地使用（承包）时间为长期，默认为‘否’，当勾选为‘是’时,无需填写林地使用时间，否则林地使用时间必填
 */
function checkldsysj(syqx, ldsyqssj,ldsyjssj){
    if(syqx ==='长期'){
        if(!(isNullOrEmpty(ldsyqssj) && isNullOrEmpty(ldsyjssj))) {
            document.getElementById('lq-ldsyqssj').value='';
            document.getElementById('lq-ldsyjssj').value='';
            throw error = new Error("选择林地使用（承包）时间为长期,无需填写林地使用时间！");
        }
    }
}

/*
* 验证权申请人的身份证位数是否为18位
*/
function checkZjhNumber(qlrList) {
    if ( qlrList == null || qlrList.length == 0) {
        return;
    }

    for (var i = 0 ; i < qlrList.length; i++) {
        var qlrzjzl =qlrList[i].zjzl;
        var qlrzjh =qlrList[i].zjh;

        if (isNullOrEmpty(qlrzjzl) || isNullOrEmpty(qlrzjh)) {
            return;
        }

        if (qlrzjzl == 1 && (qlrzjh.length != 18)) {
            removeModal();
            warnMsg("存在非18位的身份证号，请检查！");
            return;
        }
    }
}

/*
* 验证申请人的证件号是否有特殊符号
*/
function checkZjhsfhytsfh(zjh) {
    var reg = new RegExp("[`~!@#$^&*()=|{}':;',\\[\\].<>《》/?~！@#￥……&*（）——|{}【】‘；：”“'。，、？ ]");
    var verifyMsg = "";
    if (reg.test(zjh.replace(/\s*/g, ""))) {
        verifyMsg = "证件号存在符号不符合要求";
    }
    return verifyMsg;
}