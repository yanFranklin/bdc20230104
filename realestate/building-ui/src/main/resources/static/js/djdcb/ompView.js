$("#omp").click(function () {
    var ompFrameSrc = $("#ompFrame").attr("src");
    if (!ompFrameSrc) {
        var bdcdyh = $("#bdcdyh").val();
        var dzwtzm = $("#dzwtzm").val();
        var bdclx = "1";
        if(dzwtzm == "F"){
            bdclx = "2";
        }
        var iframeHeight = $("body").height()-$("#liTable").height()-45;
        $("#ompFrame").height(iframeHeight);
        document.getElementById("ompFrame").src = "../omp/redirect/" + bdcdyh + "/" + bdclx;
    }
});