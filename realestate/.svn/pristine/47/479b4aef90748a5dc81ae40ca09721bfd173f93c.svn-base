/**
 * Created by Administrator on 2018/12/21.
 */

layui.use(['jquery','laydate','table','laytpl','layer','element'], function () {
    var table = layui.table,
        $ = layui.jquery,
        laydate = layui.laydate,
        element = layui.element,
        layer = layui.layer;

    $(function () {
        //渲染配置日期控件
        laydate.render({
            elem: '#pzrq',
            type: 'datetime',
        });
        //滚动时头部固定
        var $cnotentTitle = $('.bdc-content-fix');
        var $navContainer = $('.bdc-nav-container');
        function defaultStyle() {
            if($cnotentTitle.length == 1){
                $('.bdc-form-div').css('padding-top','69px');
            }
            if($(window).scrollTop() > 85){
                $cnotentTitle.css('top','0');
                $navContainer.css('top','69px');
            }else if($(window).scrollTop() <= 85){
                $cnotentTitle.css('top','15px');
                $navContainer.css('top','69px');
            }
        }

        $(window).resize(function(){
            defaultStyle();
        });
        $(window).on('scroll',function () {
            if($(this).scrollTop() > 85){
                $cnotentTitle.css('top','0');
                $navContainer.css('top','69px');
            }else if($(this).scrollTop() <= 85){
                $cnotentTitle.css('top','15px');
                $navContainer.css('top','69px');
            }
        });

        //监听tab切换,调整参数变量表格和结果变量表格的样式
        element.on('tab(tabFilter)', function(data){
            // console.log(data.index);
            switch(data.index){
                case 2:
                    table.resize('cslb');
                    table.resize('jglb');
                    table.resize('gzbds');
                    break;
            }
        });

        //数据流
        $('.bdc-sjl').height($('.layui-tab').height() - 30);
        $('.bdc-jy').height($('.layui-tab').height() - 30);


        //获取最外层元素的padding-left的值
        var containerLeft = 300;

        //侧边栏渲染完成获取总共的个数
        var liIndex = 1;

        //新增，根据地址栏参数，默认展示指定内容
        function GetQueryString(name) {
            var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
            var r = window.location.search.substr(1).match(reg);
            if(r!=null)return  unescape(r[2]); return null;
        }
        var getNum = GetQueryString('num');
        if(getNum != null && getNum.toString().length > 1){
            for(var i = 0; i < $('.accordion .bdc-outer-li').length; i++){
                if($($('.accordion .bdc-outer-li')[i]).data('num') == getNum){
                    var nowI = i + 1;
                    $('.accordion .bdc-outer-li:nth-child('+ nowI +')').addClass('open bdc-this-li').find('.submenu').show();
                    $('.content-div iframe').attr('src',$('.accordion .bdc-outer-li:nth-child('+ nowI +') .link').data('src'));
                }
            }
        }

        //动态设置iframe的高度
        var getIframeHeight = 0;
        $('#childFrame').load(setIfameHeiht);
        function setIfameHeiht() {
           // $(this).height('100%');
            console.log($('#childFrame').contents().outerHeight(true));
            var iframeHeight = $('#childFrame').contents().outerHeight(true);
            getIframeHeight = iframeHeight;
            // console.log(iframeHeight);
            $(this).height(iframeHeight + 'px');
            $('.bdc-sjl').height(iframeHeight + 'px');
        }

        //1. 侧边栏点击效果
        //1.1  手风琴点击效果
        $(document).on('click', '.link', function () {
            var $this = $(this);
            $this.parent().toggleClass('open');
            $this.parent().siblings().removeClass('open');
            $this.parent().siblings().find('.bdc-inner-li').removeClass('open');
            $this.parent().siblings().find('.submenu').slideUp();
            $(this).siblings('.submenu').slideToggle();
            
            $('.content-div iframe').attr('src',$this.data('src'));
        });
        //1.2  对于逻辑栋点击高亮
        $(document).on('click', '.submenu a.bdc-last-child', function () {
            var $this = $(this);
            $('.submenu a').removeClass('active');
            $this.addClass('active');
            $('.accordion .bdc-invented-li').removeClass('bdc-this-li');
            $('.content-div iframe').attr('src',$(this).parent('.bdc-inner-li').data('src'));

            // console.log($this.parents('.bdc-outer-li'));
            liIndex = 0;
            for(var i = $this.parents('.bdc-outer-li').index(); i >= 0; i--){
                var getUl = $('.accordion>.bdc-outer-li:nth-child('+i+') .submenu');
                // console.log(getUl);
                if(getUl.length > 0){
                    // console.log(getUl.find('li').length);
                    liIndex += getUl.find('.bdc-inner-li').length + 1;
                }else {
                    liIndex++;
                }
            }
            liIndex += $this.parent().index() + 1;
            // console.log(liIndex);
        });

        //2. 点击侧边栏收缩按钮
        var $asideClose = $('.bdc-aside-close'),
            $asideOpen = $('.bdc-aside-open'),
            $zoom = $('.bdc-aside-zoom'),
            $menuAside = $('.bdc-menu-aside'),
            $container = $('.bdc-container'),
            $zoomLine = $('#asideLine');
        $asideClose.on('click',function () {
            containerLeft = 20;
            $(this).toggleClass('bdc-hide');
            $asideOpen.toggleClass('bdc-hide');
            $zoom.animate({'left':'-5px'},100);
            $menuAside.animate({'left': -($menuAside.width() + 20) + 'px'},100);
            $zoomLine.animate({'left': '-24px'},100);
            $container.animate({'padding-left':'20px'},100);
        });
        $asideOpen.on('click',function () {
            containerLeft = 300;
            $(this).toggleClass('bdc-hide');
            $asideClose.toggleClass('bdc-hide');
            $zoom.animate({'left': $menuAside.width() - 1 + 'px'},100);
            $menuAside.animate({'left':'0'},100);
            $zoomLine.animate({'left': $menuAside.width() - 4 + 'px'},100);
            $container.animate({'padding-left':'300px'},100);
        });

        //监听右键
        $("#accordion").on("mousedown",'a',(function (e) {
            if (e.which == 3) {
                var opertionn = {
                    name: "",
                    offsetX: 2,
                    offsetY: 2,
                    textLimit: 10,
                    beforeShow: $.noop,
                    afterShow: $.noop
                };

                var imageMenuData = [
                    [{
                        text: "添加数据流",
                        func: function () {
                           if($(this).hasClass('link')){
                               if(!$(this).parent().hasClass('open')){
                                   $(this).click();
                               }
                               var $this = $(this);
                               //新增子规则页面标识id
                               var sjlid = createUUID();
                               var nameIndex = layer.prompt({title: '请输入数据流名称'}, function(text, index){
                                   layer.close(nameIndex);
                                   var liHtml = '<li class="bdc-inner-li bdc-invented-li" data-src="../../../view/engine/zgz/bdcZgzSjl.html?sjlmc='+ encodeURI(text) +'&sjlid='+ sjlid +'">'+
                                       '<a href="javascript:;" class="bdc-last-child">'+
                                       '<img src="../../../static/image/log.png" alt="图标" class="docu-icon" name="sjlmc">' + text +
                                       '</a>'+
                                       '</li>';
                                   var $submenu = $this.parent().children('.submenu');
                                   $submenu.append(liHtml);
                                   $submenu.find("li:last-child").find("a").trigger("click");
                               });
                           }else {
                               layer.msg('请选择在文件夹下操作');
                           }
                        }
                    }],
                    [{
                        text: "删除",
                        func: function () {
                            var $li = $(this).context.parentNode;
                            var $ul = $(this).parents(".submenu");
                            var sjlsrc = $li.dataset.src;
                            var index=sjlsrc.lastIndexOf("\=");
                            var sjlid =sjlsrc.substring(index+1,sjlsrc.length);
                            var bdcGzSjlDTOList = bdcGzZgzDTO.bdcGzSjlDTOList;

                            layer.open({
                                type: 1,
                                skin: 'bdc-small-tips',
                                title: '确认删除',
                                area: ['320px'],
                                content: '是否确认删除？',
                                btn: ['确定', '取消'],
                                btnAlign: 'c',
                                yes: function (index, layero) {
                                    //获取要删除的数据流页面的页面id
                                    var sjlIds = [];
                                    for(var i in bdcGzSjlDTOList){
                                        if(bdcGzSjlDTOList[i].sjlid == sjlid){
                                            sjlIds.push(sjlid);
                                            bdcGzSjlDTOList.splice(i, 1);
                                            break;
                                        }
                                    }
                                    sessionStorage.setItem("deleteSjlId",sjlIds);
                                    $li.remove();
                                    $ul.find("li:first-child").find("a").trigger("click");
                                    layer.close(index);
                                },
                                btn2: function (index, layero) {
                                    layer.close(index);
                                }
                            });
                        }
                    }]
                ];
                $(this).smartMenu(imageMenuData, opertionn);
            }
        }));


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
                    oBox.style.width = '280px';
                    oLine.style.left = oBox.offsetWidth - 4 + 'px';
                    $zoom.css('left','280px');
                }

            };
            document.onmouseup=function(){
                document.onmousedown=null;
                document.onmousemove=null;
            };
            return false;
        };


    });


});

var BASE_URL = '/realestate-inquiry-ui';

//有数据时，生成插件
function creatGzSjl(data) {
    var opertionn = {
        name: "",
        offsetX: 2,
        offsetY: 2,
        textLimit: 10,
        beforeShow: $.noop,

        afterShow: $.noop
    };

    if (bdcGzZgzDTO.bdcGzSjlDTOList) {
        for (var i = 0; i < bdcGzZgzDTO.bdcGzSjlDTOList.length; i++) {
            $('.submenu').append('<li class="bdc-inner-li bdc-invented-li" ' +
                'data-src="../../../view/engine/zgz/bdcZgzSjl.html?sjlmc=' + encodeURI(bdcGzZgzDTO.bdcGzSjlDTOList[i].sjlmc) + '&sjlid=' + bdcGzZgzDTO.bdcGzSjlDTOList[i].sjlid + '">' +
                '<a href="javascript:;" class="bdc-last-child">' +
                '<img src="../../../static/image/log.png" alt="图标" class="docu-icon" name="sjlmc">' + bdcGzZgzDTO.bdcGzSjlDTOList[i].sjlmc +
                '</a>' +
                '</li>')
        }
        ;
    }
}
