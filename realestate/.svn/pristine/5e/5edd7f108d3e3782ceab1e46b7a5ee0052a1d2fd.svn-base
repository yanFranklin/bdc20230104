/**
 * Created by Yuan on 2019/7/12.
 */
layui.use(['form','jquery','table','layer', 'laytpl'],function () {
    var $ = layui.jquery,
        form = layui.form,
        table = layui.table,
        layer = layui.layer,
        laytpl = layui.laytpl;
    $(function () {
        //3. 滚动时头部固定
        var $cnotentTitle = $('.bdc-content-fix');
        var $navContainer = $('.bdc-nav-container');
        function defaultStyle() {
            if($cnotentTitle.length == 1){
                $('.bdc-form-div').css('padding-top','68px');
            }
            if($(window).scrollTop() > 85){
                $cnotentTitle.css('top','0');
                $navContainer.css('top','58px');
            }else if($(window).scrollTop() <= 85){
                $cnotentTitle.css('top','10px');
                $navContainer.css('top','68px');
            }
        }

        $(window).resize(function(){
            defaultStyle();
        });
        $(window).on('scroll',function () {
            if($(this).scrollTop() > 85){
                $cnotentTitle.css('top','0');
                $navContainer.css('top','58px');
            }else if($(this).scrollTop() <= 85){
                $cnotentTitle.css('top','10px');
                $navContainer.css('top','68px');
            }
        });


        // 获取参数
        var xmid = getQueryString('xmid');

        // 获取房屋套次数据
        addModel();
        getFwtcxx(xmid, function (data) {
            generateFwtcxx(data);
            removeModal();
        });

        //房屋查询
        $("#fwxxcx").click(function () {
            addModel();
            setTimeout(function () {
                //获取房屋数据
                getReturnData("/ycsl/fwcx",{xmid:xmid},"GET",function (data) {
                    getFwtcxx(xmid, function (data) {
                        generateFwtcxx(data);
                    });
                    //刷新父页面
                    if (parent && 'function' === typeof (parent.loadYcslxx())) {
                        parent.loadYcslxx();
                    }
                    removeModal();
                },function(err){
                    removeModal();
                    delAjaxErrorMsg(err);
                });
            }, 10);
        });

        /**
         * 组织房屋信息到页面
         */
        function generateFwtcxx(fwxxdata) {
            //获取数据到页面
            var json = {
                fwxxlist: fwxxdata.bdcZrfZfxxDTOList
            };
            //转入方
            var getTpl = fwxxTpl.innerHTML,view = document.getElementById('zrffwxx');
            laytpl(getTpl).render(json, function (html) {
                view.innerHTML =html;
            });
            var json = {
                fwxxlist: fwxxdata.bdcZcfZfxxDTOList
            };
            //转出方
            var tpl = fwxxTpl.innerHTML, view = document.getElementById('zcffwxx');
            //渲染数据
            laytpl(tpl).render(json, function (html) {
                view.innerHTML = html;
            });
        }

        /**
         * 获取房屋套次信息数据
         */
        function getFwtcxx(xmid,callback){
            getReturnData("/ycsl/fwtcxx",{xmid:xmid},"GET",function (data) {
                callback(data);
            },function (error) {
                delAjaxErrorMsg(error);
            })
        }

    });
});