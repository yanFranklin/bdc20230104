/**
 * Created by Ypp on 2019/10/12.
 * 颜色配置js
 */
layui.use(['colorpicker','form'], function(){
    var colorpicker = layui.colorpicker,
        $ = layui.jquery,
        form = layui.form;

    var colorList = [
        {name: "bdc-wdj", color:"#ecb79d"},
        {name: "bdc-yyg", color:"#ffca00"},
        {name: "bdc-ydj", color:"#66ccff"},
        {name: "bdc-yzx", color:"#c0c0c0"},
        {name: "bdc-ks", color:"#00ff00"},
        {name: "bdc-ys", color:"#ffff00"},
        {name: "bdc-cq", color:"#ff00ff"},
        {name: "bdc-zjgcdy", color:"#99a57c"},
        {name: "bdc-ydy", color:"#aacd06"},
        {name: "bdc-dy", color:"#009944"},
        {name: "bdc-cf", color:"#e50012"},
        {name: "bdc-ycf", color:"#ee847d"},
        {name: "bdc-dj", color:"#e5007f"},
        {name: "bdc-yy", color:"#ef8200"},
        {name: "bdc-dyi", color: "#855e6e"},
        {name: "bdc-jzq", color: "#13b1c4"}
    ];
    var changeColorObj = {
        "bdc-wdj": "#ecb79d",
        "bdc-yyg": "#ffca00",
        "bdc-ydj": "#66ccff",
        "bdc-yzx": "#c0c0c0",
        "bdc-ks": "#00ff00",
        "bdc-ys": "#ffff00",
        "bdc-cq": "#ff00ff",
        "bdc-zjgcdy": "#99a57c",
        "bdc-ydy": "#aacd06",
        "bdc-dy": "#009944",
        "bdc-cf": "#e50012",
        "bdc-ycf": "#ee847d",
        "bdc-dj": "#e5007f",
        "bdc-yy": "#ef8200",
        "bdc-dyi": "#855e6e",
        "bdc-jzq": "#13b1c4"

    };
    colorList.forEach(function (v,i) {
        colorpicker.render({
            elem: '.'+ v.name +'-config'
            ,color: v.color
            ,done: function(color){
                if(i < 4){
                    $('.' + v.name).css('color',color);
                }else {
                    $('.' + v.name).css('background-color',color);
                }
                changeColorObj[v.name] = color;
            }
        });
    });

    $('#saveColor').on('click',function () {
        console.log(changeColorObj);
    });
});