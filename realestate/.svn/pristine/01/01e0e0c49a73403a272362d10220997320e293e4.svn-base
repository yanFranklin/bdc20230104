

//上传PDF
function uploadPdf(gzlslid,dylx,fjmc,key,synr) {
    addModel();
    var dylxcs= $.getUrlParam("dylx");
    if(!isNullOrEmpty(dylxcs)){
        //如果传参dylx,按传参来
        dylx =dylxcs;
    }
    var bdcGxjkPdfDTO ={};
    bdcGxjkPdfDTO.gzlslid =gzlslid;
    bdcGxjkPdfDTO.dylx =dylx;
    bdcGxjkPdfDTO.pdffjmc =fjmc;
    bdcGxjkPdfDTO.key =key;
    bdcGxjkPdfDTO.synr =synr;
    $.ajax({
        url:"/realestate-inquiry-ui/rest/v1.0/gx/pdf",
        type: "POST",
        contentType: 'application/json',
        data: JSON.stringify(bdcGxjkPdfDTO),
        async:false,
        success: function (data) {
            removeModal();
            ityzl_SHOW_SUCCESS_LAYER("PDF上传成功");

        },
        error: function (xhr, status, error) {
            removeModal();
            delAjaxErrorMsg(xhr)
        }
    });

}

function saveJkDataToRedis(jkData) {
    var key = "";
    if(jkData) {
        $.ajax({
            url: "/realestate-inquiry-ui/rest/v1.0/gx/redis",
            type: "POST",
            contentType: 'application/json',
            data: JSON.stringify(jkData),
            async: false,
            success: function (data) {
                if (data) {
                    key = data;

                }
            },
            error: function (xhr, status, error) {
                removeModal();
                delAjaxErrorMsg(xhr)
            }
        });
    }
    return key;

}

function dealCxjgxx(result,jkname) {
    if (parent && 'function' === typeof (parent.dealCxjg)) {
        parent.dealCxjg(result,jkname);
    }


}
