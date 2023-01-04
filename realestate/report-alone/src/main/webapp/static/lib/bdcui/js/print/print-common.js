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
 * ---------------------------打印公共方法开始-------------------------------------
 */
/**
 *
 * @param  dylxArr 当前页面打印类型数组
 * 设置打印类型的session
 */
var dypzMap

function setDypzSession(dylxArr, sessionKey) {
    // 当前页面打印配置信息
    $.ajax({
        type: "POST",
        url: getContextPath() + "/rest/v1.0/dypz/common/pzxx",
        contentType: 'application/json',
        dataType: "json",
        data: JSON.stringify(dylxArr),
        async: false,
        success: function (data) {
            dypzMap = data;
        }, error: function (e) {
            delAjaxErrorMsg(e);
        }
    });
}

/**
 *
 * @param dylx 打印类型
 * @param dataUrl 获取数据源地址
 * @param hiddeMode fr3打印是否隐藏控件打印预览
 */
function printChoice(dylx, appName, dataUrl, modelUrl, hiddeMode, sessionKey) {
    console.log("打印配置参数", dypzMap);
    console.log("xml数据源", dataUrl);
    if (dypzMap && !isEmptyObject(dypzMap) && dypzMap[dylx]) {
        var bdcDysjPzDO = dypzMap[dylx];
        var fr3Path = bdcDysjPzDO.fr3path;
        var pdfPath = bdcDysjPzDO.pdfpath;
        // 如果fr3打印路径配置了，则优先选择fr3打印
        if (fr3Path) {
            fr3Path = resolvePath(fr3Path);
            print(fr3Path, dataUrl, hiddeMode);
            return;
        } else if (pdfPath) {
            pdfPath = resolvePath(pdfPath);
            //设置pdf打印参数
            var bdcPdfDyQO = {};
            bdcPdfDyQO.appName = appName;
            bdcPdfDyQO.dataUrl = dataUrl;
            bdcPdfDyQO.pdfpath = pdfPath;
            bdcPdfDyQO.fileName = sessionKey;
            ajaxPostPdfPrint(bdcPdfDyQO);
            return;
        }
    }
    // 兼容之前的打印配置
    print(modelUrl, dataUrl, hiddeMode);
}

function resolvePath(path) {
    try {
        return appendIpPort(path);
    } catch (e) {
        return path;
    }
}

/**
 * post请求打印pdf
 * @param bdcPdfDyQO pdf打印参数
 */
function ajaxPostPdfPrint(bdcPdfDyQO) {
    $.ajax({
        type: "POST",
        url: getContextPath() + "/rest/v1.0/dypz/common/pdf/param/redis",
        contentType: 'application/json',
        data: JSON.stringify(bdcPdfDyQO),
        success: function (data) {
            if (data) {
                var pdfUrl = getContextPath() + "/rest/v1.0/dypz/common/pdf/" + data;
                window.open(getContextPath() + '/static/lib/pdf/web/viewer.html?file=' + encodeURIComponent(pdfUrl), "PDF");
            }
        }, error: function (e) {
            warnMsg("pdf生成异常！")
        }
    });
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

/**
 */
function printForDacx(dylx, bdclx, fileName, redisKey, dataUrl, hiddeMode, modelUrl) {
    console.log("打印配置参数", dypzMap);
    console.log("xml数据源", dataUrl);
    if (dypzMap && !isEmptyObject(dypzMap) && dypzMap[dylx]) {
        var bdcDysjPzDO = dypzMap[dylx];
        var pdfPath = bdcDysjPzDO.pdfpath;
        var fr3Path = bdcDysjPzDO.fr3path;
        // 如果fr3打印路径配置了，则优先选择fr3打印
        if (fr3Path) {
            print(fr3Path, dataUrl, hiddeMode);
            return;
        } else if (pdfPath) {
            var pdfUrl = getContextPath() + '/rest/v1.0/zszm/print/dacx/pdf?dylx=' + dylx + '&fileName=' + fileName + '&modelWordUrl=' + pdfPath + '&bdclx=' + bdclx + '&xmlKey=' + redisKey + '&dacxFlag=true';
            window.open(getContextPath() + '/static/lib/pdf/web/viewer.html?file=' + encodeURIComponent(pdfUrl), "PDF");
        }
    }
    // 兼容之前的打印配置
    print(modelUrl, dataUrl, hiddeMode);
}

function getXmlStr(dataSourceUrl) {
    var str = "";
    $.ajax({
        url: dataSourceUrl,
        async: false,
        success: function (data) {
            str = data;
        }
    });
    return str;
}

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

function isNullOrEmpty(str){
    if(!str || "" == str || "null" == str || undefined == str || "undefined" == str){
        return true;
    }

    return false;
}
/**
 * ---------------------------打印公共方法结束-------------------------------------
 */
