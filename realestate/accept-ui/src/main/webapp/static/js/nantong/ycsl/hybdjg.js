// 获取字典信息
var zdList=[];
//接口返回婚姻比对信息
var hybdxx={};
var sqrid = getQueryString("sqrid");
var gzlslid = getQueryString("gzlslid");
var qlrzjh = getQueryString("qlrzjh");
var qlrmc = getQueryString("qlrmc");
layui.use(['jquery', 'layer', 'form', 'element','laytpl'],function () {
    $ = layui.jquery;
    layer = layui.layer;
    form = layui.form;
    element = layui.element;
    laytpl = layui.laytpl;


    var qlrmc = getQueryString("qlrmc");
    var hyzt = getQueryString("hyzt");


    var param = {
        sqrid: sqrid,
        sqrxm: qlrmc,
        sqrzjh: qlrzjh,
        hyzt: hyzt
    };
    addModel();
    setTimeout(function () {
        try {
            $.when(loadZdData()).done(function () {
                hyxxCompareResult(param);
            })
        } catch (e) {
            removeModal();
            delAjaxErrorMsg(e, "加载失败");
            return;
        }
    }, 10);



});

function loadZdData() {
    //获取字典
    getCommonZd(function (data) {
        if (isNotBlank(data)) {
            zdList = data;
        }
    });
}

/**
 * 获取婚姻比对结果
 * @param param
 */
function hyxxCompareResult(param) {
    $.ajax({
        url: getContextPath() + "/slym/jtcy/nantong/compareHyxx",
        type: 'POST',
        async: false,
        dataType: 'json',
        contentType: "application/json",
        data: JSON.stringify(param),
        success: function (data) {
            removeModal();
            if(data){
                hybdxx =data;
                generateHyxx(data);
            }

        }, error: function (xhr, status, error) {
            removeModal();
            delAjaxErrorMsg(xhr)
        }
    });
}

// 组织婚姻信息到页面
function generateHyxx(data) {
    var znlength =0;
    //以多的一方进行展示
    if(data.sbhyxxDTO.znJtcyList &&(!data.mzhyxxDTO.znJtcyList ||data.sbhyxxDTO.znJtcyList.length >=data.mzhyxxDTO.znJtcyList.length)){
        znlength =data.sbhyxxDTO.znJtcyList.length;
    }else if(data.mzhyxxDTO.znJtcyList &&(!data.sbhyxxDTO.znJtcyList ||data.mzhyxxDTO.znJtcyList.length >=data.sbhyxxDTO.znJtcyList.length)){
        znlength =data.mzhyxxDTO.znJtcyList.length;
    }
    var json ={
        hyxx: data,
        zd: zdList,
        znlength:znlength
    };

    var getTpl = hybdTpl.innerHTML;
    laytpl(getTpl).render(json, function (html) {
        $('.content-main').html(html);
    });
    form.render('select');
    disabledAddFa();
}

//导入婚姻比对信息
function daoruHybdxx(){
    if(hybdxx.mzhyxxDTO) {
        hybdxx.mzhyxxDTO.sqrid = sqrid;
        getReturnData("/slym/jtcy/import/hybdxx", JSON.stringify(hybdxx), "POST", function () {
            parent.loadYcslxx(parent.$('.layui-tab-item:nth-child(' + 2 + ')').data("xmid"), parent.$('.layui-tab-item:nth-child(' + 2 + ')'));
            ityzl_SHOW_SUCCESS_LAYER("导入成功");
        }, function (error) {
            delAjaxErrorMsg(error);
        })
    }
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


// 页面截图
function shotScreen(){
    // 使当前弹出页面最大化
    // var index = parent.layer.getFrameIndex(window.name);
    // parent.layer.full(index);
    // 对页面按钮进行隐藏
    hideButton();
    addTableCellWidth();
    // 防止屏幕截屏不全，页面置顶
    window.pageYoffset = 0;
    document.documentElement.scrollTop = 0;
    document.body.scrollTop = 0;
    html2canvas($('#hybdjgBody'), {
        onrendered: function(canvas) {
            screenshot = canvas.toDataURL();
            $("#screenshot").attr("src", screenshot);

            var index = layer.open({
                title: '截图',
                type: 1,
                skin: 'bdc-spf-layer',
                area: ['430px'],
                btn: ['上传', '取消'],
                content: $('#bdc-popup-small-shot'),
                yes: function (index, layero) {
                    var data = {
                        gzlslid : gzlslid,
                        base64str : screenshot,
                        pdffjmc: qlrmc + "婚姻比对结果",
                        foldName:"婚姻证明",
                    };
                    $.ajax({
                        url: getContextPath() + "/sjcl/upload/screenshot",
                        type: 'POST',
                        dataType: 'json',
                        async: false,
                        contentType: 'application/json;charset=utf-8',
                        data: JSON.stringify(data),
                        success: function (data) {
                            ityzl_SHOW_SUCCESS_LAYER("上传成功,即将关闭当前页面。");
                            setTimeout(function () {
                                layer.close(index);
                            }, 1000);
                        },
                        error: function (err) {
                            var responseText = JSON.parse(err.responseText);
                            ityzl_SHOW_WARN_LAYER(responseText.msg);
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