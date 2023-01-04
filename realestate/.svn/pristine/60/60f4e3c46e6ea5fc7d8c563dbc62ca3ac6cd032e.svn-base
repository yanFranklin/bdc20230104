/**
 * Created by Administrator on 2018/12/21.
 */
var xztzlx = getQueryString("xztzlx");
var jbxxid = getQueryString("jbxxid");
var lx = getQueryString("lx");
var type = getQueryString("type");
layui.use(['jquery', 'form', 'laytpl', 'element', 'table'], function () {


    var bdcdyh = getQueryString('bdcdyh');
    var tzym = getQueryString('tzym');
    var cqurl = '/realestate-register-ui/view/lsgx/bdcdyDjLsgxcq.html?bdcdyh='+bdcdyh;
    var cfurl = '/realestate-register-ui/view/lsgx/bdcdyDjLsgxxzql.html?bdcdyh='+bdcdyh;

    getTzym(tzym,bdcdyh,cqurl,cfurl);

});
function getTzym(tzym,bdcdyh,cqurl,cfurl){
    var url;
    $.ajax({
        url: '/realestate-register-ui/rest/v1.0/bdcdy/lsgxtzym?tzym='+tzym,
        type: "GET",
        async: false,
        success: function (data) {
            var arr = [];
            arr = data.split(',');
            if (arr.length>1){
                $('#childFrame1').removeClass('bdc-hide');
                $('li:nth-child(2)').removeClass('bdc-hide');
                $('#childFrame').attr("src",cqurl);
                $('#childFrame1').attr("src",cfurl);
            }else {
                if (data == 'bdcdyDjLsgxcq.html'){
                    $('#childFrame').attr("src",cqurl);
                }else{
                    $('.layui-this').text('限制历史');
                    $('#childFrame').attr("src",cfurl);
                }
            }

        }
    });
}



