/**
 * 上传评价js
 */
layui.use(['table', 'form', 'layer'], function () {
    var table = layui.table,
        form = layui.form,
        $ = layui.jquery,
        layer = layui.layer;
});

/**
 * 评价
 */
function pj(obj, data) {
    var c = layer.open({
        title: '请评价',
        type: 1,
        area: ['430px','280px'],
        btn: ['确定', '取消'],
        content: '<div id="bdc-popup-radio">\n' +
            '    <form class="layui-form" action="">\n' +
            '        <div class="layui-form-item pf-form-item">\n' +
            '            <div class="layui-inline" style="width:80%;margin-left: 65px;margin-top: 25px;">\n' +
            '                <label class="layui-form-label">评价：</label>\n' +
            '                <div class="layui-input-inline">\n' +
            '                </div>\n' +
            '            </div>\n' +
            '        </div>\n' +
            '    </form>\n' +
            '</div>',
        success: function () {
            var form = layui.form;
            var radio = "";
            radio += "<input type=\"radio\" name=\"pjRadio\" value=\"1\" title=\"满意\" lay-filter=\"pjRadio\"  class=\"change-radio-top\" checked>";
            radio += "<input type=\"radio\" name=\"pjRadio\" value=\"1\" title=\"比较满意\" lay-filter=\"pjRadio\"  class=\"change-radio-top\" >";
            radio += "<input type=\"radio\" name=\"pjRadio\" value=\"2\" title=\"一般\" lay-filter=\"pjRadio\"  class=\"change-radio-top\" >";
            radio += "<input type=\"radio\" name=\"pjRadio\" value=\"3\" title=\"不满意\" lay-filter=\"pjRadio\"  class=\"change-radio-top\" >";
            $('#bdc-popup-radio').find(".layui-input-inline").html(radio);
            form.render("radio");
        },
        yes: function (index, layero) {
            var pjjg = $("input[name='pjRadio']:checked").val();
            sessionStorage.setItem(data.XMID,pjjg);
            layer.close(c);
        }
        , btn2: function (index, layero) {
            //取消 的回调
            layer.close(c)
        }
        , cancel: function (index, layero) {
            //右上角关闭回调
            layer.close(c)
        }
    })
}

/**
 * 上报评价内容
 */
function sb(obj, data) {
    var myd = sessionStorage.getItem(data.XMID);
    if(!isNotBlank(myd)){
        myd = "1";
    }
    var pjjg = {
        slbh : data.SLBH,
        gzlslid : data.GZLSLID,
        myd : myd,
    };
    addModel();
    $.ajax({
        url: getContextPath() + "/pjq/saveAndTspjjg",
        type: 'POST',
        contentType: 'application/json',
        dataType: "json",
        data: JSON.stringify(pjjg),
        success: function (data) {
            removeModal();
            if(data.head.code == "0000"){
                ityzl_SHOW_SUCCESS_LAYER("评价成功");
            }else{
                ityzl_SHOW_WARN_LAYER("评价失败，"+data.head.msg);
            }
        },
        error: function (xhr, status, error) {
            removeModal();
            delAjaxErrorMsg(xhr);
        }
    });
}