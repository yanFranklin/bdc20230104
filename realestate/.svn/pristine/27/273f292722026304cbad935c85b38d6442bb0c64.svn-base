var currentXmxx =[];
var currentDysd =[];
var xmid ="";
var dysdid ="";
layui.use(['form','jquery','laydate','element','layer','table'],function () {
    var $ = layui.jquery,
        laytpl = layui.laytpl;
    $(function () {

        xmid =getQueryString("xmid");
        var xmtype =getQueryString("xmtype");
        dysdid =getQueryString("dysdid");


        loadMenu();


        //1. 侧边栏点击效果
        //1.1  手风琴点击效果
        $(document).on('click', '.link', function () {
            var $this = $(this);
            $this.parent().toggleClass('open');
            $(this).siblings('.submenu').slideToggle();

        });
        $(document).on('click', '.childrenlink', function () {
            var $this = $(this);
            $this.parent().toggleClass('open');
            $(this).siblings('.secondmenu').slideToggle();

        });
        //1.2 点击箭头
        $('.accordion').on('click', '.bdc-outer-li i', function (event) {
            event.stopPropagation();
            if ($(this).hasClass("fa-caret-right")) {
                //点击箭头,自动加载目录
                if (!$(this).parents(".bdc-outer-li").hasClass('bdc-render-child')) {
                    var $outer = $(this).parents(".bdc-outer-li");
                    $outer.addClass('bdc-render-child');
                }
            }
            var $this = $(this).parent();
            $this.parent().toggleClass('open');
            $this.parent().siblings().removeClass('open');
            $this.parent().siblings().find('.bdc-inner-li').removeClass('open');
            $this.parent().siblings().find('.submenu').slideUp();
            $this.siblings('.submenu').slideToggle();
        });
        //渲染模块数据
        function renderTpl(json, mkid, getTpl) {
            var dataView = document.getElementById(mkid);
            laytpl(getTpl).render(json, function (html) {
                dataView.innerHTML = html;
            });
        }

        /**
         * 显示多级级树菜单
         */
        function loadMenu() {
            if(isNotBlank(xmid)) {
                // 初始化
                getReturnData("/rest/v1.0/dyhgz/xmxx?xmid=" + xmid + "&xmtype=" + xmtype, {}, "GET", function (data) {
                    currentXmxx = data.bdcXmDOList;
                    // 渲染数据
                    var cqxxJson = {};
                    var cqglLsgxXmDTO = data.cqglLsgxXmDTO;
                    if (cqglLsgxXmDTO != null) {
                        var bdcXmDO = cqglLsgxXmDTO.bdcXmDO
                        cqxxJson.bdcqzh = bdcXmDO.bdcqzh;
                        cqxxJson.cqXzqlLsgxModelList = cqglLsgxXmDTO.cqXzqlLsgxModelList;

                        renderTpl(cqxxJson, 'cqzh', cqxxTpl.innerHTML);
                    }
                    //匹配产权数据
                    var ppcqLsgxXmDTO = data.ppcqLsgxXmDTO;
                    if (ppcqLsgxXmDTO != null) {
                        //渲染匹配产权数据
                        var ppcqxxJson = {};
                        ppcqxxJson.bdcqzh = ppcqLsgxXmDTO.bdcXmDO.bdcqzh;
                        ppcqxxJson.cqXzqlLsgxModelList = ppcqLsgxXmDTO.cqXzqlLsgxModelList;
                        renderTpl(ppcqxxJson, 'ppzh', ppxxTpl.innerHTML);
                    }
                    ;
                    //限制权利信息
                    var xzqlLsgxXmDTO = data.xzqlLsgxXmDTO;
                    if (xzqlLsgxXmDTO != null) {
                        var xzxxJson = {};
                        xzxxJson.lsgxXzqlModelDTO = xzqlLsgxXmDTO;
                        renderTpl(xzxxJson, 'xzql', xzxxTpl.innerHTML);
                    }
                    removeModal();

                }, function (error) {
                    delAjaxErrorMsg(error);
                });
            }else if(isNotBlank(dysdid)){
                //锁定信息
                getReturnData("/rest/v1.0/dyhgz/dysdxx?dysdid=" + dysdid, {}, "GET", function (data) {
                    if(data ==null){
                       data =[];
                    }
                    currentDysd = data;
                    var sdxxJson = {};
                    sdxxJson.sdxx = currentDysd;
                    renderTpl(sdxxJson, 'xzql', dysdxxTpl.innerHTML);

                }, function (error) {
                    delAjaxErrorMsg(error);
                });
            }else{
                layer.alert("缺失数据");
            }

        }
    })

});

//增加项目信息
function addXmxx(xmtype){
    //数据重复校验
    var yxidData = JSON.parse(sessionStorage.getItem("dyhgz_yxid"));
    if(yxidData &&yxidData.length >0){
        if(xmtype ===1){
            layer.alert("一个不动产单元只允许选择一条现势产权,请勿重复添加");
            return false;
        }else if(xmtype ===2 &&((isNotBlank(xmid) &&yxidData.indexOf(xmid) >-1) ||(isNotBlank(dysdid) &&yxidData.indexOf(dysdid) >-1))) {
            layer.alert("新增的权利已添加,请勿重复添加");
            return false;
        }
    }
    if(xmtype ===1){
        sessionStorage.removeItem("dyhgz_yxxmsdxx");
        sessionStorage.removeItem("dyhgz_yxid");

        //记录产权项目ID
        sessionStorage.setItem("dyhgz_cqxmid",xmid);
    }
    var xmsdData = {};
    var idData =[];

    if(sessionStorage.getItem("dyhgz_yxxmsdxx") &&sessionStorage.getItem("dyhgz_yxxmsdxx") !="undefined") {
        xmsdData = JSON.parse(sessionStorage.getItem("dyhgz_yxxmsdxx"));
        idData = JSON.parse(sessionStorage.getItem("dyhgz_yxid"));
        if(xmsdData) {
            if (xmsdData.xmxx && xmsdData.xmxx.length > 0) {
                for (var i = 0; i < currentXmxx.length; i++) {
                    xmsdData.xmxx.push(currentXmxx[i]);
                    idData.push(currentXmxx[i].xmid);
                }
            }else{
                xmsdData.xmxx =currentXmxx;
            }
            if (xmsdData.dysdxx && xmsdData.dysdxx.length > 0) {
                for (var i = 0; i < currentDysd.length; i++) {
                    xmsdData.dysdxx.push(currentDysd[i]);
                    idData.push(currentDysd[i].dysdid);
                }
            }else{
                xmsdData.dysdxx=currentDysd;
            }
        }
    }else{
        xmsdData.xmxx =currentXmxx;
        xmsdData.dysdxx=currentDysd;
        xmsdData.ygxx=[];
        if (currentXmxx && currentXmxx.length > 0) {
            for (var i = 0; i < currentXmxx.length; i++) {
                idData.push(currentXmxx[i].xmid);
            }
        }
        if (currentDysd && currentDysd.length > 0) {
            for (var i = 0; i < currentDysd.length; i++) {
                idData.push(currentDysd[i].dysdid);
            }
        }
    }
    xmsdData.yxcount =xmsdData.xmxx.length+xmsdData.dysdxx.length+xmsdData.ygxx.length;
    sessionStorage.setItem("dyhgz_yxxmsdxx",JSON.stringify(xmsdData));
    sessionStorage.setItem("dyhgz_yxid",JSON.stringify(idData));

    if(xmtype ===1) {
        parent.layer.closeAll();
    }else{
        parent.parent.layer.closeAll();

    }
    var data ={};
    data.XMID =xmid;
    parent.yxxx({},data);


}






