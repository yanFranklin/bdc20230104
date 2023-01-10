/**
 * Created by ypp on 2020/2/19.
 */
layui.use(['jquery','laydate','table','laytpl','layer','element','form'], function () {
    var table = layui.table,
        $ = layui.jquery,
        laydate = layui.laydate,
        element = layui.element,
        laytpl = layui.laytpl,
        form = layui.form,
        layer = layui.layer;

    $(function () {
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


        //数据流
        $('.bdc-sjl').height($('.layui-tab').height() - 30);


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
            console.log($('#childFrame').contents().outerHeight(true));
            var iframeHeight = $('#childFrame').contents().outerHeight(true);
            getIframeHeight = iframeHeight;
            $(this).height(iframeHeight + 'px');
            $('.bdc-sjl').height(iframeHeight + 'px');
        }

        //1. 侧边栏点击效果
        //1.1  手风琴点击效果
        $(document).on('click', '.link', function () {
            $(this).parent().siblings().removeClass('bdc-this-li');
            $(this).parent().addClass('bdc-this-li');
            var nowIndex = $(this).parent().index() + 1;
            $('#contentView form').addClass('bdc-hide');
            $('#contentView form:nth-child('+ nowIndex +')').removeClass('bdc-hide');
        });
        //1.2  对于逻辑栋点击高亮
        $(document).on('click', '.submenu a.bdc-last-child', function () {
            var $this = $(this);
            $('.submenu a').removeClass('active');
            $this.addClass('active');
            $('.accordion .bdc-invented-li').removeClass('bdc-this-li');
            $('.content-div iframe').attr('src',$(this).parent('.bdc-inner-li').data('src'));

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
            $('#contentView form').addClass('bdc-hide');
            $('#contentView form:nth-child('+ liIndex +')').removeClass('bdc-hide');
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
                        text: "重命名",
                        func: function () {
                            var $this = $(this);
                            console.log($this);
                            //新增子规则页面标识id
                            var nameIndex = layer.prompt({
                                title: '请修改子表名称',
                                value: $this.find('span').text()
                            }, function (text, index) {
                                layer.close(nameIndex);
                                console.log(text);
                                $this.find('span').text(text);
                                var updateLiIndex = $this.parent().index() + 1;
                                $('.bdc-zb-form:nth-child(' + updateLiIndex + ') input[name="ytmc"]').val(text);
                                //----------------可以在此处获取id传入后台
                            });
                        }
                    }],
                    [{
                        text: "添加子表",
                        func: function () {
                            var $this = $(this);
                            //新增子规则页面标识id
                            var nameIndex = layer.prompt({title: '请输入子表名称'}, function (text, index) {
                                layer.close(nameIndex);
                                $this.parent().siblings().removeClass('bdc-this-li');
                                $('#contentView form').addClass('bdc-hide');
                                $this.parents('.accordion').append('<li class="bdc-outer-li bdc-invented-li bdc-this-li" data-num="230103001006GB00004">' +
                                    ' <a class="link"> ' +
                                    '<img src="../../../static/image/log.png" alt="图标" class="docu-icon">' +
                                    '<span>'+ text +'</span>' +
                                    '</a> ' +
                                    '</li>');
                                var getRightTpl = addZbTpl.innerHTML;
                                laytpl(getRightTpl).render({ytmc: text}, function(html){
                                    $('#contentView').append(html);
                                    form.render('radio');
                                });

                                //----------------可以在此处获取id传入后台
                            });
                        }
                    }],
                    [{
                        text: "删除",
                        func: function () {
                            var $this = $(this);
                            //打印的是当前元素 data- 的所有值，可以根据需求写入获取
                            console.log($this.context.parentNode.dataset);
                            var nowIndex = $this.parent().index() + 1;
                            if($this.parent().hasClass('bdc-this-li')){
                                if($this.parent().next().length > 0){
                                    $this.parent().next().addClass('bdc-this-li');
                                    var nextIndex = nowIndex + 1;
                                    $('#contentView .bdc-zb-form:nth-child('+ nextIndex +')').removeClass('bdc-hide');
                                }else if($this.parent().prev().length > 0){
                                    $this.parent().prev().addClass('bdc-this-li');
                                    var prevIndex = nowIndex - 1;
                                    $('#contentView .bdc-zb-form:nth-child('+ prevIndex +')').removeClass('bdc-hide');
                                }
                            }
                            $this.parent().remove();
                            $('#contentView .bdc-zb-form:nth-child('+ nowIndex +')').remove();
                            console.log('删除');
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