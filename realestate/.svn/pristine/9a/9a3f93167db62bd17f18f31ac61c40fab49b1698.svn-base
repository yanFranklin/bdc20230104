layui.config({
    base: '../../../static/lib/form-select/'
}).extend({
    formSelects: 'formSelects-v4'
});
layui.use(['jquery','layer','form','table','element'], function() {
    var $ = layui.jquery,
        layer = layui.layer,
        table = layui.table,
        element = layui.element,
        laytpl = layui.laytpl,
        form = layui.form;
    // 请求方式
    var requestMethodOption = "<option value='POST'>POST</option><option value='GET'>GET</option>";
    $("#requestMethod").append(requestMethodOption);
    // 是否记录日志
    var logFlagOption = "<option value='0'>是</option><option value='1'>否</option>";
    $("#logFlag").append(logFlagOption);
    // 日志记录方式
    var logTypeOption = "<option value='1'>ES</option><option value='0'>数据库</option>";
    $("#logType").append(logTypeOption);
    // 消费方(共享部门)下拉框
    var consumer = getConsumer("gxbmbz");
    var consumerOption = "<option value=''>" + '请选择' + "</option>";
    if (consumer.length > 0) {
        consumer.forEach((v, i) => {
            consumerOption += "<option value='" + v.value + "'>" + v.name + "</option>";
        });
    }
    $("#jkxff").append(consumerOption);
    form.render('select');
    $(function () {
        $('.bdc-form-div').on('click', '.requesParam', function () {
            var $thisTextarea = $(this).siblings('textarea');
            var fjContent = $thisTextarea.val();
            layer.open({
                title: '请求参数',
                type: 1,
                area: ['960px'],
                btn: ['保存'],
                content: $('#fjPopup')
                , yes: function (index, layero) {
                    //提交 的回调
                    $thisTextarea.val($('#fjPopup textarea').val());
                    // if(isJSON($('#fjPopup textarea').val())){
                    //     $('#fjPopup textarea').val('');
                        layer.close(index);
                    // }
                }
                , btn2: function (index, layero) {
                    //取消 的回调
                    $('#fjPopup textarea').val('');
                }
                , cancel: function () {
                    //右上角关闭回调
                    $('#fjPopup textarea').val('');
                }
                , success: function () {
                    $('#fjPopup textarea').val(fjContent);
                }
            });
        });
    })

    form.on('submit(insert)', function(data){
        var param = {
            jkdz: $("#jkdz").val(),
            cssl: $("#requestParam").val(),
            jkqqfs: $("#requestMethod").val(),
            jkmc: $("#jkmc").val(),
            jkms: $("#jkms").val(),
            jkxff: $("#jkxff").val(),
            jkyy: $("#jkyy").val(),
            sfjlrz: $("#logFlag").val(),
            rzjlfs: $("#logType").val()
        };
        if(typeof param.jkdz == "undefined" || param.jkdz == null || param.jkdz === ""){
            layer.msg("接口地址不能为空!", {icon: 5, time: 3000});
            return;
        }
        if(typeof param.jkqqfs == "undefined" || param.jkqqfs == null || param.jkqqfs === ""){
            layer.msg("请求方式不能为空!", {icon: 5, time: 3000});
            return;
        }
        if(typeof param.cssl == "undefined" || param.cssl == null || param.cssl === ""){
            layer.msg("请求参数不能为空!", {icon: 5, time: 3000});
            return;
        }
        if(typeof param.jkmc == "undefined" || param.jkmc == null || param.jkmc === ""){
            layer.msg("接口名称不能为空!", {icon: 5, time: 3000});
            return;
        }
        if(typeof param.jkxff == "undefined" || param.jkxff == null || param.jkxff === ""){
            layer.msg("接口消费方不能为空!", {icon: 5, time: 3000});
            return;
        }
        // if(typeof param.jkyy == "undefined" || param.jkyy == null || param.jkyy === ""){
        //     layer.msg("接口应用不能为空!", {icon: 5, time: 3000});
        //     return;
        // }
        addModel();
        saveApiInfo(param);
        return true;
    })
});

function saveApiInfo(param){
    $.ajax({
        url: getContextPath() + "/rest/v1.0/apiManagement/insert/old/api",
        type: 'POST',
        async: false,
        dataType: "json",
        data: JSON.stringify(param),
        contentType: "application/json",
        success: function (data) {
            if (data && data.success){
                removeModal();
                var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
                window.parent.location.reload()
                parent.layer.msg("补录成功");
                parent.layer.close(index);
            }else {
                removeModal();
                layer.msg(data.fail.message, {icon: 5, time: 3000});
                return false;
            }
        }, error: function (xhr, status, error) {
            removeModal();
            delAjaxErrorMsg(xhr);
            return false;
        }
    });
}

/**
 * 校验json
 * @param str
 */
function isJSON(str) {
    if (typeof str == 'string') {
        try {
            var obj=JSON.parse(str);
            if(typeof obj == 'object' && obj ){
                return true;
            }else{
                layer.msg('当前输入参数非json参数');
                return false;
            }

        } catch(e) {
            console.log('error：'+str+'!!!'+e);
            if (e.message.indexOf("position ")>0){
                var lengtn = e.message.substring(e.message.indexOf("position ") + 9);
                layer.msg('当前输入参数非json格式，从这开始：' + str.substring(lengtn));
            }else if(e.message.indexOf("position:")>0){
                var lengtn = e.message.substring(e.message.indexOf("position:") + 9);
                layer.msg('当前输入参数非json格式，从这开始：' + str.substring(lengtn));
            } else {
                layer.msg('当前输入参数非json格式' + e);
            }
            return false;
        }
    }
    console.log('It is not a string!')
}

/**
 * 获取消费方字典项
 * @param dictionary
 */
function getConsumer(dictionary) {
    var consumer = [];
    $.ajax({
        url: getContextPath() + "/rest/v1.0/apiManagement/zdMul",
        dataType: "json",
        data: {
            zdNames: dictionary
        },
        async: false,
        success: function (data) {
            var gxbmbz = data.gxbmbz;
            if (typeof gxbmbz == "undefined" || gxbmbz == null) {
                return consumer;
            }
            for (var i = 0; i < gxbmbz.length; i++) {
                var data = {
                    "name": gxbmbz[i].MC,
                    "value": gxbmbz[i].DM
                };
                consumer.push(data);
            }
        },
        error: function (e) {
        }
    });
    return consumer;
}

