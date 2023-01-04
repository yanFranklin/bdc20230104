layui.use(['form', 'laytpl', 'jquery',], function () {
    var $ = layui.jquery,
        laytpl = layui.laytpl

    var id = $.getUrlParam('id');
    var msgCode = ""
    var msgId = []
    msgId.push(id);
    addModel()
    $(function () {
        $.ajax({
            type: "GET",
            url: getContextPath() + "/rest/v1.0/msg/message/" + id,
            traditional: true,
            success: function (data) {
                msgCode = data.msgCode;

                if (data.msgContent.opinion) {
                    data.msgContent = d.msgContent.opinion;
                }else if(isNullOrEmpty(data.msgContent.opinion)&& data.msgType!= "CUSTOM"){
                    data.msgContent=""
                }
                renderTpl(data, 'xxxq', xxxqTpl.innerHTML);
                $("#download").click(function () {
                    downloadFIle();
                });
                $("#read").click(function () {
                    readMsg(msgId);
                });
                removeModal();
            }

        });

        //渲染模块数据
        function renderTpl(json, mkid, getTpl) {
            var dataView = document.getElementById(mkid);
            laytpl(getTpl).render(json, function (html) {
                dataView.innerHTML = html;
            });
        }


        function downloadFIle() {
            addModel();
            $.ajax({
                type: "POST",
                url: getContextPath() + "/rest/v1.0/msg/downloadMsgFile",
                traditional: true,
                data: {msgCode: msgCode},
                success: function (data) {
                    removeModal();
                    if (!isNullOrEmpty(data)) {
                        data.forEach(function (value) {
                            window.open(encodeURI(value.downUrl));
                        });
                    }
                }, error: function (e) {
                    removeModal();
                }
            });
        }

        //标记为已读
        function readMsg(msgId) {

            $.ajax({
                type: "PUT",
                url: getContextPath() + "/rest/v1.0/msg/readMessages",
                contentType: "application/json;charset=utf-8",
                data: JSON.stringify(msgId),
                success: function () {
                    layer.msg('标记已读成功！');
                    $("#read").hide()
                }, error: function (e) {
                }
            });
        }

    });
});




