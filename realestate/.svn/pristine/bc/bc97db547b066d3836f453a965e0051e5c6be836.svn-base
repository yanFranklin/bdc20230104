//是否修改流程期限
var sfxglcqx;
var dyzl;
//页面逻辑版本
var ymVersion;
var noYzGhytList;
//审批来源集合
var splyList;
//金额单位
var commonJedw = "";
//层高
var cgList = [];
//是否持证人配置值
var sfczr = 0;
var sfchangeqljbxxTab = false;
var jzzrFjnr = "";
//需要监听修改的登记小类
var xgnrglxs = "";
var zhsjcl = false;
//收件材料上传exe模式
var sjclexe = false;
var sdqghDjxlList;
var sfxzywr = true;
var showZjjgBtn = false;
var slymPrintBtn = "";
//是否验证表单必填
var sfyzbt = true;
//权利人页面保存后是否自动关闭
var qlrsfzdgb = false;
//新增收件材料从登记原因收件材料配置获取
var addSjclFromDjyypz = false;
//增量初始化自定义选择台账配置
var zdyZlcshXztzPz = "";
var sfczrBySxh = false;
var noSfczrBySxhDjxlList = [];
//判断是否批量创建多个流程;
var plcjlc = false;
var jdmc = "";
//是否继承业务流转
var jcywlz = false;
var xzqlList = ["95", "96", "97", "98", "19", "92", "37"];
var ygdjhqspfhtxxDjxlList;
var colIdAndDjyyValMap = {};
//项目内多幢,不需要同步分幢面积之和到建筑面积的宗地特征码
var nosumfzmjZdtzm = "";
//共有方式单个是否验证
var dgGyfsYz = true;
//必填项背景色
var btxbjs = "";
//必填项标志大小
var btxbzSize = "";
//根据工作流定义id判断是否需要区分正常状态颜色默认不区分
var zcztbs = false;
var dysw = [];

var hyllc = false;

var zhlcfkdy = false;

var zhajlc = false;

var zhclSjdBtn = {};
var zhlcSqsBtn = {};

var idAuthorityMap = {};
/*
* 规则验证忽略相同子规则
* */
var gzyzHlxtZgz = false;

//展示家庭成员按钮
var showJtcyQllxList =[];

var sjdqzwz;

var sqsqzwz;

var clfmmht;

var qrsqzwz;

//默认领证方式；
var lzfsQldmMap;

var sfscwjzx;
// 签名图片地址
var signImageUrl;
var qlrjyfeMrzDjxl = [];

var zdjbdblc = false;
var bysldj = false;
//删除收件材料后是否先保存，常州配置为false，其他地区true
var scbcsjcl = true;
/*
* 是否撤销登记
* */
var sfcxdj = false;

//二次确认按钮id
var sfecqrids ;

// ppMhlx
var ppMhlx;

//是否显示维修基金信息
var xswxjj;

//全局定义layer,laydate
var laydate, layer, form;

//土地交易服务费
var tdjyfwf;

/*
* 测量结果材料类型字典值
* */
var cljgCllxZdList = [];
layui.use(['laydate', 'layer'], function () {
    laydate = layui.laydate;
    layer = layui.layer;
});

/**
 * 贷款方式（作为常量，各地区自行维护）
 * @type {string[]}
 */
var dkfs = ['公积金贷款', '组合贷款', '商业贷款', '其它'];

var zdbdcdyfwlxList = [{"DM": "xmxx", "MC": "项目内多幢"}, {"DM": "ljz", "MC": "逻辑幢"}, {
    "DM": "hs",
    "MC": "实测户室"
}, {"DM": "ychs", "MC": "预测户室"}];

var czlxArr = {1: "点击忽略", 2: "点击查看",3: "点击确定", 4: "点击取消",5: "点击是", 6: "点击否",7:"点击忽略全部",8:"点击全部例外"};
var qlrlxZjzlDzMap;
var yzsfhytsfhzjzl;
//----------------------------------------------------------滚动时头部固定-----------------------------------------------------
$(function () {
    //获取配置url地址
    querySlymUrl();
    //监听共有方式
    layui.use(['laydate', 'layer', 'form'], function () {
        form = layui.form;
        form.on('select', function (data) {
            changeBtxbjs();
            // changeAuthority(data.elem.id,data.elem.value);
        });
    });

    window.getCommonZd = function (callback) {
        var zdList = getZdList();
        if (zdList) {
            callback(zdList);
        }
    };

});

/**
 * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @description 监听某个元素改变，另外的字段设置相关权限
 * 只支持 设置不可编辑，可用，必填三个权限，隐藏不支持，有些元素在设置隐藏时直接remove了，切换元素后无法还原
 * 重要！！！ 暂不可用，页面重新加载后无法设置重新渲染
 * @date : 2022/6/6 11:27
 */
function changeAuthority(id, value) {
    var authorityElemMap = idAuthorityMap[id];
    if (isNotBlank(authorityElemMap)) {
        var authorityElem = authorityElemMap[value];
        if (isNotBlank(authorityElem)) {
            for (var i in authorityElem) {
                //i表示的时需要改变权限的字段id
                var authorityList = authorityElem[i];
                if (isNotBlank(authorityList)) {
                    for (var j = 0; j < authorityList.length; j++) {
                        //2.只读3.必填
                        if (authorityList[j] === "1" || authorityList[j] === "4" || authorityList[j] === "5") {
                            //不支持隐藏，renove 后无法还原
                            continue;
                        }
                        setElementAttr(authorityList[j], document.getElementById(i));
                    }
                }
            }
        } else {
            var url = window.location.pathname.match(/(\S*).html/)[1];
            var htmlName = url.substring(url.lastIndexOf("/") + 1)
            //移除属性所有额外附加属性 隐藏-disabled required 等等
            for (var k in authorityElemMap) {
                var needRmoeveAttrElemList = authorityElemMap[k];
                if (isNotBlank(needRmoeveAttrElemList)) {
                    for (var m in needRmoeveAttrElemList) {
                        //j 表示的是元素的id
                        removeAllAttr(m);
                    }
                }
            }
            //移除后根据表单中心设置权限
            getStateById(false, formStateId, htmlName, "", "");
        }
    }
}

function removeAllAttr(id) {
    var element = document.getElementById(id);
    //1. 移除只读属性，也就是设置为可用
    setElementAttr("4", element);
    //2. 移除必填属性
    removeRequired($("#" + id));
}

//获取配置URL地址
function querySlymUrl() {
    var processKey = getQueryString("processDefKey");
    var gzlslid = getQueryString("processInsId");
    var taskid = getQueryString("taskId");
    var gzldyid = "";
    if (processKey !== null && processKey !== undefined) {
        gzldyid = processKey;
    }
    $.ajax({
        url: '/realestate-accept-ui/url',
        type: 'GET',
        data: {gzldyid: gzldyid, gzlslid: gzlslid, taskid: taskid},
        dataType: 'json',
        async: false,
        success: function (data) {
            if (isNotBlank(data)) {
                sfxglcqx = data.sfxglcqx;
                dyzl = data.dyzl;
                ymVersion = data.ymVersion;
                noYzGhytList = (data.noYzGhytList);
                splyList = (data.splyList);
                commonJedw = data.jedw;
                cgList = data.cgList;
                sfczr = data.sfczr;
                sfchangeqljbxxTab = data.changeQlJbxxSx;
                jzzrFjnr = data.jzzrFj;
                xgnrglxs = data.xgnrglxs;
                zhsjcl = data.zhsjcl;
                sdqghDjxlList = data.sdghDjxlList;
                sfxzywr = data.sfxzywr;
                showZjjgBtn = data.sfzxZjjgBtn;
                slymPrintBtn = data.slymPrintBtnMap;
                sfyzbt = data.sfyzbt;
                qlrsfzdgb = data.autoClose;
                addSjclFromDjyypz = data.sjclFromDjyyPz;
                zdyZlcshXztzPz = data.zdyZlcshXztzPz;
                sfczrBySxh = data.sfczrBySxh;
                noSfczrBySxhDjxlList = data.noSfczrBySxhDjxlList;
                plcjlc = data.plcjlc;
                jdmc = data.jdmc;
                jcywlz = data.jcywlz;
                colIdAndDjyyValMap = data.colIdAndDjyyValMap;
                ygdjhqspfhtxxDjxlList = data.ygdjhqspfhtxxDjxlList;
                nosumfzmjZdtzm = data.nosumfzmjZdtzm;
                dgGyfsYz = data.gyfsdgyz;
                dysw = data.dysw;
                hyllc = data.hyllc;
                btxbjs = data.btxbjs;
                btxbzSize = data.btxbzSize;
                zhlcfkdy = data.zhlcfkdy;
                zhclSjdBtn = data.sjdBtn;
                zhlcSqsBtn = data.sqsBtn;
                zhajlc = data.zhajlc;
                gzyzHlxtZgz = data.gzyzHlxtZgz;
                idAuthorityMap = data.idAuthorityMap;
                tdjyfwf = data.tdjyfwf;
                for (var i=0;i<data.zcztqf.length;i++){
                    if (processKey == data.zcztqf[i]){
                        zcztbs = true;
                    }
                }
                showJtcyQllxList =data.showJtcyQllxList;
                lzfsQldmMap = data.lzfsQldmMap;
                sfscwjzx = data.sfscwjzx;
                sqsqzwz = data.sqsqzwz;
                qrsqzwz = data.qrsqzwz;
                clfmmht = data.clfmmht;
                sjdqzwz = data.sjdqzwz;
                qlrjyfeMrzDjxl = data.jyfeMrzDjxlList;
                zdjbdblc = data.zdjbdb;
                bysldj = data.bysldj;
                scbcsjcl = data.scbcsjcl;
                sfcxdj = data.cxdj;
                sfecqrids = data.sfecqrids;
                signImageUrl = data.signImageUrl;
                ppMhlx = data.ppMhlx;
                xswxjj = data.xswxjj;
                cljgCllxZdList = data.cljgCllxZdList;
                sjclexe = data.sjclExe;
                qlrlxZjzlDzMap = data.qlrlxZjzlDzMap;
                yzsfhytsfhzjzl = data.yzzjhsfhytsfh;
            }
        }, error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr)
        }
    });
}

// 获取字典信息
function getZdList() {
    getReturnData("/bdczd", {}, "POST", function (data) {
        zdList = data;
    }, function () {
    }, false);
    return zdList;
}


function getContextPath() {
    var pathName = document.location.pathname;
    var index = pathName.substr(1).indexOf("/");
    var result = pathName.substr(0, index + 1);
    return result;
}

//关闭当前页面(父页面关闭当前弹框)
function closeWin() {
    var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
}

//关闭父页面
function closeParentWindow() {
    var index = parent.parent.layer.getFrameIndex(window.name);
    parent.parent.layer.close(index);
}

/**
 * 对传入的字符串进行去空格处理
 * @date 2019.03.14 18:43
 * @author hanyaning
 * @param str 要处理的字符串
 * @param where 处理方式，all---所有空格；edge---两边；left——左边；right——右边
 * @return
 */
function deleteWhitespace(str, where) {
    if (isNotBlank(str)) {
        switch (where) {
            case "all":
                return str.replace(/\s*/g, "");
            case "edge":
                return str.replace(/^\s*|\s*$/g, "");
            case "left":
                return str.replace(/^\s*/, "");
            case "right":
                return str.replace(/(\s*$)/g, "");
            default :
                return str.replace(/\s*/g, "");
        }
    } else {
        return "";
    }
}

//----------------------------------------------------------不可编辑添加图标-----------------------------------------------------
function disabledAddFa(formid) {
    var disabledElArray = $(":disabled");
    if (isNotBlank(formid)) {
        disabledElArray = $("#" + formid).find(":disabled");
    }
    if (disabledElArray !== null && disabledElArray.length > 0) {
        for (var i = 0; i < disabledElArray.length; i++) {
            var disabledEl = disabledElArray[i];
            if (disabledEl.type !== "checkbox" && disabledEl.type !== "radio" && disabledEl.type !== "hidden") {
                if ($(disabledEl).is("input") || $(disabledEl).is("textarea") || $(disabledEl).is("select")) {
                    var imgArray = $(disabledEl).parent().find("img");
                    if (imgArray.length === 0 && !$(disabledEl).parent().parent().hasClass('layui-laypage')) {
                        $(disabledEl).parent().append("<img src=\"../../static/lib/bdcui/images/lock.png\" alt=\"\">");
                        $(disabledEl).parent().addClass('bdc-date-disabled');
                    }
                    if ($(disabledEl).parent().find('.xm-select-dis').length > 0) {
                        $(disabledEl).parent().find('.xm-select-dis .xm-select--suffix + div').attr('title', $(disabledEl).parent().find('.xm-select-dis>.xm-input').attr('title'));
                    }
                }
            }
        }
    }

    $('.layui-select-disabled i').removeClass('layui-edge');
    $('.bdc-date-disabled i').removeClass('layui-icon-close');
    $('.layui-select-disabled i').addClass('bdc-hide');


    $('.layui-select-disabled .layui-input').attr('disabled', 'true').removeClass('layui-disabled');

    $('.layui-select-disabled .layui-input').attr('placeholder', ' ');
    changeBtxbjs();
}

//----------------------------------------------------------系统参数-----------------------------------------------------
//设置IP
function getIP() {
    return document.location.protocol + "//" + window.location.host;
}

function zhzs(value) {
    var regu = /^[1-9]+[0-9]*]*$/;
    if (isNotBlank(value)) {
        if (!regu.test(value)) {
            ityzl_SHOW_INFO_LAYER("请输入正整数");

        } else {
            return value;
        }
    }
}

//给下拉框增加title属性
function renderSelect() {
    layui.use(['form', 'jquery', 'laytpl', 'element', 'laydate'], function () {
        var $ = layui.$;
        $('.layui-form').on('mouseenter', '.layui-form-select .layui-select-title', function (d) {
            var that = this,
                elem = $(that),
                title = elem.context.firstChild.value;
            if (title !== null && title !== "") {
                elem.attr("title", title);
            }
        })
    })
}

//下拉框添加删除图标
function renderSelectClose(form) {

    //判断默认情况下下拉框是否需要删除图标(当1.下拉框可编辑 2.内容不为空 3.有清空选项）
    $(".xzq-sele .layui-input-inline select").each(function () {
        if ($(this).attr("disabled") !== "disabled" && isNotBlank($(this).val()) && $(this).find("option:contains('请选择')").length > 0) {
            $(this).parents('.layui-input-inline').find('.layui-icon-close').show();
        }
    });

    //监听select选择
    form.on('select', function (data) {
        if (isNotBlank(data.value) && $(this).parents(".xzq-sele").find("option:contains('请选择')").length > 0) {
            $(this).parents('.layui-input-inline').find('.layui-icon-close').show();
        }
    });
    //监听删除图标点击
    $('.xzq-sele .layui-icon-close').on('click', function (item) {
        $(this).parent().find("option:contains('请选择')").attr("selected", "selected");
        $(this).hide();
        form.render("select");
        resetSelectDisabledCss();
    });


}

/**
 * formIds 支持多个，用英文逗号隔开
 * 监听修改字段
 */
function renderChange(formSelects, form, formIds) {
    $.each(formIds.split(","), function (index, formID) {
        if (isNotBlank(formID)) {
            var $input = $("#" + formID).find(".layui-input");
            //监听input修改
            $input.on('change', function () {
                $(this).addClass('bdc-change-input');
                var text =$(this).parents(".layui-inline").find("label").text();
                renderChangeTips(text);
            });
            var $textarea = $("#" + formID).find(".change-textarea-width");
            //监听textarea修改
            $textarea.on('change', function () {
                $(this).addClass('bdc-change-input');
                var text = $(this).parents(".change-textarea-margin").find("label").text();
                renderChangeTips(text);
            });
        }
    });

    //监听select下拉框内容修改
    form.on('select', function (data) {
        console.log(data.elem); //得到select原始DOM对象
        console.log(data.value); //得到被选中的值
        console.log(data.othis); //得到美化后的DOM对象
        $.each(formIds.split(","), function (index, formID) {
            var $select = $("#" + formID).find(data.othis).find("input");
            if ($select.length > 0) {
                $(data.elem).parents(".layui-input-inline").addClass('bdc-change-input');
                var text = $(data.elem).parents(".layui-inline").find("label").text();
                renderChangeTips(text);
            }
        });
    });
}

/**
 * text 修改字段名称
 * 修改字段时同步显示下方提示
 */
function renderChangeTips(text) {
    if (isNotBlank(text)) {
        text = text.replace("*", "");
        text = " "+text+" ";
        //显示下方提示
        $(".bdc-update-tips-box").removeClass("bdc-hide");
        //查询提示框内容，判断是否包含修改字段，不包含则增加
        //添加受理页面中打开的子页面中修改了内容，在父页面中增加提示展示
        var tiptext = "";
        if($("#updateTips p").length != 0){
            tiptext = $("#updateTips p").text();
        }else{ // 当为获取到更新tips的内容时，采用调用父页面dom来获取。
            tiptext = parent.$("#updateTips p").text();
        }
        var textArr = text.split(" ");
        textArr.forEach(function(v){
            if(tiptext.indexOf(v) < 0){
                var addText = " " + v;
                if($("#updateTips p").length != 0){
                    $("#updateTips p").append(addText);
                }else{
                    parent.$("#updateTips p").append(addText);
                }
            }
        });
        //关闭提示
        $('.bdc-update-tips-box .bdc-close').on('click', function () {
            $('.bdc-update-tips-box').addClass('bdc-hide');
        });
    }

}

function layerOpenFull(url, title) {
    var width = $(window).width() + "px";
    var height = $(window).height() + "px";
    var index = layer.open({
        title: title,
        type: 2,
        content: url,
        area: [width, height],
        maxmin: true,
        zIndex: 2147483647,
        cancel: function () {
            refreshQlxx();
        }
    });
    layer.full(index);
}


function layerOpenFullReload(url, title) {
    var width = $(window).width() + "px";
    var height = $(window).height() + "px";
    var index = layer.open({
        title: title,
        type: 2,
        content: url,
        area: [width, height],
        maxmin: true,
        zIndex: 2147483647,
        end: function () {
            if (lclx === "zhlc") {
                loadJbxx();
                loadSjcl();
                refreshQlxx();
            } else if (lclx === "pllc") {
                $.when(loadBdcdyh()).done(function () {
                    var a = setInterval(function () {
                        if ($('.bdc-form-div .layui-show .bdc-table-box .layui-table-main>.layui-table').height() !== 0) {
                            getStateById(readOnly, formStateId, "slympl");
                            clearInterval(a);
                        }
                    }, 50);
                });
            }
        }
    });
    layer.full(index);
}

/**
 * 获取权利信息页面
 */
function getQlxxYm(qllx, bdcdyfwlx) {
    var qllxym;
    var qlArr = {
        1: "tdsyq",
        2: "tdsyq",
        3: "jsydsyq",
        5: "jsydsyq",
        7: "jsydsyq",
        9: "tdcbjyqNyddqtsyq",
        11: "lq",
        12: "lq",
        13: "tdcbjyqNyddqtsyq",
        14: "tdcbjyqNyddqtsyq",
        15: "hysyq",
        16: "gzwsyq",
        17: "hysyq",
        18: "gzwsyq",
        19: "dyiq",
        23: "tdcbjyqNyddqtsyq",
        24: "gzwsyq",
        25: "gzwsyq",
        26: "gzwsyq",
        30:"tdcbjyqNyddqtsyq",
        31:"lq",
        33:"lq",
        34:"lq",
        35:"lq",
        36:"lq",
        50: "tdjyq",
        92: "jzq",
        94: "jzwqfsyqyzgybf",
        37: "dyaq",
        95: "dyaq",
        96: "ygdj",
        97: "yydj",
        98: "cfdj",
        99: "qtxgql"
    };
    if (qlArr[qllx] != undefined) {
        qllxym = qlArr[qllx];
    }
    switch (qllx) {
        case 4:
            if (bdcdyfwlx === 1) {
                qllxym = "fdcqXmndzfw";
            } else {
                qllxym = "fdcq";
            }
            break;
        case 6:
            if (bdcdyfwlx === 1) {
                qllxym = "fdcqXmndzfw";
            } else {
                qllxym = "fdcq";
            }
            break;
        case 8:
            if (bdcdyfwlx === 1) {
                qllxym = "fdcqXmndzfw";
            } else {
                qllxym = "fdcq";
            }
            break;
        default:
    }

    return qllxym;

}

/**
 * @param url url地址
 * @param title 标题
 * @param width
 * @param height
 * layer弹出层弹出全屏
 */
function layerCommonOpenFull(url, title,width, height) {
    var index = layer.open({
        type: 2,
        title: title,
        area: [width, height],
        fixed: false, //不固定
        maxmin: true, //开启最大化最小化按钮
        content: url
    });
    layer.full(index);
}

//回写信息到平台
function hxYwsj(processInsId){
    getReturnData("/slym/qlr/hxywsj?gzlslid="+processInsId,{},"POST",function (){

    },function (error){

    });
}


//处理ajax的错误
function delAjaxErrorMsg(e,message) {
    removeModal();
    layer.closeAll();
    var msg = '请求异常！';
    var detail = '';
    if (message != '' && message != undefined) {
        msg = message;
    }
    if (e.status == 500) {
        var responseText = JSON.parse(e.responseText);
        msg = (message != '' && message != undefined)? msg:responseText.msg;
        detail = responseText.detail;
    }
    // if (e.status == 302 || e.status == 401 || e.status == 403 || e.status == 0 || e.status == 405) {
    //     msg = "已离线，请刷新";
    // }
    //session失效
    // if(isSessionTimeout(e)){
    //     layer.msg('会话超时，即将刷新页面。');
    //     window.location.reload();
    //     return false;
    // }
    layer.msg('<img src="../../static/image/error-small.png" alt="">' + msg + ' <a class="bdc-show-more-tips" href="javascript:;">更多</a>', {
        time: 4000,
        success: function () {
            if($('#otherTips').length == 0){
                $('body').append('<div class="bdc-other-tips-box bdc-hide">\n' +
                    '    <div class="bdc-other-tips">\n' +
                    '        <p>错误提示：<span class="bdc-close">不再提示</span></p>\n' +
                    '        <div id="otherTips">\n' +
                    '        </div>\n' +
                    '    </div>\n' +
                    '</div>');
            }
            var exceptionMsg = '';
            $.each(detail, function (key, val) {
                exceptionMsg += '<p>' + val + '</p>';
            });
            if (exceptionMsg == '') {
                exceptionMsg = '<p>暂无详细信息，请查看系统日志</p>'
            }
            $('#otherTips').html(exceptionMsg);
            //点击更多
            $('.bdc-show-more-tips').on('click', function () {
                $('.bdc-other-tips-box').removeClass('bdc-hide');
            });
            //点击 不再提示 ，关闭提示框
            $('.bdc-other-tips-box .bdc-close').on('click', function () {
                $('.bdc-other-tips-box').addClass('bdc-hide');
            });
        }
    });
}

//处理session超时失效
function isSessionTimeout(e){
    if(e.status == 405){
        var responseText = JSON.parse(e.responseText);
        var path =responseText.path;
        if(!isNullOrEmpty(path) &&path.indexOf("/account/login") >-1) {
            return true;
        }
    }else if(e.status ==200){
        var responseText = e.responseText;
        if(!isNullOrEmpty(responseText) &&responseText.indexOf("<!DOCTYPE html>") >-1&&responseText.indexOf("登录") >-1) {
            return true;
        }
    }
    return false;
}


//操作成功提示
function ityzl_SHOW_SUCCESS_LAYER(msg) {
    layer.msg('<img src="../../static/lib/bdcui/images/success-small.png" alt="">' + msg);
}

//信息小提示
function ityzl_SHOW_INFO_LAYER(msg) {
    layer.msg('<img src="../../static/lib/bdcui/images/info-small.png" alt="">' + msg);
}

//警告小提示
function ityzl_SHOW_WARN_LAYER(msg) {
    layer.msg('<img src="../../static/lib/bdcui/images/warn-small.png" alt="">' + msg);
}

//警告框
function showAlertDialog(alertMsg) {
    var alertIndex = layer.open({
        type: 1,
        skin: 'bdc-small-tips',
        title: '信息',
        area: ['320px'],
        content: alertMsg,
        btn: ['确定'],
        btnAlign: 'c',
        yes: function () {
            layer.close(alertIndex);
        }
    });

}

/**
 * 显示提示对话框
 * @param title 提示框标题
 * @param msg 提示信息
 * @param yesFun 选择确定
 * @param yesParm
 * @param noFun 选择取消
 * @param noParm
 * @param isAddmodel 是否增加遮罩
 * @return
 */
function showConfirmDialog(title, msg, yesFun, yesParm, noFun, noParm,isAddmodel) {
    var btnName = "取消";
    if("checkSfcjWlzs" == noFun){
        btnName = "忽略";
    }
    var index = layer.open({
        type: 1,
        skin: 'bdc-small-tips',
        title: title,
        area: ['320px'],
        content: msg,
        btn: ['确定', btnName],
        btnAlign: 'c',
        yes: function () {
            if (yesFun != null && yesFun != "") {
                if(isAddmodel) {
                    addModel();
                    setTimeout(function () {
                        eval(yesFun + "(" + yesParm + ")");
                    }, 10);
                }else{
                    eval(yesFun + "(" + yesParm + ")");

                }

            }
            layer.close(index);
        },
        btn2: function () {
            //取消
            if (noFun != null && noFun != "") {
                eval(noFun + "(" + noParm + ")");
            }
            layer.close(index);
        }
    });
}

function ERROR_CONFIRM(title, msg) {
    layer.confirm(msg, {icon: 5, btn: '确认', title: title}, function (index) {
        layer.close(index);
    });
}


//添加遮罩
function addModel(message) {
    if(!isNotBlank(message)){
        message='加载中';
    }
    var modalHtml = '<div id="waitModalLayer" style="z-index: 9999999999">' +
        '<p class="bdc-wait-content">' +
        '<i class="layui-icon layui-anim layui-anim-rotate layui-anim-loop">&#xe63d;</i>' +
        '<span>'+message+'</span>' +
        '</p>' +
        '</div>';
    $('body').append(modalHtml);
}

//移除遮罩
function removeModal() {
    $("#waitModalLayer").remove();
}

//----------------------------------------------------------通用方法-----------------------------------------------------
//等待背景层
function beginAction() {
    var modalHtml = '<div id="waitModalLayer" style="position: fixed;top: 0;bottom: 0;left: 0;right: 0;z-index: 9999999999;background-color: #000;opacity: 0.3;filter:Alpha(opacity=30);"><span style="top: 50%;left: 50%;position: absolute;color: #fff;font-size: 61px;"><i style="font-size:100px;" class="layui-icon layui-anim layui-anim-rotate layui-anim-loop">&#xe63d;</i></span></div>';
    $('body').append(modalHtml);
}

/**
 * 不动产单元号分层显示
 */
function queryBdcdyh(bdcdyh) {
    if (bdcdyh.length === 28) {
        return bdcdyh.substring(0, 6) + " " + bdcdyh.substring(6, 12) + " " + bdcdyh.substring(12, 19) + " " + bdcdyh.substring(19, 28);
    } else {
        return bdcdyh;
    }
}

/**
 * 渲染日期
 */
function renderDate(form, formIds) {
    var laydate = layui.laydate;
    var format = 'yyyy-MM-dd';
    lay('.test-item').each(function () {
        if (isNotBlank(this.value)) {
            this.value = Format(this.value, format);
        }
        var id = this.id;
        var classList = this.classList;
        laydate.render({
            elem: this,
            format: format,
            trigger: 'click'
            , done: function (value, date, endDate) {
                var currentTime = new Date(value).getTime();
                if (isNotBlank(id) &&id.indexOf("qssj") > -1) {
                    var jssjid = $(this)[0].elem[0].id.replace("qssj", "jssj");
                    var endTime = new Date($('#' + jssjid).val()).getTime();
                    if (endTime < currentTime) {
                        ityzl_SHOW_WARN_LAYER('结束时间不能小于开始时间');
                        $('#' + jssjid).val('');
                        $('.laydate-disabled.layui-this').removeClass('layui-this');
                    }
                    //查封期限默认三年
                    if(id ==="cf-cfqssj") {
                        //获取当前元素的class，如果存在续封不限制，则续封也默认三年
                        var noxflimit = false;
                        if (isNotBlank(classList) && classList.contains("xfnolimit")) {
                            noxflimit = true;
                        }
                        //不为续封的默认三年 lst
                        if ($("#cf-cflx").val() != 5 || noxflimit) {
                            var jssj = DateAdd("d", -1, DateAdd("y", 3, new Date(value)));
                            if (jssj != null) {
                                $("#cf-cfjssj").val(Format(formatChinaTime(jssj), "yyyy-MM-dd"));
                            }
                        }
                    }
                }
                else if (isNotBlank(id) &&id.indexOf("jssj") > -1) {
                    var qssjid = $(this)[0].elem[0].id.replace("jssj", "qssj");
                    var startTime = new Date($('#' + qssjid).val()).getTime();
                    if (currentTime < startTime) {
                        ityzl_SHOW_WARN_LAYER('结束时间不能小于开始时间');
                        $('#' + qssjid).val('');
                        $('.laydate-disabled.layui-this').removeClass('layui-this');
                    }

                }
                //抵押权选择完起始时间后自动选择结束时间
                if (id === "dyaq-zwlxqssj") {
                    if($('.layui-show').length > 0){
                        $(".layui-show #dyaq-zwlxjssj").focus();
                        $(".layui-show #dyaq-zwlxjssj").click();
                    }else {
                        $("#dyaq-zwlxjssj").focus();
                        $("#dyaq-zwlxjssj").click();
                    }
                }
                //监听修改
                var $date = this.elem;
                if (formIds != undefined && isNotBlank(formIds)) {
                    $.each(formIds.split(","), function (index, formID) {
                        if (isNotBlank(formID)) {
                            var $date1 = $("#" + formID).find($date);
                            if ($date1.length > 0) {
                                $date.addClass('bdc-change-input');
                                var text = $($date).parents(".layui-inline").find("label").text();
                                renderChangeTips(text);
                            }
                        }
                    });
                }
            }
        });
        //监听开始时间,结束时间手动修改
        if (isNotBlank(id) &&id.indexOf("qssj") > -1) {
            var $qssj =$(this);
            //监听开始时间
            $qssj.on('change', function () {
                var jssjid = id.replace("qssj", "jssj");
                var currentTime = new Date($qssj.val()).getTime();
                var endTime = new Date($('#' + jssjid).val()).getTime();
                if (endTime < currentTime) {
                    ityzl_SHOW_WARN_LAYER('结束时间不能小于开始时间');
                    $qssj.val('');
                    $('.laydate-disabled.layui-this').removeClass('layui-this');
                }
            });
        }else if (isNotBlank(id) &&id.indexOf("jssj") > -1) {
            var $jssj =$(this);
            //监听开始时间
            $jssj.on('change', function () {
                var qssjid = id.replace("jssj", "qssj");
                var currentTime = new Date($jssj.val()).getTime();
                var startTime = new Date($('#' + qssjid).val()).getTime();
                if (currentTime < startTime) {
                    ityzl_SHOW_WARN_LAYER('结束时间不能小于开始时间');
                    $jssj.val('');
                    $('.laydate-disabled.layui-this').removeClass('layui-this');
                }
            });
        }
    });

}

function format(timestamp) {
    if (!timestamp) {
        return '';
    }

    var time = new Date(timestamp);
    var y = time.getFullYear();
    var m = time.getMonth() + 1;
    var d = time.getDate();
    var h = time.getHours();
    var mm = time.getMinutes();
    var s = time.getSeconds();
    return y + '-' + add0(m) + '-' + add0(d) + ' ' + add0(h) + ':' + add0(mm) + ':' + add0(s);
}

function add0(time) {
    if (time < 10) {
        return '0' + time;
    }
    return time;
}

/**
 * y(年),m(月）,d(日）
 * 日期增加
 */
function DateAdd(interval, number, date) {
    switch (interval) {
        case "y": {
            date.setFullYear(date.getFullYear() + number);
            return date;
            break;
        }

        case "m": {
            date.setMonth(date.getMonth() + number);
            return date;
            break;
        }

        case "d": {
            date.setDate(date.getDate() + number);
            return date;
            break;
        }
        default: {
            return null;
            break;
        }
    }
}

function Format(datetime, fmt) {
    if (isNotBlank(datetime)) {
        if (parseInt(datetime) == datetime) {
            if (datetime.length == 10) {
                datetime = parseInt(datetime) * 1000;
            } else if (datetime.length == 13) {
                datetime = parseInt(datetime);
            }
        }
        datetime = new Date(datetime.replace(/-/g, "/"));
        var o = {
            "M+": datetime.getMonth() + 1,                 //月份
            "d+": datetime.getDate(),                    //日
            "h+": datetime.getHours(),                   //小时
            "m+": datetime.getMinutes(),                 //分
            "s+": datetime.getSeconds(),                 //秒
            "q+": Math.floor((datetime.getMonth() + 3) / 3), //季度
            "S": datetime.getMilliseconds()             //毫秒
        };
        if (/(y+)/.test(fmt))
            fmt = fmt.replace(RegExp.$1, (datetime.getFullYear() + "").substr(4 - RegExp.$1.length));
        for (var k in o)
            if (new RegExp("(" + k + ")").test(fmt))
                fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        return fmt;
    }
}

//格式化中国标准时间
function formatChinaTime(datetime) {
    return datetime.getFullYear() + '-' + (datetime.getMonth() + 1) + '-' + datetime.getDate() + ' ' + datetime.getHours() + ':' + datetime.getMinutes() + ':' + datetime.getSeconds();
}

//获取URL参数
function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (null !== r) {
        if (decodeURI(r[2]) !== "null") {
            return decodeURI(r[2]);
        } else {
            return null
        }
    }
    return null;
}

//获取URL所有参数
function getRequestParam() {
    var url = window.location.search; //获取url中"?"符后的字串
    var requestParam = {};
    if (url.indexOf("?") != -1) {
        var num=url.indexOf("?")
        var str=url.substr(num+1);
        var strs = str.split("&");
        for(var i = 0; i < strs.length; i ++) {
            requestParam[strs[i].split("=")[0]] = decodeURI(strs[i].split("=")[1]);
        }
    }
    return requestParam;
}


//空字符串不包括"(空格)",只特指""
function isNotBlank(object) {
    if (typeof object === "object" && !(object instanceof Array)) {
        var hasProp = false;
        for (var prop in object) {
            hasProp = true;
            break;
        }
        if (hasProp) {
            hasProp = [hasProp];
        } else {
            return false;
        }
        return hasProp;
    }
    return typeof object != "undefined" && object != "";
}

//移除等待背景层
function endAction() {
    $("#waitModalLayer").remove();
}

//获取Token
function getToken(_path, _param, _successfn, _failFn) {
    var _tokenParam = {
        "head": {
            "origin": "2"
        },
        "data": {
            "userName": "web",
            "userPwd": "123456"
        }
    }
    jQuery.support.cors = true;
    $.ajax({
        url: getIP() + "/tokenModel/getToken",
        contentType: "application/json;charset=utf-8",
        type: "post",
        data: JSON.stringify(_tokenParam),
        success: function (data) {
            if (data && data.data && data.data.token) {
                $.cookie("myToken", data['data']['token'], {path: "/"});
                postDataToServer(_path, _param, _successfn, _failFn);
            }
        }
    });
}

//数据交互 _param参数对象：设置noModal = true关闭蒙层，async = true设置同步请求。
function postDataToServer(_path, _param, _successfn, _failfn) {
    var _token = $.cookie("myToken");
    if (!_token) {
        getToken(_path, _param, _successfn, _failfn);
        return;
    }
    if (!_path) {
        return;
    }
    var _url = getIP() + _path;
    !_param.noModal && beginAction();
    jQuery.support.cors = true;
    $.ajax({
        url: _url,
        type: 'post',
        async: _param.async || false,
        contentType: 'application/json;charset=utf-8',
        xhrFields: {withCredentials: true},
        data: JSON.stringify({
            "head": {
                origin: "2",
                sign: "",
                token: _token
            },
            "data": _param
        }),
        success: function (res) {
            endAction();
            if (res.head.code == "0000") {
                _successfn.call(this, res.data, res)
            } else if (_failfn) {
                _failfn.call(this, res);
            } else {
                layer.msg(res.head.msg, {time: 3000});
            }
        },
        error: function (err) {
            endAction();
            console.log(_path, _param, err);
            layer.msg("数据请求失败", {
                time: 1500
            });
        }
    });
}

/**
 * @param object 对象
 * @param key 属性名称
 * @return boolean 存在属性 true  不存在 false
 * @description 判断当前对象是否包含某个属性
 */
function hasProperty(object, key){
    if(!$.isPlainObject(object)){
        return false;
    }
    if($.isEmptyObject(object)){
        return false
    }
    if(undefined == key || null == key || "" == key){
        return false;
    }
    if(object.hasOwnProperty(key)){
        return true;
    }
    return false;
}

/**
 * 打印js
 * @returns {*}
 */
function print(modelUrl, dataUrl, hiddeMode) {
    var fr3Url = "v2|designMode=false|frURL=" + modelUrl
        + "|dataURL=" + dataUrl
        + "|updateUrl=http://oa.gtis.com.cn:80/platform/pluging/update.ini|hiddeMode=" + hiddeMode;

    window.location.href = "eprt://" + fr3Url;
}
//----------------------------------------------------------控件方法-----------------------------------------------------


/**
 * @date 2019.10.28
 * @author yaoyi
 * @description 将18位身份证号转换为15位身份证号
 */
function convertIdCard18To15(idCard){
    if(!isNotBlank(idCard)){
        return idCard;
    }
    if(idCard.length != 18){
        return idCard
    }
    return idCard.substring(0, 6) + idCard.substring(8, 17);
}

/**
 * @date 2019.10.31
 * @author liaoxiang
 * attrs 需要转换大写的key
 * @description 将小写字母转为大写
 */
function toUpperClass(obj,attrs){
    $.each(attrs, function (index, item) {
        if(isNotBlank(obj[item])){
            obj[item] =deleteWhitespace(obj[item], "edge").toUpperCase();
        }
    })
}

/**
 * @date 2020.09.17
 * @author yousiyi
 * class 需要加密的类
 * @description 受理页面电话加密显示(****)
 */
function toEncryptClass(className) {
    var isPhone = /^\d{3}-\d{7,8}|\d{4}-\d{7,8}$/;//电话号码
    var isMob = /^0?1[3|4|5|6|7|8|9][0-9]\d{8}$/;//手机号码

    $.each($('.'+className), function (i) {
        // 状态为不可编辑时进行加密
        if($(this).prop("disabled")){
            var phone = $(this).val(),resultPhone;
            if(isNotBlank(phone)){
                if(isMob.test(phone)){
                    // 手机号替换中间四位
                    resultPhone = phone.replace(/^(\d{3})\d{4}(\d+)/, '$1****$2');
                    $(this).val(resultPhone);
                }else if(isPhone.test(phone)){
                    // 固定电话替换区号后四位
                    var dhArr = phone.split('-');
                    var dh = dhArr[1].replace(/\d{1,4}/, '****');
                    resultPhone = phone.replace(dhArr[1],dh);
                    $(this).val(resultPhone);
                }
                $(this).attr('lay-verify','');
                //修改name属性,防止字段意外保存
                $(this).attr('name',$(this).attr('name')+'jm');
                //修改ID属性,去除原字段验证
                $(this).attr('id',$(this).attr('id')+'jm');
            }
        }
    })
}

//----------------------------------------------------------JS原生扩展---------------------------------------------------
if (!Array.prototype.forEach) {
    Array.prototype.forEach = function forEach(callback, thisArg) {
        var T, k;
        if (this == null) {
            throw new TypeError("this is null or not defined");
        }
        var O = Object(this);
        var len = O.length >>> 0;
        if (typeof callback !== "function") {
            throw new TypeError(callback + " is not a function");
        }
        if (arguments.length > 1) {
            T = thisArg;
        }
        k = 0;

        while (k < len) {

            var kValue;
            if (k in O) {

                kValue = O[k];
                callback.call(T, kValue, k, O);
            }
            k++;
        }
    };
}
if (!Array.prototype.indexOf) {
    Array.prototype.indexOf = function (elt /*, from*/) {
        var len = this.length >>> 0;

        var from = Number(arguments[1]) || 0;
        from = (from < 0)
            ? Math.ceil(from)
            : Math.floor(from);
        if (from < 0)
            from += len;

        for (; from < len; from++) {
            if (from in this && this[from] === elt)
                return from;
        }
        return -1;
    };
}
String.prototype.trim = function () {
    return this.replace(/(^\s*)|(\s*$)/g, "");
}

function isArray(obj) {
    return Object.prototype.toString.call(obj) == "[object Array]";
}
//----------------------------------------------------------浏览器相关方法------------------------------------------------

//---------------------------------------------新增2019/7/5 苑    开始------------------------------------------------------
//数据交互
function getReturnData(_path, _param, _type, _fn, _errorFn, async) {
    if (async === undefined) {
        async = true;
    }
    var _url = getContextPath() + _path;
    $.ajax({
        url: _url,
        type: _type,
        async: async,
        contentType: 'application/json;charset=utf-8',
        data: _param,
        success: function (data) {
            _fn.call(this, data, data);
        },
        error: function (err) {
            _errorFn.call(this, err);
            removeModal();
        }
    });
}

//---------------------------------------------新增2019/7/5 苑    结束------------------------------------------------------

/**
 * 获取URL地址中的参数
 * @param name 参数名称
 */
(function ($) {
    $.getUrlParam = function (name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(reg);
        if (null != r) return unescape(r[2]);
        return null;
    }
})(jQuery);

/**
 * 处理小数计算问题，四舍五入保留两位小数
 * 需要引用math.js
 * @param equation  计算公式
 */
function calculateFloat(equation) {
    var result = math.format(math.evaluate(equation), 14);
    return formatFloat(parseFloat(result));
}

//处理小数问题
function formatFloat(f) {
    if((f + '').indexOf('.') != -1){
        var d, carryD, i,
            ds = f.toString().split('.'),
            len = ds[1].length,
            b0 = '', k = 0;

        if (len > 2) {
            while(k < 2 && ds[1].substring(0, ++k) == '0') {
                b0 += '0';
            }
            d = Number(ds[1].substring(0, 2));
            // 判断保留位数后一位是否需要进1
            carryD = Math.round(Number('0.' + ds[1].substring(2, len)));
            len = (d + carryD).toString().length;
            if (len > 2) {
                return Number(ds[0]) + 1;
            } else if (len == 2) {
                return Number(ds[0] + '.' + (d + carryD));
            }
            return Number(ds[0] + '.' + b0 + (d + carryD));
        }
    }
    return f;
}

/**
 * @param obj
 * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @description js对象属性大写转小写
 * @date : 2019/12/18 11:09
 */
function lowerPropertyNames(obj) {

    if (obj == null) {
        return;
    }
    if (typeof obj != 'object') {
        return;
    }
    if (isArray(obj) && obj.length > 0) {
        // iterate over array obj
        for (var index in obj) {
            lowerPropertyNames(obj[index]);
        }
    } else {
        // iterate over object obj
        var props = Object.keys(obj);
        var propNums = props.length;
        if (propNums == 0) {
            return;
        }
        for (var index = 0; index < propNums; index++) {
            var prop = props[index];
            var prop_lowercase = prop.toLowerCase();
            if (prop_lowercase !== prop) {
                obj[prop_lowercase] = obj[prop];
                delete obj[prop];
            }
            if (typeof obj[prop_lowercase] == 'object') {
                lowerPropertyNames(obj[prop_lowercase]);
            }
        }
    }
    function isArray(o) {
        return typeof o === "object" &&
            Object.prototype.toString.call(o) === "[object Array]";
    }
}

/**
 * 判断对象是否属性是否都为 ''
 * @param obj
 * @returns {boolean}
 */
function isEmptyObject(obj) {
    var flag = true;
    if (obj != undefined && obj != null) {
        for (prop in obj) {
            if (obj[prop] != '') {
                flag = false;
            }
        }
    }
    return flag;
}

/**
 * 警告提示
 * @param msg
 */
function warnMsg(msg) {
    layer.msg('<img src="../../static/lib/bdcui/images/warn-small.png" alt="">' + msg);
}

/**
 * 拼接ur参数
 * @param uri
 * @param par
 * @param par_value
 * @returns {*}
 */
function changeURLPar(uri, par, par_value) {
    try {
        var pattern = par + '=([^&]*)';
        var replaceText = par + '=' + par_value;
        if (uri.match(pattern)) {//如果连接中带这个参数
            var tmp = '/\\' + par + '=[^&]*/';
            tmp = uri.replace(eval(tmp), replaceText);
            return (tmp);
        }
        else {
            if (uri.match('[\?]')) {//如果链接中不带这个参数但是有其他参数
                return uri + '&' + replaceText;
            }
            else {//如果链接中没有带任何参数
                return uri + '?' + replaceText;
            }
        }
        return uri + '\n' + par + '\n' + par_value;
    }catch (e) {
        return uri
    }
}

/**
 * 保存日志信息
 * @param data 需要保存的日志数据
 */
function saveLogInfo(data) {
    $.ajax({
        url: "/realestate-accept-ui/rest/v1.0/log/info",
        type: "POST",
        data: JSON.stringify(data),
        contentType: 'application/json',
        success: function (key) {
        },
        error: function () {
            console.debug("保存日志出错！");
        }
    });
}

function getSfzfByXmid(xmid){
    var sfzf = null;
    $.ajax({
        url: getContextPath()+ "/slym/ql/fwkg/sfzf/"+xmid,
        type: "GET",
        async: false,
        success: function (data) {
            if(data !== ""){
                sfzf = data;
            }
        },
        error: function (e) {
        }
    });
    return sfzf;
}

function saveSfzfByXmid(sfzf,xmid){
    $.ajax({
        type: 'PUT',
        url: '/realestate-accept-ui/slym/ql/saveSfzfByXmid',
        contentType: 'application/json',
        dataType: "json",
        data: JSON.stringify({sfzf: sfzf,id: xmid}),
        success: function (data) {

        }
    });
}

//字典项名称转代码
function changeMcToDm(mc, zdList) {
    var dm = "";
    if (isNotBlank(zdList)) {
        for (var i = 0; i < zdList.length; i++) {
            var zd = zdList[i];
            if (mc == zd.MC) {
                dm = zd.DM;
                break;
            }
        }
    }
    return dm;

}

// 设置列表高度
function setHeight(searchHeight) {
    if (isNullOrEmpty(searchHeight)) {
        searchHeight = 131;
    }
    $('.layui-table-tool-self').css('right',$('.bdc-export-tools').width()+ 17 + 'px');

    if($('.bdc-table-box .layui-table-main>.layui-table').height() == 0){
        $('.layui-table-body .layui-none').html('<img src="../../static/lib/registerui/image/table-none.png" alt="">无数据');
    }else {
        $('.layui-table-body').height($('.bdc-table-box').height() - searchHeight);
        $('.layui-table-fixed .layui-table-body').height($('.bdc-table-box').height() - searchHeight - 17);
    }
}


function reverseTableCell(reverseList) {
    var getTd = $('.layui-table-view .layui-table td');
    for(var i = 0; i < getTd.length; i++){
        reverseList.forEach(function (v) {
            if($(getTd[i]).attr('data-field') == v){
                var getTdCell = $(getTd[i]).find('.layui-table-cell');
                getTdCell.css('direction','rtl');
                if(getTdCell.find('span').length == 0) {
                    var tdHtml = reverseString(getTdCell.html());
                    console.log(tdHtml);
                    getTdCell.html('<span class="bdc-table-date">'+ tdHtml +'</span>');
                }
            }
        });
    }
}

function reverseString(str) {
    if (!isNotBlank(str)) {
        return str;
    }
    str = str.replace(/&lt;/g, '>');
    str = str.replace(/&gt;/g, '<');
    var RexStr = /\（|\）|\(|\)|\【|\】|\【|\】|\[|\]|\[|\]/g;
    str = str.replace(RexStr, function (MatchStr) {
        switch (MatchStr) {
            case "(":
                return ")";
                break;
            case ")":
                return "(";
                break;
            case "（":
                return '）';
                break;
            case "）":
                return "（";
                break;
            case "[":
                return "]";
                break;
            case "]":
                return "[";
                break;
            case "【":
                return "】";
                break;
            case "】":
                return "【";
                break;
        }
    });
    return str.split("").reverse().join("");
}

/*
* 从权籍获取不动产单元的状态
* */
function getBdcdyZt(bdcdyh,qjgldm) {
    var bdcdyZt = '<p class="bdc-table-state bdc-ql-state">';
    $.ajax({
        url: getContextPath() + "/bdcdyh/queryBdcdyZt?bdcdyh=" + bdcdyh +"&qjgldm="+qjgldm,
        type: 'GET',
        dataType: 'json',
        async: false,
        success: function (data) {
            if (data !== null) {
                if (data.yg) {
                    bdcdyZt += '<span class="bdc-yg">已预告</span>';
                } else if (data.zdZtDTO != null && data.zdZtDTO.yg) {
                    bdcdyZt += '<span class="bdc-yg">宗地预告</span>';
                }
                if (data.ycf) {
                    bdcdyZt += '<span class="bdc-ycf">预查封</span>';
                } else if (data.zdZtDTO != null && data.zdZtDTO.ycf) {
                    bdcdyZt += '<span class="bdc-ycf">宗地预查封</span>';
                }
                if (data.ydya) {
                    bdcdyZt += '<span class="bdc-ydy">预抵押</span>';
                } else if (data.zdZtDTO != null && data.zdZtDTO.ydya) {
                    bdcdyZt += '<span class="bdc-ydy">宗地预抵押</span>';
                }
                if (data.cf) {
                    bdcdyZt += '<span class="bdc-cf">查封</span>';
                } else if (data.zdZtDTO != null && data.zdZtDTO.cf) {
                    bdcdyZt += '<span class="bdc-cf">宗地查封</span>';
                }
                if (data.dya) {
                    bdcdyZt += '<span class="bdc-dya">抵押</span>';
                } else if (data.zdZtDTO != null && data.zdZtDTO.dya) {
                    bdcdyZt += '<span class="bdc-dya">宗地抵押</span>';
                }
                if (data.yy) {
                    bdcdyZt += '<span class="bdc-yy">异议</span>';
                } else if (data.zdZtDTO != null && data.zdZtDTO.yy) {
                    bdcdyZt += '<span class="bdc-yy">宗地异议</span>';
                }
                if (data.sd) {
                    bdcdyZt += '<span class="bdc-sd">锁定</span>';
                } else if (data.zdZtDTO != null && data.zdZtDTO.sd) {
                    bdcdyZt += '<span class="bdc-sd">宗地锁定</span>';
                }
                if (data.dyi) {
                    bdcdyZt += '<span class="bdc-dy">地役</span>';
                } else if (data.zdZtDTO != null && data.zdZtDTO.dyi) {
                    bdcdyZt += '<span class="bdc-dy">宗地地役</span>';
                }
                if (data.zjgcdy) {
                    bdcdyZt += '<span class="bdc-zjgcdy">在建工程抵押</span>';
                }
                if (data.jzq) {
                    bdcdyZt += '<span class="bdc-jzq">居住</span>';
                }
                if (data.fwcq) {
                    bdcdyZt += '<span class="bdc-fwcq">房屋拆迁</span>';
                }
                if (data.ks) {
                    bdcdyZt += '<span class="bdc-ks">可售</span>';
                }
                if (data.ys) {
                    bdcdyZt += '<span class="bdc-ys">已售</span>';
                }
                if (data.xjspfks) {
                    bdcdyZt += '<span class="bdc-xjspfks">新建商品房可售</span>';
                }
                if (data.xjspfys) {
                    bdcdyZt += '<span class="bdc-xjspfys">新建商品房已售</span>';
                }
                if (data.clfks) {
                    bdcdyZt += '<span class="bdc-clfks">存量房可售</span>';
                }
                if (data.clfys) {
                    bdcdyZt += '<span class="bdc-clfys">存量房已售</span>';
                }

                if (zcztbs){
                    if (bdcdyZt.indexOf("span") < 0 && !data.dj) {
                        bdcdyZt += '<span class="bdc-zjgcdy">正常</span>';
                    } else if (bdcdyZt.indexOf("span") < 0 && (data.dj)) {
                        bdcdyZt += '<span class="bdc-normal">正常</span>';
                    }
                }else {
                    if (bdcdyZt.indexOf("span") < 0){
                        bdcdyZt += '<span class="bdc-normal">正常</span>';
                    }
                }

                bdcdyZt += '</p>';
            }
        }, error: function (xhr, status, error) {
        }
    });
    return bdcdyZt
}

/*
* 获取产权证状态
* */
function getCqzZt(bdcdyh, qllx,zsxmid,qjgldm) {
    var bdcdyZt = '<p class="bdc-table-state bdc-ql-state">';
    $.ajax({
        url: getContextPath() + "/bdcdyh/queryBdcqzZt?bdcdyh=" + bdcdyh + "&qllx=" + qllx+"&zsxmid="+zsxmid +"&qjgldm="+qjgldm,
        type: 'GET',
        dataType: 'json',
        async: false,
        success: function (data) {
            if (data !== null) {
                if (data.yg) {
                    bdcdyZt += '<span class="bdc-yg">已预告</span>';
                }
                if (data.ycf) {
                    bdcdyZt += '<span class="bdc-ycf">预查封</span>';
                }
                if (data.ydya) {
                    bdcdyZt += '<span class="bdc-ydy">预抵押</span>';
                }
                if (data.cf) {
                    bdcdyZt += '<span class="bdc-cf">查封</span>';
                }
                if (data.dya) {
                    bdcdyZt += '<span class="bdc-dya">抵押</span>';
                }
                if (data.yy) {
                    bdcdyZt += '<span class="bdc-yy">异议</span>';
                }
                if (data.sd) {
                    bdcdyZt += '<span class="bdc-sd">锁定</span>';
                }
                if (data.dyi) {
                    bdcdyZt += '<span class="bdc-dy">地役</span>';
                }
                if (data.zjgcdy) {
                    bdcdyZt += '<span class="bdc-zjgcdy">在建工程抵押</span>';
                }
                if (data.jzq) {
                    bdcdyZt += '<span class="bdc-jzq">居住</span>';
                }
                if (data.fwcq) {
                    bdcdyZt += '<span class="bdc-fwcq">房屋拆迁</span>';
                }
                if (data.ks) {
                    bdcdyZt += '<span class="bdc-ks">可售</span>';
                }
                if (data.ys) {
                    bdcdyZt += '<span class="bdc-ys">已售</span>';
                }
                if (data.xjspfks) {
                    bdcdyZt += '<span class="bdc-xjspfks">新建商品房可售</span>';
                }
                if (data.xjspfys) {
                    bdcdyZt += '<span class="bdc-xjspfys">新建商品房已售</span>';
                }
                if (data.clfks) {
                    bdcdyZt += '<span class="bdc-clfks">存量房可售</span>';
                }
                if (data.clfys) {
                    bdcdyZt += '<span class="bdc-clfys" >存量房已售</span>';
                }
                if (!data.yg && !data.ycf && !data.ydya && !data.cf && !data.dya && !data.yy
                    && !data.sd && !data.dyi && !data.zjgcdy && !data.ks && !data.ys && !data.xjspfks && !data.xjspfys
                    && !data.clfks && !data.clfys && !data.jzq) {
                    bdcdyZt += '<span class="bdc-normal">正常</span>';
                }
                bdcdyZt += '</p>';
            } else {
                bdcdyZt += '<span class="bdc-normal">正常</span>';
            }
        }, error: function (xhr, status, error) {
        }
    });
    return bdcdyZt
}

/**
 * 获取逻辑幢状态
 */
function getLjzZt(fwDcbIndex, bdcdyh){
    var ljzZt = '<p class="bdc-table-state bdc-ql-state">';
    $.ajax({
        url: getContextPath() + "/bdcdyh/queryLjzZt?fwDcbIndex=" + fwDcbIndex +"&bdcdyh=" + bdcdyh,
        type: 'GET',
        dataType: 'json',
        async: false,
        success: function (data) {
            if (data !== null) {
                if (data.lhdycs) {
                    ljzZt += '<span class="bdc-lhdya">量化抵押</span>';
                }
                if (!data.lhdycs) {
                    ljzZt += '<span class="bdc-normal">正常</span>';
                }
                ljzZt += '</p>';
            } else {
                ljzZt += '<span class="bdc-normal">正常</span>';
            }
        }, error: function (xhr, status, error) {
        }
    });
    return ljzZt;
}
/**
 * 去除小数点前的0
 * 例： 00.1021 替换为  0.1021
 *     10.2021 替换为  10.2021
 */
function replaceBeforePointZero(data){
    if(isNotBlank(data)){
        data = data.replace(/^0+\./g, "0.");
    }
    return data;
}

/**
 * IE10以下版本不支持 new Map(), 自定义 Map 数据结构使用
 */
function Map() {
    this.keys = [];
    this.data = {};

    this.set = function(key, value) {
        if (this.data[key] == null) {
            if (this.keys.indexOf(key) == -1) {
                this.keys.push(key);
            }
        }
        this.data[key] = value;
    }

    this.get = function(key) {
        return this.data[key];
    }
}

//查看地籍调查表
function showDjdcbxx(bdcdyh,qjgldm) {
    layerCommonOpenFull('/building-ui/djdcb/initview?bdcdyh=' + bdcdyh +"&qjgldm="+qjgldm,"地籍调查表",'1300px','600px');
}

/*
* 展现不动产单元详情登记历史关系
*/
function showLsgx(bdcdyh) {
    var url = "/realestate-register-ui/view/lsgx/bdcdyDjLsgx.html?bdcdyh=" + bdcdyh;
    layerCommonOpenFull(url, "登记历史关系", '1150px', '600px');
}
/*
* 展现不动产单元详情登记历史关系
*/
function showLsgxNew(bdcdyh,tzym) {
    var url = "/realestate-register-ui/view/lsgx/bdcdyDjLsgxNew.html?bdcdyh=" + bdcdyh+"&tzym="+tzym;
    layerCommonOpenFull(url, "登记历史关系", '1150px', '600px');
}

//对比权籍按钮
function openDbqj(xmid,bdcdyh) {
    var url = getContextPath()+"/view/synlpb/contractLpb.html?xmid=" + xmid + "&formStateId=" + formStateId + "&readOnly=" + readOnly + "&bdcdyh=" + bdcdyh;
    layerOpenFullReload(url, "对比信息");
}

//字段增加模糊类型
function cxtjAddMhlx(cxObj){
    // 深拷贝一个cxObj备份，用于作为查询参数
    var cxObjBak = JSON.parse(JSON.stringify(cxObj));

    // 获取查询方式：精确查询(1)和模糊查询(4)
    var cxfs = cxObj.cxfs;
    if (isNotBlank(cxfs)) {
        // 查询条件精确或者模糊
        Object.keys(cxObjBak).forEach(function (key) {
            cxObj[key + 'mh'] = cxfs;
        });
    }
}

//字典项代码转名称
function changeDmToMc(dm, zdList) {
    var mc = "";
    if (isNotBlank(zdList)) {
        for (var i = 0; i < zdList.length; i++) {
            var zd = zdList[i];
            if (dm == zd.DM) {
                mc = zd.MC;
                break;
            }
        }
    }
    return mc;

}

/*
* 系统机构配置
* */

function listBdcXtJg(jglb) {
    var jgList = [];
    //机关类型
    $.ajax({
        url: getContextPath() + "/slym/qlr/bdcxtjg",
        type: 'GET',
        dataType: 'json',
        data: {jglb: jglb},
        async: false,
        success: function (data) {
            jgList = data
        }
    });
    return jgList;
}


/**
 * 判断字符串是否为空
 *
 * @param str  目标字符串
 * @returns {boolean}  为空：true ; 不为空：false
 */
function isNullOrEmpty(str) {
    if (!str || "" == str || "null" == str || undefined == str || "undefined" == str) {
        return true;
    }

    return false;
}

/*
* 点击图片放大功能
* */
function imgShow(outerdiv, innerdiv, bigimg, _this) {
    var src = _this.attr("src");//获取当前点击的pimg元素中的src属性
    $(bigimg).attr("src", src);//设置#bigimg元素的src属性

    /*获取当前点击图片的真实大小，并显示弹出层及大图*/
    $("<img/>").attr("src", src).load(function () {
        var windowW = $(window).width();//获取当前窗口宽度
        var windowH = $(window).height();//获取当前窗口高度
        var realWidth = this.width;//获取图片真实宽度
        var realHeight = this.height;//获取图片真实高度
        var imgWidth, imgHeight;
        var scale = 0.8;//缩放尺寸，当图片真实宽度和高度大于窗口宽度和高度时进行缩放

        if (realHeight > windowH * scale) {//判断图片高度
            imgHeight = windowH * scale;//如大于窗口高度，图片高度进行缩放
            imgWidth = imgHeight / realHeight * realWidth;//等比例缩放宽度
            if (imgWidth > windowW * scale) {//如宽度扔大于窗口宽度
                imgWidth = windowW * scale;//再对宽度进行缩放
            }
        } else if (realWidth > windowW * scale) {//如图片高度合适，判断图片宽度
            imgWidth = windowW * scale;//如大于窗口宽度，图片宽度进行缩放
            imgHeight = imgWidth / realWidth * realHeight;//等比例缩放高度
        } else {//如果图片真实高度和宽度都符合要求，高宽不变
            imgHeight = windowH * scale;//如大于窗口高度，图片高度进行缩放
            imgWidth = imgHeight / realHeight * realWidth;//等比例缩放宽度
            if (imgWidth > windowW * scale) {//如宽度扔大于窗口宽度
                imgWidth = windowW * scale;//再对宽度进行缩放
            }
        }
        $(bigimg).css("width", imgWidth);//以最终的宽度对图片缩放

        var w = (windowW - imgWidth) / 2;//计算图片与窗口左边距
        var h = (windowH - imgHeight) / 2;//计算图片与窗口上边距
        $(innerdiv).css({"top": h, "left": w});//设置#innerdiv的top和left属性
        $(outerdiv).fadeIn("fast");//淡入显示#outerdiv及.pimg
    });

    $(outerdiv).click(function () {//再次点击淡出消失弹出层
        $(this).fadeOut("fast");
    });
}

/**
 * bdcdyh 不动产单元号
 * 验证是否为虚拟单元号(定着物特征码前5位为00000，且非海域)
 */
function checkXndyh(bdcdyh,qllx) {
    if (isNotBlank(bdcdyh) && bdcdyh.length === 28) {
        var zdzhsxh = bdcdyh.substr(6, 6);
        var qslxdm =bdcdyh.substr(13, 1);
        //海域特征码
        var hyArr = ["G", "H"];
        if (zdzhsxh === "000000" && hyArr.indexOf(qslxdm) < 0) {
            return true;
        } else {
            return false;
        }
    } else {
        return false;
    }
}

//调用评价器方法
function commonPj(gzlslid,taskid){
    addModel();
    var slbh =$("#sjbh").val();
    getReturnData("/pjq/commonpj",{gzlslid:gzlslid,taskid:taskid},"GET",function (data){
        if(data.pjqbb ==="MK_L"){
            removeModal();
            window.open(encodeURI(data.url));

        }else if(data.pjqbb ==="MK_N"){
            removeModal();
            if (data.pjjg && data.pjjg.code == "1") {
                ityzl_SHOW_SUCCESS_LAYER("评价成功");
            } else {
                ityzl_SHOW_WARN_LAYER(data.pjjg.message);
            }
        } else if(data.pjqbb ==="QDWS"){
            // var url = data.url+"&s="+new Date().getTime();
            // getReturnData("/pjq/getPjqResponse",{url:url},"GET",function (res){
            //     removeModal();
            //     var resData = JSON.parse(res);
            //     if(resData.success ==true){
            //         //成功
            //         ityzl_SHOW_SUCCESS_LAYER("评价成功");
            //     }else {
            //         ityzl_SHOW_WARN_LAYER(resData.message);
            //     }
            // });

            // $.getJSON(data.url+"&s="+new Date().getTime(),function(qdwsData){
            //     console.info(qdwsData);
            // });
            // 淮安修改评价器评价功能，解决跨域问题
            $.ajax({
                url: encodeURI(data.url+"&s="+new Date().getTime()),
                type: "GET",
                contentType:"text/plain",
                async: false,
                success: function (qdwsData) {
                    removeModal();
                    var qdwsDataObj = JSON.parse(qdwsData);
                    if(qdwsDataObj.success ==true){
                        //成功
                        ityzl_SHOW_SUCCESS_LAYER("评价成功");
                        savePjjg(data);
                    }else {
                        ityzl_SHOW_WARN_LAYER(qdwsDataObj.message);
                    }
                },error: function (xhr, status, error) {
                    console.log(xhr);
                    delAjaxErrorMsg(xhr);
                }
            });
        }
        commonSaveDetailLog("PJQPJ", "评价器评价操作",slbh,{"PJNR": JSON.stringify(data)});

    },function (error){
        delAjaxErrorMsg(error);
    });
}

//保存评价结果
function savePjjg(pjdata){
    getReturnData("/pjq/pjjl", JSON.stringify(pjdata), "POST", function (data) {
        removeModal();
    }, function (xhr) {
        delAjaxErrorMsg(xhr);
    }, false)
}


//人证对比
function commonRzdb(qlrmc,zjh,processInsId){
    addModel();
    var slbh =$("#sjbh").val();
    var rzdbResult ={};
    getReturnData("/pjq/commonrzdb",{gzlslid:processInsId,qlrmc:qlrmc,qlrzjh:zjh},"GET",function (result){
        if(result.rzdbbb ==="MK_L"){
            removeModal();
            window.open(encodeURI(result.url));
        }else if(result.rzdbbb ==="MK_N"){
            removeModal();
            rzdbResult =result;
            if (result &&result.mkPjqDTO&& result.mkPjqDTO.code == "1") {
                if (isNullOrEmpty(result.mkPjqDTO.data.status)){
                    //连云港的摩科返回，没有data.status,data.reason
                    ityzl_SHOW_SUCCESS_LAYER("姓名:" + result.mkPjqDTO.data.name + "身份证号:" + result.mkPjqDTO.data.cardNum + "比对成功");
                }else{
                    if (result.mkPjqDTO.data.status == "0") {
                        showAlertDialog("姓名:" + result.mkPjqDTO.data.name + "身份证号:" + result.mkPjqDTO.data.cardNum + result.mkPjqDTO.data.reason);
                    } else {
                        ityzl_SHOW_WARN_LAYER(result.mkPjqDTO.data.reason)
                    }
                }
            } else if(result &&result.mkPjqDTO &&isNotBlank(result.mkPjqDTO.message)) {
                ityzl_SHOW_WARN_LAYER(result.mkPjqDTO.message);
            }
        } else if(result.rzdbbb ==="QDWS"){
            // var qdwsData={
            //     "data": {
            //         "name": "王一民",
            //         "sex": "男",
            //         "nation": "汉",
            //         "birth": "1990年12月12日",
            //         "address": "南京市建邺区奥体大街71号",
            //         "idNo": "320115199012121214",
            //         "police": "南京市公安局建邺分局",
            //         "validPeriod": "2015.11.29-2025.11.29",
            //         "photoBase64": "xxxxxxxxxxxx……",
            //         "cameraPhotoBase64": "xxxxxxxxxxxx……",
            //         "serviceId": "123",
            //         "score": 0.9251431,
            //         "result": 1
            //     },
            //     "message": "人证核验结束",
            //     "success": true
            // };
            // getReturnData("/pjq/getPjqResponse",{url:result.url},"GET",function (res){
            //     removeModal();
            //     var qdwsData = JSON.parse(res);
            //     if(qdwsData.success ==true){
            //         if(qdwsData.data.result ==1){
            //             ityzl_SHOW_SUCCESS_LAYER("比对成功");
            //             uploadFile("data:image/jpg;base64,"+ qdwsData.data.cameraPhotoBase64,processInsId,"人证对比结果",qdwsData.data.idNo+"人证对比结果",".jpg")
            //         }else{
            //             ityzl_SHOW_WARN_LAYER("比对失败");
            //         }
            //     }else{
            //         ityzl_SHOW_WARN_LAYER(qdwsData.message);
            //     }
            // });
            // 淮安修改评价器人脸比对功能，解决跨域问题
            $.ajax({
                url: encodeURI(result.url+"&s="+new Date().getTime()),
                type: "GET",
                contentType:"text/plain",
                async: false,
                success: function (qdwsData) {
                    removeModal();
                    var qdwsDataObj = JSON.parse(qdwsData);
                    if(qdwsDataObj.success == true){
                        ityzl_SHOW_SUCCESS_LAYER("比对成功");
                        // 淮安返回报文中没有附件相关信息，暂时不处理
                        // uploadFile("data:image/jpg;base64,"+ qdwsDataObj.data.cameraPhotoBase64, processInsId,"人证对比结果",qdwsDataObj.data.idNo+"人证对比结果",".jpg")
                    } else {
                        ityzl_SHOW_WARN_LAYER("比对失败，错误信息：" + qdwsDataObj.message);
                    }
                },error: function (xhr, status, error) {
                    console.log(xhr);
                    delAjaxErrorMsg(xhr);
                }
            });
        }
        commonSaveDetailLog("rzdb", "人证对比",slbh,{"RZDBNR": JSON.stringify(rzdbResult)});

    },function (error){
        delAjaxErrorMsg(error);
    });

}

//上传人证对比结果
function uploadFile(base64,gzlslid,wjjmc,fjmc,wjhz){
    $.ajax({
        url: getContextPath()+"/slym/sjcl/upload/base64",
        type: "POST",
        data: JSON.stringify({"gzlslid": gzlslid, "base64str": base64,foldName:wjjmc,pdffjmc:fjmc,fileSuffix:wjhz}),
        contentType: 'application/json',
        dataType: "json",
        success: function (data) {
        },
        error: function (xhr, status, error) {
            removeModal();
            ityzl_SHOW_WARN_LAYER("上传附件失败");
        }
    });

}

// 获取比对结果
function hqdbjg(processInsId){
    var slbh = $("#sjbh").val();
    addModel();
    getReturnData('/slPrint/rzdb',{gzlslid:processInsId, slbh: slbh},'GET',function (data) {
        removeModal();
        ityzl_SHOW_SUCCESS_LAYER("生成比对文件成功。");
    },function (xhr) {
        removeModal();
        delAjaxErrorMsg(xhr);
    });
}

// 保存记录操作信息
function commonSaveDetailLog(logType, viewTypeName,slbh,data){
    var json = JSON.parse(JSON.stringify(data));
    json.logType = logType;
    json.viewTypeName = viewTypeName;
    json.viewType = "accept-ui";
    json.slbh = slbh;
    saveLogInfo(json);
}

//关联证书
function glzs(selectXmids) {
    sessionStorage.removeItem('plwlzs' + processInsId);
    //获取工作流定义id，jbxxid，xmids
    var cs = getGlzsData(processInsId,selectXmids);
    if (cs.xmids.length === 0) {
        showAlertDialog("未选择数据");
    } else {
        setTimeout(function () {
            //打开前先清除缓存数据
            sessionStorage.removeItem('wlzs_xmids' + cs.jbxxid);
            //数据过多,存入缓存
            sessionStorage.setItem('wlzs_xmids' + cs.jbxxid, cs.xmids);
            //外联证书
            var url = '/realestate-accept-ui/view/query/selectBdcdyh.html?processDefKey=' + cs.gzldyid + '&jbxxid=' + cs.jbxxid + '&slbh=' + cs.slbh + "&xztztype=wlzs"+"&ymbz=slym";
            var index = layer.open({
                type: 2,
                title: "外联证书",
                area: ['960px', '550px'],
                fixed: false, //不固定
                maxmin: true, //开启最大化最小化按钮
                content: url
                , cancel: function () {
                    sessionStorage.removeItem('wlzs_xmids' + cs.jbxxid);
                }
            });
            layer.full(index);
        }, 150);
    }

}

function getGlzsData(gzlslid,selectXmids) {
    var result = {};
    getReturnData("/slym/xm/lcjbxx", {gzlslid: gzlslid,selectXmids:selectXmids}, "GET", function (data) {
        result = data;
        //如果存在勾选的数据，xmids 优先使用勾选的数据---批量流程
        var xmids = sessionStorage.getItem("plwlzs" + gzlslid);
        if (isNotBlank(xmids)) {
            result.xmids = xmids;
        }
        jbxxid = data.jbxxid
    }, function (xhr) {
        delAjaxErrorMsg(xhr)
    }, false);
    return result;
}

//外联证书回调函数
function wlzsCallback(ymbz) {
    //清空缓存
    sessionStorage.removeItem('wlzs_xmids' + jbxxid);
    ityzl_SHOW_SUCCESS_LAYER("添加成功");
    //受理页面
    if(ymbz ==="slym"){
        if (typeof (eval(refreshQlxx)) == "function") {
            addModel();
            refreshQlxx();
        }
        if (typeof (eval(reloadPlQlxx)) == "function") {
            reloadPlQlxx($(".layui-show").data('xmid'), $(".layui-show"));
        }else if (typeof (eval(loadPlQlxx)) == "function") {
            addModel();
            loadPlQlxx();
        }

    }
}

/**
 *根据模块管理中的元素配置设置权限
 * @param moduleCode 资源名
 */
function setElementAttrByModuleAuthority(moduleCode,tabId,resourceName,tsbs) {
    $.ajax({
        url: "/realestate-accept-ui/formCenter/moduleAuthority/" + moduleCode,
        type: 'GET',
        dataType: 'json',
        cache: false,
        async: false,
        success: function (data) {
            var available = 'false';
            if (data && !isEmptyObject(data)) {
                for (var i in data) {
                    id = i;
                    attr = data[i];
                    var element = null;
                    if (isNotBlank(tabId)) {
                        //tabId有值时，只获取对应tab的元素
                        var $element = $("#" + tabId).find("#" + id);
                        if ($element.length > 0) {
                            element = $element[0];
                        }
                    } else {
                        element = document.getElementById(id);
                    }
                    if (element !== null) {
                        //收费信息页面分开权利人和义务人权限控制
                        if(resourceName ==="sfxx" && (isNotBlank(tsbs)) && tsbs === "bdcSfxxTable") {
                            // 收费项目分开权利人义务人控制，根据names判断
                            //找到当前设置的属性值
                            var sfxmattr = element.getAttribute("attr");
                            if(isNotBlank(sfxmattr) &&(attrs.indexOf(sfxmattr) > -1)) {
                                //根据这个属性值找到所有相同属性的
                                element = getAttrElement("attr",sfxmattr);
                                if (element.length > 0) {
                                    for (var j = element.length -1; j >= 0; j--) {
                                        setElementAttrModuleAuthority(attr, element[j]);
                                    }
                                }
                            } else {

                            }
                        } else {
                            //页面所有输入框按照name去增加属性，其他按照id
                            if ((element.tagName === "INPUT" || element.tagName === "SELECT" || element.tagName === "TEXTAREA") && !isNotBlank(tabId) && element.type !== "radio") {
                                if (element.id != "dyhth" && element.id != "jyhth" && !(unNames.indexOf(element.id) > -1)) {
                                    //控制页面表格字段存在相同ID下根据name增加属性
                                    if (element.name !== '') {
                                        element = document.getElementsByName(element.name);
                                        if (element.length > 0) {
                                            for (var k = element.length - 1; k >= 0; k--) {
                                                setElementAttrModuleAuthority(attr, element[k]);
                                            }
                                        }
                                    }
                                } else {
                                    element = document.getElementById(element.id);
                                    if (element && $(element) && $(element).length > 0) {
                                        setElementAttrModuleAuthority(attr, $(element)[0]);
                                    }
                                }

                            } else if (isNotBlank(element.name) && (names.indexOf(element.name) > -1 || element.type === "button" || element.tagName === "A" || element.type === "radio")) {
                                //控制页面表格字段存在相同ID下根据name增加属性
                                element = document.getElementsByName(element.name);
                                if (element.length > 0) {
                                    for (var j = element.length - 1; j >= 0; j--) {
                                        setElementAttrModuleAuthority(attr, element[j]);
                                    }
                                }
                            } else {
                                //1.隐藏2.只读3.必填 4.可用 5.有值只读,无值必填
                                setElementAttrModuleAuthority(attr, element);
                            }
                        }
                    }
                }
            }
            if (available === 'false') {
                disabledAddFa();
            }
        }, error: function (xhr, status, error) {
            failMsg("页面权限请求设置失败，请联系管理员！");
        }
    });
}

function setElementAttrModuleAuthority(attr, element){
    if (element !== null) {
        //1.隐藏2.只读3.必填
        if (attr === "hidden") {
            if (element.type === "button" || element.type === "submit" || element.tagName === "A" || element.tagName === "SPAN" || element.tagName === "LI" || element.tagName === "DIV") {
                element.setAttribute("style", "display:none;")
            } else if (element.type === "textarea") {
                $(element.parentElement.parentElement).remove();
            } else {
                if (isNotBlank(element.className) && element.className.indexOf("jyxx") > 0) {
                    //交易字段设置隐藏即移除字段（因为只是暂时性字段，后续都移到交易信息tab标签页）
                    $(element).parents(".layui-inline").remove();
                } else {
                    //隐藏输入框时,直接移除
                    //标签为td,移除对应的th
                    if(element.parentElement.parentElement != null &&element.parentElement.parentElement.tagName ==="TD"){
                        var tdElement =element.parentElement.parentElement;
                        var thName =element.name +"TH";
                        var thElement =$(tdElement).parents("table").find('th[name="' + thName + '"]');
                        //标签为td,移除对应的th
                        $(thElement).remove();

                    }
                    $(element.parentElement.parentElement).remove();
                }
            }
        } else if (attr === "readonly") {
            element.setAttribute("disabled", "off");
            if (element.type === "button" || element.type === "submit") {
                element.classList.add("bdc-prohibit-btn");
                element.classList.remove('bdc-major-btn');
                element.classList.remove('bdc-delete-btn');

            } else if (element.type === "checkbox" || element.type === "radio") {
                e = document.getElementsByName(element.name);
                if (e.length > 0) {
                    for (var j = 0; j < e.length; j++) {
                        e[j].setAttribute("disabled", "off");
                    }
                }
            } else if (element.tagName === "A" || element.tagName === "SPAN") {
                element.removeAttribute("onclick");
                if (element.id === "next") {
                    element.classList.add("bdc-prohibit-btn");
                }
                //重新生成按钮不可点击
                if(isNotBlank(element.id) &&(element.id.indexOf("resetqlqtzk") >-1 ||element.id.indexOf("resetfj") >-1)){
                    element.classList.remove('resetqlqtzk');
                    element.classList.remove('resetfj');
                }
            }
        } else if (attr === "required") {
            var attrVal = element.getAttribute("lay-verify");
            //添加必填验证
            if (!isNotBlank(attrVal)) {
                element.setAttribute('lay-verify', 'required');
            } else if (attrVal.indexOf("required") < 0) {
                element.setAttribute('lay-verify', attrVal + "|required");
            }
            var $inline =$(element).parents(".layui-inline");
            if($inline.length >0) {
                addRequiredSpan($inline);
            }else if($(element).parents(".change-textarea-margin").length > 0){
                addRequiredSpan($(element).parents(".change-textarea-margin"));
            } else{
                var $parent =$(element).parent();
                if($parent.length >0) {
                    $parent.addClass("bdc-not-null");
                }
            }
            // dom元素为 当前input上层的div 例： .layui-inline元素
            function addRequiredSpan(dom){
                var requiredArr = $(dom).find(".required-span");
                if (requiredArr.length === 0) {
                    $(dom).addClass("bdc-not-null");
                    $(dom).find("label").prepend("<span class=\"required-span\"><sub>*</sub></span>");
                }
            }
        } else if (attr === "available") {
            available = 'true';
            //去除隐藏不可编辑属性
            if (element.type === "button" || element.type === "submit" || element.tagName === "A" || element.tagName === "SPAN" || element.tagName === "LI" || element.tagName === "DIV") {
                element.removeAttribute("style", "display:none;");
            } else if (element.parentElement != null && element.parentElement.parentElement != null) {
                element.parentElement.parentElement.removeAttribute("style", "display:none;");
                element.removeAttribute("disabled", "off");
            }
        }
    }
}


//改变必填项背景色
function changeBtxbjs() {
    setTimeout(function () {
        if (isNotBlank(btxbjs)) {
            //所有输入框背景色设置为白色
            //$('.layui-input').attr('style', 'background-color:#fff')
            var elemList = $('.bdc-not-full-bg .bdc-not-null input');
            if (isNotBlank(elemList)) {
                for (var i = 0; i < elemList.length; i++) {
                    var parentElem = $(elemList[i]).parents(".layui-input-inline");
                    if (isNotBlank(parentElem)) {
                        var elemClass = $(elemList[i]).parents(".layui-input-inline")[0].classList;
                        //设置了改变记录的背景色的元素不再设置为必填背景色
                        if (elemClass.contains("bdc-change-input")) {
                            //移除当前inpu本身的style
                            $(elemList[i]).attr('style', '');
                            continue;
                        } else {
                            $(elemList[i]).css({
                                'background-color': btxbjs
                            });
                        }
                    }
                }
            }
        }
        if (isNotBlank(btxbzSize)) {
            if (btxbzSize > 30) {
                btxbzSize = 30;
            }
            $('.bdc-not-full-bg .bdc-not-null sub').css({
                'font-size': btxbzSize + "px"
            });
            var label = $('.bdc-not-full-bg .bdc-not-null label');
            if (isNotBlank(label) && label.length > 0) {
                for (var i = 0; i < label.length; i++) {
                    var html = label[i].innerText;
                    if(html.length >0) {
                        html = html.replace("*", "");
                    }
                    var span = $(label[i]).find('span');

                    //只要符号大于16像素会换行
                    if (btxbzSize >= 16){
                        //没用br换行符的label样式处理
                        if ($( label[i]).height()=="20") {
                            //没用br换行符的label文字大于等于6会换行
                            if (html.length >= 6) {
                                $(label[i]).css({
                                    'padding': '0px 6px',
                                    'line-height': '16px'
                                });
                                $(label[i]).addClass("bdc-label-newline");
                                if (btxbzSize >= 24) {
                                    $(span).css({
                                        'top': '16%',
                                        'position': 'relative'
                                    })
                                }
                            } else {
                                // 没用br换行符的label
                                if (btxbzSize >= 24) {
                                    $(span).css({
                                        'top': '16%',
                                        'position': 'relative'
                                    })
                                }
                            }
                        }else{
                            //没用br换行符且大于24像素的label样式处理
                            if (btxbzSize >= 24) {
                                $(span).css({
                                    'top': '16%',
                                    'position': 'relative'
                                })
                            }
                        }
                    }
                }
            }
        }
    }, 100)

}
// 页面初始化时根据权限调整背景色
function reloadColorSize(nameList){
    setTimeout(function () {
        nameList.forEach(function (name) {
            var inputList = $("input[name="+name+"]").find('textarea').prevObject;
            var objList = $("textarea[name="+name+"]").find('textarea').prevObject;
            for(var i = 0;i<objList.length;i++){
                var obj = objList[i];
                var backColor = getComputedStyle(inputList[i]).backgroundColor;
                var rlen = getComputedStyle(inputList[i]).paddingRight;
                obj.style.backgroundColor = backColor;
                obj.style.paddingRight = rlen;
                if(parseInt(rlen)===26){
                    obj.style.overflowY = "hidden";
                }
                var value = obj.defaultValue;
                var char = 14.9;
                if(obj.name === "zjh"){
                    char = 18;
                }
                if(!isNullOrEmpty(value)){
                    var flagLen = obj.offsetWidth+3;
                    var y = getLenPx(value,char);
                    if(getLenPx(value,char)>flagLen){
                        obj.style.lineHeight= "16px";
                    }
                    document.onkeydown = function (ev) {
                        var oEvent = window.event;
                        if(oEvent.keyCode===13){
                            obj.style.lineHeight= "16px";
                            flagLen = getLenPx(value,14.6)+3;
                        }
                    }
                }
            }
        })
    }, 100)
}
// 自监控在textarea中输入字符时调整高度
function zjkColoeSize(nameList){
    nameList.forEach(function (name) {
        $("textarea[name="+name+"]").live('input propertychange', function () {
            var a = $(this).val();
            var obj = $(this).find('textarea').context;
            var flagLen = obj.offsetWidth+3;
            var char = 14.9;
            if(obj.name === "zjh"){
                char = 18;
            }
            document.onkeydown = function (ev) {
                var oEvent = window.event;
                if (oEvent.keyCode === 13) {
                    obj.style.lineHeight = "16px";
                    flagLen = getLenPx(a, char) + 3;
                } else if (getLenPx(a, char) > flagLen) {
                    obj.style.lineHeight = "16px";
                } else {
                    obj.style.lineHeight = "34px";
                    flagLen = obj.offsetWidth+3;
                }
            }
            if (getLenPx(a, char) > flagLen) {
                obj.style.lineHeight = "16px";
            } else {
                obj.style.lineHeight = "34px";
                flagLen = obj.offsetWidth+3;
            }
        })
    })
}

