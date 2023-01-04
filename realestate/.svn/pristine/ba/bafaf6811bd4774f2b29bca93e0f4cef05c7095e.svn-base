/**
 * Created by Ypp on 2019/10/12.
 * 颜色配置js
 */
layui.use(['colorpicker', 'form', 'jquery', 'layer', 'element', 'table', 'laytpl'], function () {
    var colorpicker = layui.colorpicker,
        $ = layui.jquery,
        form = layui.form,
        layer = layui.layer,
        element = layui.element,
        table = layui.table,
        laytpl = layui.laytpl;

    var colorList = [
        {
            type: "CONSTANT",
            name: "bdc-wdj",
            value: "WDJ_color",
            constant: "#ecb79d!important",
            font: "rgb(255,255,153)!important"
        },
        {
            type: "CONSTANT",
            name: "bdc-dj",
            value: "DJ_color",
            constant: "rgb(102,204,255)!important",
            font: "rgb(255,255,153)!important"
        },
        {
            type: "CONSTANT",
            name: "bdc-zx",
            value: "ZX_color",
            constant: "rgb(192,192,192)!important",
            font: "rgb(255,255,153)!important"
        },
        {
            type: "CONSTANT",
            name: "bdc-ba",
            value: "BA_color",
            constant: "rgb(66, 177, 30)!important",
            font: "#ecb79d!important"
        },
        {
            type: "CONSTANT",
            name: "bdc-yg",
            value: "YG_color",
            constant: "rgb(255,255,153)!important",
            font: "rgb(255,255,153)!important"
        },
        {
            type: "CONSTANT",
            name: "bdc-ks",
            value: "KS_color",
            constant: "rgb(0,255,0)!important",
            font: "#ecb79d!important"
        },
        {
            type: "CONSTANT",
            name: "bdc-ys",
            value: "YS_color",
            constant: "rgb(255,255,0)!important",
            font: "#ecb79d!important"
        },
        {
            type: "CONSTANT",
            name: "bdc-cq",
            value: "CQ_color",
            constant: "rgb(255,0,255)!important",
            font: "#ecb79d!important"
        },
        {
            type: "CONSTANT",
            name: "bdc-wg",
            value: "WG_color",
            constant: "rgb(140,70,20)!important",
            font: "#ecb79d!important"
        },
        {
            type: "CONSTANT",
            name: "bdc-az",
            value: "AZ_color",
            constant: "rgb(210,105,30)!important",
            font: "#ecb79d!important"
        },
        {
            type: "CONSTANT",
            name: "bdc-bl",
            value: "BL_color",
            constant: "rgb(128,128,128)!important",
            font: "#ecb79d!important"
        },
        {
            type: "CONSTANT",
            name: "bdc-fpt",
            value: "FPT_color",
            constant: "rgb(128,128,0)!important",
            font: "#ecb79d!important"
        },
        {
            type: "CONSTANT",
            name: "bdc-zjgcdy",
            value: "ZJGCDY_color",
            constant: "rgb(153,165,124)!important",
            font: "#ecb79d!important"
        },
        {
            type: "CONSTANT",
            name: "bdc-ydya",
            value: "YDYA_color",
            constant: "rgb(170,205,6)!important",
            font: "#ecb79d!important"
        },
        {
            type: "CONSTANT",
            name: "bdc-dya",
            value: "DYA_color",
            constant: "rgb(0,153,68)!important",
            font: "#ecb79d!important"
        },
        {
            type: "CONSTANT",
            name: "bdc-cf",
            value: "CF_color",
            constant: "rgb(238,132,125)!important",
            font: "#ecb79d!important"
        },
        {
            type: "CONSTANT",
            name: "bdc-ycf",
            value: "YCF_color",
            constant: "rgb(229,0,18)!important",
            font: "#ecb79d!important"
        },
        {
            type: "CONSTANT",
            name: "bdc-sd",
            value: "SD_color",
            constant: "rgb(229,0,127)!important",
            font: "#ecb79d!important"
        },
        {
            type: "CONSTANT",
            name: "bdc-yy",
            value: "YY_color",
            constant: "rgb(239,130,0)!important",
            font: "#ecb79d!important"
        },
        {
            type: "CONSTANT",
            name: "bdc-dyi",
            value: "DYI_color",
            constant: "rgb(133,94,110)!important",
            font: "#ecb79d!important"
        },
        {
            type: "CONSTANT",
            name: "bdc-jzq",
            value: "JZQ_color",
            constant: "#13b1c4 !important",
            font: "#ecb79d!important"
        },
        {
            type: "CONSTANT",
            name: "bdc-zs",
            value: "ZS_color",
            constant: "rgb(171, 26, 26)!important",
            font: "#ecb79d!important"
        },
        {
            type: "CONSTANT",
            name: "bdc-zm",
            value: "ZM_color",
            constant: "rgb(66, 177, 30)!important",
            font: "#ecb79d!important"
        },
        {
            type: "CONSTANT",
            name: "bdc-bfz",
            value: "BFZ_color",
            constant: "rgb(214, 182, 220)!important",
            font: "#ecb79d!important"
        }
    ];

    addModel();
    getReturnData("/lpb/yspz", {}, "GET", function (data) {
        var json = {
            colorCofigs: data
        }
        var tpl = statusTpl.innerHTML, view = document.getElementById('status');
        laytpl(tpl).render(json, function (html) {
            view.innerHTML = html;
        });
        form.render();
    }, function (xhr) {
        delAjaxErrorMsg(xhr);
    }, false);
    $.ajax({
        url: "../lpb/list/colorPz",
        type: "post",
        async: false,
        success: function (data) {
            if (data) {
                for (var key in data) {
                    colorList.forEach(function (v, i) {
                        if (key == v.value) {
                            if (v.constant.indexOf("!important") > -1) {
                                v.constant = data[key].constant;
                            } else {
                                v.constant = data[key].constant + "!important";
                            }
                            if (v.font.indexOf("!important") > -1) {
                                v.font = data[key].font;
                            } else {
                                v.font = data[key].font + "!important";
                            }

                            if($('.'+ v.name + '-back').length > 0){
                                $('.' + v.name + '-back').css('background-color', v.constant);
                                $('.' + v.name + '-back p').css('color', v.font);
                            }
                            $('.' + v.name + '-a').attr('style', 'color:' + v.font + ";cursor: pointer");
                        }
                    });
                }
            }
            removeModal();
        },
        error: function (xhr, status, error) {
            delAjaxErrorMsg(xhr, this);
        }
    });
    colorList.forEach(function (v, i) {
        colorpicker.render({
            elem: '.' + v.name + '-config'
            , color: v.constant
            , done: function (color) {
                if (i < 4) {
                    //前四位设置底色
                    $('.' + v.name + '-back').css('background-color', color);
                } else {
                    //下面的设置色块
                    $('.' + v.name).css('background-color', color);
                }
                v.constant = color + "!important";
            }
        });
        colorpicker.render({
            elem: '.' + v.name + '-font'
            , color: v.font
            , done: function (color) {
                //前四位设置底色
                $('.' + v.name + '-back p').css('color', color);
                v.font = color + "!important";
                //改变色框为字体
                $('.' + v.name + '-a').css('color', color);
                $('.' + v.name + '-a').toggleClass('bdc-hide');
                $('.' + v.name + '-font').toggleClass('bdc-hide');
            }
        });
    });

    $('#saveColor').on('click', function () {
        addModel();
        $.ajax({
            url: "../lpb/update/status/color",
            type: "post",
            contentType: "application/json;charset=UTF-8",
            data: JSON.stringify(colorList),
            success: function (data) {
                removeModal();
                layer.msg("保存成功");
            },
            error: function (xhr, status, error) {
                delAjaxErrorMsg(xhr, this);
            }
        });
    });

    $(".bdc-color-list").click(function (event) {
        //跳转
        if (event.target == this) {
            colorList.forEach(function (v, i) {
                if ($('.' + v.name + '-a').hasClass('bdc-hide')) {
                    $('.' + v.name + '-a').toggleClass('bdc-hide');
                    $('.' + v.name + '-font').toggleClass('bdc-hide');
                }
            });
        }
    });
});

function renderFont(name) {
    $('.' + name + '-a').toggleClass('bdc-hide');
    $('.' + name + '-font').toggleClass('bdc-hide');
    $('.' + name + "-font").find('.layui-colorpicker-trigger-span').trigger("click");
}