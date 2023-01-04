/**
 *获取页面值生成二维码
 */
function generateQrCode(ewmnr) {
    if (ewmnr != null && ewmnr != '') {
        $("#qrcode").qrcode({
            render: "canvas", // 渲染方式有table方式和canvas方式
            width: 100,   //默认宽度
            height: 100, //默认高度
            text: utf16to8(ewmnr), //二维码内容
            typeNumber: -1,   //计算模式一般默认为-1
            correctLevel: 2, //二维码纠错级别
            // background: "#ffffff",  //背景颜色
            // foreground: "#1E90FF"  //二维码颜色
        });
    }
}

function generateQrCodeById(id, ewmnr) {
    if (ewmnr != null && ewmnr != '' && id !== null) {
        $(id).qrcode({
            render: "canvas", // 渲染方式有table方式和canvas方式
            width: 100,   //默认宽度
            height: 100, //默认高度
            text: utf16to8(ewmnr), //二维码内容
            typeNumber: -1,   //计算模式一般默认为-1
            correctLevel: 2, //二维码纠错级别
            // background: "#ffffff",  //背景颜色
            // foreground: "#1E90FF"  //二维码颜色
        });
    }
}

/**
 * 转码
 * @param str
 * @returns {string}
 */
function utf16to8(str) {
    var out, i, len, c;
    out = "";
    len = str.length;
    for (i = 0; i < len; i++) {
        c = str.charCodeAt(i);
        if ((c >= 0x0001) && (c <= 0x007F)) {
            out += str.charAt(i);
        } else if (c > 0x07FF) {
            out += String.fromCharCode(0xE0 | ((c >> 12) & 0x0F));
            out += String.fromCharCode(0x80 | ((c >> 6) & 0x3F));
            out += String.fromCharCode(0x80 | ((c >> 0) & 0x3F));
        } else {
            out += String.fromCharCode(0xC0 | ((c >> 6) & 0x1F));
            out += String.fromCharCode(0x80 | ((c >> 0) & 0x3F));
        }
    }
    return out;
}