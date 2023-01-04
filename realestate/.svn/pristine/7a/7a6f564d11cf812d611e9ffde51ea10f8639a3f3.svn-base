
layui.use(['form', 'jquery', 'laytpl', 'element', 'laydate', 'layer'], function () {
    var form = layui.form;
    var $ = layui.jquery;
    var laytpl = layui.laytpl;
    var layer = layui.layer;

    //处理列表选择
    var tpl = $("#DmMcTpl").html();

    //获取数据
    addModel();
    $.ajax({
        url: "../djdcb/zdxxinfo",
        dataType: "json",
        data: {
            bdcdyh: bdcdyh,
            qjgldm:qjgldm
        },
        success: function (data) {
            removeModal();
            layer.closeAll();
            generateQlrList(data.qlrResponseDTOList);
            var zdxx = disposeZdsz(data.djDcbResponseDTO);
            if(jyq){
                zdxx =disposeDksz(data.djDcbResponseDTO);
            }
            form.val("zdxxform", zdxx);
            //承包宗地
            if(zdxx.cbzdDcbIndex){
                $("#pzmjmInput").show();
                $("#pzmjInput").hide();
                $("#zdmjmInput").show();
                $("#zdmjInput").hide();
            }else{
                $("#pzmjmInput").hide();
                $("#pzmjInput").show();
                $("#zdmjmInput").hide();
                $("#zdmjInput").show();
            }
            initZd($("#zdxxForm"));
            if(jyq){
                if (zdxx.jyqDkLcfDO) {
                    form.val("lcfxxForm", zdxx.jyqDkLcfDO);
                    initZd($("#lcfxxForm"));
                }
                generateJyqQlrList(zdxx.jyqDkQlrDOList);
            }
            disabledAddFa();
        },
        error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr,this)
        }
    });
});
function disposeZdsz(data) {
    if(!data){
        data = {};
    }
    var zdsz="";
    if (data.zdszd) {
        zdsz = zdsz+data.zdszd + ";"
    }
    if (data.zdszn) {
        zdsz = zdsz+ data.zdszn + ";"
    }
    if (data.zdszx) {
        zdsz = zdsz+ data.zdszx + ";"
    }
    if (data.zdszb) {
        zdsz = zdsz+ data.zdszb + ";"
    }
    data.zdsz = zdsz;
    var zdmj;
    if(data.fzmj){
        zdmj= data.fzmj
    }else {
        zdmj = data.scmj
    }
    var zdmjm;
    if(data.fzmjm){
        zdmjm= data.fzmjm
    }else {
        zdmjm = data.scmjm
    }
    data.zdmj=zdmj;
    data.zdmjm=zdmjm;
    return data;
}

function disposeDksz(data) {
    if(!data){
        data = {};
    }
    var zdsz="";
    if (data.dkszd) {
        zdsz = zdsz+data.dkszd + ";"
    }
    if (data.dkszn) {
        zdsz = zdsz+ data.dkszn + ";"
    }
    if (data.dkszx) {
        zdsz = zdsz+ data.dkszx + ";"
    }
    if (data.dkszb) {
        zdsz = zdsz+ data.dkszb + ";"
    }
    data.zdsz = zdsz;
    return data;
}

function initZd(element) {
    var zdInputList = element.find("input[zd='true']");
    if(zdInputList){
        for(var i = 0 ; i<zdInputList.length ; i++){
            var zdInput = zdInputList[i];
            var zdClass = $(zdInput).attr("class");
            var zdDm = $(zdInput).val();
            var zdVal = "";
            if(!$.isEmptyObject(zdList)){
                $.each(zdList, function (key, zdValue) {
                    if(zdClass == key){
                        for(var j = 0 ;j < zdValue.length;j++){
                            if(zdValue[j].DM == zdDm){
                                zdVal = zdValue[j].MC;
                                break;
                            }
                        }
                    }
                });
            }
            if(zdVal){
                $(zdInput).parent().find("input[type='text']").val(zdVal);
            }
        }
    }
}

//生成权利人list表单 qlrList：权利人列表 listStartWhith：从第几个开始生成 isFg：是否需要覆盖
function generateQlrList(qlrList) {
    layui.use(['form', 'jquery', 'laytpl', 'element'], function () {
        var form = layui.form;
        var $ = layui.jquery;
        var laytpl = layui.laytpl;
        var element = layui.element;
        var json = {
            data: qlrList
        }
        //获取模板
        var tpl = $("#qlrTpl").html();
        //渲染数据
        laytpl(tpl).render(json, function (html) {
            $("#qlrList").html(html);
        });
        form.render();
        element.render();
    })
}

function generateJyqQlrList(qlrList) {
    layui.use(['form', 'jquery', 'laytpl', 'element'], function () {
        var form = layui.form;
        var $ = layui.jquery;
        var laytpl = layui.laytpl;
        var element = layui.element;
        var json = {
            data: qlrList
        }
        //获取模板
        var tpl = $("#jyqqlrTpl").html();
        //渲染数据
        laytpl(tpl).render(json, function (html) {
            $("#jyqqlrList").html(html);
        });
        form.render();
        element.render();
    })
}