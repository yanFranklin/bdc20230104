/**
 * Created by  on 2019/11/1.
 * 颜色配置js
 */
layui.use(['colorpicker', 'form'], function() {
    var colorpicker = layui.colorpicker,
        $ = layui.jquery,
        form = layui.form;

    var colorList = [
        {name: "bdc-dya", color: "#a082e3"},
        {name: "bdc-sd", color: "#a7afb6"},
        {name: "bdc-yy", color: "#e87a73"},
        {name: "bdc-cf", color: "#e87a73"},
        {name: "bdc-yg", color: "#ea63dd"},
        {name: "bdc-jzq", color: "#13b1c4"},
        {name: "bdc-zzbl", color: "#f7c654"},
        {name: "bdc-qlr", color: "#f98b3e"},
        {name: "bdc-fwcq", color: "#aaaaaa"},
        {name: "bdc-btx", color: "#fef6e1"}
    ];

    var changeColorObj = {
        "bdc-dya": "#a082e3",
        "bdc-sd": "#a7afb6",
        "bdc-yy": "#e87a73",
        "bdc-cf": "#e87a73",
        "bdc-yg": "#ea63dd",
        "bdc-jzq": "#13b1c4",
        "bdc-zzbl": "#f7c654",
        "bdc-qlr": "#f98b3e",
        "bdc-fwcq": "#aaaaaa",
        "bdc-btx": "#fef6e1"
    };

    $.ajax({
        url: '/realestate-inquiry-ui/rest/v1.0/yspz/status/color',
        type: "GET",
        async: false,
        success: function(data) {
            if (isNotBlank(data)){
                changeColorObj = data;
            } else {
                changeColorObj = {};
            }
            colorList.forEach(function(v, i) {
                if (v.name === "bdc-btx" && isNullOrEmpty(changeColorObj[v.name])) {
                    //必填项优先读取默认值
                    $('.' + v.name).children('td:not(:last-child)').css('background-color', v.color);
                } else {
                    v.color = changeColorObj[v.name];
                    $('.' + v.name).children('td:not(:last-child)').css('background-color', v.color);
                }
            });
            colorList.forEach(function(v, i) {
                colorpicker.render({
                    elem: '.' + v.name + '-config',
                    color: v.color,
                    done: function(color) {
                        $('.' + v.name).children('td:not(:last-child)').css('background-color', color);
                        changeColorObj[v.name] = color;
                    }
                });
            });
        }
    });

    $('#saveColor').on('click', function() {
        console.info(changeColorObj);
        addModel();
        $.ajax({
            url: '/realestate-inquiry-ui/rest/v1.0/yspz/status/color',
            type: "POST",
            dataType: "json",
            contentType: 'application/json',
            data: JSON.stringify(changeColorObj),
            async: false,
            success: function(data) {
                removeModal();
                saveSuccessMsg();
            },error:function(){
                delFailMsg();
            }
        });

    });

});