layui.use(['jquery','layer'], function(){
    var $ = layui.jquery,
    layer = layui.layer;
    var callNumber;
    $(function(){
        $.ajax({
            type: "GET",
            url: getContextPath() + "/rest/v1.0/user/callnumber",
            async: false,
            success: function (callNumberUrl) {
                if (!isNullOrEmpty(callNumberUrl)){
                    $("#jhDefault").removeClass('bdc-hide');
                    var _ws = new csWS();
                    _ws.url = 'ws://' + callNumberUrl;
                   //注册接收服务端消息的方法 服务端数据位于event.data字段
                    _ws.onmessage = function (event) {
                        var msg = $.parseJSON(event.data);
                        console.info(msg);
                        if (msg != null) {
                            if (msg.WaitNumber != null) {
                                $(".bdc-current-num-pd-details").text(msg.WaitNumber);
                            }
                            if (msg.PauseMessage != null) {
                                $(".bdc-current-num-details").text(msg.PauseMessage);
                            }
                            if (msg.CancelPauseMessage != null) {
                                $(".bdc-current-num-details").text(msg.CancelPauseMessage);
                            }
                            if (msg.CallNumber != null) {
                                callNumber = msg.CallNumber;
                                if (msg.PauseMessage == null && msg.CancelPauseMessage == null) {
                                    $(".bdc-current-num-details").text(msg.CallNumber);
                                    try {
                                        ShowInformationPjq(callNumber);
                                    } catch (e) {
                                        console.info(e);
                                    }
                                }
                            }
                        }
                        removeJhModal();
                    };
                    //启动链接
                    _ws.start();

                    // 窗口登录
                    var initdevice = "{\"FunctionName\":\"initdevice\"}";
                    $("#initdevice").click(function () {
                        // jhAddModel();
                        _ws.sendMessage(initdevice);
                        try {
                            pjqLogin();
                        } catch (e) {
                            console.info(e);
                        }
                    });
                    // 叫号
                    var autocallnumber = "{\"FunctionName\":\"autocallnumber\"}";
                    $("#sendMessage").click(function () {
                        jhAddModel();
                        _ws.sendMessage(autocallnumber);
                    });
                    // 重新叫号
                    var autorecallnumber = "{\"FunctionName\":\"autorecallnumber\"}";
                    $("#recallnumber").click(function () {
                        jhAddModel();
                        _ws.sendMessage(autorecallnumber);
                        try {
                            ShowInformationPjq(callNumber);
                        } catch (e) {
                            console.info(e);
                        }
                    });
                    // 暂停叫号
                    var autopause = "{\"FunctionName\":\"autopause\"}";
                    $("#pause").click(function () {
                        jhAddModel();
                        _ws.sendMessage(autopause);
                        try {
                            StopServicePjq();
                        } catch (e) {
                            console.info(e);
                        }
                    });
                    // 取消暂停
                    var autocancelpause = "{\"FunctionName\":\"autocancelpause\"}";
                    $("#cancelpause").click(function () {
                        jhAddModel();
                        _ws.sendMessage(autocancelpause);
                        try {
                            RestoreServicePjq();
                        } catch (e) {
                            console.info(e);
                        }
                    });
                    //推送广告
                    $("#gxgg").click(function () {
                        try {
                            deleteAndPush();
                        } catch (e) {
                            console.info(e);
                        }
                    });

                    var ywzy = "{\"FunctionName\":\"transfer\",\"DestbusCode\":\"Q\"}";
                    //业务转移
                    $("#ywzy").click(function () {
                        try {
                            var confirms = layer.open({
                                title: '窗口提醒',
                                type: 1,
                                area: ['430px', '300px'],
                                btn: ['确定', '取消'],
                                content: $('#ywzyPopup')
                                , yes: function (index, layero) {
                                    //提交 的回调
                                    var value = $('#ywzyPopup #ywzy-select').val();
                                    if (value && value.length > 0) {
                                        ywzy = "{\"FunctionName\":\"transfer\",\"DestbusCode\":\"" + value + "\"}";
                                        jhAddModel();
                                        console.info(ywzy);
                                        _ws.sendMessage(ywzy);
                                        layer.close(confirms);
                                    }else {
                                        failMsg('请选择！');
                                    }
                                }
                                ,btn2: function(index, layero){
                                    //取消 的回调
                                    layer.close(confirms);
                                }
                                ,cancel: function(){
                                    //右上角关闭回调
                                    layer.close(confirms);
                                }
                                ,success: function () {
                                    queryYwzySelct();
                                }
                            });
                        } catch (e) {
                            console.info(e);
                        }
                    });

                    try {
                        // 叫号功能自动刷新
                        setInterval(function () {
                            var updatewaitnumber = "{\"FunctionName\":\"updatewaitnumber\"}";
                            _ws.sendMessage(updatewaitnumber);
                        }, 10000);
                    } catch (e) {
                        console.info(e);
                    }
                }
            }
        });

        $('#jhDefault').on('click',function () {
            layer.open({
                title: '窗口提醒<i class="layui-icon layui-icon-close"></i>',
                skin: 'bdc-jh-layer',
                type: 1,
                area: ['320px','290px'],
                offset: 'rt',
                shade: 0,
                content: $('#callNumber')
                ,cancel: function(){
                    //右上角关闭回调
                    //return false 开启该代码可禁止点击该按钮关闭
                }
                ,success: function () {

                }
            });
        });
    });
});

//websocket 类
var csWS = function () {
    this.ws = null;
    this.url = null;
    // 客户端接收服务端数据时触发
    this.onmessage = function () {};
    // 连接建立时触发
    this.onopen = function () {};
    // 连接关闭时触发
    this.onclose = function () {};
    // 通信发生错误时触发
    this.onerror = function () {};
};
// 客户端向服务器发送数据
csWS.prototype.sendMessage = function (message) {
    if (this.ws != null && this.ws.readyState == 1) {
        this.ws.send(message);
    }
};
csWS.prototype.start = function () {//啟動鏈接
    this.connect();
    this.conncheckstatus();
    this.heartbeat();
};
//心跳
csWS.prototype.heartbeat = function () {
    var obj = this;
    setTimeout(function () {
        if (obj.ws != null && obj.ws.readyState == 1) {
            var message = "IsHere**";
            obj.ws.send(message);
        }
        setTimeout(arguments.callee, 300000);
    }, 10);
};
// 判断连接状态
csWS.prototype.conncheckstatus = function () {
    var obj = this;
    // 0 - 表示连接尚未建立;1 - 表示连接已建立;2 - 表示连接正在进行关闭;3 - 表示连接已经关闭或者连接不能打开
    if (obj.ws != null && obj.ws.readyState != 0 && obj.ws.readyState != 1) {
        obj.connect();
    }
    // 自动重连
    // setTimeout(function () {
    //     // 0 - 表示连接尚未建立;1 - 表示连接已建立;2 - 表示连接正在进行关闭;3 - 表示连接已经关闭或者连接不能打开
    //     if (obj.ws != null && obj.ws.readyState != 0 && obj.ws.readyState != 1) {
    //         obj.connect();
    //     }
    //     setTimeout(arguments.callee, 5000);
    // }, 15);
};
csWS.prototype.connect = function () {
    this.disconnect();
    //WebSocket地址，创建 webSocket 对象
    if (this.url != null && this.url != "") {
        try {
            if ("WebSocket" in window) {
                this.ws = new WebSocket(this.url);
            }
            else if ("MozWebSocket" in window) {
                this.ws = new MozWebSocket(this.url);
            }
            else {
                alert("browser not support websocket");
            }
            if (this.ws != null) {
                var that = this;
                this.ws.onopen = function (event) { that.onopen(event); };
                this.ws.onmessage = function (event) { that.onmessage(event); };
                this.ws.onclose = function (event) { that.onclose(event); };
                this.ws.onerror = function (event) { that.onerror(event); };
            }
        }
        catch (ex) {
            console.log("connect:" + ex);
        }
    }
};
csWS.prototype.disconnect = function () {
    if (this.ws != null) {
        try {
            this.ws.close();
            this.ws = null;
        }
        catch (ex) {
            this.ws = null;
        }
    }
};

// 渲染业务转移下拉框
function queryYwzySelct() {
    var $ywzy = $('#ywzyPopup #ywzy-select');
    if($ywzy.children('option').length <= 1){
        $.ajax({
            type: "GET",
            url: getContextPath() + "/rest/v1.0/config/ywzy",
            success: function (data) {
                console.info(data);
                var options = ' <option value="">请选择</option>';
                for(key in data){
                    options += '<option value="'+ data[key] +'">'+ key+'</option>';
                }
                $ywzy.append(options);
                form.render('select');
            }, error: function (e) {
                response.fail(e, '');
            }
        });
    }
}
