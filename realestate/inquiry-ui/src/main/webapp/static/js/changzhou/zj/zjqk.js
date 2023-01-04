var $, form, layer, element, table, laytpl, laydate;
var zdList = [];
var zjid = getQueryString("zjid");
var isSubmit = true;
layui.use(['jquery','form', 'laydate', 'element', 'laytpl', 'table'],function () {
    $ = layui.jquery;
    form = layui.form;
    laydate = layui.laydate;
    element = layui.element;
    laytpl = layui.laytpl;
    table = layui.table;
    layer = layui.layer;

    zdList = getMulZdList("djlx");
    // 渲染模板
    generateZjbg();
    // 渲染时间控件
    laydate.render({
        elem: '#checkDate'
    });
    form.render();


    var verifyMsg = "必填项不能为空";
    form.verify({
        required: function (value, item) {
            if (value == '') {
                $(item).addClass('layui-form-danger');
                isSubmit = false;
                verifyMsg = "必填项不能为空";
            }
        }
    });
    form.on("submit(saveData)", function () {
        if (!isSubmit) {
            layer.msg(verifyMsg, {icon: 5, time: 3000});
            isSubmit = true;
            return false;
        } else {
            addModel();
            setTimeout(function () {
                saveZjbg();
            }, 10);
            return false;
        }
    });

    form.on('select(zjzt)', function(data){
        if(data.value == "-1"){
            $("#spyj").attr("lay-verify", "required");
        }else{
            $("#spyj").removeAttr("lay-verify", "required");
        }
    });
});

/**
 *  加载质检报告
 */
function generateZjbg(){
    addModel();
    $.ajax({
        url: getContextPath() + "/rest/changzhou/zjhc/zjqk/"+zjid,
        type: 'GET',
        dataType: 'json',
        async: false,
        success: function (data) {
            console.info(data);
            removeModal();
            if(isNotBlank(data)){
                var getTpl = zjqkTpl.innerHTML, view = document.getElementById('zjqkView');
                laytpl(getTpl).render(data[0], function(html){
                    view.innerHTML = html;
                });
                disabledAddFa();
            }else{
                failMsg("未获取到质检信息");
            }
        },
        error: function (xhr, status, error) {
            removeModal();
            delAjaxErrorMsg(xhr);
        }
    });

}

/**
 * 保存质检报告信息
 */
function saveZjbg(){
    var bdcZjxxDTO = {};
    // 获取质检信息
    $(".zjxx").serializeArray().forEach(function (item, index) {
        bdcZjxxDTO[item.name] = item.value;
    });
    bdcZjxxDTO.zjid = zjid;
    console.info(bdcZjxxDTO);
    $.ajax({
        url: getContextPath() + "/rest/v1.0/zjhc/zjxx/mx",
        type: 'PUT',
        contentType: "application/json",
        data: JSON.stringify(bdcZjxxDTO),
        success: function (data) {
            removeModal();
            ityzl_SHOW_SUCCESS_LAYER("保存成功");
            setTimeout(function(){
                var index = parent.layer.getFrameIndex(window.name);
                parent.layer.close(index);
            },500);
        },
        error: function (xhr, status, error) {
            removeModal();
            delAjaxErrorMsg(xhr);
        }
    });
}

// 弹窗关闭方法
function cancel(){
    parent.layer.closeAll();
}

/**
 * 字典项转换
 * @param name 字典项名称
 * @param zdx 实际值
 * @returns {string} 转换后的名称
 */
function getZdMc(name, zdx) {
    var zdmc = "";
    var zd = zdList[name];
    if(isNotBlank(zd)){
        $.each(zd, function (index, item) {
            if(item.DM == zdx) {
                zdmc = item.MC;
            }
        });
    }
    return zdmc;
}
