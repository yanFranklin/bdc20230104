/**
 * @author "<a href="mailto:zhuyong@gtmap.cn>yousiyi</a>"
 * @version 1.0, 2018/12/14
 * @description 建设用地使用权量化JS
 */
    // 获取参数
var gzlslid = $.getUrlParam('processInsId');
var lhdyqlzt = $.getUrlParam('lhdyqlzt');
var lhsdqlzt = $.getUrlParam('lhsdqlzt');
var type = $.getUrlParam('type');
// 获取表单权限参数readOnly
var readOnly = $.getUrlParam('readOnly');
var form, layer, laytpl;
layui.use(['element', 'form', 'jquery', 'laytpl', 'layer'], function () {
    var $ = layui.jquery;
    form = layui.form;
    layer = layui.layer;
    laytpl = layui.laytpl;

    $(function () {
        getLhxx();
        getLhfjModel();
    });

});

/**
 * 获取量化数据
 */
function getLhxx(){
    $.ajax({
        url: BASE_URL + '/jsyd/lhxx/' + gzlslid,
        type: "GET",
        dataType: "json",
        success: function (data) {
            if (data) {
                // 工程进度代码转名称
                for(var i=0; i<data.length; i++){
                    data[i].gcjdmc ='';
                    if(data[i].gcjd =='1'){
                        data[i].gcjdmc="拟建";
                    }
                    if(data[i].gcjd =='2'){
                        data[i].gcjdmc="在建";
                    }
                    if(data[i].gcjd =='3'){
                        data[i].gcjdmc="竣工";
                    }
                    if(data[i].gcjd =='4'){
                        data[i].gcjdmc="首登";
                    }
                    if(data[i].gcjd =='5'){
                        data[i].gcjdmc="已入网";
                    }
                }
                generateLh(data);
            }
        }
    });
}

/**
 * 获取量化附记模板内容
 */
function getLhfjModel(){
    addModel();
    $.ajax({
        url: BASE_URL + '/jsyd/lhxx/init/lhfj',
        data:{gzlslid: gzlslid},
        type: "GET",
        dataType: "json",
        success: function (data) {
            removeModel();
            if (data) {
                $("#lhfjxx").html("");
                var i = 0;
                $.each(data, function(index, value){
                    if(i == 0){
                        $("#lhfjxx").append("<option value='" + index + "'  selected>" + value + "</option>");
                    }else{
                        $("#lhfjxx").append("<option value='" + index + "' >" + value + "</option>");
                    }
                    i++;
                });
                form.render("select");
            }
        },
        error: function(e){
            removeModel();
            delAjaxErrorMsg(e);
        }
    });
}

/**
 * 组织量化表单内容
 */
function generateLh(data){
    var getTpl = lhTpl.innerHTML;
    laytpl(getTpl).render(data, function (html) {
        $(".layui-table tbody").html(html);
    });
    form.render();
}

/**
 * 获取选择的附记模板，向附记中追加量化附记信息
 */
function saveLhxxFj(){
    var lhfjxx = $("#lhfjxx").val();
    console.info(lhfjxx);
    $.ajax({
        url: getContextPath()+"/rest/v1.0/jsyd/lhxx/sclhfj",
        data: {gzlslid: gzlslid, model: lhfjxx},
        type: 'GET',
        dataType: 'json',
        success: function (data) {
            removeModel();
            successMsg("保存成功");
            window.parent.postMessage({refreshFj:true},'*');
        },
        error: function (err) {
            removeModel();
            delAjaxErrorMsg(err);
        }
    });
}

/**
 * 选择建设用地使用权量化信息
 */
function xzzrz(){
    var url = getContextPath()+ "/view/lh/zrzlbxx.html?gzlslid="+gzlslid;
    if(isNotBlank(lhdyqlzt)){
        url += "&lhdyqlzt=" + lhdyqlzt;
    }
    if(isNotBlank(lhsdqlzt)){
        url += "&lhsdqlzt=" + lhsdqlzt;
    }
    var index = layer.open({
        type: 2,
        title: "选择建设用地自然幢",
        area: ['1300px', '600px'],
        fixed: false, //不固定
        maxmin: true, //开启最大化最小化按钮
        content:  url,
    });
    layer.full(index);
}

/**
 * 删除建设用地使用权量化信息
 */
function removeZrz(){
    layer.confirm("是否删除量化信息？",function(index){
        addModel();
        $.ajax({
            url: getContextPath()+"/rest/v1.0/jsyd/lhxx?gzlslid=" + gzlslid,
            type: 'DELETE',
            dataType: 'json',
            success: function (data) {
                getLhxx();
                removeModel();
                successMsg("删除成功");
            },
            error: function (err) {
                removeModel();
                delAjaxErrorMsg(err);
            }
        });
    });
}


// 页面截图
function shotScreen(){
    hideButton();
    addTableCellWidth();
    // 防止屏幕截屏不全，页面置顶
    window.pageYoffset = 0;
    document.documentElement.scrollTop = 0;
    document.body.scrollTop = 0;
    html2canvas($('#jsydsyqlhBody'), {
        onrendered: function(canvas) {
            screenshot = canvas.toDataURL();
            // 需要在页面创建一个div，用于渲染图片
            $("#screenshot").attr("src", screenshot);
            // 页面图片预览操作
            var index = layer.open({
                title: '截图',
                type: 1,
                // skin: 'bdc-spf-layer',
                area: ['430px'],
                btn: ['上传', '取消'],
                content: $('#bdc-popup-small-shot'),
                yes: function (index, layero) {
                    var data = {
                        gzlslid : gzlslid,
                        base64str : screenshot,
                        foldName : "量化信息",
                        pdffjmc : "勾选的量化楼幢信息"
                    };
                    // 生成量化逻辑幢pdf文件
                    generateLhLjzPdf();
                    $.ajax({
                        url: getContextPath() + "/rest/v1.0/fjcl/upload/screenshot",
                        type: 'POST',
                        dataType: 'json',
                        async: false,
                        contentType: 'application/json;charset=utf-8',
                        data: JSON.stringify(data),
                        success: function (data) {
                            ityzl_SHOW_SUCCESS_LAYER("上传成功");
                            layer.close(index);
                        },
                        error: function (err) {
                            var responseText = JSON.parse(err.responseText);
                            layer.msg(responseText.msg);
                        }
                    });
                }, end : function (){
                    location.reload();
                }
            });
           layer.full(index);
        }
    });
}

function generateLhLjzPdf(){
    $.ajax({
        url: getContextPath() + "/rest/v1.0/jsyd/lhxx/lhljz/pdf",
        type: 'GET',
        dataType: 'json',
        contentType: 'application/json;charset=utf-8',
        data: {gzlslid: gzlslid},
        success: function (data) {
        },
        error: function (err) {
            var responseText = JSON.parse(err.responseText);
            layer.msg(responseText.msg);
        }
    });
}

// 隐藏页面按钮
function hideButton(){
    $("#buttonArea").hide();
    $("button").hide();
}
// 为TD TH 增加宽度属性
function addTableCellWidth(){
    var getTh = $('.layui-table-view .layui-table th');
    $.each(getTh, function(i, th){
        $(th).css('width', $(th).width());
    });
    var getTd = $('.layui-table-view .layui-table td');
    $.each(getTd, function(i, td){
        $(td).css('width', $(td).width());
    });
}