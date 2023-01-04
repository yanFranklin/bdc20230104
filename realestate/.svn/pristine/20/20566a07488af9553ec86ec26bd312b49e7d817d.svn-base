/**
 * Created by Administrator on 2019/1/21.
 * 合肥js
 */
//转发需要根据lcPageEdition判断显示哪个send.html
var lcPageEdition = 'hf';
var iSabandon = 'hide';
/**
 * 显示撤销按钮的节点
 */
var showAandonTaskNames = ["受理", "审核", "登簿制证"];

function webSocket(username) {
    var websocket;
    // 首先判断是否 支持 WebSocket
    if ('WebSocket' in window) {
        websocket = new WebSocket("ws://" + window.location.host + getContextPath() + "/wsInfo?username=" + username + "&type=TODO");
    } else if ('MozWebSocket' in window) {
        websocket = new MozWebSocket("ws://" + window.location.host + getContextPath() + "/wsInfo?username=" + username + "&type=TODO");
    } else {
        websocket = new SockJS("ws://" + window.location.host + getContextPath() + "/wsInfo?username=" + username + "&type=TODO");
    }
    // 打开连接时
    websocket.onopen = function (event) {
        console.log(" websocket.onopen");
    };
    // 收到消息时
    websocket.onmessage = function (event) {
        var msg = $.parseJSON(event.data);
        if (msg.msgType === 'CUSTOM') {
            var dataMsg = [];
            var msgId = [];
            dataMsg.push(msg.msgContent);
            msgId.push(msg.msgId);
            strongTips(dataMsg,function () {
                $.ajax({
                    type: "PUT",
                    url: getContextPath() + "/rest/v1.0/msg",
                    contentType: "application/json;charset=utf-8",
                    data: JSON.stringify(msgId),
                    success: function () {
                    }, error: function (e) {
                    }
                });
            });
        } else {
            bottomShowTips(msg.msgTypeName + "(" + msg.msgTitle + ")");
        }
    };
    websocket.onerror = function (event) {
        console.log("websocket.onerror");
    };
    websocket.onclose = function (event) {
        console.log("websocket.onclose");
    };
}

/**
 * 判断当前流程是否显示撤销
 */
function isAbandon(gzlslid) {
    $.ajax({
        type: "GET",
        url: getContextPath() + "/rest/v1.0/workflow/process-instances/abandon?gzlslid=" + gzlslid,
        async: false,
        success: function (data) {
            if (data === 'success') {
                iSabandon = "show";
            }
        }, error: function (e) {
            response.fail(e, '');
        }
    });
}