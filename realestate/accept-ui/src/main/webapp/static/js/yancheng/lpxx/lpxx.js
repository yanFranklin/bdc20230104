/*获取字典数据*/
var table;
var form;
var laydate;
var $, layer, element, laytpl;
var gzlslid = getQueryString("processInsId");
var result = "";
var zdList = [];
$(function () {
    layui.use(['form', 'jquery', 'laydate', 'element', 'layer', 'laytpl'], function () {
        addModel('加载中');
        form = layui.form;
        laydate = layui.laydate;
        $ = layui.jquery;
        element = layui.element;
        layer = layui.layer;
        laytpl = layui.laytpl;
        setTimeout(function () {
            try {
                $.when(loadZdData()).done(function () {
                    loadLpxx();
                })
            } catch (e) {
                removeModal();
                delAjaxErrorMsg(e, "加载失败");
                return;
            }
        }, 10);
    });
})


function loadZdData() {
    //获取字典
    getCommonZd(function (data) {
        if (isNotBlank(data)) {
            zdList = data;
        }
    });
}

//获取楼盘信息数据
function loadLpxx() {
    //获取备案信息数据
    getReturnData("/rest/v1.0/lpxx/" + gzlslid, {}, "GET", function (data) {
        if (data) {
            // 房屋用途代码转名称
            for(var i=0; i<data.fwLjzDTOList.length;i++){
                for(var j=0;j<data.fwLjzDTOList[i].groupHsDTOList.length;j++){

                    var groupHsDTO = data.fwLjzDTOList[i].groupHsDTOList[j];
                    if(isNotBlank(groupHsDTO.ghyt)){
                        groupHsDTO.ghytmc = changeDmToMc(groupHsDTO.ghyt, zdList.fwyt);
                    }
                }
            }

            generateLpxx(data);
        }
        removeModal();
    }, function (xhr) {
        delAjaxErrorMsg(xhr);
    });
}

//加载备案合同信息数据两个模块
function generateLpxx(lpxx) {
    var json = {
        lpxx: lpxx,
    };
    var tpl = lpxxTpl.innerHTML, view = document.getElementById('lpxx');
    //渲染数据
    laytpl(tpl).render(json, function (html) {
        view.innerHTML = html;
    });
    form.render();
    renderDate(form);
}

//展现楼盘信息
function showLpb(fwDcbIndex) {
    var index = layer.open({
        type: 2,
        title: "选择楼盘表",
        area: ['1300px', '600px'],
        fixed: false, //不固定
        maxmin: true, //开启最大化最小化按钮
        content: '/building-ui/lpb/view?code=bdcres&fwDcbIndex=' + fwDcbIndex + '&gzlslid=' + gzlslid,
        cancel: function () {
        }
    });
    layer.full(index);
}
