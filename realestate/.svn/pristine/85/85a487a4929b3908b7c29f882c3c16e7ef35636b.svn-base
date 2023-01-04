$("#fcfht").click(function () {
    var imgElementSrc = $("#fcfhtImg").attr("src");
    var fwDcbIndex = $("#fwDcbIndex").val();
    var djid = $("#djid").val();
    var hslx = $("#hslx").val();
    if (!imgElementSrc) {
        addModel();
        // 查询 户室图
        $.ajax({
            url: "../djdcb/hsthsanddz",
            data: {
                bdcdyh: bdcdyh,
                qjgldm:qjgldm
            },
            success: function (data) {
                if (data) {
                    renderTpl("fcfhtUploadTpl","fcfhtImgDiv",{base64: "data:image/jpg;base64," + data});
                }
                else{
                    $("#zoomIn").hide();
                    $("#zoomOut").hide();
                    renderTpl("fcfhtUploadTpl", "fcfhtImgDiv", {});
                }
                removeModal();
            },
            error: function (xhr, status, error) {
                delAjaxErrorMsg(xhr, this)
            }
        });
    }
});

function zoomIn() {
    var img = document.getElementById("fcfhtImg");
    if (img.width < ($(window).width() * 0.8)) {
        img.width = img.width * 1.2;
        img.height = img.height * 1.2;
    } else {
        layui.layer.msg('<img src="../static/lib/bdcui/images/warn-small.png" alt="">' + "已经最大了");
    }
}

function zoomOut() {
    var img = document.getElementById("fcfhtImg");
    img.width = img.width / 1.2;
    img.height = img.height / 1.2;
}


//监听鼠标滚动事件放大缩小图片
window.onmousewheel = function (e) {
    if (e.wheelDelta < 0) {
        zoomOut()
    } else if (e.wheelDelta > 0) {
        zoomIn()
    }
}



