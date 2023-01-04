layui.use(['jquery', 'layer', 'element', 'form', 'laytpl'],function () {
    form = layui.form;
    $ = layui.jquery;
    var laytpl = layui.laytpl;
    var xmid = getQueryString("xmid");


    $(function () {

        // 监听目录点击事件
        $('.bdc-container-box').on('click','.link',function () {
            $(this).parents('ul').find('a').removeClass('active');
            $(this).addClass('active');
            var id = $(this).attr('id');
            $('div.fpyzxx').hide();
            if($(this).hasClass('first-click')){
                $(this).removeClass('first-click');
                switch (id) {
                    case 'hyxx':
                        generateHyxx();
                        break;
                    case 'qyxx':
                        generateQyxx();
                        break;
                }
            }else {
                $('div.'+id).show();
            }
        })

        // 点击侧边栏收缩按钮
        var $asideClose = $('.bdc-aside-close'),
            $asideOpen = $('.bdc-aside-open'),
            $zoom = $('.bdc-aside-zoom'),
            $menuAside = $('.bdc-menu-aside'),
            $container = $('.bdc-container');
        $asideClose.on('click',function () {
            containerLeft = 20;
            $(this).toggleClass('bdc-hide');
            $asideOpen.toggleClass('bdc-hide');
            $zoom.animate({'left':'-5px'},100);
            $menuAside.animate({'left': -($menuAside.width() + 20) + 'px'},100);
            $container.animate({'padding-left':'20px'},100);
        });
        $asideOpen.on('click',function () {
            containerLeft = 300;
            $(this).toggleClass('bdc-hide');
            $asideClose.toggleClass('bdc-hide');
            $zoom.animate({'left': $menuAside.width() - 1 + 'px'},100);
            $menuAside.animate({'left':'0'},100);
            $container.animate({'padding-left':'240px'},100);
        });

        //绑定需要拖拽改变大小的元素对象
        var oBox = document.getElementById('asideBox');
        var oLine = document.getElementById('asideLine');
        oLine.onmousedown = function(ev){
            document.onmousemove=function(ev){
                var iEvent = ev||event;
                oBox.style.width =  iEvent.clientX + 'px';
                oLine.style.left = iEvent.clientX - 4 + 'px';
                $zoom.css('left',iEvent.clientX - 1);
                if(oBox.offsetWidth <= 280){
                    oBox.style.width = '220px';
                    oLine.style.left = oBox.offsetWidth - 4 + 'px';
                    $zoom.css('left','220px');
                }

            };
            document.onmouseup=function(){
                document.onmousedown=null;
                document.onmousemove=null;
            };
            return false;
        };

    });

    function generateHyxx() {
        getReturnData("/rest/v1.0/zdyympz/hyxx/"+xmid,"","GET",function (data) {
            var qlrList=[],ywrList=[];
            if(isNotBlank(data)){
                // 重新组织权利人义务人数据
                qlrList=generatrQlrList(data[0].qlrBdcFbyzHyxxMxList,data[1].qlrBdcFbyzHyxxMxList);
                ywrList=generatrQlrList(data[0].ywrdcFbyzHyxxMxList,data[1].ywrdcFbyzHyxxMxList);
            }
            var json ={
                qlrList:qlrList,
                ywrList:ywrList
            };

            var getTpl = hyxxTpl.innerHTML;
            laytpl(getTpl).render(json, function (html) {
                $('.bdc-content-box .layui-form').append(html);
            });
            fpyzTable();
            
        },function (error) {
            delAjaxErrorMsg(error);

        });
    }

    function generateQyxx() {
        getReturnData("/rest/v1.0/zdyympz/qyxx/"+xmid,"","GET",function (data) {
            var qlrList=[],ywrList=[];
            if(isNotBlank(data)){
                // 重新组织企业数据
                qlrList=generatrQlrList(data[0].qlrFpyzQyxxMxVOList,data[1].qlrFpyzQyxxMxVOList);
                ywrList=generatrQlrList(data[0].ywrdcFbyzHyxxMxList,data[1].ywrdcFbyzHyxxMxList);
            }
            var json ={
                qlrList:qlrList,
                ywrList:ywrList
            };

            var getTpl = qyxxTpl.innerHTML;
            laytpl(getTpl).render(json, function (html) {
                $('.bdc-content-box .layui-form').append(html);
            });
            fpyzTable();

        },function (error) {
            delAjaxErrorMsg(error);
        });
    }

    // 分屏验证不一致高亮显示
    function fpyzTable() {
        $('tbody tr').each(function (index,item) {
            var str1 = $(item).find('.str1').val(),
                str2 = $(item).find('.str2').val();
            if(str1 != str2){
                $(item).addClass('bdc-change-color');
            }
        });
    }

    //权利人义务人数据处理
    function generatrQlrList(djList,jkList) {
        var list = [];
        if(djList && djList.length>0){
            for (var i = 0; i < djList.length; i++) {
                var qlr = {};
                qlr.djxx = djList[i];
                qlr.jkxx = jkList[i];
                list.push(qlr);
            }
        }
        return list;
    }

});