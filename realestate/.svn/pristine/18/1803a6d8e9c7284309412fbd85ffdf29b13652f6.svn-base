/**
 * 不动产证书信息自定义查询 js
 * 用于锁定台账中，锁定不动产证书时查询证书信息
 */
var BASE_URL = '/realestate-inquiry-ui/rest/v1.0';
function sdzs(){
    if (!checkeddata || checkeddata.length == 0) {
        layer.alert("请选择需要编辑的记录！", {title: '提示'});
        return;
    }
    var data = checkeddata;
    showDialog(lowerJSONKey(data));
}

function lowerJSONKey(jsonArray){
    $.each(jsonArray, function(index, jsonObj){
        for (var key in jsonObj){
            var newKey = key.toLowerCase()
            jsonObj[newKey] = jsonObj[key];
            delete(jsonObj[key]);
        }
    });
    return jsonArray;
}

function showDialog(data) {
    console.info(data);
    var div = " <div id=\"popupTwoRows\"><form class=\"layui-form\" action=\"\">" +
        "            <div class=\"layui-form-item pf-form-item\">" +
        "                <div class=\"layui-inline\" style=\"width: 50%\">" +
        "                    <label class=\"layui-form-label\">限制机关</label>" +
        "                    <div class=\"layui-input-inline bdc-end-time-box\">" +
        "                        <input name=\"sdsqjg\" id='sdsqjg' placeholder=\"请输入内容\" class=\"layui-input\"></input>" +
        "                    </div>" +
        "                </div>" +
        "                <div class=\"layui-inline\" style=\"width: 50%\">" +
        "                    <label class=\"layui-form-label\">限制文号</label>" +
        "                    <div class=\"layui-input-inline bdc-end-time-box\">" +
        "                        <input name=\"sdsqwh\" id='sdsqwh' placeholder=\"请输入内容\" class=\"layui-input\"></input>" +
        "                    </div>" +
        "                </div>" +
        "                <div class=\"layui-inline\" style=\"width: 50%\">" +
        "                    <label class=\"layui-form-label\"><span class=\"required-span\"><sub>*</sub></span>锁定原因</label>" +
        "                    <div class=\"layui-input-inline bdc-end-time-box\">" +
        "                        <input name=\"sdyy\" id='sdyy' placeholder=\"请输入内容\" class=\"layui-input\"></input>" +
        "                    </div>" +
        "                </div>" +
        "                <div class=\"layui-inline\" style=\"width: 50%\">" +
        "                    <label class=\"layui-form-label\">备注</label>" +
        "                    <div class=\"layui-input-inline bdc-end-time-box\">" +
        "                        <input name=\"sdbz\" id='sdbz' placeholder=\"请输入内容\" class=\"layui-input\"></input>" +
        "                    </div>" +
        "                </div>" +
        "            </div>" +
        "        </form>" +
        "    </div>";
    //小弹出层
    layer.open({
        title: '锁定原因',
        type: 1,
        area: ['960px', '300px'],
        btn: ['提交', '取消'],
        content: div
        , yes: function (index, layero) {
            //提交 的回调
            saveSdxx(data,index)
        }
        , btn2: function (index, layero) {
            //取消 的回调
            layer.close(index)
        }
        , cancel: function (index) {
            //右上角关闭回调
            layer.close(index)
        }
    });
}

function saveSdxx(data,index) {

    addModel();
    var sdyy = $('#sdyy').val();

    if (!isNotBlank(sdyy)){
        warnMsg("锁定原因不能为空！");
        return;
    }

    var sdxxArr = new Array();
    var sdzsh = new Array();
    $.each(data, function (index, item) {
        var sdxx =  querySdbdczs(item);
        if(sdxx!='' && sdxx.sdzt!=undefined && sdxx.sdzt=='1'){
            sdzsh.push(item.bdcqzh);
        }

        var sdxxObj = new Object();
        sdxxObj.cqzh = item["bdcqzh"];
        sdxxObj.zsid = item["zsid"];
        sdxxObj.sdsqjg = $('#sdsqjg').val();
        sdxxObj.sdsqwh = $('#sdsqwh').val();
        sdxxObj.bz = $('#sdbz').val();
        sdxxObj.bdclx = item["bdclx"];
        sdxxArr.push(sdxxObj);
    });

    if (sdzsh.length != 0){
        var sdzshstr;
        if (sdzsh.length <= 5){
            sdzshstr = sdzsh.join('、');
        } else {
            for (var i =0;i<sdzsh.length;i++){
                sdzshstr+= sdzsh[i] + '、';
            }
            sdzshstr = sdzshstr.substring(0,sdzshstr.length -1) + '等';
        }
        removeModal();
        layer.confirm("产权证号号" + sdzshstr + "已锁定，是否继续锁定？", {
            title: "提示",
            btn: ["确认", "取消"],
            btn1: function (index2) {
                layer.close(index2);
                sdSdxx(index, sdxxArr, sdyy);
            },
            btn2: function (index) {
                layer.close(index);
            }
        });
    } else {
        sdSdxx(index, sdxxArr,sdyy);
    }

}

function sdSdxx(index, sdxxArr, sdyy) {
    $.ajax({
        url: BASE_URL + '/bdczssd/sd?sdyy=' + encodeURI(sdyy),
        type: "POST",
        data: JSON.stringify(sdxxArr),
        contentType: 'application/json',
        dataType: "json",
        success: function (data) {
            removeModal();
            saveSuccessMsg();
        },
        error: function (e) {
            removeModal();
            saveFailMsg();
        },complete:function () {
            removeModal();
            $('#sdyy').val('');
            layer.close(index);
        }
    });
}

function querySdbdczs(data){
    var result='';
    data.cqzh = data.bdcqzh;
    $.ajax({
        url: BASE_URL + '/bdczssd/sdzt',
        type: "POST",
        data: JSON.stringify(data),
        contentType: 'application/json',
        dataType: "json",
        async:false,
        success: function (data) {
            result=data;
        },
        error: function (e) {
            delAjaxErrorMsg(e);
        }
    });
    return result;
}