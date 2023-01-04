/**
 * Created by Ypp on 2019/10/12.
 * 颜色配置js
 */
layui.use(['colorpicker','form'], function(){
    var colorpicker = layui.colorpicker,
        $ = layui.jquery,
        form = layui.form;

    var colorList = [
        {type:"CONSTANT",name: "bdc-wdj",value:"WDJ_color", constant:"#ecb79d!important"},
        {type:"CONSTANT",name: "bdc-yg",value:"YG_color", constant:"rgb(255,255,153)!important"},
        {type:"CONSTANT",name: "bdc-dj",value:"DJ_color", constant:"rgb(102,204,255)!important"},
        {type:"CONSTANT",name: "bdc-zx",value:"ZX_color", constant:"rgb(192,192,192)!important"},
        {type:"CONSTANT",name: "bdc-ks",value:"KS_color", constant:"rgb(0,255,0)!important"},
        {type:"CONSTANT",name: "bdc-ys",value:"YS_color", constant:"rgb(255,255,0)!important"},
        {type:"CONSTANT",name: "bdc-cq",value:"CQ_color", constant:"rgb(255,0,255)!important"},
        {type:"CONSTANT",name: "bdc-wg",value:"WG_color", constant:"rgb(140,70,20)!important"},
        {type:"CONSTANT",name: "bdc-az",value:"AZ_color", constant:"rgb(210,105,30)!important"},
        {type:"CONSTANT",name: "bdc-bl",value:"BL_color", constant:"rgb(128,128,128)!important"},
        {type:"CONSTANT",name: "bdc-fpt",value:"FPT_color", constant:"rgb(128,128,0)!important"},
        {type:"CONSTANT",name: "bdc-zjgcdy",value:"ZJGCDY_color", constant:"rgb(153,165,124)!important"},
        {type:"CONSTANT",name: "bdc-ydya",value:"YDYA_color", constant:"rgb(170,205,6)!important"},
        {type:"CONSTANT",name: "bdc-dya",value:"DYA_color", constant:"rgb(0,153,68)!important"},
        {type:"CONSTANT",name: "bdc-cf",value:"CF_color", constant:"rgb(238,132,125)!important"},
        {type:"CONSTANT",name: "bdc-ycf",value:"YCF_color", constant:"rgb(229,0,18)!important"},
        {type:"CONSTANT",name: "bdc-sd",value:"SD_color", constant:"rgb(229,0,127)!important"},
        {type:"CONSTANT",name: "bdc-yy",value:"YY_color", constant:"rgb(239,130,0)!important"},
        {type: "CONSTANT", name: "bdc-dyi", value: "DYI_color", constant: "rgb(133,94,110)!important"},
        {type: "CONSTANT", name: "bdc-jzq", value: "JZQ_color", constant: "#13b1c4 !important"},
        {type: "CONSTANT", name: "bdc-zs", value: "ZS_color", constant: "rgb(171, 26, 26)!important"},
        {type: "CONSTANT", name: "bdc-zm", value: "ZM_color", constant: "rgb(66, 177, 30)!important"},
        {type: "CONSTANT", name: "bdc-ba", value: "BA_color", constant: "rgb(66, 177, 30)!important"},
        {type: "CONSTANT", name: "bdc-bfz", value: "BFZ_color", constant: "rgb(214, 182, 220)!important"}
    ];

    addModel();
    $.ajax({
        url: "../lpb/list/colorPz",
        type:"post",
        async:false,
        success: function (data) {
            if(data){
                for(var key in data) {
                    colorList.forEach(function (v,i) {
                        if(key == v.value){
                            if(v.constant.indexOf("!important")>-1){
                                v.constant=data[key].constant;
                            }else{
                                v.constant=data[key].constant+"!important";
                            }
                        }
                    });
                }
            }
            removeModal();
        },
        error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr,this);
        }
    });
    colorList.forEach(function (v,i) {
        colorpicker.render({
            elem: '.'+ v.name +'-config'
            ,color: v.constant
            ,done: function(color){
                if(i < 4){
                    $('.' + v.name).css('color',color);
                }else {
                    $('.' + v.name).css('background-color',color);
                }
                v.constant=color+"!important";
            }
        });
    });

    $('#saveColor').on('click',function () {
        addModel();
        $.ajax({
         url: "../lpb/update/status/color",
         type:"post",
         contentType: "application/json;charset=UTF-8",
         data: JSON.stringify(colorList),
         success: function (data) {
            removeModal();
            layer.msg("保存成功");
         },
         error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr,this);
         }
         });
    });
});