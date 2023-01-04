/**
 * Created by Administrator on 2019/8/12.
 * 当前js只存放3.0公共内容，例如统一每页条数的选择项
 */
//统一每页条数的选择项
var commonLimits = [10,20,50,100,200,500];

//统一抵押权权利类型
var commonDyaq_qllx ="37";

//文件管理器(查看)
var wjgl_ck ="{\"CanRefresh\":1,\"CanCreateNewFolder\":0,\"CanDelete\":0,\"CanRename\":0,\"CanPrint\":1,\"CanDownload\":1,\"CanUpload\":0,\"CanTakePhoto\":0,\"CanScan\":0,\"CanEdit\":-1}";

/**
 * 复选框背景设置颜色----暂时不用，备用
 * @param colorList [{name:'bdc-change-red',color: '#ff0000'},{name:'bdc-change-green',color: 'green'}]
 * name: class名称
 * color：颜色值
 */
function changeCheckboxBackground1(colorList) {
    colorList.forEach(function (v) {
        var $changeCheckbox = $('.'+ v.name);
        $changeCheckbox.each(function (i) {
            var getIndex = $($changeCheckbox[i]).parents('tr').index() + 1;
            $('.layui-table-fixed .layui-table-body tr:nth-child('+ getIndex +') .laytable-cell-checkbox').parent().css('background-color',v.color);
        });
    });
}
/**
 * tab下面的复选框背景设置颜色
 * @param colorList [{name:'bdc-change-red',color: '#ff0000'},{name:'bdc-change-green',color: 'green'}]
 * name: class名称
 * color：颜色值
 */
function changeTabCheckboxBackground(colorList) {
    colorList.forEach(function (v) {
        var $changeCheckbox = $('.layui-show .'+ v.name);
        $changeCheckbox.each(function (i) {
            var getIndex = $($changeCheckbox[i]).parents('tr').index() + 1;
            $('.layui-show .layui-table-fixed .layui-table-body tr:nth-child('+ getIndex +') .laytable-cell-checkbox').parent().css('background-color',v.color);
        });
    });
}
/**
 * 整行tr背景设置颜色
 * @param colorList [{name:'bdc-change-red',color: '#ff0000'},{name:'bdc-change-green',color: 'green'}]
 * @param tabTable 正常表格可不传值，tab下面的表格传值 true
 * name: class名称
 * color：颜色值
 */
function changeCheckboxBackground(colorList,tabTable) {
    colorList.forEach(function (v) {
        var $changeCheckbox;
        if(tabTable){
            $changeCheckbox = $('.layui-show .'+ v.name);
        }else {
            $changeCheckbox = $('.'+ v.name);
        }
        $changeCheckbox.each(function (i) {
            var getIndex = $($changeCheckbox[i]).parents('tr').index() + 1;
            $('.layui-table-main tr:nth-child('+ getIndex +') td>.layui-table-cell').css({'color': v.color?v.color:"#fff",'background-color':v.background}).find('span').css('color', v.color?v.color:"#fff");
            $('.layui-table-fixed .layui-table-body tr:nth-child('+ getIndex +')').css('background-color',v.background).find('td').css('color', v.color?v.color:"#fff");
        });
    });
}

/**
 * 整行tr背景设置颜色,最右侧操作列保持不变
 * @param colorList [{name:'bdc-change-red',color: '#000',background:'red'},{name:'bdc-change-green',color: 'green',background:'#fff'}]
 * @param tabTable 正常表格可不传值，tab下面的表格传值 true
 * name: class名称
 * color：字体颜色值，不传值默认白色
 * background：背景颜色值
 */
function changeTrBackgroundExceptRight(colorList,tabTable) {
    colorList.forEach(function (v) {
        var $changeCheckbox;
        if(tabTable){
            $changeCheckbox = $('.layui-show .'+ v.name);
        }else {
            $changeCheckbox = $('.'+ v.name);
        }
        $changeCheckbox.each(function (i) {
            var getIndex = $($changeCheckbox[i]).parents('tr').index() + 1;
            var getColor = v.color?v.color:"#fff";
            if(tabTable){
                $('.layui-show .layui-table-main tr:nth-child('+ getIndex +') td:not(.layui-table-col-special:last-child)>.layui-table-cell').addClass('bdc-special-bdcdyh-color').css({'color': getColor,'background-color':v.background}).find('span').css('color', getColor);
                $('.layui-show .layui-table-main tr:nth-child('+ getIndex +') .layui-table-col-special:last-child').css('background-color','transparent');
                $('.layui-show .layui-table-fixed-l .layui-table-body tr:nth-child('+ getIndex +')').css('background-color',v.background).find('td').css('color', getColor);
                $('.layui-show .layui-table-fixed-r .layui-table-body tr:nth-child('+ getIndex +') td:not(.layui-table-col-special:last-child)>.layui-table-cell').addClass('bdc-special-bdcdyh-color').css({'color': getColor,'background-color':v.background}).find('span').css('color', getColor);
            }else {
                $('.layui-table-main tr:nth-child('+ getIndex +') td:not(.layui-table-col-special:last-child)>.layui-table-cell').addClass('bdc-special-bdcdyh-color').css({'color': getColor,'background-color':v.background}).find('span').css('color', getColor);
                $('.layui-table-main tr:nth-child('+ getIndex +') .layui-table-col-special:last-child').css('background-color','transparent');
                $('.layui-table-fixed-l .layui-table-body tr:nth-child('+ getIndex +')').css('background-color',v.background).find('td').css('color', getColor);
                $('.layui-table-fixed-r .layui-table-body tr:nth-child('+ getIndex +') td:not(.layui-table-col-special:last-child)>.layui-table-cell').addClass('bdc-special-bdcdyh-color').css({'color': getColor,'background-color':v.background}).find('span').css('color', getColor);
            }
        });
    });
}
/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @description 替换中文 括号 为英文括号
 */
function replaceBracket(str){

    if(str==''||str==undefined){
        return str;
    }
    if(str.indexOf("（")!=-1){
        str=str.replace(new RegExp("（","gm"),"(");
    }
    if(str.indexOf("）")!=-1){
        str=str.replace(new RegExp("）","gm"),")");
    }
    return str;
}
/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @description 替换 class 为parentheses 的中文括号为英文括号
 */
function replaceBracketArray() {
    var obj = {};
    $(".bracket").each(function (i) {
        var value = $(this).val();
        var name = $(this).attr('name');
        obj[name] = replaceBracket(value);
    });
    return obj;
}

/**
 * 格式化 bdcdyh
 * @param bdcdyh 不动产单元号
 * @return {string} 返回格式化的不动产单元号字符串
 */
function formatBdcdyh(bdcdyh) {
    var result;
    if (!isNullOrEmpty(bdcdyh) && bdcdyh.length == 28) {
        result = bdcdyh.substring(0, 6) + ' '
            + bdcdyh.substring(6, 12) + ' '
            + bdcdyh.substring(12, 19) + ' '
            + bdcdyh.substring(19, 28);
    } else {
        result = bdcdyh;
    }
    return result;
}

/**
 * 对传入的字符串进行去空格处理
 * @date 2019.03.14 18:43
 * @author
 * @param str 要处理的字符串
 * @param where 处理方式，all---所有空格；edge---两边；left——左边；right——右边
 * @return
 */
function deleteWhitespace(str, where) {
    if (!isNullOrEmpty(str)) {
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

/**
 * 判断字符串是否为空
 *
 * @param str  目标字符串
 * @returns {boolean}  为空：true ; 不为空：false
 */
function isNullOrEmpty(str){
    if(!str || "" == str || "null" == str || undefined == str || "undefined" == str){
        return true;
    }

    return false;
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

/**
 * 验证联系电话,包括手机号码，固话,为空时直接验证通过
 */
function validatePhone(lxdh) {
    var isPhone = /^([0-9]{3,4}-)?[0-9]{7,8}$/;//电话号码
    var isMob = /^0?1[3|4|5|6|7|8|9][0-9]\d{8}$/;//手机号码
    if (!isNotBlank(lxdh) || isMob.test(lxdh) || isPhone.test(lxdh)) {
        return true;
    } else {
        return false;
    }
}

/**
 * 验证身份证号码格式
 * @param 证件号
 */
function verifyIdNumber(value) {
    var yzxx = {};
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

    if (!value || !/^\d{6}(18|19|20)?\d{2}(0[1-9]|1[012])(0[1-9]|[12]\d|3[01])\d{3}(\d|X)$/i.test(value)) {
        yzxx.isSubmit = false;
        yzxx.verifyMsg = "身份证号格式错误！";
    } else if (!city[value.substr(0, 2)]) {
        yzxx.isSubmit = false;
        yzxx.verifyMsg = "地址编码错误！";
    } else {
        //18位身份证需要验证最后一位校验位
        if (value !== null && value !== "" && value.length === 18) {
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
                yzxx.isSubmit = false;
                yzxx.verifyMsg = "校验位错误！";
            }
        }
    }
    return yzxx;
}

function getSearchListPz() {
    var searchListZdy = [];
    var $searchLabel;
    if($('.layui-show').length > 0){
        $searchLabel = $('.layui-show .bdc-search-box .layui-form-label');
    }else{
        $searchLabel = $('.bdc-search-box .layui-form-label');
    }
    for(var i = 0, len = $searchLabel.length; i < len; i++){
        var $curLabel = $($searchLabel[i]);
        if($curLabel.hasClass('bdc-line-hide')){
            searchListZdy.push({"name": $curLabel.text(),"isShow": false});
        }else {
            searchListZdy.push({"name": $curLabel.text(),"isShow": true});
        }
    }
    return searchListZdy;
}

/**
 * 添加用户名水印
 * @date 2020.04.30 16:03
 * @author yousiyi
 * @param setting 需要修改的水印样式
 * @param 默认方法watermark()，水印范围为body
 * @return
 */

// 使用方法前需要添加页面版本判断
// $(function () {
//     $.ajax({
//             url: getContextPath() + '/rest/v1.0/url/common/watermark',
//             type: 'GET',
//             dataType: 'json',
//             async: false,
//             success: function (data) {
//                 /*
//                 * 南通、合肥页面增加水印，内容为当前登录人
//                 * */
//                 if (data && (data.htmlversion === "nantong" ||data.htmlversion === "hefei")) {
//                     var username = data.username;
//                     $('body').attr('onload',function () {
//                         watermark({ watermark_txt: username})
//                     });
//                     var height = document.body.scrollHeight;
//                     window.onscroll= function(){
//                         //获取页面全部高度
//                         var height = document.body.scrollHeight;
//                         //高度大于600时重新添加水印
//                         if(height > 600){
//                             $(document).find('.mask_div').remove();
//                             watermark({ watermark_txt: username})
//                         }
//                     }
//                 }
//             },
//             error: function (xhr, status, error) {
//                 delAjaxErrorMsg(xhr)
//             }
//         }
//     )
// });

function watermark(settings) {
    // 水印默认配置
    var defaultSettings = {
        watermark_elem: "", //需要添加水印位置的类名
        watermark_txt: "text",
        watermark_x: 20, //水印起始位置x轴坐标
        watermark_y: 20, //水印起始位置Y轴坐标
        watermark_rows: 20, //水印行数
        watermark_cols: 20, //水印列数
        watermark_x_space: 100, //水印x轴间隔
        watermark_y_space: 50, //水印y轴间隔
        watermark_color: '#d0d5da', //水印字体颜色
        watermark_alpha: 0.6, //水印透明度
        watermark_fontsize: '14px', //水印字体大小
        watermark_font: 'Microsoft YaHei', //水印字体
        watermark_width: 200, //水印宽度
        watermark_height: 80, //水印长度
        watermark_angle: 15 //水印倾斜度数
    };

    // 获取系统登录用户名
    // $.ajax({
    //     url: getContextPath(),
    //     type: 'GET',
    //     dataType: 'json',
    //     async: false,
    //     data: { gzldyid: processDefKey },
    //     success: function(data) {
    //         if (data) {
    //             defaultSettings.watermark_txt = data;
    //         }
    //     },
    //     error: function(xhr, status, error) {
    //         delAjaxErrorMsg(xhr)
    //     }
    // });

    //采用配置项替换默认值
    if (arguments.length === 1 && typeof arguments[0] === "object") {
        var src = arguments[0] || {};
        for (key in src) {
            if (src[key] && defaultSettings[key] && src[key] === defaultSettings[key])
                continue;
            else if (src[key])
                defaultSettings[key] = src[key];
        }
    }
    var oTemp = document.createDocumentFragment();

    // 无类名时默认水印覆盖范围为body
    var maskElement = document.getElementsByClassName(defaultSettings.watermark_elem)[0] || document.body;
    //获取页面最大宽度
    var page_width = Math.max(maskElement.scrollWidth, maskElement.clientWidth, maskElement.offsetWidth);
    // var page_width = maskElement.scrollWidth;
    //获取页面最大高度
    var page_height = Math.max(maskElement.scrollHeight, maskElement.clientHeight, maskElement.offsetHeight);
    // 获取水印实际起始位置
    defaultSettings.watermark_x = Math.max(maskElement.scrollLeft, maskElement.clientLeft, maskElement.offsetLeft);
    defaultSettings.watermark_y = Math.max(maskElement.scrollTop, maskElement.clientTop, maskElement.offsetTop);

    //水印数量自适应元素区域尺寸
    defaultSettings.watermark_cols = Math.ceil((page_width - defaultSettings.watermark_x) / (defaultSettings.watermark_x_space + defaultSettings.watermark_width))-1;
    defaultSettings.watermark_rows = Math.ceil((page_height - defaultSettings.watermark_y) / (defaultSettings.watermark_y_space + defaultSettings.watermark_height))-1;


    var x, y;
    // 循环显示水印
    for (var i = 0; i < defaultSettings.watermark_rows; i++) {
        y = defaultSettings.watermark_y + (defaultSettings.watermark_y_space + defaultSettings.watermark_height) * i;
        for (var j = 0; j < defaultSettings.watermark_cols; j++) {
            x = defaultSettings.watermark_x + (defaultSettings.watermark_width + defaultSettings.watermark_x_space) * j;
            var mask_div = document.createElement('div');
            mask_div.id = 'mask_div' + i + j;
            mask_div.className = 'mask_div';
            mask_div.appendChild(document.createTextNode(defaultSettings.watermark_txt));
            //设置水印div倾斜显示
            mask_div.style.webkitTransform = "rotate(-" + defaultSettings.watermark_angle + "deg)";
            mask_div.style.MozTransform = "rotate(-" + defaultSettings.watermark_angle + "deg)";
            mask_div.style.msTransform = "rotate(-" + defaultSettings.watermark_angle + "deg)";
            mask_div.style.OTransform = "rotate(-" + defaultSettings.watermark_angle + "deg)";
            mask_div.style.transform = "rotate(-" + defaultSettings.watermark_angle + "deg)";
            mask_div.style.visibility = "";
            mask_div.style.position = "absolute";
            mask_div.style.left = x + 'px';
            mask_div.style.top = y + 'px';
            mask_div.style.overflow = "hidden";
            mask_div.style.zIndex = "9999";
            mask_div.style.pointerEvents = 'none'; //pointer-events:none 让水印不遮挡页面的点击事件
            mask_div.style.opacity = defaultSettings.watermark_alpha;
            mask_div.style.fontSize = defaultSettings.watermark_fontsize;
            mask_div.style.fontFamily = defaultSettings.watermark_font;
            mask_div.style.color = defaultSettings.watermark_color;
            mask_div.style.textAlign = "center";
            mask_div.style.width = defaultSettings.watermark_width + 'px';
            mask_div.style.height = defaultSettings.watermark_height + 'px';
            mask_div.style.display = "block";
            oTemp.appendChild(mask_div);
        };
    };
    maskElement.appendChild(oTemp);
}

function getContextPath() {
    var pathName = document.location.pathname;
    var index = pathName.substr(1).indexOf("/");
    var result = pathName.substr(0, index + 1);
    return result;
}

//将面积平方米换算成亩展示
function changeMjDw(pfmmj){
    if(isNullOrEmpty(pfmmj)){
        return '';
    }
    return calculateFloat(parseFloat(pfmmj) * 0.0015);
}

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
 * 上传文件夹
 *
 * @param processInsId 工作流实例ID
 * @param wjjmc 文件夹名称
 */
function scwjj(processInsId,wjjmc){
    getReturnData("/rest/v1.0/fjcl/folder", {gzlslid: processInsId, wjjmc: wjjmc}, 'GET', function (data) {
        if (isNotBlank(data)) {
            viewWjglFj(encodeURI(wjjmc),"clientId",data.id,'上传附件',2);
        }else{
            layer.msg(clmc+ "文件夹生成失败");
        }
    }, function (err) {
        delAjaxErrorMsg(err)
    }, false);
}



/**
 * 通过文件管理器查看附件
 *
 * @param spaceId
 * @param clientId
 * @param title 弹框标题
 * @param qxType 权限种类 1:查看 2上传
 * @param isparentLayer 是否在父页面打开，不传或者false则在当前页面，true在父页面打开
 */
function viewWjglFj(spaceId,clientId,nodeId,title,qxType,isparentLayer){
    var wjglCs =getWjglCs(spaceId,clientId,nodeId,qxType);
    if(isNullOrEmpty(wjglCs.token) ||isNullOrEmpty(wjglCs.spaceId)){
        layer.alert("缺失附件参数");
        return false;
    }
    var url = "/realestate-accept-ui/view/slym/sjd.html?paramJson=" + encodeURI(JSON.stringify(wjglCs));
    if(isparentLayer){
        var index = parent.layer.open({
            title: title,
            type: 2,
            area: ['1150px', '600px'],
            content: url
        });
        layer.full(index);
    }else{
        var index = layer.open({
            title: title,
            type: 2,
            area: ['1150px', '600px'],
            content: url
        });
        layer.full(index);
    }

}

//组织获取文件管理参数
function getWjglCs(spaceId,clientId,nodeId,qxType){
    var wjglCs = {};
    wjglCs.clientId=clientId;
    wjglCs.token=getCommonToken();
    wjglCs.spaceId = spaceId;
    if(isNotBlank(nodeId)) {
        wjglCs.nodeId = nodeId;// 用于绑定当前流程与文件中心附件的id
    }
    wjglCs.storageUrl = document.location.protocol + "//" + document.location.host + "/storage";
    //查看
    if(qxType ===1){
        wjglCs.sFrmOption=wjgl_ck;
    }
    return wjglCs;
}

//获取token
function getCommonToken(){
    var token ="";
    $.ajax({
        url: getContextPath() + '/rest/v1.0/url/common/token',
        type: 'GET',
        async: false,
        success: function (data) {
            token=data;
        },
        error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr);
        }
    });
    return token;

}

//处理ajax的错误
function delAjaxErrorMsg(e, message) {
    removeModal();
    layer.closeAll();
    var msg = '请求异常！';
    var detail = '';
    if (message != '' && message != undefined) {
        msg = message;
    }
    if (e.status == 500) {
        var responseText = JSON.parse(e.responseText);
        msg = (message != '' && message != undefined) ? msg : responseText.msg;
        detail = responseText.detail;
    }
    layer.msg('<img src="../../static/images/error-small.png" alt="">' + msg + ' <a class="bdc-show-more-tips" href="javascript:;">更多</a>', {
        time: 4000,
        success: function () {
            if ($('#otherTips').length == 0) {
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

//移除遮罩
function removeModal() {
    $("#waitModalLayer").remove();
}

/**
 * 将JSON转为URL后缀参数格式，例如 {"name":"xiao", "age": 1} 转成 name=xiao&age=1
 * @param data JSON实体
 * @returns {string} URL参数字符串
 */
function parseJsonToUrlParams(data) {
    try {
        var array = [];
        for (var i in data) {
            var key = encodeURIComponent(i);
            var value = encodeURIComponent(data[i]);
            array.push(key + '=' + value);
        }
        return array.join('&');
    } catch (err) {
        return '';
    }
}

/**
 * 针对部分地区附件、证照地址缺省IP端口补全（达到内外网段分别采用自己的IP端口目的）
 * @param path 文件storage地址，例如 /storage/rest/files/download/123
 * @returns {string|*} 补全了IP端口的文件地址，例如 http://192.168.1.1:8030/storage/rest/files/download/123
 */
function appendIpPort(path) {
    if(isNullOrEmpty(path)) {
        return "";
    }

    if(path.toLowerCase().indexOf("/storage") == 0) {
        // 以/storage开头的加上缺省的IP、端口
        return document.location.protocol + "//" + window.location.host + path;
    } else {
        return path;
    }
}
/**
 * 针对部分地区附件、证照地址缺省IP端口补全（达到内外网段分别采用自己的IP端口目的）
 * @param path 文件storage地址，例如 /storage/rest/files/download/123
 * @returns {string|*} 补全了IP端口的文件地址，例如 http://192.168.1.1:8030/storage/rest/files/download/123
 */
function getTreeData(treeid,inputShowId,inputId,url) {
    layui.use('tree', function(){
        var tree = layui.tree
        // var util = layui.util;
        tree.render({
            elem: '#'+treeid,
            data: getData(),
            id: treeid,
            showCheckbox: true,     //是否显示复选框
            onlyIconControl: true,
            oncheck: function(obj){
                var self = this;
                this.titles = []
                this.id = [];
                var treecheckdata = tree.getChecked(treeid);
                getCheckedId(treecheckdata);
                // 获取选中节点的id,遍历树形列表去获取每一级的id
                function getCheckedId(jsonObj) {
                    var this_ = this;
                    jsonObj.forEach(function(item, index,jsonObj){
                        self.titles.push(item.title);
                        self.id.push(item.id);
                        if(item.children!=[]){
                            getCheckedId(item.children);//递归实现遍历每一层级数据
                        }
                    });
                    return self.titles;
                }
                $('#'+inputShowId).val(this.titles);
                $('#'+inputId).val(this.id);
            }
        });
    });

    function getData() {
        var data = [];
        $.ajax({
            url: getContextPath() +url,    //后台数据请求地址
            type: "GET",
            contentType: "application/json;charset=utf-8",
            dataType: "json",
            async: false,
            success: function (resut) {
                data = resut;
            }
        });
        return data;
    }
}


function getIpHz() {
    var $paramArr = [];
    var $params = window.location.search.replace('?','');
    var $paramSplit = $params.split('&');
    for(var i in $paramSplit)
    {
        var $subParam = $paramSplit[i].split('=');
        $paramArr[$subParam[0]] = $subParam[1];
    }
    return $paramArr;
}