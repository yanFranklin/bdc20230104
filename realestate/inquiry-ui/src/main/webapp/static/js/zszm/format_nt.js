// 证书类型
function formatZslx(zslx){
    if (zslx == 1) {
        return '证书';
    } else if (zslx == 2){
        return '证明';
    } else {
        return '';
    }
}

// 格式化权属状态
function formatQszt(qszt){
    if (qszt == 0) {
        return '<span class="" style="color:#1d87d1;">临时</span>';
    } else if (qszt == 1){
        return '<span class="" style="color:#32b032;">现势</span>'
    } else if (qszt == 2) {
        return '<span class="" style="color:#f28943;">历史</span>'
    } else if (qszt == 3) {
        return '<span class="" style="color:#f24b43;">终止</span>'
    } else {
        return '';
    }
}

/**
 * 权属状态格式化（对于撤销申请单独处理）
 */
function formatQsztWithCx(qszt, ajzt) {
    if (qszt == 0) {
        return '<span class="" style="color:#1d87d1;">临时</span>';
    } else if (qszt == 1){
        return '<span class="" style="color:#32b032;">现势</span>';
    } else if (qszt == 2) {
        return '<span class="" style="color:#f28943;">历史</span>';
    } else if (qszt == 3) {
        if(ajzt == 5){
            return '<span class="" style="color:#f24b43;">撤回</span>';
        } else {
            return '<span class="" style="color:#f24b43;">终止</span>';
        }
    } else{
        return '';
    }
}

// 格式化权属状态
function formatQsztOfData(qszt){
    if (qszt == 0) {
        return '临时';
    } else if (qszt == 1){
        return '现势'
    } else if (qszt == 2) {
        return '历史'
    } else if (qszt == 3) {
        return '终止'
    } else {
        return '';
    }
}

// 格式化限制状态
function formatXzzt(data) {
    var value = '';
    if (data != undefined) {
        if (data.cf === true) {
            //南通样式
            value += '<span class="bdc_change_red bdc-cf" style="color:#EE0000;">查封 </span>';
            //value += '<span class="" style="color:#EE0000;">查封 </span>';
        }
        if (data.ycf === true) {
            value += '<span class="bdc-ycf" style="color:#ee717a;">预查封 </span>';
        }
        if (data.dya === true || data.zjgcdy === true) {
            value += '<span class="bdc-dya" style="color:#FF00FF;">抵押 </span>';
        }
        if (data.ydya === true) {
            value += '<span class="" style="color:#aacd06;">预抵押 </span>';
        }
        if (data.yy === true) {
            value += '<span class="bdc-yy" style="color:#ef7106;">异议 </span>';
        }
        if (data.yg === true) {
            value += '<span class="bdc-yg" style="color:#ffb60c;">预告 </span>';
        }
        if (data.dyi === true) {
            value += '<span class="" style="color:#855e6e;">地役 </span>';
        }
        if (data.sd === true) {
            value += '<span class="bdc-sd" style="color:#e50971;">锁定 </span>';
        }
        if (data.jzq === true) {
            value += '<span class="bdc-jzq" style="color:#13b1c4;">居住 </span>';
        }
        if (data.lqlzzt === true) {
            value += '<span class="bdc-lqlzzt" style="color:#13b1c4;">流转 </span>';
        }
        if (value === '') {
            value += '<span class="" style="color:#32b032;">正常</span>';
        }
    }
    return value;
}

function formatAjzt(ajzt,bdcdyh,xmid) {
    var zzbl = false;
    if(bdcdyh) {
        if(!xmid) {
            xmid = '';
        }
        $.ajax({
            url: "/realestate-inquiry-ui/rest/v1.0/zhcx/nantong/blzt/sfzzbl",
            type: "GET",
            data: {bdcdyh:bdcdyh,xmid:xmid},
            contentType: 'application/json',
            dataType: 'text',
            async: false,
            success: function (res) {
                if(res && res >0) {
                    zzbl = true;
                }
            },
            error: function () {
                failMsg("查询办理状态失败");
            }
        });
    }
    if(1 == ajzt || zzbl){
        return '<span class="bdc-zzbl">正在办理</span>';
    } else if(2 == ajzt){
        return "已办";
    } else if(3 == ajzt){
        return "暂停";
    } else {
        return "";
    }
}

// 格式化限制状态
function formatXzztOfData(data) {
    var value = '';
    if (data != undefined) {
        if (data.cf === true) {
            value += '查封 ';
        }
        if (data.ycf === true) {
            value += '预查封 ';
        }
        if (data.dya === true || data.zjgcdy === true) {
            value += '抵押 ';
        }
        if (data.ydya === true) {
            value += '预抵押 ';
        }
        if (data.yy === true) {
            value += '异议 ';
        }
        if (data.yg === true) {
            value += '预告 ';
        }
        if (data.dyi === true) {
            value += '地役 ';
        }
        if (data.sd === true) {
            value += '锁定 ';
        }
        if (value === '') {
            value += '正常';
        }
    }
    return value;
}