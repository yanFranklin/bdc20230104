var $, form, layer, element, table, laytpl, laydate;
var num = 1;
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

});

/**
 *  加载质检报告
 */
function generateZjbg(){
    addModel();
    $.ajax({
        url: getContextPath() + "/rest/v1.0/zjhc/zjmx/"+zjid,
        type: 'GET',
        dataType: 'json',
        async: false,
        success: function (data) {
            console.info(data);
            removeModal();
            if(isNotBlank(data)){
                var getTpl = bdczjbgTpl.innerHTML, view = document.getElementById('bdczjbgView');
                laytpl(getTpl).render(data, function(html){
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
    // 获取质检明细信息
    bdcZjxxDTO.zjMxDOList = getZjMx();
    console.info(bdcZjxxDTO);
    $.ajax({
        url: getContextPath() + "/rest/v1.0/zjhc/zjxx/mx",
        type: 'PUT',
        contentType: "application/json",
        data: JSON.stringify(bdcZjxxDTO),
        success: function (data) {
            removeModal();
            ityzl_SHOW_SUCCESS_LAYER("保存成功");
        },
        error: function (xhr, status, error) {
            removeModal();
            delAjaxErrorMsg(xhr);
        }
    });
}

/**
 * 获取质明细信息
 */
function getZjMx(){
    // 1、 处理页面 radio 框的 name 值
    refreshRadioName();
    // 2、获取质检情况信息
    var zjmxArray = $(".zjmx").serializeArray();
    var zjmx = {}, zjmxList = [];
    var sxh = 0;
    for (var j = 0; j < zjmxArray.length; j++) {
        var name = zjmxArray[j].name;
        zjmx[name] = zjmxArray[j].value;
        if ((j + 1 < zjmxArray.length && zjmxArray[j + 1].name === 'zjnr') || j + 1 == zjmxArray.length) {
            zjmx.zjid = zjid;
            zjmx.sftg = $("input[name='sftg"+sxh+"']:checked").val();
            zjmx.sxh = sxh++;
            zjmxList.push(zjmx);
            zjmx = {};
        }
    }
    return zjmxList;
}

/**
 * 更新单选框name值
 */
function refreshRadioName(){
    $(".zjmx_radio").each(function(index,item){
        $(item).find(".zjmx").each(function(){
            $(this).attr("name", "sftg"+index);
        });
    });
}

// 新增
function newZjqk() {
    num++;
    var rowspanCount = $('#changeRowspan').attr('rowspan');
    var getTpl = newZjqkTpl.innerHTML;
    laytpl(getTpl).render(rowspanCount, function(html){
        $(".spyjTr").before(html);
    });
    rowspanCount++;
    $('#changeRowspan').attr('rowspan', rowspanCount);
    form.render();
}

// 删除
function deleteZjqk(obj) {
    var deleteIndex = layer.open({
        type: 1,
        skin: 'bdc-small-tips',
        title: '确认删除',
        area: ['320px'],
        content: '是否确认删除？',
        btn: ['确定', '取消'],
        btnAlign: 'c',
        yes: function () {
            $(obj).parent().parent().remove();
            let rowspanCount = $('#changeRowspan').attr('rowspan');
            rowspanCount--;
            $('#changeRowspan').attr('rowspan', rowspanCount);
            layer.close(deleteIndex);
            form.render();
            refreshRadioName();
        },
        btn2: function () {
            //取消
            layer.close(deleteIndex);
        }
    });


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

// 签名
function sign(){
    $.ajax({
        url: getContextPath() + "/rest/v1.0/zjhc/zjxx/qm",
        type: 'GET',
        dataType: 'json',
        success: function (data) {
            removeModal();
            if(isNotBlank(data) && isNotBlank(data.qmxxid)){
                $("#qmxx_img").attr('src', data.qmtpdz);
                $("#qmxxid").val(data.qmxxid);
            }else{
                failMsg("签名失败。");
            }
        },
        error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr);
        }
    });
}
// 查看项目详情
function showDetails(){
    var gzlslid = $("#gzlslid").val();
    var slbh = $("#slbh").val();
    window.open("/realestate-portal-ui/view/new-page.html?name=" + gzlslid + "&type=list", slbh);
}