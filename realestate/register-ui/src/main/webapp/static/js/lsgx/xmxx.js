/**
 * author: <a href="mailto:zhuyong@gtmap.cn>zhuyong</a>
 * version 1.0.0  2019/07/30
 * describe: 项目基本信息页面，用于展示历史登记数据
 */
var zdList = [];
var form;
var laytpl;
var $;
// 获取参数
var xmid = $.getUrlParam('xmid');
layui.use(['table', 'laytpl', 'laydate', 'layer', 'form'], function () {
    $ = layui.jquery;
    laytpl = layui.laytpl;
    form = layui.form;

    if(!isNullOrEmpty(xmid)){

        getZdList();
        getXmxx();
        setTimeout(function(){

            getQlrxx();
        },500)
    }
});

function getZdList(){
    $.ajax({
        url: '/realestate-register-ui/bdczd',
        type: "POST",
        async: false,
        success: function (data) {
            zdList = data;
        }
    });
}

function getXmxx(){
    $.ajax({
        url: '/realestate-register-ui/rest/v1.0/bdcdy/xmxx?xmid=' + xmid,
        type: "GET",
        dataType: "json",
        success: function (data) {
            if(data) {
                form.val('form', data);

                if(data.sqfbcz == 0){
                    $("#cz0").attr("checked", true);
                } else if(data.sqfbcz == 1){
                    $("#cz1").attr("checked", true);
                }

                if(data.mjdw == 1){
                    $("#mjdw1").attr("checked", true);
                    $("#mjdw2").removeAttr('checked');;
                    $("#mjdw3").removeAttr('checked');;
                } else if(data.mjdw == 2){
                    $("#mjdw2").attr("checked", true);
                    $("#mjdw1").removeAttr('checked');;
                    $("#mjdw3").removeAttr('checked');;
                } else if(data.mjdw == 3){
                    $("#mjdw3").attr("checked", true);
                    $("#mjdw1").removeAttr('checked');;
                    $("#mjdw2").removeAttr('checked');;
                }
                form.render();
            }
        }
    });
}

function getQlrxx(){
    $.ajax({
        url: '/realestate-register-ui/rest/v1.0/bdcdy/qlr?xmid=' + xmid,
        type: "GET",
        dataType: "json",
        success: function (data) {

            var json = {
                xmid: xmid,
                bdcQlrDOList: data,
                zd: zdList
            };
            var tpl =  sqrTpl.innerHTML;
            var view = $('#sqrView')[0];

            console.log(json.bdcQlrDOList[0].qlrlx);
            //渲染数据
            laytpl(tpl).render(json, function (html) {
                view.innerHTML = html;
            });
            form.render('select');
        }
    })
}