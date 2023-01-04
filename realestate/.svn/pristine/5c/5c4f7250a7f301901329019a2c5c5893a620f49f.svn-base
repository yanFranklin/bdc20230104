// 查询参数
var gzlslid= $.getUrlParam("processInsId");
//目录
var menuList=[];
//未查询接口集合
var wcxjkArr =[];
//查询成功接口集合
var successjkArr =[];
//查询失败接口集合
var failjkArr =[];
layui.use(['form','jquery','laydate','element','layer','table'],function () {
    var $ = layui.jquery,
        laytpl = layui.laytpl,
        element = layui.element;

    $(function () {
        loadMenu();

        //监听 一级 第一次点击
        $(document).on('click', '.bdc-outer-li', function(){
            $(this).find('a').filter(':first').addClass('active');
            $(this).siblings('.submenu').find('.link').click;
        });

        //监听 二级菜单点击
        $(document).on('click', '.bdc-outer-li .bdc-inner-li', function(e){
            e.stopPropagation();
            $('.accordion .bdc-invented-li a').removeClass('active');
        });

        //1. 侧边栏点击效果
        //1.1  菜单点击展示其下内容
        $(document).on('click', '.link', function () {
            var $this = $(this);
            $this.parent().toggleClass('open');

            $(this).siblings('.submenu').slideToggle(function () {
            });
            if($this.data('url')){
                openGxlc($this.data('id'),$this.data('url'),$this.data('mc'));
            }
        });
        //1.2  对于逻辑栋点击高亮, 点击查询接口并改变状态
        $(document).on('click', '.submenu a.bdc-last-child', function () {
            var $this = $(this);
            $('.submenu a').removeClass('active');
            $this.addClass('active');
            $('.accordion .bdc-invented-li').removeClass('bdc-this-li');
            $('.content-div iframe').attr('src',$(this).parent('.bdc-inner-li').data('src'));
            if($this.data('url')){
                openGxlc($this.data('id'),$this.data('url'),$this.data('mc'));
            }
        });
        //1.3 点击箭头
        $('.accordion').on('click','.bdc-outer-li i',function (event) {
            event.stopPropagation();
            var $this = $(this).parent();
            $this.parent().toggleClass('open');
            $this.siblings('.submenu').slideToggle();
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

    function loadMenu() {
        addModel();
        $.ajax({
            url:"/realestate-inquiry-ui/rest/v1.0/gx/shij/ml/" + gzlslid,
            type: "POST",
            contentType: 'application/json',
            success: function (data) {
                removeModal();
                if(!isNullOrEmpty(data) && data.length>0){
                    addSecondMenu(data);
                    generateMenu(menuList);
                }
            },
            error: function (xhr, status, error) {
                removeModal();
                delAjaxErrorMsg(xhr)
            }
        });
    }

    // 添加二级目录
    function addSecondMenu(data) {
        //默认全部未查询
        for(var i=0; i<data.length; i++){
            if(isNotBlank(data[i])){
                for(var j=0; j<data[i].children.length; j++){
                    data[i].children[j].class = data[i].children[j].name;
                    wcxjkArr.push(data[i].children[j].class);
                }
            }
            menuList.push(data[i]);
        }
        $("#wcxNum").text(wcxjkArr.length);
    }

    // 组织目录数据到页面
    function generateMenu(menuList) {
        if(menuList.length > 0){

            //渲染菜单
            var getAsideTpl = menuTpl.innerHTML
                ,asideView = document.getElementById('accordion');
            laytpl(getAsideTpl).render(menuList, function(html){
                asideView.innerHTML = html;
            });
            // 只执行了下拉 未触发a标签点击事件
            eval($('#accordion a').filter(':first').attr("href"));
            //默认全部展开
            $('.submenu').css('display','block');
            $('.accordion li').toggleClass('open');


            // 点击目录标题，让其处于选中状态
            $('a.bdc-last-child').on('click', function() {
                $('a.bdc-last-child').parent().css({
                    'background': 'transparent'
                });

                $(this).parent().css({
                    'background': '#E0F2F7'
                });
            });


        }else {
            warnMsg(" 未查询到流程数据！");
        }
    }

    // 打开查询页面
    function openGxlc(id,url,mlmc) {
        $('#contentView .childFrame').hide();
        if($('#contentView').find('#' + id).length>0){
            $('#contentView').find('#' + id).show();
        }else {
            if (url.indexOf("?") != -1) {
                url =url +"&gzlslid="+gzlslid+"&mlmc="+encodeURI(mlmc)+"&dylx="+id;
            }else{
                url =url +"?gzlslid="+gzlslid+"&mlmc="+encodeURI(mlmc)+"&dylx="+id;
            }
            //jkname用来标识接口是否调用成功
            if (url.indexOf("jkname") == -1) {
                url =url +"&jkname="+id;
            }
            var iframe = '<iframe class="childFrame" id="' + id + '" src="'
                + url + '"> </iframe>'

            $('#contentView').append(iframe);
        }
    }


});

//处理查询结果
function dealCxjg(result,jkname){
    if(result ==="success"){
        if(successjkArr.indexOf(jkname) <0) {
            //查询成功数量加1
            var successNum = parseInt($("#successNum").text());
            $("#successNum").text(successNum + 1);

            if (wcxjkArr.indexOf(jkname) > -1) {
                var wcxNum =parseInt($("#wcxNum").text());
                //未查询数量减1
                $("#wcxNum").text(wcxNum -1);
                wcxjkArr.forEach(function(item, index, arr) {
                    if(item == jkname) {
                        arr.splice(index, 1);
                    }
                });
            }else{
                var failNum =parseInt($("#failNum").text());
                //查询失败数量减1
                $("#failNum").text(failNum -1);
                failjkArr.forEach(function(item, index, arr) {
                    if(item == jkname) {
                        arr.splice(index, 1);
                    }
                });
            }
            successjkArr.push(jkname);
            //图标处理
            var $child =$("#img_"+jkname).parent(".bdc-last-child");
            $("#img_"+jkname).remove();
            if($child.length >0) {
                $child.append("<img src=\"../../static/image/cx-success.png\" alt=\"图标\" class=\"cxzt cx-success\" id=img_'" + jkname + "</img>");
            }
        }
    }else if(result ==="fail"){
        if(failjkArr.indexOf(jkname) <0) {
            //查询失败数量加1
            var failNum = parseInt($("#failNum").text());
            $("#failNum").text(failNum + 1);

            if (wcxjkArr.indexOf(jkname) > -1) {
                var wcxNum =parseInt($("#wcxNum").text());
                //未查询数量减1
                $("#wcxNum").text(wcxNum -1);
                wcxjkArr.forEach(function(item, index, arr) {
                    if(item == jkname) {
                        arr.splice(index, 1);
                    }
                });
            }else{
                var successNum =parseInt($("#successNum").text());
                //查询失败数量减1
                $("#successNum").text(successNum -1);
                successjkArr.forEach(function(item, index, arr) {
                    if(item == jkname) {
                        arr.splice(index, 1);
                    }
                });
            }
            failjkArr.push(jkname);
            //图标处理
            var $child =$("#img_"+jkname).parent(".bdc-last-child");
            $("#img_"+jkname).remove();
            if($child.length >0) {
                $child.append("<img src=\"../../static/image/cx-fail.png\" alt=\"图标\" class=\"cxzt cx-fail\" id=img_'" + jkname + "</img>");
            }
        }

    }

}
