// 打印字段模板示例
var dyzdMb = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
    "<fetchdatas>\n" +
    "    <page>\n" +
    "        <datas>\n" +
    "            <data  name=\"xmid\" type=\"String\">$XMID</data>\n" +
    "            <data  name=\"tpxx\" type=\"Image\">$imageUrl</data>\n" +
    "        </datas>\n" +
    "        <detail  ID=\"zbid\">\n" +
    "            <row  ID=\"${FZID}\">\n" +
    "                <data  name=\"xh\" type=\"String\">$XH</data>\n" +
    "                <data  name=\"xmmc\" type=\"String\">$XMMC</data>\n" +
    "                <data  name=\"zh\" type=\"String\">$ZH</data>\n" +
    "                <data  name=\"ghyt\" type=\"String\">$GHYT</data>\n" +
    "                <data  name=\"jzmj\" type=\"String\">$JZMJ</data>\n" +
    "                <data  name=\"zcs\" type=\"String\">$ZCS</data>\n" +
    "            </row>\n" +
    "        </detail>\n" +
    "    </page>\n" +
    "</fetchdatas>"
// 请求地址
var BASE_URL = getContextPath() + '/rest/v1.0/dysjpz';
// 打印配置全局变量
var bdcDysjPzDTO = {"bdcDysjZbPzDOList": [{"ytmc": "用途说明/名称"}]};

//设置IP
function getIP() {
    return document.location.protocol + "//" + window.location.host;
}
function getContextPath() {
    var pathName = document.location.pathname;
    var index = pathName.substr(1).indexOf("/");
    var result = pathName.substr(0, index + 1);
    return result;
}

// 打印模板地址
var MODEL_PATH;
getPrintPath();

/**
 *  获取打印路径的配置信息
 */
function getPrintPath() {
    $.ajax({
        url: BASE_URL + "/print/model/path",
        type: "GET",
        contentType: 'application/json',
        success: function (data) {
            MODEL_PATH = data;
        },
        error: function (e) {
            response.fail(e, '');
        }
    });
}

/**
 * fr3打印js
 * @returns {*}
 */
function print(modelUrl, dataUrl, hiddeMode) {
    var fr3Url = "v2|designMode=false|frURL=" + modelUrl
        + "|dataURL=" + dataUrl
        + "|updateUrl=http://oa.gtis.com.cn:80/platform/pluging/update.ini|hiddeMode=" + hiddeMode;

    window.location.href = "eprt://" + fr3Url;
}

// 将xml存到redis
function sendXmlToRedis(xml) {
    var redisKey;
    $.ajax({
        url: BASE_URL + "/pzxx/xml",
        type: "POST",
        contentType: 'application/json',
        data: xml,
        async: false,
        success: function (data) {
            if (data) {
                redisKey = data;
            }
        },
        error: function (e) {
            response.fail(e, '');
        }
    });
    return redisKey;
}
//加载主表
function renderZub(laytpl, form, data) {
    var geZubTpl = zubTpl.innerHTML;
    laytpl(geZubTpl).render(data, function(html){
        $('#zbpzFrom').html(html);
        form.render();
    });
}

// 生成数据流数据,子表数据绑定
function creatGzSjl(laytpl, form, data) {
    var getLeftTpl = leftTpl.innerHTML;
    var getRightTpl = rightTpl.innerHTML;
    laytpl(getLeftTpl).render(data, function(html){
        $('#accordion').html(html);
    });
    laytpl(getRightTpl).render(data, function(html){
        $('#contentView').html(html);
        form.render();
    });
}

// 对象赋值
function copy(newobj, obj) {
    for (var attr in obj) {
        newobj[attr] = deleteWhitespace(obj[attr], "edge");
    }
    return newobj;
}