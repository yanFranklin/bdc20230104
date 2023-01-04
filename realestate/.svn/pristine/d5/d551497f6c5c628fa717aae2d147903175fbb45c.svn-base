var processInsId = $.getUrlParam("processInsId");
var formStateId = getQueryString("formStateId");
var readOnly = getQueryString("readOnly");

layui.use(['form', 'jquery', 'laydate', 'element', 'layer', 'laytpl'], function () {
    var $ = layui.jquery,
        form = layui.form,
        layer = layui.layer,
        laytpl = layui.laytpl;

    //加载水电气信息
    querySdqyw(processInsId);

    // 保存
    $("#saveSdq").click(function () {
        addModel();
        var sdqxx = [];
        $('.basic-info tr').each(function (index, item) {
            if (!$(this).hasClass("gray-tr")) {
                var sdq = {};
                $($(this).find("input,select")).each(function (index, item) {
                    sdq[item.name] = item.value;
                })
                if (isNotBlank(sdq.ywlx)) {
                    sdqxx.push(sdq);
                }
            }
        });
        if (sdqxx.length === 0) {
            removeModal();
            layer.alert("没有需要保存的数据");
            return false;
        }
        getReturnData("/sdqgh/saveSdq/" + processInsId + "?needHz=false", JSON.stringify(sdqxx), "POST", function () {
            removeModal();
            ityzl_SHOW_SUCCESS_LAYER("保存成功");

        }, function (error) {
            delAjaxErrorMsg(error);
        });

    });
});

/**
 * 查询当前流程下的已申请的水电气业务
 * @param processInsId
 */
function querySdqyw(processInsId) {
    addModel();
    url = "/realestate-inquiry-ui/sdqgh/sdqxx/" + processInsId;
    $.ajax({
        url: url,
        type: 'GET',
        success: function (data) {
            removeModal();

            if (data.bdcSdqghDOList != null && data.bdcSdqghDOList.length > 0) {
                for (var i = 0; i < data.bdcSdqghDOList.length; i++) {
                    var sdqyw = data.bdcSdqghDOList[i];
                    if (sdqyw.ywlx == "1") {
                        controlBtnStatus($("#shuigh"), sdqyw);
                    }
                    if (sdqyw.ywlx == "2") {
                        controlBtnStatus($("#diangh"), sdqyw);
                    }
                    if (sdqyw.ywlx == "3") {
                        controlBtnStatus($("#qigh"), sdqyw);
                    }

                }
            }
            $(".hzzl").each(function () {
                if (isNullOrEmpty($(this).val())) {
                    $(".hzzl").val(data.zl);
                }
            });
            getStateById(readOnly, formStateId, "sdqym");
        },
        error: function (xhr, status, error) {

        }
    });
}

/**
 * 控制按钮
 * @param item
 * @param consono
 * @param id
 */
function controlBtnStatus(item, data) {
    layui.use(['form'], function () {
        var form = layui.form;
        $(item).find('.id').val(data.id);
        $(item).find('.consno').val(data.consno);
        $(item).find('.sfbl').val(data.sfbl);
        $(item).find('.hzzl').val(data.hzzl);
        if (data.blzt === 2) {
            // 已申请
            $(item).find('.consno').attr({"disabled": "off"});
            $(item).find('.sfbl').attr({"disabled": "off"});
            $(item).find('.hzzl').attr({"disabled": "off"});
        }
        form.render();
    })

}

